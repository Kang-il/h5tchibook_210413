package com.h5tchibook.group;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.bo.GroupMemberBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.post.bo.GroupPostBO;
import com.h5tchibook.user.model.User;

@RequestMapping("/group")
@RestController
public class GroupRestController {
	@Autowired
	private GroupBO groupBO;
	@Autowired
	private GroupPostBO groupPostBO;
	@Autowired
	private GroupMemberBO groupMemberBO;
	
	
	@PostMapping("/create_group")
	public Map<String, Boolean> createGroup(@RequestParam("groupName") String groupName
											,HttpServletRequest request){
		HttpSession session=request.getSession();
		User user =(User)session.getAttribute("user");
		
		Group group=Group.builder() 
						.groupName(groupName)
						.groupManagerId(user.getId())
						.build();
			
		Map<String, Boolean> result=groupBO.createGroup(user,group);
		
		return result;
	}
	
	@PostMapping("/delete_group")
	public Map<String,Boolean> deleteGroup(@RequestParam("groupId")int groupId
										 , HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user =(User)session.getAttribute("user");
		
		boolean loginCheck=false;
		boolean resultCheck=false;
		Map<String,Boolean> result=new HashMap<String,Boolean>();
		
		if(user!=null) {
			loginCheck=true;
			Group group=groupBO.getGroupById(groupId);
			
			
			if(group.getGroupManagerId()==user.getId()) {
				groupPostBO.deleteGroupPostByGroupId(groupId);
				groupMemberBO.deleteGroupMemberByGroupId(groupId);
				groupBO.deleteGroupById(groupId);
			}
			
			resultCheck =true;
		}
		
		result.put("loginCheck",loginCheck);
		result.put("result",resultCheck);
		return result;
	}
}
