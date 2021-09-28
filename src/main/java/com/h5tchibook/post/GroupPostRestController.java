package com.h5tchibook.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.common.ValidateHandler;
import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.post.bo.GroupPostBO;
import com.h5tchibook.post.model.GroupPost;
import com.h5tchibook.post.model.ValidateGroupPost;
import com.h5tchibook.user.model.User;

@RequestMapping("/group/post")
@RestController
public class GroupPostRestController {
	@Autowired
	private GroupPostBO groupPostBO;
	@Autowired
	private ValidateHandler validateHandler;
	@Autowired
	private GroupBO groupBO;
	
	@PostMapping("/create_post")
	public Map<String,Object> groupPostValidation(@ModelAttribute ValidateGroupPost validateGroupPost
													,@RequestParam(value="file" , required=false) MultipartFile file
													,Errors errors
													,HttpServletRequest request){
		
		Map<String,Object> result=new HashMap<String,Object>();
		HttpSession session=request.getSession();
		
		User user=(User)session.getAttribute("user");
		boolean loginCheck=false;
		boolean resultCheck=false;
		if(user==null) {
			
		}else {
			loginCheck=true;
			if(validateHandler.postValidation(validateGroupPost, file, errors, result)==true) {
				
				GroupPost groupPost=GroupPost
									.builder()
									.groupMemberId(user.getId())
									.groupId(validateGroupPost.getGroupId())
									.content(validateGroupPost.getContent())
									.build();
				
				resultCheck=groupPostBO.createGroupPost(user.getLoginId(),groupPost,file);
			}
		}
		
		result.put("loginCheck", loginCheck);
		result.put("result", resultCheck);
		return result;
	}
	
	@PostMapping("/delete_post")
	public Map<String,Boolean> deletePost(@RequestParam("postId") int postId
										,HttpServletRequest request){
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		HttpSession session=request.getSession();
		
		User user=(User)session.getAttribute("user");
		boolean loginCheck=false;
		boolean resultCheck=false;
		
		if(user!=null) {
			loginCheck=true;
			GroupPost post =groupPostBO.getGroupPostById(postId);
			Group group=groupBO.getGroupById(post.getGroupId());
			if(post!=null && (post.getGroupMemberId()==user.getId() || group.getGroupManagerId() == user.getId()) ) {
				groupPostBO.deleteGroupPostById(postId);
				resultCheck=true;
			}
		}
		
		result.put("loginCheck", loginCheck);
		result.put("result", resultCheck);
		return result;
	}
}
