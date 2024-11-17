package com.pch.automation.stepdefinitions.cs;

import org.jbehave.core.annotations.Given;

import com.pch.automation.steps.cs.SegmentationSteps;
import com.pch.automation.steps.jm.SearchAdminSteps;

import net.thucydides.core.annotations.Steps;

/**
 * The Class SegementationScenarioSteps.
 */
public class SegementationScenarioSteps {

	/** The segmentation steps. */
	@Steps
	SegmentationSteps segmentationSteps;

	/**
	 * Assigning segment to the user by Code
	 */
	@Given("Assign segment by code '$segmentCode'")
	public void assignSegmentByCode(String segmentCode) {
		segmentationSteps.assignSegmentByCode(segmentCode);
	}

	/**
	 * Assigning segment to the user by Name
	 */
	@Given("Assign segment by name '$segmentName'")
	public void assignSegmentByName(String segmentName) {
		segmentationSteps.assignSegmentByName(SearchAdminSteps.getArticleDetails().get(segmentName));
	}
}