package com.h5tchibook.like.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.like.dao.LikeDAO;
import com.h5tchibook.like.model.Like;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Service
public class LikeBO {
	@Autowired
	private LikeDAO likeDAO;
	@Autowired
	private UserBO userBO;
	
	public void createLike(Like like) {
		likeDAO.insertLike(like);
	}
	
	public void deleteLikeByUserIdAndPostId(int userId, int PostId) {
		likeDAO.deleteLikeByUserIdAndPostId(userId, PostId);
	}
	public void deleteLikeByPostId(int postId) {
		likeDAO.deleteLikeByPostId(postId);
	}
	
	public Like getLikeByUserIdAndPostId(int userId,int postId) {
		return likeDAO.selectLikeByUserIdAndPostId(userId, postId);
	}
	
	public List<Like> getLikeListByPostId(int postId){
		List<Like> likeList=likeDAO.selectLikeListByPostId(postId);
		
		for(Like like : likeList) {
			User user=userBO.getUserById(like.getUserId());
			//like 객체의 부가적인 정보를 담아준다.
			like.setUserLoginId(user.getLoginId());
			like.setUserProfileImagePath(user.getProfileImagePath());
		}
		
		return likeList;
	}
}
