package com.h5tchibook.alert.bo;

import java.util.List;

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
		alertDAO.deleteAlertById(id);
	}
	
	public void deleteAlertByIdList(List<Integer> idList) {
		alertDAO.deleteAlertByIdList(idList);
	}
	
	public Alert getAlertBySendUserIdAndReceiveUserIdAndAlertType(int sendUserId,int receiveUserId,AlertType alertType) {
		return alertDAO.selectAlertBySendUserIdAndReceiveUserIdAndAlertType(sendUserId, receiveUserId, alertType);
	}
	
	public List<Alert> getAlertListBySendUserId(int sendUserId){
		return alertDAO.selectAlertListByReceiveUserId(sendUserId);
	}
}
