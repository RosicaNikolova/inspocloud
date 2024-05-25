package com.inspocloud.usermanagementservice.config;

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
    private String userQueue;

    @Value("${user.create.exchange}")
    private String directExchange;

    @Value("${user.create.routing-key}")
    private String routingKey;

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }

    @Bean
    Queue userQueue() {
        return new Queue(userQueue);
    }

    @Bean
    Binding binding(Queue userQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(userQueue).to(directExchange).with(routingKey);
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

    @Value("${delete.queue.ps}")
    private String photoQueue;

    @Value("${delete.queue.as}")
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
    public Queue photoQueue() {
        return new Queue(photoQueue);
    }

    @Bean
    public Binding authBinding(FanoutExchange fanoutExchange, Queue authQueue) {
        return BindingBuilder.bind(authQueue).to(fanoutExchange);
    }

    @Bean
    public Binding photoBinding(FanoutExchange fanoutExchange, Queue photoQueue) {
        return BindingBuilder.bind(photoQueue).to(fanoutExchange);
    }



    //RabbitMQ values for editing user
    //Fanout exchange config

    @Value("${user.edit.exchange}")
    private String editUserExchange;

    @Value("${edit.queue.ps}")
    private String photoEditQueue;

    @Value("${edit.queue.as}")
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
    public Queue photoEditQueue() {
        return new Queue(photoEditQueue);
    }

    @Bean
    public Binding authEditBinding(FanoutExchange editUserFanoutExchange, Queue authEditQueue) {
        return BindingBuilder.bind(authEditQueue).to(editUserFanoutExchange);
    }

    @Bean
    public Binding photoEditBinding(FanoutExchange editUserFanoutExchange, Queue photoEditQueue) {
        return BindingBuilder.bind(photoEditQueue).to(editUserFanoutExchange);
    }

}

