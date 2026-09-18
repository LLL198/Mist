package com.una.embyhub.config.common.enums;

import lombok.Generated;

public enum ResponseStatusEnum {
   FORBIDDEN(403, "Forbidden"),
   UNAUTHORIZED(401, "系统未登录，请先登录"),
   SUCCESS(200, "OK"),
   BAD_REQUEST(400, "Bad Request"),
   SYSTEM_ERROR(500, "系统异常错误"),
   NOT_EXIST(404, "请求路径不存在"),
   PERMISSION_DENIED(50030, "权限不足"),
   USER_NOT_EXIST(410, "用户不存在"),
   USER_EXIST(411, "用户已存在 ,请重新生成"),
   CARD_NOT_EXIST(412, "卡密不存在"),
   CARD_USED(413, "卡密已使用"),
   USER_DISABLED(414, "用户已禁用"),
   PASSWORD_CONTAIN_CONTINUOUS_CHAR(415, "用户密码不能包含连续的数字"),
   BIZ_CARD_PASSWORD_ERROR(416, "卡密密码错误"),
   REQUEST_LIST_ALREADY_EXISTS(417, "该影片已存在求片列表中，请勿重复添加！"),
   REQUEST_LIST_NOT_EXISTS(418, "求片信息不存在"),
   USER_DISABLE_FAILED(419, "禁用用户失败，可能emby不存在此用户，请直接删除用户信息"),
   USER_ENABLE_ERROR(420, "用户启用失败，可能emby用户已删除"),
   REQUEST_LIST_ALREADY_STORE(421, "存在已入库的数据，无法批量提交"),
   USER_ID_NOT_NULl(423, "用户id不能为空"),
   PASSWORD_LENGTH_ERROR(424, "密码长度必须在6-30之间"),
   REQUEST_LIST_ALREADY_STOCK(425, "该影片已入库"),
   CONFIG_KEY_NOT_EXIST(426, "当前配置不存在，请勿重复添加！"),
   PASSWORD_ERROR(427, "密码错误，请重新输入"),
   USER_SYNC_ERROR(428, "同步用户失败"),
   NULLBR_TYPE_ERROR(429, "nullbr类型错误"),
   NULLBR_ENABLED_ERROR(430, "nullbr未启用"),
   NULLBR_CONFIG_ERROR(430, "nullbr配置错误"),
   NOTIFY_CHANNEL_ICON_TYPE_EXIST(431, "通知渠道已存在"),
   BIZ_REQUEST_PACKAGES_CARD_PASSWORD_ERROR(432, "求片卡密密码错误"),
   REQUEST_LIST_PACKAGE_NOT_ENOUGH(433, "求片次数不足，请先购买套餐"),
   USER_REGISTER_FAILED(434, "页面注册未开启"),
   USER_NAME_NOT_ALLOWED(435, "用户名无法使用"),
   USER_NAME_EXIST(436, "用户名已存在"),
   NOTIFY_TEMPLATE_CODE_EXIST(437, "通知模板编码已存在"),
   NOTIFY_TEMPLATE_CODE_EMPTY(438, "通知模板编码不能为空"),
   EMBY_SERVER_NOT_FOUND(439, "Emby服务器信息不存在"),
   EMBY_SERVER_NOT_CONFIGURED(440, "未配置启用的Emby服务器"),
   AVATAR_UPLOAD_FAILED(441, "头像上传失败"),
   AVATAR_FILE_EMPTY(442, "请上传头像文件"),
   EMBY_INFO_DISABLED(443, "服务器已关闭"),
   EMBY_SERVER_EXISTS(444, "服务器已存在"),
   PLEASE_USE_CORRECT_CARD_KEY(445, "请使用正确卡密"),
   EMBY_USER_NOT_EXIST(446, "Emby用户不存在"),
   EMBY_EXCEPTIION(447, "Emby服务器异常"),
   MOVIEPILOT_FORBIDDEN(448, "moviepilot 登录已过期"),
   MOVIEPILOT_NOT_CONFIGURED(449, "未配置moviepilot"),
   SERVER_CONFIG_ERROR(450, "免费版暂时关闭"),
   REQUEST_LIST_REJECTED_EXISTS(451, "该影片已被拒绝，无法重复提交"),
   SYNC_SERVER_ID_CONFLICT(452, "源服务器与目标服务器不能相同"),
   INVITATION_CODE_NOT_FOUND(454, "邀请码不存在"),
   INVITATION_CODE_USED(455, "邀请码已使用"),
   INVITATION_CODE_SERVER_REQUIRED(456, "请选择要生成邀请码的服务器"),
   INVITATION_CODE_EXPIRED(457, "邀请码已过期"),
   INVITATION_CODE_USAGE_EXCEEDED(458, "邀请码已达最大使用次数"),
   HOST_LINE_NOT_FOUND(459, "主机线路不存在"),
   HOST_LINE_DUPLICATE(460, "同一 Emby 服务器下线路名称或协议+域名+端口已存在"),
   HOST_LINE_EMBY_INFO_ID_EMPTY(461, "Emby服务器ID不能为空"),
   HOST_LINE_LINE_NAME_EMPTY(462, "线路名称不能为空"),
   HOST_LINE_PROTOCOL_EMPTY(463, "线路协议不能为空"),
   HOST_LINE_DOMAIN_EMPTY(464, "线路域名不能为空"),
   HOST_LINE_PORT_EMPTY(465, "线路端口不能为空"),
   HOST_LINE_PORT_INVALID(466, "端口号需要在1-65535之间"),
   INVITATION_REGISTER_DISABLED(467, "邀请码注册未开启"),
   CARD_REGISTER_DISABLED(468, "卡密注册未开启"),
   TELEGRAM_REGISTER_DISABLED(470, "Telegram私聊注册未开启"),
   PLAYBACK_REPORTING_NOT_INSTALLED(469, "Playback Reporting Emby插件异常，请检查是否已安装该插件，已安装请忽略"),
   MULTIPLE_SERVER_MATCH(471, "用户存在于多个服务器，请选择服务器登录"),
   POINTS_NOT_ENOUGH(473, "积分不足"),
   PRODUCT_NOT_EXIST(474, "商品不存在或下架"),
   REQUEST_LIST_NOT_RELEASED(475, "该影片尚未上映，暂不支持求片"),
   CURRENT_ADMIN_CANNOT_CANCEL(476, "不能取消当前登录用户的管理员身份"),
   CARD_USED_OR_NOT_EXIST(477, "卡密已使用或不存在"),
   PRIMARY_ADMIN_PROTECTED(478, "最高管理员不能被禁用、删除或变更身份"),
   CURRENT_USER_CANNOT_DELETE(479, "不能删除当前登录用户"),
   TOO_MANY_REQUESTS(480, "请求过于频繁，请稍后再试");

   private Integer code;
   private String msg;

   private ResponseStatusEnum(Integer code, String msg) {
      this.code = code;
      this.msg = msg;
   }

   @Generated
   @Override
   public String toString() {
      return "ResponseStatusEnum." + this.name() + "(code=" + this.getCode() + ", msg=" + this.getMsg() + ")";
   }

   @Generated
   public Integer getCode() {
      return this.code;
   }

   @Generated
   public String getMsg() {
      return this.msg;
   }
}
