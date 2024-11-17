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

public class GuidedSearchFooterTests extends BaseTest {
	
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;	
	GuidedSearchPage gsPage;
	User randomUser_1;
	
	@Test(groups={GroupNames.Desktop}, description = "Validating Footer part for unrec users - 30028")
	public void unrecFooterValidation(){
		
		CustomLogger.log("############### Validating footer part for UnRecognised users ###############");
		homePage.loadGuidedSearchPage();
		CustomLogger.log("Validting BBB on GS_Page");
		gsPage.clickBBBandVerifyTitle();
		homePage.load();
		CustomLogger.log("Validting eTruste on GS_Page");
		gsPage.clickeTrusteandVerifyTitle();
		CustomLogger.log("#############################################################################");
	}
	
	/*@Test(groups={GroupNames.Desktop}, description = "Validating Footer part for rec users - 30028")
	public void recFooterValidation(){
		
		CustomLogger.log("############### Validating footer part for Recognised users ###############");
		homePage.loadGuidedSearchPage();
		headerPage.loginToSearch("np02@pchmail.com", "testing");
		CustomLogger.log("Validting BBB on GS_Page");
		gsPage.clickBBBandVerifyTitle();
		homePage.load();
		CustomLogger.log("Validting eTruste on GS_Page");
		gsPage.clickeTrusteandVerifyTitle();
		CustomLogger.log("###########################################################################");
		
	}*/
}