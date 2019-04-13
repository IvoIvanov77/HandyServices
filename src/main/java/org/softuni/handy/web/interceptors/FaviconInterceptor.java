package org.softuni.handy.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String link = "https://cache-landingpages.services.handy.com/assets/pages/region/handy_logo-e5d858d96595ec001c5268a2d7a0f91800da2c7c2f963a5307154917289c347a.svg";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
