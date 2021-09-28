package com.h5tchibook.alert.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.GroupLikeAlertDAO;

@Service
public class GroupLikeAlertBO {
	@Autowired
	private GroupLikeAlertDAO groupLikeAlertDAO;
}
