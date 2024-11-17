package com.pch.search.Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.By;

import com.pch.search.bean.TokenCounterBotResultBean;
import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;

public class TokenCounter extends Bot {

	List<String> tokenCountBeforeAfter=new ArrayList<String>();
	String tokenCount;
	String tokenAmountXPath="//span[contains(@class,'token-amount')]";
	public TokenCounter(BrowserDriver driver,CountDownLatch startSignal){
		//CustomLogger.log.log("Instantiated Bot "+this.botName());		
		this.driver=driver;
		this.startSignal=startSignal;
		botStatus=0;
	}

	private String getTokenCount(){
		return driver.findElement(By.xpath(tokenAmountXPath)).getText();
	}

	@Override
	public void run() {
		CustomLogger.log("Activated bot "+getbotName()+" Thread Id "+Thread.currentThread().getId());
		tokenCount=getTokenCount();
		tokenCountBeforeAfter.add(tokenCount);
		botStatus=1;		
		try{
			CustomLogger.log(getbotName()+ " Waiting at coundown latch");
			startSignal.await();
			waitForTargetElement();
		}catch(Exception e){
			CustomLogger.log("Exception in bot "+getbotName());
			e.printStackTrace();
		}
		CustomLogger.log("Bot "+getbotName()+" is shutting down. Thread Id  "+Thread.currentThread().getId());
		botStatus=2;
	}

	@Override
	public String getbotName() {
		return BOT_TYPE.TOKENS_COUNTER.name();
	}

	@Override
	public int getbotStatus() {	
		return botStatus;
	}

	@Override
	public Object getResult() {
		return new TokenCounterBotResultBean(tokenCountBeforeAfter);
	}


	@Override
	protected void waitForTargetElement() {
		/*
		 * Wait until Displayed token count is not different
		 * than original count. 
		 */
		while(true){
			String currentTokenCount = getTokenCount(); 
			if(currentTokenCount.equals(tokenCount)){
				CustomLogger.log(getbotName()+" Waiting for token count to refresh. Token Count is still "+currentTokenCount);
				Common.sleepFor(5000);
			}
			else{
				tokenCount=currentTokenCount;
				break;
			}
		}

		/*
		 * Check whether "Loading... is displayed, 
		 * if yes, wait for 10 more seconds to let 
		 * the actual token count to load.
		 */
		CustomLogger.log(getbotName() +" waiting for 10 seconds to let token Count Load");
		if(tokenCount.toLowerCase().startsWith("loading")){
			Common.sleepFor(10000);
			tokenCount=getTokenCount();
		}
		tokenCountBeforeAfter.add(tokenCount);
		CustomLogger.log(getbotName() +" "+ tokenCountBeforeAfter);
	}


}
