package com.softsync.zerock.config;

import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
=======

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

>>>>>>> de6ea33e1bae39fb8775b33592b6e545e3f56f35
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
<<<<<<< HEAD
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UsernameInterceptor());
=======
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/home/mit305/back/softsync/image/");
>>>>>>> de6ea33e1bae39fb8775b33592b6e545e3f56f35
    }
}
