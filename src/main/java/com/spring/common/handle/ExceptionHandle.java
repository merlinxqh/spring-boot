package com.spring.common.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.common.exception.ServiceException;
import com.spring.common.result.ResultWrapper;

/**
 * 异常捕获
 * @author leo
 *
 */
@ControllerAdvice
public class ExceptionHandle {
   
	
	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public ResultWrapper<Exception> handle(Exception e){
		if(e instanceof ServiceException){
			//TODO 
		}
		
		return new ResultWrapper<>(1, e.getMessage());
	}
}

