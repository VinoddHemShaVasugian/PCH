package com.pch.kenofrontend.pages;

import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.pch.kenofrontend.utilities.PropertiesReader;
import com.pch.kenofrontend.utilities.RandomGenerator;
import com.pch.kenofrontend.utilities.Users;
import com.pchengineering.registrations.RegistrationRequestGenerator;

import net.serenitybdd.core.pages.PageObject;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class RegistrationPage extends PageObject{

	
    //public static String rand_Email=null;
    //Elements of Keno Registration Page
    private By FirstName_txtbox = new By.ByCssSelector("input.required.first-name");    
    private By LastName_txtbox = new By.ByCssSelector("input.required.last-name");
    private By Street_txtbox = new By.ByCssSelector("input.required.street");
    private By Suite_txtbox = new By.ByCssSelector("input.apt");
    private By City_txtbox = new By.ByCssSelector("input.required.city");
    private By Zip_txtbox = new By.ByCssSelector("input.required.zip");
    private By Email_txtbox = new By.ByCssSelector("input.required.email");
    private By ConfirmEmail_txtbox = new By.ByCssSelector("input.required.confirm-email");
//    private By Pwd_txtbox = new By.ByCssSelector("input.required.password");
//    private By ConfirmPwd_txtbox = new By.ByCssSelector("input.required.confirm-password");
    private By Pwd_txtbox = new By.ByCssSelector(".regform__input.regform__input--password.password");
    private By ConfirmPwd_txtbox = new By.ByCssSelector(".regform__input.regform__input--confirmpassword.confirm-password");
    private By Title_dropdown = new By.ByXPath("//select[@name='title']");
    private By State_dropdown = new By.ByXPath("//select[@name='state']");
    private By Month_dropdown = new By.ByXPath("//select[@class='reg-input month' or contains(@class,'regform__input--month')]");
    private By Day_dropdown = new By.ByXPath("//select[@class='reg-input day'  or contains(@class,'regform__input--day')]");
    private By Year_dropdown = new By.ByXPath("//select[@class='reg-input year' or contains(@class,'regform__input--year')]");
    private By KeepMeSignedIn_checkbox = new By.ByCssSelector("input.keep-me-signed-in");
    private By KenoOptin_checkbox = new By.ByCssSelector("input#optin1");
    private By PCHOption_checkbox = new By.ByCssSelector("input#optin2");
//    private By submit_container = new By.ByClassName("submit-container");
    private By Continue_btn = new By.ByCssSelector("button#sub_btn");
    private By signin_link = new By.ByClassName("uni-nav-btn");
    //	Changes for Post Game Play token earning story  
    public static String newUserName, newPassword;
    public static String email;
	public static String gmt;
    public static String uni_firstName , uni_lastName, uni_email;
    RegistrationRequestGenerator rs;
    
    public RegistrationPage(WebDriver driver) {
		super(driver);
	}
    public void setDOB(String Month, String Day, String Year) {
        element(Month_dropdown).selectByValue(Month);
        element(Day_dropdown).selectByValue(Day);
        element(Year_dropdown).selectByValue(Year);
    }

    public void setState(String State) {
        element(State_dropdown).selectByValue(State);
    }

    public void setTitle(String title) {
        element(Title_dropdown).waitUntilVisible();    
        element(Title_dropdown).selectByValue(title);
    }

    
    public void properRegistration(String title,
            String firstName,
            String lastName,
            String street,
            String suite,
            String city,
            String state,
            String zip,
            String month,
            String day,
            String year,
            String email,
            String confirmEmail,
            String password,
            String confirmPassword,
            boolean keepSignedIn,
            boolean lottoOptin,
            boolean pchOptin) {
    	        
    	JavascriptExecutor executor=(JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();",  element(Continue_btn));
      setTitle(title);
      element(FirstName_txtbox).sendKeys(firstName);
      element(LastName_txtbox).sendKeys(lastName);
      element(Street_txtbox).sendKeys(street);
      element(Suite_txtbox).sendKeys(suite);
      element(City_txtbox).sendKeys(city);
      setState(state);
      element(Zip_txtbox).sendKeys(zip);
      setDOB(month, day, year);
    //  rand_Email=email;
      //System.out.println(rand_Email);
      element(Email_txtbox).sendKeys(email);
      element(ConfirmEmail_txtbox).sendKeys(confirmEmail);
      element(Pwd_txtbox).sendKeys(password);
      element(ConfirmPwd_txtbox).sendKeys(confirmPassword);
     
     if (keepSignedIn) {
        if (!element(KeepMeSignedIn_checkbox).isSelected())
        element(KeepMeSignedIn_checkbox).click();
     }
     if (pchOptin) {
        if (element(PCHOption_checkbox).isSelected())
        element(PCHOption_checkbox).click();
     }
     if (lottoOptin) {
        if (element(KenoOptin_checkbox).isSelected())
        element(KenoOptin_checkbox).click();
     }

//     element(submit_container).waitUntilEnabled();
     element(Continue_btn).waitUntilEnabled();
     element(Continue_btn).click();
//     executor.executeScript("arguments[0].click();",  element(Continue_btn));
    }
  
    // Changes for Keno
    
    public void magicRegistration() {
    	//String firstName=RandomGenerator.randomAlphabetic(5);
    	//String lastname=RandomGenerator.randomAlphabetic(5);
        //String autoemail = firstName+lastname + RandomGenerator.randnum() + "@pchmail.com";
    	uniqueReg();
    	String password = "pch123";
        properRegistration(
                "Mr.",
              //  firstName,
                uni_firstName,
             //   lastname,
                uni_lastName,
                "101 Channel Drive",
                "",
                "Port Washington",
                "NY",
                "11050",
                "01",
                "01",
                "1991",
             //   autoemail,
                uni_email,
            //    autoemail,
                uni_email,
                password,
                password,
                true, true, true);
        //newUserName = autoemail; //	Changes for Post Game Play token earning story
        newUserName = uni_email;
        newPassword = password;//	Changes for Post Game Play token earning story  
    }

    public String randnum() {
        Random rand =new Random();
        int num = rand.nextInt(9000000) + 1000000;
        return Integer.toString(num);
    }
    
    public void verifyIf_RegistrationPage_Loaded() {
        element(FirstName_txtbox).waitUntilVisible();
        element(Continue_btn).waitUntilVisible();
        Assert.assertEquals("Registration Page not loaded properly", getTitle(),"Register");
    }
    
    public void fill_data_for_MiniReg_User() 
    {

        setTitle("Mr.");
        element(FirstName_txtbox).sendKeys("James");
        element(LastName_txtbox).sendKeys("Bot");
        element(Street_txtbox).sendKeys("101 Channel Drive");
        element(Suite_txtbox).sendKeys("");
        element(City_txtbox).sendKeys("Port Washington");
        setState("NY");
        element(Zip_txtbox).sendKeys("11050");
        setDOB("01", "01", "1991");
        if (!element(KenoOptin_checkbox).isSelected())
        	element(KenoOptin_checkbox).click();
        if (!element(PCHOption_checkbox).isSelected())
        	element(PCHOption_checkbox).click();
        
//        if(!element(submit_container).isEnabled()){
//            System.out.println("Submit button is not enabled. Forcing submit button to be enabled...");
//            evaluateJavascript("document.getElementsByClassName('submit-container')[0].className='submit-container'");
//        }
//        evaluateJavascript("document.getElementsByClassName('submit-container')[0].className='submit-container'");
//        element(submit_container).waitUntilEnabled();
        element(Continue_btn).waitUntilEnabled();
        element(Continue_btn).click();
                
     }

    public void verifyIf_EmailID_unEditable() {
        Assert.assertEquals(element(Email_txtbox).getAttribute("readonly"), "true");
        Assert.assertEquals(element(ConfirmEmail_txtbox).getAttribute("readonly"), "true");

    }

    public void fill_data_for_FacebookwithEmail_Reg_User() 
    {

        setTitle("Mr.");
        element(FirstName_txtbox).sendKeys("James");
        element(LastName_txtbox).sendKeys("Bot");
        element(Street_txtbox).sendKeys("101 Channel Drive");
        element(Suite_txtbox).sendKeys("");
        element(City_txtbox).sendKeys("Port Washington");
        setState("NY");
        element(Zip_txtbox).sendKeys("11050");
        setDOB("01", "01", "1991");
        element(Pwd_txtbox).sendKeys("Pch123");
        element(ConfirmPwd_txtbox).sendKeys("Pch123");

//        if(!element(submit_container).isEnabled()){
//            System.out.println("Submit button is not enabled. Forcing submit button to be enabled...");
//            evaluateJavascript("document.getElementsByClassName('submit-container')[0].className='submit-container'");
//        }
//       evaluateJavascript("document.getElementsByClassName('submit-container')[0].className='submit-container'");
//        element(submit_container).waitUntilEnabled();
        element(Continue_btn).waitUntilEnabled();
        element(Continue_btn).click();

    }
    
    public void verifyUserInRegistrationPage()
    {
    	Assert.assertTrue("User is not in Registration Page", element(Continue_btn).isVisible());	    	
    }
    
  //Changes for UnRecognized User Sign In_Post Game Play story
  	public void signinLinkPresent() 
  	{  			
  		if(element(signin_link).isVisible())  			
  		element(signin_link).click();   			
  		Assert.assertTrue("Sign In Link is not displayed at top right of the Reg page", element(signin_link).isVisible());  			
  	}
  		
  	public void verifyRegPageForMiniRegUser()
  	{
  		//Verify if Registration Page is loaded or not
  		verifyIf_RegistrationPage_Loaded();
  		
  		//Verify that Email and Password fields are not present on Registration Page  		
  		if(!element(Email_txtbox).isCurrentlyVisible())
  			System.out.println("Email Textbox not appearing as expected.");
  		
  		if(!element(ConfirmEmail_txtbox).isCurrentlyVisible())
  			System.out.println("Confirm Email Textbox not appearing as expected.");
  		
  		if(!element(Pwd_txtbox).isCurrentlyVisible())
  			System.out.println("Password Textbox not appearing as expected.");
  		
  		if(!element(ConfirmPwd_txtbox).isCurrentlyVisible())
  			System.out.println("Confirm Password Textbox not appearing as expected.");  	
  	}	
  	
  	public void kenoSiteWithSocialUser()
	{		
        rs = Users.getInstance().createUser("Social");  		  		
        email= rs.getEmail();
		gmt= rs.getGmt();
		String kenoUrl=  PropertiesReader.getInstance().getData("KenoUrl");
		String kenoSocialUrl= kenoUrl+"?em="+email+"&gmt="+gmt;
		
		System.out.println(email);
		System.out.println(gmt);
		System.out.println(kenoSocialUrl);
		getDriver().navigate().refresh();
		
		getDriver().navigate().to(kenoSocialUrl);
		getDriver().manage().window().maximize();
	}
  	
  	public void verifyRegPageForSocialUser()
	{  		
  		String emailTxt = element(Email_txtbox).getTextValue();
  		String confirmEmailTxt = element(ConfirmEmail_txtbox).getTextValue();    		
  		System.out.println("Email Textbox: " + emailTxt);
  		System.out.println("Confirm Email Textbox: " + confirmEmailTxt);
  		
  		//Verify if Email & Confirm Email fields are prefilled or not

  		if (emailTxt.isEmpty())  				
  			Assert.assertFalse("Email field is appearing blank", true);  			
  		else
  			System.out.println("Email field is prefilled - As Expected");  		
		
		if (confirmEmailTxt.isEmpty())
			Assert.assertFalse("Confirm Email field is appearing blank", true);		
  		else
  			System.out.println("Confirm Email field is prefilled - As Expected");  	
  		
		//Verify if Email & Confirm Email fields are non editable or not
		
		element(Email_txtbox).sendKeys("Test");
		if (!emailTxt.equals(element(Email_txtbox).getTextValue()))		
			Assert.assertFalse("Email field is editable", true); 		
		else
			System.out.println("Email field is not editbale - As Expected");
		
		element(ConfirmEmail_txtbox).sendKeys("Test");
		if (!confirmEmailTxt.equals(element(ConfirmEmail_txtbox).getTextValue()))		
			Assert.assertFalse("Confirm Email field is editable", true); 		
		else
			System.out.println("Confirm Email field is not editbale - As Expected");
	}
  	
  	public void createMiniRegUser()
  	{  		
  		rs = Users.getInstance().createUser("MiniReg");
  		email= rs.getEmail();
		gmt= rs.getGmt();
		System.out.println(email);
		System.out.println(gmt);
  	}  	
  	
  	public void uniqueReg()
		{
			uni_firstName = RandomGenerator.randomAlphabetic(5);
			uni_lastName = RandomGenerator.randomAlphabetic(5);
	        try {
			uni_email= "Auto_keno_"+PropertiesReader.getInstance().getBaseConfig("CurrentEnvironment")+"_"+uni_firstName+uni_lastName + randnum() +"@pchmail.com";
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
  	public void createUserWithPasswordUsingRegFoundation() {
		String url, kenoUrl;
		rs = Users.getInstance().createUser("User With Password");
		email= rs.getEmail();
		gmt= rs.getGmt();
		kenoUrl = PropertiesReader.getInstance().getData("KenoUrl");
		url = kenoUrl + "?em=" + rs.getEmail() + "&gmt=" + rs.getGmt();
		System.out.println("URL is: "+url);
		getDriver().navigate().to(url);
		getDriver().manage().window().maximize();
	}
	
	public void createUserWithoutPasswordUsingRegFoundation()
	{
		String url, kenoUrl;
		rs = Users.getInstance().createUser("User Without Password");
		email= rs.getEmail();
		gmt= rs.getGmt();
		kenoUrl = PropertiesReader.getInstance().getData("KenoUrl");
		url = kenoUrl + "?em=" + rs.getEmail() + "&gmt=" + rs.getGmt();
		System.out.println("URL is: "+url);
		getDriver().navigate().to(url);
		getDriver().manage().window().maximize();
	}
}


