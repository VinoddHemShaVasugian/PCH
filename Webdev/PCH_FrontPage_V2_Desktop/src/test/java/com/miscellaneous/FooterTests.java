package com.miscellaneous;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.FooterPage;
import com.pageobjects.HomePage;
import com.pageobjects.SERPage;
import com.pageobjects.SubCategoryPage;
import com.pageobjects.VideoLandingPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class FooterTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final FooterPage footer_instance = FooterPage.getInstance();
	private final ArticlePage article_instance = ArticlePage.getInstance();
	private final VideoLandingPage video_instance = VideoLandingPage.getInstance();
	private final SubCategoryPage sub_category_instance = SubCategoryPage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();

	private final String expected_copyright_text = "Copyright © 2004-2019 Publishers Clearing HouseAll trademarks and registered trademarks appearing on this site are the property of their respective owners.Said owners do not endorse nor are they affiliated with Publishers Clearing House or its promotions.";

	@testId(test_id = "32119")
	@testCaseName(test_case_name = "[D][T] FP_Redesign - B-42004 Footer integration")
	@SuppressWarnings("unused")
	@Test(priority = 1, description = "Verify the Footer Menu Category navigations", groups = { "DESKTOP",
			"TABLET" }, testName = "32119:[D][T] FP_Redesign - B-42004 Footer integration")
	public void footer_menu_navigation() throws Exception {
		test_Method_details(1, "Verify the Footer Menu Category navigations");
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Footer menu from HomePage");
		homepage_instance.close_openx_banner();
		String current_url = getCurrentUrl();
		LinkedList<String> footer_category_urls = footer_instance.get_footer_menu_category_urls();
		List<WebElement> footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			moveToElement(footer_menu.get(count));
			footer_menu.get(count).click();
			assertEquals(getCurrentUrl(), footer_category_urls.get(count));
			invokeBrowser(current_url);
			homepage_instance.waitForGivenPageTitle("Frontpage", 10);
			footer_category_urls = footer_instance.get_footer_menu_category_urls();
			footer_menu = footer_instance.get_footer_menu();
			break;
		}
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Footer menu from Article page");
		invokeBrowser(current_url);
		homepage_instance.click_first_article_link();
		article_instance.verify_next_article_presence();
		footer_category_urls = footer_instance.get_footer_menu_category_urls();
		footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			moveToElement(footer_menu.get(count));
			footer_menu.get(count).click();
			assertEquals(getCurrentUrl(), footer_category_urls.get(count));
			invokeBrowser(current_url);
			homepage_instance.click_first_article_link();
			article_instance.verify_next_article_presence();
			footer_category_urls = footer_instance.get_footer_menu_category_urls();
			footer_menu = footer_instance.get_footer_menu();
			break;
		}
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Footer menu from Video page");
		invokeBrowser(current_url);
		homepage_instance.click_first_video_link();
		video_instance.verify_fa_videosection();
		footer_category_urls = footer_instance.get_footer_menu_category_urls();
		footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			moveToElement(footer_menu.get(count));
			footer_menu.get(count).click();
			assertEquals(getCurrentUrl(), footer_category_urls.get(count));
			invokeBrowser(current_url);
			homepage_instance.click_first_video_link();
			video_instance.verify_fa_videosection();
			footer_category_urls = footer_instance.get_footer_menu_category_urls();
			footer_menu = footer_instance.get_footer_menu();
			break;
		}
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the Footer menu from Sub-Catagory page");
		invokeBrowser(current_url);
		LinkedList<String> url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				footer_category_urls = footer_instance.get_footer_menu_category_urls();
				footer_menu = footer_instance.get_footer_menu();
				for (int count = 0; count < footer_menu.size(); count++) {
					moveToElement(footer_menu.get(count));
					footer_menu.get(count).click();
					assertEquals(getCurrentUrl(), footer_category_urls.get(count));
					invokeBrowser(url);
					sub_category_instance.verify_article_without_image_presence();
					footer_category_urls = footer_instance.get_footer_menu_category_urls();
					footer_menu = footer_instance.get_footer_menu();
					break;
				}
				break;
			}
			step_validator(5, true);
		}

		// Step 6
		test_step_details(6, "Verify the Footer menu on SERP page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.search_term_on_collapse(generateRandomString(5));
		switchToNewTab();
		assertTrue(serp_instance.verify_SERP_Completely());
		current_url = getCurrentUrl();
		footer_category_urls = footer_instance.get_footer_menu_category_urls();
		footer_menu = footer_instance.get_footer_menu();
		for (int count = 0; count < footer_menu.size(); count++) {
			moveToElement(footer_menu.get(count));
			footer_menu.get(count).click();
			assertEquals(getCurrentUrl(), footer_category_urls.get(count));
			invokeBrowser(current_url);
			footer_category_urls = footer_instance.get_footer_menu_category_urls();
			footer_menu = footer_instance.get_footer_menu();
			break;
		}
		step_validator(6, true);
	}

	@testId(test_id = "27390")
	@testCaseName(test_case_name = "Frontpage - Addition of the BBB Logo to footer")
	@Test(priority = 2, description = "Verify the BBB logo redirection from Footer", groups = { "DESKTOP",
			"TABLET" }, testName = "27390:Frontpage - Addition of the BBB Logo to footer")
	public void verify_BBB_logo() throws Exception {
		test_Method_details(2, "Verify the BBB logo redirection from Footer");
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

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
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the BBB logo redirection from Footer menu of HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		footer_instance.click_bbb_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BBB_Url"));
		switchToMainTab();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the BBB logo redirection from Footer menu of Article page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_article_link();
		article_instance.verify_next_article_presence();
		footer_instance.click_bbb_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BBB_Url"));
		switchToMainTab();
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the BBB logo redirection from Footer menu of Video page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_video_link();
		video_instance.verify_fa_videosection();
		footer_instance.click_bbb_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "BBB_Url"));
		switchToMainTab();
		step_validator(5, true);

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
				break;
			}
		}
		step_validator(6, true);
	}

	@testId(test_id = "32119")
	@testCaseName(test_case_name = "[D][T] FP_Redesign - B-42004 Footer integration")
	@Test(priority = 3, description = "Verify the TRUSTe logo redirection from Footer", groups = { "DESKTOP",
			"TABLET" }, testName = "32119:[D][T] FP_Redesign - B-42004 Footer integration")
	public void verify_TRUSTe_logo() throws Exception {
		test_Method_details(3, "Verify the TRUSTe logo redirection from Footer");
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

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
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the TRUSTe logo redirection from Footer menu of HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		footer_instance.click_truste_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "TRUSTe_Url"));
		switchToMainTab();
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the TRUSTe logo redirection from Footer menu of Article page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_article_link();
		article_instance.verify_next_article_presence();
		footer_instance.click_truste_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "TRUSTe_Url"));
		switchToMainTab();
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the TRUSTe logo redirection from Footer menu of Video page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_video_link();
		video_instance.verify_fa_videosection();
		footer_instance.click_truste_logo();
		switchToNewTab();
		assertEqualsIgnoreCase(getCurrentUrl(), xmlReader(ENVIRONMENT, "TRUSTe_Url"));
		switchToMainTab();
		step_validator(5, true);

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
				break;
			}
		}
		step_validator(6, true);
	}

	@testId(test_id = "23991")
	@testCaseName(test_case_name = "CopyrightInFooter_Desktop")
	@Test(priority = 4, description = "Verify the Copyright information from Footer", groups = { "DESKTOP",
			"TABLET" }, testName = "23991:CopyrightInFooter_Desktop")
	public void verify_copyright_text() throws Exception {
		test_Method_details(4, "Verify the Copyright information from Footer");
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Copyright information from SERP page");
		homepage_instance.search("shoes");
		switchToNewTab();
		serp_instance.verify_SERP_Completely();
		String copyright_text = footer_instance.get_copyright_text();
		assertIsStringContains(expected_copyright_text, copyright_text);
		switchToMainTab();
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Verify the Copyright information from Footer menu of HomePage");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.close_openx_banner();
		copyright_text = footer_instance.get_copyright_text();
		assertIsStringContains(copyright_text, expected_copyright_text);
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Verify the Copyright information from Footer menu of Article page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_article_link();
		article_instance.verify_next_article_presence();
		copyright_text = footer_instance.get_copyright_text();
		assertIsStringContains(copyright_text, expected_copyright_text);
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Verify the Copyright information from Footer menu of Video page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_first_video_link();
		video_instance.verify_fa_videosection();
		copyright_text = footer_instance.get_copyright_text();
		assertIsStringContains(copyright_text, expected_copyright_text);
		step_validator(5, true);

		// Step 6
		test_step_details(6, "Verify the Copyright information from Footer menu of Sub-Catagory page");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		LinkedList<String> url_list = homepage_instance.get_sub_catagory_menu_url_list();
		for (String url : url_list) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				invokeBrowser(url);
				copyright_text = footer_instance.get_copyright_text();
				assertIsStringContains(copyright_text, expected_copyright_text);
				break;
			}
		}
		step_validator(6, true);
	}

	@testId(test_id = "32119")
	@testCaseName(test_case_name = "[D][T] FP_Redesign - B-42004 Footer integration")
	@Test(priority = 5, description = "Verify the Footer Menu Add on link navigations", groups = { "DESKTOP",
			"TABLET" }, testName = "32119:[D][T] FP_Redesign - B-42004 Footer integration")
	public void footer_add_on_link_navigation() throws Exception {
		test_Method_details(5, "Verify the Footer Menu Add on link navigations");
		// Step 1
		test_step_details(1, "Navigate to Frontpage and sign-in with valid credentials");
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify the Footer menu from HomePage");
		homepage_instance.close_openx_banner();
		LinkedList<String> footer_add_on_urls = footer_instance.get_footer_add_on_urls();
		for (int count = 0; count < footer_add_on_urls.size(); count++) {
			homepage_instance.verify_back_to_top_button();
			invokeBrowser(footer_add_on_urls.get(count));
			if (footer_add_on_urls.get(count).contains("http://info.pch.com")) {
				assertIsStringContains(getCurrentUrl(), "http://info.pch.com");
			} else if (footer_add_on_urls.get(count).contains("http://privacy.pch.com")) {
				assertIsStringContains(getCurrentUrl(), "https://privacy.pch.com/en-us/");
			} else if (footer_add_on_urls.get(count).contains("http://www.pchdigital.com/")) {
				assertEquals(getCurrentUrl(), "http://media.pch.com/");
			} else {
				assertEquals(getCurrentUrl(), footer_add_on_urls.get(count));
			}
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			footer_add_on_urls = footer_instance.get_footer_add_on_urls();
		}
		step_validator(2, true);
	}
}
