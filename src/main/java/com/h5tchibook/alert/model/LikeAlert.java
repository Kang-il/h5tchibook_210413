package com.h5tchibook.alert.model;

import java.util.Date;	

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class LikeAlert implements AlertTimeLine{
	//likeAlert내용
	private int id;
	private int alertId;
	private int postId;
	private int likeId;
	private Date createdAt;
}
