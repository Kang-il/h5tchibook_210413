package com.h5tchibook.post;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.h5tchibook.like.bo.LikeBO;
import com.h5tchibook.like.model.Like;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.PostView;
import com.h5tchibook.user.model.User;

@RequestMapping("/post")
@Controller
public class PostController{
	
	@Autowired
	private UserPostBO userPostBO;
	@Autowired
	private LikeBO likeBO;
	
	@RequestMapping("/post_detail_view")
	public String postDetailView(@RequestParam("postId") int postId
								,Model model
								,HttpServletRequest request) {
	
		Date date= new Date();
		HttpSession session= request.getSession();
		User user =(User) session.getAttribute("user");
		
		if(user==null) {
			return "redirect:/user/sign_in_view";
		}else if(user!=null) {
			PostView postView=userPostBO.getPostViewById(postId);
			
			//포스트 뷰가 null이거나 타입이 photo가 아닌경우 해당 글의 주인 피드로 리다이렉트 시킨다.
			if(postView==null || !postView.getContentType().getContentType().equals("photo")) {
				return "redirect:/feed/"+postView.getUserLoginId();
			}
			
			model.addAttribute("post",postView);
			
		}
		
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView","post/post_detail_section");
		
		return "template/template_layout";
	}
}
