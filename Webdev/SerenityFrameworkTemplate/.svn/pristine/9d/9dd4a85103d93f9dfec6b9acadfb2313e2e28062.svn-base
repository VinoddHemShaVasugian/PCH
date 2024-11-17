/*
 * @Author pvadivelu
 * @Version 1.0.0
 * PCH Serenity Framework Template
 */
package com.pch.sample.steps;

import java.util.ArrayList;

import org.junit.Assert;

import com.pch.sample.pages.SamplePage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;


/**
 * The Class SampleSteps.
 */
public class SampleSteps extends ScenarioSteps{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sample page. */
	SamplePage samplePage;

	/**
	 * Click recent winner.
	 */
	@Step
	public void clickRecentWinner(){
		samplePage.clickRecentWinnerLink();
	}
	
	/**
	 * Verifying recent winner names.
	 */
	@Step
	public void verifyingRecentWinnerNames(){
		ArrayList<String> winnerNames = samplePage.getRecentWinnersList();
		for(String winner : winnerNames){
			System.out.println(winner);	
		}
		Assert.assertTrue("Verify winners should be available more than one", winnerNames.size() > 0);
		
	}
}
