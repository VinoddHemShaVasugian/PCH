package com.pch.sw.tests.shopping;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class ControlShoppingResultsByDeviceTests extends BaseTest {
	AdminBasePage joomlaPage;
	HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	SearchResultsPage serp;
	User randomUser_1, randomUser_2;
	String searchKeyword = "shoes";

	@Test(enabled = false, description = "verify web and shop tab does not show on SERP if disabled from joomla")
	public void test_VerifyShopAndWebTab_Disabled() {
		// disable shopping
		configureShopping("0");

		// go to search page and do some search, verify web and shop tab does
		// not display
		homePage.load();
		headerPage.search("pch", false);
		Assert.assertFalse(serp.isLeftSideBarExists(), "Left side bar exists");

		// login to search page and do some search, verify web and shop tab does
		// not display
		loginToSearch(randomUser_1);
		headerPage.search("pch");
		Assert.assertFalse(serp.isLeftSideBarExists(), "Left side bar exists");

	}

	@Test(groups = { GroupNames.Regression, GroupNames.Mobile,
			GroupNames.Desktop }, description = "verify web and shop tab shows on SERP if enabled from joomla")
	public void test_VerifyShopAndWebTab_Enabled() {
		// enable shopping
		// configureShopping("2");

		// go to search page and do some search, verify web and shop tab display
		homePage.load();
		headerPage.search("pch", false);
		Assert.assertTrue(serp.isLeftSideBarExists(), "Left side bar exists");

		// login to search page and do some search, verify web and shop tab
		// display
		loginToSearch(randomUser_2);
		homePage.closeUserLevelLightBox();
		headerPage.search("pch", false);
		Assert.assertTrue(serp.isLeftSideBarExists(), "Left side bar exists");
	}

	/**
	 * This function will enable and disable shopping
	 * 
	 * @param value
	 */
	public void configureShopping(String value) {
		joomlaPage.gotoPCHConfigurationTab("Infospace");
		joomlaPage.updateInfospaceSettings("Shopping Enabled", value);
		joomlaPage.saveCloseAndClearCache();
	}
}
