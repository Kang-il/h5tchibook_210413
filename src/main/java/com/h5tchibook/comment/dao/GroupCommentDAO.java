package com.h5tchibook.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.comment.model.GroupComment;

@Repository
public interface GroupCommentDAO {
	public int createGroupComment(GroupComment groupComment);
	public int deleteGroupCommentById(@Param("id") int id);
	public GroupComment selectGroupCommentById(@Param("id")int id);
	public List<GroupComment> selectGroupCommentListByPostIdList(@Param("postIdList") List<Integer>postIdList);
	public List<GroupComment> selectGroupCommentListByPostId(@Param("postId") int postId);
}
