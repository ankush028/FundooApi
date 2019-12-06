package com.bridgelabz.fundoonote.labeltest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;
import com.bridgelabz.fundoonote.label.dto.LabelDto;
import com.bridgelabz.fundoonote.label.model.Label;
import com.bridgelabz.fundoonote.label.repository.LabelRepository;
import com.bridgelabz.fundoonote.label.services.LabelServicesimpli;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.utility.Jwt;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelServiceTest {

	@InjectMocks
	LabelServicesimpli labelservice; 
	
	@Mock
	LabelRepository labelRepo;
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	ModelMapper mapper;
	
	@Mock
	Environment environment;
	
	@Mock
	Jwt jwt;
	
	String email ="akag0284@gmail.com";
	String token ="12fkdn1211df234";
	String id="adcnbvd1223423v";
	User user = new User();
	Label label = new Label();
	List<Label> list = new ArrayList<>();
	Label lab=null; 
	@Test
	public void getAllLabel()
	{
		Label lable = new Label();
		lable.setLabeltitle("abcde");
		List<Label> listOfLabel = new ArrayList<>();
		listOfLabel.add(lable);
		Mockito.when(labelRepo.findAll()).thenReturn(listOfLabel);
		List<Label> list = labelservice.getAlllabel();
		assertEquals("abcde",list.get(0).getLabeltitle());
			
	}
	@Test
	public void sortedByTitle() {
		Label label = new Label();
		label.setLabeltitle("abcde");
		List<Label> listofLabel = new ArrayList<>();
		listofLabel.add(label);
		
		Mockito.when(labelRepo.findAll().stream()
				.sorted(Comparator.comparing(Label::getLabeltitle)).collect(Collectors.toList()))
		.thenReturn(listofLabel);
		List<Label> list = labelservice.sortedByTitle();
		assertEquals("abcde",list.get(0).getLabeltitle());		
	}
	@Test
	public void addLabelTest() {
		LabelDto lbldto = new LabelDto();
		lbldto.setLabeltitle("Mylabel");
		when(jwt.getUserToken(token)).thenReturn(email);
		when(userRepo.findByEmail(email)).thenReturn(user);
		when(mapper.map(lbldto,Label.class)).thenReturn(label);
		System.out.println("Hello");
		when(labelRepo.save(label)).thenReturn(label);
		when(environment.getProperty("label")).thenReturn("Added Sucessfully");
		Response res = labelservice.addLabel(lbldto, token);
		assertEquals(200,res.getStatus());		
	}


	public void removeLabelTest() {

		when(jwt.getUserToken(token)).thenReturn(email);
		when(labelRepo.findByEmail(email)).thenReturn(list);
		doNothing().when(labelRepo).delete(label);
	//	when(list.stream().filter(i->i.getLabelid().equals(id)).findAny().get()).thenReturn(lab);
		Response response = labelservice.deleteLabel(token, id);
		assertEquals(200,response.getStatus());		
		
	}

}
