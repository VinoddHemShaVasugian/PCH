package com.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/***
 * 
 * Listener for Portal automation test suites
 * 
 * @author Maniganda Perumal
 * 
 *
 */
public class ReporterListener extends TestListenerAdapter implements IReporter, ITestListener {

	private long suiteStartTime = 0;
	private long suiteEndTime = 0;
	private long suiteTotalTime = 0;

	// Exception Map Declaration
	ArrayList<ITestNGMethod> methodExceptionList;
	LinkedHashMap<IResultMap, ArrayList<ITestNGMethod>> failedMethodMap = new LinkedHashMap<IResultMap, ArrayList<ITestNGMethod>>();
	private String testScreenshotPath = System.getProperty("user.dir") + "//screenshots//";

	private ConsolidatedHTMLReport consolidatedReport = new ConsolidatedHTMLReport();
	private TestWiseHTMLReport testwiseReport = new TestWiseHTMLReport();

	/**
	 * 
	 * Override the genreateReport() of IReporter TestNG listener to collect the
	 * execution results of the test suite
	 * 
	 * @author Maniganda Perumal S
	 * 
	 */
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public void generateReport(List xmlSuites, List suites, String outputDirectory) {

		// For each suite
		for (Object suiteObj : suites) {

			// create folders to archive Test Reports and Screenshots
			consolidatedReport.archiveTestReports();
			consolidatedReport.archiveScreenshots();
			consolidatedReport.archiveLogs();
			testwiseReport.exceptionFiltersInReport();
			ISuite suite = ((ISuite) suiteObj);
			consolidatedReport.createSuiteReport(suite.getName());
			int[] count = consolidatedReport.toGetMethodCount(suite);
			consolidatedReport.suiteOverAllExecutionDetails(
					consolidatedReport.convertLongToCanonicalLengthOfTime(suiteTotalTime), count);
			consolidatedReport.suiteReportHeader();

			Map<String, ISuiteResult> testsAll = suite.getResults();
			IResultMap failedTests = null;
			Collection<ITestNGMethod> failedMethods = null;
			IResultMap passedTests = null;
			Collection<ITestNGMethod> passedMethods = null;
			IResultMap skippedTests = null;
			Collection<ITestNGMethod> skippedMethods = null;
			// For each test tag
			for (Map.Entry<String, ISuiteResult> tests : testsAll.entrySet()) {
				ISuiteResult suiteResult = tests.getValue();
				ITestContext testContext = suiteResult.getTestContext();
				ITestNGMethod[] methods = testContext.getAllTestMethods();
				int total_no_of_class_in_test = 0;
				List<ITestNGMethod> allTestMethods = new ArrayList<ITestNGMethod>(Arrays.asList(methods));

				// failed tests
				failedTests = testContext.getFailedTests();
				failedMethods = failedTests.getAllMethods();

				// passed tests
				passedTests = testContext.getPassedTests();
				passedMethods = passedTests.getAllMethods();

				// skipped tests
				skippedTests = testContext.getSkippedTests();
				skippedMethods = skippedTests.getAllMethods();
				total_no_of_class_in_test += testContext.getCurrentXmlTest().getClasses().size();

				ArrayList<String> class_name_list = new ArrayList<String>();
				for (int i = 1; i <= total_no_of_class_in_test; i++) {
					String generate_test_report_unique = null;
					int total_method_count_of_class = 0;
					int passed_method_count_of_class = 0;
					int failed_method_count_of_class = 0;
					int skipped_method_count_of_class = 0;
					String method_declaring_class_name = null;
					long start_time = 0;
					long end_time = 0;
					for (ITestNGMethod methodName : allTestMethods) {
						method_declaring_class_name = methodName.getMethod().getDeclaringClass().getSimpleName();
						if (generate_test_report_unique == null
								&& !class_name_list.contains(method_declaring_class_name)) {
							testwiseReport.createTestReportHTML(method_declaring_class_name, false);
							total_method_count_of_class += 1;
							class_name_list.add(method_declaring_class_name);
							generate_test_report_unique = method_declaring_class_name;
							Set<ITestResult> testResultSet;
							if (passedMethods.contains(methodName)) {
								passed_method_count_of_class += 1;
								testResultSet = passedTests.getResults(methodName);
								for (ITestResult testResult : testResultSet) {
									testwiseReport.appendingTestMethodsResult(total_method_count_of_class, methodName,
											testResult, "PASS");
									start_time += testResult.getStartMillis();
									end_time += testResult.getEndMillis();
								}
							} else if (failedMethods.contains(methodName)) {
								failed_method_count_of_class += 1;
								testResultSet = failedTests.getResults(methodName);
								for (ITestResult testResult : testResultSet) {
									testwiseReport.appendingTestMethodsResult(total_method_count_of_class, methodName,
											testResult, "FAIL");
									start_time += testResult.getStartMillis();
									end_time += testResult.getEndMillis();
								}
							} else if (skippedMethods.contains(methodName)) {
								skipped_method_count_of_class += 1;
								testResultSet = skippedTests.getResults(methodName);
								for (ITestResult testResult : testResultSet) {
									testwiseReport.appendingTestMethodsResult(total_method_count_of_class, methodName,
											testResult, "SKIP");
									start_time += testResult.getStartMillis();
									end_time += testResult.getEndMillis();
								}
							}
						} else if (generate_test_report_unique != null
								&& generate_test_report_unique.equals(method_declaring_class_name)
								&& class_name_list.contains(method_declaring_class_name)) {
							total_method_count_of_class += 1;
							Set<ITestResult> testResultSet;
							if (passedMethods.contains(methodName)) {
								passed_method_count_of_class += 1;
								testResultSet = passedTests.getResults(methodName);
								for (ITestResult testResult : testResultSet) {
									testwiseReport.appendingTestMethodsResult(total_method_count_of_class, methodName,
											testResult, "PASS");
									start_time += testResult.getStartMillis();
									end_time += testResult.getEndMillis();
								}
							} else if (failedMethods.contains(methodName)) {
								failed_method_count_of_class += 1;
								testResultSet = failedTests.getResults(methodName);
								for (ITestResult testResult : testResultSet) {
									testwiseReport.appendingTestMethodsResult(total_method_count_of_class, methodName,
											testResult, "FAIL");
									start_time += testResult.getStartMillis();
									end_time += testResult.getEndMillis();
								}
							} else if (skippedMethods.contains(methodName)) {
								skipped_method_count_of_class += 1;
								testResultSet = skippedTests.getResults(methodName);
								for (ITestResult testResult : testResultSet) {
									testwiseReport.appendingTestMethodsResult(total_method_count_of_class, methodName,
											testResult, "SKIP");
									start_time += testResult.getStartMillis();
									end_time += testResult.getEndMillis();
								}
							}
						}
					}
					// Close the Test Module report
					testwiseReport.endTestHTMLReport();
					// Append the test module details in Consolidated report
					consolidatedReport.appendingTestStatusOnSuiteReport(i, generate_test_report_unique, 0,
							passed_method_count_of_class, failed_method_count_of_class, skipped_method_count_of_class,
							consolidatedReport.convertLongToCanonicalLengthOfTime(end_time - start_time));
				}
			}
			// Close the Consolidated test report.
			consolidatedReport.endSuiteHTMLReport();
			consolidatedReport.reportForCIDisplay();
			consolidatedReport.screenshotForCIDisplay();
			consolidatedReport.LogsToSendInEmail();
			// Zipping the entire reports to send email.
			TestReportZipformatter.getInstance().createZip();
		}
	}

	/**
	 * 
	 * Override the onFinish method of ITestListener to get the endTime of each test
	 * 
	 * @param context
	 * 
	 */
	public void onFinish(ITestContext context) {
		if (suiteEndTime < context.getEndDate().getTime()) {
			suiteEndTime = context.getEndDate().getTime();
		}
		suiteTotalTime = suiteEndTime - suiteStartTime;
	}

	/**
	 * 
	 * Override the onStart method of ITestListener to get the startTime of each
	 * test
	 * 
	 * @param context
	 * 
	 */

	public void onStart(ITestContext context) {
		suiteStartTime = context.getStartDate().getTime();
	}

	public void onTestFailure(ITestResult result) {
		printTestResults(result);
		String methodName = result.getMethod().getMethodName();
		getScreenShot(methodName);

	}

	public void onTestSkipped(ITestResult result) {
		printTestResults(result);
		String methodName = result.getMethod().getMethodName();
		getScreenShot(methodName);
	}

	private void printTestResults(ITestResult result) {
		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "PASS";
			break;
		case ITestResult.FAILURE:
			status = "FAIL";
			break;
		case ITestResult.SKIP:
			status = "SKIP";
			break;

		}
		System.out.println("Test result:" + status);
	}

	private void getScreenShot(String sSfilename) {

		File folder = new File(testScreenshotPath);
		if (!folder.exists())
			folder.mkdir();
		final Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(500))
				.takeScreenshot(DriverManager.getDriver());
		final BufferedImage image = screenshot.getImage();
		try {
			ImageIO.write(image, "PNG", new File(testScreenshotPath + sSfilename + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
