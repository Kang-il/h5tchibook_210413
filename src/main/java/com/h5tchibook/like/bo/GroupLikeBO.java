package com.h5tchibook.like.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.like.dao.GroupLikeDAO;

@Service
public class GroupLikeBO {
	@Autowired
	private GroupLikeDAO groupLikeDAO;
}
