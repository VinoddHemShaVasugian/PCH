package com.pch.kenofrontend.utilities;

import com.pch.kenofrontend.pages.RegistrationPage;
import com.pchengineering.registrations.RegistrationRequestGenerator;
import com.pchengineering.registrations.RequestDefaultsOverride;

public class Users {
	RegistrationPage rp;

	public RegistrationRequestGenerator createUser(String typeOfUser) {
		RegistrationRequestGenerator regReq = new RegistrationRequestGenerator();
		RequestDefaultsOverride defaultOverride = new RequestDefaultsOverride();
		// RegistrationRequestGenerator rs = new RegistrationRequestGenerator();

		String uni_email = "";

		try {

			switch (PropertiesReader.getInstance().getBaseConfig(
					"CurrentEnvironment")) {

			case "QA":
				if (typeOfUser.equalsIgnoreCase("MiniReg")) {
					System.out
							.println("-----Creating a Mini reg user in QA-----");
					// regReq.generateMiniRegUserInQA();

					rp.uniqueReg();
					defaultOverride.setEmail(RegistrationPage.uni_email);
					regReq.generateMiniRegUserInQA(defaultOverride);

					System.out.println("User created with below Email & GMT!!");
					System.out.println("Email:: " + regReq.getEmail());
					System.out.println("GMT:: " + regReq.getGmt());
				} else if (typeOfUser.equalsIgnoreCase("Social")) {
					String autoemail = "Auto_Keno_"
							+ PropertiesReader.getInstance().getBaseConfig(
									"CurrentEnvironment") + "_socialUser"
							+ RandomGenerator.randnum() + "@pchmail.com";
					String externalId = RandomGenerator.randomNumeric(8);
					System.out
							.println("-----Creating a Social user in QA-----");
					regReq.generateFaceBookUserWithEmailInQA(externalId,
							autoemail, autoemail);
					System.out.println("User created with below Email & GMT!!");
					System.out.println("Email:: " + regReq.getEmail());
					System.out.println("GMT:: " + regReq.getGmt());
				} else if (typeOfUser.equalsIgnoreCase("User With Password")) {
					uni_email = uniqueReg();
					defaultOverride.setEmail(uni_email);
					System.out
							.println("-----Creating a Fully Reg user in QA-----");
					regReq.generateGoldUserInQA(defaultOverride);
				} else if (typeOfUser.equalsIgnoreCase("User Without Password")) {
					uni_email = uniqueReg();
					defaultOverride.setEmail(uni_email);
					System.out
							.println("-----Creating a User without password in QA-----");
					regReq.generateSilverUserInSTG(defaultOverride);
				}
				break;
			case "STG":
				if (typeOfUser.equalsIgnoreCase("MiniReg")) {
					System.out
							.println("-----Creating a Mini reg user in STG-----");
					// regReq.generateMiniRegUserInSTG();
					uni_email = uniqueReg();
					defaultOverride.setEmail(uni_email);
					regReq.generateMiniRegUserInSTG(defaultOverride);
					System.out.println("User created with below Email & GMT!!");
					System.out.println("Email:: " + regReq.getEmail());
					System.out.println("GMT:: " + regReq.getGmt());
				} else if (typeOfUser.equalsIgnoreCase("Social")) {
					String autoemail = "Auto_Keno_"
							+ PropertiesReader.getInstance().getBaseConfig(
									"CurrentEnvironment") + "_socialUser"
							+ RandomGenerator.randnum() + "@pchmail.com";
					String externalId = RandomGenerator.randomNumeric(8);
					System.out
							.println("-----Creating a Social user in STG-----");
					regReq.generateFaceBookUserWithEmailInSTG(externalId,
							autoemail, autoemail);
					System.out.println("User created with below Email & GMT!!");
					System.out.println("Email:: " + regReq.getEmail());
					System.out.println("GMT:: " + regReq.getGmt());
				} else if (typeOfUser.equalsIgnoreCase("User With Password")) {
					uni_email = uniqueReg();
					defaultOverride.setEmail(uni_email);
					System.out
							.println("-----Creating a Fully Reg user in STG-----");
					regReq.generateGoldUserInSTG(defaultOverride);
				} else if (typeOfUser.equalsIgnoreCase("User Without Password")) {
					uni_email = uniqueReg();
					defaultOverride.setEmail(uni_email);
					System.out
							.println("-----Creating a User without password in STG-----");
					regReq.generateSilverUserInSTG(defaultOverride);
				}
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return regReq;
	}

	public static Users getInstance() {
		try {
			return new Users();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String uniqueReg() {
		String email = "";
		try {
			email = "Auto_keno_"
					+ PropertiesReader.getInstance().getBaseConfig(
							"CurrentEnvironment") + "_"
					+ RandomGenerator.randomAlphabetic(5)
					+ RandomGenerator.randomAlphabetic(5)
					+ RandomGenerator.randnum() + "@pchmail.com";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return email;
	}
}
