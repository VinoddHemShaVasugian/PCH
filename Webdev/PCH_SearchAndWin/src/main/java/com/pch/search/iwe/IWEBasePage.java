package com.pch.search.iwe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;

import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;

public class IWEBasePage extends Action {

	HomePage homepage = new HomePage();
	public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy");
	String appURL = Environment.getIWEAppURL();
	String giveawaysPage = appURL + "#giveaway/list";
	String devicesPage = appURL + "#device/list";
	String prizesPage = appURL + "#prize/currentprizelist";
	String giveawayGroupsPage = appURL + "#giveawaygroup/list";
	String allPlayListPage = appURL + "#user/allplaylist";
	String deviceOverviewPage = appURL + "#device/overview/";
	String winnerListPage = appURL + "#winner/list";
	String user_info_modal = appURL + "#user/showDetails/";
	By search_email = By.name("email");

	public boolean getWinnerEmailDetails(String email) {
		try {
			return driver.findElement(By.xpath("//font[normalize-space()='" + email.toLowerCase() + "']"))
					.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	private HtmlElement logOutLink() {
		return driver.findElement(By.xpath("//a[contains(@id, 'button')]//span[contains(text() ,'Logout')]/following-sibling::span[last()]"));
	}

	private HtmlElement userId() {
		return driver.findElement(By.id("j_username"));
	}

	private HtmlElement password() {
		return driver.findElement(By.id("j_password"));
	}

	private HtmlElement loginBtn() {
		return driver.findElement(By.id("submit"));
	}

	public HtmlElement devices_Btn() {
		return driver.findElement(By.xpath("//span[text()='Devices']"));
	}

	public HtmlElement devicesList() {
		return driver.findElement(By.xpath("//div[contains(@id,'devicelist')]"));
	}

	private HtmlElement column_Name() {
		return driver.findElement(By.xpath("//span[text()='Name']"));
	}

	private HtmlElement downArrow() {
		return driver.findElement(By.xpath("//span[text()='Name']/following-sibling::div"));
	}

	private HtmlElement filter() {
		return driver.findElement(By.xpath("//span[text()='Filters']"));
	}

	public String logIn() {
		String userName = Environment.getIWEUsername();
		String password = Environment.getIWEPassword();

		driver.get("https://adminiwe." + Environment.getEnvironment() + ".pch.com/iwe/");

		/*
		 * Logout if already logged in
		 */
		boolean isUserAlreadyLoggedIn = driver.getCountOfElementsWithXPath("//span[text()='Logout']") > 0;
		if (isUserAlreadyLoggedIn) {
			logOutLink().click();
			driver.waitForBrowserToLoadCompletely();
		}
		userId().sendKeys(userName);
		password().sendKeys(password);
		loginBtn().click();
		driver.waitForBrowserToLoadCompletely();
		driver.waitForElementToBeVisible(search_email, 20);

		if (logOutLink().isDisplayed()) {
			CustomLogger.log("User logged-in to iwe successfully.");
			return "success";
		}

		else {
			String result = String.format("Unable to login to iwe with username : %s and password :%s", userName,
					password);
			CustomLogger.log(result);
			return result;
		}

	}

	public String getNextMonthDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 1);
		String date = simpleDateFormat.format(cal.getTime());
		date = date + " 11:59:59 PM";
		return date;
	}

	public String getPreviousMonthDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String date = simpleDateFormat.format(cal.getTime());
		date = date + " 11:59:59 PM";
		return date;
	}

	/**
	 * To search Giveaways/Devies.....etc
	 */
	public void search(String searchText) {
		try {
			column_Name().mouseOver();
			Common.sleepFor(2000);
			downArrow().click();
			filter().click();
			driver.findElement(By.xpath("//input[contains(@name,'textfield')]")).click();
			driver.findElement(By.xpath("//input[contains(@name,'textfield')]")).sendKeys(searchText);
			Common.sleepFor(5000);

		} catch (Exception e) {
			CustomLogger.log(e.toString());
		}

	}

	public void selectItemFromDropdown(String dropdownItemName, int ind) {
		List<HtmlElement> allDropdownList = driver
				.findElements(By.xpath("//*[contains(text(), '" + dropdownItemName + "')]"));
		if (allDropdownList != null && allDropdownList.size() > 0) {
			allDropdownList.get(ind).click();
			return;
		}
	}

	public void selectExactItemFromDropdown(String dropdownItemName) {
		List<HtmlElement> allDropdownList = driver.findElements(By.xpath("//li[(text()='" + dropdownItemName + "')]"));
		for (HtmlElement dropdown : allDropdownList) {
			if (dropdown.getText().equalsIgnoreCase(dropdownItemName) && dropdown.getTagName().equalsIgnoreCase("li")) {
				dropdown.click();
				return;
			}
		}
	}

	public void closeServerWarning(String action) {
		try {
			if (driver.getCountOfElementsWithXPath("//div[contains(@id,'messagebox')]") > 0) {
				CustomLogger.log("Alert message exists....closing alert message by clicking.." + action);
				driver.findElement(By.xpath(".//div[contains(@id, 'messagebox')]/div//div[1]//a[2]//span[contains(text(),'Yes')]/following-sibling::span[last()]")).click();
			}
		} catch (Exception e) {
			CustomLogger.log("No alertbox present");
		}
	}

	public void navigateToDevicesPage() {
		driver.get(devicesPage);
		Common.sleepFor(3000);
	}

	public void navigateToGiveawaysPage() {
		driver.get(giveawaysPage);
		Common.sleepFor(3000);
	}

	public void navigateToPrizesPage() {
		driver.get(prizesPage);
		Common.sleepFor(3000);
	}

	public void navigateToGiveawayGroupsPage() {
		driver.get(giveawayGroupsPage);
		Common.sleepFor(3000);
	}

	public void navigateToAllPlayListPage() {
		driver.get(allPlayListPage);
		Common.sleepFor(3000);
	}

	public void navigateToWinnerListPage() {
		driver.get(winnerListPage);
		Common.sleepFor(3000);
	}

	public void navigateToUserInfoModal(String email) {
		driver.get(user_info_modal + email + "/" + DBUtils.getUserGMTOATFromEmail(email, "GMT"));
		Common.sleepFor(3000);
	}

}
