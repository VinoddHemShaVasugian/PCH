package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class ExperienceLightBox extends LightBox {

	private String blockingOverLayXpath ="//div[contains(@class,'fancybox-overlay fancybox-overlay-fixed')]";
	private String lightBoXPath = "//div[contains(@class,'fancybox-outer')]";

	private boolean isBlockingOverLayPresent(){
		return (driver.getCountOfElementsWithXPath(blockingOverLayXpath)!=0);
	}

	private HtmlElement blockingOverLay(){
		return driver.findElement(By.xpath(blockingOverLayXpath));
	}

	public ExperienceLightBox(BrowserDriver driver){
		this.driver=driver;
	}

	@Override
	protected HtmlElement locateLightBox() {
		return blockingOverLay().findElement(By.xpath(lightBoXPath));
	}

	@Override
	public void dismissLightBox() {
		/**
		 * Close Welcome to FrontPage message.		
		 */	

		if(isBlockingOverLayPresent()){
			HtmlElement blkngOverLay = blockingOverLay();
			HtmlElement lightBox = locateLightBox();		
			lightBox.findElement(By.xpath("//a[@title='Close']")).click();
			if(isBlockingOverLayPresent()){
				blkngOverLay.waitTillNotPresent();
			}
		}
	}

	public boolean isSignInButtonPresent(){
		try{
			return locateLightBox().findElement(By.id("reg-prompt-button-sign-in")).isDisplayed();
		}catch(Exception e){
			CustomLogger.logException(e);
			return false;
		}
	}
	
	public boolean isCompleteRegistrationButtonPresent(){
		try{
			return locateLightBox().findElement(By.id("reg-prompt-button-complete-reg")).isDisplayed();
		}catch(Exception e){
			CustomLogger.logException(e);
			return false;
		}
	}

	public boolean isRegisterButtonPresent(){
		try{
			return locateLightBox().findElement(By.id("reg-prompt-button-register")).isDisplayed();
		}catch(Exception e){
			CustomLogger.logException(e);
			return false;
		}
	}
}
