package com.una.embyhub.config.handler;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.una.embyhub.model.entity.EmbyUser;
import java.util.Date;
import lombok.Generated;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
   @Generated
   private static final Logger log = LoggerFactory.getLogger(MyMetaObjectHandler.class);

   @Override
   public void insertFill(MetaObject metaObject) {
      if (metaObject.hasSetter("identityGroupId")) {
         this.strictInsertFill(metaObject, "identityGroupId", Long.class, Long.valueOf(IdWorker.getId()));
      }

      try {
         EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
         this.strictInsertFill(metaObject, "createUserId", Long.class, embyUser.getId());
         this.strictInsertFill(metaObject, "createUserName", String.class, embyUser.getEmbyUserName());
         this.strictInsertFill(metaObject, "createDatetime", Date.class, new Date());
      } catch (Exception var3) {
         this.strictInsertFill(metaObject, "createUserId", Long.class, null);
         this.strictInsertFill(metaObject, "createUserName", String.class, "游客使用卡密激活");
         this.strictInsertFill(metaObject, "createDatetime", Date.class, new Date());
      }
   }

   @Override
   public void updateFill(MetaObject metaObject) {
      try {
         EmbyUser embyUser = (EmbyUser)StpUtil.getSession().get("user");
         this.strictUpdateFill(metaObject, "updateUserName", String.class, embyUser.getEmbyUserName());
         this.strictUpdateFill(metaObject, "updateUserId", Long.class, embyUser.getId());
         this.strictUpdateFill(metaObject, "updateDatetime", Date.class, new Date());
      } catch (Exception var3) {
         this.strictUpdateFill(metaObject, "updateUserName", String.class, "游客使用卡密激活");
         this.strictUpdateFill(metaObject, "updateUserId", Long.class, null);
         this.strictUpdateFill(metaObject, "updateDatetime", Date.class, new Date());
      }
   }
}
