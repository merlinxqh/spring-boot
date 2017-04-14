package com.spring.mq.rabbitmq;

import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.mq.rabbitmq.config.RabbitMqConfig;

/**
 * @author      leo.xu
 * @description 增加回调处理
 * @date        2017年4月13日 下午6:11:12
 * @email       leo.xu@bainuo.com
 */
@Component
public class RabbitMqSender implements RabbitTemplate.ConfirmCallback{
	
	private final static org.slf4j.Logger logger=LoggerFactory.getLogger(RabbitMqSender.class);
   
	private RabbitTemplate rabbitTemplate;
    
	@Autowired
	public RabbitMqSender(RabbitTemplate rabbitTemplate){
		this.rabbitTemplate=rabbitTemplate;
		this.rabbitTemplate.setConfirmCallback(this);
	}
	
	public void send(String msg){
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString()); 
		logger.info("send id:{}",correlationData.getId());
		this.rabbitTemplate.convertAndSend(RabbitMqConfig.FOO_EXCHANGE,RabbitMqConfig.FOO_ROUTINGKEY,msg,correlationData);
	}
    
	/**
	 * 回调函数
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		logger.info("confirm id:{}",correlationData.getId());
	}
}
