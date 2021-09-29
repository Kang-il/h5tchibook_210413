package com.h5tchibook.comment.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.bo.GroupCommentAlertBO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.comment.dao.GroupCommentDAO;
import com.h5tchibook.comment.model.GroupComment;
import com.h5tchibook.comment.model.GroupCommentView;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.post.model.GroupPost;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class GroupCommentBO {
	@Autowired
	private UserBO userBO;	
	@Autowired
	private GroupCommentDAO groupCommentDAO;
	@Autowired
	private CheckBO checkBO;
	@Autowired
	private GroupCommentAlertBO groupCommentAlertBO;
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public Map<String,Boolean> createComment(User user, GroupComment groupComment ,GroupPost post){
		Map<String,Boolean> result= checkBO.createCommentCheckElements(user,groupComment,post);
		boolean resultCheck=false;
		//for 문을 무사 통과했다면 유효성 체크를 통과 한것.
		//loginCheck
		//commentBlankCheck
		//groupIdCheck
		//postidCheck
		for(String key : result.keySet()) {
			if(!result.get(key)) {
				result.put("result", resultCheck);
				return result;
			}
		}
		
		int row=groupCommentDAO.createGroupComment(groupComment);
		
		if(row!=0) {
			resultCheck=true;
			if(user.getId()!=post.getGroupMemberId()) {
				Alert alert=Alert.builder()
								 .sendUserId(user.getId())
								 .receiveUserId(post.getGroupMemberId())
								 .alertType(AlertType.GROUP_COMMENT)
								 .build();
				groupCommentAlertBO.createGroupCommentAlert(alert, groupComment.getGroupId(), groupComment.getId(), post.getId());
			}
		}
		
		result.put("result", resultCheck);
		return result;
	}
	
	public GroupComment getGroupCommentById(int id) {
		return groupCommentDAO.selectGroupCommentById(id);
	}
	
	public List<GroupCommentView> getGroupCommentViewListByPostId(int postId){
		List<GroupComment> groupCommentList=groupCommentDAO.selectGroupCommentListByPostId(postId);
		List<GroupCommentView> groupCommentViewList=new ArrayList<GroupCommentView>();
		
		if(groupCommentList!=null) {
			List<User> userList=getUserList(groupCommentList);
			
			for(GroupComment comment : groupCommentList) {
				
				for(User user : userList) {
					if(user.getId()!=comment.getMemberId()) {
						continue;
					}
					GroupCommentView groupCommentView=setGroupCommentView(user, comment);
					groupCommentViewList.add(groupCommentView);
					break;
				}
				
			}
		}
		logger.debug(":::::::::::::: groupCommentViewList"+groupCommentViewList);
		return groupCommentViewList;
	}
	
	private List<User> getUserList(List<GroupComment> groupCommentList){
		
		List<User> userList=null;
		
		if(groupCommentList!=null) {
			Set<Integer> userIdSet=new HashSet<Integer>();
			for(GroupComment groupComment : groupCommentList) {
				userIdSet.add(groupComment.getMemberId());
			}
			
			List<Integer> userIdList=new ArrayList<Integer>(userIdSet);
			
			
			userList=userBO.getUserListByIdList(userIdList);			
		}
		
		return userList;
	}
	
	public Map<String,Object> getGroupCommentViewListByPostId(User user,int postId){
		Map<String,Object> result=new HashMap<String,Object>();
		boolean loginCheck=false;
		boolean resultCheck=false;
		List<GroupComment> groupCommentList=null;
		List<GroupCommentView> groupCommentViewList=null;
		if(user!=null) {
			loginCheck=true;
			groupCommentList=groupCommentDAO.selectGroupCommentListByPostId(postId);
			if(groupCommentList!=null) {

				List<User> userList=getUserList(groupCommentList);
				groupCommentViewList=new ArrayList<GroupCommentView>();
				
				for(GroupComment groupComment : groupCommentList) {
					for(User member: userList) {
						if(groupComment.getMemberId()!=member.getId()) {
							continue;
						}
						
						GroupCommentView groupCommentView=setGroupCommentView(member, groupComment);
						
						groupCommentViewList.add(groupCommentView);
						break;
					}
				}
				resultCheck=true;
			}
			
		}
		
		result.put("groupCommentViewList", groupCommentViewList);
		result.put("loginCheck", loginCheck);
		result.put("result", resultCheck);
		return result;
	}
	
	public List<GroupCommentView> getGroupCommentViewListByPostIdList(List<Integer> postIdList){
		
		List<GroupComment> groupCommentList=groupCommentDAO.selectGroupCommentListByPostIdList(postIdList);			
		
		
		if(groupCommentList!=null) {
			Set<Integer> userIdSet=new HashSet<Integer>();
			List<Integer> userIdList=new ArrayList<Integer>();
			
			//유저의 아이디를 중복없이 저장
			for(GroupComment groupComment : groupCommentList) {
				userIdSet.add(groupComment.getMemberId());
			}
			
			for(Integer userId : userIdSet) {
				userIdList.add(userId);
			}
			
			List<User> userList=userBO.getUserListByIdList(userIdList);
			
			List<GroupCommentView> groupCommentViewList=new ArrayList<GroupCommentView>();
			
			for(GroupComment groupComment : groupCommentList ) {
				
				for(User user: userList) {
					
					if(groupComment.getMemberId()!=user.getId()) {
						continue;
					}
					
					GroupCommentView groupCommentView=setGroupCommentView(user, groupComment);
					
					groupCommentViewList.add(groupCommentView);
				}
			}
			
			return groupCommentViewList;

		}
		
		return null;
	}
	
	public Map<String,Boolean> deleteGroupCommentByCommentId(User user,GroupComment groupComment, GroupPost groupPost, Group group){
		//1. comment memberId 와 내 아이디 비교
		Map<String,Boolean> result=checkBO.deleteGroupCommentCheckElements(user,groupComment,groupPost,group);
		boolean validationCheck=false;
		boolean resultCheck=false;
		
		for(String key : result.keySet()) {
			if(result.get(key)) {
				// 유효성 체크 (포스트 주인인지, 그룹 주인인지, 코멘트 주인인지)
				// 이중 하나만 통과하더라도 지울 수 있는 권한이 있기 때문에 
				// 하나라도 true면 validationCheck에 true를 주고 for 문 break하여 빠져나옴
				validationCheck=true;
				break;
			}
		}
		
		if(validationCheck) {
			int row=groupCommentDAO.deleteGroupCommentById(groupComment.getId());
			if(row!=0) {
				resultCheck=true;
				//내 아이디와 포스트 아이디가 다를 경우에만 groupCommentAlert가 생성됨.
				
					groupCommentAlertBO.deleteGroupCommentAlertByGroupCommentId(groupComment.getId());
				
			}
		}
		result.put("result", resultCheck);
		
		return result;
	}
	
	public void deleteCommentByPostId(int postId) {
		List<GroupComment> commentList=groupCommentDAO.selectGroupCommentListByPostId(postId);
		if(commentList!=null) {
			groupCommentDAO.deleteGroupCommentByPostId(postId);
			groupCommentAlertBO.deleteGroupCommentAlertByPostid(postId);			
		}
	}
	
	public void deleteCommentByPostIdList(List<Integer>postIdList) {
		List<GroupComment> groupCommentList = groupCommentDAO.selectGroupCommentListByPostIdList(postIdList);
		
		if(groupCommentList!=null) {
			groupCommentDAO.deleteGroupCommentByPostIdList(postIdList);
			groupCommentAlertBO.deleteGroupCommentAlertByPostIdList(postIdList);
		}
	}
	
	private GroupCommentView setGroupCommentView(User user, GroupComment groupComment) {
		
		GroupCommentView groupCommentView=GroupCommentView
				.builder()
				.id(groupComment.getId())
				.groupId(groupComment.getGroupId())
				.postId(groupComment.getPostId())
				.memberId(groupComment.getMemberId())
				.comment(groupComment.getComment())
				.createdAt(groupComment.getCreatedAt())
				.updatedAt(groupComment.getUpdatedAt())
				.userLoginId(user.getLoginId())
				.userProfileImagePath(user.getProfileImagePath())
				.build();
		
		return groupCommentView;
	}
}
