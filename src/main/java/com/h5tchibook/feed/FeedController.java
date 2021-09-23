package com.h5tchibook.feed;


import java.util.Date;	
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.h5tchibook.check.bo.CheckBO;
import com.h5tchibook.friend.bo.FriendBO;
import com.h5tchibook.friend.model.FriendView;
import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.DisclosureStatus;
import com.h5tchibook.post.model.Post;
import com.h5tchibook.post.model.PostView;
import com.h5tchibook.user.bo.UserBO;
import com.h5tchibook.user.model.User;

@Controller
@RequestMapping("/feed")
public class FeedController {
	
	@Autowired
	private UserBO userBO;
	@Autowired
	private UserPostBO userPostBO;
	@Autowired
	private FriendBO friendBO;
	@Autowired
	private CheckBO checkBO;
	@Autowired
	private GroupBO groupBO;
	
	
	@RequestMapping("/{feedUserLoginId}")
	public String userFeed(
			@PathVariable(value="feedUserLoginId",required=false) String feedUserLoginId
			,@RequestParam(value="category",required = false) String category
			,HttpServletRequest request
			,Model model) {
		
		Date date =new Date();
		HttpSession session= request.getSession();
		
		
		//세션에 있는 내 정보를 가져온다
		User user=(User) session.getAttribute("user");
		//피드 주인의 정보를 받아온다.
		User feedOwner=userBO.getUserByLoginId(feedUserLoginId);
		
		//내 정보와 피드 주인의 정보를 통한 여러 체크리스트를 작성하는 메서드.
		Map<String,Boolean> checkMap=checkBO.feedCheckElements(user, feedOwner);
		

		
		
		if(checkMap.get("loginCheck")) {
			//1. 로그인 되어있는경우
	
			if(checkMap.get("existUser")) {
				
				DisclosureStatus disclosureStatus=null;
				//본인 피드 라면
				if(checkMap.get("feedOwnerCheck")) {
					disclosureStatus=DisclosureStatus.PRIVATE;
				}else {
					//친구의 피드라면
					if(checkMap.get("friendCheck")) {
						disclosureStatus=DisclosureStatus.FRIEND;
					}else {
						//본인의 피드도 아니고 친구의 피드도 아니라면
						disclosureStatus=DisclosureStatus.PUBLIC;
					}
				}
				
				if(category==null) { //category가 null이라면 타임라인 피드
					//피드오너로 들어온 아이디로 해당 유저가 존재할 경우
					//포스트 목록 가져오기 (좋아요 댓글 포함)
					
					
					List<PostView> postList=userPostBO.getPostListByUserId(feedOwner.getId(),disclosureStatus);
					//사진 목록 가져오기
					List<Post> photoList=userPostBO.getPostListOnlyPhotoByUserId(feedOwner.getId(),9,disclosureStatus);
					//피드주인의 친구 목록 가져오기
					List<FriendView> friendList=friendBO.selectFriendListByUserId(feedOwner.getId(), 9);
					
					//친구리스트
					model.addAttribute("friendList",friendList);
					//사진 리스트
					model.addAttribute("photoList",photoList);
					//포스트 리스트(유저 타임라인)
					model.addAttribute("postList",postList);
					
					model.addAttribute("userView","user/user_feed_post_section");
					
				}else if(category.equals("friend")) { //친구 목록 피드
					
					List<FriendView> friendList=friendBO.selectFriendListByUserId(feedOwner.getId(), null);
					model.addAttribute("friendList",friendList);
					model.addAttribute("userView","user/user_feed_friend_section");
				}else if(category.equals("photo")) {
					List<Post> photoList=userPostBO.getPostListOnlyPhotoByUserId(feedOwner.getId(), null, disclosureStatus);
					
					model.addAttribute("photoList",photoList);
					model.addAttribute("userView","user/user_feed_photo_section");
				}
					
				//피드 주인의 값
				model.addAttribute("feedOwner",feedOwner);
				
			}else if( !checkMap.get("existUser")) {
				//피드오너로 들어온 아이디로 해당 유저가 존재하지 않을 경우
				//본인의 피드로 리다이렉트를 시킨다.
				return "redirect:/feed/"+user.getLoginId();
			}
		}else if( !checkMap.get("loginCheck")) {
			//로그인되어있지 않은경우
			//로그인 페이지로 리다이렉트 시킨다
			return "redirect:/user/sign_in_view";
		}

		//체크리스트
		//for문으로 모든 체크리스트의 값을 넘겨준다.
		for(String key : checkMap.keySet()) {
			model.addAttribute(key, checkMap.get(key));
		}
		model.addAttribute("currentTime",date.getTime());
		
		
		return "template/template_layout"; 
	}
	@RequestMapping("/group/{groupName}")
	public String groupFeedView(Model model
								,@PathVariable("groupName") String groupName
								,HttpServletRequest request) {
		Date date= new Date();
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Group group = groupBO.getGroupByGroupName(groupName);
		//loginCheck
		//existGroupCheck
		//groupOwnerCheck
		Map<String,Boolean> checkMap=checkBO.groupFeedCheckElements(user, group);
		
		if(checkMap.get("loginCheck")) {
			//입력한 그룹이 존재하지 않는다면.
			if(!checkMap.get("existGroupCheck")) {
				return "redirect:/timeline/group_timeline_view";
			}
			
			for(String key : checkMap.keySet()) {
				model.addAttribute(key,checkMap.get(key));
			}
			
		}else {
			return "redirect:/user/sign_in_view";
		}
		
		model.addAttribute("userView","group/group_feed_section");
		model.addAttribute("currentTime",date.getTime());
		return "template/template_layout";
	}
}
