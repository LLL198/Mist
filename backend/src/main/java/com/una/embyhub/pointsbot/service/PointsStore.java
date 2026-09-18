package com.una.embyhub.pointsbot.service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.una.embyhub.mapper.PointsBotLedgerMapper;
import com.una.embyhub.mapper.PointsBotUserMapper;
import com.una.embyhub.model.entity.PointsBotLedger;
import com.una.embyhub.model.entity.PointsBotLevelConfig;
import com.una.embyhub.model.entity.PointsBotUser;
import com.una.embyhub.pointsbot.model.PointsProfile;
import com.una.embyhub.service.PointsBotLevelConfigService;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
   rollbackFor = {Exception.class}
)
public class PointsStore {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(PointsStore.class);
   private static final String EMBYBOSS_FIXED_LEVEL_REMARK_PREFIX = "embyboss:fixed:";
   @Autowired
   private PointsBotUserMapper pointsBotUserMapper;
   @Autowired
   private PointsBotLedgerMapper pointsBotLedgerMapper;
   @Autowired
   private PointsBotLevelConfigService levelConfigService;

   public PointsProfile getOrCreate(long chatId, long userId, String username, String displayName) {
      String effectiveUsername = username != null && !username.isBlank() ? username : displayName;
      PointsBotUser user = new LambdaQueryChainWrapper<>(this.pointsBotUserMapper)
         .eq(PointsBotUser::getChatId, Long.valueOf(chatId))
         .eq(PointsBotUser::getUserId, Long.valueOf(userId))
         .one();
      if (user == null) {
         user = new PointsBotUser();
         user.setChatId(chatId);
         user.setUserId(userId);
         user.setUsername(effectiveUsername);
         user.setDisplayName(displayName);
         user.setPoints(0L);
         user.setCheckinStreak(0);
         user.setDailyMessagePoints(0);
         user.setDailyMessageCount(0);
         this.applyLevel(user);
         this.pointsBotUserMapper.insert(user);
      } else {
         boolean changed = false;
         if (effectiveUsername != null && !effectiveUsername.isBlank() && !effectiveUsername.equals(user.getUsername())) {
            user.setUsername(effectiveUsername);
            changed = true;
         }

         if (displayName != null && !displayName.isBlank() && !displayName.equals(user.getDisplayName())) {
            user.setDisplayName(displayName);
            changed = true;
         }

         if (changed) {
            this.pointsBotUserMapper.updateById(user);
         }
      }

      PointsProfile profile = this.toProfile(user);
      Long beforeLevelId = profile.getLevelId();
      this.updateLevel(profile);
      if (beforeLevelId == null ? profile.getLevelId() != null : !beforeLevelId.equals(profile.getLevelId())) {
         this.pointsBotUserMapper.updateById(this.toEntity(profile));
      }

      return profile;
   }

   public int addPoints(PointsProfile profile, int delta, String reason, String refId) {
      return this.addPoints(profile, delta, reason, refId, null);
   }

   public int addPoints(PointsProfile profile, int delta, String reason, String refId, Long serverId) {
      PointsBotUser locked = this.pointsBotUserMapper.selectForUpdate(profile.getChatId(), profile.getUserId());
      if (locked == null) {
         throw new IllegalStateException("积分用户不存在");
      } else {
         long before = locked.getPoints() == null ? 0L : locked.getPoints();

         long after;
         try {
            after = Math.max(0L, Math.addExact(before, (long)delta));
         } catch (ArithmeticException var15) {
            throw new IllegalArgumentException("积分变动超出允许范围", var15);
         }

         long appliedLong = after - before;
         if (appliedLong <= 2147483647L && appliedLong >= -2147483648L) {
            int applied = (int)appliedLong;
            profile.setId(locked.getId());
            profile.setPoints(after);
            this.updateLevel(profile);
            PointsBotUser user = this.toEntity(profile);
            this.pointsBotUserMapper.updateById(user);
            if (applied != 0) {
               this.insertLedger(profile.getChatId(), profile.getUserId(), applied, reason, refId, serverId);
            }

            return applied;
         } else {
            throw new IllegalArgumentException("单次积分变动超出允许范围");
         }
      }
   }

   public void updateDailyMessageStats(PointsProfile profile, LocalDate today) {
      PointsBotUser locked = this.pointsBotUserMapper.selectForUpdate(profile.getChatId(), profile.getUserId());
      if (locked == null) {
         throw new IllegalStateException("积分用户不存在");
      } else {
         if (profile.getLastMessageDate() == null || !today.equals(profile.getLastMessageDate())) {
            profile.setLastMessageDate(today);
            profile.setDailyMessagePoints(0);
            profile.setDailyMessageCount(0);
         }

         profile.setId(locked.getId());
         profile.setPoints(locked.getPoints() == null ? 0L : locked.getPoints());
         profile.setLevelId(locked.getLevelId());
         profile.setLevelName(locked.getLevelName());
         this.pointsBotUserMapper.updateById(this.toEntity(profile));
      }
   }

   public void updateLevel(PointsProfile profile) {
      if (!this.hasFixedImportedLevel(profile)) {
         PointsBotLevelConfig level = this.levelConfigService.findLevelForPoints(profile.getPoints());
         if (level != null) {
            profile.setLevelId(level.getId());
            profile.setLevelName(level.getLevelName());
         } else {
            profile.setLevelId(null);
            profile.setLevelName(null);
         }
      }
   }

   public List<PointsProfile> leaderboard(long chatId, int limit) {
      return ChainWrappers.lambdaQueryChain(this.pointsBotUserMapper)
         .eq(PointsBotUser::getChatId, Long.valueOf(chatId))
         .orderByDesc(PointsBotUser::getPoints)
         .last("limit " + limit)
         .list()
         .stream()
         .map(this::toProfile)
         .collect(Collectors.toList());
   }

   public PointsProfile findByUsername(long chatId, String username) {
      if (username != null && !username.isBlank()) {
         boolean normalized = username.startsWith("@");
         String lookup = normalized ? username.substring(1) : username;
         PointsBotUser user = new LambdaQueryChainWrapper<>(this.pointsBotUserMapper)
            .eq(PointsBotUser::getChatId, Long.valueOf(chatId))
            .eq(PointsBotUser::getUsername, lookup)
            .one();
         return user == null ? null : this.toProfile(user);
      } else {
         return null;
      }
   }

   public PointsProfile findByUserId(long chatId, long userId) {
      PointsBotUser user = new LambdaQueryChainWrapper<>(this.pointsBotUserMapper)
         .eq(PointsBotUser::getChatId, Long.valueOf(chatId))
         .eq(PointsBotUser::getUserId, Long.valueOf(userId))
         .one();
      return user == null ? null : this.toProfile(user);
   }

   public List<PointsProfile> getChatUsers(long chatId) {
      return new LambdaQueryChainWrapper<>(this.pointsBotUserMapper)
         .eq(PointsBotUser::getChatId, Long.valueOf(chatId))
         .list()
         .stream()
         .map(this::toProfile)
         .collect(Collectors.toList());
   }

   public void updateProfile(PointsProfile profile) {
      PointsBotUser locked = this.pointsBotUserMapper.selectForUpdate(profile.getChatId(), profile.getUserId());
      if (locked == null) {
         throw new IllegalStateException("积分用户不存在");
      } else {
         profile.setId(locked.getId());
         profile.setPoints(locked.getPoints() == null ? 0L : locked.getPoints());
         this.pointsBotUserMapper.updateById(this.toEntity(profile));
      }
   }

   private void insertLedger(long chatId, long userId, int delta, String reason, String refId, Long serverId) {
      PointsBotLedger ledger = new PointsBotLedger();
      ledger.setChatId(chatId);
      ledger.setUserId(userId);
      ledger.setDelta(delta);
      ledger.setReason(reason);
      ledger.setRefId(refId);
      ledger.setServerId(serverId);
      this.pointsBotLedgerMapper.insert(ledger);
   }

   public PointsProfile toProfile(PointsBotUser user) {
      PointsProfile profile = new PointsProfile();
      profile.setId(user.getId());
      profile.setChatId(user.getChatId());
      profile.setUserId(user.getUserId());
      profile.setUsername(user.getUsername());
      profile.setDisplayName(user.getDisplayName());
      profile.setLevelId(user.getLevelId());
      profile.setLevelName(user.getLevelName());
      profile.setPoints(user.getPoints() == null ? 0L : user.getPoints());
      profile.setCheckinStreak(user.getCheckinStreak() == null ? 0 : user.getCheckinStreak());
      profile.setLastCheckinDate(user.getLastCheckinDate());
      profile.setLastMessageDate(user.getLastMessageDate());
      profile.setDailyMessagePoints(user.getDailyMessagePoints() == null ? 0 : user.getDailyMessagePoints());
      profile.setDailyMessageCount(user.getDailyMessageCount() == null ? 0 : user.getDailyMessageCount());
      return profile;
   }

   private PointsBotUser toEntity(PointsProfile profile) {
      PointsBotUser user = new PointsBotUser();
      user.setId(profile.getId());
      user.setChatId(profile.getChatId());
      user.setUserId(profile.getUserId());
      user.setUsername(profile.getUsername());
      user.setDisplayName(profile.getDisplayName());
      user.setLevelId(profile.getLevelId());
      user.setLevelName(profile.getLevelName());
      user.setPoints(profile.getPoints());
      user.setCheckinStreak(profile.getCheckinStreak());
      user.setLastCheckinDate(profile.getLastCheckinDate());
      user.setLastMessageDate(profile.getLastMessageDate());
      user.setDailyMessagePoints(profile.getDailyMessagePoints());
      user.setDailyMessageCount(profile.getDailyMessageCount());
      return user;
   }

   private void applyLevel(PointsBotUser user) {
      if (!this.hasFixedImportedLevel(user.getLevelId())) {
         PointsBotLevelConfig level = this.levelConfigService.findLevelForPoints(user.getPoints() == null ? 0L : user.getPoints());
         if (level != null) {
            user.setLevelId(level.getId());
            user.setLevelName(level.getLevelName());
         }
      }
   }

   private boolean hasFixedImportedLevel(PointsProfile profile) {
      return profile != null && this.hasFixedImportedLevel(profile.getLevelId());
   }

   private boolean hasFixedImportedLevel(Long levelId) {
      if (levelId == null) {
         return false;
      } else {
         PointsBotLevelConfig level = this.levelConfigService.getById(levelId);
         return level != null && level.getRemark() != null && level.getRemark().startsWith("embyboss:fixed:");
      }
   }
}
