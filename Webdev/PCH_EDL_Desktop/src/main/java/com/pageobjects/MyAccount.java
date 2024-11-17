package com.pageobjects;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;
import com.util.DriverManager;

public class MyAccount extends BaseClass {

	private static final MyAccount myaccount_instance = new MyAccount();

	private MyAccount() {
	}

	public static MyAccount getInstance() {
		return myaccount_instance;
	}

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final By token_history_description = By.xpath("//div[@class='th_desc']");
	private final By my_info_link = By.xpath("//span[contains(.,'My Info')]");
	private final By dob_month = By.name("MN");
	private final By dob_day = By.name("DY");
	private final By dob_year = By.name("YR");
	private final By my_info_update_btn = By.id("sub-myaccount-btn");
	private final By my_info_update_successfully_msg = By.cssSelector("div.submit-container div#success_message");
	private final By token_Transaction = By.cssSelector("div.th_desc");

	private final By token_history = By.cssSelector("#tabtoken > span");
	private final By rewards = By.xpath("//div[@id='content']/div/ul/li[3]/a/span");
	private final By alltimetokenleaderboard = By.xpath("//div[@id='content']/div/ul/li[4]/a/span");
	private final By manageOrders = By.cssSelector("a.btn-payonline-trackorder > div");

	/**
	 * To verify the landing on My Account page
	 */
	public boolean verify_myAccount() {
		return elementPresent(my_info_update_btn) && elementPresent(token_history) && elementPresent(rewards)
				&& elementPresent(alltimetokenleaderboard) && elementPresent(manageOrders);
	}

	/**
	 * Verify the 1st token transaction in myaccount page and return the result
	 */
	public boolean verify_TokenTransaction_withText(String Text) throws Exception {
		waitForElement(token_Transaction);
		boolean status = getText(token_Transaction, 15).contains(Text);
		return status;
	}

	/**
	 * Update the profile info of a User
	 */
	public void click_my_info_update() {
		button(my_info_update_btn, 5);
		waitForElement(my_info_update_successfully_msg, 5);
	}

	/**
	 * To get the token transaction details and their no. of occurrences for the
	 * user
	 * 
	 * @param description
	 * @param token_amount
	 * @param no_of_occurance
	 * @return
	 */
	public int verify_token_transactions_details(String description, String token_amount, int no_of_occurrences) {
		int no_of_times_token_transaction_msg = 0;
		List<WebElement> desc_list = get_webelements_list(token_history_description);
		log.info("Count of Token Transaction details: " + desc_list.size());
		for (int count = 1; count <= desc_list.size(); count = count + 1) {
			log.info("Tracking details of -" + count + "- Token Transaction");
			moveToElement(desc_list.get(count - 1));
			sleepFor(2);
			desc_list = get_webelements_list(token_history_description);
			if (desc_list.get(count - 1).getText().contains(description)) {
				no_of_times_token_transaction_msg++;
				log.info("No. of times token transaction message found: " + no_of_times_token_transaction_msg);
				WebElement token_element = DriverManager.getDriver()
						.findElement(By.xpath("//article[" + count + "]//div[@class='date_tokens']/span[2]"));
				String actual_token_amount = token_element.getText().replace("+", "").replace(",", "");
				if (token_amount.equals(actual_token_amount)) {
					if (no_of_occurrences > no_of_times_token_transaction_msg) {
						continue;
					} else {
						return no_of_times_token_transaction_msg;
					}
				} else {
					return no_of_times_token_transaction_msg;
				}
			}
		}
		return no_of_times_token_transaction_msg;
	}

	/**
	 * To get the token transaction details based on the given parameter
	 * 
	 * @param description
	 * @param token_amount
	 * @param no_of_occurance
	 * @return
	 */
	public String get_token_transaction_amount(int record_row) {
		By token_transaction_amount = By.xpath("//article[" + record_row + "]//div[@class='date_tokens']/span[2]");
		return getText(token_transaction_amount, 30).replace("+", "").replace(",", "");
	}

	/**
	 * Click My Info link
	 */
	public void click_my_info_link() {
		button(my_info_link, 5);
	}

	/**
	 * To Modify DOB
	 */
	public void modify_dob(String date, String month, String year) {
		selectByVisibleText(dob_month, month, 5);
		selectByVisibleText(dob_day, date, 5);
		selectByVisibleText(dob_year, year, 5);
	}
}
