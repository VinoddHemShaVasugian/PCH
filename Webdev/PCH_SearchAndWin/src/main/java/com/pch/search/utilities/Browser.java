package com.pch.search.utilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverLogLevel;
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
		String browserName = Environment.getBrowserType();
		final BrowserDriverImpl driver = startDriver(browserName);
		// delete cookies
		driver.manage().deleteAllCookies();
		// set the implicit timeout
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		if (!(browserName.toUpperCase().contains("ANDROID") || browserName.toUpperCase().contains("CHROME_IPAD")
				|| browserName.toUpperCase().contains("IOS"))) {

			driver.manage().window().maximize();

		}

		return driver;
	}

	protected static void maximizeWindow(final BrowserDriverImpl driver) {
		driver.manage().window().maximize();
	}

	@SuppressWarnings("unused")
	private static FirefoxProfile getFirefoxProfile() {
		if (null == Environment.getFirefoxProfile()) {
			return new FirefoxProfile();
		}

		return new ProfilesIni().getProfile(Environment.getFirefoxProfile());
	}

	public static void setSystemProperty(final String propKey, final String value) {
		if (null == value) {
			return;
		}

		System.setProperty(propKey, value);
	}

	public static BrowserDriverImpl startDriver(String browserName) throws Exception {
		// to avoid capability error
		if (browserName.toUpperCase().equals("IE")) {
			browserName = "internet explorer";
		}
		if (browserName.toUpperCase().equals("IE11")) {
			browserName = "EDGE";
		}

		WebDriver driver = null;
		CustomLogger.log("Starting " + browserName.toUpperCase() + " browser ...");
		String wrongBrowserMsg = "";
		DesiredCapabilities capabilities = null;

		// CONFIGURATION: create desired capabilities for the chosen browser
		switch (browserName.toUpperCase()) {
		// Desktop Browser Invoking
		case "FIREFOX": {
			// deactivate native events (uses JS instead).
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile.setPreference("browser.private.browsing.autostart", true);
			firefoxProfile.setEnableNativeEvents(false);
			capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile.toString());
			break;
		}
		case "INTERNET EXPLORER": {
			// Avoid security restrictions from the browser
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
			capabilities.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.LOG_FILE, "reports/iedriver.log");
			capabilities.setCapability(InternetExplorerDriver.LOG_LEVEL,
					InternetExplorerDriverLogLevel.DEBUG.toString());
			capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			capabilities.setJavascriptEnabled(true);
			capabilities.setCapability("ignoreZoomSetting", true);
			capabilities.setCapability("ignoreProtectedModeSettings", true);
			// Delete Browser Cache since IE does not open a clean profile
			// unlike Chrome & FireFox
			Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 16384");
			break;
		}
		case "CHROME": {
			capabilities = DesiredCapabilities.chrome();
		}
		// Tablet Browser Invoking
		case "CHROME_ANDROID_TABLET": {
			// Lollipop version
			ChromeOptions options = new ChromeOptions();
			options.addArguments(
					"--user-agent=Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/6.0 Chrome/58.0.3029.81 Safari/537.36");

			capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			break;
		}
		// Not Supported by IOS Driver
		/*
		 * case "SAFARI_IPAD": { // iPad 8.1.3, Mobile Safari 8.0 ChromeOptions
		 * options = new ChromeOptions(); options.addArguments(
		 * "--user-agent=Mozilla/5.0 (iPad; CPU OS 8_1_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12B466 Safari/600.1.4"
		 * ); capabilities = new DesiredCapabilities();
		 * capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		 * capabilities.setCapability(CapabilityType.ForSeleniumServer.
		 * ENSURING_CLEAN_SESSION, true); break; }
		 */
		// Mobile Browser Invoking
		case "CHROME_ANDROID_PHONE": {
			// Lollipop version
			ChromeOptions options = new ChromeOptions();
			options.addArguments(
					"--user-agent=Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/6.0 Chrome/58.0.3029.81 Mobile Safari/537.36");

			capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			break;
		}
		// Not Supported by IOS Driver
		/*
		 * case "SAFARI_IOS_PHONE": { // iOS 9 ChromeOptions options = new
		 * ChromeOptions(); options.addArguments(
		 * "--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 10_3 like Mac OS X) AppleWebKit/603.1.23 (KHTML, like Gecko) Version/8.0 Mobile/14E5239e Safari/602.1"
		 * ); capabilities = new DesiredCapabilities();
		 * capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		 * capabilities.setCapability(CapabilityType.ForSeleniumServer.
		 * ENSURING_CLEAN_SESSION, true); break; }
		 */
		case "CHROME_IOS_PHONE": {
			// iOS 9
			ChromeOptions options = new ChromeOptions();
			options.addArguments(
					"--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 CriOS/56.0.2924.75 Mobile/12A366 Safari/600.1.4");
			capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			break;
		}
		default: {
			capabilities = DesiredCapabilities.firefox();
		}
		}
		// Set platform, browser type and version in capabilities
		capabilities.setPlatform(Platform.ANY);
		capabilities.setBrowserName(browserName);
		if (!Environment.getParam("browserVersion").trim().equals(""))
			capabilities.setVersion(Environment.getParam("browserVersion"));

		// INSTANTIATION: Launch browser either using Selenium Grid or locally
		if (Environment.getParam("SeleniumGrid").toUpperCase().trim().equals("YES")) {

			// create driver with desired capabilities
			driver = new RemoteWebDriver(new URL(Environment.getParam("hubURL")), capabilities);

		} else {
			// Show OS information
			CustomLogger.log("OS Information: " + System.getProperty("os.name") + "(" + System.getProperty("os.version")
					+ ") - " + System.getProperty("os.arch"));

			// Execute Selenium tests locally
			switch (browserName.toUpperCase()) {

			case "FIREFOX": {
				// Set drivers location
				System.setProperty("webdriver.firefox.marionette", "\\BrowserDriver\\geckodriver.exe");
				FirefoxProfile prof = new FirefoxProfile();
				prof.setPreference("browser.startup.homepage_override.mstone", "ignore");
				prof.setPreference("startup.homepage_welcome_url.additional", "about:blank");
				driver = new FirefoxDriver(prof);
				break;
			}
			case "CHROME": {
				// Set drivers location
				String chromeDriverFilePath = System.getProperty("user.dir") + "\\BrowserDriver\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeDriverFilePath);
				driver = new ChromeDriver();
				break;
			}
			case "INTERNET EXPLORER": {
				// Set drivers location
				String ieDriverFilePath = System.getProperty("user.dir") + "\\BrowserDriver\\IEDriverServer.exe";
				System.setProperty("webdriver.ie.driver", ieDriverFilePath);
				// create driver
				driver = new InternetExplorerDriver(capabilities);
				break;
			}
			case "EDGE": {
				String edgeDriverFilePath = System.getProperty("user.dir") + "\\BrowserDriver\\MicrosoftWebDriver.exe";
				System.setProperty("webdriver.edge.driver", edgeDriverFilePath);
				// Create driver
				// capabilities = DesiredCapabilities.edge();
				driver = new EdgeDriver();
				break;
			}
			case "CHROME_ANDROID_TABLET": {
				// Lollipop version
				ChromeOptions options = new ChromeOptions();
				options.addArguments(
						"--user-agent=Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/6.0 Chrome/58.0.3029.81 Safari/537.36");
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				String chromeDriverFilePath = System.getProperty("user.dir") + "\\BrowserDriver\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeDriverFilePath);
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().setSize(new org.openqa.selenium.Dimension(420, 620));
				break;
			}
			case "CHROME_IOS_PHONE": {
				// iOS 9
				ChromeOptions options = new ChromeOptions();
				options.addArguments(
						"--user-agent=Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 CriOS/56.0.2924.75 Mobile/12A366 Safari/600.1.4");
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				String chromeDriverFilePath = System.getProperty("user.dir") + "\\BrowserDriver\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeDriverFilePath);
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
				break;
			}
			case "CHROME_ANDROID_PHONE": {
				// iOS 9
				ChromeOptions options = new ChromeOptions();
				options.addArguments(
						"--user-agent=Mozilla/5.0 (Linux; Android 6.0.1; Nexus 5 Build/LMY48B; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/6.0 Chrome/58.0.3029.81 Mobile Safari/537.36");
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				String chromeDriverFilePath = System.getProperty("user.dir") + "\\BrowserDriver\\chromedriver.exe";
				System.setProperty("webdriver.chrome.driver", chromeDriverFilePath);
				driver = new ChromeDriver(capabilities);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				driver.manage().window().setSize(new org.openqa.selenium.Dimension(360, 640));
				break;
			}
			default: {
				// deactivate native events (uses JS instead).
				FirefoxProfile profile = new FirefoxProfile();
				profile.setEnableNativeEvents(false);
				driver = new FirefoxDriver(profile);
				wrongBrowserMsg = "[NOTE] The supported browsers are: FIREFOX, CHROME, INTERNET_EXPLORER. You asked for '"
						+ Environment.getParam("browserName")
						+ "' instead. Tests will be performed in Firefox by default.";
			}
			}
		}

		if (!wrongBrowserMsg.isEmpty()) {
			CustomLogger.log(wrongBrowserMsg);
		}
		return new BrowserDriverImpl(driver);
	}

}
