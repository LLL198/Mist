package com.una.embyhub.service;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.diboot.core.util.BeanUtils;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.config.common.utils.ConfigCacheLoaderUtils;
import com.una.embyhub.config.common.utils.TelegramClientUtils;
import com.una.embyhub.config.mybatis.MybatisPlusPage;
import com.una.embyhub.mapper.TelegramBindingReviewMapper;
import com.una.embyhub.model.dto.request.telegram.TelegramBindingReviewDecisionRequest;
import com.una.embyhub.model.dto.request.telegram.TelegramBindingReviewRequest;
import com.una.embyhub.model.dto.response.embynotifydata.TelegramResponse;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserCustomResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingActionResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingReviewResponse;
import com.una.embyhub.model.dto.response.telegram.TelegramBindingReviewStatsResponse;
import com.una.embyhub.model.entity.BaseEntity;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.TelegramBindingReview;
import com.una.embyhub.model.entity.UserOauthBinding;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Generated;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

@Service
public class TelegramBindingReviewService {
   private static final String REVIEWER_DISPLAY_NAME = "管理员";
   private final TelegramBindingReviewMapper telegramBindingReviewMapper;
   private final TelegramBindingManager telegramBindingManager;
   private final TelegramBindingReviewNotifier notifier;
   private final ConfigCacheLoaderUtils configCacheLoaderUtils;
   private final TelegramClientUtils telegramClientUtils;
   private final TelegramBindingMembershipGuard telegramBindingMembershipGuard;

   public boolean isBindReviewEnabled() {
      return this.bindingReviewSettings().bindReviewEnabled();
   }

   public boolean isUnbindReviewEnabled() {
      return this.bindingReviewSettings().unbindReviewEnabled();
   }

   public boolean isRebindReviewEnabled() {
      return this.bindingReviewSettings().rebindReviewEnabled();
   }

   public boolean isNotifyEnabled() {
      return this.bindingReviewSettings().reviewNotifyEnabled();
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingActionResponse submitBind(
      EmbyUser embyUser, Long telegramUserId, String telegramUsername, String telegramAvatar, String source, boolean allowReplace
   ) {
      if (embyUser != null && embyUser.getId() != null && telegramUserId != null) {
         this.telegramBindingMembershipGuard.assertCanBind(telegramUserId);
         if (!this.isBindReviewEnabled()) {
            EmbyUser result = this.telegramBindingManager.bind(embyUser, telegramUserId, telegramUsername, telegramAvatar, allowReplace);
            TelegramBindingReview snapshot = this.completedSnapshot(embyUser, telegramUserId, telegramUsername, telegramAvatar, "BIND", source, allowReplace);
            this.notifySuccessAfterCommit(snapshot);
            return TelegramBindingActionResponse.completed("Telegram 绑定成功", this.userResponse(result));
         } else {
            TelegramBindingReview existing = this.findPending(embyUser.getId(), String.valueOf(telegramUserId));
            if (existing != null) {
               if (embyUser.getId().equals(existing.getUserId()) && "BIND".equals(existing.getActionType())) {
                  return TelegramBindingActionResponse.pending(existing.getId(), existing.getReviewUuid(), "绑定申请已提交，请等待管理员审批", this.userResponse(embyUser));
               } else {
                  throw new BizException("该 Emby 或 Telegram 账号已有其他待审批申请");
               }
            } else {
               this.telegramBindingManager.validateBind(embyUser, telegramUserId, allowReplace);
               TelegramBindingReview review = this.newReview(embyUser, telegramUserId, telegramUsername, telegramAvatar, "BIND", source, allowReplace);
               this.insertReview(review);
               this.notifyNewReviewAfterCommit(review);
               return TelegramBindingActionResponse.pending(review.getId(), review.getReviewUuid(), "绑定申请已提交，请等待管理员审批", this.userResponse(embyUser));
            }
         }
      } else {
         throw new BizException("绑定参数无效");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingActionResponse submitUnbindByUserId(Long userId, String source) {
      UserOauthBinding binding = this.telegramBindingManager.findBindingByUserId(userId);
      if (binding != null && StringUtils.hasText(binding.getProviderUserId())) {
         return this.submitUnbind(binding, source);
      } else {
         throw new BizException("当前账号没有绑定 Telegram");
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingActionResponse submitUnbindByTelegramId(Long telegramUserId, String source) {
      UserOauthBinding binding = this.telegramBindingManager.findBindingByTelegramId(telegramUserId);
      return binding == null ? TelegramBindingActionResponse.completed("当前 Telegram 账号没有绑定 Emby 账号", null) : this.submitUnbind(binding, source);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingActionResponse submitRebind(
      EmbyUser embyUser,
      Long oldTelegramUserId,
      String oldTelegramUsername,
      String oldTelegramAvatar,
      Long newTelegramUserId,
      String newTelegramUsername,
      String newTelegramAvatar,
      String source
   ) {
      if (embyUser == null || embyUser.getId() == null || oldTelegramUserId == null || newTelegramUserId == null) {
         throw new BizException("换绑参数无效");
      } else if (oldTelegramUserId.equals(newTelegramUserId)) {
         throw new BizException("新 Telegram 账号不能与当前绑定相同");
      } else {
         this.telegramBindingMembershipGuard.assertCanBind(newTelegramUserId);
         UserOauthBinding currentBinding = this.telegramBindingManager.findBindingByUserId(embyUser.getId());
         if (currentBinding != null && String.valueOf(oldTelegramUserId).equals(currentBinding.getProviderUserId())) {
            this.telegramBindingManager.validateBind(embyUser, newTelegramUserId, true);
            if (!this.isRebindReviewEnabled()) {
               EmbyUser result = this.telegramBindingManager.rebind(embyUser, oldTelegramUserId, newTelegramUserId, newTelegramUsername, newTelegramAvatar);
               TelegramBindingReview snapshot = this.newReview(embyUser, newTelegramUserId, newTelegramUsername, newTelegramAvatar, "REBIND", source, true);
               this.applyOldBindingSnapshot(snapshot, oldTelegramUserId, oldTelegramUsername, oldTelegramAvatar);
               snapshot.setStatus(1);
               this.notifyRebindSecurityAfterCommit(snapshot);
               this.notifySuccessAfterCommit(snapshot);
               return TelegramBindingActionResponse.completed("Telegram 换绑成功", this.userResponse(result));
            } else {
               TelegramBindingReview existing = this.findPending(embyUser.getId(), String.valueOf(newTelegramUserId));
               if (existing == null) {
                  TelegramBindingReview review = this.newReview(embyUser, newTelegramUserId, newTelegramUsername, newTelegramAvatar, "REBIND", source, true);
                  this.applyOldBindingSnapshot(review, oldTelegramUserId, oldTelegramUsername, oldTelegramAvatar);
                  this.insertReview(review);
                  this.notifyNewReviewAfterCommit(review);
                  return TelegramBindingActionResponse.pending(review.getId(), review.getReviewUuid(), "换绑申请已提交，请等待管理员审批", this.userResponse(embyUser));
               } else if (embyUser.getId().equals(existing.getUserId())
                  && "REBIND".equals(existing.getActionType())
                  && String.valueOf(newTelegramUserId).equals(existing.getTelegramUserId())
                  && String.valueOf(oldTelegramUserId).equals(existing.getOldTelegramUserId())) {
                  return TelegramBindingActionResponse.pending(existing.getId(), existing.getReviewUuid(), "换绑申请已提交，请等待管理员审批", this.userResponse(embyUser));
               } else {
                  throw new BizException("该 Emby 或 Telegram 账号已有其他待审批申请");
               }
            }
         } else {
            throw new BizException("Telegram 绑定关系已变化，请刷新后重新发起换绑");
         }
      }
   }

   private void applyOldBindingSnapshot(TelegramBindingReview review, Long telegramUserId, String telegramUsername, String telegramAvatar) {
      review.setOldTelegramUserId(String.valueOf(telegramUserId));
      review.setOldTelegramUsername(telegramUsername);
      review.setOldTelegramAvatar(telegramAvatar);
   }

   private TelegramBindingActionResponse submitUnbind(UserOauthBinding binding, String source) {
      EmbyUser embyUser = this.telegramBindingManager.findUser(binding.getUserId());
      if (embyUser == null) {
         throw new BizException("绑定的 Emby 用户不存在");
      } else {
         Long telegramUserId;
         try {
            telegramUserId = Long.parseLong(binding.getProviderUserId());
         } catch (NumberFormatException var7) {
            throw new BizException("Telegram 绑定信息无效");
         }

         if (!this.isUnbindReviewEnabled()) {
            TelegramBindingReview snapshot = this.completedSnapshot(
               embyUser, telegramUserId, binding.getProviderUsername(), binding.getProviderAvatar(), "UNBIND", source, false
            );
            EmbyUser result = this.telegramBindingManager.unbindByTelegramId(telegramUserId);
            this.notifySuccessAfterCommit(snapshot);
            return TelegramBindingActionResponse.completed("Telegram 解绑成功", this.userResponse(result));
         } else {
            TelegramBindingReview existing = this.findPending(embyUser.getId(), binding.getProviderUserId());
            if (existing != null) {
               if (embyUser.getId().equals(existing.getUserId()) && "UNBIND".equals(existing.getActionType())) {
                  return TelegramBindingActionResponse.pending(existing.getId(), existing.getReviewUuid(), "解绑申请已提交，请等待管理员审批", this.userResponse(embyUser));
               } else {
                  throw new BizException("该 Emby 或 Telegram 账号已有其他待审批申请");
               }
            } else {
               TelegramBindingReview review = this.newReview(
                  embyUser, telegramUserId, binding.getProviderUsername(), binding.getProviderAvatar(), "UNBIND", source, false
               );
               this.insertReview(review);
               this.notifyNewReviewAfterCommit(review);
               return TelegramBindingActionResponse.pending(review.getId(), review.getReviewUuid(), "解绑申请已提交，请等待管理员审批", this.userResponse(embyUser));
            }
         }
      }
   }

   private TelegramBindingReview completedSnapshot(
      EmbyUser user, Long telegramUserId, String telegramUsername, String telegramAvatar, String actionType, String source, boolean allowReplace
   ) {
      TelegramBindingReview snapshot = this.newReview(user, telegramUserId, telegramUsername, telegramAvatar, actionType, source, allowReplace);
      snapshot.setStatus(1);
      return snapshot;
   }

   private TelegramBindingReview newReview(
      EmbyUser user, Long telegramUserId, String telegramUsername, String telegramAvatar, String actionType, String source, boolean allowReplace
   ) {
      TelegramBindingReview review = new TelegramBindingReview();
      review.setReviewUuid("FOAM" + UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT));
      review.setUserId(user.getId());
      review.setEmbyUserName(user.getEmbyUserName());
      review.setTelegramUserId(String.valueOf(telegramUserId));
      review.setTelegramUsername(telegramUsername);
      review.setTelegramAvatar(telegramAvatar);
      review.setActionType(actionType);
      review.setRequestSource(StringUtils.hasText(source) ? source : "WEB");
      review.setReplaceExisting(allowReplace ? 1 : 0);
      review.setStatus(0);
      review.setCreateDatetime(new Date());
      review.setCreateUserId(user.getId());
      review.setCreateUserName(user.getEmbyUserName());
      return review;
   }

   private void insertReview(TelegramBindingReview review) {
      try {
         this.telegramBindingReviewMapper.insert(review);
      } catch (DuplicateKeyException var3) {
         throw new BizException("该 Emby 或 Telegram 账号已有待审批申请，请刷新后重试");
      }
   }

   private TelegramBindingReview findPending(Long userId, String telegramUserId) {
      return new LambdaQueryChainWrapper<>(this.telegramBindingReviewMapper)
         .eq(TelegramBindingReview::getStatus, Integer.valueOf(0))
         .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
         .and(wrapper -> wrapper.eq(TelegramBindingReview::getUserId, userId).or().eq(TelegramBindingReview::getTelegramUserId, telegramUserId))
         .last("LIMIT 1")
         .one();
   }

   public boolean hasPendingReviewForTelegram(Long telegramUserId) {
      return telegramUserId == null
         ? false
         : new LambdaQueryChainWrapper<>(this.telegramBindingReviewMapper)
            .eq(TelegramBindingReview::getTelegramUserId, String.valueOf(telegramUserId))
            .eq(TelegramBindingReview::getStatus, Integer.valueOf(0))
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .exists();
   }

   public Page<TelegramBindingReviewResponse> select(MybatisPlusPage<TelegramBindingReviewRequest> pageRequest) {
      QueryWrapper<TelegramBindingReview> wrapper = this.buildFilter(pageRequest.getObject());
      wrapper.orderByDesc("id");
      Page<TelegramBindingReview> entityPage = this.telegramBindingReviewMapper.selectPage(pageRequest.getPageDto(TelegramBindingReview.class), wrapper);
      Page<TelegramBindingReviewResponse> response = new Page<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());
      response.setRecords(entityPage.getRecords().stream().map(item -> BeanUtils.convert(item, TelegramBindingReviewResponse.class)).toList());
      return response;
   }

   public TelegramBindingReviewStatsResponse stats(TelegramBindingReviewRequest request) {
      TelegramBindingReviewStatsResponse stats = new TelegramBindingReviewStatsResponse();
      stats.setTotal(this.count(request, null, null));
      stats.setPending(this.count(request, 0, null));
      stats.setApproved(this.count(request, 1, null));
      stats.setRejected(this.count(request, 2, null));
      stats.setCancelled(this.count(request, 3, null));
      stats.setApprovedBind(this.count(request, 1, "BIND"));
      stats.setApprovedUnbind(this.count(request, 1, "UNBIND"));
      stats.setApprovedRebind(this.count(request, 1, "REBIND"));
      QueryWrapper<TelegramBindingReview> rankingWrapper = this.buildFilter(request);
      rankingWrapper.eq("status", Integer.valueOf(1))
         .select(
            new String[]{
               "user_id",
               "MAX(emby_user_name) AS emby_user_name",
               "SUM(CASE WHEN action_type = 'BIND' THEN 1 ELSE 0 END) AS bind_count",
               "SUM(CASE WHEN action_type = 'UNBIND' THEN 1 ELSE 0 END) AS unbind_count",
               "SUM(CASE WHEN action_type = 'REBIND' THEN 1 ELSE 0 END) AS rebind_count",
               "COUNT(*) AS total_count"
            }
         )
         .groupBy("user_id")
         .orderByDesc("total_count")
         .last("LIMIT 10");
      List<Map<String, Object>> rows = this.telegramBindingReviewMapper.selectMaps(rankingWrapper);
      stats.setRankings(
         rows.stream()
            .map(
               row -> new TelegramBindingReviewStatsResponse.RankingItem(
                     this.longValue(row.get("user_id")),
                     this.stringValue(row.get("emby_user_name")),
                     this.longValue(row.get("bind_count")),
                     this.longValue(row.get("unbind_count")),
                     this.longValue(row.get("rebind_count")),
                     this.longValue(row.get("total_count"))
                  )
            )
            .toList()
      );
      return stats;
   }

   private long count(TelegramBindingReviewRequest request, Integer status, String actionType) {
      QueryWrapper<TelegramBindingReview> wrapper = this.buildFilter(request);
      if (status != null) {
         wrapper.eq("status", status);
      }

      if (StringUtils.hasText(actionType)) {
         wrapper.eq("action_type", actionType);
      }

      return this.telegramBindingReviewMapper.selectCount(wrapper);
   }

   private QueryWrapper<TelegramBindingReview> buildFilter(TelegramBindingReviewRequest request) {
      QueryWrapper<TelegramBindingReview> wrapper = new QueryWrapper<>();
      wrapper.eq("del_flag", Integer.valueOf(0));
      if (request == null) {
         return wrapper;
      } else {
         if (StringUtils.hasText(request.getKeyword())) {
            String keyword = request.getKeyword().trim();
            wrapper.and(
               query -> query.like("emby_user_name", keyword)
                     .or()
                     .like("telegram_username", keyword)
                     .or()
                     .like("telegram_user_id", keyword)
                     .or()
                     .like("review_uuid", keyword)
            );
         }

         if (StringUtils.hasText(request.getReviewUuid())) {
            wrapper.like("review_uuid", request.getReviewUuid().trim().toUpperCase(Locale.ROOT));
         }

         if (StringUtils.hasText(request.getEmbyUserName())) {
            wrapper.like("emby_user_name", request.getEmbyUserName().trim());
         }

         if (StringUtils.hasText(request.getTelegramUsername())) {
            wrapper.like("telegram_username", request.getTelegramUsername().trim().replaceFirst("^@", ""));
         }

         if (StringUtils.hasText(request.getTelegramUserId())) {
            wrapper.like("telegram_user_id", request.getTelegramUserId().trim());
         }

         if (StringUtils.hasText(request.getActionType())) {
            wrapper.eq("action_type", request.getActionType().trim().toUpperCase());
         }

         if (request.getStatus() != null) {
            wrapper.eq("status", request.getStatus());
         }

         if (StringUtils.hasText(request.getRequestSource())) {
            String source = request.getRequestSource().trim().toUpperCase();
            if ("BOT_COMMAND".equals(source)) {
               wrapper.in("request_source", new Object[]{"BOT", "BOT_CREDENTIAL"});
            } else {
               wrapper.eq("request_source", source);
            }
         }

         return wrapper;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingReviewResponse review(TelegramBindingReviewDecisionRequest request) {
      EmbyUser reviewer = this.currentUser();
      if (!Integer.valueOf(1).equals(reviewer.getIsAdmin())) {
         throw new BizException(ResponseStatusEnum.FORBIDDEN);
      } else {
         return this.decide(
            request.getId(),
            Boolean.TRUE.equals(request.getApproved()),
            request.getRemark(),
            reviewer.getId(),
            "管理员",
            this.resolveWebReviewerIdentity(reviewer)
         );
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingReviewResponse reviewFromTelegram(Long reviewId, boolean approved, Long telegramReviewerId) {
      return this.reviewFromTelegram(reviewId, approved, telegramReviewerId, null, null);
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingReviewResponse reviewFromTelegram(
      Long reviewId, boolean approved, Long telegramReviewerId, String telegramUsername, String telegramDisplayName
   ) {
      TelegramBindingReviewService.Reviewer reviewer = this.resolveTelegramReviewer(telegramReviewerId);
      TelegramBindingReviewNotifier.TelegramReviewerIdentity reviewerIdentity = new TelegramBindingReviewNotifier.TelegramReviewerIdentity(
         telegramReviewerId, telegramUsername, telegramDisplayName
      );
      return this.decide(reviewId, approved, null, reviewer.userId(), reviewer.userName(), reviewerIdentity);
   }

   public TelegramBindingReviewResponse findPendingByUserId(Long userId) {
      if (userId == null) {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      } else {
         TelegramBindingReview review = new LambdaQueryChainWrapper<>(this.telegramBindingReviewMapper)
            .eq(TelegramBindingReview::getUserId, userId)
            .eq(TelegramBindingReview::getStatus, Integer.valueOf(0))
            .eq(BaseEntity::getDelFlag, Integer.valueOf(0))
            .orderByDesc(TelegramBindingReview::getId)
            .last("LIMIT 1")
            .one();
         return review == null ? null : BeanUtils.convert(review, TelegramBindingReviewResponse.class);
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingReviewResponse cancelByUserId(Long userId, String reviewUuid) {
      if (userId == null) {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      } else {
         TelegramBindingReview review;
         if (StringUtils.hasText(reviewUuid)) {
            review = this.loadByUuidForCancellation(reviewUuid);
         } else {
            review = this.telegramBindingReviewMapper.selectPendingByUserIdForUpdate(userId);
            if (review == null) {
               throw new BizException("当前没有待审批的 Telegram 申请");
            }
         }

         if (!userId.equals(review.getUserId())) {
            throw new BizException("只能取消本人提交的申请");
         } else {
            return this.cancel(review, review.getUserId(), review.getEmbyUserName());
         }
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   public TelegramBindingReviewResponse cancelByTelegramUserId(Long telegramUserId, String reviewUuid) {
      if (telegramUserId == null) {
         throw new BizException("Telegram 用户信息无效");
      } else {
         TelegramBindingReview review;
         if (StringUtils.hasText(reviewUuid)) {
            review = this.loadByUuidForCancellation(reviewUuid);
         } else {
            review = this.telegramBindingReviewMapper.selectPendingByTelegramUserIdForUpdate(String.valueOf(telegramUserId));
            if (review == null) {
               throw new BizException("当前没有待审批的绑定或解绑申请");
            }
         }

         if (!String.valueOf(telegramUserId).equals(review.getTelegramUserId())) {
            throw new BizException("只能取消本人提交的申请");
         } else {
            return this.cancel(review, review.getUserId(), review.getEmbyUserName());
         }
      }
   }

   private TelegramBindingReview loadByUuidForCancellation(String reviewUuid) {
      if (!StringUtils.hasText(reviewUuid)) {
         throw new BizException("审批指纹不能为空");
      } else {
         TelegramBindingReview review = this.telegramBindingReviewMapper.selectByReviewUuidForUpdate(reviewUuid.trim().toUpperCase(Locale.ROOT));
         if (review == null) {
            throw new BizException("审批申请不存在");
         } else {
            return review;
         }
      }
   }

   private TelegramBindingReviewResponse cancel(TelegramBindingReview review, Long actorUserId, String actorUserName) {
      if (Integer.valueOf(3).equals(review.getStatus())) {
         throw new BizException("该申请已由用户自助取消");
      } else if (!Integer.valueOf(0).equals(review.getStatus())) {
         throw new BizException("该申请已完成审批，无法取消");
      } else {
         Date now = new Date();
         review.setStatus(3);
         review.setReviewerUserId(actorUserId);
         review.setReviewerUserName(actorUserName);
         review.setReviewRemark("用户自助取消申请");
         review.setReviewDatetime(now);
         review.setUpdateDatetime(now);
         review.setUpdateUserId(actorUserId);
         review.setUpdateUserName(actorUserName);
         this.telegramBindingReviewMapper.updateById(review);
         this.notifyCancellationAfterCommit(review);
         return BeanUtils.convert(review, TelegramBindingReviewResponse.class);
      }
   }

   private TelegramBindingReviewResponse decide(
      Long reviewId,
      boolean approved,
      String remark,
      Long reviewerUserId,
      String reviewerUserName,
      TelegramBindingReviewNotifier.TelegramReviewerIdentity reviewerIdentity
   ) {
      TelegramBindingReview review = this.telegramBindingReviewMapper.selectByIdForUpdate(reviewId);
      if (review == null) {
         throw new BizException("审批记录不存在");
      } else if (Integer.valueOf(3).equals(review.getStatus())) {
         throw new BizException("该申请已由用户自助取消");
      } else if (!Integer.valueOf(0).equals(review.getStatus())) {
         throw new BizException("该申请已完成审批");
      } else {
         if (approved) {
            this.applyApprovedReview(review);
         }

         Date now = new Date();
         review.setStatus(approved ? 1 : 2);
         review.setReviewerUserId(reviewerUserId);
         review.setReviewerUserName(reviewerUserName);
         review.setReviewRemark(StringUtils.hasText(remark) ? remark.trim() : null);
         review.setReviewDatetime(now);
         review.setUpdateDatetime(now);
         review.setUpdateUserId(reviewerUserId);
         review.setUpdateUserName(reviewerUserName);
         this.telegramBindingReviewMapper.updateById(review);
         this.notifyResultAfterCommit(review, reviewerIdentity);
         return BeanUtils.convert(review, TelegramBindingReviewResponse.class);
      }
   }

   private void applyApprovedReview(TelegramBindingReview review) {
      long telegramUserId;
      try {
         telegramUserId = Long.parseLong(review.getTelegramUserId());
      } catch (NumberFormatException var9) {
         throw new BizException("Telegram 用户信息无效，无法通过审批");
      }

      if ("UNBIND".equals(review.getActionType())) {
         UserOauthBinding binding = this.telegramBindingManager.findBindingByTelegramId(telegramUserId);
         if (binding != null && review.getUserId().equals(binding.getUserId())) {
            this.telegramBindingManager.unbindByTelegramId(telegramUserId);
         } else {
            throw new BizException("绑定关系已变化，请拒绝该申请并让用户重新提交");
         }
      } else {
         this.telegramBindingMembershipGuard.assertCanBind(telegramUserId);
         EmbyUser user = this.telegramBindingManager.findUsableUser(review.getUserId());
         if (user == null) {
            throw new BizException("Emby 用户不存在或已被禁用，无法通过审批");
         } else if ("REBIND".equals(review.getActionType())) {
            long oldTelegramUserId;
            try {
               oldTelegramUserId = Long.parseLong(review.getOldTelegramUserId());
            } catch (NumberFormatException var8) {
               throw new BizException("原 Telegram 用户信息无效，无法通过审批");
            }

            this.telegramBindingManager.rebind(user, oldTelegramUserId, telegramUserId, review.getTelegramUsername(), review.getTelegramAvatar());
            this.notifyRebindSecurityAfterCommit(review);
         } else {
            this.telegramBindingManager
               .bind(user, telegramUserId, review.getTelegramUsername(), review.getTelegramAvatar(), Integer.valueOf(1).equals(review.getReplaceExisting()));
         }
      }
   }

   private TelegramBindingReviewService.Reviewer resolveTelegramReviewer(Long telegramReviewerId) {
      if (telegramReviewerId == null) {
         throw new BizException("Telegram 审批人无效");
      } else {
         Long adminUserId = this.telegramBindingManager.findActiveAdminUserIdByTelegramId(telegramReviewerId);
         if (adminUserId != null) {
            return new TelegramBindingReviewService.Reviewer(adminUserId, "管理员");
         } else {
            TelegramResponse config = this.telegramClientUtils.getTelegramResponse();
            if (config != null && String.valueOf(telegramReviewerId).equals(config.getBotChatId())) {
               return new TelegramBindingReviewService.Reviewer(null, "管理员");
            } else {
               throw new BizException("只有超管或管理员可以审批");
            }
         }
      }
   }

   private TelegramBindingReviewNotifier.TelegramReviewerIdentity resolveWebReviewerIdentity(EmbyUser reviewer) {
      UserOauthBinding binding = this.telegramBindingManager.findBindingByUserId(reviewer == null ? null : reviewer.getId());
      if (binding != null && StringUtils.hasText(binding.getProviderUserId())) {
         long telegramUserId = this.longValue(binding.getProviderUserId());
         if (telegramUserId <= 0L) {
            return null;
         } else {
            TelegramBindingReviewNotifier.TelegramChatProfile profile = this.notifier.resolveTelegramChatProfile(telegramUserId);
            String username = profile.resolved() && StringUtils.hasText(profile.username()) ? profile.username() : binding.getProviderUsername();
            String displayName = profile.resolved() ? profile.displayName() : null;
            return new TelegramBindingReviewNotifier.TelegramReviewerIdentity(telegramUserId, username, displayName);
         }
      } else {
         return null;
      }
   }

   private EmbyUser currentUser() {
      EmbyUser user = (EmbyUser)StpUtil.getSession().get("user");
      if (user == null) {
         throw new BizException(ResponseStatusEnum.UNAUTHORIZED);
      } else {
         return user;
      }
   }

   private EmbyUserCustomResponse userResponse(EmbyUser user) {
      return user == null ? null : BeanUtils.convert(user, EmbyUserCustomResponse.class);
   }

   private void notifyNewReviewAfterCommit(TelegramBindingReview review) {
      if (this.isNotifyEnabled()) {
         this.afterCommit(() -> this.notifier.notifyNewReview(review));
      }
   }

   private void notifyResultAfterCommit(TelegramBindingReview review, TelegramBindingReviewNotifier.TelegramReviewerIdentity reviewerIdentity) {
      this.afterCommit(() -> this.notifier.synchronizeReviewStatus(review, reviewerIdentity));
      if (this.isNotifyEnabled()) {
         this.afterCommit(() -> this.notifier.notifyReviewResult(review));
      }

      if (Integer.valueOf(1).equals(review.getStatus())) {
         this.notifySuccessAfterCommit(review);
      }
   }

   private void notifyCancellationAfterCommit(TelegramBindingReview review) {
      this.afterCommit(() -> this.notifier.synchronizeReviewStatus(review));
      if (this.isNotifyEnabled()) {
         this.afterCommit(() -> this.notifier.notifyCancellation(review));
      }
   }

   private void notifyRebindSecurityAfterCommit(TelegramBindingReview review) {
      this.afterCommit(() -> this.notifier.notifyOldTelegramRebound(review));
   }

   private void notifySuccessAfterCommit(TelegramBindingReview review) {
      TelegramBindingReviewService.TelegramBindingSuccessNotifySettings settings = this.successNotifySettings();
      if (settings.successNotifyEnabled()) {
         Set<String> targets = settings.successNotifyTargets(review == null ? null : review.getActionType());
         if (!targets.isEmpty()) {
            this.afterCommit(() -> this.notifier.notifyBindingSuccess(review, targets));
         }
      }
   }

   private TelegramBindingReviewService.TelegramBindingReviewSettings bindingReviewSettings() {
      String value = this.configCacheLoaderUtils.getConfigValue("telegram_binding_review_config");
      if (!StringUtils.hasText(value)) {
         return TelegramBindingReviewService.TelegramBindingReviewSettings.disabled();
      } else {
         try {
            JSONObject json = JSONObject.parseObject(value);
            return json == null
               ? TelegramBindingReviewService.TelegramBindingReviewSettings.disabled()
               : new TelegramBindingReviewService.TelegramBindingReviewSettings(
                  this.booleanField(json, "bindReviewEnabled", true),
                  this.booleanField(json, "unbindReviewEnabled", true),
                  this.booleanField(json, "rebindReviewEnabled", true),
                  this.booleanField(json, "reviewNotifyEnabled", true)
               );
         } catch (Exception var3) {
            return TelegramBindingReviewService.TelegramBindingReviewSettings.disabled();
         }
      }
   }

   private TelegramBindingReviewService.TelegramBindingSuccessNotifySettings successNotifySettings() {
      String value = this.configCacheLoaderUtils.getConfigValue("telegram_binding_success_notify_config");
      if (!StringUtils.hasText(value)) {
         return TelegramBindingReviewService.TelegramBindingSuccessNotifySettings.disabled();
      } else {
         try {
            JSONObject json = JSONObject.parseObject(value);
            return json == null
               ? TelegramBindingReviewService.TelegramBindingSuccessNotifySettings.disabled()
               : new TelegramBindingReviewService.TelegramBindingSuccessNotifySettings(
                  this.booleanField(json, "enabled", true),
                  this.notifyTargets(json, "bindSuccessNotifyTargets"),
                  this.notifyTargets(json, "unbindSuccessNotifyTargets"),
                  this.notifyTargets(json, "rebindSuccessNotifyTargets")
               );
         } catch (Exception var3) {
            return TelegramBindingReviewService.TelegramBindingSuccessNotifySettings.disabled();
         }
      }
   }

   private boolean booleanField(JSONObject json, String field, boolean fallback) {
      Boolean value = json.getBoolean(field);
      return value == null ? fallback : value;
   }

   private Set<String> notifyTargets(JSONObject json, String field) {
      if (!json.containsKey(field)) {
         return this.defaultNotifyTargets();
      } else {
         Object value = json.get(field);
         Set<String> targets = new LinkedHashSet<>();
         if (value instanceof Iterable<?> values) {
            values.forEach(itemx -> this.addNotifyTarget(targets, itemx));
         } else if (value != null) {
            for (String item : String.valueOf(value).split("[,，|]")) {
               this.addNotifyTarget(targets, item);
            }
         }

         return Set.copyOf(targets);
      }
   }

   private Set<String> defaultNotifyTargets() {
      return Set.of("bot", "group");
   }

   private void addNotifyTarget(Set<String> targets, Object value) {
      String target = value == null ? "" : String.valueOf(value).trim().toLowerCase(Locale.ROOT);
      if ("bot".equals(target) || "group".equals(target)) {
         targets.add(target);
      }
   }

   private void afterCommit(Runnable callback) {
      if (!TransactionSynchronizationManager.isSynchronizationActive()) {
         callback.run();
      } else {
         TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
               callback.run();
            }
         });
      }
   }

   private long longValue(Object value) {
      if (value instanceof Number number) {
         return number.longValue();
      } else {
         try {
            return Long.parseLong(String.valueOf(value));
         } catch (Exception var3) {
            return 0L;
         }
      }
   }

   private String stringValue(Object value) {
      return value == null ? "" : String.valueOf(value);
   }

   @Generated
   public TelegramBindingReviewService(
      final TelegramBindingReviewMapper telegramBindingReviewMapper,
      final TelegramBindingManager telegramBindingManager,
      final TelegramBindingReviewNotifier notifier,
      final ConfigCacheLoaderUtils configCacheLoaderUtils,
      final TelegramClientUtils telegramClientUtils,
      final TelegramBindingMembershipGuard telegramBindingMembershipGuard
   ) {
      this.telegramBindingReviewMapper = telegramBindingReviewMapper;
      this.telegramBindingManager = telegramBindingManager;
      this.notifier = notifier;
      this.configCacheLoaderUtils = configCacheLoaderUtils;
      this.telegramClientUtils = telegramClientUtils;
      this.telegramBindingMembershipGuard = telegramBindingMembershipGuard;
   }

   private static record Reviewer(Long userId, String userName) {
   }

   private static record TelegramBindingReviewSettings(
      boolean bindReviewEnabled, boolean unbindReviewEnabled, boolean rebindReviewEnabled, boolean reviewNotifyEnabled
   ) {
      private static TelegramBindingReviewService.TelegramBindingReviewSettings disabled() {
         return new TelegramBindingReviewService.TelegramBindingReviewSettings(false, false, false, false);
      }
   }

   private static record TelegramBindingSuccessNotifySettings(
      boolean successNotifyEnabled, Set<String> bindSuccessNotifyTargets, Set<String> unbindSuccessNotifyTargets, Set<String> rebindSuccessNotifyTargets
   ) {
      private static TelegramBindingReviewService.TelegramBindingSuccessNotifySettings disabled() {
         return new TelegramBindingReviewService.TelegramBindingSuccessNotifySettings(false, Set.of(), Set.of(), Set.of());
      }

      private Set<String> successNotifyTargets(String actionType) {
         if ("BIND".equals(actionType)) {
            return this.bindSuccessNotifyTargets;
         } else if ("UNBIND".equals(actionType)) {
            return this.unbindSuccessNotifyTargets;
         } else {
            return "REBIND".equals(actionType) ? this.rebindSuccessNotifyTargets : Set.of();
         }
      }
   }
}
