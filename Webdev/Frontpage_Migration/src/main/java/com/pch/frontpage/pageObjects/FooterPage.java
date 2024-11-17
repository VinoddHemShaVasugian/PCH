package com.pch.frontpage.pageObjects;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class FooterPage extends BaseClass {
	private static final FooterPage footer_instance = new FooterPage();

	private FooterPage() {
	}

	public static FooterPage getInstance() {
		return footer_instance;
	}

	private final By footer_menu = By.cssSelector("footer.footer li a");
	private final By footer_add_on_url = By.cssSelector("footer.footer div.middle>a");
	private final By bbb_logo = By.cssSelector("img[alt='BBB']");
	private final By truste_logo = By.cssSelector("img[alt='TRUSTe']");
	private final By copyright_text = By.cssSelector("footer.footer p.container");

	/**
	 * Get the Copyright text
	 * 
	 * @return
	 */
	public String get_copyright_text() {
		return getText(copyright_text, 5).replace("\n", "").replace("\r", "");
	}

	/**
	 * Click on BBB logo
	 */
	public void click_bbb_logo() {
		button(bbb_logo, 10);
	}

	/**
	 * Click on Truste logo
	 */
	public void click_truste_logo() {
		button(truste_logo, 10);
	}

	/**
	 * Return the Category list from the Footer menu
	 * 
	 * @return
	 */
	public LinkedList<String> get_footer_menu_category_urls() {
		List<WebElement> ele_list = get_webelements_list(footer_menu);
		LinkedList<String> catagory_urls = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			catagory_urls.add(ele.getAttribute("href"));
		}
		return catagory_urls;
	}

	/**
	 * Return the Add-On list URL's from the Footer menu
	 * 
	 * @return
	 */
	public LinkedList<String> get_footer_add_on_urls() {
		List<WebElement> ele_list = get_webelements_list(footer_add_on_url);
		LinkedList<String> add_on_urls = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			add_on_urls.add(ele.getAttribute("href"));
		}
		return add_on_urls;
	}

	public List<WebElement> get_footer_menu() {
		return get_webelements_list(footer_menu);
	}

}
