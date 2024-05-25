package com.inspocloud.authservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    //Direct exchange configuration
    //RabbitMQ values for creating user
    @Value("${user.create.queue}")
    private String queueName;

    @Value("${user.create.exchange}")
    private String exchange;

    @Value("${user.create.routing-key}")
    private String routingKey;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }


    //RabbitMQ values for deleting user
    //Fanout exchange config

    @Value("${delete.queue}")
    private String authQueue;

    @Value("${delete.user.exchange}")
    private String deleteUserExchange;

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(deleteUserExchange);
    }

    @Bean
    public Queue authQueue() {
        return new Queue(authQueue);
    }


    @Bean
    public Binding authBinding(FanoutExchange fanoutExchange, Queue authQueue) {
        return BindingBuilder.bind(authQueue).to(fanoutExchange);
    }




    //RabbitMQ values for editing user
    //Fanout exchange config

    @Value("${user.edit.exchange}")
    private String editUserExchange;

    @Value("${edit.queue}")
    private String authEditQueue;


    @Bean
    public FanoutExchange editUserFanoutExchange() {
        return new FanoutExchange(editUserExchange);
    }

    @Bean
    public Queue authEditQueue() {
        return new Queue(authEditQueue);
    }

    @Bean
    public Binding authEditBinding(FanoutExchange editUserFanoutExchange, Queue authEditQueue) {
        return BindingBuilder.bind(authEditQueue).to(editUserFanoutExchange);
    }


}
