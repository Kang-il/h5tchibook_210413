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
		Boolean checkPassword=(Boolean)session.getAttribute("checkPassword");
		String uri=request.getRequestURI();
		
		logger.info(":::::::::::::::: URI :::::::::::::::::" +uri);
		
		// 1. /profile/edit/으로 시작하는 url이 아닌데 checkPassword에 값이 있는 경우 경우 잘못된 세션을 가지고 있음.
		//  -- 세션의 checkPassword 속성을 날려준다.
		if(user!=null && checkPassword!=null && !uri.startsWith("/profile/edit")) {
			session.removeAttribute("checkPassword");
		}
		// checkPassword가 null인데 /profile/edit 으로 시작하는 url로 접근하는 것을 막는다.
		if(user!= null && checkPassword==null && uri.startsWith("/profile/edit")) {
			//check_password 페이지로 리다이렉트 시킨다.
			response.sendRedirect("/profile/check_password_view/"+user.getLoginId());
			return false;
		}
		
		if (user==null && uri.startsWith("/profile")) {
			response.sendRedirect("/user/sign_in_view");
			session.invalidate();
			return false;
		}
		
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
		
		if(user==null && uri.startsWith("/feed")) {
			response.sendRedirect("/user/sign_in_view");
			return false;
		}

		
		return true;

	}
}
