package com.util;

/**
 * Created by lsharma on 11/26/2018
 */

import java.net.URL;
import java.util.InputMismatchException;
import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import net.thucydides.core.webdriver.DriverSource;

public class BrowserStackSerenityDriver implements DriverSource {

	final String USERNAME = "archanatiwari3";
	final String AUTOMATE_KEY = "tyBx7JQpeWhqHCnB3kfd";
	final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY
			+ "@hub-cloud.browserstack.com/wd/hub";

	LoggingPreferences logPrefs = new LoggingPreferences();
	DesiredCapabilities capabilities = new DesiredCapabilities();

	@SuppressWarnings("deprecation")
	public WebDriver newDriver() {
		try {
			switch (PropertiesReader.getInstance().getBaseConfig(
					"ExecutionPlatform")) {
			case "REMOTE":
				System.out.println("Building Remote Server...");
				capabilities.setCapability("os", "Windows");
				capabilities.setCapability("os_version", "10");
				capabilities.setCapability("name", "DesktopScenarios");
				capabilities.setCapability("build", "KenoDesktopBuild");
				capabilities.setCapability("project", "Keno");
				capabilities.setCapability("browserstack.local", true);
				capabilities.setCapability("browserstack.debug", true);
				capabilities.setCapability("acceptSslCerts", "true");
				capabilities.setCapability("browserstack.localIdentifier",
						"KenoFrontendTest");
				logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
				capabilities.setCapability(CapabilityType.LOGGING_PREFS,
						logPrefs);
				if (PropertiesReader.getInstance().getBaseConfig("Browser")
						.equalsIgnoreCase("CHROME")) {
					System.out
							.println("Instantiating Remote Chrome browser...");
					ChromeOptions options = new ChromeOptions();
					options.addArguments("window-size=800,480");
					capabilities.setCapability("browser", "Chrome");
					capabilities.setCapability("browser_version", "70.0");
					capabilities.setCapability(ChromeOptions.CAPABILITY,
							options);
					return new RemoteWebDriver(new URL(URL), capabilities);
				 } else if (PropertiesReader.getInstance().getBaseConfig("Browser").equalsIgnoreCase("Firefox")) 
				 {
	                    System.out.println("Instantiating Remote Firefox browser...");
	                    capabilities.setCapability("browser", "Firefox");
	                    capabilities.setCapability("browser_version", "60.0");
	                    return new RemoteWebDriver(new URL(URL), capabilities);
	                }
				 else if (PropertiesReader.getInstance()
						.getBaseConfig("Browser").equalsIgnoreCase("IE")) {
					System.out.println("Instantiating Remote IE browser...");
					capabilities.setCapability("browser", "IE");
					capabilities.setCapability("browser_version", "11.0");
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
					return new RemoteWebDriver(new URL(URL), capabilities);
				} 
				 else
				 {
					throw new InputMismatchException("Invalid Browser Value");
				 }
			//break;
			case "LOCAL":
				System.out.println("Building Local Server...");
				if (PropertiesReader.getInstance().getBaseConfig("Browser")
						.equalsIgnoreCase("CHROME")) {
					System.out.println("Instantiating Local Chrome browser...");
					ChromeOptions options = new ChromeOptions();
					options.addArguments("--no-sandbox");
					options.addArguments("--start-maximized");
					DesiredCapabilities caps = null;
					caps = DesiredCapabilities.chrome();
					logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
					caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
					caps.setCapability(ChromeOptions.CAPABILITY, options);
					System.out
							.println("Chrome browser with Performance Logging Preferences has been instantiated.");
					System.setProperty("webdriver.chrome.driver",
							"src/test/resources/browerDrivers/chromedriver.exe");
					return new ChromeDriver(caps);
				} 
				else if (PropertiesReader.getInstance().getBaseConfig("Browser").equalsIgnoreCase("FIREFOX")) 
				{
	                    System.out.println("Instantiating Local Firefox browser...");
	                    FirefoxOptions options = new FirefoxOptions();
	                    options.addArguments("--no-sandbox");
	                    options.addArguments("--start-maximized");
	                    DesiredCapabilities caps = null;
	                    caps = DesiredCapabilities.firefox();
	                    logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
	                    caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
	                    caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
	                    System.out.println("Firefox browser with Performance Logging Preferences has been instantiated.");
	                    System.setProperty("webdriver.gecko.driver","src/test/resources/browerDrivers/geckodriver.exe");
	                    return new FirefoxDriver(caps);					
					
				} else if (PropertiesReader.getInstance().getBaseConfig("Browser").equalsIgnoreCase("IE")) {
					System.out.println("Instantiating Local IE browser...");
					capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability("requireWindowFocus", true);
					capabilities.setCapability(
							InternetExplorerDriver.IGNORE_ZOOM_SETTING, false);
					capabilities.setCapability("ie.ensureCleanSession", true);
					capabilities
							.setCapability(
									InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
									true);
					capabilities.setCapability(
							InternetExplorerDriver.FORCE_CREATE_PROCESS, true);
					LoggingPreferences logPrefs = new LoggingPreferences();
					logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
					capabilities.setCapability(CapabilityType.LOGGING_PREFS,
							logPrefs);
					System.setProperty("webdriver.ie.driver",
							"src/test/resources/browerDrivers/IEDriverServer.exe");
					return new InternetExplorerDriver(capabilities);
				} else
					throw new InputMismatchException("Invalid Brower Value");
	//			break;
			default:
				throw new InputMismatchException(
						"Invalid Execution Environment Value. It must be REMOTE or LOCAL");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean takesScreenshots() {
		return true;
	}

}
