package com.miscellaneous;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.FooterPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class FooterConfigTests extends BaseClass {

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final FooterPage footer_instance = FooterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();

	@testId(test_id = "32119")
	@testCaseName(test_case_name = "[D][T] FP_Redesign - B-42004 Footer integration")
	@SuppressWarnings("unused")
	@Test(priority = 1, description = "Verify the Footer Menu Category configurations", groups = { "DESKTOP",
			"TABLET" }, testName = "32119:[D][T] FP_Redesign - B-42004 Footer integration")
	public void footer_menu_navigation() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the Footer menu from HomePage");
		homepage_instance.close_openx_banner();
		String current_url = getCurrentUrl();
		LinkedList<String> footer_category_urls = footer_instance.get_footer_menu_category_urls();
		List<WebElement> footer_menu = footer_instance.get_footer_menu();

		for (int count = 0; count < footer_menu.size(); count++) {
			moveToElement(footer_menu.get(count));
			footer_menu.get(count).click();
			assertEquals(getCurrentUrl(), footer_category_urls.get(count));
			invokeBrowser(current_url);
			homepage_instance.waitForGivenPageTitle("Frontpage", 10);
			footer_category_urls = footer_instance.get_footer_menu_category_urls();
			footer_menu = footer_instance.get_footer_menu();
			break;
		}

		// Step 3
		test_step_details(3, "Unpublish the Footer navigation menu and verify in home page");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("Videos Footer");
		admin_instance.unpublish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		boolean flag = true;
		footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			if (footer_menu.get(count).getText().trim().equalsIgnoreCase("Videos")) {
				flag = false;
				break;
			}
			flag = true;
		}
		assertTrue(flag);

		// Step 4
		test_step_details(4, "Publish the Footer navigation menu and verify in home page");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("Videos Footer");
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		footer_menu = footer_instance.get_footer_menu();

		for (int count = 0; count < footer_menu.size(); count++) {
			if (footer_menu.get(count).getText().trim().equalsIgnoreCase("Videos")) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		assertTrue(flag);
	}

	@testId(test_id = "32130")
	@testCaseName(test_case_name = "FPContentToMakeConfigurable--Footer")
	@Test(priority = 2, description = "Verify the newly created menu is on the Footer menu", groups = { "DESKTOP",
			"TABLET" })
	public void footer_menu_config() throws Exception {
		String test_footer_menu = "Test_Footer";
		// Step 1
		test_step_details(1, "Login to the Joomla admin and create a temperory menu item");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.click_newbutton();
		admin_instance.click_newitems("PCH Frontpage Menu");
		String menuname = admin_instance.save_new_menu(test_footer_menu, "- - Footer");
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Verify the added Menu item in the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		List<WebElement> footer_menu = footer_instance.get_footer_menu();
		boolean flag = true;
		for (int count = 0; count < footer_menu.size(); count++) {
			log.info(footer_menu.get(count).getText());
			if (footer_menu.get(count).getText().trim().equalsIgnoreCase(test_footer_menu)) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		assertTrue(flag);

		// Step 3
		test_step_details(3, "Delete the created Menu item");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(menuname);
		admin_instance.unpublish_article();
		admin_instance.click_firstchkbx();
		admin_instance.click_trashbutton();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 4
		test_step_details(4, "Verify the deleted Menu item in the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			if (footer_menu.get(count).getText().trim().equalsIgnoreCase(test_footer_menu)) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		assertTrue(flag);
	}
}
