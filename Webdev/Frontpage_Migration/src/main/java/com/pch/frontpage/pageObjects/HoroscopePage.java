package com.pch.frontpage.pageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class HoroscopePage extends BaseClass {

	private static final HoroscopePage horoscope_instance = new HoroscopePage();
	private final HomePage homepage_instance = HomePage.getInstance();

	private HoroscopePage() {
	}

	public static HoroscopePage getInstance() {
		return horoscope_instance;
	}

	private final By claimtokens_btn = By.xpath("//button[contains(text(), 'Claim Tokens')]");
	private final By today_lbl = By.xpath("//span[text()='TODAY']");
	private final By yesterday_lnk = By.xpath("//a[text()='Yesterday']");
	private final By tommorow_lnk = By.xpath("//a[text()='Tomorrow']");
	private final By today_lnk = By.xpath("//a[text()='Today']");
	private final By outlook_lnk = By.xpath("//a[text()='2018 Outlook']");
	private final By horoscope_title = By.xpath("//h2[@class='horoscope-overview__title']");
	private final By horoscope_sections = By.xpath("//h2[@class='section-header']");
	private final By horoscope_overview_text = By.xpath("//span[@class='horoscope-overview__text1']");
	private final By horoscope_month = By.xpath("//span[@class='horoscope-overview__text2']");
	private final By horoscope_gemini_lnk = By.xpath("//*[label='Gemini']");

	public void click_claimtokens() {
		button(claimtokens_btn, 20);
	}

	public boolean verify_today_lbl_displayed() {
		return (!getText(today_lbl, 10).isEmpty());
	}

	public boolean verify_claimtoken_btn_displayed() {
		return (elementPresent(claimtokens_btn));
	}

	public void click_yesterdaylnk() {
		button(yesterday_lnk, 10);
	}

	public String verify_overview_displayed() {
		return getText(horoscope_title, 10);
	}

	public void click_tommorow_lnk() {
		button(tommorow_lnk, 10);
	}

	public void click_today_lnk() {
		button(today_lnk, 10);
	}

	public void click_outlook_link() {
		button(outlook_lnk, 10);
	}

	public List<String> getSubsectionsList() {
		List<String> str = new ArrayList<String>();
		for (WebElement e : get_webelements_list(horoscope_sections)) {
			str.add(e.getText());
		}
		return str;
	}

	public void click_gemini() {
		button(horoscope_gemini_lnk, 10);
	}

	public String verify_overviewtext() {
		return getText(horoscope_overview_text, 10);
	}

	public String get_horoscope_month() {
		return getText(horoscope_month, 10);
	}

	public Integer randomtokens() {
		Random random = new Random();
		int x = random.nextInt(900) + 100;
		return x;
	}
	/**
	 * Cliam tokens and verify vip level up message
	 * @return true
	 */
	public boolean validating_vip_level_up(String firstName) throws Exception {
		homepage_instance.click_horoscope_menu();
		homepage_instance.click_claim_button();
		assertTrue(homepage_instance.verify_claimed_button());
		try {
			String vip_c1_msg = "Hi, "+firstName+""+msg_property_file_reader("vip_c1_msg_header")+"\n"+msg_property_file_reader("vip_c1_msg_body");
			System.out.println("vip_c1_msg: "+vip_c1_msg);
			homepage_instance.clic_new_VIP_logo(5);
			String afterActivity= homepage_instance.get_vip_msg();
			assertNotEquals(afterActivity, vip_c1_msg);
		return true;
			}
			catch(Exception e) {
				return false;
			}
	}

}
