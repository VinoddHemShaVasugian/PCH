package com.pch.survey.apis;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.pch.survey.utilities.ConfigurationReader;
import com.pch.survey.utilities.WebServiceClient;

public class 	TokenBankApi {

	
	private static String token;
	
	private static WebServiceClient client = new WebServiceClient();
//	private static String tokenBankUrl = ConfigurationReader.getTokenBankUrl();
//	private static String tokenBankKeyUrl = ConfigurationReader.getTokenBankKeyUrl();
	private static String tokenBankUrl = "http://tokenbankapi.qa.pch.com/api/v6/earned/today/desktop";
	private static String tokenBankKeyUrl = "http://tokenbank.qa.pch.com/api";
	private static String jsonString = "{ \"line_of_business\": \"PCH\", \"security_key\": \"<key>\", \"req_info\":{ \"user\": { \"oat\": \"<oat>\" } } }";

	private static int tokenBalance;
	private static int tokensEarnedToday;
	private static int tokensTotal;

	private static TimeZone tz = TimeZone.getTimeZone("EST");
	
	public static int getTotalTokensByOat(String oat) {
		String json = null;
		json = jsonString.replace("<oat>", oat);
		json = json.replace("<key>", generateSecurityKey());
		client.sendPostJsonRequest(tokenBankUrl, json, null);
		JSONObject responseJson = new JSONObject(client.getJSONResponse());
		try {
			int tokenBalance = responseJson.getInt("balance");
			int tokensTotal = responseJson.getInt("earned_alltime");
			int tokensEarnedToday = responseJson.getInt("earned_today");
			System.out.println("Balance: " + tokenBalance);
			System.out.println("Earned All Time: " + tokensTotal);
			System.out.println("Earned Today: " + tokensEarnedToday);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokensTotal;
	}

	
	
	
	
	private static String generateSecurityKey() {
		
		String lobAccesscode = "HAN4xRQqkjqjb+hAgSXpS8m6Drl4f+JD2G75tDIh57XOy3tsOuECsF2O5egCLFZ";
		String encryptToken = "LhqjWKrAVBB6SBWv";
		
		String value = getFormattedDateString()+encryptToken+lobAccesscode;
		System.out.println("getSignature - value:=" + value);
		value =  hashValue(value);
		System.out.println("getSignature - hashValue:=" + value);
		return (value);
	}
	
	


	private static String hashValue(String input){
		byte[] bytes = decodeUTF8(input);
		bytes = hashSHA1(bytes);
		return encodeUrlSafeBase64(bytes);
	}

	private static byte[] hashSHA1(byte[] input){
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			return digest.digest(input);
		}
		catch (NoSuchAlgorithmException e)
		{
		return null;
		}

	}

	private static String getFormattedDateString()	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		format.setTimeZone(tz);
		Calendar cal = Calendar.getInstance(tz);
		cal.add(Calendar.SECOND, 60);
		return format.format(cal.getTime());
	}


	private static byte[] decodeUTF8(String input){
		try	{
			return input.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			return input.getBytes();
		}
	}

	private static String encodeUrlSafeBase64(byte[] input){
		return Base64.encodeBase64URLSafeString(input);
	}

	private static String encrypt(String strToEncrypt){
		try	{

			final byte[] key = {
					0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
			};
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			final String encryptedString = Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes()));
			return encryptedString;
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		
		getTotalTokensByOat("9a3e56ae-8ee3-4023-b1a0-57859a7a04d9");
		
		
	}
}