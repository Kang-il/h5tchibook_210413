package com.h5tchibook.like.model;

import java.util.Date;	

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class GroupLike {
	private int id;
	private int groupId;
	private int memberId;
	private int postId;
	private Date createdAt;
}
