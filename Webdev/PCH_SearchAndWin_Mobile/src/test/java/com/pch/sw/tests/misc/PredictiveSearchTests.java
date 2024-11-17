package com.pch.sw.tests.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;

//QC TC Configuration Number : 16164

public class PredictiveSearchTests extends BaseTest {
	AdminBasePage joomlaPage;
	HomePage homePage;
	WebHeaderPage headerPage;
	String searchKeyword = "Publisher";

	@Test(priority = 1, description = "Turning On the predicitive suggestions and verifying it")
	public void testPredictiveSearchSuggestions_ON() {
		// Pre-Condition
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("Position", "content-top");
		joomlaPage.gotoModule("PCH Search - Search Box (Home)", filter);
		joomlaPage.selectValuesForProperty("Support Predictive Text", "Yes");
		joomlaPage.clearCache();
		Common.sleepFor(18000);
		//Verifying the Predictions
		homePage.load();
		List<String> suggestions = headerPage.getPredictiveSearchSuggestions(searchKeyword);
		Assert.assertNotNull(suggestions);
		for (String suggestion : suggestions) {
			Assert.assertTrue(suggestion.toLowerCase().startsWith(searchKeyword.toLowerCase()),
					suggestion + " don't starts with" + searchKeyword);
		}
		CustomLogger.log("Predictive search is working fine");
	}

	@Test(priority = 2, description = "Turning Off the predicitive suggestions and verifying it")
	public void testPredictiveSearchSuggestions_OFF() {
		// Pre-Condition
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("Position", "content-top");
		joomlaPage.gotoModule("PCH Search - Search Box (Home)", filter);
		joomlaPage.selectValuesForProperty("Support Predictive Text", "No");
		joomlaPage.clearCache();
		Common.sleepFor(18000);
		// Verifying the Predictions
		homePage.load();
		List<String> suggestions = headerPage.getPredictiveSearchSuggestions(searchKeyword);
		Assert.assertNull(suggestions, "Suggestions are still displayed - " + suggestions);
		CustomLogger.log("Didn't found Suggestions, As We switch off Predictive Search in Joomla.");
	}

	@Test(priority = 3, description = "Turning On the predicitive suggestions on SERP search box and verifying it")
	public void testPredictiveSearchSuggestionsOnSERP_ONtopSearchBox() {
		// Pre-Condition
		joomlaPage.gotoModule("PCH Search - Search Box (Serp) Top");
		joomlaPage.selectValuesForProperty("Support Predictive Text", "Yes");
		joomlaPage.clearCache();
		Common.sleepFor(18000);
		// Verifying the Predictions
		homePage.load();
		headerPage.search(searchKeyword, false);
		List<String> suggestions = headerPage.getPredictiveSearchSuggestions(searchKeyword);
		Assert.assertNotNull(suggestions);

		for (String suggestion : suggestions) {
			Assert.assertTrue(suggestion.toLowerCase().startsWith(searchKeyword.toLowerCase()),
					suggestion + " don't starts with" + searchKeyword);
		}
	}

	@Test(priority = 4, description = "Turning Off the predicitive suggestions on SERP search box and verifying it")
	public void testPredictiveSearchSuggestionsOffSERP_ONtopSearchBox() {
		// Pre-Condition
		joomlaPage.gotoModule("PCH Search - Search Box (Serp) Top");
		joomlaPage.selectValuesForProperty("Support Predictive Text", "No");
		joomlaPage.clearCache();
		Common.sleepFor(18000);
		// Verifying the Predictions
		homePage.load();
		headerPage.search(searchKeyword, false);
		List<String> suggestions = headerPage.getPredictiveSearchSuggestions(searchKeyword);
		Assert.assertNull(suggestions, "Suggestions are still displayed - " + suggestions);
		CustomLogger.log("No Predections found for the text " + searchKeyword);
	}

}
