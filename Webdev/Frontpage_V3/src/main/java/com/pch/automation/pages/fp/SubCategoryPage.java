package com.pch.automation.pages.fp;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;

public class SubCategoryPage extends PageObject {

	private final By articleList = By.cssSelector("div.stories > div.row.listing");
	private final By trendingElementsList = By.cssSelector("div.bottom-spacer > div.row.listing > div:nth-of-type(2)");
	private final By popularVideosSectionTitleName = By.xpath("//div[@class='bottom-spacer']/following-sibling::h3");
	private final By gptAdPosition = By
			.xpath("//div[@class='stories']/div[@id='div-gpt-ad-bottom']/preceding-sibling::*");
	private final By nativeAdPosition = By.xpath("//div[@id='div-nativ-ad-subcategory']");
	private final By viewMoreButton = By.cssSelector("div.viewMorebtn > a");
	private final By videoPlayIcon = By.cssSelector("div.stories i");
	private final By articleWithoutImage = By.cssSelector("div.col-xs-12.listing__content");
	private final By fullStoryLink = By.cssSelector("span.listing__content__a__full-story");
	private final By firstArticleLink = By
			.xpath("//div[@class='stories']/div[@class='row listing']/div[1]/a[contains(@href,'/article')][1]");
	private final By firstVideoLink = By
			.xpath("//div[@class='stories']/div[2][@class='row listing']/div[1]/a[contains(@href,'/video')][1]");
	private final By firstVideoOnPlayer = By.xpath("//li[@class='fp-video-item'][1]");
	private final By firstVideo = By
			.cssSelector("i.glyphicon.glyphicon-play.img-preview__play.img-preview__play_offset");

	HomePage homePage = new HomePage();

	/**
	 * Click the First Article link of the Sub Category page
	 */
	public void clickFirstArticleLink() {
		waitForRenderedElementsToBePresent(firstArticleLink);
		moveTo(firstArticleLink);
		try {
			clickOn(findAll(firstArticleLink).get(0));
		} catch (Exception e) {
			homePage.jsClick(firstArticleLink);
		}
	}

	/**
	 * Click the First Video on video player
	 */
	public void clickFirstVideoOnPlayer() {
		moveTo(firstVideoOnPlayer);
		clickOn(element(firstVideoOnPlayer));
	}

	public void clickFirstVideo() {
		try {
			waitForRenderedElementsToBePresent(firstVideo);
			clickOn(element(firstVideo));
		} catch (Exception e) {
			homePage.jsClick(firstVideo);
		}
	}

	/**
	 * Click the First Video link of the Sub Category page
	 */
	public void clickFirstVideoLink() {
		try {
			moveTo(firstVideoLink);
			clickOn(element(firstVideoLink));
		} catch (Exception e) {
			homePage.jsClick(firstVideoLink);
		}
	}

	/**
	 * Return the Full Story count from the Sub Category page
	 * 
	 * @return
	 */
	public int getFullStoryLinkCount() {
		return findAll(fullStoryLink).size();
	}

	/**
	 * Click on Full Story link
	 * 
	 */
	public void clickFullStoryLink() {
		try {
			waitForRenderedElementsToBePresent(fullStoryLink);
			clickOn(element(fullStoryLink));
		} catch (Exception e) {
			homePage.jsClick(fullStoryLink);
		}
	}

	/**
	 * Return True if the story without an image is present on Sub Category page
	 * 
	 * @return
	 */
	public boolean verifyArticleWithoutImagePresence() {
		return isElementVisible(articleWithoutImage);
	}

	/**
	 * Return the Count of the Video stories present on the page
	 * 
	 * @return
	 */
	public int getCountOfVideosOnPage() {
		return findAll(videoPlayIcon).size();
	}

	/**
	 * Click on View More button
	 */
	public void clickViewMore() {
		try {
			waitForRenderedElementsToBePresent(viewMoreButton);
			clickOn(element(viewMoreButton));
		} catch (Exception e) {
			homePage.jsClick(viewMoreButton);
		}
	}

	/**
	 * Return the page number details which going to navigate when click on View
	 * More button
	 * 
	 * @return
	 */
	public String getNextPageDetailsFromViewMore() {
		return element(viewMoreButton).getAttribute("href");
	}

	/**
	 * Return the position of the Native Ad unit in the Sub Category page
	 * 
	 * @return
	 */
	public int getPositionOfNativeAdUnit() {
		return findAll(nativeAdPosition).size();
	}

	/**
	 * Return the position of the GPT Ad unit in the Sub Category page
	 * 
	 * @return
	 */
	public int getPositionOfGpAdUnit() {
		return findAll(gptAdPosition).size() + 1;
	}

	/**
	 * Return the count of the Stories present in the Sub Category page
	 * 
	 * @return
	 */
	public int getArticleCount() {
		return findAll(articleList).size();
	}

	/**
	 * Return the count of the Trending Elements in the Sub Category page
	 * 
	 * @return
	 */
	public int getTrendingElementsCount() {
		return findAll(trendingElementsList).size();
	}

	/**
	 * Return the Popular Video section title name of the Sub Category page
	 * 
	 * @return
	 */
	public String getPopularVideosSectionTitleName() {
		return element(popularVideosSectionTitleName).getText();
	}

	/**
	 * Navigate to the respective sub-category page by using the given param label
	 * name.
	 * 
	 * @param sub_category_name
	 */
	public void navigateCategoryPageFromLabel(String categoryName) {
		By menu = By.xpath("//*[@id='header']//a[contains(text(),'" + categoryName.substring(1).toLowerCase() + "')]");
		try {
			waitForRenderedElementsToBePresent(menu);
			clickOn(element(menu));
		} catch (Exception e) {
			homePage.jsClick(menu);
		}
	}
}
