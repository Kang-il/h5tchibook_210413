package com.h5tchibook.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.post.model.UserPost;

@Repository
public interface UserPostDAO {
	public int insertUserPost(UserPost userpost);
	public List<UserPost> selectPostListByUserId(@Param("userId") int userId);
	public List<UserPost> selectPostListOnlyPhotoByUserId(@Param("userId") int userId
														, @Param("limit") Integer limit);
}
