package com.h5tchibook.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.h5tchibook.user.model.User;

@Component
public class PermissionInterceptror implements HandlerInterceptor{
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler )
			throws Exception {
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		String uri=request.getRequestURI();
		
		logger.info(":::::::::::::::: URI :::::::::::::::::" +uri);
		
		if(user!=null && uri.startsWith("/user")) {
			response.sendRedirect("/timeline/user_timeline_view");
			return false;
		}
		
		if(user==null && uri.startsWith("/post")) {
			response.sendRedirect("/user/sign_in_view");
			return false;
		}
		
		if(user==null && uri.startsWith("/timeline")) {
			response.sendRedirect("/user/sign_in_view");
			return false;
		}
		
		
		return true;

	}
}
