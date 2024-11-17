package com.util;

import java.util.Random;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class RandomGenerator {
	
 	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String NUMERIC_STRING = "0123456789";
 
	public static String getName() {
 		return firstCharUppercase((org.apache.commons.lang.RandomStringUtils.randomAlphabetic(15)).replaceAll("f", "z").replaceAll("F", "Z"));
	
	}

	private static String firstCharUppercase(String name) {
		return Character.toString(name.charAt(0)).toUpperCase()
				+ name.substring(1);
	}
	
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String randomAlphabetic(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_STRING.length());
			builder.append(ALPHA_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String randomNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*NUMERIC_STRING.length());
			builder.append(NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
	
	public static String randnum() {
        Random rand =new Random();
        int num = rand.nextInt(9000000) + 1000000;
        return Integer.toString(num);
    }
}
