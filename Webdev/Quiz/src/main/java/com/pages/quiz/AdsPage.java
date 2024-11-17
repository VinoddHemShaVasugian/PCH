package com.pages.quiz;

import java.util.List;

/**
 * To create and access ad related methods.
 * 
 * @author vsankar
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class AdsPage extends PageObject {

	/**
	 * Instantiates a SignIn Page.
	 *
	 * @param driver
	 */
	public AdsPage(WebDriver driver) {
		super(driver);
	}

	private final By inlineGptAd = By.cssSelector("div#div-pch-placement-between2>div>iframe");
	private final By rightRailGptMultipleAd = By.cssSelector("div#div-pch-placement-multiple> div>iframe");
	private final By rightRailGptAd = By.cssSelector("div#div-pch-placement-box> div>iframe");
	private final By bottomRightRailAd = By.cssSelector("div#div-pch-placement-box2> div>iframe");
	private final By closePlaybuzzStickyRightRailAd = By.cssSelector("div.mcd-sticky-x-button");
	private final By playbuzzStickyBottomAd = By.cssSelector("div.pb-stream-sticky-off");
	private final By playbuzzStickyRightRailAd = By.cssSelector("div.pb-stream-sticky-on");
	private final By leftRailGptAd = By.cssSelector("div#div-pch-placement-left-box> div>iframe");
	private final By inlineGptAdCategoryPage = By.cssSelector("article#div-pch-placement-bottom>div>iframe");
	private final By pageTaggingStatus = By.cssSelector("span.tagging-report-text");
	private final By googfcTaggingIframe = By.xpath("//iframe[starts-with(@id,'xpcpeer')]");
	private final By googfcPageSource = By.cssSelector("#\\:1 > table > tbody > tr:nth-child(2) > td > div");

	public boolean verifyPageTaggingStatus(int adFrame) {
		int adFrames = 1;
		boolean status = false;
		waitABit(2000);
		 List<WebElementFacade> iframes = findAll(googfcTaggingIframe);
		for (WebElementFacade iframe : iframes) {
			if (adFrames == adFrame) {
				getDriver().switchTo().frame(iframe);
				waitForRenderedElementsToBePresent(googfcPageSource);
				if (findAll(googfcPageSource).size() > 0) {
					clickOn(element(googfcPageSource));
					waitForRenderedElementsToBePresent(pageTaggingStatus);
					status = element(pageTaggingStatus).containsText("Page tagged correctly!");
				}
				getDriver().switchTo().defaultContent();
				return status;
			}
			adFrames++;
		}
		return false;
	}

	/**
	 * Verify the display of Play buzz bottom ad
	 * 
	 * @return
	 */
	public boolean verifyPlaybuzzStickyBottomAd() {
		waitForRenderedElementsToBePresent(playbuzzStickyBottomAd);
		return isElementVisible(playbuzzStickyBottomAd);
	}

	/**
	 * Verify the display of Play buzz right rail ad
	 * 
	 * @return
	 */
	public boolean verifyPlaybuzzStickyRightRailAd() {
		waitForRenderedElementsToBePresent(playbuzzStickyRightRailAd);
		return isElementVisible(playbuzzStickyRightRailAd);
	}

	/**
	 * To close Play buzz bottom ad
	 * 
	 * @return
	 */
	public void closePlaybuzzStickyRightRailAd() {
		waitForRenderedElementsToBePresent(playbuzzStickyRightRailAd);
		if (isElementVisible(playbuzzStickyRightRailAd))
			clickOn(element(closePlaybuzzStickyRightRailAd));
	}

	/**
	 * Return the size of Bottom Native Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfBottomAd() {
		waitForRenderedElementsToBePresent(bottomRightRailAd);
		String size[] = new String[2];
		size[0] = element(bottomRightRailAd).getAttribute("width");
		size[1] = element(bottomRightRailAd).getAttribute("height");
		return size;
	}

	/**
	 * Return the size of Inline GPT Ad in category pages
	 * 
	 * @return
	 */
	public String[] getSizeOfCategoryPageInlineGptAd() {
		waitForRenderedElementsToBePresent(inlineGptAdCategoryPage);
		String size[] = new String[2];
		size[0] = element(inlineGptAdCategoryPage).getAttribute("width");
		size[1] = element(inlineGptAdCategoryPage).getAttribute("height");
		return size;
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
	 * Return the size of Right Rail GPT Multiple ad slot
	 * 
	 * @return
	 */
	public String[] getSizeOfRightRailGptMultipleAd() {
		waitABit(3000);
		waitForRenderedElementsToBePresent(rightRailGptMultipleAd);
		String size[] = new String[2];
		size[0] = element(rightRailGptMultipleAd).getAttribute("width");
		size[1] = element(rightRailGptMultipleAd).getAttribute("height");
		return size;
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
	 * Return the size of Left Rail GPT Ad
	 * 
	 * @return
	 */
	public String[] getSizeOfLeftRailGptAd() {
		waitForRenderedElements(leftRailGptAd);
		String size[] = new String[2];
		size[0] = element(leftRailGptAd).getAttribute("width");
		size[1] = element(leftRailGptAd).getAttribute("height");
		return size;
	}
}