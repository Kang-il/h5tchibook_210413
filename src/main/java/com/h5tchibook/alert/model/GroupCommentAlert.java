package com.h5tchibook.alert.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class GroupCommentAlert implements AlertTimeLine{
	private int id;
	private int alertId;
	private int groupId;
	private int postId;
	private int commentId;
	private Date createdAt;
}
