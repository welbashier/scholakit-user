package com.gwais.sk_users.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwais.sk_users.dto.EmailRequest;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(this.queue.getName(), message);
        System.out.println("Sent: " + message);
    }

    public void sendEmailRequest(EmailRequest emailRequest) {
    	
    	try {
    		// convert request to JSON object
    		String jsonObject = objectMapper.writeValueAsString(emailRequest);
    		
    		// send it to Queue as Amqp Message 
    		rabbitTemplate.convertAndSend(this.queue.getName(), jsonObject);
    		
            System.out.println("Sent JSON message to email-queue: " + jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
