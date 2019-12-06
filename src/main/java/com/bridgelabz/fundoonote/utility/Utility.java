package com.bridgelabz.fundoonote.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
	private  String date;
	
	public Utility(String date) {
		this.date=date;
	}
		
	public  Date dateFormat() throws ParseException {
		
		return new SimpleDateFormat("dd/mm/yyyy").parse(date);
	}
}
