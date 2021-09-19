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
		
		//profile/edit_user_info_view 가 아닌데 checkPassword 가ㅣ true인 경우 잘못된 세션을 가지고 있음.
		
		if(checkPassword!=null) {
			if(!uri.startsWith("/profile/edit_user_info_view") && checkPassword!=true) {
				if(user==null) {
					response.sendRedirect("/user/sign_in_view");
					session.removeAttribute("checkPassword");
					return false;
				}else {
					session.removeAttribute("checkPassword");
				}
			}
			
			if(user!=null && uri.startsWith("/profile/edit_user_info_view") && checkPassword!=true) {
				response.sendRedirect("/profile/check_password_view/"+user.getLoginId());
				return false;
			}
		}
		
		if (user==null && uri.startsWith("/profile/edit_user_info_view")) {
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
