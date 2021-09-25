package com.h5tchibook.group.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupJoinRequest {
	private int id;
	private int groupId;
	private int userId;
	private Date createdAt;
}
