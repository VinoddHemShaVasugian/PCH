package com.pch.sw.tests.guidedSearch;

import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class GuidedSearchNoOfElementsTests extends BaseTest {
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	GuidedSearchPage gsPage;
	User randomUser_1;
	
	
	/*
	 * minimum for layout 1&2 : 2
	 * minimum/max for layout 3&4 : 6/24 
	 * minimum/max count for layout 5 : 5/10
	*/
	
	
	@Test(groups={GroupNames.Desktop}, description="Un-recognized users # of terms validation tests - 29923.")
	public void unrec_NoofElements(){
		
		//Validate count of terms
		//validate layout displayed		
		CustomLogger.log("############### validating GS_No of terms & layput dispayed for Unrec-user ###############");
		CustomLogger.log("Validating # of elements for partilcuar GS - AUTOMATION-L1-min_terms_Unrec");
		
		CustomLogger.log("Validating with 0 Elements for Layout-1 - we should see 3 as of now");
		homePage.loadGuidedSearch("SWGSL1_Min");
		gsPage.validateTermsCount("L1", 3);
		
		CustomLogger.log("Validating with one term on Layout-1 - we should see two terms");
		homePage.loadGuidedSearch("SWGS1-L1-1");
		gsPage.validateTermsCount("L1", 2);
		
		CustomLogger.log("Validating minimum 2 Elements for Layout-1");
		homePage.loadGuidedSearch("KiranL1_terms_2");
		gsPage.validateTermsCount("L1", 2);
		
		/*CustomLogger.log("Validating maximum Elements for Layout-1");
		homePage.loadGuidedSearch("SWGSL1_terms_150");
		gsPage.validateTermsCount("L1", 150);*/
				
		CustomLogger.log("Validating with 0 Elements for Layout-2 - we should see 4 as of now");
		homePage.loadGuidedSearch("SWGSL2_0");
		gsPage.validateTermsCount("L2", 4);
		
		CustomLogger.log("Validating with 1 term on Layout-2 - we should see 2 terms");
		homePage.loadGuidedSearch("SWGSL2_01_Terms");
		gsPage.validateTermsCount("L2", 2);
		
		CustomLogger.log("Validating minimum Elements for Layout-2");
		homePage.loadGuidedSearch("SWGSVamsiL2_Enabled");
		gsPage.validateTermsCount("L2", 6);
		
		/*CustomLogger.log("Validating with 0 Elements for Layout-2 - we should see 4 as of now");
		homePage.loadGuidedSearch("SWGSL2_Max");
		gsPage.validateTermsCount("L2", 80);*/
		
		CustomLogger.log("Validating minimum Elements for Layout-3");
		homePage.loadGuidedSearch("SWGSL3_Default");
		gsPage.validateTermsCount("L3", 12);
		
		CustomLogger.log("Validating maximum Elements for Layout-3");
		homePage.loadGuidedSearch("SWGSL3_Max");
		gsPage.validateTermsCount("L3", 96);
			
		CustomLogger.log("Validating minimum Elements for Layout-4");
		homePage.loadGuidedSearch("AutomationL4_Desktop");
		gsPage.validateTermsCount("L4", 12);
		
		CustomLogger.log("Validating minimum Elements for Layout-5");
		homePage.loadGuidedSearch("Automation_L5_Desktop");
		gsPage.validateTermsCount("L5", 10);
		
		CustomLogger.log("Validating minimum Elements for Layout-6");
		homePage.loadGuidedSearch("Automation_L6_Desktop");
		gsPage.validateTermsCount("L6", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-7");
		homePage.loadGuidedSearch("AutomationL7_Desktop");
		gsPage.validateTermsCount("L7", 6);
		
		/*
		CustomLogger.log("Validating minimum Elements for Layout-8");
		homePage.loadGuidedSearch("SWGSL8");
		gsPage.validateTermsCount("L8", 8);
		
		CustomLogger.log("Validating minimum Elements for Layout-9");
		homePage.loadGuidedSearch("SWGSL9");
		gsPage.validateTermsCount("L9", 8);
		
		CustomLogger.log("Validating minimum Elements for Layout-10");
		homePage.loadGuidedSearch("SWGSL10");
		gsPage.validateTermsCount("L10", 8);*/
		
		/*CustomLogger.log("Validating maximum Elements for Layout-4");
		homePage.loadGuidedSearch("AUTOMATION-L4-max-terms-Unrec");
		gsPage.validateTermsCount("L4", 24);
		
		CustomLogger.log("Validating maximum Elements for Layout-5");
		homePage.loadGuidedSearch("Automation_L6_Desktop-L5-max_terms_Unrec");
		gsPage.validateTermsCount("L5", 10);*/
		
		CustomLogger.log("##########################################################################################");
	}
	
	@Test(groups={GroupNames.Desktop}, description = "Recognized users # of terms validation tests - 29923.")
	public void rec_NoofElements(){
		
		CustomLogger.log("############### validating GS_No of terms & layput dispayed for Rec-user ###############");
		CustomLogger.log("Validating # of elements for recognised user. . .");
		homePage.loadGuidedSearchPage();
		headerPage.loginToSearch("np03@pchmail.com", "testing");
		
		CustomLogger.log("Validating with 0 Elements for Layout-1 - we should see 4 as of now");
		homePage.loadGuidedSearch("SWGSL1_Min");
		gsPage.validateTermsCount("L1", 3);
		
		CustomLogger.log("Validating with one term on Layout-1 - we should see two terms");
		homePage.loadGuidedSearch("SWGS1-L1-1");
		gsPage.validateTermsCount("L1", 2);
		
		CustomLogger.log("Validating minimum 2 Elements for Layout-1");
		homePage.loadGuidedSearch("KiranL1_terms_2");
		gsPage.validateTermsCount("L1", 2);
		
		/*CustomLogger.log("Validating maximum Elements for Layout-1");
		homePage.loadGuidedSearch("SWGSL1_terms_150");
		gsPage.validateTermsCount("L1", 150);*/
				
		CustomLogger.log("Validating with 0 Elements for Layout-2 - we should see 4 as of now");
	     homePage.loadGuidedSearch("SWGSL2_0");
		gsPage.validateTermsCount("L2", 4);
		
	      CustomLogger.log("Validating with 1 term on Layout-2 - we should see 2 terms");
		homePage.loadGuidedSearch("SWGSL2_01_Terms");
	     gsPage.validateTermsCount("L2", 2);
		
		CustomLogger.log("Validating minimum Elements for Layout-2");
		homePage.loadGuidedSearch("SWGSVamsiL2_Enabled");
		gsPage.validateTermsCount("L2", 6);
		
		/*CustomLogger.log("Validating with 0 Elements for Layout-2 - we should see 4 as of now");
		homePage.loadGuidedSearch("SWGSL2_Max");
		gsPage.validateTermsCount("L2", 80);*/
		
		CustomLogger.log("Validating minimum Elements for Layout-3");
		homePage.loadGuidedSearch("SWGSL3_Default");
		gsPage.validateTermsCount("L3", 12);
		
		CustomLogger.log("Validating maximum Elements for Layout-3");
		homePage.loadGuidedSearch("SWGSL3_Max");
		gsPage.validateTermsCount("L3", 96);
		
		CustomLogger.log("Validating minimum Elements for Layout-4");
		homePage.loadGuidedSearch("AutomationL4_Desktop");
		gsPage.validateTermsCount("L4", 12);
		
		CustomLogger.log("Validating minimum Elements for Layout-5");
		homePage.loadGuidedSearch("Automation_L5_Desktop");
		gsPage.validateTermsCount("L5", 10);
		
		CustomLogger.log("Validating minimum Elements for Layout-6");
		homePage.loadGuidedSearch("Automation_L6_Desktop");
		gsPage.validateTermsCount("L6", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-7");
		homePage.loadGuidedSearch("AutomationL7_Desktop");
		gsPage.validateTermsCount("L7", 6);
		
		/*CustomLogger.log("Validating minimum Elements for Layout-8");
		homePage.loadGuidedSearch("SWGSL8");
		gsPage.validateTermsCount("L8", 8);
		
		CustomLogger.log("Validating minimum Elements for Layout-9");
		homePage.loadGuidedSearch("SWGSL9");
		gsPage.validateTermsCount("L9", 8);
		
		CustomLogger.log("Validating minimum Elements for Layout-10");
		homePage.loadGuidedSearch("SWGSL10");
		gsPage.validateTermsCount("L10", 8);*/
		
		CustomLogger.log("########################################################################################");
		
	}
	
	
	
}
