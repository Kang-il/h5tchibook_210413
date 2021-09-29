package com.h5tchibook.alert.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.CommentAlert;

@Repository
public interface CommentAlertDAO {
	public void insertCommentAlertDAO(CommentAlert commentAlert);
	public void deleteCommentAlertByCommentId(@Param("commentId") int commentId);
	public CommentAlert selectCommentAlertByCommentId(@Param("commentId") int commentId);
	public CommentAlert selectCommentAlertByAlertId(@Param("alertId") int alertId);
}
