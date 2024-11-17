package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.HtmlElement;

public class RegStepAwayLightBox extends LightBox {

	public RegStepAwayLightBox(BrowserDriver driver){
		this.driver=driver;
	}
	
	@Override
	protected HtmlElement locateLightBox() {
		HtmlElement ssoOverlay = driver.findElement
				(By.xpath("//div[contains(@class, 'sso-lightbox')]"));
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
	
	public void continueRegistration(){
		HtmlElement lightbox= locateLightBox();
		HtmlElement continueBtn=lightbox.findElement(By.xpath("descendant::button[@class='continue']"));		
		continueBtn.click();
		lightbox.waitTillNotPresent();
	}

	//Search side regStepAwayLightBox
	//When we crerate a mini user through central services.and search for an word, then we will get a light Box.
	
	private HtmlElement userMail(){
		return driver.findElement(By.xpath("html/body/div[8]/div/div/div[2]/p/span/strong"));
	}
	
	private HtmlElement lighBoxText(){
		return driver.findElement(By.xpath("html/body/div[8]/div/div/div[2]/p/b"));
	}
	
	private HtmlElement continueBtn(){
		return driver.findElement(By.xpath("html/body/div[8]/div/div/div[3]/button"));
	}
	
	public boolean isMailPresent(){
		
		return userMail().isDisplayed();
		
	}
	
	public boolean isTokensPresent(){
		
		return lighBoxText().isDisplayed();
		
	}
	
	public void clickontinuebtn(){
		continueBtn().click();
	}
	
}
