package com.h5tchibook.alert.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.GroupCommentAlertDAO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.GroupCommentAlert;

@Service
public class GroupCommentAlertBO {
	@Autowired
	private GroupCommentAlertDAO groupCommentAlertDAO;
	@Autowired
	private AlertBO alertBO;
	
	public void createGroupCommentAlert(Alert alert, int groupId, int commentid, int postId) {
		alertBO.createAlert(alert);
		GroupCommentAlert groupCommentAlert=GroupCommentAlert.builder()
															 .alertId(alert.getId())
															 .groupId(groupId)
															 .commentId(commentid)
															 .postId(postId)
															 .build();
		
		groupCommentAlertDAO.insertGroupCommentAlert(groupCommentAlert);
	}
	
	public void deleteGroupCommentAlertByGroupCommentId(int groupCommentId) {
		GroupCommentAlert groupCommentAlert=groupCommentAlertDAO.selectGroupCommentAlertByCommentId(groupCommentId);
		if(groupCommentAlert!=null) {
			alertBO.deleteAlertById(groupCommentAlert.getAlertId());
			groupCommentAlertDAO.deleteGroupCommentAlertByCommentId(groupCommentId);
		}
	}
	
	public void deleteGroupCommentAlertByPostIdList(List<Integer> postIdList) {
		List<GroupCommentAlert> groupCommentAlertList=groupCommentAlertDAO.selectGroupCommentAlertListByPostIdList(postIdList);
		if(groupCommentAlertList!=null) {
			List<Integer> alertIdlist=new ArrayList<Integer>();
			for(GroupCommentAlert alert : groupCommentAlertList) {
				alertIdlist.add(alert.getAlertId());
			}
			
			alertBO.deleteAlertByIdList(alertIdlist);
			groupCommentAlertDAO.deleteGroupCommentAlertByPostIdList(postIdList);
		}
	}
	
	public GroupCommentAlert getGroupCommentAlertByAlertId(int alertId) {
		return groupCommentAlertDAO.selectGroupCommentAlertByAlertId(alertId);
	}
}
