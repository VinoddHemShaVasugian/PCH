package com.pch.automation.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.steps.NFSPSteps;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.SerpSteps;
import com.pch.automation.steps.cs.SegmentationSteps;
import com.pch.automation.steps.jm.SearchAdminSteps;
import com.pch.automation.utilities.AppConfigLoader;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Steps;

public class SerpScenarioSteps extends PageObject {

	@Steps
	SerpSteps serpSteps;
	@Steps
	NavigationSteps navSteps;
	@Steps
	SegmentationSteps segSteps;
	@Steps
	NFSPSteps nfspSteps;

	String edid;
	String tsrc;
	String tsrc2;
	String segid;
	String segment;
	String tileDetails[];
	int tokensBeforeSearch;
	int tokensAfterSearch;

	/**
	 * To perform a search and verify serp page
	 * 
	 */
	@When("Do a search and Verify SERP page")
	@Then("Do a search and Verify SERP page")
	public void searchAndVerifySERPPage() {
		serpSteps.searchAndVerifySERPPage();
	}

	/**
	 * To verify SERP message in Search result page
	 * 
	 */
	@When("Do a search and Verify serp message '$serpMessage'")
	public void verifySerpMessage(String serpMessage) {
		Assert.assertEquals("Actual and expected serp messages are different",
				SearchAdminSteps.getArticleDetails().get(serpMessage.toLowerCase()).trim(),
				serpSteps.verifySerpMessage(serpMessage));
	}

	/**
	 * To verify ecomm ads in Search result page
	 * 
	 */
	@Then("Verify SERP page")
	public void verifySerpPage() {
		Assert.assertTrue("DFP Banner is not displayed in serp page", serpSteps.verifyDfpBanner());
		Assert.assertTrue("Search Results are not rendered on serp page", serpSteps.verifySearchResults());
		Assert.assertEquals("Header/Footer Search bar is not visible", 2, serpSteps.getSearchBars());
		Assert.assertFalse("Are you looking for section is not visible", serpSteps.verifyAreYouLookingForSection());
	}

	/**
	 * To verify ecomm ads in Search result page
	 * 
	 */
	@When("Do a search and Verify search results")
	public void verifyResultsForSpecialSearchTerms() {
		Assert.assertFalse("Search Results are not rendered for search terms contains special characters",
				serpSteps.verifyResultsForSpecialSearchTerms("$#%^$#&"));
		Assert.assertFalse("Search Results are not rendered for search terms contains special characters",
				serpSteps.verifyResultsForSpecialSearchTerms("Symptoms of RA I?m fingers"));
		Assert.assertFalse("Search Results are not rendered for search terms contains special characters",
				serpSteps.verifyResultsForSpecialSearchTerms("Steiner Military?Marine 10x50"));
	}

	@When("Login to the application with the EDID, TSRC, TSRC2, Segid as '$edid', '$tsrc', '$tsrc2' and '$segid'")
	public void loginToTheAppWithTsrc(String edid, String tsrc, String tsrc2, String segid) {
		String queryParam = "email=" + RegistrationPage.regGenerator.getEmail() + "&e="
				+ RegistrationPage.regGenerator.getGmt() + "&tsrc=" + tsrc + "&tsrc2=" + tsrc2 + "&segid=" + segid
				+ "&edid=" + edid;
		this.edid = edid;
		this.tsrc = tsrc;
		this.tsrc2 = tsrc2;
		this.segid = segid;
		navSteps.navigateToSWApplication(queryParam);
	}

	@Then("Verify the regular search activity details in the search activity log table")
	public void verifySearchActivityDetails() throws SQLException {
		LinkedHashMap<String, String> map = serpSteps.getSearchDetails(RegistrationPage.regGenerator.getGmt());
		assertEquals("Search Type value is mismatched", map.get("SearchType"), "0");
		assertEquals("Edid value is mismatched", map.get("Edid"), edid);
		assertEquals("Search URL value is mismatched", map.get("Current_Url"), "pysearch");
		assertEquals("UserSignedIn value is mismatched", map.get("UserSignedIn"), "1");
		assertEquals("BlingoId value is mismatched", map.get("BlingoID"), "1");
		assertEquals("Device Type value is mismatched", map.get("DeviceType"),
				AppConfigLoader.deviceType.toUpperCase());
		assertEquals("TSRC value is mismatched", map.get("TSRC"), tsrc);
		assertEquals("TSRC2 value is mismatched", map.get("TSRC2"), tsrc2);
		assertEquals("SegId value is mismatched", map.get("SegID"), segid);
		assertEquals("Segment value is mismatched", map.get("Segment"), segment);
		assertEquals("CBL value is mismatched", map.get("cbl"), "search");
		assertEquals("FP flag value is mismatched", map.get("FrontPageFlag"), "false");
		assertEquals("Nfsp value is mismatched", map.get("Nfsp"),
				nfspSteps.getNfspSourceFromPageSource().toLowerCase());
	}

	/**
	 * Assign the scratch card segment to the user
	 */
	@Given("Assign the user to the given '$segment' segment")
	public void assignSegment(String segmentName) {
		this.segment = segmentName;
		segSteps.assignSegmentByCode(segmentName);
	}

	@Then("Do a search by click on ad market place tiles and Verify SERP page")
	public void clickAdmarktePlaceTiles() {
		tokensBeforeSearch = serpSteps.getTokens();
		tileDetails = serpSteps.clickAdMarketPlaceTile();
		navSteps.closeNewlyOpenedTab();
	}

	@Then("Do a refresh and verify the first search and consecutive tokens")
	public void verifySearchTokens() {
		waitFor(30);
		tokensAfterSearch = serpSteps.getTokens();
		assertTrue("Search Tokens are failed to award", tokensAfterSearch > tokensBeforeSearch);
	}

	@Then("Verify the daily search count as '$count' and '$prop' property contains the ad tile id")
	public void verifyDailySearchCount(int count, String propName)
			throws NumberFormatException, IOException, SQLException {
		assertTrue("Search Tokens are failed to award", count == serpSteps.getDailySearchCount());
		assertTrue("Ad tile id is mismatched on the db property",
				serpSteps.getAdTrackCountProp(propName).trim().contains(tileDetails[2].trim()));
	}

	@Then("Click on the same ad tile and verify no tokens are awarded")
	public void verifySearchTokensForSameAdTile() {
		serpSteps.clickAdMarketPlaceTileByPosition(Integer.parseInt(tileDetails[1]));
		assertEquals("On click ad tile failed to open in a new window", 2, serpSteps.getAdWindowCount());
		navSteps.closeNewlyOpenedTab();
		assertTrue("Search Tokens are awarded mistakenly for same ad tile in a day",
				serpSteps.getTokens() == tokensAfterSearch);
	}

	@Then("Click on the same ad tile for configured number of times based on property '$limit'")
	public void clickSameAdTileForMultipleTimes(String count) {
		for (int i = 1; i < Integer.parseInt(SearchAdminSteps.getArticleDetails().get(count)); i++) {
			serpSteps.clickAdMarketPlaceTileByPosition(Integer.parseInt(tileDetails[1]));
			assertEquals("On click ad tile failed to open in a new window", 2, serpSteps.getAdWindowCount());
			navSteps.closeNewlyOpenedTab();
		}
	}

	@Then("Verify the absence of the clicked tile")
	public void clickSameAdTileForMultipleTimes() {
		assertTrue("Ad Tile is been displayed even after the configured the click limit count",
				serpSteps.verifyAdTilesOnPage(tileDetails[0]));
	}

	@Then("Verify the ad market place search activity details in the search activity log table")
	public void verifyAdTileSearchActivityDetails() throws SQLException {
		LinkedHashMap<String, String> map = serpSteps.getSearchDetails(RegistrationPage.regGenerator.getGmt());
		waitABit(8000); 
		assertEquals("Search Type value is mismatched", map.get("SearchType"), "1");
		assertEquals("Edid value is mismatched", map.get("Edid"), edid);
		assertEquals("Search URL value is mismatched", map.get("Current_Url"), "admarketplace");
		assertEquals("UserSignedIn value is mismatched", map.get("UserSignedIn"), "1");
		assertEquals("BlingoId value is mismatched", map.get("BlingoID"), "-1");
		assertEquals("Device Type value is mismatched", map.get("DeviceType"),
				AppConfigLoader.deviceType.toUpperCase());
		assertEquals("TSRC value is mismatched", map.get("TSRC"), tsrc);
		assertEquals("TSRC2 value is mismatched", map.get("TSRC2"), tsrc2);
		assertEquals("SegId value is mismatched", map.get("SegID"), segid);
		assertEquals("Segment value is mismatched", map.get("Segment"), segment);
		assertEquals("CBL value is mismatched", map.get("cbl"), "search");
		assertEquals("FP flag value is mismatched", map.get("FrontPageFlag"), "false");
		assertEquals("GS flag value is mismatched", map.get("GS_Flag"), "true");
		assertTrue("GS term value is mismatched", map.get("GS_Category").contains(tileDetails[0]));
		assertEquals("GS term position value is mismatched", map.get("GS_Position"), tileDetails[1]);
	}

	@Then("Verify the pch internal tile search activity details in the search activity log table")
	public void verifyInternalTileSearchActivityDetails() throws SQLException {
		LinkedHashMap<String, String> map = serpSteps.getSearchDetails(RegistrationPage.regGenerator.getGmt());
		assertEquals("Search Type value is mismatched", map.get("SearchType"), "2");
		assertEquals("Edid value is mismatched", map.get("Edid"), edid);
		assertEquals("Search URL value is mismatched", map.get("Current_Url"), "pchinternal");
		assertEquals("UserSignedIn value is mismatched", map.get("UserSignedIn"), "1");
		assertEquals("BlingoId value is mismatched", map.get("BlingoID"), "-1");
		assertEquals("Device Type value is mismatched", map.get("DeviceType"),
				AppConfigLoader.deviceType.toUpperCase());
		assertEquals("TSRC value is mismatched", map.get("TSRC"), tsrc);
		assertEquals("TSRC2 value is mismatched", map.get("TSRC2"), tsrc2);
		assertEquals("SegId value is mismatched", map.get("SegID"), segid);
		assertEquals("Segment value is mismatched", map.get("Segment"), segment);
		assertEquals("CBL value is mismatched", map.get("cbl"), "search");
		assertEquals("FP flag value is mismatched", map.get("FrontPageFlag"), "false");
		assertEquals("GS flag value is mismatched", map.get("GS_Flag"), "true");
		assertTrue("GS term value is mismatched", map.get("GS_Category").contains(tileDetails[0].toLowerCase()));
		assertEquals("GS term position value is mismatched", map.get("GS_Position"), tileDetails[1]);
	}

	@When("Do search with suspicious Terms")
	public void doSearchWithSuspiciousTerms() {
		serpSteps.searchAndVerifySERPMessageforBadterms();
	}

	@Then("Verify Bigfish ad on serp page")
	public void verifybigfishad() {
		serpSteps.bigfishad();
	}

	@Then("Verify Bigfish Serp message")
	public void verifyBigFishExpectedserpmesg() {
		assertTrue("BigFish message mismatched", serpSteps.bigFishMsg().contains("Looking to win the big SuperPrize?"));
	}

}