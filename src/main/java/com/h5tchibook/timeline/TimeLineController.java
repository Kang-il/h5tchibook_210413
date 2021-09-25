package com.h5tchibook.timeline;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.post.bo.UserPostBO;
import com.h5tchibook.post.model.PostView;
import com.h5tchibook.user.model.User;



@Controller
@RequestMapping("/timeline")
public class TimeLineController {
	
	@Autowired
	private UserPostBO userPostBO;
	@Autowired
	private GroupBO groupBO;
	
	
	private Date date =new Date();
	
	@RequestMapping("/user_timeline_view")
	public String timeLineView(Model model , HttpServletRequest request ) {
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		if(user==null) {
			return "redirect:/user/sign_in_view";
		}else {
			List<PostView> postList = userPostBO.getPostListByUserIdAndTimeLineList(user.getId());
			model.addAttribute("postList",postList);
			model.addAttribute("currentTime",date.getTime());
			model.addAttribute("userView", "timeline/user_timeline_section");
		}
		
		
		return "template/template_layout";
	} 
	
	@RequestMapping("/group_timeline_view")
	public String groupTimeLineView(Model model , HttpServletRequest request) {
		//그룹 리스트 가져오기
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		if(user==null) {
			return "redirect:/user/sign_in_view";
		}else {
			List<Group> groupList=groupBO.getGroupListByMemberId(user.getId());
			model.addAttribute("groupList",groupList);
		}
		
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView", "timeline/group_timeline_section");
		return "template/template_layout";
	}
}
