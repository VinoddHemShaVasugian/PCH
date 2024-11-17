/*
 * @Author pvadivelu
 * @Version 1.0.0
 * PCH Serenity Framework Template
 */
package com.pch.sample.stepdefinitions;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.pch.sample.steps.SampleApiSteps;

import net.thucydides.core.annotations.Steps;


/**
 * The Class SampleAPIScenarioSteps.
 */
public class SampleAPIScenarioSteps {

	/** The sample api steps. */
	@Steps
	SampleApiSteps sampleApiSteps;
	
	/**
	 * When get the active segments from segmentation API.
	 */
	@When("Get the Active Segments from Segmentation API")
	public void whenGetTheActiveSegmentsFromSegmentationAPI() {
		sampleApiSteps.callActiveSegmentation();
	}

	/**
	 * Then verify the active segments.
	 */
	@Then("Verify the Active Segments")
	public void thenVerifyTheActiveSegments() {
		sampleApiSteps.verifyActiveSegments();
	}
}
