package com.bridgelabz.fundoonote.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

//Data Transfer object class of Login
@Data
public class LoginDto {
	
	@NotBlank
	private String email;
	@NotBlank 
	private String password;

}
