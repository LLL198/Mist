package com.una.embyhub.model.dto.request.embyuser;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.Locale;
import java.util.Set;

public interface ProtectedUserMutationRequest {
   Set<String> PROTECTED_FIELDS = Set.of(
      "isprimaryadmin",
      "primaryadmin",
      "isadmin",
      "isdistributor",
      "embyuserid",
      "userstatus",
      "expirationdate",
      "requestpackagescount",
      "hostlinetype",
      "clientip",
      "delflag",
      "createuserid",
      "updateuserid",
      "createdatetime",
      "updatedatetime"
   );

   @JsonAnySetter
   default void rejectProtectedUnknownField(String fieldName, Object ignoredValue) {
      if (PROTECTED_FIELDS.contains(normalize(fieldName))) {
         throw new IllegalArgumentException("不允许提交受保护的用户字段: " + fieldName);
      }
   }

   private static String normalize(String fieldName) {
      return fieldName == null ? "" : fieldName.replace("_", "").replace("-", "").toLowerCase(Locale.ROOT);
   }
}
