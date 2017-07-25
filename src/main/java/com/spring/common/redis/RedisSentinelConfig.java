//package com.spring.redis;
//
//import java.util.HashSet;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import RedisProperties;
//
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * redis sentinel配置
// * @author leo
// *
// */
//@Configuration  
//@EnableConfigurationProperties(RedisProperties.class)  
//@ConditionalOnProperty(name = "ecej.redis.sentinel")  //TODO
//public class RedisSentinelConfig {
//	private Logger LOG = LoggerFactory.getLogger(RedisSentinelConfig.class);  
//	
//	@Autowired
//	private RedisProperties redisProperties;
//	
//    public JedisPoolConfig jedisPoolConfig() {  
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();  
//        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());  
//        jedisPoolConfig.setMaxTotal(redisProperties.getMaxTotal());  
//        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());  
//        return jedisPoolConfig;  
//  
//    }  
//  
//    public RedisSentinelConfiguration jedisSentinelConfig() {  
//        String[] hosts = redisProperties.getHostName().split(",");  
//        HashSet<String> sentinelHostAndPorts = new HashSet<>();  
//        for (String hn : hosts) {  
//            sentinelHostAndPorts.add(hn);  
//        }  
//        return new RedisSentinelConfiguration(redisProperties.getMasterName(), sentinelHostAndPorts);  
//  
//    }
//    
//    @Bean  
//    public JedisConnectionFactory jedisConnectionFactory() {  
//  
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisSentinelConfig(),  
//                jedisPoolConfig());  
//        if (!StringUtils.isEmpty(redisProperties.getPassword()))  
//            jedisConnectionFactory.setPassword(redisProperties.getPassword());  
//        return jedisConnectionFactory;  
//    }  
//  
//    @Bean  
//    public RedisTemplate<String, String> redisTemplate() {  
//  
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();  
//        redisTemplate.setConnectionFactory(jedisConnectionFactory());  
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());  
//        LOG.info("create redisTemplate success");  
//        return redisTemplate;  
//    }  
//}
