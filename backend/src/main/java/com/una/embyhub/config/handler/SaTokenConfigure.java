package com.una.embyhub.config.handler;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
   private final AdminMenuAuthorizationInterceptor adminMenuAuthorizationInterceptor;

   public SaTokenConfigure(AdminMenuAuthorizationInterceptor adminMenuAuthorizationInterceptor) {
      this.adminMenuAuthorizationInterceptor = adminMenuAuthorizationInterceptor;
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
         .addPathPatterns("/**")
         .excludePathPatterns(
            "/embyUser/login",
            "/embyUser/insertUserCard",
            "/tmdb/trendingAllImages",
            "/emby/notifier",
            "/embyUser/registeredUser",
            "/embyUser/userExist",
            "/embyUser/enableRegistration",
            "/embyUser/embyUserNameExist",
            "/tmdb/trendingAllImagesPopular",
            "/avatars/**",
            "/wechat/bot",
            "/wechat/bot/",
            "/embyUser/registeredByInvitation",
            "/sysNotice/publicExternal",
            "/systemConfig/isEnabled",
            "/paymentAccount/config",
            "/paymentAccount/orders",
            "/paymentAccount/orders/query",
            "/paymentAccount/easypay/notify",
            "/telegramAuth/loginSession",
            "/telegramAuth/checkLogin",
            "/telegramAuth/login"
         );
      registry.addInterceptor(this.adminMenuAuthorizationInterceptor).addPathPatterns("/**");
   }
}
