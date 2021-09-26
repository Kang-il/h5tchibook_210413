package com.h5tchibook.timeline.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.timeline.model.GroupTimeLine;

@Repository
public interface GroupTimeLineDAO {
	public void insertGroupTimeLine(GroupTimeLine groupTimeLine);
	public List<GroupTimeLine> selectGroupTimeLineListByGroupIdList(@Param("groupIdList")List<Integer> groupIdList);
}
