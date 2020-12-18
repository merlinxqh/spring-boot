package com.spring.common.applicationrunner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @ClassName ServiceApplicationRunner
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/7/24 9:17
 * @Version 1.0
 */
@Component
@Order(2)
@Slf4j
public class ServiceApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
