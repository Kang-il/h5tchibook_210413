package com.h5tchibook.like.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeView {
	private int id;
	private int userId;
	private int postId;
	private Date createdAt;
	
	private String userLoginId;
	private String userProfileImagePath;
}
