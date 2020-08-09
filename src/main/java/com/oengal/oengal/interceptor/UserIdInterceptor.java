package com.oengal.oengal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Slf4j
@Component
public class UserIdInterceptor extends HandlerInterceptorAdapter {

  private final static String REQUEST_POST = "POST";

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    if(request.getMethod().equals(REQUEST_POST)) {
      MDC.put("USER_ID", "N/A");
    }
    log.info("FUCCCCCCCCKCKCKCKCKCK");
    return true;
  }

}
