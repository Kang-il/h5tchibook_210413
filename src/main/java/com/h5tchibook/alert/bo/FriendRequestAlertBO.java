package com.h5tchibook.alert.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.FriendRequestAlertDAO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.alert.model.FriendRequestAlert;

@Service
public class FriendRequestAlertBO {
	@Autowired
	private FriendRequestAlertDAO friendRequestAlertDAO;
	@Autowired
	private AlertBO alertBO;
	
	
	public void createFriendRequestAlert(Alert alert) {
		alertBO.createAlert(alert);
		FriendRequestAlert friendRequestAlert=FriendRequestAlert.builder()
																.alertId(alert.getId())
															    .build();
		friendRequestAlertDAO.insertFriendRequestAlert(friendRequestAlert);
	}
	
	public void deleteFriendRequestAlert(int sendUserId, int receiveUserId) {
		//sendUserId 와 receiveUserId alertType으로 alert 를 가져옴
		Alert alert=alertBO.getAlertBySendUserIdAndReceiveUserIdAndAlertType(sendUserId,receiveUserId,AlertType.FRIEND_REQUEST);
		
		if(alert!=null) {
			//alertId로 friendAlert 지우기
			friendRequestAlertDAO.deleteFriendRequestAlertByAlertId(alert.getId());
			//alert삭제
			alertBO.deleteAlertById(alert.getId());
		}
		
	}
	
	public FriendRequestAlert getFriendRequestAlertByAlertId(int alertId) {
		return friendRequestAlertDAO.selectFreindRequestAlertByAlertId(alertId);
	}
	
}
