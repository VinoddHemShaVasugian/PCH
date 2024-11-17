/*
*
@auther - rkhan
@date   - 28/5/2020
*
*/
package com.pages.quiz;
import com.pch.quiz.utilities.AppConfigLoader;
import com.pchengineering.billmecontestentries.databaseentries.ContestEntriesHelper;
import com.pchengineering.billmecontestentries.databasehelper.UserEntries;
import com.pch.quiz.utilities.WaitHelper;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class AcquisitionQuizPage extends PageObject {

	@Steps
	ArticlePage articlePage;
	//RegistrationRequestGenerator registrationRequestGenerator;
	RegistrationPage registrationPage;
	private By quizArray = new By.ByXPath("//div[@class='quiz_page']");
	private By claimNow = new By.ByXPath("//a[@class='claim-btn']");
	private By clickOnQuizBtn = new By.ByXPath("//button[@class='qbutton'][contains(.,'Take The Quiz')])[1]");
	private By signUpBtn = new By.ByXPath("//button[contains(text(),'Sign Up')]");
	private By Webarray1 = new By.ByXPath("//div[@class='cmp-quiz-desktop-trivia-answers grid']");
	private By answers = new By.ByXPath("//div[@class='answer']");
	private By entries = new By.ByXPath("//dl[@class='score-counter score-entries']//dd[@class='amount']");
	private By tokens = new By.ByXPath("//dl[@class='score-counter score-tokens']//dd[@class='amount']");
	private By ClaimNowBtn = new By.ByXPath("//button[@class='qbutton']");
	private final By acqPersistentBanner = new By.ByXPath("//div[@class='cmp-prize-banner']");
	private By regFormHeaderTitle = new By.ByXPath("//div[@class='regform__header-title']");
	private By spectrumPathTitle = new By.ByXPath("//p[contains(text(),'You Could Become Our Next Big Winner!')]");
	private By spectrumExitPathDefaultPromotionQA = new By.ByXPath("//div[@class='FPdoLc tfB0Bf']//input[@name='btnK']");
	private By spectrumExitPathDefaultPromotionSTG =new By.ByCssSelector("a.uninav__logo");
	private By spectrumPathNewPromotionSTG = new By.ByXPath("//a[@class='logo']//*[local-name()='svg']");
	private By spectrumPathNewPromotionQA = new By.ByXPath("//div[@class='lfloat _ohe']//h1//a");
	private By tokenHistoryTitle = new By.ByXPath("//a[contains(text(),'Token History')]");
	private By takeTheQuiz = new By.ByXPath("//article[1]//footer[1]//a[1]//button[1]");
	private By quizcompAndRegVerbiage = new By.ByXPath("//div[contains(text(),'Quiz Completion & Registration')]");
	private By quizcompAndRegTokens = new By.ByXPath("//span[contains(text(),'+10,000')]");
	private By quizcompAndRegTokensHalfwayMark = new By.ByXPath("//span[contains(text(),'+5,000')]");
	private By entryConfirmationBtn = new By.ByName("Continue");
	public static String uni_email;
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();
	private final String fullpath = "fullPath";
	private final String halfwaymark = "halfwayPath";
	private final String qapromotionSite = "facebook";
	private final String stgpromotionSite = "flatiron";
	private final String qadefaultPromotionSite = "google";
	private final String stgdefaultPromotionSite = "yahoo";
	private String promotionFromArticle = articlePage.promotionFromArticle;
	private String spectrumUrlFromArticle = articlePage.specturmURLFromArticle;
	private String contestKeyFullPathFromArticle = articlePage.contestKeyFullPathFromArticle;
	private String contestKeyHalfWayMarkFromArticle = articlePage.contestKeyHalfPathFromArticle;
	public String expectedFullPathContestKey;
	public String expectedEntriesFullPath;
	public String expectedHalfwayPathEntries;
	public String expectedEntriesHalfPath;
	public String expectedFullPathContestKeyDefaultPromotion;
	public String expectedHalfwaymarkContestKey;
	public String expectedHalfwaymarkContestKeyDefaultPromotion;
	public String expectedVerb;
	public String expectedFullPathTokens;
	//public String expectedHalfwayPathTokens;
	public String expectedHalfwayPathTokens;
	public String qapromotion;
	public String stgpromotion;
	public String defPromotion;


	public AcquisitionQuizPage(WebDriver driver) {
		super(driver);
	}


	//Go To Quizzes Acq Path
	public void gotoQuizzesAcqPath() throws URISyntaxException, InterruptedException, IOException {
		element(takeTheQuiz).click();
		WaitHelper.forceWait(1000);
		String url = getDriver().getCurrentUrl();
		String acqUrl;
		this.getQAPromotion(configInstance.getAcquisitionProperty("QAPromotion"));
		this.getStgPromotion(configInstance.getAcquisitionProperty("STGPromotion"));
		this.getDefPromotion(configInstance.getAcquisitionProperty("DefaultPromotion"));
		if (promotionFromArticle.trim().equalsIgnoreCase(qapromotion.trim())||promotionFromArticle.trim().equalsIgnoreCase(stgpromotion.trim())) {
			acqUrl = url + "/" + "?" + "acq=1" + "&" + "utm_campaign=abcd-" + promotionFromArticle.trim();
			getDriver().get(acqUrl);
			WaitHelper.forceWait(300);
		} else {
			acqUrl = url + "/" + "?" + "acq=1" + "&" + "utm_campaign=1234-" + promotionFromArticle.trim();
			getDriver().get(acqUrl);
			WaitHelper.forceWait(300);
		}
		WaitHelper.forceWait(300);
	}


	public void getQAPromotion(String promotionqa) { qapromotion = promotionqa; }
	public void getStgPromotion(String promotionstg) { stgpromotion = promotionstg; }
	public void getDefPromotion(String promotiondef) { defPromotion = promotiondef; }

	public boolean isClaimNowAppearsOnQuizPage() {
		WaitHelper.waitForVisibliyOfElement(getDriver(), claimNow);
		return element(claimNow).isDisplayed();
	}

	public void landOnRegPageAfterHalfwayPathCompletion() throws InterruptedException, IOException {
		getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		clickOn(element(claimNow));
		waitForRenderedElementsToBePresent(regFormHeaderTitle);
		Assert.assertEquals(true, element(regFormHeaderTitle).isDisplayed());
	}

	public void isClaimNowButtonDisplayOnGOS() {
		WaitHelper.waitForVisibliyOfElement(getDriver(), claimNow);
		Assert.assertEquals(true, element(claimNow).isDisplayed());
	}

	public void persistentBannerisDisplay() {
		waitABit(2000);
		waitForRenderedElementsToBePresent(acqPersistentBanner);
		Assert.assertEquals(true, element(acqPersistentBanner).isDisplayed());
	}

	public void isSpectrumPathDisplay() {
		if (promotionFromArticle.trim().equalsIgnoreCase(qapromotion.trim())||promotionFromArticle.trim().equalsIgnoreCase(stgpromotion.trim())) {
			if (spectrumUrlFromArticle.contains(qapromotionSite)) {
				WaitHelper.waitForVisibliyOfElement(getDriver(), spectrumPathNewPromotionQA);
				Assert.assertEquals(true, element(spectrumPathNewPromotionQA).isDisplayed());
				getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
				WaitHelper.waitForVisibliyOfElement(getDriver(), tokenHistoryTitle);
			} else if (spectrumUrlFromArticle.contains(stgpromotionSite)) {
				WaitHelper.waitForVisibliyOfElement(getDriver(), spectrumPathNewPromotionSTG);
				Assert.assertEquals(true, element(spectrumPathNewPromotionSTG).isDisplayed());
				getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
				WaitHelper.waitForVisibliyOfElement(getDriver(), tokenHistoryTitle);
			}

		}
		if(!(promotionFromArticle.trim().equalsIgnoreCase(qapromotion.trim())||promotionFromArticle.trim().equalsIgnoreCase(stgpromotion.trim()))) {
			if (spectrumUrlFromArticle.contains(qadefaultPromotionSite)) {
				WaitHelper.waitForVisibliyOfElement(getDriver(), spectrumExitPathDefaultPromotionQA);
				Assert.assertEquals(true, element(spectrumExitPathDefaultPromotionQA).isDisplayed());
				getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
				WaitHelper.waitForVisibliyOfElement(getDriver(), tokenHistoryTitle);
			} else if (spectrumUrlFromArticle.contains(stgdefaultPromotionSite)) {
				WaitHelper.waitForVisibliyOfElement(getDriver(), spectrumExitPathDefaultPromotionSTG);
				Assert.assertEquals(true, element(spectrumExitPathDefaultPromotionSTG).isDisplayed());
				getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
				WaitHelper.waitForVisibliyOfElement(getDriver(), tokenHistoryTitle);
			}
		}

	}
	public void fullPathRegistrationTokensVerification(String path) throws InterruptedException, IOException {
		String verb;
		String verbTokens;
		switch (path) {
			case fullpath:
				WaitHelper.waitForVisibliyOfElement(getDriver(), tokenHistoryTitle);
				element(tokenHistoryTitle).click();
				WaitHelper.waitForVisibliyOfElement(getDriver(), quizcompAndRegVerbiage);
				verb = element(quizcompAndRegVerbiage).getText();
				verbTokens = element(quizcompAndRegTokens).getText();
				WaitHelper.forceWait(1000);
				this.getVerbiage(configInstance.getAcquisitionProperty("Verbiage"));
				Assert.assertEquals(expectedVerb, verb);
				this.getFullPathTokens(configInstance.getAcquisitionProperty("TokensFullpath"));
				String verbiageTokens[] = verbTokens.split("\\+");
				System.out.println("verbTokens---->" + verbiageTokens[1]);
				WaitHelper.forceWait(3000);
				Assert.assertEquals(expectedFullPathTokens, verbiageTokens[1]);
				break;
			default:
				WaitHelper.waitForVisibliyOfElement(getDriver(), tokenHistoryTitle);
				element(tokenHistoryTitle).click();
				WaitHelper.waitForVisibliyOfElement(getDriver(), quizcompAndRegVerbiage);
				verb = element(quizcompAndRegVerbiage).getText();
				verbTokens = element(quizcompAndRegTokensHalfwayMark).getText();
				WaitHelper.forceWait(1000);
				this.getVerbiage(configInstance.getAcquisitionProperty("Verbiage"));
				//System.out.println("expectedVerb---->" + expectedVerb);
				Assert.assertEquals(expectedVerb, verb);
				this.getHalfPathTokens(configInstance.getAcquisitionProperty("TokensHalfway"));
				//System.out.println("ExpectedTokensHalfway---->" + expectedHalfwayTokens);
				String verbiageTokensHalfwayMark[] = verbTokens.split("\\+");
				//System.out.println("verbTokens---->" + verbiageTokensHalfwayMark[1]);
				WaitHelper.forceWait(3000);
				Assert.assertEquals(expectedHalfwayPathTokens, verbiageTokensHalfwayMark[1]);
				break;
		}
	}

	public void getVerbiage(String expectedVerbiage) {
		expectedVerb = expectedVerbiage;

	}

	public void getFullPathTokens(String expectedTokens) {
		expectedFullPathTokens = expectedTokens;
	}
	public void getHalfPathTokens(String expectedTokens) {
		expectedHalfwayPathTokens = expectedTokens;
	}

	public void landOnRegPageAfterPathCompletion() throws InterruptedException {
		getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, -700)", "");
		WaitHelper.waitForVisibliyOfElement(getDriver(), ClaimNowBtn);
		element(ClaimNowBtn).click();
		WaitHelper.waitForVisibliyOfElement(getDriver(), regFormHeaderTitle);
		Assert.assertEquals(true, element(regFormHeaderTitle).isDisplayed());
	}

	public void fullRegistration() {
		uni_email = registrationPage.acqRregisterUserWithOptins();
		System.out.println("email--->" + uni_email);
	}


	public void fullPathCompletion() throws InterruptedException, IOException {
		List<WebElementFacade> quizAnswers = findAll(answers);
		for (int i = 0; i < quizAnswers.size(); i = i + 2) {
			System.out.println("Quiz--->" + "i" + i + element(answers).findElements(answers).get(i).getText());
			quizAnswers.get(i).click();
			if (i == quizAnswers.size())
				break;
		}
		WaitHelper.waitForVisibliyOfElement(getDriver(), ClaimNowBtn);
		validatePathCompletionEntriesAndTokens();
	}

	public void halfWayPath() throws InterruptedException, IOException {
		List<WebElementFacade> quizAnswers = findAll(answers);
		for (int i = 0; i < quizAnswers.size(); i = i + 2) {
			System.out.println("Quiz--->" + "i" + i + element(answers).findElements(answers).get(i).getText());
			quizAnswers.get(i).click();
			if (i == (quizAnswers.size() / 2) - 2)
				break;
		}
		validateHalfwayMarkEntriesAndTokens();
	}

	public void quizPathCompletion(String userPath) throws InterruptedException, IOException {
		switch (userPath) {
			case fullpath:
				fullPathCompletion();
				break;
			default:
				halfWayPath();
				break;
		}
	}


	public void validateHalfwayMarkEntriesAndTokens() throws IOException {
		this.getHalfPathEntries(configInstance.getAcquisitionProperty("EntriesHalfway"));
		System.out.println("ExpectedEntriesHalfway---->" + expectedHalfwayPathEntries);
		this.getHalfPathTokens(configInstance.getAcquisitionProperty("TokensHalfway"));
		System.out.println("ExpectedTokensHalfway---->" + expectedHalfwayPathTokens);
		System.out.println("entries.getText()--->" + element(entries).getText());
		System.out.println("tokens.getText()--->" + element(tokens).getText());
		String actualEntries = element(entries).getText();
		String actualTokens = element(tokens).getText();
		Assert.assertEquals(expectedHalfwayPathEntries, actualEntries);
		Assert.assertEquals(expectedHalfwayPathTokens, actualTokens);
		WaitHelper.waitForVisibliyOfElement(getDriver(), claimNow);
	}


	public void validatePathCompletionEntriesAndTokens() throws IOException {
		this.getFullPathEntries(configInstance.getAcquisitionProperty("EntriesFullpath"));
		System.out.println("ExpectedEntriesFullpath---->" + expectedEntriesFullPath);
		this.getFullPathTokens(configInstance.getAcquisitionProperty("TokensFullpath"));
		System.out.println("expectedFullPathTokens---->" + expectedFullPathTokens);
		System.out.println("entries.getText()--->" + element(entries).getText());
		System.out.println("tokens.getText()--->" + element(tokens).getText());
		String actualEntries = element(entries).getText();
		String actualTokens = element(tokens).getText();
		Assert.assertEquals(expectedEntriesFullPath, actualEntries);
		Assert.assertEquals(expectedFullPathTokens, actualTokens);
		getDriver().manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);

	}

	public void verifyContestEntriesInOAM(String path) throws InterruptedException {
		if (path.equalsIgnoreCase(fullpath)) {
			if(promotionFromArticle.toLowerCase().trim().equalsIgnoreCase(qapromotion.toLowerCase().trim())) {
				verifyContestEntriesForAcquisitionUser(contestKeyFullPathFromArticle);}
			if (promotionFromArticle.toLowerCase().trim().equalsIgnoreCase(stgpromotion.toLowerCase().trim())){
				verifyContestEntriesForAcquisitionUser(contestKeyFullPathFromArticle);
			}
			if(!(promotionFromArticle.toLowerCase().trim().equalsIgnoreCase(qapromotion.toLowerCase().trim()))) {
				verifyContestEntriesForAcquisitionUser(contestKeyFullPathFromArticle);
			}
			if (!(promotionFromArticle.toLowerCase().trim().equalsIgnoreCase(stgpromotion.toLowerCase().trim()))){
				verifyContestEntriesForAcquisitionUser(contestKeyFullPathFromArticle);
			}
		}
		else if (path.equalsIgnoreCase(halfwaymark)) {
			if(promotionFromArticle.toLowerCase().trim().equalsIgnoreCase(qapromotion.toLowerCase().trim())) {
				verifyContestEntriesForAcquisitionUser(contestKeyHalfWayMarkFromArticle);}
			if (promotionFromArticle.toLowerCase().trim().equalsIgnoreCase(stgpromotion.toLowerCase().trim())){
				verifyContestEntriesForAcquisitionUser(contestKeyHalfWayMarkFromArticle);
			}
			if(!(promotionFromArticle.toLowerCase().trim().equalsIgnoreCase(qapromotion.toLowerCase().trim()))) {
				verifyContestEntriesForAcquisitionUser(contestKeyHalfWayMarkFromArticle);
			}
			if (!(promotionFromArticle.toLowerCase().trim().equalsIgnoreCase(stgpromotion.toLowerCase().trim()))){
				verifyContestEntriesForAcquisitionUser(contestKeyHalfWayMarkFromArticle);}
		}
	}

	public void verifyContestEntriesForAcquisitionUser(String expectedContestEntry) {
		if (AppConfigLoader.env.equalsIgnoreCase("QA")) {
			List<UserEntries> userEntry = ContestEntriesHelper.getContestEntriesByEmailQA(uni_email);
			boolean ck = false;
			for (UserEntries ue : userEntry) {
				if (ue.getContestKey().trim().equalsIgnoreCase(expectedContestEntry)) {
					ck = true;
					break;
				}
			}
			if (!ck)
				Assert.assertTrue("Given contest entry is not awarded to the user", false);
		} else {
			List<UserEntries> userEntry = ContestEntriesHelper.getContestEntriesByEmailSTG(uni_email);
			boolean ck = false;
			for (UserEntries ue : userEntry) {
				if (ue.getContestKey().trim().equalsIgnoreCase(expectedContestEntry)) {
					ck = true;
					break;
				}
			}
			if (!ck)
				Assert.assertTrue("Given contest entry is not awarded to the user", false);
		}
	}
	public void getFullPathEntries(String ExpectedEntriesFullPath){
		expectedEntriesFullPath = ExpectedEntriesFullPath;
	}
	public void getHalfPathEntries(String ExpectedEntriesHalfPath){
		expectedHalfwayPathEntries = ExpectedEntriesHalfPath;
	}
}




