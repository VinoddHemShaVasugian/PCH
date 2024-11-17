package com.pch.frontpage.pageObjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class InterstitialPage extends BaseClass {

	private static final InterstitialPage interstitial_instance = new InterstitialPage();

	private InterstitialPage() {
	}

	public static InterstitialPage getInstance() {
		return interstitial_instance;
	}

	private final By close_btn_signin_popup = By.xpath("//button[@class='close_lb']");
	private final By next_article_btn = By.cssSelector("div.countDownBtn.skipMe");
	private final By please_wait_bar = By.cssSelector("div.countDownBtn");

	/**
	 * Close the popup for sign in
	 **/
	public void click_closepopup() {
		button(close_btn_signin_popup, 10);
	}

	/**
	 * Click Next article button
	 **/
	public void click_next_article_button() {
		// waitForElementNotVisible(please_wait_bar, 200);
		waitForElementPresent(next_article_btn, 200);
		button(next_article_btn, 40);
	}

	/**
	 * Verify Next article button
	 **/
	public boolean verify_next_article() {
		waitForElementNotVisible(please_wait_bar, 60);
		return elementVisibility(next_article_btn, 60);
	}

}
