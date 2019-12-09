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

	/**
	 * @purpose  to store the encrypted password
	 * @return Encrypted Password
	 */
	@Bean
	public PasswordEncoder encoder() {

		return new BCryptPasswordEncoder();
	}
	
}
