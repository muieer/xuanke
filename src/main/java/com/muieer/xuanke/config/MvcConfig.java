package com.muieer.xuanke.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {


    //视图控制器映射配置
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 添加映射器，不经过controller
        registry.addViewController("/index").setViewName("index");
        //跳转到登陆页面
        registry.addViewController("/home").setViewName("login");
        registry.addViewController("/").setViewName("index");
    }
}
