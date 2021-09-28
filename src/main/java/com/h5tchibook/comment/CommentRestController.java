package com.h5tchibook.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.comment.bo.CommentBO;
import com.h5tchibook.comment.model.Comment;
import com.h5tchibook.comment.model.CommentView;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.Post;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/comment")
public class CommentRestController {

	@Autowired
	private CommentBO commentBO;
	@Autowired
	private UserPostBO userPostBO;
	
	@PostMapping("/create_comment")
	public Map<String,Object> createComment(@RequestParam("postId") int postId
										   ,@RequestParam("comment") String comment
										   ,HttpServletRequest request){
		Map<String,Object> result= new HashMap<String,Object>();
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		boolean loginCheck;
		boolean resultCheck;
		if(user!=null) {
			loginCheck=true;
			Post post=userPostBO.getPostById(postId);
			Comment comments=Comment.builder()
			.userId(user.getId())
			.postId(postId)
			.comment(comment).build();
			commentBO.createComment(comments,post);
			resultCheck=true;
		}else {
			loginCheck=false;
			resultCheck=false;
		}
		
		result.put("loginCheck", loginCheck);
		result.put("result", resultCheck);
		return result;
	}
	
	@RequestMapping("/get_comment_list")
	public Map<String,Object> getCommentList(@RequestParam("postId") int postId
											, HttpServletRequest request){
		Map<String,Object> result=new HashMap<>();
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		boolean loginCheck;
		boolean resultCheck;
		
		if(user!=null) {
			loginCheck=true;
			resultCheck=true;
			
			List<CommentView> commentList=commentBO.getCommentListByPostId(postId);
			result.put("commentList", commentList);
			
		}else {
			loginCheck=false;
			resultCheck=false;
		}
			result.put("loginCheck", loginCheck);
			result.put("resultCheck", resultCheck);
			result.put("userId", user.getId());
		return result;
	}
	
	@RequestMapping("/delete_comment")
	public Map<String,Object> deleteComment(@RequestParam("commentId") int commentId
											,HttpServletRequest request){
		HttpSession session=request.getSession();
		Map<String,Object> result=new HashMap<String,Object>();
		boolean loginCheck=false;
		boolean deleteResult=false;
		
		User user=(User)session.getAttribute("user");
		if(user != null) {
			loginCheck=true;
			Comment comment=commentBO.getCommentById(commentId);
			Post post=userPostBO.getPostById(comment.getPostId());
			
			// 포스터의 소유자가 나거나 댓글 작성자가 나인경우에만 삭제 메서드 호출
			if(comment.getUserId() == user.getId() || post.getUserId() == user.getId()) {
				commentBO.deletecommentById(commentId);
				deleteResult= true;
			}
			
		}
		result.put("loginCheck", loginCheck);
		result.put("deleteResult", deleteResult);
		
		return result;
	}
	
}
