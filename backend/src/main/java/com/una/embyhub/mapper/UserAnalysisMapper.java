package com.una.embyhub.mapper;

import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisDimensionResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisTimelineResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisUserOptionResponse;
import com.una.embyhub.model.dto.response.useranalysis.UserAnalysisUserResponse;
import java.time.LocalDate;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserAnalysisMapper {
   String RECORD_USER_MATCH = "AND pr.emby_info_id = #{embyInfoId}\nAND (\n    (#{embyUserId} IS NOT NULL AND #{embyUserId} != '' AND pr.user_id = #{embyUserId})\n    OR pr.user_name = #{embyUserName}\n)\n";
   String RECORD_DATE_RANGE = "AND pr.play_day >= #{startDate}\nAND pr.play_day <= #{endDate}\n";

   @Select({"SELECT\n    eu.id AS userId,\n    eu.emby_user_id AS embyUserId,\n    eu.emby_user_name AS embyUserName,\n    eu.emby_info_id AS embyInfoId,\n    ei.server_name AS serverName,\n    eu.avatar AS avatar,\n    eu.user_status AS userStatus,\n    eu.create_datetime AS registerDatetime,\n    MAX(pr.play_date) AS lastPlayDatetime,\n    COALESCE(COUNT(pr.id), 0) AS totalPlayCount,\n    COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds,\n    COALESCE(COUNT(DISTINCT pr.play_day), 0) AS activeDayCount\nFROM emby_user eu\nLEFT JOIN emby_info ei ON ei.id = eu.emby_info_id AND ei.del_flag = 0\nLEFT JOIN playback_reporting_record pr ON pr.del_flag = 0\n    AND pr.emby_info_id = eu.emby_info_id\n    AND pr.play_day >= #{startDate}\n    AND pr.play_day <= #{endDate}\n    AND (\n        (eu.emby_user_id IS NOT NULL AND eu.emby_user_id != '' AND pr.user_id = eu.emby_user_id)\n        OR pr.user_name = eu.emby_user_name\n    )\nWHERE eu.del_flag = 0\n  AND ei.enabled = 1\n  AND ei.`status` = 0\n  AND (#{embyInfoId} IS NULL OR eu.emby_info_id = #{embyInfoId})\nGROUP BY eu.id, eu.emby_user_id, eu.emby_user_name, eu.emby_info_id, ei.server_name,\n         eu.avatar, eu.user_status, eu.create_datetime\nORDER BY totalPlayCount DESC, lastPlayDatetime DESC, eu.create_datetime DESC\nLIMIT 10\n"})
   List<UserAnalysisUserResponse> selectTopActiveUsers(
      @Param("embyInfoId") Long embyInfoId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate
   );

   @Select({"SELECT\n    eu.id AS userId,\n    eu.emby_user_id AS embyUserId,\n    eu.emby_user_name AS embyUserName,\n    eu.emby_info_id AS embyInfoId,\n    ei.server_name AS serverName,\n    eu.avatar AS avatar,\n    eu.user_status AS userStatus,\n    eu.create_datetime AS registerDatetime,\n    MAX(pr.play_date) AS lastPlayDatetime,\n    COALESCE(COUNT(pr.id), 0) AS totalPlayCount,\n    COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds,\n    COALESCE(COUNT(DISTINCT pr.play_day), 0) AS activeDayCount\nFROM emby_user eu\nLEFT JOIN emby_info ei ON ei.id = eu.emby_info_id AND ei.del_flag = 0\nLEFT JOIN playback_reporting_record pr ON pr.del_flag = 0\n    AND pr.emby_info_id = eu.emby_info_id\n    AND pr.play_day >= #{startDate}\n    AND pr.play_day <= #{endDate}\n    AND (\n        (eu.emby_user_id IS NOT NULL AND eu.emby_user_id != '' AND pr.user_id = eu.emby_user_id)\n        OR pr.user_name = eu.emby_user_name\n    )\nWHERE eu.del_flag = 0\n  AND eu.id = #{userId}\n  AND ei.enabled = 1\n  AND ei.`status` = 0\n  AND (#{embyInfoId} IS NULL OR eu.emby_info_id = #{embyInfoId})\nGROUP BY eu.id, eu.emby_user_id, eu.emby_user_name, eu.emby_info_id, ei.server_name,\n         eu.avatar, eu.user_status, eu.create_datetime\nLIMIT 1\n"})
   UserAnalysisUserResponse selectUserSummary(
      @Param("userId") Long userId, @Param("embyInfoId") Long embyInfoId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate
   );

   @Select({"SELECT\n    MAX(eu.id) AS userId,\n    stats.embyUserId AS embyUserId,\n    stats.embyUserName AS embyUserName,\n    stats.embyInfoId AS embyInfoId,\n    ei.server_name AS serverName,\n    MAX(eu.avatar) AS avatar,\n    COALESCE(MAX(eu.user_status), 0) AS userStatus,\n    MAX(eu.create_datetime) AS registerDatetime,\n    stats.lastPlayDatetime AS lastPlayDatetime,\n    stats.totalPlayCount AS totalPlayCount,\n    stats.totalDurationSeconds AS totalDurationSeconds,\n    stats.activeDayCount AS activeDayCount\nFROM (\n    SELECT\n        pr.emby_info_id AS embyInfoId,\n        MAX(NULLIF(pr.user_id, '')) AS embyUserId,\n        COALESCE(MAX(NULLIF(pr.user_name, '')), MAX(NULLIF(pr.nick_name, '')), MAX(NULLIF(pr.user_id, ''))) AS embyUserName,\n        MAX(pr.play_date) AS lastPlayDatetime,\n        COUNT(1) AS totalPlayCount,\n        COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds,\n        COUNT(DISTINCT pr.play_day) AS activeDayCount\n    FROM playback_reporting_record pr\n    WHERE pr.del_flag = 0\n      AND pr.play_day >= #{startDate}\n      AND pr.play_day <= #{endDate}\n      AND (#{embyInfoId} IS NULL OR pr.emby_info_id = #{embyInfoId})\n    GROUP BY pr.emby_info_id,\n             COALESCE(NULLIF(pr.user_id, ''), NULLIF(pr.user_name, ''), NULLIF(pr.nick_name, ''), '未知用户')\n) stats\nLEFT JOIN emby_info ei ON ei.id = stats.embyInfoId AND ei.del_flag = 0\nLEFT JOIN emby_user eu ON eu.del_flag = 0\n    AND eu.emby_info_id = stats.embyInfoId\n    AND (\n        (stats.embyUserId IS NOT NULL AND stats.embyUserId != '' AND eu.emby_user_id = stats.embyUserId)\n        OR eu.emby_user_name = stats.embyUserName\n    )\nWHERE ei.enabled = 1\n  AND ei.`status` = 0\nGROUP BY stats.embyInfoId, stats.embyUserId, stats.embyUserName, ei.server_name,\n         stats.lastPlayDatetime, stats.totalPlayCount, stats.totalDurationSeconds, stats.activeDayCount\n"})
   List<UserAnalysisUserResponse> selectPlaybackUserSummaries(
      @Param("embyInfoId") Long embyInfoId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate
   );

   @Select({"SELECT\n    MAX(eu.id) AS userId,\n    COALESCE(MAX(NULLIF(eu.emby_user_id, '')), #{embyUserId}) AS embyUserId,\n    COALESCE(MAX(NULLIF(eu.emby_user_name, '')), #{embyUserName}) AS embyUserName,\n    ei.id AS embyInfoId,\n    ei.server_name AS serverName,\n    MAX(eu.avatar) AS avatar,\n    COALESCE(MAX(eu.user_status), 0) AS userStatus,\n    MAX(eu.create_datetime) AS registerDatetime,\n    MAX(pr.play_date) AS lastPlayDatetime,\n    COALESCE(COUNT(pr.id), 0) AS totalPlayCount,\n    COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds,\n    COALESCE(COUNT(DISTINCT pr.play_day), 0) AS activeDayCount\nFROM emby_info ei\nLEFT JOIN emby_user eu ON eu.del_flag = 0\n    AND eu.emby_info_id = ei.id\n    AND (\n        (#{embyUserId} IS NOT NULL AND #{embyUserId} != '' AND eu.emby_user_id = #{embyUserId})\n        OR eu.emby_user_name = #{embyUserName}\n    )\nLEFT JOIN playback_reporting_record pr ON pr.del_flag = 0\nAND pr.play_day >= #{startDate}\nAND pr.play_day <= #{endDate}\nAND pr.emby_info_id = #{embyInfoId}\nAND (\n    (#{embyUserId} IS NOT NULL AND #{embyUserId} != '' AND pr.user_id = #{embyUserId})\n    OR pr.user_name = #{embyUserName}\n)\nWHERE ei.del_flag = 0\n  AND ei.enabled = 1\n  AND ei.`status` = 0\n  AND ei.id = #{embyInfoId}\nGROUP BY ei.id, ei.server_name\nLIMIT 1\n"})
   UserAnalysisUserResponse selectRemoteUserSummary(
      @Param("embyInfoId") Long embyInfoId,
      @Param("embyUserId") String embyUserId,
      @Param("embyUserName") String embyUserName,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate
   );

   @Select({"SELECT\n    eu.id AS userId,\n    eu.emby_info_id AS embyInfoId,\n    eu.emby_user_id AS embyUserId,\n    eu.emby_user_name AS embyUserName,\n    ei.server_name AS serverName,\n    eu.user_status AS userStatus\nFROM emby_user eu\nLEFT JOIN emby_info ei ON ei.id = eu.emby_info_id AND ei.del_flag = 0\nWHERE eu.del_flag = 0\n  AND ei.enabled = 1\n  AND ei.`status` = 0\n  AND (#{embyInfoId} IS NULL OR eu.emby_info_id = #{embyInfoId})\nORDER BY eu.emby_user_name ASC, ei.server_name ASC\n"})
   List<UserAnalysisUserOptionResponse> selectActiveUserOptions(@Param("embyInfoId") Long embyInfoId);

   @Select({"SELECT\n    COALESCE(NULLIF(pr.location, ''), '未知地点') AS label,\n    COUNT(1) AS count,\n    COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds\nFROM playback_reporting_record pr\nWHERE pr.del_flag = 0\nAND pr.play_day >= #{startDate}\nAND pr.play_day <= #{endDate}\nAND pr.emby_info_id = #{embyInfoId}\nAND (\n    (#{embyUserId} IS NOT NULL AND #{embyUserId} != '' AND pr.user_id = #{embyUserId})\n    OR pr.user_name = #{embyUserName}\n)\nGROUP BY COALESCE(NULLIF(pr.location, ''), '未知地点')\nORDER BY count DESC, totalDurationSeconds DESC\nLIMIT #{limit}\n"})
   List<UserAnalysisDimensionResponse> selectLocationStats(
      @Param("embyInfoId") Long embyInfoId,
      @Param("embyUserId") String embyUserId,
      @Param("embyUserName") String embyUserName,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("limit") Integer limit
   );

   @Select({"SELECT\n    COALESCE(NULLIF(pr.client_name, ''), '未知播放器') AS label,\n    COUNT(1) AS count,\n    COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds\nFROM playback_reporting_record pr\nWHERE pr.del_flag = 0\nAND pr.play_day >= #{startDate}\nAND pr.play_day <= #{endDate}\nAND pr.emby_info_id = #{embyInfoId}\nAND (\n    (#{embyUserId} IS NOT NULL AND #{embyUserId} != '' AND pr.user_id = #{embyUserId})\n    OR pr.user_name = #{embyUserName}\n)\nGROUP BY COALESCE(NULLIF(pr.client_name, ''), '未知播放器')\nORDER BY count DESC, totalDurationSeconds DESC\nLIMIT #{limit}\n"})
   List<UserAnalysisDimensionResponse> selectPlayerStats(
      @Param("embyInfoId") Long embyInfoId,
      @Param("embyUserId") String embyUserId,
      @Param("embyUserName") String embyUserName,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("limit") Integer limit
   );

   @Select({"SELECT\n    COALESCE(NULLIF(pr.item_type, ''), '未知类型') AS label,\n    COUNT(1) AS count,\n    COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds\nFROM playback_reporting_record pr\nWHERE pr.del_flag = 0\nAND pr.play_day >= #{startDate}\nAND pr.play_day <= #{endDate}\nAND pr.emby_info_id = #{embyInfoId}\nAND (\n    (#{embyUserId} IS NOT NULL AND #{embyUserId} != '' AND pr.user_id = #{embyUserId})\n    OR pr.user_name = #{embyUserName}\n)\nGROUP BY COALESCE(NULLIF(pr.item_type, ''), '未知类型')\nORDER BY count DESC, totalDurationSeconds DESC\nLIMIT #{limit}\n"})
   List<UserAnalysisDimensionResponse> selectItemTypeStats(
      @Param("embyInfoId") Long embyInfoId,
      @Param("embyUserId") String embyUserId,
      @Param("embyUserName") String embyUserName,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("limit") Integer limit
   );

   @Select({"SELECT\n    period.label AS label,\n    period.count AS count,\n    period.totalDurationSeconds AS totalDurationSeconds\nFROM (\n    SELECT\n        CASE\n            WHEN HOUR(pr.play_date) BETWEEN 0 AND 5 THEN 0\n            WHEN HOUR(pr.play_date) BETWEEN 6 AND 11 THEN 1\n            WHEN HOUR(pr.play_date) BETWEEN 12 AND 17 THEN 2\n            ELSE 3\n        END AS periodSort,\n        CASE\n            WHEN HOUR(pr.play_date) BETWEEN 0 AND 5 THEN '凌晨'\n            WHEN HOUR(pr.play_date) BETWEEN 6 AND 11 THEN '上午'\n            WHEN HOUR(pr.play_date) BETWEEN 12 AND 17 THEN '下午'\n            ELSE '夜间'\n        END AS label,\n        COUNT(1) AS count,\n        COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds\n    FROM playback_reporting_record pr\n    WHERE pr.del_flag = 0\n      AND pr.play_date IS NOT NULL\nAND pr.play_day >= #{startDate}\nAND pr.play_day <= #{endDate}\nAND pr.emby_info_id = #{embyInfoId}\nAND (\n    (#{embyUserId} IS NOT NULL AND #{embyUserId} != '' AND pr.user_id = #{embyUserId})\n    OR pr.user_name = #{embyUserName}\n)\n    GROUP BY periodSort, label\n) period\nORDER BY period.periodSort ASC\n"})
   List<UserAnalysisDimensionResponse> selectTimePeriodStats(
      @Param("embyInfoId") Long embyInfoId,
      @Param("embyUserId") String embyUserId,
      @Param("embyUserName") String embyUserName,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate
   );

   @Select({"SELECT\n    DATE_FORMAT(timeline.playDate, '%m-%d') AS label,\n    timeline.count AS count,\n    timeline.totalDurationSeconds AS totalDurationSeconds\nFROM (\n    SELECT\n        pr.play_day AS playDate,\n        COUNT(1) AS count,\n        COALESCE(SUM(COALESCE(pr.duration, 0)), 0) AS totalDurationSeconds\n    FROM playback_reporting_record pr\n    WHERE pr.del_flag = 0\nAND pr.play_day >= #{startDate}\nAND pr.play_day <= #{endDate}\nAND pr.emby_info_id = #{embyInfoId}\nAND (\n    (#{embyUserId} IS NOT NULL AND #{embyUserId} != '' AND pr.user_id = #{embyUserId})\n    OR pr.user_name = #{embyUserName}\n)\n    GROUP BY pr.play_day\n    ORDER BY playDate DESC\n    LIMIT #{limit}\n) timeline\nORDER BY timeline.playDate DESC\n"})
   List<UserAnalysisTimelineResponse> selectPlayTimeline(
      @Param("embyInfoId") Long embyInfoId,
      @Param("embyUserId") String embyUserId,
      @Param("embyUserName") String embyUserName,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate,
      @Param("limit") Integer limit
   );
}
