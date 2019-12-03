package com.bridgelabz.fundoonote.utility;

import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

@Component
public class Jwt {
	
	private final String SECRET_KEY ="secret";

	
	/**@Purpose creation of token
	 * @param email
	 * @return a newly token for a new User
	 */
	public String createToken(String email)
	{
		
		return Jwts.builder().setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
		}
	
	
	/**@purpose to decode the token 
	 * @param token
	 * @return atoken
	 */
	public String getUserToken(String token) {
		Claims claim =Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		return claim.getSubject();
	}


}

