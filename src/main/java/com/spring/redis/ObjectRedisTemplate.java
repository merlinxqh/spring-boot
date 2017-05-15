package com.spring.redis;

import com.spring.serialize.ProtoStuffSerializerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ObjectRedisTemplate{
    
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 保存string到redis
	 * @param key
	 * @param value
	 */
	public void setString(String key,String value){
		redisTemplate.opsForValue().set(key,value);
	}

	/**
	 *
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void setString(String key,String value,long seconds){
		redisTemplate.opsForValue().set(key,value,seconds, TimeUnit.SECONDS);
	}

	/**
	 * get string
	 * @param key
	 * @return
	 */
	public String getString(String key){
		return redisTemplate.opsForValue().get(key);
	}


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
	 * 判断hashmap中是否包含key
	 * @param hashName
	 * @param key
	 * @return
	 */
	public boolean hashExists(String hashName,String key){
		return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
			return connection.hExists(hashName.getBytes(), key.getBytes());
		});
	}

	/**
	 * 根据集合名称  获取所有的值
	 * @param mapName
	 * @return
	 */
	public Map<Object, Object> hashGetAll(String mapName){
		return redisTemplate.opsForHash().entries(mapName);
	}


	/**
	 * 保存数据到 set集合中
	 * @param key
	 * @param value
	 */
	public void saveToSet(String key,String value){
		redisTemplate.opsForSet().add(key,value);
	}


	/**
	 * 存到指定的队列中
	 * 左进右出
	 * @param key
	 * @param value
	 * @param size 队列大小限制 0 为不限制
	 */
	public void setToQueue(String key,String value, long size){
		ListOperations<String, String> lo=redisTemplate.opsForList();
        if(size > 0 && lo.size(key) >= size){
        	lo.rightPop(key);
		}
		lo.leftPush(key,value);
	}

	/**
	 * 从队列中取得数据
	 * @param key
	 * @param size
	 * @return
	 */
	public List<String> getFromQueue(String key,long size){
		boolean flag=redisTemplate.execute((RedisCallback<Boolean>)connection -> {
           return connection.exists(key.getBytes());
		});
		if(!flag){
			return new ArrayList<String>();
		}
		ListOperations<String, String> lo=redisTemplate.opsForList();
		if (size > 0) {
			return lo.range(key, 0, size - 1);
		} else {
			return lo.range(key, 0, lo.size(key) - 1);
		}
	}

	/**
	 * 从指定的队列里取得数据
	 * @param key
	 * @return
	 */
	public String popQueue(String key){
		return redisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 将自增变量存到redis
	 * @param key
	 * @param value
	 */
	public void setIncrement(String key,long value){
        redisTemplate.opsForValue().increment(key,value);
	}

	/**
	 *  自增
	 * @param key
	 * @return
	 */
	public Long increment(String key) {

		return redisTemplate.execute((RedisCallback<Long>) connection -> {

			return connection.incr(key.getBytes());

		});
	}

	/**
	 * 自增by value
	 *
	 * @param key
	 * @return
	 */
	public  Long incrementBy(String key, long value) {
		return redisTemplate.execute((RedisCallback<Long>) connection -> {

			return connection.incrBy(key.getBytes(), value);
		});

	}

	/**
	 * 将序列值回退一个
	 *
	 * @param key
	 * @return
	 */
	public void incrementBack(String key) {
		redisTemplate.execute((RedisCallback<Long>) connection -> connection.decr(key.getBytes()));
	}

	/**
	 * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET
	 * if Not eXists』(如果不存在，则 SET)的简写。 <br>
	 * 保存成功，返回 true <br>
	 * 保存失败，返回 false
	 */
	public boolean setNx(String key,String value){
		/** 设置成功，返回 1 设置失败，返回 0 **/
		return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
			return connection.setNX(key.getBytes(), value.getBytes());
		});
	}

	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean exists(String key){
		return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
			return connection.exists(key.getBytes());
		});
	}

}
