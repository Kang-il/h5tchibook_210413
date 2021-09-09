package com.h5tchibook.post;



import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.h5tchibook.friend.bo.FriendBO;
import com.h5tchibook.friend.model.Friend;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.UserPost;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@RequestMapping("/post")
@Controller
public class PostController{
	
	@Autowired
	private UserBO userBO;
	@Autowired
	private UserPostBO userPostBO;
	@Autowired
	private FriendBO friendBO;
	
	@RequestMapping("/feed/{userLoginId}")
	public String userFeed(
			@PathVariable("userLoginId") String userLoginId
			,HttpServletRequest request
			,Model model) {
		Date date =new Date();
		HttpSession session= request.getSession();
		
		User user=(User) session.getAttribute("user");
		
		if(user!=null) {
			if(user.getLoginId().equals(userLoginId)) { // 동일하다면 내 피드
				model.addAttribute("feedOwnerCheck",true);
			}else {//동일하지않다면 다른 사람 피드
				model.addAttribute("feedOwnerCheck",false);
				//나 != 피드 일경우 친구여부 
				//친구 신청 중인지 아닌지 여부가져올것
				
				//내 아이디 값을 저장해줌
				int myId=user.getId();
				
				//user객체에 피드 주인의 값을 받아온다.
				user=userBO.getUserByLoginId(userLoginId);
				
				//1.이 유저랑 나랑 친구인지 확인
				//2.친구가 맞다면 friendCheck true 담아주기
				//3.친구가 아니라면 내가 친구요청중인지 확인 -- requestFriendCheck
				//4.내가 친구요청중이 아니라면 상대방이 나에게 요청중인지 확인 -- receiveFriendCheck
				//5.둘 중 친구요청을 한 적이 없다면 request receive 둘다 false --> 
				// 뷰상에서 둘 다 false면(request,receive) 친구요청버튼 띄워주기
				
				Friend myFriend=friendBO.getFriendByUserIdAndFriendId(myId, user.getId());
				Friend feedOwnerFriend=friendBO.getFriendByUserIdAndFriendId(user.getId(),myId);
				if(myFriend !=null && feedOwnerFriend != null ){
					
					model.addAttribute("friendCheck",true);
					
				}else if(myFriend != null && feedOwnerFriend == null){ 
					//DB에 userId에 나의 아이디  friendId 피드주인 아이디 에 대한 데이터가 있고
					// 	  userId에 피드 주인의 아이디 friendId에 나의 아이디에 데한 데이터가 없다면
					// 내가 피드 주인에게 친구 요청중
					
					model.addAttribute("friendCheck",false);
					model.addAttribute("requestFriendCheck",true);
					model.addAttribute("receiveFriendCheck",false);
					
				}else if(myFriend == null && feedOwnerFriend != null) {
					//DB에 userId에 vlem  friendId 피드주인 아이디 에 대한 데이터가 없고
					// 	  userId에 피드 주인의 아이디 friendId에 나의 아이디에 데한 데이터가 있다면
					//피드 주인이 나에게 친구 요청중
					
					model.addAttribute("friendCheck",false);
					model.addAttribute("requestFriendCheck",false);
					model.addAttribute("receiveFriendCheck",true);
					
				}else if(myFriend == null && feedOwnerFriend == null) {
					//어느 한 쪽에서도 친구요청은 없는 상태
					model.addAttribute("friendCheck",false);
					model.addAttribute("requestFriendCheck",false);
					model.addAttribute("receiveFriendCheck",false);
					
				}
			}	
			
			//포스트 목록 가져오기 (좋아요 댓글 포함)
			List<UserPost> postList=userPostBO.getPostListByUserId(user.getId());
			//사진 목록 가져오기
			List<UserPost> photoList=userPostBO.getPostListOnlyPhotoByUserId(user.getId(), 9);
			//피드주인의 친구 목록 가져오기
			List<Friend> friendList=friendBO.selectFriendListByUserId(user.getId(), 9);

			model.addAttribute("friendList",friendList);
			model.addAttribute("photoList",photoList);
			model.addAttribute("postList",postList);
			model.addAttribute("feedOwner",user);
				
		}else {//로그인 안되어있으면 리다이렉트
			return "redirect:/user/sign_in_view";
		}
		
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView","user/user_feed_post_section");
		
		return "template/template_layout"; 
	}
}
