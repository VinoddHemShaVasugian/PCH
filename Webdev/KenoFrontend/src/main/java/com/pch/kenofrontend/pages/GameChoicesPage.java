package com.pch.kenofrontend.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;

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
	
	
public void choiceSelection(String game) throws InterruptedException, FindFailed{
		
		List<WebElement> Choices_Parent = getDriver().findElements(By.cssSelector("div.choices__games__page > div"));
		System.out.println("Choices_Parent.size()  "+ Choices_Parent.size());
		int sz = Choices_Parent.size();
		String[] alttextarr = new String[sz];
		String alttext=null;
		
		int i;
		int selectedChoice=0;
		
		List<WebElement> Choices = getDriver().findElements(By.cssSelector("div.choices__games__page__game > div > img"));
		
		for (i=0; i<Choices_Parent.size();i++)
		{
			alttext = Choices.get(i).getAttribute("alt");
			alttextarr[i]=alttext;
			if (game.equalsIgnoreCase("Scratch Card"))
				{
					alttext="IWPATH";
					if(alttextarr[i].contains(alttext))
					{
					System.out.println("alttextarray  "+ Arrays.toString(alttextarr));
										String alttextIWPath = Choices.get(i).getAttribute("alt");
					System.out.println( "selectedChoice ALT is =   "+alttextIWPath);
					selectedChoice=i;
					System.out.println( "selectedChoice  = "+selectedChoice);
					break;
					}
				}
		
			if (game.equalsIgnoreCase("SFL"))
				{
					alttext="D-Keno set-for-life_3cards";
					 if(alttextarr[i].equals(alttext))
					 {
					selectedChoice=i;
					System.out.println( "selectedChoice   = "+selectedChoice);
					}
				}
		}
		
		System.out.println("alttextarray  "+ Arrays.toString(alttextarr));
		switch (alttext)
			{
			case "D-Keno set-for-life_3cards":
				{
					System.out.println( "selectedChoice  = "+selectedChoice);
					Choices.get(selectedChoice).click();
				}
				break;
			case "IWPATH":
				{
					System.out.println( "selectedChoice  = "+selectedChoice);
					Choices.get(selectedChoice).click();
				}
				break;
			default:
					System.out.println("default slots game");
					alttext="slots";
			}
		System.out.println("alttextarray  "+ Arrays.toString(alttextarr));
		
	}

	}
