package com.h5tchibook.like.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.like.model.Like;

@Repository
public interface LikeDAO {
	public void insertLike(Like like);
	public void deleteLikeByUserIdAndPostId(@Param("userId") int id
											,@Param("postID") int postId
											);
	public void deleteLikeByPostId(@Param("postId") int postId);
	public List<Like> selectLikeListByPostId(@Param("postId") int postId);
}
