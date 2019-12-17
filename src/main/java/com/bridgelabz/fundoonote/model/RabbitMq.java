package com.bridgelabz.fundoonote.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2168276084004040044L;
	
	
	private String email;
	private String body;
	private String subject;

}
