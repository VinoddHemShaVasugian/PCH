package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.HtmlElement;

public class OptinLightBox extends LightBox {

	private String blockingOverLayXpath ="//div[contains(@class,'fancybox-overlay fancybox-overlay-fixed')]";
	private String lightBoXPath = "//div[contains(@class,'fancybox-outer')]";

	private boolean isBlockingOverLayPresent(){
		return (driver.getCountOfElementsWithXPath(blockingOverLayXpath)!=0);
	}

	private HtmlElement blockingOverLay(){
		return driver.findElement(By.xpath(blockingOverLayXpath));
	}

	public OptinLightBox(BrowserDriver driver){
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
	
	public void clickCheckBox(){		
		HtmlElement lightBox = locateLightBox();
		lightBox.findElement(By.className("stylized-checkbox")).click();
	}
	
	public void checkBothCheckBox(){		
		HtmlElement lightBox = locateLightBox();
		lightBox.findElement(By.id("stylish-optin-site")).click();
		lightBox.findElement(By.id("stylish-optin-pchcom")).click();
	}

	public void dismissLightBox(boolean waitForAppearFlag){
		if(!waitForAppearFlag){
			dismissLightBox();
		}
		else{			
			locateLightBox();
			dismissLightBox();
		}
	}
	
	//Vamsi k Dec-15-2015
	public void submitOptin(){		
		HtmlElement lightBox = locateLightBox();
		lightBox.findElement(By.id("optin-submit")).click();
	}
}
