/*
 * @Author pvadivelu
 * PCH Search and Win and Front Page
 */
package com.pch.automation.steps.sw;

import java.util.ArrayList;

import org.junit.Assert;

import com.pch.automation.pages.sw.RecentWinnerPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;


// TODO: Auto-generated Javadoc
/**
 * The Class RecentWinnerSteps.
 */
public class RecentWinnerSteps extends ScenarioSteps{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The recent winner page. */
	RecentWinnerPage recentWinnerPage;

	/**
	 * Click recent winner.
	 */
	@Step
	public void clickRecentWinner(){
		recentWinnerPage.clickRecentWinnerLink();
	}
	
	/**
	 * Verifying recent winner names.
	 */
	@Step
	public void verifyingRecentWinnerNames(){
		ArrayList<String> winnerNames = recentWinnerPage.getRecentWinnersList();
		for(String winner : winnerNames){
			System.out.println(winner);	
		}
		Assert.assertTrue("Verify winners should be available more than one", winnerNames.size() > 0);
		
	}
}
