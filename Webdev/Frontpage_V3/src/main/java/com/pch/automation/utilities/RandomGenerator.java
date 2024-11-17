/*
 * @Author pvadivelu
 * PCH Search and Win and Front Page
 */
package com.pch.automation.utilities;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The Class RandomGenerator.
 */
public class RandomGenerator {

	/** The rnd. */
	private static Random rnd = new Random();

	/** The Constant ALPHA_NUMERIC_STRING. */
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/** The Constant ALPHA_STRING. */
	private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** The Constant NUMERIC_STRING. */
	private static final String NUMERIC_STRING = "123456789";

	/**
	 * Creates the guid.
	 *
	 * @return the string
	 */
	public static String createGuid() {
		UUID guid = UUID.randomUUID();
		return guid.toString();
	}

	/**
	 * Gets the random number.
	 *
	 * @return the random number
	 */
	public static int getRandomNumber() {
		return 1 + rnd.nextInt(999999);
	}

	/**
	 * Gets the random replenishment.
	 *
	 * @return the random replenishment
	 */
	public static int getRandomReplenishment() {
		return 1 + rnd.nextInt(1 + rnd.nextInt(99));
	}

	/**
	 * Gets the random prize.
	 *
	 * @param noofDigits
	 *            the noof digits
	 * @return the random prize
	 */
	public static int getRandomPrize(int noofDigits) {
		return 1 + rnd.nextInt(noofDigits);
	}

	/**
	 * Gets the random float number.
	 *
	 * @param noofDigits
	 *            the noof digits
	 * @return the random float number
	 */
	public static double getRandomFloatNumber(double noofDigits) {
		return 1 + (double) RoundTo2Decimals((rnd.nextDouble() * (0.0D + noofDigits) - 0.0D));
	}

	/**
	 * Round to 2 decimals.
	 *
	 * @param val
	 *            the val
	 * @return the double
	 */
	public static double RoundTo2Decimals(double val) {
		DecimalFormat df2 = new DecimalFormat("###.##");
		df2.setRoundingMode(RoundingMode.CEILING);
		return Double.valueOf(df2.format(val));
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public static String getName() {
		return firstCharUppercase((org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10)).replaceAll("f", "z")
				.replaceAll("F", "Z"));

	}

	/**
	 * Gets the name.
	 *
	 * @param length
	 *            the length
	 * @return the name
	 */
	public static String getName(int length) {
		return firstCharUppercase((org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(length))
				.replaceAll("f", "z").replaceAll("F", "Z"));

	}

	/**
	 * First char uppercase.
	 *
	 * @param name
	 *            the name
	 * @return the string
	 */
	private static String firstCharUppercase(String name) {
		return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
	}

	/**
	 * Random alpha numeric.
	 *
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	/**
	 * Random alphabetic.
	 *
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomAlphabetic(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_STRING.length());
			builder.append(ALPHA_STRING.charAt(character));
		}
		return builder.toString();
	}

	/**
	 * Random numeric.
	 *
	 * @param count
	 *            the count
	 * @return the string
	 */
	public static String randomNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * NUMERIC_STRING.length());
			builder.append(NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	/**
	 * Gets the ting random numberbasedoninput.
	 *
	 * @param length
	 *            the length
	 * @return the ting random numberbasedoninput
	 */
	public static int gettingRandomNumberbasedoninput(int length) {
		return rnd.nextInt(length);
	}

	/**
	 * Gets the ting random integer digit length.
	 *
	 * @return the ting random integer digit length
	 */
	public static int gettingRandomIntegerDigitLength() {
		ArrayList<Integer> digitLength = new ArrayList<Integer>();
		digitLength.add(9);
		digitLength.add(99);
		digitLength.add(999);
		digitLength.add(9999);
		digitLength.add(99999);
		return digitLength.get(rnd.nextInt(digitLength.size()));
	}

	/**
	 * Gets the ting random float digit length.
	 *
	 * @return the ting random float digit length
	 */
	public static double gettingRandomFloatDigitLength() {
		ArrayList<Double> digitLength = new ArrayList<Double>();
		digitLength.add(9.0D);
		digitLength.add(99.0D);
		digitLength.add(999.0D);
		digitLength.add(9999.0D);
		digitLength.add(99999.0D);
		return digitLength.get(rnd.nextInt(digitLength.size()));
	}

	/**
	 * Gets the ting reserved gwy number.
	 *
	 * @return the ting reserved gwy number
	 */
	public static int gettingReservedGwyNumber() {
		Random rn = new Random();
		int maximum = 9150;
		int minimum = 9001;
		int range = maximum - minimum + 1;
		int randomNum = rn.nextInt(range) + minimum;
		return randomNum;
	}

	/**
	 * Generate double.
	 *
	 * @param minVal
	 *            the min val
	 * @param maxVal
	 *            the max val
	 * @return the double
	 */
	public static Double generateDouble(double minVal, double maxVal) {

		Random randr = new Random();
		double valDbl = minVal + randr.nextDouble() * (minVal + maxVal);
		int valInt = (int) (valDbl * 100);
		valDbl = (double) valInt / 100;

		return valDbl;
	}

	/**
	 * Generate interest.
	 *
	 * @param minVal
	 *            the min val
	 * @param maxVal
	 *            the max val
	 * @return the double
	 */
	public static Double generateInterest(double minVal, double maxVal) {

		Random randr = new Random();
		double valDbl = minVal + randr.nextDouble() * (minVal + maxVal);
		int valInt = (int) (valDbl * 1000);
		valDbl = Math.round((double) valInt / 1000);

		return valDbl;
	}

	/**
	 * Generate random contest key.
	 *
	 * @return the string
	 */
	public static String generate_Random_ContestKey() {

		return randomAlphabetic(2).toUpperCase() + randomNumeric(3);
	}

	/**
	 * Auto generated email.
	 *
	 * @return the string
	 */
	public static String autoGeneratedEmail() {
		UUID uuid = UUID.randomUUID();
		String randonUUIDString = uuid.toString().substring(0, 6);
		return "fpautomationuser." + randonUUIDString + "@pchmail.com";
	}

}
