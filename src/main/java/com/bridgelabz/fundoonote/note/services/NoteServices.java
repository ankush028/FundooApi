package com.bridgelabz.fundoonote.note.services;
import java.text.ParseException;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
import java.util.List;
import com.bridgelabz.fundoonote.note.dto.NoteDto;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.response.Response;

public interface NoteServices {
	
	/**
	 * @param note
	 * @param token
	 * @return a simple Message
	 * @throws Custom
	 */
	public  Response  addNote(NoteDto note,String token);
	
	/**
	 * @param token
	 * @param id
	 * @return A message
	 */
	public Response deleteNote(String token,String id);
	/**
	 * @param note
	 * @param token
	 * @return a message
	 */
	public Response updateNote(String id,NoteDto note,String token);
	/**@param notedto
	 * @param token
	 * @return message
 	 */
	/**
	 * @return
	 */
	public List<Note> getAllNote(String email);
	/**
	 * @return
	 */
	public List<Note> sortByTitle();
	/**
	 * @return all sorted by title
	 */
	public List<Note> sortByCreatedDate();
	/**
	 * @param id
	 * @param token
	 * @return a response Message
	 */
	
	/**
	 * @return
	 */
	public List<Note> showAll();
	
	
	/**
	 * @param id
	 * @param token
	 * @return Sucess message
	 */
	public Response isPinned(String id,String token);
	/**
	 * @param id
	 * @param token
	 * @return a response message
	 */
	public Response isArchieved(String id,String token);
	/**
	 * @param id
	 * @param token
	 * @return a response message
	 */
	public Response isTrashed(String id,String token);
	
	/**
	 * @param email
	 * @param noteid
	 * @param lblid
	 * @return a response message
	 */
	public Response addLabel(String email,String noteid,String lblid);

	/**
	 * @param token
	 * @param noteid
	 * @param collabemail
	 * @return
	 */
	public Response addCollobrate(String token,String noteid,String collabemail);
	
	/**
	 * @param token
	 * @param noteid
	 * @param rcollabemail
	 * @return
	 */
	public Response removeCollobrate(String token,String noteid,String rcollabemail);
	
	/**
	 * @param token
	 * @param noteId
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public Response addReminder(String token,String noteId,String date) throws ParseException;
	
	/**
	 * @param token
	 * @param noteId
	 * @return
	 */
	public Response removeReminder(String token,String noteId);
	
	/**
	 * @param token
	 * @param noteId
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public Response updateReminder(String token,String noteId,String date) throws ParseException;
	
	/**
	 * @param token
	 * @param id
	 * @return note if present otherwise throw exceptions
	 */
	public Note isNote(String token,String id);
	

	
	
}
