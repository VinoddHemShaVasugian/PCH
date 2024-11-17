package com.miscellanous;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ErrorHandlingTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String config_article_name = "Config-Frontpage";
	private final String homepage_article = "Homepage";
	private final String topstories_check_field = "Top Stories";
	private final String config_input_field_name = "frontpage_api";
	private final String config_error_message_field = "error_message_fpcontent_failure";

	@testId(test_id = "34322")
	@testCaseName(test_case_name = "B-54212 Frontpage Error Handling Improvement [Split 1]")
	@Test
	public void verify_error_msg_on_content_down() throws Exception {

		String original_fpcontent_api_url = null;
		try {
			// Step 1
			test_step_details(1, "Get the availble Cateogry and Sub-Category menu");
			LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
			LinkedList<String> sub_category_url_list = null;
			for (String url : url_list) {
				if (!url.endsWith("more") && !url.endsWith("/") && !url.endsWith("featured")) {
					invokeBrowser(url);
					sub_category_url_list = homepage_instance.get_sub_catagory_menu_url_list();
				}
			}

			// Step 2
			test_step_details(2, "Navigate to FP admin and remove the top stories section");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(homepage_article);
			admin_instance.check_field_based_on_label_name(topstories_check_field, 0, 4);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 3
			test_step_details(3, "Go to frontpage and verify homepage loads without any error");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			lb_instance.close_gs_overlay();
			assertFalse(homepage_instance.verify_top_stories_visibility());

			// Step 4
			test_step_details(4, "Navigate to admin and change the FP content url to invalid");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			original_fpcontent_api_url = getAttribute(
					admin_instance.get_input_field_element_by_key_name(config_input_field_name), "value");
			admin_instance.enter_input_field_element_by_key_name(config_input_field_name, generateRandomString(4));
			String original_fpcontent_error_msg = getAttribute(
					admin_instance.get_input_field_element_by_key_name(config_error_message_field), "value");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

			// Step 5
			test_step_details(5, "Navigate to homepage and verify the content");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			assertTrue(homepage_instance.verify_page_after_content_down());
			assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(), original_fpcontent_error_msg);

			// Step 6
			test_step_details(6, "Verify the FP Content error message on all the Category pages");
			for (String url : url_list) {
				if (!url.endsWith("more") && !url.endsWith("/") && !url.endsWith("featured")) {
					invokeBrowser(url);
					assertTrue(homepage_instance.verify_page_after_content_down());
					assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(),
							original_fpcontent_error_msg);
					break;
				}
			}

			// Step 7
			test_step_details(7, "Verify the FP Content error message on all the Sub-Category pages");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			for (String url : sub_category_url_list) {
				invokeBrowser(url);
				assertTrue(homepage_instance.verify_page_after_content_down());
				assertEqualsIgnoreCase(homepage_instance.verify_fpcontent_error_msg(), original_fpcontent_error_msg);
				break;
			}
		} catch (Exception e) {
			// Step 8
			test_step_details(8, "revert changes to previous values if the exception occurs");
			log.error("Catch block of Error handling story :" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(homepage_article);
			admin_instance.check_field_based_on_label_name(topstories_check_field, 1, 4);
			admin_instance.save_and_close_article();
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name_via_js(config_input_field_name,
					original_fpcontent_api_url);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			// Step 9
			test_step_details(9, "Revert changes to previous values if the exception occurs");
			log.error("Finally block of Error handling story");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(homepage_article);
			admin_instance.check_field_based_on_label_name(topstories_check_field, 1, 4);
			admin_instance.save_and_close_article();
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(config_article_name);
			admin_instance.enter_input_field_element_by_key_name_via_js(config_input_field_name,
					original_fpcontent_api_url);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

}
