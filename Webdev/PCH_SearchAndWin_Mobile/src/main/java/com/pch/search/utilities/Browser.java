package com.pch.search.utilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser {

	/**
	 * This method will invoke the browser instance. Willn't load any URL
	 * 
	 * @return driver object
	 * @throws Exception
	 */
	protected static BrowserDriverImpl getBrowserInstance() throws Exception {
		// Get the browser details from the POM.xml file
		String device_type = Environment.getBrowserType();
		final BrowserDriverImpl driver = startDriver(device_type);
		// delete cookies
		driver.manage().deleteAllCookies();
		// set the implicit timeout
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		return driver;
	}

	public static void setSystemProperty(final String propKey, final String value) {
		if (null == value) {
			return;
		}
		System.setProperty(propKey, value);
	}

	public static BrowserDriverImpl startDriver(String browserName) throws Exception {
		String URL = "https://" + Environment.getBrowserStackUSername() + ":" + Environment.getBrowserStackAccessKey()
				+ "@hub-cloud.browserstack.com/wd/hub";
		String chromedriver_path = System.getProperty("user.dir") + "\\BrowserDriver\\chromedriver.exe";

		WebDriver driver = null;
		CustomLogger.log("Starting " + browserName.toUpperCase() + " browser ...");
		DesiredCapabilities capabilities = null;

		switch (browserName.toLowerCase()) {
		case "iphone":
			if (Environment.getEnvironmentToRun().equalsIgnoreCase("true")) {
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("browserName", "iPhone");
				caps.setCapability("platform", "MAC");
				caps.setCapability("device", "iPhone 6s");
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.video", "true");
				caps.setCapability("browserstack.debug", "true");
				caps.setCapability("project", "Search&Win");
				caps.setCapability("build", "IOS 9.1");
				caps.setCapability("name", "MobileTestRegression");
				caps.setCapability("deviceOrientation", "portrait");
				caps.setCapability("browserstack.safari.enablePopups", "true");
				caps.setCapability("browserstack.safari.allowAllCookies", "true");
				// caps.setCapability("browserstack.localIdentifier",
				// "Testing12354");
				driver = new RemoteWebDriver(new URL(URL), caps);
			} else {
				ChromeOptions options = new ChromeOptions();
				options.addArguments(
						"--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 CriOS/56.0.2924.75 Mobile/12A366 Safari/600.1.4");
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				System.setProperty("webdriver.chrome.driver", chromedriver_path);
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
			}
			break;
		case "android":
			if (Environment.getEnvironmentToRun().toLowerCase().equalsIgnoreCase("true")) {
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("browserName", "Android");
				caps.setCapability("os", "android");
				caps.setCapability("os_version", "6.0");
				caps.setCapability("realMobile", "true");
				caps.setCapability("device", "Samsung Galaxy S7");
				caps.setCapability("browserstack.local", "true");
				caps.setCapability("browserstack.video", "true");
				caps.setCapability("browserstack.debug", "true");
				caps.setCapability("project", "Search&Win");
				caps.setCapability("build", "Android6");
				caps.setCapability("name", "MobileTestRegression");
				caps.setCapability("deviceOrientation", "portrait");
				// caps.setCapability("browser_version", "58.0.3029.83");
				// caps.setCapability("chromedriverVersion", "2.30.477700");
				driver = new RemoteWebDriver(new URL(URL), caps);
			} else {
				ChromeOptions options = new ChromeOptions();
				options.addArguments(
						"--user-agent=Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/6.0 Chrome/58.0.3029.81 Mobile Safari/537.36");
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				System.setProperty("webdriver.chrome.driver", chromedriver_path);
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().setSize(new org.openqa.selenium.Dimension(360, 640));
			}
			break;
		default: {
			ChromeOptions options = new ChromeOptions();
			options.addArguments(
					"--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 CriOS/56.0.2924.75 Mobile/12A366 Safari/600.1.4");
			capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			System.setProperty("webdriver.chrome.driver", chromedriver_path);
			driver = new ChromeDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
			break;
		}
		}
		return new BrowserDriverImpl(driver);
	}
}
