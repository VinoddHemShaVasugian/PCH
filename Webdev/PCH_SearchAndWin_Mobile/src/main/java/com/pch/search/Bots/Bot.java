package com.pch.search.Bots;

import java.util.concurrent.CountDownLatch;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.CustomLogger;

 

public abstract class Bot extends Thread {
	
	/**
	 * -1 botstatus is dead, not instantiated.
	 * 0  bot status is instantiated.
	 * 1  botstatus is activated and started to work.
	 * 2 ready to provide output
	 */
	protected BrowserDriver driver;
	protected int MAX_TRIGGER_WAIT=30;
	protected int botStatus;
	public abstract String getbotName();
	public abstract int getbotStatus();
	public abstract Object getResult();	
	protected abstract void waitForTargetElement();
	protected CountDownLatch startSignal;
	
	public void run(){
		CustomLogger.log("Activated bot "+getbotName()+" Thread Id  "+Thread.currentThread().getId());
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
}
