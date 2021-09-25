package com.h5tchibook.like.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupLikeView {
	private int id;
	private int groupId;
	private int memberId;
	private int postId;
	private Date createdAt;
	
	private String userLoginId;
	private String userProfileImagePath;
}
