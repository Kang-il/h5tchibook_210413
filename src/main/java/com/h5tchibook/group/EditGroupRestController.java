package com.h5tchibook.group;

import java.util.HashMap;	
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.common.ValidateHandler;
import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/group/edit")
public class EditGroupRestController {
	@Autowired
	private GroupBO groupBO;
	@Autowired
	private ValidateHandler validateHandler;
	
	@PostMapping("/group_profile_image")
	public Map<String,Boolean> editGroupProfileImage(@RequestParam("groupId") int groupId
													, @RequestParam(value="file",required = false) MultipartFile file
													, HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Map<String,Boolean> result= new HashMap<String,Boolean>();
		boolean resultCheck=false;
		boolean loginCheck=false;
		if(user!=null) {
			loginCheck=true;
			if(file!=null) {
				String errorStr=validateHandler.multipartFileValidateHandling(file);
				if(errorStr!=null) {
					result.put(errorStr,false);
					result.put("loginCheck", loginCheck);
					result.put("result", resultCheck);
					return result;
				}
			}
			
			groupBO.editGroupProfileImage(groupId,file);
			resultCheck=true;
			
		}
		
		
		result.put("loginCheck",loginCheck);
		result.put("result",resultCheck);
		return result;
	}
	
	@PostMapping("/group_cover_image")
	public Map<String,Boolean> editGroupCoverImage(@RequestParam("groupId") int groupId
													, @RequestParam(value="file",required = false) MultipartFile file
													, HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Map<String,Boolean> result= new HashMap<String,Boolean>();
		boolean resultCheck=false;
		boolean loginCheck=false;
		
		if(user!=null) {
			loginCheck=true;
			if(file!=null) {
				String errorStr=validateHandler.multipartFileValidateHandling(file);
				if(errorStr!=null) {
					result.put(errorStr,false);
					result.put("loginCheck", loginCheck);
					result.put("result", resultCheck);
					return result;
				}
			}
			
			groupBO.editGroupCoverImage(groupId,file);
			resultCheck=true;
			
		}
		result.put("loginCheck",loginCheck);
		result.put("result",resultCheck);
		return result;
	}
}
