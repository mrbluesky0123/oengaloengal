package com.oengal.oengal.agenda;

import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

@Component
@Slf4j
@Aspect
public class AgendaAdvice {

  @Before("@annotation(com.oengal.oengal.agenda.UserIdFromCookie)")
  public String getUserIdFromCookie(JoinPoint joinPoint){
    HttpServletRequest req = ((ServletRequestAttributes) Objects
        .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    /* null check logic need to be added */
    String userId = WebUtils.getCookie(req, "userid").getValue();



  }

}
