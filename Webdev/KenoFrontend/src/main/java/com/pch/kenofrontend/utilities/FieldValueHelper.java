package com.pch.kenofrontend.utilities;

import java.util.UUID;


/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class FieldValueHelper {

	// returns a unique name (fname/lname) only if the cell contains "unique"
	public static String returnUniqueName(String strVal) {
		String name = "";
		if (strVal.equals("unique")) {
			String getRandomName = RandomGenerator.getName();
			name = getRandomName;
		}
		return name;
	}

	// Method to generate a UUID
	public static UUID generateUUID() {

		UUID uuid = UUID.randomUUID();

		return uuid;
	}

	public static String evalValue(String paramVal, String constantVal) {
		if (paramVal == null || paramVal.equals(""))
			return constantVal;
		else if (paramVal.equalsIgnoreCase("NULL"))
			return null;
		else if (paramVal.equalsIgnoreCase("empty"))
			return "";		
		else
			return paramVal;
	}
}
