package com.pch.automation.stepdefinitions.jm;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.jm.FpAdminSteps;
import com.pch.automation.steps.NFSPSteps;

import net.thucydides.core.annotations.Steps;

/**
 * 
 * @author mperumal
 *
 */
public class FpAdminScenarioSteps {

	@Steps
	FpAdminSteps fpAdmin;
	@Steps
	NFSPSteps nfspSteps;

	/**
	 * Login to the Frontpage Admin
	 */
	@Given("Login to the Frontpage Admin")
	@Then("Login to the Frontpage Admin")
	public void loginFrontpageAdmin() {
		fpAdmin.loginFrontpageAdmin();
	}

	/**
	 * Do a search for the article
	 */
	@Given("Do a search for an article '$articleName'")
	@Then("Do a search for an article '$articleName'")
	public void searchArticle(String articleName) {
		fpAdmin.searchArticle(articleName);
	}

	/**
	 * Retrieve the description,notice,tokens,condition info from the article
	 */
	@Given("Retrieve the description,notice,tokens,condition info from the article '$position'")
	@Then("Retrieve the description,notice,tokens,condition info from the article '$position'")
	public void articleInfoBasedOnPosition(String position) {
		fpAdmin.articleInfo(position);
	}

	/**
	 * Modify the field by its label name with the given value
	 */
	@Given("Modify the field by its label name '$labelName' with the given value '$replacingValue'")
	public void modifyFieldByLabelName(String labelName, String value) {
		fpAdmin.modifyFieldByLabelName(labelName, value);
	}

	/**
	 * Modify the field by its key name with the given value
	 */
	@Given("Modify the field by its key name '$keyName' with the given value '$replacingValue'")
	@Then("Modify the field by its key name '$keyName' with the given value '$replacingValue'")
	public void modifyFieldByKeyName(String keyName, String value) {
		fpAdmin.modifyFieldByKeyName(keyName, value);
	}

	/**
	 * Get the field value by its label name
	 */
	@Given("Get the field value by its label name '$labelName'")
	public void getFieldByLabelName(String labelName) {
		fpAdmin.getFieldByLabelName(labelName);
	}

	/**
	 * Get the field value by its label name with position
	 */
	@Given("Get the field value by its label name with position '$labelName', '$position'")
	public void getFieldByLabelName(String labelName, String position) {
		fpAdmin.getFieldByLabelNameWithPosition(labelName, position);
	}

	/**
	 * Get the field value by its key name
	 */
	@Given("Get the field value by its key name '$keyName'")
	public void getFieldByKeyName(String keyName) {
		fpAdmin.getFieldByKeyName(keyName);
	}

	/**
	 * Get the drop down field value by its label name
	 */
	@Given("Get the dropdown field value by its label name '$labelName'")
	public void getDropdownFieldByLabelName(String labelName) {
		fpAdmin.getDropdownFieldByLabelName(labelName);
	}

	/**
	 * Get the drop down field value by its label name and position
	 */
	@Given("Get the dropdown field value by its label name and position '$labelName','$position'")
	public void getDropdownFieldByLabelName(String labelName, String position) {
		fpAdmin.getDropdownFieldByLabelName(labelName, position);
	}

	/**
	 * Get the field value by its label name based on article
	 */
	@Given("Get the field value by its label name based on article '$articleName','$labelName'")
	public void getValueByLabelNameArticleBased(String articleName, String labelName) {
		fpAdmin.searchArticle(articleName);
		fpAdmin.getFieldByLabelNameArticleBased(articleName, labelName);
	}

	/**
	 * Unpublish the article
	 */
	@Given("Unpublish the article '$articleName'")
	public void unpublishArticle(String articleName) {
		fpAdmin.searchArticle(articleName);
		fpAdmin.unpublishArticle();
	}

	/**
	 * Publish the article
	 */
	@Given("Publish the article '$articleName'")
	public void publishArticle(String articleName) {
		fpAdmin.searchArticle(articleName);
		fpAdmin.publishArticle();
	}
}
