package com.godfunc.paopao.inpercept;

import com.godfunc.paopao.annotation.Login;
import com.godfunc.paopao.config.JwtConfig;
import com.godfunc.paopao.constant.CommonConstant;
import com.godfunc.paopao.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author godfunc
 * @email godfunc@outlook.com
 */
@Component
@AllArgsConstructor
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private final JwtConfig jwtConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        Login annotation;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethod().getAnnotation(Login.class);
        } else {
            return true;
        }
        if (annotation == null) {
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1) {
            throw new UnauthorizedException();
        }
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                if (StringUtils.isNotBlank(token)) {
                    Claims claim = null;
                    try {
                        claim = jwtConfig.getTokenClaim(token);
                    } catch (Exception e) {
                        throw new UnauthorizedException();
                    }
                    if (claim == null || jwtConfig.isTokenExpired(claim.getExpiration())) {
                        throw new UnauthorizedException();
                    } else {
                        request.setAttribute(CommonConstant.USER_ID, claim.getSubject());
                        return true;
                    }
                } else {
                    throw new UnauthorizedException();
                }
            }
        }
        throw new UnauthorizedException();
    }
}
