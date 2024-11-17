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
	private final By searchBox = By.id("irf0");
	private final By searchButton = By.cssSelector("input.serp-search-bar__submit");
	private final By cvCompleteDays = By.cssSelector("div.day-number.compl-day-number");
	private final By cvAll5DaysComplete = By.cssSelector("div.compl-mission-day-number.day-number.compl-day-number");
	private final By cvAll5DaysCompleteMsg = By.cssSelector("div.compl-mission-message");
	private final By dfpBanner = By.id("dfpBanner");
	private final By results = By.cssSelector("section.serp__results");
	private final By noResultsFound = By.xpath("//iframe[@data-s1search-id='mainline-notice-noresults-empty']");
	private final By searchBars = By.cssSelector("form.serp-search-bar__form");
	private final By aylf = By.cssSelector("div.aylf-bing-top");
	private final By firstAdMarketPlaceTile = By.cssSelector("a.admarketplace__ad");
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
		waitABit(5000);
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
			typeInto(element(searchBox).waitUntilVisible(), keyword);
			clickOn(element(searchButton).waitUntilClickable());
		} catch (Exception e) {
			uninavPage.closeLevelUpShelf();
			clickOn(element(searchButton).waitUntilClickable());
		}
	}

	/**
	 * Do the search with the random keyword
	 * 
	 * 
	 */
	public void search() {
		try {
			typeInto(element(searchBox).waitUntilVisible(), RandomGenerator.randomAlphabetic(6));
			clickOn(element(searchButton).waitUntilClickable());
		} catch (Exception e) {
			uninavPage.closeLevelUpShelf();
			clickOn(element(searchButton).waitUntilClickable());
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
	 * Click on the Ad Market tiles on randomly and return the clicked tile name
	 * 
	 */
	public String[] clickAndReturnAdMarktePlaceTileName() {
		String tileDetails[] = new String[3];
		List<WebElementFacade> elements = findAll(firstAdMarketPlaceTile);
		int randomNo = RandomGenerator.gettingRandomNumberbasedoninput(elements.size() - 1);
		String tileName = elements.get(randomNo).getAttribute("data-admkt-name");
		String tilePos = elements.get(randomNo).getAttribute("data-admkt-pos");
		String tileId = elements.get(randomNo).getAttribute("href").split("=")[1].trim();
		clickOn(element(elements.get(randomNo)).waitUntilClickable());
		tileDetails[0] = tileName;
		tileDetails[1] = tilePos;
		tileDetails[2] = tileId;
		return tileDetails;
	}

	/**
	 * Verify the Ad Market tiles on page based on given tile name
	 * 
	 */
	public boolean verifyAdTileOnPage(String adTileName) {
		List<WebElementFacade> elements = findAll(firstAdMarketPlaceTile);
		for (WebElementFacade ele : elements) {
			String name = ele.getAttribute("data-admkt-name");
			if (name.equalsIgnoreCase(adTileName)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Click on the Ad Market tiles based on the given position no
	 * 
	 */
	public void clickAdMarktePlaceTileNameBasedOnGivenPos(int pos) {
		List<WebElementFacade> elements = findAll(firstAdMarketPlaceTile);
		clickOn(element(elements.get(pos - 1)));
	}

	/**
	 * to get Bigfish serp message
	 * 
	 * @return
	 */

	public String getSerpSuspiciousMessage() {
		return element(suspiciousTerms).getText().trim();
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
