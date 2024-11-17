package com.pch.search.pages.lightBox;

import java.util.Date;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class ResetPasswordLightBox extends LightBox {

	public ResetPasswordLightBox(BrowserDriver driver){
		this.driver=driver;
	}

	@Override
	protected HtmlElement locateLightBox() {
		//HtmlElement ssoOverlay = driver.findElement(By.xpath("//div[contains(@class, 'sso-lightbox')]"));

		HtmlElement ssoOverlay = driver.findElement(By.id("pch-form-cnt"));
		ssoOverlay.waitForVisible();
		return ssoOverlay;		
	}

	@Override
	public void dismissLightBox() {
		HtmlElement lightbox= locateLightBox();
		HtmlElement closeBtn=lightbox.findElement(By.xpath("descendant::button[@class='close-x']"));		
		closeBtn.click();
		lightbox.waitTillNotPresent();
	}

	public void enterEmail(String email){
		HtmlElement lightbox= locateLightBox();
		HtmlElement emailBox=lightbox.findElement(By.xpath("descendant::input[@id='EM']"));
		emailBox.clear();
		emailBox.sendKeys(email);
	}

	public Date clickEnter(){
		HtmlElement lightbox= locateLightBox();
		HtmlElement enterButton=lightbox.findElement(By.linkText("Enter"));
		Date d = new Date();
		enterButton.click();
		driver.waitForBrowserToLoadCompletely();
		

		String xpath = "//div[@class='sso-outer-shell']/descendant::ul[@class='error']/li";
		if(driver.getCountOfElementsWithXPath(xpath)>0){
			//Log the error message.
			CustomLogger.log(driver.findElement(By.xpath(xpath)).getText());
			return null;
		}			
		return d;


	}




}
