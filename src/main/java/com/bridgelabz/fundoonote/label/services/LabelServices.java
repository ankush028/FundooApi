package com.bridgelabz.fundoonote.label.services;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
import java.util.List;
import com.bridgelabz.fundoonote.label.dto.LabelDto;
import com.bridgelabz.fundoonote.label.model.Label;
import com.bridgelabz.fundoonote.response.Response;

public interface LabelServices {
/**
 * @param labeldto
 * @param token
 * @return A Response message operation has performed or not
 */
public Response addLabel(LabelDto labeldto,String token);
/**
 * @param token
 * @param id
 * @return A Response message operation has performed or not
 */
public  Response deleteLabel(String token,String id);
/**
 * @param token
 * @param id
 * @param labeldto
 * @return A Response message operation has performed or not
 */
public Response updateLabel(String token,String id,LabelDto labeldto);
/**
 * @return A Response message operation has performed or not
 */
public  List<Label> getAlllabel();
/**
 * @return all label present in database
 */
public List<Label> sortedByTitle();
/**
 * @return all sorted list in sorted manner
 */
public List<Label> sortedByDate();
/**
 * @param email
 * @param noteid
 * @param lblid
 * @return A Response message operation has performed or not
 */
public Response addNote(String email,String noteid,String lblid);
}
