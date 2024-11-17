package com.pch.search.pages.web;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

import com.pch.search.bean.TokenHistoryBean;
import com.pch.search.utilities.Action;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class TokenHistoryPage extends Action {
	

	/* public TokenHistoryPage() {
	        super(driver);
	    }*/
	 
	public int getTokenBalance(){
		String tokenBalance =  driver.findElement(By.className("tokenBalance")).getAttribute("textContent");
		return Integer.parseInt(tokenBalance);
	}

	public int getAllTimetokens(){
		String tokenBalance =  driver.findElement(By.className("token_credit_total")).getAttribute("textContent");
		return Integer.parseInt(tokenBalance);
	}

	public int getTotalTokensUsed(){
		String tokenBalance =  driver.findElement(By.className("token_debit_total")).getAttribute("textContent");
		return Integer.parseInt(tokenBalance);
	}

	public void clickRedeemTokens(){
		driver.findElement(By.linkText("Redeem Tokens")).click();
	}

	public void goToNextPage(){
		String xpath = "//ul[@class='paginator']/li[@class='next']/a";
		if(driver.getCountOfElementsWithXPath(xpath)==0){
			CustomLogger.log("Already on Last Page !");
			return;
		}
		driver.findElement(By.xpath(xpath)).click();
		Common.sleepFor(2000);
	}

	public void goToPreviousPage(){
		String xpath = "//ul[@class='paginator']/li[@class='prev']/a";
		if(driver.getCountOfElementsWithXPath(xpath)==0){
			CustomLogger.log("Already on First Page !");
			return;
		}
		driver.findElement(By.xpath(xpath)).click();
		Common.sleepFor(2000);
	}

	private HtmlElement getHistoryTable(){
		HtmlElement historyTable=driver.findElement(By.id("historyTable"));
		return historyTable;
	}
	private int getColumnIndexOfProperty(String propertyName){			
		List<String> columnNames = getColumnNames();
		int i=0;
		for(String columnName:columnNames){
			if(columnName.toUpperCase().equals(propertyName.toUpperCase())){
				return i;
			}
			i++;
		}
		//If it reaches here means column is not present.
		return -1;
	}

	private List<String> getColumnNames(){
		List<HtmlElement> headerCells = getHistoryTable().findElements(By.xpath("thead/tr/th"));
		List<String> columnNames = new ArrayList<String>();
		for(HtmlElement headerCell:headerCells){
			columnNames.add(headerCell.getAttribute("textContent"));
		}
		CustomLogger.log("Columns In token History table "+columnNames);
		return columnNames;
	}
	
	
	public List<String> getContentOfVisibleRows(){
		List<HtmlElement> headerCells = driver.findElements(By.xpath(".//*[@id='th_list']/article/div[2]/div[@class='th_desc']"));
		
		List<String> desc = new ArrayList<String>();
		
		if(!(headerCells.size()==0)){
			
			for(int i=1; i<=3; i++){
				String activity = driver.findElement(By.xpath(".//*[@id='th_list']/article["+i+"]/div[2]/div[@class='th_desc']")).getText();
				CustomLogger.log(activity);
				desc.add(activity);
			}
			return desc;
		}
		return null;
	}
	
	public String tokensAtRegistration(){
		
		List<String> desc= getContentOfVisibleRows();
		String regDesc = "User Registration on PCHSearch&Win";
		String descXpath = ".//*[@id='th_list']/article/div/div[contains(text(),'User Registration on PCHSearch&Win')]";
		
		for(String temp : desc) {
			if(temp.contains(regDesc)){
				return driver.findElement(By.xpath(descXpath+"/following-sibling::div[@class='th_balance']/div[2]")).getText();
			}
		}
		return null;
	}

	public List<TokenHistoryBean> getTokenHistoryByField(String propertyName,String propertyValue){
		int columnIndex = getColumnIndexOfProperty(propertyName);
		CustomLogger.log(String.format("Column index of %s is %d", propertyName,columnIndex));
		String xpath = String.format("tbody/tr[td[%d][text()='%s']]",columnIndex+1,propertyValue);
		HtmlElement tokenHistoryTable=getHistoryTable();		
		
		if(tokenHistoryTable.getCountOfElementsWithXPath(xpath)==0){			
			xpath = String.format("tbody/tr[td[%d][*[text()='%s']]]",columnIndex+1,propertyValue);			
		}
		List<HtmlElement> targetRows = tokenHistoryTable.findElements(By.xpath(xpath));	
		
		List<TokenHistoryBean> tokenHistoryList = new ArrayList<TokenHistoryBean>();

		for(HtmlElement targetRow:targetRows)
			tokenHistoryList.add(generateTokenHistoryBean(targetRow));
		
		return tokenHistoryList;
	}
	
	public TokenHistoryBean getTokenHistoryAtRow(int row){		
		String xpath = String.format("tbody/tr[%d]",row);
		HtmlElement tokenHistoryTable=getHistoryTable();
		HtmlElement targetRow = tokenHistoryTable.findElement(By.xpath(xpath));
		return generateTokenHistoryBean(targetRow);
	}

	public List<TokenHistoryBean> getAllTokenHistories(){	  
		String xpath = "tbody/tr";
		HtmlElement tokenHistoryTable=getHistoryTable();
		List<HtmlElement> targetRows = tokenHistoryTable.findElements(By.xpath(xpath));
		List<TokenHistoryBean> tokenHistoryList = new ArrayList<TokenHistoryBean>();

		for(HtmlElement targetRow:targetRows)
			tokenHistoryList.add(generateTokenHistoryBean(targetRow));
		
		return tokenHistoryList;
	}

	private TokenHistoryBean generateTokenHistoryBean(HtmlElement targetRow){
		TokenHistoryBean bean = new TokenHistoryBean();
		CustomLogger.log("Token History row (under read) data : "+targetRow.getAttribute("textContent"));
		List<String> columnNames = getColumnNames();
		
		List<HtmlElement> cells = targetRow.findElements(By.tagName("td"));
		for(int i=0;i<cells.size();i++){
			String columnName = columnNames.get(i).trim().toUpperCase();			
			if(columnName.equals("PROPERTY")){
				bean.setProperty(cells.get(i).getAttribute("textContent"));
			}else if(columnName.equals("DATE")){
				bean.setDate(cells.get(i).getAttribute("textContent"));
			}else if(columnName.equals("TRANSACTION")){
				bean.setTransaction(cells.get(i).getAttribute("textContent"));
			}else if(columnName.equals("TOKEN AMOUNT")){
				bean.setTokenAmount(cells.get(i).getAttribute("textContent"));
			}else if(columnName.equals("BALANCE")){
				bean.setBalance(cells.get(i).getAttribute("textContent"));
			}else{
				bean.getOtherField().put(columnName, cells.get(i).getAttribute("textContent"));
			}
		}
		
		CustomLogger.log("Token History row bean data : "+bean);
		return bean;
	}

	public HtmlElement getEvent(String eventName){		
	
		int count=0;
		int noOfTimes=0;
		
		try{
			while(count==0 && noOfTimes<6){		
			driver.findElement(By.id("tokenHistory")).scrollDown(10000);
			Common.sleepFor(2000);
			count=driver.getCountOfElementsWithXPath((String.format
					("//div[contains(text(),'%s')]/preceding-sibling::div/span[contains(@class,'tokens')]",eventName)));
			noOfTimes++;
		}
		
		}catch(TimeoutException te){
			CustomLogger.log(te.toString());
		}
		return driver.findElement(By.xpath(String.format
				("//div[contains(text(),'%s')]/preceding-sibling::div/span[contains(@class,'tokens')]",eventName)));
	}
	
	public String getTokenForEvent(String eventName){		
		
		CustomLogger.log("Getting token count for event "+eventName+" from token history table");	
		String token= getEvent(eventName).getText().substring(1);
		return token.replace(",", "");	
	
	}
	
	public List<String> getActivityName() throws InterruptedException{
		CustomLogger.log("Going to get acivity names");
		List<String> activityNames=new ArrayList<String>();
		driver.findElement(By.id("tokenHistory")).scrollDown(1000);
		//get all the activities
		List<HtmlElement> articles=driver.findElements(By.xpath("//article//div[@class='th_desc']"));		
		for(HtmlElement element:articles){
			String activity=element.getText();
			activityNames.add(activity);
		}
		//return list of activities name
		return activityNames;
	}
	/**
	 * This will return the count of events in token history
	 * */
	public int getEventCount(String event){
		CustomLogger.log("Getting total no. of occurrences of the event: "+event);
		return driver.getCountOfElementsWithXPath(String.format("//div[contains(text(),'%s')]", event));
	}
}
