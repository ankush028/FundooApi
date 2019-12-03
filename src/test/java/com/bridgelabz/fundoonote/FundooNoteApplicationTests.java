package com.bridgelabz.fundoonote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import com.bridgelabz.fundoonote.customexception.Exceptions;
import com.bridgelabz.fundoonote.dto.RegisterDto;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.services.Serviceimpli;


@RunWith(MockitoJUnitRunner.class)
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
	
//	@Mock
//	RegisterDto reg;
	
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
	
	@Test 
	public void findByEmailTest() {
	when(services.findByEmail(email)).thenThrow(new Exceptions("UserNotFoundWxception"));
		Response res = services.findByEmail(email);
		System.out.println("RESPONSE :::::::::::::::::;"+res);
		assertEquals(500,res.getStatus());
	}
	
	
	
	@Test
	public void registerDtoTest() {
	
	//	Response res =null;
		RegisterDto reg = new RegisterDto();
		reg.setName("Ankush");
		reg.setEmail("akag0284@gmail.com");
		reg.setPassword("abcde");
		reg.setConfirmPassword("abcde");
	//	Response response = services.addUser(reg);
		System.out.println("hmm. i came");
//		
//		System.out.println("MayBe i Came");
//		System.out.println(u.getName());
//		assertEquals("Ankush",u.getName());
		
	//	User user = Model.getModel().map(reg,User.class);
	//	when(services.addUser(reg)).thenReturn(res);
		
	//	System.out.println(res);

		
	}
	
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