package com.pch.survey.pages.surveytab;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.CommonHeadersAndFooters;
import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.DevToolsNetworkTabDto;
import com.pch.survey.utilities.SeleniumDevTools;

public class SurveyMainPage extends CommonHeadersAndFooters {

	private By letsGoButton = By.linkText("LET'S GO");
	private By registerButton = By.linkText("Register");
	private By signInButton = By.linkText("Sign In");
	private By PrivacyPolicyClose = By.xpath("//div[@class='close_btn_thick']");
	private By tourClose = By.id("tourClose");
	private By closeTourPopup = By.cssSelector("button[class='close']");
	private By takeTour = By.linkText("Take a Tour");
	private By slideActive = By.cssSelector(".carousel-item.active");
	private By slideNext = By.className("carousel-control-next");
	private By slideIndicate = By.className("carousel-indicators");
	private By takeSurvey = By.linkText("TAKE SURVEY");
	private By featuredList = By.id("featuredList");
	private By surveylistContent = By.cssSelector(".survey__list__content__wrapper");
	private By surveyList = By.id("surveyList");
	private By clickBounsGamePlayNowBtn = By.xpath("//a[normalize-space()='Get Rewarded']");
	private By bonusGameLocked = By.xpath("//a[normalize-space()='locked']");
	private By phpdebugbarClose = By.cssSelector(".phpdebugbar-close-btn");
	private By handraiserLink = By.cssSelector("button[class='survey__btn survey__handraiser__link']");
	private By tokenAmt = By
			.xpath("//*[@class='uninav__token-center-alltime__tokens-amount uninav__token-balance__amount']");
	private By influencerUserLevelStatus = By.cssSelector("span.survey__profile-builder__content__img--influencer");
	private By influencerProUserLevelStatus = By
			.cssSelector("span.survey__profile-builder__content__img--influencerpro");
	private By influencerProPlusUserLevelStatus = By
			.cssSelector("span.survey__profile-builder__content__img--influencerproplus");
	private static String deviceType = ConfigurationReader.getInstance().getDeviceType().toUpperCase();
	public static String user;

	DevToolsNetworkTabDto dtoNetworkCalls = new DevToolsNetworkTabDto();

	private String mainWindowHandle = null;
	public static String surveyLandingPageUrl = null;

	public SurveyMainPage(WebDriver driver) {
		super(driver);
		mainWindowHandle = driver.getWindowHandle();
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public boolean verifyInfluencerUserStatus() {
		return driver.findElement(influencerUserLevelStatus).isDisplayed();
	}

	public boolean verifyInfluencerProUserStatus() {
		return driver.findElement(influencerProUserLevelStatus).isDisplayed();
	}

	public boolean verifyInfluencerProPlusUserStatus() {
		return driver.findElement(influencerProPlusUserLevelStatus).isDisplayed();
	}

	public void clickProfileBuilderLetsGoButton() {
		waitUntilUrlContains("pchsurveys");
		driver.findElement(letsGoButton).click();
	}

	public void clickRegisterButton() {
		driver.findElement(registerButton).click();
	}

	public void clickSignInButton() {
		driver.findElement(signInButton).click();
	}

	public void clickTakeSurvey(String... surveyType) {
		waitUntilUrlContains("pchsurveys");
		WebElement ele = null;
		if (surveyType.length == 0) {
			ele = driver.findElement(takeSurvey);
		} else if (surveyType.length == 1) {
			ele = driver.findElement(By.cssSelector("a[data-survey-mid*='" + surveyType[0] + "']"));
		}

		scrollIntoView(ele);
		ele.click();
	}

	public void navigateToMid(String mid) {
		try {
			waitUntilUrlContains("mid=", 30);
		} catch (Exception ex) {

		}
		surveyLandingPageUrl = driver.getCurrentUrl();
		// String url = EventCapture.intermediateURL;
		System.out.println("navigateToMid Survey landing page URL:" + surveyLandingPageUrl);

	}

	public void waitForSurveypage() {
		surveyLandingPageUrl = driver.getCurrentUrl();
		System.out.println("Survey landing page URL:" + surveyLandingPageUrl);
		try {
			waitUntilUrlContains("mid=", 30);
			surveyLandingPageUrl = driver.getCurrentUrl();
		} catch (Exception ex) {
		}
	}

	public void grabNetworkCalls(String url) {
		SeleniumDevTools.getRequestsDetails(driver, url);
//		SeleniumDevTools.getResponseDetails(driver, url);
	}

	public void getQmeeSurveyStartURL() {
		surveyLandingPageUrl = dtoNetworkCalls.getUrl();
		System.out.println("QMEE Survey start URL:" + surveyLandingPageUrl);
	}

	public boolean verifySurveyMidQueryStringParameter(String param) {
		return surveyLandingPageUrl.contains("mid=") && surveyLandingPageUrl.contains(param);
	}

	public void closePossiblePopups() {
		clickPrivacyPolicyCloseButton();
	}

	public void clickPrivacyPolicyCloseButton() {
		List<WebElement> elements = getElementList(PrivacyPolicyClose);
		if (elements.size() > 0)
			elements.get(0).click();
	}

	public void clickSurveyTourCloseButton() {
		List<WebElement> elements = null;
		if (deviceType.equalsIgnoreCase("DESKTOP"))
			elements = getElementList(tourClose);
		else
			elements = getElementList(closeTourPopup);
		if (elements.size() > 0) {
			if (elements.get(0).isDisplayed())
				elements.get(0).click();
		}
	}

	public void clickTakeATourLink() {
		waitUntilElementIsClickable(driver.findElement(takeTour)).click();

	}

	public boolean isTakeATourPopupDisplayed() {
		List<WebElement> elements = null;
		if (deviceType.equalsIgnoreCase("DESKTOP"))
			elements = getElementList(tourClose);
		else
			elements = getElementList(closeTourPopup);
		if (elements.size() > 0) {
			if (elements.get(0).isDisplayed())
				return true;
		}
		return false;
	}

	public boolean isActiveTourSlideDisplayed() {
		waitSeconds(1);
		WebElement parent = driver.findElement(slideActive);
		return parent.findElement(By.tagName("img")).isDisplayed();
	}

	public void clickCarouselNextArrow() {
		if (deviceType.equalsIgnoreCase("DESKTOP"))
			driver.findElement(slideNext).click();
		else {
			WebElement parent = driver.findElement(slideIndicate);
			List<WebElement> carouselIndicators = parent.findElements(By.tagName("li"));
			for (int x = 0; x < carouselIndicators.size(); x++) {
				if (!carouselIndicators.get(x).getAttribute("class").equals("active"))
					continue;
				carouselIndicators.get(x + 1).click();
				break;
			}
		}

	}

	public String getWindowHandle() {
		return mainWindowHandle;
	}

	public void switchToLastOpenTab() {
		waitSeconds(2);

		Set<String> handles = driver.getWindowHandles();

		for (String winHandle : handles) {
			if (winHandle.equals(mainWindowHandle))
				continue;
			driver.switchTo().window(winHandle);
		}

	}

	public int getSurveyCountInFeatureArea() {
		WebElement parent = driver.findElement(featuredList);
		List<WebElement> surveys = parent.findElements(surveylistContent);
		return surveys.size();
	}

	public int getSurveyCountInSurveyArea() {
		WebElement parent = driver.findElement(surveyList);
		List<WebElement> surveys = parent.findElements(surveylistContent);
		return surveys.size();
	}

	public int getNewCategorySurveyCountInFeatureArea() {
		int newCnt = 0;
		WebElement parent = driver.findElement(featuredList);
		List<WebElement> newSurveys = parent.findElements(surveylistContent);
		for (WebElement ele : newSurveys) {
			if (ele.getText().contains("NEW"))
				newCnt++;
		}
		return newCnt;
	}

	public int getNewCategorySurveyCountInSurveyArea() {
		int newCnt = 0;
		WebElement parent = driver.findElement(surveyList);
		List<WebElement> newSurveys = parent.findElements(surveylistContent);
		for (WebElement ele : newSurveys) {
			if (ele.getText().contains("NEW"))
				newCnt++;
		}
		return newCnt;

	}

	public void clickYesImInButton() {
		waitUntilElementIsClickable(driver.findElement(handraiserLink));
		scrollIntoView(driver.findElement(handraiserLink)).click();
	}

	public void clickRedeemTokensImage() {
		if (deviceType.equalsIgnoreCase("DESKTOP"))
			driver.findElement(By.cssSelector("a[class='uninav__token-center-alltime__redeem uninav__redeem-toggle']"))
					.click();
		else
			driver.findElement(By.linkText("PCHRewards")).click();
	}

	public boolean verifyBonusGameIsUnlocked() {
		return driver.findElement(clickBounsGamePlayNowBtn).isDisplayed();
	}

	public boolean verifyBonusGameLocked() {
		return driver.findElement(bonusGameLocked).isDisplayed();
	}
	
	public boolean isPlayNowPresent() {
		List<WebElement> elements = getElementList(By.cssSelector("a[aria-label='Bonus game unlocked play now']"));
		if (elements.size() > 0 && elements.get(0).isDisplayed())
			return true;
		return false;
	}

	public boolean isLocked() {
		WebElement lock = driver.findElement(By.xpath("//a[normalize-space()='locked']"));
		return lock.isDisplayed();

	}

	public void clickPlayNowButton() {
		scrollIntoView(driver.findElement(clickBounsGamePlayNowBtn));
		driver.findElement(clickBounsGamePlayNowBtn).click();
	}

	public void closePhpdebugbar() {
		waitUntilThePageLoads();
		try {
			WebElement closePhpdebugbar = driver.findElement(phpdebugbarClose);
			scrollIntoView(closePhpdebugbar);
			closePhpdebugbar.click();
		} catch (Exception e) {
		}
	}

	public String getTokenAmtText() {
		return driver.findElement(tokenAmt).getText();
	}

}
