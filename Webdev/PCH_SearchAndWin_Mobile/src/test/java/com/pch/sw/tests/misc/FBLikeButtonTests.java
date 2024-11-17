package com.pch.sw.tests.misc;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.admin.AdminBasePage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class FBLikeButtonTests extends BaseTest {
	private HomePage homePage;
	RegistrationPage regPage;
	WebHeaderPage headerPage;
	AdminBasePage joomlaPage;
	User randomUser_1;

	@Test(description = "TestID='16883', Verify FB link clickable and open FB login page in a new window", groups = {
			GroupNames.Regression, GroupNames.Desktop })
	public void test_FB_LikeButton_Guest_Registered_User() {

		joomlaPage.unpublishArticle("Guided Search [D]");
		// validate FB like button exits and FB login open up in new window
		homePage.load();
		homePage.validateFB_LoginWindow();

		// login with registered user and validate FB like button
		loginToSearch(randomUser_1);
		homePage.enterFBDetails("sure.lotto002@gmail.com", "ppch123456");
	    boolean like_status = homePage.waitForElementVisible(By.xpath("//button[@title='Unlike']"), 5);
		Assert.assertTrue(like_status, "Facebook Like button is not in liked status");
	}

}
