package com.miscellanous;

import java.util.LinkedList;

import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.FooterPage;
import com.pageobjects.HomePage;
import com.pageobjects.SERPage;
import com.pageobjects.VideoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class FooterTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final FooterPage footer_instance = FooterPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final VideoPage video_instance = VideoPage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();

	private final String expected_copyright_text = "Copyright © 2004-2018 Publishers Clearing HouseAll trademarks and registered trademarks appearing on this site are the property of their respective owners.Said owners do not endorse nor are they affiliated with Publishers Clearing House or its promotions.";

	@testId(test_id = "27392")
	@testCaseName(test_case_name = "B-29755 Frontpage - Addition of the BBB Logo to footer")
	@Test(priority = 1, description = "Verify the BBB logo redirection from Footer", groups = { "DESKTOP",
			"TABLET" }, testName = "27392:B-29755 Frontpage - Addition of the BBB Logo to footer")
	public void verify_BBB_logo() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the BBB logo redirection from SERP page");
		homepage_instance.search(generateRandomString(5));
		switchToNewTab();
		serp_instance.verify_SERP_Completely();
		close_all_switch_to_currently_focus_window();
		footer_instance.click_bbb_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BBB_Url"));
		switchToMainTab();

		// Step 3
		test_step_details(3, "Verify the BBB logo redirection from Footer menu of HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		footer_instance.click_bbb_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BBB_Url"));
		switchToMainTab();

		// Step 4
		test_step_details(4, "Verify the BBB logo redirection from Footer menu of Article page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_article_link();
		article_instance.verify_next_article_link();
		footer_instance.click_bbb_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BBB_Url"));
		switchToMainTab();

		// Step 5
		test_step_details(5, "Verify the BBB logo redirection from Footer menu of Video page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_video_menu();
		video_instance.verify_video_player();
		footer_instance.click_bbb_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BBB_Url"));
		switchToMainTab();

		// Step 6
		test_step_details(6, "Verify the BBB logo redirection from Footer menu of Sub-Catagory page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		LinkedList<String> url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				footer_instance.click_bbb_logo();
				switchToNewTab();
				assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BBB_Url"));
				switchToMainTab();
				// Below Break command will limit the execution to verify the
				// first sub-category page. Commenting the Break will execute
				// the
				// above entire verification for all the Sub-Category pages
				break;
			}
		}
	}

	@testId(test_id = "32119")
	@testCaseName(test_case_name = "[D][T] FP_Redesign - B-42004 Footer integration")
	@Test(priority = 2, description = "Verify the TRUSTe logo redirection from Footer", groups = { "DESKTOP",
			"TABLET" }, testName = "32119:[D][T] FP_Redesign - B-42004 Footer integration")
	public void verify_TRUSTe_logo() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the TRUSTe logo redirection from SERP page");
		homepage_instance.search("shoes");
		switchToNewTab();
		serp_instance.verify_SERP_Completely();
		close_all_switch_to_currently_focus_window();
		footer_instance.click_truste_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "TRUSTe_Url"));
		switchToMainTab();

		// Step 3
		test_step_details(3, "Verify the TRUSTe logo redirection from Footer menu of HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		footer_instance.click_truste_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "TRUSTe_Url"));
		switchToMainTab();

		// Step 4
		test_step_details(4, "Verify the TRUSTe logo redirection from Footer menu of Article page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_article_link();
		article_instance.verify_next_article_link();
		footer_instance.click_truste_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "TRUSTe_Url"));
		switchToMainTab();

		// Step 5
		test_step_details(5, "Verify the TRUSTe logo redirection from Footer menu of Video page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_video_menu();
		video_instance.verify_video_player();
		footer_instance.click_truste_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "TRUSTe_Url"));
		switchToMainTab();

		// Step 6
		test_step_details(6, "Verify the TRUSTe logo redirection from Footer menu of Sub-Catagory page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		LinkedList<String> url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				footer_instance.click_truste_logo();
				switchToNewTab();
				assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "TRUSTe_Url"));
				switchToMainTab();
				// Below Break command will limit the execution to verify the
				// first sub-category page. Commenting the Break will execute
				// the
				// above entire verification for all the Sub-Category pages
				break;
			}
		}
	}

	@testId(test_id = "23993")
	@testCaseName(test_case_name = "CopyrightInFooter_Mobile")
	@Test(priority = 3, description = "Verify the Copyright information from Footer", groups = { "DESKTOP",
			"TABLET" }, testName = "23993:CopyrightInFooter_Mobile")
	public void verify_copyright_text() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_sign_in();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());

		// Step 2
		test_step_details(2, "Verify the Copyright information from SERP page");
		homepage_instance.search(generateRandomString(5));
		switchToNewTab();
		serp_instance.verify_SERP_Completely();
		String copyright_text = footer_instance.get_copyright_text();
		assertIsStringContains(expected_copyright_text, copyright_text);
		switchToMainTab();

		// Step 3
		test_step_details(3, "Verify the Copyright information from Footer menu of HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		copyright_text = footer_instance.get_copyright_text();
		assertIsStringContains(copyright_text, expected_copyright_text);

		// Step 4
		test_step_details(4, "Verify the Copyright information from Footer menu of Article page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_article_link();
		article_instance.verify_next_article_link();
		copyright_text = footer_instance.get_copyright_text();
		assertIsStringContains(copyright_text, expected_copyright_text);

		// Step 5
		test_step_details(5, "Verify the Copyright information from Footer menu of Video page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_video_menu();
		video_instance.verify_video_player();
		copyright_text = footer_instance.get_copyright_text();
		assertIsStringContains(copyright_text, expected_copyright_text);

		// Step 6
		test_step_details(6, "Verify the Copyright information from Footer menu of Sub-Catagory page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		LinkedList<String> url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				copyright_text = footer_instance.get_copyright_text();
				assertIsStringContains(copyright_text, expected_copyright_text);
				// Below Break command will limit the execution to verify the
				// first sub-category page. Commenting the Break will execute
				// the
				// above entire verification for all the Sub-Category pages
				break;
			}
		}
	}
}
