package com.spring.common.config.autoregister;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @ClassName ManualDIBean
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/7/22 17:31
 * @Version 1.0
 */
public class ManualDIBean {

    private int id;

    @Autowired
    private OriginBean originBean;

    private String name;

    public ManualDIBean(String name) {
        Random random = new Random();
        this.id = random.nextInt(100);
        this.name = name;
    }

    public String print(String msg) {
        String o = originBean.print(" call by ManualDIBean! ");
        return "[ManualDIBean] print: " + msg + " id: " + id + " name: " + name + " originBean print:" + o;
    }
}
