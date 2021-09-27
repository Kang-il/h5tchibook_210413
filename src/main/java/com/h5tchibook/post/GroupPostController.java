package com.h5tchibook.post;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.post.bo.GroupPostBO;
import com.h5tchibook.post.model.GroupPostView;
import com.h5tchibook.user.model.User;

@Controller
@RequestMapping("/group/post")
public class GroupPostController {
	@Autowired
	private GroupPostBO groupPostBO;
	@Autowired
	private GroupBO groupBO;
	
	@RequestMapping("/group_post_detail_view")
	public String groupPostDetailView(@RequestParam("postId") int postId
									 , Model model
									 , HttpServletRequest request) {
		//groupOwnerCheck
		Date date=new Date();
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		if(user==null) {
			return "redirect:/user/sign_in_view";
		}else {
			GroupPostView post=groupPostBO.getGroupPostViewById(postId);
			Group group=groupBO.getGroupById(post.getGroupId());
			
			//contentType이 photo가 아니면 해당 그룹 피드로 리다이렉트
			if(!post.getContentType().getContentType().equals("photo")) {
				return "redirect:/feed/group/"+group.getGroupName();
			}
			
			model.addAttribute("group",group);
			model.addAttribute("post",post);
		}
		
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView","post/group_post_detail_section");
		
		return"template/template_layout";
	}
}
