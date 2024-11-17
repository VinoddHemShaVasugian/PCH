package com.pch.sw.tests.misc;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.PchDotComPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.User;

public class RemoveWelcomeMessage extends BaseTest {

	private HomePage webBasePage;
	private PchDotComPage pchDotComPage;
	private User randomUser_1;

	@Test(testName = "RemoveWelcomeMessageTheFirstLogin", description = "TestID='16236'-Verifying welcome message in Search after FirstLogin in pch.com")
	public void removeWelcomeMessageTheFirstLogin() {

		String environment = Environment.getEnvironment();
		webBasePage.load("http://www." + environment + ".pch.com");

		// Register through PCH.com
		pchDotComPage.registerUser(randomUser_1);
		pchDotComPage.closeTutorialHolder();

		// complete registration - create password
		webBasePage.createPasswordLightBox().enterPasswordandConfirm("testing");
		webBasePage.closeUserLevelLightBox();

		// validating welcome light box - we should not see any welcome light
		// box.
		webBasePage.load();
		Assert.assertFalse(webBasePage.isOptinLightBoxPresent(), "found welcome lightbox");
		CustomLogger.log("didn't found welcome lighbox");

	}

}
