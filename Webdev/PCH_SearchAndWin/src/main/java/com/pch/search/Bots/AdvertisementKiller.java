package com.pch.search.Bots;

import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;

public class AdvertisementKiller extends Bot {

	public AdvertisementKiller(BrowserDriver driver,CountDownLatch startSignal){
		//CustomLogger.log("Instantiated Bot "+this.botName());		
		this.driver=driver;
		this.startSignal=startSignal;
		botStatus=0;
	}
	
	@Override
	public String getbotName() {
		return BOT_TYPE.AD_KILLER.name();
	}

	@Override
	public int getbotStatus() {
		return botStatus;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void waitForTargetElement() {
		String advertisement_1_XPath = "//div[contains(@id,'tt-wrapper')]";
		String advertisement_2_Xpath = "//div[@id='teadsAd']";
		double startTime = System.currentTimeMillis();
		int advertisementTypeCount=0;
		while(advertisementTypeCount<2){
			if(driver.getCountOfElementsWithXPath(advertisement_1_XPath)!=0){
				CustomLogger.log(getbotName()+" - Advertisement Appeared!");
				driver.findElement(By.xpath(advertisement_1_XPath)).removeFromDOM();
				CustomLogger.log(getbotName()+" Advertisement closed.");
				advertisementTypeCount++;
			}else if(driver.getCountOfElementsWithXPath(advertisement_2_Xpath)!=0){
				CustomLogger.log(getbotName()+" - Advertisement (teads) Appeared!");
				driver.findElement(By.xpath(advertisement_2_Xpath)).removeFromDOM();
				CustomLogger.log(getbotName()+" Advertisement (teads) closed.");
				advertisementTypeCount++;
			}
			else{
				if(((System.currentTimeMillis()-startTime)/1000)>20){
					CustomLogger.log(getbotName()+ "timed out.");					
					return;
				}
				CustomLogger.log(getbotName()+" - waiting for any Advertisement to appear.");
				Common.sleepFor(1000);
			}
		}
	}
	
	

}
