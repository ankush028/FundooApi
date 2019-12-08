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

/**
 * @author admin1
 *
 */
/**
 * @author admin1
 *
 */
/**
 * @author admin1
 *
 */
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
	
	//taken raw data for junit test
	User user = new User();
	User user1 = new User();
	List<User> list= new ArrayList<>();
	String id="fhSGDFG1324";
	String email="akag02842gmail.com";
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
	/**
	 * @purpose Write Junit test for checking to retrieved all data of user
	 */
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
