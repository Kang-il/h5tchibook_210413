package com.h5tchibook.post.dao;

import org.springframework.stereotype.Repository;

import com.h5tchibook.post.model.UserPost;

@Repository
public interface UserPostDAO {
	public int insertUserPost(UserPost userpost);
}
