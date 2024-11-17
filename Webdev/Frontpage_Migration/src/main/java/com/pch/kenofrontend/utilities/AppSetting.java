package com.pch.kenofrontend.utilities;


/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public enum AppSetting {	


	/**
	 *  This Enum conatins all the static/predefined/Default Value 
	 *  which will use in Automation whenever required
	 * 
	 *
	 */
	Capture_RequestData_In_Report("true"),
	Capture_ResponseData_In_Response("true");

	final String configValue;

	private AppSetting(String value) {
		configValue = value.toString();
	}

	public String value() {
		return this.configValue;
	}

	public Integer intValue() {
		return Integer.parseInt(this.configValue);
	}

	public boolean boolValue(){
		return Boolean.parseBoolean(this.configValue);
	}

};
