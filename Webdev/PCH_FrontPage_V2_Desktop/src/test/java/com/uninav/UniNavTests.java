package com.uninav;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class UniNavTests extends BaseClass {

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String config_article_name = "Uninav Settings";

	@testId(test_id = "34759,32537")
	@testCaseName(test_case_name = "B-57427 [  Integrate shared UniNav to Desktop ]")
	@Test(priority = 1, description = "Verify the UniNav property configuration", groups = { "DESKTOP",
			"TABLET" }, testName = "34796:B-57427 [  Integrate shared UniNav to Desktop ]")
	public void verify_uninav_settings() throws Exception {
		test_Method_details(1, "Verify the UniNav property configuration");

		String pch_property_name = "Test " + generateRandomString(5);
		String pch_property_extra_link_name = "Test " + generateRandomString(5);
		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla admin and modify the UniNav settings");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.add_pch_property_on_uninav("Test", pch_property_name, "http://www.google.com/", "Test Css",
					"Test Logo");
			admin_instance.add_pch_extra_link_on_uninav("Desktop/Tablet", pch_property_extra_link_name,
					"Test Extra Url");
			admin_instance.save_and_close_article();
			admin_instance.search_for_article(config_article_name);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_menus"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_update_cache_content"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Navigate to application homepage and verify the UniNav Property and Extra link");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(homepage_instance.get_pch_property_list_names().contains(pch_property_name));// debug
			assertTrue(homepage_instance.get_pch_extra_link_list_names().contains(pch_property_extra_link_name));
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Verify the UniNav Property and Extra link on all the Category pages");
			LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					invokeBrowser(url);
					assertTrue(homepage_instance.get_pch_property_list_names().contains(pch_property_name));
					assertTrue(
							homepage_instance.get_pch_extra_link_list_names().contains(pch_property_extra_link_name));
					break;
				}
			}
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Verify the UniNav Property and Extra link on all the Sub-Category pages");
			LinkedList<String> sub_category_url_list = homepage_instance.get_sub_catagory_menu_url_list();
			for (String url : sub_category_url_list) {
				invokeBrowser(url);
				assertTrue(homepage_instance.get_pch_property_list_names().contains(pch_property_name));
				assertTrue(homepage_instance.get_pch_extra_link_list_names().contains(pch_property_extra_link_name));
				break;
			}
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Verify the UniNav Property and Extra link on Video pages");
			homepage_instance.click_vidoes_menu();
			assertTrue(homepage_instance.get_pch_property_list_names().contains(pch_property_name));
			assertTrue(homepage_instance.get_pch_extra_link_list_names().contains(pch_property_extra_link_name));
			step_validator(5, true);

			// Step 6
			test_step_details(6, "Revert back the Joomla admin configuration");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.delete_added_uninav_field_section_by_label("PCH Property");
			admin_instance.delete_added_uninav_field_section_by_label("PCH Extra Link");
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(6, true);

			// Step 7
			test_step_details(7, "Navigate to application and verify the UniNav property and extra link");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(!homepage_instance.get_pch_property_list_names().contains(pch_property_name));
			assertTrue(!homepage_instance.get_pch_extra_link_list_names().contains(pch_property_extra_link_name));
			step_validator(7, true);
		} catch (Exception e) {
			log.error("Error in UniNav setting scenario: " + e.getMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.delete_added_uninav_field_section_by_label("PCH Property");
			admin_instance.delete_added_uninav_field_section_by_label("PCH Extra Link");
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			log.error("Finally block in UniNav setting scenario");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.delete_added_uninav_field_section_by_label("PCH Property");
			admin_instance.delete_added_uninav_field_section_by_label("PCH Extra Link");
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}
}
