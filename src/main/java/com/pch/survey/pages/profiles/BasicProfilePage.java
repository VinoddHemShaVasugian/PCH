package com.pch.survey.pages.profiles;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.pch.survey.pages.PageObject;

public class BasicProfilePage extends PageObject {

	private static int questionsAnswered = 0;

	public BasicProfilePage(WebDriver driver) {
		super(driver);
	}

	public BasicProfilePage() {
		super();
	}

	public void answerCurrentProfileQuestion() {
		waitUntilThePageLoads();
		List<WebElement> activeQuestions = getElementList(By.cssSelector("div[class='profile-question active']"));
		if (activeQuestions.size() > 0) {
			List<WebElement> select = getElementList(activeQuestions.get(0), By.tagName("select"));
			if (select.size() > 0) {
				Select selectEle = new Select(select.get(0));
				selectEle.selectByIndex(2);
				activeQuestions.get(0).findElement(By.tagName("button")).click();
				return;
			}
			List<WebElement> checkBoxs = getElementList(activeQuestions.get(0), By.cssSelector(".checkmark"));
			if (checkBoxs.size() > 0) {
				scrollIntoView(checkBoxs.get(checkBoxs.size() - 1));
				checkBoxs.get(checkBoxs.size() - 1).click();
				scrollIntoView(activeQuestions.get(0).findElement(By.tagName("button"))).click();
				return;
			}
		}
		waitSeconds(10);
		waitUntilElementIsVisible(30, By.cssSelector("button[class='yes-btn']"));
		scrollIntoView(driver.findElement(By.cssSelector("button[class='yes-btn']"))).click();
		;
		waitSeconds(5);

		List<WebElement> sensitiveQuestions = getElementList(
				By.cssSelector("div[class^='profile-question sensitive']"));
		if (sensitiveQuestions.size() > 0) {
			for (int y = 0; y < sensitiveQuestions.size(); y++) {
				List<WebElement> select = sensitiveQuestions.get(y).findElements(By.tagName("select"));
				if(!select.get(0).isEnabled())
					continue;
				scrollIntoView(select.get(0));
				if (select.size() > 0) {
					Select selectEle = new Select(select.get(0));
					selectEle.selectByIndex(1);
				}
			}
			scrollIntoView(driver.findElement(By.cssSelector("button[class='list profile-button']"))).click();
		}
	}

	public void answerAllProfileQuestions() {
		questionsAnswered = 0;
		while (true) {
			waitSeconds(1);
			List<WebElement> builderQuestions = getElementList(By.cssSelector("form[class='profile-builder basic']"));
			if (builderQuestions.size() > 0) {
				try {
					answerCurrentProfileQuestion();
				} catch (Exception e) {
					System.out.println("******************** EXCEPTION ANSWERING BASIC PROFILE QUESTIONS ");	
					break;
				}
				questionsAnswered = questionsAnswered + 1;
			} else if (driver.findElements(By.id("data-collection")).size() > 0) {
				answerCurrentProfileQuestion();
				questionsAnswered = questionsAnswered + 1;
			} else
				break;
		}

	}

	public void clickNoButton() {
		driver.findElement(By.cssSelector(".no-btn")).click();
	}

	public String verifyAnsweredQuestionCount() {
		waitSeconds(1);
		return driver.findElement(By.cssSelector(".progress-wrapper")).getText();
	}

	public void agreeDataColection() {
		waitUntilThePageLoads();
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		;
	}
}
