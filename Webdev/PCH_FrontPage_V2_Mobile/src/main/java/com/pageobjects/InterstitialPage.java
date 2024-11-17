package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class InterstitialPage extends BaseClass {

	private static final InterstitialPage interstitial_instance = new InterstitialPage();

	private InterstitialPage() {
	}

	public static InterstitialPage getInstance() {
		return interstitial_instance;
	}

	private final By interstitial_sponsor_msg = By.cssSelector("div.message.default");
	private final By skip_btn = By.cssSelector("button.skip.show");

	/**
	 * Wait for the Skip button
	 **/
	public void wait_for_skip_btn() {
		waitForElementPresent(skip_btn, 50);
	}

	/**
	 * Click Skip button
	 **/
	public void click_skip_btn() {
		button(skip_btn, 50);
	}

	/**
	 * 
	 * @return Interstitial Ad Sponsor message
	 */
	public String get_sponsor_msg() {
		return getAttribute(interstitial_sponsor_msg, "innerHTML");
	}

}
