package com.h5tchibook.alert.bo;

import java.util.ArrayList;	
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertTimeLineView;
import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.alert.model.CommentAlert;
import com.h5tchibook.alert.model.CommentAlertView;
import com.h5tchibook.alert.model.FriendRequestAlert;
import com.h5tchibook.alert.model.FriendRequestAlertView;
import com.h5tchibook.alert.model.GroupCommentAlert;
import com.h5tchibook.alert.model.GroupCommentAlertView;
import com.h5tchibook.alert.model.GroupJoinRequestAlert;
import com.h5tchibook.alert.model.GroupJoinRequestAlertView;
import com.h5tchibook.alert.model.GroupLikeAlert;
import com.h5tchibook.alert.model.GroupLikeAlertView;
import com.h5tchibook.alert.model.LikeAlert;
import com.h5tchibook.alert.model.LikeAlertView;
import com.h5tchibook.comment.bo.CommentBO;
import com.h5tchibook.comment.bo.GroupCommentBO;
import com.h5tchibook.comment.model.Comment;
import com.h5tchibook.comment.model.GroupComment;
import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.post.bo.GroupPostBO;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.GroupPost;
import com.h5tchibook.post.model.Post;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;


@Service
public class AlertTimeLineBO {
	@Autowired
	private AlertBO alertBO;
	@Autowired
	private CommentAlertBO commentAlertBO;
	@Autowired
	private FriendRequestAlertBO friendRequestAlertBO;
	@Autowired
	private GroupCommentAlertBO groupCommentAlertBO;
	@Autowired
	private GroupJoinRequestAlertBO groupJoinRequestAlertBO;
	@Autowired
	private GroupLikeAlertBO groupLikeAlertBO;
	@Autowired
	private LikeAlertBO likeAlertBO;
	@Autowired
	private UserBO userBO;
	@Autowired
	private UserPostBO userPostBO;
	@Autowired
	private GroupPostBO groupPostBO;
	@Autowired
	private CommentBO commentBO;
	@Autowired
	private GroupCommentBO groupCommentBO;
	@Autowired
	private GroupBO groupBO;
	
	public List<AlertTimeLineView> getAlertTimelineViewByUserId(int userId){
		List<Alert> alertList=alertBO.getAlertListBySendUserId(userId);
		List<AlertTimeLineView> alertTimeLineViewList=null;
		
		if(alertList!=null) {
			Set<Integer> userIdSet=new HashSet<Integer>();
			
			alertTimeLineViewList=new ArrayList<AlertTimeLineView>();
			
			for(Alert alert : alertList) {
				userIdSet.add(alert.getSendUserId());
			}
			
			List<User> userList=null;
			if(userIdSet.size()!=0) {
				List<Integer> userIdList=new ArrayList<Integer>(userIdSet);
				userList=userBO.getUserListByIdList(userIdList);
			}
			
			for(Alert alert: alertList) {
				AlertType type =alert.getAlertType();
				User sendUser = getSendUserByUserList(userList, alert);
				
				
				if(type == AlertType.COMMENT) {
					
					CommentAlert commentAlert=commentAlertBO.getCommentAlertByAlertId(alert.getId());
					
					if(commentAlert!=null) {
						
						Comment comment = commentBO.getCommentById(commentAlert.getCommentId());
						Post post=userPostBO.getPostById(commentAlert.getPostId());
						
						CommentAlertView view = setCommentAlertView(alert, type, commentAlert, sendUser, comment, post);
						
						alertTimeLineViewList.add(view);
					}
				}else if(type == AlertType.FRIEND_REQUEST) {
					FriendRequestAlert friendRequestAlert=friendRequestAlertBO.getFriendRequestAlertByAlertId(alert.getId());
					
					if(friendRequestAlert!=null) {
	
						FriendRequestAlertView view = setFriendRequestAlert(alert, type, friendRequestAlert, sendUser);
						
						alertTimeLineViewList.add(view);
					}
				}else if(type == AlertType.GROUP_COMMENT) {
					GroupCommentAlert groupCommentAlert=groupCommentAlertBO.getGroupCommentAlertByAlertId(alert.getId());
					
					if(groupCommentAlert!=null) {
						
						GroupPost post=groupPostBO.getGroupPostById(groupCommentAlert.getPostId());
						GroupComment comment=groupCommentBO.getGroupCommentById(groupCommentAlert.getCommentId());
						Group group=groupBO.getGroupById(groupCommentAlert.getGroupId());
						
						GroupCommentAlertView view=setGroupCommentAlertView(alert,type,groupCommentAlert,sendUser,group,post,comment);
						
						alertTimeLineViewList.add(view);
					}
				}else if(type == AlertType.GROUP_JOIN_REQUEST) {
					GroupJoinRequestAlert groupJoinRequestAlert=groupJoinRequestAlertBO.getGroupJoinRequestAlertByAlertId(alert.getId());
					
					if(groupJoinRequestAlert!=null) {
						
						Group group=groupBO.getGroupById(groupJoinRequestAlert.getGroupId());
						
						GroupJoinRequestAlertView view=setGroupJoinReqeustAlertView(alert, type, groupJoinRequestAlert, sendUser, group);
						
						
						alertTimeLineViewList.add(view);
					}
				}else if(type == AlertType.GROUP_LIKE) {
					GroupLikeAlert groupLikeAlert=groupLikeAlertBO.getGroupLikeAlertByAlertId(alert.getId());
					
					if(groupLikeAlert!=null) {
						Group group=groupBO.getGroupById(groupLikeAlert.getGroupId());
						GroupPost post=groupPostBO.getGroupPostById(groupLikeAlert.getPostId());
						
						GroupLikeAlertView view=setGroupLikeAlertView(alert,type,groupLikeAlert,sendUser,group,post);
						
						alertTimeLineViewList.add(view);
						
					}
				}else if(type == AlertType.LIKE) {
					LikeAlert likeAlert=likeAlertBO.getLikeAlertByAlertId(alert.getId());
					
					if(likeAlert!=null) {
						Post post=userPostBO.getPostById(likeAlert.getPostId());
						LikeAlertView view=setLikeAlertView(alert, type, likeAlert, sendUser, post);
						
						alertTimeLineViewList.add(view);
					}
					
				}
				
				
			}
		}
		return alertTimeLineViewList;
	}
	
	private User getSendUserByUserList(List<User> userList , Alert alert) {
		
		User sendUser=null;
		for(User user : userList) {
			if(user.getId()==alert.getSendUserId()) {
				sendUser=user;
			}
		}
		return sendUser;
	}
	
	private LikeAlertView setLikeAlertView(Alert alert, AlertType type, LikeAlert likeAlert, User user, Post post) {
		LikeAlertView view=LikeAlertView.builder()
										.id(likeAlert.getId())
										.alertId(alert.getId())
										.sendUserId(alert.getSendUserId())
										.receiveUserId(alert.getReceiveUserId())
										.alertType(type)
										.postId(likeAlert.getPostId())
										.likeId(likeAlert.getLikeId())
										.createdAt(likeAlert.getCreatedAt())
										.sendUserLoginId(user.getLoginId())
										.sendUserProfileImagePath(user.getProfileImagePath())
										.postImagePath(post.getContentPath())
										.build();
		
		return view;
	}
	
	private GroupLikeAlertView setGroupLikeAlertView(Alert alert, AlertType type, GroupLikeAlert groupLikeAlert, User user, Group group, GroupPost post) {
		GroupLikeAlertView view=GroupLikeAlertView.builder()
												  .id(groupLikeAlert.getId())
												  .alertId(alert.getId())
												  .sendUserId(alert.getSendUserId())
												  .receiveUserId(alert.getReceiveUserId())
												  .alertType(type)
												  .postId(groupLikeAlert.getPostId())
												  .likeId(groupLikeAlert.getLikeId())
												  .createdAt(groupLikeAlert.getCreatedAt())
												  .sendUserLoginId(user.getLoginId())
												  .sendUserProfileImagePath(user.getProfileImagePath())
												  .postImagePath(post.getContentPath())
												  .build();
		return view;
	}
	
	private GroupJoinRequestAlertView setGroupJoinReqeustAlertView(Alert alert, AlertType type, GroupJoinRequestAlert groupJoinRequestAlert, User user, Group group) {
		GroupJoinRequestAlertView view=GroupJoinRequestAlertView.builder()
																.id(groupJoinRequestAlert.getId())
																.alertId(alert.getId())
																.sendUserId(alert.getSendUserId())
																.receiveUserId(alert.getReceiveUserId())
																.alertType(type)
																.groupId(group.getId())
																.createdAt(groupJoinRequestAlert.getCreatedAt())
																.sendUserLoginId(user.getLoginId())
																.sendUserProfileImagePath(user.getProfileImagePath())
																.build();
		return view;
		
	}
	private GroupCommentAlertView setGroupCommentAlertView(Alert alert, AlertType type, GroupCommentAlert groupCommentAlert,User user, Group group, GroupPost post, GroupComment comment) {
		GroupCommentAlertView view=GroupCommentAlertView.builder()
											  .id(groupCommentAlert.getId())
											  .alertId(alert.getId())
											  .sendUserId(alert.getSendUserId())
											  .receiveUserId(alert.getReceiveUserId())
											  .alertType(type)
											  .groupId(groupCommentAlert.getId())
											  .postId(post.getId())
											  .commentId(comment.getId())
											  .createdAt(groupCommentAlert.getCreatedAt())
											  .sendUserLoginId(user.getLoginId())
											  .sendUserProfileImagePath(user.getProfileImagePath())
											  .postImagePath(post.getContentPath())
											  .comment(comment.getComment())
											  .groupName(group.getGroupName())
											  .build();
		return view;
	}
	
	private FriendRequestAlertView setFriendRequestAlert(Alert alert , AlertType type, FriendRequestAlert friendRequestAlert, User user) {
		
		FriendRequestAlertView view = FriendRequestAlertView.builder()
															.id(friendRequestAlert.getId())
															.alertId(alert.getId())
															.sendUserId(alert.getSendUserId())
															.receiveUserId(alert.getReceiveUserId())
															.alertType(type)
															.createdAt(friendRequestAlert.getCreatedAt())
															.sendUserLoginId(user.getLoginId())
															.sendUserProfileImagePath(user.getProfileImagePath())
															.build();
		
		return view;
	}
	
	private CommentAlertView setCommentAlertView(Alert alert, AlertType type, CommentAlert commentAlert, User user, Comment comment , Post post) {
		
		CommentAlertView view=CommentAlertView.builder()
											  .id(commentAlert.getAlertId())
											  .alertId(commentAlert.getAlertId())
											  .commentId(commentAlert.getCommentId())
											  .postId(commentAlert.getPostId())
											  .createdAt(commentAlert.getCreatedAt())
											  .sendUserId(alert.getSendUserId())
											  .receiveUserId(alert.getReceiveUserId())
											  .alertType(type)
											  .sendUserLoginId(user.getLoginId())
											  .sendUserProfileImagePath(user.getProfileImagePath())
											  .postImagePath(post.getContentPath())
											  .comment(comment.getComment()).build();
		return view;
				
	}
}

