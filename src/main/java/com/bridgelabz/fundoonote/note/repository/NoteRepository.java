package com.bridgelabz.fundoonote.note.repository;

/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoonote.note.model.Note;

public interface NoteRepository extends MongoRepository<Note,String>{

	List<Note> findByEmail(String email);
	List<Note> findAll();

}
