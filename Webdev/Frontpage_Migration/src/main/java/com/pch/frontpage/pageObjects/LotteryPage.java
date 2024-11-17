package com.pch.frontpage.pageObjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class LotteryPage extends BaseClass {

	private static final LotteryPage lottery_instance = new LotteryPage();
	private final HomePage homepage_instance = HomePage.getInstance();

	private LotteryPage() {
	}

	public static LotteryPage getInstance() {
		return lottery_instance;
	}

	private final By lotterypage_header = By.xpath("//*[@class = 'section-header section-header--large clearfix']");
	private final By change_lottery_location_btn = By.id("lotto-map__selector");
	private final By lottery_map = By.id("lotto-map__map");
	private final By lotterymap_michigan = By.xpath("//*[@id='lotto-map__map']//a[text()='Michigan']");
	private final By lotterymap_fl = By.id("usmap-FL");
	private final By lottery_state_title = By.xpath("//*[@class='lotto-map__state']");
	private final By last_drawing_date = By
			.xpath("//*[text()='Last Drawing:']/following-sibling::span[@class='lotto-draw__date'][1]");
	private final By next_drawing_date = By
			.xpath("//*[text()='Next Drawing:']/following-sibling::span[@class='lotto-draw__date'][1]");
	private final By pastresults_nav_lnk = By.xpath("//*[text()='Past Results']");
	private final By payouts_nav_lnk = By.xpath("//*[text()='Payout']");
	private final By past_results_side = By.xpath("//div[@class='lotto-past-results__table-side-caption']");
	private final By past_results_colums = By.xpath("//th[@class='lotto-past-results__th']");
	private final By payout_columns = By.xpath("//tr[@class='lotto-payout__tr']/th");
	private final By payout_pastresults_nav_lnk = By
			.xpath("//a[@class = 'lotto-past-results-nav__link' and contains(text(), 'Click for payouts')]");
	private final By payout_header_text = By.xpath("//div[@class='lotto-past-results-nav']");
	private final By payout_payouts_nav_lnk = By
			.xpath("//a[@class = 'lotto-past-results-nav__link' and contains(text(), 'Click for past results')]");
	private final By claimtokens_btn = By.xpath("//button[contains(text(), 'Claim Tokens')]");

	public String get_lotterypage_headertext() {
		return getText(lotterypage_header, 20);
	}
	/**
	 * Cliam tokens and verify vip level up message
	 * @throws Exception 
	 */
	public boolean validating_vip_level_up(String firstName) throws Exception {
		homepage_instance.click_lottery_menu();
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

	public void clicklottrylocation() {
		button(change_lottery_location_btn, 10);
	}

	public void clicklotterymap_michigan() {
		waitForElement(lottery_map);
		button(lotterymap_michigan, 10);
	}

	public String get_lottery_state_title() {
		return getText(lottery_state_title, 20);
	}

	public void clicklotterymap_florida() {
		waitForElement(lottery_map);
		button(lotterymap_fl, 10);
	}

	public String get_lottery_lastdrawdate() {
		return get_webelements_list(last_drawing_date).get(0).getText();
	}

	public String get_lottery_nextdrawdate() {
		return get_webelements_list(next_drawing_date).get(0).getText();
	}

	public void click_lottery_pastresultslnk() {
		button(get_webelements_list(pastresults_nav_lnk).get(0), 15);
	}

	public void click_lottery_payoutslnk() {
		button(get_webelements_list(payouts_nav_lnk).get(0), 15);
	}

	public String get_past_result_side() {
		return getText(past_results_side, 10);
	}

	public List<String> get_lottery_past_results_colums() {
		List<String> columns = new ArrayList<String>();
		for (WebElement e : get_webelements_list(past_results_colums)) {
			columns.add(e.getText());
		}
		return columns;
	}

	public List<String> get_lottery_payouts_colums() {
		List<String> columns = new ArrayList<String>();
		for (WebElement e : get_webelements_list(payout_columns)) {
			columns.add(e.getText());
		}
		return columns;
	}

	public void click_payout_pastresults_nav_lnk() {
		button(payout_pastresults_nav_lnk, 20);
	}

	public void click_payout_payouts_nav_lnk() {
		button(payout_payouts_nav_lnk, 20);
	}

	public String get_payout_header_text() {
		return getText(payout_header_text, 10);
	}

	public void click_claimtokens() {
		button(claimtokens_btn, 20);
	}

	public boolean verify_claimtokens_displayed() {
		return elementPresent(claimtokens_btn);
	}

	public boolean verify_lastdraw_currentdate() throws ParseException {
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
		df.setLenient(false);
		Date dt = df.parse(get_lottery_lastdrawdate().toString() + " 23:59:59");
		System.out.println("Next draw date" + dt);

		System.out.println("Current date: " + date);

		if (dt.compareTo(date) < 0) {
			return true;
		} else if (dt.compareTo(date) == 0) {
			return true;
		} else {
			return false;
		}

	}

	public boolean verify_nextdraw_currentdate() throws ParseException {
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
		df.setLenient(false);
		Date dt = df.parse(get_lottery_nextdrawdate().toString() + " 23:59:59");
		System.out.println(dt);

		System.out.println(date);

		if (dt.compareTo(date) > 0) {
			return true;
		} else if (dt.compareTo(date) == 0) {
			return true;
		} else {
			return false;
		}

	}

}
