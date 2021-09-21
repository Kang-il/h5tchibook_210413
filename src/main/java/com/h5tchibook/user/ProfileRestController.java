package com.h5tchibook.user;
	
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
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
		
		Map<String,Boolean> result=checkBO.validateEditUserInfoCheckElements(user, editedUser);
		boolean resultCheck=false;
		
		if(checkPassword ==true) {
			if(result.get("loginCheck")) {
				//이전의 아이디와 입력받은 아이디가 동일하거나
				//동일하지않다면 아이디 길이가 조건에 맞고 중복체크값이 true인 경우
				boolean loginIdCheck=false;
				
				//기존 로그인 아이디와 같거나 다르다면 로그인아이디의 길이와 중복확인 조건을 모두 만족해야 한다.
				if(result.get("previousLoginIdCheck")) {
					loginIdCheck=true;
				}else {
					if(result.get("loginIdLengthCheck")&&result.get("duplicateLoginIdCheck")) {
						loginIdCheck=true;
					}
				}
				
				boolean passwordCheck=false;
				//애초에 비밀번호는 동일값을 받지 않아야 하므로
				//기존 비밀번호와 동일한 값을 받은경우엔 validate조건에 맞지 않는다
				//기존 비밀번호와 동일하지 않고 받은 패스워드가 있다면 정규식을 만족해야한다 또는 넘어온 비밀번호가 없어야함
				if((!result.get("previousPasswordCheck")&&result.get("passwordRegexCheck")) || password==null) {
					passwordCheck=true;
				}
				
				//비밀번호와 패스워드가 모든 조건을 만족한다면 결과 true
				if(loginIdCheck==true && passwordCheck==true) {
					resultCheck=true;
				}
				
			}
		}
		
		result.put("result",resultCheck);

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
			User editUser=User.builder().id(user.getId()).build();
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
			logger.debug("::::::::::::::"+editUser);
			userBO.editUserInfo(editUser);
			resultCheck=true;
		}
		
		result.put("loginCheck",loginCheck);
		result.put("result", resultCheck);
		
		return result;
		
	}
	
}
