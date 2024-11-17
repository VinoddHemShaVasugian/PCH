package com.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class ContestEntryPage extends BaseClass {

	private static final ContestEntryPage instance = new ContestEntryPage();

	private ContestEntryPage() {
	}

	public static ContestEntryPage getInstance() {
		return instance;
	}

	private final By managing_accounts_button = By.xpath("//span[contains(text(),'ManagingAccounts')]");
	private final By search_button = By.xpath("//span/span[text()='Search']");
	private final By leagacy_contest_entry_link = By.xpath("//u[text()='Legacy Contest Entries']");
	private final By email_textfield = By.name("email");
	private final By search_email = By.xpath("//span[text()='Search']/following-sibling::span");
	private final By entry_date_column_header = By.xpath("//div/span[text()='Entry Date']");
	private final By property_entry = By.xpath("//tbody[starts-with(@id,'gridview')]//tr[1]/td[1]/div");
	private final By source_key = By.xpath("//tbody[starts-with(@id,'gridview')]//tr[1]/td[2]/div");
	private final By source_key_list = By.xpath("//td[@class='x-grid-cell x-grid-td x-grid-cell-gridcolumn-1254 ']");

	/**
	 * Retrieve the Property Entry value of the first row
	 * 
	 * @return
	 */
	public String get_property_entry_of_first_row() {
		return getText(property_entry, 30);
	}

	/**
	 * Retrieve the Source Key value of the first row
	 * 
	 * @return
	 */
	public String get_source_key_of_first_row() {
		return getText(source_key, 30);
	}
	
	/**
	 * Retrieve the Source Key value of the first row
	 * 
	 * @return
	 */
	public List<String> get_source_key_list() {
		
		List<String> elemContentList = new ArrayList<String>();
		List<WebElement> allElems = get_webelements_list(source_key_list);
		for(WebElement elem : allElems){
			elemContentList.add(elem.getText());			
		}
		return elemContentList;
	}

	/**
	 * Verify the display of Managing Account button
	 * 
	 * @return
	 */
	public boolean verify_managing_accounts() {
		return elementVisibility(managing_accounts_button);
	}

	/**
	 * Click on the Search button
	 */
	public void click_search_button() {
		button(search_button, 45);
	}

	/**
	 * Click on the Legacy Entry link
	 */
	public void click_leagacy_contest_entry_link() {
		button(leagacy_contest_entry_link, 45);
	}

	/**
	 * Enter the Email to be search
	 * 
	 * @param user_email
	 */
	public void enter_search_email(String user_email) {
		textbox(email_textfield, "enter", user_email, 30);
	}

	/**
	 * Click on the Entry Date header columns
	 */
	public void click_entry_date_header() {
		button(entry_date_column_header, 30);
	}

	/**
	 * Click on the Search button on the Legacy Entry page
	 */
	public void click_search_on_entry_page() {
		button(search_email, 45);
	}

}
