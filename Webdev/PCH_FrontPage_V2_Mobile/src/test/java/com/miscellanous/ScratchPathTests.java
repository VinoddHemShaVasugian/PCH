package com.miscellanous;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.HamburgerMenuPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;
import com.pageobjects.SERPage;
import com.pageobjects.ScratchPathPage;
import com.pageobjects.VideoPage;
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
	private final HamburgerMenuPage ham_menu_instance = HamburgerMenuPage.getInstance();
	private final MyAccount my_account_instance = MyAccount.getInstance();
	private final VideoPage videopage_instance = VideoPage.getInstance();
	private final String scratchpath_article = "Tokens / Bonus Scratchcard Completion";
	private String user_email = "";

	@testId(test_id = "23093,23841,25016")
	@testCaseName(test_case_name = "MakeProgressBarTapable_Mobile,AddSearchAsFirstPartOfDailyMission_Mobile,B_25816_Verify daily mission counter Lightbox_Desktop")
	@Test(priority = 1, description = "Verify the Daily bonus game progress bar", groups = {
			"MOBILE" }, testName = "23093:MakeProgressBarTapable_Mobile,23841:AddSearchAsFirstPartOfDailyMission_Mobile,25016:B_25816_Verify daily mission counter Lightbox_Desktop")
	public void verify_bonus_game_bar() throws Exception {

		// Step 1
		test_step_details(1, "Create a Full Reg. User and verify the DB property");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_sign_in();
		homepage_instance.click_register();
		user_email = register_instance.register_full_user_without_optin();
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the daily Bonus Game Info window");
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		homepage_instance.click_daily_bonus_game_info_icon();
		assertTrue(homepage_instance.verify_daily_bonus_game_info_window());
		homepage_instance.close_daily_bonus_game_info_window();
		assertFalse(homepage_instance.verify_daily_bonus_game_info_window());

		// Step 3
		test_step_details(3, "Search a String and verify the daily bonus game Step-1 is unlocked.");
		homepage_instance.search(generateRandomString(5));
		switchToNewTab();
		assertTrue(serpage_instance.verify_SERP_Completely());
		switchToMainTab();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(daily_bouns_game_count, 1);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());

		// Step 4
		test_step_details(4, "Navigate to Article page and verify the Daily bonus game bar");
		homepage_instance.click_first_article_link();
		assertTrue(article_page.verify_next_article_link());
		article_page.click_claim_button();
		assertTrue(article_page.verify_claimed_button());
		doRefresh();
		daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(2, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());

		// Step 5
		test_step_details(5, "Claim video tokens and verify the Daily bonus game bar");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_video_link();
		assertTrue(videopage_instance.verify_video_claimed_status());
		doRefresh();
		daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(3, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());

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

		// Step 7
		test_step_details(7, "Claim Weather tokens and verify the Daily bonus game bar");
		homepage_instance.click_weather_menu();
		article_page.click_claim_button();
		assertTrue(article_page.verify_claimed_button());
		doRefresh();
		daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(5, daily_bouns_game_count);

		// Step 8
		test_step_details(8, "Verify the Play Bonus Game icon on the Daily Bonus game progress bar");
		assertTrue(homepage_instance.verify_daily_bonus_game_play_icon());
		assertEquals(db_instance.get_daily_progress_mission_details(user_email), "1");
	}

	@testId(test_id = "23498,26566,34057")
	@testCaseName(test_case_name = "Create Frontpage Mobile Progress Bar_Mobile,B-28518 [D][T][M] Scratchcard Path Bonus Tokens,[M Redesign] Bonus Game Integration [Split1]")
	@Test(priority = 2, description = "Verify the Scratchpath games", groups = {
			"MOBILE" }, testName = "23498:Create Frontpage Mobile Progress Bar_Mobile,26566:B-28518 [D][T][M] Scratchcard Path Bonus Tokens,34057:[M Redesign] Bonus Game Integration [Split1]", dependsOnMethods = "verify_bonus_game_bar")
	public void play_scratch_path_games() throws Exception {
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

		// Step 2
		test_step_details(2, "Click on Daily Bonus Play game icon");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_sign_in();
		account_signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.click_daily_bonus_game_play_icon();
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "BaseURL") + "scratchpath");

		// Step 3
		test_step_details(3, "Play Scratch Path games");
		String scratch_path_token;
		for (int count = 0; count < 5; count++) {
			log.info("Playing " + (count + 1) + " scratch path game.");
			scratchpath_instance.wait_for_scratchpath_ads_to_dsiplay();
			scratch_path_token = scratchpath_instance.play_scratchpath_game();
			scratchpath_instance.wait_for_scratchpath_ads_to_dsiplay();
			// Verify the earned token in Token History page
			ham_menu_instance.click_token_history();
			account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
			if (count < 4) {
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

		// Step 4
		test_step_details(4, "Verify the Daily bonus game progress bar after played 5 games");
		assertFalse(homepage_instance.verify_daily_bonus_game_play_icon());

		// Step 5
		test_step_details(5, "Verify the Complete Play Bonus Game Token amount");
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		my_account_instance.verify_token_transactions_details(expected_scratchpath_desc, token_amount_value, 1);

		// Step 6
		test_step_details(6, "Verify the Play Bonus Game property on DB");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertTrue(homepage_instance.verify_daily_bonus_game_play_icon());
		assertEquals(db_instance.get_daily_progress_mission_details(user_email), "2");
	}
}
