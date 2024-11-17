package com.pch.sw.tests.misc;

import org.testng.annotations.Test;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.GroupNames;

public class DifferentiateGuidedSearchOnActivityLog extends BaseTest {

	private SearchResultsPage searchResultPage;
	private HomePage homePage;
	private WebHeaderPage headerPage;
	RegistrationPage regPage;
	String keyword="books";

	@Test(description="TestID='28329', Verify Activity log for Guest user from browser's console",groups={GroupNames.Desktop,GroupNames.Regression},
			testName="DifferentiateGuidedSearchOnActivityLog_Desktop_Tablet")
	public void testActivityLogForGuestUserAndRegisteredUser(){	
		String ExpectedURL="http://search."+Environment.getEnvironment()+".pch.com/search?q="+keyword+"&cat=";
		homePage.load();
		headerPage.search(keyword,false);
		searchResultPage.validategBQBlingoID("1");
		searchResultPage.validateURL(ExpectedURL);
		
		headerPage.loginToSearch("kcheguri06@pchmail.com", "testing");	
		headerPage.search(keyword,false);
		searchResultPage.validategBQBlingoID("1");
		searchResultPage.validateURL(ExpectedURL);
	}
}
