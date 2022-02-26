package com.otmancr.server.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String RPC_MESSAGE_QUEUE = "rpc_msg_queue";
    public static final String RPC_REPLY_MESSAGE_QUEUE = "rpc_reply_msg_queue";
    public static final String RPC_EXCHANGE = "rpc_exchange";

    @Bean
    Queue msgQueue() {
        return new Queue(RPC_MESSAGE_QUEUE);
    }

    @Bean
    Queue replyQueue() {
        return new Queue(RPC_REPLY_MESSAGE_QUEUE);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(RPC_EXCHANGE);
    }

    @Bean
    Binding msgBinding() {
        return BindingBuilder.bind(msgQueue())
                .to(topicExchange())
                .with(RPC_MESSAGE_QUEUE);
    }

    @Bean
    Binding replyBinding() {
        return BindingBuilder.bind(replyQueue())
                .to(topicExchange())
                .with(RPC_REPLY_MESSAGE_QUEUE);
    }
}
