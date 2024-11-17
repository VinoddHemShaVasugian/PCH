package com.pch.search.bean;

import java.util.List;

public class TokenCounterBotResultBean {	
	
	private List<String> tokens;
	public TokenCounterBotResultBean(List<String> tokens){
		this.tokens=tokens;
	}
	
	public String toString(){
		return tokens.toString();
	}
	
	public long getIntitalTokens(){
		String initialTokens = tokens.get(0);
		if(initialTokens.toLowerCase().equals("loading...")){
			return 0;
		}else{
			try{
				return Long.parseLong(initialTokens.replaceAll(",",""));
			}catch(NumberFormatException nfe){
				return -1;
			}
		}
	}
	
	public long getFinalTokens(){
		//String finalTokens = tokens.get(0);
		String finalTokens = tokens.get(0);
		if(finalTokens.toLowerCase().equals("loading...")){
			return 0;
		}else{
			try{
				return Long.parseLong(finalTokens.replaceAll(",",""));
			}catch(NumberFormatException nfe){
				return -1;
			}
		}		
	}
	
	public boolean shouldLightBoxBePresent(){
		if(getIntitalTokens()<2500 && getFinalTokens()>=2500){
			return true;
		}else if(getIntitalTokens()<1000000 && getFinalTokens()>=1000000){
			return true;
		}else if(getIntitalTokens()<5000000 && getFinalTokens()>=5000000){
			return true;
		}else if(getIntitalTokens()<20000000 && getFinalTokens()>=20000000){
			return true;
		}else if(getIntitalTokens()<50000000 && getFinalTokens()>=50000000){
			return true;
		}else if(getIntitalTokens()<100000000 && getFinalTokens()>=100000000){
			return true;
		}else if(getIntitalTokens()<200000000 && getFinalTokens()>=200000000){
			return true;
		}else{
			return false;
		}
	}
}
