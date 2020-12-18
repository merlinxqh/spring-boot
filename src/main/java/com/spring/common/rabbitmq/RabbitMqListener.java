package com.spring.common.rabbitmq;

import com.rabbitmq.client.Channel;
import com.spring.common.rabbitmq.config.RabbitMqConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.Assert;

import java.util.Map;

@Configuration
@EnableRabbit
@Slf4j
public class RabbitMqListener {

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

   /**
    * 可以直接通过注解声明交换器、绑定、队列。但是如果声明的和rabbitMq中已经存在的不一致的话
    * 会报错便于测试，我这里都是不使用持久化，没有消费者之后自动删除
    * {@link RabbitListener}是可以重复的。并且声明队列绑定的key也可以有多个.
    *
    * @param headers
    * @param msg
    */
   @RabbitListener(
           bindings = @QueueBinding(
                   exchange = @Exchange(value = RabbitMQConstant.DEFAULT_EXCHANGE, type = ExchangeTypes.TOPIC,
                           durable = RabbitMQConstant.FALSE_CONSTANT, autoDelete = RabbitMQConstant.true_CONSTANT),
                   value = @org.springframework.amqp.rabbit.annotation.Queue(
                           value = RabbitMQConstant.DEFAULT_QUEUE, durable = RabbitMQConstant.FALSE_CONSTANT,
                           autoDelete = RabbitMQConstant.true_CONSTANT),
                   key = {""}
           ),
           //手动指明消费者的监听容器，默认Spring为自动生成一个SimpleMessageListenerContainer
           containerFactory = "container",
           //指定消费者的线程数量,一个线程会打开一个Channel，一个队列上的消息只会被消费一次（不考虑消息重新入队列的情况）,下面的表示至少开启5个线程，最多10个。线程的数目需要根据你的任务来决定，如果是计算密集型，线程的数目就应该少一些
           concurrency = "5-10"
   )
   public void process(@Headers Map<String, Object> headers, @Payload ExampleEvent msg) {
      log.info("basic consumer receive message:{headers = [" + headers + "], msg = [" + msg + "]}");
   }

   @Data
   @Builder
   @AllArgsConstructor
   public static class ExampleEvent{

   }

   /**
    * {@link Queue#ignoreDeclarationExceptions}
    * 声明队列会忽略错误不声明队列，这个消费者仍然是可用的
    *
    * @param headers
    * @param msg
    */
   @RabbitListener(queuesToDeclare = @org.springframework.amqp.rabbit.annotation.Queue(value = RabbitMQConstant.DEFAULT_QUEUE, ignoreDeclarationExceptions = RabbitMQConstant.true_CONSTANT))
   public void process2(@Headers Map<String, Object> headers, @Payload ExampleEvent msg) {
      log.info("basic2 consumer receive message:{headers = [" + headers + "], msg = [" + msg + "]}");
   }


   /**
    * 消费端 手动确认消息
    * @param msg
    * @param channel
    * @param deliveryTag
    * @param redelivered
    * @param head
    */
   @SneakyThrows
   @RabbitListener(bindings = @QueueBinding(
           exchange = @Exchange(value = RabbitMQConstant.CONFIRM_EXCHANGE, type = ExchangeTypes.TOPIC,
                   durable = RabbitMQConstant.FALSE_CONSTANT, autoDelete = RabbitMQConstant.true_CONSTANT),
           value = @org.springframework.amqp.rabbit.annotation.Queue(
                   value = RabbitMQConstant.CONFIRM_QUEUE, durable = RabbitMQConstant.FALSE_CONSTANT,
                   autoDelete = RabbitMQConstant.true_CONSTANT),
           key = RabbitMQConstant.CONFIRM_KEY),
           containerFactory = "containerWithConfirm")
   public void process(ExampleEvent msg, Channel channel, @Header(name = "amqp_deliveryTag") long deliveryTag,
                       @Header("amqp_redelivered") boolean redelivered, @Headers Map<String, String> head) {
      try {
         log.info("ConsumerWithConfirm receive message:{},header:{}", msg, head);
         channel.basicAck(deliveryTag, false);
      } catch (Exception e) {
         log.error("consume confirm error!", e);
         //这一步千万不要忘记，不会会导致消息未确认，消息到达连接的qos之后便不能再接收新消息
         //一般重试肯定的有次数，这里简单的根据是否已经重发过来来决定重发。第二个参数表示是否重新分发
         channel.basicReject(deliveryTag, !redelivered);
         //这个方法我知道的是比上面多一个批量确认的参数
         // channel.basicNack(deliveryTag, false,!redelivered);
      }
   }
}
