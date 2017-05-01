package com.spring.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.spring.serialize.SerailizeTest;

/**
 * Created by leo on 2017/4/29.
 */
@RunWith(value=SpringRunner.class)
@SpringBootTest
public class RedisTest {
	
	private final static Logger logger=LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test(){
    	logger.info("set data to redis start...");
        redisTemplate.opsForValue().set("redis:test:schoolone", SerailizeTest.getSchool());
        logger.info("set data to redis end...");
    }
}
