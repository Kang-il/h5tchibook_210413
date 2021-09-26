package com.h5tchibook.timeline.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.timeline.dao.GroupTimeLineDAO;
import com.h5tchibook.timeline.model.GroupTimeLine;

@Service
public class GroupTimeLineBO {
	@Autowired
	private GroupTimeLineDAO groupTimeLineDAO;
	
	
	public void createGroupTimeLine(GroupTimeLine groupTimeLine) {
		groupTimeLineDAO.insertGroupTimeLine(groupTimeLine);
	}
	
	public List<GroupTimeLine> getGroupTimeLineListByGroupIdList(List<Integer> groupIdList){
		return groupTimeLineDAO.selectGroupTimeLineListByGroupIdList(groupIdList);
	}
}
