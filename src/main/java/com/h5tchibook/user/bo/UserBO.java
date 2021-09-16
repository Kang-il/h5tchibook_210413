package com.h5tchibook.user.bo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.common.FileManagerService;
import com.h5tchibook.user.dao.UserDAO;
import com.h5tchibook.user.model.User;

@Service
public class UserBO {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private FileManagerService fileManagerService;
	
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
	
	public void editUserProfileImage(User user, MultipartFile file) {
		
		String imageUrl=null;
		
		if(user.getProfileImagePath()!=null) {// 기존의 유저 프로필이 있다면 지워준다.
			try {
				fileManagerService.deleteFile(user.getProfileImagePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(file != null) {// file null이 아니면 파일을 저장하고 파일경로를 반환받는다.
			try {
				imageUrl=fileManagerService.saveFile(user.getLoginId(), file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		userDAO.updateUserProfileImagePath(user.getId(),imageUrl);

	}
	
	public void editUserCoverImage(User user, MultipartFile file) {
		String imageUrl=null;
		
		if(user.getCoverImagePath()!=null) {
			try {
				fileManagerService.deleteFile(user.getCoverImagePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(file != null) {
			try {
				imageUrl=fileManagerService.saveFile(user.getLoginId(), file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		userDAO.updateUserCoverImagePath(user.getId(), imageUrl);
	}
}
