package com.h5tchibook.alert.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.GroupCommentAlert;

@Repository
public interface GroupCommentAlertDAO {
	public void insertGroupCommentAlert(GroupCommentAlert groupCommentAlert);
	public void deleteGroupCommentAlertByCommentId(@Param("commentId")int commentId);
	public void deleteGroupCommentAlertByPostIdList(@Param("postIdList") List<Integer> postIdlist);
	public void deleteGroupComentAlertByPostId(@Param("postId") int postId);
	public List<GroupCommentAlert> selectGroupCommentAlertByPostId(@Param("postId") int postId);
	public GroupCommentAlert selectGroupCommentAlertByCommentId(@Param("commentId")int commentId);
	public GroupCommentAlert selectGroupCommentAlertByAlertId(@Param("alertId") int alertId);
	public List<GroupCommentAlert> selectGroupCommentAlertListByPostIdList(@Param("postIdList") List<Integer> postIdList);
}
