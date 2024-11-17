package com.pch.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.pch.automation.utilities.AppConfigLoader;

import net.serenitybdd.core.pages.PageObject;

public class CouchbasePage extends PageObject {

	private final By email = By.id("auth-username-input");
	private final By password = By.id("auth-password-input");
	private final By loginBtn = By.xpath("//button[contains(.,'Sign In')]");
	private final By bucketMenu = By.cssSelector("a[mn-tab='buckets']");
	private final By documentsLink = By.linkText("Documents");
	private final By docSearch = By.cssSelector("input[ng-model='documentsListCtl.lookupId']");
	private final By docLookUp = By.xpath("//button[contains(.,'Look Up ID')]");
	private final By changeVisits = By.xpath("//pre[@class=' CodeMirror-line ']//span[contains(.,'visits')]/span[2]");
	private final By changeLastSearchDate = By
			.xpath("//pre[@class=' CodeMirror-line ']//span[contains(.,'lastSearchDate')]/span[2]");
	private final By saveDoc = By.cssSelector("button[ng-click='documentsEditingCtl.save()']");
	private final By saveDocBtnOnDisable = By
			.cssSelector("button[ng-click='documentsEditingCtl.save()'][disabled='disabled']");
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();

	public void login() {
		getDriver().get(configInstance.getEnvironmentProperty("CBUrl"));
		typeInto(element(email), configInstance.getEnvironmentProperty("CBUsername"));
		typeInto(element(password), configInstance.getEnvironmentProperty("CBPassword"));
		clickOn(element(loginBtn).waitUntilVisible());
	}

	public void modifyLastVisits(String visitDayCount) {
		evaluateJavascript("arguments[0].innerText='" + visitDayCount + "'", element(changeVisits));
	}

	public void modifyLastSearchDate(String lastSearchDate) {
		evaluateJavascript("arguments[0].innerText='\"" + lastSearchDate + "\"'", element(changeLastSearchDate));
		waitFor(10);
		Actions action = new Actions(getDriver());
		action.moveToElement(element(changeLastSearchDate)).clickAndHold().sendKeys(Keys.END).sendKeys(Keys.TAB).build()
				.perform();
		evaluateJavascript("arguments[0].innerText='\"" + lastSearchDate + "\"'", element(changeLastSearchDate));
	}

	public void saveDocument() {
		evaluateJavascript("arguments[0].click();", element(saveDoc));
		waitForAngularRequestsToFinish();
		waitForRenderedElements(saveDocBtnOnDisable);
	}

	public void searchDocument(String userId) {
		clickOn(element(bucketMenu).waitUntilClickable());
		clickOn(element(documentsLink).waitUntilClickable());
		typeInto(element(docSearch), "u:ccvisit:" + userId);
		clickOn(element(docLookUp).waitUntilClickable());
		waitForRenderedElements(saveDoc);
	}
}