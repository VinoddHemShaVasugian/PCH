package com.pch.survey.centralservices;

import java.util.List;

import com.pch.survey.utilities.ConfigurationReader;
import com.pchengineering.billmecontestentries.databaseentries.ContestEntriesHelper;
import com.pchengineering.billmecontestentries.databasehelper.UserEntries;
  

public class ContestEntries {
	private static ContestEntriesHelper contestEntryHelper = new ContestEntriesHelper();
    private static List<UserEntries> contestEntries = null;
    private static String envir = ConfigurationReader.getInstance().getEnvironment();

   
	public static int getContestEntryCountByEmail(String email) {
 	     switch (envir) {
           case "QA":
        	   return  getContestEntriesByEmailQA(email);
           case "STG":
        	   return  getContestEntriesByEmailSTG(email);
            default: 
         	   return  getContestEntriesByEmailQA(email);
      }
	}
    
	   private static int getContestEntriesByEmailQA(String email) {
	     	return ContestEntriesHelper.getContestEntriesByEmailQA(email).size();
	     	
	     }
    

	   private static int getContestEntriesByEmailSTG(String email) {
		   return contestEntryHelper.getContestEntriesByEmailSTG(email).size();
	     	
	     }

    
    
    
    
    
    
    
    
    
    
	
	public static void main(String[] args) {
		List<UserEntries> entriesByEmail = (new ContestEntriesHelper()).getContestEntriesByEmailCQA("csacb16c75b-6993-4.03.09.23@pchmail.com");
 		
 	}

}
