package com.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.util.BaseClass;
import com.util.DriverManager;

public class SERPage extends BaseClass {

	private static final SERPage serp_instance = new SERPage();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private SERPage() {
	}

	/**
	 * Returns a Singlton instance
	 * 
	 * @return SERPage
	 */
	public static SERPage getInstance() {
		return serp_instance;
	}

	private final By in_house_ad = By.cssSelector("div.bigFish");
	private final By pagination_next = By.cssSelector("a.page-link next");
	private final By pagination_previous = By.cssSelector("span.current.prev");

	private final By product_carousel = By.cssSelector("div#prodCarousel-products>div");
	private final By shopping_ads_title = By.cssSelector("div#shoppingResultsBlock>div");
	private final By more_shopping_results = By.linkText("More Shopping Results �");
	private final By first_product_prize = By
			.xpath("//div[@id='prodCarousel-products']/div[1]//div[@class='shoppingPrices']/span");
	private final By sort_dropdown = By.cssSelector("select.sort-options");
	private final By top_results_block = By.id("topResultsBlock");
	private final By shopping_results_block = By.id("shoppingResultsBlock");
	private final By bottom_results_block = By.id("bottomResultsBlock");
	private final By main_results_block = By.id("mainResultsBlock");
	private final By serp_message = By.cssSelector("#messageResults > span");

	/**
	 * Return the Price of the First shopping product
	 */
	public String get_prize_of_first_shopping_product() {
		return getText(first_product_prize, 60);
	}

	/**
	 * Click on the First shopping product
	 */
	public void click_first_shopping_product() {
		button(first_product_prize, 60);
	}

	/**
	 * Return the Title of the Inline Shopping ads carousel.
	 * 
	 * @return
	 */
	public String get_shopping_ads_title() {
		return getText(shopping_ads_title, 30);
	}

	/**
	 * Verify the SERP results page Web results section.
	 * 
	 * @return
	 */
	public boolean verify_serp_page_main_results_section() {
		return elementPresent(main_results_block);
	}

	/**
	 * Verify the SERP results page Ads section.
	 * 
	 * @return
	 */
	public boolean verify_serp_page_ad_results_section() {
		return elementPresent(top_results_block) && elementPresent(bottom_results_block);
	}

	/**
	 * Verify the SERP results page shopping section.
	 * 
	 * @return
	 */
	public boolean verify_serp_page_shopping_results_section() {
		return elementPresent(shopping_results_block);
	}

	/**
	 * Click on more shopping results links
	 */
	public void click_more_shopping_results() {
		button(more_shopping_results, 10);
	}

	/**
	 * To validate the In-line Shopping Ads carousel."
	 */
	public boolean verify_inline_shopping_ads_carousel() {
		return elementVisibility(product_carousel) && elementVisibility(more_shopping_results);
	}

	/**
	 * To verify the pagination links functionality
	 */
	public boolean verify_pagination_functionality(String searchterm) {
		boolean status;
		button(pagination_next, 10);
		status = DriverManager.getDriver().getCurrentUrl()
				.contains(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&page=2");
		button(pagination_next, 10);
		status = status && DriverManager.getDriver().getCurrentUrl()
				.contains(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&page=3");
		button(pagination_previous, 10);
		status = DriverManager.getDriver().getCurrentUrl()
				.contains(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&page=2");
		button(pagination_previous, 10);
		status = status && DriverManager.getDriver().getCurrentUrl()
				.contains(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&page=1");
		return status;
	}

	/**
	 * To verify the Shopping results sorting functionality
	 */
	public boolean verifiy_shopping_sorting_results(String searchterm) {
		boolean status;
		selectByVisibleText(sort_dropdown, "Relevance", 10);
		status = DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&cat=shopping&sort=Relevance");
		log.info("Page reloaded successfully wtih the correct sorting Option: Relevance");

		sleepFor(2);
		selectByVisibleText(sort_dropdown, "Price, Low to High", 10);
		status = status && DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&cat=shopping&sort=Price/asc");
		log.info("Page reloaded successfully wtih the correct sorting Option: Price, Low to High");

		sleepFor(2);
		selectByVisibleText(sort_dropdown, "Price, High to Low", 10);
		status = status && DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&cat=shopping&sort=Price");
		log.info("Page reloaded successfully wtih the correct sorting Option: Price, High to Low");

		return status;
	}

	/**
	 * To verify the Search Results page url.
	 */
	public boolean verify_search_results_url(String searchterm) {
		return (DriverManager.getDriver().getCurrentUrl()
				.contains(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm));
	}

	/**
	 * To Validate the SERP page In House Ad- Big Fish & Super Prize Ad
	 */
	public boolean verify_serp_inhouse_ad() {
		return elementPresent(in_house_ad);
	}
	
	/**
	 * To Validate the SERP page completely
	 */
	public boolean verify_SERP_Completely() {
		return elementPresent(serp_message);
	}

}