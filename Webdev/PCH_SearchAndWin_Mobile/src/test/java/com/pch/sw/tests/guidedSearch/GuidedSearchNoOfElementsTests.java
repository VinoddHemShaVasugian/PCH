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
	
	
	@Test(groups={GroupNames.Mobile}, description="Un-recognized users # of terms validation tests - 29923.")
	public void unrec_NoofElements(){
		
		//Validate count of terms
		//validate layout displayed
		CustomLogger.log("############### validating GS_No of terms & layput dispayed for Unrec-user ###############");
		CustomLogger.log("Validating # of elements for partilcuar GS - AUTOMATION-L1-min_terms_Unrec");
		
		CustomLogger.log("Validating minimum Elements for Layout-1");
		homePage.loadGuidedSearch("AUTOMATION-L1-min_terms_Unrec");
		gsPage.validateTermsCount("L1", 2);
		
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gsPage.validateTermsCount("L1", 4);
		
		CustomLogger.log("Validating maximum Elements for Layout-1");
		homePage.loadGuidedSearch("AUTOMATION-L1-max-terms-Unrec");
		gsPage.validateTermsCount("L1", 18);
		
		CustomLogger.log("Validating minimum Elements for Layout-2");
		homePage.loadGuidedSearch("AUTOMATION-L2");
		gsPage.validateTermsCount("L2", 4);
		
		CustomLogger.log("Validating maximum Elements for Layout-2");
		homePage.loadGuidedSearch("AUTOMATION-L2-max-terms-Unrec");
		gsPage.validateTermsCount("L2", 24);
		
		CustomLogger.log("Validating minimum Elements for Layout-3");
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gsPage.validateTermsCount("L3", 6);
		
		CustomLogger.log("Validating maximum Elements for Layout-3");
		homePage.loadGuidedSearch("AUTOMATION-L3-max-terms-Unrec");
		gsPage.validateTermsCount("L3", 24);
		
		CustomLogger.log("Validating minimum Elements for Layout-4");
		homePage.loadGuidedSearch("AUTOMATION-L4");
		gsPage.validateTermsCount("L4", 6);
		
		CustomLogger.log("Validating maximum Elements for Layout-4");
		homePage.loadGuidedSearch("AUTOMATION-L4-max-terms-Unrec");
		gsPage.validateTermsCount("L4", 24);
		
		CustomLogger.log("Validating minimum Elements for Layout-5");
		homePage.loadGuidedSearch("AUTOMATION-L5");
		gsPage.validateTermsCount("L5", 5);
		
		CustomLogger.log("Validating maximum Elements for Layout-5");
		homePage.loadGuidedSearch("AUTOMATION-L5-max_terms_Unrec");
		gsPage.validateTermsCount("L5", 10);
		
		CustomLogger.log("Validating maximum Elements for Layout-6");
		homePage.loadGuidedSearch("AUTOMATION-L6");
		gsPage.validateTermsCount("L6", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-7");
		homePage.loadGuidedSearch("Automation-L7");
		gsPage.validateTermsCount("L7", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-8");
		homePage.loadGuidedSearch("AUTOMATION-L8");
		gsPage.validateTermsCount("L8", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-9");
		homePage.loadGuidedSearch("AUTOMATION-L9");
		gsPage.validateTermsCount("L9", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-10");
		homePage.loadGuidedSearch("AUTOMATION-L10");
		gsPage.validateTermsCount("L10", 6);
		
		CustomLogger.log("##########################################################################################");
	}
	
	@Test(groups={GroupNames.Mobile}, description = "Recognized users # of terms validation tests - 29923.")
	public void rec_NoofElements(){
		
		CustomLogger.log("############### validating GS_No of terms & layput dispayed for Rec-user ###############");
		CustomLogger.log("Validating # of elements for recognised user. . .");
		homePage.load();
		headerPage.loginToSearch("vkatam0001@pchmail.com", "testing");
		
		CustomLogger.log("Validating minimum Elements for Layout-1");
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gsPage.validateTermsCount("L1", 2);
		
		CustomLogger.log("Validating maximum Elements for Layout-1");
		homePage.loadGuidedSearch("AUTOMATION-L1-max-terms-rec");
		gsPage.validateTermsCount("L1", 18);
		
		CustomLogger.log("Validating minimum Elements for Layout-2");
		homePage.loadGuidedSearch("AUTOMATION-L02");
		gsPage.validateTermsCount("L2", 6);
		
		CustomLogger.log("Validating maximum Elements for Layout-2");
		homePage.loadGuidedSearch("AUTOMATION-L2-max-terms-rec");
		gsPage.validateTermsCount("L2", 24);
		
		CustomLogger.log("Validating minimum Elements for Layout-3");
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gsPage.validateTermsCount("L3", 6);
		
		CustomLogger.log("Validating maximum Elements for Layout-3");
		homePage.loadGuidedSearch("AUTOMATION-L3-max-terms-rec");
		gsPage.validateTermsCount("L3", 24);
		
		CustomLogger.log("Validating minimum Elements for Layout-4");
		homePage.loadGuidedSearch("AUTOMATION-L4");
		gsPage.validateTermsCount("L4", 6);
		
		CustomLogger.log("Validating maximum Elements for Layout-4");
		homePage.loadGuidedSearch("AUTOMATION-L4-max-terms-rec");
		gsPage.validateTermsCount("L4", 24);
		
		CustomLogger.log("Validating minimum Elements for Layout-5");
		homePage.loadGuidedSearch("AUTOMATION-L5");
		gsPage.validateTermsCount("L5", 5);
		
		CustomLogger.log("Validating maximum Elements for Layout-5");
		homePage.loadGuidedSearch("AUTOMATION-L5-max_terms_rec");
		gsPage.validateTermsCount("L5", 10);
		
		CustomLogger.log("Validating minimum Elements for Layout-6");
		homePage.loadGuidedSearch("AUTOMATION-L6");
		gsPage.validateTermsCount("L6", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-7");
		homePage.loadGuidedSearch("Automation-L7");
		gsPage.validateTermsCount("L7", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-8");
		homePage.loadGuidedSearch("AUTOMATION-L8");
		gsPage.validateTermsCount("L8", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-9");
		homePage.loadGuidedSearch("AUTOMATION-L9");
		gsPage.validateTermsCount("L9", 6);
		
		CustomLogger.log("Validating minimum Elements for Layout-10");
		homePage.loadGuidedSearch("AUTOMATION-L10");
		gsPage.validateTermsCount("L10", 6);
		
		CustomLogger.log("########################################################################################");
		
	}
	
}
