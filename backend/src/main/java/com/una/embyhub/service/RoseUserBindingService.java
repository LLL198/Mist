package com.una.embyhub.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.una.embyhub.model.dto.request.rose.RoseBindRequest;
import com.una.embyhub.model.dto.request.rose.RoseLibraryBrowseRequest;
import com.una.embyhub.model.dto.request.rose.RoseQrStartRequest;
import com.una.embyhub.model.dto.response.embyuser.EmbyUserResponse;
import com.una.embyhub.model.dto.response.rose.RoseBindingResponse;
import com.una.embyhub.model.dto.response.rose.RoseProfileResponse;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.model.entity.RoseUserBinding;
import java.util.List;

public interface RoseUserBindingService extends IService<RoseUserBinding> {
   RoseProfileResponse profile(EmbyUser user, boolean syncRose);

   JSONObject startQr(EmbyUser user, RoseQrStartRequest request);

   JSONObject qrStatus(EmbyUser user, String sessionId);

   byte[] qrImage(EmbyUser user, String sessionId);

   RoseProfileResponse bind(EmbyUser user, RoseBindRequest request);

   RoseBindingResponse adminUnbind(EmbyUser user, String adminPassword);

   JSONObject browseLibrarySourceRoot(EmbyUser user, RoseLibraryBrowseRequest request);

   JSONObject resolveLibrarySourceRoot(EmbyUser user, RoseLibraryBrowseRequest request);

   RoseBindingResponse adminBinding(Long userId);

   void unbindExpiredUserIfBoundAsync(EmbyUser user);

   void attachBindings(List<EmbyUserResponse> users);
}
