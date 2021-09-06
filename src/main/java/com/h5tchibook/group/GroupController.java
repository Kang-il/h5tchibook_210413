package com.h5tchibook.group;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/group")
public class GroupController {
	private Date date=new Date();
	
	@RequestMapping("/create_group_view")
	public String createGroupView(Model model) {
		model.addAttribute("currentTime", date.getTime());
		model.addAttribute("userView", "group/create_group_section");
		return "template/template_layout";
	}
}
