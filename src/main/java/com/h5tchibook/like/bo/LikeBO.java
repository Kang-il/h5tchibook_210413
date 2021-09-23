package com.h5tchibook.like.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.like.dao.LikeDAO;
import com.h5tchibook.like.model.Like;
import com.h5tchibook.like.model.LikeView;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class LikeBO {
	@Autowired
	private LikeDAO likeDAO;
	@Autowired
	private UserBO userBO;
	
	public void deleteLikeByPostId(int postId) {
		likeDAO.deleteLikeByPostId(postId);
	}
	
	public Map<String,Boolean> setLike(User user,int postId){
		
		Map<String, Boolean> result=new HashMap<String,Boolean>();
		boolean loginCheck=false;
		boolean resultCheck=false;
		
		int row=0;
		
		if(user!=null) {
			
			loginCheck=true;
			
			Like like=likeDAO.selectLikeByUserIdAndPostId(user.getId(), postId);
			
			if(like!=null) {
				row=likeDAO.deleteLikeByUserIdAndPostId(user.getId(), postId);
			}else {
				
				like=Like.builder()
						.userId(user.getId())
						.postId(postId)
						.build();
				row = likeDAO.insertLike(like);
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
