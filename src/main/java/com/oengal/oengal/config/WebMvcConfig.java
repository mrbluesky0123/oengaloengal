package com.oengal.oengal.config;

import com.oengal.oengal.interceptor.UserIdInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final UserIdInterceptor interceptor;

  @Autowired
  public WebMvcConfig(UserIdInterceptor interceptor) {
    this.interceptor = interceptor;
  }

//  public void addInterceptors(InterceptorRegistry registry) {
//    registry.addInterceptor(interceptor)
//        .addPathPatterns("/**")
//        .excludePathPatterns("/user/**");
//  }
}