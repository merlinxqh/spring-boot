package com.spring.mq.rabbitmq.config;


import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;  
import org.springframework.amqp.rabbit.connection.ConnectionFactory;  
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;  
import org.springframework.beans.factory.config.ConfigurableBeanFactory;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.context.annotation.Scope;  
  
@Configuration  
public class RabbitMqConfig {  
  
    public static final String FOO_EXCHANGE   = "callback.exchange.foo";  
    public static final String FOO_ROUTINGKEY = "callback.routingkey.foo";  
    public static final String FOO_QUEUE      = "callback.queue.foo";  
  
    @Value("${spring.rabbitmq.addresses}")  
    private String addresses;  
    @Value("${spring.rabbitmq.username}")  
    private String username;  
    @Value("${spring.rabbitmq.password}")  
    private String password;  
    @Value("${spring.rabbitmq.virtual-host}")  
    private String virtualHost;  
    @Value("${spring.rabbitmq.publisher-confirms}")  
    private boolean publisherConfirms;  
  
    @Bean  
    public ConnectionFactory connectionFactory() {  
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
        connectionFactory.setAddresses(addresses);  
        connectionFactory.setUsername(username);  
        connectionFactory.setPassword(password);  
        connectionFactory.setVirtualHost(virtualHost);  
        /** 如果要进行消息回调，则这里必须要设置为true */  
        connectionFactory.setPublisherConfirms(publisherConfirms);  
        return connectionFactory;  
    }  
  
    @Bean  
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */  
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate template = new RabbitTemplate(connectionFactory());  
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;  
    }  
    
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }
  
}  