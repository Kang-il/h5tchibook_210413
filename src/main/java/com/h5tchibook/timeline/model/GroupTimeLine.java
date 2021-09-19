package com.h5tchibook.timeline.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupTimeLine {
	private int groupId;
	private int postId;
	private int userId;
	private Date createdAt;
}
