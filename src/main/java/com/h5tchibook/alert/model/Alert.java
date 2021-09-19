package com.h5tchibook.alert.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Alert {
	private int id;
	private int sendUserId;
	private int receiveUserId;
	private AlertType alertType;
	private Date createdAt;
}
