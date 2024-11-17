package com.pch.automation.pages.fp;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class VideoPage extends PageObject {
	private final By entertainmentVideos = By.xpath("//a[text()=' Entertainment']");
	private final By newsVideos = By.xpath("//a[text()=' News']");
	private final By lifestyleVideos = By.xpath("//a[text()=' Lifestyle']");;
	private final By sportsVideos = By.xpath("//a[text()=' Sports']");
	private final By breadcrumbs = By.cssSelector("div.container.breadcrumbs > h2");
	private final By playlistTitle = By.cssSelector("div.fp-video-playlist__title");
	private final By videoTitle = By.id("video-player-title");
	private final By videoDesc = By.id("vid-player-description");
	private final By tokensClaimedButton = By.cssSelector("div.fp-video-token-button--just-claimed");
	private final By claimedTokenAmount = By.cssSelector("span.fp-video-token-button__token-amt");
	private final By videoPlayer = By.id("jwPlayer");
	private final By login = By.xpath("//a[contains(text(),'Log In')]");
	private final By register = By.xpath("(//a[contains(text(),'Register')])[2]");
	private final By nextVideo = By.cssSelector("small.fp-video-overlay-next-video__small-text");
	private final By playNextVideo = By.xpath("//li[@class='fp-video-item'][1]");
	private final By completeRegistration = By
			.cssSelector("section.fp-video-overlay-next-video__unrecognized-message--complete-reg");
	private final By playCircle = By.cssSelector(".glyphicon.glyphicon-play-circle");
	private final By nextVideoDescPlayCircle = By
			.cssSelector("aside.fp-video-overlay.fp-video-overlay-next-video[style='display: block;']");
	private final By closePopup = By.xpath("//*[@class='close_lb']");
	private final By backToHomeLink = By.cssSelector("a.next-section__link");
	private final By videoBottomGptAd = By.cssSelector("div#gpt-ad-bottom-native");
	private final By selectedVideoTitleOnBottomPlaylist = By
			.cssSelector("li.fp-video-item.fp-video-item--selected div.fp-video-item__title");
	private final By videoPlayMode = By
			.cssSelector("aside.fp-video-overlay.fp-video-overlay-next-video[style='display: none;']");
	private final By videosMenu = By.xpath("//div[@id='header']//a[contains(@href ,'video')]");
	private final By playVideoMiddle = By.cssSelector("div.jw-icon-display");
	private final By skipAd = By.cssSelector("div.videoAdUiSkipButtonExperimentalText");
	private final By playVideo = By.cssSelector("div.jw-icon-playback");
	private final By videoDuration = By.cssSelector("div.jw-text-duration");
	private final By defaultTokenIcon = By.cssSelector("img.fp-video-token-button__coin");
	private final By totalVideosCount = By.cssSelector("li.fp-video-item");
	private final By categoryList = By.cssSelector("a.section-header__link");

	HomePage homePage = new HomePage();

	/**
	 * To skip the advertisements before video
	 * 
	 */
	public void skipAd() {
		if (isElementVisible(skipAd)) {
			clickOn(element(skipAd));
		}
	}

	/**
	 * To get number of videos present in the page
	 * 
	 */
	public int totalVideosCount() {
		return findAll(totalVideosCount).size();
	}

	/**
	 * To play next video
	 * 
	 */
	public void playNextVideo() {
		if (isElementVisible(playNextVideo)) {
			clickOn(element(playNextVideo));
		}
	}

	/**
	 * Verify the display of Bottom GPT ad
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verifyBottomGptDd() throws Exception {
		moveTo(videoBottomGptAd);
		return isElementVisible(videoBottomGptAd);
	}

	/**
	 * To get duration of videos playing
	 * 
	 */
	public int getVideoDuration() {
		skipAd();
		waitForRenderedElementsToBePresent(videoDuration);
		return Integer.parseInt(element(videoDuration).getText().replace("\"", "").split(":")[0].trim());
	}

	/**
	 * Tp play the video
	 * 
	 */
	public void playVideo() {
		if (isElementVisible(playVideoMiddle)) {
			homePage.jsClick(playVideoMiddle);
		} else {
			homePage.jsClick(playVideo);
		}
		skipAd();
		waitFor(getVideoDuration() * 1000);
	}

	/**
	 * Return the display of Back to Home link
	 * 
	 * @return
	 */
	public boolean verifyBackToHomeLink() {
		return isElementVisible(backToHomeLink);
	}

	/**
	 * To verify the play circle
	 * 
	 */
	public boolean verifyPlayCircle() {
		skipAd();
		waitABit(50000);
		waitForRenderedElementsToDisappear(videoPlayMode);
		waitForRenderedElementsToBePresent(nextVideoDescPlayCircle);
		return isElementVisible(nextVideoDescPlayCircle);
	}

	public void clickPlayCircle() {
		verifyPlayCircle();
		clickOn(element(playCircle));
	}

	/**
	 * To get the login urls
	 */
	public String getLoginUrl() {
		waitForRenderedElementsToBePresent(register);
		return element(login).getAttribute("href");
	}

	/**
	 * To get the register urls
	 */
	public String getRegisterUrl() {
		waitForRenderedElementsToBePresent(register);
		return element(register).getAttribute("href");
	}

	/**
	 * To verify the video end screen for Mini reg, Social user and silver usr.
	 */
	public boolean verifyVideoendscreenCompleteReg() {
		waitForRenderedElementsToBePresent(completeRegistration);
		return isElementVisible(completeRegistration);
	}

	/**
	 * To verify the video end screen for unrec user.
	 */
	public boolean verifyNextVideoUnrecUser() {
		waitABit(50000);
		waitForRenderedElementsToBePresent(register);
		return isElementVisible(login) && isElementVisible(register) && isElementVisible(nextVideo);
	}

	/**
	 * To verify the video
	 */
	public boolean verifyNextVideo() {
		waitForRenderedElementsToBePresent(nextVideo);
		return isElementVisible(nextVideo);
	}

	/**
	 * To click the video
	 */
	public void clickNextVideo() {
		waitForRenderedElementsToBePresent(nextVideo);
		clickOn(element(nextVideo));
		verifyTokensClaimedButton();
	}

	/**
	 * Verify the video play list section by the given category name
	 */
	public boolean verifyVideoPlaylist(String categoryName) {
		return isElementVisible(
				By.xpath("//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), ' "
						+ categoryName + "')]"));
	}

	/**
	 * To click on news video menu from video landing page
	 */
	public void clickNewsVideos() {
		clickOn(element(newsVideos));
	}

	/**
	 * To click on sports video menu from video landing page
	 */
	public void clickSportsVideos() {
		clickOn(element(sportsVideos));
	}

	/**
	 * To click on entertainment video menu from video landing page
	 */
	public void clickEntertainmentVideos() {
		clickOn(element(entertainmentVideos));
	}

	/**
	 * To verify the video player information
	 */
	public boolean verifyVideoPlayer() {
		return isElementVisible(videoPlayer);
	}

	/**
	 * To verify the video landing page completely
	 */
	public boolean verifyVideoLandingPage() {
		waitABit(5);
		waitForRenderedElementsToBePresent(videoTitle);
		return isElementVisible(videoTitle) && isElementVisible(breadcrumbs);
	}

	/**
	 * To verify the category links on video landing page.
	 */
	public boolean verifyCategoryPlaylist() {
		return isElementVisible(entertainmentVideos) && isElementVisible(newsVideos)
				&& isElementVisible(lifestyleVideos) && isElementVisible(sportsVideos);
	}

	/**
	 * To get category play lists.
	 */
	public LinkedList<String> getCategoryPlaylist() {
		List<WebElementFacade> eleList = findAll(categoryList);
		LinkedList<String> catagoryPlaylist = new LinkedList<String>();
		for (WebElement ele : eleList) {
			catagoryPlaylist.add(ele.getText().trim());
		}
		return catagoryPlaylist;
	}

	/**
	 * To verify the FA video section
	 */
	public boolean verifyFaVideosection() {
		return isElementVisible(videoTitle) && isElementVisible(videoDesc) && isElementVisible(playlistTitle);
	}

	/**
	 * Retrieve the Video title
	 * 
	 * @return
	 */
	public String getVideoTitle() {
		return element(videoTitle).getText();
	}

	/**
	 * Retrieve the Video title from bottom play list
	 * 
	 * @return
	 */
	public String getVideoTitleOnBottomPlaylist() {
		return element(selectedVideoTitleOnBottomPlaylist).getText();
	}

	/**
	 * To close complete registration popup
	 * 
	 */
	public void closeCompleteRegPopup() {
		clickOn(element(closePopup));
	}

	/**
	 * Verify the Tokens Claimed button on Video player
	 * 
	 * @return
	 */
	public boolean verifyTokensClaimedButton() {
		waitABit(15000);
		waitForRenderedElementsToBePresent(tokensClaimedButton);
		return isElementVisible(tokensClaimedButton);
	}

	/**
	 * Verify the Tokens Claimed amount present on Video player
	 * 
	 * @return
	 */
	public boolean verifyTokensClaimedAmount() {
		skipAd();
		waitABit(10000);
		waitForRenderedElementsToBePresent(claimedTokenAmount);
		return isElementVisible(claimedTokenAmount);
	}

	/**
	 * Retrieve the Claimed token amount
	 * 
	 * @return
	 */
	public String getClaimedTokenAmount() {
		skipAd();
		waitABit(20000);
		waitForRenderedElementsToBePresent(claimedTokenAmount);
		return element(claimedTokenAmount).getText();
	}

	/**
	 * Return the both Category & Sub-Category type
	 * 
	 * @return
	 */
	public String getCategoryType() {
		String type = element(breadcrumbs).getText();
		return type.contains("/") ? type.split("/")[1].toLowerCase().trim() : type.toLowerCase().trim();
	}

	/**
	 * Return the Video play list count
	 * 
	 * @return
	 */
	public int getPlaylistVideoCount(String category) {
		By videoPlaylistCount = By.xpath("//section[@data-category-link='" + category.toLowerCase().trim()
				+ "']//div[contains(@class,'slick-active')]");
		waitForRenderedElementsToBePresent(videoPlaylistCount);
		return findAll(videoPlaylistCount).size();
	}

	public void clickNextArrow(String category) {
		By nextArrowEnableStatus = By.cssSelector("section[data-category-link='" + category.toLowerCase().trim()
				+ "'] > svg[aria-disabled='false']:nth-of-type(2)");
		clickOn(element(nextArrowEnableStatus));
	}

	/**
	 * To click Video Menu
	 * 
	 */
	public void clickVideoMenu() {
		waitForRenderedElementsToBePresent(videosMenu);
		try {
			clickOn(element(videosMenu));
		} catch (Exception e) {
			homePage.jsClick(videosMenu);
		}
	}

	/**
	 * To verify the default token icon & message of the video player
	 */
	public boolean verifyDefaultTokenIconMsg(String message) {
		skipAd();
		waitABit(50000);
		waitForRenderedElementsToBePresent(completeRegistration);
		return (element(completeRegistration).getText()).contains(message) && isElementVisible(defaultTokenIcon);
	}
}