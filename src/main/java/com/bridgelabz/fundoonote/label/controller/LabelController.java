package com.bridgelabz.fundoonote.label.controller;
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
import com.bridgelabz.fundoonote.label.dto.LabelDto;
import com.bridgelabz.fundoonote.label.model.Label;
import com.bridgelabz.fundoonote.label.services.LabelServices;
import com.bridgelabz.fundoonote.response.Response;
@RequestMapping("/labelapi")
@RestController
public class LabelController {
		
	/**
	 * connect the labelservices interface now spring is responsible to creation 
	 * of object of implemented class
	 */
	@Autowired
	private LabelServices service;
	
	/**
	 * @param token
	 * @param labeldto
	 * @return
	 */
	@PostMapping("/addlabel")
	public Response addlabel(@RequestParam String token ,@RequestBody LabelDto labeldto) {
		return service.addLabel(labeldto, token);
	}
	/**
	 * @return all label present in database
	 */
	@GetMapping("/getAllLabel")
	public List<Label> getAllLabel(){
		return service.getAlllabel();
	}
	/**
	 * @param id
	 * @param token
	 * @return A response message operation has performed or not
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Object> delete(@RequestParam String id,@RequestParam String token) {
		return service.deleteLabel(token, id);
	}
	/**
	 * @purpose to update the label information
	 * @param id
	 * @param token
	 * @param labeldto
	 * @return  A response message operation has performed or not
	 */
	@PutMapping("/update")
	public ResponseEntity<Object> update(@RequestParam String id,@RequestParam String token,@RequestBody LabelDto labeldto) {
		return service.updateLabel(token, id, labeldto);
	}
	/**
	 * @return sorted list by via title 
	 */
	@GetMapping("/sortLabelByTitle")
	public List<Label> sortedLabel(){
		return service.sortedByTitle();
	}
	/**
	 * @return a sorted labels by  date
	 */
	@GetMapping("/sortByDate")
	public List<Label> sorted(){
		return service.sortedByDate();
	}
	/**
	 * @param email
	 * @param noteid
	 * @param lblid
	 * @return A response message operation has performed or not
	 */
	public ResponseEntity<Object> addNote(@RequestParam String email,@RequestParam String noteid,@RequestParam String lblid){
		return service.addNote(email, noteid, lblid);
	}
	
}
