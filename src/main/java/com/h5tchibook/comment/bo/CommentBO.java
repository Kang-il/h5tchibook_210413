package com.h5tchibook.comment.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.comment.dao.CommentDAO;
import com.h5tchibook.comment.model.Comment;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class CommentBO {
	
	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private UserBO userBO;
	
	public void createComment(Comment comment) {
		commentDAO.insertComment(comment);
	}
	
	public void deletecommentById(int id) {
		commentDAO.deleteCommentById(id);
	}
	
	public void deleteCommentByPostId(int postId) {
		commentDAO.deleteCommentByPostId(postId);
	}
	
	public List<Comment> getCommentListByPostId(int postId){
		List<Comment> commentList=commentDAO.selectCommentListByPostId(postId);
		
		for(Comment comment : commentList) {
			User user= userBO.getUserById(comment.getUserId());
			comment.setUserLoginId(user.getLoginId());
			comment.setUserProfileImagePath(user.getProfileImagePath());
		}
		
		return commentList;
	}
}
