package com.pch.search.utilities;

import java.util.Map;

import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.testng.ITestContext;

public class Environment {
	/*
	 * private final ITestContext context;
	 * 
	 * protected Environment(final ITestContext context) { this.context =
	 * context; }
	 * 
	 * /*public static Environment getInstance(final ITestContext context) {
	 * return new Environment(context); }
	 */

	private static Map<Long, Map<String, String>> propertyNThreadMap = new ConcurrentHashMap<Long, Map<String, String>>();

	public static String getBrowserType() {
		String browserName = System.getProperty("device.to.launch");
		if (browserName == null || browserName.isEmpty())
			browserName = getParam("browserName");
		return browserName;
	}

	public static String getDevice() {
		String device = getParam("device");
		return device;
	}

	public static String getPlatform() {
		String platform = getParam("platform");
		return platform;
	}

	public static String getBrowserName() {
		String browserName = getParam("browserName");
		return browserName;
	}

	public static String getEnableVideo() {
		String Video = getParam("enableVideo");
		return Video;

	}

	public static String getEnvironmentToRun() {
		String browserstack_mode = System.getProperty("enable.browserstack");
		if (browserstack_mode == null || browserstack_mode.equals("") || browserstack_mode.isEmpty())
			browserstack_mode = getParam("BrowserStack");
		return browserstack_mode;
	}

	public static String getBrowserStackUSername() {
		String Envirun = getParam("BrowserStack_Username");
		return Envirun;
	}

	public static String getBrowserStackAccessKey() {
		String Envirun = getParam("BrowserStack_Access_key");
		return Envirun;
	}

	public static String getBrowserBinary() {
		String browserBinaryPath = getParam("browserBinaryPath");
		return browserBinaryPath == null ? "default" : browserBinaryPath;
	}

	public static String getOamUsername() {
		String OamUserName = getParam("OamUserName");
		return OamUserName;
	}

	public static String getOamPassword() {
		String OamPwd = getParam("OamPassword");
		return OamPwd;
	}

	/*
	 * public static String getSetupBrowserType() { final String browser =
	 * getParam("setupBrowserType");rows return (browser != null) ? browser :
	 * getBrowserType(); }
	 */

	public static String getFirefoxProfile() {
		return getParam("firefoxProfile");
	}

	/*
	 * public static void main(String args[]){ loadProperties(); }
	 */

	public static void loadProperties(ITestContext context) {
		Map<String, String> propertyValuePair = null;
		propertyValuePair = context.getCurrentXmlTest().getAllParameters();
		propertyNThreadMap.put(Thread.currentThread().getId(), propertyValuePair);

		System.out.println(propertyNThreadMap.toString().replaceAll(",", "\n"));
	}

	public static void refreshPropertyMap(ITestContext context) {
		propertyNThreadMap.remove(Thread.currentThread().getId());
	}

	protected static String getParam(final String key) {
		String valueCandidate = System.getProperty(key);
		if (valueCandidate != null) {
			return valueCandidate;
		} else if ((valueCandidate = System.getenv("TESTNG_" + key)) != null) {
			return valueCandidate;
		} else {
			try {
				return propertyNThreadMap.get(Thread.currentThread().getId()).get(key);
			} catch (NullPointerException npe) {
				System.out.println("NPE for key value :" + key);
				System.out.println(
						"No browser instance present in map for Thread id - " + Thread.currentThread().getId());
				CustomLogger
						.log("No browser instance present in map for Thread id - " + Thread.currentThread().getId());
				CustomLogger.log("NPE for key value :" + key);
				return "invalidValue";
			}
		}
	}

	/**
	 * This method will let you for the test set a parameter in the suite file
	 * 
	 * @param key
	 * @param value
	 * 
	 *            NOTE: This method could use some updating to make it more
	 *            efficient
	 */
	/*
	 * public void setParam(String key, String value){ Map<String, String>
	 * parameters = new HashMap<String, String>(); parameters.put(key, value);
	 * context.getCurrentXmlTest().setParameters(parameters);
	 * propertyValuePair.put(key, value); }
	 */

	/*
	 * public static String getDomain() { return getParam("domain"); }
	 * 
	 * public static String getFirebug() { return getParam("firebug"); }
	 * 
	 * public String ReportDirectory() { return getParam("reportDirectory"); }
	 */

	public static String getRemoteUrl() {
		return getParam("remoteUrl");
	}

	public static String getLogLevel() {
		try {
			return getParam("logLevel");
		} catch (final NullPointerException e) {
			return "none";
		}
	}

	/*
	 * public static String getSharedDirectory() { return
	 * getParam("sharedDirectory"); }
	 * 
	 * public static Locale getLocale() { try { return new
	 * Locale(getParam("language"), getParam("country")); } catch (final
	 * NullPointerException e) { return Locale.US; } }
	 */

	/*
	 * public Object getAttribute(final String name) { return
	 * context.getAttribute(name); }
	 * 
	 * public void setAttribute(final String name, final Object value) {
	 * context.setAttribute(name, value); }
	 */

	public static double getNetworkLatencyTuner() {
		// return Common.convertStringToInt(getParam("waitPeriod"));
		String tuningParam = getParam("networkLatencyTuner");
		if (tuningParam == null)
			return 1.0;
		else
			return (Double.parseDouble(tuningParam));
	}

	public static String getAnalyticsFlag() {
		try {
			final String analyticsFlag = getParam("analyticsFlag");
			if (null != analyticsFlag) {
				return getParam("analyticsFlag");
			} else {
				throw new Exception();
			}

		} catch (final Exception e) {
			return "off";
		}
	}

	public static String getProxyServer() {
		try {
			final String proxyServer = getParam("proxyServer");
			if (null != proxyServer) {
				return proxyServer;
			} else {
				throw new Exception();
			}
		} catch (final Exception e) {
			return "localhost";
		}
	}

	public int getProxyPort() {
		try {
			final String proxyPort = getParam("proxyPort");
			if (null != proxyPort) {
				return Integer.parseInt(getParam("proxyPort"));
			} else {
				throw new Exception();
			}
		} catch (final Exception e) {
			return 8888;
		}
	}

	public static String getAppName() {
		final String appName = getParam("appName");

		if (null == appName) {
			return "-";
		}

		return appName;
	}

	public static String getIWEAppURL() {
		if (getEnvironment().equalsIgnoreCase("STG")) {
			return "https://adminiwe.stg.pch.com/iwe/";
		} else {
			return "https://iwe.qa.pch.com/iwe/";
		}
	}

	public static String getEnvironment() {
		return getParam("environment");
	}

	public static String getMySqlJdbc() {
		return getParam("mySqlJdbc");
	}

	public static String getMySqlUsername() {
		return getParam("mySqlUsername");
	}

	public static String getMySqlPassword() {
		return getParam("mySqlPassword");
	}

	public static String getJoomlaUsername() {
		return getParam("joomlaUserName");
	}

	public static String getJoomlaPassword() {
		return getParam("joomlaPassword");
	}

	public static String getIWEUsername() {
		return getParam("IWEUserName");
	}

	public static String getIWEPassword() {
		return getParam("IWEPassword");
	}

	public static String getSqlServerJdbc() {
		return getParam("sqlServerjdbc");
	}

	public static String getSqlServerUsername() {
		return getParam("sqlServerUsername");
	}

	public static String getSqlServerPassword() {
		return getParam("sqlServerPassword");
	}

	public static String getOSVersion() {
		return getParam("osVersion");
	}
}
