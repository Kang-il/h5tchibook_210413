package com.h5tchibook.group.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupJoinRequestView {
	private int id;
	private int groupId;
	private int userId;
	private Date createdAt;
	
	private String userLoginId;
	private String userProfileImagePath;
}
