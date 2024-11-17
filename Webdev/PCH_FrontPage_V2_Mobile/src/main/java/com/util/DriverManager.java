package com.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Mahesh Ambati, Maniganda Perumal
 * @since June 21 - 2017
 * 
 */
public class DriverManager {

	// To get and set the driver
	private static ThreadLocal<EventFiringWebDriver> event_firing_webdriver = new ThreadLocal<EventFiringWebDriver>();
	private static ThreadLocal<WebDriver> webdriver = new ThreadLocal<WebDriver>();
	private static ThreadLocal<WebDriverWait> webdriver_wait = new ThreadLocal<WebDriverWait>();

	public static WebDriver get_webdriver() {
		return webdriver.get();
	}

	protected static void set_webdriver(WebDriver driver) {
		webdriver.set(driver);
	}

	public static EventFiringWebDriver get_event_firing_webdriver() {
		return event_firing_webdriver.get();
	}

	protected static void set_event_firing_webdriver(EventFiringWebDriver webdriver) {
		event_firing_webdriver.set(webdriver);
	}

	public static WebDriverWait get_webdriver_wait() {
		return webdriver_wait.get();
	}

	protected static void set_webdriver_wait(WebDriverWait webDriver_wait) {
		webdriver_wait.set(webDriver_wait);
	}
}
