package com.h5tchibook.friend.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendView {
	private int id;
	private int userId;
	private int friendId;
	private Date createdAt;
	private Date updatedAt;
	
	private String friendLoginId;
	private String friendProfileImagePath;
}
