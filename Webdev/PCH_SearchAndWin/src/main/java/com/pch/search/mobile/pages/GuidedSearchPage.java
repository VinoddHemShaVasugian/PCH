package com.pch.search.mobile.pages;

import org.openqa.selenium.By;
import org.testng.Assert;

import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class GuidedSearchPage extends Action {
	
	WebHeaderPage headerPage = new WebHeaderPage();
	
	String iframXpath = ".//iframe[@class='guidedSearchFrame']";
	
	public void switchToFrame(){
		driver.switchTo_iframe(iframXpath);
		CustomLogger.log("Switched to iframe");
	}
	
	public HtmlElement logoHolder(){
		return driver.findElement(By.id("logoholder"));
	}
	
	public HtmlElement officialRules(){
		return driver.findElement(By.xpath(".//*[@class='link-group']/a[contains(text(), 'Official Rules')]"));		
	}
	
	public HtmlElement sweepsFacts(){
		return driver.findElement(By.xpath(".//*[@class='link-group']/a[contains(text(), 'Sweeps Facts')]"));
	}
	
	public HtmlElement privacyPolicy(){
		return driver.findElement(By.xpath(".//*[@class='link-group']/a[contains(text(), 'Privacy Policy')]"));
	}
	
	public HtmlElement swLogo(){
		return waitForElementToBeVisible(By.xpath(".//*[@class='gs-logo']/img"));
	}
	
	public HtmlElement topSearch(){
		return driver.findElement(By.xpath(".//*[@class='gs-logo']/following-sibling::div/form/div[contains(@class, 'field-block')]/input"));
	}
	
	public HtmlElement topSearchBox(){
		return driver.findElement(By.id("searchField0"));
	}
	
	public HtmlElement topSearchBtn(){
		return driver.findElement(By.xpath(".//*[@class='gs-logo']/following-sibling::div/form/div[contains(@class, 'field-block')]/input[@type='submit']"));
	}
	
	public HtmlElement banner(){
		return driver.findElement(By.xpath(".//*[@class='gs-ad']/a/img"));
	}
	
	public HtmlElement giveAwayDetails(){
		return driver.findElement(By.xpath(".//*[@class='giveaway-details']"));
	}
	
	public HtmlElement introText(){
		return driver.findElement(By.xpath(".//*[@class='row']/div/h2"));
	}
	
	public HtmlElement menu(){
		return driver.findElement(By.xpath(".//*[@class='burger']"));
	}
	
	public HtmlElement home(){
		return driver.findElement(By.xpath(".//*[contains(text(), 'Home')]"));
	}
	
	/*
	 * Xpath of terms with layouts - 1,3,4,5,6,7
	 * v1,3,4,5,6,7.//*[contains(@class, 'gs-v')]/div[@class='row']/div/ul/li
	 * v8,9,10 - .//*[contains(@class, 'gs-v')]/div[@class='row']/div/a
	 * V2 - .//*[contains(@class, 'gs-v')]/div[@class='row']/div/section/a
	*/
	public String terms(String layout){
		
		if(layout.contains("2")){
			return ".//*[contains(@class, 'gs-v')]/div[@class='row']/div/section/a";
		}else if(layout.contains("8")||layout.contains("9")||layout.contains("10")){
			return ".//*[contains(@class, 'gs-v')]/div[@class='row']/div/a";
		}else{
			return ".//*[contains(@class, 'gs-v')]/div[@class='row']/div/ul/li";
		}
		
	}
	
	public HtmlElement bottomSearch(){
		return driver.findElement(By.xpath(".//*[@class='col-xs-12 searchbox-bottom']/div[@class='search-box']/form/div/input"));
	}
	
	public HtmlElement bottomSearchBox(){
		return driver.findElement(By.xpath(".//*[@class='col-xs-12 searchbox-bottom']/div[@class='search-box']/form/div/input[@type='search']"));
	}
	
	public HtmlElement bottomSearchBtn(){
		return driver.findElement(By.xpath(".//*[@class='col-xs-12 searchbox-bottom']/div[@class='search-box']/form/div/input[@type='submit']"));
	}
	
	public HtmlElement legalnotice(){
		return driver.findElement(By.id("legal_notice"));
	}
	
	public HtmlElement eTruste(){
		return driver.findElement(By.xpath(".//*[@class='truste']/div/a/img"));
	}
	
	public HtmlElement BBB(){
		return driver.findElement(By.xpath(".//*[@id='truste_bbb']/div[2]/a"));
	}
	
	public void verifyBanner(){
		/*
		 * 1. enable 2. Disable 3. click 4. url
		 * 5. invalid openxid
		*/
		CustomLogger.log("Verifying the logo on Search&Win page. . .");
		if(driver.getCountOfElementsWithXPath(".//*[@class='gs-logo']/img")!=0){
			driver.waitForBrowserToLoadCompletely();
			Assert.assertTrue(swLogo().isDisplayed(), "Didn't found Search&Win logo. . .");
			CustomLogger.log("We are seeing Banner on S&W page. . .");
		}else{
			CustomLogger.log("Didn't found Search&Win logo. . .");
		}
		
		
			
			
		
			CustomLogger.log("Should not see the Logo. . .");
			Assert.assertTrue(driver.getCountOfElementsWithXPath(".//*[@class='gs-logo']/img")==0, "We are seeing logo on Search&Win home page. . .");
		
	}
	
	/*
	 * Idea behind it is to create a single method which work for all GS's
	 * 
	 * @parameters expected
	 * # of ele - 6
	 * Title : Not sure what to search for? Tap below for some great options!
	 * Logo - 				E(enabled)
	 * Search bar -			E
	 * Banner - 			E
	*/
	public void verifyGS(String layout, int eleCount, String Logo, String SearchBar, String banner, String introText ){
		
		driver.switchTo_iframe(iframXpath);
		CustomLogger.log("Switched to iframe");
		
		//Verifying logo
		if(Logo.equalsIgnoreCase("e")){
			CustomLogger.log("Verifying the logo on Search&Win page. . .");
			Assert.assertTrue(swLogo().isDisplayed(), "Didn't found Search&Win logo. . .");
		}else{
			CustomLogger.log("Should not see the Logo. . .");
			Assert.assertTrue(driver.getCountOfElementsWithXPath(".//*[@class='gs-logo']/img")==0, "We are seeing logo on Search&Win home page. . .");
		}
		
		//Verifying searchBar
		if(SearchBar.equalsIgnoreCase("e")){
			CustomLogger.log("Verifying Search box on Search&Win page. . .");
			Assert.assertTrue(topSearch().isDisplayed(), "Didn't find Search-Box on Swaech&Win page. . . ");
			Assert.assertTrue(bottomSearch().isDisplayed(), "Didn't find Search-Box on Swaech&Win page. . . ");
		}else{
			CustomLogger.log("Should not see Search box on Search&Win page. . .");
			Assert.assertTrue(driver.getCountOfElementsWithXPath(".//*[@class='gs-logo']/following-sibling::div/form/div[contains(@class, 'field-block')]/input")==0, "We are seeing search box on S&W page. . .");
			Assert.assertTrue(driver.getCountOfElementsWithXPath(".//*[@class='col-xs-12 searchbox-bottom']/div[@class='search-box']/form/div/input")==0, "We are seeing search box on S&W page. . .");
		}
		
		//validating intro text
		CustomLogger.log("Validating Intro text");
		Assert.assertTrue(introText().getText().trim().equalsIgnoreCase(introText), "Expected intro text is mismatched with Actual. . .");
		
		//Validating layout count
		CustomLogger.log("Validating layout count. . .");
		Assert.assertTrue(driver.getCountOfElementsWithXPath(terms(layout))==eleCount);
		
		//Validating Banner
		if(banner.equalsIgnoreCase("e")){
			CustomLogger.log("Validating Banner & Give away details");
			Assert.assertTrue(banner().isDisplayed(), "Didn't find banner on S&W homepage. . .");
			CustomLogger.log("Validating Giveaway Details. . .");
			Assert.assertTrue(giveAwayDetails().isDisplayed(), "Didn't find PCH Giveaway Details on S&W page. . .");
		}else{
			CustomLogger.log("Should not see Search box on Search&Win page. . .");
			Assert.assertTrue(driver.getCountOfElementsWithXPath(".//*[@class='gs-ad']")==0, "We are seeing banner on S&W page. . .");
			Assert.assertTrue(driver.getCountOfElementsWithXPath(".//*[@class='giveaway-details']")==0, "We are seeing banner on S&W page. . .");
		}
		
		//validating Footer part - BBB, eTrute, Legal Notice
		Assert.assertTrue(BBB().isDisplayed(), "Didn't found BBB logo on S&W home page. . .");
		Assert.assertTrue(eTruste().isDisplayed(), "Didn't found eTruste logo on S&W home page. . .");
		Assert.assertTrue(legalnotice().isDisplayed(), "Didn't found legal Notice on S&W home page. . .");
	}
	
	public void validateGs(String layout){
		driver.waitForBrowserToLoadCompletely();
		//driver.switchTo().frame(0);
		driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']");
		Common.sleepFor(3000);
		//validating intro text
		CustomLogger.log("Validating Intro text to determine the correct layout.");
		Assert.assertTrue(introText().getText().contains(layout), "Expected layout is not displayed.");
		CustomLogger.log("Correct layout "+ layout +" for Guided Search is displayed.");
		CustomLogger.log("=================================================================");
		
	}
	
	public void validateLogoPresent(){
		CustomLogger.log("Validating logo is present on guided search page");
		//driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']");
		Assert.assertTrue(swLogo()!=null,"Logo does not exists on guided search page");
		CustomLogger.log("Logo is present on guided search page");
		driver.switchTo().defaultContent();
		CustomLogger.log("=================================================================");
	}
	
	public void validateNoLogoPresent(){	
		boolean isLogoNotExists=false;
		CustomLogger.log("Validating logo is not present on guided search page");
		//driver.switchTo_iframe("//iframe[@class='guidedSearchFrame']");
		try{
			driver.waitForElementToBeDisappear(By.xpath(".//*[@class='gs-logo']/img"), timeUnit);
			isLogoNotExists=true;
		}catch(Exception e){
			CustomLogger.log("Got: "+e.getClass().getSimpleName());
		}
		Assert.assertTrue(isLogoNotExists, "Logo is exists on guided search page");
		CustomLogger.log("Logo is not present on guided search page");
		driver.switchTo().defaultContent();
		CustomLogger.log("=================================================================");
	}
	
	public boolean isBannerExists(){
		boolean isBannerPresent = true;
		CustomLogger.log("Validating the presence of Banner is S&W GS page");
		driver.waitForBrowserToLoadCompletely();
		//driver.switchTo_iframe(iframXpath);
		if(!(driver.getCountOfElementsWithXPath(".//*[@class='gs-ad']")==0)){
			CustomLogger.log("Found Banner on S&W page. . .");
			return isBannerPresent;
		}else{
			isBannerPresent = false;
			CustomLogger.log("Didn't find Banner on S&W page. . .");
			return isBannerPresent;
		}
	}
	
	public void getTitleOfBannerUrl(String expectedTitleOfPage){
		if(isBannerExists()){
			CustomLogger.log("clicking on banner to validate URL");
			banner().click();
			driver.waitForBrowserToLoadCompletely();
			Assert.assertTrue(driver.getTitle().contains(expectedTitleOfPage), "We are not seeing "+expectedTitleOfPage+" as title of page.");
			CustomLogger.log("You are in "+expectedTitleOfPage+" page");
		}else{
			CustomLogger.log("Didn't found Banner, hope u missed to enable Banner..pls do verify. . .");
		}
	}
	
	public boolean isGiveawayExists(){
		boolean isGiveawayPresent = true;
		//driver.switchTo_iframe(iframXpath);
		if(!(driver.getCountOfElementsWithXPath(".//*[@class='giveaway-details']")==0))
			return isGiveawayPresent;
		else{
			isGiveawayPresent = false;
			CustomLogger.log("Didn't found PchGiveaway Details on Banner. . .");
			return isGiveawayPresent;
		}
	}
	
	public void getTitleOfGiveawayUrl(String expectedTitleOfPage){
		if(isBannerExists()){
			
			String mainWindow = driver.getWindowHandle();
			
			CustomLogger.log("clicking on PCH Giveaway Details to validate URL");
			giveAwayDetails().click();
			driver.waitForBrowserToLoadCompletely();
			if(driver.getWindowHandles().size()>1){
				
				driver.switchToChildWindow(mainWindow);
				CustomLogger.log("Validating the URL of Give away.");
				driver.waitForBrowserToLoadCompletely();
				Assert.assertTrue(driver.getTitle().contains(expectedTitleOfPage), "We are not seeing "+expectedTitleOfPage+" as title of page.");
				CustomLogger.log("You are in "+expectedTitleOfPage+" page");
				driver.close();
				driver.switchTo().window(mainWindow);
				
			}else{
				CustomLogger.log("Validating the URL of Give away.");
				Assert.assertTrue(driver.getTitle().contains(expectedTitleOfPage), "We are not seeing "+expectedTitleOfPage+" as title of page.");
				CustomLogger.log("You are in "+expectedTitleOfPage+" page");
			}
		}else{
			CustomLogger.log("Didn't found Giveaway Details, hope u missed to enable Banner..pls do verify. . .");
		}
	}
	
	public void validateSearchBox(String exist) {

		if (exist.equalsIgnoreCase("Y")) {
			CustomLogger.log("Verifying if Search box exist. . .");
			Assert.assertTrue(topSearchBox().isDisplayed(),
					"Didn't find Search-Box on Swaech&Win page. . . ");
			CustomLogger.log("Top Search box exists.");
			/*Assert.assertTrue(bottomSearchBox().isDisplayed(),
					"Didn't find Search-Box on Swaech&Win page. . . ");
			CustomLogger.log("Bottom Search box exists.");*/
		} else {
			CustomLogger.log("Verifying if Search box doesn't exist. . .");
			Assert.assertTrue(
					driver.getCountOfElementsWithXPath(".//*[@class='gs-logo']/following-sibling::div/form/div[contains(@class, 'field-block')]/input") == 0,
					"Top Search box exists. . .");
			CustomLogger.log("Top Search box is not present.");
			/*Assert.assertTrue(
					driver.getCountOfElementsWithXPath(".//*[@class='col-xs-12 searchbox-bottom']/div[@class='search-box']/form/div/input") == 0,
					"Bottom Search box exists. . .");
			CustomLogger.log("Bottom Search box is not present.");*/
		}

	}
	
	public void searchUsingTopSearchBox(String term) {
		topSearchBox().clear();
		topSearchBox().sendKeys(term);
		topSearchBox().submit();
		driver.waitForBrowserToLoadCompletely();
		if(driver.getCurrentUrl().contains("/search/?q="+term)){
			CustomLogger.log("Searched for term '"+term+"' from top search box and landed successfully on the SERP.");
			Assert.assertTrue(driver.getCurrentUrl().contains("/search/?q="+term), "Didn't land on the SERP.");
		}else if (driver.getCurrentUrl().contains("/search?q="+term)){
			CustomLogger.log("Searched for term '"+term+"' from top search box and landed successfully on the SERP.");
			Assert.assertTrue(driver.getCurrentUrl().contains("/search?q="+term), "Didn't land on the SERP.");
		}
		
	}
	
	public void searchUsingBottomSearchBox(String term) {
		
		driver.switchTo().frame(0);
		bottomSearchBox().sendKeys(term);
		bottomSearchBox().submit();
		driver.waitForBrowserToLoadCompletely();
		if(driver.getCurrentUrl().contains("/search/?q="+term)){
			CustomLogger.log("Searched for term '"+term+"' from bottom search box and landed successfully on the SERP.");
		}
		Assert.assertTrue(driver.getCurrentUrl().contains("/search/?q="+term), "Didn't land on the SERP.");
		
	}
	

	public void clickTerm(String term){
		CustomLogger.log("clicking the term: "+term);
		driver.switchTo().frame(0);		
		waitForElementToBeVisible(By.xpath(String.format("//*[contains(text(),'%s')]",term))).click();
		
	}
	
	public void validateTermsCount(String layout, int count){
		
		driver.switchTo_iframe(iframXpath);
		CustomLogger.log("validating count of elements for layout - "+layout);
		int eleCount = driver.getCountOfElementsWithXPath(terms(layout));
		CustomLogger.log("Actual Count of terms on GS_page : "+eleCount);
		CustomLogger.log("Expected Count of terms on GS_page : "+count);
		Assert.assertTrue(eleCount==count, "expected terms count doesnt match eith Actual term count"+eleCount+":"+count);
		CustomLogger.log("Validated No of elements for Layout - "+layout);
		
	}
	

	public void clickBBBandVerifyTitle(){
		
		String mainWindow = driver.getWindowHandle();
		if(driver.getCurrentUrl().contains("guidedsearch")){
			//driver.switchTo_iframe(iframXpath);
		}
		CustomLogger.log("Clicking on BBB logo.");
		BBB().scrollDownAndClick();
		
		if(driver.getWindowHandles().size()>1){
			
			driver.waitForBrowserToLoadCompletely();
			CustomLogger.log("As BBB opened in a new window shifting the driver to new window");
			driver.switchToChildWindow(mainWindow);
			CustomLogger.log("Validating the URL of BBB.");
			driver.waitForBrowserToLoadCompletely();
			Assert.assertTrue(driver.getTitle().contains("BBB Business Profile | Publishers Clearing House LLC"));
			CustomLogger.log("Verified BBB logo and URL on GS_Page.");
			driver.close();
			driver.switchTo().window(mainWindow);
			
		}else{
			driver.waitForBrowserToLoadCompletely();
			CustomLogger.log("Validating the URL of BBB.");
			Assert.assertTrue(driver.getTitle().contains("NY - BBB Business Review - BBB serving Metropolitan New York, "), "We are seeing : "+driver.getCurrentUrl());
			CustomLogger.log("Verified BBB logo and URL on GS_Page.");
		}
		
	}
	
	public void clickeTrusteandVerifyTitle(){
		
		String mainWindow = driver.getWindowHandle();
		if(driver.getCurrentUrl().contains("guidedsearch")){
			//driver.switchTo_iframe(iframXpath);
		}
		CustomLogger.log("Clicking on eTruste logo.");
		eTruste().scrollDownAndClick();
		
		if(driver.getWindowHandles().size()>1){
			
			driver.waitForBrowserToLoadCompletely();
			CustomLogger.log("As Truste opened in a new window shifting the driver to new window");
			driver.switchToChildWindow(mainWindow);
			CustomLogger.log("Validating the URL of Truste.");
			driver.waitForBrowserToLoadCompletely();
			Assert.assertTrue(driver.getTitle().contains("online privacy and online safety are certified by TRUSTe"), "We are seeing : "+driver.getCurrentUrl());
			CustomLogger.log("Verified Truste logo and URL on GS_Page.");
			driver.close();
			driver.switchTo().window(mainWindow);
			
		}else{
			driver.waitForBrowserToLoadCompletely();
			CustomLogger.log("Validating the URL of eTruste.");
			Assert.assertTrue(driver.getTitle().contains("online privacy and online safety are certified by TRUSTe"), "We are seeing : "+driver.getCurrentUrl());
			CustomLogger.log("Verified eTruste logo and URL on GS_Page.");
		}
		
	}
	
	public boolean validateGSURL(String GS){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.waitForBrowserToLoadCompletely();
		CustomLogger.log("Vlidating GS url, with expected GS as - "+GS);
		return driver.getCurrentUrl().contains(GS);
	}

	public boolean validateBackGroundimage(String expectedOpenx){
		
		driver.switchTo_iframe(iframXpath);
		String attribute = banner().getAttribute("src");
		return attribute.contains(expectedOpenx);
		
	}

	public void gotoHomePage(){
		CustomLogger.log("Navigating to Menu");
		menu().click();
		CustomLogger.log("Clicking on Home button");
		home().click();
		driver.waitForBrowserToLoadCompletely();
		//Assert.assertTrue(driver.getTitle().contains("GuidedSearch"));
		CustomLogger.log("You are in  : "+driver.getTitle()+" page");
	}
	

}
