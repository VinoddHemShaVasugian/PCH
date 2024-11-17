package com.pch.automation.pages.fp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;

public class ArticlesPage extends PageObject {

	private final By nextArticle = By.cssSelector("div#nextArticle > a");
	private final By completeRegToEarnTokens = By
			.xpath("//div[text()='Complete Registration now to earn tokens for enjoying articles!']");
	private final By loginToEarnTokens = By
			.xpath("//div[text()='Log In or Register now to earn tokens for enjoying articles!']");
	private final By article = By.xpath("//li[@class='top-stories__li']/a[contains(@href,'/article')][1]");
	private final By errorPage = By.xpath("//h1[contains(text(),'Error 404')]");
	private final By mainPageCategory = By.cssSelector("section.breadcrumbs_wrapper");
	private final By subPageCategory = By.cssSelector("section.breadcrumbs_wrapper");
	private final By articleTitle = By.cssSelector("article#article>h1");

	HomePage js;

	/**
	 * Return the Story title
	 * 
	 * @return
	 */
	public String getArticleTitle() {
		return element(articleTitle).getText();
	}

	/**
	 * Return the Category type
	 * 
	 * @return
	 */
	public String getMainCategoryType() {
		waitABit(5);
		String type = element(mainPageCategory).getText();
		return type.contains("/") ? type.split("/")[1].toLowerCase().trim() : type.toLowerCase().trim();
	}

	/**
	 * Return the both Category & Sub-Category type
	 * 
	 * @return
	 */
	public String getSubCategoryType() {
		String type = element(subPageCategory).getText();
		return type.contains("/") ? type.split("/")[1].toLowerCase().trim() : type.toLowerCase().trim();
	}

	/**
	 * Verify the 404 Error page
	 * 
	 * @return
	 */
	public boolean verifyErrorPage() {
		return isElementVisible(errorPage);
	}

	/**
	 * Return True if the Next Article link present
	 * 
	 * @return
	 */
	public boolean verifyNextArticlePresence() {
		return isElementVisible(nextArticle);
	}

	/**
	 * Click on Next Article link
	 * 
	 */
	public void clickNextArticle() {
		try {
			clickOn(element(nextArticle));
		} catch (Exception e) {
			js.jsClick(nextArticle);
		}
	}

	/**
	 * Verify the text - Complete Registration now to earn tokens for enjoying
	 * articles!
	 * 
	 * @return
	 */
	public boolean verifyTextCompleteRegEarnTokens() {
		return isElementVisible(completeRegToEarnTokens);
	}

	/**
	 * Verify the text - Log In or Register now to earn tokens for enjoying
	 * articles!
	 * 
	 * @return
	 */
	public boolean verifyTextLoginToEarnTokens() {
		return isElementVisible(loginToEarnTokens);
	}

	/**
	 * click Any Articles from top stories
	 */

	public void clickAnyArticle() {
		waitForRenderedElementsToBePresent(article);
		js.jsClick(article);
	}

	/**
	 * Retrieve the Story Id of the read article
	 * 
	 * @return
	 */
	public String getStoryId() {
		waitABit(5);
		Pattern pattern = Pattern.compile("\\w+([0-9]+)\\w+([0-9]+)");
		Matcher matcher = pattern.matcher(getDriver().getCurrentUrl());
		matcher.find();
		return matcher.group();
	}
}