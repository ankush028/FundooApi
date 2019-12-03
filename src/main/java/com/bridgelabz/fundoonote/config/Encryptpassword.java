package com.bridgelabz.fundoonote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

@Configuration
public class Encryptpassword {
	//for encrypt and decrpt the password
	@Bean
	public PasswordEncoder encoder() {
	//it will return an object of BCryptPasswordEncoder
		return new BCryptPasswordEncoder();
	}
	
}
