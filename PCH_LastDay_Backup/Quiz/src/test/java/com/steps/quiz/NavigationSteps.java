package com.steps.quiz;

import com.pages.quiz.HeaderAndUninavPage;
import com.pages.quiz.MyAccountPage;
import com.pages.quiz.RegistrationPage;
import com.pages.quiz.SignInPage;
import com.pch.quiz.utilities.AppConfigLoader;

import com.pch.quiz.utilities.WaitHelper;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import java.util.concurrent.TimeUnit;

public class NavigationSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();

	HeaderAndUninavPage headerAndUninavPage;
	SignInPage signInPage;
	MyAccountPage accountPage;

	/**
	 * Go to Quiz application.
	 */
	@Step
	public void goToQuizApplication() {
		getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
		getDriver().manage().deleteAllCookies();
		maximizeWindow();
	}

	/**
	 * Go to Quiz tab.
	 */
	@Step
	public void goToQuizTab() {
		getDriver().get(configInstance.getEnvironmentProperty("QuizTabUrl"));
		getDriver().manage().deleteAllCookies();
		maximizeWindow();
	}

	/**
	 * Navigate to Quiz application.
	 */
	@Step
	public void navigateToQuizApplication() {
		getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
		maximizeWindow();
	}

	/**
	 * Navigate to Quiz tab.
	 */
	@Step
	public void navigateToQuizTab() {
		getDriver().get(configInstance.getEnvironmentProperty("QuizTabUrl"));
		maximizeWindow();
	}

	/**
	 * Navigate to Quiz app health page.
	 */

	@Step
	public void navigateToQuizesappHealth() throws InterruptedException {
		WaitHelper.forceWait(1000);
		getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
		getDriver().get(getDriver().getCurrentUrl() + "apphealth".trim());
		getDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		maximizeWindow();
	}

	/**
	 * Navigate to Quiz application.
	 * 
	 * @author vsankar
	 */
	@Step
	public String navigateToQuizApplication(String... append) {
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
			getDriver().get(getDriver().getCurrentUrl() + "/?" + append[0]);
		}
		return getDriver().getCurrentUrl();
	}

	/**
	 * Navigate to Token history page.
	 * 
	 * @author vsankar
	 */
	@Step
	public void redirectToTokenHistoryPage() {
		headerAndUninavPage.clickTokenHistory();
		if (signInPage.verifyPasswordField()) {
			signInPage.login(RegistrationPage.userPassword);
		}
		accountPage.verifyMyAccount();
	}

	/**
	 * Maximize the browser window
	 */
	@Step
	public void maximizeWindow() {
		getDriver().manage().window().maximize();
	}

	@Step
	public void loginToQuizSiteAsRecognizedUser() {
		navigateToQuizApplication();
		signInPage.login(AppConfigLoader.getInstance().getEnvironmentProperty("UserName"),
				AppConfigLoader.getInstance().getEnvironmentProperty("Password"));
	}
}