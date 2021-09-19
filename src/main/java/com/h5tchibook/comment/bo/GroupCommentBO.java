package com.h5tchibook.comment.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.comment.dao.GroupCommentDAO;

@Service
public class GroupCommentBO {
	@Autowired
	private GroupCommentDAO groupCommentDAO;
}
