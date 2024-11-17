package com.util;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.pageobjects.JoomlaConfigPage;

public class BaseClass extends WrapperFunctions {
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final String test_log_path = System.getProperty("user.home") + "//Logs//";
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final String interstitial_config_article = "Config-Interstitial";
	public static boolean debug_Mode = false;

	@BeforeTest(alwaysRun=true)
	public void setup(ITestContext ctx) throws Exception {
		// Clear up the Temp. files
		System.out.println("Running Before test");
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec("cmd /c " + CLEAR_TEMP_FILE);
		process.waitFor();
		ConsolidatedHTMLReport.deleteFile(new File(test_log_path));
		log.info("Test Name: " + ctx.getCurrentXmlTest().getName());
		createInstance(ctx.getCurrentXmlTest().getName());
		// Disable the Interstitial Ad's for the application
//		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
//		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
//				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
/*		admin_instance.goToArticlePage();
		admin_instance.search_for_article(interstitial_config_article);
		admin_instance.enter_text_field_element_by_label("Value", "false", 1);
		admin_instance.save_and_close_article();*/
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
	}

	@AfterTest(alwaysRun=true)
	public void close() {
		try {
			// Enable the Interstitial Ad's for the application
			System.out.println("Running After test");
	/*		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
			admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
					xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
			admin_instance.goToArticlePage();
			admin_instance.search_for_article(interstitial_config_article);
			admin_instance.enter_text_field_element_by_label("Value", "true", 1);
			admin_instance.save_and_close_article();*/
			// Quit the WebDriver Instance
			cleanAndClose();
		} catch (Exception e) {
			log.error("Error in the After Test: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Invoke the browser
	 * 
	 * @param url
	 * @throws Exception
	 */
	protected void invokeBrowser(String url) throws Exception {
		if (DriverManager.getDriver() == null) {
			createInstance();
			DriverManager.getDriver().get(url);
		} else if (DriverManager.getDriver() != null) {
			log.info("Thread id = " + Thread.currentThread().getId());
			DriverManager.getDriver().navigate().to(url);
			sleepFor(1);
		}
	}

	/**
	 * To Quit the WebDriver instance
	 */
	protected void cleanAndClose() {
		if (DriverManager.getDriver() != null) {
			DriverManager.getDriver().quit();
		}
	}

	/**
	 * Delete the browser cookies before the test functionality start execution
	 * 
	 * @throws Exception
	 */
	@BeforeMethod
	public void deleteBrowserCookies(Method method) throws Exception {
		try {
			log.info("==========================================================================================");
			log.info("Test Method Execution started for " + this.getClass().getSimpleName() + "." + method.getName());
			log.info("==========================================================================================");
			step_description.setLength(0);
			if (DriverManager.getDriver() != null) {
				close_all_switch_to_currently_focus_window();
				DriverManager.getDriver().manage().deleteAllCookies();
				invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
			}
		} catch (Exception e) {
			log.error("Error on Before Method: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Just to notify the method complete status
	 * 
	 * @param method
	 */
	@AfterMethod
	public void notify_method_completes(Method method) {
		log.info("==========================================================================================");
		log.info("Test Method Execution Completed for " + this.getClass().getSimpleName() + "." + method.getName());
		log.info("==========================================================================================");
	}
	
	/**
	 * To create unique build version for browserstack execution
	 * 
	 * 
	 */
	@BeforeSuite(alwaysRun=true)
	public void con() {
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
		String strDate = df.format(date);
		build_no=strDate.substring(8).replaceAll(" ", "_").replaceAll(",", "");
	}
}