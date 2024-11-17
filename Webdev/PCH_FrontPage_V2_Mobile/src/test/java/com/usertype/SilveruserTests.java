package com.usertype;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HamburgerMenuPage;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SilveruserTests extends BaseClass {

	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final HomePage home_instance = HomePage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final HamburgerMenuPage ham_menu_instance = HamburgerMenuPage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 1, groups = {
			"MOBILE" }, description = "Verify the Claim button for Silver user on all the pages", testName = "")
	public void verify_claim_button_on_all_pages_for_silver_user() throws Exception {

		// Step 1
		test_step_details(1, "Create new Silver user and go to FP and verify the article page");
		navigateTo(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String[] email_url = centralpage.createSilverUser();
		navigateTo(email_url[1]);
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
		sleepFor(5);
		assertTrue(serp_instance.verify_SERP_Completely());
		lb_instance.close_lb();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertEqualsInt(0, home_instance.get_daily_bonus_game_check_count());
		assertTrue(home_instance.verify_daily_bonus_game_lock_icon());
		assertTrue(home_instance.verify_complete_registration_link_uni_nav());

		// Step 7
		test_step_details(7, "Verify the complete registration form and tokens");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home_instance.click_complete_reg_link_uni_nav();
		register.complete_registration_for_silver_user();
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_level_up_lb();
		assertTrue(home_instance.verify_Home());
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 2, groups = {
			"MOBILE" }, description = "Verify the registration by entering an existing Silver user email", testName = "")
	public void verify_registration_by_existing_silver_user() throws Exception {
		// Step 1
		test_step_details(1, "Create new Silver user and go to FP with Silver user URL and verify");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_email = centralpage.createSilverUser()[0];
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		ham_menu_instance.click_sign_in();
		home_instance.click_register();
		register.register_the_already_existing_user(user_email);
		register.verify_silver_user_continue_password_screen(user_email);
		register.click_silver_user_continue_button();
		register.verify_silver_user_password_reset_email_screen();
		register.click_silver_user_reset_password_email_button();
		register.click_silver_user_continue_button();
		assertFalse(home_instance.verify_Home());
	}
}
