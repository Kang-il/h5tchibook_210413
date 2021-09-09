package com.h5tchibook.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.user.dao.UserDAO;
import com.h5tchibook.user.model.User;

@Service
public class UserBO {
	@Autowired
	private UserDAO userDAO;
	
	public int createUserAccount(User user) {
		return userDAO.insertUser(user);
	}
	
	public User getUserByLoginId(String loginId) {
		return userDAO.selectUserByloginId(loginId);
	}
	
	public User getUserByLoginIdAndPassword(String loginId,String password) {
		return userDAO.selectUserByLoginIdAndPassword(loginId,password);
	}
	
	public User getUserById(int id) {
		return userDAO.selectUserById(id);
	}
}
