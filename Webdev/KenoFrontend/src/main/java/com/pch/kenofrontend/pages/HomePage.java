package com.pch.kenofrontend.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.validation.constraints.AssertFalse;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;

import toolbox.JavaScriptReusablePage;

import com.pch.kenofrontend.utilities.PropertiesReader;
import com.pch.kenofrontend.utilities.WaitHelper;

import net.serenitybdd.core.pages.PageObject;

import com.pchengineering.registrations.RegistrationRequestGenerator;
import com.pchengineering.registrations.RequestDefaultsOverride;

public class HomePage extends PageObject {
	// Initialize the Pages
	SignInPage signInPage;
	Choice_SFL csfl;
	WaitPage waitPage;
	RegistrationPage rp;
	JavaScriptReusablePage jsp;

	// Initialize the Page Objects using By Class
	private By Register_btn = new By.ByCssSelector(
			"a.uninav__registration.register-btn");
	private By SignIn_btn = new By.ByLinkText("Sign In");
	// private By QuickPick_btn = new
	// By.ByClassName("card__controls__btn--quick");
	private By QuickPick_btn = new By.ByXPath(
			"//button[contains(text(),'Quick Pick')]");
	private By Submit_btn = new By.ByClassName("card__controls__btn--submit");
	private By Clear_btn = new By.ByClassName("card__controls__btn--clear");
	private By SignOut_link = new By.ByLinkText("Sign Out");
	private By PlayKeno_link = new By.ByLinkText("PLAY KENO!");
	private By Playnow_btn = new By.ByXPath("//button[contains(.,'Play Now!')]");

	private By Playnowbtn_frame = new By.ByXPath(
			"//div[@class='play_now_button']//a[@href='#']");
	private By RevealAll_btn = new By.ByXPath("//a[@id='reveal-all']");
	private By Continue_btn = new By.ByXPath("//span[text()='Continue']");
	private By Keepgoing_btn = new By.ByXPath(
			"//button[contains(text(),'Keep Going!')]");
	private By GameCount = new By.ByXPath(
			"//div[@class='pathgame__game-progress-numbers']");
	private By BonusReward_btn = new By.ByClassName("btn--gold");
	// private By CardTabNum = new
	// By.ByXPath("//span[contains(@class,'card-tab__num')][contains(text(),'02')]");
	// private By CardTabStatus = new
	// By.ByXPath("//div[contains(@class,'card-tab--unlocking-progress card-tab--unlocking-out card-tab--unlocked')]//div//p[contains(@class,'card-tab__status')][contains(text(),'UNLOCKED')]");
	private By CardTabStatus = new By.ByCssSelector(
			"div.card-tab--unlocking-progress > div > div.card-tab__unlocked > p.card-tab__status");
	private By CardTabNum = new By.ByCssSelector(
			"div.card-tab--unlocking-progress > div > span");

	private By GameFrame = new By.ByXPath("//div[@id='htmlgame_frame_div']");
	public By Drawn_Clock = By
			.cssSelector("div.kenobar.kenobar--live > div.kenobar__body.container > div.kenobar__clock > div.clock.clock--countdown > div.clock__time > span[class='clock__time__header'][style='visibility: hidden;']");
	public By liveDrawing = By.linkText("Live Drawing");
	private By DrawnNumbers = new By.ByCssSelector(
			"section#drawing__number-pad > div.drawing__ball.drawing__ball--drop > span");
	public By home = By.linkText("Home");
	// Changes for Results page diwresultstokens.story
	private By Results_btn = new By.ByLinkText("Results");
	private By coaching_screen = new By.ByCssSelector(".coachingContainer");
	private By selectnumbers = new By.ByCssSelector(".card__number__label");
	// Changes for Login story
	private By welcometext = new By.ByCssSelector(
			".uninav__top-bar__credentials");
	private By tokenHistory = new By.ByLinkText("Token History");
	private By myAccountLink = new By.ByLinkText("My Account");
	private By tutorialsBtn = new By.ByXPath(
			"//button[contains(text(),'How It Works')]");
	private By tutorialsNextBtn = new By.ByXPath(
			"//button[contains(text(),'Next')]");
	private By tutorialsPreviousBtn = new By.ByXPath(
			"//button[contains(text(),'Previous')]");
	// private By tutorialsCloseBtn = new
	// By.ByCssSelector("div.modal.fade.tutorial.in > div > div > div.modal-header > button");
	private By tutorialsCloseBtn = new By.ByCssSelector(
			"div.modal.fade.tutorial.in > div > div > div.modal-header > button");
	private By tutorialScreenHeader = new By.ByCssSelector(
			".tutorial__screen__header");
	private By tutorialImage = new By.ByCssSelector(".tutorial__screen__img");
	private By completeRegBtn = new By.ByLinkText("Complete Registration");

	// Changes for VIP
	// private By VIP_close_btn = new By.ByClassName("vip-message__close");
	private By VIP_close_btn = new By.ByCssSelector(".vip-message__close");
	private By OPTIN_light_box = new By.ByClassName("modal-content pre-opt");
	// private By OPTIN_light_box = new
	// By.ByXPath("//div[@class='modal-content pre-opt']//span[@aria-hidden='true'][contains(text(),'×')]");
	private By OPTIN_close_button = new By.ByCssSelector(
			"div.emailoptin > div.modal-dialog > div>.modal-content > div.modal-header > button.close");
	private By Optin_signup_button = new By.ByXPath(
			"//button[contains(text(),'Sign Up Now!')]");
	private By Optin_thankYou_message = new By.ByXPath(
			"//h4[contains(text(),\"We're So Glad You Signed Up!\")]");
	private By univnav = new By.ByCssSelector(".uninav__carousel");
	private By univnav_properties = new By.ByXPath(
			"//*[@class='uninav__carousel__navlist']/li/a");
	private By uninav_next = new By.ByCssSelector(".uninav__carousel__nxt");
	private By uninav_previous = new By.ByCssSelector(".uninav__carousel__prv");
	private By selectedNumber = new By.ByCssSelector(
			"form.card__form > ol.card__numbers > li.card__number--selected > label.card__number__label > input.card__number__input");
	private By dailyTokenBonus_button = new By.ByCssSelector("button.btn--gold");
	private By dailyTokenBonusModuleTokens = new By.ByCssSelector(
			"div.modal-body > ol.dailybonus__values > li.dailybonus__value--complete > span.dailybonus__value__bonus");
	private By dailyTokenBonusModuleVisitNumber = new By.ByCssSelector(
			"div.modal-body > ol.dailybonus__values > li.dailybonus__value--complete > p.dailybonus__value__visits > span.dailybonus__value__visits__body");

	private By leaderboard = new By.ByCssSelector(".leaderboard--header");
	private By leaderboard_mobile_icon = new By.ByCssSelector(
			".leaderboard--mobile-icon");
	private By leaderboard_header = new By.ByCssSelector(
			".leaderboard--scoreboard.dark>h3");
	private By leaderboard_desktop_icon = new By.ByCssSelector(
			".leaderboard--desktop-icon");
	private By leaderboard_yesterday_winners = new By.ByCssSelector(
			".leaderboard--yesterday-view");
	private By leaderboard_yesterday_winners_details = new By.ByCssSelector(
			".winner");

	// Initialize Variables
	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	Hashtable<Integer, ArrayList<String>> selectedNumbersGrp = new Hashtable<Integer, ArrayList<String>>();
	// Below line change for Results Page story
	public static Hashtable<Integer, ArrayList<String>> numbers_selected_forallcards = new Hashtable<Integer, ArrayList<String>>();
	ArrayList<String> selectedNumbers;
	ArrayList<String> numbersDrawn = new ArrayList<String>();
	static String email;
	public static List<String> numbersSubmittedAre = new ArrayList<String>();
	static String[] numbersSubmittedarr = new String[10];

	ArrayList<String> expectedScreenHeader = new ArrayList<String>();
	int currentScreenNumber = 0;
	String openedScreenHeading;
	List<WebElement> tutorialScreenHeaders = null;
	List<WebElement> tutorialScreenImages = null;
	static String leaderboardheader = "";

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public void clickOn_Register_Btn() {
		element(Register_btn).click();
	}

	public void clickOn_SignIn_Btn() {
		element(SignIn_btn).click();
	}

	public void clickOn_Live_Drawing() {
		element(liveDrawing).click();
	}

	public void clickOn_QuickPick_Btn() {

		if (getDriver().getCurrentUrl().contains("live-drawing")) {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(PlayKeno_link));
			WaitHelper.waitForVisibliyOfElement(getDriver(), PlayKeno_link);
			element(PlayKeno_link).click();
		}
		try {
			WaitHelper.forceWait(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		WebElement e = element(QuickPick_btn);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);", e);

		wait.until(ExpectedConditions.visibilityOfElementLocated(QuickPick_btn));
		WaitHelper.waitForElementToBeClickable(getDriver(), QuickPick_btn, 3);
		element(QuickPick_btn).click();
		System.out.println("Quick Pick button clicked");

	}

	public void playAllCards(int cardCount) {

		int slotGames = 0, scratchGames = 0, totalNumberOfGames = 0, flag = 0, cardsPlayed = 0;
		try {
			for (slotGames = 1; slotGames <= cardCount; slotGames++) {
				cardsPlayed++;
				waitForAnElement(QuickPick_btn);
				WaitHelper.waitForElementToBeClickable(getDriver(),
						QuickPick_btn);
				scrollPage();
				element(QuickPick_btn).click();
				clickOn_Submit_Btn_AfterQuickPick(slotGames);
				WaitHelper.forceWait(5000);
				HandleVideoAdvertisement();
				getDriver().switchTo().parentFrame();
				if (element(QuickPick_btn).isVisible()) {
					flag++;
					continue;
				}
				if (flag == 4)
					break;
				waitForAnElement(Playnow_btn);
				element(Playnow_btn).click();
				waitForAnElement(GameFrame);
				String[] str = element(GameCount).getText().split("of");
				totalNumberOfGames = Integer.parseInt(str[1].trim());
				System.out
						.println("Total number of Scratch Games for Slot card "
								+ slotGames + " is " + totalNumberOfGames);
				for (scratchGames = 1; scratchGames <= totalNumberOfGames; scratchGames++) {
					scrollPage();
					moveToPathGameFrame();
					System.out.println("Switched to Scratch Game frame");
					waitForAnElement(Playnowbtn_frame);
					element(Playnowbtn_frame).click();
					System.out
							.println("Clicked Play now button inside Scratch Game frame");
					WaitHelper.forceWait(5000);
					getDriver().switchTo().parentFrame();
					System.out
							.println("Switched to Parent frame to handle If Already Played/Technical Difficulty popup came");
					if (element(Continue_btn).isDisplayed()) {
						element(Continue_btn).click();
						HandleVideoAdvertisement();
						if (scratchGames != totalNumberOfGames)
							waitForAnElement(GameFrame);
					} else {
						moveToPathGameFrame();
						System.out.println("Switched to Scratch Game frame");
						playScratchCards();
						if (scratchGames != totalNumberOfGames)
							waitForAnElement(GameFrame);
					}

					System.out
							.println("Expected Scratch Games to be played is "
									+ totalNumberOfGames + " for the card "
									+ slotGames);
					System.out.println("Number of Scratch Games are played is "
							+ scratchGames + " for the card " + slotGames);

				}

				Assert.assertTrue(
						"Expected Scratch Games are not played completely",
						scratchGames - 1 == totalNumberOfGames);
			}
		}

		catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Element is not present");
		}

		catch (Exception e) {
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
		}

		Assert.assertTrue("Expected Slot cards are not played completly",
				cardsPlayed == cardCount);
	}

	public void playScratchCards() throws InterruptedException {
		try {

			if (element(Playnowbtn_frame).isDisplayed()) {
				System.out
						.println("Play Now button is still appearing so clicked again");
				element(Playnowbtn_frame).click();
			}

			element(RevealAll_btn).click();
			getDriver().switchTo().parentFrame();
			System.out.println("Switched to Parent frame");
			waitForAnElement(Keepgoing_btn);
			System.out.println("Keep going button visible"
					+ isElementVisible(Keepgoing_btn));
			scrollPage();
			WaitHelper.waitForVisibliyOfElement(getDriver(), Keepgoing_btn);
			element(Keepgoing_btn).click();

			System.out.println("Clicked Keep Going button");
			HandleVideoAdvertisement();
		} catch (org.openqa.selenium.WebDriverException e) {
			System.out.println("Exception" + e);

		}

	}

	public void HandleVideoAdvertisement() throws InterruptedException {
		By AdvContainer = new By.ByCssSelector("#ad_holder");
		By AdvContain = new By.ByCssSelector("#jwplayer-ad");
		System.out.println("inside Video Advertisement div   ");
		try {
			if ((element(AdvContainer).isPresent())
					|| (element(AdvContain).isPresent())) {
				System.out
						.println("Video Advertisement Frame is Available, going to force skip   ");
				JavascriptExecutor js = (JavascriptExecutor) getDriver();
				js.executeScript("jwplayer().trigger('adComplete')");
			} else {
				System.out
						.println("Due to error while skipping video ad, we are forcing wait for 15 seconds.");
				WaitHelper.forceWait(15000);
			}

		} catch (Exception e) {
			System.out
					.println("Faced exception while skipping video ad. Hence forcing wait of 15 seconds.");
			WaitHelper.forceWait(15000);
		}

	}

	public void scrollPage() {
		((JavascriptExecutor) getDriver()).executeScript("scroll(0,700)");
	}

	public void CompletedStatusVerification(int card) {
		List<String> cardStatus = new ArrayList<String>();
		waitForElementPresence(CardTabNum);
		for (WebElement ele : element(CardTabNum).findElements(CardTabNum)) {
			cardStatus.add(ele.getText());
		}
		for (int i = 0; i < card; i++) {
			Assert.assertTrue(
					"Status displaying as un completed instead of completed",
					cardStatus.get(i).contains("COMPLETED"));
		}
	}

	public void clickOn_Clear_Btn() {
		element(Clear_btn).waitUntilVisible();
		element(Clear_btn).click();
	}

	public void clickOn_Submit_Btn_AfterQuickPick() {
		if (element(Submit_btn).isVisible()) {
			element(Submit_btn).click();
			System.out.println("Submit  button clicked");
		}
		((JavascriptExecutor) getDriver()).executeScript(
				"window.scrollBy(0, -700)", "");

	}

	public void clickOn_Submit_Btn_AfterQuickPick(int index) {
		if (element(Submit_btn).isVisible()) {
			element(Submit_btn).click();
		} else {
			clickOn_QuickPick_Btn();
		}
		selectedNumbers = new ArrayList<String>();
		List<WebElement> selecetdNumbers = getDriver()
				.findElements(
						By.cssSelector("#main-content > article[data-cardnum=\""
								+ index
								+ "\"] > section.card.card--withshadow.card--unlocking > form > ol > li.card__number--selected > label"));
		for (int i = 0; i < selecetdNumbers.size(); i++) {
			String selectedNum = selecetdNumbers.get(i).getText().trim();
			selectedNumbers.add(selectedNum);
		}
		selectedNumbersGrp.put(index, selectedNumbers);
		System.out.println("Selected numbers are: " + selectedNumbersGrp);
		numbers_selected_forallcards = selectedNumbersGrp;
		element(Submit_btn).click();
	}

	public void signoutLinkPresent() {

		if (element(BonusReward_btn).isVisible())
			element(BonusReward_btn).click();
	//	Assert.assertTrue("User Login / Registration got Passed", element(SignOut_link).isVisible());
	}

	public void PlayFirstGame() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(Playnow_btn));
		element(Playnow_btn).click();
	}

	public void skipAdd() {
		WebDriverWait wait = new WebDriverWait(getDriver(), 20);
		By SkipAdd = new By.ByXPath(
				"//div[contains (text(),'Skip Ad') or contains(@class,'videoAdUiSkipButtonExperimentalText') or contains(@class,'videoAdUiFixedPaddingSkipButtonText')]");
		try {
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("jwplayer().trigger('adComplete')");
			System.out
					.println("Entered Video Advertisement Frame to Skip If SKip Button Available");
			WebElement AdvFrame = getDriver()
					.findElement(
							By.xpath("//iframe[contains(@src,'https://securepubads.g.doubleclick.net')]"));
			getDriver().switchTo().frame(AdvFrame);
			wait.until(ExpectedConditions.elementToBeClickable(SkipAdd));
			if (element(SkipAdd).isDisplayed())
				element(SkipAdd).click();
		} catch (org.openqa.selenium.NoSuchElementException e) {

			System.out
					.println("Video Advertisement Frame is not available right now");

		} catch (org.openqa.selenium.TimeoutException e) {

			System.out.println("Skip Add Button is not Located ");
		} catch (org.openqa.selenium.WebDriverException e) {
			System.out
					.println("Exception : Context for Skip Ad Button is not found");
		} catch (Exception e) {
			System.out.println("**Exception name is: " + e);
		}
		getDriver().switchTo().parentFrame();

	}

	public void waitForAnElement(By locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println(locator + "Element is not Located ");
		}
	}

	public void waitForElementPresence(By locator) {
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println(locator + "Element is not Presence ");
		}
	}

	public void moveToPathGameFrame() {
		try {
			WebElement PathGameFrame = getDriver()
					.findElement(
							By.xpath("//background-image:url[contains(@src,'//pathgamesassets')]"));
			getDriver().switchTo().frame(PathGameFrame);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Game Frame is not available");
		}
	}

	public void getDrawnNumbers() {
		List<WebElement> drawnNumbersBalls = null;
		boolean b = true;
		while (b) {
			wait.until(ExpectedConditions
					.presenceOfElementLocated(DrawnNumbers));
			drawnNumbersBalls = getDriver().findElements(DrawnNumbers);
			if (drawnNumbersBalls.size() == 10)
				b = false;
		}
		for (int i = 0; i < drawnNumbersBalls.size(); i++) {
			numbersDrawn.add(drawnNumbersBalls.get(i).getText().trim());
		}
		System.out.println(numbersDrawn);
	}

	public void verifymatchingDeatils() {
		for (int i = 1; i <= 5; i++) {
			List<String> grpData = new ArrayList<String>();
			if (selectedNumbersGrp.get(i) != null)
				grpData = selectedNumbersGrp.get(i);
			grpData.retainAll(numbersDrawn);
			System.out.println("Matched numbers for card " + i + " : "
					+ grpData);
			String matchedText = getDriver()
					.findElement(
							By.cssSelector("div.drawing__side-game-match-"
									+ (i)
									+ " > div.side-match-data > div.cardflip > div.drawing__side-game-match-matched > div.colored-top > div.matched"))
					.getText();
			matchedText = matchedText.toLowerCase().replace("matched:", "")
					.trim();
			Assert.assertTrue(" Card number : " + i + "The Drawn Numbers :"
					+ numbersDrawn + " QuickPick Numbers :"
					+ selectedNumbersGrp.get(i),
					grpData.size() == Integer.parseInt(matchedText));
		}
	}

	public void verifyLiveDrawingNumbers() {
		if (!getDriver().getCurrentUrl().endsWith("live-drawing")) {
			clickOn(getDriver().findElement(By.linkText("Live Drawing")));
		}
		WaitHelper.waitForPresenceOfElement(getDriver(), Drawn_Clock, 1200);
		getDrawnNumbers();
		verifymatchingDeatils();
	}

	public void playAllCards(int cardCount, boolean registered) {

		int cards = 0, scratchGames = 0, totalNumberOfGames = 0, flag = 0, cardsPlayed = 0;
		try {
			for (cards = 1; cards <= cardCount; cards++) {
				cardsPlayed++;
				waitForAnElement(QuickPick_btn);
				WaitHelper.waitForElementToBeClickable(getDriver(),
						QuickPick_btn);
				scrollPage();
				element(QuickPick_btn).click();
				clickOn_Submit_Btn_AfterQuickPick(cards);
				WaitHelper.forceWait(5000);
				if (!registered) {
					RegistrationPage registrationPage = new RegistrationPage(
							getDriver());
					registrationPage.magicRegistration();
					signoutLinkPresent();
				}
				HandleVideoAdvertisement();
				getDriver().switchTo().parentFrame();
				if (element(QuickPick_btn).isVisible()) {
					flag++;
					continue;
				}
				if (flag == 4)
					break;
				waitForAnElement(Playnow_btn);
				element(Playnow_btn).click();
				waitForAnElement(GameFrame);
				String[] str = element(GameCount).getText().split("of");
				totalNumberOfGames = Integer.parseInt(str[1].trim());
				System.out
						.println("Total number of Scratch Games for Slot card "
								+ cards + " is " + totalNumberOfGames);
				for (scratchGames = 1; scratchGames <= totalNumberOfGames; scratchGames++) {
					scrollPage();
					moveToPathGameFrame();
					System.out.println("Switched to Scratch Game frame");
					waitForAnElement(Playnowbtn_frame);
					element(Playnowbtn_frame).click();
					System.out
							.println("Clicked Play now button inside Scratch Game frame");
					WaitHelper.forceWait(5000);
					getDriver().switchTo().parentFrame();
					System.out
							.println("Switched to Parent frame to handle If Already Played/Technical Difficulty popup came");
					if (element(Continue_btn).isDisplayed()) {
						element(Continue_btn).click();
						HandleVideoAdvertisement();
						if (scratchGames != totalNumberOfGames)
							waitForAnElement(GameFrame);
					} else {
						moveToPathGameFrame();
						System.out.println("Switched to Scratch Game frame");
						playScratchCards();
						if (scratchGames != totalNumberOfGames)
							waitForAnElement(GameFrame);
					}

					System.out
							.println("Expected Scratch Games to be played is "
									+ totalNumberOfGames + " for the card "
									+ cards);
					System.out.println("Number of Scratch Games are played is "
							+ scratchGames + " for the card " + cards);
				}

				Assert.assertTrue(
						"Expected Scratch Games are not played completely",
						scratchGames - 1 == totalNumberOfGames);
			}

		}

		catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Element is not present");
		}

		catch (Exception e) {
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
		}

		Assert.assertTrue("Expected Slot cards are not played completly",
				cardsPlayed == cardCount);
	}

	public void clickOn_Home() {
		if (!getDriver().getTitle().contains("Home"))
			element(home).click();
	}

	// Changes for Results page diwresultstokens.story
	public void clickOn_Results_Btn() throws InterruptedException {
		System.out.println("Current URL is: " + getDriver().getCurrentUrl());
		if (element(BonusReward_btn).isVisible()
				&& element(BonusReward_btn).isEnabled()) {
			element(BonusReward_btn).click();
			WaitHelper.forceWait(10000);
		}

		if (element(Results_btn).isVisible()) {
			element(Results_btn).click();
			if (!getDriver().getCurrentUrl().endsWith("results"))
				element(Results_btn).click();
		}

	}

	// Changes for Coaching Screen story
	public void coachingQuickpick() {
		waitForAnElement(coaching_screen);
		if (element(coaching_screen).isDisplayed()) {
			coachingTickingClock();
			Assert.assertEquals("Pick 10 Numbers or Choose Quick Pick!",
					element(coaching_screen).getText());
		} else
			System.out
					.println("Coaching screen for quick pick is not displayed");
	}

	public void coachingSubmit() {
		waitForAnElement(coaching_screen);
		if (element(coaching_screen).isDisplayed()) {
			coachingTickingClock();
			Assert.assertEquals("Submit Your Numbers!",
					element(coaching_screen).getText());
		} else
			System.out.println("Coaching screen for Submit is not displayed");
	}

	// Changes for Coaching Screen story
	public void coachingTickingClock() {
		waitForAnElement(coaching_screen);
		if (element(coaching_screen).isDisplayed()
				&& element(coaching_screen)
						.getText()
						.contains(
								"The Clock is Ticking! Get all 5 Keno Cards in for the Drawing")) {
			System.out
					.println("Ticking clock message is appearing on screen. Hence waiting to disappear it");
			WaitHelper.waitForVisibliyOfElement(getDriver(), coaching_screen);
		}
	}

	public void LandOnGameChoices() throws InterruptedException {

		HandleVideoAdvertisement();
		scrollPage();
		WaitHelper.forceWait(8000);
		// Assert.assertTrue(getDriver().getCurrentUrl().contains("game-choices"));
		System.out.println("User has landed on Game choices page");
		// scrollPage();
		// WaitHelper.forceWait(10000);
		// waitForAnElement(Playnow_btn);
		// System.out.println("this is Play now of choices screen        "+
		// Playnow_btn);
		// Assert.assertTrue("game-choices",element(coaching_screen).getText());

	}

	public void coachingIWGame() {
		waitForAnElement(coaching_screen);
		if (element(coaching_screen).isDisplayed()) {
			Assert.assertEquals(
					"Play Any Instant Win Game To Unlock Your Next Keno Card!",
					element(coaching_screen).getText());
		} else
			System.out.println("Coaching screen for IW Game is not displayed");
	}

	public void scratchpathplay() throws Exception {
		int scratchGames = 1, totalNumberOfGames = 0;
		getDriver().switchTo().parentFrame();

		try {
			waitForAnElement(Playnow_btn);
			System.out.println("identifiable button playnow"
					+ element(Playnow_btn).isCurrentlyEnabled());
			scrollPage();
			element(Playnow_btn).click();
			waitForAnElement(GameFrame);

			String[] str = element(GameCount).getText().split("of");
			totalNumberOfGames = Integer.parseInt(str[1].trim());
			for (scratchGames = 1; scratchGames <= totalNumberOfGames; scratchGames++) {
				waitForAnElement(Playnowbtn_frame);
				System.out.println("Is PLAY NOW visible/clickable ?"
						+ isElementVisible(Playnowbtn_frame));
				element(Playnowbtn_frame).click();
				WaitHelper.forceWait(5000);
				getDriver().switchTo().parentFrame();
				/* Changes on Feb 27th for iFrame** */
				/*
				 * if (element(Continue_btn).isDisplayed()) {
				 * element(Continue_btn).click(); HandleVideoAdvertisement(); if
				 * (scratchGames != totalNumberOfGames)
				 * waitForAnElement(GameFrame); } else { moveToPathGameFrame();
				 */
				playScratchCards();
				if (scratchGames != totalNumberOfGames)
					waitForAnElement(GameFrame);
				// }
			}
			Assert.assertTrue(
					"Expected Scratch Games are not played completely",
					scratchGames - 1 == totalNumberOfGames);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("******Element is not present*********");
			throw (e);
		}

		catch (Exception e) {
			System.out.println(e.getClass());
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	public void nocoaching() {
		try {
			if (element(coaching_screen).isDisplayed()) {
				Assert.assertFalse(true);
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("No Coaching screen is appearing on this page");
		}

	}

	// Changes for UnRecognized User Sign In_Post Game Play story
	// Below method is to select numbers only in ascending order. Otherwise
	// volume of permutation and combination will slow up the method execution
	public void select_numbersforKenoCard(List<String> numberstoselect) {
		int count = 0;
		waitForElementPresence(selectnumbers);
		for (WebElement ele : getDriver().findElements(selectnumbers)) {
			if (ele.getText().equals(numberstoselect.get(count).toString())) {
				ele.click();
				count++;
			}
			if (count == 10)
				break;
		}
		// ((JavascriptExecutor) getDriver()).executeScript("scroll(0,200)");
		((JavascriptExecutor) getDriver()).executeScript(
				"arguments[0].scrollIntoView(true);", element(Submit_btn));
		waitForElementPresence(Submit_btn);
		element(Submit_btn).click();
	}

	// Changes for Login story
	public void userprofile() {
		waitForElementPresence(welcometext);
		Assert.assertTrue(
				"Welcome text is not displayed in profile bar after login",
				element(welcometext).getText().contains("Welcome")
						&& (!element(welcometext).getText().contains(
								"@pchmail.com")));

	}

	// Changes for Post Game Play token earning story
	public void clickTokenHistory() {
		WaitHelper.waitForElementToBeClickable(getDriver(), tokenHistory);
		if (element(tokenHistory).isEnabled())
			element(tokenHistory).click();
		signInPage.login(RegistrationPage.newUserName,
				RegistrationPage.newPassword);

	}

	public void validateTokensForNewUser() {

	}

	public void vipPresent() {
		System.out.println("Checking for VIP screen");
		// WaitHelper.waitForVisibliyOfElement(getDriver(), VIP_close_btn);
		// WaitHelper.waitForVisibliyOfElement(getDriver(),VIP_close_btn,3);
		if (element(VIP_close_btn).isVisible()) {
			System.out.println("Closing VIP screen");
			element(VIP_close_btn).click();
		}
	}

	public void closeOptinLightBox() {
		// WaitHelper.waitForElementToBeClickable(getDriver(), OPTIN_light_box);
		// WebElement e1 = element(OPTIN_light_box);
		// Actions a1 = new Actions(getDriver());
		// a1.moveToElement(e1).click().perform();
		if (element(OPTIN_light_box).isPresent()) {
			element(OPTIN_close_button).click();
		}
	}

	public void playSecondKenoCard() {
		WaitHelper.waitForVisibliyOfElement(getDriver(), CardTabStatus);
		WaitHelper.waitForVisibliyOfElement(getDriver(), CardTabNum);
		if ((element(CardTabStatus).getCssValue("visibility")
				.equalsIgnoreCase("visible"))
				&& (element(CardTabNum).getText().equalsIgnoreCase("02"))) {
			System.out.println("Inside Keno Card 2");
			WebElement e1 = element(QuickPick_btn);
			JavascriptExecutor js1 = (JavascriptExecutor) getDriver();
			js1.executeScript("arguments[0].scrollIntoView(true);", e1);
			try {
				WaitHelper.forceWait(2000);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			element(QuickPick_btn).click();
			// waitForElementPresence(Submit_btn);
			element(Submit_btn).click();
			System.out.println("Keno Card 2 Submit  button clicked");
			try {
				WaitHelper.forceWait(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * if (element(Submit_btn).isEnabled()) {
			 * element(Submit_btn).click();
			 * System.out.println("Keno Card 2 Submit  button clicked"); }
			 */
		} else
			System.out.println("Not able to validate keno card 2 ");

	}

	public void rsRegistration() {
		create_user_withpassword();
	}

	public void create_user_withpassword() {

		RequestDefaultsOverride defaultOverride = new RequestDefaultsOverride();
		RegistrationRequestGenerator rs = new RegistrationRequestGenerator();

		String gmt, url;
		System.out.println("Echo: Creating the URL for User with password");

		rp.uniqueReg();
		defaultOverride.setEmail(RegistrationPage.uni_email);
		rs.generateGoldUserInSTG(defaultOverride);

		// rs.generateGoldUserInSTG();
		email = rs.getEmail();
		gmt = rs.getGmt();
		url = getDriver().getCurrentUrl() + "?em=" + email + "&gmt=" + gmt;
		System.out.println(email);
		System.out.println(gmt);
		System.out.println(url);
		getDriver().navigate().to(url);

	}

	public void univnavpresence() {

		WaitHelper.waitForVisibliyOfElement(getDriver(), univnav);
		if (!element(univnav).isVisible()) {
			System.out.println("Universal Navigation is not appearing on site");
		}
	}

	public void validatetabnavigation() {
		List<String> propertynames = new ArrayList<String>();
		System.out
				.println("Total number of properties appearing in Univ Navigation is: "
						+ findAll(univnav_properties).size());
		for (int i = 0; i < findAll(univnav_properties).size(); i++) {
			try {
				if (getDriver().getTitle().contains("keno")) {
					if (findAll(univnav_properties).get(i).getAttribute("href")
							.toString().length() > 23) {
						propertynames.add(findAll(univnav_properties).get(i)
								.getAttribute("href").toString()
								.substring(7, 25));
					} else {
						propertynames.add(findAll(univnav_properties).get(i)
								.getAttribute("href").toString()
								.substring(7, 22));
					}
					findAll(univnav_properties).get(i).click();
					waitPage.waitForLoad(getDriver());
					if (getDriver().getCurrentUrl().contains("blackjack")) {
						Assert.assertTrue(propertynames.get(i).toString()
								.contains("pch.com/bl"));
					} else {
						Assert.assertTrue(getDriver().getCurrentUrl().contains(
								propertynames.get(i).toString()));
					}

				}
				getDriver().navigate().back();
				waitPage.waitForLoad(getDriver());
			} catch (ElementNotVisibleException e) {
				System.out
						.println("Element to click is not visible. Therefore moving right");
				element(uninav_next).click();
				i = i - 1;
			}
		}
	}

	public void scrollnavigation() {
		int count = 1;
		while (!element(uninav_next).getAttribute("class").contains("disabled")) {
			System.out
					.println("Scrolling right of the universal navigation and number of times it scrolled is: "
							+ count);
			element(uninav_next).click();
			count++;
		}
		count = 1;
		while (!element(uninav_previous).getAttribute("class").contains(
				"disabled")) {
			System.out
					.println("Scrolling left of the universal navigation and number of times it scrolled is: "
							+ count);
			element(uninav_previous).click();
			count++;
		}

	}

	public void verifyOptinLightboxPresent() {
		//WaitHelper.waitForPresenceOfElement(getDriver(), OPTIN_light_box);
		if (element(OPTIN_light_box).isPresent()) {
			System.out.println("Optin lightbox is present");
		} else {
			System.out.println("Optin lightbox is not displayed");
		}
	}

	public void clickOnOptinSignupButton() {
		element(Optin_signup_button).click();
		/*if (element(Optin_signup_button).isPresent())
		{
			element(Optin_signup_button).click();
		}
		else 
		{
			System.out.println("Optin lightbox is not displayed");
		}*/
		
	}

	public void verifyOptinThankYouMessage() {
		WaitHelper
				.waitForPresenceOfElement(getDriver(), Optin_thankYou_message);
		if (element(Optin_thankYou_message).isPresent()) {
			System.out.println("Optin Signed up ");
		} else {
			System.out.println("Optin not Signed up");
		}
	}

	public void verifyOptinTokens() throws InterruptedException {
		WaitHelper.forceWait(3000);
		// ((JavascriptExecutor)
		// getDriver()).executeScript("window.scrollBy(0, -700)", "");
		/*
		 * if (element(dailyTokenBonus_button).isPresent()) {
		 * System.out.println("Closing daily token bonus screen");
		 * element(dailyTokenBonus_button).click(); }
		 */
		WaitHelper.waitForVisibliyOfElement(getDriver(), tokenHistory, 3);
		element(tokenHistory).click();
		List<WebElement> tokenDescription = getDriver()
				.findElements(
						By.cssSelector("section#th_list > article > div.th_details > div.th_desc"));
		List<WebElement> tokenHistory = getDriver().findElements(
				By.cssSelector("section#th_list > article"));
		List<WebElement> creditTokens = getDriver()
				.findElements(
						By.cssSelector("section#th_list > article > div.th_details > div.date_tokens > span.tokens"));
		Iterator<WebElement> i = tokenDescription.iterator();
		Iterator<WebElement> i2 = creditTokens.iterator();
		System.out.println("Token History size is  " + tokenHistory.size());

		Thread.sleep(3000);

		while (i.hasNext() && (i2.hasNext())) {
			WebElement e1 = i.next();
			WebElement e2 = i2.next();

			System.out.println("e1    " + e1.getText());
			System.out.println("e2    " + e2.getText());

			if (e1.getText().contains("Email Sign Up Bonus Tokens!")) {
				Assert.assertEquals("Email Sign Up Bonus Tokens!", e1.getText());
				System.out.println("creditTokens  "
						+ e2.getText().substring(1).toString());
				Assert.assertTrue(e1.getText().contains(
						"Email Sign Up Bonus Tokens!"));
			}
		}
		System.out.println("Validated Optin tokens");

	}

	public void openTutorials() {
		element(tutorialsBtn).waitUntilVisible();

		if (element(tutorialsBtn).isEnabled())
			element(tutorialsBtn).click();
		else
			System.out.println("Tutorials button not enabled");
	}

	public void verifyTutorialsOpen() {
		try {
			Assert.assertTrue(element(tutorialsNextBtn).isDisplayed());
			System.out.println("SYSO:: Tutorial screen open");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Tutorials screen is not appearing.");
		}

		// Fetch Header & Image elements for all 6 tutorial screens
		tutorialScreenHeaders = getDriver().findElements(tutorialScreenHeader);
		tutorialScreenImages = getDriver().findElements(tutorialImage);

		// Check which screen is open and fetch its Heading & Screen number
		for (int i = 0; i < 6; i++) {
			if (element(tutorialScreenHeaders.get(i)).isCurrentlyVisible()) {
				openedScreenHeading = element(tutorialScreenHeaders.get(i))
						.getText();
				currentScreenNumber = i + 1;
				break;
			}
		}

		System.out.println("Heading of open tutorial screen:: "
				+ openedScreenHeading);
		System.out.println("Tutorial Screen " + currentScreenNumber
				+ " is open");

		expectedScreenHeader
				.add("Play PCHkeno and You Could Win $1,000,000.00!");
		expectedScreenHeader.add("Win Cash and Score Tokens! Play All Day!");
		expectedScreenHeader.add("Chances to WIN INSTANTLY All Day Long!");
		expectedScreenHeader.add("Live Drawing Every 20 Minutes!");
		expectedScreenHeader.add("See if You Won!");
		expectedScreenHeader.add("Daily $10,000,000.00 Sweepstakes Entry!");

	}

	public void clickTutorialsNext() {
		for (int i = 0; i < (6 - currentScreenNumber); i++) {
			// Clicking Next button if enabled
			element(tutorialsNextBtn).waitUntilEnabled();
			if (element(tutorialsNextBtn).isEnabled()) {
				element(tutorialsNextBtn).click();
				System.out.println("Tutorial Next button clicked");
			} else
				System.out.println("Tutorials Next button not enabled");

			// Checking if expected screen is appearing & fetch the header
			element(tutorialScreenImages.get(currentScreenNumber + i))
					.waitUntilVisible();
			element(tutorialScreenHeaders.get(currentScreenNumber + i))
					.waitUntilVisible();
			if (element(tutorialScreenHeaders.get(currentScreenNumber + i))
					.isCurrentlyVisible()) {
				openedScreenHeading = element(
						tutorialScreenHeaders.get(currentScreenNumber + i))
						.getText();
				System.out.println("openedScreenHeading:: "
						+ openedScreenHeading);
				System.out.println("Tutorial Screen "
						+ (currentScreenNumber + i + 1) + " is open");
			} else {
				System.out.println("Screen " + (currentScreenNumber + i + 1)
						+ " is not open");
				Assert.assertFalse(true);
			}

			// Checking if the fetched header text is expected or not
			if (openedScreenHeading.equals(expectedScreenHeader
					.get(currentScreenNumber + i))) {
				System.out.println("Correct Tutorial screen is open.");
				System.out.println("Heading of open tutorial screen:: "
						+ openedScreenHeading);
				System.out.println("");
			} else {
				Assert.assertFalse(true);
			}
		}
	}

	public void clickTutorialsPrevious() {
		for (int i = 0; i < (5); i++) {
			// Clicking Previous button if enabled
			element(tutorialsPreviousBtn).waitUntilEnabled();
			if (element(tutorialsPreviousBtn).isEnabled()) {
				element(tutorialsPreviousBtn).click();
				System.out.println("Tutorial Previous button clicked");
			} else
				System.out.println("Tutorials Previous button not enabled");

			// Checking if expected screen is appearing & fetch the header
			element(tutorialScreenImages.get(4 - i)).waitUntilVisible();
			element(tutorialScreenHeaders.get(4 - i)).waitUntilVisible();
			if (element(tutorialScreenHeaders.get(4 - i)).isCurrentlyVisible()) {
				openedScreenHeading = element(tutorialScreenHeaders.get(4 - i))
						.getText();
				System.out.println("openedScreenHeading:: "
						+ openedScreenHeading);
				System.out.println("Tutorial Screen " + (5 - i) + " is open");
			} else {
				System.out.println("Screen " + (5 - i) + " is not open");
				Assert.assertFalse(true);
			}

			// Checking if the fetched header text is expected or not
			if (openedScreenHeading.equals(expectedScreenHeader.get(4 - i))) {
				System.out.println("Correct Tutorial screen is open.");
				System.out.println("Heading of open tutorial screen:: "
						+ openedScreenHeading);
				System.out.println("");
			} else {
				Assert.assertFalse(true);
			}
		}
	}

	public void clickTutorialsClose() {
		if (element(tutorialsCloseBtn).isEnabled())
			element(tutorialsCloseBtn).click();
		else
			System.out.println("Tutorials Close button not enabled");

		try {
			Assert.assertTrue(element(QuickPick_btn).isDisplayed());
			System.out
					.println("SYSO:: Tutorial screen closed. Home page appearing.");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Tutorials screen is not closed.");
		}
	}

	public void clickCompleteRegBtn() {
		element(completeRegBtn).waitUntilVisible();

		if (element(completeRegBtn).isEnabled())
			element(completeRegBtn).click();
		else
			System.out.println("Complete Registration button is not enabled");
	}

	public void verifySuccessfulRegistration() {
		// This method can be used to verify when below users category completes
		// registration and returns to Home Page -
		// - Mini Reg User
		// - Social User
		// - User without password

		// Verify presence of My Account link
		WaitHelper.waitForElementToBeClickable(getDriver(), myAccountLink);
		element(myAccountLink).waitUntilVisible();
		if (!element(myAccountLink).isCurrentlyVisible())
			Assert.assertFalse("My Account link is not appearing.", true);
		else
			System.out.println("My Account link is appearing.");

		// Verify presence of Token History link
		element(tokenHistory).waitUntilVisible();
		if (!element(tokenHistory).isCurrentlyVisible())
			Assert.assertFalse("Token History link is not appearing.", true);
		else
			System.out.println("Token History link is appearing.");

		// Verify absence of Complete Registration button
		if (!element(completeRegBtn).isCurrentlyVisible())
			System.out
					.println("Complete Registration button not appearing - As Expected.");
		else
			Assert.assertFalse("Complete Registration button still appearing",
					true);
	}

	public void navigateToTokenHistory() {
		element(tokenHistory).waitUntilVisible();
		if (element(tokenHistory).isEnabled())
			element(tokenHistory).click();
		else
			Assert.assertFalse("Token History link is not enabled.", true);

		signInPage.loginIfSignInPageAppearing();
	}

	public void readPickedNumbers() {
		List<WebElement> numbersSubmitted = getDriver().findElements(
				selectedNumber);
		System.out.println("numbersSubmitted.size()  "
				+ numbersSubmitted.size());

		numbersSubmittedAre.clear();
		for (int i = 0; i < 10; i++) {
			numbersSubmittedAre.add(numbersSubmitted.get(i).getAttribute(
					"value"));
		}

		System.out.println("Numbers submitted " + numbersSubmittedAre);

	}

	public void clickSignOut() {
		if (element(SignOut_link).isVisible())
			element(SignOut_link).click();
		else
			Assert.assertFalse("Sign Out link is not appearing", true);
	}

	public void numbersSubmittedSameOnSideRail() {
		List<WebElement> sideRailNumbers = getDriver()
				.findElements(
						By.cssSelector("div.card-tab-holder > div.card-tab--completed > div > div.card-tab__completed > ul.card-tab__completed__nums > li.card-tab__completed__num > span"));
		System.out.println("sideRailNumbers.size()  " + sideRailNumbers.size());
		int sz = sideRailNumbers.size();
		String[] sideRailNumbersarr = new String[sz];
		for (int i = 0; i < sz; i++) {
			sideRailNumbersarr[i] = sideRailNumbers.get(i).getText();
		}
		System.out.println("numbers on side Rail "
				+ Arrays.toString(sideRailNumbersarr));
		Assert.assertArrayEquals(numbersSubmittedarr, sideRailNumbersarr);

	}

	public void verifyDailyTokenBonusTokens() {
		if (element(dailyTokenBonus_button).isPresent()) {
			System.out.println("Daily Token Bonus Module Tokens   "
					+ element(dailyTokenBonusModuleTokens).getTextValue());
			System.out.println(" Module visit number "
					+ element(dailyTokenBonusModuleVisitNumber).getText());
			int visit = Integer.parseInt(element(
					dailyTokenBonusModuleVisitNumber).getTextValue());
			System.out.println("visit  = " + visit);
			System.out.println("JoomlaAdminPage.dailyTokenBonusArr nth visit "
					+ JoomlaAdminPage.dailyTokenBonusArr[visit - 1]);
			Assert.assertEquals(JoomlaAdminPage.dailyTokenBonusArr[visit - 1],
					element(dailyTokenBonusModuleTokens).getTextValue());
		} else {
			System.out.println("Daily token bonus NOT displayed here");
		}
	}

	public void choiceSelection(String game) throws InterruptedException,
			FindFailed {

		List<WebElement> Choices_Parent = getDriver().findElements(
				By.cssSelector("div.choices__games__page > div"));
		System.out.println("Choices_Parent.size()  " + Choices_Parent.size());
		int sz = Choices_Parent.size();
		String[] alttextarr = new String[sz];
		String alttext = null;

		int i;
		int selectedChoice = 0;

		List<WebElement> Choices = getDriver().findElements(
				By.cssSelector("div.choices__games__page__game > div > img"));

		for (i = 0; i < Choices_Parent.size(); i++) {
			alttext = Choices.get(i).getAttribute("alt");
			alttextarr[i] = alttext;
			if (game.equalsIgnoreCase("Scratch Card")) {
				alttext = "IWPATH";
				if (alttextarr[i].contains(alttext)) {
					System.out.println("alttextarray  "
							+ Arrays.toString(alttextarr));
					String alttextIWPath = Choices.get(i).getAttribute("alt");
					System.out.println("selectedChoice ALT is =   "
							+ alttextIWPath);
					selectedChoice = i;
					System.out.println("selectedChoice  = " + selectedChoice);
					break;
				}
			}

			if (game.equalsIgnoreCase("SFL")) {
				alttext = "D-Keno set-for-life_3cards";
				if (alttextarr[i].equals(alttext)) {
					selectedChoice = i;
					System.out.println("selectedChoice   = " + selectedChoice);
				}
			}
		}

		System.out.println("alttextarray  " + Arrays.toString(alttextarr));
		switch (alttext) {
		case "D-Keno set-for-life_3cards": {
			System.out.println("selectedChoice  = " + selectedChoice);
			Choices.get(selectedChoice).click();
		}
			break;
		case "IWPATH": {
			System.out.println("selectedChoice  = " + selectedChoice);
			Choices.get(selectedChoice).click();
		}
			break;
		default:
			System.out.println("default slots game");
			alttext = "slots";
		}
		System.out.println("alttextarray  " + Arrays.toString(alttextarr));

	}

	public void playChoiceAsUserWPwd(String game) throws FindFailed,
			InterruptedException {
		switch (game) {
		case "SFL": {
			Thread.sleep(3000);
			int totalSFLGames = 2;
			for (int selectedChoice = 0; selectedChoice <= totalSFLGames; selectedChoice++) {
				csfl.clickOnSFLPlayNow();
				if (element(Keepgoing_btn).isPresent()) // support for Tech Diff
														// when TD GOS shows
														// before Reveal all
				{
					scrollPage();
					System.out.println("Handling Technical difficulty "
							+ isElementVisible(Keepgoing_btn));

					WaitHelper.waitForElementToBeClickable(getDriver(),
							Keepgoing_btn);
					if (element(Keepgoing_btn).isPresent()) {
						element(Keepgoing_btn).click();
					}

					((JavascriptExecutor) getDriver()).executeScript(
							"window.scrollBy(0, -500)", "");
					HandleVideoAdvertisement();
				} else {
					csfl.clickOnRevealNowButton();
					waitForAnElement(Keepgoing_btn);
					WaitHelper.forceWait(2000);
					scrollPage();
					WaitHelper.waitForElementToBeClickable(getDriver(),
							Keepgoing_btn);
					if (element(Keepgoing_btn).isPresent()) {
						element(Keepgoing_btn).click();
					}

					((JavascriptExecutor) getDriver()).executeScript(
							"window.scrollBy(0, -500)", "");
					HandleVideoAdvertisement();
				}
			}
		}
			break;
		case "Scratch Card": {
			int scratchGames = 0, totalNumberOfGames = 0;
			scrollPage();
			String[] str = element(GameCount).getText().split("of");
			totalNumberOfGames = Integer.parseInt(str[1].trim());
			for (scratchGames = 1; scratchGames <= totalNumberOfGames; scratchGames++) {
				WaitHelper.forceWait(1000);
				waitForAnElement(Playnowbtn_frame);
				element(Playnowbtn_frame).click();
				WaitHelper.forceWait(1000);
				if (element(Keepgoing_btn).isPresent()) // support for Tech Diff
														// when TD GOS shows
														// before Reveal all
				{
					waitForAnElement(Keepgoing_btn);
					WaitHelper.forceWait(2000);
					scrollPage();
					System.out.println("Handling Technical difficulty "
							+ isElementVisible(Keepgoing_btn));

					WaitHelper.waitForElementToBeClickable(getDriver(),
							Keepgoing_btn);
					if (element(Keepgoing_btn).isPresent()) {
						element(Keepgoing_btn).click();
					}

					((JavascriptExecutor) getDriver()).executeScript(
							"window.scrollBy(0, -700)", "");
					HandleVideoAdvertisement();
				} else {
					if (element(RevealAll_btn).isPresent()) {
						element(RevealAll_btn).click();
					} else // support for Play now frame not going away upon
							// clicking
					{
						WaitHelper.forceWait(1000);
						getDriver().navigate().refresh();
						WaitHelper.forceWait(1000);
						element(Playnowbtn_frame).click();
						WaitHelper.forceWait(1000);
						element(RevealAll_btn).click();
						waitForAnElement(Keepgoing_btn);
						WaitHelper.forceWait(2000);
						scrollPage();

						WaitHelper.waitForElementToBeClickable(getDriver(),
								Keepgoing_btn);
						if (element(Keepgoing_btn).isPresent()) {
							element(Keepgoing_btn).click();
						}

						((JavascriptExecutor) getDriver()).executeScript(
								"window.scrollBy(0, -700)", "");
						HandleVideoAdvertisement();
					}

					waitForAnElement(Keepgoing_btn);
					WaitHelper.forceWait(2000);
					scrollPage();

					WaitHelper.waitForElementToBeClickable(getDriver(),
							Keepgoing_btn);
					if (element(Keepgoing_btn).isPresent()) {
						element(Keepgoing_btn).click();
					}
					((JavascriptExecutor) getDriver()).executeScript(
							"window.scrollBy(0, -700)", "");
					HandleVideoAdvertisement();

				}
			}
		}
			break;
		default: {
			System.out.println("Playing default slots game");
		}

		}

	}

	public void observeLeaderboard() {
		waitForElementPresence(leaderboard);
		System.out.println("Leaderboard headers are: "
				+ element(leaderboard).getText());
		Assert.assertTrue(element(leaderboard).getText().contains(
				"TOKEN leaderboard"));
	}

	public void mobileTabAppearingInLeaderboard() throws Exception {
		if (element(leaderboard_mobile_icon).isCurrentlyEnabled()) {
			// jsp.scrollToView(element(leaderboard_mobile_icon));
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);",
					element(leaderboard_mobile_icon));
			element(leaderboard_mobile_icon).click();
			Thread.sleep(3000);
			System.out.println("***header is: "
					+ element(leaderboard_header).getText());
			Assert.assertTrue(element(leaderboard_header).getText().contains(
					"MOBILE"));
			leaderboardheader = element(leaderboard_header).getText();
		} else
			System.out.println("Mobile Icon is not clickable");
	}

	public void desktopTabAppearingInLeaderboard() throws Exception {
		if (element(leaderboard_desktop_icon).isCurrentlyEnabled()) {
			// jsp.scrollToView(element(leaderboard_mobile_icon));
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);",
					element(leaderboard_desktop_icon));
			System.out.println("***header is: "
					+ element(leaderboard_header).getText());
			Assert.assertTrue(element(leaderboard_header).getText()
					.equalsIgnoreCase("DESKTOP & TABLET"));
			leaderboardheader = element(leaderboard_header).getText();
		} else
			System.out.println("Desktop Icon is not clickable");
	}

	public void winnersAppearingInLeaderboard() throws Exception {

		if (element(leaderboard_yesterday_winners).isCurrentlyEnabled()) {
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);",
					element(leaderboard_yesterday_winners));
			element(leaderboard_yesterday_winners).click();
			Thread.sleep(3000);
			if (element(leaderboard_yesterday_winners_details).isPresent())
				System.out.println("***winner details are: "
						+ element(leaderboard_yesterday_winners_details)
								.getText());
			else
				System.out.println("Yesterday Winner section is not appearing");
		}
	}

	public void OpenKenoHomePage() {
		getDriver().get(PropertiesReader.getInstance().kenoUrl);
		getDriver().manage().window().maximize();
	}
}