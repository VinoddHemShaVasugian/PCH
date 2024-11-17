package com.pch.automation.stepdefinitions;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.pch.automation.steps.NFSPSteps;
import com.pch.automation.steps.jm.FpAdminSteps;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Steps;

/**
 * 
 * @author vsankar
 *
 */
public class NFSPScenarioSteps {
	/** To access nfsp steps. */
	@Steps
	NFSPSteps nfspSteps;

	/**
	 * To Verify NFSP source and segment
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 * 
	 */
	@Then("Verify NFSP source and segment as per admin config '$accessIdJson','$nfsp'")
	public void verifyNFSPSourceAndSegment(String accessIdJson, String nfsp)
			throws JsonProcessingException, IOException {
		if (FpAdminSteps.getArticleDetails().get(nfsp.toLowerCase()) != null) {
			nfsp = FpAdminSteps.getArticleDetails().get(nfsp.toLowerCase());
		}
		if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
			Assert.assertEquals("Actual and expected nfsp source are different", nfsp,
					nfspSteps.getNfspSourceFromPageSource());
			Assert.assertEquals("Actual and expected nfsp segment are different",
					(nfspSteps.getNfspSegmentFromJson(FpAdminSteps.getArticleDetails().get(accessIdJson), nfsp)),
					nfspSteps.getNfspSegmentFromPageSource());
		}
	}

	/**
	 * To Perform six consecutive searches
	 * 
	 */
	@When("Perform consecutive searches '$searchCount'")
	@Then("Perform consecutive searches '$searchCount'")
	public void performConsecutiveSearches(String searchCount) {
		nfspSteps.consecutiveSearches(searchCount);
	}

	/**
	 * To Verify algo NFSP source and segment
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 * 
	 */
	@Then("Verify algo NFSP source and segment as per admin config '$accessIdJson','$algoNfspDesktop','$algoNfspTablet','$algoNfspMobile'")
	public void verifyAlgoNFSPSourceAndSegment(String accessIdJson, String algoNfspDesktop, String algoNfspTablet,
			String algoNfspMobile) throws JsonProcessingException, IOException {
		if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
			Assert.assertEquals("Actual and expected nfsp source are different", algoNfspDesktop,
					nfspSteps.getNfspSourceFromPageSource());
			Assert.assertEquals("Actual and expected nfsp segment are different", (nfspSteps
					.getNfspSegmentFromJson(FpAdminSteps.getArticleDetails().get(accessIdJson), algoNfspDesktop)),
					nfspSteps.getNfspSegmentFromPageSource());
		} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Tablet")) {
			Assert.assertEquals("Actual and expected nfsp source are different", algoNfspTablet,
					nfspSteps.getNfspSourceFromPageSource());
			Assert.assertEquals("Actual and expected nfsp segment are different", (nfspSteps
					.getNfspSegmentFromJson(FpAdminSteps.getArticleDetails().get(accessIdJson), algoNfspTablet)),
					nfspSteps.getNfspSegmentFromPageSource());
		}
	}

	/**
	 * Reset daily search count in db for the user
	 * 
	 * @throws Exception
	 * 
	 */
	@When("Reset daily search count '$searchCount'")
	@Then("Reset daily search count '$searchCount'")
	public void resetDailySearchCount(String searchCount) throws Exception {
		if (FpAdminSteps.getArticleDetails().get(searchCount) != null) {
			nfspSteps.resetDailySearchCount(FpAdminSteps.getArticleDetails().get(searchCount));
		} else {
			nfspSteps.resetDailySearchCount(searchCount);
		}
	}

	/**
	 * Assigning segment to the user based on admin property
	 */
	@Given("Assign the segment based on admin property '$segment'")
	public void getSegmentValue(String keyName) {
		nfspSteps.assignSegmentByAdminProperty(keyName);
	}

	/**
	 * Retrive nfsp from 'segments' JSON based on segment
	 * 
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	@Given("Retrive segmented nfsp values '$segment'")
	public void retriveSegmentedNFSP(String segment) throws Exception {
		nfspSteps.retriveSegmentNFSP(segment);
	}
}
