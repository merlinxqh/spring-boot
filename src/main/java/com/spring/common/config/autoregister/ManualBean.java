package com.spring.common.config.autoregister;

import java.util.Random;

/**
 * @ClassName ManualBean
 * @Description 手动注册bean
 * @Author xuqianghui
 * @Date 2019/7/22 17:30
 * @Version 1.0
 */
public class ManualBean {
    private int id;

    public ManualBean() {
        Random random = new Random();
        id = random.nextInt(100);
    }

    public String print(String msg) {
        return "[ManualBean] print : " + msg + " id: " + id;
    }
}
