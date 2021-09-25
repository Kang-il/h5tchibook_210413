package com.h5tchibook.like.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.like.model.Like;

@Repository
public interface LikeDAO {
	public int insertLike(Like like);
	public int deleteLikeByUserIdAndPostId(@Param("userId") int userId
											,@Param("postId") int postId
											);
	public void deleteLikeByPostId(@Param("postId") int postId);
	public Like selectLikeByUserIdAndPostId(@Param("userId")int userId
									  , @Param("postId")int postId
									  );
	public List<Like> selectLikeListByPostId(@Param("postId") int postId);
}
