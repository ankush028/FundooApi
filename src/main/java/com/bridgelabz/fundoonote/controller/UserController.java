package com.bridgelabz.fundoonote.controller;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgelabz.fundoonote.dto.LoginDto;
import com.bridgelabz.fundoonote.dto.RegisterDto;
import com.bridgelabz.fundoonote.dto.ResetDto;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.services.Services;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */


//this is a controller of All Services
@RequestMapping("/userapi")
@RestController
public class UserController {
	//@see com.bridgelabz.fundoonote.services 
	@Autowired
	private Services service;
	/**@purpose add the User 
	 * @param regdto
	 * @return response with status
	 */
	@PostMapping("/addUser")
	public Response adduser(@Valid @RequestBody RegisterDto regdto) {
		
		return service.addUser(regdto);
	}
	/**@purpose find all	 the User 
	 * @return	all user with details
	 */
	@GetMapping("/getAllUser")
	public List<User> getAllUser(){
		return service.getAllUser();
	}
	
	/**@purpose search the user by id
	 * @param id
	 * @return user detail
	 */
	@GetMapping("/getUser")
	public Response serachById(@RequestParam String id) {
		return service.searchById(id);
	}
	
	/**
	 * @param id
	 * @return A message deleted or not
	 */
	@DeleteMapping("/delete")
	public Response deleteById(@RequestParam String id) {
		return service.deleteById(id);
	}
	/**
	 * @param id
	 * @param regdto
	 * @return a message updated or not
	 */
	@PutMapping("/update")
	public Response updateUser(@RequestParam String id,@RequestBody  RegisterDto regdto) {
		return service.update(regdto, id);
	}
	/**
	 * @param logindto
	 * @return message loggedin or not
	 */
	@PostMapping("/login")
	public Response loginUser(@RequestBody LoginDto logindto) {
		return service.login(logindto);
		
	}
	/**
	 * @param email
	 * @return user details
	 */
	

	@GetMapping("/findByEmail")
	public Response findByEmail(@RequestParam String email) {
		return service.findByEmail(email);
	}	
	@PostMapping("/reset/{token}")
	public Response reset(@RequestBody ResetDto resetdto,@PathVariable String token) {
		return service.reset(resetdto,token);
	}
	/**
	 * @param token
	 * @return message token is valid or not
	 */
	@PostMapping("/isvalid")
	public Response isValid(@RequestParam String token) {
		return service.isValidate(token);
	}
	/**@purpose to forgot password
	 * @param email
	 * @return a message 
	 */
	@PostMapping("/forgot")
	public Response forgot(@RequestParam String email) {
		return service.forgot(email);
	}
	@PostMapping("/upload")
	public Response upload(@RequestBody MultipartFile file ,@RequestParam String token) throws IOException {
		return service.uploadPhoto(file, token);
	}
	@DeleteMapping("/removePhoto")
	public Response remove(@RequestParam String token) {
		return service.removePhoto(token);
	}
	@PostMapping("/updatePhoto")
	public Response updatePhoto(@RequestBody MultipartFile file ,@RequestParam String token) throws IOException {
		
		return service.updatePhoto(file, token);
	}
}
