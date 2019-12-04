package com.bridgelabz.fundoonote.note.controller;
import java.text.ParseException;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonote.note.dto.NoteDto;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.services.NoteServices;
import com.bridgelabz.fundoonote.response.Response;
@RestController
@RequestMapping("/noteapi")
public class NoteController {
	
	@Autowired
	private NoteServices service;
	
	@PostMapping("/addNote")
	public ResponseEntity<Object> addNote(@RequestBody NoteDto notedto,@RequestParam String token) {
		
		return service.addNote(notedto, token);
	}
	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteNote(@RequestParam String token,@RequestParam String id) {
		return service.deleteNote(token, id);
	}
	@GetMapping("/showAllNote")
	public List<Note> showAll(){
		return service.showAll();
	}
	@GetMapping("/getAllNoteByEmail")
	public List<Note> getAllNoteOfUser(@RequestParam String email){
		return service.getAllNote(email);
	}
	@PutMapping("/updateNote")
	public ResponseEntity<Object> updateNote(@RequestParam String id,@RequestBody NoteDto notedto,@RequestParam String token) {
		System.out.println(token);
		return service.updateNote(id,notedto, token);
	}	
	@GetMapping("/sortedbyTitle")
	public List<Note> sortedByTitle(){
		return service.sortByTitle();
	}
	@GetMapping("/sortByDate")
	public List<Note> sortedByDate(){
		return service.sortByCreatedDate();
	}
	@PostMapping("/pin")
	public ResponseEntity<Object> isPin(@RequestParam String id,@RequestParam String token) {
		return service.isPinned(id, token);
	}
	@PostMapping("/archieve")
	public ResponseEntity<Object> isArchieve(@RequestParam String id,@RequestParam String token) {
		return service.isPinned(id, token);
	}
	@PostMapping("/trash")
	public ResponseEntity<String> isTrash(@RequestParam String id,@RequestParam String token) {
		return service.isTrashed(id, token);
	}
	@PostMapping("/addlbl")
	public ResponseEntity<Object> addLabel(@RequestParam String email,@RequestParam String noteid,@RequestParam String lblid) {
		return service.addLabel(email, noteid, lblid);
	}
	@PostMapping("/collobrate")
	public ResponseEntity<Object> collobrate(@RequestParam String token, @RequestParam String noteid, @RequestParam String collabemail){
	return service.addCollobrate(token, noteid, collabemail);		
	}
	@PostMapping("/removecollobrate")
	public ResponseEntity<Object> removecollobrate(@RequestParam String token, @RequestParam String noteid, @RequestParam String collabemail){
	return service.removeCollobrate(token, noteid, collabemail);		
	}
	@PostMapping("/addReminder")
	public Response addReminder(@RequestParam String token,@RequestParam String noteId,@RequestParam String date) throws ParseException {
		return service.addReminder(token, noteId, date);
	}
	@PostMapping("/removeReminder")
	public Response removeReminder(@RequestParam String token,@RequestParam String noteId) {
		return service.removeReminder(token, noteId);
	}
	@PostMapping("/updateReminder")
	public Response updatereminder(@RequestParam String token,@RequestParam String noteId,@RequestParam String date) {
		return service.updateReminder(token, noteId, date);
	}

}




















