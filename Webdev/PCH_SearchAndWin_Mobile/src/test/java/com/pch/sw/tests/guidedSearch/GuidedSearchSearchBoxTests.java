package com.pch.sw.tests.guidedSearch;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;

public class GuidedSearchSearchBoxTests extends BaseTest {
	
	private HomePage homePage;
	private WebHeaderPage header;
	private GuidedSearchPage gs;
	String searchKeyword="shoes";
	
	@Test(groups={GroupNames.Mobile}, description="Validate search box is displayed if enabled - TestID-29928")
	public void gsSearchBox_Enable(){
		
		// load default GS for unrecognized users and validate 
		homePage.load();
		/*gs.validateGs("L1-UD-6E-1-DEFAULT");
		
		homePage.loadGuidedSearch("AUTOMATION-L1");
		gs.validateGs("L1-U-4E-3");*/
		
		// Validate if both top and bottom search boxes are present
		gs.validateSearchBox("Y");
		
		// Login as a guest user and validate the above functionality
		homePage.load();
		header.loginToSearch("kcheguri03@pchmail.com", "testing");
		/*gs.validateGs("L1-RD-5E-2-DEFAULT");*/
		
		// Validate if both top and bottom search boxes are present
		gs.validateSearchBox("Y");
		
		
	}
	
	/*@Test(groups={GroupNames.Mobile}, description="Validate search box is not displayed if disabled - TestID-29928")
	public void gsSearchBox_Disable(){
		
		// load default GS for unrecognized users and validate 
		homePage.load();
		gs.validateGs("L1-UD-6E-1-DEFAULT");
		
		homePage.loadGuidedSearch("AUTOMATION-L2");
		gs.validateGs("L2-U-4E-6");
		
		// Validate if both top and bottom search boxes are not present
		gs.validateSearchBox("N");
		
		
	}*/
	
	@Test(groups={GroupNames.Mobile}, description="Validate if we are able to search using top and bottom search boxes - TestID-29928")
	public void gsSearchBox_Search(){
		
		// load default GS for unrecognized users and validate 
		homePage.load();
		/*gs.validateGs("L1-UD-6E-1-DEFAULT");*/
		
		// Enter any term in the top search box and search
		CustomLogger.log("Searching for the term in the top search box");
		gs.searchUsingTopSearchBox(searchKeyword);
		
		// Go to Home page and search using bottom search box
		homePage.load();
		CustomLogger.log("Searching for the term in the bottom search box");
		gs.searchUsingBottomSearchBox(searchKeyword);
		
	}
	
	@Test(groups={GroupNames.Mobile}, description="Top Search box - Validate if predictive search terms are shown as per the keyword provided - TestID -29929")
	public void testPredictiveSearchSuggestions_TopSearchBox_ON(){
		
		homePage.load();
		//gs.switchToFrame();
		
		// Validate predictive text functionality
		List<String> suggestions = header.getPredictiveSearchSuggestions(searchKeyword);
		Assert.assertNotNull(suggestions);
		for(String suggestion:suggestions){
			Assert.assertTrue(suggestion.toLowerCase().startsWith(searchKeyword.toLowerCase()),suggestion+" don't starts with" +searchKeyword);
		}
		CustomLogger.log("Predictive search is working fine");
	}
	
	@Test(groups={GroupNames.Mobile}, description="Bottom Search box - Validate if predictive search terms are shown as per the keyword provided - TestID-29929")
	public void testPredictiveSearchSuggestions_BottomSearchBox_ON(){
		homePage.load();
		//gs.switchToFrame();
		
		// Validate predictive text functionality
		List<String> suggestions = header.getPredictiveSearchSuggestionsForBottomSearchBox(searchKeyword);
		Assert.assertNotNull(suggestions);
		for(String suggestion:suggestions){
			Assert.assertTrue(suggestion.toLowerCase().startsWith(searchKeyword.toLowerCase()),suggestion+" don't starts with" +searchKeyword);
		}
		CustomLogger.log("Predictive search is working fine");
	}
	
	/*@Test(groups={GroupNames.Mobile}, description="Top Search box - Validate if predictive search terms are not shown as per the keyword provided - TestID-29929")
	public void testPredictiveSearchSuggestions_TopSearchBox_OFF(){
		
		// load default GS for unrecognized users and validate
		homePage.load();
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gs.validateGs("L1-UD-6E-1-DEFAULT");
		
		// Go to GS where predictive text functionality is disabled
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gs.validateGs("L3-U-6E-9");
		
		// Validate predictive text functionality
		List<String> suggestions = header.getPredictiveSearchSuggestions(searchKeyword);
		Assert.assertNull(suggestions,"Suggestions are still displayed - "+suggestions);
		CustomLogger.log("Didn't found Suggestions, As We switched off Predictive text in GS Admin.");
	}*/
	
	/*@Test(groups={GroupNames.Mobile}, description="Top Search box - Validate if predictive search terms are not shown as per the keyword provided - TestID-29929")
	public void testPredictiveSearchSuggestions_BottomSearchBox_OFF(){
		
		// load default GS for unrecognized users and validate
		homePage.load();
		homePage.loadGuidedSearch("AUTOMATION-L3");
		
		gs.validateGs("L1-UD-6E-1-DEFAULT");
		
		// Go to GS where predictive text functionality is disabled
		homePage.loadGuidedSearch("AUTOMATION-L3");
		gs.validateGs("L3-U-6E-9");
		
		// Validate predictive text functionality
		List<String> suggestions = header.getPredictiveSearchSuggestionsForBottomSearchBox(searchKeyword);
		Assert.assertNull(suggestions,"Suggestions are still displayed - "+suggestions);
		CustomLogger.log("Didn't found Suggestions, As We switched off Predictive text in GS Admin.");
	}*/
	
	
	
	
}
