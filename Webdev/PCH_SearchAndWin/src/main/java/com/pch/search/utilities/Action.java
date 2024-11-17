package com.pch.search.utilities;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;

import com.pch.search.Bots.AdvertisementKiller;
import com.pch.search.Bots.BOT_TYPE;
import com.pch.search.Bots.Bot;
import com.pch.search.Bots.LastActivity;
import com.pch.search.Bots.Toaster;
import com.pch.search.Bots.TokenCounter;
import com.pch.search.bean.LastActivityBotResultBean;
import com.pch.search.bean.ToasterBotResultBean;
import com.pch.search.bean.TokenCounterBotResultBean;

public abstract class Action {
	// DELAY is a value which will be used in waitForElement method and can be
	// increased if the environment is slow.
	// private static final Integer DELAY = 3;
	protected BrowserDriver driver;
	protected Integer WAIT_PERIOD;
	protected static String REPORT_DIRECTORY;
	protected static String DOMAIN;
	protected boolean EXPLICIT_WAIT = false;
	protected ToasterBotResultBean botResult = null;
	protected static ArrayList<Bot> runningBotsList = null;
	protected String areaLocatorXPath = null;
	protected ToasterBotResultBean toasterResult;
	protected LastActivityBotResultBean lastActivity;
	protected TokenCounterBotResultBean tokenCounter;
	protected CountDownLatch startSignal;
	protected int timeUnit = 20;

	@SuppressWarnings("deprecation")
	private void initializeBot(BOT_TYPE botType) {
		/*
		 * Check whether a bot of this instance already present.
		 */
		for (Bot bot : runningBotsList) {
			if (bot.getbotName().equals(botType.name())) {
				/*
				 * Check if thread is running, if yes kill the thread
				 */
				if (bot.isAlive()) {
					bot.stop();
				}
				// Remove instance of Bot from running bots list
				runningBotsList.remove(bot);
				break;
			}
		}

		Bot b = null;
		if (botType.name().equals(BOT_TYPE.LAST_ACTIVITY.name())) {
			b = new LastActivity(driver, startSignal);
		} else if (botType.name().equals(BOT_TYPE.TOASTER.name())) {
			b = new Toaster(driver, startSignal);
		} else if (botType.name().equals(BOT_TYPE.TOKENS_COUNTER.name())) {
			b = new TokenCounter(driver, startSignal);
			/*
			 * }else
			 * if(botType.name().equals(BOT_TYPE.LIGHTBOX_DISMISSER.name())){
			 * runningBotsList.add(new LightBoxDismisser(driver));
			 */
		} else if (botType.name().equals(BOT_TYPE.AD_KILLER.name())) {
			b = new AdvertisementKiller(driver, startSignal);
		}

		b.setName("#" + Thread.currentThread().getId() + "#");
		runningBotsList.add(b);
	}

	private void launchBots() {
		for (Bot bot : runningBotsList) {
			bot.start();
			while (bot.getbotStatus() == 0) {
				CustomLogger.log("Waiting for bot - " + bot.getbotName() + " to activate.");
				Common.sleepFor(2000);
			}
		}
	}

	protected void activateBots(BOT_TYPE... botype) {
		startSignal = new CountDownLatch(1);
		CustomLogger.log("Activating Requested Bots");
		// if(runningBotsList==null)
		runningBotsList = new ArrayList<Bot>();

		for (BOT_TYPE botType : botype) {
			initializeBot(botType);
		}
		launchBots();
	}

	protected Object getBotResult(BOT_TYPE botType) {
		Object result = null;
		if (runningBotsList == null) {
			result = "Bot result = null, as bots weren't activated.";
			CustomLogger.log("Result from " + botType.name() + "............" + result.toString());
			return result;
		}
		for (Bot bot : runningBotsList) {
			if (bot.getbotName().equals(botType.name())) {
				/**
				 * Wait for Bot to finish, if not finished already Poll for
				 * status every 03 seconds.
				 */
				while (bot.getbotStatus() != 2) {
					Common.sleepFor(3000);
				}
				result = bot.getResult();
				CustomLogger.log("Result from " + botType.name() + "............" + result.toString());
				return result;
			}
		}
		result = "Bot was '" + botType + "' not activated.";
		CustomLogger.log("Result from " + botType.name() + "............" + result.toString());
		return result;
	}

	public String getWebPageTitle() {
		return driver.getTitle();
	}

	/*
	 * public void enableExplicitWait() { if (!EXPLICIT_WAIT) {
	 * driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	 * EXPLICIT_WAIT = true; } }
	 * 
	 * public void enableImplicitWait() { if (EXPLICIT_WAIT) {
	 * driver.manage().timeouts() .implicitlyWait(WAIT_PERIOD,
	 * TimeUnit.SECONDS); EXPLICIT_WAIT = false; } }
	 */

	public void refreshPage() {
		CustomLogger.log("Refreshing Page");
		driver.navigate().refresh();
		if (Environment.getBrowserType().equalsIgnoreCase("chrome"))
			driver.waitForBrowserToLoadCompletely();
	}

	public LastActivityBotResultBean getLastActivityBotResult() {
		return lastActivity;
	}

	public ToasterBotResultBean getToastBotResult() {
		return toasterResult;
	}

	public TokenCounterBotResultBean getTokenCounterBotResult() {
		return tokenCounter;
	}

	public void removeAllCookies() {
		driver.manage().deleteAllCookies();
		CustomLogger.log("Deleting all Cookies");
		Common.sleepFor(5000);
	}

	public void removeCookie(String cookieName) {
		driver.manage().deleteCookieNamed(cookieName);
	}

	/**
	 * Search the page for the text passed in
	 *
	 * @param expectedText
	 * @return boolean
	 */
	/*
	 * public boolean verifyTextPresentIn(final String expectedText) { final
	 * String lowerCase = expectedText.toLowerCase();
	 * 
	 * for (int i = 0; i < DELAY * 5; i ++) { if
	 * (driver.getPageSource().toLowerCase().contains(lowerCase)) { return true;
	 * }
	 * 
	 * }
	 * 
	 * return false; }
	 */

	/**
	 * 
	 * @param driver
	 * @param expectedText
	 * @param sec
	 * @return
	 */
	/*
	 * public boolean verifyTextNotPresentIn (WebDriver driver, String
	 * expectedText, int sec) { if
	 * (driver.getPageSource().contains(expectedText)){ Assert.fail(
	 * "[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText +
	 * " HAS BEEN FOUND; TEXT SHOULD NOT APPEAR!"); return false; } else {
	 * return true; }
	 * 
	 * }
	 */

	public String getCurrentURL() {
		String curentlyLoadedURL = driver.getCurrentUrl();
		return curentlyLoadedURL;
	}

	public boolean isLinkPresent(String linkText) {
		try {
			if (areaLocatorXPath == null) {
				if (driver.getCountOfElementsWithXPath(String.format("//a[text()='%s']", linkText)) == 0)
					return false;

				HtmlElement e = driver.findElement(By.linkText(linkText));
				return e.isDisplayed();
			} else {
				HtmlElement areaLocatorElement = driver.findElement(By.xpath(areaLocatorXPath));
				if (areaLocatorElement.getCountOfElementsWithXPath(String.format("//a[text()='%s']", linkText)) == 0)
					return false;

				HtmlElement e = areaLocatorElement.findElement(By.linkText(linkText));
				return e.isDisplayed();
			}
		} catch (WebDriverException e) {
			// CustomLogger.logException(e);
			return false;
		}
	}

	public boolean isTextDisplayed(String displayedText) {
		String xpath = String.format("//*[contains(text(),\"%s\")]", displayedText);
		try {
			if (driver.getCountOfElementsWithXPath(xpath) == 0) {
				return false;
			}
			HtmlElement e = driver.findElement(By.xpath(xpath));
			return e.isDisplayed();
		} catch (WebDriverException e) {
			CustomLogger.logException(e);
			return false;
		}
	}

	/**
	 * This will wait till the element become clickable
	 * 
	 * @param: element
	 *             locator
	 * @return: HtmlElement
	 */
	public HtmlElement waitForElementToBeClickable(By by) {
		HtmlElement element = null;
		try {
			element = driver.waitForElementToBeClickable(by, timeUnit);
		} catch (Exception e) {
			CustomLogger.log("Got: " + e.getClass().getSimpleName() + " for " + by);
		}
		return element;
	}

	/**
	 * This will wait till the element become Visible
	 * 
	 * @param: element
	 *             locator
	 * @return: HtmlElement
	 */
	public HtmlElement waitForElementToBeVisible(By by) {
		HtmlElement element = null;
		int attempts = 0;
		while (attempts < 2) {
			try {
				element = driver.waitForElementToBeVisible(by, timeUnit);
			} catch (ElementNotVisibleException e) {
				CustomLogger.log("Got: " + e.getClass().getSimpleName() + " for " + by);
				CustomLogger.log("Trying againg to find the element");
			}
			attempts++;
		}
		return element;
	}

	/**
	 * This will wait till the element become Visible
	 * 
	 * @param: element
	 *             locator
	 * @return: HtmlElement
	 */
	public void waitForElementToBeDisappear(By by) {
		try {
			driver.waitForElementToBeDisappear(by, timeUnit);
		} catch (Exception e) {
			CustomLogger.log("Got: " + e.getClass().getSimpleName() + " for " + by);
		}

	}

	/**
	 * Wait for Element to be Invisible Additionally it will refresh the page
	 * for each second.
	 * 
	 * @param by
	 */
	public void waitForElementInVisible(By by, int noOfTimesToRefresh) {
		int count = 0;
		while (count < noOfTimesToRefresh) {
			try {
				driver.waitForElementToBeDisappear(by, 1);
				break;
			} catch (Exception e) {
				CustomLogger.log("Got: " + e.getClass().getSimpleName() + " for " + by);
				driver.navigate().refresh();
				count = count + 1;
			}
		}
	}

	/**
	 * Wait for Element to be Invisible Additionally it will refresh the page
	 * for each second.
	 * 
	 * @param by
	 */
	public void waitForElementVisible(By by, int noOfTimesToRefresh) {
		int count = 0;
		while (count < noOfTimesToRefresh) {
			try {
				driver.waitForElementToBeVisible(by, 1);
				break;
			} catch (Exception e) {
				CustomLogger.log("Got: " + e.getClass().getSimpleName() + " for " + by);
				driver.navigate().refresh();
				count = count + 1;
			}
		}
	}

	/**
	 * This function is getting console logs from the browser
	 * 
	 * @return: console logs
	 */
	public String getBrowserConsoleSearchLog() {
		CustomLogger.log(driver.executeScript("return window.PCH.SW.search").toString());
		return driver.executeScript("return window.PCH.SW.search").toString();

	}

	/**
	 * This function is getting console logs from the browser
	 * 
	 * @return: console logs
	 */
	public String getActivityLog(String param) {
		CustomLogger.log("Getting Activity logs from the browser's console");
		CustomLogger.log("Executing return " + param);
		CustomLogger.log("Got " + driver.executeScript("return " + param + "").toString() + " from browser's console");
		return driver.executeScript("return " + param + "").toString();

	}

	public boolean isAlertPresent() {
		boolean isPresent = false;
		try {
			isPresent = driver.isAlertPresent(timeUnit);
		} catch (TimeoutException e) {
			CustomLogger.log("No Alert found on the page");
		}
		return isPresent;
	}

	public boolean isAlertNotPresent() {
		try {
			driver.switchTo().alert();
			return false;
		} // try
		catch (NoAlertPresentException Ex) {
			return true;
		} // catch
	} // isAlertPresent()

	public String getAlertText() {
		String alertText = null;
		try {
			if (isAlertPresent()) {
				CustomLogger.log("Going to get the text on the alert");
				alertText = driver.switchTo().alert().getText().trim();
				CustomLogger.log("Found alert text: " + alertText);
			}
		} catch (NoAlertPresentException e) {
			// do nothing
		}
		return alertText;
	}

	public void acceptAlert() {
		try {
			if (isAlertPresent()) {
				CustomLogger.log("Going to accept the alert");
				driver.switchTo().alert().accept();
				CustomLogger.log("Alert is Accepted");
			}
		} catch (NoAlertPresentException e) {
			// do nothing
		}

	}

	public void dismissAlert() {
		try {
			if (isAlertPresent()) {
				CustomLogger.log("Going to dismiss the alert");
				driver.switchTo().alert().dismiss();
				CustomLogger.log("Alert is dismissed");
			}
		} catch (NoAlertPresentException e) {
			// do nothing
		}

	}
}