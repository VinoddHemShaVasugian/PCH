package com.sweepstakes;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.EDLHomePage;
import com.pageobjects.HomePage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SweepstakesPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class Sweepstakes extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final AccountsSignInPage account_signin_instance = AccountsSignInPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final EDLHomePage edl_home_instance = EDLHomePage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final SweepstakesPage sweeps_instance = SweepstakesPage.getInstance();
	private String sweeps_edl_home_1 = "Sweepstakes edl home 1";
	private String sweepstakes_edl_home_Completed = "Sweepstakes edl home Completed";
	private String sweepstakes_health_1 = "Sweepstakes Health 1";
	private String sweepstakes_edl_health_Completed = "Sweepstakes edl health Completed";
	private String sweepstakes_recipe_1 = "Sweepstakes Recipe 1";
	private String sweepstakes_edl_recipe_completed = "Sweepstakes edl Recipe Completed";
	private String sweepstakes_lifehacks_1 = "Sweepstakes Lifehacks 1";
	private String sweepstakes_edl_lifehacks_completed = "Sweepstakes edl Lifehacks Completed";
	private String sweepstakes_pets_1 = "Sweepstakes Pets 1";
	private String sweepstakes_edl_pets_completed = "Sweepstakes edl Pets Completed";
	private String sweepstakes_trending_1 = "Sweepstakes Trending 1";
	private String sweepstakes_edl_trending_completed = "Sweepstakes edl Trending Completed";
	private String sweepstakes_comics_1 = "Sweepstakes Comics 1";
	private String sweepstakes_edl_comics_completed = "Sweepstakes edl Comics Completed";

	private String[] crmn = { "r180pchcom", "dr123", "dh123", "dc123", "dl123", "dp123", "dt123" };
	private String description_config = "Enter The $15,000.00 Sweepstakes!";
	private String sweeps_path_config = "https://spectrum.pch.com/path/15mserenity/fullreg.aspx?tid=f6da016a-4791-4889-822d-465588729e34";
	private String sweeps_complete_message_description_config = "You've entered all sweeps for today, come back tomorrow";

	/**
	 * Delete sweeps for all the pages and configure single valid sweepstakes for
	 * execution
	 * 
	 * @author vsankar
	 * @throws Exception
	 */
	@BeforeClass
	public void sweeps_config_on_admin() throws Exception {
		try {
			sweeps_instance.delete_sweeps(sweeps_edl_home_1, sweepstakes_edl_home_Completed);
			sweeps_instance.delete_sweeps(sweepstakes_recipe_1, sweepstakes_edl_recipe_completed);
			sweeps_instance.delete_sweeps(sweepstakes_health_1, sweepstakes_edl_health_Completed);
			sweeps_instance.delete_sweeps(sweepstakes_comics_1, sweepstakes_edl_comics_completed);
			sweeps_instance.delete_sweeps(sweepstakes_lifehacks_1, sweepstakes_edl_lifehacks_completed);
			sweeps_instance.delete_sweeps(sweepstakes_pets_1, sweepstakes_edl_pets_completed);
			sweeps_instance.delete_sweeps(sweepstakes_trending_1, sweepstakes_edl_trending_completed);
			sweeps_instance.config_sweeps(sweeps_edl_home_1, sweepstakes_edl_home_Completed, crmn[0],
					description_config, sweeps_path_config, sweeps_complete_message_description_config);
			sweeps_instance.config_sweeps(sweepstakes_recipe_1, sweepstakes_edl_recipe_completed, crmn[1],
					description_config, sweeps_path_config, sweeps_complete_message_description_config);
			sweeps_instance.config_sweeps(sweepstakes_health_1, sweepstakes_edl_health_Completed, crmn[2],
					description_config, sweeps_path_config, sweeps_complete_message_description_config);
			sweeps_instance.config_sweeps(sweepstakes_comics_1, sweepstakes_edl_comics_completed, crmn[3],
					description_config, sweeps_path_config, sweeps_complete_message_description_config);
			sweeps_instance.config_sweeps(sweepstakes_lifehacks_1, sweepstakes_edl_lifehacks_completed, crmn[4],
					description_config, sweeps_path_config, sweeps_complete_message_description_config);
			sweeps_instance.config_sweeps(sweepstakes_pets_1, sweepstakes_edl_pets_completed, crmn[5],
					description_config, sweeps_path_config, sweeps_complete_message_description_config);
			sweeps_instance.config_sweeps(sweepstakes_trending_1, sweepstakes_edl_trending_completed, crmn[6],
					description_config, sweeps_path_config, sweeps_complete_message_description_config);

		} catch (Exception e) {
			log.error("Error in sweeps configuration on admin: " + e.getLocalizedMessage());
		}
	}

	@testId(test_id = "RT-04265")
	@testCaseName(test_case_name = "EDL Sweepstakes [D/T]")
	@Test(priority = 1, description = "Verify sweepstakes in EDL Home page", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04265: EDL Sweepstakes [D/T]")
	public void verify_sweepstakes_on_edl_home_page() throws Exception {
		test_Method_details(1, "Verify sweepstakes in EDL Home page");

		// Step 1
		test_step_details(1, "Launch EDL site and sign-in with valid credentials");
		db_instance.delete_CRMN_value(xmlReader(ENVIRONMENT, "ValidUserName1"));
		sweeps_instance.get_sweeps_from_admin(sweeps_edl_home_1, sweepstakes_edl_home_Completed);
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "EDLBaseURL"));
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Verify sweeps presence in home page");
		assertTrue(sweeps_instance.verify_sweepstakes());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Play sweeps and verify complete state in home page");
		assertTrue(sweeps_instance.play_sweeps_and_verify_complete_state(sweeps_edl_home_1,
				sweepstakes_edl_home_Completed));
		step_validator(3, true);
	}

	@testId(test_id = "RT-04265")
	@testCaseName(test_case_name = "EDL Sweepstakes [D/T]")
	@Test(priority = 2, description = "Verify sweepstakes in EDL Recipe page", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04265: EDL Sweepstakes [D/T]")
	public void verify_sweepstakes_on_edl_recipe_page() throws Exception {
		test_Method_details(2, "Verify sweepstakes in EDL Recipe page");

		// Step 1
		test_step_details(1, "Launch EDL site and sign-in with valid credentials");
		db_instance.delete_CRMN_value(xmlReader(ENVIRONMENT, "ValidUserName1"));
		sweeps_instance.get_sweeps_from_admin(sweepstakes_recipe_1, sweepstakes_edl_recipe_completed);
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "EDLBaseURL"));
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to recipe page and verify sweeps presence");
		edl_home_instance.click_recipe_header_menu();
		assertTrue(sweeps_instance.verify_sweepstakes());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Play sweeps and verify complete state in recipe page");
		assertTrue(sweeps_instance.play_sweeps_and_verify_complete_state(sweepstakes_recipe_1,
				sweepstakes_edl_recipe_completed));
		step_validator(3, true);
	}

	@testId(test_id = "RT-04265")
	@testCaseName(test_case_name = "EDL Sweepstakes [D/T]")
	@Test(priority = 3, description = "Verify sweepstakes in EDL Health page", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04265: EDL Sweepstakes [D/T]")
	public void verify_sweepstakes_on_edl_health_page() throws Exception {
		test_Method_details(3, "Verify sweepstakes in EDL Health page");

		// Step 1
		test_step_details(1, "Launch EDL site and sign-in with valid credentials");
		db_instance.delete_CRMN_value(xmlReader(ENVIRONMENT, "ValidUserName1"));
		sweeps_instance.get_sweeps_from_admin(sweepstakes_health_1, sweepstakes_edl_health_Completed);
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "EDLBaseURL"));
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to health page and verify sweeps presence");
		edl_home_instance.click_health_menu();
		assertTrue(sweeps_instance.verify_sweepstakes());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Play sweeps and verify complete state in health page");
		assertTrue(sweeps_instance.play_sweeps_and_verify_complete_state(sweepstakes_health_1,
				sweepstakes_edl_health_Completed));
		step_validator(3, true);
	}

	@testId(test_id = "RT-04265")
	@testCaseName(test_case_name = "EDL Sweepstakes [D/T]")
	@Test(priority = 4, description = "Verify sweepstakes in EDL Comics page", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04265: EDL Sweepstakes [D/T]")
	public void verify_sweepstakes_on_edl_comics_page() throws Exception {
		test_Method_details(4, "Verify sweepstakes in EDL Comics page");

		// Step 1
		test_step_details(1, "Launch EDL site and sign-in with valid credentials");
		db_instance.delete_CRMN_value(xmlReader(ENVIRONMENT, "ValidUserName1"));
		sweeps_instance.get_sweeps_from_admin(sweepstakes_comics_1, sweepstakes_edl_comics_completed);
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "EDLBaseURL"));
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to comics page and verify sweeps presence");
		edl_home_instance.click_comics_header_menu();
		assertTrue(sweeps_instance.verify_sweepstakes());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Play sweeps and verify complete state in comics page");
		assertTrue(sweeps_instance.play_sweeps_and_verify_complete_state(sweepstakes_comics_1,
				sweepstakes_edl_comics_completed));
		step_validator(3, true);
	}

	@testId(test_id = "RT-04265")
	@testCaseName(test_case_name = "EDL Sweepstakes [D/T]")
	@Test(priority = 5, description = "Verify sweepstakes in EDL LifeHacks page", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04265: EDL Sweepstakes [D/T]")
	public void verify_sweepstakes_on_edl_lifehacks_page() throws Exception {
		test_Method_details(5, "Verify sweepstakes in EDL LifeHacks page");

		// Step 1
		test_step_details(1, "Launch EDL site and sign-in with valid credentials");
		db_instance.delete_CRMN_value(xmlReader(ENVIRONMENT, "ValidUserName1"));
		sweeps_instance.get_sweeps_from_admin(sweepstakes_lifehacks_1, sweepstakes_edl_lifehacks_completed);
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "EDLBaseURL"));
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to lifehacks page and verify sweeps presence");
		edl_home_instance.click_lifehacks_header_menu();
		assertTrue(sweeps_instance.verify_sweepstakes());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Play sweeps and verify complete state in lifehacks page");
		assertTrue(sweeps_instance.play_sweeps_and_verify_complete_state(sweepstakes_lifehacks_1,
				sweepstakes_edl_lifehacks_completed));
		step_validator(3, true);
	}

	@testId(test_id = "RT-04265")
	@testCaseName(test_case_name = "EDL Sweepstakes [D/T]")
	@Test(priority = 6, description = "Verify sweepstakes in EDL Pets page", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04265: EDL Sweepstakes [D/T]")
	public void verify_sweepstakes_on_edl_pets_page() throws Exception {
		test_Method_details(6, "Verify sweepstakes in EDL Pets page");

		// Step 1
		test_step_details(1, "Launch EDL site and sign-in with valid credentials");
		db_instance.delete_CRMN_value(xmlReader(ENVIRONMENT, "ValidUserName1"));
		sweeps_instance.get_sweeps_from_admin(sweepstakes_pets_1, sweepstakes_edl_pets_completed);
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "EDLBaseURL"));
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to pets page and verify sweeps presence");
		edl_home_instance.click_pets_header_menu();
		assertTrue(sweeps_instance.verify_sweepstakes());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Play sweeps and verify complete state in pets page");
		assertTrue(sweeps_instance.play_sweeps_and_verify_complete_state(sweepstakes_pets_1,
				sweepstakes_edl_pets_completed));
		step_validator(3, true);
	}

	@testId(test_id = "RT-04265")
	@testCaseName(test_case_name = "EDL Sweepstakes [D/T]")
	@Test(priority = 7, description = "Verify sweepstakes in EDL Trending page", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "RT-04265: EDL Sweepstakes [D/T]")
	public void verify_sweepstakes_on_edl_trending_page() throws Exception {
		test_Method_details(7, "Verify sweepstakes in EDL Trending page");

		// Step 1
		test_step_details(1, "Launch EDL site and sign-in with valid credentials");
		db_instance.delete_CRMN_value(xmlReader(ENVIRONMENT, "ValidUserName1"));
		sweeps_instance.get_sweeps_from_admin(sweepstakes_trending_1, sweepstakes_edl_trending_completed);
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "EDLBaseURL"));
		homepage_instance.click_SignIn();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"),
				xmlReader(ENVIRONMENT, "ValidPassword"));
		lb_instance.close_welcome_optin_lb();
		assertTrue(edl_home_instance.verify_home());
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to trending page and verify sweeps presence");
		edl_home_instance.click_trending_header_menu();
		assertTrue(sweeps_instance.verify_sweepstakes());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Play sweeps and verify complete state in trending page");
		assertTrue(sweeps_instance.play_sweeps_and_verify_complete_state(sweepstakes_trending_1,
				sweepstakes_edl_trending_completed));
		step_validator(3, true);
	}
}
