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
import com.h5tchibook.friend.bo.FriendBO;
import com.h5tchibook.like.bo.LikeBO;
import com.h5tchibook.post.dao.UserPostDAO;
import com.h5tchibook.post.model.ContentType;
import com.h5tchibook.post.model.DisclosureStatus;
import com.h5tchibook.post.model.Post;
import com.h5tchibook.post.model.PostView;
import com.h5tchibook.timeline.bo.UserTimeLineBO;
import com.h5tchibook.timeline.model.UserTimeLine;
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
	private UserTimeLineBO userTimeLineBO;
	@Autowired
	private FriendBO friendBO;
	
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
		
		int row=userPostDAO.insertUserPost(userPost);
		UserTimeLine userTimeLine=UserTimeLine.builder()
											  .postId(userPost.getId())
											  .userId(userId)
											  .build();
		
		userTimeLineBO.createdUserTimeLine(userTimeLine);
		return row;
	}
	
	public void deletePostById(int postId) {
		Post post=userPostDAO.selectPostById(postId);
		userPostDAO.deleteUserPostById(postId);
		if(post.getContentPath()!=null) {
			try {
				fileManagerService.deleteFile(post.getContentPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		commentBO.deleteCommentByPostId(postId);
		likeBO.deleteLikeByPostId(postId);
	}
	
	public Post getPostById(int postId) {
		
		return userPostDAO.selectPostById(postId);
	}
	
	public PostView getPostViewById(int postId) {
		
		Post userPost=userPostDAO.selectPostById(postId);
		PostView postView=null;
		if(userPost!=null) {
			User user=userBO.getUserById(userPost.getUserId());
			postView=setPostView(userPost,user);
		}
		
		return postView;
	}

	public List<PostView> getPostListByUserId(int userId, DisclosureStatus disclosureStatus){
		
		List<Post> postList=userPostDAO.selectPostListByUserId(userId,disclosureStatus.getDisclosureStatus());
		logger.debug(":::::::::::::::::::::::::::::::::::::::::"+disclosureStatus.getDisclosureStatus());
		
		
		List<PostView> postViewList=new ArrayList<PostView>();
		User user = userBO.getUserById(userId);
		for(Post userPost : postList) {
			
			PostView postView=setPostView(userPost, user);
			
			postViewList.add(postView);
		}
		return postViewList;
	}
	
	public List<Post> getPostListOnlyPhotoByUserId(int userId, Integer limit, DisclosureStatus disclosureStatus){
		
		List<Post> photoList=userPostDAO.selectPostListOnlyPhotoByUserId(userId, limit, disclosureStatus.getDisclosureStatus());

		return photoList;
	}
	
	public List<PostView> getPostListByUserIdAndTimeLineList(int userId){
		//????????? ????????? ???????????? ?????????
		List<Integer> friendIdList=friendBO.getFriendIdListByUserid(userId);
		//?????? ???????????? ???????????? ?????? ???????????? ????????? ???????????? ????????? ?????????.
		friendIdList.add(userId);
		//?????????????????? ?????? ????????? ???????????? ?????????
		List<UserTimeLine> timeLineList=userTimeLineBO.getUserTimeLineListByUserIdList(friendIdList);
		
		List<Integer> postIdList=new ArrayList<Integer>();
		
		//????????? ???????????? ??????.
		for(UserTimeLine timeLine:timeLineList) {
			postIdList.add(timeLine.getPostId());
		}
		
		//????????? ????????? ???????????? ????????? ???????????? ?????????
		//????????? ????????? ???????????? 0??????(??????????????? ????????? ???????????? ??????.) ?????? ????????? ?????? ??? ???????????? null??? ??????????????? ??????.
		if(postIdList.size()!=0) {
			List<Post> postList = userPostDAO.selectPostByPostIdList(postIdList);
			List<PostView> postViewList=new ArrayList<PostView>();
			
			//????????? ????????? ????????? ????????? view??? ????????? postView????????? ??????
			for(Post userPost : postList) {
				User user=userBO.getUserById(userPost.getUserId());
				PostView postView=setPostView(userPost, user);
				postViewList.add(postView);
			}
			//???????????? ????????? ?????? ??????????????? ???????????? PostView ????????? ???????????? ????????????.
			return postViewList;
		}
		
		return null;
	}
	
	//postView????????? ???????????? ?????????
	private PostView setPostView(Post userPost, User user) {
		PostView postView=PostView.builder()
				  .id(userPost.getId())
				  .userId(userPost.getUserId())
				  .contentType(userPost.getContentType())
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
		return postView;
	}
	
	private String generateImageUrlByFile(String userLogInId, MultipartFile file) {
		String imageUrl=null;
		if(file!=null) {
			try {
				imageUrl=fileManagerService.saveFile(userLogInId, file);
				logger.error(":::::::::::::::: ????????? ?????? ::::"+imageUrl);
			} catch (IOException e) {
				logger.error(":::::::::::::::: ????????? ????????? ?????? ::::"+e.getMessage());
			}
		}
		return imageUrl;
	}
}
