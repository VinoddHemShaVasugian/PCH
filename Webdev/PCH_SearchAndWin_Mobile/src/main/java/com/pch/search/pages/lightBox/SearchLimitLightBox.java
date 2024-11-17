package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;
public class SearchLimitLightBox extends LightBox {

	private String blockingOverLayXpath ="//div[contains(@class,'fancybox-overlay fancybox-overlay-fixed')]";
	private String lightBoXPath = "//div[contains(@class,'fancybox-outer')]";

	private boolean isBlockingOverLayPresent(){
		return (driver.getCountOfElementsWithXPath(blockingOverLayXpath)!=0);
	}

	private HtmlElement blockingOverLay(){
		return driver.findElement(By.xpath(blockingOverLayXpath));
	}

	public SearchLimitLightBox(BrowserDriver driver){
		this.driver=driver;
	}

	@Override
	protected HtmlElement locateLightBox() {
		return blockingOverLay().findElement(By.xpath(lightBoXPath));
	}

	@Override
	public void dismissLightBox() {
		/**
		 * Close LightBox		
		 */		
		HtmlElement blkngOverLay = blockingOverLay();
		HtmlElement lightBox = locateLightBox();		
		lightBox.findElement(By.xpath("//a[@title='Close']")).click();
		CustomLogger.log("Light box closed.");
		if(isBlockingOverLayPresent()){
			blkngOverLay.waitTillNotPresent();
		}

	}

	public void clickContinueToMySearch(){
		HtmlElement lightBox = locateLightBox();
		HtmlElement blkngOverLay = blockingOverLay();
		lightBox.findElement(By.xpath("//a[@title='Close']")).click();
		CustomLogger.log("Light box closed.");
		if(isBlockingOverLayPresent()){
			blkngOverLay.waitTillNotPresent();
		}
	}


}
