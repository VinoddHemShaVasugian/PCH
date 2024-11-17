package com.pch.automation.steps.fp;

import java.io.IOException;
import java.sql.SQLException;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.fp.SweepstakesPage;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class SweepstakeSteps extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AppConfigLoader configProp = AppConfigLoader.getInstance();
	DatabaseHelper dbHelper = DatabaseHelper.getInstance();

	SweepstakesPage sweepsPage;

	@Step
	public boolean verifyEDLHomePage() {
		return sweepsPage.verifyHome();
	}

	@Step
	public boolean verifySweepstakes() {
		return sweepsPage.verifySweepstakes();
	}

	@Step
	public String getSweepsDescription() {
		return sweepsPage.getSweepHomeDescription();
	}

	@Step
	public void clickSweeps() {
		sweepsPage.clickSweepstakes();
	}

	@Step
	public boolean verifySweepsPath(String sweepsPath) {
		if (getDriver().getCurrentUrl().equalsIgnoreCase(sweepsPath)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public void navigateToCRMN(String crmn) {
		getDriver().get(configProp.getEnvironmentProperty("SweepsCRMN") + crmn);
	}

	@Step
	public boolean verifySweepsProgress() {
		return sweepsPage.verifyProgressBar();
	}

	@Step
	public boolean verifySweepsEntryActivity() {
		if (getDriver().getCurrentUrl().equalsIgnoreCase(configProp.getEnvironmentProperty("EDLAppUrl") + "?ec=1")) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public String readSweepsEntryComfirmationMsg() {
		return configProp.msgPropertyFileReader("entryConfirmationMessage");
	}

	@Step
	public String getSweepsCommpletedStatusMessage() {
		return sweepsPage.getSweepstakesCompleteMessage();
	}

	@Step
	public String getSweepsPropertyFromDB() throws IOException, SQLException {
		return dbHelper.getSweepReturnDetails(RegistrationPage.regGenerator.getEmail());
	}
}