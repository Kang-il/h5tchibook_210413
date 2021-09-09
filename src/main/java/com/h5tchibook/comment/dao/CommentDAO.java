package com.h5tchibook.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.comment.model.Comment;

@Repository
public interface CommentDAO {
	public void insertComment(Comment comment);
	public void deleteCommentById(@Param("id") int id);
	public void deleteCommentByPostId(@Param("postId") int postId);
	public List<Comment> selectCommentListByPostId(@Param("postId") int postId);
}
