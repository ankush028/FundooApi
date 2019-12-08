package com.bridgelabz.fundoonote.note.model;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import com.bridgelabz.fundoonote.label.model.Label;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Data
public class Note {
	@Pattern(regexp=".+@.+\\.[a-z]+")
	private String email;
	@Id
	private String id;
	
	@Size(min=3,max=50)
	@NotBlank
	private String title;
	
	@NotBlank
	private String description;
	
	private LocalDate createdDate;
	
	private LocalDate updatedDate;
	
	private boolean isPinned;
	
	private boolean isTrashed;
	
	private boolean isArchieved;
	
	private Date reminder;
	
	
	
	@DBRef(lazy=true)
	ArrayList<Label> listOfLabel=new ArrayList<>();
	

	ArrayList<String> listOfcollobarator = new ArrayList<>();
	@Override
	public String toString() {
		return "Note [id=" + id + ", title=" + title + ", description=" + description + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", isPinned=" + isPinned + ", isTrashed=" + isTrashed
				+ ", isArchieved=" + isArchieved + "]";
	}
	
	


}
