package com.pch.survey.webdrivers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.pch.survey.utilities.ConfigurationReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebdriverBuilder {
	static WebDriver driver;
	static String browser;
	static String deviceType;
	static String driverType;
	public static String featureName;
	public static String scenarioName;

	static {
		browser = ConfigurationReader.getInstance().getBrowser();
		deviceType = ConfigurationReader.getInstance().getDeviceType();
		driverType = ConfigurationReader.getInstance().getDriverType();
	}

	private static WebDriver createDriver() {
		System.out.println("********************        Creating new  Driver *********************************");
//		System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver_116.exe");
//		System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
//		System.setProperty("webdriver.edge.driver", "src/test/resources/drivers/msedgedriver.exe");
		System.setProperty("webdriver.http.factory", "jdk-http-client"); // Backwards compatibility with Java 8
		WebDriverManager.chromedriver().setup();
		WebDriverManager.edgedriver().setup();
		WebDriverManager.firefoxdriver().setup();

		if (driverType.toLowerCase().equals("local")) {
			if (browser.toLowerCase().equals("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

				if (deviceType.toLowerCase().equals("mobile")) {
					Map<String, Object> deviceMetrics = new HashMap<>();
					Map<String, Object> mobileEmulation = new HashMap<>();
					deviceMetrics.put("width", 384);
					deviceMetrics.put("height", 740);
					mobileEmulation.put("deviceMetrics", deviceMetrics);
					mobileEmulation.put("userAgent",
							"Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");
					options.setExperimentalOption("mobileEmulation", mobileEmulation);
					options.addExtensions(new File("src/test/resources/drivers/ChromeEventTracker.crx"));
					options.addArguments("--remote-allow-origins=*");
					driver = new ChromeDriver(options);

				} else {
					options.addArguments("--remote-allow-origins=*");
					options.addArguments("--no-sandbox");
					options.addArguments("--disable-dev-shm-usage");
					options.addArguments("--start-maximized"); // open Browser in maximized mode
					options.addExtensions(new File("src/test/resources/drivers/ChromeEventTracker.crx"));
					driver = new ChromeDriver(options);
				}
			} else if (browser.toLowerCase().equals("firefox")) {
				FirefoxOptions options = new FirefoxOptions();
				options.setProfile(new FirefoxProfile());
				options.setAcceptInsecureCerts(true);

				if (deviceType.toLowerCase().equals("mobile")) {
					Map<String, Object> mobileEmulation = new HashMap<>();
				} else {
					options.addArguments("start-maximized");
				}
				driver = new FirefoxDriver(options);

			} else {
				EdgeOptions options = new EdgeOptions();
				if (deviceType.toLowerCase().equals("mobile")) {
				} else {
					options.addArguments("start-maximized");
				}
				driver = new EdgeDriver(options);
				driver.manage().window().maximize();
			}
		} else if(driverType.toLowerCase().equals("browserstack")){
			driver = BrowserStackDriver.newDriver();
		}
		else {
			ChromeOptions options = new ChromeOptions();
			options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
//			options.addArguments("--headless");
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--start-maximized"); // open Browser in maximized mode
			options.addExtensions(new File("src/test/resources/drivers/ChromeEventTracker.crx"));
			try {
				driver = new RemoteWebDriver(
						new URL(ConfigurationReader.getInstance().getGriHubUrl()),options);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(90));
		return driver;
	}

	public static WebDriver getDriver() {
		if (driver == null) {
			driver = createDriver();
		}
		return driver;
	}

	public static void closeDriver() {
//		driver.close();
		driver.quit();
		driver = null;
	}
	
 
}
