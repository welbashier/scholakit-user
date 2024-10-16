package com.gwais.sk_users.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue myQueue() {
    	// true = durable (all messages sent to it will be persistent)
        return new Queue("email-queue", true); 
    }
}