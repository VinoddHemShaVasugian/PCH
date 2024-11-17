package com.pch.automation.steps;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.MyAccount;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SignInPage;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class NavigationSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();

	HeaderAndUninavPage headerAndUninavPage;
	SignInPage signInPage;
	LightboxPage lightboxPage;
	MyAccount accountPage;

	/**
	 * Navigate to application.
	 */
	@Step
	public void navigateToSWApplication() {
		waitFor(2);
		getDriver().get(configInstance.getEnvironmentProperty("SWAppUrl"));
	}

	/**
	 * Navigate to Segmentation page
	 */
	@Step
	public void navigateToSegmentationPage() {
		getDriver().get(configInstance.getEnvironmentProperty("RFSegmentationUrl"));
	}

	/**
	 * Go to FP application.
	 */
	@Step
	public void goToFPApplication() {
		getDriver().manage().deleteAllCookies();
		getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
		maximizeWindow();
	}

	/**
	 * Navigate to FP application.
	 * 
	 * @author vsankar
	 */
	@Step
	public String navigateToFPApplication(String... append) {
		String query = "";
		if ((append.length == 1) && (append[0].contains(configInstance.getEnvironmentProperty("AppUrl")))) {
			getDriver().get(append[0]);
		} else if ((append.length == 1) && !(append[0].contains(configInstance.getEnvironmentProperty("AppUrl")))) {
			getDriver().get(configInstance.getEnvironmentProperty("AppUrl") + "?" + append[0]);
		} else if (append.length > 1) {
			query = configInstance.getEnvironmentProperty("AppUrl") + "?";
			for (int i = 0; i < append.length; i++) {
				if (i == 0) {
					query = query + append[i];
				} else {
					query = query + "&" + append[i];
				}
			}
			maximizeWindow();
			getDriver().get(query);
		}

		lightboxPage.closeOptinLightbox();
		return getDriver().getCurrentUrl();
	}

	/**
	 * Navigate to current URL.
	 * 
	 * @author vsankar
	 */
	@Step
	public String navigateToCurrentURL(String... append) {
		if ((append.length == 1) && (append[0].contains(getDriver().getCurrentUrl()))) {
			getDriver().get(append[0]);
		} else if ((append.length == 1) && !(append[0].contains(getDriver().getCurrentUrl()))) {
			getDriver().get(getDriver().getCurrentUrl() + "&" + append[0]);
		}
		lightboxPage.closeOptinLightbox();
		return getDriver().getCurrentUrl();
	}

	/**
	 * Navigate to FP application.
	 */
	@Step
	public void navigateToFPApplication() {
		getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
		maximizeWindow();
		lightboxPage.closeWelcomeLightbox();
	}

	/**
	 * Maximize the browser window
	 */
	@Step
	public void maximizeWindow() {
		getDriver().manage().window().maximize();
	}

	/**
	 * Navigate to search and win site as recent user
	 * 
	 */
	@Step
	public void loginToFPApplicationAsRecentUser() {
		navigateToFPApplication(AppConfigLoader.getInstance().getEnvironmentProperty("AppUrl") + "?em="
				+ RegistrationPage.regGenerator.getEmail() + "&gmt=" + RegistrationPage.regGenerator.getGmt());
		getDriver().navigate().refresh();
	}

	@Step
	public void redirectToTokenHistoryPage() {
		headerAndUninavPage.clickTokenHistory();
		if (signInPage.verifyPasswordField()) {
			signInPage.login(RegistrationPage.userPassword);
		}
		accountPage.verifyMyAccount();
	}

	public void loginToFrontpageSiteAsRecognizedUser() {
		navigateToFPApplication();
		signInPage.login(AppConfigLoader.getInstance().getEnvironmentProperty("UserName"),
				AppConfigLoader.getInstance().getEnvironmentProperty("Password"));
	}

	public void navigateToEDLApplication() {
		getDriver().get(configInstance.getEnvironmentProperty("EDLAppUrl"));
		maximizeWindow();
	}
}
