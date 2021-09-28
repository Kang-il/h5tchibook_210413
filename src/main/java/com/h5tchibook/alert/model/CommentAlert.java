package com.h5tchibook.alert.model;

import java.util.Date;	

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentAlert implements AlertTimeLine{
	
	//alert Table 정보
	private int alertId;
	private int sendUserId;
	private int receiveUserId;
	private AlertType alertType;
	
	//commentAlert 정보
	private int id;
	private int commentId;
	private int postId;
	
	private Date createdAt;
	
	//추가사항 
	private String sendUserLoginId;
	private String sendUserProfileImagePath;
	private String postImagePath;
	private String comment;
}
