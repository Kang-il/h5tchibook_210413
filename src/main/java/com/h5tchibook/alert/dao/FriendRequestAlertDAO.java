package com.h5tchibook.alert.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.FriendRequestAlert;

@Repository
public interface FriendRequestAlertDAO {
	public void insertFriendRequestAlert(FriendRequestAlert friendRequestAlert);
	public void deleteFriendRequestAlertByAlertId(@Param("alertId") int alertId);
	public FriendRequestAlert selectFreindRequestAlertByAlertId(@Param("alertId") int alertId);
}
