package com.h5tchibook.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.h5tchibook.user.model.User;

@Repository
public interface UserDAO {
	public int insertUser(User user);
	public User selectUserByloginId(@Param("loginId") String loginId);
	public User selectUserByLoginIdAndPassword(@Param("loginId")String loginId
											  ,@Param("password") String password);
	public User selectUserById(@Param("id") int id);
	public void updateUserProfileImagePath(@Param("id")int id, @Param("profileImagePath") String profileImagePath);
}
