package com.pch.kenofrontend.steps;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import net.serenitybdd.core.webdriver.driverproviders.ChromeDriverProvider;
import net.thucydides.core.webdriver.DriverSource;

public class CustomChromeDriver implements DriverSource
{

	@Override
	public WebDriver newDriver() {
		WebDriver driver=null;
		ChromeDriverProvider d=null;
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
	System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\browerDrivers\\chromedriver.exe");
		
		ChromeOptions opt= new ChromeOptions();
		//opt.addExtensions(new File("src\\test\\resources\\browerDrivers\\SuperBlock-Adblocker_v.crx"));
		//opt.addArguments("--load-extensions:\"C:\\Users\\kramu\\Downloads\\SuperBlock-Adblocker_v");
		capabilities.setCapability(ChromeOptions.CAPABILITY, opt);
		driver= new ChromeDriver(capabilities);
		return driver;
	
	}

	@Override
	public boolean takesScreenshots() {
		// TODO Auto-generated method stub
		return false;
	}

}
