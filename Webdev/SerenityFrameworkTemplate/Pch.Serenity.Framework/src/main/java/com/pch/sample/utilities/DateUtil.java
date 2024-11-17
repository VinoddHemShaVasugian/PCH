/*
 * @Author pvadivelu
 * @Version 1.0.0
 * PCH Serenity Framework Template
 */
package com.pch.sample.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.text.ParseException;


/**
 * The Class DateUtil.
 */
public class DateUtil {

	/** The last year in range. */
	public String firstYearInRange = "", lastYearInRange = "";
	
	/** The only one year in range. */
	public boolean onlyOneYearInRange = false;
	
	/**
	 * Return date.
	 *
	 * @param value the value
	 * @param format the format
	 * @return the string
	 */
	// Method to calculate date based on the supplied value (+, -, 0).
	public String returnDate(int value, String format) {

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, value);
		Date Day = c.getTime();
		DateFormat dateFormat = new SimpleDateFormat(format);
		String getDate = dateFormat.format(Day);
		return getDate;
	}


	/**
	 * Convert string to date.
	 *
	 * @param stringDate the string date
	 * @return the date
	 */
	// Convert date in String format to Date format
	public Date convertStringToDate(String stringDate) {

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

	/**
	 * Convert string to date new.
	 *
	 * @param stringDate the string date
	 * @return the date
	 */
	public Date convertStringToDateNew(String stringDate){

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		Date date = null;

		try {
			date = formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * Gets the current date.
	 *
	 * @return the current date
	 */
	public String getCurrentDate() {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();

		String currDate = formatter.format(cal.getTime());

		return currDate;
	}
	
	/**
	 * Gets the current date.
	 *
	 * @param dateFormat the date format
	 * @return the current date
	 */
	public static String getCurrentDate(String dateFormat) {

		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();

		String currDate = formatter.format(cal.getTime());

		return currDate;
	}


	/**
	 * Process date.
	 *
	 * @param date the date
	 * @param format the format
	 * @return the string
	 */
	// Method to process (String) date
	public String processDate(String date,String format) {

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

	/**
	 * Conver date string to date time.
	 *
	 * @param stringDate the string date
	 * @return the date
	 */
	// Input Date in String format and output Date in date-time format
	public Date converDateStringToDateTime(String stringDate){

		Date dt = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
		try {
			dt = formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}	
	
	/**
	 * Conver date string to date time.
	 *
	 * @param stringDate the string date
	 * @param sourceFormat the source format
	 * @return the date
	 */
	public Date converDateStringToDateTime(String stringDate, String sourceFormat){

		Date dt = null;
		SimpleDateFormat formatter = new SimpleDateFormat(sourceFormat);
		try {
			dt = formatter.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}	


	/**
	 * Gets the correct date format.
	 *
	 * @param dateStringfromRequest the date stringfrom request
	 * @return the correct date format
	 */
	public String getCorrectDateFormat(String dateStringfromRequest) {
		String date = null;
		try{
			Date d = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")).parse(dateStringfromRequest);
			date = (new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a")).format(d).toString();		 
		} catch(Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * Adds the days togiven date.
	 *
	 * @param date the date
	 * @param addDays the add days
	 * @return the string
	 */
	public String addDaysTogivenDate(String date, int addDays){

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.setTime(convertStringToDateNew(date.substring(0, Math.min(date.length(), 10))));
		c.add(Calendar.DATE, addDays);		
		String getNewDate = formatter.format(c.getTime());		
		return getNewDate;
	}

	/**
	 * Gets the current dateand time.
	 *
	 * @return the current dateand time
	 */
	public String getCurrentDateandTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Calendar c = Calendar.getInstance();
		return formatter.format(c.getTime());		
	}

	/**
	 * Gets the current dateand time.
	 *
	 * @param format the format
	 * @return the current dateand time
	 */
	public String getCurrentDateandTime(String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		return formatter.format(c.getTime());		
	}

	/**
	 * Gets the current dateand time.
	 *
	 * @param format the format
	 * @param minutes the minutes
	 * @return the current dateand time
	 */
	public String getCurrentDateandTime(String format, int minutes){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, minutes);
		return formatter.format(c.getTime());		
	}


	/**
	 * Gets the current dateand time.
	 *
	 * @param minutes the minutes
	 * @return the current dateand time
	 */
	public String getCurrentDateandTime(int minutes){
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, minutes);
		return formatter.format(c.getTime());
	}

	/**
	 * Convert json dateto java script string.
	 *
	 * @param jsonDate the json date
	 * @return the string
	 */
	public static String convertJsonDatetoJavaScriptString(String jsonDate){
		String convertedDate = null; 
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		convertedDate = dateFormat.format(Day)+"T04:00:00.000Z";
		//System.out.println(convertedDate);
		return convertedDate;
	}
	
	/**
	 * Convert json dateto java script string.
	 *
	 * @param jsonDate the json date
	 * @param format the format
	 * @return the string
	 */
	public static String convertJsonDatetoJavaScriptString(String jsonDate, String format){
		String convertedDate = null; 
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
		DateFormat dateFormat = new SimpleDateFormat(format);		
		convertedDate = dateFormat.format(Day);
		return convertedDate;
	}
	
	/**
	 * Convert json dateto java script stringwithout time.
	 *
	 * @param jsonDate the json date
	 * @return the string
	 */
	public static String convertJsonDatetoJavaScriptStringwithoutTime(String jsonDate){		
		return convertJsonDatetoJavaScriptString(jsonDate).split("T")[0];
	}

	/**
	 * Convert json dateto valid date formatin date.
	 *
	 * @param jsonDate the json date
	 * @return the date
	 * @throws ParseException the parse exception
	 */
	public static Date convertJsonDatetoValidDateFormatinDate(String jsonDate) throws ParseException{	
		String convertedDate = null; 
		convertedDate = convertJsonDatetoJavaScriptString(jsonDate);
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		//System.out.println(convertedDate);
		return dateFormat.parse(convertedDate);		
	}

	/**
	 * Checks if is afteror before.
	 *
	 * @param jsonDate the json date
	 * @param value the value
	 * @return the string
	 */
	public static String isAfterorBefore(String jsonDate, int value){

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date date = null;
		try {				
			date = dateFormat.parse(jsonDate);			
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.DATE, value);
		return dateFormat.format(cal.getTime());

	}

	/**
	 * Gets the months between dates.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the months between dates
	 * @throws ParseException the parse exception
	 */
	//
	public int getMonthsBetweenDates(Date startDate, Date endDate) throws ParseException{

		Calendar start = Calendar.getInstance();

		Calendar end = Calendar.getInstance();

		start.setTime(startDate);

		end.setTime(endDate);

		int monthsBetween = 0;

		for(String year: getYearsBetweenDates(startDate, endDate)){
			monthsBetween += getyearWiseMonthsCount(startDate, endDate, year);
			System.out.println(year + ": " + getyearWiseMonthsCount(startDate, endDate, year));
		}
		//		int dateDiff = end.get(Calendar.DAY_OF_MONTH) - start.get(Calendar.DAY_OF_MONTH);
		//		
		//		if(dateDiff<0){
		//			int borrow = end.getActualMaximum(Calendar.DAY_OF_MONTH);
		//			dateDiff = end.get(Calendar.DAY_OF_MONTH) + borrow - start.get(Calendar.DAY_OF_MONTH);
		//			monthsBetween--;
		//		}
		//		if(dateDiff>0){
		//			monthsBetween++;
		//		}
		//		else{
		//			monthsBetween++;
		//		}
		//		monthsBetween += end.get(Calendar.MONTH)-start.get(Calendar.MONTH);      
		//        monthsBetween  += (end.get(Calendar.YEAR)-start.get(Calendar.YEAR))*12;
		//        
		//        //monthsBetween++;
		//        
		//        int mb = 0;

		//        System.out.println("MB: " +mb);

		return monthsBetween;

	}
	
	/**
	 * Gets the days between dates.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the days between dates
	 * @throws ParseException the parse exception
	 */
	public int getDaysBetweenDates(Date startDate, Date endDate) throws ParseException{

		Calendar start = Calendar.getInstance();

		Calendar end = Calendar.getInstance();

		start.setTime(startDate);

		end.setTime(endDate);

		int daysBetween = 0;

		for(String year: getYearsBetweenDates(startDate, endDate)){
			daysBetween += getyearWiseDaysCount(startDate, endDate, year);
		}
		return daysBetween;

	}

	/**
	 * Gets the years between dates.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the years between dates
	 * @throws ParseException the parse exception
	 */
	public List<String> getYearsBetweenDates(Date startDate, Date endDate) throws ParseException{

		List<String> years = new ArrayList<String>();

		Calendar cal = new GregorianCalendar();


		cal.setTime(startDate);



		SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
		while(cal.getTime().before(format2.parse(format2.format(endDate))) || (format2.format(cal.getTime()).equalsIgnoreCase(format2.format(endDate)))){

			Date result = cal.getTime();

			years.add(format2.format(result));
			cal.add(Calendar.YEAR, 1);
		}

		if(years.size()>1){
			firstYearInRange = years.get(0);
			lastYearInRange = years.get(years.size()-1);
		}
		if(years.size()==1){
			onlyOneYearInRange = true;
			firstYearInRange = years.get(0);
			lastYearInRange = years.get(0);
		}
		return years;
	}

	/**
	 * Gets the year wise months count.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param year the year
	 * @return the year wise months count
	 */
	public int getyearWiseMonthsCount(Date startDate, Date endDate, String year){

		int yearmonths = 0;

		Calendar cal = new GregorianCalendar();

		cal.setTime(startDate);
		SimpleDateFormat format1 = new SimpleDateFormat("MM/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
		boolean insideIf = false;
		while(cal.getTime().before(endDate) || (format1.format(cal.getTime()).equalsIgnoreCase(format1.format(endDate)))){

			Date result = cal.getTime();
			if(format2.format(result).equalsIgnoreCase(year)){
				insideIf = true;
				System.out.println(format1.format(result));
				yearmonths++;
			}
			cal.add(Calendar.MONTH, 1);
		}
		if((yearmonths==0)&&(insideIf == false))
			yearmonths++;
		else if(yearmonths==1)
			yearmonths++;

		return yearmonths;

	}
	
	/**
	 * Gets the year wise days count.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param year the year
	 * @return the year wise days count
	 */
	public int getyearWiseDaysCount(Date startDate, Date endDate, String year){

		int yearDays = 0;

		Calendar cal = new GregorianCalendar();

		cal.setTime(startDate);
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy");
		boolean insideIf = false;
		while(cal.getTime().before(endDate) || (format1.format(cal.getTime()).equalsIgnoreCase(format1.format(endDate)))){

			Date result = cal.getTime();
			if(format2.format(result).equalsIgnoreCase(year)){
				insideIf = true;
				yearDays++;
			}
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		if((yearDays==0)&&(insideIf == false))
			yearDays++;
		else if(yearDays==1)
			yearDays++;

		return yearDays;
	}
	
	/**
	 * Gets the days between dates.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the days between dates
	 * @throws ParseException the parse exception
	 */
	public long getDaysBetweenDates(String startDate, String endDate) throws ParseException{
		
		long days = 0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		start.setTime(dateFormat.parse(startDate));
		end.setTime(dateFormat.parse(endDate));
		
		long differenceBetweenDates = end.getTime().getTime() - start.getTime().getTime();
		System.out.println(differenceBetweenDates);
		days = differenceBetweenDates/(24 * 60 * 60 * 1000);
		
		return days;
	}
	
	/**
	 * Convert date format.
	 *
	 * @param stringDate the string date
	 * @param sourceformat the sourceformat
	 * @param destinationFormat the destination format
	 * @return the string
	 */
	public String convertDateFormat(String stringDate, String sourceformat, String destinationFormat){

		Date dt = null;
		SimpleDateFormat srcformatter = new SimpleDateFormat(sourceformat);
		SimpleDateFormat destformatter = new SimpleDateFormat(destinationFormat);
		try {
			dt = srcformatter.parse(stringDate);			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return destformatter.format(dt);
	}
	
	/**
	 * Convert json dateto valid date formatin string.
	 *
	 * @param jsonDate the json date
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String convertJsonDatetoValidDateFormatinString(String jsonDate) throws ParseException{	
		String convertedDate = null; 
		convertedDate = convertJsonDatetoJavaScriptString(jsonDate);
		return convertedDate;		
	}
}