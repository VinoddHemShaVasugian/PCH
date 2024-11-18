package com.pch.survey.runnerfiles;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.pch.survey.webdrivers.BrowserStackDriver;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:features/test.feature" }, plugin = { "pretty", "html:target/cucumber-reports/Cucumber.html",
		"com.epam.reportportal.cucumber.ScenarioReporter" }, glue = {
				"com.pch" }, stepNotifications = false, tags = ("@Desktop"))

public class OffersRunnerTest {

	@AfterClass
	public static void killBSLocal() {
		BrowserStackDriver.stopBsLocal();
	}

}