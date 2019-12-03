package com.bridgelabz.fundoonote.config;

import org.modelmapper.ModelMapper;

/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
//create singleton object of modelMapper
//@Purpose to create a singleton object of ModelMapper
public class Model {
	private Model() {
		
	}
	private static  ModelMapper mapper;
	public static ModelMapper getModel() {
		if(mapper==null) {
			return new ModelMapper();
		}
		return mapper;
	}
	}
