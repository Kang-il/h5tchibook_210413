package com.h5tchibook.alert.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.LikeAlert;

@Repository
public interface LikeAlertDAO {
	public void insertLikeAlert(LikeAlert likeAlert);
	public void deleteLikeAlertByLikeId(@Param("likeId") int likeId);
	public void deleteLikeAlertByPostId(@Param("postId") int postId);
	public List<LikeAlert> selectLikeAlertListByPostId(@Param("postId") int postId);
	public LikeAlert selectLikeAlertByAlertId(@Param("alertId") int alertId);
	public LikeAlert selectLikeAlertByLikeId(@Param("likeId") int likeId);
}
