package com.pch.search.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

public class BrowserDriverImpl implements BrowserDriver {
	private WebDriver driver;

	public BrowserDriverImpl(WebDriver driver) {
		this.driver = driver;
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public void get(String url) {
		try {
			driver.get(url);
			driver.switchTo().alert().accept();
			// driver.getTitle();
		} catch (NoAlertPresentException napee) {
			CustomLogger.log("No Alert found while loading page");
		}
		if (Environment.getBrowserType().equalsIgnoreCase("chrome"))
			waitForBrowserToLoadCompletely();

	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public List<HtmlElement> findElements(By by) {
		List<HtmlElement> elements = new ArrayList<HtmlElement>();
		List<WebElement> webElements = (new WebDriverWait(driver, 10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));

		for (WebElement e : webElements) {
			elements.add(new HtmlElementImpl(e, this));
		}
		return elements;
	}

	public HtmlElement findElement(By by) {	
		return findElement(by, 20);
	}

	public HtmlElement findElement(By by, int waitingTimeInSeconds) {
		WebElement element = (new WebDriverWait(driver, waitingTimeInSeconds))
				.until(ExpectedConditions.presenceOfElementLocated(by));

		return new HtmlElementImpl(element, this);
	}

	public HtmlElement waitForElementToBeClickable(By by, int waitingTimeInSeconds) {
		if (Environment.getParam("logLevel").toUpperCase().equals("DEBUG")) {
			CustomLogger.log("waiting for the element: " + by.toString() + " to be clickable");
		}

		WebElement element = (new WebDriverWait(driver, waitingTimeInSeconds))
				.until(ExpectedConditions.elementToBeClickable(by));

		return new HtmlElementImpl(element, this);
	}

	public HtmlElement waitForElementToBeVisible(By by, int waitingTimeInSeconds) {
		if (Environment.getParam("logLevel").toUpperCase().equals("DEBUG")) {
			CustomLogger.log("waiting for the element: " + by.toString() + " to be visible");
		}
		WebElement element = (new WebDriverWait(driver, waitingTimeInSeconds))
				.until(ExpectedConditions.visibilityOfElementLocated(by));

		return new HtmlElementImpl(element, this);
	}

	public HtmlElement waitForElementToBeVisible(WebElement ele, int waitingTimeInSeconds) {
		if (Environment.getParam("logLevel").toUpperCase().equals("DEBUG")) {
			CustomLogger.log("waiting for the element: " + ele.toString() + " to be visible");
		}
		WebElement element = (new WebDriverWait(driver, waitingTimeInSeconds))
				.until(ExpectedConditions.visibilityOf(ele));

		return new HtmlElementImpl(element, this);
	}

	public void waitForElementToBeDisappear(By by, int waitingTimeInSeconds) {
		if (Environment.getParam("logLevel").toUpperCase().equals("DEBUG")) {
			CustomLogger.log("waiting for the element: " + by.toString() + " to be invisible");
		}
		CustomLogger.log("waiting for the element: " + by.toString() + " to be invisible");
		new WebDriverWait(driver, waitingTimeInSeconds).until(ExpectedConditions.invisibilityOfElementLocated(by));

	}

	public boolean isAlertPresent(int waitingTimeInSeconds) {
		boolean isAlertPresent = false;
		CustomLogger.log("validaing if alert is present or not");
		try {
			WebDriverWait wait = new WebDriverWait((WebDriver) driver,
					waitingTimeInSeconds /* timeout in seconds */);
			if (!(wait.until(ExpectedConditions.alertIsPresent()) == null)) {
				isAlertPresent = true;
				CustomLogger.log("Alert is Present on the page");
			}
		} catch (NoAlertPresentException e) {
			// do nothing, it will return false
			CustomLogger.log("No Alert found on the page");
		}
		return isAlertPresent;
	}

	public String getPageSource() {
		return driver.getPageSource();
	}

	public void close() {
		driver.close();

	}

	public void quit() {
		driver.quit();

	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	public WebDriver getwrappedDriver() {
		return driver;
	}

	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	public void switchtoFrame(String frameId) {
		driver.switchTo().frame(frameId);
		// (new WebDriverWait(driver,
		// 60)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
	}

	public void switchTo_iframe(String xpathExpression) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().frame(driver.findElement(By.xpath(xpathExpression)));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Options manage() {
		return driver.manage();
	}

	public Navigation navigate() {
		return driver.navigate();
	}

	public void waitForBrowserToLoadCompletely() {
		String state = null;
		String oldstate = null;
		try {
			System.out.print("Waiting for browser loading to complete");
			int i = 0;
			while (i < 5) {
				Thread.sleep(1000);
				state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
				System.out.print("." + Character.toUpperCase(state.charAt(0)) + ".");
				if (state.equals("interactive") || state.equals("loading"))
					break;
				/*
				 * If browser in 'complete' state since last X seconds. Return.
				 */

				if (i == 1 && state.equals("complete")) {
					System.out.println();
					return;
				}
				i++;
			}
			i = 0;
			oldstate = null;
			Thread.sleep(2000);

			/*
			 * Now wait for state to become complete
			 */
			while (true) {
				state = ((JavascriptExecutor) driver).executeScript("return document.readyState;").toString();
				System.out.print("." + state.charAt(0) + ".");
				if (state.equals("complete"))
					break;

				if (state.equals(oldstate))
					i++;
				else
					i = 0;
				/*
				 * If browser state is same (loading/interactive) since last 60
				 * secs. Refresh the page.
				 */
				if (i == 15 && state.equals("loading")) {
					System.out.println("\nBrowser in " + state + " state since last 60 secs. So refreshing browser.");
					driver.navigate().refresh();
					System.out.print("Waiting for browser loading to complete");
					i = 0;
				} else if (i == 6 && state.equals("interactive")) {
					System.out.println(
							"\nBrowser in " + state + " state since last 30 secs. So starting with execution.");
					return;
				}

				Thread.sleep(4000);
				oldstate = state;

			}
			System.out.println();

		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	/**
	 * Capture screenshot local or remote and return the fileobject to
	 * screenshot
	 */
	public File takeScreenShot() {
		File screenshot = null;
		// if(!"ie".contains(Environment.getBrowserType())){

		try {
			screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		} catch (Exception e) {
			CustomLogger.logException(e);
			screenshot = new File(System.getProperty("user.dir") + "//src//test//resources//ScreenshotError.png");
		}

		// }
		return screenshot;
	}

	/**
	 * Use to execute a java Script
	 */

	public Object executeScript(String script, Object... args) {
		return ((JavascriptExecutor) driver).executeScript(script, args);
	}

	public SelectList findSelectList(By by) {
		SelectList s = new SelectList(new Select(driver.findElement(by)));
		return s;
	}

	public int getCountOfElementsWithXPath(String xpath) {
		return driver.findElements(By.xpath(xpath)).size();
	}

	public void openNewTabAndSwitchToIt() {
		// WebElement bodyElement=driver.findElement(By.tagName("body"));
		Actions newTabOpening = new Actions(driver);
		newTabOpening.sendKeys(Keys.CONTROL + "t").perform();

		Common.sleepFor(1000);
		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabs.size() - 1));
	}

	public void switchToTab(int tabIndex) {
		List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabIndex));
	}

	public TextBoxElement findTextBox(By by) {
		TextBoxElement tbe = new TextBoxElement(this.findElement(by));
		return tbe;
	}

	public CheckBoxElement findCheckBox(By by) {
		CheckBoxElement cbe = new CheckBoxElement(this.findElement(by));
		return cbe;
	}

	public int getCountOfElementsWithCSSSelcector(String cssSelector) {
		return driver.findElements(By.cssSelector(cssSelector)).size();
	}

	/**
	 * To move the focus to the Child window from the Main window
	 *
	 * @param mainWindow
	 *            Handle of the Main Window
	 */
	public boolean switchToChildWindow(String mainWindow) {

		boolean switched = false;
		CustomLogger.log("Size of Windows : " + driver.getWindowHandles().size());
		for (int i = 0; i < 10; i++) {
			if (driver.getWindowHandles().size() > 1) {
				break;
			}
			CustomLogger.log("Still Waiting " + i + " Seconds are over");
			Common.sleepFor(1000);
		}
		CustomLogger.log("Size of Windows After Switch : " + driver.getWindowHandles().size());
		Set<String> windows = driver.getWindowHandles();

		for (String window : windows) {
			if (!window.contains(mainWindow)) {
				driver.switchTo().window(window);
				switched = true;
			}
		}
		if (!switched) {
			CustomLogger.log("Please check either you should not call switch to Window Or "
					+ "Child window did not appear in given Time");
		}
		return switched;
	}

	public void maximizeWindow() {
		driver.manage().window().maximize();
	}

	@Override
	public void resizeWindow(int x, int y) {
		driver.manage().window().setSize(new org.openqa.selenium.Dimension(x, y));

	}

	@Override
	public void switchToFrame(WebElement element) {
		// TODO Auto-generated method stub
		driver.switchTo().frame(element);
	}

}
