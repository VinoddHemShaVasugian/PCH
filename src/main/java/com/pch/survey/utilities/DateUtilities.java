package com.pch.survey.utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;


public class DateUtilities {

	private static final SimpleDateFormat MMddyyyyFORMAT = new SimpleDateFormat("MM/dd/yyyy");
	private static final SimpleDateFormat yyyyMMddFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat MMddyyyyFORMAT1 = new SimpleDateFormat("MM-dd-yyyy");
 	private static final SimpleDateFormat yyyyMMddFORMAT1 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat yyyyMMddTimeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ISO_OFFSET_DATE_TIME;
 	
 	
	public static int compareDate1ToDate2(String dateStr1, String dateStr2) {
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = MMddyyyyFORMAT1.parse(dateStr1.trim());
			date2 = MMddyyyyFORMAT1.parse(dateStr2.trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date1.compareTo(date2);
	}

	// SQL Server Date formats


	public static String getCurrentDateAsSQLDateString() {
		return yyyyMMddFORMAT.format(new Date());
	}
	


	public static String getCurrentDateAsMMddyyyy() {
		return MMddyyyyFORMAT1.format(new Date());
	}
	
	
	
	public static String getCurrentDateAsMMddyyyyWithSlashes() {
		return MMddyyyyFORMAT.format(new Date());
	}
	 
	
	public static String getADifferentDateFromFromToday(int daysMinus) {
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, daysMinus);
        return yyyyMMddFORMAT.format(cal.getTime());
    }

	public static String getCurrentTimeStampSQLTSString() {
		return new Timestamp(System.currentTimeMillis()).toString();
	}


	
	public static String addToDateTimesAsString(int numberOfDaysBeforeCurrentDate) {
		String date;

		Date today = new Date();
		;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		today = DateUtils.addDays(today, numberOfDaysBeforeCurrentDate);
		date = dateFormat.format(today);

		return date;
	}
	
 
	
	
	public static String getCurrentDateAsString() {
		return yyyyMMddFORMAT.format(new Date());
		
	}

	public static String getCurrentDateTimeAsString() {
		return new Timestamp(System.currentTimeMillis()).toString();
		
	}
	
	public static String getTodaysDate() {
		Calendar today = Calendar.getInstance();
		return MMddyyyyFORMAT.format(today.getTime());

	}

	public static boolean isFirstOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String firstOfMonth = MMddyyyyFORMAT.format(calendar.getTime());
		if(getTodaysDate().equals(firstOfMonth)){
			return true;
		}
       	return false;
	}
	
	
	public static String getOnlyTodaysDate(){
		Calendar today = Calendar.getInstance();
		return yyyyMMddFORMAT1.format(today.getTime());
	}
	
	public static int getCurrentMonth(){
		return Calendar.getInstance().get(Calendar.MONTH);
	}
	

	public static int getCurrentYear(){
		return Year.now().getValue();
	}
	
	
	public static String getLastDayOfYear(){
		// USED ONLY FOR keeping item description in scope actual number of days do not matter
	 
		return Calendar.getInstance().get(Calendar.YEAR) +"364";
	}
	
	
	public static String calculateNewDateMMddYYY(String dateStr) {
		String returnDate = null;
		if (!dateStr.startsWith("[today")) 
		   return 	returnDate;
		dateStr= dateStr.replace("]","");
		int days =0;
		if (dateStr.startsWith("[today-")) {
			days = Integer.parseInt(dateStr.substring(dateStr.indexOf("-")));
		} else if (dateStr.startsWith("[today+")) {
			days = Integer.parseInt(dateStr.substring(dateStr.indexOf("+")));
		} else if (dateStr.startsWith("[today")) {
			days =0;
		}
		String date;
		Date today = new Date();
		today = DateUtils.addDays(today, days);
		date = MMddyyyyFORMAT.format(today);
		return date;
		
	}

	
	public static String calculateNewDateyyyyMMddFORMAT(String dateStr) {
		String returnDate = null;
		if (!dateStr.startsWith("[today")) 
		   return 	returnDate;
		dateStr= dateStr.replace("]","");
		int days =0;
		if (dateStr.startsWith("[today-")) {
			days = Integer.parseInt(dateStr.substring(dateStr.indexOf("-")));
		} else if (dateStr.startsWith("[today+")) {
			days = Integer.parseInt(dateStr.substring(dateStr.indexOf("+")));
		} else if (dateStr.startsWith("[today")) {
			days =0;
		}
		String date;
		Date today = new Date();
		today = DateUtils.addDays(today, days);
		date = yyyyMMddFORMAT.format(today);
		return date;
		
	}

	 
	
	
	
	public static String getCurrentDateAheadbyDays(int days) {

		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS a");

		cal.add(Calendar.DAY_OF_YEAR, days);
		String beforeDate = dateFormat.format(cal.getTime());
		return beforeDate;
	}
	
	
	public static String get4CharYear() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		return ""+year;
	}
	
	
	
	public static String get2CharDayOfMonth() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		return (day<10?("0"+day):(""+day));
	}
	
	
	public static String get2CharMonthOfYear() {
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH)+1;
		return (month<10?("0"+month):(""+month));
	}
	
	public static String get2CharHourOfDay() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		return (hour<10?("0"+hour):(""+hour));
	}
	

	public static long getTimeStampDifferenceInSeconds(String timestampString1, String timestampString2) {
           // Parse the timestamp strings to LocalDateTime objects
		OffsetDateTime  timestamp1 = OffsetDateTime.parse(timestampString1, dateTimeFormatter);
		OffsetDateTime  timestamp2 = OffsetDateTime.parse(timestampString2, dateTimeFormatter);
         // Calculate the duration between the two timestamps
        Duration duration = Duration.between(timestamp1, timestamp2);
         // Get the difference in seconds
        return  duration.getSeconds();
		
	}
	
	
	public static void main(String[] args) throws ParseException {
		
		
		String x = "[2023-08-25T00:00:30.162618-04:00][ Ref: PC02Q-64E8275E-36C6TA ] lucid.DEBUG: [getUserObject] TempUser created with OAT: b5716903-a117-44fa-b4e6-06c769570905  \r\n"
				+ "";
		
		x = x.substring(1,x.indexOf("]"));
		System.out.println(x);
		System.out.println(DateUtilities.getTimeStampDifferenceInSeconds("2023-08-25T07:01:04.287620-04:00","2023-08-25T07:01:09.287620-04:00"));

	}
	
}
