package com.una.embyhub.movie.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.una.embyhub.movie.entity.MovieDownloadRecordEntity;
import com.una.embyhub.movie.service.MovieMetadataScrapeService;
import com.una.embyhub.movie.util.MovieEpisodeParser;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.season.TvSeasonEpisode;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeasonsAppendToResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.Generated;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MovieMetadataScrapeServiceImpl implements MovieMetadataScrapeService {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MovieMetadataScrapeServiceImpl.class);
   private static final String DEFAULT_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original";
   private static final Pattern EPISODE_CODE_PATTERN = Pattern.compile("(?i)S(\\d{1,3})E(\\d{1,4})");
   private static final Pattern SEASON_FOLDER_PATTERN = Pattern.compile("(?i)^season\\s*\\d+$");
   private static final Set<String> VIDEO_EXTS = Set.of(
      ".mkv", ".mp4", ".avi", ".mov", ".wmv", ".iso", ".m4v", ".ts", ".m2ts", ".flv", ".webm", ".mpg", ".mpeg", ".rmvb"
   );
   private final TmdbApi tmdbApi;
   @Value("${tmdb.imageUrl:https://image.tmdb.org/t/p/original}")
   private String imageBaseUrl;

   @Override
   public void scrape(MovieDownloadRecordEntity record) {
      if (record != null && record.getTmdbId() != null) {
         if ("movie".equalsIgnoreCase(record.getMediaType())) {
            this.scrapeMovie(record);
         } else if ("tv".equalsIgnoreCase(record.getMediaType())) {
            this.scrapeTv(record);
         } else {
            log.debug("跳过刮削，未知媒体类型 id={} mediaType={}", record.getId(), record.getMediaType());
         }
      }
   }

   private void scrapeMovie(MovieDownloadRecordEntity record) {
      try {
         List<MovieMetadataScrapeServiceImpl.RecordFileInfo> details = this.listRecordFiles(record);
         Path movieRoot = this.resolveMovieRoot(record, details);
         if (movieRoot == null) {
            log.warn("电影刮削失败，未定位到目标目录 id={}", record.getId());
            return;
         }

         Files.createDirectories(movieRoot);
         MovieDb movie = this.tmdbApi.getMovies().getDetails(record.getTmdbId().intValue(), "zh-CN");
         if (movie == null) {
            log.warn("电影刮削失败，TMDB未返回数据 tmdbId={}", record.getTmdbId());
            return;
         }

         String movieNfo = this.buildMovieNfo(movie, record.getTmdbId());
         List<Path> videoFiles = this.collectMovieVideoFiles(record, details);
         if (!videoFiles.isEmpty()) {
            for (Path videoFile : videoFiles) {
               this.writeTextFile(this.toSiblingNfo(videoFile), movieNfo);
            }
         } else {
            this.writeTextFile(movieRoot.resolve("movie.nfo"), movieNfo);
         }

         this.downloadNamedImage(movie.getPosterPath(), movieRoot, "poster");
         this.downloadNamedImage(movie.getBackdropPath(), movieRoot, "backdrop");
         this.downloadNamedImage(movie.getBackdropPath(), movieRoot, "fanart");
      } catch (Exception var9) {
         log.warn("电影刮削失败 id={} tmdbId={}: {}", record.getId(), record.getTmdbId(), var9.getMessage());
      }
   }

   private void scrapeTv(MovieDownloadRecordEntity record) {
      try {
         List<MovieMetadataScrapeServiceImpl.RecordFileInfo> details = this.listRecordFiles(record);
         Path tvRoot = this.resolveTvRoot(record, details);
         if (tvRoot == null) {
            log.warn("剧集刮削失败，未定位到剧集根目录 id={}", record.getId());
            return;
         }

         Files.createDirectories(tvRoot);
         TvSeriesDb tv = this.tmdbApi.getTvSeries().getDetails(record.getTmdbId().intValue(), "zh-CN");
         if (tv == null) {
            log.warn("剧集刮削失败，TMDB未返回数据 tmdbId={}", record.getTmdbId());
            return;
         }

         this.writeTextFile(tvRoot.resolve("tvshow.nfo"), this.buildTvShowNfo(tv, record.getTmdbId()));
         this.downloadNamedImage(tv.getPosterPath(), tvRoot, "poster");
         this.downloadNamedImage(tv.getBackdropPath(), tvRoot, "backdrop");
         this.downloadNamedImage(tv.getBackdropPath(), tvRoot, "fanart");
         MovieMetadataScrapeServiceImpl.TvScrapeContext context = this.collectTvContext(record, details, tvRoot);
         Map<Integer, TvSeasonDb> seasonCache = new HashMap<>();

         for (Integer season : context.seasonEpisodes().keySet().stream().sorted().toList()) {
            Path seasonDir = context.seasonDirs().getOrDefault(season, tvRoot.resolve("Season " + season));
            Files.createDirectories(seasonDir);
            TvSeasonDb seasonDb = this.getSeasonDb(record.getTmdbId().intValue(), season, seasonCache);
            if (seasonDb != null) {
               this.writeTextFile(seasonDir.resolve("season.nfo"), this.buildSeasonNfo(seasonDb, season));
               this.writeSeasonPoster(tvRoot, seasonDb, season);
            } else {
               this.writeTextFile(seasonDir.resolve("season.nfo"), this.buildSeasonNfo(null, season));
            }
         }

         for (MovieMetadataScrapeServiceImpl.EpisodeFile episodeFile : context.episodeFiles()) {
            TvSeasonDb seasonDb = this.getSeasonDb(record.getTmdbId().intValue(), episodeFile.season(), seasonCache);
            TvSeasonEpisode episode = this.findEpisode(seasonDb, episodeFile.episode());
            this.writeTextFile(
               this.toSiblingNfo(episodeFile.videoPath()), this.buildEpisodeNfo(episode, record.getTmdbId(), episodeFile.season(), episodeFile.episode())
            );
            if (episode != null) {
               this.downloadEpisodeImage(episode.getStillPath(), episodeFile.videoPath());
            }
         }
      } catch (Exception var11) {
         log.warn("剧集刮削失败 id={} tmdbId={}: {}", record.getId(), record.getTmdbId(), var11.getMessage());
      }
   }

   private List<MovieMetadataScrapeServiceImpl.RecordFileInfo> listRecordFiles(MovieDownloadRecordEntity record) {
      if (record == null) {
         return List.of();
      } else {
         String linkPath = record.getLinkFilePath();
         String episodeCodes = record.getEpisodeCodes();
         return !StringUtils.hasText(linkPath) && !StringUtils.hasText(episodeCodes)
            ? List.of()
            : List.of(new MovieMetadataScrapeServiceImpl.RecordFileInfo(linkPath, episodeCodes));
      }
   }

   private List<Path> collectMovieVideoFiles(MovieDownloadRecordEntity record, List<MovieMetadataScrapeServiceImpl.RecordFileInfo> details) {
      Set<Path> files = new LinkedHashSet<>();

      for (MovieMetadataScrapeServiceImpl.RecordFileInfo detail : details) {
         Path path = this.toPath(detail.linkFilePath());
         if (path != null && Files.isRegularFile(path) && this.isVideoFile(path)) {
            files.add(path);
         }
      }

      if (!files.isEmpty()) {
         return files.stream().sorted(Comparator.comparing(Path::toString)).toList();
      } else {
         Path linkPath = this.toPath(record.getLinkFilePath());
         if (linkPath == null) {
            return List.of();
         } else if (Files.isRegularFile(linkPath) && this.isVideoFile(linkPath)) {
            return List.of(linkPath);
         } else {
            return Files.isDirectory(linkPath) ? this.walkVideoFiles(linkPath) : List.of();
         }
      }
   }

   private MovieMetadataScrapeServiceImpl.TvScrapeContext collectTvContext(
      MovieDownloadRecordEntity record, List<MovieMetadataScrapeServiceImpl.RecordFileInfo> details, Path tvRoot
   ) {
      Map<Integer, Set<Integer>> seasonEpisodes = new LinkedHashMap<>();
      Map<Integer, Path> seasonDirs = new LinkedHashMap<>();
      List<MovieMetadataScrapeServiceImpl.EpisodeFile> episodeFiles = new ArrayList<>();
      Set<Path> touchedFiles = new LinkedHashSet<>();

      for (MovieMetadataScrapeServiceImpl.RecordFileInfo detail : details) {
         Path filePath = this.toPath(detail.linkFilePath());
         List<MovieMetadataScrapeServiceImpl.EpisodeRef> refs = this.parseEpisodeRefs(detail.episodeCodes());
         if (refs.isEmpty()) {
            MovieMetadataScrapeServiceImpl.EpisodeRef fromPath = this.parseEpisodeFromPath(filePath);
            if (fromPath != null) {
               refs = List.of(fromPath);
            }
         }

         if (!refs.isEmpty()) {
            for (MovieMetadataScrapeServiceImpl.EpisodeRef ref : refs) {
               seasonEpisodes.computeIfAbsent(ref.season(), key -> new LinkedHashSet<>()).add(ref.episode());
               if (filePath != null) {
                  seasonDirs.putIfAbsent(ref.season(), this.resolveSeasonDirFromFile(tvRoot, filePath, ref.season()));
               }
            }

            if (filePath != null && Files.isRegularFile(filePath) && this.isVideoFile(filePath) && touchedFiles.add(filePath)) {
               MovieMetadataScrapeServiceImpl.EpisodeRef primaryRef = refs.get(0);
               episodeFiles.add(new MovieMetadataScrapeServiceImpl.EpisodeFile(filePath, primaryRef.season(), primaryRef.episode()));
            }
         }
      }

      if (!episodeFiles.isEmpty()) {
         return new MovieMetadataScrapeServiceImpl.TvScrapeContext(seasonEpisodes, seasonDirs, episodeFiles);
      } else {
         Path linkPath = this.toPath(record.getLinkFilePath());
         if (linkPath == null) {
            return new MovieMetadataScrapeServiceImpl.TvScrapeContext(seasonEpisodes, seasonDirs, episodeFiles);
         } else {
            List<Path> candidates;
            if (Files.isDirectory(linkPath)) {
               candidates = this.walkVideoFiles(linkPath);
            } else if (Files.isRegularFile(linkPath) && this.isVideoFile(linkPath)) {
               candidates = List.of(linkPath);
            } else {
               candidates = List.of();
            }

            for (Path candidate : candidates) {
               MovieMetadataScrapeServiceImpl.EpisodeRef refx = this.parseEpisodeFromPath(candidate);
               if (refx != null) {
                  seasonEpisodes.computeIfAbsent(refx.season(), key -> new LinkedHashSet<>()).add(refx.episode());
                  seasonDirs.putIfAbsent(refx.season(), this.resolveSeasonDirFromFile(tvRoot, candidate, refx.season()));
                  if (touchedFiles.add(candidate)) {
                     episodeFiles.add(new MovieMetadataScrapeServiceImpl.EpisodeFile(candidate, refx.season(), refx.episode()));
                  }
               }
            }

            return new MovieMetadataScrapeServiceImpl.TvScrapeContext(seasonEpisodes, seasonDirs, episodeFiles);
         }
      }
   }

   private TvSeasonDb getSeasonDb(int tvTmdbId, int season, Map<Integer, TvSeasonDb> cache) {
      if (cache.containsKey(season)) {
         return cache.get(season);
      } else {
         TvSeasonDb seasonDb = null;

         try {
            seasonDb = this.tmdbApi.getTvSeasons().getDetails(tvTmdbId, season, "zh-CN", TvSeasonsAppendToResponse.values());
         } catch (Exception var6) {
            log.warn("获取季度信息失败 tmdbId={} season={}: {}", tvTmdbId, season, var6.getMessage());
         }

         cache.put(season, seasonDb);
         return seasonDb;
      }
   }

   private TvSeasonEpisode findEpisode(TvSeasonDb seasonDb, int episodeNumber) {
      if (seasonDb != null && seasonDb.getEpisodes() != null) {
         for (TvSeasonEpisode episode : seasonDb.getEpisodes()) {
            if (episode != null && episode.getEpisodeNumber() != null && episode.getEpisodeNumber() == episodeNumber) {
               return episode;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private Path resolveMovieRoot(MovieDownloadRecordEntity record, List<MovieMetadataScrapeServiceImpl.RecordFileInfo> details) {
      Path recordPath = this.toPath(record.getLinkFilePath());
      Path byRecord = this.resolveFileOrDirRoot(recordPath);
      if (byRecord != null) {
         return byRecord;
      } else {
         for (MovieMetadataScrapeServiceImpl.RecordFileInfo detail : details) {
            Path path = this.resolveFileOrDirRoot(this.toPath(detail.linkFilePath()));
            if (path != null) {
               return path;
            }
         }

         return null;
      }
   }

   private Path resolveTvRoot(MovieDownloadRecordEntity record, List<MovieMetadataScrapeServiceImpl.RecordFileInfo> details) {
      Path path = this.toPath(record.getLinkFilePath());
      Path byRecord = this.resolveTvRootFromPath(path);
      if (byRecord != null) {
         return byRecord;
      } else {
         for (MovieMetadataScrapeServiceImpl.RecordFileInfo detail : details) {
            Path byDetail = this.resolveTvRootFromPath(this.toPath(detail.linkFilePath()));
            if (byDetail != null) {
               return byDetail;
            }
         }

         return null;
      }
   }

   private Path resolveTvRootFromPath(Path path) {
      if (path == null) {
         return null;
      } else if (Files.isDirectory(path)) {
         return this.isSeasonFolder(path.getFileName() == null ? "" : path.getFileName().toString()) && path.getParent() != null ? path.getParent() : path;
      } else if (Files.isRegularFile(path)) {
         return this.resolveShowRootFromFile(path);
      } else {
         String filename = path.getFileName() == null ? "" : path.getFileName().toString();
         if (this.isVideoFilename(filename)) {
            return this.resolveShowRootFromFile(path);
         } else {
            return this.isSeasonFolder(filename) && path.getParent() != null ? path.getParent() : path;
         }
      }
   }

   private Path resolveFileOrDirRoot(Path path) {
      if (path == null) {
         return null;
      } else if (Files.isDirectory(path)) {
         return path;
      } else if (Files.isRegularFile(path)) {
         return path.getParent();
      } else {
         String filename = path.getFileName() == null ? "" : path.getFileName().toString();
         return this.isVideoFilename(filename) ? path.getParent() : path;
      }
   }

   private Path resolveShowRootFromFile(Path filePath) {
      if (filePath == null) {
         return null;
      } else {
         Path parent = filePath.getParent();
         if (parent == null) {
            return null;
         } else {
            return this.isSeasonFolder(parent.getFileName() == null ? "" : parent.getFileName().toString()) && parent.getParent() != null
               ? parent.getParent()
               : parent;
         }
      }
   }

   private Path resolveSeasonDirFromFile(Path tvRoot, Path filePath, int season) {
      if (filePath != null) {
         Path parent = filePath.getParent();
         if (parent != null && this.isSeasonFolder(parent.getFileName() == null ? "" : parent.getFileName().toString())) {
            return parent;
         }
      }

      return tvRoot.resolve("Season " + season);
   }

   private List<MovieMetadataScrapeServiceImpl.EpisodeRef> parseEpisodeRefs(String episodeCodes) {
      if (!StringUtils.hasText(episodeCodes)) {
         return List.of();
      } else {
         List<MovieMetadataScrapeServiceImpl.EpisodeRef> refs = new ArrayList<>();
         String[] tokens = episodeCodes.split(",");

         for (String token : tokens) {
            if (StringUtils.hasText(token)) {
               Matcher matcher = EPISODE_CODE_PATTERN.matcher(token.trim());
               if (matcher.find()) {
                  int season = Integer.parseInt(matcher.group(1));
                  int episode = Integer.parseInt(matcher.group(2));
                  refs.add(new MovieMetadataScrapeServiceImpl.EpisodeRef(season, episode));
               }
            }
         }

         return refs;
      }
   }

   private MovieMetadataScrapeServiceImpl.EpisodeRef parseEpisodeFromPath(Path path) {
      if (path == null) {
         return null;
      } else {
         MovieEpisodeParser.EpisodeMeta meta = MovieEpisodeParser.parseFromPath(path);
         if (meta != null && meta.getBeginEpisode() != null) {
            int season = meta.getBeginSeason() != null ? meta.getBeginSeason() : 1;
            return new MovieMetadataScrapeServiceImpl.EpisodeRef(season, meta.getBeginEpisode());
         } else {
            return null;
         }
      }
   }

   private List<Path> walkVideoFiles(Path root) {
      if (root != null && Files.isDirectory(root)) {
         try {
            List var3;
            try (Stream<Path> stream = Files.walk(root)) {
               var3 = stream.filter(x$0 -> Files.isRegularFile(x$0)).filter(this::isVideoFile).sorted(Comparator.comparing(Path::toString)).toList();
            }

            return var3;
         } catch (IOException var7) {
            return List.of();
         }
      } else {
         return List.of();
      }
   }

   private boolean isVideoFile(Path path) {
      return path != null && path.getFileName() != null ? this.isVideoFilename(path.getFileName().toString()) : false;
   }

   private boolean isVideoFilename(String filename) {
      if (!StringUtils.hasText(filename)) {
         return false;
      } else {
         String lower = filename.toLowerCase(Locale.ROOT);

         for (String ext : VIDEO_EXTS) {
            if (lower.endsWith(ext)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean isSeasonFolder(String name) {
      if (!StringUtils.hasText(name)) {
         return false;
      } else if (SEASON_FOLDER_PATTERN.matcher(name.trim()).matches()) {
         return true;
      } else {
         String normalized = name.trim().toLowerCase(Locale.ROOT);
         return "specials".equals(normalized) || "sp".equals(normalized) || "sps".equals(normalized);
      }
   }

   private Path toPath(String value) {
      if (!StringUtils.hasText(value)) {
         return null;
      } else {
         try {
            return Paths.get(value).normalize();
         } catch (Exception var3) {
            return null;
         }
      }
   }

   private Path toSiblingNfo(Path videoFile) {
      String filename = videoFile.getFileName().toString();
      int dot = filename.lastIndexOf(46);
      String base = dot > 0 ? filename.substring(0, dot) : filename;
      return videoFile.resolveSibling(base + ".nfo");
   }

   private void downloadNamedImage(String imagePath, Path targetDir, String targetBaseName) {
      if (StringUtils.hasText(imagePath) && targetDir != null && StringUtils.hasText(targetBaseName)) {
         String ext = this.resolveImageExtension(imagePath);
         Path targetFile = targetDir.resolve(targetBaseName + ext);
         this.downloadImage(imagePath, targetFile);
      }
   }

   private void writeSeasonPoster(Path tvRoot, TvSeasonDb seasonDb, int season) {
      if (tvRoot != null && seasonDb != null && StringUtils.hasText(seasonDb.getPosterPath())) {
         String ext = this.resolveImageExtension(seasonDb.getPosterPath());
         String filename = season == 0 ? "season-specials-poster" + ext : String.format(Locale.ROOT, "season%02d-poster%s", season, ext);
         this.downloadImage(seasonDb.getPosterPath(), tvRoot.resolve(filename));
      }
   }

   private void downloadEpisodeImage(String stillPath, Path videoFile) {
      if (StringUtils.hasText(stillPath) && videoFile != null) {
         String filename = videoFile.getFileName().toString();
         int dot = filename.lastIndexOf(46);
         String base = dot > 0 ? filename.substring(0, dot) : filename;
         Path target = videoFile.resolveSibling(base + this.resolveImageExtension(stillPath));
         this.downloadImage(stillPath, target);
      }
   }

   private void downloadImage(String imagePath, Path targetFile) {
      if (StringUtils.hasText(imagePath) && targetFile != null) {
         String url = this.buildImageUrl(imagePath);
         if (StringUtils.hasText(url)) {
            try {
               try (HttpResponse response = HttpUtil.createGet(url).timeout(30000).execute()) {
                  if (response.getStatus() >= 200 && response.getStatus() < 300) {
                     byte[] bytes = response.bodyBytes();
                     if (bytes != null && bytes.length != 0) {
                        Files.createDirectories(targetFile.getParent());
                        Files.write(targetFile, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                        return;
                     }

                     return;
                  }

                  log.debug("下载图片失败 status={} url={}", response.getStatus(), url);
               }
            } catch (Exception var9) {
               log.debug("下载图片失败 {} -> {}: {}", url, targetFile, var9.getMessage());
            }
         }
      }
   }

   private String buildImageUrl(String imagePath) {
      if (!StringUtils.hasText(imagePath)) {
         return "";
      } else if (!imagePath.startsWith("http://") && !imagePath.startsWith("https://")) {
         String base = StringUtils.hasText(this.imageBaseUrl) ? this.imageBaseUrl.trim() : "https://image.tmdb.org/t/p/original";
         if (base.endsWith("/") && imagePath.startsWith("/")) {
            return base.substring(0, base.length() - 1) + imagePath;
         } else {
            return !base.endsWith("/") && !imagePath.startsWith("/") ? base + "/" + imagePath : base + imagePath;
         }
      } else {
         return imagePath;
      }
   }

   private String resolveImageExtension(String imagePath) {
      if (!StringUtils.hasText(imagePath)) {
         return ".jpg";
      } else {
         String normalized = imagePath;
         int queryIndex = imagePath.indexOf(63);
         if (queryIndex >= 0) {
            normalized = imagePath.substring(0, queryIndex);
         }

         int dot = normalized.lastIndexOf(46);
         if (dot >= 0 && dot < normalized.length() - 1) {
            String ext = normalized.substring(dot).toLowerCase(Locale.ROOT);
            return ext.length() <= 8 && !ext.contains("/") && !ext.contains("\\") ? ext : ".jpg";
         } else {
            return ".jpg";
         }
      }
   }

   private void writeTextFile(Path path, String content) throws IOException {
      if (path != null && content != null) {
         if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
         }

         Files.writeString(path, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      }
   }

   private String buildMovieNfo(MovieDb movie, Long tmdbId) {
      StringBuilder sb = new StringBuilder();
      this.appendXmlHeader(sb);
      sb.append("<movie>\n");
      this.appendNode(sb, 1, "title", this.firstText(movie.getTitle(), movie.getOriginalTitle()));
      this.appendNode(sb, 1, "originaltitle", movie.getOriginalTitle());
      this.appendNode(sb, 1, "premiered", movie.getReleaseDate());
      this.appendNode(sb, 1, "year", this.extractYear(movie.getReleaseDate()));
      this.appendCdataNode(sb, 1, "plot", movie.getOverview());
      this.appendCdataNode(sb, 1, "outline", movie.getOverview());
      this.appendNode(sb, 1, "rating", this.formatRating(movie.getVoteAverage()));
      this.appendNode(sb, 1, "tmdbid", String.valueOf(tmdbId));
      this.appendUniqueIdNode(sb, String.valueOf(tmdbId), "tmdb", true);
      this.appendGenres(sb, movie.getGenres());
      sb.append("</movie>\n");
      return sb.toString();
   }

   private String buildTvShowNfo(TvSeriesDb tv, Long tmdbId) {
      StringBuilder sb = new StringBuilder();
      this.appendXmlHeader(sb);
      sb.append("<tvshow>\n");
      this.appendNode(sb, 1, "title", this.firstText(tv.getName(), tv.getOriginalName()));
      this.appendNode(sb, 1, "originaltitle", tv.getOriginalName());
      this.appendNode(sb, 1, "premiered", tv.getFirstAirDate());
      this.appendNode(sb, 1, "year", this.extractYear(tv.getFirstAirDate()));
      this.appendCdataNode(sb, 1, "plot", tv.getOverview());
      this.appendCdataNode(sb, 1, "outline", tv.getOverview());
      this.appendNode(sb, 1, "rating", this.formatRating(tv.getVoteAverage()));
      this.appendNode(sb, 1, "tmdbid", String.valueOf(tmdbId));
      this.appendUniqueIdNode(sb, String.valueOf(tmdbId), "tmdb", true);
      this.appendNode(sb, 1, "season", "-1");
      this.appendNode(sb, 1, "episode", "-1");
      this.appendGenres(sb, tv.getGenres());
      sb.append("</tvshow>\n");
      return sb.toString();
   }

   private String buildSeasonNfo(TvSeasonDb seasonDb, int season) {
      StringBuilder sb = new StringBuilder();
      this.appendXmlHeader(sb);
      sb.append("<season>\n");
      this.appendNode(sb, 1, "title", seasonDb != null ? this.firstText(seasonDb.getName(), "季 " + season) : "季 " + season);
      this.appendCdataNode(sb, 1, "plot", seasonDb != null ? seasonDb.getOverview() : "");
      this.appendCdataNode(sb, 1, "outline", seasonDb != null ? seasonDb.getOverview() : "");
      this.appendNode(sb, 1, "premiered", seasonDb != null ? seasonDb.getAirDate() : "");
      this.appendNode(sb, 1, "releasedate", seasonDb != null ? seasonDb.getAirDate() : "");
      this.appendNode(sb, 1, "year", seasonDb != null ? this.extractYear(seasonDb.getAirDate()) : "");
      this.appendNode(sb, 1, "seasonnumber", String.valueOf(season));
      sb.append("</season>\n");
      return sb.toString();
   }

   private String buildEpisodeNfo(TvSeasonEpisode episode, Long seriesTmdbId, int season, int episodeNumber) {
      StringBuilder sb = new StringBuilder();
      this.appendXmlHeader(sb);
      sb.append("<episodedetails>\n");
      this.appendNode(sb, 1, "title", episode != null ? this.firstText(episode.getName(), "第 " + episodeNumber + " 集") : "第 " + episodeNumber + " 集");
      this.appendCdataNode(sb, 1, "plot", episode != null ? episode.getOverview() : "");
      this.appendCdataNode(sb, 1, "outline", episode != null ? episode.getOverview() : "");
      this.appendNode(sb, 1, "aired", episode != null ? episode.getAirDate() : "");
      this.appendNode(sb, 1, "year", episode != null ? this.extractYear(episode.getAirDate()) : "");
      this.appendNode(sb, 1, "season", String.valueOf(season));
      this.appendNode(sb, 1, "episode", String.valueOf(episodeNumber));
      this.appendNode(sb, 1, "rating", episode != null ? this.formatRating(episode.getVoteAverage()) : "0");
      this.appendNode(sb, 1, "tmdbid", String.valueOf(seriesTmdbId));
      sb.append("</episodedetails>\n");
      return sb.toString();
   }

   private void appendXmlHeader(StringBuilder sb) {
      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
   }

   private void appendNode(StringBuilder sb, int indentLevel, String tag, String value) {
      if (tag != null) {
         this.indent(sb, indentLevel);
         sb.append('<').append(tag).append('>').append(this.escapeXml(value)).append("</").append(tag).append(">\n");
      }
   }

   private void appendCdataNode(StringBuilder sb, int indentLevel, String tag, String value) {
      if (tag != null) {
         this.indent(sb, indentLevel);
         sb.append('<').append(tag).append('>').append(this.toCdata(value)).append("</").append(tag).append(">\n");
      }
   }

   private void appendUniqueIdNode(StringBuilder sb, String value, String type, boolean isDefault) {
      this.indent(sb, 1);
      sb.append("<uniqueid type=\"")
         .append(this.escapeXml(type))
         .append("\" default=\"")
         .append(isDefault ? "true" : "false")
         .append("\">")
         .append(this.escapeXml(value))
         .append("</uniqueid>\n");
   }

   private void appendGenres(StringBuilder sb, List<Genre> genres) {
      if (genres != null && !genres.isEmpty()) {
         for (Genre genre : genres) {
            if (genre != null && StringUtils.hasText(genre.getName())) {
               this.appendNode(sb, 1, "genre", genre.getName());
            }
         }
      }
   }

   private void indent(StringBuilder sb, int level) {
      for (int i = 0; i < level; i++) {
         sb.append("  ");
      }
   }

   private String escapeXml(String value) {
      return StringEscapeUtils.escapeXml11(value == null ? "" : value);
   }

   private String toCdata(String value) {
      String content = value == null ? "" : value;
      content = content.replace("]]>", "]]]]><![CDATA[>");
      return "<![CDATA[" + content + "]]>";
   }

   private String firstText(String first, String fallback) {
      return StringUtils.hasText(first) ? first : (fallback == null ? "" : fallback);
   }

   private String extractYear(String dateValue) {
      return StringUtils.hasText(dateValue) && dateValue.length() >= 4 ? dateValue.substring(0, 4) : "";
   }

   private String formatRating(Double rating) {
      return rating == null ? "0" : String.format(Locale.US, "%.1f", rating);
   }

   @Generated
   public MovieMetadataScrapeServiceImpl(final TmdbApi tmdbApi) {
      this.tmdbApi = tmdbApi;
   }

   private static record EpisodeFile(Path videoPath, int season, int episode) {
   }

   private static record EpisodeRef(int season, int episode) {
   }

   private static record RecordFileInfo(String linkFilePath, String episodeCodes) {
   }

   private static record TvScrapeContext(
      Map<Integer, Set<Integer>> seasonEpisodes, Map<Integer, Path> seasonDirs, List<MovieMetadataScrapeServiceImpl.EpisodeFile> episodeFiles
   ) {
   }
}
