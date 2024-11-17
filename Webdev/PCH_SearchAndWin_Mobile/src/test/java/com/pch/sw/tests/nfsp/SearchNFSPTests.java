package com.pch.sw.tests.nfsp;

import java.util.Enumeration;
import java.util.Properties;

import org.testng.annotations.Test;

import com.pch.search.iwe.AllPlaysPage;
import com.pch.search.iwe.DevicePage;
import com.pch.search.iwe.GiveawayPage;
import com.pch.search.iwe.IWEBasePage;
import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.WinnersLightBox;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class SearchNFSPTests extends BaseTest {
WebHeaderPage header;
	GuidedSearchPage gsPage;
	SearchResultsPage serp;
      IWEBasePage iweBasePage; //new IWEBasePage();
	DevicePage devicePage;
	GiveawayPage giveawayPage;
	AllPlaysPage allPlaysPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	WinnersLightBox winnersLightBox;
	CentralServicesPage centralServicesPage;
	AdminBasePage joomlaPage;
	User randomUser_1, randomUser_2, randomUser_3, randomUser_4;
	String segment="SEARCH_EXIT_SUPPRESSION";

	String searchKeyword = "shoes";

	@Test(groups = { GroupNames.Mobile,GroupNames.Regression},testName = "GuidedSearch-NFSP_Mobile-TestID-30267", description = "verify default nfsp for guest and registered user")
	public void testDefaultMobileNFSP() {

		// validate NFSP for unregistered user
		homePage.load();
		headerPage.search("shoes", false);
		CustomLogger.log("verifying Nfsp after making a search");
		serp.validateNFSP("source=def_m");
		serp.validateSegment("segment=pchmobile1.pchmobile1");
		
		//Verify the NFSP source & segment for recognized user
		headerPage.loginToSearch("np03@pchmail.com", "testing");
		serp.validateNFSP("source=def_m");
		serp.validateSegment("segment=pchmobile1.pchmobile1");
		}
	
	@Test(groups ={ GroupNames.Mobile,GroupNames.Regression},description = "verify nfsp values for all Mobile nfsp's")
	public void testMobileNFSP() {

		Properties pro = Common.getPropertiesFromFile("NfspMobile.properties");

		@SuppressWarnings("rawtypes")
		Enumeration em = pro.keys();
		while (em.hasMoreElements()) {
			// Get nfsp from the properties file
			String nfsp = (String) em.nextElement();
			CustomLogger.log("==============================================");
			CustomLogger.log("Validating " + nfsp + "=" + pro.get(nfsp));
			CustomLogger.log("==============================================");

			// Get Segment from the properties file
			String segment = (String) pro.get(nfsp);

			// Load SERP with nfsp and search term in a browser
			homePage.loadSerpWithNfsp(searchKeyword, nfsp);

			// Validate nfsp and segment values from browser console log
			header.validateNFSP(nfsp);
			header.validateSegment(segment);

			// Clear browser cookies for validating the next segment
			header.clearBrowserCookies();
			CustomLogger.log("==============================================");
		}
	}
	@Test(groups = { GroupNames.Mobile,GroupNames.Regression })
	public void testSegmentMobileNFSP() {

		//create a segmented user and verify the nfsp for the user
				centralServicesPage.createFullRegAndGetGMT(randomUser_2);
				centralServicesPage.navigateToSegmentMembershipPage();
				centralServicesPage.setSegmentMembership(randomUser_2.getEmail(), segment);
				homePage.load();
				headerPage.loginToSearch(randomUser_2.getEmail(), "testing");
				homePage.closeOptinLigthBox();
				headerPage.search("shoes", false);
		  	    serp.validateNFSP("source=mx_m");
				serp.validateSegment("segment=pchmaxclick.pchmaxclick.mobile");
					}
}
