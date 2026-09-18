package com.una.embyhub.config.handler;

import cn.dev33.satoken.stp.StpUtil;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.AdminMenuPermissionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminMenuAuthorizationInterceptor implements HandlerInterceptor {
   private final AdminMenuPolicyRegistry policyRegistry;
   private final AdminMenuPermissionService adminMenuPermissionService;
   private final EmbyUserMapper embyUserMapper;

   public AdminMenuAuthorizationInterceptor(
      AdminMenuPolicyRegistry policyRegistry, AdminMenuPermissionService adminMenuPermissionService, EmbyUserMapper embyUserMapper
   ) {
      this.policyRegistry = policyRegistry;
      this.adminMenuPermissionService = adminMenuPermissionService;
      this.embyUserMapper = embyUserMapper;
   }

   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
      if (handler instanceof HandlerMethod handlerMethod) {
         AdminMenuPolicyRegistry.Policy policy = this.policyRegistry.resolve(handlerMethod);
         if (!policy.enforced()) {
            return true;
         } else {
            EmbyUser current = this.embyUserMapper.selectById(Long.valueOf(StpUtil.getLoginIdAsLong()));
            if (current == null || !Integer.valueOf(0).equals(current.getUserStatus())) {
               throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
            } else if (!Integer.valueOf(1).equals(current.getIsAdmin())) {
               if (policy.allowNonAdministrator()) {
                  return true;
               } else {
                  throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
               }
            } else if (Integer.valueOf(1).equals(current.getIsPrimaryAdmin())) {
               return true;
            } else if (!policy.requiredMenuKeys().isEmpty() && this.adminMenuPermissionService.hasAnyMenuPermission(current.getId(), policy.requiredMenuKeys())
               )
             {
               return true;
            } else {
               throw new BizException(ResponseStatusEnum.PERMISSION_DENIED.getCode(), "无权访问该菜单功能");
            }
         }
      } else {
         return true;
      }
   }
}
