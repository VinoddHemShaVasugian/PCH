package com.pch.search.pages.web;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.lightBox.RegStepAwayLightBox;
import com.pch.search.pages.lightBox.SearchLimitLightBox;
import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;

public class SearchResultsPage extends Action {

	HomePage homePage = new HomePage();
	WebHeaderPage headerPage = new WebHeaderPage();
	AdminBasePage joomlaPage = new AdminBasePage();
	String device = Environment.getDevice();

	public RegStepAwayLightBox getRegStepAwayLightBox() {
		RegStepAwayLightBox lightBox = new RegStepAwayLightBox(driver);
		return lightBox;
	}

	public int getWebSearchResultCount() {
		String xpath = "//div[contains(@class,'searchResult')]//a[@class='resultTitle']";
		int results = driver.getCountOfElementsWithXPath(xpath);
		CustomLogger.log("Web Search Results Count :" + results);
		return results;
	}

	public SearchLimitLightBox getSearchLimitLightBox() {
		return new SearchLimitLightBox(driver);
	}

	public HtmlElement pchSearchAndWinLogo() {
		return driver.findElement(By.xpath(".//*[@id='uni_header']/div[2]/div[1]/h1/a/img"));
	}

	private HtmlElement searchBar() {
		return driver.findElement(By.name("q"));
	}

	private HtmlElement searchBtn() {
		return driver.findElement(By.cssSelector(".submitButton.js-searchbox-submit-button"));
	}

	public void clickSearchBtn() {
		searchBtn().click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Clicked on the search button.");
	}

	private HtmlElement bottomSearchBar() {
		return driver.findElement(By.xpath(".//*[@class='center-bottom-content']/div/form/input[1]"));
	}

	private HtmlElement bottomSearchBtn() {
		return driver.findElement(By.xpath(".//*[@class='center-bottom-content']/div/form/input[3]"));
	}

	private HtmlElement webTab() {
		return driver.findElement(By.xpath(".//*[contains(@class,'cat-web') or contains(text(),'Web Results')]"));
	}
	
	
	public void Areyoulookingfor(){
	    Assert.assertEquals("Are you looking for?", driver.findElement(By.xpath(".//*[@id='right-insp-title']")).getText());
		CustomLogger.log("Verifying the font of the are you looking for? ");
		 Assert.assertEquals("18px", driver.findElement(By.xpath(".//*[@id='right-insp-title']")).getCssValue("font-size"));
	}
	
	private HtmlElement shoppingTab() {
		HtmlElement shoppingTab = null;
		if (device.equalsIgnoreCase("mobile")) {
			shoppingTab = driver.findElement(By.xpath("//*[contains(text(),'More Shopping Results')]"));
		} else {
			shoppingTab = driver.findElement(By.xpath(".//*[contains(@class,'cat-shopping')]"));
		}
		return shoppingTab;
	}

	private HtmlElement webTabEnabled() {
		return driver.findElement(By.xpath(".//*[@class='cat-shopping on' or contains(text(),'Web Results')]"));
	}

	private HtmlElement shoppingTabEnabled() {
		return driver.findElement(By.xpath(".//*[@class='cat-shopping on' or contains(text(),'Shopping Results')]"));
	}

	/**
	 * This function will return text inside the search text box
	 */
	public String getSearchBarText() {
		return searchBar().getAttribute("value");
	}

	public void clickOnWebTab() {
		webTab().click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Clicked on Web Tab in the left nav and Web Search Results Page loaded.");
	}

	public void clickOnShoppingTab() {
		shoppingTab().click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Clicked on Shopping Tab in the left nav and Shopping Search Results Page loaded.");

	}

	public void clickOnMoreShoppingResultsLink() {
		moreShoppingResults().click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger
				.log("Clicked on more Shopping results link in the carousel to land on shopping Search Results Page.");
	}

	private HtmlElement serpHeaderMsg() {
		return driver.findElement(By.xpath(".//*[@id='messageResults']/span"));
	}

	private HtmlElement searchBox() {
		return driver.findElement(By.name("q"));
	}

	public boolean isSerpHeaderMsgPresent() {
		String attr = driver.findElement(By.id("messageResults")).getAttribute("style");

		if (attr.contains("display: none")) {
			CustomLogger.log("Serp Header Message is not displayed.");
			return false;
		} else {
			CustomLogger.log("Serp Header Message is displayed.");
			return true;
		}
	}

	// We can find this element only for guest user
	public HtmlElement serpHeaderRegisterNow() {
		return driver.findElement(By.xpath(".//*[@id='messageResults']/span[2]/a[contains(text(),'Register now')]"));
	}

	public HtmlElement signOut() {
		return driver.findElement(By.xpath(".//*[@id='uni-top-bar']/div/p[1]/a[contains(text(),'Sign Out')]"));
	}

	public HtmlElement serpHeaderSignIn() {
		return driver.findElement(By.xpath(".//*[@id='messageResults']/span[2]/a[contains(text(),'Register now')]"));
	}

	public HtmlElement topAds() {
		return driver.findElement(By.id("topResultsBlock"));
	}

	public HtmlElement bottomAds() {
		return driver.findElement(By.id("bottomResultsBlock"));
	}

	private HtmlElement isShoppingAdsTextDisplayed() {
		return driver.findElement(By.xpath(".//*[@id='shoppingResultsBlock']/div[contains(text(),'Shopping Ads')]"));
	}

	public HtmlElement webResults() {
		return driver.findElement(By
				.xpath(".//*[@id='mainResultsBlock']//div[contains(@class,'searchResult')]//a[@class='resultTitle']"));
	}

	private HtmlElement pagination() {
		return driver.findElement(By.id("bottomPagination"));
	}

	private HtmlElement previousPagination() {
		return driver.findElement(By.xpath(".//*[@id='bottomPagination']/ul/li[1]"));
	}

	private HtmlElement nextPagination() {
		return driver.findElement(By.xpath("//a[contains(text(),'Next') and contains(@class,'next')]"));
	}

	public int getTopAdsCount() {
		return driver.getCountOfElementsWithXPath(".//*[@id='insp_result_top_0']/span");
	}

	public int getRightAdsCount() {
		return driver.getCountOfElementsWithXPath(".//*[@id='insp_result_right_0']/span");
	}

	public void gotoPreviousPage() {
		previousPagination().click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Navigated to the previous page.");
	}

	public void gotoNextPage() {
		nextPagination().click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Navigated to the next page.");
	}

	private HtmlElement currentPagenum() {
		return driver.findElement(By.xpath(".//*[@id='bottomPagination']/ul/li[@class='active'][1]"));
	}

	private HtmlElement webImage() {
		return driver.findElement(By.xpath(".//*[@class='serp-left-categories']/li[1]"));
	}

	private HtmlElement shoppingImage() {
		return driver.findElement(By.xpath(".//*[@class='serp-left-categories']/li[2]"));
	}

	public HtmlElement winnerList() {
		return driver.findElement(By.id("winner-list"));
	}

	private HtmlElement carouselShoppingAdsBlock() {
		return driver.findElement(By.xpath(".//*[@id='shoppingResultsBlock'][@style='display: block;']"));
	}

	private HtmlElement shopppingCarousel() {
		return driver.findElement(By.xpath(".//*[@id='prodCarousel' and @class='carousel']"));
	}

	private HtmlElement shopppingCarouselTitle() {
		return driver.findElement(By.xpath(".//*[@id='shoppingResultsBlock']/div[1]"));
	}

	public void validateShoppingCarousel() {
		// Validate if shopping carousel is displayed
		Assert.assertTrue(shopppingCarousel().isDisplayed());
		CustomLogger.log("Shopping Ads Carousel is displayed on web search results page.");

		// Validate if shopping ads are displayed as one block
		Assert.assertTrue(carouselShoppingAdsBlock().isDisplayed());
		CustomLogger.log("Shopping Ads are displayed as One block.");

		// Validate if four Ads are present on the carousel
		Assert.assertTrue(driver.getCountOfElementsWithXPath("//div[@class='product']") == 10);
		CustomLogger.log("Ten products are present on the carousel.");

		// Validate more Shopping results link
		Assert.assertTrue(moreShoppingResults().isDisplayed());
		CustomLogger.log("More Shopping Ads link is present.");
		System.out.println(moreShoppingResults().getAttribute("href"));
		Assert.assertTrue(moreShoppingResults().getAttribute("href").contains("/search/?q=shoes&cat=shopping"));
		CustomLogger.log("More shopping results link is pointing to correct location. "
				+ moreShoppingResults().getAttribute("href"));

		// Validate Shopping Carousel Title
		Assert.assertTrue(shopppingCarouselTitle().getText().contains("Shopping Ads"));
		CustomLogger.log("Carousel title is as expected -- " + shopppingCarouselTitle().getText());

	}

	private HtmlElement backToPreviouPageLink() {
		return driver.findElement(By.xpath(".//*[@id='insp_result_main_0']/a"));
	}

	private HtmlElement productDetails() {
		return driver.findElement(By.xpath(".//*[@id='prodCarousel-products']"));
	}

	private HtmlElement productThumbnailPane() {
		return driver.findElement(By.xpath(".//*[contains(@class,'shoppingThumbnailPane')]"));
	}

	private HtmlElement shoppingInfoPane() {
		return driver.findElement(By.xpath(".//*[contains(@class,'shoppingInfoPane')]"));
	}

	private HtmlElement buyButton() {
		return driver.findElement(By.xpath(".//*[@id='shopButton']"));
	}

	private HtmlElement moreSellersInfo() {
		return driver.findElement(By.xpath(".//*[@id='moreSellers']"));
	}

	public void validateProductPageDetails() {
		Assert.assertTrue(productDetails().isDisplayed());
		CustomLogger.log("Product details are displayed");

		Assert.assertTrue(productThumbnailPane().isDisplayed());
		CustomLogger.log("Product Image is displayed");

		Assert.assertTrue(shoppingInfoPane().isDisplayed());
		CustomLogger.log("Product shopping info is displayed");

		 Assert.assertTrue(buyButton().isDisplayed());
		 CustomLogger.log("Product Buy button is displayed");
		
		 Assert.assertTrue(moreSellersInfo().isDisplayed());
		 CustomLogger.log("Product more sellers info is displayed");
	}

	public void navigateToProductPageAndValidate(String searchKeyword) {

		clickOnCarouselProduct();

		// Validate "Back to Web/Shopping results" link
		Assert.assertTrue(backToPreviouPageLink().isDisplayed());
		CustomLogger.log("'Back to web results' link is present.");
		Assert.assertTrue(backToPreviouPageLink().getAttribute("href").contains("/search/?q=" + searchKeyword));
		CustomLogger.log("'Back to web results' link is - " + backToPreviouPageLink().getAttribute("href"));
		Assert.assertTrue(backToPreviouPageLink().getText().contains("« Back to web results"));
		CustomLogger.log("'Back to web results' link text is - " + backToPreviouPageLink().getText());

		// Validate product page
		validateProductPageDetails();

		// Click on "Back to Web/Shopping results" link and validate
		backToPreviouPageLink().click();
		driver.waitForBrowserToLoadCompletely();
		Assert.assertTrue(driver.getCurrentUrl().contains("/search/?q=" + searchKeyword));
		CustomLogger.log("Landed successfully on web serp");

		// Navigate to Product page from shopping results page & validate
		clickOnShoppingTab();
		clickOnProductOnShoppingPage();

		// Validate "Back to Web/Shopping results" link
		Assert.assertTrue(backToPreviouPageLink().isDisplayed());
		CustomLogger.log("'Back to shopping results' link is present.");
		Assert.assertTrue(
				backToPreviouPageLink().getAttribute("href").contains("/search/?q=" + searchKeyword + "&cat=shopping"));
		CustomLogger.log("'Back to shopping results' link is - " + backToPreviouPageLink().getAttribute("href"));
		Assert.assertTrue(backToPreviouPageLink().getText().contains("« Back to shopping results"));
		CustomLogger.log("'Back to shopping results' link text is - " + backToPreviouPageLink().getText());

		// Validate product page
		validateProductPageDetails();

		// Click on "Back to Web/Shopping results" link and validate
		backToPreviouPageLink().click();
		driver.waitForBrowserToLoadCompletely();
		Assert.assertTrue(driver.getCurrentUrl().contains("/search/?q=" + searchKeyword + "&cat=shopping"));
		CustomLogger.log("Landed successfully on shopping serp");
	}

	private HtmlElement moreShoppingResults() {
		return driver.findElement(By.xpath(".//*[@id='moreShopping']/a"));
	}

	private HtmlElement selectProduct() {
		return driver.findElement(By.xpath(".//*[@class='product'][1]"));
	}

	private HtmlElement carosalProductInfo() {
		return driver.findElement(By.xpath(".//*[@id='prodCarousel-products']/div[1]/a/div/div/div[2]/div[1]"));
	}

	private HtmlElement productInfoInDescriptionPage() {
		return driver.findElement(By.xpath(".//*[@id='productResultDetails']/div[1]/div[2]/div[2]/div[1]"));
	}

	private HtmlElement shoppingCenterMidContent() {
		return driver.findElement(By.xpath(".//*[@class='center-mid-content']"));
	}

	public HtmlElement shoppingResultsSERP() {
		return driver.findElement(By.xpath(".//*[@id='mainResultsBlock']/div[3]"));
	}

	private HtmlElement sortingOrder() {
		return driver.findElement(By.xpath(".//*[@id='shopping-sort-options']/select"));
	}

	private HtmlElement sorting() {
		return waitForElementToBeVisible(By.xpath(".//*[@id='shopping-sort-options']//select"));

	}

	/*
	 * Get the URL After clicking on PCH Search and win logo
	 */
	public String getSearchAndWinLogoLinkURL() {
		pchSearchAndWinLogo().click();
		Common.sleepFor(5000);
		return driver.getCurrentUrl();
	}

	public boolean pchCalculator(int num1, int num2) {
		try {
			if (driver.getCurrentUrl()
					.contains("http://search." + Environment.getEnvironment() + ".pch.com/guidedsearch")) {
				// driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']");
			}

			int sum = num1 + num2;
			String total = String.valueOf(sum);
			searchBar().clear();
			searchBar().sendKeys(num1 + "+" + num2);
			searchBtn().click();
			Common.sleepFor(4000);
			Assert.assertTrue(driver.findElement(By.id("calcResults")).isDisplayed());
			String actualSum = driver.findElement(By.id("calcResults")).getText();
			Assert.assertTrue(actualSum.contains(total));

			return true;
		} catch (NoSuchElementException e) {
			e.getStackTrace();
			return false;
		}
	}

	private HtmlElement friendlyMessage() {
		return driver.findElement(By.id("errorResults"));
	}

	public String searchWithSpecialChar(String spclChar) {
		searchBar().clear();
		searchBar().sendKeys(spclChar);
		searchBtn().click();
		Common.sleepFor(4000);
		return friendlyMessage().getText();
	}

	/*
	 * Need to get few more HTMLElements like Previous, Next
	 */

	public int searchAndGetCount(String elementToSearch) {
		driver.waitForBrowserToLoadCompletely();
		if (driver.getCurrentUrl()
				.contains("http://search." + Environment.getEnvironment() + ".pch.com/guidedsearch")) {
			CustomLogger.log("Navigate to home page");
		}
		searchBar().clear();
		searchBar().sendKeys(elementToSearch);
		homePage.waitFor(2);
		searchBar().submit();
		driver.waitForBrowserToLoadCompletely();
		int resultSearch = getWebSearchResultCount();

		return resultSearch;
	}

	public int searchAndGetCountOfBottomSearchBar(String elementToSearch) {
		bottomSearchBar().clear();
		bottomSearchBar().sendKeys(elementToSearch);
		homePage.waitFor(5);
		bottomSearchBtn().scrollDownAndClick();
		driver.waitForBrowserToLoadCompletely();
		int resultSearch = getWebSearchResultCount();

		return resultSearch;
	}

	/*
	 * Here we are checking for web/ShoppingImg, headermsg,headerMsgRegisterNw
	 * link,top/bottom ads, search results, pagination.
	 */
	public boolean checkForGuestStructure(String element) {

		try {

			// if(searchCarouselAdds().isDisplayed()){
			if (element.equalsIgnoreCase("shoes")) {
				Assert.assertTrue(isShoppingAdsTextDisplayed().isDisplayed());
				CustomLogger.log("Thanks for Searching for - " + element);
				// int carouselCount =
				// driver.getCountOfElementsWithXPath(".//*[@id='prodCarousel-products']/div");
				Assert.assertTrue(moreShoppingResults().isDisplayed());
			}

			else
				CustomLogger.log("Search for any shopping items..to enable shopping Carousel..");

			Assert.assertTrue(webImage().isDisplayed());

			Assert.assertTrue(shoppingImage().isDisplayed());

			Assert.assertTrue(winnerList().isDisplayed());

			Assert.assertTrue(serpHeaderMsg().isDisplayed());

			Assert.assertTrue(topAds().isDisplayed());

			Assert.assertTrue(bottomAds().isDisplayed());

			Assert.assertTrue(webResults().isDisplayed());

			Assert.assertTrue(pagination().isDisplayed());

			return true;

		} catch (NoSuchElementException e) {
			return false;
		}

	}

	public String getTextInHeaderMsg() {
		return serpHeaderMsg().getText();
	}

	public void clickOnCarouselProductAndVerifyProductDetails() {

		String carosalProductInfo = carosalProductInfo().getText();
		CustomLogger.log(carosalProductInfo);

		selectProduct().click();
		driver.waitForBrowserToLoadCompletely();
		// System.out.println(productInfoInDescriptionPage().getText());
		Assert.assertTrue(carosalProductInfo.contains(productInfoInDescriptionPage().getText()));
	}

	public void clickOnCarouselProduct() {

		String carosalProductInfo = carosalProductInfo().getText();
		CustomLogger.log(carosalProductInfo);

		selectProduct().click();
		driver.waitForBrowserToLoadCompletely();
		// System.out.println(productInfoInDescriptionPage().getText());
		// Assert.assertTrue(driver.getCurrentUrl().contains("shoppingdetails/?q="));
		CustomLogger.log("Product details page from carousel is loaded successfully.");
	}

	public void clickOnProductOnShoppingPage() {

		driver.findElements(By.xpath("//div[@class='product auto-grid-item']")).get(0).click();
		driver.waitForBrowserToLoadCompletely();
		// System.out.println(productInfoInDescriptionPage().getText());
		System.out.println(driver.getCurrentUrl().contains("shoppingdetails/?q="));
		Assert.assertTrue(driver.getCurrentUrl().contains("shoppingdetails/?q="));
		CustomLogger.log("Product details page from Shopping serp is loaded successfully.");
	}

	public void validateLeftSideBar(String elementToSearch) {

		// By Default it will show only web results..
		// pchSearchAndWinLogo().click();
		driver.waitForBrowserToLoadCompletely();

		// webImage().click();
		searchAndGetCount(elementToSearch);
		CustomLogger.log("We are viewing web resuls by deafult..");

		shoppingImage().click();
		driver.waitForBrowserToLoadCompletely();

		Assert.assertTrue(shoppingCenterMidContent().isEnabled());

		Assert.assertTrue(driver.getCurrentUrl().contains("&cat=shopping"));
	}

	public void paginationBarFunctionality(String elementToSearch) {
		validateLeftSideBar(elementToSearch);

		driver.findElement(By.xpath(".//*[@id='bottomPagination']/ul/li[3]")).scrollDownAndClick();
		driver.waitForBrowserToLoadCompletely();
		String pageNumString = currentPagenum().getText();
		int pageNum = Integer.parseInt(pageNumString);
		CustomLogger.log("Now you are in " + pageNum + " num page");

		CustomLogger.log("Clicking on Next btn..");
		nextPagination().click();
		driver.waitForBrowserToLoadCompletely();
		String pageNum2String = currentPagenum().getText();
		int pageNum2 = Integer.parseInt(pageNum2String);
		CustomLogger.log("Now you are in " + pageNum2 + " num page");
		Assert.assertEquals(pageNum2, pageNum + 1);

		CustomLogger.log("Clicking on previous btn..");
		previousPagination().click();
		driver.waitForBrowserToLoadCompletely();
		String pageNum3String = currentPagenum().getText();
		int pageNum3 = Integer.parseInt(pageNum3String);
		CustomLogger.log("Now you are in " + pageNum3 + " num page");
		Assert.assertEquals(pageNum3, pageNum2 - 1);
	}

	public void validateShoppingResultsPage(String searchElement) {

		// Validate shopping results page url.
		Assert.assertTrue(driver.getCurrentUrl().contains("search/?q=" + searchElement + "&cat=shopping"));
		CustomLogger.log("Landed on Shopping results page- " + driver.getCurrentUrl());

		// Validate left nav tabs
		Assert.assertTrue(webImage().isDisplayed());
		Assert.assertTrue(shoppingImage().isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath(".//*[@class='cat-shopping on']")).isDisplayed());
		CustomLogger.log("Shopping tab in left nav is enabled.");

		// Validate if winner list is displayed
		Assert.assertTrue(winnerList().isDisplayed());
		CustomLogger.log("Recent Winners module is displayed.");

		Assert.assertTrue(serpHeaderMsg().isDisplayed());
		CustomLogger.log("Serp header message is displayed.");

		Assert.assertTrue(sortingOrder().isDisplayed());
		CustomLogger.log("Sorting module is displayed.");

		Assert.assertTrue(pagination().isDisplayed());
		CustomLogger.log("Pagination module is displayed.");

	}

	public void selectSortingOption(String sortType) {
		if (device.equalsIgnoreCase("mobile")) {
			sortResultsForMobile(sortType);
		} else {
			sorting().selectDropdownElementUsingVisibleText(sortType);
		}
		CustomLogger.log("Selected sort type: " + sortType);
	}

	public void sortResultsForMobile(String sortType) {
		driver.findElement(By.xpath("//span[contains(@class,'sort-handle')]")).click();
		driver.findElement(By.xpath(String.format("//li[contains(text(),'%s')]", sortType))).click();
	}

	public void validateSortingShoppingresults(String searchKeyword) {

		// Select an option and validate the url
		selectSortingOption("Relevance");

		driver.waitForBrowserToLoadCompletely();
		Assert.assertTrue(driver.getCurrentUrl().endsWith("?q=" + searchKeyword + "&cat=shopping&sort=Relevance"));
		CustomLogger.log("Page reloaded successfully wtih the correct sorting Option: Relevance");

		// Select an option and validate the url
		selectSortingOption("Price, Low to High");
		driver.waitForBrowserToLoadCompletely();
		System.out.println(driver.getCurrentUrl());
		System.out.println("?q=" + searchKeyword + "&cat=shopping&sort=Price/asc");
		Assert.assertTrue(driver.getCurrentUrl().endsWith("?q=" + searchKeyword + "&cat=shopping&sort=Price/asc"));
		CustomLogger.log("Page reloaded successfully wtih the correct sorting Option: Price, Low to High");

		// Select an option and validate the url
		selectSortingOption("Price, High to Low");
		driver.waitForBrowserToLoadCompletely();
		Assert.assertTrue(driver.getCurrentUrl().endsWith("?q=" + searchKeyword + "&cat=shopping&sort=Price"));
		CustomLogger.log("Page reloaded successfully wtih the correct sorting Option: Price, High to Low");
	}

	public void clickOnPaginationNum(int i) {
		driver.findElement(By.xpath(".//*[@id='bottomPagination']/ul/li[" + (i + 1) + "]/a")).click();
	}

	public void validatePagination(String searchKeyword) {

		driver.get("http://search." + Environment.getEnvironment() + ".pch.com/search/?q=" + searchKeyword
				+ "&cat=shopping");
		driver.waitForBrowserToLoadCompletely();

		// Go to different page number and validate the url
		clickOnPaginationNum(2);
		driver.waitForBrowserToLoadCompletely();
		Assert.assertTrue(driver.getCurrentUrl().endsWith("?q=" + searchKeyword + "&cat=shopping&page=2"));
		CustomLogger.log("Page reloaded successfully wtih the correct page number selected: 2");

		// Go to different page number and validate the url
		clickOnPaginationNum(3);
		driver.waitForBrowserToLoadCompletely();
		Assert.assertTrue(driver.getCurrentUrl().endsWith("?q=" + searchKeyword + "&cat=shopping&page=3"));
		CustomLogger.log("Page reloaded successfully wtih the correct page number selected: 3");

	}

	public void validateLeftNav() {

		// Validate is Web & Shopping Tabs are present
		Assert.assertTrue(webTab().isDisplayed());
		CustomLogger.log("Web Tab in the left navigation is displayed");
		if (device.equalsIgnoreCase("mobile")) {
			webTab().click();
		}
		Assert.assertTrue(shoppingTab().isDisplayed());
		CustomLogger.log("Shopping Tab in the left navigation is displayed");

		// Validate if shopping tab is enabled
		Assert.assertTrue(shoppingTabEnabled().isDisplayed());
		CustomLogger.log("Shopping Tab in the left navigation is displayed and is enabled");

		// Click on Web tab and validate if the web tab is enabled
		if (!device.equalsIgnoreCase("mobile")) {
			webTab().click();
		}
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Clicked on Web Tab in the left navigation to land on Web SERP");
		Assert.assertTrue(webTabEnabled().isDisplayed());
		CustomLogger.log("Web Tab in the left navigation is displayed and is enabled");
	}

	public void searchAndHandleWindow(String elementToSearch) {
		String parentHandle = driver.getWindowHandle();

		searchBar().clear();
		searchBar().sendKeys(elementToSearch);
		homePage.waitFor(1);
		searchBtn().click();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		getWebSearchResultCount();
		searchAndGetCount(elementToSearch);

		driver.close();
		driver.switchTo().window(parentHandle);
	}

	public void clickOnTrendingNowAndHandleWindow() {
		String parentHandle = driver.getWindowHandle();
		driver.findElement(By.xpath("html/body/main/div[1]/div[1]/div[1]/div[2]/a[1]")).click();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		homePage.waitFor(2);

		driver.close();
		driver.switchTo().window(parentHandle);

	}

	private HtmlElement superPrizeAd() {
		return driver.findElement(By.xpath(".//*[@id='serpHeader']/div/a[1]/img"));
	}

	private HtmlElement superPrizeAdMessage() {
		return driver.findElement(By.xpath(".//*[@id='serpHeader']/div/h1/a"));
	}

	private HtmlElement superPrizeAdPchComLink() {
		return driver.findElement(By.xpath(".//*[@id='serpHeader']/div/a[2]"));
	}

	public void validateSuperPrizeAd() {
		// Validate if Ad is displayed
		Assert.assertTrue(superPrizeAd().isDisplayed());
		CustomLogger.log("Super Prize Ad logo is displayed in the header.");

		// Validate if correct image is displayed
		Assert.assertTrue(superPrizeAd().getAttribute("src")
				.contains(".pch.com/images/content/serp_header/bad_terms/SERP_logo_pch_150x400_transparent.png"));
		CustomLogger.log("Correct super prize Ad image is displayed.");

		// Validate if correct message beside image is displayed
		Assert.assertEquals(superPrizeAdMessage().getText(), "Looking to win the big SuperPrize?");
		CustomLogger.log("Correct super prize Ad message is displayed. " + superPrizeAdMessage().getText());

		// Validate if message is pointing to correct Url
		Assert.assertTrue(superPrizeAdMessage().getAttribute("href").contains("http://www.pch.com"));
		CustomLogger.log("Super prize Ad message is pointing to correct location. "
				+ superPrizeAdMessage().getAttribute("href"));

		// Validate if PChcom link is present
		Assert.assertEquals(superPrizeAdPchComLink().getText(), "www.pch.com");
		CustomLogger.log("Correct message is shown. " + superPrizeAdPchComLink().getText());

		// Validate if PCHcom link is pointing to correct Url
		Assert.assertTrue(superPrizeAdPchComLink().getAttribute("href").contains("http://www.pch.com"));
		CustomLogger.log("PCHcom link message is pointing to correct location. "
				+ superPrizeAdPchComLink().getAttribute("href"));

	}

	private HtmlElement houseAd() {
		return driver.findElement(By.xpath(".//*[@id='serpHeader']/div/a/img"));
	}

	private HtmlElement houseAdMessage() {
		return driver.findElement(By.xpath(".//*[@id='serpHeader']/div/h1/a"));
	}

	public void validateHouseAd() {
		// Validate if Ad is displayed
		Assert.assertTrue(houseAd().isDisplayed());
		CustomLogger.log("House Ad logo is displayed in the header.");

		// Validate if correct image is displayed
		Assert.assertTrue(houseAd().getAttribute("src").contains("/images/content/serp_header/"));
		CustomLogger.log("Correct House Ad image is displayed.");

		// Validate if correct message beside image is displayed
		Assert.assertEquals(houseAdMessage().getText(), "Looking to win the big SuperPrize?");
		CustomLogger.log("Correct House Ad message is displayed. " + houseAdMessage().getText());

		// Validate if message is pointing to correct Url
		Assert.assertTrue(houseAdMessage().getAttribute("href").contains("http://www.pch.com"));
		CustomLogger.log("House Ad message is pointing to correct location. " + houseAdMessage().getAttribute("href"));

	}

	private HtmlElement lottoOptin() {
		return driver.findElement(By.xpath(".//*[@class='lotto-serpad']"));
	}

	public HtmlElement nfspRelated() {
		return driver.findElement(By.id("nfsprelated"));
	}

	public HtmlElement lottoOptinImage() {
		return driver.findElement(By.xpath(".//*[@class='lotto-entry']/img"));
	}

	private HtmlElement lottoOptinMessage() {
		return driver.findElement(By.xpath(".//*[@class='lotto-serpad']/div[1]/div/p"));
	}

	private HtmlElement lottoPrivacyPolicyLink() {
		return driver.findElement(By.xpath(".//*[@class='lotto-serpad']/div[1]/div/p/a"));
	}

	private HtmlElement lottoOptinYesBtn() {
		return driver.findElement(By.xpath(".//*[@class='lotto-submit']"));
	}

	private HtmlElement lottoOptinConfImg() {
		return driver.findElement(By.xpath(".//*[@class='serp-lotto-confirmation']"));
	}

	private HtmlElement OpenXImage() {
		return driver.findElement(By.xpath("html/body/a/img"));

	}

	public void validateLottoOptin() {
		// Validate if Lotto Optin Ad is displayed
		Assert.assertTrue(lottoOptin().isDisplayed());
		CustomLogger.log("Lotto Optin is displayed on SERP.");

		// Validate if correct image is displayed
		Assert.assertTrue(lottoOptinImage().getAttribute("src").contains("/dist/images/serp/serpad/lotto-entry.png"));
		CustomLogger.log("Correct Lotto Optin Ad image is displayed.");

		// Validate if correct message beside image is displayed
		String OptinMessage = "Yes, I would like to receive the PCHLotto newsletter to be informed about contests and opportunities. I know I can unsubscribe at any time by clicking the unsub link or the \"EZ Unsubscribe\" seal in any email. Privacy Policy";
		Assert.assertEquals(lottoOptinMessage().getText(), OptinMessage);
		CustomLogger.log("Correct Lotto Optin Ad message is displayed. " + lottoOptinMessage().getText());

		// Validate if message is pointing to correct Url
		Assert.assertTrue(
				lottoPrivacyPolicyLink().getAttribute("href").contains("http://privacy.pch.com/play-and-win-privacy"));
		CustomLogger.log("Privacy Policy link is pointing to correct location. "
				+ lottoPrivacyPolicyLink().getAttribute("href"));

	}

	public void clickOnLottoOptinYesButton() {
		lottoOptinYesBtn().click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Clicked on PCH Lotto Optin 'Yes' button.");
	}

	public void validateLottoOptinConfirmation() {
		// Validate if correct image is displayed
		Assert.assertTrue(lottoOptinConfImg().getAttribute("src")
				.contains("/dist/images/serp/serpad/SERP-Lotto-confirmation.png"));
		CustomLogger.log("Correct Lotto Optin Confirmation image is displayed.");
	}

	public void validateOpenXImage() {

		driver.switchTo().frame(0);
		// Validate if correct image is displayed
		// Assert.assertTrue(OpenXImage().getAttribute("src").contains("https://pch-d.openx.net"));
		Assert.assertTrue(OpenXImage().getAttribute("src").contains("ox-i.mods.pch.com"));
		CustomLogger.log("Image hosted by Open-X is displayed.");
		driver.switchTo().defaultContent();
	}

	public List<String> getNfspRelatedText() {

		HtmlElement nfspRelated = driver.findElement(By.xpath(".//*[@id='insp_result_related_0']"));
		Assert.assertTrue(nfspRelated.isDisplayed(), "Didn't find nfspRelated search for Guest User.");
		CustomLogger.log("We found -nfsp related- terms in current page ");

		int nfspRelatedCount = driver.getCountOfElementsWithXPath(".//*[@id='insp_result_related_0']/span");
		List<String> areYouLookingFor = new ArrayList<String>();

		CustomLogger.log("We are seeing the Content of Are you looking for?");
		CustomLogger.log("-----Are you looking for?-----");

		for (int i = 1; i <= nfspRelatedCount; i++) {
			CustomLogger
					.log(driver.findElement(By.xpath(".//*[@id='insp_result_related_0']/span[" + i + "]")).getText());
			areYouLookingFor
					.add(driver.findElement(By.xpath(".//*[@id='insp_result_related_0']/span[" + i + "]")).getText());
		}
		CustomLogger.log("------------------------------");
		return areYouLookingFor;
	}

	public boolean isHighRiskVideoExists(String keyword) {
		/*CustomLogger.log("Going to search");
		searchBox().clear();
		searchBox().sendKeys(keyword);
		driver.findElement(By.xpath("//input[contains(@class,'submitButton')]")).click();*/
		CustomLogger.log("Going to validate existence of video");
		boolean isExists = false;
		try {
			driver.findElement(By.id("pchVideoPlayer"));
			isExists = true;
		} catch (Exception e) {
			// do nothing
		}
return isExists;
	}

	public boolean isLeftSideBarExists() {
		boolean isExists = false;
		try {
			webTabEnabled();
			isExists = true;
		} catch (TimeoutException e) {
			// do nothing
		}
		return isExists;
	}

	public void validateNFSP(String nfsp) {
		CustomLogger.log("Validating NFSP from browser console: " + nfsp);
		Assert.assertTrue(getBrowserConsoleSearchLog().contains(nfsp));
		CustomLogger.log("Validated NFSP from browser console: " + nfsp);
	}

	public void validateAdvertisingSource(String logData) {
		CustomLogger.log("Validating Advertising Source from browser console: " + logData);
		Assert.assertTrue(getBrowserConsoleSearchLog().contains(logData));
		CustomLogger.log("Validated Advertising Source from browser console: " + logData);
	}

	public void validateSegment(String segment) {
		CustomLogger.log("Validating segment from browser console: " + segment);
		// CustomLogger.log(getBrowserConsoleLog().toString());
		Assert.assertTrue(getBrowserConsoleSearchLog().toString().contains(segment));
		CustomLogger.log("Validated segment from browser console: " + segment);
	}

	public void validategBQBlingoID(String expectedId) {
		String actualId = getActivityLog("window.gBQBlingoID");
		CustomLogger
				.log("Validating actual gBQBlingoID '" + actualId + "' and expected gBQBlingoID '" + expectedId + "'");
		Assert.assertEquals(actualId, expectedId, "gBQBlingoID does not match");
		CustomLogger.log("Validated gBQBlingoID");
	}

	public void validateURL(String expectedURL) {
		String actualURL = driver.getCurrentUrl();
		CustomLogger.log("Validating current URL");
		CustomLogger.log("Validating Current URL '" + actualURL + " and Expected URL '" + expectedURL);
		Assert.assertEquals(driver.getCurrentUrl(), expectedURL, "current URL and expected URL are not same");
		CustomLogger.log("Validated URL");
	}
}
