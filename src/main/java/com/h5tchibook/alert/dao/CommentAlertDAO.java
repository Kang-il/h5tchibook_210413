package com.h5tchibook.alert.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.CommentAlert;

@Repository
public interface CommentAlertDAO {
	public void insertCommentAlert(CommentAlert commentAlert);
	public void deleteCommentAlertByCommentId(@Param("commentId") int commentId);
	public void deleteCommentAlertByPostId(@Param("postId") int postId);
	public List<CommentAlert> selectCommentAlertByPostId(@Param("postId") int postId);
	public CommentAlert selectCommentAlertByCommentId(@Param("commentId") int commentId);
	public CommentAlert selectCommentAlertByAlertId(@Param("alertId") int alertId);
}
