package com.h5tchibook.post.bo;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.common.FileManagerService;
import com.h5tchibook.post.dao.UserPostDAO;
import com.h5tchibook.post.model.ContentType;
import com.h5tchibook.post.model.DisclosureStatus;
import com.h5tchibook.post.model.UserPost;

@Service
public class PostBO {
	@Autowired
	private UserPostDAO userPostDAO;
	@Autowired
	private FileManagerService fileManagerService;
	
	Logger logger =LoggerFactory.getLogger(this.getClass());
	
	public int createUserPost(int userId
							,String userLoginId
							, String content
							, MultipartFile file 
							, String disclosureStatus) {
		
		String imageUrl=generateImageUrlByFile(userLoginId, file);
		
		DisclosureStatus disclosureType = null;
		
		if(disclosureStatus.equals("public")) {
			disclosureType=DisclosureStatus.PUBLIC;
		}else if(disclosureStatus.equals("friend")) {
			disclosureType=DisclosureStatus.FRIEND;
		}else if(disclosureStatus.equals("private")) {
			disclosureType=DisclosureStatus.PRIVATE;
		}
		
		ContentType contentType=null;
		
		if(imageUrl==null) {
			contentType=ContentType.TEXT;
		}else {
			contentType=ContentType.PHOTO;
		}
		
		UserPost userPost = UserPost.builder()
				.userId(userId)
				.content(content)
				.contentType(contentType)
				.contentPath(imageUrl)
				.disclosureStatus(disclosureType)
				.build();
		
		
		return userPostDAO.insertUserPost(userPost);
	}
	
	private String generateImageUrlByFile(String userLogInId, MultipartFile file) {
		String imageUrl=null;
		if(file!=null) {
			try {
				imageUrl=fileManagerService.saveFile(userLogInId, file);
				logger.error(":::::::::::::::: 이미지 경루 ::::"+imageUrl);
			} catch (IOException e) {
				logger.error(":::::::::::::::: 이미지 업로드 오류 ::::"+e.getMessage());
			}
		}
		return imageUrl;
	}
}
