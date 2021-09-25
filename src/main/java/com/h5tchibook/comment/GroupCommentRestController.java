package com.h5tchibook.comment;

import java.util.List;	
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.comment.bo.GroupCommentBO;
import com.h5tchibook.comment.model.GroupComment;
import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.post.bo.GroupPostBO;
import com.h5tchibook.post.model.GroupPost;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/group/comment")
public class GroupCommentRestController {
	
	@Autowired
	private GroupCommentBO groupCommentBO;
	@Autowired
	private GroupBO groupBO;
	@Autowired
	private GroupPostBO groupPostBO;
	
	@PostMapping("/create_comment")
	public Map<String,Boolean> createGroupComment(@RequestParam("postId") int postId
												, @RequestParam("groupId") int groupId
												, @RequestParam("comment") String comment
												,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		GroupComment groupComment=GroupComment.builder()
											  .groupId(groupId)
											  .memberId(user.getId())
											  .postId(postId)
											  .comment(comment)
											  .build();
		 
		Map<String,Boolean> result=groupCommentBO.createComment(user,groupComment);
		
		return result;
	}
	
	@PostMapping("/get_group_comment_list")
	public Map<String,Object> getGroupCommentList(@RequestParam("postId") int postId
												,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		Map<String,Object>result=groupCommentBO.getGroupCommentViewListByPostId(user, postId);
		List<Integer> groupIdList=null;
		
		if(user!=null) {
			groupIdList=groupBO.getGroupIdListkByGroupManagerId(user.getId());
		}
		
		result.put("groupIdList", groupIdList);
		
		return result;
	}
	
	@PostMapping("/delete_group_comment")
	public Map<String,Boolean> deleteGroupComment(@RequestParam("commentId") int commentId
												,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		//유효성 체크를 위한 객체
		GroupComment groupComment=groupCommentBO.getGroupCommentById(commentId);
		GroupPost groupPost=groupPostBO.getGroupPostById(groupComment.getPostId());
		Group group=groupBO.getGroupById(groupPost.getGroupId());
		
		Map<String,Boolean> result=groupCommentBO.deleteGroupCommentByCommentId(user,groupComment,groupPost,group);
		
		return result;
	}
}
