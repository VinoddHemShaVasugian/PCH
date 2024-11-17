package com.pch.automation.stepdefinitions.jm;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import com.pch.automation.steps.jm.SearchAdminSteps;
import com.pch.automation.steps.NFSPSteps;

import net.thucydides.core.annotations.Steps;

/**
 * 
 * @author mperumal
 *
 */
public class SearchAdminScenarioSteps {

	@Steps
	SearchAdminSteps searchAdmin;
	@Steps
	NFSPSteps nfspSteps;

	/**
	 * Login to the Search Admin
	 */
	@Given("Login to the Search Admin")
	@Then("Login to the Search Admin")
	public void loginSearchAdmin() {
		searchAdmin.loginSearchAdmin();
	}

	/**
	 * Do a search for the article
	 */
	@Given("Do a search for an article '$articleName'")
	@Then("Do a search for an article '$articleName'")
	public void searchArticle(String articleName) {
		searchAdmin.searchArticle(articleName);
	}

	/**
	 * Retrieve the description,notice,tokens,condition info from the article
	 */
	@Given("Retrieve the description,notice,tokens,condition info from the article '$position'")
	@Then("Retrieve the description,notice,tokens,condition info from the article '$position'")
	public void articleInfo(String position) {
		searchAdmin.articleInfo(position);
	}

	/**
	 * Modify the field by its label name with the given value
	 */
	@Given("Modify the field by its label name '$labelName' with the given value '$replacingValue'")
	public void modifyFieldByLabelName(String labelName, String value) {
		searchAdmin.modifyFieldByLabelName(labelName, value);
	}

	/**
	 * Modify the field by its key name with the given value
	 */
	@Given("Modify the field by its key name '$keyName' with the given value '$replacingValue'")
	@Then("Modify the field by its key name '$keyName' with the given value '$replacingValue'")
	public void modifyFieldByKeyName(String keyName, String value) {
		searchAdmin.modifyFieldByKeyName(keyName, value);
	}

	/**
	 * Get the field value by its label name
	 */
	@Given("Get the field value by its label name '$labelName'")
	public void getFieldByLabelName(String labelName) {
		searchAdmin.getFieldByLabelName(labelName);
	}

	/**
	 * Get the field value by its label name
	 */
	@Given("Get the field value by its label name with position '$labelName', '$position'")
	public void getFieldByLabelName(String labelName, String position) {
		searchAdmin.getFieldByLabelNameWithPosition(labelName, position);
	}

	/**
	 * Get the field value by its key name
	 */
	@Given("Get the field value by its key name '$keyName'")
	public void getFieldByKeyName(String keyName) {
		searchAdmin.getFieldByKeyName(keyName);
	}

	/**
	 * Get the drop down field value by its label name
	 */
	@Given("Get the dropdown field value by its label name '$labelName'")
	public void getDropdownFieldByLabelName(String labelName) {
		searchAdmin.getDropdownFieldByLabelName(labelName);
	}

	/**
	 * Get the drop down field value by its label name and position
	 */
	@Given("Get the dropdown field value by its label name and position '$labelName','$position'")
	public void getDropdownFieldByLabelName(String labelName, String position) {
		searchAdmin.getDropdownFieldByLabelName(labelName, position);
	}
	
	/**
	 * Get the drop down field value by its label name
	 */
	@Given("Get the dropdown field value by its label name with position '$labelName','$position'")
	public void getDropdownFieldByLabelNameBasedOnPosition(String labelName, String position) {
		searchAdmin.getDropdownFieldByLabelName(labelName, position);
	}
	
	/**
	 * Get the field value by its label name based on article
	 */
	@Given("Get the field value by its label name based on article '$articleName','$labelName'")
	public void getValueByLabelNameArticleBased(String articleName, String labelName) {
		searchAdmin.searchArticle(articleName);
		searchAdmin.getFieldByLabelNameArticleBased(articleName, labelName);
	}

	/**
	 * Unpublish the article
	 */
	@Given("Unpublish the article '$articleName'")
	public void unpublishArticle(String articleName) {
		searchAdmin.searchArticle(articleName);
		searchAdmin.unpublishArticle();
	}

	/**
	 * Publish the article
	 */
	@Given("Publish the article '$articleName'")
	public void publishArticle(String articleName) {
		searchAdmin.searchArticle(articleName);
		searchAdmin.publishArticle();
	}
}
