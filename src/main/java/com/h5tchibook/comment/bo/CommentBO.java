package com.h5tchibook.comment.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.bo.CommentAlertBO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.comment.dao.CommentDAO;
import com.h5tchibook.comment.model.Comment;
import com.h5tchibook.comment.model.CommentView;
import com.h5tchibook.post.model.Post;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class CommentBO {
	
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private UserBO userBO;
	@Autowired
	private CommentAlertBO commentAlertBO;
	
	public void createComment(Comment comment,Post post) {
		commentDAO.insertComment(comment);
		
		//본인 포스터에 남긴 본인 댓글이 아닌 경우에만 알람생성
		if(comment.getUserId()!=post.getUserId()) {
			
			Alert alert=Alert.builder()
							.sendUserId(comment.getUserId())
							.receiveUserId(post.getUserId())
							.alertType(AlertType.COMMENT)
							.build();
			
			commentAlertBO.createCommentAlert(alert, comment.getId(), post.getId());
		}
		
	}
	
	public void deletecommentById(int id) {
		Comment comment=commentDAO.selectCommentById(id);
		if(comment!=null) {
			commentDAO.deleteCommentById(id);
			commentAlertBO.deleteCommentAlertByCommentId(id);
		}
	}
	
	public void deleteCommentByPostId(int postId) {
		List<Comment> commentList=commentDAO.selectCommentListByPostId(postId);
		if(commentList!=null) {
			commentDAO.deleteCommentByPostId(postId);
			commentAlertBO.deleteCommentAlertByPostId(postId);			
		}
	}
	
	public Comment getCommentById(int commentId) {
		return commentDAO.selectCommentById(commentId);
	}
	
	public List<CommentView> getCommentListByPostId(int postId){
		List<Comment> commentList=commentDAO.selectCommentListByPostId(postId);
		
		List<CommentView> viewCommentList = new ArrayList<CommentView>();
		
		for(Comment comment : commentList) {
			User user= userBO.getUserById(comment.getUserId());
			CommentView viewComment=CommentView.builder()
					.id(comment.getId())
					.userId(comment.getUserId())
					.postId(comment.getPostId())
					.comment(comment.getComment())
					.createdAt(comment.getCreatedAt())
					.updatedAt(comment.getUpdatedAt())
					.userLoginId(user.getLoginId())
					.userProfileImagePath(user.getProfileImagePath())
					.build();
			viewCommentList.add(viewComment);
		}
		
		return viewCommentList;
	}
	
	
}
