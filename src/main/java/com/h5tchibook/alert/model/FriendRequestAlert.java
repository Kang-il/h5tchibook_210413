package com.h5tchibook.alert.model;


import java.util.Date;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FriendRequestAlert implements AlertTimeLine{
	private int id;
	private int alertId;
	private Date createdAt;
}
