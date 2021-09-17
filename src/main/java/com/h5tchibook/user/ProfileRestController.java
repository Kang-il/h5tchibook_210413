package com.h5tchibook.user;
	
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.common.ValidateHandler;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;



@RestController
@RequestMapping("/profile")

public class ProfileRestController {
	
	@Autowired
	private UserBO userBO;
	@Autowired
	private ValidateHandler validateHandler;
	@Autowired
	private CheckBO checkBO;
	
	@PostMapping("/update_profile_img/{feedOwnerLoginId}")
	public Map<String,Boolean> updateProfileImg(@RequestParam(value= "file", required=false) MultipartFile file
												,@PathVariable("feedOwnerLoginId") String feedOwnerLoginId
											  , HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User) session.getAttribute("user");
		Map<String,Boolean> result=checkBO.updateUserProfileInfoCheckElements(user, feedOwnerLoginId);
		boolean resultCheck=false;
		
		if(result.get("loginCheck") && result.get("feedOwnerCheck") && result.get("existUser")) {
			
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
	
	@PostMapping("/set_background_img/{feedOwnerLoginId}")
	public Map<String,Boolean> updateBackgroundImg(@RequestParam(value= "file", required=false) MultipartFile file
												  ,@PathVariable("feedOwnerLoginId") String feedOwnerLoginId
												  ,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Map<String,Boolean> result=checkBO.updateUserProfileInfoCheckElements(user, feedOwnerLoginId);

		boolean resultCheck=false;

		if(result.get("loginCheck") && result.get("feedOwnerCheck") && result.get("existUser")) {

			if(file!=null) {
				String check=validateHandler.multipartFileValidateHandling(file);
				if(check!=null) {
					result.put(check,true);
					result.put("result", resultCheck);
					return result;
				}
			}
			
			userBO.editUserCoverImage(user,file);
			User updatedUser=userBO.getUserById(user.getId());
			session.setAttribute("user", updatedUser);
			resultCheck=true;
		}
		
		result.put("result", resultCheck);
		
		return result;
		
	}
	
	@PostMapping("/set_introduce/{feedOwnerLoginId}")
	public Map<String,Boolean> updateIntroduce(@RequestParam(value="introduce",required=false) String introduce
											,@PathVariable("feedOwnerLoginId") String feedOwnerLoginId
											,HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Map<String,Boolean> result=checkBO.updateUserProfileInfoCheckElements(user, feedOwnerLoginId);
		
	
		boolean resultCheck=false;
		if(result.get("loginCheck") && result.get("feedOwnerCheck") && result.get("existUser")) {
			
			userBO.editUserIntroduce(user.getId(),introduce);
			
			User updatedUser=userBO.getUserById(user.getId());
			session.setAttribute("user", updatedUser);
			
			resultCheck=true;
		}
		
		result.put("result", resultCheck);
		return result;
	}
	
	
}
