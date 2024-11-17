package com.pch.survey.pages.profiles;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;

public class ProfileBuilderPage extends PageObject{
 
	public ProfileBuilderPage(WebDriver driver) {
		super(driver);
	}

	public ProfileBuilderPage() {
		super();
	}

   public String getStatusMessage() {
	    return  	driver.findElement(By.cssSelector(".survey__profile-builder__header__progress")).getText();
   }
	
   public String getPercentComplete() {
	    return  	driver.findElement(By.cssSelector(".survey__profile-builder__header__progress__txt")).getText();
  }
	
	
	public void agreeDataColection() {
	   	waitUntilThePageLoads();
	   	driver.findElement(By.cssSelector("#yesConsent")).click();;
	   	waitSeconds(1);
 	   	driver.findElement(By.cssSelector("button[type='submit']")).click();;
	   	
	}
	
	
	
    public void  selectProfileCategory(String category) {
    	waitUntilThePageLoads();
    	WebElement parentEle = driver.findElement(By.cssSelector("[data-profile-category="+category+"]"));
        WebElement arrowEle = parentEle.findElement(By.tagName("a"));
    	scrollIntoView(arrowEle);
    	arrowEle.click();
    }
	
	public List<WebElement> getProfileCategories(){
	 	waitUntilThePageLoads();
	 	return driver.findElements(By.cssSelector(".profile-category"));
	}
       
    public void clickExitButton() {
    	scrollIntoView(driver.findElement(By.cssSelector("a[href*='/pchsurveys']"))).click();
    }
    
    public String getProfileBuilderTitle() {
    	waitUntilThePageLoads();
  		waitSeconds(10);
  		return driver.findElement(By.xpath("//div[@class='survey__header']")).getText();
    }
    
    public void PercentageCompletionProgressBar() {
    	waitUntilThePageLoads();
    	WebElement ProgressBar = driver.findElement(By.xpath("//div[@class='survey__profile-builder__nav']/div[1]"));
    	if(ProgressBar.isDisplayed()) {
    		System.out.println("Percentage completion progress bar is displayed in the profile builder page");
    	}else {
    		 Assert.fail("No progress bar is shown");
    	}
    }
    
    public String getTextNextToProgressBar() {
    	waitUntilThePageLoads();
  		waitSeconds(10);
  		String textC = driver.findElement(By.xpath("//div[@class='survey__profile-builder__nav__txt']")).getText();
  		System.out.println(textC);
  		return driver.findElement(By.xpath("//div[@class='survey__profile-builder__nav__txt']")).getText();
    }
    
    public void PrivacyPolicyInText() {
    	waitUntilThePageLoads();
    	WebElement PrivacyPolicy = driver.findElement(By.xpath("//div[@class='survey__profile-builder__nav__txt']/a"));
    	if(PrivacyPolicy.isDisplayed()) {
    		System.out.println("Privacy Policy is added to the text copy next to the progress bar");
    	}else {
    		 Assert.fail("Privacy Policy is not added to the text copy");
    	}
    }
    
    public void ExitButtonInProfileBuilder() {
    	waitUntilThePageLoads();
    	WebElement ExitButton = driver.findElement(By.xpath("//a[text()='Exit']"));
    	if(ExitButton.isDisplayed()) {
    		System.out.println("Exit button is placed in the bottom of the profile builder page");
    	}else {
    		 Assert.fail("Exit button is not added in the profile builder page");
    	}
    }
}
