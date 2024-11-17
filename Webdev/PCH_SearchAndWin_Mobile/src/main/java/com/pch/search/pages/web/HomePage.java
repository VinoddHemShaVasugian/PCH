package com.pch.search.pages.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import com.pch.search.pages.lightBox.CreatePasswordLightBox;
import com.pch.search.pages.lightBox.LevelLightBox;
import com.pch.search.pages.lightBox.LightBox;
import com.pch.search.pages.lightBox.OptinLightBox;
import com.pch.search.pages.lightBox.ResetPasswordLightBox;
import com.pch.search.pages.lightBox.SignInLightBox;
import com.pch.search.pages.lightBox.WelcomeLightBox;
import com.pch.search.pages.lightBox.WinnersLightBox;
import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;

public class HomePage extends Action {

	private By winnerMessage = By.xpath("//div[contains(@class,'messageIWE')]/div");

	private HtmlElement FB_Like_Btn() {
		return waitForElementToBeVisible(By.xpath("//button[contains(@title,'Like')]"));

	}

	String device = Environment.getDevice();

	public HtmlElement menu() {
		return driver.findElement(By.xpath(".//*[@class='burger']"));
	}

	// elements under Navigation bar - Mobile
	public HtmlElement myAccount() {
		return driver.findElement(By.xpath("//a[contains(text(),'My Account')]"));
	}

	public HtmlElement home(){
		return driver.findElement(By.linkText("Home"));
	}
	
	public HtmlElement aboutPCHSearchWin(){
		return driver.findElement(By.linkText("About PCHSearch&Win"));
	}
	
	public HtmlElement howToSearch(){
		return driver.findElement(By.linkText("How To Search"));
	}
	
	public HtmlElement doAnddonts(){
		return driver.findElement(By.linkText("Dos and Don'ts"));
	}
	
	public HtmlElement aboutSuperPrize(){
		return driver.findElement(By.linkText("About SuperPrize®"));
	}
	
	public HtmlElement blog(){
		return driver.findElement(By.linkText("Blog"));
	}
	
	public HtmlElement aboutPCH(){
		return driver.findElement(By.linkText("About PCH"));
	}
	
	public HtmlElement contactUs(){
		return driver.findElement(By.linkText("Contact Us"));
	}
	
	public HtmlElement hamburgerMenu(){
		return driver.findElement(By.cssSelector("div.burger"));
	}
	
	public HtmlElement videoLink(){
		return driver.findElement(By.linkText("View video"));
	}
	
	public void verify_VideoLinkURL(){
		Assert.assertTrue(videoLink().getAttribute("href").contains("http://youtu.be"));
	}

	public void verifyAboutSearchWin(){
		hamburgerMenu().click();
		verifyLinkURL(aboutPCHSearchWin(), "about");
	}
	
	public void verifyHome(){
		hamburgerMenu().click();
		verifyLinkURL(home(), Common.getAppUrl(Environment.getAppName()));
	}
	
	public void verifyHowToSearch(){
		hamburgerMenu().click();
		verifyLinkURL(howToSearch(), "howto");
	}
	
	public void verifyDoAnddonts(){
		hamburgerMenu().click();
		verifyLinkURL(doAnddonts(), "dosdonts");
	}
	
	public void verifyAboutSuperPrize(){
		hamburgerMenu().click();
		verifyLinkURL(aboutSuperPrize(), "aboutsuperprize");
	}
	
	public void verifyBlog(){
		hamburgerMenu().click();
		blog().click();
		Verify_NewTab_And_Close("blog");
	}
	
	public void verifyAboutPCH(){
		hamburgerMenu().click();
		aboutPCH().click();
		Verify_NewTab_And_Close("about");
	}
	
	public void verifyContactUs(){
		hamburgerMenu().click();
		contactUs().click();
		Verify_NewTab_And_Close("custhelp");
	}
	
	
	public String welcomeMesg() {
		HtmlElement welcome;
		if (Environment.getDevice().equalsIgnoreCase("Mobile")) {
			driver.findElement(By.xpath(".//*[@id='uni-nav-top']/div[2]/div[1]")).click();
			welcome = driver.findElement(By.xpath(".//*[@id='uni-nav-middle']/div/div/a[1]"));
		}
		welcome = driver.findElement(By.xpath("//*[contains(@class,'credentials')]"));
		return welcome.getText();
	}

	public boolean isFbLikeButtonPresent() {
		boolean isExist = true;
		try {
			CustomLogger.log("Validating if FB like button exists");
			FB_Like_Btn();
			CustomLogger.log("FB Like button exists");
		} catch (Exception e) {
			CustomLogger.log("FB Like button does not exists");
			isExist = false;
		}
		return isExist;
	}

	public void clickFb_Like_Btn() {
		try {
			isFbLikeButtonPresent();
			CustomLogger.log("Going to click FB Like button");
			FB_Like_Btn().click();
			CustomLogger.log("FB Like button clicked");
		} catch (Exception e) {
			CustomLogger.log(e.toString());
		}

	}

	public void validateFB_LoginWindow() {
		try {
			String mainWindow = driver.getWindowHandle();
			driver.switchTo_iframe("//iframe[contains(@title,'fb:like Facebook Social Plugin')]");
			clickFb_Like_Btn();
			driver.switchToChildWindow(mainWindow);
			CustomLogger.log("Going to validate FB login page is open up in a new window");
			System.out.println(driver.getWindowHandles().size());
			Assert.assertTrue(driver.getWindowHandles().size() > 1, "Window handles not matched");
			CustomLogger.log("FB login page open up in a new window");
			driver.close();
			driver.switchTo().window(mainWindow);
		} catch (Exception e) {
			CustomLogger.log(e.toString());
		}
	}

	public void enterFBDetails(String email, String password) {
		String parent_window = driver.getWindowHandle();
		driver.switchTo_iframe("//iframe[contains(@title,'fb:like Facebook Social Plugin')]");
		clickFb_Like_Btn();
		Set<String> window_handles = driver.getWindowHandles();
		for (String handle : window_handles) {
			if (!handle.equals(parent_window)) {
				driver.switchTo().window(handle);
				break;
			}
		}
		// Login to FB
		driver.findElement(By.name("email")).sendKeys(email);
		driver.findElement(By.name("pass")).sendKeys(password);
		driver.findElement(By.name("login")).click();
		waitForElementToBeVisible(By.xpath("//button[contains(text(),'Like')]")).click();
		driver.switchTo().window(parent_window);
		driver.switchTo_iframe("//iframe[contains(@title,'fb:like Facebook Social Plugin')]");
	}

	public boolean isInlineAdPresent() {
		try {
			HtmlElement inLineAd = driver.findElement(By.xpath(
					"//div[@class='tbl content clearfix morenews']/preceding-sibling::div[a[@class='ad-choice']]/descendant::div[contains(@id,'google_ads_iframe')]/iframe"));
			return inLineAd.isDisplayed();

		} catch (WebDriverException wde) {
			return false;
		}
	}

	public void closeWelcomeToSearchLightBox() {
		LightBox lb = new WelcomeLightBox(driver);
		// Common.sleepFor(3000);
		lb.dismissLightBox();
	}

	public void closeWelcomeToSearchLightBoxAfterWait() {
		LightBox lb = new WelcomeLightBox(driver);

		((WelcomeLightBox) lb).dismissLightBox(true);
	}

	public void closeUserLevelLightBox() {
		CustomLogger.log("Closing user level light box if there is any");
		LightBox lb = new LevelLightBox(driver);
		lb.dismissLightBox();
	}

	public void closeWinnerLightBox() {
		LightBox lb = new WinnersLightBox(driver);
		lb.dismissLightBox();
	}

	public OptinLightBox getOptinLightBox() {
		return new OptinLightBox(driver);
	}

	public boolean isOptinLightBoxPresent() {
		LightBox lb = new WelcomeLightBox(driver);
		return lb.isLightBoxPresent();
	}

	public void closeOptinLigthBox() {
		LightBox lb = new WelcomeLightBox(driver);
		lb.dismissLightBox();
	}

	/**
	 * Loads the search if not already loaded. <br>
	 * Checks for the presence of search logo. <br>
	 * If logo present do nothing.
	 */
	public void load() {
		if (!isPageLoaded()) {
			String applicationURL = Common.getAppUrl(Environment.getAppName());
			load(applicationURL);
		}
	}
	
	public void Gsload() {
		if (!isPageLoaded()) {
			String applicationURL = "http://search.stg.pch.com/guidedsearch";
			load(applicationURL);
		}
	}


	public boolean isPageLoaded() {
		String searchLogoXPath;
		if (Environment.getDevice().equalsIgnoreCase("mobile")) {
			searchLogoXPath = ".//*[@class='gs-logo']/img";
		} else {
			searchLogoXPath = "//a[@title='PCH Search and Win']";
		}
		if (driver.getCountOfElementsWithXPath(searchLogoXPath) > 0) {
			if (driver.findElement(By.xpath(searchLogoXPath)).isDisplayed()) {
				CustomLogger.log("Search & Win already loaded.");
				return true;
			}
		}

		return false;
	}

	public HtmlElement searchLogo() {
		return driver.findElement(By.xpath("//a[@title='PCH Search and Win']"));
	}

	/**
	 * Loads the search with URL generated from reg foundation page
	 * 
	 * @param registrationURL
	 *            URL generated from reg foundation
	 */
	public void load(String registrationURL) {
		CustomLogger.log("Loading :" + registrationURL + " .......");
		driver.get(registrationURL);
		CustomLogger.log(registrationURL + " Loaded");
		closeWelcomeToSearchLightBox();
	}

	public void loadInNewTab() {
		driver.openNewTabAndSwitchToIt();
		load();
	}

	public ResetPasswordLightBox resetPasswordLightBox() {
		return new ResetPasswordLightBox(driver);
	}

	public SignInLightBox signInLightBox() {
		return new SignInLightBox(driver);
	}

	public CreatePasswordLightBox createPasswordLightBox() {
		return new CreatePasswordLightBox(driver);
	}

	public String PCHLeaderBoardUserTypeMessage() {

		HtmlElement pchusermessage = driver.findElement(By.xpath("//*[contains(@class, 'get_tokens')]"));
		String usermessageleaderboard = pchusermessage.getText();
		return usermessageleaderboard;

	}

	public String get6x6CarouselTitle() {
		return driver.findElement(By.xpath("//div[@id='carouselSync']/preceding-sibling::h2[1]")).getText();
	}

	public String getTrendingSearchHeight() {
		try {
			HtmlElement element = driver.findElement(By.cssSelector("div.featured"));
			CustomLogger.log("Trending Search Element height " + element.getCssValue("height"));
			return element.getCssValue("height");
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public List<String> getTrendingSearches() {
		try {
			List<HtmlElement> trendingSearchElements = driver
					.findElements(By.cssSelector("div.featured div.content>a"));
			List<String> trendingSearches = new ArrayList<String>();
			for (HtmlElement element : trendingSearchElements) {
				trendingSearches.add(element.getText());
			}
			CustomLogger.log("Trending Searches on home page -" + trendingSearches);
			return trendingSearches;
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public void waitTillPageLoads() {
		try {
			CustomLogger.log("Waiting fro the page to load completely..");
			driver.waitForBrowserToLoadCompletely();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String titleOfPage() {
		return driver.getTitle();
	}

	public void waitFor(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception e) {
			CustomLogger.log("Failed to wait..");
		}
	}

	public void clickBBBandVerifyTitle(HtmlElement element) {
		String parentHandle = driver.getWindowHandle(); // get the current
														// window handle
		element.scrollDownAndClick(); // click some link that opens a new window

		driver.waitForBrowserToLoadCompletely();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle); // switch focus of WebDriver to
													// the next found window
													// handle (that's your newly
													// opened window)
		}

		Assert.assertTrue(driver.getTitle().contains("NY - BBB Business Review - BBB serving Metropolitan New York, "));

		driver.close(); // close newly opened window when done with it
		driver.switchTo().window(parentHandle);

	}

	public List<Integer> getLighBoxCountFB(int afterStoryviewCount, int triggerCount, String registrationURL) {
		int lbCount = 0;
		int forHowmanyClicks = 0;
		for (int j = 0; j < triggerCount; j++) {

			for (int i = 0; i < afterStoryviewCount; i++) {
				HtmlElement element = driver
						.findElement(By.xpath("//a[starts-with(text(),'Full') and contains(text(),'story')]"));
				element.scrollDownAndClick();
				driver.waitForBrowserToLoadCompletely();

				if (i == 0) {
					WelcomeLightBox wlb = new WelcomeLightBox(driver);
					if (wlb.isLightBoxPresent()) {
						wlb.isTextDisplayed("Complete Registration");
						wlb.dismissLightBox();
					}
					lbCount++;
				}
				forHowmanyClicks++;

				load(registrationURL);
				driver.waitForBrowserToLoadCompletely();
			}
		}
		List<Integer> totalItertions = new ArrayList<Integer>();
		totalItertions.add(lbCount);
		totalItertions.add(forHowmanyClicks / lbCount);

		return totalItertions;

	}

	public void goToMyAccountPage() {
		if (device.equalsIgnoreCase("mobile")) {
			menu().click();
			myAccount().click();
		}
		CustomLogger.log("Navigating to account page");
		// driver.findElement(By.xpath("//a[contains(text(),'My
		// Account')]")).click();
	}

	public void goToAboutPCHSearchAndWinPage() {
		CustomLogger.log("Navigating to about pch page");
		driver.findElement(By.xpath("//a[text()='About PCHSearch&Win']")).click();
	}

	public void goToDosAndDonts() {
		CustomLogger.log("Navigating to dos and don'ts page");
		driver.findElement(By.xpath("//a[contains(text(),'Dos and Don')]")).click();
	}

	public void goToAboutTokens() {
		CustomLogger.log("Navigating to about tokens page");
		driver.navigate().to("http://search." + Environment.getEnvironment() + ".pch.com/abouttokens");
	}

	public void goToHomePage() {
		CustomLogger.log("Navigating to home page");
		if (device.equalsIgnoreCase("mobile")) {
			// menu().click();
			// driver.findElement(By.xpath("//a[text()='Home']")).click();
			// clicking on home link is navigating to pch.com
			driver.get("http://search.stg.pch.com/guidedsearch");
		} else {
			driver.findElement(By.xpath("//a[@title='PCH Search and Win']")).moveToElementAndClick();
			driver.waitForBrowserToLoadCompletely();
		}
	}

	public void goToMyInfoPage(String userName) {
		CustomLogger.log("Navigating to My Info Page");
		if (device.equalsIgnoreCase("mobile")) {
			menu().click();
			driver.findElement(By.xpath(String.format("//a[contains(text(),'%s')]", userName))).click();
		} else {
			driver.findElement(By.xpath(String.format("//a[contains(text(),'%s')]", userName))).click();
			driver.waitForBrowserToLoadCompletely();
		}
	}

	public void goToTokenHistory() {
		CustomLogger.log("Going to Token Hisrory Page");
		if (device.equalsIgnoreCase("mobile")) {
			menu().click();
		}
		driver.findElement(By.xpath("//a[text()='Token History']")).click();
	}

	public boolean isConsecutiveVisitDisplay() {
		if (device.equalsIgnoreCase("mobile")) {
			CustomLogger.log("Not Applicable for Mobile.");
			return true;
		} else {
			boolean isDisplayed;
			try {
				driver.findElements(By.xpath("//div[@id='consecutive-visits']"));
				isDisplayed = true;
			} catch (Exception e) {
				isDisplayed = false;
			}
			return isDisplayed;
		}
	}

	public String getMessage() {
		Common.sleepFor(2000);
		return driver.findElement(By.xpath(".//div[@id='messageResults']/span")).getText();
	}

	public boolean isConsecutiveVisitTokensAwardedForCurrentDay(int day) {
		if (device.equalsIgnoreCase("mobile")) {
			CustomLogger.log("Not Applicable for Mobile.");
			return true;
		} else {
			return driver.findElements(By.xpath("//div[@class='compl-day-indicator']")).size() == day;
		}

	}

	public boolean isConsecutiveVisitTokensAwardedForFifthtDay() {
		if (device.equalsIgnoreCase("mobile")) {
			CustomLogger.log("Not Applicable for Mobile.");
			return true;
		} else {
			Common.sleepFor(3000);
			return driver.findElements(By.xpath("//div[@class='compl-mission-message']")) != null;
		}
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public String getVisbileTextInPage() {
		return driver.findElement(By.xpath("//*[@class='mainContainer']")).getText();
	}

	/*
	 * precondition: Registered in PCH.com and complete registration and
	 * navigate to SearchPage. Verifying welcome message expected: we should not
	 * get welcome message.
	 */
	public boolean verifyingWelcomeMsgFromPCH() {
		driver.openNewTabAndSwitchToIt();
		driver.switchToTab(1);
		load();
		LightBox lb = new WelcomeLightBox(driver);
		Assert.assertFalse(lb.isLightBoxPresent(), "We are seeing welcome message , but we should'nt");

		return true;
	}

	public String getMessageFromWinnerLightBox() {
		String message = null;

		LightBox lb = new WinnersLightBox(driver);
		if (lb.isLightBoxPresent()) {
			Common.sleepFor(2000);
			message = driver.findElement(winnerMessage).getText();
		} else {
			CustomLogger.log("Lightbox for Winners does not appear");
		}
		return message;
	}

	public HtmlElement videoPopUP() {
		return driver.findElement(By.id("pchVideoContainer"));
	}

	public HtmlElement homePageVideopopUp() {
		return driver.findElement(By.id("pchVideoPlayer_wrapper"));
	}

	public int homePageVideopopUpCount() {
		return driver.getCountOfElementsWithXPath(".//*[@id='pchVideoPlayer_wrapper']");
	}

	public int videoPopUpCount() {
		return driver.getCountOfElementsWithXPath(".//*[@id='congratsBox']");
	}

	public void loadGuidedSearch(String gsName) {
		String url = "http://search." + Environment.getEnvironment() + ".pch.com/guidedsearch?gsSearchID=" + gsName;
		driver.get(url);
		driver.waitForBrowserToLoadCompletely();
		driver.navigate().refresh();
		CustomLogger.log(url + " loaded successfully.");
		CustomLogger.log(gsName + " Guided Search loaded successfully.");
	}

	public void loadSearchWithEmailLink(String url) {
		driver.get(url);
		driver.waitForBrowserToLoadCompletely();
		driver.navigate().refresh();
		CustomLogger.log(url + " loaded successfully.");
	}

	public void verifyNFSP(String nfsp) {
		driver.switchTo().frame(0);
		Common.sleepFor(3000);
		driver.findElement(By.xpath("//span[contains(text(),'Desserts')]")).click();
		Assert.assertTrue(getBrowserConsoleSearchLog().toString().contains(nfsp));
	}

	public void gotoURL(String url) {
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Navigating to : " + url);
		driver.get(url);
	}

	public void loadSerpWithNfsp(String searchTerm, String nfsp) {
		String url = "http://search." + Environment.getEnvironment() + ".pch.com/search?q=" + searchTerm + "&nfsp="
				+ nfsp;
		driver.get(url);
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log(url + " loaded successfully.");
	}

	public void loadCrossPromotion(String string) {
		String url = "http://search." + Environment.getEnvironment() + ".pch.com/" + string;
		driver.get(url);
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log(url + " loaded successfully.");
		
	}

}
