package com.bridgelabz.fundoonote.label.repository;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoonote.label.model.Label;
//@purpose repository of label
public interface LabelRepository extends MongoRepository<Label, String>{ 
 public List<Label> findByEmail(String email);
}
