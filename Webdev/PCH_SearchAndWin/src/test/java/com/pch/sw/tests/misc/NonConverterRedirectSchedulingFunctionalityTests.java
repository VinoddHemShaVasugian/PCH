//Commenting this TC - As the TC is dependent on PCH.com - to configure game for each any run we do.


/*package com.pch.sw.tests.misc;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.PchDotComPage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

import org.sikuli.script.*;

public class NonConverterRedirectSchedulingFunctionalityTests extends BaseTest {

	private HomePage homePage;
	private AdminBasePage joomlaPage;
	private CentralServicesPage csPage;
	private User randomUser_1;
	private WebHeaderPage webHeaderPage;
	private RegistrationPage registrationPage;
	private PchDotComPage pchDotComPage;
	
	
	@Test(description = "[2]B-31193 Non-Converter Redirect Scheduling Functionality - 28244")
	public void publishRedirectNonConvertersVIP() throws FindFailed{
		
		String environment = Environment.getEnvironment();
		
		joomlaPage.publishArticle("Redirect - Non Converters VIP");
		csPage.createFullRegUserWithPassword(randomUser_1);
		
		List<String> segmentName = new ArrayList<String>();
		
		segmentName.add("SEARCH_NONCONVERTERS2");
		segmentName.add("SEARCH_NONCONVERTERS1");
		
		List<String> segmentCode = new ArrayList<String>();
		segmentCode.add("SNV2");
		
		String userEmail = randomUser_1.getEmail();
		CustomLogger.log("Email : "+userEmail);
		
		joomlaPage.setegmentMemberShip(userEmail, "code", segmentCode);
		joomlaPage.setegmentMemberShip(userEmail, "name", segmentName);
		
		loginToSearch(randomUser_1);
		homePage.closeOptinLigthBox();
		homePage.closeUserLevelLightBox();
		
		String returnPaathURL = pchDotComPage.sikuliFunctionToPlayGames();
		
		CustomLogger.log("Validating the return path");
		
		Assert.assertTrue(returnPaathURL.contains("search."+environment+".pch.com"));
		
		//check the tokens
		
		CustomLogger.log("Successfully returned to Search Home page");
		
	}
	
	
}
*/