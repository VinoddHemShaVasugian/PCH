package com.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class HoroscopePage extends BaseClass {

	private static final HoroscopePage horoscope_instance = new HoroscopePage();

	private HoroscopePage() {
	}

	public static HoroscopePage getInstance() {
		return horoscope_instance;
	}

	private final By today_label = By.xpath("//span[text()='TODAY']");
	private final By yesterday_link = By.xpath("//a[text()='Yesterday']");
	private final By tommorow_link = By.xpath("//a[text()='Tomorrow']");
	private final By today_link = By.xpath("//a[text()='Today']");
	private final By outlook_link = By.xpath("//a[text()='2018 Outlook']");
	private final By horoscope_title = By.xpath("//h2[@class='horoscope-overview__title']");
	private final By horoscope_sections = By.xpath("//h2[@class='section-header']");
	private final By horoscope_overview_text = By.xpath("//span[@class='horoscope-overview__text1']");
	private final By horoscope_month = By.xpath("//span[@class='horoscope-overview__text2']");
	private final By horoscope_gemini_lnk = By.xpath("//*[label='Gemini']");
	private final By unclaim_token_amount = By.cssSelector("span.buttons__token-amount");

	/**
	 * Verify the high-lightened of Horoscope Today's link
	 * 
	 * @return
	 */
	public boolean verify_today_label() {
		
		return elementPresent(today_label);
	}

	/**
	 * Click on the Yesterday Horoscope link
	 */
	public void click_yesterday_link() {
		button(yesterday_link, 10);
	}

	/**
	 * Verify the Horoscope title
	 */
	public String verify_overview_displayed() {
		return getText(horoscope_title, 10);
	}

	/**
	 * Click on the Tomorrow Horoscope link
	 */
	public void click_tommorow_link() {
		button(tommorow_link, 10);
	}

	/**
	 * Click on the Today Horoscope link
	 */
	public void click_today_link() {
		button(today_link, 10);
	}

	/**
	 * Click on the Outlook Horoscope link
	 */
	public void click_outlook_link() {
		button(outlook_link, 10);
	}

	/**
	 * Retrieve the Horoscope sub sections list
	 */
	public List<String> getSubsectionsList() {
		List<String> str = new ArrayList<String>();
		for (WebElement e : get_webelements_list(horoscope_sections)) {
			str.add(e.getText());
		}
		return str;
	}

	/**
	 * Click on the Horoscope Gemini link
	 */
	public void click_gemini() {
		button(horoscope_gemini_lnk, 10);
	}

	/**
	 * Retrieve the Horoscope title
	 * 
	 * @return
	 */
	public String verify_overview_text() {
		return getText(horoscope_overview_text, 10);
	}

	/**
	 * Retrieve the current Horoscope month
	 * 
	 * @return
	 */
	public String get_horoscope_month() {
		return getText(horoscope_month, 10);
	}
	/**
	 * Get the Claim Token value
	 * 
	 * @return
	 */
	public String get_unclaim_token_value() {
		return getText(unclaim_token_amount, 15);
	}
}
