package com.lightbox;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ProgressBarLightBox extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();

	@testId(test_id = "25018")
	@testCaseName(test_case_name = "B_25817_Verify daily mission counter Lightbox_Mobile")
	@Test(priority = 1, description = "Verify the Progress Bar LightBox for Recognised User", groups = {
			"MOBILE" }, testName = "25018:B_25817_Verify daily mission counter Lightbox_Mobile")
	public void verify_progress_bar_lb() throws Exception {

		// Step 1
		test_step_details(1, "Login as recognised user to FP");
		homepage_instance.click_sign_in();
		signin_instance.click_register();
		register_instance.register_full_user_with_optin();
		assertTrue(lb_instance.verify_welcome_lb_learn_more_link());
		assertTrue(lb_instance.verify_lb_accept_button());

		// Step 2
		test_step_details(2, "Click on Progress bar and verify the Progress bar Light Box");
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		homepage_instance.click_daily_bonus_game_info_icon();
		assertTrue(homepage_instance.verify_daily_bonus_game_info_window());
		homepage_instance.close_daily_bonus_game_info_window();
		assertFalse(homepage_instance.verify_daily_bonus_game_info_window());

		// Step 3
		test_step_details(3, "Do some action and verify the progress bar update amd nfo window");
		homepage_instance.click_first_video_link();
		video_instance.verify_video_claimed_status();
		assertEqualsInt(homepage_instance.get_daily_bonus_game_check_count(), 1);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		homepage_instance.click_daily_bonus_game_info_icon();
		assertTrue(homepage_instance.verify_daily_bonus_game_info_window());
		homepage_instance.close_daily_bonus_game_info_window();
		assertFalse(homepage_instance.verify_daily_bonus_game_info_window());
	}
}
