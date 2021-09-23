package com.h5tchibook.group.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupMember {
	private int id;
	private int groupId;
	private int groupMemberId;
	private Date createdAt;
}
