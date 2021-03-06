package com.spring.common.rabbitmq;

import com.spring.common.rabbitmq.config.RabbitMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.Assert;

@Configuration
public class RabbitMqListener {
   private final static Logger logger=LoggerFactory.getLogger(RabbitMqListener.class);
   
   /**
    * 设置交换机类型
    * @return
    */
   @Bean
   public DirectExchange defaultExchange(){
       /** 
        * DirectExchange:按照routingkey分发到指定队列 
        * TopicExchange:多关键字匹配 
        * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念 
        * HeadersExchange ：通过添加属性key-value匹配 
        */ 
	   return new DirectExchange(RabbitMqConfig.FOO_EXCHANGE);
   }
   
   @Bean
   public Queue testQueue(){
	   return new Queue(RabbitMqConfig.FOO_QUEUE);
   }
   
   @Bean  
   public Binding binding() {
       /** 将队列绑定到交换机 */
       return BindingBuilder.bind(testQueue()).to(defaultExchange()).with(RabbitMqConfig.FOO_ROUTINGKEY);  
   } 
   
   @RabbitListener(queues=RabbitMqConfig.FOO_QUEUE, containerFactory="rabbitListenerContainerFactory")
   public void process(@Payload String foo){
      System.out.println("---------------"+foo);
       Assert.isTrue(false,"就是不成功");
   }
}
