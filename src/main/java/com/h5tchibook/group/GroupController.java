package com.h5tchibook.group;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.h5tchibook.alert.bo.AlertTimeLineBO;
import com.h5tchibook.alert.model.AlertTimeLineView;
import com.h5tchibook.user.model.User;

@Controller
@RequestMapping("/group")
public class GroupController {
	
	@Autowired
	AlertTimeLineBO alertTimeLineBO;
	
	@RequestMapping("/create_group_view")
	public String createGroupView(Model model,HttpServletRequest request) {
		Date date=new Date();
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		List<AlertTimeLineView> alertList=null;
		if(user!=null) {
			alertList=alertTimeLineBO.getAlertTimelineViewByUserId(user.getId());
			
		}
		model.addAttribute("currentTime", date.getTime());
		model.addAttribute("alertList", alertList);
		model.addAttribute("userView", "group/create_group_section");
		return "template/template_layout";
	}
}
