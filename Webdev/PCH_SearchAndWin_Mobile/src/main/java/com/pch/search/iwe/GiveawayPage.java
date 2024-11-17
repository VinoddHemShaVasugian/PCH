package com.pch.search.iwe;
import org.openqa.selenium.By;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;

public class GiveawayPage extends IWEBasePage {

	
	

	public HtmlElement getName(){
		return driver.findElement(By.name("name"));
	}
	public HtmlElement getEndDate(){
		return driver.findElement(By.name("endDate"));
	}
	public HtmlElement getEstOddsOfWinning(){
		return driver.findElement(By.name("estOddsOfWinning"));
	}
	public HtmlElement getGiveawayTypeDropdown(){
		return driver.findElement(By.name("giveawayType.id"));
	}
	public HtmlElement getMonetary(){
		return driver.findElement(By.xpath("//*[contains(text(), 'Monetary')][1]"));
	}
	
	public HtmlElement getSaveButton(){
		return driver.findElement(By.xpath("//*[contains(text(), 'Save')]"));
	}
	public HtmlElement setComment(){
		return driver.findElement(By.name("comment"));
	}
	/**
	 * To search Giveaways
	 * */
	public boolean searchGiveaways(String giveawayName){
		boolean isFound=false;	
		try{
			search(giveawayName);
			driver.findElement(By.xpath(String.format("//font[text()='%s']",giveawayName)));
			isFound=true;
		}catch(Exception e){
			CustomLogger.log(e.toString());
		}
		return isFound;

	}

	public void createOrEditGiveaways(String giveawayName,String estOddsOfWinning,String action){
		if(searchGiveaways(giveawayName)){
			//if giveaway already present...change end date
			driver.findElement(By.xpath(String.format("//font[text()='%s']",giveawayName))).click();
			getEndDate().clear();
			getEndDate().sendKeys(getNextMonthDate());
			Common.sleepFor(3000);
			setComment().sendKeys("updated");
			getSaveButton().click();
			closeServerWarning(action);
			Common.sleepFor(5000);
		}else{
			createGiveaway(giveawayName,estOddsOfWinning);
		}

	}

	public void saveMonetaryGiveaway(String giveawayName, String estOddsOfWinning)	{

		getName().sendKeys(giveawayName);
		getEndDate().clear();
		getEndDate().sendKeys(getNextMonthDate());
		getEstOddsOfWinning().sendKeys(estOddsOfWinning);
		getGiveawayTypeDropdown().click();
		getMonetary().click();
	//	getTimeAllowedToClaim().sendKeys(timeAllowedToClaim);
	//	getTimeAllowedToClaimDropdown().click();
	//	getHours().click();
	//	getGiveawayBudget().sendKeys(giveawayBudget);
	//	getTotalNumberOfPrizes().sendKeys(totalNumberOfPrizes);
		getSaveButton().click();
		Common.sleepFor(5000);
	}
	
	public void createGiveaway(String giveawayName, String estOddsOfWinning){
		driver.get("https://iwe."+Environment.getEnvironment()+".pch.com/iwe/#giveaway/create");
		Common.sleepFor(5000);
		saveMonetaryGiveaway(giveawayName,estOddsOfWinning);
	}
	
}


