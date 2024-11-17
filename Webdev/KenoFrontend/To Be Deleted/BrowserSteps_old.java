package com.pch.kenofrontend.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.pch.kenofrontend.pages.HomePage;
import com.pch.kenofrontend.pages.JoomlaAdminPage;
import com.pch.kenofrontend.pages.OAMPage;
import com.pch.kenofrontend.utilities.PropertiesReader;

import net.thucydides.core.ThucydidesSystemProperty;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;


/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class BrowserSteps_old extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  static int browserRetryCount =
			Integer.parseInt(System.getProperty("webdriver.instance.retry.count","5"));
	private static int browserAttempt = 1;
	HomePage kenoHomePage;
	JoomlaAdminPage jap;
	OAMPage oamp;
	
	WebDriver driver = null;

	public void OpenFirefoxProfile(){
		try{
			FirefoxProfile firefoxProfile = new FirefoxProfile();
//			firefoxProfile.setPreference("network.automatic-ntlm-auth.trusted-uris", PropertiesReader.getInstance().kenoUrl.split("//")[1]);
//			firefoxProfile.setPreference("network.negotiate-auth.delegation-uris", PropertiesReader.getInstance().kenoUrl.split("//")[1]);
//			firefoxProfile.setPreference("network.negotiate-auth.trusted-uris", PropertiesReader.getInstance().kenoUrl.split("//")[1]);
//			firefoxProfile.setPreference("webdriver.load.strategy", "unstable");
			/*Below code is to support adblocker
			firefoxProfile.setPreference("webdriver.load.strategy", "unstable");
			//firefoxProfile.setPreference("extensions.bootstrappedAddons", "{\"adblockultimate@adblockultimate.net\":{\"version\":\"2.26\",\"type\":\"extension\",\"descriptor\":\"C:\\\\Users\\\\kramu\\\\workspacemarsKeno\\\\KenoBackend\\\\src\\\\test\\\\resources\\\\browerDrivers\\\\adblocker_ultimate-2.26.xpi\",\"multiprocessCompatible\":false,\"runInSafeMode\":true}}");
			firefoxProfile.setPreference("extensions.alwaysUnpack", true);
			firefoxProfile.addExtension(new File("src\\test\\resources\\browerDrivers\\adblocker_ultimate-2.26.xpi"));
			 */
//			firefoxProfile.setPreference("app.update.enabled", false); //Remove this line if above adblocker code is enabled
//			firefoxProfile.setAcceptUntrustedCertificates(true);
//			firefoxProfile.setEnableNativeEvents(false);
//	Serenity.useFirefoxProfile(firefoxProfile);
//			kenoHomePage.getDriver().manage().window().maximize();
		}
		catch(Exception ex){
			System.out.println("Error while initializing Driver object: "+ex.getMessage());
			String bindErrorMessage = "Unable to bind to locking port";
			if ((ex.getMessage().contains(bindErrorMessage)) && (browserAttempt<=browserRetryCount)) {
				System.out.println("Attempt 2: browser instance creation!");
				browserAttempt++;
				int newPort = 7060+new java.util.Random().nextInt(10);
				System.out.println("newPort="+newPort);
				System.setProperty("webdriver.firefox.port", newPort+"");
				OpenFirefoxProfile();                          
			} else {
				System.out.println("Not able to create a driver object"+ex.getMessage());
			}

		}		

	}

	@Step
	public void OpenKenoHomePage(){

		EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
		String browserName = variables.getProperty(ThucydidesSystemProperty.WEBDRIVER_DRIVER);
		/*Below code is to support adblocker
		System.out.println(variables.getProperty(ThucydidesSystemProperty.CHROME_SWITCHES));
		System.out.println(SerenitySystemProperties.getProperties());
		System.out.println(variables.getProperty("thucydides.driver.capabilities"));
		System.out.println(variables.getProperty(ThucydidesSystemProperty.WEBDRIVER_REMOTE_URL));
		List<String> keys=variables.getKeys();
		//variables.setProperty(, "--load-extension=src\\test\\resources\\browerDrivers\\SuperBlock-Adblocker_v;--start-maximized;--no-sandbox;--ignore-certificate-errors");
		//	variables.setProperty(ThucydidesSystemProperty.THUCYDIDES_DRIVER_CAPABILITIES., "");
		String remoteBrowserName = variables.getProperty(ThucydidesSystemProperty.WEBDRIVER_REMOTE_DRIVER);
		String remoteBrowserName11 = variables.getProperty(ThucydidesSystemProperty.GECKO_FIREFOX_OPTIONS);
		System.out.println(remoteBrowserName11);
		 
		if((browserName!=null && browserName.equalsIgnoreCase("Chrome")) || (remoteBrowserName!=null && remoteBrowserName.equalsIgnoreCase("Chrome"))) 
		*/
		if(browserName.equalsIgnoreCase("Chrome")) 
		{
					
			kenoHomePage.getDriver().get(PropertiesReader.getInstance().kenoUrl);
			getDriver().manage().window().maximize();
			
		}
		else
		{
		OpenFirefoxProfile();
		kenoHomePage.getDriver().get(PropertiesReader.getInstance().kenoUrl);
		getDriver().manage().window().maximize();
		}
	    
	}

	
	@Step
	public void OpenKenoAdminPage(){

		EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
		String browserName = variables.getProperty(ThucydidesSystemProperty.WEBDRIVER_DRIVER);

		if(browserName.equalsIgnoreCase("Chrome")) 
		{

			jap.getDriver().get(PropertiesReader.getInstance().kenoAdminUrl);
			getDriver().manage().window().maximize();

		}
		else
		{
			OpenFirefoxProfile();
			jap.getDriver().get(PropertiesReader.getInstance().kenoAdminUrl);
		}

	}

}
