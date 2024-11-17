package com.pch.automation.pages;

import java.util.List;

import org.openqa.selenium.By;

import com.pch.automation.utilities.RandomGenerator;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * Page objects and methods for SERP page
 *
 * @author vsankar
 */

public class SerpPage extends PageObject {

	private final By serpMessage = By.cssSelector("div.serp__message>span");
	private final By searchBox = By.id("searchField0");
	private final By searchButton = By.cssSelector("div.search__bar-wrapper__submit");
	private final By cvCompleteDays = By.cssSelector("div.day-number.compl-day-number");
	private final By cvAll5DaysComplete = By.cssSelector("div.compl-mission-day-number.day-number.compl-day-number");
	private final By cvAll5DaysCompleteMsg = By.cssSelector("div.compl-mission-message");
	private final By dfpBanner = By.cssSelector("div.rightSerpAd");
	private final By results = By.cssSelector("section.serp__results");
	private final By noResultsFound = By.xpath("//iframe[@data-s1search-id='mainline-notice-noresults-empty']");
	private final By searchBars = By.cssSelector("form.serp-search-bar__form");
	private final By aylf = By.cssSelector("div.aylf-bing-top");
	private final By suspiciousTerms = By.cssSelector("div.bigFish");

	HeaderAndUninavPage uninavPage;

	/**
	 * Return the completed CV days
	 * 
	 * @return
	 */
	public int getCVCompletedDays() {
		if (isElementVisible(cvAll5DaysComplete)) {
			return 5;
		} else {
			return findAll(cvCompleteDays).size();
		}

	}

	/**
	 * Return the CV 5th day complete message
	 * 
	 * @return
	 */
	public String getCV5DayCompleteMsg() {
		return element(cvAll5DaysCompleteMsg).getText();
	}

	/**
	 * To Validate the SERP page completely
	 * 
	 * @return True
	 */
	public boolean verifySERPCompletely() {
		return isElementVisible(serpMessage);
	}

	/**
	 * To verify Are you looking for section on SERP page
	 * 
	 * @return True
	 */
	public boolean verifyAreYouLookingForSection() {
		return isElementVisible(aylf);
	}

	/**
	 * To perform consecutive searches
	 * 
	 * @param searchCount
	 * @author vsankar
	 */
	public void consecutiveSearches(int searchCount, String keyword) {
		for (int i = 0; i < searchCount; i++) {
			search(keyword);
		}
	}

	/**
	 * To perform consecutive searches
	 * 
	 * @param searchCount
	 * @author vsankar
	 */
	public void consecutiveSearches(int searchCount) {
		for (int i = 0; i <= searchCount; i++) {
			search();
		}
	}

	/**
	 * Do the search with the given keyword
	 * 
	 * @param keyword
	 * @throws InterruptedException
	 */
	public void search(String keyword) {
		try {
			typeInto(element(searchBox), keyword);
			clickOn(element(searchButton));
		} catch (Exception e) {
			uninavPage.closeLevelUpShelf();
			typeInto(element(searchBox), keyword);
			clickOn(element(searchButton));
		}
	}

	/**
	 * Do the search with the random keyword
	 * 
	 * 
	 */
	public void search() {
		try {
			typeInto(element(searchBox), RandomGenerator.randomAlphabetic(6));
			clickOn(element(searchButton));
		} catch (Exception e) {
			uninavPage.closeLevelUpShelf();
			clickOn(element(searchButton));
		}
	}

	/**
	 * To read Serp message
	 * 
	 */
	public String getSerpMessage() {
		return element(serpMessage).getText().trim();
	}

	/**
	 * Verify DFP banner/Ecomm ads
	 * 
	 */
	public boolean verifyDfpBanner() {
		return isElementVisible(dfpBanner);
	}

	/**
	 * Verify search results
	 * 
	 */
	public boolean verifySearchResults() {
		return isElementVisible(results);
	}

	/**
	 * Verify search bars
	 * 
	 */
	public int getSearchBars() {
		int count = 0;
		List<WebElementFacade> srcBarCount = findAll(searchBars);
		for (int i = 0; i < srcBarCount.size(); i++) {
			if (srcBarCount.get(i).isCurrentlyVisible())
				count++;
		}
		return count;
	}

	/**
	 * Verify no search results found message
	 * 
	 */
	public boolean verifyNoResultsFound() {
		return isElementVisible(noResultsFound);
	}

	/**
	 * to get Bigfish serp message
	 * 
	 * @return
	 */

	public String getSerpSuspiciousMessage() {

		String msg = element(suspiciousTerms).getText().trim();

		System.out.println("expected test" + msg);
		if (msg.contains("Looking to win the big SuperPrize?")) {
			System.out.println("expected test");
		}

		else {
			System.out.println("expected test not found");
		}

		return msg.trim();
	}

	/**
	 * to verify BigFish ad present
	 * 
	 * @return
	 */

	public boolean verifyBigfishTag() {
		return isElementVisible(suspiciousTerms);
	}

}
