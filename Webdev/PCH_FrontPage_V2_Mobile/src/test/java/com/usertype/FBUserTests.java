package com.usertype;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class FBUserTests extends BaseClass {

	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final HomePage home_instance = HomePage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();
	private final SERPage results = SERPage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 1, groups = {
			"MOBILE" }, description = "Verify the Claim button for Social user on all the pages", testName = "")
	public void verify_claim_button_on_all_pages_for_social_user() throws Exception {

		// Step 1
		test_step_details(1,
				"Create new FB user and verify the article page text - Complete Registration now to earn tokens for enjoying articles!");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		navigateTo(centralpage.createSocialUser()[1]);
		lb_instance.close_welcome_optin_lb();
		home_instance.close_openx_banner();
		home_instance.click_first_article_link();
		assertTrue(article_instance.verify_text_complete_reg_earn_tokens());

		// Step 2
		test_step_details(2, "Verify the videos section for claim token button not displayed");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_first_video_link();
		video_instance.play_video();
		video_instance.verify_play_circle();
		assertTrue(video_instance.verify_complete_reg_on_video_player());

		// Step 3
		test_step_details(3, "Verify the horoscope section for claim token button presence");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_our_pick_horoscope_tile();
		assertFalse(home_instance.verify_claim_button());

		// Step 4
		test_step_details(4, "Verify the lottery section for claim token button presence");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_lottery_menu();
		assertFalse(home_instance.verify_claim_button());

		// Step 5
		test_step_details(5, "Verify the weather section for claim token button presence");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_weather_menu();
		assertFalse(home_instance.verify_claim_button());

		// Step 6
		test_step_details(6, "Do a search and verify the progress bar");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.search(generateRandomString(6));
		assertTrue(results.verify_SERP_Completely());
		lb_instance.close_lb();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertEqualsInt(0, home_instance.get_daily_bonus_game_check_count());
		assertTrue(home_instance.verify_daily_bonus_game_lock_icon());
		assertTrue(home_instance.verify_complete_registration_link_uni_nav());

		// Step 7
		test_step_details(7, "Verify the complete registration form and tokens");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_complete_reg_link_uni_nav();
		assertTrue(register.verify_register_page());
		assertTrue(register.verify_email_field_uneditable_mode_for_social_reg_user());
		register.complete_registration_for_social_user();
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_level_up_lb();
		assertTrue(home_instance.get_token_amount_from_uni_nav() >= 1000);
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 2, groups = {
			"MOBILE" }, description = "Verify the Registration form field validation for Social user", testName = "")
	public void verify_the_registration_form_for_social_user() throws Exception {
		// Step 1
		test_step_details(1, "Create new FB user and go to FP with FB user URL and verify");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_email = centralpage.createSocialUser()[0];
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		home_instance.click_sign_in();
		home_instance.click_register();
		register.register_the_already_existing_user(user_email);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_level_up_lb();
		assertTrue(home_instance.get_token_amount_from_uni_nav() >= 1000);
	}
}
