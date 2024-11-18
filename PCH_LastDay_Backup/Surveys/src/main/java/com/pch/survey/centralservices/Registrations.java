package com.pch.survey.centralservices;

import com.pch.survey.utilities.ConfigurationReader;
import com.pchengineering.registration.RegistrationRequestGenerator;

public class Registrations {
	private static RegistrationRequestGenerator regRequest = new RegistrationRequestGenerator();
	private static String envir = ConfigurationReader.getInstance().getEnvironment();
	private static String email;
	private static String gmt;
	private static String password = "Pch123";
	private static String firstName;

	public static void createGoldUser() {
		switch (envir) {
		case "QA":
			regRequest.generateGoldUserInQA();
			break;
		case "STG":
			regRequest.generateGoldUserInSTG();
			break;
		default:
			regRequest.generateGoldUserInQA();
			break;
		}
		email = regRequest.getEmail();
		gmt = regRequest.getGmt();
		password = "Pch123";
		firstName = regRequest.getFirstName();

	}

	public static void createSilverUser() {
		switch (envir) {
		case "QA":
			regRequest.generateSilverUserInQA();
			break;
		case "STG":
			regRequest.generateSilverUserInSTG();
			break;
		default:
			regRequest.generateSilverUserInCQA();
			break;
		}
		email = regRequest.getEmail();
		gmt = regRequest.getGmt();
		password = "Pch123";
		firstName = regRequest.getFirstName();

	}

	public static void createSocialUser() {
		switch (envir) {
		case "QA":
			regRequest.generatePreRegUserInQA();
			break;
		case "STG":
			regRequest.generatePreRegUserInSTG();
			break;
		default:
			regRequest.generatePreRegUserInQA();
			break;
		}
		email = regRequest.getEmail();
		gmt = regRequest.getGmt();
		password = "Pch123";
		firstName = regRequest.getFirstName();

	}

	public static void createMiniRegUser() {
		switch (envir) {
		case "QA":
			regRequest.generateMiniRegUserInQA();
			break;
		case "STG":
			regRequest.generateMiniRegUserInSTG();
			break;
		default:
			regRequest.generateMiniRegUserInQA();
			break;
		}
		email = regRequest.getEmail();
		gmt = regRequest.getGmt();
		password = "Pch123";
		firstName = regRequest.getFirstName();

	}

	public static String getEmail() {
		return email;
	}

	public static String getGmt() {
		return gmt;
	}

	public static String getPassword() {
		return password;
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setEmail(String val) {
		email = val;
	}

	public static void setGmt(String val) {
		gmt = val;
	}

	public static void main(String[] args) {

	}

}
