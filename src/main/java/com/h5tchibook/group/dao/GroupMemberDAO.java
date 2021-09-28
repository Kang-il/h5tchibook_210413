package com.h5tchibook.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.group.model.GroupMember;

@Repository
public interface GroupMemberDAO {
	public int insertGroupMember(GroupMember groupMember);
	public int deleteGroupMemberByGroupIdAndGroupMemberId(@Param("groupId") int groupId 
														, @Param("groupMemberId") int groupMemberId);
	public void deleteGroupMemberByGroupId(@Param("groupId") int groupId);
	public int selectGroupMemberCountByGroupId(@Param("groupId")int groupId);
	public GroupMember selectGroupMemberByGroupIdAndGroupMeberId(@Param("groupId") int groupId
																,@Param("groupMemberId") int groupMemberId);
	public List<GroupMember> selectGroupMemberListByGroupMemberId(@Param("groupMemberId") int groupMemberId);
	public List<GroupMember> selectGroupMemberListByGroupId(@Param("groupId") int groupId
														  , @Param("limit")Integer limit);
}
