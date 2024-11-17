package com.pch.sw.tests.misc;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.AboutSuperPrize;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;

public class SuperPrizeTests extends BaseTest {

	public SearchResultsPage searchResultPage;
	private HomePage webBasePage;
	private AboutSuperPrize aboutSuperPrize;
	private AdminBasePage joomlaPage;
	RegistrationPage regPage;
	private String articleName = "About SuperPrize®";
	private String externalURL = "http://rules.pch.com/viewrulesfacts?type=searchdisclosure&nocss=1";

	@BeforeMethod
	public void postCondtion() {
		joomlaPage.goToArticle(articleName);
		joomlaPage.setValue("External URL", externalURL);
		joomlaPage.saveCloseAndClearCache();
		joomlaPage.publishArticle(articleName);
	}

	@Test(description = "validating About super prize [D] by publishng/unpublishng article in joomla  - 21933")
	public void aboutSuperPrizeDisclosure() {

		// SuperPrize disclosure content should be same as on the rules server
		// link provided below.
		// http://rules.pch.com/viewrulesfacts?type=searchdisclosure&nocss=1
		List<String> expectedContent = aboutSuperPrize.contentOfValuesAndFactsPage();

		// Login to S&W Joomla and open article 'About SuperPrize®' Change the
		// status of the article to Unpublished.
		joomlaPage.unpublishArticle(articleName);
		joomlaPage.clearCache();

		// Clear browser cache and navigate to the url.
		// http://search.qa.pch.com/aboutsuperprize
		webBasePage.load("http://search." + Environment.getEnvironment() + ".pch.com/aboutsuperprize");
		webBasePage.waitForElementInVisible(aboutSuperPrize.aboutSuperPrizeContentByValue(), 20);

		// About SuperPrize page should be blank without any errors.
		Assert.assertFalse(aboutSuperPrize.aboutSuperPrizeContentCount() > 0);

		// Login to S&W Joomla and open article 'About SuperPrize®' Change the
		// status of the article to Published.
		joomlaPage.publishArticle(articleName);
		joomlaPage.clearCache();

		// Navigating to about SuperPrize page and validating PCH Content as
		// provided in Joomla article.
		webBasePage.load("http://search." + Environment.getEnvironment() + ".pch.com/aboutsuperprize");
		webBasePage.waitForElementVisible(aboutSuperPrize.aboutSuperPrizeContentByValue(), 20);
		Assert.assertTrue(aboutSuperPrize.aboutSuperPrizeContent().isDisplayed());

		// Getting visible text from the page
		String pageContent = webBasePage.getVisbileTextInPage();
		CustomLogger.log("Expected content Array : " + expectedContent);

		// refresh the page to get the give away number. - here we mentioned the
		// count to refresh
		if (!(aboutSuperPrize.giveAwayNumberCount() > 0)) {
			aboutSuperPrize.waitTillGravitypresent(5);
		}

		// validating Give away number in AboutSuperPrize page
		CustomLogger.log("validating Give away number in AboutSuperPrize page");
		Assert.assertTrue(aboutSuperPrize.giveAwayNumber().isDisplayed());

		for (int i = 0; i < expectedContent.size(); i++) {
			Assert.assertTrue(pageContent.contains(expectedContent.get(i)),
					"Didn't find " + expectedContent.get(i) + " in the page");
			CustomLogger.log("Found " + expectedContent.get(i) + " in About Super Prize page..");
		}
		// Login to S&W Joomla and open article 'About SuperPrize®' Delete the
		// url from the 'Content External URL' field and save.
		joomlaPage.goToArticle(articleName);
		joomlaPage.setValue("External URL", "");
		joomlaPage.saveCloseAndClearCache();
		joomlaPage.clearCache();

		// SuperPrize disclosure content should not be present on the page as we
		// cleared the External link in Joomla article.
		webBasePage.load("http://search." + Environment.getEnvironment() + ".pch.com/aboutsuperprize");
		webBasePage.waitForElementInVisible(aboutSuperPrize.aboutSuperPrizeContentByValue(), 20);
		Assert.assertFalse(aboutSuperPrize.giveAwayNumberCount() > 0);

		// Login to S&W Joomla and open article 'About SuperPrize®' Update the
		// 'Content External URL' and save.
		joomlaPage.goToArticle(articleName);
		joomlaPage.setValue("External URL", externalURL);
		joomlaPage.saveCloseAndClearCache();
		joomlaPage.clearCache();

		// Scroll to the bottom of the page and validate the SuperPrize
		// disclosure details with Giveaway number.
		webBasePage.load("http://search." + Environment.getEnvironment() + ".pch.com/aboutsuperprize");
		webBasePage.waitForElementVisible(aboutSuperPrize.aboutSuperPrizeContentByValue(), 20);
		webBasePage.waitForElementVisible(aboutSuperPrize.giveAwayNumberByContent(), 20);
		aboutSuperPrize.giveAwayNumber().isDisplayed();
	}
}
