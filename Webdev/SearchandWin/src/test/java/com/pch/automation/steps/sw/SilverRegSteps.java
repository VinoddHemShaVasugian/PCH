package com.pch.automation.steps.sw;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.RegistrationPage;

public class SilverRegSteps {
	RegistrationPage reg;
	HeaderAndUninavPage uninav;

	public void fullReg() {
		uninav.clickCompleteRegistration();
		reg.compelteSilverUser();
	}

}
