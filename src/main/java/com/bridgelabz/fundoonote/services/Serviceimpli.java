/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 * @Date 20 Nov 2019
 */
package com.bridgelabz.fundoonote.services;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoonote.config.Encryptpassword;
import com.bridgelabz.fundoonote.config.Model;
import com.bridgelabz.fundoonote.customexception.Exceptions;
import com.bridgelabz.fundoonote.dto.LoginDto;
import com.bridgelabz.fundoonote.dto.RegisterDto;
import com.bridgelabz.fundoonote.dto.ResetDto;
import com.bridgelabz.fundoonote.model.RabbitMq;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.utility.Jms;
import com.bridgelabz.fundoonote.utility.Jwt;
@PropertySource("classpath:messages.properties")
@Service
public class Serviceimpli implements Services{
	
	/**
	 * @see com.bridgelabz.fundoonote.repository
	 */
	@Autowired
	private UserRepository userRepo;
	/**
	 * @see com.bridgelabz.fundoonote.Utility
	 */
	@Autowired
	JavaMailSender javaMailsender;
	/**
	 * @see com.bridgelabz.fundoonote.config
	 */
	@Autowired
	Encryptpassword encodePassword;
	/**
	 * @see com.bridgelabz.fundoonote.Utility
	 */
	@Autowired
	 Jwt usertoken;
	/**
	 * @see com.bridgelabz.fundoonote.Utility
	 */
	@Autowired
		Jms jms;
	
	@Autowired
	Environment environment;
	
//	@Autowired
//	RabbitTemplate rabbitTemplate;
	
	
	/**
	 * Logger
	 */
//	@Autowired	
//	private static final java.util.logging.Logger logger =java.util.logging.Logger.getLogger(FundooNoteApplication.class.getName());
	/**
	 *To Add A New User with A Unique EmailId
	 */	
	@Override
	public Response addUser(RegisterDto regdto) {
      //To Map The ModelClass
		User user = Model.getModel().map(regdto, User.class);
		//password and confirm password must be equal 
		boolean checkPasswords = regdto.getPassword().equals(regdto.getConfirmPassword());
		//email id must be Unique
		User isEmailPresent = userRepo.findByEmail(regdto.getEmail());
		if(checkPasswords) {  //condition check
			if(isEmailPresent==null) { //email validation exist or not
				user.setPassword(encodePassword.encoder().encode((regdto.getPassword())));//set encrypted password to the data bases
				//save the user details in data base
//				logger.info(environment.getProperty("Add"));
				userRepo.save(user);
				//token generated
				String generatedToken=usertoken.createToken(user.getEmail());
				//token send to the users mail
			RabbitMq model = new RabbitMq(
						regdto.getEmail(),environment.getProperty("subject"), environment.getProperty("url")+generatedToken);
				
			//	rabbitTemplate.convertAndSend(model);
				jms.sendMail(model);
							
				//return response with status
				return new Response(200,environment.getProperty("Add"),HttpStatus.OK);
			
			}//return response
			return new Response(200,environment.getProperty("Email_Exist"),HttpStatus.OK);
		}//return response
		return new Response(200,environment.getProperty("password_MisMatch"),HttpStatus.OK);
	}	
	//get all users in the data base
	/**
	 *METHOD TO GET ALL USER
	 */
	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}
	//delete user by id
	/**
	 *METHOD FOR DELETE A USER BY ID
	 */
	@Override
	public Response deleteById(String id) {
		userRepo.deleteById(id);
		return new Response(200,environment.getProperty("delete"),HttpStatus.OK);
	}
	
	/**
	 *FINDTHE USER BY ID
	 *@param ID
	 *@return user details with message
	 */
	@Override
	public Response searchById(String id) {
	Optional<User> user=userRepo.findById(id);
	return new Response(200,environment.getProperty("Find"),user);       
	}
	//update the user details
	/**
	 *@param Registration Dto
	 *@param id
	 *@return response message
	 */
	@Override
	public Response update(RegisterDto regdto, String id) {
		
		User userUpdate = userRepo.findById(id).get();
		userUpdate.setName(regdto.getName());
		userRepo.save(userUpdate);
		return new Response(200,environment.getProperty("update"),HttpStatus.ACCEPTED);
	}
	// this method to login user by emailid and password
	/**
	 *@param login dto
	 *@return Resp[onse message
	 */
	public Response login(LoginDto logindto) {
		//find user by email
		User user = userRepo.findByEmail(logindto.getEmail());
		//condition check
		if(user!=null) {
			//authentication emailid and password to a encrypted password
			if(user.isIsvalidate()&& user.getEmail().equals(logindto.getEmail())&&
				//	@see com.bridgelabz.fundoonote.config --for decryption method
					encodePassword.encoder().matches(logindto.getPassword(),user.getPassword())) {
//				@see com.bridgelabz.fundoonote.serviceimpli.MessageReference
				return new Response(200,environment.getProperty("Login"),HttpStatus.OK);	
			}
		} 
		return new Response(200,environment.getProperty("Login_Fail"),HttpStatus.NON_AUTHORITATIVE_INFORMATION);//if entered detail will not match
	}

	
	/**
	 *method for find the user by email id
	 */
	@Override
	public Response findByEmail(String email) {
		User user =userRepo.findByEmail(email);
		if(user==null) {
			throw new Exceptions("UserNotFoundExceptions");
		}
		return new Response(200,environment.getProperty("Find"),user);
	}
//check validation of token 
	/**
	 *this method is written for validation check of the token
	 */
	public Response isValidate(String token) {
		String email = usertoken.getUserToken(token);
		boolean flag=userRepo.findAll().stream().anyMatch(i->i.getEmail().equals(email));
		if(Boolean.FALSE.equals(flag)) {
			throw new Exceptions("InvalidTokenExceptions");
		}
		User user = userRepo.findByEmail(email);
		user.setIsvalidate(true);
		userRepo.save(user);
		return new Response(200,environment.getProperty("valid"),HttpStatus.OK);
	}
	/**
	 *Method for resetting the password
	 */
	@Override
	public Response reset(ResetDto resetdto,String tokenn) {
		//resetting password
		if(resetdto.getPassword().equals(resetdto.getConfirmPassword())) {//condition check password with confirm password
			User user = Model.getModel().map(resetdto,User.class);//map the dto class with model class
			String email = usertoken.getUserToken(tokenn);//get email is from the users token 
			//get the user with email
			User userForset = userRepo.findAll().stream().filter(i->i.getEmail().equals(email)).findAny().orElse(null);
			if(user==null) {
				throw new Exceptions("UserNotFoundExceptions");
			}
			userForset.setPassword(encodePassword.encoder().encode(user.getPassword()));
			userForset.setConfirmPassword(user.getConfirmPassword());
			userRepo.save(userForset);
			return new Response(200,environment.getProperty("reset"),HttpStatus.OK);
		}
		return new Response(200,environment.getProperty("password_MisMatch"),HttpStatus.OK);
	}
	//Forgot the password 
	/**
	 *Method for forgetting the password
	 */
	@Override
	public Response forgot(String email) {
		if(userRepo.findAll().stream().anyMatch(i->i.getEmail().equals(email))) //condition check user exist or not 
			{
			String generatedToken=usertoken.createToken(email);//genmerate token 
			//send the link to the user mail for reset the password
			RabbitMq model = new RabbitMq(email, environment.getProperty("reset")+generatedToken,environment.getProperty("subject"));		
			jms.sendMail(model);
			return new Response(200,environment.getProperty("validation_link"),HttpStatus.OK);
		}
		return new Response(200,environment.getProperty("User_Not_Found"),HttpStatus.OK);
	}
	/**
	 *METHOD FOR UPLOAD A PHOTO 
	 */
	/**
	 *@param FILE
	 *@param TOKEN
	 *@return A RESPONSE MESSAGE
	 */
	@Override
	public Response uploadPhoto(MultipartFile file,String token) throws IOException {
		//get email from token
		String email = usertoken.getUserToken(token);
		//get user from email
		User user = userRepo.findByEmail(email);
		// file should have to contain data and user should not be null otherwise throw the exceptions
		if(file.isEmpty() && user==null) {
			throw new Exceptions("UserNotFoundException");
		}
		//first convert the file into byte array
			byte[] bytes = file.getBytes();		
			//we use the extension
			String extension =file.getContentType().replace("image/","");
			//we have given the pass to save the file
			String fileLocation="/home/admin1/Desktop/SpringWorkSpace/FundooNote/Images"+email+"."+extension;
			Path path = Paths.get(fileLocation);
			Files.write(path, bytes);
			//i have setted the file in the location
			user.setProfile(fileLocation);			
			userRepo.save(user);
			//return the response message
			return new Response(200,environment.getProperty("upload"),HttpStatus.OK);

	}
	/**
	 *
	 */
	@Override
	public Response removePhoto(String token) {
		String email = usertoken.getUserToken(token);
		User user = userRepo.findByEmail(email);
		if(user==null) {
			throw new Exceptions("UserNotFoundException");
		}
		user.setProfile(null);
		userRepo.save(user);
		return new Response(200,environment.getProperty("imageDelete"),HttpStatus.OK);
	}
	/**
	 *method for update the photo
	 */
	@Override
	public Response updatePhoto(MultipartFile file, String token) throws IOException {
		
		String email = usertoken.getUserToken(token);
		
		User user = userRepo.findByEmail(email);
		
		if(user==null) {
			throw new Exceptions("User Not Found Sucessfully");
		}
		byte[] bytes = file.getBytes();		
		String extension =file.getContentType().replace("image/","");
		String fileLocation=""+email+"."+extension;
		Path path = Paths.get(fileLocation);
		Files.write(path, bytes);
		user.setProfile(fileLocation);			
		userRepo.save(user);
		return new Response(200,environment.getProperty("imageUpdate"),HttpStatus.OK);
	}
	
	
}
