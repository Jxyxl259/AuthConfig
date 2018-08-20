package com.yaic.auth.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaic.auth.external.common.ResultMessage;
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResultMessage defaultExceptionHandler(Exception e) {
		e.printStackTrace();
		logger.error(null,e);
        //System.out.println("处理Exception");
		return null;
	}

}
