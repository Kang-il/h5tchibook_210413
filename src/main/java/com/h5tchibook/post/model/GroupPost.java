package com.h5tchibook.post.model;

import java.util.Date;
import java.util.List;

import com.h5tchibook.comment.model.GroupComment;
import com.h5tchibook.like.model.GroupLike;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupPost {
	private int id;
	private int groupMemberId;
	private int groupId;
	private ContentType contentType;
	private String contentPath;
	private Date createdAt;
	private Date updatedAt;
	
	private String userLoginId;
	private String userProfilePath;
	
	private List<GroupComment> commentList;
	private List<GroupLike> likeList;
}
