package com.h5tchibook.alert.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.LikeAlert;

@Repository
public interface LikeAlertDAO {
	public void insertLikeAlert(LikeAlert likeAlert);
	public void deleteLikeAlertByLikeId(@Param("likeId") int likeId);
	public LikeAlert selectLikeAlertByAlertId(@Param("alertId") int alertId);
	public LikeAlert selectLikeAlertByLikeId(@Param("likeId") int likeId);
}
