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
        String link = "https://cache-landingpages.services.handy.com/assets/favicon-465fb4e7d7152f5abb6b2ecf7330aefa8a067f174110dcd80324b56aeedfea59.ico";

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
