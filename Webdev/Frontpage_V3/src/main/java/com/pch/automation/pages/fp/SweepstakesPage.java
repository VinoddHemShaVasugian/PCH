package com.pch.automation.pages.fp;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;

public class SweepstakesPage extends PageObject {

	private final By progressBar = By.id("progress-bar-outer");
	private final By sweepHome = By.cssSelector("div.sweeps");
	private final By sweepHomeDescription = By.xpath("//div[@class='sweeps ']/following-sibling::div");
	private final By sweepCompletedHome = By.cssSelector("div.sweeps.sweeps--completed");
	private final By sweepCompletedHomeMessage = By
			.xpath("//div[@class='sweeps sweeps--completed']/following-sibling::div");
	private final By myAccount = By.linkText("My Account");

	public boolean verifyHome() {
		waitForRenderedElementsToBePresent(myAccount);
		return isElementVisible(myAccount);
	}

	public boolean verifySweepstakes() {
		waitForRenderedElementsToBePresent(sweepHome);
		return isElementVisible(sweepHome);
	}

	/**
	 * Return True if the sweep stakes complete image present
	 * 
	 * @return
	 */
	public boolean verifySweepstakesCompleteState() {
		return isElementVisible(sweepCompletedHome);
	}

	/**
	 * Return sweep stakes complete message
	 * 
	 * @return
	 */
	public String getSweepstakesCompleteMessage() {
		return element(sweepCompletedHomeMessage).getText();
	}

	/**
	 * Click sweep stakes link
	 * 
	 * 
	 */
	public void clickSweepstakes() {
		waitForRenderedElementsToBePresent(sweepHome);
		clickOn(element(sweepHome));
	}

	/**
	 * Verify sweep stakes description in home page Return sweep stakes description
	 * 
	 * @return
	 */
	public String getSweepHomeDescription() {
		return element(sweepHomeDescription).getText();
	}

	/**
	 * Return True if progress bar is present
	 * 
	 * @return
	 */
	public boolean verifyProgressBar() {
		waitFor(2);
		if (isElementVisible(progressBar)) {
			waitFor(10);
			waitForRenderedElementsToDisappear(progressBar);
			return true;
		} else {
			return false;
		}
	}
}