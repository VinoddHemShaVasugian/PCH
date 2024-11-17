package com.pch.search.pages.web;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import com.pch.search.Bots.BOT_TYPE;
import com.pch.search.bean.LastActivityBotResultBean;
import com.pch.search.bean.TokenCounterBotResultBean;
import com.pch.search.pages.lightBox.LevelLightBox;
import com.pch.search.pages.lightBox.LightBox;
import com.pch.search.pages.lightBox.SignInLightBox;
import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;
import com.pch.search.utilities.PageFactory;
import com.pch.search.utilities.User;

public class WebHeaderPage extends Action {

	HomePage homePage = new HomePage();
	String device = Environment.getDevice();
	String appUrl = Common.getAppUrl(Environment.getAppName());

	/*
	 * public WebHeaderPage(final WebDriver driver, final Environment env) {
	 * super(driver, env); }
	 */
	public WebHeaderPage() {
		areaLocatorXPath = "//div[@id='uni_header']";
	}

	private HtmlElement myAccountLink() {
		return driver.findElement(By.className("my-account"));
	}

	private HtmlElement subNavigationContainer() {
		try {
			return driver.findElement(By.xpath("//div[@class='subNav container']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement welcomeText() {
		return driver.findElement(By.xpath("//p[@class='credentials']"));
	}

	public HtmlElement logo() {
		return driver.findElement(By.xpath(".//*[@alt='PCH Search and Win']"));
	}

	public void gotoHome() {
		logo().click();
		CustomLogger.log("Navigated to Home Page.");
	}

	/* Mobile */
	public HtmlElement menu() {
		return driver.findElement(By.xpath(".//*[@class='burger']"));
	}

	// elements under Navigation bar - Mobile
	public HtmlElement userName() {
		return driver.findElement(By.xpath(".//*[@class='user-name']"));
	}

	/*
	 * public HtmlElement signIn(){ return driver.findElement(By.xpath(
	 * ".//*[@class='account-action'][contains(text(), 'Sign In')]")); }
	 * 
	 * public HtmlElement signOutBtn(){ return driver.findElement(By.xpath(
	 * ".//*[@class='account-action'][contains(text(), 'Sign Out')]")); }
	 */

	public String getWelcomeUserLinkText() {
		CustomLogger.log("Getting logged in user");
		try {
			if (device.equalsIgnoreCase("mobile")) {
				menu().click();
				Common.sleepFor(2000);
				return userName().getText();
			} else {
				return driver.findElement(By.xpath(".//*[@class='content profile']/p[1]")).getText();
			}
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public boolean areHaderLinksDisplayed() {

		return driver.getCountOfElementsWithXPath(".//*[@id='uni-top-bar']/div/p/a") > 0;
	}

	public HtmlElement aboutPCHSearchAndWin() {
		try {
			driver.get(appUrl);
			new LevelLightBox(driver).dismissLightBox();
			if (driver.getCurrentUrl()
					.contains("http://search." + Environment.getEnvironment() + ".pch.com/guidedsearch")) {
				if (!areHaderLinksDisplayed()) {
					search("term", false);
				}
			}
			return driver.findElement(By.xpath("//a[text()='About PCHSearch&Win']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement howToSearch() {
		try {
			return driver.findElement(By.xpath("//a[text()='How To Search']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement help() {
		try {
			return driver.findElement(By.xpath("//a[text()='Help']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement officialRules() {
		try {
			driver.get(appUrl);
			// homePage.closeUserLevelLightBox();
			// if()
			/*
			 * if(driver.getCurrentUrl().contains("http://search."+Environment.
			 * getEnvironment()+".pch.com/guidedsearch")){
			 */
			if (driver.getCurrentUrl()
					.contains("http://search." + Environment.getEnvironment() + ".pch.com/guidedsearch")) {
				if (!areHaderLinksDisplayed()) {
					search("term", false);
				}
			}
			return driver.findElement(By.xpath("//a[text()='Official Rules']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement sweepstakesFacts() {
		try {
			/*
			 * if(driver.getCurrentUrl().contains("http://search."+Environment.
			 * getEnvironment()+".pch.com/guidedsearch")){
			 */
			driver.get(appUrl);
			if (driver.getCurrentUrl()
					.contains("http://search." + Environment.getEnvironment() + ".pch.com/guidedsearch")) {
				if (!areHaderLinksDisplayed()) {
					search("term", false);
				}
			}
			return driver.findElement(By.xpath("//a[text()='Sweepstakes Facts']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement contestDetails() {
		try {
			return driver.findElement(By.xpath("//a[text()='Contest Details']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement signOutLink() {
		try {
			return driver.findElement(By.xpath("//a[text()='Sign Out']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement signInButton() {
		driver.switchTo().defaultContent();
		HtmlElement signIn = null;
		try {
			signIn = driver.findElement(By.xpath(
					"//div[contains(@class,'multi-nav') or @class='unrecognized']//a[contains(text(),'Sign In')]"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
		}
		return signIn;
	}

	/*
	 * try{ if(device.equalsIgnoreCase("mobile")){ return
	 * driver.findElement(By.xpath("//*[@id='uni-nav-top']/div[2]/div[3]/a"));
	 * }else{ return
	 * driver.findElement(By.cssSelector("a.sign-in.link-button")); } } catch
	 * (WebDriverException wde) { CustomLogger.logException(wde); return null; }
	 */

	public HtmlElement registerButton() {
		try {
			return driver.findElement(By.cssSelector("a.registration.register-btn"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement userId() {
		HtmlElement userId = null;
		try {
			userId = driver.findElement(By.xpath("//input[@name='email' or @name='EM']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
		}
		return userId;
	}

	private HtmlElement password() {
		HtmlElement pwd = null;
		try {
			pwd = driver.findElement(By.xpath("//input[@name='password' or @name='PW']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
		}
		return pwd;
	}

	private HtmlElement loginBtn() {
		HtmlElement signInBtn = null;
		try {
			signInBtn = driver.findElement(By.xpath("//*[@id='login-sub-btn' or @class='sign-in-btn']"));
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
		}
		return signInBtn;
	}

	// *[@id='login-sub-btn' or @class='sign-in-btn']
	public Object getaboutPCHSearchAndWin() {
		return aboutPCHSearchAndWin();
	}

	public Object getofficialRulesLink() {
		return officialRules();
	}

	public Object gethowToSearch() {
		return howToSearch();
	}

	public Object gethelp() {
		return help();
	}

	public Object getSweepStakeFactLink() {
		return sweepstakesFacts();
	}

	public Object getContestDetailLink() {
		return contestDetails();
	}

	public Object getSignInButton() {
		return signInButton();
	}

	public Object getRegisterButton() {
		return registerButton();
	}

	public Object getSignOutLink() {
		return logOutLink();
	}

	public Object getMyAccountLink() {
		return myAccountLink();
	}

	public void signOut() {
		CustomLogger.log("Signing out user");
		if (device.equalsIgnoreCase("mobile")) {
			menu().click();
			logOutLink().click();
		} else {
			logOutLink().scrollUpAndClick();
			driver.waitForBrowserToLoadCompletely();
		}
	}

	public String[] getCategories() {

		List<HtmlElement> categoryElements = subNavigationContainer().findElements(By.xpath("descendant::a"));
		String[] categories = new String[categoryElements.size()];
		for (int i = 0; i < categoryElements.size(); i++) {
			categories[i] = categoryElements.get(i).getText();
		}

		return categories;
	}

	/**
	 * Verifies whether someone is already logged in. <br>
	 * If yes, will check whether it's same as asked user. If yes, <br>
	 * will return (do nothing) <br>
	 * else, logout the current logged in user and login with asked user.
	 * 
	 * @param user
	 * 
	 * @return <br>
	 *         Will return -1 if user is not present. <br>
	 *         Will return -2 if Unhandled Scenario (error message) is present.
	 *         <br>
	 *         Will return 0 if provided email is registered but password is
	 *         incorrect. <br>
	 *         Will return 1 if user is successfully logged in. <br>
	 *         Will return 2 if user was created using minireg and completion of
	 *         registration is pending.
	 * 
	 * 
	 */
	public int loginToSearch(User user) {
		CustomLogger.log("Going to Login in the application");
		String welcomeText = getWelcomeUserLinkText();
		CustomLogger.log("Welcome Text - " + welcomeText);
		if (!welcomeText.contains("Welcome to PCHSearch")) {
			if (!welcomeText.contains("Guest")) {
				if (welcomeText.startsWith("Welcome " + user.getFirstname() + " " + user.getLastname())) {
					CustomLogger.log("User already logged in.");
					return 1;
				} else if (welcomeText.toLowerCase().startsWith("welcome " + user.getEmail().toLowerCase())) {
					CustomLogger.log("User already logged in as mini reg.");
					return 1;
				} else {
					// logout
					signOut();
				}
			}
		}

		CustomLogger
				.log(String.format("Signing with username %s and password %s", user.getEmail(), user.getPassword()));
		if (Environment.getBrowserType().equals("ie")) {
			signInButton().sendKeys(Keys.ENTER);
			driver.waitForBrowserToLoadCompletely();
		} else {
			signInButton().click();
			driver.waitForBrowserToLoadCompletely();
		}

		try {
			String currentURL = driver.getCurrentUrl();
			CustomLogger.log(currentURL);
			String loadedPCHProperty = Common.subString(currentURL,
					String.format("(?<=http:\\/\\/|https:\\/\\/).*(?=%s)", Environment.getEnvironment()));

			if (loadedPCHProperty.startsWith("Search.")) {

				// Behavior changed after including a common sign in and
				// registration page
				userId().sendKeys(user.getEmail());
				password().sendKeys(user.getPassword());
				loginBtn().click();

				try {
					if (driver
							.findElement(By.xpath("//div[@class='sso-outer-shell']/descendant::ul[@class='error']/li"),
									4)
							.isDisplayed()) {
						HtmlElement pleaseCorrect = driver.findElement(
								By.xpath("//div[@class='sso-outer-shell']/descendant::ul[@class='error']/li"), 4);
						String pleaseCorrectText = pleaseCorrect.getAttribute("textContent");
						CustomLogger.log(pleaseCorrectText);
						try {
							PageFactory.getBrowserNDriverMap()
									.get(Environment.getBrowserType() + Thread.currentThread().getId()).getDriver()
									.findElement(By.cssSelector("a[evergage-closeicon=evergage-closeicon]")).click();
						} catch (Exception e) {

						}
						if (pleaseCorrectText.contains("The password you entered is invalid")) {
							CustomLogger.log(String.format("Incorrect password %s for email %s", user.getPassword(),
									user.getEmail()));
							return 0;
						} else if (pleaseCorrectText.contains("the email you provided is not recognized")) {
							CustomLogger.log(String.format("User with email %s is not registered.", user.getEmail()));
							return -1;
						}
					}
				} catch (Exception es) {
					return 1;
				}

			}

			else if (loadedPCHProperty.startsWith("accounts")) {
				SignInLightBox silb = new SignInLightBox(driver);
				return silb.signIn(user);

			}

			CustomLogger.log("Unhandled error message is displayed while signing In, refer screenshot.");
			return -2;
			// return 1;

		} catch (TimeoutException toe) {
			/*
			 * User is present
			 */
			CustomLogger.logException(toe);
			driver.waitForBrowserToLoadCompletely();
			String name = getWelcomeUserLinkText();
			CustomLogger.log("Welcome user link text " + name);
			if (name != null) {
				// String name=loggedInUserLink.getText();
				// if(name.startsWith(user.getFirstname())&&name.endsWith(user.getLastname())){
				if (name.startsWith("Welcome " + user.getFirstname() + " " + user.getLastname())) {
					CustomLogger.log("User already registered and successfully logged in.");
					return 1;

				} else if (name.toLowerCase().startsWith("welcome " + user.getEmail().toLowerCase())) {
					CustomLogger.log("User was registered using miniReg and succesfully logged in.");
					return 2;
				} else {
					/**
					 * Check whether Loader icon is displayed
					 */
					String loaderIconXpath = "//img[@class='loader-icon show']";
					if (driver.getCountOfElementsWithXPath(loaderIconXpath) != 0) {
						CustomLogger.log("Loader icon keeps on displaying upon clicking signin button.");
					} else {
						CustomLogger.log("Unexpected error occurred while signing in.");
					}
					return -2;
				}
			} else {
				CustomLogger.log("Unexpected error occurred while signing in.");
				return -2;
			}

		}
	}

	// Creating a login method with User Name and Password as parameters

	public int loginToSearch(User user, String password) {
		String welcomeText = getWelcomeUserLinkText();
		CustomLogger.log("Welcome Text - " + welcomeText);
		if (!welcomeText.contains("Welcome to PCHSearch")) {
			if (welcomeText.startsWith("Welcome " + user.getFirstname() + " " + user.getLastname())) {
				CustomLogger.log("User already logged in.");
				return 1;
			} else if (welcomeText.toLowerCase().startsWith("welcome " + user.getEmail().toLowerCase())) {
				CustomLogger.log("User already logged in as mini reg.");
				return 1;
			} else {
				// logout
				signOut();
			}
		}

		CustomLogger.log(String.format("Signing with username %s and password %s", user.getEmail(), password));
		if (Environment.getBrowserType().equals("ie")) {
			signInButton().sendKeys(Keys.RETURN);
		} else {
			signInButton().click();
			driver.waitForBrowserToLoadCompletely();
		}

		try {
			String currentURL = driver.getCurrentUrl();
			CustomLogger.log(currentURL);
			String loadedPCHProperty = Common.subString(currentURL,
					String.format("(?<=http:\\/\\/|https:\\/\\/).*(?=%s)", Environment.getEnvironment()));

			if (loadedPCHProperty.startsWith("Search.")) {

				// Behavior changed after including a common sign in and
				// registration page
				userId().sendKeys(user.getEmail());
				password().sendKeys(password);
				loginBtn().click();

				HtmlElement pleaseCorrect = driver
						.findElement(By.xpath("//div[@class='sso-outer-shell']/descendant::ul[@class='error']/li"), 4);
				String pleaseCorrectText = pleaseCorrect.getAttribute("textContent");
				CustomLogger.log(pleaseCorrectText);
				try {
					PageFactory.getBrowserNDriverMap()
							.get(Environment.getBrowserType() + Thread.currentThread().getId()).getDriver()
							.findElement(By.cssSelector("a[evergage-closeicon=evergage-closeicon]")).click();
				} catch (Exception e) {

				}
				if (pleaseCorrectText.contains("The password you entered is invalid")) {
					CustomLogger.log(String.format("Incorrect password %s for email %s", password, user.getEmail()));
					return 0;
				} else if (pleaseCorrectText.contains("the email you provided is not recognized")) {
					CustomLogger.log(String.format("User with email %s is not registered.", user.getEmail()));
					return -1;
				}

			}

			else if (loadedPCHProperty.startsWith("accounts")) {
				SignInLightBox silb = new SignInLightBox(driver);
				return silb.signIn(user, password);

			}

			CustomLogger.log("Unhandled error message is displayed while signing In, refer screenshot.");
			return -2;
			// return 1;

		} catch (TimeoutException toe) {
			/*
			 * User is present
			 */
			CustomLogger.logException(toe);
			driver.waitForBrowserToLoadCompletely();
			String name = getWelcomeUserLinkText();
			CustomLogger.log("Welcome user link text " + name);
			if (name != null) {
				// String name=loggedInUserLink.getText();
				// if(name.startsWith(user.getFirstname())&&name.endsWith(user.getLastname())){
				if (name.startsWith("Welcome " + user.getFirstname() + " " + user.getLastname())) {
					CustomLogger.log("User already registered and successfully logged in.");
					return 1;

				} else if (name.toLowerCase().startsWith("welcome " + user.getEmail().toLowerCase())) {
					CustomLogger.log("User was registered using miniReg and succesfully logged in.");
					return 2;
				} else {
					/**
					 * Check whether Loader icon is displayed
					 */
					String loaderIconXpath = "//img[@class='loader-icon show']";
					if (driver.getCountOfElementsWithXPath(loaderIconXpath) != 0) {
						CustomLogger.log("Loader icon keeps on displaying upon clicking signin button.");
					} else {
						CustomLogger.log("Unexpected error occurred while signing in.");
					}
					return -2;
				}
			} else {
				CustomLogger.log("Unexpected error occurred while signing in.");
				return -2;
			}

		}
	}

	public void loginToSearch(String email, String password) {

		if (device.equalsIgnoreCase("Mobile")) {
			signInButton().click();
			driver.waitForBrowserToLoadCompletely();
			CustomLogger.log("Landed successfully on Log-In page.");

			userId().sendKeys(email);
			password().sendKeys(password);
			loginBtn().click();
			driver.waitForBrowserToLoadCompletely();
			CustomLogger.log("Logged-In and landed successfully on Home page.");
		} else {
			CustomLogger.log("Going to Login in the application");
			String welcomeText = getWelcomeUserLinkText();
			CustomLogger.log("Welcome Text - " + welcomeText);
			if (!(welcomeText.contains("Welcome to PCHSearch") || welcomeText.contains("Guest"))) {
				CustomLogger.log("User already logged in.");
				signOut();
			}

			CustomLogger.log(String.format("Signing with username %s and password %s", email, password));
			signInButton().click();
			CustomLogger.log("Landed successfully on Log-In page.");
			userId().sendKeys(email);
			password().sendKeys(password);
			loginBtn().click();

			while (driver.getTitle().contains("Login")) {
				Common.sleepFor(5000);
			}

			driver.waitForBrowserToLoadCompletely();
			CustomLogger.log("Logged-In and landed successfully on Home page.");
			try {
				PageFactory.getBrowserNDriverMap().get(Environment.getBrowserType() + Thread.currentThread().getId())
						.getDriver().findElement(By.cssSelector("a[evergage-closeicon=evergage-closeicon]")).click();
			} catch (Exception e) {

			}
		}
	}

	public void loginToSearchUsingUrl(String email, String gmt) {
		String url = Common.getAppUrl(Environment.getAppName());
		url = url + "/?email=" + email + "&gmt=" + gmt;
		driver.get(url);
		CustomLogger.log("Successfully logged-in using email  and gmt.");
	}

	public boolean isLoginPageLoaded() {
		boolean isLoaded = false;
		CustomLogger.log("Going to validate Login page loaded");
		try {
			userId().waitForVisible();
			password().waitForVisible();
			driver.getCurrentUrl().contentEquals("https://accounts." + Environment.getEnvironment() + ".pch.com/login");
			isLoaded = true;
		} catch (Exception e) {
			CustomLogger.log("Login page not loaded");
		}
		return isLoaded;
	}

	public void clickRegisterBtn() {
		CustomLogger.log("User is not registered.....going to register it");
		String currentURL = driver.getCurrentUrl();
		CustomLogger.log(currentURL);
		String loadedPCHProperty = Common.subString(currentURL,
				String.format("(?<=http:\\/\\/|https:\\/\\/).*(?=%s)", Environment.getEnvironment()));
		HtmlElement register = null;
		if (loadedPCHProperty.toLowerCase().startsWith("search.".toLowerCase())) {
			register = driver.findElement(By.xpath("//*[contains(@class,'register-btn')]"));
		} else if (loadedPCHProperty.startsWith("accounts")) {
			register = driver.findElement(By.xpath(".//a[contains(text(), 'Register')]"));
		}
		register.click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Navigated to Registration Page");
	}

	public void clickForgotPassword() {
		if (Environment.getBrowserType().equals("ie")) {
			signInButton().sendKeys(Keys.RETURN);
		} else {
			signInButton().click();
			driver.waitForBrowserToLoadCompletely();
		}
		SignInLightBox silb = new SignInLightBox(driver);
		silb.forgotPasswordLink().click();
		driver.waitForBrowserToLoadCompletely();
		// driver.findElement(By.xpath("//span[text()='Forgot password?
		// ']/a[text()='Click here']")).click();
	}

	// vamsi Dec-07-2015
	public void clickSignInBtn() {
		signInButton().click();
		driver.waitForBrowserToLoadCompletely();
	}

	public void navigateToMyAccountPage() {
		myAccountLink().scrollUpAndClick();
		driver.waitForBrowserToLoadCompletely();
	}

	public HtmlElement tokenCenterCompleteRegBtn() {
		Common.sleepFor(3000);
		return driver.findElement(By.xpath(".//div[@id='token_center']//a[contains(text(),'Complete Registration')]")); //// *[contains(@class,'token-center')]/div/ul/li/a[1]
	}

	/**
	 * This function will return true only if Complete registration button
	 * exits, False otherwise
	 * 
	 * @return: True/False
	 * @author Vaibhav.Singh
	 */
	public boolean isCompleteRegBtnExists() {
		boolean isExists;
		;
		try {
			isExists = driver.findElements(
					By.xpath(".//div[@id='token_center']//a[contains(text(),'Complete Registration')]")) != null;
		} catch (Exception e) {
			isExists = false;
		}
		return isExists;
	}

	public HtmlElement headerCompleteRegLink() {
		Common.sleepFor(3000);

		return driver.findElement(By.xpath("//*[contains(@class,'credentials')]/a[1]"));
	}

	public HtmlElement logOutLink() {
		// Common.sleepFor(3000);
		String signOutButtonXpath = "//a[@class='sign-out']";
		String signOutButtonXpathMobile = "//a[text()='Sign Out']";
		if (driver.getCountOfElementsWithXPath(signOutButtonXpath) > 0) {
			return driver.findElement(By.xpath(signOutButtonXpath));
		} else if (driver.getCountOfElementsWithXPath(signOutButtonXpathMobile) > 0) {
			return driver.findElement(By.xpath(signOutButtonXpathMobile));
		} else {
			return driver.findElement(By.xpath("//*[contains(@class,'not-you')]"));
		}

	}

	public HtmlElement searchBox() {
		return waitForElementToBeVisible(By.name("q"));

	}

	private HtmlElement bottomSearchBox() {
		if (device.equalsIgnoreCase("mobile")) {
			return driver.findElement(By.xpath(
					".//*[@class='col-xs-12 searchbox-bottom']/div[@class='search-box']/form/div/input[@type='search']"));
		} else {
			return driver.findElement(By.xpath(
					".//*[@class='center-bottom-content']/div[contains(@class,'search-box')]/form/input[contains(@name,'q')]"));
		}

	}

	public void search(String keyword) {
		if (device.equalsIgnoreCase("mobile")) {
			driver.waitForBrowserToLoadCompletely();
			if (!driver.getCurrentUrl().contains(".com/search")) {
				driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']");
			}
			searchBox().clear();
			searchBox().sendKeys(keyword);
			searchBox().submit();
			driver.waitForBrowserToLoadCompletely();
			CustomLogger.log("Searched for keyword \"" + keyword + "\" and landed on SERP.");
		} else {
			searchBox().clear();
			searchBox().sendKeys(keyword);
			activateBots(BOT_TYPE.LAST_ACTIVITY, BOT_TYPE.TOKENS_COUNTER);
			searchBox().submit();
			startSignal.countDown();
			driver.waitForBrowserToLoadCompletely();
			lastActivity = (LastActivityBotResultBean) getBotResult(BOT_TYPE.LAST_ACTIVITY);
			tokenCounter = (TokenCounterBotResultBean) getBotResult(BOT_TYPE.TOKENS_COUNTER);

			/*
			 * Check if user's level has changed and lightbox for the same is
			 * present, if yes dismiss that.
			 */
			if (tokenCounter.shouldLightBoxBePresent()) {
				CustomLogger.log("Waiting for Light box if applicable");
				LevelLightBox lb = new LevelLightBox(driver);
				lb.dismissLightBox(true);
			}
		}
	}

	public void search(String keyword, boolean activateBots) {
		CustomLogger.log("Searching for keyword: " + keyword);
		if (activateBots) {
			search(keyword);
			driver.waitForBrowserToLoadCompletely();
		} else {
			if (device.equalsIgnoreCase("mobile")) {
				Common.sleepFor(3000);
				driver.waitForBrowserToLoadCompletely();
				/*
				 * if(!driver.getCurrentUrl().contains("search/?q=")){
				 * driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']"
				 * ); }
				 */
				searchBox().clear();
				searchBox().sendKeys(keyword);
				searchBox().submit();
				driver.waitForBrowserToLoadCompletely();
				CustomLogger.log("Searched for keyword \"" + keyword + "\" and landed on SERP.");
			} else {
				/*
				 * if(!driver.getCurrentUrl().contains("search/?q=")){
				 * CustomLogger.log("Switching to the Frame");
				 * driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']"
				 * ); }
				 */
				/*
				 * if(driver.getCurrentUrl().contains("http://search."+
				 * Environment.getEnvironment()+".pch.com/guidedsearch")){
				 * CustomLogger.log("Switching to the Frame");
				 * driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']"
				 * ); }
				 */
				searchBox().clear();
				searchBox().sendKeys(keyword);
				searchBox().submit();
				driver.waitForBrowserToLoadCompletely();
				CustomLogger.log("Searched for keyword \"" + keyword + "\" and landed on SERP.");
				// LightBox lb = new LevelLightBox(driver);
				// lb.dismissLightBox();
			}
		}
	}

	public List<String> getPredictiveSearchSuggestions(String keyword) {
		/*
		 * if(driver.getCurrentUrl().contains("http://search."+Environment.
		 * getEnvironment()+".pch.com/guidedsearch")){
		 * //driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']"); }
		 */

		searchBox().clear();
		searchBox().sendKeys(keyword);
		Common.sleepFor(2000);
		List<String> searchSuggestions = null;
		String suggestions_xpath = "//div[@id='suggestPanel']/div";
		try {
			driver.findElement(By.xpath(suggestions_xpath), 5);
		} catch (WebDriverException we) {
			return searchSuggestions;
		}

		searchSuggestions = new ArrayList<String>();
		for (HtmlElement element : driver.findElements(By.xpath(suggestions_xpath))) {
			searchSuggestions.add(element.getText());
		}
		CustomLogger.log("Predictive suggestions - " + searchSuggestions);
		driver.get(appUrl);

		return searchSuggestions;
	}

	public List<String> getPredictiveSearchSuggestionsForBottomSearchBox(String keyword) {
		bottomSearchBox().clear();
		bottomSearchBox().sendKeys(keyword);
		Common.sleepFor(2000);
		List<String> searchSuggestions = null;
		String suggestions_xpath = "//div[@id='suggestPanel']/div";
		try {
			driver.findElement(By.xpath(suggestions_xpath), 5);
		} catch (WebDriverException we) {
			return searchSuggestions;
		}

		searchSuggestions = new ArrayList<String>();
		for (HtmlElement element : driver.findElements(By.xpath(suggestions_xpath))) {
			searchSuggestions.add(element.getText());
		}
		CustomLogger.log("Predictive suggestions - " + searchSuggestions);
		return searchSuggestions;
	}

	/**
	 * 
	 * @return The number of tokens accrued by user.
	 */
	public int getTokenCount() {
		try {
			HtmlElement tokenCountElement = driver.findElement(By.className("token-amount"));
			String tokens = tokenCountElement.getText().replaceAll("(?<=\\d),(?=\\d)", "");

			return Integer.parseInt(tokens);
		} catch (NumberFormatException nfe) {
			CustomLogger.logException(nfe);
			return -1;
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return -1;
		}

	}

	private HtmlElement tokenCenter() {
		return driver.findElement(By.xpath("//div[contains(@class,'token-center')]"));
	}

	/**
	 * Get latest activity message which contains Tokens accrued by latest
	 * activity & What was the latest activity.
	 * 
	 * @return
	 */
	public String getLatestActivityFromStatus() {
		String recentActivity = "No recent Activity Found";
		try {
			Common.sleepFor(2000);

			String classAtrib = "";
			// WaitFor compression to complete (if currently in process)
			do {
				Common.sleepFor(1000);
				HtmlElement element = driver.findElement(By.id("token_center"));
				classAtrib = element.getAttribute("class");
			} while (classAtrib.contains("expanded"));

			HtmlElement tokenCentre = tokenCenter();
			double originalHeight = Double.parseDouble(tokenCentre.getCssValue("height").replaceAll("px", ""));
			driver.findElement(By.className("action-button")).scrollUpAndClick();

			// WaitFor expansion to complete
			// String classAtrib="";
			do {
				Common.sleepFor(1000);
				HtmlElement element = driver.findElement(By.id("token_center"));
				classAtrib = element.getAttribute("class");
			} while (!classAtrib.contains("expanded"));

			HtmlElement recentActivityElement = tokenCentre
					.findElement(By.xpath("descendant::div[@class='message-display']"));

			recentActivity = recentActivityElement.getAttribute("textContent");
			// Remove the digit separator ',' in tokens, e.g will make 1,000 as
			// 1000
			recentActivity = recentActivity.replaceAll("(?<=\\d),(?=\\d)", "").trim();
			CustomLogger.log("Recent Activity - " + recentActivity);

			// HtmlElement expandedTokenCenter =
			// driver.findElement(By.xpath("//div[contains(@class,'expanded')][@id='token_center']"));

			driver.findElement(By.className("action-button")).scrollDownAndClick();
			do {
				Common.sleepFor(2000);
				HtmlElement element = driver.findElement(By.id("token_center"));
				classAtrib = element.getAttribute("class");
			} while (classAtrib.contains("expanded"));

			tokenCentre = tokenCenter();
			double collapsedHeight = Double.parseDouble(tokenCentre.getCssValue("height").replaceAll("px", ""));
			if (collapsedHeight != originalHeight) {
				CustomLogger.log("My status not collapsed completely.");
				throw new WebDriverException();
			}
		} catch (WebDriverException wde) {
			CustomLogger.log("No Latest Activity was displayed");
		}
		return recentActivity;
	}

	/**
	 * Gets the random message beside Search Bar
	 */
	public String getMessageBesideSearchBar() {
		try {
			String message = driver.findElement(By.xpath("//div[@id='searchBar']/descendant::p"))
					.getAttribute("textContent");
			CustomLogger.log("Message beside search box - " + message);
			return message;
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return "";
		}
	}

	private HtmlElement getPromotionAreaContainer() {
		return driver.findElement(By.id("exp-promo-area"));
	}

	private HtmlElement getExpandedPromotionContainer() {
		return getPromotionAreaContainer().findElement(By.xpath("div[contains(@class,'mr-big')]"));
	}

	/**
	 * 
	 * @return 1 if promotion image is expanded, 0 if promotion image is
	 *         collapsed, -1 in error condition.
	 */
	public int getpromotionImageExpansionStatus() {
		HtmlElement promotionAreaContainer = getPromotionAreaContainer();
		HtmlElement expandedPromotionContainer = getExpandedPromotionContainer();

		int height_promotionAreaContainer = promotionAreaContainer.getSize().getHeight();
		int height_expandedPromotionContainer = expandedPromotionContainer.getSize().getHeight();

		CustomLogger.log("Height of Promotion Area Container " + height_promotionAreaContainer);
		CustomLogger.log("Height of Expanded Promotion Container " + height_expandedPromotionContainer);

		if (height_promotionAreaContainer == 250 && height_expandedPromotionContainer == 250) {
			return 1;
		} else if (height_promotionAreaContainer == 62 && height_expandedPromotionContainer == 0)
			return 0;
		else
			return -1;
	}

	public String getUserStatus() {
		try {
			String completeClassName = tokenCenter().findElement(By.xpath("//div[contains(@class,'my-status-icon')]"))
					.getAttribute("class");
			String status = Common.subString(completeClassName, "(?<=token-level-icon-).*");
			return status;
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return "NO STATUS";
		}
	}

	public HtmlElement redeemTokensLink() {
		return driver.findElement(By.linkText("Redeem Tokens"));
	}

	private HtmlElement tokenHistoryLink() {
		return driver.findElement(By.linkText("Token History"));
	}

	public boolean isRedeemTokensLinkPresent() {
		try {
			return redeemTokensLink().isDisplayed();
		} catch (WebDriverException wde) {
			// CustomLogger.logException(wde);
			return false;
		}
	}

	public boolean isTokenHistoryLinkPresent() {
		try {
			return tokenHistoryLink().isDisplayed();
		} catch (WebDriverException wde) {
			// CustomLogger.logException(wde);
			return false;
		}
	}

	public void clickTokenHistory() {
		tokenHistoryLink().scrollUpAndClick();
		driver.waitForBrowserToLoadCompletely();
	}

	public void clickRedeemTokens() {
		redeemTokensLink().scrollUpAndClick();
		driver.waitForBrowserToLoadCompletely();
	}

	// vamsi Dec-09
	public boolean isSignINBtnDsplayed() {
		try {
			return signInButton().isDisplayed();
		} catch (WebDriverException wde) {
			return false;
		}

	}

	public boolean isRegisterBtnDsplayed() {
		try {
			return registerButton().isDisplayed();
		} catch (WebDriverException wde) {
			return false;
		}
	}

	public String getCookieValues(String cookieName) {
		String cookieValue = driver.manage().getCookieNamed(cookieName).getValue();
		return cookieValue;

	}

	/**
	 * Gets the Registration Events from test/sso/user page
	 */
	public String getRegEvent() {
		try {
			driver.get("http://search." + Environment.getEnvironment() + ".pch.com/test/sso/user");

			String message = driver.findElement(By.xpath("//td[5]")).getText();
			CustomLogger.log("Registration Event - " + message);

			return message;
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return "";
		}
	}

	/**
	 * Gets the Registration Events from test/sso/user page
	 */
	public String getOptins() {
		try {
			driver.get("http://search." + Environment.getEnvironment() + ".pch.com/test/sso/user");

			String message = driver.findElement(By.xpath("//td[5]")).getText();
			CustomLogger.log("Optins - " + message);

			return message;
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return "";
		}
	}

	/**
	 * Go to Frontpage Site
	 */
	public void goToFrontpage() {
		driver.get("http://frontpage." + Environment.getEnvironment() + ".pch.com");
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Navigated to Frontpage site.");

	}

	/**
	 * 
	 */
	public void addEcParameterAndReloadPage(String value) {
		String EcUrl = "http://search." + Environment.getEnvironment() + ".pch.com/?ec=" + value;
		driver.get(EcUrl);
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("EC Url = " + EcUrl);
		CustomLogger.log("Reloaded the page with the EC Parameter value");
	}

	/**
	 * 
	 */
	public void reloadPageUsingPromotionLink(String segID) {
		String promoUrl = "http://search." + Environment.getEnvironment() + ".pch.com/?segid=" + segID;
		driver.get(promoUrl);
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("EC Url = " + promoUrl);
		CustomLogger.log("Reloaded the page with the Link Promotion Url.");
	}

	/**
	 * Uni Nav Element
	 * 
	 * @return
	 */
	public HtmlElement uniNav() {
		return driver.findElement(By.xpath("//div[@class='nav']"));
	}

	/**
	 * Uni Nav Previous Btn Element
	 * 
	 * @return
	 */
	public HtmlElement uniNavPrevBtn() {
		return driver.findElement(By.xpath("//span[@class='prv']"));
	}

	/**
	 * Uni Nav Next Btn Element
	 * 
	 * @return
	 */
	public HtmlElement uniNavNextBtn() {
		return driver.findElement(By.xpath("//span[@class='nxt']"));
	}

	/**
	 * verify if UniNav is displayed
	 */
	public boolean isUniNavDisplayed() {
		try {
			Common.sleepFor(2000);
			return uniNav().isDisplayed();
		} catch (WebDriverException wde) {
			return false;
		}
	}

	private int uniNavLinksCount() {
		return driver.getCountOfElementsWithXPath(".//*[@class='uni-nav-bar']/div/div[@class='nav-list']/div/ul/li");
	}

	public void getAllUniNavLinksAndValidate() {
		for (int i = 1; i <= uniNavLinksCount(); i++) {
			HtmlElement uniNavLink = driver.findElement(By.xpath("//div[@class='nav']/ul/li[" + i + "]"));
			HtmlElement uniNavLinkLocation = driver.findElement(By.xpath("//*[@class='nav']/ul/li[" + i + "]/a"));

			if (i < 5) {
				CustomLogger.log(uniNavLink.getText());
				CustomLogger.log(uniNavLinkLocation.getAttribute("href"));
				if (i == 1) {
					Assert.assertTrue(uniNavLink.getText().contentEquals("PCH.com"));
					Assert.assertEquals(uniNavLinkLocation.getAttribute("href"),
							"http://www." + Environment.getEnvironment()
									+ ".pch.com/?utm_source=pchportal&utm_medium=uninav&utm_campaign=linkout");
				}

				if (i == 2) {
					Assert.assertTrue(uniNavLink.getText().contentEquals("PCHFrontpage"));
					Assert.assertTrue(uniNavLinkLocation.getAttribute("href")
							.contains("http://frontpage." + Environment.getEnvironment() + ".pch.com"));
				}

				if (i == 3) {
					Assert.assertTrue(uniNavLink.getText().contentEquals("PCHLotto"));
					Assert.assertEquals(uniNavLinkLocation.getAttribute("href"),
							"http://lotto." + Environment.getEnvironment()
									+ ".pch.com/?utm_source=pchportal&utm_medium=uninav&utm_campaign=linkout");
				}

				if (i == 4) {
					Assert.assertTrue(uniNavLink.getText().contentEquals("PCHSlots"));
					Assert.assertEquals(uniNavLinkLocation.getAttribute("href"),
							"http://www." + Environment.getEnvironment() + ".pch.com/slots?source=slots");
				}
			}

			else {
				driver.findElement(By.xpath("//span[@class='nxt']")).click();
				Common.sleepFor(2000);
				CustomLogger.log(uniNavLink.getText());
				Assert.assertTrue(uniNavLink.getText().contentEquals("PCHBlackjack"));
				Assert.assertEquals(uniNavLinkLocation.getAttribute("href"),
						"http://blackjack." + Environment.getEnvironment()
								+ ".pch.com/?utm_source=pchportal&utm_medium=uninav&utm_campaign=linkout");
			}

		}
	}

	public boolean verifyHeaderLinksURL() {

		/*
		 * if(driver.getCurrentUrl().contains("http://search."+Environment.
		 * getEnvironment()+".pch.com/guidedsearch")){
		 */
		if (driver.getCurrentUrl()
				.contains("http://search." + Environment.getEnvironment() + ".pch.com/guidedsearch")) {
			if (!areHaderLinksDisplayed()) {
				search("term", false);
			}
		}

		try {

			aboutPCHSearchAndWin().click();
			Common.sleepFor(3000);
			String aboutPchSearcAndWinUrl = driver.getCurrentUrl();
			CustomLogger.log("Verifying the URL of About PCH Search and Win - " + aboutPchSearcAndWinUrl);
			Assert.assertTrue(aboutPchSearcAndWinUrl.contains(appUrl + "/about"));

			howToSearch().click();
			Common.sleepFor(3000);
			String howToSearchURL = driver.getCurrentUrl();
			CustomLogger.log("Verifying the URL of How to Search and Win - " + howToSearchURL);
			Assert.assertTrue(howToSearchURL.contains(appUrl + "/howto"));

			help().click();
			Common.sleepFor(3000);
			String helpURL = driver.getCurrentUrl();
			CustomLogger.log("Verifying the URL of Help in Search and Win - " + helpURL);
			Assert.assertTrue(helpURL.contains(appUrl + "/help"));

			officialRules().click();
			Common.sleepFor(3000);
			String officialRulesURL = driver.getCurrentUrl();
			CustomLogger.log("Verifying the URL of official Rules in Search and Win - " + officialRulesURL);
			Assert.assertTrue(officialRulesURL.contains(appUrl + "/rules"));

			sweepstakesFacts().click();
			Common.sleepFor(3000);
			String sweepStakesFactsURL = driver.getCurrentUrl();
			CustomLogger.log("Verifying the URL of Sweepstakes Facts in Search and Win - " + sweepStakesFactsURL);
			Assert.assertTrue(sweepStakesFactsURL.contains(appUrl + "/facts"));

			driver.get(Common.getAppUrl(Environment.getAppName()));

			return true;

		} catch (Exception e) {
			CustomLogger.logException(e);
			return false;
		}
	}

	public HtmlElement pchDotCom() {
		return driver.findElement(By.xpath(".//*[@class='nav']/ul/li/a[text()='PCH.com']"));
	}

	public HtmlElement pchFrontPage() {
		return driver.findElement(By.xpath(".//*[@class='nav']/ul/li/a[text()='PCHFrontpage']"));
	}

	public HtmlElement pchLotto() {
		return driver.findElement(By.xpath(".//*[@class='nav']/ul/li/a[text()='PCHLotto']"));
	}

	public HtmlElement pchSlots() {
		return driver.findElement(By.xpath(".//*[@class='nav']/ul/li/a[text()='PCHSlots']"));
	}

	public HtmlElement pchBlackJack() {
		driver.findElement(By.xpath("//span[@class='nxt']")).click();
		Common.sleepFor(2000);
		return driver.findElement(By.xpath(".//*[@class='nav']/ul/li/a[text()='PCHBlackjack']"));
	}

	/*
	 * Note : This is for Recognized user.
	 * 
	 * Here we will verify all the uni - nav tab url's PCH.com PCHFrontpage
	 * PCHLotto PCHSlots
	 */

	public boolean verifyUniNavTabURLForRecognizeduser() {

		// HomePage homePage = new HomePage();
		try {

			driver.get(appUrl);
			pchDotCom().click();
			Common.sleepFor(3000);
			// driver.waitForBrowserToLoadCompletely();
			String pchDotCOMUrl = driver.getCurrentUrl();
			CustomLogger.log("Validating PCH.com URL");
			Assert.assertTrue(pchDotCOMUrl.contains("http://www." + Environment.getEnvironment()
					+ ".pch.com/?utm_source=pchportal&utm_medium=uninav&utm_campaign=linkout"));

			driver.get(Common.getAppUrl(Environment.getAppName()));

			pchFrontPage().click();
			driver.waitForBrowserToLoadCompletely();
			String fpUrl = driver.getCurrentUrl();
			CustomLogger.log("Validating PCHFrontpage URL");
			Assert.assertTrue(fpUrl.contains("http://frontpage." + Environment.getEnvironment() + ".pch.com"));

			driver.get(appUrl);
			pchLotto().click();
			driver.waitForBrowserToLoadCompletely();
			String lottoUrl = driver.getCurrentUrl();
			CustomLogger.log("Validating PCHLotto URL");
			Assert.assertTrue(lottoUrl.contains("http://lotto." + Environment.getEnvironment()
					+ ".pch.com/?utm_source=pchportal&utm_medium=uninav&utm_campaign=linkout"));

			driver.get(appUrl);
			// homePage.closeUserLevelLightBox();
			pchSlots().click();
			driver.waitForBrowserToLoadCompletely();
			String slotsUrl = driver.getCurrentUrl();
			CustomLogger.log("Validating PCHSlots URL");
			Assert.assertTrue(slotsUrl.contains("www." + Environment.getEnvironment() + ".pch.com/slots?source=slots"));

			driver.get(appUrl);
			driver.navigate().refresh();
			pchBlackJack().click();
			driver.waitForBrowserToLoadCompletely();
			String blackJackUrl = driver.getCurrentUrl();
			CustomLogger.log("Validating PCHBlackjack URL");
			Assert.assertTrue(blackJackUrl.contains("http://blackjack." + Environment.getEnvironment()
					+ ".pch.com/?utm_source=pchportal&utm_medium=uninav&utm_campaign=linkout"));

			driver.get(appUrl);

			return true;

		} catch (Exception e) {
			CustomLogger.logException(e);
			return false;
		}

	}

	public List<String> getExpectedRulesFromOfficialsRulesPage() {

		List<String> setOfRules = new ArrayList<String>();
		for (int i = 1; i < 12; i++) {
			String rules = driver.findElement(By.xpath(".//*[@id='main']/h2[" + i + "]")).getText();
			setOfRules.add(rules);
		}
		return setOfRules;

	}

	/*
	 * Here we are getting all the 11 rules we have in official rules page
	 */
	public List<String> getOfficialRulesPageContentSearch() {

		String header = "Official Rules for Search & Win Sweepstakes";

		officialRules().click();
		Common.sleepFor(5000);
		String officialRulesURL = driver.getCurrentUrl();
		CustomLogger.log("Verifying the URL of official Rules in Search and Win - " + officialRulesURL);
		Assert.assertTrue(officialRulesURL.contains(appUrl + "/rules"));

		HtmlElement officailRulesHeader = driver.findElement(By.xpath(".//*[@id='official_rules']/h1"));
		Assert.assertTrue(header.contains(officailRulesHeader.getText()));

		List<String> setOfRules = new ArrayList<String>();
		for (int i = 1; i < 12; i++) {
			String rules = driver.findElement(By.xpath(".//*[@id='official_rules']/h2[" + i + "]")).getText();
			setOfRules.add(rules);
		}

		return setOfRules;
	}

	public List<String> getSweepstakesPageContentSearch() {
		driver.waitForBrowserToLoadCompletely();
		String tableTitle;
		List<String> actualPageContent = new ArrayList<String>();
		sweepstakesFacts().click();
		Common.sleepFor(3000);
		tableTitle = driver.findElement(By.xpath(".//*[@id='official_prizes']/div/div/div")).getText();
		actualPageContent.add(tableTitle);

		int colTitleCount = driver
				.getCountOfElementsWithXPath(".//*[@id='official_prizes']/div/div/table/tbody/tr[1]/th");

		for (int i = 1; i <= colTitleCount; i++) {
			String colTitle = driver
					.findElement(By.xpath(".//*[@id='official_prizes']/div/div/table/tbody/tr[1]/th[" + i + "]"))
					.getText();
			actualPageContent.add(colTitle);
		}

		int bottomTipsCount = driver.getCountOfElementsWithXPath(".//td[@style='text-align:left;']/p/b");

		for (int j = 1; j <= bottomTipsCount; j++) {
			String tipsAtBottom = driver.findElement(By.xpath(".//td[@style='text-align:left;']/p[" + j + "]/b"))
					.getText();
			actualPageContent.add(tipsAtBottom);
		}
		CustomLogger.log("Gathered the Contents from Search sweeps stakes page..");

		return actualPageContent;
	}

	public List<String> getExpectedSweepStakesContentSearch() {

		String tableTitle;
		List<String> expectedPageContent = new ArrayList<String>();

		tableTitle = driver.findElement(By.xpath(".//*[@id='main']/div/div/div")).getText();
		expectedPageContent.add(tableTitle);

		int colTitleCount = driver.getCountOfElementsWithXPath(".//*[@id='main']/div/div/table/tbody/tr[1]/th");
		for (int i = 1; i <= colTitleCount; i++) {
			String colTitle = driver.findElement(By.xpath(".//*[@id='main']/div/div/table/tbody/tr[1]/th[" + i + "]"))
					.getText();
			expectedPageContent.add(colTitle);
		}

		int bottomTipsCount = driver.getCountOfElementsWithXPath(".//td/p/b");
		if (bottomTipsCount > 0) {
			for (int j = 1; j <= bottomTipsCount; j++) {
				String tipsAtBottom = driver.findElement(By.xpath(".//td[@style='text-align:left;']/p[" + j + "]/b"))
						.getText();
				expectedPageContent.add(tipsAtBottom);
			}
		}

		CustomLogger.log("Gathered the Contents from Search sweeps stakes page..");

		return expectedPageContent;
	}

	/**
	 * This function is to validate the URL for the given link
	 * 
	 * @param: Link
	 *             for which validating the url
	 * @param: Expected
	 *             URL
	 */
	public void verifyLinkURL(HtmlElement link, String expectedURL) {
		String mainWindow;
		try {
			mainWindow = driver.getWindowHandle();
			String LinkURL;
			link.click();
			Common.sleepFor(5000);
			if (driver.getWindowHandles().size() > 1) {
				driver.switchToChildWindow(mainWindow);
				LinkURL = driver.getCurrentUrl();
				CustomLogger.log("Verifying the URL of link - " + LinkURL);
				Assert.assertTrue(LinkURL.contains(expectedURL));
				driver.close();
				driver.switchTo().window(mainWindow);
			} else {
				LinkURL = driver.getCurrentUrl();
				CustomLogger.log("Verifying the URL of link - " + LinkURL);
				Assert.assertTrue(LinkURL.contains(expectedURL));
			}
		} catch (WebDriverException wde) {
			CustomLogger.log(wde.toString());
		}
	}

	public HtmlElement officialRulesEntryDeadline() {
		return driver.findElement(By.linkText("Official Rules/Entry Deadline"));// By.xpath(".//*[@id='uni_header']/div[1]/div/a[4]"));
	}

	public HtmlElement seeSweepStakesFacts() {
		return driver.findElement(By.xpath(".//*[@id='uni_header']/div[1]/div/a[contains(text(), 'Sweepstakes')]"));
	}

	public boolean verifyHeaderLinksURLonRegistrartionPage() {
		boolean isRegistrationPageHeaderLinksVerified = false;
		try {

			driver.waitForBrowserToLoadCompletely();
			// verify URL's for header links
			verifyLinkURL(officialRulesEntryDeadline(), "http://rules.pch.com/viewrulesfacts?type=searchreg");
			verifyLinkURL(seeSweepStakesFacts(), "http://rules.pch.com/viewrulesfacts?type=searchreg#facts");

			isRegistrationPageHeaderLinksVerified = true;
		} catch (Exception e) {
			CustomLogger.logException(e);
		}
		return isRegistrationPageHeaderLinksVerified;
	}

	public void validateNFSP(String nfsp) {
		CustomLogger.log("Validating NFSP from browser console: " + nfsp);
		// CustomLogger.log(getBrowserConsoleSearchLog());
		Assert.assertTrue(getBrowserConsoleSearchLog().contains("source=" + nfsp));
		CustomLogger.log("Validated NFSP from browser console 'source=" + nfsp + "'");
	}

	public void validateSegment(String segment) {
		CustomLogger.log("Validating segment from browser console: " + segment);
		// CustomLogger.log(getBrowserConsoleSearchLog());
		Assert.assertTrue(getBrowserConsoleSearchLog().toString().contains("segment=" + segment));
		CustomLogger.log("Validated segment from browser console 'segment=" + segment + "'");
	}

	public void clearBrowserCookies() {
		System.out.println(driver.manage().getCookieNamed("pci"));
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		System.out.println(driver.manage().getCookieNamed("pci"));
		CustomLogger.log("Cleared all browser cookies.");
	}

}
