package com.spring.config;

import com.alibaba.fastjson.JSON;
import com.spring.config.properties.RedisProperties;
import com.spring.serialize.hessian2.Hessian2SerializationRedisSerializer;
import com.spring.serialize.protostuff.ProtostuffRedisSerailize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2017/4/29.
 */
@Configuration
@EnableAutoConfiguration
public class RedisConfig {

    @Autowired
    private RedisProperties redis;

    /**
     * Jedis 连接池配置
     * @return
     */
    @Bean(name="jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        System.out.println("redis config:"+JSON.toJSONString(redis));
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMinIdle(redis.getMinIdle());
        config.setMaxIdle(redis.getMaxIdle());
        config.setMaxWaitMillis(redis.getMaxWaitMillis());
        config.setTestOnBorrow(redis.isTestOnBorrow());
        config.setTestOnReturn(redis.isTestOnReturn());
        config.setTestWhileIdle(redis.isTestWhileIdle());
        return config;
    }

    /**
     * jedis连接工厂配置
     * @return
     */
    @Bean(name="jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory factory=new JedisConnectionFactory(redisSenitnelConfiguration(), jedisPoolConfig());
        factory.setTimeout(redis.getTimeout());
        factory.setPassword(redis.getPassword());
        return factory;
    }

    /**
     * redis监听哨兵
     * @return
     */
    @Bean
    public RedisSentinelConfiguration redisSenitnelConfiguration(){
        RedisSentinelConfiguration sentinel=new RedisSentinelConfiguration();
        //redis主库节点
        RedisNode masterNode=new RedisNode(redis.getMasterHost(), redis.getMasterPort());
        masterNode.setName(redis.getMasterName());
        sentinel.setMaster(masterNode);
        
        //sentinel监听节点
        List<RedisNode> sentinels=new ArrayList<RedisNode>();
        for(String host:redis.getSentinelHost().split(",")){
        	RedisNode sentinelNode=new RedisNode(host.split(":")[0], Integer.valueOf(host.split(":")[1]));
            sentinels.add(sentinelNode);
        }
        sentinel.setSentinels(sentinels);
        return sentinel;
    }
	@Bean
	public StringRedisSerializer stringRedisSerializer(){
		return new StringRedisSerializer();
	}
	

	@Bean
	public Hessian2SerializationRedisSerializer hessian2Serializer(){
	    return new Hessian2SerializationRedisSerializer();
    }


//    @Bean
//    public RedisTemplate<String,Object> redisTemplate(){
//        RedisTemplate<String,Object> template=new RedisTemplate<String,Object>();
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.setKeySerializer(stringRedisSerializer());
//        template.setHashKeySerializer(stringRedisSerializer());
//        template.setDefaultSerializer(protostuffSerailize());
//        return template;
//    }

    @Bean
    public StringRedisTemplate redisTemplate(){
       StringRedisTemplate redisTemplate=new StringRedisTemplate();
       redisTemplate.setConnectionFactory(jedisConnectionFactory());
       return redisTemplate;
    }

}
