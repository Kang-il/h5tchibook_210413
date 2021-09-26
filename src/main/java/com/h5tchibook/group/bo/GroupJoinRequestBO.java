package com.h5tchibook.group.bo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.group.dao.GroupJoinRequestDAO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.group.model.GroupJoinRequest;
import com.h5tchibook.group.model.GroupMember;
import com.h5tchibook.user.model.User;

@Service
public class GroupJoinRequestBO {
	@Autowired
	private GroupJoinRequestDAO groupJoinRequestDAO;
	@Autowired
	private CheckBO checkBO;
	@Autowired
	private GroupMemberBO groupMemberBO;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
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
			int row =groupJoinRequestDAO.insertGroupJoinRequest(groupJoinRequest);
			if(row!=0) {
				resultCheck=true;
			}
		}
		result.put("result",resultCheck);
		return result;
	}
	
	public Map<String,Boolean> responseJoinGroup(User user,Group group,int userId,String decision){
		GroupJoinRequest groupJoinRequest=groupJoinRequestDAO.selectGroupJoinRequestByUserIdAndGroupId(userId, group.getId());
		
		boolean validationCheck=true;
		boolean resultCheck=false;
		Map<String,Boolean> result=checkBO.responseJoinGroupCheckElements(user,group,groupJoinRequest,userId);
		logger.debug(":::::::::::::::::;"+result);
		for(String key:result.keySet()) {
			if(!result.get(key)) {
				validationCheck=false;
				break;
			}
		}
		
		if(validationCheck) {
			// 그룹 가입요청을 수락 한 경우
			if(decision.equals("accept")) {
				GroupMember groupMember = GroupMember.builder()
										.groupId(group.getId())
										.groupMemberId(userId)
										.build();
				//그룹 멤버를 insert시킨다.
				
				int row=groupMemberBO.createGroupMember(groupMember);
				//row 가 0이 아니라면 insert 성공
				if(row!=0) {
					//insert 성공 시 가입요청 내역을 지운다
					row=groupJoinRequestDAO.deleteGroupJoinRequestByGroupIdAndUserId(group.getId(), userId);
						
					if(row!=0) {
						resultCheck=true;
					}
				}
			//그룹 가입 요청을 취소한 경우
			}else if(decision.equals("deny")) {
				//멤버를 추가시키지 않고 가입요청내역만 지워주면 된다.
				int row=groupJoinRequestDAO.deleteGroupJoinRequestByGroupIdAndUserId(group.getId(), userId);
				if(row!=0) {
					resultCheck=true;
				}
			}
		}
		
		result.put("result", resultCheck);
		
		return result;
	}
	
	public Map<String,Boolean> deleteJoinGroupRequest(User user,Group group){
		boolean resultCheck=false;
		boolean validationCheck=true;
		
		GroupJoinRequest requestCheck=groupJoinRequestDAO.selectGroupJoinRequestByUserIdAndGroupId(user.getId(), group.getId());
		
		 Map<String,Boolean> result=checkBO.deleteGroupJoinRequestCheckElements(user, group, requestCheck);
		 for(String key:result.keySet()) {
			 if(!result.get(key)) {
				validationCheck=false;
				 break;
			 }
		 }
		 
		 if(user.getId()==group.getGroupManagerId()) {
			 validationCheck=false;
		 }
		 
		 if(validationCheck) {
			 int row=groupJoinRequestDAO.deleteGroupJoinRequestByGroupIdAndUserId(group.getId(), user.getId());
			 if(row!=0) {
				 resultCheck=true;
			 }
		 }
		 
		 result.put("result", resultCheck);
		 return result;
	}
	
	public GroupJoinRequest getGroupJoinRequestByUserIdAndGroupId(int userId , int groupId) {
		return groupJoinRequestDAO.selectGroupJoinRequestByUserIdAndGroupId(userId,groupId);
	}
}
