package com.h5tchibook.group;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.h5tchibook.group.bo.GroupBO;
import com.h5tchibook.group.model.Group;
import com.h5tchibook.user.model.User;

@RequestMapping("/group")
@RestController
public class GroupRestController {
	@Autowired
	private GroupBO groupBO;
	@RequestMapping("/create_group")
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
}
