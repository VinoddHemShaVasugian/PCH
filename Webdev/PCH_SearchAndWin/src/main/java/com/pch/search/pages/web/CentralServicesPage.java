package com.pch.search.pages.web;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;
import com.pch.search.utilities.User;

public class CentralServicesPage extends Action {
	
	String REGISTRATION_FOUNDATION_URL = "http://centralservices."+Environment.getEnvironment()+".pch.com/RFApi_v8/Svc/testpage.aspx";
	String REGISTRATION_FOUNDATION_URL_SEGMENTS_MEMBERSHIP = "http://centralservices."+Environment.getEnvironment()+".pch.com/segmentationapi/admin/SegmentsMembership.aspx";
		
	private void selectAPI(String API){
		String xpath = String.format("//select[@id='lstServices']/descendant::option[text()='%s']", API);
		driver.findElement(By.xpath(xpath)).click();
	}
	
	private HtmlElement csUserId() {
		Common.sleepFor(1000);
		return driver.findElement(By.id("req_Email"));
		
	}

	private HtmlElement csPassword() {
		return driver.findElement(By.id("req_Password"));
		
	}

	private void clickTestApiBtn() {
		driver.findElement(By.id("btnSubmitRequest")).click();		
	}

	private void clickGetAccessCode(){
		driver.findElement(By.id("btnGetAccessCodeRequest")).click();
		//Wait till table with tokens is loaded
		driver.findElement(By.xpath("//div[@id='divAcceccCode']/table"));
	}
	
	public String createMiniRegUser(User user){
		driver.get(REGISTRATION_FOUNDATION_URL);
		selectAPI("registration/miniregistermember");
		csUserId().clear();
		csUserId().sendKeys(user.getEmail());
		csPassword().clear();
		csPassword().sendKeys(user.getPassword());
		clickTestApiBtn();
		//Common.sleepFor(1000);
		CustomLogger.log("MiniReg User has been created successfully.");
		CustomLogger.log("Minireg UserID = '" + user.getEmail() + "'");
		CustomLogger.log("Password = '" + user.getPassword() + "'");
		return(getRegUserLoginUrl(user));
	}
	
	public String createFBUser(User user){
		driver.get(REGISTRATION_FOUNDATION_URL);
		selectAPI("externalconnect/facebookwithemail");
		setField("ExternalEmail", user.getEmail());
		setField("ExternalId", Integer.toString(user.getEmail().hashCode()&Integer.MAX_VALUE));
		setField("PchEmail", user.getEmail());
		clickTestApiBtn();
		return(getRegUserLoginUrl(user));
		//setField("ExternalId", Integeruser.getEmail().hashCode());
	}
	
	private void setField(String fieldName,String value){		 
		//HtmlElement fieldTable = driver.findElement(By.id("tbl_Fields"));
		HtmlElement fieldLabel= driver.findElement(By.xpath(String.format("//td[text()='%s']",fieldName)));		
		HtmlElement fieldText = fieldLabel.findElement(By.xpath("following-sibling::td/input"));		
		fieldText.clear();
		fieldText.sendKeys(value);		
	}
	
		
	public String createFullRegUserWithoutPassword(User user){
		driver.get(REGISTRATION_FOUNDATION_URL);
		selectAPI("registration/registermember");
		setSelectableField("Title", user.getTitle());
		setSelectableField("FirstName",user.getFirstname());
		setSelectableField("LastName",user.getLastname());
		setSelectableField("Address1",user.getStreet());
		setSelectableField("City",user.getCity());
		
		String state = user.getState();
		if (state.equals("New York")){
			state="NY";
		}
		setSelectableField("State",state);
		setSelectableField("ZipCode",user.getZip());
		setSelectableField("Country","USA");
		setSelectableField("Email",user.getEmail());
		setSelectableField("ConfirmEmail",user.getEmail());
		unCheckField("Password");
		unCheckField("ConfirmPassword");
		selectSubscription("FRONTPAGE");
		clickTestApiBtn();
		return(getRegUserLoginUrl(user));
	}	
	
	
	/*vamsi k Nov 26 2015
			To create Silveruser		
	*/
	public String createSilverUser(User user){
		driver.get(REGISTRATION_FOUNDATION_URL);
		selectAPI("registration/setmember");
		setSelectableField("Title", user.getTitle());
		setSelectableField("FirstName",user.getFirstname());
		setSelectableField("LastName",user.getLastname());
		setSelectableField("Address1",user.getStreet());
		setSelectableField("City",user.getCity());
		
		String state = user.getState();
		if (state.equals("New York")){
			state="NY";
		}
		setSelectableField("State",state);
		setSelectableField("ZipCode",user.getZip());
		setSelectableField("Country","USA");
		setSelectableField("Email",user.getEmail());
		setSelectableField("ConfirmEmail",user.getEmail());
		unCheckField("Password");
		unCheckField("ConfirmPassword");
		selectSubscription("FRONTPAGE");
		clickTestApiBtn();
		
		Common.sleepFor(6000);
		
		return(getRegUserLoginUrl(user));
	}	
	
	
	public boolean expirePasswordForUser(String email){
		driver.get(REGISTRATION_FOUNDATION_URL);
		selectAPI("password/forgot");
		setField("Email", email);
		clickGetAccessCode();
		return clickExpire();
	}
	
	private boolean clickExpire(){
		String xpath = "//input[contains(@id,'btnExpire')][@class='expireCSS']";
		int tokensAvailableForExpiry = driver.getCountOfElementsWithXPath(xpath); 
		if(tokensAvailableForExpiry==0){
			CustomLogger.log("No Password Available to expire, all already expired !");
			return false;
		}else{
			driver.findElement(By.xpath(xpath)).click();
			Common.sleepFor(2000);
			Alert alert = driver.switchTo().alert();
			String alertMessage = alert.getText();
			alert.accept();
			if(alertMessage.contains("Access Code Expired")){				
				return true;
			}else{
				return false;
			}
		}
	}
	
	public String createFullRegUserWithPassword(User user){
		driver.get(REGISTRATION_FOUNDATION_URL);
		selectAPI("registration/registermember");
		setSelectableField("Title", user.getTitle());
		setSelectableField("FirstName",user.getFirstname());
		setSelectableField("LastName",user.getLastname());
		setSelectableField("Address1",user.getStreet());
		setSelectableField("City",user.getCity());
		
		String state = user.getState();
		if (state.equals("New York")){
			state="NY";
		}
		setSelectableField("State",state);
		setSelectableField("ZipCode",user.getZip());
		setSelectableField("Country","USA");
		setSelectableField("Email",user.getEmail());
		setSelectableField("ConfirmEmail",user.getEmail());
		setSelectableField("Password",user.getPassword());
		setSelectableField("ConfirmPassword",user.getPassword());
		
		selectSubscription("FRONTPAGE");
		clickTestApiBtn();
		return(getRegUserLoginUrl(user));
	}
		
	private String getRegUserLoginUrl(User user){	
		Common.sleepFor(3000);
		String gmt = getGMT();
		if (gmt.isEmpty()){			
			return null;
		}
		String url= Common.getAppUrl(Environment.getAppName()) + "/?e=" + gmt + "&email=" + user.getEmail();
		CustomLogger.log("Registration URL "+url);
		return url;
	}
	
	private void setSelectableField(String fieldName,String value){
		Character specialChar = '\u00A0'; 
		HtmlElement fieldTable = driver.findElement(By.id("tbl_Fields"));
		HtmlElement fieldLabel= fieldTable.findElement(By.xpath(String.format("//td[text()='%s']",fieldName+specialChar)));
		HtmlElement fieldChkBox = fieldLabel.findElement(By.xpath("input[@type='checkbox']"));
		HtmlElement fieldText = fieldLabel.findElement(By.xpath("following-sibling::td/input"));
		
		if(!fieldChkBox.isSelected()){
			fieldChkBox.click();
		}
		
		fieldText.clear();
		fieldText.sendKeys(value);		
	}
	
	private void unCheckField(String fieldName){
		Character specialChar = '\u00A0'; 
		HtmlElement fieldTable = driver.findElement(By.id("tbl_Fields"));
		HtmlElement fieldLabel= fieldTable.findElement(By.xpath(String.format("//td[text()='%s']",fieldName+specialChar)));
		HtmlElement fieldChkBox = fieldLabel.findElement(By.xpath("input[@type='checkbox']"));
				
		if(fieldChkBox.isSelected()){
			fieldChkBox.click();
		}
	}
	
	private void selectSubscription(String subscriptionCode){
		HtmlElement fieldTable = driver.findElement(By.id("tbl_SubscriptionData"));
		HtmlElement fieldLabel= fieldTable.findElement(By.xpath(String.format("//td[text()='%s']",subscriptionCode)));
		HtmlElement fieldChkBox = fieldLabel.findElement(By.xpath("preceding-sibling::td/input[@type='checkbox']"));
		
		if(!fieldChkBox.isSelected()){
			fieldChkBox.click();
		}
		
	}
	
	private String getGMT(){
		String response = csGetResponse();
		String gmtParserRegex = "(?<=GlobalMemberToken\": \").*?(?=\")";
		String gmt = Common.subString(response, gmtParserRegex);
		CustomLogger.log("Global Member Token - "+gmt);
		return(gmt);
		/*String[] temp = response.split("\"GlobalMemberToken\": \"");
		String[] temp2 = temp[1].split("\",");
		String gmt = temp2[0];
		CustomLogger.log("GlobalMemberToken = " + gmt);
		return gmt;*/
	}
	
	private String csGetResponse() {		
		return new WebDriverWait(driver.getwrappedDriver(), 10).until(new ExpectedCondition<String>() {

			public String apply(WebDriver arg0) {
				String text = driver.findElement(By.id("status-message")).getText();
				if(!text.isEmpty()){
					return text;
				}else{
					return null;
				}
			}
		});
				
	}
	public void navigateToSegmentMembershipPage(){
		driver.get(REGISTRATION_FOUNDATION_URL_SEGMENTS_MEMBERSHIP);
	}

	public void setSegmentMembership(String userEmail,String textToBeSelect){
		driver.findElement(By.id("txtSetByEmailAndNameEmail")).sendKeys(userEmail);
		HtmlElement dropDown=driver.findElement(By.id("lbxSetByEmailAndNameActiveSegments"));
		dropDown.selectDropdownElementUsingVisibleText(textToBeSelect);
		driver.findElement(By.id("btnSetByEmailAndNameSegmentsMembership")).click();	
		
	}
	
	/*
	 * To get GMT value
	 * @param: user
	 * @returns: create a user and returns GMT of that user.
	*/
	public String createFullRegAndGetGMT(User user){
		
		driver.get(REGISTRATION_FOUNDATION_URL);
		selectAPI("registration/registermember");
		setSelectableField("Title", user.getTitle());
		setSelectableField("FirstName",user.getFirstname());
		setSelectableField("LastName",user.getLastname());
		setSelectableField("Address1",user.getStreet());
		setSelectableField("City",user.getCity());
		
		String state = user.getState();
		if (state.equals("New York")){
			state="NY";
		}
		setSelectableField("State",state);
		setSelectableField("ZipCode",user.getZip());
		setSelectableField("Country","USA");
		setSelectableField("Email",user.getEmail());
		setSelectableField("ConfirmEmail",user.getEmail());
		setSelectableField("Password",user.getPassword());
		setSelectableField("ConfirmPassword",user.getPassword());
		
		selectSubscription("FRONTPAGE");
		clickTestApiBtn();
				
		return getGMT();
	}
	
	
}
