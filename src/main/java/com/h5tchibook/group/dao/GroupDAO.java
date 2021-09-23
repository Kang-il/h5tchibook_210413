package com.h5tchibook.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.group.model.Group;

@Repository
public interface GroupDAO {
	public void insertGroup(Group group);
	public Group selectGroupByGroupName(@Param("groupName")String groupName);
	public List<Group> selectGroupListByIdList(@Param("idList")List<Integer> idList);
}
