package com.pch.automation.steps;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.MyAccount;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SignInPage;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class NavigationSteps extends ScenarioSteps {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private final AppConfigLoader configInstance = AppConfigLoader.getInstance();
	EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();

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
		getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
	}

	/**
	 * Navigate to Segmentation page
	 */
	@Step
	public void navigateToSegmentationPage() {
		getDriver().get(configInstance.getEnvironmentProperty("RFSegmentationUrl"));
	}

	/**
	 * Go to application.
	 */
	@Step
	public void goToSWApplication() {
		getDriver().manage().deleteAllCookies();
		getDriver().get(configInstance.getEnvironmentProperty("AppUrl"));
	}

	/**
	 * Navigate to sw application.
	 * 
	 * @author vsankar
	 */
	@Step
	public String navigateToSWApplication(String... append) {
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
			System.out.println("Current URl: " + getDriver().getCurrentUrl() + "&" + append[0]);
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
		getDriver().get(configInstance.getEnvironmentProperty("FPAppUrl"));
	}

	/**
	 * Maximize the browser window
	 */
	@Step
	public void maximizeWindow() {
		if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
			getDriver().manage().window().maximize();
		} else if (environmentVariables != null
				&& !environmentVariables.getProperty("webdriver.driver").equalsIgnoreCase("provided")) {
			getDriver().manage().window().setSize(new Dimension(414, 736));
		}
	}

	/**
	 * Navigate to search and win site as recent user
	 * 
	 */
	@Step
	public void loginToSWApplicationAsRecentUser() {
		navigateToSWApplication(AppConfigLoader.getInstance().getEnvironmentProperty("AppUrl") + "?em="
				+ RegistrationPage.regGenerator.getEmail() + "&gmt=" + RegistrationPage.regGenerator.getGmt());
		getDriver().navigate().refresh();
		lightboxPage.closeOptinLightbox();
	}

	@Step
	public void redirectToTokenHistoryPage() {
		headerAndUninavPage.clickTokenHistory();
		if (signInPage.verifyPasswordField()) {
			signInPage.login(RegistrationPage.userPassword);
		}
		accountPage.verifyMyAccount();
	}

	/**
	 * Navigate to new window and close it then back to parent window.
	 */
	@Step
	public void closeNewlyOpenedTab() {
		WebDriver driver = getDriver();
		String parentHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentHandle)) {
				driver.switchTo().window(handle).close();
				break;
			}
		}
		driver.switchTo().window(parentHandle);
		driver.navigate().refresh();
	}
}
