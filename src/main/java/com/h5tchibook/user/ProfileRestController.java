package com.h5tchibook.user;
	
import java.util.HashMap;
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
import com.h5tchibook.common.EncryptUtils;
import com.h5tchibook.common.ValidateHandler;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.Sex;
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
	
	@PostMapping("/check_password")
	public Map<String,Boolean> checkPassword(@RequestParam("password") String password
											,HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		
		boolean loginCheck=false;
		boolean resultCheck=false;
		
		if(user!=null) {
			loginCheck=true;
			String encryptedPassword=EncryptUtils.mb5(password);
			User checkUser=userBO.getUserByLoginIdAndPassword(user.getLoginId(), encryptedPassword);
			
			if(checkUser!=null) {
				resultCheck=true;
				session.setAttribute("checkPassword", true);
			}else {
				session.setAttribute("checkPassword", false);
			}
		}

		result.put("loginCheck", loginCheck);
		result.put("result", resultCheck);
		return result;
	}
	
	@PostMapping("/edit/validate_edit_user_info")
	public Map<String,Boolean> editUserInfo(@RequestParam("loginId") String userLoginId
											,@RequestParam(value="password",required = false) String password
											,@RequestParam("sex") String sex
											,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		Boolean checkPassword=(Boolean)session.getAttribute("checkPassword");
		
		User user = (User)session.getAttribute("user");
		
		Sex sexType = sex.equals("male") ? Sex.MALE : Sex.FEMALE;
		
		User editedUser=User.builder()
							.loginId(userLoginId)
							.password(password)
							.sex(sexType)
							.build();
		
		Map<String,Boolean> result=null;
		
		if(checkPassword==true) {
			result=checkBO.validateEditUserInfoCheckElements(user, editedUser);
		}
		
		return result;
	}
	
	@PostMapping("/edit/set_user_profile")
	public Map<String,Boolean> editUserProfile(@RequestParam("loginId") String loginId
											,@RequestParam(value="password",required=false)String password
											,@RequestParam("sex")String sex
											,HttpServletRequest request){
		
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Boolean checkPassword=(Boolean)session.getAttribute("checkPassword");
		boolean loginCheck=false;
		boolean resultCheck=false;
		//checkPassword가 true 이고 user가 null이 아니어야 한다.
		if(user!=null && checkPassword==true) {
			loginCheck=true;
			User editUser=User.builder()
							  .id(user.getId())
							  .build();
			
			if(!loginId.equals(user.getLoginId())) {
				editUser.setLoginId(loginId);
			}
			
			if(password!=null) {
				String encryptedPassword=EncryptUtils.mb5(password);
				editUser.setPassword(encryptedPassword);
			}
			
			if(!user.getSex().getSex().equals(sex)) {
				if(user.getSex().equals(Sex.MALE)) {
					editUser.setSex(Sex.FEMALE);
				}else if(user.getSex().equals(Sex.FEMALE)) {
					editUser.setSex(Sex.MALE);
				}
				
			}

			userBO.editUserInfo(editUser);
			resultCheck=true;
		}
		
		result.put("loginCheck",loginCheck);
		result.put("result", resultCheck);
		
		return result;
		
	}
	
}
