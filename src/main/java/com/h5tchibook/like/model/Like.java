package com.h5tchibook.like.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {
	private int id;
	private int userId;
	private int postId;
	private Date createdAt;
	
	private String userLoginId;
	private String userProfileImagePath;
}
