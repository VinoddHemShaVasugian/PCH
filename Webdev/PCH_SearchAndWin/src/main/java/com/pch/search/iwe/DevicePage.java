package com.pch.search.iwe;

import org.openqa.selenium.By;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class DevicePage extends IWEBasePage {

	By giveAwayGroupName = By.xpath("//span[contains(text(),'Gwy. Group:')]");
	By giveawayName = By
			.xpath("//div[@id='deviceoverview-body']//td[@role='gridcell']/div/font[contains(text(),'SnW')]");
	By giveaway_token_catch_all = By.xpath("//span[contains(text(),'Gwy. Token Catchall:')]");
	By prize_token_catch_all = By
			.xpath("//div[@id='deviceoverview-body']//td[@role='gridcell']/div/font[contains(text(),'SnW')]");
	By closeBtn = By.xpath(".//div[contains(@id,'toolbar')]/div//div[1]//a//span[contains(text(),'Close')]/following-sibling::span[last()]");
	By catch_all_tree_button = By.id("catchallButton-btnIconEl");
	By prize_token_catch_all_close_icon = By
			.xpath("//span[starts-with(text(),'Edit Prize Token Catchall:')]/../following-sibling::div/img");

	private String getPrizeValue() {
		return driver.findElement(By.name("prizeValue")).getAttribute("value");
	}

	private HtmlElement deviceNameDownArrow() {
		return driver.findElement(By.xpath(".//*[@id='gridcolumn-1163-triggerEl']"));
	}

	private HtmlElement filter() {
		return driver.findElement(By.id("menucheckitem-1246-textEl"));
	}

	public HtmlElement getStartDate() {
		return driver.findElement(By.name("startDate"));
	}

	public HtmlElement getEndDate() {
		return driver.findElement(By.name("endDate"));
	}

	public HtmlElement setComment() {
		return driver.findElement(By.name("comment"));
	}

	public HtmlElement saveBtn() {
		return driver.findElement(By.xpath("//div[contains(@id,'toolbar')]/div//div[1]//a[2]//span[contains(text(),'Save')]/following-sibling::span[last()]"));
	}

	public void clickArrow() {
		deviceNameDownArrow().click();
		filter().click();
		driver.findElement(By.id("textfield-1229-inputEl")).sendKeys("Search");
	}

	/**
	 * To search devices
	 */
	public boolean searchDevices(String deviceName) {
		boolean isFound = false;
		try {
			search(deviceName);
			driver.findElement(By.xpath(String.format("//font[text()='%s']", deviceName)));
			isFound = true;
		} catch (Exception e) {
			CustomLogger.log(e.toString());
		}
		return isFound;

	}

	public void openDeviceOverview(String deviceName) {
		driver.findElement(By.xpath(String.format(
				"//font[text()='%s']/../parent::td/following-sibling::td//font[text()='Overview']", deviceName)))
				.click();
	}

	public void openDeviceOverviewById(String deviceID) {
		driver.get(deviceOverviewPage + deviceID);
	}

	public void makeGiveawayActive(String deviceID) {
		openDeviceOverviewById(deviceID);
		Common.sleepFor(2000);
		driver.findElement(giveAwayGroupName).click();
		driver.findElement(giveawayName).click();
		Common.sleepFor(2000);
		getStartDate().clear();
		getStartDate().sendKeys(getPreviousMonthDate());
		getEndDate().clear();
		getEndDate().sendKeys(getNextMonthDate());
		setComment().sendKeys("update date");
		saveBtn().click();
		closeServerWarning("Yes");
		Common.sleepFor(2000);
		driver.findElement(closeBtn).click();
	}

	public void makeGiveawayInactive(String deviceID) {
		openDeviceOverviewById(deviceID);
		Common.sleepFor(2000);
		driver.findElement(giveAwayGroupName).click();
		driver.findElement(giveawayName).click();
		Common.sleepFor(2000);
		getStartDate().click();
		getStartDate().clear();
		getStartDate().sendKeys(getPreviousMonthDate());
		getEndDate().click();
		getEndDate().clear();
		getEndDate().sendKeys(getPreviousMonthDate());
		setComment().sendKeys("update date");
		saveBtn().click();
		closeServerWarning("Yes");
		Common.sleepFor(2000);
		driver.findElement(closeBtn).click();
	}

	public String getTokenCatchAllPrizeValue(String deviceID) {
		openDeviceOverviewById(deviceID);
		Common.sleepFor(2000);
		driver.findElement(catch_all_tree_button).click();
		Common.sleepFor(2000);
		driver.findElement(giveaway_token_catch_all).click();
		Common.sleepFor(1000);
		driver.findElement(prize_token_catch_all).click();
		Common.sleepFor(2000);
		String prize_value = getPrizeValue();
		driver.findElement(prize_token_catch_all_close_icon).click();
		driver.findElement(closeBtn).click();
		return prize_value;
	}

	public void clickTokenCatchAllWinsTab() {
		driver.findElement(By.xpath("//span[contains(text(),'Token CatchAll Wins')]")).click();
	}

	public void nextPagePaginationByArrow() {
		driver.findElement(By.xpath("//div[starts-with(@id,'usertokencatchallwins')]//a[@data-qtip='Next Page']"))
				.click();
	}

	public void getBy(String element_keyword){
		By.xpath("//div[text()='"+element_keyword+"']");
	}
}