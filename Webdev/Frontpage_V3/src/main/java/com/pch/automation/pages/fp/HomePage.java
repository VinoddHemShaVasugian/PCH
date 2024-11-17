package com.pch.automation.pages.fp;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.automation.utilities.RandomGenerator;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * Page objects and methods for Home page
 *
 * @author vsankar
 */
public class HomePage extends PageObject {

	private final By searchBox = By.id("searchField0");
	private final By searchButton = By.cssSelector("div.search__bar-wrapper__submit");
	private final By myAccount = By.linkText("My Account");
	private final By samsBanner = By.xpath("//img[contains(@src,'https://samsadmin')]");
	private final By defaultBanner = By
			.xpath("//img[contains(@src,'http://cdn.pch.com/ui/openx-ads/fallbacks/pchsearch')]");
	private final By mainCatagoryMenuUrls = By.cssSelector("div.col-md-12 ul.menu--header li.menu__item  >a");
	private final By subCatagoryMenuUrls = By.xpath("//div[@id='header']//ul[starts-with(@class,'sub-menu')]//a");
	private final By subCatagoryMenuPageName = By
			.xpath("//div[@id='header']//ul[starts-with(@class,'sub-menu')]//a/span");
	private final By greenUnclaimToken = By.cssSelector("button.buttons_green.unclaimed");
	private final By greyClaimToken = By.cssSelector("button.buttons_grey.claimed");
	private final By unclaimTokenAmount = By.cssSelector("span.buttons__token-amount");
	private final By dailyBonusGameBarCheckList = By.cssSelector("li.progress-bar-step.progress-bar-step--checked");
	private final By dailyBonusGameLockedIcon = By.cssSelector("img.progress-bar-sticky__lock");
	private final By topStoriesSection = By.cssSelector("a.top-stories__li__a");
	private final By ourPicksSection = By.cssSelector("div.our-picks");
	private final By lifestyleSection = By.xpath("//a[text()='Lifestyle'][@class='section-header__link']");
	private final By sportsSection = By.xpath("//a[text()='Sports'][@class='section-header__link']");
	private final By newsSection = By.xpath("//a[text()='News'][@class='section-header__link']");
	private final By entertainmentSection = By.xpath("//a[text()='Entertainment'][@class='section-header__link']");
	private final By trendingVideosSection = By.xpath("//div[@id='right']//h3[text()='Videos']");
	private final By trendingNowSection = By.xpath("//div[@id='right']//h3[text()='Trending Now']");
	private final By dailyBonusGamePlayIcon = By.cssSelector("img.progress-bar-sticky__play-now");
	private final By dailyBonusGameInfoIcon = By.cssSelector("i.progress-bar-sticky__info>img");
	private final By dailyBonusGameInfoWindow = By.cssSelector("div.progress-bar-sticky-menu.animated.fadeInUp");
	private final By dailyBonusGameInfoWindowClose = By.cssSelector("div.progress-bar-sticky-menu__close");
	private final By videosMenu = By.xpath("//div[@id='header']//a[contains(@href ,'video')]");
	private final By horoscopeMenu = By.xpath("//*[@id='header']//a[text()='Horoscope ']");
	private final By lotteryMenu = By.xpath("//*[@id='header']//a[text()='Lottery ']");
	private final By entertainmentMenu = By.xpath("//*[@id='header']//a[text()='Entertainment ']");
	private final By newsMenu = By.xpath("//*[@id='header']//a[text()='News ']");
	private final By weatherMenu = By.xpath("//*[@id='header']//a//img[contains(@src,'weather')]");
	private final By ourpicksCategories = By.cssSelector("div.our-picks__col__category");
	private final By firstArticleLinkFromTopStories = By
			.xpath("//li[@class='top-stories__li']/a[contains(@href,'/article')][1]");
	private final By firstVideoLinkFromTopStories = By
			.xpath("//li[@class='top-stories__li']/a[contains(@href,'/video')][1]");

	/**
	 * To click on videos menu
	 */
	public void clickVidoesMenu() {
		jsClick(videosMenu);
	}

	/**
	 * To get categories from our picks section
	 */
	public List<WebElementFacade> getOurpicksCategories() {
		return findAll(ourpicksCategories);
	}

	/**
	 * To click on Entertainment menu
	 */
	public void clickEntertainmentMenu() {
		waitForRenderedElementsToBePresent(entertainmentMenu);
		jsClick(entertainmentMenu);
	}

	/**
	 * To click on News menu
	 */
	public void clickNewsMenu() {
		jsClick(newsMenu);
	}

	/**
	 * To click on Horoscope menu
	 */
	public void clickHoroscopeMenu() {
		try {
			clickOn(element(horoscopeMenu));
		} catch (Exception e) {
			waitForRenderedElementsToBePresent(horoscopeMenu);
			jsClick(horoscopeMenu);
		}
	}

	/**
	 * Go to Weather Page
	 */
	public void clickWeatherMenu() {
		waitForRenderedElementsToBePresent(weatherMenu);
		jsClick(weatherMenu);
	}

	/**
	 * To click on lottery menu
	 */
	public void clickLotteryMenu() {
		waitForRenderedElementsToBePresent(lotteryMenu);
		jsClick(lotteryMenu);
	}

	public boolean verifyOurPicksSection() {
		return isElementVisible(ourPicksSection);
	}

	public boolean verifyTopStoriesSection() {
		return isElementVisible(topStoriesSection);
	}

	public boolean verifyCategoriesSection() {
		return (isElementVisible(lifestyleSection) && isElementVisible(sportsSection) && isElementVisible(newsSection)
				&& isElementVisible(entertainmentSection));
	}

	public boolean verifyTrendingVideosSection() {
		waitForRenderedElementsToBePresent(trendingVideosSection);
		return isElementVisible(trendingVideosSection);
	}

	public boolean verifyTrendingNowSection() {
		waitForRenderedElementsToBePresent(trendingNowSection);
		return isElementVisible(trendingNowSection);
	}

	/**
	 * Do the search with the given keyword
	 * 
	 * @param keyword
	 */
	public void search(String keyword) {
		try {
			typeInto(element(searchBox), keyword);
		} catch (ElementClickInterceptedException e) {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("document.getElementById('searchField0').setAttribute('value', '" + keyword + "')");
		} catch (StaleElementReferenceException stale) {
			waitForRenderedElementsToBePresent(searchBox);
			typeInto(element(searchBox), keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		waitForRenderedElementsToBePresent(searchButton);
		try {
			clickOn(element(searchButton));
		} catch (Exception e) {
			jsClick(searchButton);
		}
	}

	/**
	 * To verify the my account link and also can be used to successful login and
	 * registration
	 */
	public boolean verifyHomePage() {
		waitForRenderedElementsToBePresent(myAccount);
		return isElementVisible(myAccount);
	}

	/**
	 * Do the search with the random keyword
	 * 
	 * 
	 */
	public void search() {
		typeInto(element(searchBox), RandomGenerator.randomAlphabetic(6));
		clickOn(element(searchButton).waitUntilClickable());
	}

	/**
	 * To verify the presence of searchBox
	 * 
	 * @return True
	 */
	public boolean verifySearchBox() {
		return isElementVisible(searchBox);
	}

	/***
	 * Verify the display of SAMS banner
	 * 
	 * @return True
	 */
	public boolean verifySamsBanner() {
		return isElementVisible(samsBanner);
	}

	/***
	 * Verify the display of fallback banner
	 * 
	 * @return True
	 */
	public boolean verifyFallbackDefaultBanner() {
		return isElementVisible(defaultBanner);
	}

	/**
	 * Return the Sub_catagory pages Url's. It includes the More menu also.
	 * 
	 * @return
	 */
	public LinkedList<String> getSubCatagoryMenuUrlList() {
		waitABit(5000);
		waitForRenderedElementsToBePresent(subCatagoryMenuUrls);
		List<WebElementFacade> eleList = findAll(subCatagoryMenuUrls);
		LinkedList<String> subCatagoryUrls = new LinkedList<String>();
		for (WebElement ele : eleList) {
			subCatagoryUrls.add(ele.getAttribute("href"));
		}
		return subCatagoryUrls;
	}

	/**
	 * Return the Sub_catagory pages name in list. It includes the More menu also.
	 * 
	 * @return
	 */
	public LinkedList<String> getSubCatagoryMenuList() {
		List<WebElementFacade> eleList = findAll(subCatagoryMenuPageName);
		LinkedList<String> subCatagoryMenuNameList = new LinkedList<String>();
		for (WebElement ele : eleList) {
			subCatagoryMenuNameList.add(ele.getAttribute("innerHTML").toLowerCase());
		}
		return subCatagoryMenuNameList;
	}

	/**
	 * Return the Main Category pages Url's including the More menu.
	 * 
	 * @return
	 */
	public LinkedList<String> getMainCatagoryMenuUrlList() {
		List<WebElementFacade> eleList = findAll(mainCatagoryMenuUrls);
		LinkedList<String> mainCatagoryUrls = new LinkedList<String>();
		for (WebElement ele : eleList) {
			String url = ele.getAttribute("href");
			if (!url.contains("#")) {
				mainCatagoryUrls.add(url);
			}
		}
		return mainCatagoryUrls;
	}

	/**
	 * Return the Main category names including the More menu.
	 * 
	 * @return
	 */
	public LinkedList<String> getMainCatagoryMenuName() {
		List<WebElementFacade> eleList = findAll(mainCatagoryMenuUrls);
		LinkedList<String> mainCatagoryUrls = new LinkedList<String>();
		for (WebElement ele : eleList) {
			String url = ele.getAttribute("href");
			if (!url.contains("#")) {
				mainCatagoryUrls.add(url.substring(url.lastIndexOf("/") + 1, url.length()).toLowerCase().trim());
			}
		}
		return mainCatagoryUrls;
	}

	/**
	 * Click on Claim button
	 * 
	 */
	public boolean clickClaimButton() {
		waitForRenderedElementsToBePresent(greenUnclaimToken);
		moveTo(greenUnclaimToken);
		try {
			clickOn(element(greenUnclaimToken));
		} catch (Exception e) {
			jsClick(greenUnclaimToken);
		}
		return verifyClaimedButton();
	}

	/**
	 * Verify the Claimed Token button
	 * 
	 * @return
	 */
	public boolean verifyClaimedButton() {
		waitForRenderedElementsToBePresent(greyClaimToken);
		return isElementVisible(greyClaimToken);
	}

	/**
	 * Get the Claim Token button text
	 * 
	 * @return
	 */
	public String getUnclaimButtonText() {
		return element(greenUnclaimToken).getText();
	}

	/**
	 * Get the Claim Token value
	 * 
	 * @return
	 */
	public String getUnclaimTokenValue() {
		return element(unclaimTokenAmount).getText();
	}

	/**
	 * Verify the Claimed Token button text
	 * 
	 * @return
	 */
	public String getClaimedButtonText() {
		return element(greyClaimToken).getText();
	}

	/**
	 * Verify the UnClaimed Token button
	 * 
	 * @return
	 */
	public boolean verifyUnclaimedButton() {
		return isElementVisible(greenUnclaimToken);
	}

	/**
	 * Return the count of the Daily bonus game unlocked state
	 * 
	 * @return
	 */
	public int getDailyBonusGameCheckCount() {
		waitForRenderedElementsToBePresent(dailyBonusGameBarCheckList);
		return findAll(dailyBonusGameBarCheckList).size();
	}

	/**
	 * Verify the Daily bonus game progress bar lock icon
	 * 
	 * @return
	 */
	public boolean verifyDailyBonusGameLockIcon() {
		return isElementVisible(dailyBonusGameLockedIcon);
	}

	/**
	 * Verify the Daily bonus game progress bar lock icon
	 * 
	 * @return
	 */
	public boolean verifyDailyBonusGamePlayIcon() {
		waitABit(10000);
		return isElementVisible(dailyBonusGamePlayIcon);
	}

	/**
	 * Click the daily bonus game info icon
	 */
	public void clickDailyBonusGameInfoIcon() {
		waitForRenderedElementsToBePresent(dailyBonusGameInfoIcon);
		moveTo(dailyBonusGameInfoIcon);
		jsClick(dailyBonusGameInfoIcon);
	}

	/**
	 * Verify the Daily bonus game info icon
	 * 
	 * @return
	 */
	public boolean verifyDailyBonusGameInfoWindow() {
		waitABit(2000);
		return isElementVisible(dailyBonusGameInfoWindow);
	}

	/**
	 * Close the Daily bonus game info window
	 */
	public void closeDailyBonusGameInfoWindow() {
		waitForRenderedElementsToBePresent(dailyBonusGameInfoWindowClose);
		clickOn(element(dailyBonusGameInfoWindowClose));
	}

	/**
	 * Click on Daily bonus game progress bar lock icon
	 * 
	 * @return
	 */
	public void clickDailyBonusGamePlayIcon() {
		waitABit(30000);
		waitForRenderedElementsToBePresent(dailyBonusGamePlayIcon);
		moveTo(dailyBonusGamePlayIcon);
		jsClick(dailyBonusGamePlayIcon);
	}

	/**
	 * To click element using javascript executor
	 * 
	 * @parameter Locator
	 */
	public void jsClick(By locator) {
		waitForRenderedElementsToBePresent(locator);
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		try {
			executor.executeScript("arguments[0].click();", element(locator));
		} catch (StaleElementReferenceException stale) {
			waitForRenderedElementsToBePresent(locator);
			executor.executeScript("arguments[0].click();", element(locator));
		}
	}

	/**
	 * To click element using javascript executor
	 * 
	 * @parameter WebElement
	 */
	public void jsClick(WebElement locator) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		try {
			executor.executeScript("arguments[0].click();", locator);
		} catch (StaleElementReferenceException stale) {
			executor.executeScript("arguments[0].click();", locator);
		}
	}

	public void clickInfoPages(By locator) {
		clickOn(element(locator));
	}

	/**
	 * Navigate to new window and close it then back to parent window.
	 */
	public void closeCurrentlyFocusedTab() {
		WebDriver driver = getDriver();
		String parentHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(parentHandle).close();
				driver.switchTo().window(handle);
				break;
			}
		}
	}

	/**
	 * Navigate to new window and close it then back to parent window.
	 */
	public void switchToNewlyOpenedTab() {
		WebDriver driver = getDriver();
		String parentHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}

	/**
	 * Click the First Article link of the Top Stories section of the Home page
	 * 
	 */
	public void clickFirstArticleLink() {
		try {
			waitABit(2000);
			clickOn(findAll(firstArticleLinkFromTopStories).get(0));
		} catch (Exception e) {
			jsClick(firstArticleLinkFromTopStories);
		}
	}

	/**
	 * Click the First Video link of the Top Stories section of the Home page
	 * 
	 */
	public void clickFirstVideoLink() {
		try {
			waitABit(2000);
			clickOn(findAll(firstVideoLinkFromTopStories).get(0));
		} catch (Exception e) {
			jsClick(firstVideoLinkFromTopStories);
		}
	}
}