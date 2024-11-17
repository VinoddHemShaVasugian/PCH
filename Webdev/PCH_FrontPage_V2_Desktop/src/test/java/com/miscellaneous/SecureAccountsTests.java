package com.miscellaneous;

import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SecureAccountsTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final AccountsSignInPage signin = AccountsSignInPage.getInstance();

	@testId(test_id = "27038")
	@testCaseName(test_case_name = "B-28626 Frontpage - Implementation of the secure accounts site")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-28626 Frontpage - Implementation of the secure accounts site - login page and Logout page")
	public void verify_the_login_logout_url() throws Exception {
		homepage_instance.click_SignIn();
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "login_accounts_url"));
		signin.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.verify_sign_out();
		invokeBrowser(xmlReader(ENVIRONMENT, "logout_FP_url"));
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "BaseURL"));
	}

	@testId(test_id = "27038")
	@testCaseName(test_case_name = "B-28626 Frontpage - Implementation of the secure accounts site")
	@Test(groups = { "DESKTOP",
			"TABLET" }, description = "B-28626 Frontpage - Implementation of the secure accounts site - Register page and Login page")
	public void verify_the_FP_login_register_url() throws Exception {
		invokeBrowser(xmlReader(ENVIRONMENT, "login_FP_url"));
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "login_accounts_url"));
		invokeBrowser(xmlReader(ENVIRONMENT, "register_FP_url"));
		assertEquals(getCurrentUrl(), xmlReader(ENVIRONMENT, "register_accounts_url"));
	}
}
