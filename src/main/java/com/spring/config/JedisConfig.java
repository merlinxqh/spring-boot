//package com.spring.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import redis.clients.jedis.JedisPool;
//
//@Configuration
//public class JedisConfig {
//     
//	@Value("${redis.url}")
//	private String redisUrl;
//	
//	@Value("${redis.port}")
//	private int redisPort;
//   	
//	@Bean
//	public JedisPool jedisPool(){
//		JedisPool pool=new JedisPool(redisUrl,redisPort);
//		return pool;
//	}
//	
//}
