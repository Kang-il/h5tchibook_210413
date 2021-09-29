package com.h5tchibook.group;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.h5tchibook.alert.bo.AlertTimeLineBO;
import com.h5tchibook.alert.model.AlertTimeLineView;
import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.bo.GroupJoinRequestBO;
import com.h5tchibook.group.bo.GroupMemberBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.group.model.GroupJoinRequestView;
import com.h5tchibook.group.model.GroupMemberView;
import com.h5tchibook.user.model.User;

@Controller
@RequestMapping("/group/edit")
public class EditGroupController {
	
	@Autowired
	private GroupBO groupBO;
	@Autowired
	private GroupJoinRequestBO groupJoinRequestBO;
	@Autowired
	private GroupMemberBO groupMemberBO;
	@Autowired
	private AlertTimeLineBO alertTimeLineBO;
	
	@RequestMapping("/edit_group_list_view")
	public String editGroupListView(Model model, HttpServletRequest request) {
		
		Date date =new Date();
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		List<AlertTimeLineView> alertList=null;
		List<Group>groupList=null;
		
		if(user == null) {
			return "redirect:/user/sign_in_view";
		}else {
			groupList =groupBO.getGroupListByGroupManagerId(user.getId());
			alertList=alertTimeLineBO.getAlertTimelineViewByUserId(user.getId());
		}
		
		model.addAttribute("alertList",alertList);
		model.addAttribute("groupList",groupList);
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView","group/edit_group_list_section");
	
		
		return "template/template_layout";
	}
	
	@RequestMapping("/edit_group_view/{groupName}")
	public String editGroupView(Model model
								, HttpServletRequest request 
								, @PathVariable("groupName") String groupName) {
		
		Date date =new Date();
		
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		Group group=null;
		List<AlertTimeLineView> alertList=null;
		List<Group> groupList=null;
		List<GroupJoinRequestView> requestViewList=null;
		List<GroupMemberView> memberList=null;
		
		if(user == null) {
			return "redirect:/user/sign_in_view";
		}else {
			group=groupBO.getGroupByGroupName(groupName);
			
			if(group==null || group.getGroupManagerId() != user.getId()) {
				return "redirect:/timeline/group_timeline_view";
			}
			
			alertList=alertTimeLineBO.getAlertTimelineViewByUserId(user.getId());
			groupList=groupBO.getGroupListByMemberId(user.getId());
			requestViewList=groupJoinRequestBO.getGroupJoinRequestViewByGroupId(group.getId());
			memberList=groupMemberBO.getGroupMemberViewListByGroupId(group.getId(),"member");
			
		}
		
		
		model.addAttribute("alertList",alertList);
		model.addAttribute("group",group);
		model.addAttribute("groupList",groupList);
		model.addAttribute("joinRequestList",requestViewList);
		model.addAttribute("groupMemberList",memberList);
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView","group/edit_group_section");
		
		return "template/template_layout";
	}
	
}
