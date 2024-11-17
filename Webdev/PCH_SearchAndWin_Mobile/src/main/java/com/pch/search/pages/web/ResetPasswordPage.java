package com.pch.search.pages.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Folder;
import javax.mail.Store;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;

public class ResetPasswordPage extends Action {
	
	HomePage homePage = new HomePage();
	Store store=null;
	@SuppressWarnings("unused")
	private Folder inbox;
	
	private HtmlElement getResetPasswordButton(){
		return driver.findElement(By.id("reset-password-btn"));
	}
	
	/*private HtmlElement getSuccessElement(){
		return driver.findElement(By.xpath("//div[@class='fp_thanks_holder']/span[contains(text(),'Your password has been reset')]"),30);
	}*/
	
	public void clickContinueButton(){
		HtmlElement continueButton = driver.findElement(By.id("continue-btn"));
		continueButton.click();
		//continueButton.waitTillNotPresent(60);
		driver.waitForBrowserToLoadCompletely();
	}
	
	/*private HtmlElement getPasswordLinkUsedElement(){
		return driver.findElement(By.xpath("//div[@class='content']/div/p"));
	}*/

	public String resetPasswordUsingLink(String link,String password){
		driver.get(link);
		driver.waitForBrowserToLoadCompletely();
		if(driver.getCountOfElementsWithXPath("//div[@class='headline']/p[text()=\"We're Sorry!\"]")>0){			
			return "Password link already used/expired";
		}else if(driver.getCurrentUrl().endsWith("resetpasswordused")){
			return "Password link already used";
		}else if(driver.getCountOfElementsWithXPath("//div[@class='content']/h1[text()=\"We're Sorry.\"]")>0){
			return "Password link already used";
		}
		driver.findElement(By.id("PW")).sendKeys(password);
		driver.findElement(By.id("CPW")).sendKeys(password);
		getResetPasswordButton().click();
		CustomLogger.log("Clicked Reset Password button");
		//getResetPasswordButton().waitTillNotPresent(60);
		driver.waitForBrowserToLoadCompletely();
		
		//HtmlElement successElement = getSuccessElement();
		
		int count = driver.getCountOfElementsWithXPath("//div[@id='message_area']/p[contains(text(),'Your password has been reset')]");
		if(count>0){
			//getContinueButton().click();
			//driver.waitForBrowserToLoadCompletely();	
			return "success";
		}else{
			return "Success not displayed";
		}		
		//driver.findElement(By.)
	}
	
	private HtmlElement mailID(){
		return driver.findElement(By.id("txtUsername"));
	}
	
	private HtmlElement password(){
		return driver.findElement(By.id("txtPassword"));
	}
	
	private HtmlElement loginBtn(){
		return driver.findElement(By.id("lbLogin"));
	}
	
	private boolean mailDisplayed(String mailID){
		return driver.findElement(By.xpath(".//*[@id='HeaderContent_lblHeaderContent'][contains(text(),'"+mailID+"')]")).isDisplayed();
	}
	
	private HtmlElement inbox(){
		return driver.findElement(By.id("MainContent_hypInbox"));/*driver.findElement(By.xpath(".//span[@class='rtIn'][contains(text(), 'Inbox')]"));*/
	}
	
	private HtmlElement refreshMail(){
		return driver.findElement(By.xpath(".//*[@id='ucMailbox_MailboxMenu']/ul/li/a/span[contains(text(), 'Refresh Mail')]"));
	}
	
	private int getunreadMails(){
		return driver.getCountOfElementsWithXPath(".//*[@id='item-container']/div/div/img");
	}
	
	private boolean stausOfFirstMail(){
		return driver.getCountOfElementsWithXPath(".//*[@id='item-container']/div[1]/div/img")>0;
	}
	
	public HtmlElement helpIsOnTheWay(){
		return driver.findElement(By.xpath(".//*[@id='pwd_message_area']/form/p"));
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean loginToImail(String email){
		String password="password3";
		String hostname = "http://imail.ny.pch.com/IClient/Login.aspx?ReturnUrl=%2ficlient";
		
		driver.get(hostname);
		//Enter valid user name and password and login to I-Mail 
		CustomLogger.log("Enter Username");
		mailID().sendKeys(email);
		CustomLogger.log("Enter Password");
		password().sendKeys(password);
		loginBtn().click();
		driver.waitForBrowserToLoadCompletely();
		Assert.assertTrue(mailDisplayed(email), "We are not seeing the expected mailID, Hope yot are not in home page of the mail.");
		//validating title of the page
		CustomLogger.log("Title of page : "+driver.getTitle());
		
		return driver.getTitle().equalsIgnoreCase("IMail Web Client");
	}
	
	public boolean openMailAndValidateCreatePassword(Date d){
				
		String mailIDforForgetPwsd = "np85@pchmail.com";
		String emailId ="nptest@pchmail.com";
//		String expectedSender ="Publishers Cle…";
//		String expectedSubject="Forgot Your Pas…";
		
		//Converting system date format to I-mail date format 
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy h:mm a ");
		String dateString = formatter.format(d);
		
		String trimDate = dateString.substring(0, 11);
		CustomLogger.log("Trim Date : "+trimDate);
		
		CustomLogger.log("Date : "+dateString+" Trim Date : "+trimDate);
		
		//Login to I-Mail
		CustomLogger.log("Login to I-Mail");
		Assert.assertTrue(loginToImail(emailId));
		
		//Click on inbox and select the valid mail by validating Sender, Subject and Date
		inbox().click();
		homePage.waitFor(2);
		//driver.switchtoFrame("iframe_632e17a2-9504-4b5b-8f9b-77a9cd4e23c1");//_iframe("iframe_632e17a2-9504-4b5b-8f9b-77a9cd4e23c1");
		//get count of unread messages
		int unreadMsgCount = getunreadMails();
		
		//Validating status of first mail
		CustomLogger.log("Validating status of first mail");
		int elementCounter = 0;
		while(!stausOfFirstMail()){
			refreshMail().click();
			homePage.waitFor(3);
			elementCounter++;
			if(elementCounter == 5){
				Assert.assertTrue(stausOfFirstMail(), "Unable to produce a mail tried refreshg mail "+elementCounter+" times.");
				break;
			}
		}
		
		//Validating Mail by comparing Sender, Subject and Date of mail
		CustomLogger.log("Validating Mail by comparing Sender, Subject and Date of mail");
		if(unreadMsgCount > 0){
			for(int i = 1; i<=unreadMsgCount; i++){
				String actualSender =  driver.findElement(By.xpath(".//*[@id='item-container']/div["+i+"]/div/p[1]")).getText().replace("\"", "").trim();
				String actualSubject = driver.findElement(By.xpath(".//*[@id='item-container']/div["+i+"]/div/p[2]/a")).getText().trim();
				String mailDate = driver.findElement(By.xpath(".//*[@id='item-container']/div["+i+"]/div[contains(@class,'ListItemContentDate')]")).getText().trim();

				CustomLogger.log("Sender and subject : "+actualSender+" : "+actualSubject+" : "+mailDate);
				if(mailDate.contains(trimDate)){
					driver.findElement(By.xpath(".//*[@id='item-container']/div["+i+"]/div/p[2]/a")).click();
					driver.waitForBrowserToLoadCompletely();
					break;
				}
				
			}
		}

		//Get the mail content/Message
		CustomLogger.log("Get the mail content/Message");
		String messageBody = driver.findElement(By.xpath(".//*[@id='MainContent_lblMessageBody']/table[5]/tbody/tr/td")).getText();
		CustomLogger.log("Message body : "+messageBody);		
		Assert.assertTrue(resetPasswordValidateCreatePswdLinkTwice(mailIDforForgetPwsd), "Failed some where in creating password");		
		return true;
	}
	
	public boolean resetPasswordValidateCreatePswdLinkTwice(String email){
		HomePage homePage = new HomePage();
		String mainWindow=driver.getWindowHandle();
		String TitleOfPage;
		String pswd = "testing";
		Date passwordRequestedTime;
		
		HtmlElement createPswd = driver.findElement(By.xpath(".//*[@id='MainContent_lblMessageBody']/table[5]/tbody/tr/td/a/img"));
		createPswd.click();
		homePage.waitFor(3);
		
		//validating Forgot password page
		CustomLogger.log("validating Forgot password page");
		if(driver.getWindowHandles().size()>1){
			driver.switchToChildWindow(mainWindow);
			TitleOfPage = driver.getTitle();
			CustomLogger.log("Verifying the URL of link - "+TitleOfPage);
			Assert.assertTrue(TitleOfPage.contains("Forgot Password"));
		}	else{					
			TitleOfPage = driver.getTitle();
			CustomLogger.log("Verifying the URL of link - "+TitleOfPage);
			Assert.assertTrue(TitleOfPage.contains("Forgot Password"));
		}
		
		//Enter valid password and submit
		CustomLogger.log("Enter valid password and submit");
		HtmlElement resetPwsdInput = driver.findElement(By.name("password"));
		HtmlElement confirmResetPswdInput = driver.findElement(By.name("confirm-password"));
		HtmlElement submitbtn = driver.findElement(By.xpath(".//*[@class='create-now'][contains(text(),'Submit')]"));
		
		resetPwsdInput.sendKeys(pswd);
		confirmResetPswdInput.sendKeys(pswd);
		submitbtn.click();
		homePage.waitFor(3);
		
		//Validating password reset validation page.
		CustomLogger.log("Validating password reset validation page. ");
		if(driver.getCountOfElementsWithXPath(".//*[@class='sso-form create-password fp_thanks_holder']/form")>0){
			Assert.assertTrue(driver.findElement(By.xpath(".//*[@class='continue-create-now'][contains(text(),'Continue')]")).isDisplayed(), "Didn't found continue Btn..");
			driver.findElement(By.xpath(".//*[@class='continue-create-now'][contains(text(),'Continue')]")).click();
			CustomLogger.log("URL : "+driver.getCurrentUrl());
			
			Assert.assertTrue(driver.getCurrentUrl().contains("http://search."+Environment.getEnvironment()+".pch.com"), "you are not on the expected page : Home page");
			driver.close();
			
			//verifying the create password link again.
			CustomLogger.log("verifying the create password link again.");
			driver.switchTo().window(mainWindow);
			createPswd.click();
			homePage.waitFor(2);
			if(driver.getWindowHandles().size()>1){
				driver.switchToChildWindow(mainWindow);
				homePage.waitFor(2);
				TitleOfPage = driver.getTitle();
				CustomLogger.log("Verifying the URL of link - "+TitleOfPage);
				Assert.assertTrue(TitleOfPage.contains("Error/Reset"), "Hope you are not on the required page..");
			}	else{					
				TitleOfPage = driver.getTitle();
				CustomLogger.log("Verifying the URL of link - "+TitleOfPage);
				CustomLogger.log("validating Error/Reset page.");
				Assert.assertTrue(TitleOfPage.contains("Error/Reset"), "Hope you are not on the required page..");
			}
			
			//validating Error/Reset page.
			
			String contentOfPage = driver.findElement(By.xpath("html/body/div[3]/p")).getText();
			CustomLogger.log("Content of the page : "+contentOfPage);
			Assert.assertTrue(contentOfPage.contains("This password reset link has already been used."), "we not seeing the valid message when user tried to reset pswd again.");
			
			//Click on continue on Reset page.
			CustomLogger.log("Click on continue on Reset page.");
			driver.findElement(By.xpath("html/body/div[3]/p/a[contains(text(), 'CONTINUE')]")).click();homePage.waitFor(2);
			Assert.assertTrue(driver.getTitle().contains("Forgot Password"), "Hope you are not on the required page..");
			
			//Validate email id and request for password
			CustomLogger.log("Validate email id and request fo password");
			Assert.assertTrue(driver.findElement(By.name("email")).getAttribute("value").equalsIgnoreCase(email), "Email id mis-matched..");
			
			CustomLogger.log("Click on submit button.");
			driver.findElement(By.xpath(".//*[@class='forgot-password-btn']")).click();homePage.waitFor(2);
			//validating Date from machine
			passwordRequestedTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy h:mm a ");
			String dateString = formatter.format(passwordRequestedTime);
			
			String trimDate = dateString.substring(0, 11);
			CustomLogger.log("Trim Date : "+trimDate);
			
			//			Date passwordRequestedTime = driver.findElement(By.id("EM")).submit();
			/*driver.findElement(By.id("miniforgotpass-sub-btn")).click();*/
			driver.waitForBrowserToLoadCompletely();
			
			//validating Help is on the way page.
			CustomLogger.log("validating Help is on the way page.");
			String messageWhenReqPswdTwice = helpIsOnTheWay().getText();
			CustomLogger.log("Message Displayed when we tried for the second time : "+messageWhenReqPswdTwice);
			Assert.assertTrue(messageWhenReqPswdTwice.contains("Help is on the way!"), "Message Displayed when we tried for the second time is incorrect");
			driver.close();
			driver.switchTo().window(mainWindow);
			homePage.waitFor(3);
			
			/*//checking the status of first mail and validate it by comparing Subject, Sender and Date.
			CustomLogger.log("checking the status of first mail and validate it by comparing Subject, Sender and Date.");
			int elementCounter = 0;
			while(!stausOfFirstMail()){
				refreshMail().click();
				homePage.waitFor(3);
				elementCounter++;
				if(elementCounter == 5){
					Assert.assertTrue(stausOfFirstMail(), "Unable to produce a mail tried refreshg mail "+elementCounter+" times.");
					break;
				}else{
					String mailDate = driver.findElement(By.xpath(".//*[@id='ucMailbox_msgGrid_ctl00']/tbody/tr[1]/td[7]/nobr")).getText();
					if(!(expectedSender.equalsIgnoreCase("Publishers Clearing House")&&expectedSubject.equalsIgnoreCase("Forgot Your Password?")&&mailDate.contains(trimDate)))
					refreshMail().click();homePage.waitFor(3);
				}
			}
			
			CustomLogger.log("validating unread mail count");
			int unreadMsgCount = getunreadMails();
			
			CustomLogger.log("Validating Sender, Subject and Date of Mail and picking the proper one.");
			if(unreadMsgCount > 0){
				for(int i = 1; i<=unreadMsgCount; i++){
					String actualSender =  driver.findElement(By.xpath(".//*[@id='ucMailbox_msgGrid_ctl00']/tbody/tr["+i+"]/td[4]/nobr")).getText();
					String actualSubject = driver.findElement(By.xpath(".//*[@id='ucMailbox_msgGrid_ctl00']/tbody/tr["+i+"]/td[6]/nobr")).getText();
					String mailDate = driver.findElement(By.xpath(".//*[@id='ucMailbox_msgGrid_ctl00']/tbody/tr["+i+"]/td[7]/nobr")).getText();
					
					CustomLogger.log("Sender and subject : "+actualSender+" : "+actualSubject+" : "+mailDate);
					if(actualSender.equalsIgnoreCase(expectedSender)&&actualSubject.equalsIgnoreCase(expectedSubject)&&mailDate.contains(trimDate))
						driver.findElement(By.xpath(".//*[@id='ucMailbox_msgGrid_ctl00']/tbody/tr["+i+"]")).click(); break;
					
				}
			}
			
			//validating mail and selecting the proper mail
			CustomLogger.log("validating mail and selecting the proper mail");
			driver.switchtoFrame("ucMailbox_dmMessage_txtMessage_contentIframe");
			String messageBody = driver.findElement(By.xpath("html/body/span/table[5]/tbody/tr/td")).getText();
			CustomLogger.log("Message body : "+messageBody);
			
			driver.findElement(By.xpath("html/body/span/table/tbody/tr/td/a/img")).click();
			homePage.waitFor(3);
			
			if(driver.getWindowHandles().size()>1){
				driver.switchToChildWindow(mainWindow);					
				TitleOfPage = driver.getTitle();
				CustomLogger.log("Verifying the URL of link - "+TitleOfPage);
				Assert.assertTrue(TitleOfPage.contains("Forgot Password"));
			}	else{					
				TitleOfPage = driver.getTitle();
				CustomLogger.log("Verifying the URL of link - "+TitleOfPage);
				Assert.assertTrue(TitleOfPage.contains("Forgot Password"));
			}
			
			HtmlElement resetPwsd = driver.findElement(By.id("PW"));
			HtmlElement confirmResetPswd = driver.findElement(By.id("CPW"));
			HtmlElement submitbtns = driver.findElement(By.id("sub_btn"));
			
			resetPwsd.sendKeys(pswd);
			confirmResetPswd.sendKeys(pswd);
			submitbtns.click();
			homePage.waitFor(3);
			
			if(driver.getCountOfElementsWithXPath(".//*[@id='message_area']")>0){
				Assert.assertTrue(driver.findElement(By.id("continue-btn")).isDisplayed(), "Didn't found continue Btn..");
				driver.findElement(By.id("continue-btn")).click();
				CustomLogger.log("URL : "+driver.getCurrentUrl());
				
				Assert.assertTrue(driver.getCurrentUrl().contains("http://search."+Environment.getEnvironment()+".pch.com"), "you are not on the expected page : Home page");
				//Navigating to Accounts page and validating email id
				driver.findElement(By.xpath("//a[contains(text(),'My Account')]")).click();
				Assert.assertTrue(driver.findElement(By.id("EM")).getAttribute("value").equalsIgnoreCase(email), "Email id mis-matched..");
				driver.close();
				driver.switchTo().window(mainWindow);
			}else{
				CustomLogger.log("Didn't find success message..");
				return false;
			}*/
			
			return true;
		}else{
			CustomLogger.log("Didn't find success message..");
			/*Assert.assertTrue(driver.findElement(By.id("continue-btn")).isDisplayed(), "Didn't found continue Btn..");*/
			return false;
		}
		
	}
	
	public HtmlElement enterEmailAddress(){
		return driver.findElement(By.xpath(".//*[@id='frgt-pwd-form']/form/input"));
	}
	
	private HtmlElement passwordForgot(){
		return driver.findElement(By.xpath(".//*[@id='lstServices']/optgroup/option[3][contains(text(),'password/forgot')]"));
	}
	
	private HtmlElement emailRequired(){
		return driver.findElement(By.id("req_Email"));
	}
	
	private HtmlElement getAccessCodeBtn(){
		return driver.findElement(By.id("btnGetAccessCodeRequest"));
	}
	
	private HtmlElement firstViewBtn(){
		return driver.findElement(By.xpath(".//*[@id='divAcceccCode']/table/tbody/tr[2]/td[4]/input"));
	}
	
	private HtmlElement firstExpireBtn(){
		return driver.findElement(By.xpath(".//*[@id='divAcceccCode']/table/tbody/tr[2]/td[5]/input"));
	}
	
	private HtmlElement popupWithURL(){
		return driver.findElement(By.xpath(".//*[@id='toPopup']/p"));
	}
	
	private HtmlElement closePopUp(){
		return driver.findElement(By.xpath(".//*[@id='closeDiv']/p/a[contains(text(),'Close')]"));
	}
	
	private HtmlElement pswdAssistancePageHeader(){
		return driver.findElement(By.xpath("html/body/div[3]/div[contains(@class,'headline')]/p"));
	}
	
	private HtmlElement pswdAssistancePageContent(){
		return driver.findElement(By.xpath("html/body/div[3][contains(@class,'content')]/p"));
	}
	
	public void verifyPasswordExpiredRefound(String mailIDforForgetPwsd){
		String regFound = "http://centralservices."+Environment.getEnvironment()+".pch.com/RFApi_v9/Svc/testpage.aspx#";
		driver.waitForBrowserToLoadCompletely();
		if(!driver.getTitle().contains("RFApi Test Page")){
			CustomLogger.log("Navigate to Reg Foundation page");
			driver.get(regFound);
		}

		//click on password/forgot on reg foundation page and enter valid email and click on "Get AccessCode"
		CustomLogger.log("Clicking on password/forgot");
		passwordForgot().click();
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Entering email : "+mailIDforForgetPwsd);
		emailRequired().clear();
		emailRequired().sendKeys(mailIDforForgetPwsd);
		CustomLogger.log("Click on get access code to generate URL");
		getAccessCodeBtn().click();
		homePage.waitFor(3);

		//click on view button and copy the URL and make the URL to expire 
		firstViewBtn().click();
		homePage.waitFor(3);
		CustomLogger.log("get URL from the pop up..");
		String resetPasswordContent = popupWithURL().getText();
		CustomLogger.log(resetPasswordContent);
		String resetPasswordURL = resetPasswordContent.substring(25, resetPasswordContent.length());
		CustomLogger.log(resetPasswordURL);
		CustomLogger.log("Close the URL popUp");
		closePopUp().click();
		CustomLogger.log("click on expire to make the link to expire..");homePage.waitFor(2);
		firstExpireBtn().click();
		
		CustomLogger.log("Accepting Alert..");
		/*driver.switchTo().alert().accept();*/
		
		//Navigate to Copied link and validate content of the page.
		CustomLogger.log("Navigating to reset ppassword link..");
		driver.get(resetPasswordURL);
		driver.waitForBrowserToLoadCompletely();
		
		CustomLogger.log("Validating header and content of the validation message displayed");
		Assert.assertTrue(pswdAssistancePageHeader().getText().contains("We're Sorry!"), "We are not seeing a valid message, we are seeing : "+pswdAssistancePageHeader().getText());
		Assert.assertTrue(pswdAssistancePageContent().getText().contains("This password reset link has expired."), "We are not seeing a valid message, we arseeing : "+pswdAssistancePageContent().getText());
		
	}
	
	public HtmlElement errorContent(){
		return driver.findElement(By.xpath(".//*[@class='sso-outer-shell']/div/span"));
	}
	
	public String errorContentText(){
		return errorContent().getText();
	}
	
	public boolean isErrorDisplayed(){
		return driver.getCountOfElementsWithXPath(".//*[@class='sso-outer-shell']")>0;
	}
	
	public HtmlElement submitBtnOnForgotPswdPage(){
		return driver.findElement(By.xpath(".//*[@class='forgot-password-btn']"));
	}
	
	public void verifyWithInvalidIDs(){
		String invalidFormatID = "np01@pch";
		String unregisteredEmail = "np1111@pchmail.com";
		String validEmail = "np85@pchmail.com";
		CustomLogger.log("Validating with empty Email field");
		enterEmailAddress().clear();
		
		submitBtnOnForgotPswdPage().click();
		homePage.waitFor(5);
		if(isErrorDisplayed()){
			String errorContent = errorContent().getText();
			//String errorMainContent = driver.findElement(By.xpath(".//*[@class='sso-outer-shell']/div[contains(@class,'inner-shell')]/div")).getText();
			CustomLogger.log("Header : "+errorContent);
			//CustomLogger.log("Header : "+errorMainContent);
			Assert.assertTrue(errorContent.contains("Please enter a valid Email."), "We are seeing some other alert : "+errorContent);
			//Assert.assertTrue(errorMainContent.contains("Email"), "We are seeing some other alert : "+errorMainContent);
		}
		
		CustomLogger.log("Validating with invalid format email");
		enterEmailAddress().clear();
		enterEmailAddress().sendKeys(invalidFormatID);
		submitBtnOnForgotPswdPage().click();
		homePage.waitFor(5);
		if(isErrorDisplayed()){
			String errorContent = errorContent().getText();
			//String errorMainContent = driver.findElement(By.xpath(".//*[@class='sso-outer-shell']/div[contains(@class,'inner-shell')]/ul/li")).getText();
			CustomLogger.log("Header : "+errorContent);
			//CustomLogger.log("Header : "+errorMainContent);
			Assert.assertTrue(errorContent.contains("Please enter a valid Email."), "We are seeing some other alert : "+errorContent);
			//Assert.assertTrue(errorMainContent.contains("Please enter a valid Email."), "We are seeing some other alert : "+errorMainContent);
		}
		
		CustomLogger.log("Validating with unregistered email");
		enterEmailAddress().clear();
		enterEmailAddress().sendKeys(unregisteredEmail);
		submitBtnOnForgotPswdPage().click();
		homePage.waitFor(5);
		if(isErrorDisplayed()){
			String errorContent = errorContent().getText();
			//String errorMainContent = driver.findElement(By.xpath(".//*[@class='sso-outer-shell']/div[contains(@class,'inner-shell')]/ul/li")).getText();
			CustomLogger.log("Header : "+errorContent);
			//CustomLogger.log("Header : "+errorMainContent);
			Assert.assertTrue(errorContent.contains("Sorry, the email you provided is not recognized. Please 'Register' to create your own account today."), "We are seeing some other alert : "+errorContent);
			//Assert.assertTrue(errorMainContent.contains("Sorry, the email you provided is not recognized. Please 'Register' to create your own account today."), "We are seeing some other alert : "+errorMainContent);
		}
		
		CustomLogger.log("Validating with valid email");
		enterEmailAddress().clear();
		enterEmailAddress().sendKeys(validEmail);
		submitBtnOnForgotPswdPage().click();
		homePage.waitFor(5);
		if(!isErrorDisplayed()){
			String helpContent = helpIsOnTheWay().getText();
			//String errorMainContent = driver.findElement(By.xpath(".//*[@class='sso-outer-shell']/div[contains(@class,'inner-shell')]/ul/li")).getText();
			CustomLogger.log("Header : "+helpContent);
			//CustomLogger.log("Header : "+errorMainContent);
			Assert.assertTrue(helpContent.contains("Help is on the way!"), "We are seeing some other alert : "+helpContent);
			//Assert.assertTrue(errorMainContent.contains("Sorry, the email you provided is not recognized. Please 'Register' to create your own account today."), "We are seeing some other alert : "+errorMainContent);
		}
		
	}
	
}
