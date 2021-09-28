package com.h5tchibook.like.bo;

import java.util.ArrayList;	
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.alert.bo.LikeAlertBO;
import com.h5tchibook.alert.model.Alert;
import com.h5tchibook.alert.model.AlertType;
import com.h5tchibook.like.dao.LikeDAO;
import com.h5tchibook.like.model.Like;
import com.h5tchibook.like.model.LikeView;
import com.h5tchibook.post.model.Post;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class LikeBO {
	@Autowired
	private LikeDAO likeDAO;
	@Autowired
	private UserBO userBO;
	@Autowired
	private LikeAlertBO likeAlertBO;
	
	public void deleteLikeByPostId(int postId) {
		likeDAO.deleteLikeByPostId(postId);
	}
	
	public Map<String,Boolean> setLike(User user,Post post){
		
		Map<String, Boolean> result=new HashMap<String,Boolean>();
		boolean loginCheck=false;
		boolean resultCheck=false;
		
		int row=0;
		
		if(user!=null) {
			
			loginCheck=true;
			
			Like like=likeDAO.selectLikeByUserIdAndPostId(user.getId(), post.getId());
			if(like!=null) {
				row=likeDAO.deleteLikeByUserIdAndPostId(user.getId(), post.getId());
				
				//보내는이 받는이가 다르경우에만 Alert가 생성되어있음
				if(user.getId()!=post.getUserId()) {
					//userId postId
					likeAlertBO.deleteLikeAlertByLikeId(like.getId());
				}
				
			}else {
				
				like=Like.builder()
						.userId(user.getId())
						.postId(post.getId())
						.build();
				row = likeDAO.insertLike(like);
				
				//보내는이와 받는이가 다를 경우에만 알람 생성
				if(user.getId()!=post.getUserId()) {
					
					Alert alert=Alert.builder()
							.sendUserId(user.getId())
							.receiveUserId(post.getUserId())
							.alertType(AlertType.LIKE)
							.build();
					
					likeAlertBO.createLikeAlert(alert , post.getId() , like.getId());
				}
			}
			
			if(row!=0) {
				resultCheck=true;
			}
		}
		
		
		result.put("loginCheck",loginCheck);
		result.put("result",resultCheck);
		return result;
	}
	
	public List<LikeView> getLikeListByPostId(int postId){
		List<Like> likeList=likeDAO.selectLikeListByPostId(postId);
		List<LikeView> likeViewList=new ArrayList<LikeView>();
		for(Like like : likeList) {
			User user=userBO.getUserById(like.getUserId());
			LikeView likeView = LikeView.builder()
					.id(like.getId())
					.userId(like.getUserId())
					.postId(like.getPostId())
					.createdAt(like.getCreatedAt())
					.userLoginId(user.getLoginId())
					.userProfileImagePath(user.getProfileImagePath())
					.build();
			//like 객체의 부가적인 정보를 담아준다.
			likeViewList.add(likeView);
		}
		
		return likeViewList;
	}
}
