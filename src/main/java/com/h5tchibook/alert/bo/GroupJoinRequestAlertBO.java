package com.h5tchibook.alert.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.GroupJoinRequestAlertDAO;

@Service
public class GroupJoinRequestAlertBO {
	@Autowired
	private GroupJoinRequestAlertDAO groupJoinRequestAlertDAO;
}
