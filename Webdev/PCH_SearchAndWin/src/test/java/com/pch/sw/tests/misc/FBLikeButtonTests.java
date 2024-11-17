package com.pch.sw.tests.misc;

import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class FBLikeButtonTests extends BaseTest{
	private HomePage homePage;
	RegistrationPage regPage;
	User randomUser_1;
	
	@Test(description="TestID='16883', Verify FB link clickable and open FB login page in a new window",
			groups={GroupNames.Regression,GroupNames.Desktop})
	public void test_FB_LikeButton_Guest_Registered_User(){
		//validate FB like button exits and FB login open up in new window
		homePage.load();
		homePage.validateFB_LoginWindow();
	
		//login with registered user and validate FB like button
		loginToSearch(randomUser_1);
		homePage.validateFB_LoginWindow();
	}
	
}
