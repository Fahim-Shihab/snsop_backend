package com.kit.snsop.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value(value = "${kit.rabbit.registration.exchange}")
    private String REGISTRATION_EXCHANGE_NAME;

    @Value(value = "${kit.rabbit.registration.routing-key}")
    private String REGISTRATION_ROUTING_KEY;

    @Value(value = "${kit.rabbit.registration.queue}")
    private String REGISTRATION_QUEUE_NAME;

    @Value(value = "${kit.rabbit.edit.exchange}")
    private String EDIT_EXCHANGE_NAME;

    @Value(value = "${kit.rabbit.edit.routing-key}")
    private String EDIT_ROUTING_KEY;

    @Value(value = "${kit.rabbit.edit.queue}")
    private String EDIT_QUEUE_NAME;

    @Bean
    public Queue registrationQueue() {
        return new Queue(REGISTRATION_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange registrationExchange() {
        return new DirectExchange(REGISTRATION_EXCHANGE_NAME);
    }

    @Bean
    public Binding registrationBinding(Queue registrationQueue, DirectExchange registrationExchange) {
        return BindingBuilder
                .bind(registrationQueue)
                .to(registrationExchange)
                .with(REGISTRATION_ROUTING_KEY);
    }

    @Bean
    public Queue editQueue() {
        return new Queue(EDIT_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange editExchange() {
        return new DirectExchange(EDIT_EXCHANGE_NAME);
    }

    @Bean
    public Binding editBinding(Queue editQueue, DirectExchange editExchange) {
        return BindingBuilder
                .bind(editQueue)
                .to(editExchange)
                .with(EDIT_ROUTING_KEY);
    }

    // === Message Converter ===
    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("com.kit.snsop.beneficiary.model"); // allow only your DTO package
        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    // === RabbitTemplate with converter ===
    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                       MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}
