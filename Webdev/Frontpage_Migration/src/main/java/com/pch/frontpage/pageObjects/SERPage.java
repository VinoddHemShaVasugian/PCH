package com.pch.frontpage.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.util.BaseClass;
import com.util.DriverManager;

public class SERPage extends BaseClass {

	private static final SERPage serp_instance = new SERPage();

	private SERPage() {
	}

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final By serp_message = By.cssSelector("#messageResults > span");
	private final By search_box = By.name("q");
	private final By searchBtn = By.cssSelector("div.search__bar-wrapper__submit");
	private final By inhouse_ad = By.cssSelector("div.bigFish");
	private final By pagination_next = By.linkText("Next");
	private final By pagination_previous = By.linkText("Previous");
	private final By serpHeaderPage = By.xpath(".//*[@id='messageResults']/span[1]");
	private final By WebIcon = By.xpath(".//*[@class='serp-left-categories']/li[1]");
	private final By ShopIcon = By.xpath(".//*[@class='serp-left-categories']/li[2]");
	private final By topAds = By.id("topResultsBlock");
	private final By bottomAds = By.id("bottomResultsBlock");
	private final By carouselProducts = By.xpath(".//*[@id='prodCarousel-products']");
	private final By sorting = By.xpath(".//*[@id='shopping-sort-options']//select");
	private final By moreShoppingResults = By.linkText("More Shopping Results »");
	private final By webResults = By.xpath("//div[contains(@class,'searchResult')]//a[@class='resultTitle']");

	/**
	 * Returns a Singlton instance
	 * 
	 * @return SERPage
	 */
	public static SERPage getInstance() {
		return serp_instance;
	}

	/**
	 * Validate the left side bar.
	 */
	public boolean verifyLeftsidebar() {
		System.out.println(DriverManager.getDriver().findElements(webResults).size());
		return DriverManager.getDriver().findElements(webResults).size() > 1;
	}

	/**
	 * to click on more shopping results links
	 */
	public void click_moreShoppingResults() {
		button(moreShoppingResults, 10);
	}

	/**
	 * To verify the sorting Shopping results
	 */
	public boolean verifiy_sortingShoppingResults(String searchterm) {
		boolean status;
		selectByVisibleText(sorting, "Relevance", 10);
		status = DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&cat=shopping&sort=Relevance");
		log.info("Page reloaded successfully wtih the correct sorting Option: Relevance");

		sleepFor(2);
		selectByVisibleText(sorting, "Price, Low to High", 10);
		status = status && DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&cat=shopping&sort=Price/asc");
		log.info("Page reloaded successfully wtih the correct sorting Option: Price, Low to High");

		sleepFor(2);
		selectByVisibleText(sorting, "Price, High to Low", 10);
		status = status && DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&cat=shopping&sort=Price");
		log.info("Page reloaded successfully wtih the correct sorting Option: Price, High to Low");

		return status;
	}

	/**
	 * To validate the In-line Shopping Ads carousel."
	 */
	public boolean verify_ShoppingAdsCarousel() {
		return elementPresent(serpHeaderPage) && elementPresent(WebIcon) && elementPresent(ShopIcon)
				&& elementPresent(topAds) && elementPresent(bottomAds) && elementPresent(carouselProducts);
	}

	/**
	 * To verify the pagination links functionality
	 */
	public boolean verify_paginationFunctionality(String searchterm) {
		boolean status;
		button(pagination_next, 10);
		status = DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&page=2");
		button(pagination_next, 10);
		status = status && DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&page=3");
		button(pagination_previous, 10);
		status = DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&page=2");
		button(pagination_previous, 10);
		status = status && DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm + "&page=1");
		return status;
	}

	/**
	 * To Validate the SERP page completely
	 */
	public boolean verify_SERP_Completely() {
		return elementPresent(serp_message);
	}

	/**
	 * To verify the Search Results page.
	 */
	public boolean verify_searchresultspage(String searchterm) {
		return elementVisibility(serp_message) && (DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm));
	}

	/**
	 * To verify the Search Results page url.
	 */
	public boolean verify_search_results_url(String searchterm) {
		return (DriverManager.getDriver().getCurrentUrl()
				.equals(xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + searchterm));
	}

	/**
	 * Search the given string
	 * 
	 * @param SearchString
	 */
	public void search(String searchvalue) {
		textbox(search_box, "enter", searchvalue, 5);
		button(searchBtn, 10);
		sleepFor(2);
	}

	/**
	 * Click on the Search button
	 * 
	 */
	public void click_search_button() {
		button(searchBtn, 3);
	}

	/**
	 * Returns the SERP message
	 * 
	 * @return
	 */
	public String get_serp_message() {
		return getText(serp_message, 5);
	}

	/**
	 * To Validate the SERP page In House Ad
	 */
	public boolean verify_serp_inhouse_ad() {
		return elementPresent(inhouse_ad);
	}

	/**
	 * To Validate the SERP page In House Ad
	 */
	public boolean verify_search_right_rail_box() {
		return elementPresent(search_box);
	}
}