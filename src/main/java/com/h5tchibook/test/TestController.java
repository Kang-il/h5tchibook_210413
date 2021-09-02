package com.h5tchibook.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.h5tchibook.test.bo.TestBO;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestBO testBO;
	
	@RequestMapping("/test")
	@ResponseBody
	public String helloWorld() {
		return "Hello World!";
	}
	
	@RequestMapping("/test_db")
	@ResponseBody
	public Map<String,Object> testResponseBody(){
		Map<String,Object> map=new HashMap<>();
		map.put("result", testBO.getTest());
		return map;
	}
	
	@RequestMapping("/test_jsp")
	public String testJsp() {
		return "test/test";
	}
}
