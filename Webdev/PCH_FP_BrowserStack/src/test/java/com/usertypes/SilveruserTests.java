package com.usertypes;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.LotteryPage;
import com.pageobjects.SERPage;
import com.pageobjects.VideoLandingPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SilveruserTests extends BaseClass {

	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final HomePage home_instance = HomePage.getInstance();
	private final VideoLandingPage video_instance = VideoLandingPage.getInstance();
	private final LotteryPage lottery_instance = LotteryPage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();

	@testId(test_id = "RT-04212")
	@testCaseName(test_case_name = "Silver user test scenario [D/T]")
	@Test(priority = 1, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Claim button for Silver user on all the pages", testName = "RT-04212:Silver user test scenario [D/T]")
	public void verify_claim_button_on_all_pages_for_silver_user() throws Exception {
		test_Method_details(1, "Verify the Claim button for Silver user on all the pages");

		// Step 1
		test_step_details(1, "Create new Silver user and go to FP and verify the article page");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String[] email_url = centralpage.createSilverUser();
		sleepFor(3);
		navigateTo(email_url[1]);
		lb_instance.close_welcome_optin_lb();
		home_instance.close_openx_banner();
		home_instance.click_first_article_link();
		assertTrue(article_instance.verify_text_complete_reg_earn_tokens());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the videos section for claim token button not displayed");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_first_video_link();
		video_instance.close_completeregpopup();
		video_instance.verify_play_circle();
		assertTrue(video_instance.verify_videoendscreen_completeReg());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the lottery section for claim token button not displayed");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_lotterymenu();
		assertFalse(lottery_instance.verify_claimtokens_displayed());
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the weather section for claim token button not displayed");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_weather_our_pick();
		assertFalse(home_instance.verify_unclaimed_button());
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Do a search and verify the progress bar");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.search_term_on_collapse(generateRandomString(6));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		lb_instance.close_lb();
		switchToMainTab();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertEqualsInt(0, home_instance.get_daily_bonus_game_check_count());
		doRefresh();
		assertTrue(home_instance.verify_daily_bonus_game_lock_icon());
		assertTrue(home_instance.verify_complete_registration());
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify the complete registration and tokens");
		home_instance.click_complete_registration();
		register.completer_RegistrationSilveruser();
		assertTrue(home_instance.get_Tokens() >= 1000);
		step_validator(6, true);
	}
/*
	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 2, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the registration by entering an existing Silver user email", testName = "RT-04212:Silver user test scenario [D/T]")
	public void verify_registration_by_existing_silver_user() throws Exception {
		test_Method_details(2, "Verify the registration by entering an existing Silver user email");
		// Step 1
		test_step_details(1, "Verfiy the Registration form email validation by entering the existing email");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String[] user_details = centralpage.createSilverUser();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_Register();
		register.register_the_already_existing_user(user_details[0]);
		assertTrue(register.verify_existing_pwd_confirm_continue_button());
		assertEquals(register.get_existing_pwd_confirm_msg(), "We already have your email on file.");
		step_validator(1, true);
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 3, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the PCH Properties on Uni Nav bar for Silver user", testName = "RT-04212:Silver user test scenario [D/T]")
	public void verify_uni_nav_bar() throws Exception {
		test_Method_details(3, "Verify the PCH Properties on Uni Nav bar for Silver user");
		// Step 1
		test_step_details(1, "Create new Silver user and go to FP");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		invokeBrowser(centralpage.createSilverUser()[1]);
		lb_instance.close_welcome_optin_lb();
		home_instance.close_openx_banner();
		assertTrue(home_instance.verify_complete_registration());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Validate the PCH Properties on the Uni Nav bar");
		assertTrue(home_instance.verify_uninav_previous_arrow_disabled_status());
		assertTrue(home_instance.verify_uninav_next_arrow_enabled_status());
		LinkedList<String> prop_list = home_instance.get_pch_property_list_names();
		assertTrue(home_instance.verify_uninav_previous_arrow_enabled_status());
		assertTrue(home_instance.verify_uninav_next_arrow_disabled_status());
		assertTrue(prop_list.size() > 0);
		step_validator(2, true);
	}*/
}