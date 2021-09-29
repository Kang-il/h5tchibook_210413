package com.h5tchibook.alert.bo;

import java.util.ArrayList;	
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.GroupLikeAlertDAO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.GroupLikeAlert;

@Service
public class GroupLikeAlertBO {
	@Autowired
	private GroupLikeAlertDAO groupLikeAlertDAO;
	@Autowired
	private AlertBO alertBO;
	
	public void createGroupLikeAlert(Alert alert, int postId, int likeId, int groupId) {
		alertBO.createAlert(alert);
		GroupLikeAlert likeAlert=GroupLikeAlert.builder()
									.alertId(alert.getId())
									.groupId(groupId)
									.postId(postId)
									.likeId(likeId)
									.build();
		groupLikeAlertDAO.insertGroupLikeAlert(likeAlert);
	}
	
	public void deleteGroupLikeAlertByLikeId(int likeId) {
		GroupLikeAlert alert=groupLikeAlertDAO.selectGroupLikeAlertByLikeId(likeId);
		if(alert!=null) {
			alertBO.deleteAlertById(alert.getAlertId());
			groupLikeAlertDAO.deleteGroupLikeAlertByLikeId(likeId);
		}
	}
	
	public void deleteGroupLikeAlertByLikeIdList(List<Integer> likeIdList) {
		List<GroupLikeAlert> likeAlertList=groupLikeAlertDAO.selectGroupLikeAlertListByLikeIdList(likeIdList);
		if(likeAlertList!=null) {
			List<Integer> alertIdList=new ArrayList<Integer>();
			for(GroupLikeAlert alert : likeAlertList) {
				alertIdList.add(alert.getAlertId());
			}
			alertBO.deleteAlertByIdList(alertIdList);
			groupLikeAlertDAO.deleteGroupLikeAlertByLikeIdList(likeIdList);
		}
	}
	
	public void deleteGroupLikeAlertByPostId(int postId) {
		List<GroupLikeAlert> alertList=groupLikeAlertDAO.selectGroupLikeAlertByPostId(postId);
		if(alertList!=null) {
			List<Integer> alertIdList=new ArrayList<Integer>();
			for(GroupLikeAlert alert : alertList) {
				alertIdList.add(alert.getAlertId());
			}
			if(alertIdList.size()!=0) {
				alertBO.deleteAlertByIdList(alertIdList);
				groupLikeAlertDAO.deleteGroupLikeAlertByPostId(postId);				
			}
		}
	}
	
	public GroupLikeAlert getGroupLikeAlertByAlertId(int alertId) {
		return groupLikeAlertDAO.selectGroupLikeAlertByAlertId(alertId);
	}
}
