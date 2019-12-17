package com.bridgelabz.fundoonote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */


@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class FundooNoteApplication {
	 
	public static void main(String[] args) {
		SpringApplication.run(FundooNoteApplication.class, args);
		
	}

}
