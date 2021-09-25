package com.h5tchibook.group.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Group {
	private int id;
	private int groupManagerId;
	private String groupName;
	private String groupProfileImagePath;
	private String groupCoverImagePath;
	private Date createdAt;
	private Date updatedAt;

}
