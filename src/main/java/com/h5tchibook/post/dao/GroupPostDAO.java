package com.h5tchibook.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.post.model.GroupPost;

@Repository
public interface GroupPostDAO {
	public int insertGroupPost(GroupPost groupPost);
	public GroupPost selectGroupPostById(@Param("id") int id);
	public List<GroupPost> selectGroupPostListByGroupId(@Param("groupId") int groupId);
	public List<GroupPost> selectGroupPostListOnlyPhotoTypeByGroupId(@Param("groupId") int groupId
																	,@Param("limit") Integer limit);
	public List<GroupPost> selectGroupPostListByIdList(@Param("idList")List<Integer> idList);
	public void deleteGroupPostByGroupId(@Param("groupId") int groupId);
}
