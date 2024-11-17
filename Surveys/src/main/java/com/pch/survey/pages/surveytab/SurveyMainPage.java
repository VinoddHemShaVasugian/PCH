package com.pch.survey.pages.surveytab;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.CommonHeadersAndFooters;
import com.pch.survey.utilities.ConfigurationReader;

import junit.framework.Assert;

public class SurveyMainPage extends CommonHeadersAndFooters {

	private By letsGoButton = By.linkText("LET'S GO");
	private By registerButton = By.linkText("Register");
	private By signInButton = By.linkText("Sign In");
	private static String deviceType = ConfigurationReader.getInstance().getDeviceType().toUpperCase();

	private String mainWindowHandle = null;
	public static String surveyLandingPageUrl = null;

	public SurveyMainPage(WebDriver driver) {
		super(driver);
		mainWindowHandle = driver.getWindowHandle();
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
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

	public void clickTakeSurvey(String ... surveyType ) {
		waitUntilUrlContains("pchsurveys");
		WebElement ele = null ;
		if(surveyType.length==0) {
			 ele = driver.findElement(By.linkText("TAKE SURVEY"));
		}
		else if(surveyType.length==1) {
			 ele = driver.findElement(By.cssSelector("a[data-survey-mid*='" + surveyType[0] + "']"));
		}
		
		scrollIntoView(ele);
		ele.click();
	}

	/*
	 * public void clickTakeSurvey() { 
	 * waitUntilUrlContains("pchsurveys");
	 * WebElement ele = driver.findElement(By.linkText("TAKE SURVEY"));
	 * scrollIntoView(ele); 
	 * ele.click(); }
	 */

	public void navigateToMid(String mid) {
		
 	try{
 			waitUntilUrlContains("mid=",5);
		}
		catch(Exception ex) {

		}
		surveyLandingPageUrl = driver.getCurrentUrl();
		System.out.println("Survey landing page URL:" + surveyLandingPageUrl);
		
	}

	public void waitForSurveypage() {
		try{
 			waitUntilUrlContains("mid=");
 			surveyLandingPageUrl = driver.getCurrentUrl();
 			System.out.println("Survey landing page URL:" + surveyLandingPageUrl);

		}
		catch(Exception ex) {

		}

	}		
		
	public boolean verifySurveyMidQueryStringParameter(String param) {

		return surveyLandingPageUrl.contains("mid=") && surveyLandingPageUrl.contains(param);


	}


	public void closePossiblePopups() {
		clickPrivacyPolicyCloseButton();
		//		clickSurveyTourCloseButton();

	}

	public void clickPrivacyPolicyCloseButton() {
		By PrivacyPolicyClose = By.xpath("//div[@class='close_btn_thick']");
		List<WebElement> elements = getElementList(PrivacyPolicyClose);
		if (elements.size() > 0)
			elements.get(0).click();
	}

	public void clickSurveyTourCloseButton() {
		List<WebElement> elements = null;
		if (deviceType.equalsIgnoreCase("DESKTOP"))
			elements = getElementList(By.id("tourClose"));
		else
			elements = driver.findElements(By.cssSelector("button[class='close']"));
		if (elements.size() > 0) {
			if (elements.get(0).isDisplayed())
				elements.get(0).click();
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
		WebElement parent = driver.findElement(By.id("featuredList"));
		List<WebElement> surveys = parent.findElements(By.cssSelector(".survey__list__content__wrapper"));
		return surveys.size();
	}

	public int getSurveyCountInSurveyArea() {
		WebElement parent = driver.findElement(By.id("surveyList"));
		List<WebElement> surveys = parent.findElements(By.cssSelector(".survey__list__content__wrapper"));
		return surveys.size();
	}


	public int getNewCategorySurveyCountInFeatureArea() {
		int newCnt = 0;
		WebElement parent = driver.findElement(By.id("featuredList"));
		List<WebElement> newSurveys = parent.findElements(By.cssSelector(".survey__list__content__wrapper"));
		for (WebElement ele : newSurveys) {
	 		if (ele.getText().contains("NEW"))
				newCnt++;
		}
		return newCnt;
	}

	public int getNewCategorySurveyCountInSurveyArea() {
		int newCnt = 0;
		WebElement parent = driver.findElement(By.id("surveyList"));
		List<WebElement> newSurveys = parent.findElements(By.cssSelector(".survey__list__content__wrapper"));
		for (WebElement ele : newSurveys) {
	 		if (ele.getText().contains("NEW"))
				newCnt++;
		}
		return newCnt;

	}

	public void clickYesImInButton() {
		scrollIntoView(driver.findElement(By.cssSelector("button[class='survey__btn survey__handraiser__link']"))).click();
	}

	public void clickRedeemTokensImage() {
		if (deviceType.equalsIgnoreCase("DESKTOP"))
			driver.findElement(By.cssSelector("a[class='uninav__token-center-alltime__redeem uninav__redeem-toggle']")).click();
		else
			driver.findElement(By.linkText("PCHRewards")).click();
	}

	public void verifyPlayNowIsNotPresent() {

		boolean isElementDisplayed = false;
		try {

			driver.findElement(By.xpath("//a[@aria-label='Bonus game unlocked play now']"));
			isElementDisplayed = true;
		} catch (Exception e) {

		}

		assertFalse(isElementDisplayed);

	}


}
