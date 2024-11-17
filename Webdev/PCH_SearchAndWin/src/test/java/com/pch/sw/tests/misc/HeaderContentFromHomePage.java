package com.pch.sw.tests.misc;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.User;

public class HeaderContentFromHomePage extends BaseTest {
	
	private HomePage webBasePage;
	public RegistrationPage regpage;
	private WebHeaderPage webHeaderPage;
	private SearchResultsPage searchResultPage;
	private User randomUser_1;
	

	@Test(description="[1]RulesAndFacts_Desktop")
	public void rulesAndFactsGuestUser(){
		
		webBasePage.load("https://claim-int.qa.pch.com/claim/services/swssrules?deviceId=1");
		List<String> expectedOfficialRules =  webHeaderPage.getExpectedRulesFromOfficialsRulesPage();
		System.out.println(expectedOfficialRules);
		
		webBasePage.load();
		searchResultPage.searchAndGetCount("hair");
		CustomLogger.log("Verifying official rules content for Guest user");
		List<String> actulaSearchOfficialRules = webHeaderPage.getOfficialRulesPageContentSearch();
		System.out.println(actulaSearchOfficialRules);
		
		if(expectedOfficialRules.size()==actulaSearchOfficialRules.size()){
			for(int i=0;i<expectedOfficialRules.size();i++){
				CustomLogger.log("Expected : "+expectedOfficialRules.get(i)+" Actual : "+actulaSearchOfficialRules.get(i));
				Assert.assertTrue(expectedOfficialRules.get(i).contains(actulaSearchOfficialRules.get(i)));
				//CustomLogger.log(expectedOfficialRules.get(i)+" : "+actulaSearchOfficialRules.get(i));
			}
		}
		
	}
	
	@Test(description="[1]RulesAndFacts_Desktop")
	public void rulesAndFactsRecognizeduser(){
		
		webBasePage.load("https://claim-int.qa.pch.com/claim/services/swssrules?deviceId=1");
		List<String> expectedOfficialRules =  webHeaderPage.getExpectedRulesFromOfficialsRulesPage();
		CustomLogger.log("expectedOfficialRules:: " + expectedOfficialRules);
		
		webBasePage.load();
		loginToSearch(randomUser_1);
//		searchResultPage.searchAndGetCount("hair");
		CustomLogger.log("Verifying official rules for Recognized user");
		List<String> actulaSearchOfficialRules = webHeaderPage.getOfficialRulesPageContentSearch();
		CustomLogger.log("actulaSearchOfficialRules:: " + actulaSearchOfficialRules);
		
		if(expectedOfficialRules.size()==actulaSearchOfficialRules.size()){
			for(int i=0;i<expectedOfficialRules.size();i++){
				CustomLogger.log("Expected : "+expectedOfficialRules.get(i)+" Actual : "+actulaSearchOfficialRules.get(i));
				Assert.assertTrue(expectedOfficialRules.get(i).contains(actulaSearchOfficialRules.get(i)));
				//CustomLogger.log(expectedOfficialRules.get(i)+" : "+actulaSearchOfficialRules.get(i));
			}
		}else{
			CustomLogger.log("Please do check the missed Rule in one of the page..");
		}
		
	}
	
	@Test(description = "[1]SweepstakesFactsContentHomePage - 16556")
	public void sweepStakesFactsContentGuestUser(){
		
		String sweepStakesLiveUrl = "http://rules.pch.com/viewrulesfacts?nocss=1&type=searchfacts";
		
		//Capturing sweep stakes content from production site
		CustomLogger.log("Capturing sweep stakes content from production site");
		webBasePage.load(sweepStakesLiveUrl);
		List<String> expectedStakesContent = webHeaderPage.getExpectedSweepStakesContentSearch();
		
		boolean islogin = false;
		for(int cycle=0; cycle<2; cycle++){

			webBasePage.load();
			if(islogin){
				webBasePage.load();
				loginToSearch(randomUser_1);
				CustomLogger.log("Validatng sweepstakes content for Recognized user.");
			}else{
				CustomLogger.log("Validating sweepstakes content for Guest User.");
			}		
			
			//Capturing the sweep stakes content from current Site
			CustomLogger.log("Capturing the sweep stakes content from current Site");
			List<String> actualStakesContent = webHeaderPage.getSweepstakesPageContentSearch();
			
			CustomLogger.log(expectedStakesContent.size()+" : Expected -:- Actual : "+actualStakesContent.size());
			
			//Validating content of current site with production site
			CustomLogger.log("Validating content of current site with production site");
			if(expectedStakesContent.size()==actualStakesContent.size()){
				for(int i=0; i<expectedStakesContent.size(); i++){
					CustomLogger.log("Actual : "+actualStakesContent.get(i)+" Expected : "+expectedStakesContent.get(i));
					Assert.assertTrue(expectedStakesContent.get(i).contains(actualStakesContent.get(i)));
					CustomLogger.log("Actual : "+actualStakesContent.get(i)+" Expected : "+expectedStakesContent.get(i));
				}
			}else{
				CustomLogger.log("Please do check the missed info on the page..");
			}
			islogin=true;
		}		
		
	}
	
	@Test(description = "[1]SweepstakesFactsContentFromSERP - 16557")
	public void sweepStakesFactsContentSERP(){
		
		String sweepStakesLiveUrl = "http://rules.pch.com/viewrulesfacts?nocss=1&type=searchfacts";
		
		webBasePage.load(sweepStakesLiveUrl);
		
		//Capturing sweep stakes content from production site
		CustomLogger.log("Capturing sweep stakes content from production site");
		List<String> expectedStakesContent = webHeaderPage.getExpectedSweepStakesContentSearch();
		
		boolean islogin = false;
		for(int cycle=0; cycle<2; cycle++){

			webBasePage.load();
			if(islogin){
				webBasePage.load();
				loginToSearch(randomUser_1);
				CustomLogger.log("Validatng sweepstakes content for Recognized user.");
			}else{
				CustomLogger.log("Validating sweepstakes content for Guest User.");
			}
			
			//SCapturing the sweep stakes content from current Site
			CustomLogger.log("SCapturing the sweep stakes content from current Site");
			List<String> actualStakesContent = webHeaderPage.getSweepstakesPageContentSearch();
			
			CustomLogger.log(expectedStakesContent.size()+" : Expected -:- Actual : "+actualStakesContent.size());
			
			//Validating content of current site with production site
			CustomLogger.log("Validating content of current site with production site");
			if(expectedStakesContent.size()==actualStakesContent.size()){
				for(int i=0; i<expectedStakesContent.size(); i++){
					CustomLogger.log("Actual : "+actualStakesContent.get(i)+" Expected : "+expectedStakesContent.get(i));
					Assert.assertTrue(expectedStakesContent.get(i).contains(actualStakesContent.get(i)));
					CustomLogger.log("Actual : "+actualStakesContent.get(i)+" Expected : "+expectedStakesContent.get(i));
				}
			}else{
				CustomLogger.log("Please do check the missed info on the page..");
			}
			islogin=true;
		}		
		
	}

	
}
