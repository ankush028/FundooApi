package com.bridgelabz.fundoonote.services;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoonote.dto.LoginDto;
import com.bridgelabz.fundoonote.dto.RegisterDto;
import com.bridgelabz.fundoonote.dto.ResetDto;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.response.Response;

/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

public interface Services {
		/**
		 * @param regdto
		 * @return
		 */
		public Response addUser(RegisterDto regdto);
		
		/**
		 * @return
		 */
		public List<User> getAllUser();
		
		/**
		 * @param id
		 * @return List of Users
		 */
		public Response deleteById(String id);
		/**
		 * @param id
		 * @return
		 */
		public Response searchById(String id);
		/**
		 * @param regdto
		 * @param id
		 * @return
		 */
		public Response update(RegisterDto regdto ,String id);
		/**
		 * @param logindto
		 * @return
		 */
		public Response login(LoginDto logindto);
		/**
		 * @param email
		 * @return
		 */
		public Response findByEmail(String email);
		/**
		 * @param resetdto
		 * @param token
		 * @return
		 */
		public Response reset(ResetDto resetdto,String token);
		/**
		 * @param email
		 * @return
		 */
		public Response forgot(String email);
		/**
		 * @param token
		 * @return
		 */
		public Response isValidate(String token);
		
		/**
		 * @param file
		 * @param token
		 * @return
		 * @throws IOException
		 */
		public Response uploadPhoto(MultipartFile file,String token) throws IOException;
		
		/**
		 * @param token
		 * @return
		 */
		public Response removePhoto(String token);
		
		/**
		 * @param file
		 * @param token
		 * @return
		 * @throws IOException
		 */
		public Response updatePhoto(MultipartFile file,String token) throws IOException;
		
		
		
}
