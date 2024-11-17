package com.util;

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
import java.util.concurrent.TimeUnit;

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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author mperumal, mahesh
 *
 */
public class WrapperFunctions {

	private static Integer DELAY = 1;
	private final static long ACTION_TIMEOUT_TIME = 30000;
	protected int stepNo;
	protected StringBuffer step_description = new StringBuffer();

	static {
		Properties sys_props = System.getProperties();
		System.out.println(sys_props.get("env_to_execute"));
		System.out.println(sys_props.get("device_to_launch"));
		System.out.println(sys_props.get("browser_to_invoke"));
		System.out.println(sys_props.get("enable_browserstack"));
		System.out.println(sys_props.size());
	}

	protected final static String WORK_DIRECTORY = System.getProperty("user.dir");
	private final static String CHROMEDRIVER_PATH = "src/main/resources/browserdrivers/chromedriver.exe";
	protected final static String DEVICE = env_property_file_reader("device_to_launch");
	protected final static String ENVIRONMENT = env_property_file_reader("env_to_execute");
	protected final static String RUN_ON_BROWSERSTACK = env_property_file_reader("enable_browserstack");
	protected final static String APP_MESSAGES_PROP_PATH = "src/main/resources/prop/app_message.properties";
	private final static String ENVIRONMENT_PROP_PATH = "src/main/resources/prop/Environment.properties";
	private final static String APPLICATION_INPUT_FILE = "src/test/resources/AppData.xml";
	protected final static String CLEAR_TEMP_FILE = "src/main/resources/batchfiles/TempDelete.bat";
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private static final String BROWSERSTACK_URL = "https://" + env_property_file_reader("browserstack_username") + ":"
			+ env_property_file_reader("browserstack_accesskey") + "@hub-cloud.browserstack.com/wd/hub";

	/**
	 * Invoke the Mobile device browser based on the given configuration either
	 * from the Browser Stack or from the local machine
	 * 
	 * @param xml_test_name
	 * @return
	 * @throws MalformedURLException
	 */
	public static WebDriver createInstance(String... xml_test_name) throws MalformedURLException {
		WebDriver driver = null;
		EventFiringWebDriver event_firing_driver = null;
		DesiredCapabilities caps = new DesiredCapabilities();
		Map<String, Object> mobile_emulation = new HashMap<String, Object>();
		ChromeOptions chrome_options = new ChromeOptions();
		log.info("==========================================================================================");
		log.info("=============================== TEST EXECUTION STARTED ===============================");
		log.info("==========================================================================================");
		log.info("Device is :: " + DEVICE);

		if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
			instantiate_browserstack_instance();
		}
		switch (DEVICE.toLowerCase()) {
		case "android":
			if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
				caps.setCapability("os_version", "7.1");
				caps.setCapability("device", "Google Pixel");
				caps.setCapability("browserstack.appium_version", "1.7.2");
				caps.setCapability("build", "Android 7.1");
				break;
			} else {
				mobile_emulation.put("deviceName", "Nexus 6P");
				// mobile_emulation.put("userAgent",
				// "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5
				// Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko)
				// Chrome/18.0.1025.166 Mobile Safari/535.19");
			}
			break;
		case "ios":
			if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
				caps.setCapability("os_version", "11.0");
				caps.setCapability("device", "iPhone 8 Plus");
				caps.setCapability("build", "IOS 11.0");
			} else {
				mobile_emulation.put("deviceName", "iPhone 7");
			}
		}

		if (RUN_ON_BROWSERSTACK != null && RUN_ON_BROWSERSTACK.equalsIgnoreCase("true")) {
			caps.setCapability("real_mobile", "true");
			caps.setCapability("browserstack.local", "true");
			caps.setCapability("browserstack.debug", "true");
			caps.setCapability("project", "FrontPage");
			caps.setCapability("name", "Mobile" + xml_test_name[0]);
			// caps.setCapability("deviceOrientation", "landscape");
			driver = new RemoteWebDriver(new URL(BROWSERSTACK_URL), caps);
		} else {
			// ChromeDriverManager.getInstance().setup();
			System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
			// Map<String, Object> deviceMetrics = new HashMap<>();
			// deviceMetrics.put("width", 360);
			// deviceMetrics.put("height", 640);
			// deviceMetrics.put("pixelRatio", 3.0);
			// mobile_emulation.put("deviceMetrics", deviceMetrics);
			chrome_options.setExperimentalOption("mobileEmulation", mobile_emulation);
			driver = new ChromeDriver(chrome_options);
			driver.manage().window().setSize(new org.openqa.selenium.Dimension(420, 690));
		}

		// Added the implicit timeout 10 seconds for Driver instance
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// Casted WebDriver as EventFiringWebDriver instance
		DriverManager.set_webdriver(driver);
		event_firing_driver = new EventFiringWebDriver(driver);
		EventHandler handler = new EventHandler();
		event_firing_driver.register(handler);
		driver.manage().deleteAllCookies();
		DriverManager.set_event_firing_webdriver(event_firing_driver);
		return DriverManager.get_event_firing_webdriver();
	}

	/**
	 * Used to instantiate the Browser Stack instance of the local machine
	 * irrespective of the OS
	 * 
	 * @author mperumal
	 */
	private static void instantiate_browserstack_instance() {
		String os_name = System.getProperty("os.name").toLowerCase();
		String absolute_path;
		log.info("os_name: " + os_name);
		if (os_name.contains("windows")) {
			log.info("Inside Windows BrowserStack call");
			absolute_path = System.getProperty("user.dir") + "\\src\\main\\resources\\browserstack\\";
			try {
				ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "StartBrowserStack.bat");
				File dir = new File(absolute_path);
				pb.directory(dir);
				pb.start();
				sleepFor(90);
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

	public static void switchToLandscopeMode(int width, int hight) throws Exception {
		DriverManager.get_event_firing_webdriver().manage().window().setSize(new Dimension(width, hight));
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
			NodeList nodes = doc.getElementsByTagName(NodeName.toUpperCase());

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) node;
				}
			}
			Value = getValue(PropertyValue, element);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Value;
	}

	// Dependent method to read data from XML
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	// To read data from environment properties file
	public static String env_property_file_reader(String propertyname) {
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
		return prop.getProperty(propertyname);
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
		log.info("app message: " + value);
		return value;
	}

	// To get the current date in MM-dd-yyyy HH-mm-ss format
	public static String getCurrentDateWithTimeStamp() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss");
		String Current = format1.format(cal.getTime());
		// log.info("Current date is :: " + Current);
		return Current;
	}

	public WebElement waitForElement(By locator) {
		try {
			int count = 1;
			log.info("Waiting upto " + ACTION_TIMEOUT_TIME + "ms for element with locator: \"" + locator
					+ "\" to appear on page.");
			while (DriverManager.get_event_firing_webdriver().findElements(locator).size() == 0 && count <= 60) {
				Thread.sleep(1000);
				log.info("Waiting " + 1 + "000 ms for count " + count);
				count++;
			}
			return (DriverManager.get_event_firing_webdriver().findElement(locator));
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
			while (element.isDisplayed() && count <= 60) {
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
			WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), wait_time);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception e) {
		}
	}

	public static void waitForElementVisibility(By locator, int wait_time) {
		log.info("Waiting for visibility of element with locator: \"" + locator + "\" to appear on page.");
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), wait_time);
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
			WebElement element = DriverManager.get_event_firing_webdriver().findElement(locator);
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
	public boolean elementVisibility(By locator, int... wait_time) {
		try {
			if (wait_time.length > 0)
				waitForElementVisibility(locator, wait_time[0]);
			else
				waitForElementVisibility(locator, 30);
			moveToElement(locator);
			WebElement element = DriverManager.get_event_firing_webdriver().findElement(locator);
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
			} catch (Exception IES) {
				log.error("Element is not currently not interactable: " + IES.getMessage());
				JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
				executor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
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
			} catch (Exception IES) {
				log.error("Element is not currently not interactable: " + IES.getMessage());
				JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
				executor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
			}
		}
		return element;
	}

	/**
	 * Enter the text box by given value via JS
	 * 
	 * @param element
	 * @param textBoxAction
	 * @param value
	 */
	public void textbox_by_js(WebElement element, String textBoxAction, String value) {
		waitForElement(element);
		moveToElement(element);
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	/**
	 * Enter the text box by given value via JS
	 * 
	 * @param element
	 * @param textBoxAction
	 * @param value
	 */
	public void textbox_by_js(By locator, String textBoxAction, String value, int wait_time) {
		WebElement element = waitForElement(locator, wait_time);
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
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
		Wait<WebDriver> wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), DELAY * sec);
		ExpectedCondition<WebElement> condition = new ElementPresent(element);
		try {
			WebElement we = wait.until(condition);
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

		Wait<WebDriver> wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), DELAY * sec);
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

	public void selectByVisibleText(By locator, String value, int WaitTime) {
		Select localeList;
		WebElement element = waitForElement(locator, WaitTime);
		if (element != null) {
			moveToElement(element);
			localeList = new Select(element);
			localeList.selectByVisibleText(value);
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 */

	public void selectByVisibleText(WebElement element, String value, int WaitTime) {
		Select localeList;
		if (element != null) {
			moveToElement(element);
			localeList = new Select(element);
			localeList.selectByVisibleText(value);
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 */

	public void select_visible_text_by_js(By locator, String value, int WaitTime) {
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		WebElement element = waitForElement(locator, WaitTime);
		if (element != null) {
			List<WebElement> elements = element.findElements(By.tagName("option"));
			for (WebElement ele : elements) {
				String dropdown_text = (String) executor.executeScript("return arguments[0].text;", ele);
				if (value.equalsIgnoreCase(dropdown_text)) {
					executor.executeScript("arguments[0].selected=\"selected\"", ele);
					dropdown_text = (String) executor.executeScript("return arguments[0].text;", ele);
					break;
				}
			}
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 */

	public void select_visible_text_by_js(WebElement element, String value, int WaitTime) {
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		if (element != null) {
			for (WebElement ele : element.findElements(By.tagName("option"))) {
				String dropdown_text = (String) executor.executeScript("return arguments[0].text;", ele);
				if (value.equalsIgnoreCase(dropdown_text)) {
					executor.executeScript("arguments[0].selected=\"selected\"", ele);
					break;
				}
			}
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 * 
	 * 		To select a value from drop down by its Option value
	 */

	public void selectByOption(By locator, String value, int WaitTime) {
		WebElement element = waitForElement(locator, WaitTime);
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		if (element != null) {
			for (WebElement ele : element.findElements(By.tagName("option"))) {
				String category_type = (String) executor.executeScript("return arguments[0].text;", ele);
				String category_type_value = "0";
				if (category_type.equalsIgnoreCase(value)) {
					try {
						category_type_value = (String) executor.executeScript("return arguments[0].value;", ele);
						ele.click();
						break;
					} catch (Exception e) {
						try {
							executor.executeScript("arguments[0].click();", ele);
							executor.executeScript("arguments[0].value=\"" + category_type_value + "\"", element);
							break;
						} catch (Exception ex) {
							moveToElement(element);
							Actions builder = new Actions(DriverManager.get_event_firing_webdriver());
							builder.moveToElement(ele);
							builder.click(ele).build().perform();
						}
					}
				}
			}
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 * 
	 * 		To select a value from drop down by its Option value
	 */

	public void selectByOption(WebElement element, String value, int WaitTime) {
		waitForElement(element);
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		if (element != null) {
			for (WebElement ele : element.findElements(By.tagName("option"))) {
				String category_type = (String) executor.executeScript("return arguments[0].text;", ele);
				String category_type_value = "0";
				if (category_type.equalsIgnoreCase(value)) {
					try {
						category_type_value = (String) executor.executeScript("return arguments[0].value;", ele);
						ele.click();
						break;
					} catch (Exception e) {
						try {
							executor.executeScript("arguments[0].click();", ele);
							executor.executeScript("arguments[0].value=\"" + category_type_value + "\"", element);
							break;
						} catch (Exception ex) {
							moveToElement(element);
							Actions builder = new Actions(DriverManager.get_event_firing_webdriver());
							builder.moveToElement(ele);
							builder.click(ele).build().perform();
						}
					}
				}
			}
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 */

	public Select selectByVisibleValue(By locator, String value, int WaitTime) {
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
			localeList.selectByValue(value);
		} catch (IllegalStateException e) {
			localeList.selectByValue(value);
		} catch (Exception e) {
			log.error("Element is not interactable: " + e.getMessage());
			JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
			executor.executeScript("arguments[0].value=\"" + value + "\"", element);
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

	public Select selectByVisibleValue(WebElement element, String value, int WaitTime) {
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
			localeList.selectByValue(value);
		} catch (IllegalStateException e) {
			localeList.selectByValue(value);
		} catch (Exception e) {
			log.error("Element is not interactable: " + e.getMessage());
			JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
			executor.executeScript("arguments[0].value=\"" + value + "\"", element);
		}
		return localeList;
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param action
	 * @return
	 * @throws Exception
	 */

	public WebElement button(By locator, int WaitTime) {
		WebElement element = waitForElement(locator, WaitTime);
		if (element != null) {
			try {
				moveToElement(element);
				elementIsClickable(locator, 10);
				element.click();
			} catch (WebDriverException e) {
				try {
					js_click(locator, WaitTime);
				} catch (Exception ex) {
					moveToElement(element);
					Actions builder = new Actions(DriverManager.get_event_firing_webdriver());
					builder.moveToElement(element);
					builder.click(element).build().perform();
				}
			}
		}
		return element;
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param action
	 * @return
	 * @return
	 * @throws Exception
	 */
	public WebElement button(WebElement element, int WaitTime) {
		if (element != null) {
			try {
				moveToElement(element);
				waitForElement(element);
				element.click();
			} catch (WebDriverException e) {
				try {
					js_click(element);
				} catch (Exception ex) {
					moveToElement(element);
					Actions builder = new Actions(DriverManager.get_event_firing_webdriver());
					builder.moveToElement(element);
					builder.click(element).build().perform();
				}
			}
		}
		return element;
	}

	/**
	 * Perform Click action by Action class
	 */
	public void action_click() {
		Actions a = new Actions(DriverManager.get_event_firing_webdriver());
		Action click = a.click().build();
		click.perform();
	}

	/**
	 * Perform Click action by Javascript class
	 */
	public void js_click(WebElement element) {
		waitForElement(element);
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * Perform Click action by Javascript class
	 */
	public WebElement js_click(By locator, int wait_time) {
		WebElement ele = waitForElement(locator, wait_time);
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("arguments[0].click();", ele);
		return ele;
	}

	/**
	 * @param Locator
	 * @param driver
	 */
	public boolean elementIsClickable(By Locator, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), wait_time);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// To close external Popup windows upon windows count
	public static void getAdHandler(WebDriver driver) throws Exception {
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
		Assert.fail("[ELEMENT SEARCH FAILED] ELEMENT WITH DESCRIPTION " + locator
				+ " IS NOT FOUND; TEXT CANNOT BE EXTRACTED!");
		return "";
	}

	// To refresh the page and accept the nav away message if displayed
	public void doRefresh() throws Exception {
		try {
			DriverManager.get_event_firing_webdriver().navigate().refresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
		acceptIfAlertPresent();
	}

	// To accept the nav away message on browser window
	public void acceptIfAlertPresent() {
		try {
			WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), 2);
			wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = DriverManager.get_event_firing_webdriver().switchTo().alert();
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
	 * @author Mahesh Ambati
	 * 
	 *         To mouse over on any element using Java script executor
	 */

	public void mouseHoverJScript(WebElement HoverElement) {
		try {
			if (isElementPresent(HoverElement)) {
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) DriverManager.get_event_firing_webdriver()).executeScript(mouseOverScript,
						HoverElement);
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
			DriverManager.get_event_firing_webdriver().switchTo()
					.frame(DriverManager.get_event_firing_webdriver().findElements(By.tagName(FrameName)).get(count));
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	// To switch the focus to iframe based on id/name
	public void switch_to_iframe_by_webelement(By frame_locator, int count) throws Exception {
		DriverManager.get_event_firing_webdriver().switchTo()
				.frame(DriverManager.get_event_firing_webdriver().findElements(frame_locator).get(count));
	}

	// To switch the focus back to the main page
	public void switch_iframe_default_content() {
		DriverManager.get_event_firing_webdriver().switchTo().defaultContent();
	}

	/**
	 * 
	 * @param appDriver
	 * @param locator
	 * @param expectedAttribute
	 * @return
	 * 
	 * 		To get specified attribute value of a page element and returns
	 *         the String value of an attribute
	 */
	public String getAttribute(By locator, String expectedAttribute) {
		String attributeValue = null;
		try {
			waitForElement(locator);
			attributeValue = DriverManager.get_event_firing_webdriver().findElement(locator)
					.getAttribute(expectedAttribute).trim();
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
	 * 		To get specified attribute value of a page element and returns
	 *         the String value of an attribute
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
		log.info("Attribute value is :: "
				+ DriverManager.get_event_firing_webdriver().findElement(element).getAttribute(Attribute));
		boolean status = DriverManager.get_event_firing_webdriver().findElement(element).getAttribute(Attribute)
				.contains(MatchText);
		return status;
	}

	// Navigate to specific URL, Clear cookies, wait for page to load and accept
	// the nav away message if displayed.
	public void navigateTo(String URL) throws Exception {
		acceptIfAlertPresent();
		DriverManager.get_event_firing_webdriver().manage().deleteAllCookies();
		try {
			DriverManager.get_event_firing_webdriver().navigate().refresh();
			acceptIfAlertPresent();

		} catch (WebDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// WrapperFunctions.waitForPageToLoad(DriverManager.get_event_firing_webdriver());
		DriverManager.get_event_firing_webdriver().navigate().to(URL);
		acceptIfAlertPresent();
	}

	// Navigate to URL without clearing cookies, wait for page to load and
	// accept the nav away message if displayed.
	public void navigateTo_WOC_Cookies(String URL) {
		// WrapperFunctions.waitForPageToLoad(DriverManager.get_event_firing_webdriver());
		// Navigate to specific URL
		try {
			DriverManager.get_event_firing_webdriver().navigate().to(URL);
			acceptIfAlertPresent();
		} catch (WebDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// To count the Total iframes on the page and get the count of desired
	// iframe. Parameter frame_position is to get the desired frame from the
	// DOM, if the page has same frame attributes for multiple frames.
	public int count_iframe_get_desired_iframe_count(String eIframe, String egetAttribute, String verificationText) {
		int frameNo = 0;
		List<WebElement> frames = DriverManager.get_event_firing_webdriver().findElements(By.tagName(eIframe));
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
		// waitForPageToLoad(DriverManager.get_event_firing_webdriver());
		boolean Step_Status = DriverManager.get_event_firing_webdriver().getPageSource().contains(PageSource);
		return Step_Status;
	}

	// To verify the image url based on image location
	protected boolean verifyImage(WebElement element, String Attribute, String ImageURL) {
		boolean status = element.getAttribute(Attribute).equalsIgnoreCase(ImageURL);
		return status;
	}

	// To delete single cookie
	public void deleteCookieByName(String CookieName) throws Exception {
		// waitForPageToLoad(DriverManager.get_event_firing_webdriver());
		DriverManager.get_event_firing_webdriver().manage().deleteCookieNamed(CookieName);
		sleepFor(5);
	}

	// To verify new tab is opened, verify the text in URL and close the tab
	public boolean verify_NewTab_And_Close(String VerifURLText) {
		boolean Status = false;
		sleepFor(1);
		String currentHandle = DriverManager.get_event_firing_webdriver().getWindowHandle();
		Set<String> handles = DriverManager.get_event_firing_webdriver().getWindowHandles();
		handles.remove(currentHandle);
		if (handles.size() > 0) {
			DriverManager.get_event_firing_webdriver().switchTo().window(handles.iterator().next());

			Status = DriverManager.get_event_firing_webdriver().getCurrentUrl().contains(VerifURLText);
		}
		DriverManager.get_event_firing_webdriver().close();
		try {
			Alert alert = DriverManager.get_event_firing_webdriver().switchTo().alert();
			if (alert != null) {
				alert.accept();
			}
		} catch (Exception ignore) {
		}
		DriverManager.get_event_firing_webdriver().switchTo().window(currentHandle);
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
		log.info("StepNo: " + StepNo + " is initialized.");
		log.info("Step decription is: " + step);
	}

	// Validate the step and take screenshot if the step is failed.
	protected boolean step_validator(boolean step_status) throws IOException {
		Assert.assertTrue(step_status);
		log.info("StepNo: " + stepNo + " is Completed.");
		return step_status;
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
	 * Get the logger instance for the specified class. Logged the message in
	 * the Info level
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
			moveToElement(DriverManager.get_event_firing_webdriver().findElement(locator));
		} catch (NoSuchElementException NSE) {
			log.info(NSE.getMessage());
		}
		return DriverManager.get_event_firing_webdriver().findElements(locator);
	}

	/**
	 * Trigger the GET Call via HTTP Client third party jar. Uses Jackson third
	 * party API to parse the JSON response to get the specified parameter value
	 * 
	 * @return Given parameter value from the GET Response
	 * @author mperumal
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
	 * @author mperumal
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
	 * Parse the JSON format String response and retrieve the value for the
	 * given parameter key. To retrieve the child node, give the parent node
	 * name as third parameter
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
		WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), wait_time);
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
		return cal.getTime().getTime() / 1000L;
	}

	// To switch to New Tab
	public void switchToNewTab() {
		sleepFor(1);
		ArrayList<String> tabs2 = new ArrayList<String>(DriverManager.get_event_firing_webdriver().getWindowHandles());
		try {
			DriverManager.get_event_firing_webdriver().switchTo().window(tabs2.get(1));
			sleepFor(3);
		} catch (Exception e) {
		}
	}

	// to switch back to Main Execution Tab
	public void switchToMainTab() {
		sleepFor(1);
		ArrayList<String> tabs2 = new ArrayList<String>(DriverManager.get_event_firing_webdriver().getWindowHandles());
		if (tabs2.size() > 1) {
			DriverManager.get_event_firing_webdriver().close();
			DriverManager.get_event_firing_webdriver().switchTo().window(tabs2.get(0));
			sleepFor(3);
		}
	}

	/**
	 * It will close all the opened windows and witch the focus to currently
	 * focused window
	 */
	public void close_all_switch_to_currently_focus_window() {
		sleepFor(1);
		String currently_focused_window_handle = DriverManager.get_event_firing_webdriver().getWindowHandle();
		for (String handles : DriverManager.get_event_firing_webdriver().getWindowHandles()) {
			if (!handles.equals(currently_focused_window_handle)) {
				DriverManager.get_event_firing_webdriver().switchTo().window(handles);
				DriverManager.get_event_firing_webdriver().close();
				DriverManager.get_event_firing_webdriver().switchTo().window(currently_focused_window_handle);
			}
		}
	}

	/**
	 * 
	 * @return the Title of the page
	 */
	public String getTitle() {
		return DriverManager.get_event_firing_webdriver().getTitle();
	}

	/**
	 * 
	 * @return the Current URL of the page
	 */
	public String getCurrentUrl() {
		return DriverManager.get_event_firing_webdriver().getCurrentUrl();
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
				JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
				executor.executeScript("arguments[0].scrollIntoView(true);", ele);
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
			WebElement ele = DriverManager.get_event_firing_webdriver().findElement(locator);
			try {
				JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
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
		moveToElement(DriverManager.get_event_firing_webdriver().findElement(locator));
		Actions action = new Actions(DriverManager.get_event_firing_webdriver());
		action.moveToElement(DriverManager.get_event_firing_webdriver().findElement(locator)).build().perform();
	}

	/**
	 * Mouse hover to the specified element
	 * 
	 * @param ele
	 */
	public void mouseHover(WebElement locator) {
		moveToElement(locator);
		Actions action = new Actions(DriverManager.get_event_firing_webdriver());
		action.moveToElement(locator).build().perform();
	}

	/**
	 * @param driver
	 * @param webElement
	 * @return
	 */

	public boolean elementNotPresent(By locator) {
		try {
			waitForElementPresent(locator, 30);
			WebElement element = DriverManager.get_event_firing_webdriver().findElement(locator);
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
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("window.scrollTo(0, 250);");
		sleepFor(2);
	}

	/**
	 * Used to scroll to top of the page
	 * 
	 * @throws Exception
	 */
	public void scrollToBottomOfPage() throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("window.scrollBy(0, 500);", "");
		sleepFor(2);
	}

	public void scrollToElement(WebElement element) throws Exception {
		JavascriptExecutor executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("arguments[0].scrollIntoView();", element);
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
		WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), wait_time);
		wait.until(ExpectedConditions.titleIs(title_to_compare));
	}

	/**
	 * Wait for element in visibility from the page
	 * 
	 * @param title_to_compare
	 * @param wait_time
	 */
	public void waitForElementNotVisible(By locator, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), wait_time);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/**
	 * Wait for element in visibility from the page by text
	 * 
	 * @param title_to_compare
	 * @param wait_time
	 */
	public void waitForElementNotVisibleByText(By locator, String text, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), wait_time);
		wait.until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
	}

	/**
	 * Wait for element value to be not empty
	 * 
	 * @param title_to_compare
	 * @param wait_time
	 */
	public void waitForElementValueNotEmpty(By locator, String attribute_name, int wait_time) {
		WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), wait_time);
		wait.until(ExpectedConditions.attributeToBeNotEmpty(
				DriverManager.get_event_firing_webdriver().findElement(locator), attribute_name));

	}

	/**
	 * Delete all the cookies of the current session
	 */
	public void delete_session_cookies() {
		DriverManager.get_event_firing_webdriver().manage().deleteAllCookies();
	}

	/**
	 * Print the Web Browser console logs
	 */
	public void analyzeLog() {
		LogEntries logEntries = DriverManager.get_event_firing_webdriver().manage().logs().get(LogType.BROWSER);
		for (LogEntry entry : logEntries) {
			System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
			// do something useful with the data
		}
	}

	/**
	 * To navigate back to the page
	 */
	public void navigate_back() {
		DriverManager.get_event_firing_webdriver().navigate().back();
		acceptIfAlertPresent();
		log.info("Navigated back to Backpage in Browser");
	}

	/**
	 * Get NFSP Source from the Page Source. If the parameter position value is
	 * 2, it will return the second NFSP Source value which is present in the
	 * view page source.
	 * 
	 * @param position
	 * @return
	 */
	public String get_nfsp_source_from_page_source(int position) {
		String page_source = DriverManager.get_event_firing_webdriver().getPageSource();
		for (int pos = 0; pos < position; pos++) {
			page_source = page_source.substring(page_source.indexOf("\"source\":") + 9, page_source.length());
		}
		page_source = page_source.substring(1, page_source.indexOf(",") - 1);
		log.info("NFSP Source: " + page_source);
		return page_source;
	}

	/**
	 * Get NFSP Segment from the Page Source. If the parameter position value is
	 * 2, it will return the second NFSP Segment value which is present in the
	 * view page source.
	 * 
	 * @param position
	 * @return
	 */
	public String get_nfsp_segment_from_page_source(int position) {
		String page_source = DriverManager.get_event_firing_webdriver().getPageSource();
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
	 * @param json_string
	 *            - JSON in string
	 * @param source_name
	 *            - Name which access id/segment has to be retrieve
	 * @param key_name
	 *            - Some cases access id/segment has key name. If it has can be
	 *            pass here. It's a variable argument.
	 * @author mperumal
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static String get_nfsp_segment_from_json(String json_string, String source_name, String... key_name)
			throws JsonProcessingException, IOException {
		// source_name = source_name.endsWith("_m") ? source_name.substring(0,
		// source_name.lastIndexOf("_") - 1)
		// : source_name;
		String segment = (key_name.length > 0 ? json_parser(json_string, source_name, key_name[0])
				: json_parser(json_string, source_name)).replace("[", "").replace("]", "").replace("\"", "").trim();
		if (key_name.length > 0)
			return segment;
		else {
			return segment.contains(",") ? segment.split(",")[1] : segment;
		}
	}

	/**
	 * Get GS Id from the Page Source. If the parameter position value is 2, it
	 * will return the second NFSP Segment value which is present in the view
	 * page source.
	 * 
	 * @param position
	 * @return
	 */
	public String get_gs_id(int position) {
		String page_source = DriverManager.get_event_firing_webdriver().getPageSource();
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
		String page_source = DriverManager.get_event_firing_webdriver().getPageSource();
		return page_source;
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