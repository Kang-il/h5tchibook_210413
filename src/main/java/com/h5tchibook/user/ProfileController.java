package com.h5tchibook.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.h5tchibook.alert.bo.AlertTimeLineBO;
import com.h5tchibook.alert.model.AlertTimeLineView;
import com.h5tchibook.user.model.User;




@RequestMapping("/profile")
@Controller
public class ProfileController {
	
	@Autowired
	private AlertTimeLineBO alertTimeLineBO;
	
	@RequestMapping("/check_password_view/{feedOwnerLoginId}")
	public String checkPasswordView(Model model
								   ,HttpServletRequest request
								   ,@PathVariable("feedOwnerLoginId") String feedOwnerLoginId) {
		
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		Date date=new Date();
		if(user!=null) {
			if(user.getLoginId().equals(feedOwnerLoginId)) {//본인피드인 경우에만 접근 가능하도록 하기
				List<AlertTimeLineView> alertList=alertTimeLineBO.getAlertTimelineViewByUserId(user.getId());
				model.addAttribute("alertList",alertList);
				model.addAttribute("userView","user/user_check_password_section");
				model.addAttribute("currentTime",date.getTime());
			}else {//본인이 아닌경우 본인의 피드로 리다이렉트 시킬것
				return "redirect:/feed/"+user.getLoginId();
			}
		}else {
			return "redirect:/user/sign_in_view";
		}

		return "template/template_layout";
	}
	
	@RequestMapping("/edit/user_info_view/{userLoginId}")
	public String editUserInfoView(Model model
								   ,HttpServletRequest request
								   ,@PathVariable("userLoginId") String userLoginId) {
		
		
		HttpSession session=request.getSession();
		Boolean checkPassword=(Boolean)session.getAttribute("checkPassword");
		
		User user=(User) session.getAttribute("user");
		Date date=new Date();
		
		if(user!=null) {
			//도메인만 쳐서 본래의 경로가 아닌 다른 경로로 들어갈 경우를 방지하기위해
			if(checkPassword==null) {
				return "redirect:/profile/check_password_view/"+user.getLoginId();
			}
			
			if(user.getLoginId().equals(userLoginId)) {//본인피드인 경우에만 접근 가능하도록 하기
				List<AlertTimeLineView> alertList=alertTimeLineBO.getAlertTimelineViewByUserId(user.getId());
				model.addAttribute("alertList",alertList);
				model.addAttribute("userView","user/user_edit_info_section");
				model.addAttribute("currentTime",date.getTime());
				
			}else {//본인이 아닌경우 본인의 피드로 리다이렉트 시킬것
				return "redirect:/feed/"+user.getLoginId();
			}
		}else {
			return "redirect:/user/sign_in_view";
		}

		return "template/template_layout";

	}
}
