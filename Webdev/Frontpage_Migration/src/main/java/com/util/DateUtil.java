package com.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;


/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class DateUtil {

	// Method to calculate date based on the supplied value (+, -, 0).
	public static String returnDate(int value, String format) {

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, value);
		Date Day = c.getTime();
		DateFormat dateFormat = new SimpleDateFormat(format);
		String getDate = dateFormat.format(Day);
		return getDate;
	}
	
	
	// Convert date in String format to Date format
	public static Date convertStringToDate(String stringDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

		Date date = null;

		try {
			
			if(!stringDate.equals("") || stringDate != null){
				date = formatter.parse(stringDate);
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	public static String getCurrentDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();

		String currDate = formatter.format(cal.getTime());
		return currDate.substring(3, 5);
	}
	

	// Method to process (String) date
	public static String processDate(String date,String format) {

		String processedDate = "";

		if (date.equalsIgnoreCase("empty")) {

			return processedDate;

		} else if (date.equalsIgnoreCase("CurrentDt")) {

			processedDate = returnDate(0,format);

		} else if (date.startsWith("CurrentDt+")) {

			processedDate = returnDate(Integer.parseInt(date
					.split("\\+")[1]),format);

		} else if (date.startsWith("CurrentDt-")) {

			processedDate = returnDate(Integer.parseInt("-"
					+ date.split("\\-")[1]),format);

		} else {
			return date;
		}

		return processedDate;
	}
	
	// Input Date in String format and output Date in date-time format
	public static Date converDateStringToDateTime(String stringDate){
		
		Date dt = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		try {
			dt = formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}	
	
	
	public static String getCorrectDateFormat(String dateStringfromRequest) {
		String date = null;
		try{
			Date d = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")).parse(dateStringfromRequest);
			date = (new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")).format(d).toString();		 
			} catch(Exception e) {
			e.printStackTrace();
			}
		return date;
	}
	
	public static String convertJsonDatetoValidDateFormat(String jsonDate){	
		Calendar calendar = Calendar.getInstance();
		String datereip = null;
		if(jsonDate.contains("-0400")){
			datereip = jsonDate.replace("/Date(", "").replace(")/", "").replace("-0400", "");
		}else{
			datereip = jsonDate.replace("/Date(", "").replace(")/", "").replace("-0500", "");
		}		
		Long timeInMillis = Long.valueOf(datereip);
		calendar.setTimeInMillis(timeInMillis);	
		Date Day = calendar.getTime();	
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSS");
		return dateFormat.format(Day);	
	}
	
	public static String getYesterday() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
		String strDay = null;
		switch (day) {
		    case Calendar.SUNDAY:
		    	strDay = "Sunday";
		    	break;
		    case Calendar.MONDAY:
		    	strDay = "Monday";
		    	break;
		    case Calendar.TUESDAY:
		        strDay = "Tuesday";
		        break;
		    case Calendar.WEDNESDAY:
		        strDay = "Wednesday";
		        break;
		    case Calendar.THURSDAY:
		        strDay = "Thursday";
		        break;
		    case Calendar.FRIDAY:
		        strDay = "Friday";
		        break;
		    case Calendar.SATURDAY:
		        strDay = "Saturday";
		        break;
		     default :
		        	break;
		        
		}
		return strDay;
	}
	
	public static int getCurrentMinutes() {
		Calendar cal = Calendar.getInstance();
		cal.get(Calendar.MINUTE);
		int min = cal.get(Calendar.MINUTE);
		return min;
	}
}