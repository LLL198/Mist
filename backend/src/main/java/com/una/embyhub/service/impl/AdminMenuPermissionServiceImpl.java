package com.una.embyhub.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.una.embyhub.config.common.enums.AdminMenuKey;
import com.una.embyhub.config.common.enums.ResponseStatusEnum;
import com.una.embyhub.config.common.exception.BizException;
import com.una.embyhub.mapper.AdminMenuPermissionMapper;
import com.una.embyhub.mapper.EmbyUserMapper;
import com.una.embyhub.model.dto.response.adminmenu.AdminMenuCatalogResponse;
import com.una.embyhub.model.dto.response.adminmenu.AdminMenuPermissionAssignmentResponse;
import com.una.embyhub.model.entity.AdminMenuPermission;
import com.una.embyhub.model.entity.EmbyUser;
import com.una.embyhub.service.AdminMenuPermissionService;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminMenuPermissionServiceImpl extends ServiceImpl<AdminMenuPermissionMapper, AdminMenuPermission> implements AdminMenuPermissionService {
   private final EmbyUserMapper embyUserMapper;

   public AdminMenuPermissionServiceImpl(EmbyUserMapper embyUserMapper) {
      this.embyUserMapper = embyUserMapper;
   }

   @Override
   public List<AdminMenuCatalogResponse> listCatalog() {
      AdminMenuKey[] values = AdminMenuKey.values();
      return IntStream.range(0, values.length).mapToObj(index -> {
         AdminMenuKey item = values[index];
         return new AdminMenuCatalogResponse(item.getKey(), item.getTitle(), item.getSection(), item.getRoute(), index);
      }).toList();
   }

   @Override
   public AdminMenuPermissionAssignmentResponse getAssignment(Long adminUserId) {
      this.assertCurrentPrimaryAdmin();
      EmbyUser target = this.requireDelegatedAdministrator(adminUserId);
      return new AdminMenuPermissionAssignmentResponse(target.getId(), target.getEmbyUserName(), this.resolveMenuKeys(target));
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public AdminMenuPermissionAssignmentResponse replaceAssignment(Long adminUserId, Collection<String> menuKeys) {
      this.assertCurrentPrimaryAdmin();
      EmbyUser target = this.requireDelegatedAdministrator(adminUserId);
      LinkedHashSet<String> normalized = this.normalizeMenuKeys(menuKeys);
      this.removeAssignments(adminUserId);
      Date now = new Date();

      for (String menuKey : normalized) {
         AdminMenuPermission permission = new AdminMenuPermission();
         permission.setAdminUserId(adminUserId);
         permission.setMenuKey(menuKey);
         permission.setCreateDatetime(now);
         this.save(permission);
      }

      return new AdminMenuPermissionAssignmentResponse(target.getId(), target.getEmbyUserName(), List.copyOf(normalized));
   }

   @Override
   public List<String> resolveMenuKeys(EmbyUser user) {
      if (user == null || !Integer.valueOf(1).equals(user.getIsAdmin())) {
         return List.of();
      } else if (Integer.valueOf(1).equals(user.getIsPrimaryAdmin())) {
         return AdminMenuKey.allKeys();
      } else {
         Set<String> assigned = this.list(new LambdaQueryWrapper<AdminMenuPermission>().eq(AdminMenuPermission::getAdminUserId, user.getId()))
            .stream()
            .map(AdminMenuPermission::getMenuKey)
            .collect(Collectors.toSet());
         return AdminMenuKey.allKeys().stream().filter(assigned::contains).toList();
      }
   }

   @Override
   public boolean hasAnyMenuPermission(Long adminUserId, Collection<String> menuKeys) {
      if (adminUserId != null && menuKeys != null && !menuKeys.isEmpty()) {
         List<String> validKeys = menuKeys.stream().filter(key -> AdminMenuKey.fromKey(key).isPresent()).distinct().toList();
         return validKeys.isEmpty()
            ? false
            : this.count(
                  new LambdaQueryWrapper<AdminMenuPermission>().eq(AdminMenuPermission::getAdminUserId, adminUserId)
                     .in(AdminMenuPermission::getMenuKey, validKeys)
               )
               > 0L;
      } else {
         return false;
      }
   }

   @Transactional(
      rollbackFor = {Exception.class}
   )
   @Override
   public void removeAssignments(Long adminUserId) {
      if (adminUserId != null) {
         this.remove(new LambdaQueryWrapper<AdminMenuPermission>().eq(AdminMenuPermission::getAdminUserId, adminUserId));
      }
   }

   @Override
   public void assertCurrentPrimaryAdmin() {
      if (!StpUtil.isLogin()) {
         throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
      } else {
         EmbyUser current = this.embyUserMapper.selectById(Long.valueOf(StpUtil.getLoginIdAsLong()));
         if (current == null
            || !Integer.valueOf(0).equals(current.getUserStatus())
            || !Integer.valueOf(1).equals(current.getIsAdmin())
            || !Integer.valueOf(1).equals(current.getIsPrimaryAdmin())) {
            throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
         }
      }
   }

   private EmbyUser requireDelegatedAdministrator(Long adminUserId) {
      if (adminUserId == null) {
         throw new BizException(ResponseStatusEnum.USER_ID_NOT_NULl);
      } else {
         EmbyUser target = this.embyUserMapper.selectById(adminUserId);
         if (target == null || !Integer.valueOf(0).equals(target.getUserStatus()) || !Integer.valueOf(1).equals(target.getIsAdmin())) {
            throw new BizException(ResponseStatusEnum.USER_NOT_EXIST);
         } else if (Integer.valueOf(1).equals(target.getIsPrimaryAdmin())) {
            throw new BizException(ResponseStatusEnum.PERMISSION_DENIED);
         } else {
            return target;
         }
      }
   }

   private LinkedHashSet<String> normalizeMenuKeys(Collection<String> menuKeys) {
      Collection<String> source = (Collection<String>)(menuKeys == null ? List.of() : menuKeys);
      LinkedHashSet<String> requested = source.stream()
         .filter(Objects::nonNull)
         .map(String::trim)
         .filter(keyx -> !keyx.isEmpty())
         .collect(Collectors.toCollection(LinkedHashSet::new));

      for (String key : requested) {
         if (AdminMenuKey.fromKey(key).isEmpty()) {
            throw new BizException(ResponseStatusEnum.BAD_REQUEST.getCode(), "包含无效菜单权限");
         }
      }

      LinkedHashSet<String> ordered = new LinkedHashSet<>();

      for (String keyx : AdminMenuKey.allKeys()) {
         if (requested.contains(keyx)) {
            ordered.add(keyx);
         }
      }

      return ordered;
   }
}
