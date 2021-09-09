package com.h5tchibook.friend.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Friend {
	private int id;
	private int userId;
	private int friendId;
	private Date createdAt;
	private Date updatedAt;
	
	private String friendLoginId;
	private String friendProfileImagePath;
}
