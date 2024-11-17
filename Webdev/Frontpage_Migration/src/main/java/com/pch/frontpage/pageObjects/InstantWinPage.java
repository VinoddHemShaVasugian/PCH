package com.pch.frontpage.pageObjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class InstantWinPage extends BaseClass {
	private static final InstantWinPage iwe_instance = new InstantWinPage();

	private InstantWinPage() {
	}

	public static InstantWinPage getInstance() {
		return iwe_instance;
	}

	private final String device_overview_page = xmlReader(ENVIRONMENT, "IweURL") + "#device/overview/";
	private final String winner_list_page = xmlReader(ENVIRONMENT, "IweURL") + "#winner/list";

	private final By username = By.id("j_username");
	private final By password = By.id("j_password");
	private final By login_button = By.id("submit");
	private final By user_email = By.name("email");
	private final By device_overview_modal_close_icon = By
			.xpath("//div[@id='deviceoverview_header-targetEl']/div[2]/img");
	private final By giveaway_group_name = By.xpath("//span[contains(text(),'Gwy. Group:')]");
	private final By giveaway_name = By
			.xpath("//div[@id='deviceoverview-body']//td[@role='gridcell']/div/font[contains(text(),'SnW')]");
	private final By gwy_start_date = By.name("startDate");
	private final By gwy_end_date = By.name("endDate");
	private final By comment_field = By.name("comment");
	private final By save_button = By.xpath(
			"//div[contains(@id,'toolbar')]/div//div[1]//a[2]//span[contains(text(),'Save')]/following-sibling::span[last()]");
	private final By server_warning_modal = By.xpath("//div[contains(@id,'messagebox')]");
	private final By yes_on_server_warning_modal = By.xpath(
			"//div[contains(@id, 'messagebox')]/div//div[1]//a[2]//span[contains(text(),'Yes')]/following-sibling::span[last()]");
	private final By winner_list_page_winner_id_column = By.xpath("//span[text()='Winner ID']");
	private final By catch_all_tree_button = By.id("catchallButton-btnIconEl");
	private final By giveaway_token_catch_all = By.xpath("//span[contains(text(),'Gwy. Token Catchall:')]");
	private final By prize_token_catch_all = By
			.xpath("//div[@id='deviceoverview-body']//td[@role='gridcell']/div/font[contains(text(),'SnW')]");
	private final By prize_value = By.name("prizeValue");
	private final By prize_token_catch_all_close_icon = By
			.xpath("//span[starts-with(text(),'Edit Prize Token Catchall:')]/../following-sibling::div/img");
	private final By close_button = By.xpath(
			".//div[contains(@id,'toolbar')]/div//div[1]//a//span[contains(text(),'Close')]/following-sibling::span[last()]");

	/**
	 * Login to the application
	 * 
	 * @param iwe_username
	 * @param iwe_password
	 */
	public void login(String iwe_username, String iwe_password) {
		textbox(username, "enter", iwe_username, 10);
		textbox(password, "enter", iwe_password, 10);
		button(login_button, 10);
	}

	/**
	 * Verify the application successfully landing page
	 * 
	 * @return
	 */
	public boolean verify_home() {
		return elementPresent(user_email);
	}

	/**
	 * Navigate to Device overview modal
	 * 
	 * @param device_id
	 * @throws Exception
	 */
	public void navigate_device_overview_page(String device_id) throws Exception {
		invokeBrowser(device_overview_page + device_id);
		waitForElementPresent(device_overview_modal_close_icon, 15);
	}

	/**
	 * Navigate to Device overview modal
	 * 
	 * @param device_id
	 * @throws Exception
	 */
	public void navigate_winner_list_page(String device_id) throws Exception {
		invokeBrowser(winner_list_page);
		waitForElementPresent(winner_list_page_winner_id_column, 20);
	}

	/**
	 * Make the Give away alive by modifying the Start and End date
	 */
	public void make_gwy_active() {
		button(giveaway_group_name, 10);
		button(giveaway_name, 10);
		textbox(gwy_start_date, "enter", getDateWithOffset(-1, "MMM dd, yyyy") + " 11:59:59 PM", 10);
		textbox(gwy_end_date, "enter", getCurrentDate("MMM dd, yyyy") + " 11:59:59 PM", 10);
		textbox(comment_field, "enter", "Testing FP", 10);
		button(save_button, 10);
		close_server_warning_gwy_msg();
		waitForElementNotVisible(save_button, 30);
		button(device_overview_modal_close_icon, 10);
	}

	/**
	 * Make the Give away in active by modifying the Start and End date
	 */
	public void make_gwy_inactive() {
		button(giveaway_group_name, 10);
		button(giveaway_name, 10);
		textbox(gwy_start_date, "enter", getDateWithOffset(-2, "MMM dd, yyyy") + " 11:59:59 PM", 10);
		textbox(gwy_end_date, "enter", getDateWithOffset(-1, "MMM dd, yyyy") + " 11:59:59 PM", 10);
		textbox(comment_field, "enter", "Testing FP", 10);
		button(save_button, 10);
		close_server_warning_gwy_msg();
		waitForElementNotVisible(save_button, 30);
		button(device_overview_modal_close_icon, 10);
	}

	/**
	 * Close the Warning message from the Give away when an alive give away is
	 * modified
	 */
	public void close_server_warning_gwy_msg() {
		if (get_webelements_list(server_warning_modal).size() > 0) {
			button(yes_on_server_warning_modal, 15);
		}
	}

	/**
	 * Verify the Winner list page presence
	 * 
	 * @param user_email
	 * @return
	 */
	public boolean verify_winner_email(String user_email) {
		return elementPresent(By.xpath("//font[normalize-space()='" + user_email.toLowerCase() + "']"));
	}

	/**
	 * Return the Token Prize Catch All Value
	 * 
	 * @return
	 */
	public String get_prize_token_catch_all_value() {
		button(catch_all_tree_button, 10);
		button(giveaway_token_catch_all, 10);
		button(prize_token_catch_all, 10);
		sleepFor(3);
		String prize = getAttribute(prize_value, "value");
		button(prize_token_catch_all_close_icon, 15);
		button(close_button, 10);
		return prize;
	}
}
