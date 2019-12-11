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
import java.util.logging.Logger;
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

	
	static Logger logger =	Logger.getLogger(Serviceimpli.class.getName());
	private String userExcepKey = "userException";
	/**
	 * @param Register dto
	 * @return Response body
	 *To Add A New User with A Unique EmailId
	 */	
	@Override
	public Response addUser(RegisterDto regdto) {
		
	
		boolean checkPasswords = regdto.getPassword().equals(regdto.getConfirmPassword());
		if(!checkPasswords) {			
			return new Response(400,environment.getProperty("password_MisMatch"),HttpStatus.UNAUTHORIZED);		
		}
		User isEmailPresent = userRepo.findByEmail(regdto.getEmail()); 
		if(isEmailPresent!=null) {
			logger.warning("User exist already");
			return new Response(200,environment.getProperty("Email_Exist"),HttpStatus.OK);
		}
			User user =Model.getModel().map(regdto, User.class);
			user.setPassword( encodePassword.encoder().encode((regdto.getPassword())));//set encrypted password to the data bases
			userRepo.save(user);
			logger.info( environment.getProperty("Add"));
			String generatedToken=usertoken.createToken(user.getEmail());			
			RabbitMq rmodel = new RabbitMq(
			regdto.getEmail(),environment.getProperty("url")+generatedToken,environment.getProperty("subject"));				
			jms.sendMail(rmodel);	
		
			return new Response(200,environment.getProperty("Add"),HttpStatus.OK);
	}	

	/**
	 *METHOD TO GET ALL USER
	 */
	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

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
		
		Optional<User> userUpdate = userRepo.findById(id);
		if(!userUpdate.isPresent()) {
			throw new Exceptions(environment.getProperty(userExcepKey));
		}
		userUpdate.get().setName(regdto.getName());
		userRepo.save(userUpdate.get());
		return new Response(200,environment.getProperty("update"),HttpStatus.ACCEPTED);
	}
	// this method to login user by emailid and password
	/**
	 *@param login dto
	 *@return Response body
	 */
	public Response login(LoginDto logindto) {
		
		User user = userRepo.findByEmail(logindto.getEmail());	
		if(user==null) {

			throw new IllegalArgumentException(environment.getProperty(userExcepKey));
		}
			if(user.isIsvalidate()&& user.getEmail().equals(logindto.getEmail())&&	
					encodePassword.encoder().matches(logindto.getPassword(),user.getPassword())) {				
				return new Response(200,environment.getProperty("Login"),HttpStatus.OK);	
			}
			return new Response(200,environment.getProperty("Login_Fail"),HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		} 

	/**
	 * @param email
	 * @return Response body
	 *method for find the user by email id
	 */
	@Override
	public Response findByEmail(String email) {
		User user =userRepo.findByEmail(email);
		if(user==null) {
			throw new Exceptions(environment.getProperty(userExcepKey));
		}
		return new Response(200,environment.getProperty("Find"),user);
	}
//check validation of token 
	/**
	 * @param token
	 * @return Response body
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
	 * @param resetsto 
	 * @param token
	 * @return Response body
	 *Method for resetting the password
	 */
	@Override
	public Response reset(ResetDto resetdto,String tokenn) {
		
		if(resetdto.getPassword().equals(resetdto.getConfirmPassword())) {
			User user = Model.getModel().map(resetdto,User.class);
			String email = usertoken.getUserToken(tokenn);
			
			Optional<User> userForset = userRepo.findAll().stream().filter(i->i.getEmail().equals(email)).findAny();
			if(!userForset.isPresent()) {
				throw new Exceptions(environment.getProperty(userExcepKey));
			}
			userForset.get().setPassword(encodePassword.encoder().encode(user.getPassword()));
			userForset.get().setConfirmPassword(user.getConfirmPassword());
			userRepo.save(userForset.get());
			return new Response(200,environment.getProperty("reset"),HttpStatus.OK);
		}
		return new Response(200,environment.getProperty("password_MisMatch"),HttpStatus.OK);
	}
	//Forgot the password 
	/**
	 * @param email
	 * @return link will goes in youe email
	 * @return status
	 *Method for forgetting the password
	 */
	@Override
	public Response forgot(String email) {
		if(userRepo.findAll().stream().noneMatch(i->i.getEmail().equals(email)))
			{
			return new Response(200,environment.getProperty("User_Not_Found"),HttpStatus.OK);
		}
		String generatedToken=usertoken.createToken(email);
		RabbitMq model = new RabbitMq(email, environment.getProperty("reset")+generatedToken,environment.getProperty("subject"));		
		jms.sendMail(model);
		return new Response(200,environment.getProperty("validation_link"),HttpStatus.OK);
		
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

		String email = usertoken.getUserToken(token);

		User user = userRepo.findByEmail(email);

		if(file.isEmpty() && user==null) {
			throw new Exceptions(environment.getProperty(userExcepKey));
		}	
			byte[] bytes = file.getBytes();				
			String extension =file.getContentType().replace("image/","");		
			String fileLocation="/home/admin1/Desktop/SpringWorkSpace/FundooNote/Images"+email+"."+extension;
			Path path = Paths.get(fileLocation);
			Files.write(path, bytes);	
			user.setProfile(fileLocation);			
			userRepo.save(user);	
			
			return new Response(200,environment.getProperty("upload"),HttpStatus.OK);

	}
	/**
	 *@param TOKEN
	 *@return Response status And Response message
	 */
	@Override
	public Response removePhoto(String token) {
		String email = usertoken.getUserToken(token);
		User user = userRepo.findByEmail(email);
		if(user==null) {
			throw new Exceptions(environment.getProperty(userExcepKey));
		}
		user.setProfile(null);
		userRepo.save(user);
		return new Response(200,environment.getProperty("imageDelete"),HttpStatus.OK);
	}
	/**
	 * @param  file
	 * @param token
	 * @return Response body
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
