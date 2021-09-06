package com.h5tchibook.timeline;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/timeline")
public class TimeLineController {
	
	private Date date =new Date();
	
	@RequestMapping("/user_timeline_view")
	public String timeLineView(Model model) {
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView", "timeline/user_timeline_section");
		return "template/template_layout";
	} 
	
	@RequestMapping("/group_timeline_view")
	public String groupTimeLineView(Model model) {
		model.addAttribute("currentTime",date.getTime());
		model.addAttribute("userView", "timeline/group_timeline_section");
		return "template/template_layout";
	}
}
