package com.pch.automation.steps;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SerpPage;
import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.utilities.RandomGenerator;
import com.pch.automation.utilities.WebServiceClient;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class SerpSteps extends ScenarioSteps {

	private static final long serialVersionUID = 1L;
	/** The home page. */
	HomePage homePage;
	/** The search results page. */
	SerpPage serpPage;
	HeaderAndUninavPage uniNav;
	DatabaseHelper dbHelper = DatabaseHelper.getInstance();

	@Step
	public void searchAndVerifySERPPage() {
		homePage.search(RandomGenerator.randomAlphabetic(5));
		serpPage.verifySERPCompletely();
	}

	@Step
	public boolean verifyResultsForSpecialSearchTerms(String searchTerm) {
		homePage.search(RandomGenerator.randomAlphaNumeric(5) + searchTerm);
		return serpPage.verifyNoResultsFound();
	}

	@Step
	public String verifySerpMessage(String serpMessage) {
		homePage.search(RandomGenerator.randomAlphabetic(5));
		return serpPage.getSerpMessage();
	}

	@Step
	public boolean verifyDfpBanner() {
		return serpPage.verifyDfpBanner();
	}

	@Step
	public boolean verifySearchResults() {
		return serpPage.verifySearchResults();
	}

	@Step
	public int getSearchBars() {
		return serpPage.getSearchBars();
	}

	@Step
	public boolean verifyAreYouLookingForSection() {
		return serpPage.verifyAreYouLookingForSection();
	}

	@Step
	public LinkedHashMap<String, String> getSearchDetails(String gmt) throws SQLException {
		String query = "select SearchType,UID,Edid,Current_Url,UserSignedIn, Nfsp,BlingoID,DeviceType,TSRC,TSRC2,SegID,Segment,cbl,FrontPageFlag, GS_Flag, GS_Category, GS_Position from search_activity where UID='"
				+ gmt + "'";
		return dbHelper.getMulitpleColumnValues(query);
	}

	@Step
	public int getAdWindowCount() {
		return getDriver().getWindowHandles().size();
	}

	@Step
	public int getTokens() {
		waitFor(30);
		getDriver().navigate().refresh();
		return uniNav.getTokens();
	}

	@Step
	public int getDailySearchCount() throws IOException, NumberFormatException, SQLException {
		String userId = WebServiceClient.getInstance().getUserIdFromEmail(RegistrationPage.regGenerator.getEmail());
		String query = "select value from sso_user_data where user='" + userId + "' and item = 'dailysearchcount';";
		return Integer.parseInt(dbHelper.executeQuery(query));
	}

	@Step
	public void searchAndVerifySERPMessageforBadterms() {
		homePage.search("3080");

	}

	@Step
	public void bigfishad()

	{
		serpPage.verifyBigfishTag();
	}

	public String bigFishconfirmMsg() {
		return serpPage.getSerpSuspiciousMessage();

	}
}
