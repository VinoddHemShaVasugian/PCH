package com.pch.search.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;

public class AboutPage extends Action {



	public boolean isVideoExists(String videoName){
		CustomLogger.log("validating video exists or not");
		boolean isExists=false;
		try{
			driver.findElement(By.id(videoName));
			isExists=true;
		}catch(NoSuchElementException e){
			//do nothing
		}
		return isExists;
	}

	public void waitTillGetTheTokens(){
		int i=0;
		String initialAmount=driver.findElement(By.className("token-amount")).getText();
		CustomLogger.log("Waiting till we get tokens for watching videos");
		while(true){			
			Common.sleepFor(10000);
			String currentAmount=driver.findElement(By.className("token-amount")).getText();
			if(!(initialAmount.equals(currentAmount)) || i==70000){
				break;
			}
			CustomLogger.log("Does not get tokens yet...waiting for few more seconds");
			i=i+10000;
		}

	}
}
