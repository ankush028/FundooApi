package com.bridgelabz.fundoonote.dto;


import lombok.Data;
/**@Purpose Fundoo Api
 * @author Ankush Kumar Agrawal
 *@Date 20 Nov 2019
 */

//Data Transfer object class of Reset the Password
@Data
public class ResetDto {
private String password;
private String confirmPassword;
}
