package com.h5tchibook.group.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupMemberView {
	private int id;
	private int groupId;
	private int groupMemberId;
	
	private String groupMemberLoginId;
	private String groupMemberProfileImagePath;
	
	private Date createdAt;
}
