package com.h5tchibook.like;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.like.bo.LikeBO;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/like")
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;
	
	@RequestMapping("/set_like/{postId}")
	public Map<String,Boolean> setLike(@PathVariable("postId") int postId
									,HttpServletRequest request){
		
		HttpSession session= request.getSession();
		
		User user = (User)session.getAttribute("user");
		
		Map<String, Boolean> result=likeBO.setLike(user, postId);
	
		return result;
	}
}
