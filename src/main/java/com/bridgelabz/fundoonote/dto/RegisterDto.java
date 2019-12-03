package com.bridgelabz.fundoonote.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import lombok.Data;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

//Data Transfer object class of Registeration
@Data
public class RegisterDto {

	
	@NotBlank(message="name can not be empty")
	private String name;
	
	@Pattern(regexp=".+@.+\\.[a-z]+")//email pasttern validation
	private String email;
	
	@Size(min=3,max=10,message ="size will be in b/W 2 to 10")//password validation
	private String password;
	@Size(min=3,max=10)
	private String confirmPassword;

}
