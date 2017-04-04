package com.spring.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.spring.config.ConfigProperties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 用protostuff序列化&kryo序列化
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
	public void protostuffTest(){
		Jedis jedis=jedisPool.getResource();
		try {
			String key="protostuff:serialize";
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
	
	
	@Test
	public void kryoTest() throws FileNotFoundException{
		Jedis jedis=jedisPool.getResource();
		Output output=null;
		try {
			String key="kryo:serialize";
		    byte[] bytes=jedis.get(key.getBytes());
		    if(null != bytes){
		    	Kryo kryo = new Kryo();
		    	Input input=new Input(new ByteArrayInputStream(bytes));
		    	ConfigProperties pro=kryo.readObject(input, ConfigProperties.class);
		    	logger.info("kryo deserialize result:{}",JSONObject.toJSON(pro));
		    }else{
		    	//序列化对象 存放至redis
		    	ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		    	Kryo kryo=new Kryo();
		        output=new Output(outputStream);
		        kryo.writeObject(output, config);
		        String result=jedis.setex(key.getBytes(), 3600, output.getBuffer());
		        logger.info("set into redis result:{}",result);
		    }
		} finally {
			jedis.close();
			if(null != output){
				output.close();
			}
		}
	}

}
