package com.h5tchibook.alert.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.dao.LikeAlertDAO;

@Service
public class LikeAlertBO {
	@Autowired
	private LikeAlertDAO likeAlertDAO;
}
