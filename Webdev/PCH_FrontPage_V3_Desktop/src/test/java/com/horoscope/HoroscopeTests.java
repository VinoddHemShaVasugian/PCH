package com.horoscope;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.HoroscopePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class HoroscopeTests extends BaseClass {

	private final AccountsRegisterPage register = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final HoroscopePage horoscope_instance = HoroscopePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsSignInPage sign_in_instance = AccountsSignInPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private String article_name = "Tokens / Horoscope";

	/*@testId(test_id = "32352")
	@testCaseName(test_case_name = "FP Redesign-Horoscope")
	@Test(priority = 1, description = "Verify Horoscope page tokens and subsections - Recognized user", groups = {
			"DESKTOP", "TABLET" }, testName = "32352:FP Redesign-Horoscope")
	public void verify_horoscope_page_recognized_user() throws Exception {
		String horoscope_tokens = horoscope_instance.randomtokens().toString();

		// Step 1
		test_step_details(1, "Modify the horoscope page tokens and save in Joomla");
		log.info("Login to Joomla and navigate to article name: " + article_name);
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(article_name);
		admin_instance.enter_text_field_element_by_label("Tokens", horoscope_tokens);
		admin_instance.save_and_close_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_tokens"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));

		// Step 2
		test_step_details(2, "Create a Full Reg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		register.register_FullUser(getCurrentDate("dd"), getMonthWithOffset(2, "MMMM"), getYearWithOffset(-15, "YYYY"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());

		// Step 3
		test_step_details(3, "Claim tokens in horoscope page and verify the Progress bar and verify VIP activity");
		int token_amount = homepage_instance.get_Tokens();
		homepage_instance.clic_new_VIP_logo(5);
		String beforeActivity= homepage_instance.get_vip_msg();
		homepage_instance.click_horoscope_menu();
		assertEqualsInt(homepage_instance.get_unclaim_token_value(), Integer.parseInt(horoscope_tokens));
		horoscope_instance.click_claimtokens();
		assertTrue(homepage_instance.get_Tokens() > token_amount);
		doRefresh();
		homepage_instance.clic_new_VIP_logo(5);
		String afterActivity= homepage_instance.get_vip_msg();
		assertNotEqualsIgnoreCase(beforeActivity, afterActivity);
		int daily_bouns_game_count = homepage_instance.get_daily_bonus_game_check_count();
		assertEqualsInt(1, daily_bouns_game_count);
		assertTrue(homepage_instance.verify_daily_bonus_game_lock_icon());

		// Step 4
		test_step_details(4, "Verify the Horoscope page links and the given month details");
		assertIsStringContainsIgnoreCase(horoscope_instance.get_horoscope_month(),
				getMonthWithOffset(2, "MMMM").substring(0, 3));
		assertTrue(horoscope_instance.verify_today_lbl_displayed());
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("today's overview"));
		horoscope_instance.click_yesterdaylnk();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("yesterday"));
		horoscope_instance.click_tommorow_lnk();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("tomorrow"));
		horoscope_instance.click_outlook_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("2018 Outlook"));
		horoscope_instance.click_today_lnk();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("today's overview"));

		// Step 5
		test_step_details(5, "Verify the Horoscope page subsections");
		assertTrue(horoscope_instance.getSubsectionsList().size() == 5);
		horoscope_instance.click_gemini();
		assertTrue(horoscope_instance.verify_overviewtext().equalsIgnoreCase("Gemini"));
	}

	@testId(test_id = "32352")
	@testCaseName(test_case_name = "FP Redesign-Horoscope")
	@Test(priority = 2, description = "Verify Horoscope page tokens and subsections - Unrecognized user", groups = {
			"DESKTOP", "TABLET" }, testName = "32352:FP Redesign-Horoscope")
	public void verify_horoscope_page_unrecognized_user() throws Exception {
		// Step 1
		test_step_details(1, "Verify the Claim button on Horoscope page for Un Recognised user");
		homepage_instance.click_horoscope_menu();
		assertFalse(homepage_instance.verify_unclaimed_button());
		assertFalse(homepage_instance.verify_claimed_button());

		// Step 2
		test_step_details(2, "Verify the Horoscope page links for Un Recognised user");
		assertTrue(horoscope_instance.verify_today_lbl_displayed());
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("today's overview"));
		horoscope_instance.click_yesterdaylnk();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("yesterday"));
		horoscope_instance.click_tommorow_lnk();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("tomorrow"));
		horoscope_instance.click_outlook_link();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("2018 Outlook"));
		horoscope_instance.click_today_lnk();
		assertTrue(horoscope_instance.verify_overview_displayed().equalsIgnoreCase("today's overview"));

		// Step 3
		test_step_details(3, "Verify the Horoscope page subsections");
		assertTrue(horoscope_instance.getSubsectionsList().size() == 5);
		horoscope_instance.click_gemini();
		assertTrue(horoscope_instance.verify_overviewtext().equalsIgnoreCase("Gemini"));
	}*/

	@testId(test_id = "RT-04391")
	@testCaseName(test_case_name = "[D/T] FP: Verify ads in across the site")
	@Test(priority = 3, description = "Verify display of Horoscope page Ad's", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04391:[D/T] FP: Verify ads in across the site")
	public void verify_ads_on_horoscope_page() {
		String right_rail_ad_1_width = "300";
		String right_rail_ad_1_height = "600";
		String right_rail_ad_2_height = "250";

		// Step 1
		test_step_details(1, "Login as a Recognised user");
		homepage_instance.click_SignIn();
		sign_in_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.close_openx_banner();
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the display of Right Rail Ad's and the Size");
		homepage_instance.click_horoscope_menu();
		assertTrue(homepage_instance.verify_right_rail_gpt_ad_1());
		assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[0], right_rail_ad_1_width);
		if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_1_height)) {
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_1_height);
		} else if (homepage_instance.get_size_of_right_rail_gpt_ad_1()[1].equals(right_rail_ad_2_height)) {
			assertEquals(homepage_instance.get_size_of_right_rail_gpt_ad_1()[1], right_rail_ad_2_height);
		} else {
			assertTrue(false, "Right Rail First ad is mismatched in the height");
		}
	}
}
