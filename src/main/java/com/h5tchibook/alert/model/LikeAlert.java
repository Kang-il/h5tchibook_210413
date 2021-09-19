package com.h5tchibook.alert.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeAlert {
	//alert 내용
	private int alertId;
	private int sendUserId;
	private int receiveUserId;
	private AlertType alertType;
	
	//likeAlert내용
	private int id;
	private int postId;
	private int likeId;
	private Date createdAt;
	
	//추가사항 
	private String sendUserLoginId;
	private String sendUserProfileImagePath;
	private String postImagePath;

}
