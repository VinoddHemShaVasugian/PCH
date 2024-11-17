package com.miscellaneous;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;
import com.pageobjects.SERPage;
import com.pageobjects.ScratchPathPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ScratchPathTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final ArticlePage article_page = ArticlePage.getInstance();
	private final SERPage serpage_instance = SERPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final ScratchPathPage scratchpath_instance = ScratchPathPage.getInstance();
	private final AccountsSignInPage account_signin_instance = AccountsSignInPage.getInstance();
	private final MyAccount my_account_instance = MyAccount.getInstance();
	private final String scratchpath_article = "Tokens / Bonus Scratchcard Completion";
	private String user_email = "";

	@testId(test_id = "RT-04209")
	@testCaseName(test_case_name = "Scratchpath [D/T/M]")
	@Test(priority = 1, description = "Perform sequence of activity and verify daily bonus game progress bar", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04209:Scratchpath [D/T/M]")
	public void verify_bonus_game_bar() throws Exception {
		test_Method_details(1, "Verify the Daily bonus game progress bar");

		// Step 1
		test_step_details(1, "Create a Full Reg. User and verify the DB property");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		user_email = register_instance.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the daily Bonus Game Info window");
		homepage_instance.click_daily_bonus_game_info_icon();
		assertTrue(homepage_instance.verify_daily_bonus_game_info_window());
		homepage_instance.close_daily_bonus_game_info_window();
		assertFalse(homepage_instance.verify_daily_bonus_game_info_window());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Search a String and verify the daily bonus game Step-1 is unlocked.");
		homepage_instance.search(generateRandomString(5));
		switchToNewTab();
		assertTrue(serpage_instance.verify_SERP_Completely());
		switchToMainTab();
		doRefresh();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(daily_bouns_game_count, 1);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Navigate to Article page and verify the Daily bonus game bar");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		doRefresh();
		homepage_instance.click_first_article_link();
		assertTrue(article_page.verify_next_article_presence());
		article_page.click_claim_button();
		assertTrue(article_page.verify_claimed_button());
		doRefresh();
		daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(2, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Claim Horoscope tokens and verify the Daily bonus game bar");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_horoscope_menu();
		article_page.click_claim_button();
		assertTrue(article_page.verify_claimed_button());
		doRefresh();
		daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(3, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Claim Lottery tokens and verify the Daily bonus game bar");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_lottery_menu();
		article_page.click_claim_button();
		assertTrue(article_page.verify_claimed_button());
		doRefresh();
		daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(4, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Claim Weather tokens and verify the Daily bonus game bar");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_weather_menu();
		article_page.click_claim_button();
		assertTrue(article_page.verify_claimed_button());
		doRefresh();
		daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(5, daily_bouns_game_count);
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verify the Play Bonus Game icon on the Daily Bonus game progress bar");
		assertTrue(homepage_instance.verify_daily_bonus_game_play_icon());
		assertEquals(db_instance.get_daily_progress_mission_details(user_email), "1");
		step_validator(8, true);

	}

	@testId(test_id = "RT-04209")
	@testCaseName(test_case_name = "Scratchpath [D/T/M]")
	@Test(priority = 2, description = "Play and verify the daily bonus game", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04209:Scratchpath [D/T/M]", dependsOnMethods = "verify_bonus_game_bar")
	public void play_scratch_path_games() throws Exception {
		test_Method_details(2, "Verify the Daily bonus game progress bar");
		String token_amount_value = String.valueOf(rand(0, 20000));

		// Step 1
		test_step_details(1, "Navigate to Joomla admin and modify the ScratchPath complete token amount");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(scratchpath_article);
		admin_instance.enter_text_field_element_by_label("Tokens", token_amount_value, 1);
		String expected_scratchpath_desc = getAttribute(
				admin_instance.get_text_field_element_by_label("Description", "1"), "value");
		assertTrue(admin_instance.publish_article());
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Click on Daily Bonus Play game icon");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		account_signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.click_daily_bonus_game_play_icon();
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "BaseURL") + "scratchpath");
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Play Scratch Path games");
		String scratch_path_token;
		String scratch_path_total_game_count = scratchpath_instance.get_scratch_path_total_game_count();
		int games_total_count = Integer.parseInt(scratch_path_total_game_count);
		for (int count = 0; count < games_total_count; count++) {
			log.info("Playing " + (count + 1) + " scratch path game.");
			scratchpath_instance.wait_for_scratchpath_ads_to_complete();
			scratch_path_token = scratchpath_instance.play_scratchpath_game();
			scratchpath_instance.wait_for_scratchpath_ads_to_complete();
			homepage_instance.click_token_history();
			account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
			if (count < games_total_count - 1) {
				assertEquals(my_account_instance.get_token_transaction_amount(1), scratch_path_token);
				log.info("scratch_path_token: " + scratch_path_token);
				invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
				homepage_instance.click_daily_bonus_game_play_icon();
			} else {
				assertEquals(my_account_instance.get_token_transaction_amount(2), scratch_path_token);
				log.info("scratch_path_token: " + scratch_path_token);
				invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			}
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Daily bonus game progress bar after played 5 games");
		assertFalse(homepage_instance.verify_daily_bonus_game_play_icon());
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the Complete Play Bonus Game Token amount");
		homepage_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		my_account_instance.verify_token_transactions_details(expected_scratchpath_desc, token_amount_value, 1);
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify the Play Bonus Game property on DB");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		doRefresh();
		assertFalse(homepage_instance.verify_daily_bonus_game_play_icon());
		System.out.println("Catch block executed");
		assertEquals(db_instance.get_daily_progress_mission_details(user_email), "2");
		step_validator(6, true);
	}
}
