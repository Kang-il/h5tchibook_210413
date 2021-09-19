package com.h5tchibook.comment.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupComment {
	private int id;
	private int groupId;
	private int memberId;
	private int postId;
	private String comment;
	private Date createdAt;
	private Date updatedAt;
	
	private String userLoginId;
	private String userProfileImagePath;
}
