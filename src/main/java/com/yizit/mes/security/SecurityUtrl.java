package com.yizit.mes.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by jack on 2017/12/13.
 */
public class SecurityUtrl {
    public static SecurityUser getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        return (SecurityUser) authentication.getPrincipal();
    }

    public static boolean isRoot() {
        return "root".equals(getUser().getUsername());
    }
}
