package com.pageobjects;

import java.util.LinkedList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class HealthPage extends BaseClass {

	private static final HealthPage health_instance = new HealthPage();

	private HealthPage() {
	}

	public static HealthPage getInstance() {
		return health_instance;
	}

	private final By tile_category_sub_menus = By.cssSelector("a.menu__item__link");

	/**
	 * Return the Sub Menu Tile names in the Linked List
	 * 
	 * @return
	 */
	public LinkedList<String> get_tile_sub_menus() {
		LinkedList<String> sub_menu_list = new LinkedList<String>();
		for (WebElement ele : get_webelements_list(tile_category_sub_menus)) {
			sub_menu_list.add(ele.getText());
		}
		return sub_menu_list;
	}

	/**
	 * Click on the Forward arrow of the tile section. Arrow which present above
	 * the video section.
	 * 
	 * @param tile_category_name
	 */
	public void click_tile_section_forward_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/following-sibling::div/*[@class='svg-carousel-nav-forward']");
		button(locator, 60);
	}

	/**
	 * Click on the Backward arrow of the tile section. Arrow which present
	 * above the video section.
	 * 
	 * @param tile_category_name
	 */
	public void click_tile_section_backward_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/following-sibling::div/*[@class='svg-carousel-nav-backward']");
		button(locator, 60);
	}

	/**
	 * Verify the disable state of the Forward arrow. Arrow which present above
	 * the video section.
	 * 
	 * @param tile_category_name
	 */
	public boolean verify_disablity_of_tile_section_forward_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/following-sibling::div[@class='svg-carousel-nav-container at-last']");
		return elementVisibility(locator, 60);
	}

	/**
	 * Verify the disable state of the Backward arrow. Arrow which present above
	 * the video section.
	 * 
	 * @param tile_category_name
	 */
	public boolean verify_disablity_of_tile_section_backward_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/following-sibling::div[@class='svg-carousel-nav-container at-first']");
		return elementVisibility(locator, 60);
	}

	/**
	 * Verify the Enable state of both the Forward and Backward arrows.
	 * 
	 * @param tile_category_name
	 * @return
	 */
	public boolean verify_enable_state_of_tile_section_arrows(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/following-sibling::div[@class='svg-carousel-nav-container']");
		return elementVisibility(locator, 60);
	}

	/**
	 * Click on the Forward arrow when hover the tile section.
	 * 
	 * @param tile_category_name
	 */
	public void click_tile_section_hovering_forward_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/parent::div/following-sibling::div[1]/a[@class='pch-carousel-forward-link']");
		mouseHover(locator);
		button(locator, 60);
	}

	/**
	 * Click on the Backward arrow when hover the tile section.
	 * 
	 * @param tile_category_name
	 */
	public void click_tile_section_hovering_backward_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/parent::div/following-sibling::div[1]/a[@class='pch-carousel-back-link']");
		mouseHover(locator);
		button(locator, 60);
	}

	/**
	 * Verify the disable state of the hovering Forward arrow.
	 * 
	 * @param tile_category_name
	 * @return
	 */
	public boolean verify_disablity_of_tile_section_hovering_forward_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/parent::div/following-sibling::div[@class='pch-carousel at-last'][1]");
		mouseHover(locator);
		return elementVisibility(locator, 60);
	}

	/**
	 * Verify the disable state of the hovering Backward arrow.
	 * 
	 * @param tile_category_name
	 * @return
	 */
	public boolean verify_disablity_of_tile_section_hovering_backward_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/parent::div/following-sibling::div[@class='pch-carousel at-first'][1]");
		mouseHover(locator);
		return elementVisibility(locator, 60);
	}

	/**
	 * Verify the enable state of both the Forward & Backward hovering arrow
	 * 
	 * @param tile_category_name
	 * @return
	 */
	public boolean verify_enable_state_of_tile_section_hovering_arrow(String tile_category_name) {
		By locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/parent::div/following-sibling::div[@class='pch-carousel'][1]");
		mouseHover(locator);
		return elementVisibility(locator, 60);
	}

	/**
	 * Return the count of visible videos of the category section
	 * 
	 * @param tile_category_name
	 * @return
	 */
	public int get_visible_video_of_section(String tile_category_name) {
		By locator;
		try {
			locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
					+ "')]/parent::div/following-sibling::div[1]/div/div[contains(@class,'pch-carousel-content-item d-fxx d-f')]");
			return get_webelements_list(locator).size();
		} catch (Exception e) {
			locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
					+ "')]/parent::div/following-sibling::div[1]/div/div[contains(@class,'pch-carousel-content-item d-fxx d-b')]");
			return get_webelements_list(locator).size();
		}
	}

	/**
	 * Click a video of the Tile category section.
	 * 
	 * @param tile_category_name
	 *            - Click the video of the given category param
	 * @param video_position
	 *            - Click the given video position. On default it click the
	 *            first video. It's variable argument.
	 * @return
	 */
	public String click_video_of_tile_category(String tile_category_name, int... video_position) {
		By video_locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/parent::div/following-sibling::div[1]/div/div[1]/a/div");
		By video_title_locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
				+ "')]/parent::div/following-sibling::div[1]/div/div[1]/a/div[2]");
		String video_title;
		if (video_position.length > 0 && video_position[0] > 0 && video_position[0] < 4) {
			video_locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
					+ "')]/parent::div/following-sibling::div[1]/div/div[" + video_position + "]/a/div");
			video_title_locator = By.xpath("//div[contains(text(),'" + tile_category_name.toUpperCase()
					+ "')]/parent::div/following-sibling::div[1]/div/div[" + video_position + "]/a/div[2]");
		}
		video_title = getText(video_title_locator, 30);
		button(video_locator, 60);
		return video_title;
	}
}
