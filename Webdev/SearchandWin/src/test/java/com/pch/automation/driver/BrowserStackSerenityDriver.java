package com.pch.automation.driver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.browserstack.local.Local;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import net.thucydides.core.webdriver.DriverSource;

public class BrowserStackSerenityDriver implements DriverSource {
	private static Local bsLocal = null;

	public WebDriver newDriver() {
		EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
		try {
			// Configuring the Browser Stack User and Access key from runtime
			String username = System.getenv("BROWSERSTACK_USERNAME");
			String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
			String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
			String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
			String browserStackTCBuild = System.getenv("BROWSERSTACK_BUILD");

			// Configuring the Browser Stack User and Access key from local
			if (username == null && accessKey == null && browserstackLocal == null) {
				username = environmentVariables.getProperty("browserstack.user");
				accessKey = environmentVariables.getProperty("browserstack.key");
				browserstackLocal = "true";
				bsLocal = new Local();
				Map<String, String> bsLocalArgs = new HashMap<String, String>();
				bsLocalArgs.put("key", accessKey);
				if (browserstackLocalIdentifier == null) {
					browserstackLocalIdentifier = "SearchandWin";
				}
				bsLocalArgs.put("localIdentifier", browserstackLocalIdentifier);
				bsLocal.start(bsLocalArgs);
				browserStackTCBuild = "Local_Execution";
				System.out.println("Browser Stack Local Alive:" + bsLocal.isRunning());
			}

			// Adding desired capability as per the browser and device input
			DesiredCapabilities capabilities = new DesiredCapabilities();
			if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
				switch (AppConfigLoader.browserType.toLowerCase()) {
				case "chrome":
					capabilities.setCapability("os", "Windows");
					capabilities.setCapability("browser", "chrome");
					break;
				case "firefox":
					capabilities.setCapability("os", "Windows");
					capabilities.setCapability("browser", "Firefox");
					break;
				default:
					capabilities.setCapability("os", "Windows");
					capabilities.setCapability("browser", "chrome");
				}
			} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Mobile")) {
				switch (AppConfigLoader.browserType.toLowerCase()) {
				case "android":
					capabilities.setCapability("os_version", "7.0");
					capabilities.setCapability("device", "Samsung Galaxy S8");
					capabilities.setCapability("real_mobile", "true");
					break;
				case "iphone":
					capabilities.setCapability("os_version", "11");
					capabilities.setCapability("device", "iPhone 8 Plus");
					capabilities.setCapability("real_mobile", "true");
					break;
				default:
					capabilities.setCapability("os_version", "7.0");
					capabilities.setCapability("device", "Samsung Galaxy S8");
					capabilities.setCapability("real_mobile", "true");
				}
			}

			capabilities.setCapability("browserstack.local", browserstackLocal);
			capabilities.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
			capabilities.setCapability("browserstack.debug", true);
			capabilities.setCapability("browserstack.video", true);
			capabilities.setCapability("project", "SW");
			capabilities.setCapability("browserstack.appium_version", "1.9.1");
			capabilities.setCapability("build", browserStackTCBuild);
			return new RemoteWebDriver(
					new URL("https://" + username + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"),
					capabilities);
		} catch (Exception e) {
			System.out.println(e);
			if (bsLocal != null) {
				try {
					bsLocal.stop();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}

	public boolean takesScreenshots() {
		return true;
	}

	/**
	 * Singleton method to return the instance of the class
	 * 
	 * @return
	 */
	public static Local getLocalInstance() {
		return bsLocal;
	}
}
