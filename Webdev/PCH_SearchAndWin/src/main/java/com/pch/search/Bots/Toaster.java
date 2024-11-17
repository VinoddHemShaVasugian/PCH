package com.pch.search.Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.By;

import com.pch.search.bean.ToasterBotResultBean;
import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class Toaster extends Bot {

	private String toastXpath = "//div[contains(@class,'bottom-area-levels-complete-popup-box ')]/div[contains(@class,'show')]";
	private List<String> toastMessages = new ArrayList<String>();
	
	public Toaster(BrowserDriver driver,CountDownLatch startSignal){
		//CustomLogger.log("Instantiated Bot "+this.botName());		
		this.driver=driver;
		this.startSignal=startSignal;
		botStatus=0;
	}

	@Override
	public String getbotName() {		
		return BOT_TYPE.TOASTER.name();
	}
	@Override
	public int getbotStatus() {
		return botStatus;
	}

	@Override
	public Object getResult() {
		return new ToasterBotResultBean(toastMessages);
	}

	@Override
	protected void waitForTargetElement() {
		double startTime = System.currentTimeMillis();
		int expectedToastMessageCount=2;
		int appearedToastMessageCount=0;
		while(true){
			if (driver.getCountOfElementsWithXPath(toastXpath)!=0){
				String toastMessage="";
				CustomLogger.log(getbotName()+" - toast message appeared!");
				HtmlElement toastMessageElement = driver.findElement(By.xpath(toastXpath));
				//toastMessageElement.waitForVisible();
				//toastMessage = toastMessageElement.getText();
				toastMessage = toastMessageElement.getAttribute("textContent");
				//replace multiple contiguous spaces with a single space.
				toastMessage=toastMessage.replaceAll("\\s+", " ");
				//Remove the digit separator ',' in tokens, e.g will make 1,000 as 1000.
				toastMessage=toastMessage.replaceAll("(?<=\\d),(?=\\d)", "").trim();
				CustomLogger.log(getbotName() +" "+ toastMessage);
				toastMessages.add(toastMessage);
				toastMessageElement.waitTillNotVisible();
				appearedToastMessageCount++;
				/*
				 * Wait for second toast message to appear
				 */
				if(appearedToastMessageCount<expectedToastMessageCount){
					startTime = System.currentTimeMillis();
					MAX_TRIGGER_WAIT=3;
					continue;
				}else
					break;
			}
			else{
				if(((System.currentTimeMillis()-startTime)/1000)>MAX_TRIGGER_WAIT){
					CustomLogger.log(getbotName()+ " timed out after waiting."+MAX_TRIGGER_WAIT+"seconds");					
					return;
				}
				CustomLogger.log(getbotName()+" - waiting for toast message to appear.");
				Common.sleepFor(1000);
			}
		}		
	}



}
