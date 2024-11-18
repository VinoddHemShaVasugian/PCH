package com.pch.survey.pages.surveytab;

import java.util.List;
/**
 * @author vsankar
 */
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.pch.survey.centralservices.Registrations;
import com.pch.survey.pages.CommonHeadersAndFooters;

public class QOTDPage extends CommonHeadersAndFooters {

	private String mainWindowHandle = null;

	private By incompleteState = By.cssSelector(".check-complete");
	private By completeState = By.cssSelector(".compl-cell");
	private By phpdebugbarClose = By.cssSelector(".phpdebugbar-close-btn");

	private By buttonLetsGo = By.cssSelector(".btn.button");
	private By qotdModule = By.cssSelector("#qotd");
	private By qotdModuleDesc = By.cssSelector(".sub-title-cell");

	private By qotdPrivacyPolicy = By.xpath("//*[text()='Privacy Policy']");
	private By qotdRules = By.xpath("//*[text()='Official Rules']");
	private By qotdSweepstakes = By.xpath("//*[text()='Sweepstake Facts']");
	private By qotdDisclaimerTitle = By.cssSelector(".profile-title");
	private By qotdDisclaimerDesc = By.cssSelector(".profile-subtitle");
	private By qotdSectionHeader = By.xpath("//article/header");
	private By qotdSectionTitle = By.cssSelector(".title-cell");

	private By btnQotdSave = By.xpath("//button[text()='Save']");
	private By btnQotdExit = By.xpath("//button[text()='Exit']");

	private By feedbackPopup = By.cssSelector(".feedback");
	private By feedbackPopupTitle = By.cssSelector(".card-headline");
	private By feedbackPopupSubTitle = By.cssSelector("div.subtitle-1");
	private By feedbackPopupBody = By.cssSelector("div.body-2");
	private By feedbackPopupAcceptBtn = By.xpath("//div[@class='actions']/button[text()='Yes']");
	private By feedbackPopupNoBtn = By.xpath("//div[@class='actions']/button[text()='No']");

	private By exitPopup = By.cssSelector(".sc-modal");
	private By exitPopupAcceptBtn = By.xpath("//button[text()='Yes']");
	private By exitPopupNoBtn = By.xpath("//button[text()='No']");

	private By qapiPrivacyPolicy = By.cssSelector(".match-privacy");
	private By qapiProgressBar = By.cssSelector(".pb-progress span");
	private By qapiTokenAmt = By.cssSelector(".amount span");
	private By qapiCompletePageTokenAmt = By.cssSelector("div.text");
	private By qapiContinueBtn = By.xpath("//button[text()='Continue']");

	private By qotdCreatePasswordLb = By.cssSelector("article.manage-password");
	private By qotdCreatePwdLbTitle = By.cssSelector("h2.card-headline");
	private By qotdCreatePwdLbDesc = By.cssSelector("div.message");
	private By qotdCreatepwdLbExtraTokenAmt = By.cssSelector("div.directions");
	private By qotdCreatePwdLbPasswordField = By.cssSelector("label.password-wrap div");
	private By qotdCreatePwdLbConfirmPasswordField = By.cssSelector("label.confirm-password-wrap div");
	private By qotdCreatePwdLbNoThanksLink = By.cssSelector(".no-thanks a");
	private By qotdCreatePwdLbPrivacyPolicyLink = By.cssSelector(".privacy-policy a");
	private By qotdCreatePwdLbSubmit = By.xpath("//button[text()=' SUBMIT ']");
	private By qotdCreatePwdSuccessLb = By.cssSelector(".create-complete");
	private By qotdCreatePwdSuccessLbTokenAmt = By.cssSelector("h2.card-headline");
	private By qotdCreatePwdSuccessLbMsg = By.cssSelector("div.subtitle-1");
	private By qotdCreatePwdSuccessLbAdditionalText = By.cssSelector("div.body-2");
	private By qotdCreatePwdSuccessLbAcceptBtn = By.xpath("//div[@class='create-complete']//*[text()='Yes']");
	private By qotdCreatePwdSuccessLbNoBtn = By.xpath("//div[@class='create-complete']//*[text()='No']");

	private By qotdGuestSuccessLb = By.xpath("//h2[text()='Entry Earned!']");
	private By qotdGuestSuccessLbAdditionalText = By.cssSelector("div.body-2");

	public QOTDPage(WebDriver driver) {
		super(driver);
		mainWindowHandle = driver.getWindowHandle();
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

	public boolean verifyQotdGuestSuccessLb() {
		return driver.findElement(qotdGuestSuccessLb).isDisplayed();
	}

	public String getQotdGuestSuccessLbAdditionalText() {
		return driver.findElement(qotdGuestSuccessLbAdditionalText).getText();
	}

	public void clickQotdGuestLbContinueBtn() {
		driver.findElement(qapiContinueBtn).click();
	}

	public boolean verifyQotdCreatePasswordLb() {
		return driver.findElement(qotdCreatePasswordLb).isDisplayed();
	}

	public String getQotdCreatePwdLbTitle() {
		return driver.findElement(qotdCreatePwdLbTitle).getText();
	}

	public String getQotdCreatePwdLbDesc() {
		return driver.findElement(qotdCreatePwdLbDesc).getText();
	}

	public String getQotdCreatepwdLbExtraTokenAmt() {
		return driver.findElement(qotdCreatepwdLbExtraTokenAmt).getText();
	}

	public void enterPasswordOnCreatePwdLbPasswordField() {
		waitUntilElementIsClickable(qotdCreatePwdLbPasswordField);
		scrollIntoView(qotdCreatePwdLbPasswordField);
		driver.findElement(qotdCreatePwdLbPasswordField).sendKeys(Registrations.getPassword());
	}

	public void enterPasswordOnCreatePwdLbConfirmPasswordField() {
		waitUntilElementIsClickable(qotdCreatePwdLbConfirmPasswordField);
		scrollIntoView(qotdCreatePwdLbConfirmPasswordField);
		driver.findElement(qotdCreatePwdLbConfirmPasswordField).sendKeys(Registrations.getPassword());
	}

	public void clickQotdCreatePwdLbNoThanksLink() {
		driver.findElement(qotdCreatePwdLbNoThanksLink).click();
	}

	public boolean verifyQotdCreatePwdLbNoThanksLink() {
		return driver.findElement(qotdCreatePwdLbNoThanksLink).isDisplayed();
	}

	public void clickQotdCreatePwdLbPrivacyPolicyLink() {
		driver.findElement(qotdCreatePwdLbPrivacyPolicyLink).click();
	}

	public boolean verifyQotdCreatePwdLbPrivacyPolicyLink() {
		return driver.findElement(qotdCreatePwdLbPrivacyPolicyLink).isDisplayed();
	}

	public void clickQotdCreatePwdLbSubmit() {
		driver.findElement(qotdCreatePwdLbSubmit).click();
	}

	public boolean verifyQotdCreatePwdLbSubmit() {
		return driver.findElement(qotdCreatePwdLbSubmit).isDisplayed();
	}

	public boolean verifyQotdCreatePwdSuccessLb() {
		return driver.findElement(qotdCreatePwdSuccessLb).isDisplayed();
	}

	public String getQotdCreatePwdSuccessLbTokenAmt() {
		return driver.findElement(qotdCreatePwdSuccessLbTokenAmt).getText();
	}

	public String getQotdCreatePwdSuccessLbMsg() {
		return driver.findElement(qotdCreatePwdSuccessLbMsg).getText();
	}

	public String getQotdCreatePwdSuccessLbAdditionalText() {
		return driver.findElement(qotdCreatePwdSuccessLbAdditionalText).getText();
	}

	public void clickQotdCreatePwdSuccessLbAcceptBtn() {
		driver.findElement(qotdCreatePwdSuccessLbAcceptBtn).click();
	}

	public boolean verifyQotdCreatePwdSuccessLbAcceptBtn() {
		return driver.findElement(qotdCreatePwdSuccessLbAcceptBtn).isDisplayed();
	}

	public void clickQotdCreatePwdSuccessLbNoBtn() {
		driver.findElement(qotdCreatePwdSuccessLbNoBtn).click();
	}

	public boolean verifyQotdCreatePwdSuccessLbNoBtn() {
		return driver.findElement(qotdCreatePwdSuccessLbNoBtn).isDisplayed();
	}

	public String getQotdSectionTitle() {
		return driver.findElement(qotdSectionTitle).getText();
	}

	public String getQotdSectionHeader() {
		return driver.findElement(qotdSectionHeader).getText();
	}

	public String getQotdDisclaimerTitle() {
		return driver.findElement(qotdDisclaimerTitle).getText();
	}

	public String getQotdDisclaimerDesc() {
		return driver.findElement(qotdDisclaimerDesc).getText();
	}

	public void clickQotdPrivacyPolicyLink() {
		driver.findElement(qotdPrivacyPolicy).click();
	}

	public boolean verifyQotdPrivacyPolicyLink() {
		return driver.findElement(qotdPrivacyPolicy).isDisplayed();
	}

	public void clickQotdSweepstakesLink() {
		driver.findElement(qotdSweepstakes).click();
	}

	public boolean verifyQotdSweepstakesLink() {
		return driver.findElement(qotdSweepstakes).isDisplayed();
	}

	public void clickQotdRulesLink() {
		driver.findElement(qotdRules).click();
	}

	public boolean verifyQotdRulesLink() {
		return driver.findElement(qotdRules).isDisplayed();
	}

	public String getQapiCompletePageTokenAmt() {
		String tokalToken = driver.findElement(qapiCompletePageTokenAmt).getText().replace(",", "").trim();
		return tokalToken.replaceAll("[^0-9]+", "");
	}

	public void clickQapiCompletePageContinueBtn() {
		driver.findElement(qapiContinueBtn).click();
	}

	public String getQapiTokenAmt() {
		return driver.findElement(qapiTokenAmt).getText().replace(",", "").trim();
	}

	public boolean verifyQapiTokenModule() {
		return driver.findElement(qapiTokenAmt).isDisplayed();
	}

	public String getQapiProgressBarStatus() {
		waitSeconds(3);
		String[] count;
		count = driver.findElement(qapiProgressBar).getText().split("/");
		return count[0].trim();
	}

	public boolean verifyQapiProgressBar() {
		return driver.findElement(qapiProgressBar).isDisplayed();
	}

	public String getQapiTotalQuestionsCount() {
		String[] count;
		count = driver.findElement(qapiProgressBar).getText().split("/");
		return count[1].trim();
	}

	public boolean verifyFeedbackPopupAcceptBtn() {
		return driver.findElement(feedbackPopupAcceptBtn).isDisplayed();
	}

	public boolean verifyFeedbackPopupNoBtn() {
		return driver.findElement(feedbackPopupNoBtn).isDisplayed();
	}

	public void clickFeedbackPopupAcceptBtn() {
		driver.findElement(feedbackPopupAcceptBtn).click();
	}

	public void clickFeedbackPopupNoBtn() {
		driver.findElement(feedbackPopupNoBtn).click();
	}

	public String getFeedbackPopupBody() {
		return driver.findElement(feedbackPopupBody).getText();
	}

	public String getFeedbackPopupTitle() {
		return driver.findElement(feedbackPopupTitle).getText();
	}

	public String getFeedbackPopupSubTitle() {
		return driver.findElement(feedbackPopupSubTitle).getText();
	}

	public boolean verifyExitPopup() {
		WebElement ele = null;
		for (int i = 0; i < 5; i++) {
			try {
				waitUntilElementIsVisible(30, exitPopup);
				ele = driver.findElement(exitPopup);
				break;
			} catch (Exception ex) {
			}
		}
		return ele.isDisplayed();
	}

	public String getExitPopupDesc() {
		return driver.findElement(exitPopup).getText();
	}

	public boolean verifyExitPopupAcceptBtn() {
		return driver.findElement(exitPopupAcceptBtn).isDisplayed();
	}

	public boolean verifyExitPopupNoBtn() {
		return driver.findElement(exitPopupNoBtn).isDisplayed();
	}

	public void clickExitPopupAcceptBtn() {
		driver.findElement(exitPopupAcceptBtn).click();
	}

	public void clickSaveButton() {
		driver.findElement(btnQotdSave).click();
	}

	public void clickExitButton() {
		for (int i = 0; i <= 5; i++) {
			try {
				waitUntilElementIsClickable(btnQotdExit);
				scrollIntoView(btnQotdExit);
				driver.findElement(btnQotdExit).click();
				break;
			} catch (ElementClickInterceptedException ex) {
			}
		}
	}

	public boolean verifySaveButton() {
		return driver.findElement(btnQotdSave).isDisplayed();
	}

	public boolean verifySaveButtonStatus() {
		return driver.findElement(btnQotdSave).isEnabled();
	}

	public boolean verifyExitButton() {
		return driver.findElement(btnQotdExit).isDisplayed();
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public boolean verifyQotdFeedbackPopup() {
		waitUntilElementIsVisible(30, feedbackPopup);
		return driver.findElement(feedbackPopup).isDisplayed();
	}

	public void clickLetsGoButton() {
		waitUntilThePageLoads();
		waitUntilUrlContains("pchsurveys");
		waitUntilElementIsClickable(30, buttonLetsGo);
		driver.findElement(buttonLetsGo).click();
	}

	public boolean verifyQOTDModule() {
		return driver.findElement(qotdModule).isDisplayed();
	}

	public boolean verifyQAPIPage() {
		return driver.findElement(qapiPrivacyPolicy).isDisplayed();
	}

	public void clickQAPIPrivacyPolicy() {
		waitUntilUrlContains("question-set/qotd/offers-qotd");
		driver.findElement(qapiPrivacyPolicy).click();
	}

	public boolean verifyQAPIPrivacyPolicy() {
		return driver.findElement(qapiPrivacyPolicy).isDisplayed();
	}

	public boolean verifyQOTDInitialState() {
		waitUntilThePageLoads();
		waitUntilElementIsVisible(30, buttonLetsGo);
		return driver.findElement(buttonLetsGo).isDisplayed();
	}

	public boolean verifyQOTDIncompleteState() {
		waitUntilThePageLoads();
		waitUntilElementIsVisible(30, incompleteState);
		return driver.findElement(incompleteState).isDisplayed();
	}

	public String getQotdModuleDesc() {
		return driver.findElement(qotdModuleDesc).getText();
	}

	public boolean verifyQOTDCompleteState() {
		waitUntilThePageLoads();
		waitUntilElementIsVisible(30, completeState);
		return driver.findElement(completeState).isDisplayed();
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

	public void answerQAPIQuestion() {
		waitUntilThePageLoads();
		waitSeconds(5);
		List<WebElement> activeQuestions = getElementList(By.cssSelector("div[class='q-wrap']"));
		if (activeQuestions.size() > 0) {
			List<WebElement> select = getElementList(activeQuestions.get(0), By.tagName("select"));
			if (select.size() > 0) {
				Select selectEle = new Select(select.get(0));
				selectEle.selectByIndex(2);
				scrollIntoView(activeQuestions.get(0).findElement(btnQotdSave)).click();
			}
			List<WebElement> checkBoxs = getElementList(activeQuestions.get(0), By.cssSelector(".checkmark"));
			if (checkBoxs.size() > 0) {
				scrollIntoView(checkBoxs.get(checkBoxs.size() - 1));
				checkBoxs.get(checkBoxs.size() - 1).click();
				scrollIntoView(activeQuestions.get(0).findElement(btnQotdSave)).click();
			}
		}
	}

	public void answerQotdQuestion() {
		waitUntilThePageLoads();
		waitSeconds(5);
		List<WebElement> activeQuestions = getElementList(By.cssSelector("div[class='q-wrap font-defaults qotd']"));
		if (activeQuestions.size() > 0) {
			List<WebElement> select = getElementList(activeQuestions.get(0), By.tagName("select"));
			if (select.size() > 0) {
				Select selectEle = new Select(select.get(0));
				selectEle.selectByIndex(2);
				scrollIntoView(activeQuestions.get(0).findElement(btnQotdSave)).click();
				return;
			}
			List<WebElement> checkBoxs = getElementList(activeQuestions.get(0), By.cssSelector(".checkmark"));
			if (checkBoxs.size() > 0) {
				scrollIntoView(checkBoxs.get(checkBoxs.size() - 1));
				checkBoxs.get(checkBoxs.size() - 1).click();
				scrollIntoView(activeQuestions.get(0).findElement(btnQotdSave)).click();
				return;
			}
		}
	}

}
