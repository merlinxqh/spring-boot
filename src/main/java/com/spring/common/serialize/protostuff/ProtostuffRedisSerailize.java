package com.spring.common.serialize.protostuff;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * protostuff序列化
 * @author leo
 *
 */
public class ProtostuffRedisSerailize implements RedisSerializer<Object>{
    private Converter<Object, byte[]> serializer = new SerializingConverter(new ProtostuffSerializer());
    private Converter<byte[], Object> deserializer = new DeserializingConverter(new ProtostuffDeSerializer());

    public Object deserialize(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

    public byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }
        try {
            return serializer.convert(object);
        } catch (Exception ex) {
            throw new SerializationException("Cannot serialize", ex);
        }
    }

//	public byte[] serialize(Object obj){
//		if (obj == null) {
//            throw new RuntimeException("序列化对象(" + obj + ")!");
//        }
//		Schema schema = (Schema) RuntimeSchema.getSchema(obj.getClass());
//        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
//        byte[] protostuff = null;
//        try {
//            protostuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        } catch (Exception e) {
//            throw new RuntimeException("序列化(" + obj.getClass() + ")对象(" + obj + ")发生异常!", e);
//        } finally {
//            buffer.clear();
//        }
//        return protostuff;
//	}
//
//	public Object deserialize(byte[] bytes){
//		if (bytes == null || bytes.length == 0) {
//            throw new RuntimeException("反序列化对象发生异常,byte序列为空!");
//        }
//        Object instance = null;
//        try {
//            instance = Object.class.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            throw new RuntimeException("反序列化过程中依据类型创建对象失败!", e);
//        }
//        Schema<Object> schema = RuntimeSchema.getSchema(Object.class);
//        ProtostuffIOUtil.mergeFrom(bytes, instance, schema);
//        return instance;
//	}

}
