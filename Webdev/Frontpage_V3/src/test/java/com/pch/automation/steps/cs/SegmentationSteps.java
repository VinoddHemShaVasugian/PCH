package com.pch.automation.steps.cs;

import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SegmentationPage;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.fp.OptinSteps;
import com.pch.automation.steps.jm.FpAdminSteps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * The Class SegmentationSteps.
 */
public class SegmentationSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	SegmentationPage segPage;
	@Steps
	NavigationSteps navigationSteps;

	@Steps
	FpAdminSteps searchAdmin;

	@Steps
	OptinSteps optinSteps;

	@Step
	public void assignSegmentByCode(String segment) {
		navigationSteps.navigateToSegmentationPage();
		segPage.setSegmentByCode(RegistrationPage.regGenerator.getEmail(), segment);
		navigationSteps.navigateToFPApplication();

	}

	@Step
	public void assignSegmentByName(String segment) {
		navigationSteps.navigateToSegmentationPage();
		segPage.setSegmentByName(RegistrationPage.regGenerator.getEmail(), segment);
		navigationSteps.navigateToFPApplication();
	}
}