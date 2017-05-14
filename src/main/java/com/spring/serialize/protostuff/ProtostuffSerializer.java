package com.spring.serialize.protostuff;

import org.springframework.core.serializer.Serializer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by leo on 2017/5/13.
 */
public class ProtostuffSerializer implements Serializer<Object> {

    @Override
    public void serialize(Object object, OutputStream outputStream) throws IOException {

    }
}
