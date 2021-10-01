package com.h5tchibook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.h5tchibook.interceptor.PermissionInterceptror;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Autowired
	private PermissionInterceptror permissionInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(permissionInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/user/sign_out", "/static/**", "/error" );
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/upload_images/**")
		.addResourceLocations("File:///home/ec2-user/upload_images/");
	}
}
