package com.pch.kenofrontend.stepdefinitions;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeStory;

public class PreAndPostScenarioSteps {
	
    @BeforeStory
	
	public static void test() throws InterruptedException
	{
		TimeZone timezone = TimeZone.getTimeZone("America/New_York");
		  
		Calendar calendar = Calendar.getInstance(timezone);
		
		int min =calendar.get(Calendar.MINUTE);
		   
		// If the User has less than 14 minutes to Play Slot cards then wait for next 20 minutes game set
		
		if((min % 20) > 6) {
		   
		System.out.println("Execution will sleep for " + (20 - (min % 20))+ " mins to get fresh 20 minutes game set " );
		   
		//TimeUnit.MINUTES.sleep(20 - (min % 20));
		
		}
		
	}

}
