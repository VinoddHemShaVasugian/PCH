package com.healthination;

import java.util.LinkedHashMap;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HealthiNationPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class HealthinationTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final HealthiNationPage healthination_page = HealthiNationPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final MyAccount myaccount_instance = MyAccount.getInstance();
	private final CentralServices_Page cs_instnace = CentralServices_Page.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();

	private final String healthination_article_name = "Healthination";

	@testId(test_id = "RT-04235")
	@testCaseName(test_case_name = "Healthination Page [D/T/M]")
	@Test(priority = 1, description = "Verify the HealthiNation page for the Full Reg user", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04235:Healthination Page [D/T/M]")
	public void verify_healthination_for_full_reg() throws Exception {
		String healthination_url = xmlReader(ENVIRONMENT, "BaseURL") + "healthination";
		String token_amount_value = String.valueOf(rand(100, 500));

		// Step 1
		test_step_details(1, "Navigate to Joomla admin and modify the token amount for HealthiNation");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(healthination_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Create a Full Reg. user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		String user_email = register_instance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		homepage_instance.clic_new_VIP_logo(5);
		String beforeActivity= homepage_instance.get_vip_msg();
		

		// Step 3
		test_step_details(3, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		assertTrue(healthination_page.verify_video_player());
		assertTrue(healthination_page.verify_bottom_ad());
		assertTrue(healthination_page.verify_right_rail_ad_one());
		assertTrue(healthination_page.verify_right_rail_ad_two());

		// Step 4
		test_step_details(4, "Verify the Video and the Token award message");
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_success_message", token_amount_value));
		assertTrue(healthination_page.verify_token_claim_button());
		assertEquals(homepage_instance.get_latest_activity_message(),
				msg_property_file_reader("healthination_token_activity_message"));
		assertEquals(homepage_instance.get_latest_activity_token_amount(), token_amount_value);

		// Step 5
		test_step_details(5, "Verify the application redirects to Homepage after video completes and verify VIP activity");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());
		homepage_instance.clic_new_VIP_logo(5);
		String afterActivity= homepage_instance.get_vip_msg();
		assertNotEqualsIgnoreCase(beforeActivity, afterActivity);
		

		// Step 6
		test_step_details(6, "Verify the HealthiNation details in the User Data and Video log tables");
		assertEquals(db_instance.get_healthination_details(user_email), token_amount_value);
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_email);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), "healthination");

		// Step 7
		test_step_details(7, "Verify the HealthiNation message in the Token History tab");
		homepage_instance.click_token_history();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		myaccount_instance.verify_token_transactions_details(
				msg_property_file_reader("healthination_success_message", token_amount_value), token_amount_value, 1);

		// Step 8
		test_step_details(8, "Verify the HealthiNation page and the token award message for the same user");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_already_token_claimed_message"));
		assertTrue(healthination_page.verify_token_already_claim_button());

		// Step 9
		test_step_details(9, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 10
		test_step_details(10, "Verify the Progress bar update after the HealthiNation video completes");
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
	}

	@testId(test_id = "RT-04235")
	@testCaseName(test_case_name = "Healthination Page [D/T/M]")
	@Test(priority = 2, description = "Verify the HealthiNation page for the Mini Reg user", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04235:Healthination Page [D/T/M]")
	public void verify_healthination_for_mini_reg() throws Exception {
		String healthination_url = xmlReader(ENVIRONMENT, "BaseURL") + "healthination";
		String token_amount_value = String.valueOf(rand(0, 500));

		// Step 1
		test_step_details(1, "Navigate to Joomla admin and modify the token amount for HealthiNation");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(healthination_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Create a Mini Reg. user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createMiniReguser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		homepage_instance.clic_new_VIP_logo(5);
		String beforeActivity= homepage_instance.get_vip_msg();

		// Step 3
		test_step_details(3, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		assertTrue(healthination_page.verify_video_player());
		assertTrue(healthination_page.verify_bottom_ad());
		assertTrue(healthination_page.verify_right_rail_ad_one());
		assertTrue(healthination_page.verify_right_rail_ad_two());

		// Step 4
		test_step_details(4, "Verify the Video and the Complete Registration button & message");
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		// assertEquals(healthination_page.get_token_award_message(),
		// msg_property_file_reader("healthination_complete_registration_message"));
		assertFalse(healthination_page.verify_token_claim_button());
		assertTrue(homepage_instance.verify_complete_registration());

		// Step 5
		test_step_details(5, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 6
		test_step_details(6, "Complete the Registration");
		homepage_instance.click_complete_registration();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();

		// Step 7
		test_step_details(7, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_success_message", token_amount_value));
		assertTrue(healthination_page.verify_token_claim_button());
		assertEquals(homepage_instance.get_latest_activity_message(),
				msg_property_file_reader("healthination_token_activity_message"));
		assertEquals(homepage_instance.get_latest_activity_token_amount(), token_amount_value);
		homepage_instance.clic_new_VIP_logo(5);
		String afterActivity= homepage_instance.get_vip_msg();
		assertNotEqualsIgnoreCase(beforeActivity, afterActivity);


		// Step 8
		test_step_details(8,
				"Verify the HealthiNation details in the User Data and Video log tables after complete the registration");
		assertEquals(db_instance.get_healthination_details(user_details[0]), token_amount_value);
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), "healthination");

		// Step 9
		test_step_details(9, "Verify the HealthiNation message in the Token History tab");
		homepage_instance.click_token_history();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		myaccount_instance.verify_token_transactions_details(
				msg_property_file_reader("healthination_success_message", token_amount_value), token_amount_value, 1);

		// Step 10
		test_step_details(10, "Verify the HealthiNation page and the token award message for the same user");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_already_token_claimed_message"));
		assertTrue(healthination_page.verify_token_already_claim_button());

		// Step 11
		test_step_details(11, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 12
		test_step_details(12, "Verify the Progress bar update after the HealthiNation video completes");
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
	}

	@testId(test_id = "RT-04235")
	@testCaseName(test_case_name = "Healthination Page [D/T/M]")
	@Test(priority = 3, description = "Verify the HealthiNation page for the Silver user", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04235:Healthination Page [D/T/M]")
	public void verify_healthination_for_silver_user() throws Exception {
		String healthination_url = xmlReader(ENVIRONMENT, "BaseURL") + "healthination";
		String token_amount_value = String.valueOf(rand(0, 500));

		// Step 1
		test_step_details(1, "Navigate to Joomla admin and modify the token amount for HealthiNation");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(healthination_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Create a Mini Reg. user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSilverUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		homepage_instance.clic_new_VIP_logo(5);
		String beforeActivity= homepage_instance.get_vip_msg();

		// Step 3
		test_step_details(3, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		assertTrue(healthination_page.verify_video_player());
		assertTrue(healthination_page.verify_bottom_ad());
		assertTrue(healthination_page.verify_right_rail_ad_one());
		assertTrue(healthination_page.verify_right_rail_ad_two());

		// Step 4
		test_step_details(4, "Verify the Video and the Complete Registration button & message");
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		// assertEquals(healthination_page.get_token_award_message(),
		// msg_property_file_reader("healthination_complete_registration_message"));
		assertFalse(healthination_page.verify_token_claim_button());
		assertTrue(homepage_instance.verify_complete_registration());

		// Step 5
		test_step_details(5, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 6
		test_step_details(6, "Complete the Registration");
		homepage_instance.click_complete_registration();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.completer_RegistrationSilveruser();
		lb_instance.close_bronze_level_up_lb();

		// Step 7
		test_step_details(7, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_success_message", token_amount_value));
		assertTrue(healthination_page.verify_token_claim_button());
		assertEquals(homepage_instance.get_latest_activity_message(),
				msg_property_file_reader("healthination_token_activity_message"));
		assertEquals(homepage_instance.get_latest_activity_token_amount(), token_amount_value);
		homepage_instance.clic_new_VIP_logo(5);
		String afterActivity= homepage_instance.get_vip_msg();
		assertNotEqualsIgnoreCase(beforeActivity, afterActivity);


		// Step 8
		test_step_details(8,
				"Verify the HealthiNation details in the User Data and Video log tables after complete the registration");
		assertEquals(db_instance.get_healthination_details(user_details[0]), token_amount_value);
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), "healthination");

		// Step 9
		test_step_details(9, "Verify the HealthiNation message in the Token History tab");
		homepage_instance.click_token_history();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		myaccount_instance.verify_token_transactions_details(
				msg_property_file_reader("healthination_success_message", token_amount_value), token_amount_value, 1);

		// Step 10
		test_step_details(10, "Verify the HealthiNation page and the token award message for the same user");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_already_token_claimed_message"));
		assertTrue(healthination_page.verify_token_already_claim_button());

		// Step 11
		test_step_details(11, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 12
		test_step_details(12, "Verify the Progress bar update after the HealthiNation video completes");
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
	}

	@testId(test_id = "RT-04235")
	@testCaseName(test_case_name = "Healthination Page [D/T/M]")
	@Test(priority = 4, description = "Verify the HealthiNation page for the Social user", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04235:Healthination Page [D/T/M]")
	public void verify_healthination_for_social_user() throws Exception {
		String healthination_url = xmlReader(ENVIRONMENT, "BaseURL") + "healthination";
		String token_amount_value = String.valueOf(rand(0, 500));

		// Step 1
		test_step_details(1, "Navigate to Joomla admin and modify the token amount for HealthiNation");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(healthination_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Create a Mini Reg. user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instnace.createSocialUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		homepage_instance.clic_new_VIP_logo(5);
		String beforeActivity= homepage_instance.get_vip_msg();

		// Step 3
		test_step_details(3, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		assertTrue(healthination_page.verify_video_player());
		assertTrue(healthination_page.verify_bottom_ad());
		assertTrue(healthination_page.verify_right_rail_ad_one());
		assertTrue(healthination_page.verify_right_rail_ad_two());

		// Step 4
		test_step_details(4, "Verify the Video and the Complete Registration button & message");
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		// assertEquals(healthination_page.get_token_award_message(),
		// msg_property_file_reader("healthination_complete_registration_message"));
		assertFalse(healthination_page.verify_token_claim_button());
		assertTrue(homepage_instance.verify_complete_registration());

		// Step 5
		test_step_details(5, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 6
		test_step_details(6, "Complete the Registration");
		homepage_instance.click_complete_registration();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForSocialUser();
		lb_instance.close_bronze_level_up_lb();

		// Step 7
		test_step_details(7, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_success_message", token_amount_value));
		assertTrue(healthination_page.verify_token_claim_button());
		assertEquals(homepage_instance.get_latest_activity_message(),
				msg_property_file_reader("healthination_token_activity_message"));
		assertEquals(homepage_instance.get_latest_activity_token_amount(), token_amount_value);
		homepage_instance.clic_new_VIP_logo(5);
		String afterActivity= homepage_instance.get_vip_msg();
		assertNotEqualsIgnoreCase(beforeActivity, afterActivity);


		// Step 8
		test_step_details(8,
				"Verify the HealthiNation details in the User Data and Video log tables after complete the registration");
		assertEquals(db_instance.get_healthination_details(user_details[0]), token_amount_value);
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_details[0]);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), "healthination");

		// Step 9
		test_step_details(9, "Verify the HealthiNation message in the Token History tab");
		homepage_instance.click_token_history();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		myaccount_instance.verify_token_transactions_details(
				msg_property_file_reader("healthination_success_message", token_amount_value), token_amount_value, 1);

		// Step 10
		test_step_details(10, "Verify the HealthiNation page and the token award message for the same user");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_already_token_claimed_message"));
		assertTrue(healthination_page.verify_token_already_claim_button());

		// Step 11
		test_step_details(11, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 12
		test_step_details(12, "Verify the Progress bar update after the HealthiNation video completes");
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
	}

	@testId(test_id = "RT-04235")
	@testCaseName(test_case_name = "Healthination Page [D/T/M]")
	@Test(priority = 5, description = "Verify the HealthiNation page for the Guest user", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04235:Healthination Page [D/T/M]")
	public void verify_healthination_for_guest_user() throws Exception {
		String healthination_url = xmlReader(ENVIRONMENT, "BaseURL") + "healthination";

		// Step 1
		test_step_details(1, "Close the OpenX banner");
		homepage_instance.close_openx_banner();

		// Step 2
		test_step_details(2, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		assertTrue(healthination_page.verify_video_player());
		assertTrue(healthination_page.verify_bottom_ad());
		assertTrue(healthination_page.verify_right_rail_ad_one());
		assertTrue(healthination_page.verify_right_rail_ad_two());

		// Step 3
		test_step_details(3, "Verify the Video and the Sign-in/Register button & message");
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		// assertEquals(healthination_page.get_token_award_message(),
		// msg_property_file_reader("healthination_message_for_unrec"));
		assertFalse(healthination_page.verify_token_claim_button());
		assertTrue(homepage_instance.verify_register());
		assertTrue(homepage_instance.verify_signin());

		// Step 4
		test_step_details(4, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());
	}

	@testId(test_id = "RT-04235")
	@testCaseName(test_case_name = "Healthination Page [D/T/M]")
	@Test(priority = 6, description = "Verify the HealthiNationToken Award message for Zero tokens", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04235:Healthination Page [D/T/M]")
	public void verify_healthination_token_message_for_zero_tokens() throws Exception {
		String healthination_url = xmlReader(ENVIRONMENT, "BaseURL") + "healthination";
		String token_amount_value = String.valueOf(rand(0, 500));

		// Step 1
		test_step_details(1, "Navigate to Joomla admin and modify the token amount for HealthiNation");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(healthination_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", "0", 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Create a Full Reg. user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		String user_email = register_instance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());

		// Step 3
		test_step_details(3, "Navigate to HealthiNation page and verify the Video player and Ad's");
		invokeBrowser(healthination_url);
		assertTrue(healthination_page.verify_video_player());
		assertTrue(healthination_page.verify_bottom_ad());
		assertTrue(healthination_page.verify_right_rail_ad_one());
		assertTrue(healthination_page.verify_right_rail_ad_two());

		// Step 4
		test_step_details(4, "Verify the Video and the token award message overlay");
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertFalse(healthination_page.verify_token_award_default_message());

		// Step 5
		test_step_details(5, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 6
		test_step_details(6,
				"Verify the HealthiNation details in the User Data and Video log tables by making the token value as 0");
		LinkedHashMap<String, String> log_details = db_instance.get_video_log_details(user_email);
		assertEquals(log_details.get("tokens"), "0");
		assertEquals(log_details.get("claimed"), "0");
		if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), "healthination");

		// Step 7
		test_step_details(7, "Navigate to Joomla admin and modify the token amount for HealthiNation");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(healthination_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 8
		test_step_details(8,
				"Navigate to HealthiNation page and verify the Video player after modified the Token amount");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_success_message", token_amount_value));
		assertTrue(healthination_page.verify_token_claim_button());
		assertEquals(homepage_instance.get_latest_activity_message(),
				msg_property_file_reader("healthination_token_activity_message"));
		assertEquals(homepage_instance.get_latest_activity_token_amount(), token_amount_value);

		// Step 9
		test_step_details(9,
				"Verify the HealthiNation details in the User Data and Video log tables by making the token value as other than 0");
		log_details = db_instance.get_video_log_details(user_email);
		assertEquals(log_details.get("tokens"), token_amount_value);
		assertEquals(log_details.get("claimed"), "1");
		if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Desktop")) {
			assertEquals(log_details.get("device"), "D");
		} else if (env_property_file_reader("device.to.launch").equalsIgnoreCase("Tablet")) {
			assertEquals(log_details.get("device"), "T");
		}
		assertEquals(log_details.get("category"), "healthination");

		// Step 10
		test_step_details(10, "Verify the HealthiNation message in the Token History tab");
		homepage_instance.click_token_history();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		myaccount_instance.verify_token_transactions_details(
				msg_property_file_reader("healthination_success_message", token_amount_value), token_amount_value, 1);

		// Step 11
		test_step_details(11, "Verify the HealthiNation page and the token award message for the same user");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertEquals(healthination_page.get_token_award_message(),
				msg_property_file_reader("healthination_already_token_claimed_message"));
		assertTrue(healthination_page.verify_token_already_claim_button());

		// Step 12
		test_step_details(12, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());

		// Step 13
		test_step_details(7, "Navigate to Joomla admin and modify the token amount to 0 for HealthiNation");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(healthination_article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", "0", 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 14
		test_step_details(14, "Verify the Video and the already token award message overlay");
		invokeBrowser(healthination_url);
		healthination_page.wait_for_ad_complete();
		sleepFor(8); // Tokens will be award after 6 secs video played
		assertFalse(healthination_page.verify_token_award_default_message());

		// Step 15
		test_step_details(15, "Verify the application redirects to Homepage after video completes");
		healthination_page.wait_for_video_complete();
		assertTrue(homepage_instance.verify_lottery_our_pick());
	}
}
