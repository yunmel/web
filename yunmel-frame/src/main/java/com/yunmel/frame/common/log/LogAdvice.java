package com.yunmel.frame.common.log;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yunmel.commons.utils.IPUtils;
import com.yunmel.frame.sys.model.SysLog;
import com.yunmel.frame.sys.service.SysLogService;

@Aspect
@Component
public class LogAdvice
{
  private static Logger LOGGER = LoggerFactory.getLogger(LogAdvice.class);

  @Resource
  private SysLogService sysLogService;

  @Around("within(@org.springframework.stereotype.Controller *)")
  public Object recordSysLog(ProceedingJoinPoint point) throws Throwable
  {
    String strMethodName = point.getSignature().getName();
    String strClassName = point.getTarget().getClass().getName();
    Object[] params = point.getArgs();
    StringBuffer bfParams = new StringBuffer();
    Enumeration<String> paraNames = null;
    HttpServletRequest request = null;
    if (params != null && params.length > 0)
    {
      request =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      paraNames = request.getParameterNames();
      String key;
      String value;
      while (paraNames.hasMoreElements())
      {
        key = paraNames.nextElement();
        value = request.getParameter(key);
        bfParams.append(key).append("=").append(value).append("&");
      }
      if (StringUtils.isBlank(bfParams))
      {
        bfParams.append(request.getQueryString());
      }
    }

    String strMessage =
        String.format("[类名]:%s,[方法]:%s,[参数]:%s", strClassName, strMethodName, bfParams.toString());
    LOGGER.info(strMessage);
    if (isWriteLog(strMethodName))
    {
      try
      {
        Subject currentUser = SecurityUtils.getSubject();
        PrincipalCollection collection = currentUser.getPrincipals();
        if (null != collection)
        {
          String loginName = collection.getPrimaryPrincipal().toString();
          SysLog log = new SysLog();
          log.setMethod(strMethodName);
          if(null != request){
            log.setRemoteAddr(IPUtils.getClientAddress(request));
            log.setRequestUri(request.getRequestURI());
            log.setUserAgent(request.getHeader("user-agent"));
          }
          log.setParams(bfParams.toString());
          log.setCreateBy(loginName);
          log.setDescription(strMessage);
          sysLogService.insertSelective(log);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }

    return point.proceed();
  }

  private boolean isWriteLog(String method)
  {
    String[] pattern = {"login", "logout","save", "add", "edit", "delete", "grant"};
    for (String s : pattern)
    {
      if (method.indexOf(s) > -1)
      {
        return true;
      }
    }
    return false;
  }
}
