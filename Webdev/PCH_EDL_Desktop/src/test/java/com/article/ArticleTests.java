package com.article;

import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;

import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ArticleTests extends BaseClass {

	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final EDLHomePage edl_home_instance = EDLHomePage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final MyAccount myaccount_instance = MyAccount.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private String edl_article_tokens = "Tokens / Edl Story Claim Tokens";
	private String edl_article_name = "Edl Article";
	String token_amount_value = String.valueOf(rand(0, 2000));
	String cbl = "everydaylife";

	//@BeforeClass
	public void set_story_tokens() throws Exception {
		String percentage_value = "100";
		test_step_details(1, "Navigate to Joomla admin and modify the token amount");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(edl_article_tokens);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		admin_instance.enter_text_field_element_by_label("Percentage", percentage_value);
		admin_instance.save_and_close_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
	}

	@testId(test_id = "34585")
	@testCaseName(test_case_name = "B-54865 [D&T] Article Page")
	//@Test
	public void verify_article_page() throws Exception {

		try {
			// step 1
			test_step_details(1, "Register a full reg and click on recipe menu");
			edl_home_instance.click_register();
			register.register_FullUser();
			lb_instance.close_bronze_level_up_lb();
			lb_instance.close_welcome_optin_lb();
			edl_home_instance.click_recipe_header_menu();
			article_instance.click_recipe_of_day();

			// step 2
			test_step_details(3, "verify the article page by clicking on recipe of the day");
			assertTrue(article_instance.verify_recipe_category_title());
			assertFalse(article_instance.verify_next_article_presence());
			assertTrue(article_instance.verify_teads_ad());
			assertTrue(article_instance.verify_bottom_gpt_ad());
			assertTrue(article_instance.verify_dont_miss_section());
			assertTrue(article_instance.verify_footer_menu());
			assertTrue(article_instance.verify_header_menu());
			assertTrue(article_instance.verify_openx());
			assertTrue(article_instance.verify_search_bar());
			assertTrue(article_instance.verify_unclaimed_button());
			article_instance.click_claim_button();
			assertTrue(article_instance.verify_claimed_button());
			lb_instance.close_level_up_lb();

			// step 3
			test_step_details(3, "verify the token history and last activity tab");
			assertEquals(homepage_instance.get_latest_activity_message(),
					msg_property_file_reader("article_token_activity_message"));
			assertEquals(homepage_instance.get_latest_activity_token_amount(), token_amount_value);
			homepage_instance.click_token_history();
			sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
			assertEqualsInt(myaccount_instance.verify_token_transactions_details(
					msg_property_file_reader("article_token_activity_message"), token_amount_value, 1), 1);

			// step 4
			test_step_details(4, "Navigate to Joomla admin and disable the claim Token articles");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(edl_article_tokens);
			assertTrue(admin_instance.unpublish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			edl_home_instance.click_recipe_header_menu();
			article_instance.click_article_category();
			assertFalse(article_instance.verify_claimed_button());
			assertFalse(article_instance.verify_unclaimed_button());

		} catch (Exception e) {
			log.error("Error on disable the Token/Story Claim Article " + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(edl_article_tokens);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			throw e;
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(edl_article_tokens);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		}
	}

	@testId(test_id = "35195")
	@testCaseName(test_case_name = "B-60664 [D T] Central Business Location to track EDL activity")
	@Test
	public void verify_story_log() throws Exception {
		edl_home_instance.click_register();
		String user_email = register.register_FullUser();
		lb_instance.close_bronze_level_up_lb();
		edl_home_instance.click_recipe_header_menu();
		String story_title = article_instance.get_recipe_title();
		article_instance.click_recipe_of_day();
		String story_id = article_instance.get_story_id();
		String token_amount = article_instance.get_unclaim_token_value();
		String category_type = edl_home_instance.get_main_category_type();
		// got null pointer exception get_mulitple_columns_values_of_user
		LinkedHashMap<String, String> log_details = db_instance.get_story_log_details(user_email); 
		assertEquals(log_details.get("story_id"), story_id);
		assertEquals(log_details.get("story_title"), story_title);
		assertEquals(log_details.get("tokens"), token_amount);
		assertEquals(log_details.get("claimed"), "0");
		assertEquals(log_details.get("cbl"), cbl);
		if (DEVICE.equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (DEVICE.equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		Assert.assertTrue(log_details.get("category").replace(" ", "").equals(category_type.replaceAll("-", "")));
	}

	@testId(test_id = "34585")
	@testCaseName(test_case_name = "B-54865 [D&T] Article Page")
	//@Test
	public void verify_article_from_category_guest_user() throws Exception {
		edl_home_instance.click_recipe_header_menu();
		article_instance.click_article_category();
		assertTrue(article_instance.verify_recipe_category_title());
		article_instance.click_next_article();
		assertTrue(article_instance.verify_next_article_presence());
		assertTrue(article_instance.verify_teads_ad());
		assertTrue(article_instance.verify_bottom_gpt_ad());
		assertTrue(article_instance.verify_dont_miss_section());
		assertTrue(article_instance.verify_footer_menu());
		assertTrue(article_instance.verify_header_menu());
		assertTrue(article_instance.verify_openx());
		assertTrue(article_instance.verify_search_bar());
		assertFalse(article_instance.verify_claimed_button());
		assertTrue(article_instance.verify_text_login_to_earn_tokens());
	}

	@testId(test_id = "34585")
	@testCaseName(test_case_name = "B-54865 [D&T] Article Page")
	//@Test
	public void verify_error_page() throws Exception {

		// Step 1
		try {
			test_step_details(1, "Navigate to Joomla admin and disable the Article");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(edl_article_name);
			assertTrue(admin_instance.unpublish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			edl_home_instance.click_recipe_header_menu();
			article_instance.click_article_category();
			assertTrue(article_instance.verify_error_page());

		} catch (Exception e) {
			log.error("Error on disable the Token/Story Claim Article " + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(edl_article_name);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			throw e;
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(edl_article_name);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		}
	}

}
