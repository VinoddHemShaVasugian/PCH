package com.pch.survey.pages.profiles;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.pch.survey.pages.PageObject;

public class BasicProfilePage extends PageObject {

	private static int questionsAnswered = 0;
	private By yesButton = By.xpath("//button[normalize-space()='Yes']");
	private By noButton = By.xpath("//button[normalize-space()='No']");
	private By consentSubmitted = By.cssSelector(".prf-match");
	private By consentForm = By.cssSelector(".prf-consent");

	public BasicProfilePage(WebDriver driver) {
		super(driver);
	}

	public BasicProfilePage() {
		super();
	}

	public void selectConsentYes() {
		List<WebElement> prfConsent = getElementList(consentForm);
		if (prfConsent.size() > 0) {

			waitUntilElementIsVisible(60, yesButton);
			scrollIntoView(yesButton);
			WebElement btn = driver.findElement(yesButton);

			try {
				btn.click();
			} catch (Exception e) {
				waitUntilElementIsClickable(btn);
				btn.click();
			}
			waitUntilElementIsVisible(60, consentSubmitted);
		}
	}

	public void selectConsentNo() {
		List<WebElement> prfConsent = getElementList(consentForm);
		if (prfConsent.size() > 0) {
			for (int i = 0; i <= 5; i++) {
				try {
					waitSeconds(3);
					scrollIntoView(noButton);
					waitUntilElementIsClickable(noButton);
					driver.findElement(noButton).click();
					break;
				} catch (ElementClickInterceptedException ex) {
				}
			}
		}
	}

	public void answerAllProfileQuestions() {
		questionsAnswered = 0;
		while (true) {
			selectConsentYes();

			waitSeconds(1);
			// form-control profile-question__control

			// survey questions css
			List<WebElement> selectQuestions = getElementList(
					By.cssSelector(".form-control.profile-question__control"));
			if (selectQuestions.size() > 0) {
				for (WebElement selectEle : selectQuestions) {
					if (selectEle.isEnabled()) {
						Select select = new Select(selectEle);
						select.selectByIndex(select.getOptions().size() - 1);
						questionsAnswered = questionsAnswered + 1;
					}
				}
				List<WebElement> nextSteps = getElementList(By.cssSelector(".next-steps"));
				if (nextSteps.size() > 0) {
					WebElement butt;
					try {
						waitUntilElementIsVisible(30, By.tagName("button"));
						butt = nextSteps.get(0).findElement(By.tagName("button"));
					} catch (StaleElementReferenceException stale) {
						butt = nextSteps.get(0).findElement(By.tagName("button"));
					}

					if (butt.isEnabled()) {
						scrollIntoView(butt);
						butt.click();
					}
				}
			}

			List<WebElement> checkBoxInputs = getElementList(By.cssSelector(".checkmark"));
			if (checkBoxInputs.size() > 0) {
				scrollIntoView(checkBoxInputs.get(checkBoxInputs.size() - 1));
				checkBoxInputs.get(checkBoxInputs.size() - 1).click();
				questionsAnswered = questionsAnswered + 1;
				List<WebElement> nextSteps = getElementList(By.cssSelector(".next-steps"));
				if (nextSteps.size() > 0) {
					WebElement butt = nextSteps.get(0).findElement(By.tagName("button"));
					if (butt.isEnabled())
						butt.click();
				}
			}
			List<WebElement> review = getElementList(By.cssSelector(".review"));
			if (review.size() > 0) {
				WebElement ele;
				try {
					ele = review.get(0).findElement(By.cssSelector(".pbtn"));
					waitUntilElementIsClickable(ele);
					ele.click();
					break;
				} catch (ElementClickInterceptedException eci) {
					ele = review.get(0).findElement(By.cssSelector(".pbtn"));
					scrollIntoView(ele);
					ele.click();
					break;
				} catch (Exception e) {
					waitUntilElementIsVisible(30, By.cssSelector(".pbtn"));
					review.get(0).findElement(By.cssSelector(".pbtn")).click();
					break;
				}
			}

			if (selectQuestions.size() == 0 && checkBoxInputs.size() == 0)
				break;
		}

	}

	public void answerNonSensitiveQuestions() {
		questionsAnswered = 0;
		while (true) {
			selectConsentNo();
			waitSeconds(1);
			List<WebElement> selectQuestions = getElementList(
					By.cssSelector(".form-control.profile-question__control"));
			if (selectQuestions.size() > 0) {
				for (WebElement selectEle : selectQuestions) {
					if (selectEle.isEnabled()) {
						Select select = new Select(selectEle);
						select.selectByIndex(select.getOptions().size() - 1);
						questionsAnswered = questionsAnswered + 1;
					}
				}
				List<WebElement> nextSteps = getElementList(By.cssSelector(".next-steps"));
				if (nextSteps.size() > 0) {
					WebElement butt;
					try {
						waitUntilElementIsVisible(30, By.tagName("button"));
						butt = nextSteps.get(0).findElement(By.tagName("button"));
					} catch (StaleElementReferenceException stale) {
						butt = nextSteps.get(0).findElement(By.tagName("button"));
					}

					if (butt.isEnabled()) {
						scrollIntoView(butt);
						butt.click();
					}
				}
			}

			List<WebElement> checkBoxInputs = getElementList(By.cssSelector(".checkmark"));
			if (checkBoxInputs.size() > 0) {
				scrollIntoView(checkBoxInputs.get(checkBoxInputs.size() - 1));
				checkBoxInputs.get(checkBoxInputs.size() - 1).click();
				questionsAnswered = questionsAnswered + 1;
				List<WebElement> nextSteps = getElementList(By.cssSelector(".next-steps"));
				if (nextSteps.size() > 0) {
					WebElement butt = nextSteps.get(0).findElement(By.tagName("button"));
					if (butt.isEnabled())
						butt.click();
				}
			}
			List<WebElement> review = getElementList(By.cssSelector(".review"));
			if (review.size() > 0) {
				WebElement ele;
				try {
					ele = review.get(0).findElement(By.cssSelector(".pbtn"));
					waitUntilElementIsClickable(ele);
					ele.click();
					break;
				} catch (ElementClickInterceptedException eci) {
					ele = review.get(0).findElement(By.cssSelector(".pbtn"));
					scrollIntoView(ele);
					ele.click();
					break;
				} catch (Exception e) {
					waitUntilElementIsVisible(30, By.cssSelector(".pbtn"));
					review.get(0).findElement(By.cssSelector(".pbtn")).click();
					break;
				}
			}

			if (selectQuestions.size() == 0 && checkBoxInputs.size() == 0)
				break;
		}

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
		try {
			waitUntilElementIsVisible(30, By.cssSelector("button[class='yes-btn']"));
			scrollIntoView(driver.findElement(By.cssSelector("button[class='yes-btn']"))).click();
		} catch (Exception e) {

		}
		waitSeconds(5);

		List<WebElement> sensitiveQuestions = getElementList(
				By.cssSelector("div[class^='profile-question sensitive']"));
		if (sensitiveQuestions.size() > 0) {
			for (int y = 0; y < sensitiveQuestions.size(); y++) {
				List<WebElement> select = sensitiveQuestions.get(y).findElements(By.tagName("select"));
				if (!select.get(0).isEnabled())
					continue;
				scrollIntoView(select.get(0));
				if (select.size() > 0) {
					Select selectEle = new Select(select.get(0));
					selectEle.selectByIndex(1);
				}
			}
			scrollIntoView(driver.findElement(By.cssSelector("button[class='profile-button	 list']"))).click();
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
	}
}
