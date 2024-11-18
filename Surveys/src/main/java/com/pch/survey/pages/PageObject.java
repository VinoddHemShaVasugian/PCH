package com.pch.survey.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pch.survey.utilities.ConfigurationReader;

public class PageObject {

	public static WebDriver driver;

	public PageObject(WebDriver driver) {
		this.driver = driver;

	}

	public PageObject() {
	}

	public WebElement scrollIntoView(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-125)");
		return ele;
	}

	public WebElement scrollIntoView(By by) {
		WebElement ele = driver.findElement(by);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-125)");
		return ele;
	}

	public void selectFromDropdown(WebElement element, String option) {
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(option);
	}

	public boolean waitUntilTextContains(By locator, Pattern text) {
		return (new WebDriverWait(driver, Duration.ofSeconds(45)).until(ExpectedConditions.textMatches(locator, text)));

	}

	public boolean waitUntilPageTitleContains(String title) {

		System.out.println("********************************************************");
		System.out.println(driver.getTitle());
		System.out.println(driver.getCurrentUrl());

		return (new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.titleContains(title)));
	}

	public boolean waitUntilUrlContains(String urlString) {
		switchToLastOpenTab();
		return (new WebDriverWait(driver, Duration.ofSeconds(90)).until(ExpectedConditions.urlContains(urlString)));
	}

	public boolean waitUntilUrlContains(String urlString, int seconds) {
		switchToLastOpenTab();
		return (new WebDriverWait(driver, Duration.ofSeconds(seconds))
				.until(ExpectedConditions.urlContains(urlString)));
	}

	public boolean waitUntilThePageLoads() {

		try {
			long start = System.currentTimeMillis();
			try {
				Thread.sleep(700);
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
			while (System.currentTimeMillis() - start < 10000) {
				if (((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equalsIgnoreCase("complete")) {
					return true;
				} else {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception ignore) {
		}
		return false;
	}

	public WebElement waitUntilElementIsClickable(WebElement Element) {
		return (new WebDriverWait(driver, Duration.ofSeconds(30))
				.until(ExpectedConditions.elementToBeClickable(Element)));
	}

	public WebElement waitUntilElementIsClickable(By Element) {
		return (new WebDriverWait(driver, Duration.ofSeconds(30))
				.until(ExpectedConditions.elementToBeClickable(driver.findElement(Element))));
	}

	public WebElement waitUntilElementIsClickable(int timeout, By Element) {
		return (new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.until(ExpectedConditions.elementToBeClickable(driver.findElement(Element))));
	}

	// To explicitly wait for an element upon timeout seconds
	public void waitUntilElementIsVisible(int timeoutInSeconds, By element) {
		new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	public void waitUntilElementIsVisible(int timeoutInSeconds, WebElement element) {
		new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until(ExpectedConditions.visibilityOf(element));
	}

	public boolean waitForMultipleWindowsToOpen() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return (driver.getWindowHandles().size() > 1);
				}
			});
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void switchToLastOpenTab() {
		if (!ConfigurationReader.getDeviceType().equalsIgnoreCase("mobile"))
			return;
		waitSeconds(2);

		Set<String> handles = driver.getWindowHandles();

		for (String winHandle : handles) {
			driver.switchTo().window(winHandle);
		}

	}

	public List<WebElement> getElementList(WebElement parent, By by) {
		List<WebElement> elements = new ArrayList<WebElement>();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		for (int i = 0; i <= 5; i++) {
			try {
				waitSeconds(3);
				elements = parent.findElements(by);
				break;
			} catch (StaleElementReferenceException stale) {
			} catch (ElementClickInterceptedException ecie) {
			} catch (NoSuchElementException nse) {
			} finally {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			}
		}
		return elements;

	}

	public List<WebElement> getElementList(By by) {
		List<WebElement> elements = new ArrayList<WebElement>();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		try {
			elements = driver.findElements(by);
		} catch (NoSuchElementException e) {
		} finally {
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}
		return elements;

	}

	public void waitSeconds(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}