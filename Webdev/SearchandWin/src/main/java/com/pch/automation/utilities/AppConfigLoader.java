package com.pch.automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

/**
 * Loads the application and environment specific config files.
 * 
 * @author mperumal
 *
 */
public class AppConfigLoader {

	private static AppConfigLoader configLoaderInstance = null;
	private Properties appConfigPropertySet = new Properties();
	private Properties envConfigPropertySet = new Properties();
	private InputStream appConfigPropertyStream = null;
	private InputStream envConfigPropertyStream = null;
	public static String env;
	private final String baseConfigFile = "/config/baseAppConfig.properties";
	private final static String appMsgPropertyFilePath = "src/test/resources/properties/app_message.properties";
	public static String deviceType;
	public static String browserType;

	private AppConfigLoader() throws IOException {
		try {
			// Read properties file
			appConfigPropertyStream = AppConfigLoader.class.getResourceAsStream(baseConfigFile);
			// Read Serenity variables
			EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
			appConfigPropertySet.load(appConfigPropertyStream);
			env = System.getProperty("environment");
			deviceType = System.getProperty("testing.devicetype");
			browserType = System.getProperty("testing.browser");

			if (env == null) {
				env = appConfigPropertySet.getProperty("CurrentEnvironment");
				deviceType = environmentVariables.getProperty("DeviceType");
				browserType = environmentVariables.getProperty("Browser");
			}
			System.out.println("********'SUITE RUNNING ON " + env + " ENVIRONMENT'********");
			envConfigPropertyStream = AppConfigLoader.class
					.getResourceAsStream("/config/" + env + "/appConfig.properties");
			envConfigPropertySet.load(envConfigPropertyStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (envConfigPropertySet != null)
				envConfigPropertyStream.close();
			if (appConfigPropertySet != null)
				appConfigPropertyStream.close();
		}
	}

	/**
	 * Retrieve the property from the config file based on the given key
	 * 
	 * @param key
	 * @return
	 */
	public String getEnvironmentProperty(String key) {
		String value = envConfigPropertySet.getProperty(key);
		return value != null ? value.trim() : "";
	}

	/**
	 * Singleton method to instantiate one instance for the run
	 * 
	 * @return AppConfigLoader instance
	 */
	public static AppConfigLoader getInstance() {
		if (configLoaderInstance == null) {
			try {
				configLoaderInstance = new AppConfigLoader();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return configLoaderInstance;
	}

	/**
	 * To read data from application message properties file
	 * 
	 * @author vsankar
	 */
	public String msgPropertyFileReader(String propertyName, String... replaceValue) {
		File file = new File(appMsgPropertyFilePath);

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {

		}
		Properties prop = new Properties();
		try {
			prop.load(fileInput);
		} catch (IOException e) {
		}
		String value = prop.getProperty(propertyName);
		for (String param : replaceValue) {
			value = value.replaceFirst("<&&>", param);
		}
		return value;
	}
}
