package com.pch.automation.steps.sw;

import com.pch.automation.pages.HeaderAndUninavPage;

import net.thucydides.core.annotations.Step;

public class UninavSteps {
	HeaderAndUninavPage uninav;

	@Step
	public void onclickRedeemToken() {
		uninav.verifyRedeemPagenavigation();
	}

	@Step
	public void onclickLevelupicon() {
		uninav.verifyPlaynowButton();
		uninav.Searchuninav();
	}

	@Step
	public void infoPage() {
		uninav.verifyinfopages();
	}

	@Step
	public boolean verifySigninAndRegister() {
		if (uninav.verifyRegister() && uninav.verifySignin()) {
			return true;
		} else {
			return false;
		}
	}
}