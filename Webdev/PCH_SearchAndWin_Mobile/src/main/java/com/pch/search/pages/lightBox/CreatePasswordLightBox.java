package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;

public class CreatePasswordLightBox extends LightBox {

	public CreatePasswordLightBox(BrowserDriver driver) {
		this.driver = driver;
	}

	@Override
	protected HtmlElement locateLightBox() {
		HtmlElement ssoOverlay = driver.findElement(By.xpath("//div[contains(@class, 'sso-lightbox')]"));
		ssoOverlay.waitForVisible();
		return ssoOverlay;
	}

	@Override
	public void dismissLightBox() {
		HtmlElement lightbox = locateLightBox();
		HtmlElement closeBtn = lightbox.findElement(By.xpath("descendant::button[@class='close-x']"));
		closeBtn.click();
		lightbox.waitTillNotPresent();
	}

	public void enterPasswordandConfirm(String password) {
		CustomLogger.log("Enter Password and Confirm password");
		if (Environment.getDevice().equalsIgnoreCase("Mobile")) {
			driver.findElement(By.name("password")).sendKeys(password);
			driver.findElement(By.name("confirm-password")).sendKeys(password);
			driver.findElement(By.className("create-now")).click();
		    // driver.findElement(By.className("continue-create-now")).click();
		} else {
			HtmlElement lightbox = locateLightBox();
			lightbox.findElement(By.xpath("descendant::input[@class='lbpassword required']")).sendKeys(password);
			lightbox.findElement(By.xpath("descendant::input[@class='lbconfirmPassword required']")).sendKeys(password);
			submit();
		}
		CustomLogger.log("Entered Password and Confirm password..clicked submit button");
	}

	public HtmlElement passwordErrorMessage() {
		return driver.findElement(By.xpath("//span[contains(text(),'Please enter a valid Password.')]"));
	}

	public HtmlElement confirmPasswordErrorMessage() {
		return driver.findElement(By.xpath("//span[contains(text(),'Please confirm your Password.')]"));
	}

	public HtmlElement createPasswordFormSubmitButton() {
		return driver.findElement(By.className("create-now"));
	}

	public void submit() {
		HtmlElement lightbox = locateLightBox();
		HtmlElement submitButton = lightbox.findElement(By.xpath("descendant::button[@class='submit']"));
		submitButton.click();
		// lightbox.waitTillNotPresent();

		driver.waitForBrowserToLoadCompletely();
	}

}
