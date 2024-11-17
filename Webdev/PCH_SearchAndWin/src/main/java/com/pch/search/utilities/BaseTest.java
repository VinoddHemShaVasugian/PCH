package com.pch.search.utilities;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.pch.search.pages.web.CentralServicesPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.WebHeaderPage;

public abstract class BaseTest {
	// private static String screenshotDirectory =
	// System.getProperty("user.dir") + "\\Reports\\Screenshots\\";
	private static final Common common = new Common();

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method m, ITestContext context) throws Exception {
		// CustomLogger.createContext();
		System.out.println(":::::::::::::::::Inside Before Method::::::::::::::::::::::::::");
		Environment.loadProperties(context);
		System.out.println("[TEST START] Test Name: " + m.getName());
		System.out.println(String.format("Logging start for execution of %s method from class %s", m.getName(),
				this.getClass().getName()));
		Field[] fields = this.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				if (Action.class.isAssignableFrom(field.getType())) {
					field.setAccessible(true);
					CustomLogger.log("Creating page instance of " + field.getName());
					field.set(this, PageFactory.getPageInstance(field.getType()));
				}
			}
			// PageFactory.getBrowserNDriverMap().get(Environment.getBrowserType()
			// + Thread.currentThread().getId())
			// .getDriver().findElement(By.cssSelector("a[evergage-closeicon=evergage-closeicon]")).click();
		} catch (WebDriverException wde) {
			CustomLogger.logException(wde);
			throw new WebDriverException(wde);
		}
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass(ITestContext context) throws IllegalArgumentException, IllegalAccessException {
		System.out.println((":::::::::::::::::Inside Before Class::::::::::::::::::::::::::"));
		context.setAttribute("className", this.getClass().getName());
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().equals(User.class)) {
				field.setAccessible(true);
				User user = ((Users) common.xmlToJava("/Users.xml")).getUser(field.getName());
				if (user == null) {
					throw new Error(field.getName() + " not present in Users class or Users.xml.");
				}
				if (user.getFirstname().equalsIgnoreCase("RANDOM"))
					user.setFirstname(Common.getRandomUserName("FN-"));

				if (user.getLastname().equalsIgnoreCase("RANDOM"))
					user.setLastname(Common.getRandomUserName("LN-"));

				if (user.getEmail().equalsIgnoreCase("RANDOM"))
					user.setEmail(Common.generateRandomID("QaAuto") + "@pchmail.com");

				field.set(this, user);
			}
		}
	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		System.out.println(":::::::::::::::::Inside After Class::::::::::::::::::::::::::");
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		System.out.println(":::::::::::::::::Inside After Suite::::::::::::::::::::::::::");
	}

	// TO get String from a class and to display it on Excel reports
	protected void setManualComment(String _comment) {
		String COMMENTS = "comments";
		if (System.getProperty(COMMENTS) != null && !System.getProperty(COMMENTS).equals(""))
			System.setProperty(COMMENTS, System.getProperty(COMMENTS) + _comment);
		else
			System.setProperty(COMMENTS, _comment);
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(Method m, ITestResult result, ITestContext context) throws IOException {
		System.out.println(":::::::::::::::::Inside After Method::::::::::::::::::::::::::");
		if (result.getStatus() == ITestResult.FAILURE) {
			CustomLogger.log("[TEST FAILED] " + getTestInfo(result));
			CustomLogger.log(" >> Caused by : " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			CustomLogger.log("[TEST PASSED] " + getTestInfo(result));
		} else if (result.getStatus() == ITestResult.SKIP) {
			CustomLogger.log("[TEST SKIPPED] " + getTestInfo(result));
		}

		try {
			String browserName = Environment.getBrowserType();
			BrowserDriverImpl browserInstance = PageFactory.getBrowserNDriverMap()
					.get(browserName + Thread.currentThread().getId());
			if (browserInstance != null) {
				System.out.println("Closing browser " + browserName);
				PageFactory.getBrowserNDriverMap().remove(browserName + Thread.currentThread().getId());
				browserInstance.quit();
				System.out.println("Closed the Browser");
			}
		} catch (Exception wde) {
			CustomLogger.log("After Method Exception :");
			CustomLogger.logException(wde);
		} finally {
			System.out.println("Clear the Custom Logger Context Starts");
			// result.setAttribute("logfilePath", CustomLogger.resetContext());
			Environment.refreshPropertyMap(context);
			System.out.println("Clearing Custom Logger Context is done");
		}
	}

	protected void loginToSearch(User user1) {
		WebHeaderPage headerPage = null;
		RegistrationPage regPage = null;
		HomePage homePage = null;
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (WebHeaderPage.class.isAssignableFrom(field.getType())) {
					headerPage = (WebHeaderPage) (field.get(this));
				} else if (RegistrationPage.class.isAssignableFrom(field.getType())) {
					regPage = (RegistrationPage) (field.get(this));
				} else if (HomePage.class.isAssignableFrom(field.getType())) {
					homePage = (HomePage) (field.get(this));
				}
			}

			Assert.assertNotNull(headerPage,
					"No HeaderPage object found, please declare a HeaderPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(regPage,
					"No RegistrationPage object found, please declare a RegistrationPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(homePage, "No HomePage object found, please declare a HomePage object in test class"
					+ this.getClass().getName());

			homePage.load();
			int userStatus = headerPage.loginToSearch(user1);
			Assert.assertTrue(userStatus != -2, "Unhandled Scenario !");
			if (userStatus == -1) {
				/*
				 * User is not present already. So register this new user.
				 */
				headerPage.clickRegisterBtn();
				regPage.enterUserDetails(user1);
				regPage.clickSubmitButton();

				CustomLogger.log("Registration completed");
				try {
					PageFactory.getBrowserNDriverMap()
							.get(Environment.getBrowserType() + Thread.currentThread().getId()).getDriver()
							.findElement(By.cssSelector("a[evergage-closeicon=evergage-closeicon]")).click();
				} catch (Exception e) {

				}
				// Close the optin
				if (!user1.isSubscribedToSearch()) {
					CustomLogger.log("Closing the optin light box");
					homePage.closeOptinLigthBox();
				}

				homePage.closeUserLevelLightBox();

			} else if (userStatus == 0) {
				/*
				 * Password incorrect.
				 */
				Assert.fail("Password is not correct");
			} else if (userStatus == 2) {
				/*
				 * User logged in was miniRegistered. so delegate logging to
				 * minireg logging method.
				 */
				loginToSearchAsMiniregUser(user1);
			}

		} catch (IllegalAccessException iae) {
			CustomLogger.logException(iae);
			Assert.fail(iae.getMessage());
			iae.printStackTrace();
		}
	}

	protected void loginToSearchOptout(User user1) {
		WebHeaderPage headerPage = null;
		RegistrationPage regPage = null;
		HomePage homePage = null;
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (WebHeaderPage.class.isAssignableFrom(field.getType())) {
					headerPage = (WebHeaderPage) (field.get(this));
				} else if (RegistrationPage.class.isAssignableFrom(field.getType())) {
					regPage = (RegistrationPage) (field.get(this));
				} else if (HomePage.class.isAssignableFrom(field.getType())) {
					homePage = (HomePage) (field.get(this));
				}
			}

			Assert.assertNotNull(headerPage,
					"No HeaderPage object found, please declare a HeaderPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(regPage,
					"No RegistrationPage object found, please declare a RegistrationPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(homePage, "No HomePage object found, please declare a HomePage object in test class"
					+ this.getClass().getName());

			homePage.load();
			int userStatus = headerPage.loginToSearch(user1);
			Assert.assertTrue(userStatus != -2, "Unhandled Scenario !");
			if (userStatus == -1) {
				/*
				 * User is not present already. So register this new user.
				 */
				headerPage.clickRegisterBtn();
				regPage.enterUserDetailsOptOut(user1);
				regPage.clickSubmitButton();

				CustomLogger.log("Registration completed");
				try {
					PageFactory.getBrowserNDriverMap()
							.get(Environment.getBrowserType() + Thread.currentThread().getId()).getDriver()
							.findElement(By.cssSelector("a[evergage-closeicon=evergage-closeicon]")).click();
				} catch (Exception e) {

				}
			} else if (userStatus == 0) {
				/*
				 * Password incorrect.
				 */
				Assert.fail("Password is not correct");
			} else if (userStatus == 2) {
				/*
				 * User logged in was miniRegistered. so delegate logging to
				 * minireg logging method.
				 */
				loginToSearchAsMiniregUser(user1);
			}

		} catch (IllegalAccessException iae) {
			CustomLogger.logException(iae);
			Assert.fail(iae.getMessage());
			iae.printStackTrace();
		}
	}

	protected void loginToSearchSnWOptIn(User user1) {
		WebHeaderPage headerPage = null;
		RegistrationPage regPage = null;
		HomePage homePage = null;
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (WebHeaderPage.class.isAssignableFrom(field.getType())) {
					headerPage = (WebHeaderPage) (field.get(this));
				} else if (RegistrationPage.class.isAssignableFrom(field.getType())) {
					regPage = (RegistrationPage) (field.get(this));
				} else if (HomePage.class.isAssignableFrom(field.getType())) {
					homePage = (HomePage) (field.get(this));
				}
			}

			Assert.assertNotNull(headerPage,
					"No HeaderPage object found, please declare a HeaderPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(regPage,
					"No RegistrationPage object found, please declare a RegistrationPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(homePage, "No HomePage object found, please declare a HomePage object in test class"
					+ this.getClass().getName());

			homePage.load();
			int userStatus = headerPage.loginToSearch(user1);
			Assert.assertTrue(userStatus != -2, "Unhandled Scenario !");
			if (userStatus == -1) {
				/*
				 * User is not present already. So register this new user.
				 */
				headerPage.clickRegisterBtn();
				regPage.enterUserDetailsSnWOptIn(user1);
				regPage.clickSubmitButton();

				CustomLogger.log("Registration completed");
				try {
					PageFactory.getBrowserNDriverMap()
							.get(Environment.getBrowserType() + Thread.currentThread().getId()).getDriver()
							.findElement(By.cssSelector("a[evergage-closeicon=evergage-closeicon]")).click();
				} catch (Exception e) {

				}
			} else if (userStatus == 0) {
				/*
				 * Password incorrect.
				 */
				Assert.fail("Password is not correct");
			} else if (userStatus == 2) {
				/*
				 * User logged in was miniRegistered. so delegate logging to
				 * minireg logging method.
				 */
				loginToSearchAsMiniregUser(user1);
			}

		} catch (IllegalAccessException iae) {
			CustomLogger.logException(iae);
			Assert.fail(iae.getMessage());
			iae.printStackTrace();
		}
	}

	protected void loginToSearchPchComOptIn(User user1) {
		WebHeaderPage headerPage = null;
		RegistrationPage regPage = null;
		HomePage homePage = null;
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (WebHeaderPage.class.isAssignableFrom(field.getType())) {
					headerPage = (WebHeaderPage) (field.get(this));
				} else if (RegistrationPage.class.isAssignableFrom(field.getType())) {
					regPage = (RegistrationPage) (field.get(this));
				} else if (HomePage.class.isAssignableFrom(field.getType())) {
					homePage = (HomePage) (field.get(this));
				}
			}

			Assert.assertNotNull(headerPage,
					"No HeaderPage object found, please declare a HeaderPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(regPage,
					"No RegistrationPage object found, please declare a RegistrationPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(homePage, "No HomePage object found, please declare a HomePage object in test class"
					+ this.getClass().getName());

			homePage.load();
			int userStatus = headerPage.loginToSearch(user1);
			Assert.assertTrue(userStatus != -2, "Unhandled Scenario !");
			if (userStatus == -1) {
				/*
				 * User is not present already. So register this new user.
				 */
				headerPage.clickRegisterBtn();
				regPage.enterUserDetailsPchComOptIn(user1);
				regPage.clickSubmitButton();

				CustomLogger.log("Registration completed");
				try {
					PageFactory.getBrowserNDriverMap()
							.get(Environment.getBrowserType() + Thread.currentThread().getId()).getDriver()
							.findElement(By.cssSelector("a[evergage-closeicon=evergage-closeicon]")).click();
				} catch (Exception e) {

				}
			} else if (userStatus == 0) {
				/*
				 * Password incorrect.
				 */
				Assert.fail("Password is not correct");
			} else if (userStatus == 2) {
				/*
				 * User logged in was miniRegistered. so delegate logging to
				 * minireg logging method.
				 */
				loginToSearchAsMiniregUser(user1);
			}

		} catch (IllegalAccessException iae) {
			CustomLogger.logException(iae);
			Assert.fail(iae.getMessage());
			iae.printStackTrace();
		}
	}

	protected void loginToSearchAsMiniregUser(User user1) {
		WebHeaderPage headerPage = null;
		RegistrationPage regPage = null;
		HomePage homePage = null;
		CentralServicesPage csPage = null;

		Field[] fields = this.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (WebHeaderPage.class.isAssignableFrom(field.getType())) {
					headerPage = (WebHeaderPage) (field.get(this));
				} else if (RegistrationPage.class.isAssignableFrom(field.getType())) {
					regPage = (RegistrationPage) (field.get(this));
				} else if (HomePage.class.isAssignableFrom(field.getType())) {
					homePage = (HomePage) (field.get(this));
				} else if (CentralServicesPage.class.isAssignableFrom(field.getType())) {
					csPage = (CentralServicesPage) (field.get(this));
				}
			}

			Assert.assertNotNull(headerPage,
					"No HeaderPage object found, please declare a HeaderPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(regPage,
					"No RegistrationPage object found, please declare a RegistrationPage object in test class."
							+ this.getClass().getName());
			Assert.assertNotNull(homePage, "No HomePage object found, please declare a HomePage object in test class"
					+ this.getClass().getName());
			Assert.assertNotNull(csPage,
					"No CentralServicesPage object found, please declare a CentralServicesPage object in test class"
							+ this.getClass().getName());
			homePage.load();
			int userStatus = headerPage.loginToSearch(user1);
			Assert.assertTrue(userStatus != -2, "Unhandled Scenario !");
			if (userStatus == -1) {
				/*
				 * User is not present already. create this new user using
				 * MiniReg.
				 */
				csPage.createMiniRegUser(user1);
				loginToSearchAsMiniregUser(user1);
			} else if (userStatus == 0) {
				/*
				 * Password incorrect.
				 */
				Assert.fail("Password is not correct");
			} else if (userStatus == 2) {
				homePage.closeWelcomeToSearchLightBoxAfterWait();
			}

		} catch (IllegalAccessException iae) {
			CustomLogger.logException(iae);
			Assert.fail(iae.getMessage());
			iae.printStackTrace();
		}
	}

	@BeforeSuite
	public void beforeSuite() throws Exception {

		System.out.println(":::::::::::::::::Inside Before Suite::::::::::::::::::::::::::");
		// Clear up the Temp. files
		// Runtime runtime = Runtime.getRuntime();
		// Process process = runtime.exec("cmd /c BatchFiles\\TempDelete.bat");
		// process.waitFor();

		/*
		 * Deletion of previously present log and screenshot files.
		 * 
		 * File reportDir = new File(System.getProperty("user.dir") +
		 * "//Reports"); File logsDir = new File(reportDir, "Logs"); File
		 * screenShotDir = new File(reportDir, "ScreenShots");
		 * 
		 * if (reportDir.exists()) { for (File reportFile :
		 * reportDir.listFiles()) { if (reportFile.isFile()) { if
		 * (!reportFile.delete()) { System.out.println( String.format(
		 * "Unable to remove %s, please close if opened.",
		 * reportFile.getName())); System.exit(1); } } } if (logsDir.exists()) {
		 * for (File logFile : logsDir.listFiles()) { if (!logFile.delete()) {
		 * System.out.println( String.format(
		 * "Unable to remove %s, please close if opened.", logFile.getName()));
		 * System.exit(1); } } } else { logsDir.mkdir(); }
		 * 
		 * if (screenShotDir.exists()) { for (File scrnshtFile :
		 * screenShotDir.listFiles()) { if (!scrnshtFile.delete()) {
		 * System.out.println( String.format(
		 * "Unable to remove %s, please close if opened.",
		 * scrnshtFile.getName())); System.exit(1); } } } else {
		 * screenShotDir.mkdir(); }
		 * 
		 * } else { reportDir.mkdir(); logsDir.mkdir(); screenShotDir.mkdir(); }
		 */
	}

	/**
	 * Gets the test information from TestNG {@link ITestResult}
	 * 
	 * @param result
	 *            test result from TestNG execution
	 * @return tetsInfo test name, description and parameters formatted in a
	 *         String
	 */
	private String getTestInfo(ITestResult result) {

		ITestNGMethod method = result.getMethod();

		// set test name and description
		StringBuilder testInfo = new StringBuilder();
		testInfo.append("Test Name: ");
		testInfo.append(method.getMethodName());
		testInfo.append(" - Description: ");
		testInfo.append(method.getDescription());
		testInfo.append(" - QC Test IDs: ");
		// testInfo.append(getTestLinkIdsAsString(method));
		testInfo.append(" Parameters: ");
		testInfo.append((result.getParameters().toString()));

		return testInfo.toString();
	}
}
