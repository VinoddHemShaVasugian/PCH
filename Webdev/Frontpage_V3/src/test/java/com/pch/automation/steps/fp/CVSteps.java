package com.pch.automation.steps.fp;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.MyAccount;
import com.pch.automation.pages.SerpPage;
import com.pch.automation.pages.SignInPage;
import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.steps.jm.FpAdminSteps;
import com.pch.automation.utilities.RandomGenerator;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class CVSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	HomePage homePage;
	SerpPage serpPage;
	HeaderAndUninavPage headerPage;
	SignInPage signInPage;
	MyAccount accountPage;

	@Step
	public void searchAndVerifySERPPage() {
		homePage.search(RandomGenerator.randomAlphabetic(5));
		serpPage.verifySERPCompletely();
	}

	@Step
	public int getCVDayCount() {
		return serpPage.getCVCompletedDays();
	}

	@Step
	public int verifyCVMsgFromTokenHistory(String descPropName, String tokenPropName, String position) {
		String descKey = FpAdminSteps.getArticleDetails().get(descPropName.toLowerCase() + position);
		String tokenKey = FpAdminSteps.getArticleDetails().get(tokenPropName.toLowerCase() + position);
		return accountPage.verifyTokenTransactionsDetails(descKey, tokenKey, Integer.parseInt(position));
	}
}
