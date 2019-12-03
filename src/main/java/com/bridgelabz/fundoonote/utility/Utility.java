package com.bridgelabz.fundoonote.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {
	public static Date dateFormat(String date) throws ParseException {
	
	SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");
	Date date1= sdf.parse(date);
		return date1;
	}
	
}
