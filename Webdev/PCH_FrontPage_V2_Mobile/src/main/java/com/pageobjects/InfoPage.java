package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class InfoPage extends BaseClass {
	private static final InfoPage info_instance = new InfoPage();

	private InfoPage() {
	}

	public static InfoPage getInstance() {
		return info_instance;
	}

	private final By about_page = By.xpath("//div[@class='site-links']/a[contains(text(),'About Frontpage')]");
	private final By how_to_search = By.xpath("//div[@class='site-links']/a[contains(text(),'How To Search')]");
	private final By earn_tokens = By.xpath("//div[@class='site-links']/a[contains(text(),'Earn Tokens')]");
	private final By do_donts = By.xpath("//div[@class='site-links']/a[contains(@href,'/dos-donts')]");
	private final By ways_to_win = By.xpath("//div[@class='site-links']/a[contains(text(),'Ways To Win')]");
	private final By official_rules = By.xpath("//a[contains(text(),'Rules')]");
	private final By contest_details = By.xpath("//a[contains(text(),'Contest')]");
	private final By sweepstake_facts = By.xpath("//a[contains(text(),'Facts')]");

	public void click_about_page() {
		button(about_page, 10);
	}

	public void click_how_to_search() {
		button(how_to_search, 10);
	}

	public void click_earn_tokens() {
		button(earn_tokens, 10);
	}

	public void click_do_donts() {
		button(do_donts, 10);
	}

	public void click_ways_to_win() {
		button(ways_to_win, 10);
	}

	public void click_contest_details() {
		button(contest_details, 10);
	}

	public void click_official_rules() {
		button(official_rules, 10);
	}

	public void click_sweepstake_facts() {
		button(sweepstake_facts, 10);
	}

}
