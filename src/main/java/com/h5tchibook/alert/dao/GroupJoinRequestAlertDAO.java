package com.h5tchibook.alert.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.alert.model.GroupJoinRequestAlert;

@Repository
public interface GroupJoinRequestAlertDAO {
	
	public void insertGroupJoinRequestAlert(GroupJoinRequestAlert groupJoinAlertRequest);
	public void deleteGroupJoinRequestAlertByAlertId(@Param("alertId") int alertId);
	public void deleteGroupJoinRequestAlertByGroupId(@Param("groupId") int groupId);
	public List<GroupJoinRequestAlert> selectGroupJoinRequestAlertListByGroupId(@Param("groupId") int groupId);
	public GroupJoinRequestAlert selectGroupJoinRequestAlertByAlertId(@Param("alertId") int alertId);
}
