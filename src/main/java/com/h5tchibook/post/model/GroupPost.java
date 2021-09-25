package com.h5tchibook.post.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupPost {
	private int id;
	private int groupMemberId;
	private int groupId;
	private String content;
	private ContentType contentType;
	private String contentPath;
	private Date createdAt;
	private Date updatedAt;
}
