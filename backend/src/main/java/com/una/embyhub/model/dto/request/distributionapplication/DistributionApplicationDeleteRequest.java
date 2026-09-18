package com.una.embyhub.model.dto.request.distributionapplication;

import java.io.Serializable;
import java.util.List;
import lombok.Generated;

public class DistributionApplicationDeleteRequest implements Serializable {
   private List<Long> ids;

   @Generated
   public List<Long> getIds() {
      return this.ids;
   }

   @Generated
   public void setIds(final List<Long> ids) {
      this.ids = ids;
   }

   @Generated
   @Override
   public boolean equals(final Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof DistributionApplicationDeleteRequest other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         Object this$ids = this.getIds();
         Object other$ids = other.getIds();
         return this$ids == null ? other$ids == null : this$ids.equals(other$ids);
      }
   }

   @Generated
   protected boolean canEqual(final Object other) {
      return other instanceof DistributionApplicationDeleteRequest;
   }

   @Generated
   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $ids = this.getIds();
      return result * 59 + ($ids == null ? 43 : $ids.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "DistributionApplicationDeleteRequest(ids=" + this.getIds() + ")";
   }
}
