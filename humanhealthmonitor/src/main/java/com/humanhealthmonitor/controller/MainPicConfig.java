package com.humanhealthmonitor.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//新增加一个类用来添加虚拟路径映射
@Configuration
public class MainPicConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //windows下图片映射
        //registry.addResourceHandler("/images/**").addResourceLocations("file:H:/Java/IntellijProject2019/humanhealthmonitor/src/main/resources/static/images/");
        //linux下图片映射
        registry.addResourceHandler("/images/**").addResourceLocations("file:/root/SpringRun/images/");
    }
}
