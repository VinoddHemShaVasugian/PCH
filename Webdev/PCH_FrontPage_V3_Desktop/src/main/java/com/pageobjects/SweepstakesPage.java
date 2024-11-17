package com.pageobjects;

import org.openqa.selenium.By;
import com.util.BaseClass;
import com.util.DB_Connector;

import org.apache.log4j.Logger;

public class SweepstakesPage extends BaseClass {
	private static final SweepstakesPage sweeps_instance = new SweepstakesPage();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private SweepstakesPage() {
	}

	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();

	public static SweepstakesPage getInstance() {
		return sweeps_instance;
	}

	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final By progress_bar = By.id("progress-bar-outer");
	private final By sweep_home = By.cssSelector("div.sweeps");
	private final By sweep_home_description = By.xpath("//div[@class='sweeps ']/following-sibling::div");
	private final By sweep_completed_home = By.cssSelector("div.sweeps.sweeps--completed");
	private final By sweep_completed_home_message = By
			.xpath("//div[@class='sweeps sweeps--completed']/following-sibling::div");
	private final By sweep_rr = By.cssSelector("div.sweeps__desc >div");

	private String[] description = null;
	private String[] crmn = null;
	private String[] device = null;
	private String[] sweeps_path = null;
	private String[] sweeps_complete_message_description = null;

//	private String description_config = "Enter The $15,000.00 Sweepstakes!";
//	private String sweeps_path_config = "https://spectrum.pch.com/path/15mserenity/fullreg.aspx?tid=f6da016a-4791-4889-822d-465588729e34";
//	private String sweeps_complete_message_description_config = "You've entered all sweeps for today, come back tomorrow";

	/**
	 * Return True if the sweepstakes present
	 * 
	 * @return
	 */
	public boolean verify_sweepstakes() {
		return elementPresent(sweep_home);
	}

	/**
	 * Return True if the sweepstakes complete image present
	 * 
	 * @return
	 */
	public boolean verify_sweepstakes_complete_state() {
		return elementPresent(sweep_completed_home);
	}

	/**
	 * Return sweep stakes complete message
	 * 
	 * @return
	 */
	public String verify_sweepstakes_complete_message() {
		return getText(sweep_completed_home_message, 3);
	}

	/**
	 * Click sweep stakes link
	 * 
	 * 
	 */
	public void click_sweepstakes() {
		link(sweep_home, "click", 5);
	}

	/**
	 * Verify sweep stakes description in homepage Return sweep stakes description
	 * 
	 * @return
	 */
	public String verify_sweep_home_description() {
		return getText(sweep_home_description, 3);
	}

	/**
	 * Return sweep stakes description
	 * 
	 * @return
	 */
	public String verify_sweep_rr_description() {
		return getText(sweep_rr, 3);
	}

	/**
	 * Return True if progress bar is present
	 * 
	 * @return
	 */
	public boolean verify_progressbar() {
		return elementPresent(progress_bar);
	}

	/**
	 * Delete all sweeps configuration except first sweep Common method for all the
	 * category pages.
	 * 
	 * @throws Exception
	 */
	public void delete_sweeps(String sweep_page, String complete_page) {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(sweep_page);
			boolean status = true;
			for (int i = 2; i < 10; i++) {
				if (status) {
					status = admin_instance.retain_section_based_on_position_by_label("Description", 2);
				} else {
					break;
				}
			}
			admin_instance.unpublish_article();
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(complete_page);
			boolean status1 = true;
			for (int i = 2; i < 10; i++) {
				if (status1) {
					status1 = admin_instance.retain_section_based_on_position_by_label("Description", 2);
				} else {
					break;
				}
			}
			admin_instance.unpublish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		System.out.println("delete_sweeps complete for " + sweep_page + " & " + complete_page);
	}

	/**
	 * Configure sweep stakes (Description, Sweepstakes Path, Sweepstakes Path
	 * Return Value and device type) Configure sweep stakes complete message
	 * (Sweepstakes complete message and device type)
	 * 
	 * Common method for all the category pages.
	 * 
	 * @author vsankar
	 * @throws Exception
	 */
	public void config_sweeps(String sweep_page, String complete_page, String crmn, String description_config, String sweeps_path_config, String sweeps_complete_message_description_config) {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(sweep_page);
			admin_instance.enter_text_area_field_element_by_label("Description", description_config, 1);
			admin_instance.enter_text_field_element_by_label("Sweepstakes Path", sweeps_path_config, 1);
			admin_instance.enter_text_field_element_by_label("Sweepstakes Path Return Value", crmn, 1);
			admin_instance.select_option_dropdown_field_element_by_label("Device", DEVICE, 1);
			admin_instance.publish_article();
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(complete_page);
			admin_instance.enter_text_area_field_element_by_label("Description",
					sweeps_complete_message_description_config, 1);
			admin_instance.select_option_dropdown_field_element_by_label("Device", DEVICE, 1);
			admin_instance.publish_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error while configure sweeps: " + e.getLocalizedMessage());
		}
		System.out.println("config_sweeps complete for " + sweep_page + " & " + complete_page + " & " + crmn);
	}

	/**
	 * Retrive sweep config and store it for execution (Description, Sweepstakes
	 * Path, Sweepstakes Path Return Value and device type) Retrive sweep stakes
	 * complete message config and store it for verification (Sweepstakes complete
	 * message and device type)
	 * 
	 * Common method for all the category pages.
	 * 
	 * @throws Exception
	 */
	public void get_sweeps_from_admin(String sweep, String sweep_complete) {
		try {
			invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(sweep);
			device = admin_instance.get_dropdown_field_elements_by_label("Device");
			description = admin_instance.get_textarea_field_elements_by_label("Description");
			sweeps_path = admin_instance.get_text_field_elements_by_label("Sweepstakes Path");
			crmn = admin_instance.get_text_field_elements_by_label("Sweepstakes Path Return Value");
			admin_instance.close_article();
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(sweep_complete);
			sweeps_complete_message_description = admin_instance.get_textarea_field_elements_by_label("Description");
			admin_instance.close_article();
			invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		} catch (Exception e) {
			log.error("Error in the sweeps value retrieving from admin: " + e.getLocalizedMessage());
			System.out.println("Error in the sweeps value retrieving from admin: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Return True if sweep stakes functionality is working fine. Play sweepstakes
	 * and verify sweep complete message Common method for all the category pages.
	 * 
	 * @throws Exception
	 */
	public boolean play_sweeps_and_verify_complete_state(String sweep, String sweep_complete) throws Exception {
		int a = device.length;
		String url = getTitle();
		try {
			for (int i = 0; i < a; i++) {
				if (DEVICE.equalsIgnoreCase(device[i])) {
					if (url.contains("Everydaylife")) {
						assertEquals(sweeps_instance.verify_sweep_home_description(), description[i]);
					} else {
						assertEquals(sweeps_instance.verify_sweep_rr_description(), description[i]);
					}
					sweeps_instance.click_sweepstakes();
					assertEqualsIgnoreCase(getCurrentUrl(), sweeps_path[i]);
					invokeBrowser(xmlReader(ENVIRONMENT, "Sweeps_Exit") + crmn[i]);
					assertTrue(sweeps_instance.verify_progressbar());
					sleepFor(35);
					assertEqualsURLs(getCurrentUrl(), xmlReader(ENVIRONMENT, "EDLBaseURL") + "?ec=" + (i + 1));
					assertEquals(homepage_instance.get_latest_entry_activity_message(),
							msg_property_file_reader("entry_confirmation_message"));
					assertTrue(sweeps_instance.verify_sweepstakes_complete_state());
					if (url.contains("Everydaylife")) {
						assertEquals(sweeps_instance.verify_sweepstakes_complete_message(),
								sweeps_complete_message_description[i]);
					} else {
						assertEquals(sweeps_instance.verify_sweep_rr_description(),
								sweeps_complete_message_description[i]);
					}
					assertEqualsIgnoreCase(
							db_instance.get_sweep_return_details(xmlReader(ENVIRONMENT, "ValidUserName1")), crmn[i]);
					System.out.println("test");
				} else {
					continue;
				}
			}
			return true;
		} catch (Exception e) {
			log.error("Error in sweeps verification for " + sweep + " & " + sweep_complete + ": "
					+ e.getLocalizedMessage());
			System.out.println("Error in sweeps verification for " + sweep + " & " + sweep_complete + ": "
					+ e.getLocalizedMessage());
		}
		return false;
	}
}