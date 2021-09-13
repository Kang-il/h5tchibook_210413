package com.h5tchibook.post.bo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.comment.bo.CommentBO;
import com.h5tchibook.common.FileManagerService;
import com.h5tchibook.like.bo.LikeBO;
import com.h5tchibook.post.dao.UserPostDAO;
import com.h5tchibook.post.model.ContentType;
import com.h5tchibook.post.model.DisclosureStatus;
import com.h5tchibook.post.model.Post;
import com.h5tchibook.post.model.PostView;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class UserPostBO {
	
	@Autowired
	private UserPostDAO userPostDAO;
	@Autowired
	private UserBO userBO;
	@Autowired
	private CommentBO commentBO;
	@Autowired
	private LikeBO likeBO;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	Logger logger =LoggerFactory.getLogger(this.getClass());
	
	public int createUserPost(int userId
							,String userLoginId
							, String content
							, MultipartFile file 
							, String disclosureStatus) {
		
		String imageUrl=generateImageUrlByFile(userLoginId, file);
		
		DisclosureStatus disclosureType = null;
		
		if(disclosureStatus.equals("public")) {
			disclosureType=DisclosureStatus.PUBLIC;
		}else if(disclosureStatus.equals("friend")) {
			disclosureType=DisclosureStatus.FRIEND;
		}else if(disclosureStatus.equals("private")) {
			disclosureType=DisclosureStatus.PRIVATE;
		}
		
		ContentType contentType=null;
		
		if(imageUrl==null) {
			contentType=ContentType.TEXT;
		}else {
			contentType=ContentType.PHOTO;
		}
		
	Post userPost = Post.builder()				
			.userId(userId)				
			.content(content)
			.contentType(contentType)
			.contentPath(imageUrl)
			.disclosureStatus(disclosureType)				
			.build();
		
		
		return userPostDAO.insertUserPost(userPost);
	}
	
	private String generateImageUrlByFile(String userLogInId, MultipartFile file) {
		String imageUrl=null;
		if(file!=null) {
			try {
				imageUrl=fileManagerService.saveFile(userLogInId, file);
				logger.error(":::::::::::::::: 이미지 경루 ::::"+imageUrl);
			} catch (IOException e) {
				logger.error(":::::::::::::::: 이미지 업로드 오류 ::::"+e.getMessage());
			}
		}
		return imageUrl;
	}
	
	public Post getPostById(int postId) {
		return userPostDAO.selectPostById(postId);
	}
	
	public List<PostView> getPostListByUserId(int userId){
		List<Post> postList=userPostDAO.selectPostListByUserId(userId);
		List<PostView> postViewList=new ArrayList<PostView>();
		User user = userBO.getUserById(userId);
		for(Post userPost : postList) {
			PostView postView=PostView.builder()
					.id(userPost.getId())
					.userId(userPost.getUserId())
					.content(userPost.getContent())
					.contentPath(userPost.getContentPath())
					.disclosureStatus(userPost.getDisclosureStatus())
					.createdAt(userPost.getCreatedAt())
					.updatedAt(userPost.getUpdatedAt())
					.userLoginId(user.getLoginId())
					.userProfilePath(user.getProfileImagePath())
					.commentList(commentBO.getCommentListByPostId(userPost.getId()))
					.likeList(likeBO.getLikeListByPostId(userPost.getId()))
					.build();
			postViewList.add(postView);
		}
		return postViewList;
	}
	
	public List<Post> getPostListOnlyPublicByUserId(int userId){
		return null;
	}
	
	public List<Post> getPostListOnlyPhotoByUserId(int userId, Integer limit){
		return userPostDAO.selectPostListOnlyPhotoByUserId(userId,limit);
	}
	
	
}
