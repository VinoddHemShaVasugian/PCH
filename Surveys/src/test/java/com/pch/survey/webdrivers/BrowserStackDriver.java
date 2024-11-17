package com.pch.survey.webdrivers;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.browserstack.local.Local;
import com.pch.survey.stepdefinitions.CommonStepDefinitions;
import com.pch.survey.utilities.ConfigurationReader;


public class BrowserStackDriver {

	private static Local bsLocal = null;
   private static long localCnt = System.currentTimeMillis();
	
	
	public static WebDriver newDriver() {

  				
 		try {
			// Configuring the Browser Stack User and Access key from runtime
			  String username  = System.getenv("BROWSERSTACK_USERNAME");
			  String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
			  String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
 	   		  String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
 			  String browserStackTCBuild = System.getProperty("BROWSERSTACK_BUILD");
 			  System.out.println("from system BROWSERSTACK_USERNAME   " + username);
 			  System.out.println("from system BROWSERSTACK_ACCESS_KEY    "+accessKey);
 			  System.out.println("from system BROWSERSTACK_LOCAL    "+browserstackLocal);
 			  System.out.println("from system BROWSERSTACK_LOCAL_IDENTIFIER    "+browserstackLocalIdentifier);
	 		
			
			// Configuring the Browser Stack User and Access key from local
			if (username == null && accessKey == null && browserstackLocal == null ) {
				username  = ConfigurationReader.getInstance().getBrowserStackUserName();
				accessKey = ConfigurationReader.getInstance().getBrowserStackKey();

				browserstackLocal = "true";
				
				bsLocal = new Local();
				Map<String, String> bsLocalArgs = new HashMap<String, String>();
				bsLocalArgs.put("key", accessKey);
				if (browserstackLocalIdentifier == null) {
					browserstackLocalIdentifier =  String.valueOf(new Random().nextInt());
				}
				bsLocalArgs.put("localIdentifier",browserstackLocalIdentifier);
				bsLocal.start(bsLocalArgs);
				System.out.println(bsLocal.toString());
				System.out.println("Browserstack local started");

				browserStackTCBuild = "Survey_Local_Execution -" + localCnt ; 
				System.out.println("Browser Stack Local Alive:" + bsLocal.isRunning());
			}
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("browserName", ConfigurationReader.getInstance().getBrowser());
			capabilities.setCapability("browserVersion", "latest");
 			capabilities.setCapability("acceptInsecureCerts", true);
 			
			if(ConfigurationReader.getInstance().getBrowser().equalsIgnoreCase("chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addExtensions(new File("src/test/resources/drivers/ChromeEventTracker.crx"));
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			}			
			//Browser Stack Options
			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();

		 	
			//Browser Stack Options
			
			ConfigurationReader.getInstance();
			if(ConfigurationReader.getDeviceType().equalsIgnoreCase("mobile")) {
				browserstackOptions.put("deviceName", ConfigurationReader.getInstance().getDeviceName());
			}
			browserstackOptions.put("os", ConfigurationReader.getInstance().getOs());
			browserstackOptions.put("osVersion",ConfigurationReader.getInstance().getOsVersion());
			browserstackOptions.put("local", browserstackLocal);
			browserstackOptions.put("localIdentifier", browserstackLocalIdentifier);
			browserstackOptions.put("debug", false);
			browserstackOptions.put("seleniumLogs", false);
			browserstackOptions.put("video", true);
 			browserstackOptions.put("consoleLogs", "disable");
			browserstackOptions.put("projectName", "PCH Surveys");
			browserstackOptions.put("buildName", browserStackTCBuild);
			browserstackOptions.put("sessionName", CommonStepDefinitions.featureName + " -- " +CommonStepDefinitions.scenarioName );
			browserstackOptions.put("timezone", "New_York");
			browserstackOptions.put("maskCommands", "setValues, getValues, setCookies, getCookies");
			browserstackOptions.put("idleTimeout", 1000);
			browserstackOptions.put("resolution", "1920x1080");
			browserstackOptions.put("maskBasicAuth", true);
	 	
			
			
 			capabilities.setCapability("bstack:options", browserstackOptions);
	 		
	 		browserstackOptions.entrySet().forEach(entry -> {
	 		    System.out.println(entry.getKey() + " " + entry.getValue());
	 		});
	 		
	 	 		
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
 

	public static void stopBsLocal() {

		if (bsLocal != null) {
			try {
				bsLocal.stop();
				System.out.println(bsLocal.toString());
				System.out.println("Browserstack local stopped");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		
		
		
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
