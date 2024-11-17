package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class BonusGameWelcomeLightBox extends LightBox {

	public BonusGameWelcomeLightBox(BrowserDriver driver){
		this.driver=driver;
	}

	@Override
	protected HtmlElement locateLightBox() {
		int totalIframeCount=driver.getCountOfElementsWithXPath("//iframe");
		if(totalIframeCount==0){
			return null;
		}
		
		int expectedFrameIndex = 4;
		if(expectedFrameIndex>=totalIframeCount){
			expectedFrameIndex=0;
		}
		driver.switchTo().frame(expectedFrameIndex);
		String xPathOFConfirmingElement = "//div[@class='content']/h1[contains(text(),'Welcome')]";
		if(driver.getCountOfElementsWithXPath(xPathOFConfirmingElement)==0){		
			/*
			 * Expected frame index have changed.
			 * So try by switching to proximity frames 3,5;2,6;1,7;0,8
			 */
			driver.switchTo().defaultContent();
			
			int upperIndex=expectedFrameIndex+1;
			int lowerIndex=expectedFrameIndex-1;
			while(true){
				if(upperIndex<totalIframeCount){
					driver.switchTo().frame(upperIndex);
					if(driver.getCountOfElementsWithXPath(xPathOFConfirmingElement)>0){						
						break;
					}					
					upperIndex++;
					driver.switchTo().defaultContent();
				}
				
				if(lowerIndex>=0){
					driver.switchTo().frame(lowerIndex);
					if(driver.getCountOfElementsWithXPath(xPathOFConfirmingElement)>0){
						break;
					}					
					lowerIndex--;
					driver.switchTo().defaultContent();
				}
				
			}			
		}
		
		driver.findElement(By.xpath(xPathOFConfirmingElement)).waitForVisible();
		return driver.findElement(By.className("content"));
	}
			
	private HtmlElement playNowButton(){
		HtmlElement lightBox = locateLightBox();
		HtmlElement playNowButton = lightBox.findElement(By.xpath("div[@class='play_now_button']/a"));
		return playNowButton;
	}

	/**
	 * Currently this method don't wait for dismissing of lightbox
	 * completely, it just waits for dismissal of 'Play Now' Button.
	 */
	@Override
	public void dismissLightBox() {		
		HtmlElement playNowButton = playNowButton();
		Common.sleepFor(2000);
		playNowButton.click();
		CustomLogger.log("Waiting for Bonus Game welcomeLight box to disappear");		
		playNowButton.waitTillNotVisible(60);
		driver.switchTo().defaultContent();	
		CustomLogger.log("PlayNow button disappeared.");	
		//add code to wait till lightbox is disappeared
	}

}
