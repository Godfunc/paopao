package com.godfunc.paopao.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * 异常处理器
 */
@ControllerAdvice
@Slf4j
public class GExceptionHandler {

	@ExceptionHandler(MissingRequestCookieException.class)
	public String handleMissingRequestCookieException(MissingRequestCookieException e){
		log.info(e.getMessage(), e);
		return "redirect:/user/login";
	}
}
