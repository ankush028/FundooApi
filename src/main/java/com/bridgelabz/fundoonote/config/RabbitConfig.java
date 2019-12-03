//package com.bridgelabz.fundoonote.config;
//
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//	
//@Configuration
//  public class RabbitConfig {
//		String qname;
//		String exchange;
//		String routingKey;
//		@Bean
//		public Queue queue() {
//			return new Queue(qname,false);
//		}
//		@Bean
//		public DirectExchange exchange() {
//			return new DirectExchange(exchange);
//		}
//		@Bean
//		public Binding binding(Queue queue,DirectExchange exchange) {
//			return BindingBuilder.bind(queue).to(exchange).with(routingKey);
//		}
//		@Bean
//		public  MessageConverter jsonMessageConverter() {
//				return new Jackson2JsonMessageConverter();
//			}
//		@Bean
//		public AmqpTemplate rabbitTemplate(ConnectionFactory connfactory) {
//			final RabbitTemplate rabbitTemplate= new RabbitTemplate(connfactory);
//			rabbitTemplate.setMessageConverter(jsonMessageConverter());
//			
//			return rabbitTemplate;
//		}
//}
