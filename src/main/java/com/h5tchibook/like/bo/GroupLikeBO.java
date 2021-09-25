package com.h5tchibook.like.bo;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.like.dao.GroupLikeDAO;
import com.h5tchibook.like.model.GroupLike;
import com.h5tchibook.like.model.GroupLikeView;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class GroupLikeBO {
	@Autowired
	private GroupLikeDAO groupLikeDAO;
	@Autowired
	private UserBO userBO;
	
	public List<GroupLikeView> getGroupLikeViewListByPostIdList(List<Integer> postIdList){
		//포스트 아이디리스트로 라이크를 받아올 경우 
		List<GroupLike> groupLikeList=groupLikeDAO.selectGroupLikeListByPostIdList(postIdList);
		// 여러개의 포스트에 같은 유저가 라이크를 했을 경우 있음
		// set을 이용하여 중복을 제거할 것.
		if(groupLikeList!=null) {
			Set<Integer> groupMemberIdSet=new HashSet<Integer>();
			
			for(GroupLike groupLike : groupLikeList) {
				//중복제거
				groupMemberIdSet.add(groupLike.getId());
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
					
					groupLikeViewList.add(groupLikeView);
				}
			}
			
			return groupLikeViewList;
		}
		return null;
	}
}
