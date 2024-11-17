package com.miscellaneous;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.CategoryPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ErrorHandlingTests extends BaseClass {

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final CategoryPage category_instance = CategoryPage.getInstance();
	private final String config_article_name = "Config-Frontpage";

	@testId(test_id = "RT-04223")
	@testCaseName(test_case_name = "[D/T/M] Frontpage Error Handling Improvement")
	@Test(priority = 1, description = "Verify the Error Hadnling message", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04223:[D/T/M] Frontpage Error Handling Improvement")
	public void verify_error_msg_on_fpcontent_down() throws Exception {

		String original_fpcontent_api_url = null;
		String original_fpcontent_error_msg = null;
		String updated_fpcontent_api_url = generateRandomString(10);
		String updated_fpcontent_error_msg = generateRandomString(30);
		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla admin and modify the FP content API url and message");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			original_fpcontent_api_url = admin_instance.get_input_field_element_by_key_name("frontpage_api")
					.getAttribute("value");
			original_fpcontent_error_msg = admin_instance
					.get_input_field_element_by_key_name("error_message_fpcontent_failure").getAttribute("value");
			admin_instance.enter_input_field_element_by_key_name("frontpage_api", updated_fpcontent_api_url);
			admin_instance.enter_input_field_element_by_key_name("error_message_fpcontent_failure",
					updated_fpcontent_error_msg);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 2
			test_step_details(2, "Navigate to application homepage and verify the FP Content error message");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(), updated_fpcontent_error_msg);
			assertTrue(homepage_instance.verify_openx_banner());
			assertTrue(homepage_instance.verify_right_rail_gpt_ad_1());
			assertTrue(homepage_instance.verify_right_rail_gpt_ad_2());

			// Step 3
			test_step_details(3, "Verify the FP Content error message on all the Category pages");
			LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					invokeBrowser(url);
					assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(), updated_fpcontent_error_msg);
					assertTrue(homepage_instance.verify_openx_banner());
					assertTrue(homepage_instance.verify_right_rail_gpt_ad_1());
					assertTrue(homepage_instance.verify_right_rail_gpt_ad_2());
					break;
				}
			}

			// Step 4
			test_step_details(4, "Verify the FP Content error message on all the Sub-Category pages");
			LinkedList<String> sub_category_url_list = homepage_instance.get_sub_catagory_menu_url_list();
			for (String url : sub_category_url_list) {
				invokeBrowser(url);
				assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(), updated_fpcontent_error_msg);
				assertTrue(homepage_instance.verify_openx_banner());
				assertTrue(homepage_instance.verify_right_rail_gpt_ad_1());
				assertTrue(homepage_instance.verify_right_rail_gpt_ad_2());
				break;
			}

			// Step 5
			test_step_details(5, "Verify the FP Content error message on Video pages");
			homepage_instance.click_vidoes_menu();
			assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(), updated_fpcontent_error_msg);

			// Step 6
			test_step_details(6, "Revert back the Joomla admin configuration");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name("frontpage_api", original_fpcontent_api_url);
			admin_instance.enter_input_field_element_by_key_name("error_message_fpcontent_failure",
					original_fpcontent_error_msg);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 7
			test_step_details(7, "Navigate to application and verify the FP Content");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(homepage_instance.verify_lottery_our_pick());

		} catch (Exception e) {
			log.error("Error in Error hadling scenario: " + e.getMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name("frontpage_api", original_fpcontent_api_url);
			admin_instance.enter_input_field_element_by_key_name("error_message_fpcontent_failure",
					original_fpcontent_error_msg);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			log.error("Finally block in Error hadling scenario");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name("frontpage_api", original_fpcontent_api_url);
			admin_instance.enter_input_field_element_by_key_name("error_message_fpcontent_failure",
					original_fpcontent_error_msg);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "RT-04223")
	@testCaseName(test_case_name = "[D/T/M] Frontpage Error Handling Improvement")
	@Test(priority = 1, description = "Verify the 404 Error message by passing invalid configs and params", groups = {
			"DESKTOP",
			"TABLET" }, testName = "RT-04223:[D/T/M] Frontpage Error Handling Improvement")
	public void verify_error_msg_cache_content_on_pages() throws Exception {
		String category_name = null;
		String original_playlist_id = null;

		try {
			// Step 1
			test_step_details(1, "Verify the Error message by passing a Image URL");
			invokeBrowser("https://frontpage." + ENVIRONMENT.toLowerCase() + ".pch.com/abc.png");
			assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(),
					msg_property_file_reader("img_error_message"));

			// Step 2
			test_step_details(2, "Verify the Error message by passing a Invalid Lottery state code in the URL");
			invokeBrowser("https://frontpage." + ENVIRONMENT.toLowerCase() + ".pch.com/lottery/MEP");
			assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(),
					msg_property_file_reader("img_error_message"));

			// Step 3
			test_step_details(3, "Verify the Lottery FP content Cache storage by modifying the Config.");
			invokeBrowser("https://frontpage." + ENVIRONMENT.toLowerCase() + ".pch.com/lottery");
			invokeBrowser("https://frontpage." + ENVIRONMENT.toLowerCase() + ".pch.com/getcachevalue/lottery-states");
			assertTrue(homepage_instance.verify_fpcontent_cache_content());

			// Step 4
			test_step_details(4, "Verify the Lottery FP content Cache storage by modifying the Config.");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name("enable_cache_debug", "0");
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser("https://frontpage." + ENVIRONMENT.toLowerCase()
					+ ".pch.com/getcachevalue/lottery-states?delete=1");
			invokeBrowser("https://frontpage." + ENVIRONMENT.toLowerCase() + ".pch.com/lottery");
			invokeBrowser("https://frontpage." + ENVIRONMENT.toLowerCase() + ".pch.com/getcachevalue/lottery-states");
			assertFalse(homepage_instance.verify_fpcontent_cache_content());

			// Step 5
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			test_step_details(5,
					"Verify the Video player by updating the Video Player id on admin for the category pages");
			LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
			for (String url : url_list) {
				if (!url.endsWith("more")) {
					category_name = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
					log.info("Login to Joomla and navigate to article name: " + category_name);
					invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
					admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
							xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
					admin_instance.goToArticlePage();
					admin_instance.search_for_article("PCH Frontpage " + category_name);
					original_playlist_id = admin_instance.get_text_field_element_by_label("Playlist")
							.getAttribute("value");
					System.out.println(original_playlist_id);
					admin_instance.enter_text_field_element_by_label("Playlist", generateRandomString(6));
					admin_instance.publish_article();
					invokeBrowser(xmlReader(ENVIRONMENT, "app_build_category_pages"));
					invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
					sleepFor(10);
					invokeBrowser(url);
					assertFalse(category_instance.verify_top_story_native_ad());
					break;
				}
			}

			// Step 6
			test_step_details(6, "Revert back the Joomla admin configuration");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name("enable_cache_debug", "1");
			admin_instance.publish_article();
			admin_instance.goToArticlePage();
			admin_instance.search_for_article("PCH Frontpage " + category_name);
			admin_instance.enter_text_field_element_by_label("Playlist", original_playlist_id);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_build_category_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in Error hadling & Caching Strategy scenario: " + e.getMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name("enable_cache_debug", "1");
			admin_instance.publish_article();
			admin_instance.goToArticlePage();
			admin_instance.search_for_article("PCH Frontpage " + category_name);
			admin_instance.enter_text_field_element_by_label("Playlist", original_playlist_id);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_build_category_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			log.error("Finally block in Error handling & Caching Strategy scenario");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name("enable_cache_debug", "1");
			admin_instance.publish_article();
			admin_instance.goToArticlePage();
			admin_instance.search_for_article("PCH Frontpage " + category_name);
			admin_instance.enter_text_field_element_by_label("Playlist", original_playlist_id);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_build_category_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}
}
