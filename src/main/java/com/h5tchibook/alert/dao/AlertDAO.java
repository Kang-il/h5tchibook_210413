package com.h5tchibook.alert.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;

@Repository
public interface AlertDAO {
	public void insertAlert(Alert alert);
	public void deletealertById(@Param("id") int id);
	public Alert selectAlertBySendUserIdAndReceiveUserIdAndFriendAlertType(@Param("sendUserId")int sendUserId 
																		 , @Param("receiveUserId") int receiveUserId
																		 , @Param("alertType")AlertType alertType);
}
