package com.h5tchibook.timeline.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.timeline.dao.GroupTimeLineDAO;

@Service
public class GroupTimeLineBO {
	@Autowired
	private GroupTimeLineDAO groupTimeLineDAO;
}
