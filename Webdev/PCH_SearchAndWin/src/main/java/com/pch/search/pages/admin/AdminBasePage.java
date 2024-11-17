package com.pch.search.pages.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.pch.search.bean.JoomlaProperty;
import com.pch.search.bean.JoomlaProperty.PropertyType;
import com.pch.search.pages.web.HomePage;
import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;
import com.pch.search.utilities.PageFactory;
import com.pch.search.utilities.SelectList;

public class AdminBasePage extends Action {

	HomePage homepage = new HomePage();

	private HtmlElement userId() {
		return driver.findElement(By.id("mod-login-username"));
	}

	private HtmlElement password() {
		return driver.findElement(By.id("mod-login-password"));
	}

	private HtmlElement loginBtn() {
		return driver.findElement(By.cssSelector(".next>a"));
	}

	private HtmlElement logOutLink() {
		return driver.findElement(By.linkText("Log out"));
	}

	private HtmlElement earnTokensTextBox() {
		return driver.findElement(By.id("jform_params_earn_link"));
	}

	public void clearEarnTokenTextBox() {
		earnTokensTextBox().clear();
	}

	public void sendTextToEarnTokens(String text) {
		earnTokensTextBox().sendKeys(text);
	}

	public HtmlElement articleManagerIcon() {
		return driver.findElement(
				By.xpath("//div[@class='icon-wrapper']/descendant::span[contains(text(),'Article Manager')]"));
	}

	public HtmlElement openInNewTabRadioBtnFrontPageSearchbox() {
		return driver.findElement(By.id("jform_params_new_window0"));
	}

	public HtmlElement pchContent(){
		return driver.findElement(By.xpath(".//*[@id='pch_content_group_0_pch_content']"));
	}
	
	public HtmlElement redirectSourceUrl(){
		return driver.findElement(By.xpath(".//*[@id='pch_redirect_source_url']"));
	}
	
	public HtmlElement redirectTargetUrl(){
		return driver.findElement(By.xpath(".//*[@id='pch_redirect_target_url']"));
	}
	
	public HtmlElement redirectExpiredUrl(){
		return driver.findElement(By.xpath(".//*[@id='pch_redirect_expired_url']"));
	}
	
	
	public void enterRedirectdetails(){
		redirectSourceUrl().sendKeys("/privacy");
		redirectTargetUrl().sendKeys("http://privacy.pch.com/en-us/");
		redirectExpiredUrl().sendKeys("/home");
		saveCloseAndClearCache();
	}
	
	 
	public void OamTool(String uname, String Pwd, String Email) throws IOException {
			String url = "admin." + Environment.getEnvironment()
					+ ".pch.com/onlineaccountmanagement/";
		   String BaseUrl="http://"+ uname +":"+ Pwd + "@" + url;
	       driver.get(BaseUrl);
	       String url1="http://admin." + Environment.getEnvironment()
					+ ".pch.com/onlineaccountmanagement/";
		   driver.get(url1);
	       driver.findElement(By.xpath(".//span[starts-with(@id,'button')]/span[contains(text(),'Search')]")).click();
	       waitForElementToBeVisible(By.xpath("//u[contains(text(),'Legacy Contest Entries')]")).click();
	       driver.findElement(By.xpath(".//input[@name='email']")).sendKeys(Email);
	       driver.findElement(By.cssSelector("#button-1041-btnIconEl")).click();
	       WebDriver drivers = PageFactory.getBrowserNDriverMap()
					.get(Environment.getBrowserType() + Thread.currentThread().getId()).getDriver();
	        List<WebElement> tr_collection=drivers.findElements(By.xpath(".//*[@id='gridview-1243-body']/tr"));
	    	     System.out.println(tr_collection.size());
	    	    Assert.assertEquals(tr_collection.size(), 2);
	    	    for(int i=0;i<=tr_collection.size();i++){
	    	   System.out.println( tr_collection.get(i).getText());
	    	     String Data=tr_collection.get(i).getText();
	    	     Assert.assertTrue(Data.contains("Search & Win"));
	    	    }
	    	    
	    	     }
	
	
	private String logIn() {
		String userName = Environment.getJoomlaUsername();
		String password = Environment.getJoomlaPassword();

		if (Environment.getEnvironment().equals("stg")) {
			driver.get("https://searchadmin.stg.pch.com/administrator/index.php");
		} else {
			String appName = Environment.getAppName();
			driver.get(Common.getAppUrl(appName) + "/administrator/index.php");
		}
		/*
		 * Logout if already logged in
		 */
		boolean isUserAlreadyLoggedIn = driver.getCountOfElementsWithXPath("//a[text()='Log out']") > 0;
		if (isUserAlreadyLoggedIn) {
			logOutLink().click();
		}
		userId().sendKeys(userName);
		password().sendKeys(password);
		Common.sleepFor(1000);
		loginBtn().click();
		driver.waitForBrowserToLoadCompletely();
		Common.sleepFor(2000);

		if (logOutLink().isDisplayed()) {
			CustomLogger.log("User logged-in to joomla admin successfully.");
			return "success";
		}

		else {
			String result = String.format("Unable to login to joomla with username : %s and password :%s", userName,
					password);
			CustomLogger.log(result);
			return result;
		}

	}
	
	public void verifyContentUpdated()
	{
		String Text= driver.getPageSource();
		if(Text.contains("Single search"))
		{
			CustomLogger.log("Content on the pages changed");
		}
		else
		{
			CustomLogger.log("content didnt get changed");
			
		}
		 
	}

	public List<String> getStoryViewsAndTimes(String tabName) {
		gotoPCHConfigurationTab(tabName);
		String numStoryViews = driver.findElement(By.id("jform_reg_lb_on_story_views")).getAttribute("value");
		/*
		 * driver.findElement(By.id("jform_reg_lb_on_story_views_repetition")).
		 * clear();
		 * driver.findElement(By.id("jform_reg_lb_on_story_views_repetition")).
		 * sendKeys("2");
		 */
		String everyStoryViews = driver.findElement(By.id("jform_reg_lb_on_story_views_repetition"))
				.getAttribute("value");
		String triggerCount = driver.findElement(By.id("jform_reg_lb_trigger_counter")).getAttribute("value");
		List<String> totalCount = new ArrayList<String>();
		totalCount.add(numStoryViews);
		totalCount.add(everyStoryViews);
		totalCount.add(triggerCount);

		saveCloseAndClearCache();

		return totalCount;
	}

	public String gotoPCHConfigurationTab(String tabName) {
		CustomLogger.log("Navigation to PCH Configuration - " + tabName);
		try {
			String loginStatus = logIn();
			if (!loginStatus.equalsIgnoreCase("success")) {
				return loginStatus;
			}

			HtmlElement moduleManagerIcn = driver.findElement(
					By.xpath("//div[@class='icon-wrapper']/descendant::span[contains(text(),'PCH Configuration')]"));
			moduleManagerIcn.click();
			// driver.waitForBrowserToLoadCompletely();
			HtmlElement tabLink = driver.findElement(
					By.xpath(String.format("//div[@id='submenu-box']/descendant::a[text()='%s']", tabName)));
			tabLink.click();
			// driver.waitForBrowserToLoadCompletely();
			return "success";

		} catch (WebDriverException wde) {
			wde.printStackTrace();
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public String gotoModule(String module) {
		CustomLogger.log("Navigation to module - " + module);
		try {
			String loginStatus = logIn();
			if (!loginStatus.equalsIgnoreCase("success")) {
				return loginStatus;
			}

			HtmlElement moduleManagerIcn = driver.findElement(
					By.xpath("//div[@class='icon-wrapper']/descendant::span[contains(text(),'Module Manager')]"));
			moduleManagerIcn.click();
			// driver.waitForBrowserToLoadCompletely();
			searchArticleOrModule(module);

			List<HtmlElement> moduleLink = driver.findElements(By.xpath(
					String.format("//table[@id='modules-mgr']/tbody/tr/descendant::a[contains(text(),'%s')]", module)));

			for (HtmlElement element : moduleLink) {
				if (!element.getText().endsWith("(copy)")) {
					if (element.getText().equalsIgnoreCase(module)) {
						element.click();
						break;
					}
				}
			}

			// driver.waitForBrowserToLoadCompletely();

			return "success";
		} catch (WebDriverException wde) {
			wde.printStackTrace();
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	/**
	 * Open a module after applying the search filters-
	 * Position,Type,Status,Access,Language.
	 * 
	 * @param module
	 * @param filters
	 * @return
	 */
	public String gotoModule(String module, Map<String, String> filters) {
		CustomLogger.log("Navigation to module - " + module);
		try {
			String loginStatus = logIn();
			if (!loginStatus.equalsIgnoreCase("success")) {
				return loginStatus;
			}

			HtmlElement moduleManagerIcn = driver.findElement(
					By.xpath("//div[@class='icon-wrapper']/descendant::span[contains(text(),'Module Manager')]"));
			moduleManagerIcn.click();
			driver.waitForBrowserToLoadCompletely();

			if (filters != null) {
				for (String filter : filters.keySet()) {
					String filterValue = filters.get(filter);
					if (filter.equalsIgnoreCase("Position")) {
						driver.findSelectList(By.name("filter_position")).selectByVisibleText(filterValue);
					} else if (filter.equalsIgnoreCase("Type")) {
						driver.findSelectList(By.name("filter_module")).selectByVisibleText(filterValue);
					} else if (filter.equalsIgnoreCase("Status")) {
						driver.findSelectList(By.name("filter_state")).selectByVisibleText(filterValue);
					} else if (filter.equalsIgnoreCase("Access")) {
						driver.findSelectList(By.name("filter_access")).selectByVisibleText(filterValue);
					} else if (filter.equalsIgnoreCase("Language")) {
						driver.findSelectList(By.name("filter_language")).selectByVisibleText(filterValue);
					} else {
						CustomLogger.log("Invalid Filter");
					}
				}
				Common.sleepForIE(2000);
			}

			searchArticleOrModule(module);

			List<HtmlElement> moduleLink = driver.findElements(By.xpath(
					String.format("//table[@id='modules-mgr']/tbody/tr/descendant::a[contains(text(),'%s')]", module)));
			Common.sleepFor(1000);
			for (HtmlElement element : moduleLink) {
				String elementText = element.getText();
				Common.sleepFor(2000);
				if (!elementText.endsWith("(copy)")) {
					element.click();
					driver.waitForBrowserToLoadCompletely();
					break;
				}
			}

			// driver.waitForBrowserToLoadCompletely();

			return "success";
		} catch (WebDriverException wde) {
			wde.printStackTrace();
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public void expandOption(String option) {
		String xpath = String.format(
				"//div[@class='panel'][descendant::span[text()='%s']]/div[contains(@class,'pane-slider')]", option);
		driver.waitForBrowserToLoadCompletely();
		HtmlElement e = driver.findElement(By.xpath(xpath));
		if (e.getAttribute("class").contains("pane-hide")) {
			e.findElement(By.xpath("../h3")).click();
		}
		Common.sleepFor(1000);
	}

	private void searchArticleOrModule(String text) {
		driver.findElement(By.id("filter_search")).clear();
		driver.findElement(By.id("filter_search")).sendKeys(text);
		driver.findElement(By.xpath("//button[text()='Search']")).click();
		Common.sleepFor(3000);
		// driver.waitForBrowserToLoadCompletely();
	}

	public String goToArticle(String article) {
		CustomLogger.log("Navigation to article - " + article);
		try {
			String loginStatus = logIn();
			if (!loginStatus.equalsIgnoreCase("success")) {
				return loginStatus;
			}

			HtmlElement articleManagerIcn = driver.findElement(
					By.xpath("//div[@class='icon-wrapper']/descendant::span[contains(text(),'Article Manager')]"));
			articleManagerIcn.click();
			// driver.waitForBrowserToLoadCompletely();

			searchArticleOrModule(article);
			driver.waitForBrowserToLoadCompletely();

			HtmlElement articleLink = driver.findElement(By.xpath(String
					.format("//table[@class='adminlist']/tbody/tr/descendant::a[contains(text(),'%s')]", article)));
			articleLink.click();
			Common.sleepFor(3000);
			// driver.waitForBrowserToLoadCompletely();
			return "success";
		} catch (WebDriverException wde) {
			wde.printStackTrace();
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public String goToArticleWithoutLogin(String article) {
		CustomLogger.log("Navigation to article - " + article);
		try {
			HtmlElement articleManagerIcn = driver.findElement(
					By.xpath("//div[@class='icon-wrapper']/descendant::span[contains(text(),'Article Manager')]"));
			articleManagerIcn.click();

			searchArticleOrModule(article);
			driver.waitForBrowserToLoadCompletely();

			HtmlElement articleLink = driver.findElement(By.xpath(String
					.format("//table[@class='adminlist']/tbody/tr/descendant::a[contains(text(),'%s')]", article)));
			articleLink.click();
			Common.sleepFor(3000);
			// driver.waitForBrowserToLoadCompletely();
			return "success";
		} catch (WebDriverException wde) {
			wde.printStackTrace();
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	/**
	 * Open a module after applying the search filters-
	 * Position,Type,Status,Access,Language.
	 * 
	 * @param module
	 * @param filters
	 * @return
	 */
	public String goToArticle(String article, Map<String, String> filters) {
		CustomLogger.log("Navigation to article - " + article);
		try {
			String loginStatus = logIn();
			if (!loginStatus.equalsIgnoreCase("success")) {
				return loginStatus;
			}

			HtmlElement articleManagerIcn = driver.findElement(
					By.xpath("//div[@class='icon-wrapper']/descendant::span[contains(text(),'Article Manager')]"));
			articleManagerIcn.click();

			for (String filter : filters.keySet()) {
				String filterValue = filters.get(filter);
				if (filter.equalsIgnoreCase("Category")) {
					driver.findSelectList(By.name("filter_category_id")).selectByVisibleText(filterValue);
				} else if (filter.equalsIgnoreCase("Max Levels")) {
					driver.findSelectList(By.name("filter_level")).selectByVisibleText(filterValue);
				} else if (filter.equalsIgnoreCase("Status")) {
					driver.findSelectList(By.name("filter_published")).selectByVisibleText(filterValue);
				} else if (filter.equalsIgnoreCase("Access")) {
					driver.findSelectList(By.name("filter_access")).selectByVisibleText(filterValue);
				} else if (filter.equalsIgnoreCase("Language")) {
					driver.findSelectList(By.name("filter_language")).selectByVisibleText(filterValue);
				} else if (filter.equalsIgnoreCase("Author")) {
					driver.findSelectList(By.name("filter_author_id")).selectByVisibleText(filterValue);
				} else {
					CustomLogger.log("Invalid Filter");
				}
			}

			searchArticleOrModule(article);

			HtmlElement articleLink = driver.findElement(By.xpath(String
					.format("//table[@class='adminlist']/tbody/tr/descendant::a[contains(text(),'%s')]", article)));
			articleLink.click();
			// driver.waitForBrowserToLoadCompletely();

			return "success";
		} catch (WebDriverException wde) {
			wde.printStackTrace();
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public String setTextProperty(String key, Object value) {
		CustomLogger.log(String.format("Setting %s to %s", key, value));
		Common.sleepFor(1000);
		try {
			setValue(key, value.toString());
			return saveCloseAndClearCache();
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public String setTokensOnfirstSearch(Object value) {
		try {
			HtmlElement txtBox = driver.findElement(By.id("tokenmatch_1_tokens"));
			txtBox.clear();
			txtBox.sendKeys(value.toString());
			return saveCloseAndClearCache();
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public String saveCloseAndClearCache() {
		CustomLogger.log("Saving the made changes");
		String resultUponSaving = saveAndClose();
		if (resultUponSaving.equals("success")) {
			administrationLink().click();
			driver.waitForBrowserToLoadCompletely();
			resultUponSaving = clearCache();
		}
		return resultUponSaving;
	}

	private HtmlElement administrationLink() {
		return driver.findElement(By.xpath("//a[text()='Administration']"));
	}

	public String clearCache() {
		CustomLogger.log("Clearing Cache on joomla");
		HtmlElement clearCacheIcn = driver
				.findElement(By.xpath("//div[@class='icon-wrapper']/descendant::span[contains(text(),'Clear Cache')]"));
		clearCacheIcn.click();
		return checkSuccessMessage();
	}

	public String setTextProperties(Map<String, Object> keyValuePair) {
		try {
			for (String key : keyValuePair.keySet()) {
				setValue(key, keyValuePair.get(key).toString());
			}
			return saveCloseAndClearCache();

		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	/**
	 * This function will set the value for single/multiple same properties
	 * 
	 * @param: Property
	 *             name
	 * @param: Proerty
	 *             value/values
	 * @return: function that will save settings and clear the cache
	 * @author Vaibhav.Singh
	 */

	public String setProperties(String property, List<String> values) {
		String locator = String.format("//div[label[contains(text(),'%s')]]/following-sibling::div/input", property);
		try {
			if (driver.getCountOfElementsWithXPath(locator) > 0) {
				List<HtmlElement> elements = driver.findElements(By.xpath(locator));
				for (int i = 0; i < elements.size(); i++) {
					elements.get(i).clear();
					elements.get(i).sendKeys(values.get(i).toString());
				}
			} else {
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(values.get(0).toString());
			}
			return saveCloseAndClearCache();

		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public void selectSegmentByValue(String value) {
		String element = null;
		try {
			element = String.format("//*[@id='pch_video_player_group_0_segments_included']");
			if (driver.getCountOfElementsWithXPath(element) > 0) {
				String elements = "//*[@id='pch_video_player_group_0_segments_included']/option[@value='" + value
						+ "']";
				driver.findElement(By.xpath(elements)).click();
			} else {

				HtmlElement locator = driver.findElement(By.id("cck1r_segments_included"));
				if (locator.isDisplayed()) {
					String locators = "//*[@id='segments_included']/option[@value='" + value + "']";
					driver.findElement(By.xpath(locators)).click();

					/*
					 * Select selectByValue = new Select((WebElement)
					 * driver.findElement(By.id("segments_included")));
					 * selectByValue.selectByValue(value);
					 */

				}

			}
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			wde.printStackTrace();
		}
	}

	public String selectValuesForProperty(String key, String... value) {
		try {
			selectValue(key, value);
			return saveCloseAndClearCache();
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public String selectValuesForProperties(Map<String, String[]> keyValuesPair) {
		try {
			for (String key : keyValuesPair.keySet()) {
				selectValue(key, keyValuesPair.get(key));
			}
			return saveCloseAndClearCache();
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			return (wde.getMessage());
		}
	}

	public void setValue(String key, String value) {
		String locator = null;
		try {
			Object json = new JSONTokener(key).nextValue();
			if (json instanceof JSONObject) {
				String fieldGroupName = ((JSONObject) json).keys().next().toString();
				String fieldName = ((JSONObject) json).getString(fieldGroupName);
				locator = String.format(
						"//li[descendant::label[text()='%s']]/following-sibling::li/descendant::label[text()='%s']/following-sibling::input",
						fieldGroupName, fieldName);
				Common.sleepFor(3000);
			} else {
				locator = String.format("//div[label[text()='%s']]/following-sibling::div/input", key);
				Common.sleepFor(3000);
				if (driver.getCountOfElementsWithXPath(locator) == 0)
					locator = String.format("//label[text()='%s']/following-sibling::input", key);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}

		driver.findElement(By.xpath(locator)).clear();
		driver.findElement(By.xpath(locator)).sendKeys(value.toString());
	}

	public String getTextProperty(String key) {
		String locator = null;
		try {
			Object json = new JSONTokener(key).nextValue();
			if (json instanceof JSONObject) {
				String fieldGroupName = ((JSONObject) json).keys().next().toString();
				String fieldName = ((JSONObject) json).getString(fieldGroupName);
				locator = String.format(
						"//li[descendant::label[text()='%s']]/following-sibling::li/descendant::label[text()='%s']/following-sibling::input",
						fieldGroupName, fieldName);
			} else {
				locator = String.format("//li[descendant::label[text()='%s']]/input", key);
				if (driver.getCountOfElementsWithXPath(locator) == 0) {
					locator = String.format("//label[text()='%s']/following-sibling::input", key);
					if (driver.getCountOfElementsWithXPath(locator) == 0) {
						locator = String.format("//label[text()='%s']/following-sibling::div/input", key);
						if (driver.getCountOfElementsWithXPath(locator) == 0) {
							locator = String
									.format("//label[text()='%s']/ancestor::div[1]/following-sibling::div/input", key);
						}

					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}

		return driver.findElement(By.xpath(locator)).getAttribute("value");
	}

	private void selectValue(String key, String... values) {
		String locator = String.format("//label[text()='%s']/following-sibling::select", key);
		String locatorArticlePage = String
				.format(".//label[text()='%s']/ancestor::div[1]/following-sibling::div/select", key);
		/*
		 * Check if this is a select box property.
		 */
		if (driver.getCountOfElementsWithXPath(locator) > 0) {
			SelectList selectableProperty = driver.findSelectList(By.xpath(locator));
			if (!selectableProperty.areValuesSelected(values)) {
				selectableProperty.selectByVisibleText(values);
			}
		} else if (driver.getCountOfElementsWithXPath(locatorArticlePage) > 0) {
			SelectList selectableProperty = driver.findSelectList(By.xpath(locatorArticlePage));
			if (!selectableProperty.areValuesSelected(values)) {
				selectableProperty.selectByVisibleText(values);
			}
		}
		/*
		 * Assuming it to be a radio button
		 */
		else {
			locator = String.format(
					"//label[text()='%s']/following-sibling::fieldset[@class='radio']/label[text()='%s']", key,
					values[0]);
			driver.findElement(By.xpath(locator)).click();
		}
	}

	private String checkSuccessMessage() {

		String successMessage = driver.findElement(By.xpath("//dd[contains(@class,'message')]//li")).getText();
		// check if success message is what we expect
		if (successMessage.contains("Successfully Saved.") || successMessage.contains("COM_PCH_CONFIG_SAVE_SUCCESS")
				|| successMessage.contains("Module successfully saved")
				|| successMessage.contains("Plugin successfully saved") || successMessage.contains("Cache Cleared")) {
			return "success";
		} else {
			return "Success element is not Displayed";
		}

	}

	private String saveAndClose() {
		if (Environment.getBrowserType().equals("ie")) {
			String locator = "#toolbar-o_save>a";
			if (driver.getCountOfElementsWithCSSSelcector(locator) == 0)
				locator = ".icon-32-save";
			driver.findElement(By.cssSelector(locator)).click();
			// driver.findElement(By.xpath(locator)).click();
			driver.waitForBrowserToLoadCompletely();
			return checkSuccessMessage();
		} else {
			String locator = "//li[@id='toolbar-o_save']/a";
			if (driver.getCountOfElementsWithXPath(locator) == 0)
				locator = "//li[@id='toolbar-save']/a";
			driver.findElement(By.xpath(locator)).click();
			driver.waitForBrowserToLoadCompletely();
			return checkSuccessMessage();
		}
	}

	/**
	 * This method will publish a article.
	 * 
	 * @param articleName
	 */
	public String publishArticle(String article) {
		goToArticle(article);
		CustomLogger.log("Publishing " + article);
		return selectValuesForProperty("Status", "Published");
	}

	/**
	 * This method will publish a module.
	 * 
	 * @param modulename
	 */
	public String publishModule(String modulename) {
		gotoModule(modulename);
		return selectValuesForProperty("Status", "Published");
	}

	/**
	 * This method will publish a module.
	 * 
	 * @param modulename
	 */
	public String publishModule(String modulename, Map<String, String> filter) {
		gotoModule(modulename, filter);
		return selectValuesForProperty("Status", "Published");
	}

	/**
	 * This method will un-publish a module.
	 * 
	 * @param modulename
	 */
	public String unpublishArticle(String articlename) {
		goToArticle(articlename);
		CustomLogger.log("Un-Publishing " + articlename);
		return selectValuesForProperty("Status", "Unpublished");
	}

	/**
	 * This method will un-publish a module.
	 * 
	 * @param modulename
	 */
	public String unpublishModule(String modulename, Map<String, String> filter) {
		gotoModule(modulename, filter);
		return selectValuesForProperty("Status", "Unpublished");
	}

	private HtmlElement getcckForm(String cckName) {
		String xpath = String.format(
				"//div[contains(@class,'cck_label_group')] [label[text()='%s']]/following-sibling::div", cckName);
		return driver.findElement(By.xpath(xpath));
	}

	private List<HtmlElement> getcckGroups(String cckName) {
		HtmlElement cckForm = getcckForm(cckName);
		return cckForm.findElements(By.xpath("descendant::div[contains(@class,'cck_form_group_x')]/div"));
	}

	public String getSERPDesktopFirstSearchMessage() {
		return driver.findElement(By.id("serp_messaging_recognized_desktop_first_search")).getText();
	}

	public String getDesktopFirstSearchTokenValue() {
		return driver.findElement(By.id("pch_iwe_instantwin_tokens_group_0_pch_iwe_tokens_amount"))
				.getAttribute("value");
	}

	public String getSERPDesktopLaterSearchMessage() {
		return driver.findElement(By.id("serp_messaging_recognized_desktop_later_search")).getText();
	}

	public List<JoomlaProperty> getPropertiesFromGroup(String cckGroupName, List<String> properties) {
		List<HtmlElement> cckGroupElements = getcckGroups(cckGroupName);
		List<JoomlaProperty> propertiesFromGroup = new ArrayList<JoomlaProperty>();

		/*
		 * Read every group under this group name
		 */
		int groupNumber = 0;
		for (HtmlElement groupElement : cckGroupElements) {
			groupNumber++;
			List<HtmlElement> propertyRows = groupElement.findElements(By.xpath("div"));
			/*
			 * Load the required properties.
			 */
			for (HtmlElement propertyRow : propertyRows) {
				String propertyName = propertyRow.findElement(By.xpath("descendant::label")).getText();
				if (properties.contains(propertyName)) {
					String propertyValue = "";
					PropertyType propertyType = null;
					HtmlElement valueElement = propertyRow
							.findElement(By.xpath("descendant::*[contains(@class,'input')]"));
					if (valueElement.getTagName().equals("select")) {
						propertyValue = new Select(valueElement.getWrappedElement()).getFirstSelectedOption().getText();
						propertyType = PropertyType.SELECT;

					} else if (valueElement.getTagName().equals("input")) {
						propertyType = PropertyType.TEXT;
						propertyValue = valueElement.getAttribute("value");
					} else {
						continue;
					}
					JoomlaProperty jp = new JoomlaProperty(propertyType, propertyName, propertyValue, groupNumber);
					propertiesFromGroup.add(jp);
				}
			}
		}

		return propertiesFromGroup;
	}

	public String setPropertiesOfGroup(String cckGroupName, List<JoomlaProperty> properties) {
		List<HtmlElement> cckGroupElements = getcckGroups(cckGroupName);

		for (JoomlaProperty property : properties) {
			int groupNumberForProperty = property.getGroupIndex();
			HtmlElement groupElement = cckGroupElements.get(groupNumberForProperty - 1);

			String xpath = String.format("descendant::label[text()='%s']/../../descendant::*[contains(@class,'input')]",
					property.getKey());
			HtmlElement field = groupElement.findElement(By.xpath(xpath));

			if (property.getType().equals(PropertyType.TEXT)) {
				field.clear();
				field.sendKeys(property.getValue());
			} else if (property.getType().equals(PropertyType.SELECT)) {
				new Select(field.getWrappedElement()).selectByVisibleText(property.getValue());
			} else {
				continue;
			}

		}

		return saveCloseAndClearCache();
	}

	public List<String> getGenericCarouselImageCount() {
		List<String> imgSrcs = new ArrayList<String>();
		String xpath = "//a[contains(@href,'/images/carousel/')]";
		for (HtmlElement e : driver.findElements(By.xpath(xpath))) {
			String src = e.getAttribute("href");
			imgSrcs.add(src.substring(src.lastIndexOf("/") + 1));
		}
		return imgSrcs;
	}

	/*
	 * This method is to select sub elements of Extensions tab in joomla We need
	 * to pass the 'visible-text' of the element which we want to click under
	 * Extensions.
	 */
	public String selectExtensionsSubElementUsingVisibleText(String module) {
		String headerText = null;
		try {
			String loginStatus = logIn();
			if (!loginStatus.equalsIgnoreCase("success")) {
				return loginStatus;
			}

			// Extensions & element's xpath
			Common.sleepFor(2000);
			HtmlElement extension = driver
					.findElement(By.xpath("//*[@id='menu']/li[6]/a[contains(text(),'Extensions')]"));
			Common.sleepFor(2000);
			HtmlElement element = driver.findElement(
					By.xpath(String.format(".//*[@id='menu']/li[6]/ul/li/a[contains(text(),'%s')]", module)));

			// Actions action = new Actions((WebDriver) driver);
			extension.click();
			Common.sleepFor(4000);
			element.click();

			driver.waitForBrowserToLoadCompletely();
			HtmlElement header = driver.findElement(By.xpath(".//*[@id='toolbar-box']/div/div[2]/h2"));

			headerText = header.getText();

		} catch (NoSuchElementException nse) {
			nse.printStackTrace();
		}
		return headerText;
	}

	public boolean headerPresent() {
		HtmlElement header = driver.findElement(By.xpath(".//*[@id='toolbar-box']/div/div[2]/h2"));
		return header.getText().contains("Plug-in Manager: Plug-ins");
	}

	private HtmlElement plugInManagerFilter() {
		return driver.findElement(By.id("filter_search"));
	}

	private HtmlElement plugInManagerSeachBtn() {
		return driver.findElement(By.xpath("//*[@type='submit'][contains(text(),'Search')]"));
	}

	public HtmlElement plugInManagerClearBtn() {
		return driver.findElement(By.xpath("//*[@type='button'][contains(text(),'Clear')]"));
	}

	public int searchInPlugInManager(String toSearch) {
		try {
			plugInManagerFilter().clear();
			plugInManagerFilter().sendKeys(toSearch);
			plugInManagerSeachBtn().click();

			int resultCoun = driver.getCountOfElementsWithXPath("//*[@id='adminForm']/table/tbody/tr");

			return resultCoun;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public HtmlElement resultOfplugInMangerSeach() {
		return driver.findElement(By.xpath("//*[@id='adminForm']/table/tbody/tr/td[2]/a[@href]"));
	}

	private HtmlElement validSSOUrl() {
		return driver.findElement(By.id("jform_params_validUriPath"));
	}

	private HtmlElement inValidSSOUrl() {
		return driver.findElement(By.id("jform_params_invalidUriPath"));
	}

	public String validSSOcontent() {

		String content = validSSOUrl().getText();
		// List<String> myList = new
		// ArrayList<String>(Arrays.asList(content.split(",")));

		return content;
	}

	public String inValidSSOContent() {

		String content = inValidSSOUrl().getText();
		// List<String> myList = new
		// ArrayList<String>(Arrays.asList(content.split(",")));

		return content;
	}

	public String getCurrentURL() {
		String curentlyLoadedURL = driver.getCurrentUrl();
		return curentlyLoadedURL;
	}

	public void testValidSSO(String links) {
		String presentURL = getCurrentURL();
		String applicationURL = Common.getAppUrl(Environment.getAppName());
		// List<String> links = validSSOcontent();
		List<String> myList = new ArrayList<String>(Arrays.asList(links.split(",")));
		for (int i = 0; i < myList.size(); i++) {
			// System.out.println(presentURL+""+myList.get(i));
			try {
				if (!myList.get(i).equals("logout")) {

					driver.get(applicationURL + "/" + myList.get(i));
					// System.out.println(presentURL+""+myList.get(i));

					Assert.assertTrue(getCurrentURL().startsWith("https://accounts."));
					CustomLogger.log(myList.get(i) + " is working fine");

				} else {
					CustomLogger.log("As you are looking for logout, you can't get the expected URL ");
				}

			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}

		}
	}

	public void testInValidSSO(String links) {
		String presentURL = getCurrentURL();
		String applicationURL = Common.getAppUrl(Environment.getAppName());
		// List<String> links = validSSOcontent();
		List<String> myList = new ArrayList<String>(Arrays.asList(links.split(",")));
		for (int i = 0; i < myList.size(); i++) {
			// System.out.println(presentURL+""+myList.get(i));
			try {
				driver.get(applicationURL + "/" + myList.get(i));
				// System.out.println(presentURL+""+myList.get(i));
				homepage.waitFor(2);

				Assert.assertTrue(getCurrentURL().contains("search."));// startsWith("https://search"));
				CustomLogger.log(myList.get(i) + " is working fine");

			} catch (NoSuchElementException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> serpUnRecognisedMsgs(String article) {
		goToArticle(article);

		String headerOfPage = driver.findElement(By.xpath(".//*[@id='toolbar-box']/div/div[2]/h2")).getText();

		Assert.assertTrue(headerOfPage.contains("SERP Messaging"));

		// List<String> content = null;
		String desktopFirstID = "serp_messaging_unrecognized_desktop_first_search";
		String desktopLaterID = "serp_messaging_unrecognized_desktop_later_search";

		HtmlElement desktopFirst = driver.findElement(By.id(desktopFirstID));
		HtmlElement desktopLater = driver.findElement(By.id(desktopLaterID));

		String desktopFirstText = desktopFirst.getAttribute("value");
		String desktopLaterText = desktopLater.getAttribute("value");

		List<String> desktopUnRecogText = new ArrayList<String>();
		desktopUnRecogText.add(desktopFirstText);
		desktopUnRecogText.add(desktopLaterText);

		// System.out.println(desktopUnRecogText);

		return desktopUnRecogText;
	}

	public List<String> serpRecognizedMsgs(String article) {
		goToArticle(article);

		String headerOfPage = driver.findElement(By.xpath(".//*[@id='toolbar-box']/div/div[2]/h2")).getText();

		Assert.assertTrue(headerOfPage.contains("SERP Messaging"));

		// List<String> content = null;

		String desktopFirstID = "serp_messaging_recognized_desktop_first_search";
		String desktopLaterID = "serp_messaging_recognized_desktop_later_search";

		HtmlElement desktopFirst = driver.findElement(By.id(desktopFirstID));
		HtmlElement desktopLater = driver.findElement(By.id(desktopLaterID));

		String desktopFirstText = desktopFirst.getAttribute("value");
		String desktopLaterText = desktopLater.getAttribute("value");

		List<String> desktopUnRecogText = new ArrayList<String>();
		desktopUnRecogText.add(desktopFirstText);
		desktopUnRecogText.add(desktopLaterText);

		// System.out.println(desktopUnRecogText);

		return desktopUnRecogText;
	}

	public void enableFrontPageSearchBoxOpenSERPinNewTab() {
		// enable open in a new window radio btn
		if (!openInNewTabRadioBtnFrontPageSearchbox().isEnabled()) {
			openInNewTabRadioBtnFrontPageSearchbox().click();
			CustomLogger.log("Enabled..");
		} else {
			CustomLogger.log("Already Enabled..");
		}
	}

	public void defaultFrotPageSearchBoxOpenSERPinNewTab() {
		if (!driver.findElement(By.id("jform_params_new_window1")).isEnabled()) {
			driver.findElement(By.id("jform_params_new_window1")).click();
			CustomLogger.log("Enabled..");
		} else {
			CustomLogger.log("Already Enabled..");
		}
	}

	public void enableInterstitialConfig() {
		gotoPCHConfigurationTab("PCHFrontpage");
		HtmlElement header = driver.findElement(By.xpath(".//*[@class='pagetitle icon-48-config']"));
		if (header.getText().contains("COM_PCH_CONFIG")) {
			driver.findElement(By.id("pchfrontpage")).click();
			Common.sleepFor(2000);
			boolean enabled = driver.findElement(By.xpath(".//*[@id='jform_enable_interstitial']/input[@value='1']"))
					.isEnabled();
			if (!enabled) {
				driver.findElement(By.xpath(".//*[@id='jform_enable_interstitial']/input[@value='1']")).click();
				CustomLogger.log("Enabled Interstitial Config");
			} else {
				CustomLogger.log("Interstitial onfiguration is aleady enabled");
			}
		}
	}

	public void setSegment(String textToBeSelect) {
		HtmlElement segmentsIncluded = driver.findElement(By.id("pch_iwe_instantwin_device_group_0_segments_included"));
		segmentsIncluded.selectDropdownElementUsingVisibleText(textToBeSelect);
	}

	/*
	 * This method is to select segments for User
	 * 
	 * @params: User Mail, segment type - By Segment Name/Code, Segment name(s)
	 */
	public boolean setegmentMemberShip(String userMail, String segmentType, List<String> segmentName) {
		String segmentationURL = "http://centralservices.qa.pch.com/segmentationapi/admin/SegmentsMembership.aspx";
		driver.get(segmentationURL);

		if (segmentType.equalsIgnoreCase("name")) {

			CustomLogger.log("Segment membership - by segment Name " + segmentName + " for " + userMail);
			driver.findElement(By.id("txtSetByEmailAndNameEmail")).sendKeys(userMail);

			HtmlElement segmentToBeSelected = driver.findElement(By.id("lbxSetByEmailAndNameActiveSegments"));
			segmentToBeSelected.selectMultipleSegmnetsUsingVisibleText(segmentName);

			HtmlElement setSegmentMemberShipByNameBtn = driver
					.findElement(By.id("btnSetByEmailAndNameSegmentsMembership"));
			setSegmentMemberShipByNameBtn.click();

			Common.sleepFor(2000);

			HtmlElement successMSg = driver.findElement(By.id("lblSetByEmailAndNameSegmentsDesc"));
			return successMSg.isDisplayed();

		} else if (segmentType.equalsIgnoreCase("code")) {

			CustomLogger.log("Segment membership - by segment code -" + segmentName + "- for " + userMail);
			driver.findElement(By.id("txtSetByEmailAndCodeEmail")).sendKeys(userMail);

			HtmlElement segmentToBeSelected = driver.findElement(By.id("lbxSetByEmailAndCodeActiveSegments"));
			segmentToBeSelected.selectMultipleSegmnetsUsingVisibleText(segmentName);

			HtmlElement setSegmentCodeBtn = driver.findElement(By.id("btnSetByEmailAndCodeSegmentsMembership"));
			setSegmentCodeBtn.click();

			Common.sleepFor(2000);

			HtmlElement successMSG = driver.findElement(By.id("lblSetByEmailAndCodeSegmentsDesc"));
			return successMSG.isDisplayed();
		}
		return false;
	}

	public void changeSearchTermAndWeight(String oldSearchTerm, String newSearchTerm, String weight) {
		HtmlElement searchTerm = driver.findElement(By.xpath(String.format("//input[@value='%s']", oldSearchTerm)));
		searchTerm.clear();
		searchTerm.sendKeys(newSearchTerm);
		HtmlElement weightInput = driver.findElement(By.xpath(String.format(
				"//input[@value='%s']/../parent::div/following-sibling::div//input[contains(@id,'weight')]",
				oldSearchTerm)));
		weightInput.clear();
		weightInput.sendKeys(weight);
	}

	public String getContestKeys(String key) {
		return driver.findElement(By.xpath(String.format(
				"//div[contains(text(),'Desktop')]/following-sibling::div//label[text()='%s']/parent::div/following-sibling::div/input",
				key))).getAttribute("value");
	}

	public String getSelectedSegment(String segmentType) {
		HtmlElement segment = driver.findElement(By.xpath(String.format("//select[contains(@id,'%s')]", segmentType)));
		return segment.getFirstSelectedOption();

	}

	public void selectSegmentByText(String segmentType, String segmentName) {
		HtmlElement segment = driver.findElement(By.xpath(String.format("//select[contains(@id,'%s')]", segmentType)));
		segment.selectDropdownElementUsingVisibleText(segmentName);

	}

	/**
	 * This will update infospace settings
	 * 
	 * @param: labelName
	 * @param: value
	 */
	public void updateInfospaceSettings(String labelName, String value) {
		HtmlElement element = driver.findElement(
				By.xpath(String.format("//label[contains(text(),'%s')]/following-sibling::input", labelName)));
		element.clear();
		element.sendKeys(value);
	}

}
