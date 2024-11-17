package com.pch.search.iwe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;

import com.pch.search.utilities.Common;
import com.pch.search.utilities.HtmlElement;

public class PrizesPage extends IWEBasePage {

	String createPrizePage=appURL+"#prize/create";
	public HtmlElement prizeValue(){
		return driver.findElement(By.name("prizeValue"));
	}
	public HtmlElement setName(){
		return driver.findElement(By.name("name"));
	}
	public HtmlElement setDesc(){
		return driver.findElement(By.name("description"));
	}
	public HtmlElement setCode(){
		return driver.findElement(By.name("code"));
	}
	public HtmlElement setPrizeType(){
		return driver.findElement(By.name("prizeTypeData.id"));
	}
	public HtmlElement setPrizeValue(){
		return driver.findElement(By.name("prizeValue"));
	}
	public HtmlElement setNumberOfPrize(){
		return driver.findElement(By.name("totalNumberOfPrize"));
	}
	public HtmlElement setGiveaway(){
		return driver.findElement(By.name("giveawayData.id"));
	}
	public HtmlElement setComment(){
		return driver.findElement(By.name("comment"));
	}
	public HtmlElement saveBtn(){
		return driver.findElement(By.xpath("//*[contains(text(), 'Save')]"));
	}


	/**
	 * To search Giveaways
	 * */
	public boolean searchPrize(String prize){
		boolean isFound=false;	
		try{
			search(prize);
			driver.findElement(By.xpath(String.format("//font[text()='%s']",prize)));
			isFound=true;
		}catch(Exception e){
			//do nothing
		}
		return isFound;

	}

	public void createOrEditPrize(String cashPrizeName
			, String code
			, String prizeType
			, String prizeValue
			, String totalNumberOfPrize
			, String monetaryGiveawayName
			){
		
		if(searchPrize(cashPrizeName)){
		
			//if Prize already present...edit prize end date
	//		driver.findElement(By.xpath(String.format("//font[text()='%s']",prizeValue))).click();
	//		prizeValue().sendKeys(prizeValue);

		}else{
			createPrize(cashPrizeName,code,prizeType,prizeValue,totalNumberOfPrize,monetaryGiveawayName);
		}

	}

	public void createPrize(String cashPrizeName
			, String code
			, String prizeType
			, String prizeValue
			, String totalNumberOfPrize
			, String monetaryGiveawayName
			){
		driver.get(createPrizePage);
		saveCashPrize(cashPrizeName,code,prizeType,prizeValue,totalNumberOfPrize,monetaryGiveawayName);

	}

	/**
	 * @param cashPrizeName
	 * @param code
	 * @param prizeType
	 * @param prizeValue
	 * @param totalNumberOfPrize
	 * @param monetaryGiveawayName	
	 * @see This method is used for save cash prize
	 */
	public void saveCashPrize(String cashPrizeName
			, String code
			, String prizeType
			, String prizeValue
			, String totalNumberOfPrize
			, String monetaryGiveawayName
			){

		setName().sendKeys(cashPrizeName);
		setCode().sendKeys(code);
		setPrizeType().sendKeys(prizeType);
		Common.sleepFor(3000);
		selectExactItemFromDropdown(prizeType);
		//getSecondPrizeTypeInDropdown().click();
		setPrizeValue().sendKeys(prizeValue);
		setNumberOfPrize().sendKeys(totalNumberOfPrize);
		setGiveaway().sendKeys(monetaryGiveawayName);
		Common.sleepFor(3000);
		//getFirstGiveawayInDropdown().click();
		selectItemFromDropdown(monetaryGiveawayName,0);
		//	getImageUrl().sendKeys(imageUrl);
		saveBtn().click();
		Common.sleepFor(5000);
	}
	
	/**
	 * @return String
	 * @see This method is used to get four digit random number for prize code
	 */
	public String getFourDigitRandomNumber(){
		String result = "";
		List<Integer> numbers = new ArrayList<Integer>();
		for(int i = 0; i < 10; i++)
			numbers.add(i);
		Collections.shuffle(numbers);
		for(int i = 0; i < 4; i++)
			result += numbers.get(i).toString();
		return result;
	}
}
