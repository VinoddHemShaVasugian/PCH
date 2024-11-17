package com.pch.kenofrontend.utilities;

import com.pch.kenofrontend.pages.RegistrationPage;
import com.pchengineering.registrations.RegistrationRequestGenerator;
import com.pchengineering.registrations.RequestDefaultsOverride;

public class Users {
	RegistrationPage rp;
	
	public RegistrationRequestGenerator createUser(String typeOfUser)
	{
        RegistrationRequestGenerator regReq = new RegistrationRequestGenerator();
        RequestDefaultsOverride defaultOverride = new RequestDefaultsOverride();
		//RegistrationRequestGenerator rs = new RegistrationRequestGenerator();
        
        try 
        {
					
        	switch(PropertiesReader.getInstance().getBaseConfig("CurrentEnvironment"))
        	{

            case "QA":
                if(typeOfUser.equalsIgnoreCase("MiniReg"))
                {
                	System.out.println("-----Creating a Mini reg user in QA-----");
                	//regReq.generateMiniRegUserInQA();
                	
                	rp.uniqueReg();
        			defaultOverride.setEmail(RegistrationPage.uni_email);
        			regReq.generateMiniRegUserInQA(defaultOverride);
        			
                	System.out.println("User created with below Email & GMT!!");
                	System.out.println("Email:: "+regReq.getEmail());
                	System.out.println("GMT:: "+regReq.getGmt());
                }
                else if (typeOfUser.equalsIgnoreCase("Social"))
                {
                    String autoemail = "Auto_Keno_" +PropertiesReader.getInstance().getBaseConfig("CurrentEnvironment")+"_socialUser"+ RandomGenerator.randnum() + "@pchmail.com";
            		String externalId = RandomGenerator.randomNumeric(8);
            		System.out.println("-----Creating a Social user in QA-----");	
            		regReq.generateFaceBookUserWithEmailInQA(externalId,autoemail,autoemail);
            		System.out.println("User created with below Email & GMT!!");
                	System.out.println("Email:: "+regReq.getEmail());
                	System.out.println("GMT:: "+regReq.getGmt());
                }
                break;
            case "STG":
                if(typeOfUser.equalsIgnoreCase("MiniReg"))
                {
                	System.out.println("-----Creating a Mini reg user in STG-----");
                	//regReq.generateMiniRegUserInSTG();
                	rp.uniqueReg();
        			defaultOverride.setEmail(RegistrationPage.uni_email);
        			regReq.generateMiniRegUserInSTG(defaultOverride);
        			
                	System.out.println("User created with below Email & GMT!!");
                	System.out.println("Email:: "+regReq.getEmail());
                	System.out.println("GMT:: "+regReq.getGmt());
                }
                else if (typeOfUser.equalsIgnoreCase("Social"))
                {
                	String autoemail = "Auto_Keno_" +PropertiesReader.getInstance().getBaseConfig("CurrentEnvironment")+"_socialUser"+ RandomGenerator.randnum() + "@pchmail.com";
             		String externalId = RandomGenerator.randomNumeric(8);
             		System.out.println("-----Creating a Social user in STG-----");
             		regReq.generateFaceBookUserWithEmailInSTG(externalId,autoemail,autoemail);
             		System.out.println("User created with below Email & GMT!!");
                	System.out.println("Email:: "+regReq.getEmail());
                	System.out.println("GMT:: "+regReq.getGmt());
                }
                break;            
        	}
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return regReq;
    }
	
    public static Users getInstance()
    {
        try {
            return new Users();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
