package com.spring.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class HttpAspect {
   
	private final static Logger logger=LoggerFactory.getLogger(HttpAspect.class);
	
	@Pointcut("execution(public * com.spring.controller.HelloController.*(..))")
	public void log(){
		
	}
	
	@Before("log()")
	public void doBefore(JoinPoint joinPoint){
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		//url
		logger.info("URL={}",request.getRequestURL());
		//请求方法
		logger.info("method={}",request.getMethod());
		//请求IP
		logger.info("ip={}",request.getRemoteAddr());
		//获取request参数
		logger.info("params={}",request.getParameterMap());
		//
		logger.info("class_method={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
		//获取方法参数列表
		logger.info("args={}",joinPoint.getArgs());
	}
	
	@After("log()")
	public void doAfter(){
		logger.info("after......");
	}
	
	@AfterReturning(returning="object",pointcut="log()")
	public void afterReturning(Object object){
		logger.info("response={}",object.toString());
	}
	
	
}
