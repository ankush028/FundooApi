package com.bridgelabz.fundoonote.repository;

/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoonote.model.User;


//main repository to perform the operation 
public interface UserRepository extends MongoRepository<User,String>{
	//method to find the user by email id
	public User findByEmail(String email);

}
