package com.una.embyhub.config.handler;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.AdminMenuPermissionService;
import com.una.embyhub.service.EmbyUserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StpInterfaceImpl implements StpInterface {
   @Autowired
   private EmbyUserService embyUserService;
   @Autowired
   private AdminMenuPermissionService adminMenuPermissionService;

   @Override
   public List<String> getPermissionList(Object loginId, String loginType) {
      List<String> list = new ArrayList<>();
      EmbyUser embyUser = new LambdaQueryChainWrapper<>(this.embyUserService.getBaseMapper())
         .eq(EmbyUser::getId, loginId)
         .eq(EmbyUser::getUserStatus, Integer.valueOf(0))
         .eq(EmbyUser::getIsAdmin, Integer.valueOf(1))
         .one();
      if (embyUser != null) {
         list.add("admin");
         this.adminMenuPermissionService.resolveMenuKeys(embyUser).stream().map(key -> "admin-menu:" + key).forEach(list::add);
         return list;
      } else {
         return list;
      }
   }

   @Override
   public List<String> getRoleList(Object loginId, String loginType) {
      return new ArrayList<>();
   }
}
