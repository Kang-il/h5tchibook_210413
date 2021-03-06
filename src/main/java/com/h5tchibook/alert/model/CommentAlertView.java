package com.h5tchibook.alert.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentAlertView implements AlertTimeLineView{
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
