package com.pch.kenofrontend.steps;

import com.pch.kenofrontend.pages.Leaderboard;
import net.thucydides.core.annotations.Step;

public class LeaderboardSteps {

	Leaderboard leaderBoard;

	@Step
	public void verifyUserDataInLeaderboard() {
		leaderBoard.verifyUserDataInLeaderboard();

	}
	
	@Step
	public void compareMobileLeaderboardData(){
		leaderBoard.compareMobileLeaderboardData();
	}
	
	@Step
	public void verifyYesterdayWinnersInLeaderboard(){
		leaderBoard.verifyYesterdayWinnersInLeaderboard();
	}

	@Step
	public void verifyMyDailyTokenTotal(){
		leaderBoard.verifyMyDailyTokenTotal();
	}
	
	@Step
	public void verifyMyDailyTokenTotalUserWithoutPassword() {
		leaderBoard.verifyMyDailyTokenTotalUserWithoutPassword();
	}
}
