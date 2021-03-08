package com.oengal.oengal.agenda;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.WebUtils;

@Component
@Slf4j
@Aspect
public class AgendaAdvice {

  @Around("@annotation(com.oengal.oengal.agenda.UserIdFromCookie)")
  public Object getUserIdFromCookie(ProceedingJoinPoint joinPoint){
    HttpServletRequest req = ((ServletRequestAttributes) Objects
        .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    /* null check logic need to be added */
//    String userId = WebUtils.getCookie(req, "userid").getValue();
    String userId = "FFF";

    Map<String, String> pathVariable = (Map<String, String>) req.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    Long agendaId =  Long.parseLong(pathVariable.get("agendaId"));
    log.error("!!!!!!!!!! agednaId = " + agendaId + ", uesrId = " + userId);
    Object resultObj = null;
    try {
      resultObj = joinPoint.proceed(new Object[] { userId, agendaId });
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return resultObj;

  }

  @Before("execution(* com.oengal.oengal.agenda.*Controller.*(..))")
  public void setUserIdToMDC(JoinPoint joinPoint) {
    CodeSignature s = (CodeSignature) joinPoint.getSignature();
    s.getParameterTypes();
    HttpServletRequest req =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    if(req.getMethod().equals("POST")) {
      Agenda agenda = (Agenda) joinPoint.getArgs()[0];
      log.error("#####" + Arrays.toString(s.getParameterTypes()));
      String userId = null;
      if(agenda.getUserId() == null || agenda.getUserId().toString().equals("")) {
        userId = "N/A";
      } else {
        userId = agenda.getUserId().toString();
      }
      MDC.put("USER_ID", userId);
    } else {
      MDC.put("USER_ID", "N/A");
    }

  }
}
