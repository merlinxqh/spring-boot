package com.spring.serialize;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * protostuff序列化
 * @author leo
 *
 */
public class ProtostuffSerailize implements RedisSerializer<Object>{
	
	
	@Override
	public byte[] serialize(Object obj) throws SerializationException {
		if (obj == null) {
            throw new RuntimeException("序列化对象(" + obj + ")!");
        }
		Schema schema = (Schema) RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("序列化(" + obj.getClass() + ")对象(" + obj + ")发生异常!", e);
        } finally {
            buffer.clear();
        }
        return protostuff;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length == 0) {
            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
        }
        Object instance = null;
        try {
            instance = Object.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e);
        }
        Schema<Object> schema = RuntimeSchema.getSchema(Object.class);
        ProtostuffIOUtil.mergeFrom(bytes, instance, schema);
        return instance;
	}

}
