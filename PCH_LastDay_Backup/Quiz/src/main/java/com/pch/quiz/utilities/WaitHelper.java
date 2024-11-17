package com.pch.quiz.utilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelper {

	public static void forceWait(int milliSeconds) throws InterruptedException
	{
		Thread.sleep(milliSeconds);
	}

	public static void waitForPresenceOfElement(WebDriver driver, By by)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		}catch(Exception t)
		{
			t.printStackTrace();
		}
	}

	public static void waitForPresenceOfElement(WebDriver driver, By by, int seconds)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		}catch(Exception t)
		{
			t.printStackTrace();
		}
	}

	public static void waitForVisibliyOfElement(WebDriver driver, By by)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		}catch(Exception t)
		{
			t.printStackTrace();
		}
	}

	public static void waitForVisibliyOfElement(WebDriver driver, By by, int seconds)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		}catch(Exception t)
		{
			t.printStackTrace();
		}
	}


	public static void waitForInVisibliyOfElement(WebDriver driver, By by)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		}catch(Exception t)
		{
			t.printStackTrace();
		}
	}

	public static void waitForInVisibliyOfElement(WebDriver driver, By by, int seconds)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		}catch(Exception t)
		{
			t.printStackTrace();
		}
	}


	public static void waitForElementToBeClickable(WebDriver driver, By by)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(by));
		}catch(Exception t)
		{
			t.printStackTrace();
		}
	}

	//new
	public static void waitforElementClickable(WebDriver driver,By by)
	{
		try {

			WebDriverWait wait3 = new WebDriverWait(driver, 10);
			wait3.until(ExpectedConditions.invisibilityOfElementLocated(by));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}

	public static void waitForElementToBeClickable(WebDriver driver, By by, int seconds)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.elementToBeClickable(by));
		}catch(Exception t)
		{
			t.printStackTrace();
		}
	}

	public static void waitForInVisibliyOfElementByText(WebDriver driver, By by, int seconds, String text)
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.invisibilityOfElementWithText(by, text));
		}
		catch(Exception t)
		{
			t.printStackTrace();
		}
	}

	public static void waitForURLLoad(WebDriver driver, String text){
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.urlContains(text));
	}



}

