package com.awardtokens;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.SERPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class TokenQueueTests extends BaseClass {

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final HomePage homepage_instance = HomePage.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final SERPage serppage_instance = SERPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();

	private final String application_property_name = "Front Page";
	private final String token_config_article = "Config-Prizemachine";

	@testId(test_id = "RT-04352")
	@testCaseName(test_case_name = "Token Queue [D/T/M]")
	@Test(priority = 1, description = "Verify Token credit queue record by making Invalid API url on admin", groups = {
			"DESKTOP", "TABLET" }, testName = "RT-04352:Token Queue [D/T/M]")
	public void verify_credit_token_queue() throws Exception {
		String token_api_url = "";
		String dummy_token_api_url = generateRandomString(5);
		try {
			// Step 1
			test_step_details(1, "Navigate to Joomla admin and retrieve the Contest Key details");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(token_config_article);
			token_api_url = admin_instance.get_text_field_element_by_label("Value").getAttribute("value");
			admin_instance.enter_text_field_element_by_label("Value", dummy_token_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			step_validator(1, true);

			// Step 2
			test_step_details(2, "Create a Full Reg. user");
			invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			homepage_instance.click_Register();
			String user_email = register_instance.register_FullUser();
			lb_instance.close_welcome_optin_lb();
			homepage_instance.close_openx_banner();
			assertTrue(homepage_instance.verify_Home());
			step_validator(2, true);

			// Step 3
			test_step_details(3, "Verify the Token details in the queue for Registration");
			LinkedHashMap<String, String> token_details = db_instance.get_queued_token_details();
			assertEquals(token_details.get("lob"), application_property_name.toUpperCase().replace(" ", ""));// debug
			assertEquals(token_details.get("status"), "0");
			assertIsStringContains(token_details.get("request_data"),
					db_instance.getUserGMTOATFromEmail(user_email, "OAT"));
			step_validator(3, true);

			// Step 4
			test_step_details(4, "Do a First Search");
			homepage_instance.search_term_on_collapse(generateRandomString(5));
			switchToNewTab();
			assertTrue(serppage_instance.verify_SERP_Completely());
			switchToMainTab();
			step_validator(4, true);

			// Step 5
			test_step_details(5, "Verify the Token details in the queue for First Search");
			token_details = db_instance.get_queued_token_details();
			assertEquals(token_details.get("lob"), application_property_name.toUpperCase().replace(" ", ""));
			assertEquals(token_details.get("status"), "0");
			assertIsStringContains(token_details.get("request_data"),
					db_instance.getUserGMTOATFromEmail(user_email, "OAT"));
			step_validator(5, true);

			// Step 6
			test_step_details(6, "Verify the Token status details in the queue after running the cron");
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_token_cron"));
			token_details = db_instance.get_queued_token_details();
			assertEquals(token_details.get("status"), "1");
			step_validator(6, true);

			// Step 7
			test_step_details(7, "Reset the Joomla Admin values");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(token_config_article);
			admin_instance.enter_text_field_element_by_label("Value", token_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			sleepFor(180);
			step_validator(7, true);

			// Step 8
			test_step_details(8, "Re-Run the cron and verify the contest entry queue table");
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_token_cron"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_run_token_cron"));
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			token_details = db_instance.get_queued_token_details();
			log.info("Token Queue Details :" + token_details);
			assertTrue(token_details == null);
			step_validator(8, true);

		} catch (Exception e) {
			System.out.println("Entered catch block");
			log.error("Exception in the contest queue:" + e.getLocalizedMessage());
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(token_config_article);
			admin_instance.enter_text_field_element_by_label("Value", token_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
			throw e;
		} finally {
			System.out.println("Entered finally block");
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(token_config_article);
			admin_instance.enter_text_field_element_by_label("Value", token_api_url, 1);
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		}
	}
}