package com.h5tchibook.like;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.like.bo.GroupLikeBO;
import com.h5tchibook.post.bo.GroupPostBO;
import com.h5tchibook.post.model.GroupPost;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/group/like")
public class GroupLikeRestController {
	
	@Autowired
	private GroupLikeBO groupLikeBO;
	@Autowired
	private GroupPostBO groupPostBO;
	
	@RequestMapping("/set_like/{postId}")
	public Map<String,Boolean> setLike(@PathVariable("postId") int postId
									 , @RequestParam("groupId") int  groupId
									 ,	HttpServletRequest request){
		
		HttpSession session=request.getSession();
		
		User user=(User)session.getAttribute("user");
		GroupPost post=groupPostBO.getGroupPostById(postId);
		
		Map<String,Boolean> result=groupLikeBO.setGroupLike(user, groupId, post);
		
		return result;
	}
	
}
