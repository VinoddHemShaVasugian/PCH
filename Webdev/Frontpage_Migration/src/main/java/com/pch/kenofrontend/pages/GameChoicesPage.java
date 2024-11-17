package com.pch.kenofrontend.pages;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import net.serenitybdd.core.pages.PageObject;

public class GameChoicesPage extends PageObject {

	// Initialize the Pages
	HomePage homePage;

	// Initialize the Page Objects using By Class
	private By selectedNumbers = new By.ByCssSelector(".card-tab__completed__num__val");
	
	// Initialize Variables
	WebDriverWait wait = new WebDriverWait(getDriver(), 60);
	ArrayList<String> submittednumbers = new ArrayList<String>();

	public GameChoicesPage(WebDriver driver) {
		super(driver);
	}

	// Below method is to validate matching numbers only in ascending order.
	// Otherwise volume of permutation and combination will slow up the method
	// execution
	public void matchsubmittednumbers(List<String> numberstoselect) 
	{
		homePage.waitForElementPresence(selectedNumbers);
		//Commenting out below 'if' condition, as submitted numbers can be checked on side rail on Live Drawing page as well (if Live Drawing has started)
		//selectedNumbers locator is same on game-choices page & live-drawing page  
		//if(getDriver().getCurrentUrl().contains("game-choices")) 
		//{
		System.out.println("Print submitted numbers from GameChoicesPage file");
		System.out.println(numberstoselect);
			int index = 0;
			String temporarynumber = "";
			for (WebElement ele : getDriver().findElements(selectedNumbers)) 
			{
				temporarynumber = ele.getText();

				//Below steps not required as above getText method fetches single number without zero.
				//From story file, we can pass numbers without zero.
				/*if (temporarynumber.length() == 1) 
					temporarynumber = "0" + temporarynumber;*/
								
				if (temporarynumber.equals(numberstoselect.get(index).toString()))
					index++;
				else				
					break;				
				
				if (index == 10)
					break;					
			}
			
			if (index == 10) 
				System.out.println("All 10 matching numbers are: " + numberstoselect);
			else
				Assert.assertFalse("Numbers submitted and appearing on game choices are not matching", true);
			
		//} else
		//	Assert.assertFalse("User did not land on Game Choices page", true);
		}
	}
