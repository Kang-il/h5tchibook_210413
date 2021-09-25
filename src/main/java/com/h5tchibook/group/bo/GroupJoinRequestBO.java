package com.h5tchibook.group.bo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.group.dao.GroupJoinRequestDAO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.group.model.GroupJoinRequest;
import com.h5tchibook.user.model.User;

@Service
public class GroupJoinRequestBO {
	@Autowired
	private GroupJoinRequestDAO groupJoinRequestDAO;
	@Autowired
	private CheckBO checkBO;
	
	public Map<String, Boolean> createGroupJoinRequest(User user,Group group){
		boolean resultCheck=false;
		boolean validationCheck=true;
		
		GroupJoinRequest requestCheck=groupJoinRequestDAO.selectGroupJoinRequestByUserIdAndGroupId(user.getId(), group.getId());
		Map<String,Boolean> result=checkBO.createGroupJoinRequestCheckElements(user, group,requestCheck);
		
		for(String key :  result.keySet()) {
			if(!result.get(key)) {
				validationCheck=false;
				break;
			}
		}
		
		if(validationCheck) {
			GroupJoinRequest groupJoinRequest=GroupJoinRequest
												.builder()
												.groupId(group.getId())
												.userId(user.getId())
												.build();
			int row =groupJoinRequestDAO.createGroupJoinRequest(groupJoinRequest);
			if(row!=0) {
				resultCheck=true;
			}
		}
		
		result.put("result", resultCheck);
		
		return null;
	}
	
	public GroupJoinRequest getGroupJoinRequestByUserIdAndGroupId(int userId , int groupId) {
		return groupJoinRequestDAO.selectGroupJoinRequestByUserIdAndGroupId(userId,groupId);
	}
}
