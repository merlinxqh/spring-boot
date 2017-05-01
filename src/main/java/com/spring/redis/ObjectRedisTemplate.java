package com.spring.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ObjectRedisTemplate<T>{
    
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public void set(String key,T t){
//		redisTemplate.
	}
	
	public void set(String key,T t,int expire){
		
	}
	
}
