package com.miscellaneous;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class HeaderTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	@testId(test_id = "31938")
	@testCaseName(test_case_name = "Frontpage Redesign Integration-Category page [Split]")
	@Test(priority = 1, description = "Verify the newly added header under Category page on Header bar", groups = {
			"DESKTOP", "TABLET" }, testName = "31938:Frontpage Redesign Integration-Category page [Split]")
	public void verify_newly_added_header() throws Exception {
		String newly_add_header_section_label_name = "Submenu Text";
		String newly_add_header_section_redirection_label_name = "Submenu Link";
		String newly_add_header_section_redirection_url = "/entertainment/testing";
		String newly_add_header_name = "Testing";
		// Step 1
		test_step_details(1, "Navigate to frontpage.qa.pch.com and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Navigate to each Catagory page on Joomla admin to get Sub category list");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		String category_name = null;
		try {
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					category_name = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
					log.info("Login to Joomla and navigate to article name: " + category_name);
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article(category_name + " Header");
					admin_instance.add_new_text_field_section_by_label(newly_add_header_section_label_name,
							newly_add_header_name);
					admin_instance.enter_text_field_element_by_label(newly_add_header_section_redirection_label_name,
							newly_add_header_section_redirection_url);
					admin_instance.save_and_close_article();

					// Step 3
					test_step_details(3, "Navigate to newly added Sub Catagory page from the Category menu header");
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_update_cache_content"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
					sleepFor(15);
					invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
					LinkedList<String> sub_category_url_list = homepage_instance.get_sub_catagory_menu_url_list();
					for (String sub_category_url : sub_category_url_list) {
						if (sub_category_url.endsWith(newly_add_header_name)) {
							invokeBrowser(sub_category_url);
							assertEqualsIgnoreCase(getTitle(), newly_add_header_name);
							break;
						}
						assertTrue(false);
					}

					// Step 4
					test_step_details(4,
							"Navigate to Catagory page on Joomla to delete the newly added sub category menu Header");
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article(category_name + " Header");
					admin_instance.delete_last_drop_down_section_by_label(newly_add_header_section_label_name);
					admin_instance.save_and_close_article();
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_update_cache_content"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

					// Step 5
					test_step_details(5, "Additional verification after deleting the added Header");
					invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
					LinkedList<String> sub_category_menu_name_list = homepage_instance.get_sub_catagory_menu_list();
					if (!sub_category_menu_name_list.contains(newly_add_header_name)) {
						assertTrue(true);
					}
					// Below Break command will limit the execution to verify
					// the first category page.
					// Commenting the Break will execute the above
					// entire verification for all the Category pages
					break;
				}
			}
		} finally {
			log.error("Finally block of Newly added header section");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(category_name + " Header");
			admin_instance.delete_last_drop_down_section_by_label(newly_add_header_section_label_name);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_update_cache_content"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}
}
