package com.yunmel.frame.common.log;
//package com.yunmel.common.log;
//
//import java.lang.reflect.Method;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.joda.time.DateTime;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.yunmel.base.model.SysLog;
//import com.yunmel.base.model.SysUser;
//import com.yunmel.base.service.SysLogService;
//import com.yunmel.common.utils.SysUserUtils;
//import com.yunmel.commons.utils.IPUtils;
//import com.yunmel.commons.utils.StrUtils;
//import com.yunmel.consts.Constant;
//
//@Aspect
//@Component
//public class LogAdvice2 {
//	@Resource
//	private SysLogService sysLogService;
//	private static final Logger LOG = LoggerFactory.getLogger("LOG");
//	
//	@Pointcut("@annotation(com.yunmel.common.log.Log)")
//	public void logAspect() {
//	}
//
//	@Pointcut("execution(* com.yunmel..*Service.*(..))")
//	public void throwingAspect(){
//	}
//	
//	/**
//	 * 方法执行后的日志
//	 * 	主要记录用户的操作日志
//	 * 		如: 添加 、修改、删除等操作
//	 * @param joinPoint
//	 */
//	@After("logAspect()")
//	public void doAfter(JoinPoint joinPoint) {
//		HttpServletRequest request = SysUserUtils.getCurRequest();
//		LogBean bean = getLogBean(joinPoint);
//		SysLog sysLog = new SysLog();
//		sysLog.setParams(bean.params);
//		sysLog.setRequestUri(bean.target + " $$ " + bean.method);
//		sysLog.setRemoteAddr(IPUtils.getClientAddress(request));
//		sysLog.setType(Constant.LOGTYPE_ACCESS);
//		sysLog.setMethod(request.getMethod());
//		SysUser user = SysUserUtils.getCacheLoginUser();
//		String opt = bean.opt;
//		if(StrUtils.isBlank(opt)){
//			String method = bean.method;
//			if(method.startsWith("save")){
//				opt = "保存";
//			}else if(method.startsWith("update")){
//				opt = "修改";
//			}else if(method.startsWith("delete") || method.startsWith("del")){
//				opt = "删除";
//			}else{
//				opt = "未知操作";
//			}
//		}
//		String moudel = bean.moudel;
//		if(StrUtils.isBlank(moudel)){
//			String target = bean.target;
//			int offset = target.lastIndexOf(".") + 1;
//			moudel = StrUtils.substring(target, offset, target.length() - 7);
//		}
//		sysLog.setDescription(String.format("用户[%s]于[%s][%s]了[%s]", user.getName(),
//				DateTime.now().toString("yyyy-MM-dd HH:mm:ss"),opt,moudel));
//		sysLogService.saveSysLog(sysLog);
//	}
//
//	@AfterThrowing(value="throwingAspect()",throwing="e")
//	public void doAfterThrowing(JoinPoint joinPoint,Throwable e){
//		saveLog(joinPoint, e);
//	}
//	
//	protected void saveLog(JoinPoint joinPoint,Throwable e){
//		try {
//			HttpServletRequest request = SysUserUtils.getCurRequest();
//			SysLog log = new SysLog();
//			// 判断参数
//			if (joinPoint.getArgs() != null) {
//				StringBuffer rs = dealArguments(joinPoint);
//				log.setParams(rs.toString());
//			}
//			log.setRemoteAddr(IPUtils.getClientAddress(request));
//			log.setRequestUri(request.getRequestURI());
//			log.setMethod(request.getMethod());
//			log.setUserAgent(request.getHeader("user-agent"));
//			log.setException(e==null?null:e.toString());
//			log.setType(Constant.LOGTYPE_EXCEPTION);
//			Method m = ((MethodSignature) joinPoint.getSignature()).getMethod();
//			Log sclog = m.getAnnotation(Log.class);
//			if (sclog != null) log.setDescription(sclog.description());
//			//log保存到数据库
//			sysLogService.saveSysLog(log);
//		} catch (Exception ex) {
//			LOG.error(ex.getMessage());
//		}
//	}
//
//	private StringBuffer dealArguments(JoinPoint joinPoint) {
//		StringBuffer rs = new StringBuffer();
//		for (int i = 0, len = joinPoint.getArgs().length; i < len; i++) {
//			Object info = joinPoint.getArgs()[i];
//			if (info != null) {
//				String paramType = info.getClass().getSimpleName();
//				rs.append("[参数" + (i + 1) + "，类型:" + paramType + "，值:" + info.toString() + "]");
//			} else {
//				rs.append("[参数" + (i + 1) + "，值:null]");
//			}
//		}
//		return rs;
//	}
//
//	
//	
//	/**
//	 * 
//	 * @param joinPoint
//	 *            切点
//	 * @return 方法描述
//	 * @throws Exception
//	 */
//	public LogBean getLogBean(JoinPoint joinPoint) {
//		Class<?> targetClass;
//		LogBean log = new LogBean();
//		try {
//			String targetName = joinPoint.getTarget().getClass().getName();
//			log.target = targetName;
//			String methodName = joinPoint.getSignature().getName();
//			log.method = methodName;
//			Object[] arguments = joinPoint.getArgs();
//			if (arguments != null) {
//				log.params = dealArguments(joinPoint).toString();
//			}
//			
//			targetClass = Class.forName(targetName);
//			Method[] methods = targetClass.getMethods();
//			for (Method method : methods) {
//				if (method.getName().equals(methodName)) {
//					Class<?>[] clazzs = method.getParameterTypes();
//					if (null != arguments && clazzs.length == arguments.length) {
//						Log aLog = method.getAnnotation(Log.class);
//						log.opt = aLog.opt();
//						log.moudel = aLog.moudel();
//						break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			LOG.error("get log bean error : {}",e.getMessage());
//		}
//		return log;
//	}
//}
//
//class LogBean {
//	String moudel;
//	String opt;
//	String target;
//	String params;
//	String method;
//}
