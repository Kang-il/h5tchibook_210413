package com.h5tchibook.timeline.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserTimeLine {
	private int id;
	private int postId;
	private int userId;
	private Date createdAt;
}
