package com.pch.sw.tests.coe;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.DBUtils;
import com.pch.search.utilities.GroupNames;

public class ChangeOfEmailTests extends BaseTest {

	HomePage homePage;
	WebHeaderPage headerPage;
	private String UserID;

	@Test(groups = { GroupNames.Mobile,
			GroupNames.Regression }, description = "Change of email-Replace Email Address with UserID")
	public void replaceEmailWithUserId() {
		homePage.load();
		headerPage.loginToSearch("np03@pchmail.com", "testing");

		// Verify that sso_user_data doesn't retrieve any data with email id
		Assert.assertTrue(DBUtils.executeQueryAndVerifyValue(
				"SELECT * FROM `sandw_sso_user_data` WHERE user=\"np03@pchmail.com\"", "user").equals(""));
		UserID = DBUtils.getUserIdFromEmail("np03@pchmail.com");

		// Verify that sso_user_data does retrieve data with user id
		Assert.assertTrue(
				DBUtils.executeQueryAndVerifyValue("SELECT * FROM `sandw_sso_user_data` WHERE user=" + UserID, "user")
						.equals(UserID));

	}
}
