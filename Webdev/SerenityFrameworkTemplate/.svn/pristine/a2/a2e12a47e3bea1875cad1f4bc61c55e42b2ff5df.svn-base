/*
 * @Author pvadivelu
 * @Version 1.0.0
 * PCH Serenity Framework Template
 */
package com.pch.sample.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * The Class AppConfigLoader.
 */
public class AppConfigLoader {

	/** The sample URL. */
	public String sampleURL = null;
	
	/** The sample APIURL. */
	public String sampleAPIURL = null;
	
	/** The sample db connection string. */
	public String sampleDbConnectionString = null;
	
	/** The environment. */
	public String environment = null;
	
	/** The my instance. */
	private static AppConfigLoader _myInstance = null;

	/**
	 * Instantiates a new app config loader.
	 */
	private AppConfigLoader() {

		Properties appConfigPropertySet = new Properties();
		Properties envConfigPropertySet = new Properties();

		try {

			// read properties file
			InputStream appConfigPropertyStream = AppConfigLoader.class.getResourceAsStream("/config/baseAppConfig.properties");
			appConfigPropertySet.load(appConfigPropertyStream);

			InputStream envConfigPropertyStream = null;			

			// check if current environment is defined (QA, Integration or Staging)
			if(System.getProperty("environment") != null){
				envConfigPropertyStream = AppConfigLoader.class
						.getResourceAsStream("/config/"
								+ System.getProperty("environment")
								+ "/appConfig.properties");
				System.out.println("********'ENVIRONMENT Details taken from Runtime'********");
				System.out.println("********'"+System.getProperty("environment")+" ENVIRONMENT'********");
				environment = System.getProperty("environment");
			}
			else {
				envConfigPropertyStream = AppConfigLoader.class
						.getResourceAsStream("/config/"
								+ appConfigPropertySet.getProperty("CurrentEnvironment")
								+ "/appConfig.properties");
				System.out.println("********'ENVIRONMENT Details taken from Baseapp config property file'********");
				System.out.println("********'"+appConfigPropertySet.getProperty("CurrentEnvironment")+" ENVIRONMENT'********");
				environment = appConfigPropertySet.getProperty("CurrentEnvironment");
			}

			envConfigPropertySet.load(envConfigPropertyStream);
		
			this.sampleURL = envConfigPropertySet.getProperty("SampleUrl");
			this.sampleAPIURL = envConfigPropertySet.getProperty("SampleAPIUrl");
			
			this.sampleDbConnectionString = envConfigPropertySet.getProperty("SampleConnectionString");
			
			envConfigPropertyStream.close();
			appConfigPropertyStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the single instance of AppConfigLoader.
	 *
	 * @return single instance of AppConfigLoader
	 */
	public static AppConfigLoader getInstance() {
		if (_myInstance == null) {
			_myInstance = new AppConfigLoader();		
		}
		return _myInstance;
	}
}
