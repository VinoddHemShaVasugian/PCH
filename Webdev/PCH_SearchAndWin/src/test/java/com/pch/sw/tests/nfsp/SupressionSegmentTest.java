package com.pch.sw.tests.nfsp;

import org.testng.annotations.Test;

import com.pch.search.iwe.AllPlaysPage;
import com.pch.search.iwe.DevicePage;
import com.pch.search.iwe.GiveawayPage;
import com.pch.search.iwe.IWEBasePage;
import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.WinnersLightBox;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class SupressionSegmentTest extends BaseTest{
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
	SearchResultsPage serp;
	User randomUser_1, randomUser_2, randomUser_3, randomUser_4;
	String segment="SEARCH_EXIT_SUPPRESSION";
	
	@Test
	public void VerifyAlgoForNonsegmentedUser()
	{
		//Verify the NFSP source & segment for unrecognized user
		homePage.load();
		headerPage.search("shoes", false);
		CustomLogger.log("verifying Nfsp after making a search");
		serp.validateNFSP("source=");
		serp.validateSegment("segment=pchmeta4.pchmeta4");
		
		//Verify the NFSP source & segment for recognized user
		loginToSearch(randomUser_1);
		serp.validateNFSP("source=");
		serp.validateSegment("segment=pchmeta4.pchmeta4");
		}
	
	@Test
	public void VerifyAlgoForSegmentedUser()
	{
		//create a segmented user and verify the nfsp for the user
		centralServicesPage.createFullRegAndGetGMT(randomUser_1);
		centralServicesPage.navigateToSegmentMembershipPage();
		centralServicesPage.setSegmentMembership(randomUser_1.getEmail(), segment);
		loginToSearch(randomUser_1);
		headerPage.search("shoes", false);
		serp.validateNFSP("source=mx_d");
		serp.validateSegment("segment=pchmaxclick.pchmaxclick.desktop");
}
	
	
	@Test(groups = { GroupNames.Mobile,
			GroupNames.Regression })
     public void testDefaultMobileNFSP() {

		//Verify the NFSP source & segment for unrecognized user
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
	@Test(groups = { GroupNames.Mobile,
			GroupNames.Regression })
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
	
	     @Test(groups = { GroupNames.Tablet,GroupNames.Regression })
	public void testTabletNFSP() {
		//Verify the NFSP source & segment for unrecognized user
				homePage.load();
				headerPage.search("shoes", false);
				CustomLogger.log("verifying Nfsp after making a search");
				serp.validateNFSP("source=tab_t");
				serp.validateSegment("segment=pchtablet1.pchtablet1");
				
				//Verify the NFSP source & segment for recognized user
				loginToSearch(randomUser_1);
				serp.validateNFSP("source=tab_t");
				serp.validateSegment("segment=pchtablet1.pchtablet1");
	}
	
	@Test(groups = { GroupNames.Tablet,GroupNames.Regression })
	public void testTabletNFSPforsegmentuser() {
		//create a segmented user and verify the nfsp for the user
				centralServicesPage.createFullRegAndGetGMT(randomUser_1);
				centralServicesPage.navigateToSegmentMembershipPage();
				centralServicesPage.setSegmentMembership(randomUser_1.getEmail(), segment);
				loginToSearch(randomUser_1);
				homePage.closeOptinLigthBox();
				headerPage.search("shoes", false);
				serp.validateNFSP("source=mx_t");
				serp.validateSegment("segment=pchmaxclick.pchmaxclick.tablet");
		}
}
	


