package com.h5tchibook.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.group.model.GroupMember;

@Repository
public interface GroupMemberDAO {
	public void insertGroupMember(GroupMember groupMember);
	public List<GroupMember> selectGroupMemberListByGroupMemberId(@Param("groupMemberId") int groupMemberId);
}
