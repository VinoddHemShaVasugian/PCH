package com.pch.survey.pages.surveytab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;

public class MissionModulesPage extends PageObject {

	private By dailyMissionProgressBar = By.cssSelector("mission-progress-bar[data-alias='newdailymission']");
	private By dailyMissionShadow = By.cssSelector("mission-dropdown[data-alias='newdailymission']");
	private By weeklyMissionProgressBar = By.cssSelector("mission-progress-bar[data-alias='newweeklymission']");
	private By weeklyMissionShadow = By.cssSelector("mission-dropdown[data-alias='newweeklymission']");
	private By monthlyMissionShadow = By.cssSelector("mission-dropdown[data-alias='newmonthlymission']");
	private By shadowAutoMissionModule = By.cssSelector("mission-toggle[data-alias='automationmission']");
	private By autoMissionProgressBar = By.cssSelector("mission-progress-bar[data-alias='automationmission']");
	private By autoMissionShadow = By.cssSelector("mission-dropdown[data-alias='automationmission']");
	private By expandMissionModule = By.cssSelector("#mission-toggle-btn");
	private By collapseMissionModule = By.cssSelector(".mission-toggle-btn.rotate");
	private By missionSteps = By.cssSelector("mission-step[data-status='available']");
	private By missionProgressBarStatus = By.cssSelector("#numbers");
	private By letgoBtn = By.cssSelector("#cta");
	private By missionCompleteMsg = By.cssSelector("div > p.msg > span");
	private By missionCenters = By.cssSelector("#mission-center");
	private By missionModule = By.tagName("//mission-single");
	private By missionModuleSteps = By.tagName("//mission-step");
	private By autoMissionInfoIcon = By.cssSelector("div.mission_info");

	public MissionModulesPage() {
		super(driver);
	}

	public MissionModulesPage(WebDriver driver) {
		super(driver);
	}

	public int getMissionModuleCount() {
		return driver.findElements(missionModule).size();
	}

	public boolean verifyMissionModule() {
		waitUntilThePageLoads();
		return driver.findElement(missionCenters).isDisplayed();
	}

	public boolean verifyInfoIconOnAutomationMissionModule() {
		waitUntilThePageLoads();
		waitUntilElementIsVisible(10, shadowAutoMissionModule);
		WebElement expandBtn = driver.findElement(shadowAutoMissionModule).getShadowRoot()
				.findElement(autoMissionInfoIcon);
		return expandBtn.isDisplayed();
	}

	public boolean expandMissionModule() {
		waitUntilThePageLoads();
		waitSeconds(3);
		waitUntilElementIsVisible(10, shadowAutoMissionModule);
		WebElement expandBtn = driver.findElement(shadowAutoMissionModule).getShadowRoot()
				.findElement(expandMissionModule);
		waitUntilElementIsClickable(expandBtn);
		scrollIntoView(expandBtn).click();
		try {
			return driver.findElement(shadowAutoMissionModule).getShadowRoot().findElement(collapseMissionModule)
					.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public String[] getMissionStepsCount() {
		waitUntilThePageLoads();
		waitUntilElementIsVisible(60, autoMissionProgressBar);
		String[] totalCount = driver.findElement(autoMissionProgressBar).getShadowRoot()
				.findElement(missionProgressBarStatus).getText().split("/");
		return totalCount;
	}

	public boolean navigateToMissionStep() {
		waitUntilThePageLoads();
		waitUntilElementIsVisible(60,
				driver.findElement(shadowAutoMissionModule).getShadowRoot().findElement(collapseMissionModule));
		waitUntilElementIsVisible(30, autoMissionShadow);
		waitUntilElementIsClickable(driver.findElement(autoMissionShadow));
		try {
			WebElement missionStep = driver.findElement(autoMissionShadow).getShadowRoot().findElement(missionSteps)
					.getShadowRoot().findElement(letgoBtn);
			waitUntilElementIsClickable(missionStep);
			scrollIntoView(missionStep).click();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getMissionModuleDescriptionMsg() {
		waitUntilThePageLoads();
		waitSeconds(5);
		try {
			return driver.findElement(autoMissionShadow).getShadowRoot().findElement(missionCompleteMsg).getText();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String[] getDailyMissionStepsCount() {
		waitUntilThePageLoads();
		String[] totalCount = driver.findElement(dailyMissionProgressBar).getShadowRoot()
				.findElement(missionProgressBarStatus).getText().split("/");
		return totalCount;
	}

	public String[] getWeeklyMissionStepsCount() {
		waitUntilThePageLoads();
		WebElement progressBar = driver.findElement(weeklyMissionProgressBar).getShadowRoot()
				.findElement(missionProgressBarStatus);
		String StepsCount = progressBar.getText();
		String[] totalCount = StepsCount.split("/");
		return totalCount;
	}

	public boolean verifyAutomationMissionProgressBar() {
		waitUntilThePageLoads();
		waitUntilElementIsVisible(60, autoMissionProgressBar);
		return driver.findElement(autoMissionProgressBar).isDisplayed();
	}

	public boolean verifyAutomationMissionSteps() {
		waitUntilThePageLoads();
		waitUntilElementIsVisible(60, autoMissionProgressBar);
		return driver.findElement(autoMissionShadow).getShadowRoot().findElement(missionModuleSteps).isDisplayed();
	}

	public boolean collapseAutoamtionMissionModule() {
		waitUntilThePageLoads();
		WebElement collapseBtn = driver.findElement(shadowAutoMissionModule).getShadowRoot()
				.findElement(collapseMissionModule);
		waitUntilElementIsClickable(collapseBtn);
		scrollIntoView(collapseBtn).click();
		try {
			return driver.findElement(shadowAutoMissionModule).getShadowRoot().findElement(expandMissionModule)
					.isEnabled();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean navigateToDailyMissionStep() {
		try {
			WebElement missionStep = driver.findElement(dailyMissionShadow).getShadowRoot().findElement(missionSteps)
					.getShadowRoot().findElement(letgoBtn);
			waitUntilElementIsClickable(missionStep);
			scrollIntoView(missionStep).click();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean navigateToWeeklyMissionStep() {
		try {
			WebElement missionStep = driver.findElement(weeklyMissionShadow).getShadowRoot().findElement(missionSteps)
					.getShadowRoot().findElement(letgoBtn);
			waitUntilElementIsClickable(missionStep);
			scrollIntoView(missionStep).click();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}