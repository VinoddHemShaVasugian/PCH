package com.pch.automation.steps.fp;

import com.pch.automation.pages.HeaderAndUninavPage;

public class RewardsUninavSteps {

	HeaderAndUninavPage rewards;

	public void onclickRedeemToken() {
		rewards.verifyRedeemPagenavigation();
	}

	public void onclickLevelupicon() {
		rewards.verifyPlaynowButton();
		rewards.Searchuninav();
	}
}
