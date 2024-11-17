package com.pch.sw.tests.guidedSearch;

import org.testng.annotations.Test;
import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.GroupNames;

public class GuidedSearchLogoTests extends BaseTest{	

	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;	
	GuidedSearchPage gsPage;	

	@Test(description="validate logo on guided search page for unrecgnized and recognized user",
			testName="GuidedSearch-LOGO_Mobile-TestID=29927",groups={GroupNames.Mobile})
	public void gsLogo_ForRecognizedAndUnRecognizedUser(){	

		//Load guided search for unrecognized user and validate logo
		homePage.load();		
		gsPage.validateLogoPresent();		
		/*homePage.loadGuidedSearch("AUTOMATION-L1-NoLogo-UnrecUser");		
		gsPage.validateNoLogoPresent();*/

		//Login to guided search and validate logo
		headerPage.loginToSearch("kcheguri06@pchmail.com", "testing");	
		/*homePage.loadGuidedSearch("AUTOMATION-L1-Logo-RecUser");	*/
		gsPage.validateLogoPresent();		
		/*homePage.loadGuidedSearch("AUTOMATION-L1-NoLogo-RecUser");		
		gsPage.validateNoLogoPresent();		*/
	}

}
