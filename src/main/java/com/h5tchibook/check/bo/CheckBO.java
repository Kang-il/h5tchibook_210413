package com.h5tchibook.check.bo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.h5tchibook.friend.bo.FriendBO;
import com.h5tchibook.friend.model.Friend;
import com.h5tchibook.user.model.User;

@Service
public class CheckBO {
	
	@Autowired
	private FriendBO friendBO;
	
	public Map<String,Boolean> feedCheckElements(User user, User feedOwner){
		Map<String, Boolean> result=new HashMap<>();
		//로그인 체크 
		boolean loginCheck=false;
		//피드의 유저가 존제하는지 체크
		boolean existUser=false;
		//피드 오너 체크 :: 
		boolean feedOwnerCheck=false;
		//서로 친구인지 체크
		boolean friendCheck=false;
		//친구 요청중인지 체크
		boolean requestFriendCheck=false;
		//친구요청을 받았는지 체크
		boolean receiveFriendCheck=false;
		
		if(feedOwner==null) {
			result.put("existUser", existUser);
			//피드 오너가 존재하지 않다면 바로 리턴해주기
			//아래 코드를 실행할 필요가 없음
			return result;
		}else {
			existUser=true;
		}
		
		
		if(user != null) {
				loginCheck=true;
			if(feedOwner.getLoginId().equals(user.getLoginId())) {
				feedOwnerCheck=true;
			}else if(!feedOwner.getLoginId().equals(user.getLoginId())) {
				feedOwnerCheck=false;
			
			
			
			//1.이 유저랑 나랑 친구인지 확인
			//2.친구가 맞다면 friendCheck true 담아주기
			//3.친구가 아니라면 내가 친구요청중인지 확인 -- requestFriendCheck
			//4.내가 친구요청중이 아니라면 상대방이 나에게 요청중인지 확인 -- receiveFriendCheck
			//5.둘 중 친구요청을 한 적이 없다면 request receive 둘다 false --> 
			// 뷰상에서 둘 다 false면(request,receive) 친구요청버튼 띄워주기
			Friend myFriend=friendBO.getFriendByUserIdAndFriendId(user.getId(), feedOwner.getId());
			Friend feedOwnerFriend=friendBO.getFriendByUserIdAndFriendId(feedOwner.getId(), user.getId());
			
			
			if(myFriend !=null && feedOwnerFriend != null ){
				//DB에 userId에 나의 아이디  friendId 피드주인 아이디 에 대한 데이터가 있고
				// userId에 피드 주인의 아이디 friendId에 나의 아이디에 데한 데이터가 없다면
				// 내가 피드 주인에게 친구 요청중
				requestFriendCheck = true;			
			}else if(myFriend == null && feedOwnerFriend != null) {
				//DB에 userId에 vlem  friendId 피드주인 아이디 에 대한 데이터가 없고
				// 	  userId에 피드 주인의 아이디 friendId에 나의 아이디에 데한 데이터가 있다면
				//피드 주인이 나에게 친구 요청중
				receiveFriendCheck = true;
			}else if(myFriend == null && feedOwnerFriend == null) {
				//어느 한쪽도 값이 없는 경우 
				// 친구도 아니고 양쪽에서 요청 한 적없음
				// 기본적으로 친구에 관한 모든 boolean은 false가 기본이므로 따로 조작할 필요가 없다.
			}
			
		}
			
		}else {
			loginCheck=false;
		}
		
		result.put("loginCheck", loginCheck);
		result.put("existUser", existUser);
		result.put("friendCheck", friendCheck);
		result.put("requestFriendCheck",requestFriendCheck);
		result.put("receiveFriendCheck", receiveFriendCheck);
		result.put("feedOwnerCheck", feedOwnerCheck);
		
		return result;
	}
	
}
