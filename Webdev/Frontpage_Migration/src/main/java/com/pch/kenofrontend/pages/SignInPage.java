package com.pch.kenofrontend.pages;

import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.pch.kenofrontend.utilities.PropertiesReader;
import com.pch.kenofrontend.utilities.RandomGenerator;
import com.pch.kenofrontend.utilities.Users;
import com.pchengineering.registrations.RegistrationRequestGenerator;

import net.serenitybdd.core.pages.PageObject;

public class SignInPage extends PageObject{
	
	//RegistrationRequestGenerator rs;
	RegistrationPage registrationPage; 
		
	public SignInPage(WebDriver driver) {
		super(driver);
	}
	
	private By Email_txtbox = new By.ById("EM");
	private By Pass_txtbox = new By.ById("PW");
	private By KeepMeSignedIn_checkbox = new By.ByCssSelector("input.keep-me-signed-in");
//	private By SignIn_link = new By.ByLinkText("Log In");
	private By SignIn_link = new By.ByCssSelector(".sub-btn-left");
	
	public void login(ExamplesTable credentials)
	{
		element(Email_txtbox).sendKeys(credentials.getRow(0).get("user_name"));
		element(Pass_txtbox).sendKeys(credentials.getRow(0).get("password"));
		if (!element(KeepMeSignedIn_checkbox).isSelected())
	    element(KeepMeSignedIn_checkbox).click();
		element(SignIn_link).click();
	}
//Below method is to support sign in for existing user created while automation run
	public void login(String username, String password)
	{
		element(Email_txtbox).sendKeys(username);
		element(Pass_txtbox).sendKeys(password);
		if (!element(KeepMeSignedIn_checkbox).isSelected())
	    element(KeepMeSignedIn_checkbox).click();
		element(SignIn_link).click();
	}
	
	public void loginAsMiniUser() 	
	{
		//Create a mini reg user
		registrationPage.createMiniRegUser();
						
		//Sign in with mini reg user
		login(RegistrationPage.email,"Pch123");
	}
	
	public void loginIfSignInPageAppearing()
	{
		if (element(Email_txtbox).isCurrentlyVisible())
			login(RegistrationPage.email, "Pch123");	
	}
	
	
		
	
	

}
