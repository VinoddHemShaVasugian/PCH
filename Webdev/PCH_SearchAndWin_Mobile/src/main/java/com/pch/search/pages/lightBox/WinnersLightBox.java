package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class WinnersLightBox extends LightBox{

	private String blockingOverLayXpath ="//div[contains(@class,'fancybox-overlay fancybox-overlay-fixed')]";
	private String winnerOverLayXpath ="//div[contains(@class,'fancybox-skin')]";
	private String lightBoXPath = "//div[contains(@class,'fancybox-outer')]";
	
	
	private boolean isBlockingOverLayPresent(){
		return (driver.getCountOfElementsWithXPath(blockingOverLayXpath)!=0);
	}
	
	public boolean isWinnerOverLayPresent(){
		return (driver.getCountOfElementsWithXPath(winnerOverLayXpath)!=0);
	}
	
	private HtmlElement blockingOverLay(){
		return driver.findElement(By.xpath(blockingOverLayXpath));
	}

	public WinnersLightBox(BrowserDriver driver){
		this.driver=driver;
	}
	@Override
	protected HtmlElement locateLightBox() {
		
		return driver.findElement(By.xpath(lightBoXPath));
	}

	/**
	 * Close winners message.		
	 */	
	public void dismissLightBox() {		
		
		if(isBlockingOverLayPresent()){
			CustomLogger.log("light box found ....closing it");
			HtmlElement blkngOverLay = blockingOverLay();
			HtmlElement lightBox = locateLightBox();		
			lightBox.findElement(By.xpath("//a[@title='Close']")).click();
			if(isBlockingOverLayPresent()){
				blkngOverLay.waitTillNotPresent();
			}			
		}else{
			CustomLogger.log("light box not found ");
		}
	}
		
	}


