package com.lottery;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HamburgerMenuPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.LotteryPage;
import com.pageobjects.MyAccount;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class LotteryTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final LotteryPage lottery_instance = LotteryPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final HamburgerMenuPage ham_menu_instance = HamburgerMenuPage.getInstance();
	private final MyAccount my_account_instance = MyAccount.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String page_title = "Lottery";
	private final String article_name = "Tokens / Lottery";

	@testId(test_id = "26360")
	@testCaseName(test_case_name = "B-28247 [m]Lotto Background for recognized users")
	@Test(priority = 1, description = "Verify Lottery page links and navigations - Unrecognized user", groups = {
			"MOBILE", "SANITY" }, testName = "B-28247 [m]Lotto Background for recognized users")
	public void verify_lottery_page_recognized_user() throws Exception {
		String lottery_desc = "For Lottery!" + generateRandomString(6);
		String lottery_token_amt = String.valueOf(rand(100, 500));

		// Step 1
		test_step_details(1, "Login to Joomla and change the Lottery token amount and description");
		switchToMainTab();
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(article_name);
		admin_instance.enter_text_field_element_by_label("Description", lottery_desc);
		admin_instance.enter_text_field_element_by_label("Tokens", lottery_token_amt);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));

		// Step 2
		test_step_details(2, "Register as a new user and verify the lottery page header");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_gs_overlay();
		homepage_instance.click_sign_in();
		sign_in_instance.click_register();
		register_instance.register_full_user_without_optin();
		homepage_instance.click_lottery_menu();
		assertEquals(getTitle(), page_title);
		assertIsStringContainsIgnoreCase(lottery_instance.get_lottery_page_header(), page_title);

		// Step 3
		test_step_details(3, "Verfiy the last draw date and next draw date of the First lottery section detail");
		assertTrue(lottery_instance.verify_lastdraw_currentdate());
		assertTrue(lottery_instance.verify_nextdraw_currentdate());
		assertTrue(homepage_instance.verify_claim_button());

		// Step 4
		test_step_details(4, "Claim the tokens and verify the token description and daily mission progress bar");
		assertEquals(homepage_instance.get_unclaimed_token_amount(), lottery_token_amt);
		homepage_instance.click_claim_button();
		assertTrue(homepage_instance.verify_claimed_button());
		assertIsStringContainsIgnoreCase(homepage_instance.get_unis_message(),
				msg_property_file_reader("lottery_activity_message", lottery_token_amt) + " " + lottery_desc);
		assertEqualsInt(homepage_instance.get_daily_bonus_game_check_count(), 1);

		// Step 5
		test_step_details(5, "Verify the claimed token amount in the Token History page");
		ham_menu_instance.click_token_history();
		my_account_instance.verify_token_transactions_details(lottery_desc, lottery_token_amt, 1);

	}

	@testId(test_id = "26358")
	@testCaseName(test_case_name = "B-28247 [m]Lotto Background for unrecognised users")
	@Test(priority = 2, description = "Verify Lottery page links and navigations - Unrecognized user", groups = {
			"MOBILE" }, testName = "B-28247 [m]Lotto Background for unrecognised users")
	public void verify_lottery_page_unrecognized_user() throws Exception {
		// Step 1
		test_step_details(1, "Navigate the Lottery page and verify the lottery page header");
		lb_instance.close_gs_overlay();
		homepage_instance.click_lottery_menu();
		assertEquals(getTitle(), page_title);
		assertIsStringContainsIgnoreCase(lottery_instance.get_lottery_page_header(), page_title);

		// Step 2
		test_step_details(2, "Verfiy the last draw date and next draw date of the First lottery section detail");
		assertTrue(lottery_instance.verify_lastdraw_currentdate());
		assertTrue(lottery_instance.verify_nextdraw_currentdate());
		assertFalse(homepage_instance.verify_claim_button());
	}

	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 3, description = "Verify the Page Ad's", testName = "")
	public void verify_ads() throws Exception {
		String dfp_ad_width = "300";
		String dfp_ad_height_1 = "250";
		String dfp_ad_height_2 = "50";

		// Step 1
		test_step_details(1, "Login as existing user and navigate to Lottery page");
		homepage_instance.click_sign_in();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.verify_Home();
		homepage_instance.click_lottery_menu();
		assertEquals(getTitle(), page_title);
		assertIsStringContainsIgnoreCase(lottery_instance.get_lottery_page_header(), page_title);

		// Step 2
		test_step_details(2, "Verify the presence of Page Ad's and Size");
		assertTrue(homepage_instance.verify_category_page_gpt_ad());
		assertEquals(homepage_instance.get_size_of_category_page_gpt_ad()[0], dfp_ad_width);
		if (homepage_instance.get_size_of_category_page_gpt_ad()[1] == dfp_ad_height_1
				|| homepage_instance.get_size_of_category_page_gpt_ad()[1] == dfp_ad_height_2) {
			assertTrue(false, "Ad height is mismatcehd");
		}
	}
}
