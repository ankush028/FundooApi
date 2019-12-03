package com.bridgelabz.fundoonote.label.services;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonote.config.Model;
import com.bridgelabz.fundoonote.customexception.Exceptions;
import com.bridgelabz.fundoonote.label.dto.LabelDto;
import com.bridgelabz.fundoonote.label.model.Label;
import com.bridgelabz.fundoonote.label.repository.LabelRepository;
import com.bridgelabz.fundoonote.model.User;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.NoteRepository;
import com.bridgelabz.fundoonote.repository.UserRepository;
import com.bridgelabz.fundoonote.response.Response;
import com.bridgelabz.fundoonote.utility.Jwt;


@Service
public class LabelServicesimpli implements LabelServices{

	/**
	 * @see com.bridgelabz.fundoonote.utility
	 */
	@Autowired
	private Jwt jwtToken;
	/**
	 * @see com.bridgelabz.fundoonote.label.repository
	 */
	@Autowired
	private LabelRepository labelRepo;
	/**
	 * @see com.bridgelabz.fundoonote.repository
	 */
	@Autowired
	private UserRepository userRepo;
	/**
	 * @see com.bridgelabz.fundoonote.note.repository
	 */
	@Autowired
	private NoteRepository noteRepo;
//	
	@Autowired
	Environment environment;
	
	/**
	 *METHOD ADD NEW LABEL
	 */
	@Override
	public Response addLabel(LabelDto labeldto, String token) {
		//find email id from token
		String email = jwtToken.getUserToken(token);
		//find user from email
		User user = userRepo.findByEmail(email);
		//check Condition if user has not found
 		if(user==null) {
			throw new Exceptions("UserNotFoundException");
		}
		//map the labeldto class to label model
		Label label =Model.getModel().map(labeldto,Label.class);
		//Set the created date
		label.setCreatedDate(LocalDate.now());
		label.setEmail(email);
		//save is database
		labelRepo.save(label);
		//return response
		return new Response(200,environment.getProperty("Addedlabel"),HttpStatus.OK);
	}

	/**
	 *METHOD FOR DELETE LABEL	
	 */
	@Override
	public ResponseEntity<Object> deleteLabel(String token, String id) {
		//convert token to email
		String email = jwtToken.getUserToken(token);
		//find list of label present in this email
		List<Label> listOflabel =  labelRepo.findByEmail(email);
		//find a label from label id
		Label label = listOflabel.stream().filter(i->i.getLabelid().equals(id)).findAny().orElse(null);
		if(label==null) {		//condition check is it present or not
			throw new Exceptions("LabelNotFoundExceptions");
		}
		//delete label
		labelRepo.delete(label);
		//return reaponse
		return new ResponseEntity<>(environment.getProperty("delete_label"),HttpStatus.OK);
	}

	/**
	 *UPDATE THE EXISTING LABEL
	 */
	@Override
	public ResponseEntity<Object> updateLabel(String token,String id,LabelDto labeldto) {
		//convert token to the email
		String email = jwtToken.getUserToken(token);
		//find list of label present in this email
		List<Label> listOflabel =  labelRepo.findByEmail(email);
		//find a single one using the label id
		Label label = listOflabel.stream().filter(i->i.getLabelid().equals(id)).findAny().orElse(null);
		if(label==null) { //check condition is it found or not
		throw new Exceptions("LabelNotFoundExceptions");
		}
		//set updated date
		label.setUpdatedDate(LocalDate.now());
		//set a new Title
		label.setLabeltitle(labeldto.getLabeltitle());
		//And Save updated label
		labelRepo.save(label);
		//return response
		return new ResponseEntity<>(environment.getProperty("update_label"),HttpStatus.OK);
	}

	/**
	 *METHOD FOR GET ALL LABLES
	 */
	@Override
	public List<Label> getAlllabel() {
		//return all the labels present in the database
		return labelRepo.findAll();
	}

	/**
	 *METHOD FOR SORT THE LABEL BY TITLE
	 */
	@Override
	public List<Label> sortedByTitle() {
		//use stream to sort the labels using title
		List<Label> sorted = labelRepo.findAll().stream()
				.sorted(Comparator.comparing(Label::getLabeltitle)).collect(Collectors.toList());
		//and return the list
		return sorted;
	}

	/**
	 *METHOD FOR SORT LABELS BY CREATED DATE
	 */
	@Override
	public List<Label> sortedByDate() {
		List<Label> sorted = labelRepo.findAll().stream()
				.sorted(Comparator.comparing(Label::getCreatedDate)).collect(Collectors.toList());
		return sorted;	
	}
	/**
	 *METHOD TO ADD A NOTE IN A LABEL
	 */
	public ResponseEntity<Object> addNote(String email ,String noteid,String lblid) {
		//find all the note present in the email
		List<Note> listOfNote = noteRepo.findByEmail(email);
		//getr all the labels present in the email
		List<Label> listOfLabel = labelRepo.findByEmail(email);
		//find one using note id
		Note note = listOfNote.stream().filter(i->i.getId().equals(noteid)).findAny().orElse(null);
		//find one using label id
		Label label = listOfLabel.stream().filter(i->i.getLabelid().equals(lblid)).findAny().orElse(null);
		if(note==null && label==null) {  //check condition 
			//add the label in note and addnote in the label 
			throw new Exceptions("EitherNoteOrUserNotFoundException");
		}
		note.getListOfLabel().add(label);
		label.getListOfNote().add(note);
		noteRepo.save(note);
		labelRepo.save(label);
		//return response
		return new ResponseEntity<>(environment.getProperty("Sucess"),HttpStatus.OK);
	}


}
