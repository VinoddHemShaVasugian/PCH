package com.pch.search.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pch.search.utilities.Common;

public class LastActivityBotResultBean {
	private List<String> activities ;
	
	public LastActivityBotResultBean(List<String> activities){
		this.activities=activities;
	}
	
	public String toString(){
		return StringUtils.join(activities, "|");
	}
	
	public int getTokensEarnedInActivity(int index){
		return Integer.parseInt(Common.subString(activities.get(index), Common.REGEX_PARSING_TOKENS_FROM_ACTIVITY));
	}
	
	public String getActivityName(int index){
		return Common.subString(activities.get(index), Common.REGEX_PARSING_ACTIVITYNAME_FROM_ACTIVITY);
	}
	
	public int getTokensEarnedInActivity(){
		return getTokensEarnedInActivity(0);
	}
	
	public String getActivityName(){
		return getActivityName(0);
	}
	
	public List<String> getActivityNames(){
		Common.sleepFor(3000);
		List<String> activityNames = new ArrayList<String>();
		for(String activity:activities){
			activityNames.add(Common.subString(activity, Common.REGEX_PARSING_ACTIVITYNAME_FROM_ACTIVITY));
		}
		
		return activityNames;
	}
		
}
