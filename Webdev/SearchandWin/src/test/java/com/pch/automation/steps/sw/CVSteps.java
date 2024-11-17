package com.pch.automation.steps.sw;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.CouchbasePage;
import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.MyAccount;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SerpPage;
import com.pch.automation.pages.SignInPage;
import com.pch.automation.pages.sw.HomePage;
import com.pch.automation.steps.jm.SearchAdminSteps;
import com.pch.automation.utilities.RandomGenerator;
import com.pch.automation.utilities.WebServiceClient;

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
	CouchbasePage cbPage;

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
		String descKey = SearchAdminSteps.getArticleDetails().get(descPropName.toLowerCase() + position);
		String tokenKey = SearchAdminSteps.getArticleDetails().get(tokenPropName.toLowerCase() + position);
		return accountPage.verifyTokenTransactionsDetails(descKey, tokenKey, Integer.parseInt(position));
	}

	@Step
	public void loginCB() {
		cbPage.login();
	}

	@Step
	public void searchDoc() throws IOException {
		String userId = WebServiceClient.getInstance().getUserIdFromEmail(RegistrationPage.regGenerator.getEmail());
		cbPage.searchDocument(userId);
	}

	@Step
	public void modifyLastSearchDate(String dayCount) throws IOException {
		LocalDate date = LocalDate.now().minusDays(Integer.parseInt(dayCount));
		cbPage.modifyLastSearchDate(date.toString());
	}

	@Step
	public void modifyLastVisit(String dayCount) throws IOException {
		cbPage.modifyLastVisits("3");
	}

	@Step
	public void updateSearchCount(String dayCount) throws IOException, SQLException {
		DatabaseHelper.getInstance().updateDailySearchCount(RegistrationPage.regGenerator.getEmail(), dayCount);
	}

	@Step
	public void saveDoc() {
		cbPage.saveDocument();
	}
}
