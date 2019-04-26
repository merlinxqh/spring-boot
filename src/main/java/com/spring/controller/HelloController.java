package com.spring.controller;

import com.spring.common.config.properties.ConfigProperties;
import com.spring.common.redis.ObjectRedisTemplate;
import com.spring.common.result.ResultWrapper;
import com.spring.common.serialize.School;
import com.spring.common.serialize.SerailizeTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ConfigProperties config;
	
//	@Autowired
//	private RabbitMqSender sender;

	@Autowired
	private ObjectRedisTemplate redisTemplate;
	

	@GetMapping("/send")
	public ResultWrapper<?> send(String msg){
		redisTemplate.setListObj("redis:template:list:school", SerailizeTest.getSchoolList());

		List<School> schoolList=redisTemplate.getListObj("redis:template:list:school",School.class);

		return new ResultWrapper<>(1,"从redis读取对象",schoolList);
	}
	
	@RequestMapping("/hello/{name}")
	public String index(@PathVariable String name,ModelMap model){
		model.put("name", name);
		/**
		 * 默认模板文件夹:src/main/resources/templates/目录下添加一个模板文件hello.html
		 * 默认静态资源文件夹:src/main/resources/static
		 * 
		 */
		System.out.println("abc:"+environment.getProperty("abc"));
		System.out.println("xqh:"+environment.getProperty("xqh"));
		return "hello";
	}
	
	
	@RequestMapping("/test")
	public @ResponseBody Map<String, Object> test(String test){
		Map<String,Object> res=new HashMap<>();
		Assert.isTrue(true, "这里是一个异常");
		res.put("response", "success");
		res.put("baby", config);
		return res;
	}

}
