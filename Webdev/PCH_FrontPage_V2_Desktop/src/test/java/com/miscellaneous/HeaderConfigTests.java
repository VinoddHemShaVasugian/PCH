package com.miscellaneous;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class HeaderConfigTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	@testId(test_id = "32128")
	@testCaseName(test_case_name = "FPContentToMakeConfigurable--Header")
	@Test(priority = 1, description = "Verify the Header Menu Category configurations", groups = { "DESKTOP",
			"TABLET" }, testName = "[D][T] FP_Redesign - B-42004 Footer integration")
	public void footer_menu_navigation() throws Exception {

		// Step 1
		test_step_details(1, "Unpublish the Header navigation menu and verify in home page");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("Videos Header");
		admin_instance.unpublish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		boolean flag = true;
		List<WebElement> header_menu = homepage_instance.getHeaderMenuList();
		for (int count = 0; count < header_menu.size(); count++) {
			if (header_menu.get(count).getText().trim().equalsIgnoreCase("videos")) {
				flag = false;
				break;
			}
			flag = true;
		}
		assertTrue(flag);

		// Step 2
		test_step_details(2, "Publish the header navigation menu and verify in home page");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article("Videos Header");
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		header_menu = homepage_instance.getHeaderMenuList();
		for (int count = 0; count < header_menu.size(); count++) {
			if (header_menu.get(count).getText().trim().equalsIgnoreCase("videos")) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		assertTrue(flag);
	}

	@testId(test_id = "32128")
	@testCaseName(test_case_name = "FPContentToMakeConfigurable--Header")
	@Test(priority = 2, description = "Verify the newly created menu is on the Header menu", groups = { "DESKTOP",
			"TABLET" })
	public void header_menu_config() throws Exception {
		// Step 1
		test_step_details(1, "Login to the Joomla admin and create a temperory Header item");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.click_newbutton();
		admin_instance.click_newitems("PCH Frontpage Menu");
		String menuname = admin_instance.save_new_menu("menutestheader", "- - Header");
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		sleepFor(15);

		// Step 2
		test_step_details(2, "Verify the added Header item in the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		List<WebElement> header_menu = homepage_instance.getHeaderMenuList();
		boolean flag = true;
		for (int count = 0; count < header_menu.size(); count++) {
			log.info(header_menu.get(count).getText());
			if (header_menu.get(count).getText().trim().equalsIgnoreCase("menutestheader")) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}
		assertTrue(flag);

		// Step 3
		test_step_details(3, "Delete the created Header item");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_articles(menuname);
		admin_instance.click_firstchkbx();
		admin_instance.click_trashbutton();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 4
		test_step_details(4, "Verify the deleted Header item in the application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		header_menu = homepage_instance.getHeaderMenuList();
		for (int count = 0; count < header_menu.size(); count++) {
			if (header_menu.get(count).getText().trim().equalsIgnoreCase("menutestheader")) {
				flag = false;
				break;
			} else {
				flag = true;
			}
		}
		assertTrue(flag);
	}
}
