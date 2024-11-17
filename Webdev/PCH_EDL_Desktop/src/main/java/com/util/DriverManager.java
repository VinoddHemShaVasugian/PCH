package com.util;

import org.openqa.selenium.WebDriver;

/**
 * @author Mahesh Ambati
 * @since June 21 - 2017
 * 
 */
public class DriverManager {

	// To get and set the driver
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

	public static WebDriver getDriver() {
		return webDriver.get();
	}

	protected static void setWebDriver(WebDriver driver) {
		webDriver.set(driver);
	}
}
