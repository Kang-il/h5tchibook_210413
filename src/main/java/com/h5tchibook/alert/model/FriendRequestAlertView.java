package com.h5tchibook.alert.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRequestAlertView implements AlertTimeLineView{
	// alert 내용
	private int alertId;
	private int sendUserId;
	private int receiveUserId;
	private AlertType alertType;

	// likeAlert내용
	private int id;
	private Date createdAt;

	// 추가사항
	private String sendUserLoginId;
	private String sendUserProfileImagePath;
}
