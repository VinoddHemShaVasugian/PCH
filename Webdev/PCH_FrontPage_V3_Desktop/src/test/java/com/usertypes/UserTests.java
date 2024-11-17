package com.usertypes;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
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

public class UserTests extends BaseClass {

	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final HomePage home_instance = HomePage.getInstance();
	private final VideoLandingPage video_instance = VideoLandingPage.getInstance();
	private final LotteryPage lottery_instance = LotteryPage.getInstance();
	private final SERPage results = SERPage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final AccountsSignInPage signin = AccountsSignInPage.getInstance();

	@testId(test_id = "RT-04214")
	@testCaseName(test_case_name = "Social user test scenario [D/T]")
	@Test(priority = 1, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Claim button for Social user on all the pages", testName = "RT-04214:Social user test scenario [D/T]")
	public void verify_claim_button_on_all_pages_for_social_user() throws Exception {
		test_Method_details(1, "Verify the Claim button for Social user on all the pages");

		// Step 1
		test_step_details(1,
				"Create new FB user and verify the article page text - Complete Registration now to earn tokens for enjoying articles!");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSocialUser()[1]);
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
		assertTrue(results.verify_SERP_Completely());
//		lb_instance.close_lb();
		switchToMainTab();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_lb();
		assertEqualsInt(0, home_instance.get_daily_bonus_game_check_count());
		doRefresh();
		sleepFor(3);
		assertTrue(home_instance.verify_daily_bonus_game_lock_icon());
		assertTrue(home_instance.verify_complete_registration());
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify the complete registration and tokens");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_complete_registration();
		assertTrue(register.verify_AccountsRegisterScreen());
		register.complete_RegistrationForSocialUser();
		assertTrue(home_instance.get_Tokens() >= 1000);
		step_validator(6, true);
	}

	@testId(test_id = "RT-04214")
	@testCaseName(test_case_name = "Social user test scenario [D/T]")
	@Test(priority = 2, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Registration form field validation for Social user", testName = "RT-04214:Social user test scenario [D/T]")
	public void verify_the_registration_form_for_social_user() throws Exception {
		test_Method_details(2, "Verify the Registration form field validation for Social user");
		// Step 1
		test_step_details(1, "Create new FB user and go to FP with FB user URL and verify");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSocialUser()[1]);
		lb_instance.close_welcome_optin_lb();
		assertTrue(home_instance.verify_complete_registration());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Close the LB and clik on complete registration and verify the Registration form");
		home_instance.click_complete_registration();
		assertTrue(register.verify_AccountsRegisterScreen());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Complete the Registration and verfiy the tokens");
		register.complete_RegistrationForSocialUser();
		switchToMainTab();
		doRefresh();
		assertTrue(home_instance.get_Tokens() >= 1000);
		step_validator(3, true);
	}
	
	@testId(test_id = "RT-04210")
	@testCaseName(test_case_name = "Mini Reg user test scenario [D/T]")
	@Test(priority = 3, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Claim button on all pages for the Mini Reg user", testName = "RT-04210:Mini Reg user test scenario [D/T]")
	public void verify_claim_button_on_all_pages_for_mini_reg_user() throws Exception {
		test_Method_details(3, "Verify the Claim button on all pages for the Mini Reg user");

		// Step 1
		test_step_details(1,
				"Create new Mini Reguser and go to FP and verify the article page text - Complete Registration now to earn tokens for enjoying articles!");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createMiniReguser()[1]);
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
		assertTrue(results.verify_SERP_Completely());
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
		signin.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(register.verifyEmailPasswordNotPresent());
		register.complete_RegistrationForMiniRegUser();
		assertTrue(home_instance.get_Tokens() >= 1000);
		step_validator(6, true);
	}
	
	@testId(test_id = "RT-04212")
	@testCaseName(test_case_name = "Silver user test scenario [D/T]")
	@Test(priority = 4, groups = { "DESKTOP",
			"TABLET" }, description = "Verify the Claim button for Silver user on all the pages", testName = "RT-04212:Silver user test scenario [D/T]")
	public void verify_claim_button_on_all_pages_for_silver_user() throws Exception {
		test_Method_details(4, "Verify the Claim button for Silver user on all the pages");

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
		assertTrue(results.verify_SERP_Completely());
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
}
