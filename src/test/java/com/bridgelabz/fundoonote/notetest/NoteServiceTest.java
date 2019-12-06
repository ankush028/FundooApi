package com.bridgelabz.fundoonote.notetest;
import static org.mockito.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
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

	@InjectMocks
	NoteServiceImpli noteservice;
	
	@Mock
	UserRepository userRepo;
	
	@Mock
	ModelMapper mapper;
	
	@Mock
	NoteRepository noteRepo;
	
	@Mock
	Jwt noteJwt;
	
	
	String token ="sdjkfbsdifwefisfjksdfiusgdfw";
	String id="12345abcde";
	Note note=new Note();
	String email = "akddin@gmail.com";
	User user = new User();
	List<Note> notes = new ArrayList<>();
	String collabemail="akag005@gmail.com";
	String rcollabemail="akag005@gmail.com";
	String date = "12/05/2019";
	boolean flag;

	public void deleteNote() {
		notes.add(note);	
		when(noteRepo.findByEmail(email)).thenReturn(notes);
		Mockito.when(noteservice.isNote(token,id)).thenReturn(null);
		
		doNothing().when(noteRepo).delete(note);
		Response response = noteservice.deleteNote(token, id);
		assertEquals(HttpStatus.MOVED_PERMANENTLY,response.getData());
		
	}
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
	@Test
	public void createNoteTest() {
		NoteDto notedto = new NoteDto();
		notedto.setDescription("hello");
		notedto.setDescription("hi");
	//	Exceptions e = new Exceptions("UserNotFoundException");
		Mockito.when(noteJwt.getUserToken(token)).thenReturn(anyString());
		when(userRepo.findByEmail(email)).thenReturn(user);

	//	doThrow(e).when(user.equals(null));
		when(mapper.map(notedto,Note.class)).thenReturn(note);
		when(noteRepo.save(note)).thenReturn(note);
		System.out.println("hello");
		Response response = noteservice.addNote(notedto, token);
		System.out.println(response);
		assertEquals(200,response.getStatus());
	}
	

	public void isPinnedTest() {
		when(noteservice.isNote(token,id)).thenReturn(note);
		Response response = noteservice.isPinned(id, token);
		assertEquals(200,response.getStatus());
	}

	public void isTrashedTest() {
		//when(noteservice.isNote(token,id)).thenReturn(note);
		Response response = noteservice.isTrashed(id, token);
		assertEquals(200,response.getStatus());
	}

	public void isArchievedTest() {
		//when(noteservice.isNote(token,id)).thenReturn(note);
		Response response = noteservice.isPinned(id, token);
		assertEquals(200,response.getStatus());
	}
	

	public void addCollobratorTest() {
		when(userRepo.findByEmail(email)).thenReturn(user);
		when(noteservice.isNote(token, id)).thenReturn(note);
		when(note.getListOfcollobarator().contains(collabemail)).thenReturn(flag);
		doNothing().when(note.getListOfcollobarator().add(collabemail));
		doNothing().when(noteRepo.save(note));
		Response response = noteservice.addCollobrate(token, id, collabemail);
		assertEquals(200,response.getStatus());
	}


	public void removeCollobratorTest() {
		when(noteservice.isNote(token, id)).thenReturn(note);
		doNothing().when(note.getListOfcollobarator().remove(note));
		Response response = noteservice.removeCollobrate(token, id, rcollabemail);
		assertEquals(200,response.getStatus());
	}
	
	public void addReminderTest() throws ParseException {
		when(noteservice.isNote(token, id)).thenReturn(note);
		doNothing().when(noteRepo.save(note));
		Response response= noteservice.addReminder(token, id, date);
		assertEquals(200,response.getStatus());		
	}
	
}
