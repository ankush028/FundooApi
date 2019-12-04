package com.bridgelabz.fundoonote.notetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.NoteRepository;
import com.bridgelabz.fundoonote.note.services.NoteServiceImpli;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteServiceTest {

	@InjectMocks
	NoteServiceImpli noteservice;
	
	@Mock
	NoteRepository noteRepo;
	
	String token ="sdjkfbsdifwefisfjksdfiusgdfw";
	String id="jfbjskdfsjk";
	Note note=new Note();
	String email = "akddin@gmail.com";
	//@Test
	public void deleteNote() {
		Mockito.when(noteservice.isNote(token,id)).thenReturn(note);
		doNothing().when(noteRepo).delete(note);
		ResponseEntity<Object> response = noteservice.deleteNote(token, id);
		assertEquals(HttpStatus.MOVED_PERMANENTLY,response.getStatusCode());
		
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
	
	
}
