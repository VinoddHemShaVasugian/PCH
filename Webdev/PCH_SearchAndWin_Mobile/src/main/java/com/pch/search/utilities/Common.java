package com.pch.search.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Reporter;

import com.thoughtworks.xstream.XStream;

public class Common {
	public static final String CURRENCY_USD = "USD";
	public static final String CURRENCY_EUR = "EUR";

	private static final Random RANDOM_GENERATOR = new Random();
	public static Integer EXPLICIT_WAIT_PERIOD = 0;
	public static Integer IMPLICIT_WAIT_PERIOD = 10;

	public static String REGEX_PARSING_TOKENS_FROM_ACTIVITY = "\\d+(?= Tokens)";
	public static String REGEX_PARSING_TOKENS_FROM_TOAST_MESSAGE = "\\d+(?= TOKENS)";
	public static String REGEX_PARSING_ACTIVITYNAME_FROM_ACTIVITY = "((?<=!).*)$";

	/**
	 * This method will make the testwait for the number of milliseconds passed
	 * as parameter
	 * 
	 * @param i
	 * @return void
	 */
	public static void sleepFor(final long i) {
		try {
			CustomLogger.log(String.format("Sleeping for %d milliseconds", i));
			Thread.sleep(i);
		} catch (final InterruptedException e) {
		}
	}

	public static void sleepForIE(final long i) {
		if (Environment.getBrowserType().contains("ie")) {
			sleepFor(i);
		}
	}

	/**
	 * This method can be used to start the application
	 * 
	 * @param env
	 *            - Environment object, this object should have all environment
	 *            values.
	 * @param browserType
	 *            - ie, firefox, chrome
	 * @param application
	 *            - webstore
	 * @return webdriver
	 */
	/*
	 * public WebDriver startApplication(String application) { return
	 * startApplication(browserName, application, CURRENCY_USD); }
	 * 
	 * public WebDriver startApplication(final String browserName, final String
	 * application, final String currency) { final String url =
	 * getAppUrl(application, currency); return
	 * Browser.getBrowserInstance(browserName,url); }
	 */

	/**
	 * This method can be used to start the application
	 * 
	 * @param env
	 * @param application
	 * @return webdriver
	 */
	/*
	 * public static WebDriver startApplication(final Environment env, final
	 * String application) { final String url = getAppUrl(env, application);
	 * return Browser.getBrowserInstance(env, url);
	 * 
	 * }
	 */

	/**
	 * This method can be used to annotate a defect in the console and testng
	 * report log to signify why the test is failing
	 * 
	 * @param defetID
	 * @return
	 */
	public static void logDefectInReport(final String defectID) {
		final String defectMsg = "The following test has the defect(s) associated with it: ";
		Reporter.log(defectMsg + defectID);
	}

	/**
	 * start a new driver (open a new browser) and copy the cookies from a
	 * previously-used driver
	 * 
	 * @param env
	 *            configuration object for this runtime
	 * @param browserType
	 *            browser to start (ie, firefox, chrome)
	 * @param application
	 *            application name (webstore)
	 * @param driverWithCookies
	 *            the previous driver from which to copy cookies
	 * @return webdriver
	 */
	public static String getAppUrl(final String application) {
		return getAppUrl(application.trim(), CURRENCY_USD);
	}

	/**
	 * @param env
	 * @param application
	 * @return
	 */
	public static String getAppUrl(final String application, final String currency) {
		// Get the Environment details from the POM.xml file
		String exection_env = System.getProperty("env.to.execute");
		if (exection_env == null || exection_env.isEmpty())
			exection_env = Environment.getEnvironment();
		return String.format("http://search%spch.com", exection_env.equalsIgnoreCase("prod") ? "."
				: (exection_env.equalsIgnoreCase("stg") ? ".stg." : ".qa."));
	}

	/**
	 * This method will generate a java object for the xml data file provided.
	 * For this to work,every xml should have corresponding class file.
	 *
	 * @param fileName
	 *            - xml file
	 *
	 * @return - java object representing the class.
	 */
	public Object xmlToJava(final String fileName) {
		final XStream xstream = new XStream();
		xstream.alias("User", User.class);
		return xstream.fromXML(getClass().getResourceAsStream(fileName));
	}

	/**
	 * This method generates a 6 digit random number with a prefix
	 * 
	 * @param prefix
	 * @return String - a String with prefix followed by a random number
	 */
	public static String generateRandomID(final String prefix) {
		return new StringBuilder(prefix).append(RANDOM_GENERATOR.nextInt(999999999)).toString();
	}

	/**
	 * Get random number between min and max
	 * 
	 * @param min
	 * @param max
	 * @return int
	 */
	public static int getRandomNumber(final int min, final int max) {
		return min + RANDOM_GENERATOR.nextInt(max - min);
	}

	public String getAbsoluteFilePath(final String fileName) {
		return new File(getClass().getResource(fileName).getFile()).getAbsolutePath();
	}

	/**
	 * Get random username with spcified prefix followed by a random string
	 * 
	 * @param String
	 *            prefix
	 * @return String
	 */

	public static String getRandomUserName(final String prefix) {
		return new StringBuilder(prefix).append(getRandomString(8)).toString();
	}

	/**
	 * Get random string with specified length between a and z
	 * 
	 * @param int
	 *            length
	 * @return String
	 */

	public static String getRandomString(final int length) {
		final String charset = "abcdefghijklmnopqrstuvwxyz";

		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			final int pos = getRandomNumber(0, 25);
			sb.append(charset.charAt(pos));
		}
		return sb.toString();
	}

	/**
	 * Verify whether a string is a number
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(final String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Checks if list is ascending
	 * 
	 * @param doubleArray
	 * @return
	 */
	public static boolean isAscending(final Double[] doubleArray) {
		final boolean flag = true;
		for (int i = 1; i < doubleArray.length; i++) {
			if (doubleArray[i] < doubleArray[i - 1]) {
				return false;
			}
		}
		return flag;
	}

	/**
	 * Checks if double array is descending
	 * 
	 * @param doubleArray
	 * @return
	 */
	public static boolean isDescending(final Double[] doubleArray) {
		final boolean flag = true;
		for (int i = 1; i < doubleArray.length; i++) {
			if (doubleArray[i] > doubleArray[i - 1]) {
				return false;
			}
		}
		return flag;
	}

	/**
	 * Checks if passed in array is ascending
	 * 
	 * @param stringArray
	 * @return
	 */
	public static boolean isAscending(final String[] stringArray) {
		final boolean flag = true;
		for (int i = 1; i < stringArray.length; i++) {
			if (stringArray[i].compareTo(stringArray[i - 1]) < 0) {
				return false;
			}
		}
		return flag;
	}

	/**
	 * Checks if passed in string array is descending
	 * 
	 * @param stringArray
	 * @return
	 */
	public static boolean isDescending(final String[] stringArray) {
		final boolean flag = true;
		for (int i = 1; i < stringArray.length; i++) {
			if (stringArray[i].compareTo(stringArray[i - 1]) > 0) {
				return false;
			}
		}
		return flag;
	}

	/**
	 * Converts a double value to a double value that has 2 places after
	 * decimal.
	 * 
	 * @param num
	 * @return
	 */
	public static double convertDoubleToADoubleVal(final double num) {
		return Double.parseDouble(new DecimalFormat("###.##").format(num));
	}

	/**
	 * Converts string to float
	 * 
	 * @param num
	 * @return
	 */
	public static float convertStringToFloat(final String num) {
		return Float.parseFloat(num);
	}

	/**
	 * Converts string to int
	 * 
	 * @param num
	 * @return
	 */
	public static int convertStringToInt(final String num) {
		return Integer.parseInt(num);
	}

	public static float convertCurrencyToFloat(String currency) {
		Number num = null;
		boolean blnNegative = false;
		if (currency.startsWith("-")) {
			currency = currency.replace("-", "");
			blnNegative = true;
		}
		try {
			num = NumberFormat.getCurrencyInstance().parse(currency.replaceAll(" ", ""));

		} catch (final ParseException e) {
			// fail("Not able to Convert : " + currency);

		}

		if (blnNegative) {
			return 0 - num.floatValue();
		}

		return num.floatValue();
	}

	/**
	 * Converts a float value to a float value that has 2 places after decimal.
	 * 
	 * @param num
	 * @return float
	 */

	public static float convertFloatToAFloatDecimalVal(final float num) {
		final DecimalFormat dec = new DecimalFormat("###.##");
		final String decimalStr = dec.format(num);
		final float decimalVal = Float.parseFloat(decimalStr);
		return decimalVal;
	}

	/**
	 * Converts a double with two decimals to a string
	 * 
	 * @param num
	 * @return string
	 */
	public static String convertDoubleToAString(final double num) {
		final DecimalFormat dec = new DecimalFormat("0.00");
		final String shortString = dec.format(num);
		return shortString;

	}

	/**
	 * Get any date in any valid format. the offset parameter value is the
	 * number of days before or after the current date depending on whether the
	 * it is positive or negative
	 * 
	 * @return
	 *//*
		 * public static String getDate(final String dateFormat, final Integer
		 * offset) { final SimpleDateFormat format = new
		 * SimpleDateFormat(dateFormat); final Calendar date =
		 * Calendar.getInstance(); date.add(Calendar.DAY_OF_MONTH, offset);
		 * final String formattedDate = format.format(date.getTime()); return
		 * formattedDate; }
		 */

	/**
	 * Convert date object to other timezone.
	 * 
	 * @param d
	 * @param timeZone
	 * @return
	 */
	public static Date getDateInTimeZone(Date d, String timeZone) {
		DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		String strr = df.format(d);
		// System.out.println(strr);

		df = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
		try {
			d = df.parse(strr);
			return d;
		} catch (ParseException e) {
			CustomLogger.logException(e);
			return null;
		}
	}

	public static String getTime(final String dateFormat, final Integer offset) {
		final SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		final Calendar date = Calendar.getInstance();
		date.add(Calendar.MINUTE, offset);
		final String formattedDate = format.format(date.getTime());
		return formattedDate;
	}

	/**
	 * surround a string with double quotes
	 * 
	 * @param str
	 * @return
	 */
	public static String quote(final String str) {
		return new StringBuilder("\"").append(str).append("\"").toString();
	}

	/**
	 * This method will check whether the string input is an integer
	 * 
	 * @param i
	 * @return boolean
	 */
	public static boolean isParsableToInt(final String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (final NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Creates a string of fixed length with a repeating char
	 * 
	 * @param fillChar
	 * @param count
	 * @return
	 */
	public static String fillString(final char fillChar, final int count) {
		final char[] chars = new char[count];
		java.util.Arrays.fill(chars, fillChar);
		return new String(chars);
	}

	/**
	 * Retrieve a property value from a standard Properties XML file
	 * 
	 * @param resourceFile
	 * @param key
	 * @return key value
	 */
	public String getSimpleXMLProperty(String resourceFile, String key) {
		Properties properties = new Properties();

		try {
			InputStream xmlStream = getClass().getResourceAsStream(resourceFile);
			if (xmlStream == null) {
				// fail("Cannot read resource file '" + resourceFile + "'");
			}
			properties.loadFromXML(xmlStream);
			String value = properties.getProperty(key);
			if (value == null) {
				// fail("Property '" + key + "' not found");
			}
			return value;
		} catch (IOException exception) {
			// fail("Cannot read resource file '" + resourceFile + "'");
		}
		return null;
	}

	public String RemoveHTMLTags(String strMsg) {
		strMsg = strMsg.replaceAll("\\<.*?>", "");
		strMsg = strMsg.replaceAll("&nbsp;", " ");
		strMsg = strMsg.replaceAll("&amp;", "&");
		strMsg = strMsg.replaceAll("  ", " ");
		return strMsg;
	}

	public Timestamp getCurrentTimeStamp() {
		// 1) create a java calendar instance
		Calendar calendar = Calendar.getInstance();

		// 2) get a java.util.Date from the calendar instance.
		// this date will represent the current instant, or "now".
		Date now = calendar.getTime();

		// 3) a java current time (now) instance
		Timestamp currentTimestamp = new Timestamp(now.getTime());
		return currentTimestamp;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isLinkBroken(String url) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			request.addHeader("User-Agent", "USER_AGENT");
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				return false;
			}

			return true;

		} catch (ClientProtocolException cpe) {
			CustomLogger.logException(cpe);
			return true;
		} catch (IOException e) {
			CustomLogger.logException(e);
			return true;
		}
	}

	public static String getCurrentDate(String timeZone, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		return sdf.format(new Date());
	}

	public static long getEpochTimeStamp(String date, String format, String timeZone) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		try {
			Date d = sdf.parse(date);
			return d.getTime() / 1000L;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1L;
		}
	}

	/**
	 * Adds or subtracts year(s) to a date provide as a string in the specified
	 * format.
	 * 
	 * @param date
	 *            - A date in String format.
	 * @param dateFormat
	 *            - Format of entered string date e.g. yyyy-MM-dd
	 * @param offset
	 *            - can be negative for subtraction
	 * @return - String in the same format as input date.
	 */
	public static String addYearToDate(String date, String dateFormat, int offset) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			Date d = sdf.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.YEAR, offset);
			return sdf.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error, refer stack trace";
		}
	}

	/**
	 * Adds or subtracts day(s) to a date provide as a string in the specified
	 * format.
	 * 
	 * @param date
	 *            - A date in String format.
	 * @param dateFormat
	 *            - Format of entered string date e.g. yyyy-MM-dd
	 * @param offset
	 *            - can be negative for subtraction
	 * @return - String in the same format as input date.
	 */
	public static String addDaysToDate(String date, String dateFormat, int offset) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			Date d = sdf.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, offset);
			return sdf.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error, refer stack trace";
		}
	}

	/**
	 * Adds or subtracts month(s) to a date provide as a string in the specified
	 * format.
	 * 
	 * @param date
	 *            - A date in String format.
	 * @param dateFormat
	 *            - Format of entered string date e.g. yyyy-MM-dd
	 * @param offset
	 *            - can be negative for subtraction
	 * @return - String in the same format as input date.
	 */
	public static String addMonthsToDate(String date, String dateFormat, int offset) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			Date d = sdf.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.MONTH, offset);
			return sdf.format(cal.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error, refer stack trace";
		}
	}

	public static Integer compareDate(String date1, String date2, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			Date dait1 = sdf.parse(date1);
			Date dait2 = sdf.parse(date2);
			return dait1.compareTo(dait2);
		} catch (ParseException e) {
			CustomLogger.logException(e);
			return null;
		}

	}

	/**
	 * 
	 * @param dateString
	 *            - String to parse
	 * @param format-
	 *            Format of string to parse date from.
	 * @return Date object
	 */

	public static Date parseDateFromString(String dateString, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CustomLogger.logException(e);
			return null;
		}
	}

	/**
	 * Extract a substring from subject string using regex
	 * 
	 * @param subjectString
	 * @param pattern
	 *            - Regex pattern
	 * @return - Returns the matched substring
	 */
	public static String subString(String subjectString, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(subjectString);
		String result = "";
		if (m.find()) {
			result = m.group();
		}
		return result;
	}

	/**
	 * Checks wether the subject string satisfies the pattern.
	 * 
	 * @param subjectString
	 * @param pattern
	 *            - Regex Pattern
	 * @return True/False
	 */
	public static boolean satisfiesPattern(String subjectString, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(subjectString);
		boolean result = false;
		if (m.find()) {
			result = true;
		}
		return result;
	}

	public static String generateMD5(String str) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				String hex = Integer.toHexString(0xff & byteData[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			CustomLogger.logException(e);
			return null;
		}
	}

	public static Properties getPropertiesFromFile(String nfspFile) {

		// Load properties file
		Properties pro = new Properties();
		try {
			FileInputStream in = new FileInputStream(nfspFile);
			pro.load(in);
			return pro;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}

	}

	/**
	 * Trigger the GET Call via HTTP Client third party jar. Uses Jackson third
	 * party API to parse the JSON response to get the specified parameter value
	 * 
	 * @return Given parameter value from the GET Response
	 * @author mperumal
	 */
	public static String getResponseParamFromGETCall(String endpoint_call, String response_key_param) {

		try {
			HttpClient http_client = HttpClients.createDefault();
			HttpGet http_get = new HttpGet(endpoint_call);
			// Execute and get the response.
			HttpResponse response = http_client.execute(http_get);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			String response_in_string = result.toString();
			CustomLogger.log("GET Call -" + endpoint_call + "- response: " + response_in_string);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode json_node = mapper.readTree(response_in_string);
			return jsonParser(json_node, response_key_param);
		} catch (Exception e) {
			CustomLogger.log("Exception in Get Call trigger: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Trigger the POST Call via HTTP Client third party jar. Uses Jackson third
	 * party API to parse the JSON response to get the specified parameter value
	 * 
	 * @return Given parameter value from the POST Response
	 * @author mperumal
	 */
	public static String getResponseParamFromPOSTCall(String endpoint_call, String payload,
			LinkedHashMap<String, String> headers, String response_key_param) {
		try {
			String line;
			StringBuffer jsonString = new StringBuffer();

			URL url = new URL(endpoint_call);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			if (headers != null && !headers.isEmpty()) {
				for (String key : headers.keySet()) {
					connection.setRequestProperty(key, headers.get(key));
				}
			}
			connection.setRequestProperty("Accept", "application/json");

			if (payload.length() > 0) {
				connection.setRequestProperty("Content-Type", "application/json");
				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(payload);
				writer.close();
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = br.readLine()) != null) {
				jsonString.append(line);
			}
			br.close();
			connection.disconnect();
			String response_in_string = jsonString.toString();
			CustomLogger.log("POST Call -" + endpoint_call + "- response: " + response_in_string);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode json_node = mapper.readTree(response_in_string);
			return jsonParser(json_node, response_key_param);
		} catch (Exception e) {
			CustomLogger.log("Exception in Post Call trigger: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Parse the JSON response and retrieve the value for the given parameter
	 * key.
	 * 
	 * @param rootNode
	 * @return Response parameter value
	 * @author mperumal
	 */
	public static String jsonParser(JsonNode rootNode, String response_key_param) {
		return rootNode.findValue(response_key_param).asText().toString();
	}
}
