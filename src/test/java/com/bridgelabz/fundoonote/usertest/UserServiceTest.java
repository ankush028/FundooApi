package com.bridgelabz.fundoonote.usertest;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;	
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import com.bridgelabz.fundoonote.config.Encryptpassword;
import com.bridgelabz.fundoonote.dto.RegisterDto;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.services.Serviceimpli;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
	 
	/**
	 * @see com.bridgelabz.fundoonote.services
	 */
	@InjectMocks
	Serviceimpli services;

	/**
	 * @see com.bridgelabz.fundoonote.repository
	 */
	@Mock
	UserRepository userRepository;
	

	/**
	 *@purpose i have taken for test the environment variables used in method
	 */
	@Mock
	Environment environment;
	/**
	 * @purpose to convert notedto to model 
	 */
	@Mock
	ModelMapper mapper;
	
	/**
	 * @purpose to encode the password and decode the password
	 */
	@Mock
	Encryptpassword encodePassword;
	
	/**
	 * @purpose For Write Junit Test TAKE RAW DATA*
	 * 
	 */
	String email="akag02842gmail.com";
	String id="fhSGDFG1324";
	User user = new User("Ankush",id,email,"1234", "1234", true, "abcdef.jpg");
	User user1 = new User("Ankit", id, email, "1234", "1234", false, "abcfs312def.jpg");
	List<User> list= new ArrayList<>();
	Optional<User> optuser = Optional.of(user);
	
	/**
	 * @purpose Written Junit test for checking Note has deleted  by id sucessfully
	 */
	@Test
	public void deleteByIdTest()
	{

		when(services.deleteById(id)).thenReturn(null);
		doNothing().when(userRepository).deleteById("id");
		Response response=services.deleteById(id);
		assertEquals(200,response.getStatus());
	}
	/**
	 * @purpose Written Junit test for checking Note has searched sucessfully
	 */
	@Test
	public void searchById() {
		Response res = services.searchById(id);
		assertEquals(200,res.getStatus());

	}
	/**
	 * @purpose Written Junit test for checking Note has found by email sucessfully
	 */
	@Test
	public void findByEmail() {

		when(userRepository.findByEmail(anyString())).thenReturn(user);
		Response response = services.findByEmail(anyString());
		assertEquals(200,response.getStatus());
	}
	/**
	 * @purpose Write Junit test for checking to retrieved all data of user
	 */
	@Test
	public void getAllUser() {
		list.add(user);
		list.add(user1);
		when(userRepository.findAll()).thenReturn(list);	
		List<User> listOfUser =services.getAllUser();
		assertEquals("Ankush",listOfUser.get(0).getName());		
	}
	
	/**
	 * @purpose written Junit Test to Test details update of user
	 * @status 200 Passed Otherwise Faild
	 */
	@Test
	public void updateUserTest() {
		RegisterDto dto = new RegisterDto();
		dto.setName("Raja");
		dto.setEmail("akag0284@gmail.com");
		dto.setPassword("1234");
		dto.setConfirmPassword("1234");
		when(userRepository.findById(id)).thenReturn(optuser);
		optuser.get().setName("Rahul");
		when(userRepository.save(user)).thenReturn(user);
		Response response = services.update(dto, id);
		assertEquals(200,response.getStatus());
		
	}

}
