package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;
import com.pch.search.utilities.User;

public class SignInLightBox extends LightBox {

	public SignInLightBox(BrowserDriver driver){
		this.driver=driver;
	}
	
	public String device = Environment.getDevice();
	
	@Override
	protected HtmlElement locateLightBox() {
		HtmlElement lghtBox = driver.findElement
				(By.id("pch-login-form"));
		lghtBox.waitForVisible();
		return lghtBox;
	}

	@Override
	public void dismissLightBox() {
		// TODO Auto-generated method stub

	}

	/*public void enterEmail(String email){

	}*/

	public String getPresentEmail(){
		HtmlElement element = locateLightBox();
		return element.findElement(By.id("EM")).getText();
	}

	public boolean isEmailReadOnly(){
		HtmlElement element = locateLightBox();
		//System.out.println(element.findElement(By.id("PW")).getAttribute("readonly")==null);
		if(element.findElement(By.id("EM")).getAttribute("readonly")==null){
			return false;
		}else{
			return true;
		}
	}
	
	public HtmlElement forgotPasswordLink(){
		HtmlElement element = locateLightBox();
		return element.findElement(By.xpath(".//*[@id='pch-form-cnt']/form/a"));
	}

	public void enterPassword(String password){
		if(device.equalsIgnoreCase("mobile")){
			driver.findElement(By.xpath(".//*[@class='password']")).sendKeys(password);
		}else{
			HtmlElement element = locateLightBox();
			element.findElement(By.id("PW")).sendKeys(password);
		}
	}

	public void enterEmail(String username){
		if(device.equalsIgnoreCase("mobile")){
			driver.findElement(By.xpath(".//*[@class='email']")).sendKeys(username);
		}else{
			HtmlElement element = locateLightBox();
			element.findElement(By.id("EM")).sendKeys(username);
		}
	}

	public void clickSignIn(){
		if(device.equalsIgnoreCase("mobile")){
			driver.findElement(By.xpath(".//*[@class='sign-in-btn']")).click();
			driver.waitForBrowserToLoadCompletely();
		}else{
			HtmlElement lghtBox=locateLightBox();
			lghtBox.findElement(By.id("login-sub-btn")).click();
			//lghtBox.waitTillNotPresent(20);
			driver.waitForBrowserToLoadCompletely();
		}
	}

	public void clickSignInBtn(){
		clickSignIn();
		driver.waitForBrowserToLoadCompletely();
	}
	public int signIn(User user){
		
		try{
			enterEmail(user.getEmail());
			enterPassword(user.getPassword());
			clickSignIn();
			Common.sleepFor(3500);
			
			//-----Vamsi Nov - 20 -2015
		
			 /*If we Login with Valid User, 
			then it was looking for unsuccessful login error message, 
			which will never come with valid user..
			so implemented a if stmnt, if title doesn't matches to Frontpage */
		
			//if(!driver.getTitle().contains("Frontpage")){		
			String titleOfPage = driver.getTitle();
			HtmlElement pleaseCorrect;
			
			if(!titleOfPage.contains("GuidedSearch")){
				CustomLogger.log(String.format("Title of the page is : %s",titleOfPage));
				if(device.equalsIgnoreCase("mobile")){
					pleaseCorrect = driver.findElement(By.xpath(".//*[@class='inner-shell email']"),14);
				}else{
					pleaseCorrect = driver.findElement(By.xpath("//div[@class='sso-outer-shell']/descendant::ul[@class='error']/li"),14);
				}
			
			String pleaseCorrectText = pleaseCorrect.getAttribute("textContent"); 
			CustomLogger.log(pleaseCorrectText);

			if(pleaseCorrectText.contains("The password you entered is invalid")){
				CustomLogger.log(String.format("Incorrect password %s for email %s", user.getPassword(),user.getEmail()));
				return 0;
			}
			else if(pleaseCorrectText.contains("the email you provided is not recognized")){
				CustomLogger.log(String.format("User with email %s is not registered.", user.getEmail()));
				return -1;
			}
			else if(pleaseCorrectText
					.contains("the email you provided is not recognized")) {
				CustomLogger.log(String.format(
						"User with email %s is not registered.",
						user.getEmail()));
				return -1;
			}
			CustomLogger.log("Unhandled error message is displayed while signing In, refer screenshot.");
			return -2;
			
			}
			
		}catch(TimeoutException toe){
			//throw new TimeoutException();
			CustomLogger.log(toe.getMessage().toString());
		}
		
		return 1;
	}
	
	//Vamsi Dec 08-2015
	//Created an extra method with parameters as user and password 
	
public int signIn(User user, String password){	
	try{
		enterEmail(user.getEmail());
		enterPassword(password);
		clickSignIn();
		Common.sleepFor(3500);
		
		//-----Vamsi Nov - 20 -2015
	
		 /*If we Login with Valid User, 
		then it was looking for unsuccessful login error message, 
		which will never come with valid user..
		so implemented a if stmnt, if title doesn't matches to Frontpage */
	
		//if(!driver.getTitle().contains("Frontpage")){		
		String titleOfPage = driver.getTitle();
		HtmlElement pleaseCorrect;
				
		if(!titleOfPage.contains("Frontpage")){
			CustomLogger.log(String.format("Title of the page is : %s",titleOfPage));
		if(device.equalsIgnoreCase("mobile")){
			pleaseCorrect = driver.findElement(By.xpath(".//*[@class='inner-shell email']"),14);
		}else{
			pleaseCorrect = driver.findElement(By.xpath("//div[@class='sso-outer-shell']/descendant::ul[@class='error']/li"),14);
		}
		
		String pleaseCorrectText = pleaseCorrect.getAttribute("textContent"); 
		CustomLogger.log(pleaseCorrectText);

		if(pleaseCorrectText.contains("The password you entered is invalid")){
			CustomLogger.log(String.format("Incorrect password %s for email %s", password,user.getEmail()));
			return 0;
		}
		else if(pleaseCorrectText.contains("the email you provided is not recognized")){
			CustomLogger.log(String.format("User with email %s is not registered.", user.getEmail()));
			return -1;
		}
		else if(pleaseCorrectText.contains("pleaseCorrectText.contains")){
			
		}
		
		CustomLogger.log("Unhandled error message is displayed while signing In, refer screenshot.");
		return -2;
		
		}
		
	}catch(TimeoutException toe){
		throw new TimeoutException();
	}
	
	return 1;
}

}
