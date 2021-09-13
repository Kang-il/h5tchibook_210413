package com.h5tchibook.post.model;

import java.util.Date;
import java.util.List;

import com.h5tchibook.comment.model.CommentView;
import com.h5tchibook.like.model.LikeView;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostView {
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
	private List<CommentView> commentList;
	private List<LikeView> likeList;
}
