package com.h5tchibook.timeline.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.timeline.model.UserTimeLine;

@Repository
public interface UserTimeLineDAO {
	public void insertUserTimeLine(UserTimeLine userTimeLine);
	public List<UserTimeLine> selectUserTimeLineListByUserIdList(@Param("userIdList")List<Integer> userIdList);
}
