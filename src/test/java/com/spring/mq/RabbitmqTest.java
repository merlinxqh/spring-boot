package com.spring.mq;

import com.spring.common.rabbitmq.RabbitMqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by leo on 2017/5/16.
 */
@RunWith(value=SpringRunner.class)
@SpringBootTest
public class RabbitmqTest {

    @Autowired
    private RabbitMqSender sender;

    @Test
    public void test(){
        sender.send("发送了一条消息给你");
    }

}
