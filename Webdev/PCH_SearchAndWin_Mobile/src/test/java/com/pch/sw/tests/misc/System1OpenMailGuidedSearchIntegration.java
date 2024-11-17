package com.pch.sw.tests.misc;

import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.PromotionPage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.User;

public class System1OpenMailGuidedSearchIntegration extends BaseTest{
	GuidedSearchPage gsPage;
	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	private PromotionPage promotionPage;
	private SearchResultsPage serp;
	User randomUser_1, randomUser_2;
	
	//@Test(testName="System1OpenMailGuidedSearchIntegration",priority=1)
	public void VerifyGsTestPage(){
		joomlaPage.publishModule("MV Controller");
		joomlaPage.gotoModule("MV Controller");
	    Common.sleepFor(5000);
	   //setting up value 99% to Gstest and 1% to normal module");
		promotionPage.SelectgsTest("99");
		promotionPage.selectcontrol("1");
		promotionPage.TestcycleRandomNumber();
		joomlaPage.saveCloseAndClearCache();
		homePage.load();
		Common.sleepFor(5000);
		//Verify the Gs test page with system1 terms & the nfsp for the System1 terms
		promotionPage.GsTestPage();
		promotionPage.Systemterm("//iframe[contains(@src,'//y.sysduo.com/yframe')]","//ul[@class='list list--kw']/li[1]/a");
		serp.validateSegment("segment=swguidedtest1mobile.swguidedtest1mobile");
		//String  user_id =DBUtils.getUserIdFromEmail(randomUser_1.getEmail());
		//DBUtils.executeQueryAndVerifyValue("SELECT * FROM  `pch_sso_user_data` WHERE  `user` =" + user_id + "&& item = 'mvtest_mod179'","value").contains("1:c");
		joomlaPage.goToArticle("Guided Search Terms Only");
	    String Content= joomlaPage.Gstestcontent().getText();
	    String replaceText= Content;
	    joomlaPage.Gstestcontent().clear();
	    //Gs timeout by replacing the url in the admin
	    joomlaPage.Gstestcontent().sendKeys(replaceText.replaceAll("//lamtrigger.com/lam.js","//lamtrigger123.com/lam.js"));
	    joomlaPage.saveCloseAndClearCache();
	    homePage.load();
	    //Verify the Gstest page with Gs terms & the nfsp for the Gstest terms
	    promotionPage.Systemterm("//iframe[@class='guidedSearchFrame']","//article[@class='gs-v1']//li[1]/a");
	    serp.validateSegment("segment=guideddefaultmobile.guideddefaultmobile");
	    }
	//@Test(description="replacing with the correct content",priority=2)
	public void settingOriginalContent(){
		joomlaPage.goToArticle("Guided Search Terms Only");
		String Content= joomlaPage.Gstestcontent().getText();
		//replacing with the correct content
		 joomlaPage.Gstestcontent().clear();
		 joomlaPage.Gstestcontent().sendKeys(Content.replaceAll("//lamtrigger123.com/lam.js","//lamtrigger.com/lam.js"));
		 joomlaPage.saveCloseAndClearCache();
	}
	
   @Test(testName="Create Module to redirect Users server side [DTM]",priority=3)
	public void ServerSideRedirect(){
	    joomlaPage.gotoModule("MV Controller");
		//joomlaPage.publishModule("MV Controller");
		Common.sleepFor(5000);
		promotionPage.SelectgsTest("1");
		promotionPage.selectcontrol("99");
		promotionPage.TestcycleRandomNumber();
		joomlaPage.saveCloseAndClearCache();
		loginToSearch(randomUser_1);
		promotionPage.Systemterm("//iframe[@class='guidedSearchFrame']","//article[@class='gs-v1']//li[1]/a");
		serp.validateSegment("segment=pchmobile1.pchmobile1");
		String  user_id =DBUtils.getUserIdFromEmail(randomUser_1.getEmail());
		DBUtils.executeQueryAndVerifyValue("SELECT * FROM  `sandw_sso_user_data` WHERE  `user` =" + user_id + " AND item LIKE 'mvtest_mod%'","value").contains(":T1");
	    
	    }
	}




