package com.pch.sw.tests.guidedSearch;


import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.GroupNames;


public class GuidedSearchNFSPTests extends BaseTest{
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;	
	GuidedSearchPage gsPage;
	SearchResultsPage serp;

	@Test(groups={GroupNames.Mobile},testName="GuidedSearch-NFSP_Mobile-TestID-30267", description="verify default nfsp for guest and registered user")
	public void testDefaultNFSP_ForGuestAndRegisteredUser(){

		//validate NFSP for unregistered user
		homePage.load();		
		headerPage.search("pch",false);		
		serp.validateNFSP("source=def_m");
		serp.validateSegment("segment=pchmobile1.pchmobile1");

		//validate NFSP for registered user
		headerPage.loginToSearch("kcheguri06@pchmail.com", "testing");
		headerPage.search("pch",false);		
		serp.validateNFSP("source=def_m");
		serp.validateSegment("segment=pchmobile1.pchmobile1");
	}

	@Test(groups={GroupNames.Mobile},testName="GuidedSearch-NFSP_Mobile-TestID-30267", description="Navigate to any created guided search and validate nfsp for guest and registered user")
	public void testConfiguredNFSP_ForGuestAndRegisteredUser(){		

		//validate NFSP for unregistered user
		homePage.loadGuidedSearch("AUTOMATION-L1-NoLogo-UnrecUser");
		gsPage.clickTerm("Desserts");
		serp.validateNFSP("source=guided_m");	
		serp.validateSegment("segment=searchguidedmobile.searchguidedmobile");		

		homePage.goToHomePage();
		headerPage.search("pch",false);		
		serp.validateNFSP("source=guided_m");
		serp.validateSegment("segment=searchguidedmobile.searchguidedmobile");

		//validate NFSP for registered user
		headerPage.loginToSearch("kcheguri06@pchmail.com", "testing");
		homePage.loadGuidedSearch("AUTOMATION-L1-Logo-RecUser");
		gsPage.clickTerm("Desserts");
		serp.validateNFSP("source=guided_m");	
		serp.validateSegment("segment=searchguidedmobile.searchguidedmobile");		

		homePage.goToHomePage();
		headerPage.search("pch",false);		
		serp.validateNFSP("source=guided_m");
		serp.validateSegment("segment=searchguidedmobile.searchguidedmobile");
	}

	@Test(groups={GroupNames.Mobile},testName="GuidedSearch-NFSP_Mobile-TestID-30267", description="Update nfsp field of any guided search and verify nfsp for guest and registered user")
	public void testUpdatedNFSP_ForGuestAndRegisteredUser(){	
		
		//validate NFSP for unregistered user
		homePage.loadGuidedSearch("AUTOMATION-L1-NFSP");
		gsPage.clickTerm("Desserts");
		serp.validateNFSP("source=acq_m");	
		serp.validateSegment("segment=pchmobile1.pchmobile1.acqexit");

		//validate NFSP for registered user
		headerPage.loginToSearch("kcheguri06@pchmail.com", "testing");
		homePage.loadGuidedSearch("AUTOMATION-L1-NFSP");
		gsPage.clickTerm("Desserts");
		serp.validateNFSP("source=acq_m");	
		serp.validateSegment("segment=pchmobile1.pchmobile1.acqexit");

	}
}
