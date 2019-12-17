package com.bridgelabz.fundoonote.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bridgelabz.fundoonote.model.RabbitMq;

public class Utility {
	private  String date;
	
	public Utility(String date) {
		this.date=date;
	}
		
	public  Date dateFormat() throws ParseException {
		
		return new SimpleDateFormat("dd/mm/yyyy").parse(date);
	}
	
	
	
	public static RabbitMq  getRabbitMq(String email ,String token) {
		
			RabbitMq rm=new RabbitMq();
			rm.setBody(token);
			rm.setEmail(email);
			rm.setSubject("Verfication link");
			return rm;
		
		
		
	}
}
