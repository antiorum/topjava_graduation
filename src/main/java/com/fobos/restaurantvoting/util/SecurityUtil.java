package com.fobos.restaurantvoting.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
