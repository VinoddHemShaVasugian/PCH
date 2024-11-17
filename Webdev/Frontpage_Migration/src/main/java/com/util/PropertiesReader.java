package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class PropertiesReader {

	
	public String environment = null;
	public String browser = null;
	public String kenoUrl = null;
	public String kenoAdminUrl = null;
	public String OAMUrl = null;
	public String FPBaseUrl = null;
	public String FrontpageAdminUrl = null;

	
	@SuppressWarnings("unused")
	private static PropertiesReader propertiesReader_instance = null;

		
private PropertiesReader() throws Exception {
	
	this.environment = getBaseConfig("CurrentEnvironment");
	this.kenoAdminUrl = getData("KenoAdminUrl");
	this.kenoUrl= getData("KenoUrl");
	this.OAMUrl=getData("OAMUrl");
	this.FPBaseUrl=getData("FPBaseUrl");
	this.FrontpageAdminUrl=getData("FrontpageAdminUrl");
}


public String getData(String key) {
	try{
	Properties prop = new Properties();
	InputStream in;
	if(getBaseConfig("CurrentEnvironment").equalsIgnoreCase("STG"))
	{
		in = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/resources/config/STAGING/appConfig.properties"));
	}
	else if (getBaseConfig("CurrentEnvironment").equalsIgnoreCase("INTEGRATION"))
		in = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/resources/config/INTEGRATION/appConfig.properties"));
	else
		in = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/resources/config/QA/appConfig.properties"));
	prop.load(in);
	return prop.getProperty(key).trim();
	}
	catch(Exception e){
		throw new IllegalArgumentException("No such key found");
	}
}

public String getBaseConfig(String key) throws Exception{
	Properties prop = new Properties();
	InputStream in = new FileInputStream(new File(System.getProperty("user.dir")+"/src/test/resources/config/baseAppConfig.properties"));
	prop.load(in);
	return prop.getProperty(key).trim();
}

public static PropertiesReader getInstance() 
  {
	try {
		return new PropertiesReader();
		} catch (Exception e) 
		{
		e.printStackTrace();
		return null;
	}		
} 
}
