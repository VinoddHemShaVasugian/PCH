package com.miscellanous;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServicesPage;
import com.pageobjects.HomePage;
import com.pageobjects.InfoPage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class InfoTests extends BaseClass {
	private final HomePage home = HomePage.getInstance();
	private final InfoPage info = InfoPage.getInstance();
	private final CentralServicesPage centralpage = CentralServicesPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String uninav_article_name = "Uninav Settings";
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	/**
	 * UnPublishing the custom UniNav article to make visible all the info pages
	 */
	@BeforeClass
	public void unpublish_custom_uninav_article() {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(uninav_article_name);
			admin_instance.unpublish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in Unpublishing the custom UniNav article :" + e.getLocalizedMessage());
		}
	}

	/**
	 * Publishing the custom UniNav article to revert back to existing state
	 */
	@AfterClass
	public void publish_custom_uninav_article() {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(uninav_article_name);
			admin_instance.unpublish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in publishing the custom UniNav article :" + e.getLocalizedMessage());
		}
	}

	@testId(test_id = "34295,25001,25446,25004,24998,24995,33691")
	@testCaseName(test_case_name = "[M Redesign] Info Pages-Rules-Facts, InstructionalPages_AboutFrontpage_Mobile,InstructionalPages_CreateEarnTokensPage_Mobile,InstructionalPages_DosAndDonts_Mobile,InstructionalPages_HowToSearch_Mobile,InstructionalPages_HowToWinPage_Mobile,Contest Details Link in Header [M]")
	@Test(priority = 1, description = "Verify the Info pages for the recongnised user on all pages", testName = "34295:[M Redesign] Info Pages-Rules-Facts,25001:InstructionalPages_AboutFrontpage_Mobile,25004:InstructionalPages_DosAndDonts_Mobile,25446:InstructionalPages_CreateEarnTokensPage_Mobile,24998:InstructionalPages_HowToSearch_Mobile,24995:InstructionalPages_HowToWinPage_Mobile,33691:Contest Details Link in Header [M]")
	public void verify_info_page() throws Exception {
		// Step 1
		test_step_details(1, "Create a Full Reg. user");
		home.click_sign_in();
		signin_instance.click_register();
		register_instance.register_full_user_with_optin();
		lb_instance.close_welcome_optin_lb();
		test_step_details(1, "verify the info links on homepage");
		info.click_about_page();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "About_FP")));
		info.click_do_donts();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Do_Donts")));
		info.click_how_to_search();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "How_To_Search")));
		info.click_earn_tokens();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Earn_Tokens")));
		info.click_ways_to_win();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Ways_To_Win")));
		info.click_official_rules();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Official_Rules")));
		switchToMainTab();
		info.click_sweepstake_facts();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Sweeps")));
		switchToMainTab();
		info.click_contest_details();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Contest_Details")));
		switchToMainTab();

		// Step 2
		test_step_details(2, "Verify the info links on all the Category pages");
		LinkedList<String> url_list = home.get_main_catagory_menu_url_list();
		for (String url : url_list) {
			if (!(url.endsWith("/") || url.endsWith("everydaylife") || url.endsWith("horoscope"))) {
				invokeBrowser(url);
				info.click_about_page();
				assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "About_FP")));
				info.click_do_donts();
				assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Do_Donts")));
				info.click_how_to_search();
				assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "How_To_Search")));
				info.click_earn_tokens();
				assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Earn_Tokens")));
				info.click_ways_to_win();
				assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Ways_To_Win")));
				info.click_official_rules();
				switchToNewTab();
				assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Official_Rules")));
				switchToMainTab();
				info.click_sweepstake_facts();
				switchToNewTab();
				assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Sweeps")));
				switchToMainTab();
				info.click_contest_details();
				switchToNewTab();
				assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Contest_Details")));
				switchToMainTab();
				// Break statement will stop the execution by verify the Story
				// log details for the first category page of the menu. Added to
				// Save the execution time.
				break;
			}
		}

		// Step 3
		test_step_details(3, "Verify the info links on story page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		home.click_first_article_link();
		info.click_about_page();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "About_FP")));
		info.click_do_donts();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Do_Donts")));
		info.click_how_to_search();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "How_To_Search")));
		info.click_earn_tokens();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Earn_Tokens")));
		info.click_ways_to_win();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Ways_To_Win")));
		info.click_official_rules();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Official_Rules")));
		switchToMainTab();
		info.click_sweepstake_facts();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Sweeps")));
		switchToMainTab();
		info.click_contest_details();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Contest_Details")));
		switchToMainTab();

		// Step 4
		test_step_details(4, "verify the info pages on subcategory page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + "entertainment/movies");
		info.click_about_page();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "About_FP")));
		info.click_do_donts();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Do_Donts")));
		info.click_how_to_search();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "How_To_Search")));
		info.click_earn_tokens();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Earn_Tokens")));
		info.click_ways_to_win();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Ways_To_Win")));
		info.click_official_rules();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Official_Rules")));
		switchToMainTab();
		info.click_sweepstake_facts();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Sweeps")));
		switchToMainTab();
		info.click_contest_details();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Contest_Details")));
		switchToMainTab();
	}

	@Test(priority = 2, description = "Verify the Info pages for the Mini Reg user")
	public void verify_info_page_for_mini_reg_user() throws Exception {
		// Step 1
		test_step_details(1, " verify the info pages for minireg user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		invokeBrowser(centralpage.create_mini_reg_user()[1]);
		lb_instance.close_welcome_optin_lb();
		info.click_about_page();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "About_FP")));
		info.click_do_donts();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Do_Donts")));
		info.click_how_to_search();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "How_To_Search")));
		info.click_earn_tokens();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Earn_Tokens")));
		info.click_ways_to_win();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Ways_To_Win")));
		info.click_official_rules();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Official_Rules")));
		switchToMainTab();
		info.click_sweepstake_facts();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Sweeps")));
		switchToMainTab();
		info.click_contest_details();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Contest_Details")));
		switchToMainTab();
	}

	@Test(priority = 3, description = "Verify the Info pages for the Silver user")
	public void verify_info_page_for_silver_user() throws Exception {
		// Step 1
		test_step_details(1, "Verify the info pages for silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		invokeBrowser(centralpage.create_silver_user()[1]);
		lb_instance.close_welcome_optin_lb();
		info.click_about_page();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "About_FP")));
		info.click_do_donts();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Do_Donts")));
		info.click_how_to_search();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "How_To_Search")));
		info.click_earn_tokens();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Earn_Tokens")));
		info.click_ways_to_win();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Ways_To_Win")));
		info.click_official_rules();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Official_Rules")));
		switchToMainTab();
		info.click_sweepstake_facts();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Sweeps")));
		switchToMainTab();
		info.click_contest_details();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Contest_Details")));
		switchToMainTab();
	}

	@Test(priority = 4, description = "Verify the Info pages for the Social user")
	public void verify_info_page_social_user() throws Exception {
		// Step 1
		test_step_details(1, "verify the info pages for fb user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		invokeBrowser(centralpage.create_social_user()[1]);
		lb_instance.close_welcome_optin_lb();
		info.click_about_page();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "About_FP")));
		info.click_do_donts();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Do_Donts")));
		info.click_how_to_search();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "How_To_Search")));
		info.click_earn_tokens();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Earn_Tokens")));
		info.click_ways_to_win();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Ways_To_Win")));
		info.click_official_rules();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Official_Rules")));
		switchToMainTab();
		info.click_sweepstake_facts();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Sweeps")));
		switchToMainTab();
		info.click_contest_details();
		switchToNewTab();
		assertEquals(getCurrentUrl(), (xmlReader(ENVIRONMENT, "Contest_Details")));
		switchToMainTab();
	}
}
