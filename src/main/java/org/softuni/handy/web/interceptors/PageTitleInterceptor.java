package org.softuni.handy.web.interceptors;

import org.softuni.handy.web.anotations.PageTitle;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PageTitleInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {

        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        }
        if (handler instanceof HandlerMethod) {
            PageTitle methodAnnotation = ((HandlerMethod) handler).getMethodAnnotation(PageTitle.class);

            if (methodAnnotation != null) {
                modelAndView
                        .addObject("pageTitle", methodAnnotation.value());
            }
        }
    }
}
