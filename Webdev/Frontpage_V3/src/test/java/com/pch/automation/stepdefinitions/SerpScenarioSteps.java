package com.pch.automation.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import com.pch.automation.steps.jm.FpAdminSteps;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Steps;

public class SerpScenarioSteps {

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
				FpAdminSteps.getArticleDetails().get(serpMessage).trim(), serpSteps.verifySerpMessage(serpMessage));
	}

	/**
	 * To verify ecomm ads in Search result page
	 * 
	 */
	@Then("Verify SERP page")
	public void verifySerpPage() {
		Assert.assertTrue("DFP Banner is not displayed in serp page", serpSteps.verifyDfpBanner());
		Assert.assertTrue("Search Results are not rendered on serp page", serpSteps.verifySearchResults());
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
		navSteps.navigateToFPApplication(queryParam);
	}

	@Then("Verify the regular search activity details in the search activity log table")
	public void verifySearchActivityDetails() throws SQLException {
		LinkedHashMap<String, String> map = serpSteps.getSearchDetails(RegistrationPage.regGenerator.getGmt());
		assertEquals("Search Type value is mismatched", map.get("SearchType"), "0");
		assertEquals("Edid value is mismatched", map.get("Edid"), edid);
		assertEquals("Search URL value is mismatched", map.get("Current_Url"), "search");
		assertEquals("UserSignedIn value is mismatched", map.get("UserSignedIn"), "1");
		assertEquals("BlingoId value is mismatched", map.get("BlingoID"), "7");
		assertEquals("Device Type value is mismatched", map.get("DeviceType"),
				AppConfigLoader.deviceType.toUpperCase());
		assertEquals("TSRC value is mismatched", map.get("TSRC"), tsrc);
		assertEquals("TSRC2 value is mismatched", map.get("TSRC2"), tsrc2);
		assertEquals("SegId value is mismatched", map.get("SegID"), segid);
		assertEquals("Segment value is mismatched", map.get("Segment"), segment);
		assertEquals("CBL value is mismatched", map.get("cbl"), "frontpage");
		assertEquals("FP flag value is mismatched", map.get("FrontPageFlag"), "true");
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

	@Then("Do a refresh and verify the first search and consecutive tokens")
	public void verifySearchTokens() {
		tokensAfterSearch = serpSteps.getTokens();
		assertTrue("Search Tokens are failed to award", tokensAfterSearch > tokensBeforeSearch);
	}

	@Then("Verify the ad market place search activity details in the search activity log table")
	public void verifyAdTileSearchActivityDetails() throws SQLException {
		LinkedHashMap<String, String> map = serpSteps.getSearchDetails(RegistrationPage.regGenerator.getGmt());
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
		assertEquals("GS term value is mismatched", map.get("GS_Category"), tileDetails[0]);
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
		assertEquals("GS term value is mismatched", map.get("GS_Category"), tileDetails[0].toLowerCase());
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
		assertTrue("message mismatched", serpSteps.bigFishconfirmMsg().contains("Looking to win the big SuperPrize?"));
	}
}