package com.pch.kenofrontend.stepdefinitions;

import java.io.IOException;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.json.JSONException;

import com.pch.kenofrontend.steps.LeaderboardSteps;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

public class LeaderboardScenarioSteps {
	
	@Steps
	LeaderboardSteps leaderboard;
		
	@Then("top token earner for the day is displayed on top of the leaderboard")
	public void compareMobileLeaderboardData(){
		leaderboard.compareMobileLeaderboardData();
	}
	
	@Then("the user's rank, firstname last initial, status and Tokens are displayed on the leaderboard")
	public void verifyUserDataInLeaderboard(){
		leaderboard.verifyUserDataInLeaderboard();
	}
	
	@Then("verify Yesterday's winners are displayed in leaderboard")
	public void verifyYesterdayWinnersInLeaderboard(){
		leaderboard.verifyYesterdayWinnersInLeaderboard();
	}

	@Then("verify user details are displayed in my daily token total")
	public void verifyMyDailyTokenTotal(){
		leaderboard.verifyMyDailyTokenTotal();
	}
	@Then("verify information for user without password is appearing in leaderboard")
	public void verifyMyDailyTokenTotalUserWithoutPassword() {
		leaderboard.verifyMyDailyTokenTotalUserWithoutPassword();
	}
	@Then("verify 'Token Leader Prize Details' link on PCH Rewards")
	public void verifyLeaderboardPrizeDetails(){
		leaderboard.verifyLeaderboardPrizeDetails();
	}
	
	@Then("top token earner for the day is displayed on top of the desktop leaderboard")
	public void compareDesktopLeaderboardData(){
		leaderboard.compareDesktopLeaderboardData();
	}
	
	@Then("verify 'Token Leader Rewards Details' button in Token leaderboard")
	public void verifyLeaderboardRewardDetails(){
		leaderboard.verifyLeaderboardRewardDetails();
	}
	
	@Then("verify that My Daily Token Total is not present")
	public void verifyNoDailyTokenTotal(){
		leaderboard.verifyNoDailyTokenTotal();
	}
}
