package com.sweepstakes;

import org.testng.annotations.Test;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.EDLHomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class Sweepstakes extends BaseClass {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final AccountsRegisterPage register_instance = AccountsRegisterPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final CentralServices_Page cs_instance = CentralServices_Page.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final EDLHomePage edl_home_instance = EDLHomePage.getInstance();
	private List<WebElement> sweeps_description; 
	private String[] crmn;
	private String[] device;
	private String[] sweeps_path;
	private String sweeps_edl_home_1 = "Sweepstakes edl home 1";
	
	@BeforeClass
	public void get_nfsp_from_admin() throws Exception {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(sweeps_edl_home_1);
			sweeps_description = admin_instance.get_textarea_field_elements_by_label("Description");
			for() {
			System.out.println();}
//			admin
//			default_nfsp_source = DEVICE.equalsIgnoreCase("Desktop")
//					? json_parser(default_nfsp_source_json, "DESKTOP", application_name)
//					: json_parser(default_nfsp_source_json, "TABLET", application_name);
//			nfsp_segment_json = admin_instance.get_input_field_element_by_key_name(nfsp_segment_field_name)
//					.getAttribute("value");
			admin_instance.save_and_close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in the NFSP value retrieving: " + e.getLocalizedMessage());
		}
	}
	
	@testId(test_id = "")
	@testCaseName(test_case_name = "")
	@Test(priority = 1, description = "Launch EDL homepage and verify sweepstakes in EDL Homepage", groups = { "DESKTOP",
			"TABLET", "SANITY" })
	public void verify_sweepstakes_on_edl_site_pages() throws Exception {
		test_Method_details(1, "Launch EDL homepage and verify sweepstakes in EDL Homepage");
		// Step 1
		test_step_details(1, "Launch EDL Homepage and sign-in with valid credentials");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
//		edl_home_instance.click_sign_in();
		edl_home_instance.click_register();
		String user_email = register_instance.register_FullUser();
//		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(edl_home_instance.verify_home());
		step_validator(1, true);
		
		// Step 2
		test_step_details(2, "Verify Sweeps on all the EDL pages except Horoscope page");
		assertTrue(edl_home_instance.verify_home_sweepstakes());
		step_validator(2, true);

		// Step 3
	}
}
