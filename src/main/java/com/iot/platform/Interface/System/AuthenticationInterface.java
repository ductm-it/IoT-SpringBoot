package com.iot.platform.Interface.System;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public interface AuthenticationInterface {

    public static String staticGetJwt(HttpServletRequest request) {
        String bearerToken = AuthenticationInterface.staticGetBearerToken(request);
        if (bearerToken != null) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static String staticGetBearerToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public default String getBearerToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return AuthenticationInterface.staticGetBearerToken(request);
    }

    public default String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        return AuthenticationInterface.staticGetJwt(request);
    }

}