package com.bridgelabz.fundoonote.usertest;


import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundoonote.config.Encryptpassword;
import com.bridgelabz.fundoonote.config.Model;

import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.services.Serviceimpli;
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
	 
	@InjectMocks
	Serviceimpli services;
	
	@Mock
	UserRepository userRepository;
	
		String id="fhSGDFG1324";
	 String email="akag02842gmail.com";

	@Mock
	Environment environment;
	
	@Mock
	Model model;
	
	@Mock
	Encryptpassword encodePassword;
	
	@Test
	public void deleteByIdTest()
	{

		Mockito.when(services.deleteById(id)).thenReturn(null);
		doNothing().when(userRepository).deleteById("id");
		Response response=services.deleteById(id);
		assertEquals(200,response.getStatus());
	}
	@Test
	public void searchById() {
		Response res = services.searchById(id);
		assertEquals(200,res.getStatus());

	}
	@Test
	public void findByEmail() {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setName("ankush");
		user.setPassword("1234");
		user.setConfirmPassword("1234");
		user.setIsvalidate(true);	
		Mockito.when(userRepository.findByEmail(email)).thenReturn(user);
		Response response = services.findByEmail(email);
		assertEquals(200,response.getStatus());
	}
	
	@Test
	public void getAllUser() {
		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setName("ankush");
		user.setPassword("1234");
		user.setConfirmPassword("1234");
		user.setIsvalidate(true);
		User user1 = new User();
		user1.setId(id);
		user1.setEmail(email);
		user1.setName("ankit");
		user1.setPassword("12345");
		user1.setConfirmPassword("12345");
		user1.setIsvalidate(true);
		List<User> list= new ArrayList<>();
		list.add(user);
		list.add(user1);
		Mockito.when(userRepository.findAll()).thenReturn(list);	
		List<User> listOfUser =services.getAllUser();
		assertEquals("ankush",listOfUser.get(0).getName());		
	}
//	@Test
//	public void addUserTest() {
//
//		String email1="abcd@gmail.com";
//
//		RegisterDto dto = new RegisterDto();
//		dto.setName("Ankush");
//		dto.setEmail(email);
//		dto.setPassword("1234");
//		dto.setConfirmPassword("1234");
//		User user = new User();
//		user.setId(id);
//		user.setEmail(email);
//		user.setName("ankush");
//		user.setPassword("1234");
//		user.setConfirmPassword("1234");
//		user.setIsvalidate(false);		
//	//	Mockito.when(model.getModel().map(dto,User.class)).thenReturn(user);
//	//	System.out.println("Hello");
//	//	Mockito.when(encodePassword.encoder().encode(password)).thenReturn(any);
//		System.out.println("hello i am");
//	//	assertTrue(dto.getPassword().equals(dto.getConfirmPassword()));
//		System.out.println("hello i am 2");
//		Mockito.when(userRepository.findByEmail(email1)).thenReturn(null);
//		assertNull(null);
//		Mockito.when(userRepository.save(user)).thenReturn(null);
//		Response response = services.addUser(dto);
//		assertEquals(200,response.getStatus());		
//	}



}
