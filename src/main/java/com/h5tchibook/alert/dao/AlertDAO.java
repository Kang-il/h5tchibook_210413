package com.h5tchibook.alert.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;

@Repository
public interface AlertDAO {
	public void insertAlert(Alert alert);
	public void deleteAlertById(@Param("id") int id);
	public void deleteAlertByIdList(@Param("idList") List<Integer> idList);
	public Alert selectAlertBySendUserIdAndReceiveUserIdAndAlertType(@Param("sendUserId")int sendUserId 
																		 , @Param("receiveUserId") int receiveUserId
																		 , @Param("alertType")AlertType alertType);
	public List<Alert> selectAlertListByReceiveUserId(@Param("receiveUserId") int receiveUserId);
}
