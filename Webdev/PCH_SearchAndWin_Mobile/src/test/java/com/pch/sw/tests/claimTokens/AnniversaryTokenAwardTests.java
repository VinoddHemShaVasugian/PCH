package com.pch.sw.tests.claimTokens;

import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class AnniversaryTokenAwardTests extends BaseTest {

	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;	
	TokenHistoryPage tokenHistoryPage;
	User randomUser_1,randomUser_2,randomUser_3,randomUser_4,randomUser_5;
	String latestActivityName;
	int tokensUponAnniversary = 10000;
	String dateFormat = "yyyy-MM-dd";
	String timeZone = "America/New_York";
	String currentDate = Common.getCurrentDate(timeZone, dateFormat);
	String anniversaryTokenAwardMessage = "For Your Anniversary on PCHSearch&Win";
	String searchtokenAwardMessage = "For Searching Today!";

	@Test(enabled=false)
	public void testAnniversaryTokenConfiguration(){
		joomlaPage.goToArticle("Tokens / Anniversary");
		Assert.assertEquals(joomlaPage.setTextProperty("Token Amount", 10000),"success");
	}

	@Test(description="Anniversary tokens should not be awarded before anniversary date", testName = "20712")
	public void testAnniversary_Scenario1() throws SQLException, InterruptedException{
		loginToSearch(randomUser_1);
		headerPage.search("PCH",false);	
		headerPage.signOut();
		DBUtils.expireDailySearch(randomUser_1.getEmail());
		String updatedRegistrationDate = Common.addYearToDate(currentDate, dateFormat, -1);
		updatedRegistrationDate=Common.addDaysToDate(updatedRegistrationDate, dateFormat, +1);		
		DBUtils.updateRegistrationDate(randomUser_1.getEmail(),updatedRegistrationDate);		
		loginToSearch(randomUser_1);		
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH2",false);	
		homePage.goToTokenHistory();
		Assert.assertFalse(tokenHistoryPage.getActivityName().contains(anniversaryTokenAwardMessage));

	}

	@Test(description="Anniversary tokens should be awarded on anniversary date", testName = "20712")
	public void testAnniversary_Scenario2() throws SQLException, InterruptedException{
		loginToSearch(randomUser_2);
		headerPage.search("PCH",false);	
		headerPage.signOut();				
		DBUtils.expireDailySearch(randomUser_2.getEmail());
		DBUtils.updateRegistrationDate(randomUser_2.getEmail(),Common.addYearToDate(currentDate, dateFormat, -1));

		loginToSearch(randomUser_2);
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH2",false);		

		/* Logout and login again and perform a search,
		 * no anniversary tokens should be awarded as they have been already awarded.  
		 */
		headerPage.signOut();
		loginToSearch(randomUser_2);
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH3",false);
		homePage.goToTokenHistory();
		Common.sleepFor(2000);
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(anniversaryTokenAwardMessage));
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent(anniversaryTokenAwardMessage),String.valueOf(tokensUponAnniversary));		
		Assert.assertFalse(tokenHistoryPage.getEventCount(anniversaryTokenAwardMessage)>1);
	}


@Test(description="Anniversary tokens should be awarded if login is on one day after anniversary date", testName = "20712")
	public void testAnniversary_Scenario3() throws SQLException, InterruptedException{
		loginToSearch(randomUser_3);
		headerPage.search("PCH",false);

		headerPage.signOut();

		DBUtils.expireDailySearch(randomUser_3.getEmail());
		String updatedRegistrationDate = Common.addYearToDate(currentDate, dateFormat, -1);
		updatedRegistrationDate=Common.addDaysToDate(updatedRegistrationDate, dateFormat, -1);

		DBUtils.updateRegistrationDate(randomUser_3.getEmail(),updatedRegistrationDate);

		loginToSearch(randomUser_3);		
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH2",false);

		/*Logout and login again and perform a search,
		 * no anniversary tokens should be awarded as they have been already awarded.  
		 */
		headerPage.signOut();
		loginToSearch(randomUser_3);	
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH3",false);
         homePage.goToTokenHistory();
         Common.sleepFor(2000);
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(anniversaryTokenAwardMessage));
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent(anniversaryTokenAwardMessage),String.valueOf(tokensUponAnniversary));
		Assert.assertFalse(tokenHistoryPage.getEventCount(anniversaryTokenAwardMessage)>1);		
	}


	@Test(description="Anniversary tokens should be awarded if login is within seven days of anniversary date", testName = "20712")
	public void testAnniversary_Scenario4() throws SQLException, InterruptedException{
		loginToSearch(randomUser_4);
		headerPage.search("PCH",false);	
		headerPage.signOut();

		DBUtils.expireDailySearch(randomUser_4.getEmail());
		String updatedRegistrationDate = Common.addYearToDate(currentDate, dateFormat, -1);
		updatedRegistrationDate=Common.addDaysToDate(updatedRegistrationDate, dateFormat, -6);
		DBUtils.updateRegistrationDate(randomUser_4.getEmail(),updatedRegistrationDate); 

		loginToSearch(randomUser_4);
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH2",false);

		/* Logout and login again and perform a search,
		 * no anniversary tokens should be awarded as they have been already awarded.  
		 */
		headerPage.signOut();
		loginToSearch(randomUser_4);
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH3",false);
		Common.sleepFor(2000);
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(anniversaryTokenAwardMessage));
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent(anniversaryTokenAwardMessage),String.valueOf(tokensUponAnniversary));		
		Assert.assertFalse(tokenHistoryPage.getEventCount(anniversaryTokenAwardMessage)>1);
	}

	@Test(description="Anniversary tokens should not be awarded if login is after seven days of anniversary date", testName = "20712")
	public void testAnniversary_Scenario5() throws SQLException, InterruptedException{
		loginToSearch(randomUser_5);
		headerPage.search("PCH",false);	
		headerPage.signOut();				
		DBUtils.expireDailySearch(randomUser_5.getEmail());
		String updatedRegistrationDate = Common.addYearToDate(currentDate, dateFormat, -1);
		updatedRegistrationDate=Common.addDaysToDate(updatedRegistrationDate, dateFormat, -8);		
		DBUtils.updateRegistrationDate(randomUser_5.getEmail(),updatedRegistrationDate);		
		loginToSearch(randomUser_5);
		homePage.closeUserLevelLightBox();
		headerPage.search("PCH2",false);
		homePage.goToTokenHistory();
		Assert.assertFalse(tokenHistoryPage.getActivityName().contains(anniversaryTokenAwardMessage));	
	}


}
