package com.otmancr.server.controller;

import com.otmancr.server.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RpcServerController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMqConfig.RPC_MESSAGE_QUEUE)
    public void process(Message message) {
        byte[] body = message.getBody();
        //This is the message to be returned by the server
        Message build = MessageBuilder.withBody(("I am the server, I received the message from the client：" + new String(body)).getBytes()).build();
        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationId());
        rabbitTemplate.sendAndReceive(RabbitMqConfig.RPC_EXCHANGE, RabbitMqConfig.RPC_REPLY_MESSAGE_QUEUE, build, correlationData);
    }
}
