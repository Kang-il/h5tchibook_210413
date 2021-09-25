package com.h5tchibook.group.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.group.model.GroupJoinRequest;

@Repository
public interface GroupJoinRequestDAO {
	public int createGroupJoinRequest(GroupJoinRequest groupJoinRequest);
	public GroupJoinRequest selectGroupJoinRequestByUserIdAndGroupId(@Param("userId") int userId 
																	, @Param("groupId") int groupId);
}
