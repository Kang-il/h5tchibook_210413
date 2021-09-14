package com.h5tchibook.like;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.like.bo.LikeBO;
import com.h5tchibook.like.model.Like;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/like")
public class LikeRestController {
	
	@Autowired
	private LikeBO likeBO;
	
	@RequestMapping("/set_like/{postId}")
	public Map<String,Object> setLike(@PathVariable("postId") int postId
									,HttpServletRequest request){
		Map<String, Object> result=new HashMap<String,Object>();
		
		HttpSession session= request.getSession();
		
		User user = (User)session.getAttribute("user");
		if(user != null) {
			result.put("loginCheck", true);
			
			//1. 라이크가 있는지 확인하기 
			//2. 이미 존재하면 라이크 취소
			//3. 존재하지 않는다면 라이크 하기
			//4. 라이크 리스트 새로 받아오기
			Like like=likeBO.getLikeByUserIdAndPostId(user.getId(), postId);
			if(like!=null) {//라이크 취소
				likeBO.deleteLikeByUserIdAndPostId(user.getId(), postId);
			}else {//라이크 하기
				like=Like.builder()
					.userId(user.getId())
					.postId(postId)
					.build();
					
				likeBO.createLike(like);
			}
			result.put("result", true);
		}else {
			result.put("loginCheck", false);
		}
		
		
		return result;
	}
}
