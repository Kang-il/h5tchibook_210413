package com.h5tchibook.post.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPost {
	private int id;
	private int userId;
	private String content;
	private ContentType contentType;
	private String contentPath;
	private DisclosureStatus disclosureStatus;
	private Date createdAt;
	private Date updatedAt;
}
