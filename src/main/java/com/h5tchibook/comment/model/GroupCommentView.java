package com.h5tchibook.comment.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupCommentView {
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
