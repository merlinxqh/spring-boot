package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@RequestMapping("/hello/{name}")
	public String index(@PathVariable String name,ModelMap model){
		model.put("name", name);
		/**
		 * 默认模板文件夹:src/main/resources/templates/目录下添加一个模板文件hello.html
		 * 默认静态资源文件夹:src/main/resources/static
		 * 
		 */
		return "hello";
	}

}
