package com.bridgelabz.fundoonote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.services.Serviceimpli;


@RunWith(SpringRunner.class)
@SpringBootTest
class FundooNoteApplicationTests {


	@InjectMocks
	Serviceimpli services;
	
	@Mock
	UserRepository userRepository;
	
	private static final String id="fhSGDFG1324";
	private static final String email ="akag006@gmail.com";
	
	@Mock
	Environment environment;
	
	@Test
	public void deleteByIdTest()
	{
	//	Response res = null;
		System.out.println("checkk./............");
		Mockito.when(services.deleteById(id)).thenReturn(null);
		//doNothing().when(userRepository).deleteById("id");
		Response response=services.deleteById(id);
		System.out.println("Response::::::::"+response);
		assertEquals(200,response.getStatus());
	}
	
	@Test
	public void searchById() {
		Response res = services.searchById(id);
		assertEquals(200,res.getStatus());
		
	}
	
<<<<<<< HEAD
=======
//	@Test 
//	public void findByEmailTest() {
//	when(services.findByEmail(email)).thenThrow(new Exceptions("UserNotFoundWxception"));
//		Response res = services.findByEmail(email);
//		System.out.println("RESPONSE :::::::::::::::::;"+res);
//		assertEquals(500,res.getStatus());
//	}
	
//	
//	

>>>>>>> update
	
}
/*
 * @Autowired MockMvc mockMvc;
 * 
 * @MockBean UserRepository userRepo;
 * 
 * @Test void contextLoads() throws Exception { mockMvc.perform(
 * MockMvcRequestBuilders.get("/userapi/getAllUser").accept(MediaType.
 * APPLICATION_JSON)).andReturn();
 * 
 * Mockito.verify(userRepo).findAll();
 * 
 * 
 * }
 */
