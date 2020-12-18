//package com.spring.common.config.autoregister;
//
//import groovy.util.logging.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @ClassName AnoOriginBean
// * @Description TODO
// * @Author xuqianghui
// * @Date 2019/7/22 17:33
// * @Version 1.0
// */
//@Slf4j
//@Component
//public class AnoOriginBean {
//    // 希望可以注入 主动注册的Bean
//    @Autowired
//    private ManualBean manualBean;
//
//    public AnoOriginBean() {
//        System.out.println("AnoOriginBean init: " + System.currentTimeMillis());
//    }
//
//    public String print() {
//        return "[AnoOriginBean] print！！！ manualBean == null ? " + (manualBean == null);
//    }
//}
