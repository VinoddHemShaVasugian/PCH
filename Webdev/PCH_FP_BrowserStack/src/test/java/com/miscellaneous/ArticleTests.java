package com.miscellaneous;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;
import com.pageobjects.SubCategoryPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ArticleTests extends BaseClass {

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final ArticlePage article_page = ArticlePage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final MyAccount myaccount_instance = MyAccount.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final SubCategoryPage sub_category_instance = SubCategoryPage.getInstance();
	private final String article_name = "Article";
	private final String token_story_article_name = "Tokens / Story Claim Tokens";
	private final String teads_article_name = "Teads";

	@testId(test_id = "RT-04273")
	@testCaseName(test_case_name = "[D/T/M] : FP Article page integration")
	@Test(priority = 1, description = "Verify the Article page", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04273:[D/T/M] : FP Article page integration")
	public void verify_article_page() throws Exception {
		test_Method_details(1,"Verify the Article page");
		String unclaim_button_text = "CLAIM TOKENS ";
		String claimed_button_text = "CLAIMED";
		String token_amount_value = String.valueOf(rand(0, 2000));
		try {
			// Step 1
			test_step_details(1, "Create a Full Reg. user");
			homepage_instance.click_Register();
			register_instance.register_FullUser();
			lb_instance.close_welcome_optin_lb();
			homepage_instance.close_openx_banner();
			assertTrue(homepage_instance.verify_Home());
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Navigate to Joomla admin and modify the token amount");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(token_story_article_name);
			admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
			assertTrue(admin_instance.publish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Navigate to Article page and verify the claim Token");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			homepage_instance.click_first_article_link();
			assertTrue(article_page.verify_next_article_presence());
			assertTrue(article_page.verify_unclaimed_button());
			assertEqualsIgnoreCase(article_page.get_unclaim_button_text().trim(),
					(unclaim_button_text + token_amount_value));
			assertEqualsIgnoreCase(article_page.get_unclaim_token_value().trim(), token_amount_value);
			article_page.click_claim_button();
			assertTrue(article_page.verify_claimed_button());
			assertEquals(article_page.get_claimed_button_text(), claimed_button_text);
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Verify the cliamed article token amount in latest activity & token history tab");
			assertEquals(homepage_instance.get_latest_activity_message(),
					msg_property_file_reader("article_token_activity_message"));
			assertEquals(homepage_instance.get_latest_activity_token_amount(), token_amount_value);
			homepage_instance.click_token_history();
			sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
			assertEqualsInt(myaccount_instance.verify_token_transactions_details(
					msg_property_file_reader("article_token_activity_message"), token_amount_value, 1), 1);
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Navigate to Joomla admin and disable the claim Token articles");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(token_story_article_name);
			assertTrue(admin_instance.unpublish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			homepage_instance.click_first_article_link();
			article_page.verify_next_article_presence();
			article_page.click_next_article();
			assertFalse(article_page.verify_claimed_button());
			assertFalse(article_page.verify_unclaimed_button());
			step_validator(5, true);

			// Step 6
			test_step_details(6, "Navigate to Joomla admin and disable the Article");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(article_name);
			assertTrue(admin_instance.unpublish_article());
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			homepage_instance.click_first_article_link();
//			assertTrue(article_page.verify_error_page());
			step_validator(6, true);
		} catch (Exception e) {
			log.error("Error on disable the Token/Story Claim Article " + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(article_name);
			admin_instance.publish_article();
			admin_instance.search_for_article(token_story_article_name);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			throw e;
		} finally {
			System.out.println("Entered into finally block");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(article_name);
			admin_instance.publish_article();
			admin_instance.search_for_article(token_story_article_name);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		}
	}

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 2, description = "Verify the Bottom DFP ad on article page", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_article_page_bottom_dfp_ad() throws Exception {
		test_Method_details(2, "B-49294 Move triple ad on Article pages to DFP Tag");

		// Step 1
		test_step_details(1, "Login to the FP with valid user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to article page from top stories section");
		homepage_instance.click_first_article_link();
		assertTrue(article_page.verify_next_article_presence());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Bottom GPT ad");
		assertTrue(article_page.verify_bottom_gpt_ad());
		step_validator(3, true);
	}

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 3, description = "Verify the Teads ad on article page", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_article_page_teads_ad() throws Exception {
		test_Method_details(3,
				"Move teads ad within article pages to DFP [D&T],B-29394 [MT] Replace In Line Ad on Article Page with Teads ad - IOS_Copy_1");
		try {
			// Step 1
			test_step_details(1, "Login to the FP with valid user");
			homepage_instance.click_SignIn();
			sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
			lb_instance.close_welcome_optin_lb();
			homepage_instance.close_openx_banner();
			assertTrue(homepage_instance.verify_Home());
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Navigate to article page from top stories section");
			homepage_instance.click_first_article_link();
			assertTrue(article_page.verify_next_article_presence());
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Verify the Teads ad");
			assertTrue(article_page.verify_teads_ad());
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Navigate to Joomla admin and disable the Article Teads Ad");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(teads_article_name);
			admin_instance.enter_text_field_element_by_label("Value", "N", 1);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Verify the Teads ad");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			homepage_instance.click_first_article_link();
			assertTrue(article_page.verify_next_article_presence());
			assertFalse(article_page.verify_teads_ad());
			step_validator(5, true);
		} catch (Exception e) {
			log.error("Exception in disable Tead ad:" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(teads_article_name);
			admin_instance.enter_text_field_element_by_label("Value", "Y", 1);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} finally {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(teads_article_name);
			admin_instance.enter_text_field_element_by_label("Value", "Y", 1);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}

	@testId(test_id = "RT-04233")
	@testCaseName(test_case_name = "RT-04233 : Create Local Tables for Video and Story Logs [D/T/M]")
	@Test(priority = 4, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Story details on Story log table after reads the story for Full Reg user", testName = "RT-04233 : Create Local Tables for Video and Story Logs [D/T/M]")
	public void verify_story_details_on_story_log_for_full_reg() throws Exception {
		test_Method_details(4, "Verify the Story details on Story log table after reads the story for Full Reg user");
		// Step 1
		test_step_details(1, "Create a Full Reg user");
		navigateTo(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		String user_email = register_instance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Story log by read a story from Featured page");
		homepage_instance.click_first_article_link();
		String story_id = article_page.get_story_id();
		String token_amount = article_page.get_unclaim_token_value();
		String category_type = article_page.get_main_category_type();
		article_page.click_claim_button();
		lb_instance.close_level_up_lb();
		assertTrue(article_page.verify_claimed_button());
		LinkedHashMap<String, String> log_details = db_instance.get_story_log_details(user_email);
		assertEquals(log_details.get("story_id"), story_id);
		assertEquals(log_details.get("tokens"), token_amount);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Story log by read story from from all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more") && !url.endsWith("everydaylife")) {
				invokeBrowser(url);
				homepage_instance.click_first_article_link();
				if (story_id.equals(article_page.get_story_id())) {
					article_page.click_next_article();
					article_page.verify_next_article_presence();
				} else {
					story_id = article_page.get_story_id();
				}
				token_amount = article_page.get_unclaim_token_value();
				category_type = article_page.get_sub_category_type();
				article_page.click_claim_button();
				lb_instance.close_level_up_lb();
				assertTrue(article_page.verify_claimed_button());
				log_details = db_instance.get_story_log_details(user_email);
				assertEquals(log_details.get("story_id"), story_id);
				assertEquals(log_details.get("tokens"), token_amount);
				assertEquals(log_details.get("claimed"), "1");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}

		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Story log by read story from from all the Sub-Category pages");
		url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				sub_category_instance.click_first_article_link();
				if (story_id.equals(article_page.get_story_id())) {
					article_page.click_next_article();
					article_page.verify_next_article_presence();
				} else {
					story_id = article_page.get_story_id();
				}
				token_amount = article_page.get_unclaim_token_value();
				category_type = article_page.get_sub_category_type();
				article_page.click_claim_button();
				lb_instance.close_level_up_lb();
				assertTrue(article_page.verify_claimed_button());
				log_details = db_instance.get_story_log_details(user_email);
				assertEquals(log_details.get("story_id"), story_id);
				assertEquals(log_details.get("tokens"), token_amount);
				assertEquals(log_details.get("claimed"), "1");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}
		step_validator(4, true);
	}

	@testId(test_id = "RT-04233")
	@testCaseName(test_case_name = "RT-04233 : Create Local Tables for Video and Story Logs [D/T/M]")
	@Test(priority = 5, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Story details on Story log table after reads the story for Mini Reg user", testName = "RT-04233 : Create Local Tables for Video and Story Logs [D/T/M]")
	public void verify_story_details_on_story_log_for_mini_reg() throws Exception {
		test_Method_details(5, "Verify the Story details on Story log table after reads the story for Mini Reg user");
		// Step 1
		test_step_details(1, "Create a Mini Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = centralpage.createMiniReguser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Story log by read a story from Featured page");
		homepage_instance.click_first_article_link();
		String story_id = article_page.get_story_id();
		String category_type = article_page.get_main_category_type();
		assertTrue(homepage_instance.verify_complete_registration());
		assertTrue(article_page.verify_text_complete_reg_earn_tokens());
		LinkedHashMap<String, String> log_details = db_instance.get_story_log_details(user_details[0]);
		assertEquals(log_details.get("story_id"), story_id);
		assertEquals(log_details.get("claimed"), "0");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Story log by read a story from all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more") && !url.endsWith("home") && !url.endsWith("everydaylife")) {
				invokeBrowser(url);
				homepage_instance.click_first_article_link();
				story_id = article_page.get_story_id();
				category_type = article_page.get_sub_category_type();
				assertTrue(homepage_instance.verify_complete_registration());
				assertTrue(article_page.verify_text_complete_reg_earn_tokens());
				log_details = db_instance.get_story_log_details(user_details[0]);
				assertEquals(log_details.get("story_id"), story_id);
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Story log by read a story from all the Sub-Category pages");
		url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				sub_category_instance.click_first_article_link();
				story_id = article_page.get_story_id();
				category_type = article_page.get_sub_category_type();
				assertTrue(homepage_instance.verify_complete_registration());
				assertTrue(article_page.verify_text_complete_reg_earn_tokens());
				log_details = db_instance.get_story_log_details(user_details[0]);
				assertEquals(log_details.get("story_id"), story_id);
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Complete the Registration");
		homepage_instance.click_complete_registration();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(5, true);

		// Step 6
		test_step_details(6,
				"Verify the Story log by read a Featured article after complete the registration");
		story_id = article_page.get_story_id();
		category_type = article_page.get_sub_category_type();
		String token_amount_value = article_page.get_unclaim_token_value();
		article_page.click_claim_button();
		sleepFor(3);
		log_details = db_instance.get_story_log_details(user_details[0]);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
		step_validator(6, true);
	}

	@testId(test_id = "RT-04233")
	@testCaseName(test_case_name = "RT-04233 : Create Local Tables for Video and Story Logs [D/T/M]")
	@Test(priority = 6, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Story details on Story log table after reads the story for Silver user", testName = "RT-04233 : Create Local Tables for Video and Story Logs [D/T/M]")
	public void verify_story_details_on_story_log_for_silver_user() throws Exception {
		test_Method_details(6, "Verify the Story details on Story log table after reads the story for Silver user");
		// Step 1
		test_step_details(1, "Create a Silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = centralpage.createSilverUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Story log by read a story from Featured page");
		homepage_instance.click_first_article_link();
		String story_id = article_page.get_story_id();
		String category_type = article_page.get_sub_category_type();
		assertTrue(homepage_instance.verify_complete_registration());
		assertTrue(article_page.verify_text_complete_reg_earn_tokens());
		LinkedHashMap<String, String> log_details = db_instance.get_story_log_details(user_details[0]);
		assertEquals(log_details.get("story_id"), story_id);
		assertEquals(log_details.get("claimed"), "0");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Story log by read a story from all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more") && !url.endsWith("everydaylife")) {
				invokeBrowser(url);
				homepage_instance.click_first_article_link();
				story_id = article_page.get_story_id();
				category_type = article_page.get_sub_category_type();
				assertTrue(homepage_instance.verify_complete_registration());
				assertTrue(article_page.verify_text_complete_reg_earn_tokens());
				log_details = db_instance.get_story_log_details(user_details[0]);
				assertEquals(log_details.get("story_id"), story_id);
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Story log by read a story from all the Sub-Category pages");
		url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				sub_category_instance.click_first_article_link();
				story_id = article_page.get_story_id();
				category_type = article_page.get_sub_category_type();
				assertTrue(homepage_instance.verify_complete_registration());
				assertTrue(article_page.verify_text_complete_reg_earn_tokens());
				log_details = db_instance.get_story_log_details(user_details[0]);
				assertEquals(log_details.get("story_id"), story_id);
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Complete the Registration");
		homepage_instance.click_complete_registration();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.completer_RegistrationSilveruser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(5, true);

		// Step 6
		test_step_details(6,
				"Verify the Story log by read a Featured article after complete the registration and Verify VIP activity");
		story_id = article_page.get_story_id();
		category_type = article_page.get_sub_category_type();
		String token_amount_value = article_page.get_unclaim_token_value();
		article_page.click_claim_button();
		sleepFor(3);
		log_details = db_instance.get_story_log_details(user_details[0]);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
		step_validator(6, true);
	}

	@testId(test_id = "RT-04233")
	@testCaseName(test_case_name = "RT-04233 : Create Local Tables for Video and Story Logs [D/T/M]")
	@Test(priority = 7, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Story details on Story log table after reads the story for Social user", testName = "RT-04233 : Create Local Tables for Video and Story Logs [D/T/M]")
	public void verify_story_details_on_story_log_for_social_user() throws Exception {
		test_Method_details(7, "Verify the Story details on Story log table after reads the story for Social user");
		// Step 1
		test_step_details(1, "Create a Social user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = centralpage.createSocialUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_bronze_level_up_lb();
		homepage_instance.close_openx_banner();
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Story log by read a story from Featured page");
		homepage_instance.click_first_article_link();
		String story_id = article_page.get_story_id();
		String category_type = article_page.get_sub_category_type();
		assertTrue(homepage_instance.verify_complete_registration());
		assertTrue(article_page.verify_text_complete_reg_earn_tokens());
		LinkedHashMap<String, String> log_details = db_instance.get_story_log_details(user_details[0]);
		assertEquals(log_details.get("story_id"), story_id);
		assertEquals(log_details.get("claimed"), "0");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Story log by read a story from all the Category pages");
		LinkedList<String> url_list = homepage_instance.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("more") && !url.endsWith("everydaylife")) {
				invokeBrowser(url);
				homepage_instance.click_first_article_link();
				story_id = article_page.get_story_id();
				category_type = article_page.get_sub_category_type();
				assertTrue(homepage_instance.verify_complete_registration());
				assertTrue(article_page.verify_text_complete_reg_earn_tokens());
				log_details = db_instance.get_story_log_details(user_details[0]);
				assertEquals(log_details.get("story_id"), story_id);
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Story log by read a story from all the Sub-Category pages");
		url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				sub_category_instance.click_first_article_link();
				story_id = article_page.get_story_id();
				category_type = article_page.get_sub_category_type();
				assertTrue(homepage_instance.verify_complete_registration());
				assertTrue(article_page.verify_text_complete_reg_earn_tokens());
				log_details = db_instance.get_story_log_details(user_details[0]);
				assertEquals(log_details.get("story_id"), story_id);
				assertEquals(log_details.get("claimed"), "0");
				if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
					assertEquals(log_details.get("device"), "D");
				} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
					assertEquals(log_details.get("device"), "T");
				}
				assertEquals(log_details.get("category"), category_type);
				break;
			}
		}
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Complete the Registration");
		homepage_instance.click_complete_registration();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForSocialUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(5, true);

		// Step 6
		test_step_details(6,
				"Verify the Story log by read a Featured article after complete the registration and Verify VIP activity");
		story_id = article_page.get_story_id();
		category_type = article_page.get_sub_category_type();
		String token_amount_value = article_page.get_unclaim_token_value();
		article_page.click_claim_button();
		sleepFor(3);
		log_details = db_instance.get_story_log_details(user_details[0]);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device_to_launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), category_type);
		step_validator(6, true);
	}

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 8, description = "Verify display of Article page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ads_on_article_page() throws IOException {
		test_Method_details(8, "B-44512 Frontpage Redesign-Ad Tags");
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_height = "250";
		String bottom_native_ad_width = "770";
		String bottom_native_ad_height = "320";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		homepage_instance.click_SignIn();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the display of Tead Ad and the Size");
		homepage_instance.click_first_article_link();
		assertTrue(article_page.verify_teads_ad());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the display of Right Rail Ad's and the Size");
		assertTrue(homepage_instance.verify_right_rail_gpt_ad_1());
		assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[0], right_rail_ad_1_width);
		if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_1_height)) {
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_1_height);
		} else if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_2_height)) {
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_2_height);
		} else {
			assertTrue(false, "Right Rail First ad is mismatched in the height");
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the display of bottom Native Ad");
		assertTrue(homepage_instance.verify_bottom_native_ad());
		assertEquals(homepage_instance.get_size_of_bottom_native_ad()[0], bottom_native_ad_width);
		assertEquals(homepage_instance.get_size_of_bottom_native_ad()[1], bottom_native_ad_height);
		step_validator(4, true);
	}
}
