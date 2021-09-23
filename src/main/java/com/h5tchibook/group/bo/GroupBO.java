package com.h5tchibook.group.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.group.dao.GroupDAO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.group.model.GroupMember;
import com.h5tchibook.user.model.User;

@Service
public class GroupBO {
	@Autowired
	private GroupDAO groupDAO;
	@Autowired
	private CheckBO checkBO;
	
	@Autowired
	private GroupMemberBO groupMemberBO;
	
	public Map<String,Boolean> createGroup(User user,Group group) {
		Group groupCheck=groupDAO.selectGroupByGroupName(group.getGroupName());
		
		Map<String,Boolean> result=checkBO.createGroupCheckElements(user,groupCheck);
		boolean resultCheck=false;
		//그룹 중복체크 중복 없을 시 true있을 시 false;
		if(result.get("loginCheck")) {
			if(result.get("existGroupCheck")) {
				groupDAO.insertGroup(group);
				//groupMember에 정보를 담아줌(내가 그룹장)
				GroupMember groupMember=GroupMember.builder()
						.groupId(group.getId())
						.groupMemberId(group.getGroupManagerId())
						.build();
				//groupMember생성
				groupMemberBO.createGroupMember(groupMember);
				resultCheck=true;
			}			
		}
		result.put("result",resultCheck);
		return result;
	}
	
	public Group getGroupByGroupName(String groupName) {
		return groupDAO.selectGroupByGroupName(groupName);
	}
	
	public List<Group> getGroupListByMemberId(int memberId){
		List<GroupMember> groupMemberList=groupMemberBO.getGroupMemberListByGroupMemberId(memberId);
		List<Integer> groupIdList=new ArrayList<Integer>();
		if(groupMemberList!=null) {
			for(GroupMember groupMember : groupMemberList) {
				groupIdList.add(groupMember.getGroupId());
			}
			List<Group> groupList=groupDAO.selectGroupListByIdList(groupIdList);
			return groupList;
		}
		
		
		return null;
	}
}
