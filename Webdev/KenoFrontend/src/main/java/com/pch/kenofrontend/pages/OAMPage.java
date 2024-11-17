package com.pch.kenofrontend.pages;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.kenofrontend.utilities.PropertiesReader;
import com.pch.kenofrontend.utilities.Users;
import com.pch.kenofrontend.utilities.WaitHelper;
import com.pchengineering.registrations.RegistrationRequestGenerator;

import net.serenitybdd.core.pages.PageObject;

/**
 * @author atiwari Oct 1, 2018
 *
 */
public class OAMPage extends PageObject {	

	private By Search_Menu = new By.ByXPath("//span[contains(text(),'Search')]");
	private By LegacyContestEntry_tab = new By.ByXPath("//u[contains(text(),'Legacy Contest Entries')]");
	private By EmailTextBox = new By.ByXPath("//input[@id='Email-TextField-inputEl']");
	private By Key = new By.ByClassName("x-grid-cell-inner");
	private By SubscriptionEvents_tab = new By.ByXPath("//u[contains(text(),'Subscriptions Events')]");


	
	public OAMPage(WebDriver driver) 
	{
		super(driver);
	}
	
	public void loginOAM()
	{
		
		getDriver().get(PropertiesReader.getInstance().getData("OAMUrl"));
		getDriver().manage().window().maximize();
	}


	public void clickSearchMenu()
	{
		WaitHelper.waitForElementToBeClickable(getDriver(), Search_Menu,5);
			try {
				WaitHelper.forceWait(1000);
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
					element(Search_Menu).click();
	}

	public void clickLegacyContestEntryTab(){
		WaitHelper.waitForElementToBeClickable(getDriver(), LegacyContestEntry_tab,10);
		element(LegacyContestEntry_tab).click();
	}

	public void enterUserWithoutPwdEmail() throws InterruptedException 
	{		
		
		System.out.println(" this is  email " + RegistrationPage.email);
		WaitHelper.waitForElementToBeClickable(getDriver(), EmailTextBox,30);
		WaitHelper.forceWait(7000);
		element(EmailTextBox).typeAndEnter(RegistrationPage.email);
		}
	
	
	public void enterUserWithPwdEmail(String email) throws InterruptedException 
	{		
		
		System.out.println(" this is  email " + RegistrationPage.uni_email);
		WaitHelper.waitForElementToBeClickable(getDriver(), EmailTextBox,30);
		WaitHelper.forceWait(7000);
		//element(EmailTextBox).typeAndEnter(RegistrationPage.rand_Email);
		element(EmailTextBox).typeAndEnter(RegistrationPage.uni_email);
		}
	
	/*public void enterUserWithPwdEmail() throws InterruptedException 
	{		
		
		System.out.println(" this is  email " + RegistrationPage.email);
		WaitHelper.waitForElementToBeClickable(getDriver(), EmailTextBox,30);
		WaitHelper.forceWait(7000);
		element(EmailTextBox).typeAndEnter(RegistrationPage.email);
		}*/

	public void verifyContestKeyInContestEntryRecord() throws InterruptedException
	{
		WaitHelper.forceWait(8000);
		element(Key).isPresent();
																												
		List<WebElement> contestKeyInOAM = getDriver().findElements(By.cssSelector("tbody#gridview-1364-body > tr#gridview-1364-record-ext-record-914 > td.x-grid-cell-gridcolumn-1354 > div.x-grid-cell-inner"));
		List<WebElement> foreignSourceInOAM = getDriver().findElements(By.cssSelector("tbody#gridview-1364-body > tr#gridview-1364-record-ext-record-914 > td.x-grid-cell-gridcolumn-1358 > div.x-grid-cell-inner"));
		//List<WebElement> Parent = getDriver().findElements(By.cssSelector("tbody#gridview-1363-body > tr"));	
		List<WebElement> Parent = getDriver().findElements(By.cssSelector("tbody#gridview-1364-body > tr"));
		
		Iterator <WebElement> i1 = contestKeyInOAM.iterator();
		Iterator <WebElement> i2 = foreignSourceInOAM.iterator();
		
			System.out.println("OAM record size is  " + Parent.size());
			Thread.sleep(3000);
			Assert.assertTrue(Parent.size()!=0);
			
			while(i1.hasNext() && (i2.hasNext()))
			{
				WebElement e1 = i1.next();
				WebElement e2 = i2.next();
			
				System.out.println("e1    "+e1.getText());
				System.out.println("e2    "+e2.getText());
				
				if(e1.getText().contains(JoomlaAdminPage.s1))
				{
				System.out.println("JoomlaAdminPage.s1    "+JoomlaAdminPage.s1);
				Assert.assertEquals(e1.getText(),JoomlaAdminPage.s1);
				Assert.assertTrue(e2.getText().equalsIgnoreCase("Keno"));
		
				}
			}
		System.out.println("Checked Contest Entry in OAM");	
		
	}

	public void clickSubscriptionTab(){
		WaitHelper.waitForElementToBeClickable(getDriver(), SubscriptionEvents_tab,10);
		element(SubscriptionEvents_tab).click();
	}


	public void verifyKenoOptinRecord() throws InterruptedException {		
		WaitHelper.forceWait(8000);
		element(Key).isPresent();

		List<WebElement> SubscriptionProperty = getDriver().findElements(By.cssSelector("tbody#gridview-1434-body > tr#gridview-1434-record-ext-record-916 > td.x-grid-cell-gridcolumn-1409 > div.x-grid-cell-inner"));
		List<WebElement> IsEmailable = getDriver().findElements(By.cssSelector("tbody#gridview-1434-body > tr#gridview-1434-record-ext-record-916 > td.x-grid-cell-gridcolumn-1410 > div.x-grid-cell-inner"));
		List<WebElement> Parent = getDriver().findElements(By.cssSelector("tbody#gridview-1434-body > tr"));
		//List<WebElement> Parent = getDriver().findElements(By.cssSelector("div#emailability-events-top-grid-id-normal-body"));
	
		Iterator <WebElement> i1 = SubscriptionProperty.iterator();
		Iterator <WebElement> i2 = IsEmailable.iterator();
		
			System.out.println("OAM record size is  " + Parent.size());
			Thread.sleep(3000);
			//Assert.assertTrue(Parent.size()!=0);
			
			while(i1.hasNext() && (i2.hasNext()))
			{
				WebElement e1 = i1.next();
				WebElement e2 = i2.next();
				
				System.out.println("e1    "+e1.getText());
				System.out.println("e2    "+e2.getText());
				
				if(e1.getText().equalsIgnoreCase("Keno"))
				{							
				Assert.assertEquals(e1.getText(),"Keno");
				Assert.assertTrue(e2.getText().equalsIgnoreCase("Y"));
				}
			}
		System.out.println("Completed checking Optin / Subscription in OAM");
	}

}
