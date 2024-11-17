package com.pch.search.bean;

public class WeatherConditionBean {
	
		private String currentTemperature;
		private String maxTemperature;
		private String minTemparature;
		private String skyConditions;
		
		public String getCurrentTemperature() {
			return currentTemperature;
		}
		public void setCurrentTemperature(String currentTemperature) {
			this.currentTemperature = currentTemperature;
		}
		public String getMaxTemperature() {
			return maxTemperature;
		}
		public void setMaxTemperature(String maxTemperature) {
			this.maxTemperature = maxTemperature;
		}
		public String getMinTemparature() {
			return minTemparature;
		}
		public void setMinTemparature(String minTemparature) {
			this.minTemparature = minTemparature;
		}
		public String getSkyConditions() {
			return skyConditions;
		}
		public void setSkyConditions(String skyConditions) {
			this.skyConditions = skyConditions;
		}	
}
