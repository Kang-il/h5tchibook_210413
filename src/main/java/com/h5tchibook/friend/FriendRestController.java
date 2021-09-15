package com.h5tchibook.friend;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.friend.bo.FriendBO;
import com.h5tchibook.user.model.User;


import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
public class FriendRestController {
	
	@Autowired
	private FriendBO friendBO;
	@Autowired
	private CheckBO checkBO;
	
	@PostMapping("/request_friend")
	public Map<String,Boolean> requestFriend(@RequestParam("friendId") int friendId
											,HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		Map<String,Boolean> checkMap = checkBO.friendRequestAndResponseCheckElements(user,friendId);
		boolean result=false;
		
		if(checkMap.get("loginCheck") && checkMap.get("existUser")) {
			
			if(!checkMap.get("requestFriendCheck") 
				&& !checkMap.get("receiveFriendCheck")) {// 두 조건이 모두 false여야 친구신청하지도 않았고 친구신청도 받지 않은 것
				friendBO.createFriend(user.getId(), friendId);
				result=true;
			}
			
		}
		checkMap.put("result", result);
		return checkMap;
	}
	
	@PostMapping("/receive_friend")
	public Map<String,Boolean> receiveFriend(@RequestParam("friendId") int friendId
											,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		Map<String,Boolean> checkMap = checkBO.friendRequestAndResponseCheckElements(user,friendId);
		boolean result=false;
		
		if(checkMap.get("loginCheck") && checkMap.get("existUser")) {
			
			if(!checkMap.get("friendCheck") 
				&& !checkMap.get("requestFriendCheck") 
				&& checkMap.get("receiveFriendCheck")) {//받은 친구목록만 있을경우
				friendBO.createFriend(user.getId(), friendId);
				result=true;
			}
		}
		
		checkMap.put("result",result);
		return checkMap;
	}
	
	@PostMapping("/refuse_friend")
	public Map<String,Boolean> refuseFriend(@RequestParam("friendId") int friendId
											,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		Map<String,Boolean> checkMap = checkBO.friendRequestAndResponseCheckElements(user,friendId);
		boolean result=false;
		
		if(checkMap.get("loginCheck") && checkMap.get("existUser")) {
			if( !checkMap.get("requestFriendCheck") 
				&& checkMap.get("receiveFriendCheck")) {//받은 친구목록만 있을경우
					friendBO.deleteFriendRequest(friendId, user.getId());
					result=true;
				}
		}
		
		checkMap.put("result",result);
		return checkMap;
	}
	
	@PostMapping("/cancel_friend_request")
	public Map<String,Boolean> cancelFriendRequest(@RequestParam("friendId") int friendId
												  ,HttpServletRequest request){

		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		Map<String,Boolean> checkMap = checkBO.friendRequestAndResponseCheckElements(user,friendId);
		boolean result=false;
		
		if(checkMap.get("loginCheck") && checkMap.get("existUser")) {
			if( checkMap.get("requestFriendCheck") 
				&& !checkMap.get("receiveFriendCheck")) {//친구요청을 신청한 경우
					friendBO.deleteFriendRequest(user.getId(), friendId);
					result=true;
				}
		}
		checkMap.put("result",result);
		return checkMap;
	}
	
	@PostMapping("/delete_friend")
	public Map<String,Boolean> deleteFriend(@RequestParam("friendId") int friendId
										  ,HttpServletRequest request){
		//loginCheck
		//friendCheck
		HttpSession session =request.getSession();
		User user=(User)session.getAttribute("user");
		
		Map<String,Boolean> checkMap = checkBO.friendDeleteCheckElemnts(user, friendId);
		boolean result=false;
		
		if(checkMap.get("loginCheck")){
			if(checkMap.get("friendCheck")) {//친구인 경우
				//삭제
				friendBO.deleteFriend(user.getId(), friendId);
				result=true;
			}
		}
		
		checkMap.put("result", result);
		
		return checkMap;
	}
}
