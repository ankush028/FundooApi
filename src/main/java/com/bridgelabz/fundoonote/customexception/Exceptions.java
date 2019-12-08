package com.bridgelabz.fundoonote.customexception;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

public class Exceptions extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	

	/**
	 * @param message
	 */
	public Exceptions(String message) {
		super(message);
	}

}
