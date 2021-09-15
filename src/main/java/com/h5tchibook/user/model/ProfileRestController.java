package com.h5tchibook.user.model;

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
import com.h5tchibook.user.bo.UserBO;



@RestController
@RequestMapping("/profile")

public class ProfileRestController {
	
	@Autowired
	private UserBO userBO;
	@Autowired
	private ValidateHandler validateHandler;
	
	@PostMapping("/update_profile_img")
	public Map<String,Object> updateProfileImg(@RequestParam(value= "file", required=false) MultipartFile file
											  , HttpServletRequest request){
		Map<String,Object> result=new HashMap<String,Object>();
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		boolean loginCheck=false;
		boolean resultCheck=false;
		
		if(user!=null) {
			loginCheck=true;
			result.put("loginCheck", loginCheck);
			
			if(file!=null) {
				//file확장자의 유효성 체크
				String check=validateHandler.multipartFileValidateHandling(file);
				if(check!=null) {
					result.put(check,true);
					result.put("result",resultCheck);
					
					//아래 코드 실행 할 수 없게 반환한다.
					return result;
				}
			}
			
			userBO.editUserProfileImage(user, file);// 값 변경
			
			//기존 session에 들어있는 user객체는 update 이전의 값이므로 
			//변경된 User다시 DB에서 받아와 기존 세션의 user값과 교체시켜준다.
			User updatedUser=userBO.getUserById(user.getId());
			session.setAttribute("user", updatedUser);
			
			resultCheck=true;
		}

		result.put("result", resultCheck);
		return result;
	}
}
