package com.bridgelabz.fundoonote.labeltest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.utility.Jwt;

/**
 * @author ANKUSH KUMAR AGRAWAL
 * @purpose Junit test of label services
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelServiceTest {

	/**
	 * @see com.bridgelabz.fundoonote.label.services
	 */
	@InjectMocks
	LabelServicesimpli labelservice; 
	/**
	 * @see com.bridgelabz.fundoonote.label.repository
	 */
	@Mock
	LabelRepository labelRepo;
	/**
	 * @see com.bridgelabz.fundoonote.repository
	 */
	@Mock
	UserRepository userRepo;
	/**
	 *@purpose Model Mapper to convert dto to model
	 */
	@Mock
	ModelMapper mapper;
	
	/**
	 *@purpose i have taken for test the environment variables used in method
	 */
	@Mock
	Environment environment;
	/**
	 * @see com,bridgelabz.fundoonote.utility
	 * @purpose to conversion of token 
	 */
	
	@Mock
	Jwt jwt;
	
	@Mock
	NoteRepository noteRepo;
	
	@Mock
	Label label = new Label();
	
	/**
	 * @purpose Raw data for testing all methods 
	 */
	String email ="akag0284@gmail.com";
	String token ="12fkdn1211df234";
	String id="adcnbvd1223423v";
	
	@Mock
	User user = new User("Ankush",email, email, email, email, false, email);
	
	List<Label> list = new ArrayList<>();
	List<Note> noteslist = new ArrayList<>();
	Note note1 = new Note();
	Label lab=null; 
	Optional<Label> label1 = Optional.of(label);
 
	
	/**
	 * @purpose Write Junit test for checking all data retrieved 
	 */
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
	/**
	 * @purpose Write Junit test for checking all data sorted retrieved 
	 */
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
	/**
	 * @purpose Write Junit test for checking label data added Sucessfully
	 * @status 200  test passed 
	 */
	@Test
	public void addLabelTest() {
		LabelDto lbldto = new LabelDto();
		lbldto.setLabeltitle("Mylabel");
		when(jwt.getUserToken(token)).thenReturn(email);
		when(userRepo.findByEmail(email)).thenReturn(user);
		when(mapper.map(lbldto,Label.class)).thenReturn(label);
		when(labelRepo.save(label)).thenReturn(label);
		when(environment.getProperty("label")).thenReturn("Added Sucessfully");
		Response res = labelservice.addLabel(lbldto, token);
		assertEquals(200,res.getStatus());		
	}

	/**
	 * @purpose Write Junit test for checking label removed Sucessfully
	 * @status 200  test passed 
	 */
	public void removeLabelTest() {

		when(jwt.getUserToken(token)).thenReturn(email);
		when(labelRepo.findByEmail(email)).thenReturn(list);
		doNothing().when(labelRepo).delete(label);
		Response response = labelservice.deleteLabel(token, id);
		assertEquals(200,response.getStatus());		
		
	}

	
	
}






































