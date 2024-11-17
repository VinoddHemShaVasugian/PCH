package com.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import net.serenitybdd.core.pages.PageObject;
import com.util.BrowserStackSerenityDriver;

public class WrapperFunctions extends PageObject {

	private static Integer DELAY = 1;
	private final static long ACTION_TIMEOUT_TIME = 30000;
	protected int stepNo;
	public static String build_no;
	protected StringBuffer step_description = new StringBuffer();
//	private static WrapperFunctions wrapper_Functions = null;
	static {
		Properties sys_props = System.getProperties();
		System.out.println("System Properties");
		System.out.println(sys_props.get("env_to_execute"));
		System.out.println(sys_props.get("device_to_launch"));
		System.out.println(sys_props.get("browser_to_invoke"));
		System.out.println(sys_props.get("enable_browserstack"));
		System.out.println(sys_props.size());
	}

	protected final static String WORK_DIRECTORY = System.getProperty("user.dir");
	private final static String CHROMEDRIVER_PATH = "src/main/resources/browserdrivers/chromedriver.exe";
	private final static String IEDRIVER_PATH = "src/main/resources/browserdrivers/IEDriverServer.exe";
	private final static String GECKODRIVER_PATH = "src/main/resources/browserdrivers/geckodriver.exe";
	private final static String BROWSER = env_property_file_reader("browser_to_invoke");
	protected final static String DEVICE = env_property_file_reader("device_to_launch");
	protected final static String ENVIRONMENT = env_property_file_reader("env_to_execute");
	protected final static String RUN_ON_BROWSERSTACK = env_property_file_reader("enable_browserstack");
	protected final static String APP_MESSAGES_PROP_PATH = "src/main/resources/prop/app_message.properties";
	private final static String ENVIRONMENT_PROP_PATH = "src/main/resources/prop/Environment.properties";
	private final static String APPLICATION_INPUT_FILE = "src/test/resources/AppData.xml";
	protected final static String CLEAR_TEMP_FILE = "src/main/resources/batchfiles/TempDelete.bat";
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private static final String BROWSERSTACK_URL = "http://" + env_property_file_reader("browserstack_username") + ":"
			+ env_property_file_reader("browserstack_accesskey") + "@hub-cloud.browserstack.com/wd/hub";
//	private static final String BROWSERSTACK_URL = "http://vinothkumar67:BhtTXqRzp1ryUseD1DEW@hub-cloud.browserstack.com/wd/hub";



	// Method created to give freedom to automation developer to run individual
	// scripts.
	public static WebDriver createInstance(String... xml_test_name) throws MalformedURLException {
		WebDriver driver = null;
		ChromeOptions options = null;
		DesiredCapabilities dcap = new DesiredCapabilities();
		log.info("==========================================================================================");
		log.info("=============================== TEST EXECUTION STARTED ===============================");
		log.info("==========================================================================================");
		log.info("Environment is :: " + BROWSER);
		log.info("Device is :: " + DEVICE);

		if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
			instantiate_browserstack_instance();
		}
		switch (DEVICE.toLowerCase()) {
		case "desktop":
			switch (BROWSER.toLowerCase()) {
			case "chrome":
				if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
					dcap.setCapability("os", "Windows");
					dcap.setCapability("os_version", "10");
					dcap.setCapability("browser", "Chrome");
					dcap.setCapability("browser_version", "71.0");
					dcap.setCapability("project", "PCH_FrontPage_V3_Desktop");
					dcap.setCapability("build", build_no);
					dcap.setCapability("name", xml_test_name[0]);
					dcap.setCapability("browserstack.local", "true");
					dcap.setCapability("browserstack.localIdentifie", "localIdentifier");
					dcap.setCapability("browserstack.debug", "true");
					dcap.setCapability("browserstack.video", "false");
					dcap.setCapability("browserstack.console", "info");
					dcap.setCapability("browserstack.networkLogs", "true");
					dcap.setCapability("browserstack.timezone", "UTC");
					driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), dcap);
				} else {
					System.setProperty("webdriver.chrome.verboseLogging", "true");
					System.setProperty("webdriver.chrome.logfile", "C:\\chromedriver.log");
					System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
					options = new ChromeOptions();
					driver = new ChromeDriver(options);
					driver.manage().window().maximize();
					driver.manage().deleteAllCookies();
				}
				break;
			case "firefox":
				if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
					dcap.setCapability("os", "Windows");
					dcap.setCapability("os_version", "10");
					dcap.setCapability("browser", "Firefox");
					dcap.setCapability("browserstack.local", "true");
					dcap.setCapability("browserstack.video", "true");
					dcap.setCapability("browserstack.debug", "true");
					dcap.setCapability("project", "FrontPage");
					dcap.setCapability("build", "Firefox on Windows 10");
					dcap.setCapability("name", "Desktop" + xml_test_name[0]);
					driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), dcap);
				} else {
					FirefoxDriverManager.getInstance().setup();
					dcap = DesiredCapabilities.firefox();
					dcap.setCapability("marionette", true);
					dcap.setCapability("--disable-extensions", true);
					driver = new FirefoxDriver(dcap);
					driver.manage().window().maximize();
					driver.manage().deleteAllCookies();
				}
				break;
			case "ie":
				if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
					dcap.setCapability("os", "Windows");
					dcap.setCapability("os_version", "10");
					dcap.setCapability("browser", "IE");
					dcap.setCapability("browserstack.local", "true");
					dcap.setCapability("browserstack.video", "true");
					dcap.setCapability("browserstack.debug", "true");
					dcap.setCapability("project", "FrontPage");
					dcap.setCapability("build", "IE on Windows 10");
					dcap.setCapability("name", "Desktop" + xml_test_name[0]);
					driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), dcap);
				} else {
					InternetExplorerDriverManager.getInstance().setup();
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
					driver.manage().deleteAllCookies();
				}
				break;
			}
			break;
		case "Tablet_IOS":
			if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
				dcap.setCapability("os_version", "11.0");
				dcap.setCapability("device", "iPad 5th");
				dcap.setCapability("real_mobile", "true");
				dcap.setCapability("project", "FrontPage");
				dcap.setCapability("build", "IOS 11.0");
				dcap.setCapability("name", "Tablet" + xml_test_name[0]);
				dcap.setCapability("browserstack.local", "true");
				dcap.setCapability("browserstack.debug", "true");
				driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), dcap);
			} else {
				ChromeDriverManager.getInstance().setup();
				Map<String, String> mobileEmulation = new HashMap<String, String>();
				mobileEmulation.put("deviceName", "iPad");
				Map<String, Object> chromeOptions = new HashMap<String, Object>();
				chromeOptions.put("mobileEmulation", mobileEmulation);
				dcap = DesiredCapabilities.chrome();
				dcap.setBrowserName("chrome");
				dcap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				driver = new ChromeDriver(dcap);
				driver.manage().deleteAllCookies();
			}
			break;
		case "Tablet_Android":
			if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
				dcap.setCapability("os_version", "6.0");
				dcap.setCapability("device", "Samsung Galaxy Note 4");
				dcap.setCapability("real_mobile", "true");
				dcap.setCapability("project", "FrontPage");
				dcap.setCapability("build", "Android 6.0");
				dcap.setCapability("name", "Tablet" + xml_test_name[0]);
				dcap.setCapability("browserstack.local", "true");
				dcap.setCapability("browserstack.debug", "true");
				driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), dcap);
			} else {
				ChromeDriverManager.getInstance().setup();
				Map<String, String> mobileEmulation = new HashMap<String, String>();
				mobileEmulation.put("deviceName", "Nexus 10");
				Map<String, Object> chromeOptions = new HashMap<String, Object>();
				chromeOptions.put("mobileEmulation", mobileEmulation);
				dcap = DesiredCapabilities.chrome();
				dcap.setBrowserName("chrome");
				dcap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				driver = new ChromeDriver(dcap);
				driver.manage().deleteAllCookies();
			}
			break;
		}
		DriverManager.setWebDriver(driver);
		return driver;
	}

	/**
	 * Used to instantiate the Browser Stack instance of the local machine
	 * irrespective of the OS
	 * 
	 */
	private static void instantiate_browserstack_instance() {
		String os_name = System.getProperty("os.name").toLowerCase();
		String absolute_path;
		log.info("os_name: " + os_name);
		if (os_name.contains("windows")) {
			log.info("Inside Windows BrowserStack call");
			absolute_path = System.getProperty("user.dir") + "\\src\\main\\resources\\browserstack";
			try {
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "StartBrowserStack.bat");
				File dir = new File(absolute_path);
				pb.directory(dir);
				pb.start();
				sleepFor(30);
			} catch (Exception e) {
				log.error("Windows BrowserStack instantiate exception: " + e.getLocalizedMessage());
			}
		} else if (os_name.contains("linux") || os_name.contains("mac") || os_name.contains("ubuntu")) {
			absolute_path = System.getProperty("user.dir") + "/src/main/resources/browserstack/";
			try {
				log.info("Inside Linux BrowserStack call");
				String script_name = absolute_path + "/StartBrowserStack.sh";
				String commands[] = new String[] { script_name };
				Runtime rt = Runtime.getRuntime();
				Process process = rt.exec(commands);
				process.waitFor();
				sleepFor(90);
			} catch (Exception e) {
				log.error("Linux BrowserStack instantiate exception: " + e.getLocalizedMessage());
			}
		}
	}

	/**
	 * Switch the window to landscape mode
	 * 
	 * @param width
	 * @param hight
	 * @throws Exception
	 */
	public static void switchToLandscopeMode(int width, int hight) throws Exception {
		DriverManager.getDriver().manage().window().setSize(new Dimension(width, hight));
	}

	// Method to create a instance browser while running scripts from testng.xml
	// file. Not in use.
	public static WebDriver createInstance(String browserName, String Grid_URL) throws Exception {
		WebDriver driver = null;
		log.info("==========================================================================================");
		log.info("=============================== TEST EXECUTION STARTED ===============================");
		log.info("==========================================================================================");

		// Script will run either on Grid or Local based on
		// ScriptRunningEnvrironment value from Environment properties file
		driver = runScriptsOnGrid(driver, browserName, Grid_URL);
		return driver;
	}

	// Supporting method for createInstance to Run scripts on Local. Not in use.
	public static WebDriver runScriptonLocal(WebDriver driver, String Browser) {
		log.info("AUTOMATION SCRIPTS ARE RUNNING ON LOCAL");
		log.info("Browser is " + ":: " + Browser);
		// browser selection will be based on the inputs from testng.xml
		// file
		ChromeOptions options = null;
		DesiredCapabilities dcap = null;
		if (Browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", GECKODRIVER_PATH);
			driver = new FirefoxDriver();
			DriverManager.setWebDriver(driver);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

		} else if (Browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", IEDRIVER_PATH);
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		} else if (Browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
			options = new ChromeOptions();
			options.addArguments("--disable-extensions");
			driver = new ChromeDriver(options);
			DriverManager.setWebDriver(driver);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		} else if (Browser.equalsIgnoreCase("Tablet_IOS")) {
			File file = new File(CHROMEDRIVER_PATH);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Apple iPad Mini");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			dcap = DesiredCapabilities.chrome();
			dcap.setBrowserName("chrome");
			dcap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			driver = new ChromeDriver(dcap);
			driver.manage().deleteAllCookies();
			DriverManager.setWebDriver(driver);
		} else if (Browser.equalsIgnoreCase("Tablet_Android")) {
			File file = new File(CHROMEDRIVER_PATH);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Apple iPad Mini");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			dcap = DesiredCapabilities.chrome();
			dcap.setBrowserName("chrome");
			dcap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			driver = new ChromeDriver(dcap);
			driver.manage().deleteAllCookies();
			DriverManager.setWebDriver(driver);
		}
		return driver;
	}

	// Supporting method for createInstance to Run scripts on Grid. Not in use.
	public static WebDriver runScriptsOnGrid(WebDriver driver, String Browser, String Grid_URL)
			throws MalformedURLException {
		// Getting the Grid url from Environment.properties file
		DesiredCapabilities dcap = null;
		log.info("AUTOMATION SCRIPTS ARE RUNNING ON GRID");
		log.info("Browser is :: " + Browser);
		/*
		 * Browser selection will be based on the inputs from testng.xml file
		 */
		if (Browser.equalsIgnoreCase("Firefox")) {
			/*
			 * Before running make sure to have below path and drivers on Node configured
			 * system.
			 */
			System.setProperty("webdriver.gecko.driver", GECKODRIVER_PATH);
			dcap = DesiredCapabilities.firefox();
			dcap.setBrowserName("firefox");
			dcap.setPlatform(Platform.VISTA);
			driver = new RemoteWebDriver(new URL(Grid_URL), dcap);
			DriverManager.setWebDriver(driver);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

		} else if (Browser.equalsIgnoreCase("IE")) {
			/*
			 * Before running make sure to have below path and drivers on Node configured
			 * system.
			 */
			System.setProperty("webdriver.ie.driver", IEDRIVER_PATH);
			dcap = DesiredCapabilities.internetExplorer();
			dcap.setBrowserName("iexplore");
			driver = new RemoteWebDriver(new URL(Grid_URL), dcap);
			DriverManager.setWebDriver(driver);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

		} else if (Browser.equalsIgnoreCase("Chrome")) {
			/*
			 * Before running make sure to have below path and drivers on Node configured
			 * system.
			 */
			File file = new File(CHROMEDRIVER_PATH);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			dcap = DesiredCapabilities.chrome();
			dcap.setBrowserName("chrome");
			driver = new RemoteWebDriver(new URL(Grid_URL), dcap);
			DriverManager.setWebDriver(driver);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();

		} else if (Browser.equalsIgnoreCase("Tablet_IOS")) {
			File file = new File(CHROMEDRIVER_PATH);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Apple iPad Mini");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			dcap = DesiredCapabilities.chrome();
			dcap.setBrowserName("chrome");
			dcap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			driver = new RemoteWebDriver(new URL(Grid_URL), dcap);
			driver.manage().deleteAllCookies();
			DriverManager.setWebDriver(driver);

		} else if (Browser.equalsIgnoreCase("Tablet_Android")) {
			File file = new File(CHROMEDRIVER_PATH);
			System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", "Apple iPad Mini");
			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);
			dcap = DesiredCapabilities.chrome();
			dcap.setBrowserName("chrome");
			dcap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			driver = new RemoteWebDriver(new URL(Grid_URL), dcap);
			driver.manage().deleteAllCookies();
			DriverManager.setWebDriver(driver);
		}

		return driver;
	}

	// Method to read data from XML file
	public static String xmlReader(String NodeName, String PropertyValue) {
		Element element = null;
		String Value = null;
		try {

			File Prop = new File(APPLICATION_INPUT_FILE);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document doc = dbBuilder.parse(Prop);
			doc.getDocumentElement().normalize();
			doc.getDocumentElement().getNodeName();
			NodeList nodes = doc.getElementsByTagName(NodeName);

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) node;
				}
			}
			Value = getValue(PropertyValue, element).trim();
		} catch (Exception ex) {
			log.error("Error in the XML Reader file:" + ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		return Value.trim();
	}

	// Dependent method to read data from XML
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	// To read data from environment properties file
	public static String env_property_file_reader(String propertyname) {
//		String exe_prop_value = System.getProperty(propertyname);//Commented to get value from property files
		String exe_prop_value = null;
		if (exe_prop_value == null) {
			File file = new File(ENVIRONMENT_PROP_PATH);
			FileInputStream fileInput = null;
			try {
				fileInput = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Properties prop = new Properties();
			// load properties file
			try {
				prop.load(fileInput);
			} catch (IOException e) {
				log.info(e.getMessage());
			}
			exe_prop_value = prop.getProperty(propertyname);

		}
		return exe_prop_value;
	}

	// To read data from application message properties file
	public static String msg_property_file_reader(String propertyname, String... replace_value) {
		File file = new File(APP_MESSAGES_PROP_PATH);

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		}
		Properties prop = new Properties();
		// load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		String value = prop.getProperty(propertyname);
		for (String param : replace_value) {
			value = value.replaceFirst("<value>", param);
		}
		return value;
	}

	// To get the current date in MM-dd-yyyy HH-mm-ss format
	public static String getCurrentDateWithTimeStamp() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss");
		String Current = format1.format(cal.getTime());
		log.info("Current date is :: " + Current);
		return Current;
	}

	public WebElement waitForElement(By locator) {
		try {
			int count = 1;
			log.info("Waiting upto " + ACTION_TIMEOUT_TIME + "ms for element with locator: \"" + locator
					+ "\" to appear on page.");
			while (DriverManager.getDriver().findElements(locator).size() == 0 && count <= 60) {
				Thread.sleep(1000);
				log.info("Waiting " + 1 + "000 ms for count " + count);
				count++;
			}
			WebElement element = DriverManager.getDriver().findElement(locator);
			HighlightMyElement(element);
			return element;
		} catch (NoSuchElementException e) {

			Assert.fail(locator + " NoSuchElementException NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(locator + "NullPointerException NOT FOUND. TEST FAILED.");
		} catch (StaleElementReferenceException e) {
			log.info("Element is not attached to the page document " + e.getStackTrace());
		} catch (Exception e) {
			log.info("Element " + locator + " was not available " + e.getStackTrace());
		}
		return null;
	}

	/**
	 * Wait for an element using WebElement
	 * 
	 * @param element
	 */
	public void waitForElement(WebElement element) {
		try {
			int count = 1;
			log.info("Waiting upto " + ACTION_TIMEOUT_TIME + "ms for element with locator: \"" + element
					+ "\" to appear on page.");
			moveToElement(element);
			while (!element.isDisplayed() && count <= 60) {
				Thread.sleep(1000);
				log.info("Waiting " + 1 + "000 ms for count " + count);
				count++;
			}
		} catch (NoSuchElementException e) {
			Assert.fail(element + " NoSuchElementException NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(element + "NullPointerException NOT FOUND. TEST FAILED.");
		} catch (StaleElementReferenceException e) {
			log.info("Element is not attached to the page document " + e.getStackTrace());
		} catch (Exception e) {
			log.info("Element " + element + " was not available " + e.getStackTrace());
		}
	}

	public static void waitForElementPresent(By locator, int wait_time) {
		log.info("Waiting for element with locator: \"" + locator + "\" to appear on page.");
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), wait_time);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
			log.info("Element: " + locator + " was not available " + e.getStackTrace());
		}
	}

	public static void waitForElementVisibility(By locator, int wait_time) {
		log.info("Waiting for visibility of element with locator: \"" + locator + "\" to appear on page.");
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), wait_time);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (Exception e) {
		}
	}

	/**
	 * @param driver
	 * @param webElement
	 * @return
	 */

	public boolean elementPresent(By locator) {
		try {
			waitForElementPresent(locator, 30);
			moveToElement(locator);
			WebElement element = DriverManager.getDriver().findElement(locator);
			HighlightMyElement(element);
			if (element.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait until the element is visibility on page
	 * 
	 * @param locator
	 * @return
	 */
	public boolean elementVisibility(By locator, int... time_out) {
		try {
			if (time_out.length > 0)
				waitForElementVisibility(locator, time_out[0]);
			else
				waitForElementVisibility(locator, 30);
			moveToElement(locator);
			WebElement element = DriverManager.getDriver().findElement(locator);
			HighlightMyElement(element);
			if (element.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param textBoxAction
	 * @param value
	 * @return
	 */

	public WebElement textbox(By locator, String textBoxAction, String value, int WaitTime) {
		WebElement element = waitForElement(locator, WaitTime);
		if (element != null) {
			moveToElement(element);
			try {
				if (textBoxAction.equalsIgnoreCase("enter")) {
					element.clear();
					element.sendKeys(value);
				} else if (textBoxAction.equalsIgnoreCase("clear")) {
					element.clear();
				} else {
					Assert.fail("TEXTBOX ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.clear();
				element.sendKeys(value);
			} catch (NoSuchElementException e) {
				Assert.fail(locator + " TEXTBOX NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				Assert.fail(locator + " TEXTBOX NOT FOUND. TEST FAILED.");
			}
		}
		return element;
	}

	/**
	 * Enter the text into field using WebElement
	 * 
	 * @param element
	 * @param textBoxAction
	 * @param value
	 * @param WaitTime
	 * @return
	 */
	public WebElement textbox(WebElement element, String textBoxAction, String value, int WaitTime) {
		if (element != null) {
			moveToElement(element);
			try {
				if (textBoxAction.equalsIgnoreCase("enter")) {
					element.clear();
					element.sendKeys(value);
				} else if (textBoxAction.equalsIgnoreCase("clear")) {
					element.clear();
				} else {
					Assert.fail("TEXTBOX ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.clear();
				element.sendKeys(value);
			} catch (NoSuchElementException e) {
				Assert.fail(element + " TEXTBOX NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				Assert.fail(element + " TEXTBOX NOT FOUND. TEST FAILED.");
			}
		}
		return element;
	}

	/**
	 * Submit the form
	 * 
	 * @param locator
	 * @param WaitTime
	 * @return
	 */
	public WebElement submit(By locator, int WaitTime) {
		WebElement element = waitForElement(locator, WaitTime);
		try {
			element.submit();
		} catch (Exception e) {
			Assert.fail(locator + " GIVEN LOCATOR NOT SUPPORTED THE SUBMIT ACTION. TEST FAILED.");
		}
		return element;
	}

	/**
	 * @param driver
	 * @param element
	 * @param sec
	 * @return
	 */

	public static WebElement waitForElement(By element, int sec) {
		Wait<WebDriver> wait = new WebDriverWait(DriverManager.getDriver(), DELAY * sec);
		ExpectedCondition<WebElement> condition = new ElementPresent(element);
		try {
			WebElement we = wait.until(condition);
			if (we == null) {
				DriverManager.getDriver().navigate().refresh();
				we = wait.until(condition);
			}
			HighlightMyElement(we);
			return we;
		} catch (WebDriverException e) {
			return null;
		}
	}

	/**
	 * @param driver
	 * @param element
	 * @param sec
	 * @return
	 */

	public boolean waitForElementToBePresent(By element, Integer sec) {

		Wait<WebDriver> wait = new WebDriverWait(DriverManager.getDriver(), DELAY * sec);
		ExpectedCondition<WebElement> condition = new ElementPresent(element);
		try {
			wait.until(condition);
			return true;
		} catch (WebDriverException e) {
			return false;
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 */

	public Select selectByVisibleText(By locator, String value, int WaitTime) {
		Select localeList;
		WebElement element = waitForElement(locator, WaitTime);
		if (element != null) {
			moveToElement(element);
			try {
				localeList = new Select(element);
			} catch (IllegalStateException e) {
				localeList = new Select(element);
			}
		} else {
			localeList = null;
		}
		try {
			localeList.selectByVisibleText(value);
		} catch (IllegalStateException e) {
			localeList.selectByVisibleText(value);
		} catch (NoSuchElementException e) {
			Assert.fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail(locator + " ACTION FAILED. TEST FAILED.");
		}
		return localeList;
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 */

	public Select selectByVisibleText(WebElement element, String value, int WaitTime) {
		Select localeList;
		if (element != null) {
			moveToElement(element);
			try {
				localeList = new Select(element);
			} catch (IllegalStateException e) {
				localeList = new Select(element);
			}
		} else {
			localeList = null;
		}
		try {
			localeList.selectByVisibleText(value);
		} catch (IllegalStateException e) {
			localeList.selectByVisibleText(value);
		} catch (NoSuchElementException e) {
			Assert.fail(element + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(element + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail(element + " ACTION FAILED. TEST FAILED.");
		}
		return localeList;
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param action
	 * @return
	 * @throws InterruptedException
	 * @throws Exception
	 */

	public WebElement button(By locator, int WaitTime) {

		WebElement element = waitForElement(locator, WaitTime);
		if (element != null) {
			try {
				moveToElement(element);
				elementIsClickable(locator, 10);
				element.click();
			} catch (IllegalStateException e) {
				element.click();
			} catch (StaleElementReferenceException e) {
				log.info("StaleElementReferenceException Found, Wait for some more time.....");
				element = waitForElement(locator, WaitTime);
				element.click();
			}
			catch (NoSuchElementException e) {
				Assert.fail(locator + " BUTTON NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				Assert.fail(locator + " BUTTON NOT FOUND. TEST FAILED.");
			} catch (WebDriverException e) {
				JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
				executor.executeScript("arguments[0].scrollIntoView();", element);
				executor.executeScript("arguments[0].click();", element);
			}
		}
		HighlightMyElement(element);
		return element;
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public void button(WebElement element, int WaitTime) {
		if (element != null) {
			try {
				moveToElement(element);
				element.click();
			} catch (IllegalStateException e) {
				element.click();
			} catch (StaleElementReferenceException e) {
				log.info("StaleElementReferenceException Found, Wait for some more time.....");
				waitForElement(element);
				element.click();
			} catch (NoSuchElementException e) {
				Assert.fail(element + " BUTTON NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				Assert.fail(element + " BUTTON NOT FOUND. TEST FAILED.");
			} catch (WebDriverException e) {
				JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
				executor.executeScript("arguments[0].click();", element);
			}
		}
	}

	public void action_click() {
		Actions a = new Actions(DriverManager.getDriver());
		Action click = a.click().build();
		click.perform();
	}
	
	public void action_click(By locator) throws AWTException {
//		moveToElement(locator);
		
		WebElement we=waitForElement(locator);
		Actions a = new Actions(DriverManager.getDriver());
		Action click = a.click(we).build();
		click.perform();
//		a.click(we).perform();
//		a.click(we).perform();
//		Point p=new Point();
//		mouseHover(we);
//		Robot r= new Robot();
//		r.keyPress(we);
////		a.sendKeys(we, Keys.ENTER);
		button(locator, 10);
		System.out.println("test");
	}

	/**
	 * @param Locator
	 * @param driver
	 */
	public boolean elementIsClickable(By Locator, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), wait_time);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(Locator));
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	// To wait for certain amount of time irrespective of element present and
	// page load
	public static void sleepFor(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// To close external Popup windows upon windows count
	public static void getAdHandler(WebDriver driver) throws Exception {
		// waitForPageToLoad(driver);
		try {
			String currentHandle = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			handles.remove(currentHandle);
			if (handles.size() > 0) {
				driver.switchTo().window(handles.iterator().next());
				driver.close();
				driver.switchTo().window(currentHandle);
				// waitForPageToLoad(driver);
			}
		} catch (Exception ignore) {
		}
		sleepFor(1);
	}

	/**
	 * @param driver
	 * @param locator
	 * @param linkAction
	 * @return
	 */

	public WebElement link(By locator, String linkAction, int WaitTime) {

		WebElement element = waitForElement(locator, WaitTime);
		try {
			element.click();
		} catch (IllegalStateException e) {
			element.click();
		} catch (NoSuchElementException e) {
			Assert.fail(locator + " LINK NOT FOUND. TEST FAILED.");
		} catch (InvalidArgumentException e) {

		} catch (NullPointerException e) {
			Assert.fail(locator + " LINK NOT FOUND. TEST FAILED.");
		}
		return element;
	}

	// To get text from page element of AUT
	public String getText(By locator, int sec) {
		WebElement element = waitForElement(locator, sec);
		if (element != null) {
			return element.getText().trim();
		}
		log.error("[ELEMENT SEARCH FAILED] ELEMENT WITH DESCRIPTION " + locator
				+ " IS NOT FOUND; TEXT CANNOT BE EXTRACTED!");
		return null;
	}

	// To refresh the page and accept the nav away message if displayed
	public void doRefresh() throws Exception {
		try {
			DriverManager.getDriver().navigate().refresh();
		} catch (Exception e) {
		}
		acceptIfAlertPresent();
	}

	// To accept the nav away message on browser window
	public void acceptIfAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = DriverManager.getDriver().switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ignore) {
		} catch (UnhandledAlertException ignore) {
		} catch (WebDriverException ignore) {
		}
		/*
		 * try { pressENTER(); pressENTER(); } catch (Exception ignore) { }
		 */

	}

	/**
	 *         To mouse over on any element using Java script executor
	 */

	public void mouseHoverJScript(WebElement HoverElement) {
		try {
			if (isElementPresent(HoverElement)) {
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) DriverManager.getDriver()).executeScript(mouseOverScript, HoverElement);
			} else {
				log.info("Element was not visible to hover " + "\n");
			}
		} catch (StaleElementReferenceException e) {
			log.info("Element with " + HoverElement + "is not attached to the page document" + e.getStackTrace());
		} catch (NoSuchElementException e) {
			log.info("Element " + HoverElement + " was not found in DOM" + e.getStackTrace());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Error occurred while hovering" + e.getStackTrace());
		}
	}

	// To verify if element is displayed on AUT
	public static boolean isElementPresent(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed() || element.isEnabled())
				flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		} catch (StaleElementReferenceException e) {
			flag = false;
		}
		return flag;
	}

	// To switch the focus to iframe upon iframe count
	public void switch_to_iframe(String FrameName, int count) throws Exception {
		try {
			DriverManager.getDriver().switchTo()
					.frame(DriverManager.getDriver().findElements(By.tagName(FrameName)).get(count));
		} catch (IndexOutOfBoundsException e) {
			// e.printStackTrace();
		}
	}

	// To switch the focus to iframe based on id/name
	public void switch_to_iframe_by_webelement(By frame_locator, int count) throws Exception {
		DriverManager.getDriver().switchTo().frame(DriverManager.getDriver().findElements(frame_locator).get(count));
	}

	// To switch the focus back to the main page
	public void switch_iframe_default_content() {
		DriverManager.getDriver().switchTo().defaultContent();
	}

	/**
	 * 
	 * @param appDriver
	 * @param locator
	 * @param expectedAttribute
	 * @return
	 * 
	 * 		To get specified attribute value of a page element and returns the
	 *         String value of an attribute
	 */
	public String getAttribute(By locator, String expectedAttribute) {
		String attributeValue = null;
		try {
			waitForElement(locator);
			attributeValue = DriverManager.getDriver().findElement(locator).getAttribute(expectedAttribute).trim();
			log.info("Attribute value is :: " + attributeValue);
		} catch (NoSuchElementException e) {
		}
		return attributeValue;
	}

	/**
	 * 
	 * @param appDriver
	 * @param locator
	 * @param expectedAttribute
	 * @return
	 * 
	 * 		To get specified attribute value of a page element and returns the
	 *         String value of an attribute
	 */
	public String getAttribute(WebElement ele, String expectedAttribute) {
		String attributeValue = null;
		try {
			attributeValue = ele.getAttribute(expectedAttribute);
			log.info("Attribute value is :: " + attributeValue);
		} catch (NoSuchElementException e) {
		}
		return attributeValue;
	}

	// To get the value of an attribute and verify the attribute value with
	// matching text
	public boolean getValueByAttribute(By element, String Attribute, String MatchText) {
		log.info("Attribute value is :: " + DriverManager.getDriver().findElement(element).getAttribute(Attribute));
		boolean status = DriverManager.getDriver().findElement(element).getAttribute(Attribute).contains(MatchText);
		return status;
	}

	// Navigate to specific URL, Clear cookies, wait for page to load and accept
	// the nav away message if displayed.
	public void navigateTo(String URL) throws Exception {
		acceptIfAlertPresent();
		DriverManager.getDriver().manage().deleteAllCookies();
		try {
			DriverManager.getDriver().navigate().refresh();
			acceptIfAlertPresent();

		} catch (WebDriverException e) {
			e.printStackTrace();
		}
		// WrapperFunctions.waitForPageToLoad(DriverManager.getDriver());
		DriverManager.getDriver().navigate().to(URL);
		acceptIfAlertPresent();
	}

	// Navigate to URL without clearing cookies, wait for page to load and
	// accept the nav away message if displayed.
	public void navigateTo_WOC_Cookies(String URL) {
		// WrapperFunctions.waitForPageToLoad(DriverManager.getDriver());
		// Navigate to specific URL
		try {
			DriverManager.getDriver().navigate().to(URL);
			acceptIfAlertPresent();
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
	}

	// To count the Total iframes on the page and get the count of desired
	// iframe. Parameter frame_position is to get the desired frame from the
	// DOM, if the page has same frame attributes for multiple frames.
	public int count_iframe_get_desired_iframe_count(String eIframe, String egetAttribute, String verificationText) {
		int frameNo = 0;
		List<WebElement> frames = DriverManager.getDriver().findElements(By.tagName(eIframe));
		for (int i = 0; i <= frames.size(); i++) {
			log.info("Current Frame count is --> " + i);
			try {
				if (frames.get(i).getAttribute(egetAttribute).contains(verificationText)) {
					frameNo = i;
					break;
				}
			} catch (Exception e) {
				continue;
			}
		}
		return frameNo;
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 * 
	 * 		To select a value from dropdown by its value
	 */

	public Select selectByValue(By locator, String value, int WaitTime) {

		Select localeList;

		WebElement element = waitForElement(locator, WaitTime);
		if (element != null) {
			localeList = new Select(element);
		} else {
			localeList = null;
		}
		try {
			localeList.selectByValue(value);
		} catch (IllegalStateException e) {
			localeList.selectByValue(value);
		} catch (NoSuchElementException e) {
			Assert.fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail(locator + "WEB DRIVER EXCEPTION, ACTION FAILED. TEST FAILED.");
		}
		return localeList;
	}

	/**
	 * Select the value from WebElement
	 * 
	 * @param element
	 * @param value
	 * @param WaitTime
	 * @return
	 */
	public Select selectByValue(WebElement element, String value, int WaitTime) {
		Select localeList;
		if (element != null) {
			localeList = new Select(element);
		} else {
			localeList = null;
		}
		try {
			localeList.selectByValue(value);
		} catch (IllegalStateException e) {
			localeList.selectByValue(value);
		} catch (NoSuchElementException e) {
			Assert.fail(" LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(" LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail("WEB DRIVER EXCEPTION, ACTION FAILED. TEST FAILED.");
		}
		return localeList;
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 * 
	 * 		To select a value from drop down by its Option valueF
	 */

	public Select selectByOption(By locator, String value, int WaitTime) {
		Select localeList = null;
		WebElement element = waitForElement(locator, WaitTime);
		try {
			if (element != null) {
				for (WebElement ele : element.findElements(By.tagName("option"))) {
					if (ele.getText().equalsIgnoreCase(value)) {
						ele.click();
						break;
					}
				}
			}
		} catch (NoSuchElementException e) {
			Assert.fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail(locator + "WEB DRIVER EXCEPTION, ACTION FAILED. TEST FAILED.");
		}
		return localeList;
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 * 
	 * 		To select a value from drop down by its Option valueF
	 */

	public Select selectByOption(WebElement element, String value, int WaitTime) {
		Select localeList = null;
		waitForElement(element);
		try {
			if (element != null) {
				for (WebElement ele : element.findElements(By.tagName("option"))) {
					if (ele.getText().equalsIgnoreCase(value)) {
						ele.click();
						break;
					}
				}
			}
		} catch (NoSuchElementException e) {
			Assert.fail(element + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(element + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail(element + "WEB DRIVER EXCEPTION, ACTION FAILED. TEST FAILED.");
		}
		return localeList;
	}

	/**
	 * DeSelect all the value from WebElement
	 * 
	 * @param element
	 * @param value
	 * @param WaitTime
	 * @return
	 */
	public void deSelectAll(WebElement element, int WaitTime) {
		Select localeList;
		if (element != null) {
			localeList = new Select(element);
		} else {
			localeList = null;
		}
		try {
			localeList.deselectAll();
		} catch (IllegalStateException e) {
			localeList.deselectAll();
		} catch (NoSuchElementException e) {
			Assert.fail(" LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(" LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail("WEB DRIVER EXCEPTION, ACTION FAILED. TEST FAILED.");
		}
	}

	// To verify the display of any string value in page source
	protected boolean verifyPageSource(String PageSource) {
		// waitForPageToLoad(DriverManager.getDriver());
		boolean Step_Status = DriverManager.getDriver().getPageSource().contains(PageSource);
		return Step_Status;
	}

	// To verify the image url based on image location
	protected boolean verifyImage(WebElement element, String Attribute, String ImageURL) {
		boolean status = element.getAttribute(Attribute).equalsIgnoreCase(ImageURL);
		return status;
	}

	// To delete single cookie
	public void deleteCookieByName(String CookieName) throws Exception {
		// waitForPageToLoad(DriverManager.getDriver());
		DriverManager.getDriver().manage().deleteCookieNamed(CookieName);
		sleepFor(5);
	}

	// To verify new tab is opened, verify the text in URL and close the tab
	public boolean verify_NewTab_And_Close(String VerifURLText) {
		boolean Status = false;
		sleepFor(1);
		String currentHandle = DriverManager.getDriver().getWindowHandle();
		Set<String> handles = DriverManager.getDriver().getWindowHandles();
		handles.remove(currentHandle);
		if (handles.size() > 0) {
			DriverManager.getDriver().switchTo().window(handles.iterator().next());

			Status = DriverManager.getDriver().getCurrentUrl().contains(VerifURLText);
		}
		DriverManager.getDriver().close();
		try {
			Alert alert = DriverManager.getDriver().switchTo().alert();
			if (alert != null) {
				alert.accept();
			}
		} catch (Exception ignore) {
		}
		DriverManager.getDriver().switchTo().window(currentHandle);
		return Status;
	}

	/**
	 * Update the Step No & Step Desc and print the same for debug
	 * 
	 * @param step_no
	 * @param step_dec_length
	 * @param step_desc
	 */
	protected void test_step_details(int step_no, String step_desc) {
		stepNo = step_no;
		step_description.setLength(0);
		step_description.append(step_desc);
		step_Start_Message(stepNo, step_description);
	}

	// Method to just print in console about the step invoke
	protected void step_Start_Message(int StepNo, StringBuffer step) {
		log.info("StepNo: " + StepNo + " is initialized. Step decription is: " + step);
		System.out.println("StepNo: " + StepNo + " is initialized. Step decription is: " + step);
	}

	// Method to just print in console about the step invoke
	protected void step_Complete_Message(int StepNo, StringBuffer step) {
		log.info("StepNo: " + StepNo + " is completed. Step decription is: " + step);
		System.out.println("StepNo: " + StepNo + " is completed. Step decription is: " + step);
	}

	// Validate the step and take screenshot if the step is failed.
	protected void step_validator(int step_no, boolean step_status) throws IOException {
		Assert.assertTrue(step_status);
		log.info("StepNo: " + step_no + " is Completed.");
		System.out.println("StepNo: " + step_no + " is Completed.");
	}

	/**
	 * Update the Test No & Test Desc and print the same for debug
	 * 
	 * @param Test_no
	 * @param Test_dec_length
	 * @param Test_desc
	 */

	protected void test_Method_details(int step_no, String step_desc) {
		stepNo = step_no;
		step_description.setLength(0);
		step_description.append(step_desc);
		test_Start_Message(stepNo, step_description);
	}

	// Method to just print in console about the step invoke
	protected void test_Start_Message(int StepNo, StringBuffer step) {
		log.info("TEST NUMBER: " + StepNo + " IS INITIALIZED. TEST DESCRIPTION IS: " + step);
		System.out.println("TEST NUMBER: " + StepNo + " IS INITIALIZED. TEST DESCRIPTION IS: " + step);
	}

	/**
	 * Compare the strings and return the Boolean value
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean compareEqualStrings(String actual, String expected) {
		return actual.trim().equals(expected.trim());
	}

	/**
	 * Compare the strings and return the Boolean value
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean compareEqualStringsWithIgnoreCase(String actual, String expected) {
		return actual.trim().equalsIgnoreCase(expected.trim());
	}

	/**
	 * Compare the not equal strings and return the Boolean value
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean compareNotEqualStrings(String actual, String expected) {
		return !actual.trim().equals(expected.trim());
	}

	/**
	 * Compare the Integers and return the Boolean value
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean compareInts(int actual, int expected) {
		return actual == expected;
	}

	/**
	 * Compare the not equal Integers and return the Boolean value
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean compareNotEqualInts(int actual, int expected) {
		return actual != expected;
	}

	/**
	 * Verify the Actual String contains the Expected String
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean isStringContains(String actual, String expected) {
		return actual.trim().contains(expected.trim());
	}

	/**
	 * Verify the Actual String not contains the Expected String
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean isStringNotContains(String actual, String expected) {
		return !actual.trim().contains(expected.trim());
	}
	
	/**
	 * Verify the Actual String contains the Expected String (Comparing URL's without ssl)
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean assertEqualsURLs(String actual, String expected) {
		actual=actual.substring(6);
		return expected.trim().contains(actual.trim());
	}

	/**
	 * Compare the actual strings ends with the expected value and return the
	 * Boolean value
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean isStringEndsWith(String actual, String expected) {
		return actual.endsWith(expected);
	}

	/**
	 * Asserting the String Equals
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertEquals(String actual, String expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertEquals(actual.trim(), expected.trim());
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Asserting the String Equals with Ignore case
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertEqualsIgnoreCase(String actual, String expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertEquals(actual.toLowerCase().trim(), expected.toLowerCase().trim());
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Asserting the String Not Equals
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertNotEquals(String actual, String expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertNotEquals(actual.toLowerCase().trim(), expected.toLowerCase().trim());
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Asserting the String Not Equals ignore case
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertNotEqualsIgnoreCase(String actual, String expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertNotEquals(actual.toLowerCase().trim(), expected.toLowerCase().trim());
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Assert Int Equals
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertEqualsInt(int actual, int expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertEquals(actual, expected);
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Assert Int Not Equals
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertNotEqualsInt(int actual, int expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertNotEquals(actual, expected);
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Assert Is String contains
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertIsStringContains(String actual, String expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertTrue(actual.trim().contains(expected.trim()));
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Assert Is String contains with ignore case
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertIsStringContainsIgnoreCase(String actual, String expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertTrue(actual.trim().toLowerCase().contains(expected.trim().toLowerCase()));
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Assert Is String Not contains
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertIsStringNotContains(String actual, String expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertFalse(actual.trim().contains(expected.trim()));
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Assert Is String ends with
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertIsStringEndsWith(String actual, String expected) {
		log.info("Actual: " + actual);
		log.info("Expected: " + expected);
		Assert.assertTrue(actual.trim().endsWith(expected.trim()));
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Assert True
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertTrue(boolean value, String... msg) {
		Assert.assertTrue(value);
		log.info("StepNo: " + stepNo + " is Completed.");
	}

	/**
	 * Assert False
	 * 
	 * @param actual
	 * @param expected
	 */
	protected void assertFalse(boolean value) {
		Assert.assertFalse(value);
		log.info("StepNo: " + stepNo + " is Completed.");
	}
	
	/**
	 * Get the logger instance for the specified class. Logged the message in the
	 * Info level
	 * 
	 * @param class_instance
	 * @param message
	 */
	public static void getLoggers(Logger log, String message) {
		log.info(message);
	}

	/**
	 * Returns the List of WebElements
	 * 
	 * @param locator
	 * @return
	 */
	public List<WebElement> get_webelements_list(By locator) {
		try {
			waitForElementPresent(locator, 30);
			moveToElement(DriverManager.getDriver().findElement(locator));
		} catch (NoSuchElementException NSE) {
			log.info(NSE.getMessage());
		}
		return DriverManager.getDriver().findElements(locator);
	}

	/**
	 * Trigger the GET Call via HTTP Client third party jar. Uses Jackson third
	 * party API to parse the JSON response to get the specified parameter value
	 * 
	 * @return Given parameter value from the GET Response
	 */
	public static String getResponseParamFromGETCall(String endpoint_call, String response_key_param,
			String... parent_node) {

		try {
			HttpClient http_client = HttpClients.createDefault();
			HttpGet http_get = new HttpGet(endpoint_call);
			// Execute and get the response.
			HttpResponse response = http_client.execute(http_get);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			String response_in_string = result.toString();
			log.info("GET Call -" + endpoint_call + "- response: " + response_in_string);
			return json_parser(response_in_string, response_key_param, parent_node);
		} catch (Exception e) {
			log.info("Exception in Get Call trigger: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Trigger the POST Call via HTTP Client third party jar. Uses Jackson third
	 * party API to parse the JSON response to get the specified parameter value
	 * 
	 * @return Given parameter value from the POST Response
	 */
	public static String getResponseParamFromPOSTCall(String endpoint_call, String payload,
			LinkedHashMap<String, String> headers, String response_key_param, String... parent_node) {
		try {
			String line;
			StringBuffer jsonString = new StringBuffer();

			URL url = new URL(endpoint_call);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					connection.setRequestProperty(key, headers.get(key));
				}
			}
			connection.setRequestProperty("Accept", "application/json");

			if (payload.length() > 0) {
				connection.setRequestProperty("Content-Type", "application/json");
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(payload);
				writer.close();
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
			String response_in_string = jsonString.toString();
			log.info("POST Call -" + endpoint_call + "- response: " + response_in_string);
			return json_parser(response_in_string, response_key_param, parent_node);
		} catch (Exception e) {
			log.info("Exception in Post Call trigger: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Parse the JSON format String response and retrieve the value for the given
	 * parameter key. To retrieve the child node, give the parent node name as third
	 * parameter
	 * 
	 * @param response_in_string
	 * @param response_key_param
	 * @param parent_node
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static String json_parser(String response_in_string, String response_key_param, String... parent_node)
			throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode json_node = mapper.readTree(response_in_string);
		for (String node : parent_node) {
			json_node = json_node.get(node);
		}
		json_node = json_node.get(response_key_param);
		if (json_node.isArray()) {
			return json_node.toString();
		} else {
			return json_node.asText().toString();
		}
	}

	// To return the date in yyyy ss format
	public static String Date() {
		// DateFormat dateFormat = new SimpleDateFormat(�yyyy/MM/dd HH:mm:ss�);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy ss");
		// get current date time with Date()
		Date date = new Date();
		String DATE = dateFormat.format(date);
		DATE = DATE.replace("/", "");
		DATE = DATE.replace(":", "");
		DATE = DATE.replace(" ", "");
		return DATE;
	}

	// To generate a random string
	@SuppressWarnings("deprecation")
	public static String randomString(int lo, int hi) {
		int n = rand(lo, hi);
		byte b[] = new byte[n];
		for (int i = 0; i < n; i++)
			b[i] = (byte) rand('a', 'z');
		return new String(b, 0);
	}

	public static int rand(int min, int max) {
		Random generator = new Random();
		// nextInt() can only return random number from 0 to a max.
		// can instead find a random number from 0 to max - min, and then add
		// min to bring number within range.
		return generator.nextInt(max - min) + min;
	}

	/**
	 * Another way to generate Random String to a given parameter length
	 * 
	 * @param rng
	 * @param characters
	 * @param length
	 * @return
	 */
	public static String generateRandomString(int length) {
		Random rng = new Random();
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}

	/**
	 * Wait for an element for the specific text to be present
	 * 
	 * @param locator
	 * @param text_to_compare
	 * @param wait_time
	 */
	public void waitForElementUntilTextPresent(By locator, String text_to_compare, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), wait_time);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text_to_compare));
	}

	/**
	 * Add or Subtract the year by the givne offset from the current date
	 * 
	 * @param dateFormat
	 * @param offset
	 * @return
	 */

	public String modifyCurrentDateByOffset(String dateFormat, String type, int offset) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		switch (type.toLowerCase()) {
		case "year":
			cal.add(Calendar.YEAR, offset);
			break;
		case "month":
			cal.add(Calendar.MONTH, offset);
			break;
		case "day":
			cal.add(Calendar.DATE, offset);
			break;
		}
		return sdf.format(cal.getTime());
	}

	/**
	 * Returns the current date of the month
	 * 
	 * @return
	 */
	// @SuppressWarnings("deprecation")
	public String getCurrentDate(String date_format) {
		SimpleDateFormat date_in_string = new SimpleDateFormat(date_format);
		Calendar cal = Calendar.getInstance();
		return date_in_string.format(cal.getTime());
	}

	/**
	 * Returns the current Month of the year
	 * 
	 * @return
	 */
	public String getCurrentMonth(String date_format) {
		SimpleDateFormat month_in_string = new SimpleDateFormat(date_format);
		Calendar cal = Calendar.getInstance();
		return month_in_string.format(cal.getTime());
	}

	/**
	 * Returns the current Year
	 * 
	 * @return
	 */
	public String getCurrentYear(String date_format) {
		SimpleDateFormat year_in_string = new SimpleDateFormat(date_format);
		Calendar cal = Calendar.getInstance();
		return year_in_string.format(cal.getTime());
	}

	/**
	 * Returns the Year with the given offset value
	 * 
	 * @return
	 */
	public String getMonthWithOffset(int offset, String date_format) {
		SimpleDateFormat month_in_string = new SimpleDateFormat(date_format);
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, offset);
		return month_in_string.format(cal.getTime());
	}

	/**
	 * Returns the Year with the given offset value
	 * 
	 * @return
	 */
	public String getYearWithOffset(int offset, String date_format) {
		SimpleDateFormat year_in_string = new SimpleDateFormat(date_format);
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.YEAR, offset);
		return year_in_string.format(cal.getTime());
	}

	/**
	 * Returns the Year with the given offset value
	 * 
	 * @return
	 */
	public String getDateWithOffset(int offset, String date_format) {
		SimpleDateFormat date_in_string = new SimpleDateFormat(date_format);
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.DATE, offset);
		return date_in_string.format(cal.getTime().getTime());
	}

	/**
	 * Add or Subtract the year by the given offset from the current date
	 * 
	 * @param dateFormat
	 * @param offset
	 * @return
	 */

	public long modifyCurrentDateByOffset(String type, int offset) {
		Date d = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		switch (type.toLowerCase()) {
		case "year":
			cal.add(Calendar.YEAR, offset);
			break;
		case "month":
			cal.add(Calendar.MONTH, offset);
			break;
		case "day":
			cal.add(Calendar.DATE, offset);
			break;
		}
		// On DB table, Timestamp in milliseconds is not accepted but the Epoch
		// Timestamp is accepted.
		// To get the Epoch timestamp division operand is used on long datatype
		// timestamp in ms.
//		System.out.println("Changed Time: " + cal.toString());
		return cal.getTime().getTime() / 1000L;
	}

	// To switch to New Tab
	public void switchToNewTab() {
		sleepFor(1);
		ArrayList<String> tabs2 = new ArrayList<String>(DriverManager.getDriver().getWindowHandles());
		try {
			DriverManager.getDriver().switchTo().window(tabs2.get(1));
			sleepFor(3);
		} catch (Exception e) {
		}
	}

	// to switch back to Main Execution Tab
	public void switchToMainTab() {
		sleepFor(1);
		ArrayList<String> tabs2 = new ArrayList<String>(DriverManager.getDriver().getWindowHandles());
		if (tabs2.size() > 1) {
			DriverManager.getDriver().close();
			DriverManager.getDriver().switchTo().window(tabs2.get(0));
			sleepFor(3);
		}
	}

	/**
	 * It will close all the opened windows and witch the focus to currently focused
	 * window
	 */
	public void close_all_switch_to_currently_focus_window() {
		sleepFor(1);
		String currently_focused_window_handle = DriverManager.getDriver().getWindowHandle();
		for (String handles : DriverManager.getDriver().getWindowHandles()) {
			if (!handles.equals(currently_focused_window_handle)) {
				DriverManager.getDriver().switchTo().window(handles);
				DriverManager.getDriver().close();
				DriverManager.getDriver().switchTo().window(currently_focused_window_handle);
			}
		}
	}

	/**
	 * 
	 * @return the Title of the page
	 */
	public String getTitle() {
		return DriverManager.getDriver().getTitle();
	}

	/**
	 * 
	 * @return the Current URL of the page
	 */
	public String getCurrentUrl() {
		sleepFor(5);
		return DriverManager.getDriver().getCurrentUrl();
	}

	/**
	 * Return the value from the JS command
	 * 
	 * @param JScommand
	 */
	public Object javascriptExecutionWithReturn(String command) {
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
		Object value = executor.executeScript("return " + command);
		return value;
	}

	/**
	 * Execute the JS command
	 * 
	 * @param JScommand
	 */
	public void javascriptExecutionWithoutReturn(String command) {
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
		executor.executeScript(command);
	}

	/**
	 * Will forward the Streaming video to the end.
	 * 
	 */
	public void fast_forward_jw_video_player() {
		Object value = javascriptExecutionWithReturn("jwplayer().getDuration();");
		int duration = 0;
		if (value instanceof Long) {
			duration = Math.toIntExact(Long.parseLong(String.valueOf(value)));
		} else if (value instanceof Double) {
			duration = Math.toIntExact(Math.round(Double.parseDouble(String.valueOf(value))));
		}
		log.info("Duration Time:: " + duration);
		duration = duration - 30;
		log.info("Duration Time:: " + duration);
		// javascriptExecutionWithoutReturn("jwplayer().seek(" + duration +
		// ");");
	}

	/**
	 * Move to specific web element.
	 * 
	 * @param ele
	 */
	public void moveToElement(WebElement ele) {
		for (int i = 0; i < 5; i++) {
			sleepFor(1);
			try {
				JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
				executor.executeScript("arguments[0].scrollIntoView(true);", ele);
				HighlightMyElement(ele);
				break;
			} catch (StaleElementReferenceException e) {
				continue;
			}
		}
	}

	/**
	 * Move to specific web element.
	 * 
	 * @param ele
	 */
	public void moveToElement(By locator) {
		for (int i = 0; i < 5; i++) {
			sleepFor(1);
			WebElement ele = DriverManager.getDriver().findElement(locator);
			HighlightMyElement(ele);
			try {
				JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
				executor.executeScript("arguments[0].scrollIntoView(true);", ele);
				break;
			} catch (StaleElementReferenceException e) {
				continue;
			}
		}
	}

	/**
	 * Mouse hover to the specified element
	 * 
	 * @param ele
	 */
	public void mouseHover(By locator) {
		moveToElement(DriverManager.getDriver().findElement(locator));
		Actions action = new Actions(DriverManager.getDriver());
		action.moveToElement(DriverManager.getDriver().findElement(locator)).build().perform();
	}

	/**
	 * Mouse hover to the specified element
	 * 
	 * @param ele
	 * @throws AWTException 
	 */
	public void mouseHover(WebElement locator) {
//		moveToElement(locator);
//		Actions action = new Actions(DriverManager.getDriver());
//		action.moveToElement(locator).build().perform();

		moveToElement(locator);
//		Actions action = new Actions(DriverManager.getDriver());
		Point location = locator.getLocation();
		int x=location.getX();
		int y=location.getY();
		Robot r=null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		action.moveToElement(locator, x+1, y);
//		action.moveByOffset(x, y).perform();
//		action.moveToElement(locator).click(locator).build().perform();
//		action.moveToElement(locator, x, y).perform();
		r.mouseMove(x, y);
	}

	/**
	 * @param driver
	 * @param webElement
	 * @return
	 */

	public boolean elementNotPresent(By locator) {
		try {
			waitForElementPresent(locator, 30);
			WebElement element = DriverManager.getDriver().findElement(locator);
			if (!element.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Used to scroll to top of the page
	 * 
	 * @throws Exception
	 */
	public void scrollToTopOfPage() throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
		executor.executeScript("window.scrollTo(0, 250);");
		sleepFor(2);
	}

	/**
	 * Used to scroll to top of the page
	 * 
	 * @throws Exception
	 */
	public void scrollToBottomOfPage() throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
		executor.executeScript("window.scrollBy(0, 500);", "");
		sleepFor(2);
	}

	/**
	 * Return the selected option of the Drop down
	 * 
	 * @param locator
	 * @param wait_time
	 * @return
	 */
	public String get_first_selected_option(By locator, int wait_time) {

		String value = null;
		Select localeList;
		WebElement element = waitForElement(locator, wait_time);
		if (element != null) {
			moveToElement(element);
			try {
				localeList = new Select(element);
			} catch (IllegalStateException e) {
				localeList = new Select(element);
			}
		} else {
			localeList = null;
		}
		try {
			value = localeList.getFirstSelectedOption().getAttribute("value");
		} catch (IllegalStateException e) {
			value = localeList.getFirstSelectedOption().getAttribute("value");
		} catch (NoSuchElementException e) {
			Assert.fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail(locator + " ACTION FAILED. TEST FAILED.");
		}
		log.info("Selcted option: " + value);
		return value;
	}

	/**
	 * Return the selected option of the Drop down
	 * 
	 * @param locator
	 * @param wait_time
	 * @return
	 */
	public String get_first_selected_option(WebElement element, String type, int wait_time) {

		String value = null;
		Select localeList;
		if (element != null) {
			moveToElement(element);
			try {
				localeList = new Select(element);
			} catch (IllegalStateException e) {
				localeList = new Select(element);
			}
		} else {
			localeList = null;
		}
		try {
			if (type.equalsIgnoreCase("value")) {
				value = localeList.getFirstSelectedOption().getAttribute("value");
			} else {
				value = localeList.getFirstSelectedOption().getText();
			}
		} catch (IllegalStateException e) {
			if (type.equalsIgnoreCase("value")) {
				value = localeList.getFirstSelectedOption().getAttribute("value");
			} else {
				value = localeList.getFirstSelectedOption().getText();
			}
		} catch (NoSuchElementException e) {
			Assert.fail(element + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (NullPointerException e) {
			Assert.fail(element + " LISTBOX NOT FOUND. TEST FAILED.");
		} catch (org.openqa.selenium.WebDriverException e) {
			Assert.fail(element + " ACTION FAILED. TEST FAILED.");
		}
		log.info("Selected option: " + value);
		return value;
	}

	/**
	 * Wait for an page to display the specific title
	 * 
	 * @param title_to_compare
	 * @param wait_time
	 */
	public void waitForGivenPageTitle(String title_to_compare, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), wait_time);
		wait.until(ExpectedConditions.titleIs(title_to_compare));

	}

	/**
	 * Wait for element in visibility from the page
	 * 
	 * @param title_to_compare
	 * @param wait_time
	 */
	public void waitForElementNotVisible(By locator, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), wait_time);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/**
	 * Wait for element in visibility from the page by text
	 * 
	 * @param title_to_compare
	 * @param wait_time
	 */
	public void waitForElementNotVisibleByText(By locator, String text, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), wait_time);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
	}

	/**
	 * Wait for element value to be not empty
	 * 
	 * @param title_to_compare
	 * @param wait_time
	 */
	public void waitForElementValueNotEmpty(By locator, String attribute_name, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), wait_time);
		wait.until(ExpectedConditions.attributeToBeNotEmpty(DriverManager.getDriver().findElement(locator),
				attribute_name));

	}

	/**
	 * Delete all the cookies of the current session
	 */
	public void delete_session_cookies() {
		DriverManager.getDriver().manage().deleteAllCookies();
	}

	/**
	 * Print the Web Browser console logs
	 */
	public void analyzeLog() {
		LogEntries logEntries = DriverManager.getDriver().manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
			// do something useful with the data
		}
	}

	/**
	 * To navigate back to the page
	 */
	public void navigate_back() {
		DriverManager.getDriver().navigate().back();
		acceptIfAlertPresent();
		log.info("Navigated back to Backpage in Browser");
	}

	/**
	 * Get NFSP Source from the Page Source. If the parameter position value is 2,
	 * it will return the second NFSP Source value which is present in the view page
	 * source.
	 * 
	 * @param position
	 * @return
	 */
	public String get_nfsp_source_from_page_source(int position) {
		String page_source = DriverManager.getDriver().getPageSource();
		for (int pos = 0; pos < position; pos++) {
			page_source = page_source.substring(page_source.indexOf("\"source\":") + 9, page_source.length());
		}
		page_source = page_source.substring(1, page_source.indexOf(",") - 1);
		log.info("NFSP Source: " + page_source);
		return page_source;
	}

	/**
	 * Get NFSP Segment from the Page Source. If the parameter position value is 2,
	 * it will return the second NFSP Segment value which is present in the view
	 * page source.
	 * 
	 * @param position
	 * @return
	 */
	public String get_nfsp_segment_from_page_source(int position) {
		String page_source = DriverManager.getDriver().getPageSource();
		for (int pos = 0; pos < position; pos++) {
			page_source = page_source.substring(page_source.indexOf("\"segment\"") + 10, page_source.length());
		}
		page_source = page_source.substring(1, page_source.indexOf(",") - 1);
		log.info("NFSP Segment: " + page_source);
		return page_source;
	}

	/**
	 * Get NFSP Segment from the given JSON string.
	 * 
	 * @param json_string - JSON in string
	 * @param source_name - Name which access id/segment has to be retrieve
	 * @param key_name    - Some cases access id/segment has key name. If it has can
	 *                    be pass here. It's a variable argument.
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static String get_nfsp_segment_from_json(String json_string, String source_name, String... key_name)
			throws JsonProcessingException, IOException {
		String segment = (key_name.length > 0 ? json_parser(json_string, source_name, key_name[0])
				: json_parser(json_string, source_name)).replace("[", "").replace("]", "").replace("\"", "").trim();
		if (key_name.length > 0)
			return segment;
		else {
			return segment.contains(",")
					? DEVICE.equalsIgnoreCase("Desktop") ? segment.split(",")[0] : segment.split(",")[2]
					: segment;
		}
	}

	/**
	 * Get GS Id from the Page Source. If the parameter position value is 2, it will
	 * return the second NFSP Segment value which is present in the view page
	 * source.
	 * 
	 * @param position
	 * @return
	 */
	public String get_gs_id(int position) {
		String page_source = DriverManager.getDriver().getPageSource();
		for (int pos = 0; pos < position; pos++) {
			page_source = page_source.substring(page_source.indexOf("gsid") + 6, page_source.length()).trim()
					.replaceAll("'", "");
		}
		page_source = page_source.substring(0, page_source.indexOf("}")).trim();
		log.info("GS Id: " + page_source);
		return page_source;
	}

	/**
	 * Returns the Page Source
	 * 
	 * @return
	 */
	public String get_page_source() {
		String page_source = DriverManager.getDriver().getPageSource();
		return page_source;
	}

	// To Highlight element - Added by Vinoth
	public static void HighlightMyElement(WebElement element) {
		if (BaseClass.debug_Mode == true) {
			JavascriptExecutor highlight = (JavascriptExecutor) DriverManager.getDriver();
			// To highlight an Element
			highlight.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"background: cyan; border: 5px solid yellow;");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// To make the element default
			highlight.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");

		}

	}

}

class ElementPresent implements ExpectedCondition<WebElement> {
	private final By locator;

	public ElementPresent(By locator) {
		this.locator = locator;
	}

	public WebElement apply(WebDriver driver) {
		return driver.findElement(locator);
	}

}