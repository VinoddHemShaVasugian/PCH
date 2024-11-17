package com.pch.automation.pages;

import java.util.List;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class ScratchcardPage extends PageObject {

	private final By playNowButton = By.cssSelector("span.line1");
	private final By continueButton = By.cssSelector("span.gos__but-text");
	private final By scratchPathTokenAmount = By.cssSelector("div.gos__amount");
	private final By adVideoPlayer = By.cssSelector("div[aria-label='Video Player']");
	private final By revealAll = By.id("reveal-all");
	private final By scratchPathTotalGameCount = By.cssSelector("span.pathgame__game-progress-total");

	/**
	 * Return the earned ScratchPath total games count
	 * 
	 * @return
	 */
	public String getScratchPathTotalGameCount() {
		return element(scratchPathTotalGameCount).getText();
	}

	/**
	 * Click on Play Now button on ScratchPath games
	 * 
	 * @throws Exception
	 */
	public String playScratchPathGame() throws Exception {
		waitFor(5);
		switchToScratchpathFrame();
		clickOn(element(playNowButton));
		getDriver().switchTo().defaultContent();
		while (!isElementVisible(continueButton)) {
			switchToScratchpathFrame();
			clickOn(element(revealAll));
			getDriver().switchTo().defaultContent();
		}
		String tokenAmount = getScratchPathTokenAmount();
		clickOn(element(continueButton));
		return tokenAmount;
	}

	/**
	 * Wait for the ScratchPath Ad's to complete
	 * 
	 * @throws Exception
	 */
	public void waitForScratchPathAdsToComplete() {
		if (isElementVisible(adVideoPlayer)) {
			waitForRenderedElementsToDisappear(adVideoPlayer);
		}
	}

	/**
	 * Return the earned ScratchPath token amount
	 * 
	 * @return
	 */
	public String getScratchPathTokenAmount() {
		return element(scratchPathTokenAmount).getText().split(" ")[0].replace(",", "");
	}

	/**
	 * Switch to the Scratch path IFrame
	 * 
	 * @throws Exception
	 */
	public void switchToScratchpathFrame() throws Exception {
		switchToFrame("iframe", iframeCount("iframe", "src", "pathgamesassets"));
	}

	public int iframeCount(String eIframe, String egetAttribute, String verificationText) {
		int frameNo = 0;
		List<WebElementFacade> frames = findAll(By.tagName(eIframe));
		for (int i = 0; i <= frames.size(); i++) {
			try {
				if (frames.get(i).getAttribute(egetAttribute).contains(verificationText)) {
					frameNo = i;
					break;
				}
			} catch (Exception e) {
				continue;
			}
		}
		return frameNo;
	}

	public void switchToFrame(String FrameName, int count) throws Exception {
		try {
			getDriver().switchTo().frame(findAll(By.tagName(FrameName)).get(count));
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
}