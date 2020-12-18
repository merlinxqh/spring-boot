package com.spring.common.config.autoregister;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName OriginBean
 * @Description TODO
 * @Author xuqianghui
 * @Date 2019/7/22 17:31
 * @Version 1.0
 */
@Slf4j
@Component
public class OriginBean {

    private LocalDateTime time;

    public OriginBean() {
        time = LocalDateTime.now();
    }

    public String print(String msg) {
        return "[OriginBean] print msg: " + msg + ", time: " + time;
    }
}
