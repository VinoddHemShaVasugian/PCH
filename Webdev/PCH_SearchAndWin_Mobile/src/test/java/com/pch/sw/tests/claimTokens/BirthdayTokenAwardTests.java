package com.pch.sw.tests.claimTokens;

import java.awt.AWTException;
import java.sql.SQLException;
import java.util.Calendar;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.MyAccountPage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.TokenHistoryPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.User;

public class BirthdayTokenAwardTests extends BaseTest {
	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	MyAccountPage myAccountPage;
	TokenHistoryPage tokenHistoryPage;
	User randomUser_1,randomUser_2,randomUser_3,randomUser_4,randomUser_5;
	MyAccountPage macntPage;
	
	String latestActivityName;
	int tokensUponBirthDay = 10000;
	String dateFormat = "MMMM dd yyyy";
	String timeZone = "America/New_York";
	Calendar cal = Calendar.getInstance();
	String currentDate = Common.getCurrentDate(timeZone, dateFormat);	
	String birthdayTokenAwardMessage ="For Your Birthday on PCHSearch&Win";
	String searchtokenAwardMessage = "For Searching Today!";
	String firstSearchtokenAwardMessage = "For Your First Search of the Day!";
	String firstSeacrh = "PCH";
	String secondSearch = "Shoes";
	
	@Test(enabled=false)
	public void testBirthDayTokenConfiguration(){
		joomlaPage.goToArticle("Tokens / Birthday");
		Assert.assertEquals(joomlaPage.setTextProperty("Token Amount", 10000),"success");
	}
	
	@Test(/*dependsOnMethods="testBirthDayTokenConfiguration",*/testName = "20713", description="Birthday tokens should not be awarded before birthday but other tokens should be awarded, e.g. tomorrow", priority=1)
	public void testBirthdayTokens_Scenario1() throws SQLException, InterruptedException{
		
		// Set tomorrow as birthday.		 
		String birthDate = Common.addDaysToDate(currentDate, dateFormat, 1);		
		randomUser_1.setDob_Month(birthDate.split("\\s+")[0]);
		randomUser_1.setDob_Day(birthDate.split("\\s+")[1]);		
		
		loginToSearch(randomUser_1);
		headerPage.search(secondSearch,false);	
	
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(firstSearchtokenAwardMessage));
		Assert.assertFalse(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));	
		
	}
	
	@Test(/*dependsOnMethods="testBirthDayTokenConfiguration",*/testName = "20713", description="Birthday tokens should be awarded on Birthday along with other tokens")
	public void testBirthdayTokens_Scenario2() throws SQLException, InterruptedException{
				
		// Set today as birthday.		 
		String birthDate = currentDate;		
		randomUser_2.setDob_Month(birthDate.split("\\s+")[0]);
		randomUser_2.setDob_Day(birthDate.split("\\s+")[1]);
		
		loginToSearch(randomUser_2);
		headerPage.search(firstSeacrh,false);
		homePage.closeUserLevelLightBox();		
		homePage.goToTokenHistory();		
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(firstSearchtokenAwardMessage));
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));
				
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent(birthdayTokenAwardMessage),String.valueOf(tokensUponBirthDay));
		
		/* Logout and login again and perform a search,
		 * no birthday tokens should be awarded as they have been already awarded.  
		 */
		homePage.goToHomePage();
		homePage.closeUserLevelLightBox();
		headerPage.signOut();
		loginToSearch(randomUser_2);
		headerPage.search(secondSearch,false);
		homePage.closeUserLevelLightBox();
	
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));		
		Assert.assertFalse(tokenHistoryPage.getEventCount(birthdayTokenAwardMessage)>1);
	}
	
	
	@Test(/*dependsOnMethods="testBirthDayTokenConfiguration",*/testName = "20713", description="Birthday tokens should be awarded if Birthday was yesterday (along with other tokens)")
	public void testBirthdayTokens_Scenario3() throws SQLException, InterruptedException, AWTException{
		
		 // Set yesterday as birthday.		 
		String birthDate = Common.addDaysToDate(currentDate, dateFormat, -1);
		randomUser_3.setDob_Month(birthDate.split("\\s+")[0]);
		randomUser_3.setDob_Day(birthDate.split("\\s+")[1]);
		//randomUser_2.setDob_Year(birthDate.split("\\s+")[2]);
		
		
		loginToSearch(randomUser_3);	
		headerPage.search(firstSeacrh,false);
		homePage.goToTokenHistory();
		
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(firstSearchtokenAwardMessage));
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));					
	
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent(birthdayTokenAwardMessage),String.valueOf(tokensUponBirthDay));
		
		 /*Logout and login again and perform a search,
		 * no birthday tokens should be awarded as they have been already awarded.  
		 */
		homePage.goToHomePage();
		homePage.closeUserLevelLightBox();
		headerPage.signOut();		
		loginToSearch(randomUser_3);		
		headerPage.search(secondSearch,false);
		
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));		
		Assert.assertFalse(tokenHistoryPage.getEventCount(birthdayTokenAwardMessage)>1);
	}
	
	@Test(/*dependsOnMethods="testBirthDayTokenConfiguration",*/testName = "20713", description="Birthday tokens should be awarded if Birthday was within 7 days (along with other tokens)")
	public void testBirthdayTokens_Scenario4() throws SQLException, InterruptedException{
		
		 // Set yesterday as birthday.	 
		
		String birthDate = Common.addDaysToDate(currentDate, dateFormat, -7);
		randomUser_4.setDob_Month(birthDate.split("\\s+")[0]);
		randomUser_4.setDob_Day(birthDate.split("\\s+")[1]);
			
		loginToSearch(randomUser_4);
		headerPage.search(firstSeacrh,false);
		
		homePage.goToTokenHistory();	
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(firstSearchtokenAwardMessage));
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));		
				
	
		Assert.assertEquals(tokenHistoryPage.getTokenForEvent(birthdayTokenAwardMessage),String.valueOf(tokensUponBirthDay));
		
		 /*Logout and login again and perform a search,
		 * no birthday tokens should be awarded as they have been already awarded.  
		 */
		homePage.goToHomePage();
		homePage.closeUserLevelLightBox();
		headerPage.signOut();
		loginToSearch(randomUser_4);		
		headerPage.search(secondSearch,false);		
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));		
		Assert.assertFalse(tokenHistoryPage.getEventCount(birthdayTokenAwardMessage)>1);
	}
	
	@Test(/*dependsOnMethods="testBirthDayTokenConfiguration",*/testName = "20713", description="Birthday tokens should not be awarded if Birthday was before 7+ days (along with other tokens)")
	public void testBirthdayTokens_Scenario5() throws SQLException, InterruptedException{
		
		 // Set yesterday as birthday.	
		
		String birthDate = Common.addDaysToDate(currentDate, dateFormat, -8);
		randomUser_5.setDob_Month(birthDate.split("\\s+")[0]);
		randomUser_5.setDob_Day(birthDate.split("\\s+")[1]);
				
		loginToSearch(randomUser_5);
		headerPage.search(firstSeacrh,false);
	
		homePage.goToTokenHistory();
		Assert.assertTrue(tokenHistoryPage.getActivityName().contains(firstSearchtokenAwardMessage));
		Assert.assertFalse(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));	
		
		homePage.goToHomePage();
		homePage.closeUserLevelLightBox();
		headerPage.signOut();
		
		/*
		 * Update user's b'day
		 */
		loginToSearch(randomUser_5);
		homePage.closeUserLevelLightBox();		
		homePage.goToMyInfoPage(randomUser_5.getFirstname());
		
		birthDate = Common.addDaysToDate(birthDate, dateFormat, -1);		
		randomUser_1.setDob_Month(birthDate.split("\\s+")[0]);
		randomUser_1.setDob_Day(birthDate.split("\\s+")[1]);
		macntPage.updateUserInfo(randomUser_1);
		
		homePage.goToHomePage();
		headerPage.search(secondSearch,false);	
		homePage.goToTokenHistory();
		Assert.assertFalse(tokenHistoryPage.getActivityName().contains(birthdayTokenAwardMessage));
		
	}
	
	

}
