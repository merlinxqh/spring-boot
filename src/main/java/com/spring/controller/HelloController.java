package com.spring.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.config.properties.ConfigProperties;
import com.spring.mq.rabbitmq.RabbitMqSender;
import com.spring.serialize.School;
import com.spring.serialize.SerailizeTest;
import com.spring.service.ResourceService;

@RestController
public class HelloController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private ConfigProperties config;
	
	@Autowired
	private ResourceService resourceService;
	
//	@Autowired
//	private RabbitMqSender sender;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@GetMapping("/send")
	public String send(String msg){
//		sender.send(msg);
		redisTemplate.opsForValue().set("redis:test:school", "dddfasdfasdfasdf");
		return "Send Ok.";
	}
	
	@GetMapping(value="getList")
	public @ResponseBody Object getList(){
		return resourceService.findByBiz(null);
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
