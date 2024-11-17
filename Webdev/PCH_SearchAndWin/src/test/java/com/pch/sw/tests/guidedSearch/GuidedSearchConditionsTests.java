package com.pch.sw.tests.guidedSearch;

import java.util.Calendar;

import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.GroupNames;

public class GuidedSearchConditionsTests extends BaseTest {
	
	private HomePage homePage;
	private WebHeaderPage header;
	private GuidedSearchPage gs;
	
	@Test(groups={GroupNames.Desktop}, description="GS validation tests for condition 'Gender=m or f'  - TestID-29926")
	public void gsConditionsGenderTest(){
		
		//No Segment User - kcheguri03@pchmail.com / testing gender=m
		homePage.load();
		header.loginToSearch("kcheguri03@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		
		//Load GS with the condition "gender=m" and validate
		homePage.loadGuidedSearch("DAUTOMATION-GenderM");//AUTOMATION-GenderM_D
		gs.validateGs("L6-6E-213-gender=m");
		
		//Logout and login with a gender=f credentials and validate
		/*header.loginToSearchUsingUrl("kcheguri04@pchmail.com", "c1b60a49-0082-44ed-a36f-9404d2abc3a9");
		homePage.loadGuidedSearch("AUTOMATION-Gender");
		gs.validateGs("L1-RD-7E-44");*/
		
		//Validate a GS with gender=f
		homePage.load();
		header.signOut();
		header.loginToSearch("np04@pchmail.com", "testing");
		homePage.loadGuidedSearch("AUTOMATION-GenderF");
		gs.validateGs("L1-R-3E-45-gender=f");
		
	}
	
	/*@Test(groups={GroupNames.Mobile}, description="GS validation tests for condition 'RegList'  - TestID-29926")
	public void gsConditionsRegListTest(){
		
		homePage.load();
		header.loginToSearch("kcheguri03@pchmail.com", "testing");
		gs.validateGs("L1-RD-5E-2-DEFAULT");
		
		//User - kcheguri03@pchmail.com/testing; reglist=["BLINGO","FRONTPAGE","PCHCOM"];
		homePage.loadGuidedSearch("AUTOMATION-RegList");
		gs.validateGs("L1-RD-4E-59-RegList");
		
		//User - kcheguri06@pchmail.com/testing; reglist=["BLINGO","FRONTPAGE","LOTTO","PCHCOM"];
		header.loginToSearchUsingUrl("kcheguri06@pchmail.com", "c97f3a96-8b6c-4154-aeca-1e5372ab436c");
		homePage.loadGuidedSearch("AUTOMATION-RegList");
		gs.validateGs("L1-R-4E-58-RegList");
		
	}*/
	
	
	/*Commented this TC,	As of the info from Dev:
	 * state is not working anymore, with all PII stuff, the state was removed from PCHUSER object and we no longer have access to it*/
	/*@Test(groups={GroupNames.Desktop}, description="GS validation tests for condition 'State' - TestID-29926")
	public void gsConditionsStateTest(){
		
		//No Segment User - kcheguri03@pchmail.com / testing state=NY
		homePage.load();
		header.loginToSearch("vkatam0001@pchmail.com", "testing");
		
		//Load GS with the condition "state=PA" and validate
		homePage.loadGuidedSearch("DAUTOMATION-StateNY");
		gs.validateGs("L6-6E-210-State-NY");
		
		homePage.load();
				
		//Logout and login with a user state=PA credentials and validate
		header.signOut();
		header.loginToSearch("kcheguri63@pchmail.com", "testing");
		homePage.loadGuidedSearch("DAUTOMATION-StatePA");
		gs.validateGs("L6-6E-199-state-PA");	
	}*/
	
	@Test(groups={GroupNames.Desktop}, description="GS validation tests for condition 'Level' - TestID-29926")
	public void gsConditionsLevelTest(){
		
		//User - kcheguri03@pchmail.com / testing level=emarald
		homePage.load();
		header.loginToSearch("np04@pchmail.com", "testing");
		homePage.loadGuidedSearch("DAUTOMATION-LevelG");
		gs.validateGs("L7-6E-200-Level-Gold");
		
		//Logout and login with a level= silver credentials and validate
		homePage.load();
		header.signOut();
		header.loginToSearch("vkatam0003@pchmail.com", "testing");
		homePage.loadGuidedSearch("DAUTOMATION-LevelS");
		gs.validateGs("L5-5E-201-Level-Silver");

	}
	
	@Test(groups={GroupNames.Desktop}, description="GS validation tests for condition 'Age' - TestID-29926")
	public void gsConditionsAgeTest(){
		
		homePage.load();
		
		//Load GS with Unregistered User and validate
		homePage.loadGuidedSearch("AUTOMATION-Age");
		gs.validateGs("L1-RD-4E-72-Age");
		
		homePage.load();
		
		//Login as a registered user and Load GS with the condition "age between 20 & 30" and validate
		header.loginToSearch("kcheguri08@pchmail.com", "testing");
		homePage.loadGuidedSearch("AUTOMATION-Age");
		gs.validateGs("L1-R-6E-71-Age");
		
		//Load GS with User not in ages between 20 & 30 and validate
		header.loginToSearchUsingUrl("kcheguri06@pchmail.com", "c97f3a96-8b6c-4154-aeca-1e5372ab436c");
		homePage.loadGuidedSearch("AUTOMATION-Age");
		gs.validateGs("L1-RD-4E-72-Age");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for condition 'Day' - TestID-29926")
	public void gsConditionsDayTest(){
		
		homePage.load();
		//Guest User - Load GS for specific day of the week and validate
		homePage.loadGuidedSearch("AUTOMATION-Day");
		
		// Get Day and validate
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		
		if(day==1 || day==3 || day==5 || day==7){
			gs.validateGs("L1-RD-4E-74-DayTUE-THU-SAT-SUN");
		}else{
			gs.validateGs("L1-R-4E-73-DayMON-WED-FRI");
		}
		
		homePage.loadGuidedSearch("AUTOMATION-Day");
		
		//Registered User - Load GS for specific day of the week and validate
		header.loginToSearch("kcheguri08@pchmail.com", "testing");
		homePage.loadGuidedSearch("AUTOMATION-Day");
		
		if(day==1 || day==3 || day==5 || day==7){
			gs.validateGs("L1-RD-4E-74-DayTUE-THU-SAT-SUN");
		}else{
			gs.validateGs("L1-R-4E-73-DayMON-WED-FRI");
		}
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for condition 'Device' - TestID-29926")
	public void gsConditionsDeviceTest(){
		
		homePage.load();
		
		//Load GS with Unregistered User and validate
		homePage.loadGuidedSearch("AUTOMATION-Device");
		gs.validateGs("L1-R-4E-75-Device-D-T");
		
		homePage.load();
		
		//Login as a registered user and validate 
		header.loginToSearch("kcheguri08@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		homePage.loadGuidedSearch("AUTOMATION-Device");
		gs.validateGs("L1-R-4E-75-Device-D-T");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for multiple conditions - TestID-29926")
	public void gsConditionsMultipleConditionsTest(){
		
		homePage.load();
		
		//Load GS with Unregistered User and validate
		homePage.loadGuidedSearch("AUTOMATION-AllConditions");
		gs.validateGs("L1-R-4E-78-AllConditions-Default");
		
		homePage.load();
		
		//Login as a registered user and validate 
		header.loginToSearch("kcheguri70@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		
		homePage.loadGuidedSearch("AUTOMATION-AllConditions");
		gs.validateGs("L1-R-4E-78-AllConditions-Default");
		
	}
	
	
	
}
