package com.bridgelabz.fundoonote.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonote.model.RabbitMq;


/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

@Component
public class Jms {
	
	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * @param mailSender
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender=mailSender;
	}
	
	/**
	 * @param from
	 * @param to
	 * @param subject
	 * @param message
	 * @return 
	 */
	public void sendMail(RabbitMq rabbitModel) {
		SimpleMailMessage msg = new SimpleMailMessage();
		
	//	msg.setFrom(rabbitModel.getFrom());
		msg.setTo(rabbitModel.getTo());
		msg.setSubject(rabbitModel.getSubject());
		msg.setText(rabbitModel.getMessage());
		mailSender.send(msg);
		
	}

}
