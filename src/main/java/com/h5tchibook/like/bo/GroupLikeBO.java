package com.h5tchibook.like.bo;


import java.util.ArrayList;	
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.bo.GroupLikeAlertBO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.like.dao.GroupLikeDAO;
import com.h5tchibook.like.model.GroupLike;
import com.h5tchibook.like.model.GroupLikeView;
import com.h5tchibook.post.model.GroupPost;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class GroupLikeBO {
	@Autowired
	private GroupLikeDAO groupLikeDAO;
	@Autowired
	private UserBO userBO;
	@Autowired
	private GroupLikeAlertBO groupLikeAlertBO;
	
	public Map<String,Boolean> setGroupLike(User user , int groupId, GroupPost post){
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		
		boolean loginCheck=false;
		boolean resultCheck=false;
		int row=0;
		if(user!=null) {
			loginCheck=true;
			
			GroupLike groupLike=groupLikeDAO.selectGroupLikeByPostIdAndMemberId(post.getId(), user.getId());
		
			if(groupLike!=null) {
				
				row=groupLikeDAO.deleteGroupLikeByPostIdAndMemberId(post.getId(), user.getId());
				groupLikeAlertBO.deleteGroupLikeAlertByLikeId(groupLike.getId());
				
			}else {
				groupLike=GroupLike
						.builder()
						.groupId(groupId)
						.memberId(user.getId())
						.postId(post.getId())
						.build();
				row=groupLikeDAO.insertGroupLike(groupLike);
				Alert alert=Alert.builder()
						 .sendUserId(user.getId())
						 .receiveUserId(post.getGroupMemberId())
						 .alertType(AlertType.GROUP_LIKE)
						 .build();
				groupLikeAlertBO.createGroupLikeAlert(alert, post.getId(), groupLike.getId(), groupId);
			}
			
			if(row!=0) {
				resultCheck=true;
			}
		}
		
		result.put("loginCheck", loginCheck);
		result.put("result", resultCheck);
		
		return result;
	}
	public void deleteGroupLikeByPostIdList(List<Integer> postIdList) {
		List<GroupLike> groupLikeList=groupLikeDAO.selectGroupLikeListByPostIdList(postIdList);
		if(groupLikeList!=null) {
			List<Integer> idList=new ArrayList<Integer>();
			
			groupLikeDAO.deleteGroupLikeByPostIdList(postIdList);
			for(GroupLike like : groupLikeList) {
				idList.add(like.getId());
			}
			if(idList.size()!=0) {
				groupLikeAlertBO.deleteGroupLikeAlertByLikeIdList(idList);				
			}
		}
	}
	
	public void deleteGroupLikeByPostId(int postId) {
		List<GroupLike>likeList=groupLikeDAO.selectGroupLikeByPostId(postId);
		if(likeList!=null) {
			groupLikeDAO.deleteGroupLikeByPostId(postId);
			groupLikeAlertBO.deleteGroupLikeAlertByPostId(postId);			
		}
	}
	
	public List<GroupLikeView> getGroupLikeViewListByPostId(int postId){
		List<GroupLike> likeList=groupLikeDAO.selectGroupLikeByPostId(postId);
		List<GroupLikeView> likeViewList=new ArrayList<GroupLikeView>();
		if(likeList!=null) {
			List<User> userList=getUserList(likeList);
			for(GroupLike like : likeList) {
				for(User user : userList) {
					if(user.getId()!=like.getMemberId()) {
						continue;
					}
					GroupLikeView likeView=setGroupLikeView(user, like);
					likeViewList.add(likeView);
					break;
				}
			}
		}
		
		return likeViewList;
	}
	
	
	
	public List<GroupLikeView> getGroupLikeViewListByPostIdList(List<Integer> postIdList){
		//????????? ????????????????????? ???????????? ????????? ?????? 
		List<GroupLike> groupLikeList=groupLikeDAO.selectGroupLikeListByPostIdList(postIdList);
		// ???????????? ???????????? ?????? ????????? ???????????? ?????? ?????? ??????
		// set??? ???????????? ????????? ????????? ???.
		if(groupLikeList!=null) {
			
			Set<Integer> groupMemberIdSet=new HashSet<Integer>();
			
			for(GroupLike groupLike : groupLikeList) {
				//????????????
				groupMemberIdSet.add(groupLike.getMemberId());
			}
			
			List<Integer> groupMemberIdList=new ArrayList<Integer>();
			
			for(Integer groupMemberId : groupMemberIdSet) {
				groupMemberIdList.add(groupMemberId);
			}
			
			List<User> userList=userBO.getUserListByIdList(groupMemberIdList);
			
			List<GroupLikeView> groupLikeViewList=new ArrayList<GroupLikeView>();
			
			for(GroupLike groupLike : groupLikeList) {
				for(User user : userList) {
					if(groupLike.getMemberId()!=user.getId()) {
						continue;
					}
					
					GroupLikeView groupLikeView=setGroupLikeView(user, groupLike);
					
					groupLikeViewList.add(groupLikeView);
					break;
				}
			}
			
			return groupLikeViewList;
		}
		return null;
	}
	
	
	private List<User> getUserList(List<GroupLike> likeList){
		List<User> userList =null;
		if(likeList!=null) {
			Set<Integer> userIdSet=new HashSet<Integer>();
			for(GroupLike like:likeList) {
				userIdSet.add(like.getMemberId());
			}
			List<Integer> userIdList=new ArrayList<Integer>(userIdSet);
			userList=userBO.getUserListByIdList(userIdList);
		}
		return userList;
	}
	
	private GroupLikeView setGroupLikeView(User user , GroupLike groupLike) {
		GroupLikeView groupLikeView=GroupLikeView
				.builder()
				.id(groupLike.getId())
				.groupId(groupLike.getGroupId())
				.postId(groupLike.getPostId())
				.memberId(groupLike.getMemberId())
				.createdAt(groupLike.getCreatedAt())
				.userLoginId(user.getLoginId())
				.userProfileImagePath(user.getProfileImagePath())
				.build();
		
		return groupLikeView;
	}
}
