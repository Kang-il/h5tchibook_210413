package com.h5tchibook.group;

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
import com.h5tchibook.user.model.User;

@RestController
@RequestMapping("/group/member")
public class GroupMemberRestController {
	@Autowired
	private GroupMemberBO groupMemberBO;
	@Autowired
	private GroupBO groupBO;
	
	@PostMapping("/delete_group_member")
	public Map<String,Boolean> deleteGroupMember(@RequestParam("groupId") int groupId
												,@RequestParam("groupMemberId") int groupMemberId
												,HttpServletRequest request){
		
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		Group group =groupBO.getGroupById(groupId);
		
		Map<String,Boolean> result=groupMemberBO.deleteGroupMember(user,group,groupMemberId);
		
		return result;
	}
	
}
