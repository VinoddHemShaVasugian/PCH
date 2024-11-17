/*
 * @Author pvadivelu
 * @Version 1.0.0
 * PCH Serenity Framework Template
 */
package com.pch.sample.steps;

import com.pch.sample.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;


/**
 * The Class NavigationSteps.
 */
public class NavigationSteps extends ScenarioSteps{

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Navigate to application.
	 */
	@Step
	public void navigateToApplication(){
		getDriver().manage().window().maximize();
		getDriver().get(AppConfigLoader.getInstance().sampleURL);
	}
	
}
