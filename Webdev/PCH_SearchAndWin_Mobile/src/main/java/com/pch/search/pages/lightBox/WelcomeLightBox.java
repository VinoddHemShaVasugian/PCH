package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.HtmlElement;

public class WelcomeLightBox extends LightBox {

	private String blockingOverLayXpath ="//div[contains(@class,'fancybox-overlay fancybox-overlay-fixed')]";
	private String lightBoXPath = "//div[contains(@class,'fancybox-outer')]";
	private String levelLightBoxXpath = "//div[contains(@class,'dismiss_lb')]";

	private boolean isBlockingOverLayPresent(){
		return (driver.getCountOfElementsWithXPath(blockingOverLayXpath)!=0);
	}
	
	public boolean isLevelLightBoxPresent(){
		return (driver.getCountOfElementsWithXPath(levelLightBoxXpath)!=0);
	}

	public HtmlElement levelLightBox(){
		return driver.findElement(By.xpath(levelLightBoxXpath));
	}

	public WelcomeLightBox(BrowserDriver driver){
		this.driver=driver;
	}

	@Override
	protected HtmlElement locateLightBox() {
		return driver.findElement(By.xpath(lightBoXPath));
	}
	
	private HtmlElement blockingOverLay(){
		return driver.findElement(By.xpath(blockingOverLayXpath));
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

	public void dismissLightBox(boolean waitForAppearFlag){
		if(!waitForAppearFlag){
			dismissLightBox();
		}
		else{			
			locateLightBox();
			dismissLightBox();
		}
	}
}


