package com.spring.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.spring.config.ConfigProperties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 用protostuff序列化
 * @author leo
 *
 */
@RunWith(value=SpringRunner.class)
@SpringBootTest
public class RedisSerialize {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private ConfigProperties config;
	
	private RuntimeSchema<ConfigProperties> schema=RuntimeSchema.createFrom(ConfigProperties.class);
	
	@Test
	public void test(){
		Jedis jedis=jedisPool.getResource();
		try {
			String key="rediskey:config";
		    byte[] bytes=jedis.get(key.getBytes());
		    if(null != bytes){
		    	//反序列化
		    	ConfigProperties pro=new ConfigProperties();
		    	ProtostuffIOUtil.mergeFrom(bytes, pro, schema);
		    	logger.info("object from redis:{}",pro.toString());
		    }else{
		    	//序列化对象 存放至redis
		    	byte[] inbytes=ProtostuffIOUtil.toByteArray(config, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
		    	String result=jedis.setex(key.getBytes(), 3600, inbytes);
		    	logger.info("set into redis result:{}",result);
		    }
		} finally {
			jedis.close();
		}
	}

}
