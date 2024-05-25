package com.cloudinspo.photouploadservice.configuration;

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

    @Value("${photo.upload.queue}")
    private String queueName;

    @Value("${photo.upload.exchange}")
    private String exchange;

    @Value("${photo.upload.routing-key}")
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



    //RabbitMQ values for deleting photo, received from Photo Management Service
    //Direct exchange config

    @Value("${delete.photo.queue}")
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
