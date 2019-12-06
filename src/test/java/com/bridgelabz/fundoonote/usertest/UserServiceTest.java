package com.bridgelabz.fundoonote.usertest;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;	
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import com.bridgelabz.fundoonote.config.Encryptpassword;
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
	ModelMapper mapper;
	
	@Mock
	Encryptpassword encodePassword;
	
	
	User user = new User();
	User user1 = new User();
	List<User> list= new ArrayList<>();
	@Test
	public void deleteByIdTest()
	{

		when(services.deleteById(id)).thenReturn(null);
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
		
		user.setId(id);
		user.setEmail(email);
		user.setName("ankush");
		user.setPassword("1234");
		user.setConfirmPassword("1234");
		user.setIsvalidate(true);	
		when(userRepository.findByEmail(anyString())).thenReturn(user);
		Response response = services.findByEmail(anyString());
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
		user1.setId(id);
		user1.setEmail(email);
		user1.setName("ankit");
		user1.setPassword("12345");
		user1.setConfirmPassword("12345");
		user1.setIsvalidate(true);
		
		list.add(user);
		list.add(user1);
		when(userRepository.findAll()).thenReturn(list);	
		List<User> listOfUser =services.getAllUser();
		assertEquals("ankush",listOfUser.get(0).getName());		
	}
	


}
