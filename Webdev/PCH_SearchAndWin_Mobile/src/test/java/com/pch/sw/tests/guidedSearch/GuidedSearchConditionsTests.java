package com.pch.sw.tests.guidedSearch;

import java.util.Calendar;

import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.GroupNames;

public class GuidedSearchConditionsTests extends BaseTest {
	
	private HomePage homePage;
	private WebHeaderPage header;
	private GuidedSearchPage gs;
	
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for condition 'Gender=m or f'  - TestID-29926")
	public void gsConditionsGenderTest(){
		
		//No Segment User - kcheguri03@pchmail.com / testing gender=m
		homePage.load();
		header.loginToSearch("kcheguri03@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		
		//Load GS with the condition "gender=m" and validate
		homePage.loadGuidedSearch("AUTOMATION-Gender");
		gs.validateGs("L1-R-5E-40-gender=m");
		
		//Logout and login with a gender=f credentials and validate
		header.loginToSearchUsingUrl("kcheguri04@pchmail.com", "c1b60a49-0082-44ed-a36f-9404d2abc3a9");
		homePage.loadGuidedSearch("AUTOMATION-Gender");
		gs.validateGs("L1-RD-7E-44");
		
		//Validate a GS with gender=f
		homePage.loadGuidedSearch("AUTOMATION-GenderF");
		gs.validateGs("L1-R-3E-45");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for condition 'RegList'  - TestID-29926")
	public void gsConditionsRegListTest(){
		
		homePage.load();
		header.loginToSearch("kcheguri03@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		
		//User - kcheguri03@pchmail.com/testing; reglist=["BLINGO","FRONTPAGE","PCHCOM"];
		homePage.loadGuidedSearch("AUTOMATION-RegList");
		gs.validateGs("L1-RD-4E-59-RegList");
		
		//User - kcheguri06@pchmail.com/testing; reglist=["BLINGO","FRONTPAGE","LOTTO","PCHCOM"];
		header.loginToSearchUsingUrl("kcheguri06@pchmail.com", "c97f3a96-8b6c-4154-aeca-1e5372ab436c");
		homePage.loadGuidedSearch("AUTOMATION-RegList");
		gs.validateGs("L1-R-4E-58-RegList");
		
	}
	
	/* -- PII changes removed State value from PCHUSER library. This condition wont work anymore.
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for condition 'State' - TestID-29926")
	public void gsConditionsStateTest(){
		
		//No Segment User - kcheguri03@pchmail.com / testing state=NY
		homePage.load();
		header.loginToSearchUsingUrl("kcheguri63@pchmail.com", "04ad0ad1-2a3d-4032-9fb5-01d1cf6c9336");
		gs.validateGs("L1-RD-5E-2-DEFAULT");
		
		//Load GS with the condition "state=PA" and validate
		homePage.loadGuidedSearch("AUTOMATION-State");
		gs.validateGs("L1-RD-3E-67-State");
		
		//Logout and login with a user state=NY credentials and validate
		header.loginToSearchUsingUrl("kcheguri03@pchmail.com", "467ddfbd-5e68-4e6c-babe-692b7ad72a94");
		homePage.loadGuidedSearch("AUTOMATION-State");
		gs.validateGs("L1-R-6E-66-State");
		
	}
	*/
	
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for condition 'Level' - TestID-29926")
	public void gsConditionsLevelTest(){
		
		//User - kcheguri03@pchmail.com / testing level=emarald
		homePage.load();
		header.loginToSearch("testing0001@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		
		//Load GS with the user "level=emarald" and validate
		homePage.loadGuidedSearch("AUTOMATION-Level");
		gs.validateGs("L1-R-4E-68-Level");
		homePage.load();
		header.signOut();
		
		//Logout and login with a level= not emarald credentials and validate
		header.loginToSearchUsingUrl("kcheguri63@pchmail.com", "04ad0ad1-2a3d-4032-9fb5-01d1cf6c9336");
		Common.sleepFor(2000);
		homePage.loadGuidedSearch("AUTOMATION-Level");
		gs.validateGs("L1-RD-5E-70-Level");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for condition 'Age' - TestID-29926")
	public void gsConditionsAgeTest(){
		
		homePage.load();
		
		//Load GS with Unregistered User and validate
		homePage.loadGuidedSearch("AUTOMATION-Age");
		gs.validateGs("L1-RD-4E-72-AgeDefault");
		
		//Login as a registered user and validate Guided Search
		header.loginToSearch("kcheguri08@pchmail.com", "testing");
		//gs.validateGs("L1-RD-5E-2-DEFAULT");
		
		//Load GS with the condition "age between 20 & 30" and validate
		homePage.loadGuidedSearch("AUTOMATION-Age");
		gs.validateGs("L1-R-6E-71-Age");
		
		//Load GS with User not in ages between 20 & 30 and validate
		header.loginToSearchUsingUrl("kcheguri06@pchmail.com", "c97f3a96-8b6c-4154-aeca-1e5372ab436c");
		homePage.loadGuidedSearch("AUTOMATION-Age");
		gs.validateGs("L1-RD-4E-72-AgeDefault");
		
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
		gs.validateGs("L1-R-4E-76-Device-M");
		
		//Login as a registered user and validate 
		header.loginToSearch("kcheguri08@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		
		homePage.loadGuidedSearch("AUTOMATION-Device");
		gs.validateGs("L1-R-4E-76-Device-M");
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="GS validation tests for multiple conditions - TestID-29926")
	public void gsConditionsMultipleConditionsTest(){
		
		homePage.load();
		
		//Load GS with Unregistered User and validate
		homePage.loadGuidedSearch("AUTOMATION-AllConditions");
		gs.validateGs("L1-R-4E-78-AllConditions-Default");
		
		//Login as a registered user and validate 
		header.loginToSearch("kcheguri70@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		
		homePage.loadGuidedSearch("AUTOMATION-AllConditions");
		gs.validateGs("L1-R-4E-77-AllConditions");
		
	}
	
	
	
}
