package com.bridgelabz.fundoonote.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RabbitMq {
	//private String from;
	private String to;
	private String message;
	private String subject;

}
