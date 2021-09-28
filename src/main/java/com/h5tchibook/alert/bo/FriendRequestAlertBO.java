package com.h5tchibook.alert.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.FriendRequestAlertDAO;
import com.h5tchibook.alert.model.Alert;
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
	
}
