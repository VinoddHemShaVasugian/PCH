package com.pch.sw.tests.misc;

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

public class SuspiciousSearchTerm_Mobile extends BaseTest {
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
	String term1="3080";
    String term2="pch";
	
	
    @Test(groups={GroupNames.Mobile},description="verifying bigfish ad for an unrecognized users")
	public void BigFishadforunrecusers(){
		joomlaPage.goToArticle("SERP Header Bad Terms 3080");
		CustomLogger.log("Getting the terms from the article");
		String terms=joomlaPage.getTerms().getText();
		homePage.load();
		headerPage.search("term1");
		if(terms.contains("term1")){
		serp.validateHouseAd();
		 CustomLogger.log("Validating the house ads for an unrecognised users");
		}
    }
		@Test(groups={GroupNames.Mobile},description="verifying bigfish ad for a recognized users")
		public void BigFishadforrecusers(){
			joomlaPage.goToArticle("SERP Header Bad Terms 3080");
			CustomLogger.log("Getting the terms from the article");
			String terms=joomlaPage.getTerms().getText();
			homePage.load();
			loginToSearch(randomUser_1);
			headerPage.search("term2");
			if(terms.contains("term2")){
			serp.validateHouseAd();
	      CustomLogger.log("Validating the house ads for a recognised users");
			
		}
	
		}
    
	
	
}
