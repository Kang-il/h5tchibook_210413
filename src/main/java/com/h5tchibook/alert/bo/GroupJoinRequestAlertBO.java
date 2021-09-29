package com.h5tchibook.alert.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.GroupJoinRequestAlertDAO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.alert.model.GroupJoinRequestAlert;

@Service
public class GroupJoinRequestAlertBO {
	@Autowired
	private GroupJoinRequestAlertDAO groupJoinRequestAlertDAO;
	@Autowired
	private AlertBO alertBO;
	
	
	public void createGroupJoinRequestAlert(Alert alert, int groupId) {
		alertBO.createAlert(alert);
		
		GroupJoinRequestAlert groupJoinRequestAlert=GroupJoinRequestAlert.builder()
																		 .alertId(alert.getId())
																		 .groupId(groupId)
																		 .build();
		
		groupJoinRequestAlertDAO.insertGroupJoinRequestAlert(groupJoinRequestAlert);
	}
	
	public void deleteGroupJoinRequestAlertBySendUserIdAndReceiveUserIdAndAlertType(int sendUserId,int receiveUserId) {
		
		Alert alert=alertBO.getAlertBySendUserIdAndReceiveUserIdAndAlertType(sendUserId, receiveUserId, AlertType.GROUP_JOIN_REQUEST);
		
		if(alert!=null) {
			groupJoinRequestAlertDAO.deleteGroupJoinRequestAlertByAlertId(alert.getId());
			alertBO.deleteAlertById(alert.getId());
		}
	}
	
	public void deleteroupJoinRequestAlertByGroupId(int groupId) {
		List<GroupJoinRequestAlert> alertList=groupJoinRequestAlertDAO.selectGroupJoinRequestAlertListByGroupId(groupId);
		if(alertList != null) {
			List<Integer> alertIdList=new ArrayList<Integer>(); 
			for(GroupJoinRequestAlert alert : alertList) {
				alertIdList.add(alert.getAlertId());
			}
			groupJoinRequestAlertDAO.deleteGroupJoinRequestAlertByGroupId(groupId);
			alertBO.deleteAlertByIdList(alertIdList);
		}
	}
	
	public GroupJoinRequestAlert getGroupJoinRequestAlertByAlertId(int alertId) {
		return groupJoinRequestAlertDAO.selectGroupJoinRequestAlertByAlertId(alertId);
	}
}
