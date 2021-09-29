package com.h5tchibook.alert.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.GroupLikeAlert;

@Repository
public interface GroupLikeAlertDAO {
	public void insertGroupLikeAlert(GroupLikeAlert groupLikeAlert);
	public void deleteGroupLikeAlertByLikeId(@Param("likeId") int likeId);
	public void deleteGroupLikeAlertByLikeIdList(@Param("likeIdList") List<Integer> likeIdList);
	public GroupLikeAlert selectGroupLikeAlertByLikeId(@Param("likeId") int likeId);
	public GroupLikeAlert selectGroupLikeAlertByAlertId(@Param("alertId") int alertId);
	public List<GroupLikeAlert> selectGroupLikeAlertListByLikeIdList(@Param("likeIdlist") List<Integer> likeIdList);
}
