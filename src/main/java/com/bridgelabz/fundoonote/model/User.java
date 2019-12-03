package com.bridgelabz.fundoonote.model;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
private String name;
@Id
private String id;
private String email;
private String password;
private String confirmPassword;
private boolean isvalidate;
private String profile;
}
