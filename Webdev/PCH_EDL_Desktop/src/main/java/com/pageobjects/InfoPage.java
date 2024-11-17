package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class InfoPage extends BaseClass {

	private static InfoPage infopage_instance = new InfoPage();

	private InfoPage() {
	}

	public static InfoPage getInstance() {
		return infopage_instance;
	}

	private final By official_rules_link = By.xpath("//div[@id='sw_rules']/a[1]");
	private final By official_prizes_link = By.xpath("//div[@id='sw_rules']/a[2]");
	private final By edl_official_prizes_link = By.xpath("//div[@id='sw_rules']/a[3]");

	private final By official_rules_title_txt = By.id("rules_header");
	private final By official_prizes_title_txt = By.xpath("//div[@id='official_prizes']/h1");
	private final By edl_official_prizes_title_txt = By.xpath("//div[@id='edl_official_prizes']/h1");
	private final By facts_box = By.className("factsBox");

	/** Returns the number of facts boxes present on page */
	public int get_facts_box_count() {
		return get_webelements_list(facts_box).size();
	}

	/** Returns the rules page title */
	public String get_rules_page_title() {

		return getTitle();
	}

	/** Returns the official rules link text. First link on rules page */
	public String get_official_rules_link_text() {
		moveToElement(official_rules_link);
		return getText(official_rules_link, 20);
	}

	/** Returns the official prizes link text. Second link on rules page */
	public String get_official_prizes_link_text() {
		moveToElement(official_prizes_link);
		return getText(official_prizes_link, 20);
	}

	/** Returns the Edl prizes link text. Third link on rules page */
	public String get_edl_official_prizes_link_text() {
		moveToElement(edl_official_prizes_link);
		return getText(edl_official_prizes_link, 20);
	}

	/** Click on the official rules link */
	public void click_official_rules_link_text() {
		button(official_rules_link, 20);
	}

	/** Clicks on the official prizes link */
	public void click_official_prizes_link_text() {
		button(official_prizes_link, 20);
	}

	/** Click on the edl prizes link */
	public void click_edl_official_prizes_link_text() {
		button(edl_official_prizes_link, 20);
	}

	/** Returns the text of the official rules Title */
	public String get_official_rules_title_text() {
		moveToElement(official_rules_title_txt);
		return getText(official_rules_title_txt, 20);
	}

	/** Returns the text of the official prizes title */
	public String get_official_prizes_title_text() {
		moveToElement(official_prizes_title_txt);
		return getText(official_prizes_title_txt, 20);
	}

	/** Returns the text of the edl prizes title */
	public String get_edl_official_prizes_title_text() {
		moveToElement(edl_official_prizes_title_txt);
		return getText(edl_official_prizes_title_txt, 20);
	}

}
