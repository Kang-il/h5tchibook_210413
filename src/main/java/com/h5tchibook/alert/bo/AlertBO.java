package com.h5tchibook.alert.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.AlertDAO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;

@Service
public class AlertBO {
	@Autowired
	private AlertDAO alertDAO;
	
	public void createAlert(Alert alert) {
		alertDAO.insertAlert(alert);
	}
	
	public void deleteAlertById(int id) {
		alertDAO.deletealertById(id);
	}
	
//	public Alert getAlertBySendUserIdAndReceiveUserIdAndFriendAlertType(int sendUserId,int receiveUserId) {
//		AlertType alertType=AlertType.FRIEND_REQUEST;
//	}
}
