
package com.vip;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.awardtokens.SegIdTokens;
import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.ArticlePage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HomePage;
import com.pageobjects.HoroscopePage;
import com.pageobjects.InterstitialPage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.LotteryPage;
import com.pageobjects.SERPage;
import com.pageobjects.VideoLandingPage;
import com.pageobjects.WeatherPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class VipTests extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final CentralServices_Page cs_instance = CentralServices_Page.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final AccountsSignInPage signin = AccountsSignInPage.getInstance();
	private final InterstitialPage inetrstitial_instance = InterstitialPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final ArticlePage article_page = ArticlePage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();
	private final WeatherPage weather_instance = WeatherPage.getInstance();
	private final LotteryPage lottery_instance = LotteryPage.getInstance();
	private final HoroscopePage horoscope_instance = HoroscopePage.getInstance();
	private final SegIdTokens segid_instance = SegIdTokens.getInstance();
	private final VideoLandingPage videopage_instace = VideoLandingPage.getInstance();
	private String global_vip = "Global VIP Config";
	private String existing_vip_config = "Config-Frontpage";
	private String config_key = "enable_vip_badge";
	private String segment_to_assign = "CMAC";
	String token_seg_id_article_name = "Tokens / Link Promotion / SegId 111814-021815";

	@testId(test_id = "RT-04229")
	@testCaseName(test_case_name = "RT-04229 Integrate with VIP Assets - Including Lightbox [D/T/M]")
	@Test(priority = 1, description = "Verify the VIP call trigger order in presence of lightbox", groups = { "DESKTOP",
			"TABLET" }, testName = "RT-04229 Integrate with VIP Assets - Including Lightbox [D/T/M]")
	public void verify_vip_call_trigger_order() throws Exception {
		test_Method_details(1, "Verify the VIP call trigger order in presence of lightbox");
		// Step 1
		test_step_details(1, "Navigate to admin and publish global vip article & disable existing vip badge");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(global_vip);
		admin_instance.publish_article();
		admin_instance.enter_input_field_element_by_key_name(config_key, "0");
		admin_instance.save_and_close_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Register a user");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_Register();
		String user_email = register_instance.register_full_user_without_optin_bronze_levelup();
		step_validator(2, true);

		// Step 3
		test_step_details(3,
				"Verify the VIP call trigger order by considering the Level up, Welcome and Optin lightbox");
//		assertFalse(homepage_instance.verify_new_vip_badge(2));//debug

		lb_instance.close_bronze_level_up_lb();
//		assertFalse(homepage_instance.verify_new_vip_badge(2));

		lb_instance.close_welcome_light_box();
//		assertFalse(homepage_instance.verify_new_vip_badge(2));

		lb_instance.close_optin_light_box();
		assertTrue(homepage_instance.verify_new_vip_badge(15));
		step_validator(3, true);

		// Step 4
		test_step_details(4,
				"Verify the VIP call trigger order by considering the Max searches(25/40/75/100) lightbox");
		homepage_instance.search_term(generateRandomString(5));
		homepage_instance.switchToNewTab();
		// Make the count to 25
		db_instance.updateDailySearchCount(user_email, 24);
		serp_instance.search(generateRandomString(6));
		serp_instance.verify_SERP_Completely();
//		assertFalse(homepage_instance.verify_new_vip_badge(2));
		lb_instance.close_lb();
		assertTrue(homepage_instance.verify_new_vip_badge(5));
		// Make the count to 40
		db_instance.updateDailySearchCount(user_email, 39);
		serp_instance.search(generateRandomString(6));
		serp_instance.verify_SERP_Completely();
//		assertFalse(homepage_instance.verify_new_vip_badge(2));
		lb_instance.close_lb();
		assertTrue(homepage_instance.verify_new_vip_badge(5));
		// Make the count to 75
		db_instance.updateDailySearchCount(user_email, 74);
		serp_instance.search(generateRandomString(6));
		serp_instance.verify_SERP_Completely();
//		assertFalse(homepage_instance.verify_new_vip_badge(2));
		lb_instance.close_lb();
		assertTrue(homepage_instance.verify_new_vip_badge(5));
		// Make the count to 100
		db_instance.updateDailySearchCount(user_email, 100);
		serp_instance.search(generateRandomString(6));
		serp_instance.verify_SERP_Completely();
//		assertFalse(homepage_instance.verify_new_vip_badge(2));
		lb_instance.close_lb();
		assertTrue(homepage_instance.verify_new_vip_badge(5));
		homepage_instance.switchToMainTab();
		step_validator(4, true);

		// Step 5
		test_step_details(5,
				"Verify the VIP call trigger order by considering the Complete Registration lightbox of Mini Reg/ Social user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String[] mini_user_details = cs_instance.createMiniReguser();
		invokeBrowser(mini_user_details[1]);
		homepage_instance.search_term(generateRandomString(5));
		homepage_instance.switchToNewTab();
		serp_instance.search(generateRandomString(6));
		serp_instance.verify_SERP_Completely();
		serp_instance.search(generateRandomString(6));
		serp_instance.verify_SERP_Completely();
		// assertFalse(homepage_instance.verify_new_vip_badge(1));
		 lb_instance.close_lb();
		assertTrue(homepage_instance.verify_new_vip_badge(5));
		homepage_instance.switchToMainTab();
		step_validator(5, true);

		// Step 6
		test_step_details(6,
				"Verify the VIP call trigger order by considering the Create Password lightbox of Silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String[] silver_user_details = cs_instance.createSilverUser();
		invokeBrowser(silver_user_details[1]);
		lb_instance.close_welcome_optin_lb();
		assertTrue(homepage_instance.verify_complete_registration());
		homepage_instance.close_openx_banner();
		homepage_instance.click_first_article_link();
		article_page.verify_next_article_presence();
		for (int loop = 0; loop < 2; loop++) {
			article_page.click_next_article();
			if (getTitle().contains("Interstitial")) {
				inetrstitial_instance.click_next_article_button();
			} else {
				article_page.verify_next_article_presence();
			}
		}
//		 assertFalse(homepage_instance.verify_new_vip_badge(1));
		 lb_instance.close_lb();
		assertTrue(homepage_instance.verify_new_vip_badge(5));
		homepage_instance.switchToMainTab();
		step_validator(6, true);
	}


	@testId(test_id = "RT-04227")
	@testCaseName(test_case_name = "RT-04229 Integrate with VIP Assets - Including Lightbox [D/T/M] & RT-04227 Integrate with VIP Assets - Excluding LightBox [D/T/M]")
	@Test(priority = 3, description = "Verify the VIP level up message", groups = { "DESKTOP",
			"TABLET" }, testName = "Integrate with VIP Assets - Including Lightbox [DT]")
	public void verify_vip_activity_by_search() throws Exception {
		test_Method_details(3, "Verify the VIP level up message");
		// Step 1
		test_step_details(1, "Register a gold user, login and get VIP message");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createGoldUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		step_validator(1, true);
		
		// Step 2
		test_step_details(2, "verify VIP msg before and after search for Gold user");
		assertTrue(verify_VIP_msg_before_and_after_search());
		step_validator(2, true);
		
		// Step 3
		test_step_details(3, "Register a silver user, login and get VIP message for silver user");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String silver_user_details[] = cs_instance.createSilverUser();
		invokeBrowser(silver_user_details[1]);
		lb_instance.close_welcome_optin_lb();
		assertFalse(homepage_instance.verify_Home());
		homepage_instance.click_complete_registration();
		register_instance.completer_RegistrationSilveruser();
		lb_instance.close_bronze_level_up_lb();
		assertTrue(homepage_instance.verify_Home());
		step_validator(3, true);
		
		// Step 4
		test_step_details(4, "verify VIP msg before and after search for Silver user");
		assertTrue(verify_VIP_msg_before_and_after_search());
		step_validator(4, true);
		
		// Step 5
		test_step_details(5, "Register a Mini reg user, login and get VIP message");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String mini_user_details[] = cs_instance.createMiniReguser();
		invokeBrowser(mini_user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_complete_registration();
		signin.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForMiniRegUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(5, true);
		
		// Step 6
		test_step_details(6, "verify VIP msg before and after search for Mini reg user");
		assertTrue(verify_VIP_msg_before_and_after_search());
		step_validator(6, true);
		
		// Step 7
		test_step_details(7, "Register a Social user, login and get VIP message");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String social_user_details[] = cs_instance.createSocialUser();
		invokeBrowser(social_user_details[1]);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.click_complete_registration();
		signin.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		register_instance.complete_RegistrationForSocialUser();
		lb_instance.close_bronze_level_up_lb();
		step_validator(7, true);
		
		// Step 8
		test_step_details(8, "verify VIP msg before and after search for Social user");
		assertTrue(verify_VIP_msg_before_and_after_search());
		step_validator(8, true);
		
	}
	
	@testId(test_id = "RT-04227")
	@testCaseName(test_case_name = "RT-04229 Integrate with VIP Assets - Including Lightbox [D/T/M] & RT-04227 Integrate with VIP Assets - Excluding LightBox [D/T/M]")
	@Test(priority = 3, description = "Verify the VIP level up message", groups = { "DESKTOP",
			"TABLET" }, testName = "Integrate with VIP Assets - Including Lightbox [DT]")
	public void verify_vip_activity() throws Exception {
		test_Method_details(3, "Verify the VIP level up message");
		// Step 1
		test_step_details(1, "Register a gold user and verify VIP activity by claim token in whether page");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details[] = cs_instance.createGoldUser();
		invokeBrowser(user_details[1]);
		lb_instance.close_welcome_optin_lb();
		Assert.assertTrue(homepage_instance.verify_vip_message_for_new_users(user_details[2]));
		Assert.assertTrue(weather_instance.validating_vip_level_up(user_details[2]));
		step_validator(1, true);
		
		// Step 2
		test_step_details(2, "Register a gold user and verify VIP activity by claim token in lottery page");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details_lottery[] = cs_instance.createGoldUser();
		invokeBrowser(user_details_lottery[1]);
		lb_instance.close_welcome_optin_lb();
		Assert.assertTrue(homepage_instance.verify_vip_message_for_new_users(user_details_lottery[2]));
		Assert.assertTrue(lottery_instance.validating_vip_level_up(user_details_lottery[2]));
		step_validator(2, true);
		
		// Step 3
		test_step_details(3, "Register a gold user and verify VIP activity by claim token in Horoscope page");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details_horoscope[] = cs_instance.createGoldUser();
		invokeBrowser(user_details_horoscope[1]);
		lb_instance.close_welcome_optin_lb();
		Assert.assertTrue(homepage_instance.verify_vip_message_for_new_users(user_details_horoscope[2]));
		Assert.assertTrue(horoscope_instance.validating_vip_level_up(user_details_horoscope[2]));
		step_validator(3, true);
		
		// Step 4
		test_step_details(4, "Register a gold user and verify VIP activity for segid users");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(token_seg_id_article_name);
		String seg_id_value = getAttribute(admin_instance.get_text_field_element_by_label("Conditions"), "value").split("=")[1]
				.trim();
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details_segid[] = cs_instance.createGoldUser();
		invokeBrowser(user_details_segid[1] + "&segid=" + seg_id_value);
		lb_instance.close_welcome_optin_lb();
//		homepage_instance.clic_new_VIP_logo(5);
//		String beforeActivity = homepage_instance.get_vip_msg();
//		homepage_instance.search(randomString(5, 6));
//		switchToNewTab();
//		switchToMainTab();
//		homepage_instance.clic_new_VIP_logo(5);
//		String afterActivity = homepage_instance.get_vip_msg();
//		assertNotEqualsIgnoreCase(beforeActivity, afterActivity);
//		invokeBrowser(user_details_horoscope[1]);
//		lb_instance.close_welcome_optin_lb();
		Assert.assertTrue(homepage_instance.verify_vip_message_for_new_users(user_details_segid[2]));
		Assert.assertTrue(segid_instance.validating_vip_level_up(user_details_segid[2]));
		step_validator(4, true);
		
		// Step 5
		test_step_details(5, "Register a gold user and verify VIP activity by claim token in Article page");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details_article[] = cs_instance.createGoldUser();
		invokeBrowser(user_details_article[1]);
		lb_instance.close_welcome_optin_lb();
		Assert.assertTrue(homepage_instance.verify_vip_message_for_new_users(user_details_article[2]));
		Assert.assertTrue(article_page.validating_vip_level_up(user_details_article[2]));
		step_validator(5, true);
		
		/*// Step 6
		test_step_details(5, "Register a gold user and verify VIP activity by playing video");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String user_details_video[] = cs_instance.createGoldUser();
		invokeBrowser(user_details_video[1]);
		lb_instance.close_welcome_optin_lb();
		Assert.assertTrue(homepage_instance.verify_vip_message_for_new_users(user_details_video[2]));
		Assert.assertTrue(videopage_instace.validating_vip_level_up(user_details_video[2]));
		step_validator(6, true);*/
	}
	
	public boolean verify_VIP_msg_before_and_after_search() {
		try {
		homepage_instance.clic_new_VIP_logo(5);
		String beforeSearch= homepage_instance.get_vip_msg();
		System.out.println("VIP message before search: "+beforeSearch);
		homepage_instance.search_term(generateRandomString(5));
		homepage_instance.switchToNewTab();
		homepage_instance.clic_new_VIP_logo(5);
		String afterSearch= homepage_instance.get_vip_msg();
		System.out.println("VIP message after search: "+afterSearch);
		serp_instance.verify_SERP_Completely();
		assertNotEqualsIgnoreCase(beforeSearch, afterSearch);
		return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}