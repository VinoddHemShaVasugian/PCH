package com.pch.sw.tests.misc;


import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.PromotionPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.GroupNames;

public class CrossPromoTests extends BaseTest {
	
	private GuidedSearchPage gsPage;
	private AdminBasePage joomlaPage;
	private HomePage webBasePage;
	private PromotionPage promotionPage;
	private SearchResultsPage serp;
	private String ArticleName = "Guilded Search Promotion";
    
    @Test( testName = "48526 - S&W Guided Search cross-promo- mobile path [M]",description="Guided search promotion of lotto")
	public void testCrossPromolotto() {

		gsPage.driver.maximizeWindow();
		joomlaPage.publishArticle(ArticleName);
		CustomLogger.log("Guided Search Promotion article is published");
		gsPage.driver.resizeWindow(375, 667);
		webBasePage.load();
		 String LottoPromotionalUrl="guidedsearchpromotion?returnUrl=http://lotto."+ Environment.getEnvironment()+".pch.com";
	      webBasePage.loadCrossPromotion(LottoPromotionalUrl);
		promotionPage.validatePromotionPage();
		CustomLogger.log("Making first search of the day with Guest user..");
		if(Environment.getBrowserType().equalsIgnoreCase("android")){
			CustomLogger.log( "validate the Gs page with openx and gs terms and skip button");
			serp.validateShoppingResulstsPageFromCustomPromotion();
		    gsPage.searchUsingTopSearchBox("shoes");
		    serp.Verify_NewTab_And_Close("/search?q=shoes");
			serp.validateReturnURl("lotto");
			serp.validateSegment("segment=pchmobile1.pchmobile1");
			webBasePage.loadCrossPromotion(LottoPromotionalUrl);
	        serp.Gsterm();
	        serp.validateSegment("segment=searchmobilepath");
		    serp.Verify_NewTab_And_Close("&guided=true&nsfp=swguidedmpath");
		}
		else if(Environment.getBrowserType().equalsIgnoreCase("iphone")){
			CustomLogger.log( "validate the Gs page with openx and gs terms and skip button");
			serp.validateShoppingResulstsPageFromCustomPromotion();
			CustomLogger.log("making a search and validating continue button on serp page ");
			gsPage.searchUsingTopSearchBox("shoes");
			serp.ValidateContinueButton();
			CustomLogger.log("Validating the nfsp segment");
			serp.validateSegment("segment=pchmobile1.pchmobile1");
			serp.continue_crossPromotion().click();
			serp.validateReturnURl("lotto");
			webBasePage.loadCrossPromotion(LottoPromotionalUrl);
		    serp.Gsterm();
	        serp.validateSegment("segment=searchmobilepath.searchmobilepath");
		    serp.Verify_NewTab_And_Close("&guided=true&nsfp=swguidedmpath");
		}
	}
	

}
