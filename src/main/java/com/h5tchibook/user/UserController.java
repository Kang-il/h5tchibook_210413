package com.h5tchibook.user;

import java.util.Date;	

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

	private Date date=new Date();
	
	@RequestMapping("/sign_in_view")
	public String userSignUpView(Model model) {
		
		model.addAttribute("currentTime",date.getTime());
		
		return "template/user_sign_layout";
	}
	
	@RequestMapping("/feed")
	public String mainFeed(Model model) {
		
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView","user/user_feed_section");	
		return "template/template_layout";
	}
}
