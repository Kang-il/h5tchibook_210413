package com.h5tchibook.user;

import java.util.HashMap;	
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.common.EncryptUtils;
import com.h5tchibook.common.ValidateHandler;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.Sex;
import com.h5tchibook.user.model.User;
import com.h5tchibook.user.model.ValidateUser;


@RequestMapping("/user")
@RestController
public class UserRestController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserBO userBO;
	@Autowired
	private ValidateHandler validateHandler;
	
	
	@PostMapping("/sign_up_validation")
	public Map<String,Object> signUpValidation(
							@ModelAttribute @Valid ValidateUser validateUser
							,Errors errors){
		
		Map<String,Object> result=new HashMap<String,Object>();

		//1. 유효성 검사
		if(errors.hasErrors()) {
			Map<String,String> validatorResult=validateHandler.validateHandling(errors);
			for(String key : validatorResult.keySet()) {
				result.put(key, validatorResult.get(key));
			}
			result.put("result",false);
			
			//유효성 체크중 오류 발생 시 아래 코드는 더이상 실행하지 않아도 됨.
			return result;
		}
		
		//2. 유효성 검사 후 아이디 중복 체크
		User user=userBO.getUserByLoginId(validateUser.getLoginId());
		if(user!=null) {
			result.put("result", false);
			result.put("valid_duplicateId", "duplicateId");
		}else {
			// 위의 유효성 검사와 아이디 중복검사를 모두 통과 할 경우 result에 true를 담아준다
			result.put("result",true);
		}
		return result;
	}
	
	@PostMapping("/sign_up")
	public Map<String,Object> signUp(@RequestParam("loginId") String loginId
									,@RequestParam("name") String name
									,@RequestParam("password") String password
									,@RequestParam("sex") String sex){
		Map<String,Object> result=new HashMap<String,Object>();
		
		String encriptedPassword=EncryptUtils.mb5(password);
		
		
		User user= User.builder()
		.loginId(loginId)
		.name(name)
		.password(encriptedPassword)
		.sex((sex.equals("male")? Sex.MALE : Sex.FEMALE))
		.build();
		

		
		int row=userBO.createUserAccount(user);
		if(row==1) {
			result.put("result", true);
		}else if(row==0){
			result.put("result",false);
		}
		
		return result;
	}
	
	@PostMapping("/sign_in")
	public Map<String,Object> signIn(@RequestParam("loginId") String loginId
									,@RequestParam("password") String password
									,HttpServletRequest request){
		
		Map<String,Object> result=new HashMap<String,Object>();
		
		String encriptedPassword=EncryptUtils.mb5(password);
		User user=userBO.getUserByLoginIdAndPassword(loginId,encriptedPassword);
		
		if(user!=null) {
			HttpSession session=request.getSession();
			session.setAttribute("user", user);
			result.put("result",true);
		}else {
			result.put("result",false);
		}
		
		return result;
	}
	
}
