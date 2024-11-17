package com.lottery;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.HoroscopePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.LotteryPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class LotteryTests extends BaseClass {
	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final LotteryPage lottery_instance = LotteryPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final HoroscopePage horoscope_instance = HoroscopePage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();

	@testId(test_id = "RT-04336,RT-04337")
	@testCaseName(test_case_name = "Lottery PayOut page [D/T/M],Lottery Past Results page [D/T/M]")
	@Test(priority = 1, description = "Verify Lottery page links and navigations - Unrecognized user", groups = {
			"DESKTOP",
			"TABLET" }, testName = "RT-04336:Lottery PayOut page [D/T/M],RT-04337:Lottery Past Results page [D/T/M]")
	public void verify_lottery_page_unrecognized_user() throws Exception {
		test_Method_details(1, "Verify Lottery page links and navigations - Unrecognized user");
		// Step 1
		test_step_details(1, "Navigate the Lottery page and verify the lottery header");
		homepage_instance.click_lotterymenu();
		assertEqualsIgnoreCase(lottery_instance.get_lotterypage_headertext().trim(), "Lottery");
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Change Lottery location and verify the text");
		lottery_instance.clicklottrylocation();
		lottery_instance.clicklotterymap_michigan();
		assertEqualsIgnoreCase(lottery_instance.get_lottery_state_title().trim(), "Michigan Lottery");
		lottery_instance.clicklottrylocation();
		lottery_instance.clicklotterymap_florida();
		assertEqualsIgnoreCase(lottery_instance.get_lottery_state_title().trim(), "Florida Lottery");
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verfiy the last draw date and next draw date");
		assertTrue(lottery_instance.verify_lastdraw_currentdate());
		assertTrue(lottery_instance.verify_nextdraw_currentdate());
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verfiy past results page");
		lottery_instance.click_lottery_pastresultslnk();
		assertIsStringContains(lottery_instance.get_payout_header_text().toLowerCase(), "past results");
		assertEqualsIgnoreCase(lottery_instance.get_past_result_side(), "Past Results");
		assertEqualsInt(lottery_instance.get_lottery_past_results_colums().size(), 2);
		assertTrue(lottery_instance.verify_lastdraw_currentdate());
		assertTrue(lottery_instance.verify_nextdraw_currentdate());
		lottery_instance.clicklottrylocation();
		lottery_instance.clicklotterymap_michigan();
		assertEqualsIgnoreCase(lottery_instance.get_lottery_state_title().trim(), "Michigan Lottery");
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verfiy payouts page");
		lottery_instance.click_lottery_payoutslnk();
		assertIsStringContains(lottery_instance.get_payout_header_text().toLowerCase(), "payout");
		assertEqualsInt(lottery_instance.get_lottery_payouts_colums().size(), 4);
		assertTrue(lottery_instance.verify_lastdraw_currentdate());
		assertTrue(lottery_instance.verify_nextdraw_currentdate());
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verfiy payouts & past results navigation links");
		lottery_instance.click_payout_payouts_nav_lnk();
		assertIsStringContains(lottery_instance.get_payout_header_text().toLowerCase(), "past results");
		lottery_instance.click_payout_pastresults_nav_lnk();
		assertIsStringContains(lottery_instance.get_payout_header_text().toLowerCase(), "payout");
		step_validator(6, true);
	}

	@testId(test_id = "RT-04336,RT-04337")
	@testCaseName(test_case_name = "Lottery PayOut page [D/T/M],Lottery Past Results page [D/T/M]")
	@Test(priority = 2, description = "Verify Lottery page links and navigations - Recognized user", groups = {
			"DESKTOP",
			"TABLET" }, testName = "RT-04336:Lottery PayOut page [D/T/M],RT-04337:Lottery Past Results page [D/T/M]")
	public void verify_lottery_page_recognized_user() throws Exception {
		test_Method_details(2, "Verify Lottery page links and navigations - Recognized user");

		// Step 1
		test_step_details(1, "Modify the horoscope page tokens and save in Joomla");
		String article_name = "Tokens / Lottery";
		log.info("Login to Joomla and navigate to article name: " + article_name);
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", horoscope_instance.randomtokens().toString());
		admin_instance.save_article();
		waitForElement(admin_instance.get_text_field_element_by_label("Tokens"));
		int expected_tokens = Integer
				.parseInt(getAttribute(admin_instance.get_text_field_element_by_label("Tokens"), "value"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Create a Full Reg user and verify VIP activity");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		register.register_FullUser();
		lb_instance.close_welcome_optin_lb();
		int token_amount = homepage_instance.get_Tokens();
		homepage_instance.click_lotterymenu();
		assertEqualsInt(homepage_instance.get_unclaim_token_value(), expected_tokens);
		lottery_instance.click_claimtokens();
		assertTrue(homepage_instance.get_Tokens() > token_amount);
		homepage_instance.click_lotterymenu();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Claimed status and the Progress bar");
		assertIsStringContains(lottery_instance.get_lotterypage_headertext().trim(), "LOTTERY");
		assertIsStringContains(lottery_instance.get_lotterypage_headertext().trim(), "CLAIMED");
		doRefresh();
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Change Lottry location and verify the text");
		lottery_instance.clicklottrylocation();
		lottery_instance.clicklotterymap_michigan();
		assertEqualsIgnoreCase(lottery_instance.get_lottery_state_title().trim(), "Michigan Lottery");
		lottery_instance.clicklottrylocation();
		lottery_instance.clicklotterymap_florida();
		assertEqualsIgnoreCase(lottery_instance.get_lottery_state_title().trim(), "Florida Lottery");
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verfiy the last draw date and next draw date");
		assertTrue(lottery_instance.verify_lastdraw_currentdate());
		assertTrue(lottery_instance.verify_nextdraw_currentdate());
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verfiy past results page");
		lottery_instance.click_lottery_pastresultslnk();
		assertIsStringContains(lottery_instance.get_payout_header_text().toLowerCase(), "past results");
		assertEqualsIgnoreCase(lottery_instance.get_past_result_side(), "Past Results");
		assertEqualsInt(lottery_instance.get_lottery_past_results_colums().size(), 2);
		assertTrue(lottery_instance.verify_lastdraw_currentdate());
		assertTrue(lottery_instance.verify_nextdraw_currentdate());
		lottery_instance.clicklottrylocation();
		lottery_instance.clicklotterymap_michigan();
		assertEqualsIgnoreCase(lottery_instance.get_lottery_state_title().trim(), "Michigan Lottery");
		step_validator(6, true);

		// Step 7
		test_step_details(7, "Verfiy payouts page");
		lottery_instance.click_lottery_payoutslnk();
		assertIsStringContains(lottery_instance.get_payout_header_text().toLowerCase(), "payout");
		assertEqualsInt(lottery_instance.get_lottery_payouts_colums().size(), 4);
		assertTrue(lottery_instance.verify_lastdraw_currentdate());
		assertTrue(lottery_instance.verify_nextdraw_currentdate());
		step_validator(7, true);

		// Step 8
		test_step_details(8, "Verfiy payouts & past results navigation links");
		lottery_instance.click_payout_payouts_nav_lnk();
		assertIsStringContains(lottery_instance.get_payout_header_text().toLowerCase(), "past results");
		lottery_instance.click_payout_pastresults_nav_lnk();
		assertIsStringContains(lottery_instance.get_payout_header_text().toLowerCase(), "payout");
		step_validator(8, true);
	}

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 3, description = "Verify display of Lottery page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ads_on_lottery_page() throws IOException {
		test_Method_details(3, "Verify display of Lottery page Ad's");
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_width = "300";
		String right_rail_ad_2_height = "250";
		String inline_ad_width = "728";
		String inline_ad_height = "90";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		homepage_instance.click_SignIn();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the display of Inline Ad's and the Size");
		homepage_instance.click_lotterymenu();
		assertTrue(homepage_instance.verify_inline_gpt_ad());
		assertEquals(homepage_instance.get_size_of_inline_gpt_ad()[0], inline_ad_width);
		assertEquals(homepage_instance.get_size_of_inline_gpt_ad()[1], inline_ad_height);
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the display of Right Rail First Ad and the Size");
		assertTrue(homepage_instance.verify_right_rail_gpt_ad_1());
		assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[0], right_rail_ad_1_width);
		if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_1_height)) {
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_1_height);
		} else if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_2_height)) {
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_2_height);
		} else {
			assertTrue(false, "Right Rail First ad is mismatched in the height");
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the display of Right Rail Second Ad and the Size");
		assertTrue(homepage_instance.verify_right_rail_gpt_ad_2());
		assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_2()[0], right_rail_ad_2_width);
		assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_2()[1], right_rail_ad_2_height);
		step_validator(4, true);
	}
}
