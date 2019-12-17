package com.bridgelabz.fundoonote.response;

import java.io.Serializable;

import lombok.Data;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 * @param 
 * @Date 20 Nov 2019
 */

@Data
public class Response   implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5278408958669964093L;
	private int status;
	private String message;
	private Object data;
	/**
	 * @param status
	 * @param message
	 * @param data
	 */
	public Response(int status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	
	
}
