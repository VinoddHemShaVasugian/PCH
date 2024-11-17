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
			testName="GuidedSearch-LOGO_Mobile-TestID=29927",groups={GroupNames.Desktop})
	public void gsLogo_ForRecognizedAndUnRecognizedUser(){	

		//Load guided search for unrecognized user and validate logo
		homePage.loadGuidedSearch("SWGSVamsiL2_Enabled");		
		gsPage.validateLogoPresent();
		homePage.loadGuidedSearch("SWGSVamsiL2Disabled");
		gsPage.validateLogoPresent();

		//Login to guided search and validate logo
		headerPage.loginToSearch("kcheguri06@pchmail.com", "testing");
		homePage.loadGuidedSearch("SWGSVamsiL2_Enabled");
		gsPage.validateLogoPresent();
		homePage.loadGuidedSearch("SWGSVamsiL2Disabled");
		gsPage.validateLogoPresent();
	}

}
