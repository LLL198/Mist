package com.una.embyhub.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.invitation.InvitationCodeGenerateRequest;
import com.una.embyhub.model.dto.request.invitation.InvitationCodeQueryRequest;
import com.una.embyhub.model.dto.response.invitation.InvitationCodeResponse;
import com.una.embyhub.model.dto.response.invitation.InvitationCodeStatusResponse;
import com.una.embyhub.model.entity.InvitationCode;
import java.util.List;

public interface InvitationCodeService extends IService<InvitationCode> {
   List<InvitationCodeResponse> generate(InvitationCodeGenerateRequest request);

   Page<InvitationCodeResponse> query(InvitationCodeQueryRequest request);

   void deleteById(Long invitationCodeId);

   InvitationCode useInvitationCode(String code, String usedBy);

   InvitationCodeStatusResponse status();
}
