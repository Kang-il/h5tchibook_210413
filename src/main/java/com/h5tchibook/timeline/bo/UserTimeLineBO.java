package com.h5tchibook.timeline.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.timeline.dao.UserTimeLineDAO;
import com.h5tchibook.timeline.model.UserTimeLine;

@Service
public class UserTimeLineBO {
	@Autowired
	private UserTimeLineDAO userTimeLineDAO;
	
	public void createdUserTimeLine(UserTimeLine userTimeLine) {
		userTimeLineDAO.insertUserTimeLine(userTimeLine);
	}
	
	public List<UserTimeLine> getUserTimeLineListByUserIdList(List<Integer> userIdList){
		return userTimeLineDAO.selectUserTimeLineListByUserIdList(userIdList);
	}
}
