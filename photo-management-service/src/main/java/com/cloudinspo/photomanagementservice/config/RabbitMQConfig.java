package com.cloudinspo.photomanagementservice.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //RabbitMQ values for uploading photos, received from Photo Upload Service
    //Direct exchange config

    @Value("${photo.upload.queue}")
    private String queueName;

    @Value("${photo.upload.exchange}")
    private String exchange;

    @Value("${photo.upload.routing-key}")
    private String routingKey;

    @Bean
    Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
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


    //RabbitMQ values for deleting user, received from User Management Service
    //Fanout exchange config

    @Value("${delete.user.queue}")
    private String photoQueue;

    @Value("${delete.user.exchange}")
    private String deleteUserExchange;


    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(deleteUserExchange);
    }

    @Bean
    public Queue photoQueue() {
        return new Queue(photoQueue);
    }


    @Bean
    public Binding photoBinding(FanoutExchange fanoutExchange, Queue photoQueue) {
        return BindingBuilder.bind(photoQueue).to(fanoutExchange);
    }


    //RabbitMQ values for editing user, received from User Management Service
    //Fanout exchange config

    @Value("${user.edit.exchange}")
    private String editUserExchange;

    @Value("${edit.queue.ps}")
    private String photoEditQueue;

    @Bean
    public FanoutExchange editUserFanoutExchange() {
        return new FanoutExchange(editUserExchange);
    }

    @Bean
    public Queue photoEditQueue() {
        return new Queue(photoEditQueue);
    }

    @Bean
    public Binding photoEditBinding(FanoutExchange editUserFanoutExchange, Queue photoEditQueue) {
        return BindingBuilder.bind(photoEditQueue).to(editUserFanoutExchange);
    }


    //RabbitMQ values for deleting photo, sent to Photo Upload Service
    //Direct exchange config

    @Value("${delete.queue.ps}")
    private String deletePhotoQueue;

    @Value("${delete.photo.exchange}")
    private String deletePhotoExchange;

    @Value("${delete.photo.routing-key}")
    private String deletePhotoRoutingKey;

    @Bean
    Queue DeletePhotoQueue() {
        return new Queue(deletePhotoQueue);
    }

    @Bean
    DirectExchange DeletePhotoExchange() {
        return new DirectExchange(deletePhotoExchange);
    }

    @Bean
    Binding DeletePhotoBinding(Queue DeletePhotoQueue, DirectExchange DeletePhotoExchange) {
        return BindingBuilder.bind(DeletePhotoQueue).to(DeletePhotoExchange).with(deletePhotoRoutingKey);
    }


}
