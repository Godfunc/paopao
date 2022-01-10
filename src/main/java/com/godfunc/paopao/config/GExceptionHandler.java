package com.godfunc.paopao.config;

import com.godfunc.paopao.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author godfunc
 * @email godfunc@outlook.com
 * 异常处理器
 */
@ControllerAdvice
@Slf4j
public class GExceptionHandler {

    @ExceptionHandler(MissingRequestCookieException.class)
    public String handleMissingRequestCookieException(MissingRequestCookieException e) {
        log.info(e.getMessage(), e);
        return "redirect:/user/login";
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        log.error("系统异常", e);
        Map<String, String> model = new LinkedHashMap<>();
        model.put("msg", "系统错误");
        return new ModelAndView("error", model);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException(UnauthorizedException e) {
        return "redirect:/user/login";
    }
}
