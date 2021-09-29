package com.h5tchibook.post;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.h5tchibook.alert.bo.AlertTimeLineBO;
import com.h5tchibook.alert.model.AlertTimeLineView;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.PostView;
import com.h5tchibook.user.model.User;

@RequestMapping("/post")
@Controller
public class PostController{
	
	@Autowired
	private UserPostBO userPostBO;
	@Autowired
	private AlertTimeLineBO alertTimeLineBO;
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(@RequestParam("postId") int postId
								,Model model
								,HttpServletRequest request) {
	
		Date date= new Date();
		HttpSession session= request.getSession();
		User user =(User) session.getAttribute("user");
		
		PostView postView=null;
		List<AlertTimeLineView> alertList=null;
		
		if(user==null) {
			return "redirect:/user/sign_in_view";
		}else if(user!=null) {
			postView=userPostBO.getPostViewById(postId);
			alertList=alertTimeLineBO.getAlertTimelineViewByUserId(user.getId());

		}
		model.addAttribute("post",postView);
		model.addAttribute("alertList",alertList);
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView","post/post_detail_section");
		
		return "template/template_layout";
	}
}
