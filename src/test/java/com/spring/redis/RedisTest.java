package com.spring.redis;

import com.alibaba.fastjson.JSON;
import com.spring.common.redis.ObjectRedisTemplate;
import com.spring.common.serialize.School;
import com.spring.common.serialize.SerailizeTest;
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
//            redisTemplate.saveToSet("set_key",""+i);
//        }
//        System.out.println(redisTemplate.existsInSet("set_key","200"));
//        System.out.println(redisTemplate.listSet("set_key"));
//        for(int i=0;i<20;i++){
//            System.out.println(redisTemplate.popSet("set_key"));
//        }
//        System.out.println("------------------------"+redisTemplate.getFromQueue("queue_int",100));
//        System.out.println("------------------------  "+redisTemplate.popQueue("queue_int"));
//        redisTemplate.setString("string:incr:key","200");
//        redisTemplate.incrementBack("string:incr:key");
//        System.out.println("------------------------=============   "+redisTemplate.getString("string:incr:key"));
//        System.out.println("------------------------");
//        System.out.println(redisTemplate.existsInSet("mapKey3","abcdefg_3") + "    "+redisTemplate.hashExists("mapName","mapKey3"));

          redisTemplate.setObjToMap("mapName","mapKeyObj", SerailizeTest.getSchool());
        System.out.println(JSON.toJSONString(redisTemplate.getObjFromMap("mapName","mapKeyObj", School.class)));
    }
}
