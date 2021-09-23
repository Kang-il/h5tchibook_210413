package com.h5tchibook.group.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.group.dao.GroupMemberDAO;
import com.h5tchibook.group.model.GroupMember;

@Service
public class GroupMemberBO {
	
	@Autowired
	private GroupMemberDAO groupMemberDAO;
	
	public void createGroupMember(GroupMember groupMember) {
		groupMemberDAO.insertGroupMember(groupMember);
	}
	
	public List<GroupMember> getGroupMemberListByGroupMemberId(int memberId){
		return groupMemberDAO.selectGroupMemberListByGroupMemberId(memberId);
	}
}
