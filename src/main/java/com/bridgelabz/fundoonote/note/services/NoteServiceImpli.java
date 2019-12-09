package com.bridgelabz.fundoonote.note.services;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonote.config.Model;
import com.bridgelabz.fundoonote.customexception.Exceptions;
import com.bridgelabz.fundoonote.label.model.Label;
import com.bridgelabz.fundoonote.label.repository.LabelRepository;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.note.dto.NoteDto;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.utility.Jwt;
import com.bridgelabz.fundoonote.utility.Utility;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

//@PropertySource("classpath:messages.properties")
@Service
public class NoteServiceImpli implements NoteServices{
	
	/**
	 * @see com.bridgelabz.fundoonote.note.utility
	 * it has used to convert token to mail and mail to token
	 */
	@Autowired
	Jwt noteJwt;
	
	/**
	 * @see  com.bridgelabz.fundoonote.Repository
	 */
	@Autowired
	UserRepository userRepo;
	/**
	 * @see  com.bridgelabz.fundoonote.note.Repository
	 */
	@Autowired
	NoteRepository noteRepo; 

	@Autowired
	Environment environment;
	/**@purpose to create a new note
	 *@param notedto
	 *@param toekn
	 *@return a simple Message
	 */
	
	@Autowired
	LabelRepository labelRepo;
	
	@Override
	public Response addNote(NoteDto notedto, String token) {
		String email= noteJwt.getUserToken(token);
		User user = userRepo.findByEmail(email);
		if(user==null) {
			throw new Exceptions("UserNotFoundException");
		}
			Note note = Model.getModel().map(notedto,Note.class);
			note.setCreatedDate(LocalDate.now());
			note.setEmail(email);
			noteRepo.save(note);	
			
			return new Response(200,environment.getProperty("ADD_NOTE"),HttpStatus.OK);
		}

	/**@purpose delete the note from the user
	 *@param notedto
	 *@param token
	 *@return a simple Message
	 */
	@Override
	public Response deleteNote(String token, String id) {
		
		Note note = isNote(token,id);
		noteRepo.delete(note);
		 return new Response(200,environment.getProperty("Delete_NOTE"),HttpStatus.MOVED_PERMANENTLY);
		
	}
	/**
	 * @purpose to update/edit the note 
	 *@param notedto
	 *@param token
	 *@return a simple Message
	 */
	@Override
	public Response updateNote(String id,NoteDto notedto, String token) {
	
		Note note = isNote(token,id);
		note.setUpdatedDate(LocalDate.now());
		note.setDescription(notedto.getDescription());
		note.setTitle(notedto.getTitle());
		noteRepo.save(note);
		return new Response(200,environment.getProperty("update"),HttpStatus.OK);
	}
	/**
	 *To get all the the notes
	 *
	 *@return total available notes
	 */
	
	@Override
	public List<Note> getAllNote(String email) {

		return noteRepo.findByEmail(email);
	}
	
	@Override
	public List<Note> showAll(){
		
		
		return noteRepo.findAll();
	}
	
	/**
	 *METHOD FOR SORT THE NOTE BY TITLE
	 */
	@Override
	public List<Note> sortByTitle(){

		return noteRepo.findAll().stream()
				.sorted(Comparator.comparing(Note::getTitle)).parallel().collect(Collectors.toList());
	}
	/**
	 *METHOD FOR SORT  THE NOTE BY CREATED DATE
	 */
	@Override
	public List<Note> sortByCreatedDate(){
		

		return noteRepo.findAll().stream()
				.sorted(Comparator.comparing(Note::getCreatedDate)).parallel().collect(Collectors.toList());
	}
	/**
	 *
	 */
	@Override
	public Response isPinned(String id,String token) {
		Note note = isNote(token,id);
		note.setPinned(!note.isPinned());
		noteRepo.save(note);
		return new Response(200,environment.getProperty("pin"),HttpStatus.OK);
	}
		/**
		 *
		 */
		@Override
	public Response isTrashed(String id,String token) {
		Note note = isNote(token,id);
		note.setTrashed(!note.isTrashed());
		noteRepo.save(note);
		return new Response(200,environment.getProperty("trash"),HttpStatus.OK);
	}
	/**
	 *
	 */
	@Override
	public Response isArchieved(String id,String token) {
		Note note = isNote(token,id);
		note.setTrashed(!note.isArchieved());
		noteRepo.save(note);
		return new Response(200,environment.getProperty("archive"),HttpStatus.OK);
	}
		
	/**
	 * @param token
	 * @param noteId
	 * @param collobrate email
	 * @return Response message
	 * 
	 */

	public Response addCollobrate(String token,String noteId,String collabemail){
		
		Note note = isNote(token,noteId);
		boolean status = note.getListOfcollobarator().contains(collabemail);

		if(status) {
			return new Response(200,environment.getProperty("already"),HttpStatus.OK);
		}
		note.getListOfcollobarator().add(collabemail);
		noteRepo.save(note);
		return new Response(200,environment.getProperty("Sucess"),HttpStatus.OK);
	}
	
	/**
	 *REMOVE COLLOBRATOR
	 */
	public Response removeCollobrate(String token ,String noteId,String rcollabemail){
		Note note = isNote(token,noteId);
		note.getListOfcollobarator().remove(rcollabemail);
		return new Response(200,environment.getProperty("CHECK"),HttpStatus.OK);
		
	}
	
	/**
	 *METHOD FOR ADD LABEL 
	 */
	public Response addLabel(String email ,String noteid,String lblid) {
		List<Note> listOfNote = noteRepo.findByEmail(email);
		List<Label> listOfLabel = labelRepo.findByEmail(email);	
		Optional<Note> note = listOfNote.stream().filter(i->i.getId().equals(noteid)).findAny();
		Optional<Label> label = listOfLabel.stream().filter(i->i.getLabelid().equals(lblid)).findAny();
		if(label.isPresent() && note.isPresent()) {
			note.get().getListOfLabel().add(label.get());
			noteRepo.save(note.get());
			label.get().getListOfNote().add(note.get());
			labelRepo.save(label.get());
			return new Response(200,environment.getProperty("Sucess"),HttpStatus.OK);
		
		}
		throw new Exceptions("NoteOrLabelNoteFoundExceptions");
	}

	@Override
	public Response addReminder(String token, String noteId, String date) throws ParseException{
		Note note = isNote(token,noteId);

		note.setReminder(new Utility(date).dateFormat());
		noteRepo.save(note);
		
		return new Response(200,environment.getProperty("ReminderAdded"),HttpStatus.OK);
	}



	@Override
	public Response removeReminder(String token, String noteId) {

		String email = noteJwt.getUserToken(token);
		List<Note> notes= noteRepo.findByEmail(email);
		Optional<Note> note = notes.stream().filter(i->i.getId().equals(noteId)).findAny();
		if(!note.isPresent()) {
			throw new IllegalArgumentException("Note");
		}
		note.get().setReminder(null);
		noteRepo.save(note.get());
		return new Response(200,environment.getProperty("ReminderRemoved"),HttpStatus.OK);
	}



	@Override
	public Response updateReminder(String token, String noteId, String date) throws ParseException {
		String email = noteJwt.getUserToken(token);
		List<Note> notes= noteRepo.findByEmail(email);
		Optional<Note> note = notes.stream().filter(i->i.getId().equals(noteId)).findAny();
		if(!note.isPresent()) {
			throw new Exceptions("NoteNotFoundException");
		}
			note.get().setReminder(new Utility(date).dateFormat());
		noteRepo.save(note.get());
		
		return new Response(200,environment.getProperty("ReminderUpdate"),HttpStatus.OK);
	}
	/**
	 *
	 */
	@Override
	public Note isNote(String token,String id) {
		String email = noteJwt.getUserToken(token);
		List<Note> notes = noteRepo.findByEmail(email);
		Optional<Note> note = notes.stream().filter(i->i.getId().equals(id)).findAny();
//		if(!note.isPresent()) {
//			throw new Exceptions("NoteNotFoundException");
//		}
		return note.get();
		
	}

}
	


