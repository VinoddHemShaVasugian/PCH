package com.pages.quiz;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebElement;

/**
 * Page objects and methods for Quiz page
 *
 * @author vsankar
 */
public class QuizPage extends PageObject {

	/**
	 * Instantiates a Quiz page.
	 *
	 * @param driver
	 */
	public QuizPage(WebDriver driver) {
		super(driver);
	}

	private final By quizPageHeader = By.xpath("//div[@id='persistent__header-container']");
	private final By answers = By.cssSelector("div.answer");

	/**
	 * Verify quiz page
	 *
	 * @paramnone
	 * @return boolean
	 * @author vsankar
	 */
	public boolean verifyQuizPage() {
		waitABit(2000);
		waitForRenderedElementsToBePresent(quizPageHeader);
		return isElementVisible(quizPageHeader);
	}

	/**
	 * Play quiz in Scroll view
	 *
	 * @author vsankar
	 */
	public void playQuizScrollView() {
		List<WebElementFacade> quizAnswers = findAll(answers);
		for (int i = 0; i < quizAnswers.size(); i = i + 2) {
			if (verifyQuizPage()) {
				WebElement answersPanel=quizAnswers.get(i);
				System.out.println("answersPanel;-->"+answersPanel);
				((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", answersPanel);
				((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0,-200)");
				quizAnswers.get(i).click();
			}
			if (i >= quizAnswers.size()) {
				break;
			}
		}
	}

	/**
	 * Play quiz in Scroll view upto defined level of questions
	 *
	 * @author vsankar
	 */
	public void playLimitedQuizScrollView(int questionNumber) {
		List<WebElementFacade> quizAnswers = findAll(answers);
		for (int i = 0; i < quizAnswers.size(); i = i + 2) {
			if (verifyQuizPage()) {
				WebElement answersPanel=quizAnswers.get(i);
				System.out.println("answersPanel;-->"+answersPanel);
				((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView();", answersPanel);
				((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0,-200)");
				quizAnswers.get(i).click();
			}
			if (i >= quizAnswers.size() || i >= questionNumber * 2) {
				break;
			}
		}
	}
	
	/**
	 * Verify Inline ad between questions
	 *
	 * @return True
	 * @author vsankar
	 */
	public boolean verifyInlineAdBetweenQuestions(int slotNumber) {
		waitForRenderedElementsToBePresent(By.cssSelector("div#div-pch-placement-between" + slotNumber + "> div>iframe"));
		return isElementVisible(By.cssSelector("div#div-pch-placement-between" + slotNumber + "> div>iframe"));
	}

	/**
	 * Play quiz in scroll view and verify Inline ad between questions
	 *
	 * @return True
	 * @author vsankar
	 */
	public boolean playQuizScrollViewAndVerifyInlineAds() {
		boolean inlineAd = false;
		List<WebElementFacade> quizAnswers = findAll(answers);
		for (int i = 1; i <= quizAnswers.size(); i = i + 2) {
			if (verifyQuizPage()) {
				waitABit(1000);
				quizAnswers.get(i).click();
				if ((i > 2) && i < quizAnswers.size()) {
					System.out.println("Print i="+i);
					inlineAd = verifyInlineAdBetweenQuestions(i-1);
				}
			}
			if (i >= quizAnswers.size()) {
				break;
			}
		}
		System.out.println("Testing - playQuizScrollViewAndVerifyInlineAds");
		return inlineAd;
	}

}