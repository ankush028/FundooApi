package com.bridgelabz.fundoonote.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.bridgelabz.fundoonote.model.RabbitMq;

public class MessageReceiver {
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * purpose: this method will take a rabbitMQ body as input,and sends mail by
	 * 			fetching all details from that body
	 * 
	 * @param body RabbitMQBody
	 */
	public void sendMessage(RabbitMq body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(body.getEmail());
		message.setText(body.getBody());
		message.setSubject(body.getSubject());
		mailSender.send(message);

	}

}
