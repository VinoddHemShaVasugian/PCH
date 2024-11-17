package com.usertype;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HamburgerMenuPage;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class MiniRegUserTests extends BaseClass {

	private final CentralServices_Page centralpage = CentralServices_Page.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final HomePage home_instance = HomePage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final HamburgerMenuPage ham_instance = HamburgerMenuPage.getInstance();
	private final AccountsSignInPage signin = AccountsSignInPage.getInstance();
	private final SERPage results = SERPage.getInstance();
	boolean step_status;

	@testId(test_id = "23486")
	@testCaseName(test_case_name = "CompleteRegistrationMiniReg_Mobiles")
	@Test(priority = 1, groups = {
			"MOBILE" }, description = "Verify the Claim button on all pages for the Mini Reg user", testName = "23486:CompleteRegistrationMiniReg_Mobiles")
	public void verify_claim_button_on_all_pages_for_mini_reg_user() throws Exception {

		// Step 1
		test_step_details(1,
				"Create new Mini Reguser and go to FP and verify the article page text - Complete Registration now to earn tokens for enjoying articles!");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		invokeBrowser(centralpage.createMiniReguser()[1]);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_gs_overlay();
		home_instance.click_first_article_link();
		scrollToBottomOfPage();
		assertTrue(home_instance.verify_bottom_complete_reg_msg_of_non_fully_reg_users());
		assertEquals(home_instance.get_bottom_complete_reg_msg_of_non_fully_reg_users(),
				msg_property_file_reader("complete_reg_msg"));

		// Step 2
		test_step_details(2, "Verify the videos section for claim token button presence");
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
		sleepFor(5);
		lb_instance.close_lb();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		assertEqualsInt(0, home_instance.get_daily_bonus_game_check_count());
		assertTrue(home_instance.verify_daily_bonus_game_lock_icon());
		assertTrue(home_instance.verify_complete_registration_link_uni_nav());

		// Step 7
		test_step_details(7, "Verify the complete registration and tokens");
		home_instance.click_complete_reg_link_uni_nav();
		signin.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(register.verify_email_password_field_absence_for_mini_reg_user());
		register.complete_registration_for_mini_reg_user();
		lb_instance.close_bronze_level_up_lb();
		lb_instance.close_level_up_lb();
		assertTrue(home_instance.get_token_amount_from_uni_nav() >= 1000);
	}

	@testId(test_id = "23486")
	@testCaseName(test_case_name = "CompleteRegistrationMiniReg_Mobiles")
	@Test(priority = 2, groups = {
			"MOBILE" }, description = "Verify the Registration form field validation for Mini Reg user", testName = "23486:CompleteRegistrationMiniReg_Mobiles")
	public void verify_the_registration_form_for_mini_reg_user() throws Exception {
		test_step_details(1,
				"Create new Mini Reguser and go to FP and try to register with the same ID and verify tokens after registration");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_email = centralpage.createMiniReguser()[0];
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		ham_instance.click_sign_in();
		home_instance.click_register();
		register.register_the_already_existing_user(user_email);
		lb_instance.close_welcome_optin_lb();
		lb_instance.close_level_up_lb();
		assertTrue(home_instance.get_token_amount_from_uni_nav() >= 1000);
	}
}
