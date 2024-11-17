package com.pch.sw.tests.misc;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class SearchActivityLogTests extends BaseTest {

	private HomePage homePage;
	RegistrationPage regPage;
	String keyword="books";
	AdminBasePage joomlaPage;
	WebHeaderPage headerPage;
	private User randomUser_1, randomUser_2, randomUser_3, randomUser_4, randomUser_5;

	//@Test(description="TestID='17297', Verify Activity log for Guest user from browser's console",groups={GroupNames.Desktop,GroupNames.Regression},testName="DifferentiateGuidedSearchOnActivityLog_Desktop_Tablet")
	public void testActivityLogForGuestUserAndRegisteredUser() throws SQLException{	
		String Url, email, tc, v;
		tc= ""; v="";
		
		
		if(Environment.getEnvironment().equalsIgnoreCase("STG")){
			email = "activitytable@pchmail.com";
			tc = "204SX4880K";
			v= "20073489";
			Url = "http://search.stg.pch.com/search/?q=shoes&src1=swemail&src2=13S2505&mailid=pch13S2505&tc=204SX4880K&v=20073489&e=994d9975-debd-4434-bce6-47fe2a2465e4&email=activitytable@pchmail.com";
		}else{
			email = "drtest90@pchmail.com";
			tc = "204SX4880K";
			v= "20073489";
			Url = "http://search.stg.pch.com/search/?q=shoes&src1=swemail&src2=13S2505&mailid=pch13S2505&tc=204SX4880K&v=20073489&e=03320A92-F11F-4EAD-825D-231E24563E39&email=drtest90@pchmail.com";
		}
		
		
		homePage.load();
		homePage.loadSearchWithEmailLink(Url);
		
		// Validate TC & V values and agent ID in search activity table
		DBUtils.validateSearchActivity(email, "tc", tc);
		DBUtils.validateSearchActivity(email, "v", v);
		CustomLogger.log("Validated the record successfully in Search_Activity_Log table.");
		
		
	}
	@Test(description="Verifying the contest entries on OAM Tool")
	public void verifyContestOam_tool() throws IOException
	{
		
		 loginToSearch(randomUser_1);
		 headerPage.search("books");
		//homePage.load();
		//headerPage.loginToSearch("np40@pchmail.com","testing");
		 joomlaPage.OamTool(Environment.getOamUsername(),Environment.getOamPassword(),randomUser_1.getEmail()); 
	}
}
