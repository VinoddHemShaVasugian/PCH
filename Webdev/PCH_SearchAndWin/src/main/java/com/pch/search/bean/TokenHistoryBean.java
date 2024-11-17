package com.pch.search.bean;

import java.util.HashMap;
import java.util.Map;

public class TokenHistoryBean {
	private String property;
	private String date;
	private String transaction;
	private int tokenAmount;
	private int balance;
	private Map<String,String> otherFields;
	
	public TokenHistoryBean(){
		otherFields = new HashMap<String,String>();
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTransaction() {
		return transaction;
	}
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}
	public int getTokenAmount() {
		return tokenAmount;
	}
	public void setTokenAmount(String tokenAmount) {
		this.tokenAmount = Integer.parseInt(tokenAmount.substring(0,tokenAmount.indexOf("token")).trim().replaceAll(",",""));
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = Integer.parseInt(balance.replaceAll(",","").trim());
	}
	public Map<String,String> getOtherField() {
		return otherFields;
	}
	public void setOtherField(Map<String,String> otherFields ) {
		this.otherFields = otherFields;
	}
	
	public String toString(){
		String beanData = String.format("Property : %s|Date : %s|Transaction : %s|TokenAmount : %s|Balance : %s", 
				property,date,transaction,tokenAmount,balance);
		return beanData;
	}
	
}
