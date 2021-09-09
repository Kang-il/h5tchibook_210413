package com.h5tchibook.post.model;

import java.util.Date;
import java.util.List;

import com.h5tchibook.comment.model.Comment;
import com.h5tchibook.like.model.Like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPost {
	private int id;
	private int userId;
	private String content;
	private ContentType contentType;
	private String contentPath;
	private DisclosureStatus disclosureStatus;
	private Date createdAt;
	private Date updatedAt;
	
	
	private String userLoginId;
	private String userProfilePath;
	private List<Comment> commentList;
	private List<Like> likeList;
}
