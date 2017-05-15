package com.spring.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by leo on 2017/4/29.
 */
@RunWith(value=SpringRunner.class)
@SpringBootTest
public class RedisTest {
	
	private final static Logger logger=LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    private ObjectRedisTemplate redisTemplate;

    @Test
    public void test(){
//        redisTemplate.hashSet("mapName","mapKey1","abcdefg_1");
//        redisTemplate.hashSet("mapName","mapKey2","abcdefg_2");
//        redisTemplate.hashSet("mapName","mapKey3","abcdefg_3");
//        System.out.println(redisTemplate.hashGetAll("mapName"));
//        for(int i=0;i<100;i++){
//            redisTemplate.setToQueue("queue_int",""+i,100);
//        }
//        System.out.println("------------------------"+redisTemplate.getFromQueue("queue_int",100));
//        System.out.println("------------------------  "+redisTemplate.popQueue("queue_int"));
//        redisTemplate.setString("string:incr:key","200");
//        redisTemplate.incrementBack("string:incr:key");
//        System.out.println("------------------------=============   "+redisTemplate.getString("string:incr:key"));
//        System.out.println("------------------------");
        System.out.println(redisTemplate.isMember("mapKey3","abcdefg_3") + "    "+redisTemplate.hashExists("mapName","mapKey3"));
    }
}
