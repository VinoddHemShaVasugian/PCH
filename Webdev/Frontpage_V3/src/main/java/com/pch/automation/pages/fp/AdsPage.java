package com.pch.automation.pages.fp;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;

public class AdsPage extends PageObject {

	private final By inlineGptAd = By.cssSelector("div#div-gpt-ad-bottom>div>iframe");
	private final By rightRailGptMultipleAd = By.cssSelector("div#div-gpt-ad-multiple> div>iframe");
	private final By rightRailGptAd = By.cssSelector("div#div-gpt-ad-box> div>iframe");
	private final By inlineGptTileAd = By.cssSelector("div#div-gpt-ad-tile>div>iframe");
	private final By bottomNativeAd = By.cssSelector("div#gpt-ad-bottom-native");
	private final By bottomNativeAdSize = By.cssSelector("div#gpt-ad-bottom-native>div>iframe");
	private final By topStoriesNativeAd = By.cssSelector("div#div-nativ-ad-home>div>iframe");
	private final By trendingNowNativeAd = By.cssSelector("div#div-nativ-ad-trendingnow");
	private final By sponsoredNativeAd = By.cssSelector("div#div-native-ad-sponsored>div>iframe");
	private final By subCategoryNativeAd = By.cssSelector("div#div-nativ-ad-subcategory>div>iframe");
	private final By ourPickNativeAd = By.cssSelector("div#div-nativ-ad-ourpicks>div>iframe");
	private final By nextArticleBtn = By.cssSelector("div.countDownBtn.skipMe");
	private final By pleaseWaitbar = By.cssSelector("div.countDownBtn");

	/**
	 * Verify the display of Our Pick Native Ad
	 * 
	 * @return
	 */
	public boolean verifyOurPickNativeAd() {
		return isElementVisible(ourPickNativeAd);
	}

	/**
	 * Return the size of Our Pick Native Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfOurPickNativeAd() {
		String size[] = new String[2];
		size[0] = element(ourPickNativeAd).getAttribute("width");
		size[1] = element(ourPickNativeAd).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Sub Category Native Ad
	 * 
	 * @return
	 */
	public boolean verifySubCategoryNativeAd() {
		return isElementVisible(subCategoryNativeAd);
	}

	/**
	 * Return the size of Sub Category Native Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfSubCategoryNativeAd() {
		String size[] = new String[2];
		size[0] = element(subCategoryNativeAd).getAttribute("width");
		size[1] = element(subCategoryNativeAd).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Sponsored Native Ad
	 * 
	 * @return
	 */
	public boolean verifySponsoredNativeAd() {
		return isElementVisible(sponsoredNativeAd);
	}

	/**
	 * Return the size of Sponsored Native Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfSponsoredNativeAd() {
		waitABit(5000);
		waitForRenderedElementsToBePresent(sponsoredNativeAd);
		String size[] = new String[2];
		size[0] = element(sponsoredNativeAd).getAttribute("width");
		size[1] = element(sponsoredNativeAd).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Trending Now Native Ad
	 * 
	 * @return
	 */
	public boolean verifyTrendingNowNativeAd() {
		return isElementVisible(trendingNowNativeAd);
	}

	/**
	 * Return the size of Trending Now Native Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfTrendingNowNativeAd() {
		waitABit(5000);
		waitForRenderedElementsToBePresent(trendingNowNativeAd);
		String size[] = new String[2];
		size[0] = element(trendingNowNativeAd).getAttribute("width");
		size[1] = element(trendingNowNativeAd).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Top Story Native Ad
	 * 
	 * @return
	 */
	public boolean verifyTopStoriesNativeAd() {
		return isElementVisible(topStoriesNativeAd);
	}

	/**
	 * Return the size of Top Story Native Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfTopStoriesNativeAd() {
		String size[] = new String[2];
		size[0] = element(topStoriesNativeAd).getAttribute("width");
		size[1] = element(topStoriesNativeAd).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Bottom Native Ad
	 * 
	 * @return
	 */
	public boolean verifyBottomNativeAd() {
		return isElementVisible(bottomNativeAd);
	}

	/**
	 * Return the size of Bottom Native Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfBottomNativeAd() {
		String size[] = new String[2];
		size[0] = element(bottomNativeAdSize).getAttribute("width");
		size[1] = element(bottomNativeAdSize).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Inline GPT Tile Ad
	 * 
	 * @return
	 */
	public boolean verifyInlineGptTileAd() {
		return isElementVisible(inlineGptTileAd);
	}

	/**
	 * Return the size of Inline GPT Tile Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfInlineGptTileAd() {
		waitForRenderedElementsToBePresent(inlineGptTileAd);
		String size[] = new String[2];
		size[0] = element(inlineGptTileAd).getAttribute("width");
		size[1] = element(inlineGptTileAd).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Inline GPT Ad
	 * 
	 * @return
	 */
	public boolean verifyInlineGptAd() {
		return isElementVisible(inlineGptAd);
	}

	/**
	 * Return the size of Inline GPT Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfInlineGptAd() {
		waitForRenderedElementsToBePresent(inlineGptAd);
		String size[] = new String[2];
		size[0] = element(inlineGptAd).getAttribute("width");
		size[1] = element(inlineGptAd).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Right Rail GPT Multiple ad slot
	 * 
	 * @return
	 */
	public boolean verifyRightRailGptMultipleAd() {
		return isElementVisible(rightRailGptMultipleAd);
	}

	/**
	 * Return the size of Right Rail GPT Multiple ad slot
	 * 
	 * @return
	 */
	public String[] getSizeOfRightRailGptMultipleAd() {
		String size[] = new String[2];
		size[0] = element(rightRailGptMultipleAd).getAttribute("width");
		size[1] = element(rightRailGptMultipleAd).getAttribute("height");
		return size;
	}

	/**
	 * Verify the display of Right Rail GPT Ad
	 * 
	 * @return
	 */
	public boolean verifyRightRailGptAd() {
		waitFor(2);
		return isElementVisible(rightRailGptAd);
	}

	/**
	 * Return the size of Right Rail GPT Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfRightRailGptAd() {
		waitForRenderedElements(rightRailGptAd);
		String size[] = new String[2];
		size[0] = element(rightRailGptAd).getAttribute("width");
		size[1] = element(rightRailGptAd).getAttribute("height");
		return size;
	}

	/**
	 * Click Next article button from Interstitial ad
	 **/
	public void clickNextArticleOnInterstitialAd() {
		waitABit(30000);
		waitForRenderedElementsToBePresent(nextArticleBtn);
		clickOn(element(nextArticleBtn));
	}

	/**
	 * Verify Next article button
	 **/
	public boolean verifyNextArticle() {
		waitForRenderedElementsToDisappear(pleaseWaitbar);
		return isElementVisible(nextArticleBtn);
	}
}