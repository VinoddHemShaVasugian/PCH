package com.pch.search.Bots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.openqa.selenium.By;

import com.pch.search.bean.LastActivityBotResultBean;
import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class LastActivity extends Bot {

	private List<String> recentActivities = new ArrayList<String>();
	
	public LastActivity(BrowserDriver driver,CountDownLatch startSignal){
		//CustomLogger.log("Instantiated Bot "+this.botName());		
		this.driver=driver;
		this.startSignal=startSignal;
		botStatus=0;
	}

	@Override
	public String getbotName() {
		return BOT_TYPE.LAST_ACTIVITY.name();
	}

	@Override
	public int getbotStatus() {	
		return botStatus;
	}

	@Override
	public Object getResult() {
		return new LastActivityBotResultBean(recentActivities);
	}

	@Override
	public void waitForTargetElement() {
		String lastActivityPopupXpath = "//div[@id='token_center'][contains(@class,'last-activity expanded')]";
		boolean atLeastOneActivityLogged=false;
		double startTime = System.currentTimeMillis();
		while(true){
			if (driver.getCountOfElementsWithXPath(lastActivityPopupXpath)!=0){
				String recentActivity;				
				HtmlElement lastActivityElement = driver.findElement(By.className("message-display"));
				recentActivity = lastActivityElement.getAttribute("textContent");
				recentActivity=recentActivity.replaceAll("(?<=\\d),(?=\\d)", "").trim();
				
				if(!recentActivities.contains(recentActivity)){
					CustomLogger.log(getbotName()+" - last activity popup appeared!");
					CustomLogger.log(getbotName()+" "+recentActivity);
					recentActivities.add(recentActivity);
				}
				atLeastOneActivityLogged=true;
				// Don't break as the message of recent activity can still change.
				//break;
			}
			else{
				if(((System.currentTimeMillis()-startTime)/1000)>MAX_TRIGGER_WAIT){
					CustomLogger.log(getbotName()+ "timed out.");
					//recentActivity="TIMED_OUT";
					return;
				}
				if(atLeastOneActivityLogged){
					CustomLogger.log(getbotName()+" - last activity popup not visible anymore.");
					return;
				}
				CustomLogger.log(getbotName()+" - waiting for last activity popup to appear.");
				Common.sleepFor(1000);
			}
		}		
	}




}
