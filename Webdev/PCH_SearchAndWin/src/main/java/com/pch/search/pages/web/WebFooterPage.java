package com.pch.search.pages.web;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;


public class WebFooterPage extends Action {
	String appUrl;
	String mainWindow;

	/*public WebFooterPage(final WebDriver driver, final Environment env) {
		super(driver, env);
	}*/

	public HtmlElement middleFooter(){
		return driver.findElement(By.xpath("//div[@class='middle']"));
	}

	@SuppressWarnings("unused")
	private HtmlElement homeLink(){
		try{
			return driver.findElement(By.xpath("//div[@class='footer']//a[text()='Home']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement aboutPchLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='About PCH']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement privacyPolicyLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Privacy Policy']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement termsOfUseLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Terms of Use']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement customerServiceLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Customer Service']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement advertiseWithUsLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Advertise With Us']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	public void Help(){
		String helpUrl = "http://search." + Environment.getEnvironment() + ".pch.com/help";
		driver.get(helpUrl);
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("EC Url = " + helpUrl);
		CustomLogger.log("Reloaded the page with the Url.");
	}
	
	public void moreWaysToWin(){
		String winUrl = "http://search." + Environment.getEnvironment() + ".pch.com/morewaystowin";
		driver.get(winUrl);
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("EC Url = " + winUrl);
		CustomLogger.log("Reloaded the page with the Url.");
	}
	
	public HtmlElement recentWinnersLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Recent Winners']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement dosAndDontsLink(){
		try{
			return driver.findElement(By.xpath("//div[@class='main_wrapper']//div[3]//ul//li[3]//a"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement blogLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Blog']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement facebookLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Facebook']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement twitterLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Twitter']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement contestDetailsLink(){
		try{
			return driver.findElement(By.xpath("descendant::a[text()='Contest Details']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	private HtmlElement copyright(){
		try{
			return driver.findElement(By.className("copy-text"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);
			return null;
		}
	}

	public HtmlElement logoBBB(){
		return	driver.findElement(By.xpath("//a[@title='Publishers Clearing House LLC BBB Business Review']"));
	}

	public HtmlElement logoTrustE(){
		return driver.findElement(By.xpath("//img[contains(@alt,'TRUSTe online privacy certification')]"));
	}

	public boolean isBBBlogoPresent(){
		try{
			return logoBBB().isDisplayed();
		}catch(NoSuchElementException e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean istrustElogoPresent(){
		try{
			return logoTrustE().isDisplayed();
		}catch(NoSuchElementException e){
			e.printStackTrace();
			return false;
		}
	}

	public HtmlElement guidedSearchSection(){
		HtmlElement element=null;
		try{
			element= driver.findElement(By.xpath(".//section[@id='guided-search']"));
		}catch(WebDriverException wde){
			CustomLogger.logException(wde);;

		}
		return element;
	}

	public void clickOnBBBlogo(){
		logoBBB().click();
	}

	public void clickOnTrustElogo(){
		logoTrustE().click();
	}
	//

	public String getcopyrightText(){
		return copyright().getText();
	}

	public Object getAboutPchLink(){
		return aboutPchLink();
	}

	public Object getPrivacyPolicyLink(){
		return privacyPolicyLink();
	}

	public Object getTermsOfUseLink(){
		return termsOfUseLink();
	}

	public Object getCustomerServiceLink(){
		return customerServiceLink();
	}

	public Object getRecentWinnersLink(){
		return recentWinnersLink();
	}

	public Object getDosAndDontsLink(){
		return dosAndDontsLink();
	}

	public Object getBlogLink(){
		return blogLink();
	}

	public Object getFacebookLink(){
		return facebookLink();
	}

	public Object getTwitterLink(){
		return twitterLink();
	}

	public Object getContestDetailsLink(){
		return contestDetailsLink();
	}

	public Object getadvertiseWithUsLink(){
		return advertiseWithUsLink();
	}
	/**
	 * This function is to validate URL's for the Footer Links
	 *@return: True if URL's are as expected , False otherwise
	 * */
	public boolean verifyFooterLinksURL(){
		boolean isFooterLinkVerified=false;
		appUrl = Common.getAppUrl(Environment.getAppName());
		try{

			driver.waitForBrowserToLoadCompletely();
			//verify URL's for footer links
			verifyLinkURL(recentWinnersLink(),appUrl+"/winners");			
			verifyLinkURL(dosAndDontsLink(),appUrl+"/dosdonts");			
			verifyLinkURL(blogLink(),"http://blog.pch.com/pchsearchandwin/");				
			verifyLinkURL(facebookLink(),"https://www.facebook.com/PCHSearchAndWin");			
			verifyLinkURL(twitterLink(),"https://twitter.com/search?q=pchsearchandwin");			
			verifyLinkURL(aboutPchLink(),"http://info.pch.com/category/about");			
			verifyLinkURL(contestDetailsLink(),appUrl+"/aboutsuperprize#disclosure");			
			verifyLinkURL(customerServiceLink(),"http://pchsearch.custhelp.com/");	
			/*homeLink().click();
			Common.sleepFor(3000);*/
			verifyLinkURL(privacyPolicyLink(),"http://privacy.pch.com/en-us/");			 
			verifyLinkURL(termsOfUseLink(),"http://privacy.pch.com/en-us/searchTOU");			
			//verifyLinkURL(advertiseWithUsLink(),"http://liquid.pch.com/");
			verifyLinkURL(advertiseWithUsLink(),"http://media.pch.com/");

			driver.get(Common.getAppUrl(Environment.getAppName()));
			isFooterLinkVerified=true;
		}catch (Exception e) {
			CustomLogger.logException(e);
		}
		return isFooterLinkVerified;
	}

	/**
	 * This function is to validate the URL for the given link
	 * @param: Link for which validating the url
	 * @param: Expected URL
	 * */
	public void verifyLinkURL(HtmlElement link,String expectedURL){
		try{
			mainWindow=driver.getWindowHandle();
			String LinkURL;
			link.click();	
			Common.sleepFor(5000);
			if(driver.getWindowHandles().size()>1){
				driver.switchToChildWindow(mainWindow);					
				LinkURL = driver.getCurrentUrl();
				CustomLogger.log("Verifying the URL of link - "+LinkURL);
				Assert.assertTrue(LinkURL.contains(expectedURL));
				driver.close();
				driver.switchTo().window(mainWindow);
			}	else{					
				LinkURL = driver.getCurrentUrl();
				CustomLogger.log("Verifying the URL of link - "+LinkURL);
				Assert.assertTrue(LinkURL.contains(expectedURL));
			}
		}catch(WebDriverException wde){
			CustomLogger.log(wde.toString());
		}
	}
}
