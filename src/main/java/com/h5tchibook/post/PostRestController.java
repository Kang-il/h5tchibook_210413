package com.h5tchibook.post;

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
import org.springframework.web.multipart.MultipartFile;

import com.h5tchibook.common.ValidateHandler;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.ValidateUserPost;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserPostBO userPostBO;
	@Autowired
	private ValidateHandler validateHandler;
	
	@PostMapping("/create_post")
	public Map<String, Object> userPostValidation(@ModelAttribute @Valid ValidateUserPost validateUserPost
												,@RequestParam(value="file", required=false) MultipartFile file
												,Errors errors
												,HttpServletRequest request
												){
		Map<String,Object> result=new HashMap<String,Object>();
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		if(user==null) {
			//로그인 되어있지 않을경우 loginCheck false를 담아준다.
			result.put("loginCheck",false);
			
		}else{
			result.put("loginCheck",true);
			
			//true 반환이 안될 경우 맵에 "result"키로 false 가 이미 담겨있음. 
			if(validateHandler.postValidation(validateUserPost,file,errors,result) == true) {
				//글 등록코드 작성
				int row = userPostBO.createUserPost(user.getId()
												,user.getLoginId()
												,validateUserPost.getContent()
												,file
												,validateUserPost.getDisclosureStatus());
				if(row == 1) {
					//성공
					result.put("result", true);
				}else {
					result.put("result",false);
				}
				
			}
			//아래 코드까지 왔으면 유효성 검사 통과 한 것이므로 result에 true를 담아주고 리턴한다.
		}
		return result;
	}
	
	
	

}
