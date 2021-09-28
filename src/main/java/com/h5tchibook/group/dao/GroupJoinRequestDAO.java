package com.h5tchibook.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.group.model.GroupJoinRequest;

@Repository
public interface GroupJoinRequestDAO {
	public int insertGroupJoinRequest(GroupJoinRequest groupJoinRequest);
	public int deleteGroupJoinRequestByGroupIdAndUserId(@Param("groupId")int groupId
														,@Param("userId")int userId);
	public GroupJoinRequest selectGroupJoinRequestByUserIdAndGroupId(@Param("userId") int userId 
																	, @Param("groupId") int groupId);
	public List<GroupJoinRequest> selectGroupJoinRequestByGroupId(@Param("groupId") int groupId);
}
