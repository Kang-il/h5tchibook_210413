package com.h5tchibook.comment.model;


import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Builder
public class Comment {
	private int id;
	private int userId;
	private int postId;
	private String comment;
	private Date createdAt;
	private Date updatedAt;
}
