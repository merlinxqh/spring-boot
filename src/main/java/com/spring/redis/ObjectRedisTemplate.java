package com.spring.redis;

import com.spring.serialize.ProtoStuffSerializerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ObjectRedisTemplate{
    
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 保存对象到redis
	 * @param key
	 * @param obj
	 */
	public <T> boolean setObj(String key,T obj){
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = ProtoStuffSerializerUtils.serialize(obj);
		boolean result=redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(bkey, bvalue);
				return true;
			}
		});

		return result;
	}

	/**
	 * 保存list 对象到redis
	 * @param key
	 * @param list
	 * @return
	 */
	public <T> boolean setListObj(String key, List<T> list){
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = ProtoStuffSerializerUtils.serializeList(list);
		boolean result=redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(bkey, bvalue);
				return true;
			}
		});

		return result;
	}

	/**
	 * 保存list 对象到redis
	 * 设置过期时间
	 * @param key
	 * @param list
	 * @return
	 */
	public <T> boolean setListObj(String key, List<T> list,final long expireTime){
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = ProtoStuffSerializerUtils.serializeList(list);
		boolean result=redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setEx(bkey, expireTime, bvalue);
				return true;
			}
		});

		return result;
	}


	public <T> boolean setObj(String key,T obj,final long expireTime){
		final byte[] bkey = key.getBytes();
		final byte[] bvalue = ProtoStuffSerializerUtils.serialize(obj);
		boolean result=redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setEx(bkey, expireTime, bvalue);
				return true;
			}
		});

		return result;
	}

	/**
	 * 从redis中读取对象
	 * @param key
	 * @param targetClass
	 * @return
	 */
	public <T> T getObj(String key, Class<T> targetClass){
		byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
		if (result == null) {
			return null;
		}
		return ProtoStuffSerializerUtils.deserialize(result, targetClass);
	}

	/**
	 * 从redis中读取list对象
	 * @param key
	 * @param targetClass
	 * @return
	 */
	public <T> List<T> getListObj(String key, Class<T> targetClass){
		byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
		if (result == null) {
			return null;
		}
		return ProtoStuffSerializerUtils.deserializeList(result, targetClass);
	}

	/**
	 * 保存到hash集合中
	 * @param hName  集合名称
	 * @param key
	 * @param value
	 */
	public void hashSet(String hName, String key, String value){
       redisTemplate.opsForHash().put(hName, key, value);
	}

	/**
	 * 根据集合名称  获取所有的值
	 * @param mapName
	 * @return
	 */
	public Map<Object, Object> hashGetAll(String mapName){
		return redisTemplate.opsForHash().entries(mapName);
	}
	
}
