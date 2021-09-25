package com.h5tchibook.post.model;

import java.util.Date;	
import java.util.List;


import com.h5tchibook.comment.model.GroupCommentView;
import com.h5tchibook.like.model.GroupLikeView;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupPostView {
	private int id;
	private int groupMemberId;
	private int groupId;
	private String content;
	private ContentType contentType;
	private String contentPath;
	private Date createdAt;
	private Date updatedAt;
	
	private String groupName;
	private String userLoginId;
	private String userProfilePath;
	
	private List<GroupCommentView> commentList;
	private List<GroupLikeView> likeList;
}
