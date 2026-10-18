package com.app.Producto.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {

	public static final String EXCHANGE = "facturacion.exchange";
	public static final String QUEUE = "facturacion.eventos.queue";
	public static final String ROUTING_KEY = "facturacion.eventos.routingkey";

	@Bean
	public Queue facturacionQueue() {
		return new Queue(QUEUE, true);
	}

	@Bean
	public TopicExchange facturacionExchange() {
		return new TopicExchange(EXCHANGE);
	}

	@Bean
	public Binding facturacionBinding() {
		return BindingBuilder.bind(facturacionQueue()).to(facturacionExchange()).with(ROUTING_KEY);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
}
