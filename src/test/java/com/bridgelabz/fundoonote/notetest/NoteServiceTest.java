package com.bridgelabz.fundoonote.notetest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import static org.mockito.Matchers.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.note.dto.NoteDto;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.NoteRepository;
import com.bridgelabz.fundoonote.note.services.NoteServiceImpli;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.utility.Jwt;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {

	/**
	 * @see com.bridgelabz.fundoonote.note.services
	 */
	@InjectMocks
	NoteServiceImpli noteservice;
	/**
	 * @see com.bridgelabz.fundoonote.repository
	 */
	@Mock
	UserRepository userRepo;
	/**
	 * @purpose to convert notedto to model 
	 */
	@Mock
	ModelMapper mapper;
	/**
	 * @see com.bridgelabz.fundoonote.note.repository
	 */
	@Mock
	NoteRepository noteRepo;
	/**
	 * @see com.bridgelabz.fundoonote.utility
	 * @purpose to conversion of token 
	 */	
	@Mock
	Jwt noteJwt;
	
	/**
	 *@purpose i have taken for test the environment variables used in method
	 */
	@Mock
	Environment env;
	
	/**
	 * @purpose Raw data for testing all methods 
	 */
	String token ="sdjkfbsdifwefisfjksdfiusgdfw";
	String id="1234";
	Note note=new Note();
	String email = "akddin@gmail.com";
	User user = new User("", "","","", "", true, "");
	List<Note> notes = new ArrayList<>();
	String collabemail="akag005@gmail.com";
	String rcollabemail="akag005@gmail.com";
	String date = "12/05/2019";
	boolean flag;
	Optional<Note> optnote = Optional.of(note);
 	//Response res = null;
	String msg = "Added";
	/**
	 * @purpose Write Junit test for checking Note has deleted sucessfully
	 * @status HttpStatus.MOVED_PERMANENTLY Passed
	 */
	public void deleteNote() {
		notes.add(note);	
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		Mockito.when(noteservice.isNote(token,id)).thenReturn(null);
		
		doNothing().when(noteRepo).delete(note);
		Response response = noteservice.deleteNote(token, id);
		assertEquals(HttpStatus.MOVED_PERMANENTLY,response.getData());
		
	}
	/**
	 * @purpose Write Junit test for checking all data retrieved 
	 */
	@Test
	public void getAllNote() {
		Note n= new Note();
		n.setDescription("abcd");
		List<Note> notes = new ArrayList<>();
		notes.add(n);		
		Mockito.when(noteRepo.findAll()).thenReturn(notes);
		List<Note> noteees = noteservice.showAll();
		assertEquals("abcd",noteees.get(0).getDescription());
	}
	/**
	 * @purpose Write Junit test for checking all note retrieved
	 * 	Of user By email 
	 */
	@Test
	public void getAllNoteByEmail() {
		Note n = new Note();
		n.setDescription("1234");
		List<Note> notes = new ArrayList<>();
		notes.add(n);
		Mockito.when(noteRepo.findByEmail(email)).thenReturn(notes);
		List<Note> listNote = noteservice.getAllNote(email);
		assertEquals("1234",listNote.get(0).getDescription());
		
	}
	/**
	 * @purpose Write Junit test for checking all sorted note retrieved 
	 */
	@Test
	public void sortByTitle() {
		Note n = new Note();
		n.setTitle("abcd");
		List<Note> notes = new ArrayList<>();
		notes.add(n);
	Mockito.when( noteRepo.findAll().stream()
			.sorted(Comparator.comparing(Note::getTitle)).parallel().collect(Collectors.toList()))
		.thenReturn(notes);
	List<Note> listNote = noteservice.sortByTitle();
	assertEquals("abcd",listNote.get(0).getTitle());
	
	}
	/**
	 * @purpose Write Junit test for checking Note has created sucessfully
	 * @status 200 Passed
	 */
	@Test
	public void createNoteTest() {
		NoteDto notedto = new NoteDto();
		notedto.setDescription("hello");
		notedto.setDescription("hi");

		Mockito.when(noteJwt.getUserToken(token)).thenReturn(anyString());
		when(userRepo.findByEmail(email)).thenReturn(user);
		when(mapper.map(notedto,Note.class)).thenReturn(note);
		when(noteRepo.save(note)).thenReturn(note);
		System.out.println("hello");
		when(env.getProperty(msg)).thenReturn(msg);
		Response response = noteservice.addNote(notedto, token);
		System.out.println(response);
		assertEquals(200,response.getStatus());
	}
	/**
	 * @purpose Write Junit test for checking Note has sucessfully returned
	 */
	@Test
	public void isNoteTest() {
		Note notee= new Note();
		notee.setId("1234");
		notes.add(notee);
		when(noteJwt.getUserToken(token)).thenReturn(email);
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		Note note = noteservice.isNote(token, "1234");
		assertEquals("1234",note.getId());
	}
	/**
	 * @purpose Write Junit test for checking note is pinned sucessfully
	 * @status 200 Passed otherwise Fail
	 */
	@Test
	public void isPinnedTest() {
		Note notee= new Note();
		notee.setId("1234");
		notes.add(notee);
		when(noteJwt.getUserToken(token)).thenReturn(email);
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		note.setPinned(true);
		Mockito.when(noteRepo.save(note)).thenReturn(note);	
		Response response = noteservice.isPinned(id, token);
		assertEquals(200,response.getStatus());
	}
	/**
	 * @purpose Write Junit test for checking note is trashed sucessfully
	 * @status 200 Passed otherwise Fail
	 */
	@Test
	public void isTrashedTest() {
		Note notee= new Note();
		notee.setId("1234");
		notes.add(notee);
		when(noteJwt.getUserToken(token)).thenReturn(email);
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		note.setTrashed(true);
		Mockito.when(noteRepo.save(note)).thenReturn(note);	
		Response response = noteservice.isTrashed(id, token);
		assertEquals(200,response.getStatus());
	}
	/**
	 * @purpose Write Junit test for checking note Archieve sucessfully
	 * @status 200 Passed otherwise Fail
	 */
	@Test
	public void isArchievedTest() {
		Note notee= new Note();
		notee.setId("1234");
		notes.add(notee);
		when(noteJwt.getUserToken(token)).thenReturn(email);
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		note.setArchieved(true);
		Mockito.when(noteRepo.save(note)).thenReturn(note);	
		Response response = noteservice.isPinned(id, token);
		assertEquals(200,response.getStatus());
	}
	/**
	 * @purpose Write Junit test for checking collobrator is added sucessfully
	 * @status 200 Passed otherwise Fail
	 */
	@Test
	public void addCollobratorTest() {
		Note notee= new Note();
		notee.setId("1234");
		notes.add(notee);
		when(noteJwt.getUserToken(token)).thenReturn(email);
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		Mockito.when(noteRepo.save(note)).thenReturn(note);
		
		note.getListOfcollobarator().add(collabemail);
		Mockito.when(noteRepo.save(note)).thenReturn(note);
		Response response = noteservice.addCollobrate(token, id, collabemail);
		assertEquals(200,response.getStatus());
	}
	/**
	 * @purpose Write Junit test for checking collobrator is removed sucessfully
	 * @status 200 Passed otherwise Fail
	 */
	@Test
	public void removeCollobratorTest() {
		Note notee= new Note();
		notee.setId("1234");
		notes.add(notee);
		when(noteJwt.getUserToken(token)).thenReturn(email);
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		Mockito.when(noteRepo.save(note)).thenReturn(note);
		note.getListOfcollobarator().remove(collabemail);
		Response response = noteservice.removeCollobrate(token, id, rcollabemail);
		assertEquals(200,response.getStatus());
	}
	/**
	 * @purpose Write Junit test for checking reminder is added sucessfully
	 * @status 200 Passed otherwise Fail
	 */
	@Test
	public void addReminderTest() throws ParseException {	
		Note notee= new Note();
		notee.setId("1234");
		notes.add(notee);
		when(noteJwt.getUserToken(token)).thenReturn(email);
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		Mockito.when(noteRepo.save(note)).thenReturn(note);
		when(noteRepo.save(note)).thenReturn(note);
		Response response= noteservice.addReminder(token, id, date);
		assertEquals(200,response.getStatus());		
	}
	
}
