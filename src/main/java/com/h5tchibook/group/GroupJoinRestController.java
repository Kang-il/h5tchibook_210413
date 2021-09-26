package com.h5tchibook.group;

import java.util.Map;	

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.bo.GroupJoinRequestBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/group")
public class GroupJoinRestController {
	@Autowired
	private GroupJoinRequestBO groupJoinRequestBO;
	@Autowired
	private GroupBO groupBO;
	
	
	@RequestMapping("/request_join_group")
	public Map<String, Boolean> requestJoinGroup(@RequestParam("groupId") int groupId
												,HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		
		Group group=groupBO.getGroupById(groupId);
		
		Map<String,Boolean> result=groupJoinRequestBO.createGroupJoinRequest(user, group);
		
		return result;
	}
	
	@RequestMapping("/cancel_join_request")
	public Map<String,Boolean> cancleJoinRequest(@RequestParam("groupId") int groupId
												,HttpServletRequest request){
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Group group=groupBO.getGroupById(groupId);
		
		Map<String,Boolean> result=groupJoinRequestBO.deleteJoinGroupRequest(user, group);
		
		
		return result;
	}
	
	@RequestMapping("/response_join_group")
	public Map<String,Boolean>responseJoinGroup(@RequestParam("decision") String decision
												,@RequestParam("userId") int userId
												,@RequestParam("groupId") int groupId
												,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Group group=groupBO.getGroupById(groupId);
		Map<String,Boolean> result=groupJoinRequestBO.responseJoinGroup(user,group,userId,decision);
		
		return result;
	}
}
