package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class InHouseAlertPage extends BaseClass {

	private static final InHouseAlertPage news_alert__instance = new InHouseAlertPage();

	private InHouseAlertPage() {
	}

	public static InHouseAlertPage getInstance() {
		return news_alert__instance;
	}

	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();

	/**
	 * Verify the News Alert section
	 */
	public boolean verify_news_alert_section(String news_alert_name) {
		By news_alert = By.xpath("//a[@class='top-stories__li__a'][contains(.,'" + news_alert_name + "')]");
		return elementPresent(news_alert);
	}

	/**
	 * Click on the News Alert section
	 */
	public void click_on_news_alert_section(String news_alert_name) {
		By news_alert = By.xpath("//a[@class='top-stories__li__a'][contains(.,'" + news_alert_name + "')]");
		button(news_alert, 15);
	}

	/**
	 * Return the position of the GPT Ad unit in the Sub Category page
	 * 
	 * @return
	 */
	public int get_position_of_news_alert_on_category_page(String news_alert_name) {
		By news_alert_position = By.xpath("//a[@class='top-stories__li__a'][contains(.,'" + news_alert_name
				+ "')]/parent::li/preceding-sibling::*");
		return get_webelements_list(news_alert_position).size() + 1;
	}

	/**
	 * Select the news alert position
	 * 
	 * @param alert_position
	 */
	public void select_news_alert_positon(int alert_position) {
		selectByVisibleText(admin_instance.get_dropdown_field_element_by_label("Alert Position"),
				String.valueOf(alert_position), 15);
	}

	/**
	 * Select the news alert display category page
	 * 
	 * @param alert_position
	 */
	public void select_news_alert_category_page(int alert_category_position) {
		deSelectAll(admin_instance.get_dropdown_field_element_by_label("Category", "2"), 15);
		selectByValue(admin_instance.get_dropdown_field_element_by_label("Category", "2"),
				String.valueOf(alert_category_position), 15);
	}
}
