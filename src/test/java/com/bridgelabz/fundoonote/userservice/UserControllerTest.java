package com.bridgelabz.fundoonote.userservice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.services.Serviceimpli;
import com.bridgelabz.fundoonote.services.Services;

public class UserControllerTest {
	
	@InjectMocks
	Serviceimpli imp;

	@Mock
	Services service;
	
	@Mock
	UserRepository userRepo;
	
	User u = new User("Ankush","2156423165","akag0284@gmail.com","12654","12654",true,"1sdfsedsw");
	List<User> list = new ArrayList<User>();
	
	
	@Test
	public void getUsertest() {
		when(userRepo.findAll()).thenReturn(list);
		assertEquals(userRepo.findAll(), list);
	}
	

}