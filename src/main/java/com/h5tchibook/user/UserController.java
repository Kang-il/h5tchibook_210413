package com.h5tchibook.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

	//로그인
	@RequestMapping("/sign_in_view")
	public String userSignUpView(Model model) {
		Date date =new Date();
		model.addAttribute("currentTime",date.getTime());
		return "template/user_sign_layout";
	}
	
	//로그아웃
	@RequestMapping("/sign_out")
	public String signOut(Model model,HttpServletRequest request) {
		HttpSession session=request.getSession();
		session.invalidate();
		return "redirect:/user/sign_in_view";
	}
	
}
