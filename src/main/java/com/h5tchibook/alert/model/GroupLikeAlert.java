package com.h5tchibook.alert.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GroupLikeAlert implements AlertTimeLine{
	private int id;
	private int groupId;
	private int postId;
	private int likeId;
	private Date createdAt;
}
