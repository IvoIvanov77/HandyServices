package org.softuni.handy.config;

import org.softuni.handy.web.interceptors.FaviconInterceptor;
import org.softuni.handy.web.interceptors.PageTitleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMvcConfiguration implements WebMvcConfigurer {

    private final PageTitleInterceptor titleInterceptor;
    private final FaviconInterceptor faviconInterceptor;

    public ApplicationWebMvcConfiguration(PageTitleInterceptor titleInterceptor, FaviconInterceptor faviconInterceptor) {
        this.titleInterceptor = titleInterceptor;
        this.faviconInterceptor = faviconInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
        registry.addInterceptor(this.faviconInterceptor);
    }


}
