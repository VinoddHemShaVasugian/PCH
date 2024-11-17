package com.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EventHandler implements WebDriverEventListener {

	private JavascriptExecutor executor;
	// SuperPage superpage_instance = SuperPage.getInstance();

	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		// TODO Auto-generated method stub

	}

	public void afterNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	public void afterNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	public void afterNavigateTo(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	public void afterScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("arguments[0].scrollIntoView(true);", arg0);
		arg0.clear();
	}

	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		executor = (JavascriptExecutor) DriverManager.get_event_firing_webdriver();
		executor.executeScript("arguments[0].scrollIntoView(true);", arg0);
		WebDriverWait wait = new WebDriverWait(DriverManager.get_event_firing_webdriver(), 30);
		wait.until(ExpectedConditions.elementToBeClickable(arg0));
		Actions builder = new Actions(DriverManager.get_event_firing_webdriver());
		builder.moveToElement(arg0);
	}

	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		// TODO Auto-generated method stub
	}

	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		// TODO Auto-generated method stub
	}

	public void beforeNavigateBack(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	public void beforeNavigateForward(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	public void beforeScript(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	public void onException(Throwable arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	public void afterNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	public void beforeNavigateRefresh(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterAlertAccept(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterAlertDismiss(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeAlertAccept(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeAlertDismiss(WebDriver arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
		// TODO Auto-generated method stub

	}
}
