package com.pch.search.pages.admin;

import org.openqa.selenium.By;

import com.pch.search.utilities.User;

public class AdminSearchBoxMessagesPage extends AdminBasePage {

	/*public AdminSearchBoxMessagesPage(final WebDriver driver, final Environment env) {
		super(driver, env);
	}*/
	
	public String custSearchMesgFor(String user, User address){
		
		String mesg;
		
		for (int i=0; i<=5; i++){
			if (driver.findElement(By.id("message_group_"+i+"_conditions")).getAttribute("value").contains(user)){
				mesg = driver.findElement(By.id("message_group_"+i+"_message")).getAttribute("value");
				if(user.contentEquals("unrecognized")){
					mesg = mesg.replace("__USER__", "Guest");
				}else if(user.contentEquals("recognized")){
					mesg = mesg.replace("__USER__", address.getFirstname());
				}
				return mesg;
			}
		}
		return null;
		
	}
	
	
}
