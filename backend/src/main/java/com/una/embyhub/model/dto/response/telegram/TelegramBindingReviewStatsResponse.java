package com.una.embyhub.model.dto.response.telegram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class TelegramBindingReviewStatsResponse implements Serializable {
   private long total;
   private long pending;
   private long approved;
   private long rejected;
   private long cancelled;
   private long approvedBind;
   private long approvedUnbind;
   private long approvedRebind;
   private List<TelegramBindingReviewStatsResponse.RankingItem> rankings = new ArrayList<>();

   @Generated
   public long getTotal() {
      return this.total;
   }

   @Generated
   public long getPending() {
      return this.pending;
   }

   @Generated
   public long getApproved() {
      return this.approved;
   }

   @Generated
   public long getRejected() {
      return this.rejected;
   }

   @Generated
   public long getCancelled() {
      return this.cancelled;
   }

   @Generated
   public long getApprovedBind() {
      return this.approvedBind;
   }

   @Generated
   public long getApprovedUnbind() {
      return this.approvedUnbind;
   }

   @Generated
   public long getApprovedRebind() {
      return this.approvedRebind;
   }

   @Generated
   public List<TelegramBindingReviewStatsResponse.RankingItem> getRankings() {
      return this.rankings;
   }

   @Generated
   public void setTotal(final long total) {
      this.total = total;
   }

   @Generated
   public void setPending(final long pending) {
      this.pending = pending;
   }

   @Generated
   public void setApproved(final long approved) {
      this.approved = approved;
   }

   @Generated
   public void setRejected(final long rejected) {
      this.rejected = rejected;
   }

   @Generated
   public void setCancelled(final long cancelled) {
      this.cancelled = cancelled;
   }

   @Generated
   public void setApprovedBind(final long approvedBind) {
      this.approvedBind = approvedBind;
   }

   @Generated
   public void setApprovedUnbind(final long approvedUnbind) {
      this.approvedUnbind = approvedUnbind;
   }

   @Generated
   public void setApprovedRebind(final long approvedRebind) {
      this.approvedRebind = approvedRebind;
   }

   @Generated
   public void setRankings(final List<TelegramBindingReviewStatsResponse.RankingItem> rankings) {
      this.rankings = rankings;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof TelegramBindingReviewStatsResponse other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getTotal() != other.getTotal()) {
         return false;
      } else if (this.getPending() != other.getPending()) {
         return false;
      } else if (this.getApproved() != other.getApproved()) {
         return false;
      } else if (this.getRejected() != other.getRejected()) {
         return false;
      } else if (this.getCancelled() != other.getCancelled()) {
         return false;
      } else if (this.getApprovedBind() != other.getApprovedBind()) {
         return false;
      } else if (this.getApprovedUnbind() != other.getApprovedUnbind()) {
         return false;
      } else if (this.getApprovedRebind() != other.getApprovedRebind()) {
         return false;
      } else {
         Object this$rankings = this.getRankings();
         Object other$rankings = other.getRankings();
         return this$rankings == null ? other$rankings == null : this$rankings.equals(other$rankings);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof TelegramBindingReviewStatsResponse;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      long $total = this.getTotal();
      result = result * 59 + (int)($total >>> 32 ^ $total);
      long $pending = this.getPending();
      result = result * 59 + (int)($pending >>> 32 ^ $pending);
      long $approved = this.getApproved();
      result = result * 59 + (int)($approved >>> 32 ^ $approved);
      long $rejected = this.getRejected();
      result = result * 59 + (int)($rejected >>> 32 ^ $rejected);
      long $cancelled = this.getCancelled();
      result = result * 59 + (int)($cancelled >>> 32 ^ $cancelled);
      long $approvedBind = this.getApprovedBind();
      result = result * 59 + (int)($approvedBind >>> 32 ^ $approvedBind);
      long $approvedUnbind = this.getApprovedUnbind();
      result = result * 59 + (int)($approvedUnbind >>> 32 ^ $approvedUnbind);
      long $approvedRebind = this.getApprovedRebind();
      result = result * 59 + (int)($approvedRebind >>> 32 ^ $approvedRebind);
      Object $rankings = this.getRankings();
      return result * 59 + ($rankings == null ? 43 : $rankings.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "TelegramBindingReviewStatsResponse(total="
         + this.getTotal()
         + ", pending="
         + this.getPending()
         + ", approved="
         + this.getApproved()
         + ", rejected="
         + this.getRejected()
         + ", cancelled="
         + this.getCancelled()
         + ", approvedBind="
         + this.getApprovedBind()
         + ", approvedUnbind="
         + this.getApprovedUnbind()
         + ", approvedRebind="
         + this.getApprovedRebind()
         + ", rankings="
         + this.getRankings()
         + ")";
   }

   public static class RankingItem implements Serializable {
      private Long userId;
      private String embyUserName;
      private long bindCount;
      private long unbindCount;
      private long rebindCount;
      private long totalCount;

      @Generated
      public Long getUserId() {
         return this.userId;
      }

      @Generated
      public String getEmbyUserName() {
         return this.embyUserName;
      }

      @Generated
      public long getBindCount() {
         return this.bindCount;
      }

      @Generated
      public long getUnbindCount() {
         return this.unbindCount;
      }

      @Generated
      public long getRebindCount() {
         return this.rebindCount;
      }

      @Generated
      public long getTotalCount() {
         return this.totalCount;
      }

      @Generated
      public void setUserId(final Long userId) {
         this.userId = userId;
      }

      @Generated
      public void setEmbyUserName(final String embyUserName) {
         this.embyUserName = embyUserName;
      }

      @Generated
      public void setBindCount(final long bindCount) {
         this.bindCount = bindCount;
      }

      @Generated
      public void setUnbindCount(final long unbindCount) {
         this.unbindCount = unbindCount;
      }

      @Generated
      public void setRebindCount(final long rebindCount) {
         this.rebindCount = rebindCount;
      }

      @Generated
      public void setTotalCount(final long totalCount) {
         this.totalCount = totalCount;
      }

      @Generated
      @Override
      public boolean equals(final Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof TelegramBindingReviewStatsResponse.RankingItem other)) {
            return false;
         } else if (!other.canEqual(this)) {
            return false;
         } else if (this.getBindCount() != other.getBindCount()) {
            return false;
         } else if (this.getUnbindCount() != other.getUnbindCount()) {
            return false;
         } else if (this.getRebindCount() != other.getRebindCount()) {
            return false;
         } else if (this.getTotalCount() != other.getTotalCount()) {
            return false;
         } else {
            Object this$userId = this.getUserId();
            Object other$userId = other.getUserId();
            if (this$userId == null ? other$userId == null : this$userId.equals(other$userId)) {
               Object this$embyUserName = this.getEmbyUserName();
               Object other$embyUserName = other.getEmbyUserName();
               return this$embyUserName == null ? other$embyUserName == null : this$embyUserName.equals(other$embyUserName);
            } else {
               return false;
            }
         }
      }

      @Generated
      protected boolean canEqual(final Object other) {
         return other instanceof TelegramBindingReviewStatsResponse.RankingItem;
      }

      @Generated
      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         long $bindCount = this.getBindCount();
         result = result * 59 + (int)($bindCount >>> 32 ^ $bindCount);
         long $unbindCount = this.getUnbindCount();
         result = result * 59 + (int)($unbindCount >>> 32 ^ $unbindCount);
         long $rebindCount = this.getRebindCount();
         result = result * 59 + (int)($rebindCount >>> 32 ^ $rebindCount);
         long $totalCount = this.getTotalCount();
         result = result * 59 + (int)($totalCount >>> 32 ^ $totalCount);
         Object $userId = this.getUserId();
         result = result * 59 + ($userId == null ? 43 : $userId.hashCode());
         Object $embyUserName = this.getEmbyUserName();
         return result * 59 + ($embyUserName == null ? 43 : $embyUserName.hashCode());
      }

      @Generated
      @Override
      public String toString() {
         return "TelegramBindingReviewStatsResponse.RankingItem(userId="
            + this.getUserId()
            + ", embyUserName="
            + this.getEmbyUserName()
            + ", bindCount="
            + this.getBindCount()
            + ", unbindCount="
            + this.getUnbindCount()
            + ", rebindCount="
            + this.getRebindCount()
            + ", totalCount="
            + this.getTotalCount()
            + ")";
      }

      @Generated
      public RankingItem() {
      }

      @Generated
      public RankingItem(
         final Long userId, final String embyUserName, final long bindCount, final long unbindCount, final long rebindCount, final long totalCount
      ) {
         this.userId = userId;
         this.embyUserName = embyUserName;
         this.bindCount = bindCount;
         this.unbindCount = unbindCount;
         this.rebindCount = rebindCount;
         this.totalCount = totalCount;
      }
   }
}
