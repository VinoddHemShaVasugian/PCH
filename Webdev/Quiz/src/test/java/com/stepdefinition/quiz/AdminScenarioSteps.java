package com.stepdefinition.quiz;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

import com.steps.quiz.AdminSteps;

import net.thucydides.core.annotations.Steps;

/**
 * Step definitions for Joomla admin - Login and Article pages
 * 
 * @author vsankar
 *
 */
public class AdminScenarioSteps {

	@Steps
	AdminSteps quizAdmin;

	/**
	 * Login to the Quiz Admin
	 */
	@Given("Login to the Quiz Admin")
	@Then("Login to the Quiz Admin")
	public void loginQuizAdmin() {
		quizAdmin.loginQuizAdmin();
	}

	/**
	 * Do a search for the article
	 */
	@Given("Do a search for an article '$articleName'")
	@Then("Do a search for an article '$articleName'")
	public void searchArticle(String articleName) {
		quizAdmin.searchArticle(articleName);
	}

	/**
	 * Modify the field by its label name with the given value
	 */
	@Given("Modify the field by its label name '$labelName' with the given value '$replacingValue'")
	public void modifyFieldByLabelName(String labelName, String value) {
		quizAdmin.modifyFieldByLabelName(labelName, value);
	}

	/**
	 * Do a search for article field Value
	 */
	@Then("Get the field value by its val name '$valName'")
	public void searchValByArticle(String valName) {
		quizAdmin.getValFromArticle(valName);
	}

	/**
	 * Modify the field by its key name with the given value
	 */
	@Given("Modify the field by its key name '$keyName' with the given value '$replacingValue'")
	@Then("Modify the field by its key name '$keyName' with the given value '$replacingValue'")
	public void modifyFieldByKeyName(String keyName, String value) {
		quizAdmin.modifyFieldByKeyName(keyName, value);
	}

	/**
	 * Get the field value by its label name
	 */
	@Given("Get the field value by its label name '$labelName'")
	public void getFieldByLabelName(String labelName) {
		quizAdmin.getFieldByLabelName(labelName);
	}

	/**
	 * Get the field value by its label name based on position
	 */
	@Given("Get the field value by its label name with position '$labelName','$position'")
	public void getFieldByLabelName(String labelName, String position) {
		quizAdmin.getFieldByLabelNameWithPosition(labelName, position);
	}

	/**
	 * Get the field value by its key name
	 */
	@Given("Get the field value by its key name '$keyName'")
	public void getFieldByKeyName(String keyName) {
		quizAdmin.getFieldByKeyName(keyName);
	}

	/**
	 * Get the drop down field value by its label name
	 */
	@Given("Get the dropdown field value by its label name '$labelName'")
	public void getDropdownFieldByLabelName(String labelName) {
		quizAdmin.getDropdownFieldByLabelName(labelName);
	}

	/**
	 * Get the drop down field value by its label name and position
	 */
	@Given("Get the dropdown field value by its label name and position '$labelName','$position'")
	public void getDropdownFieldByLabelName(String labelName, String position) {
		quizAdmin.getDropdownFieldByLabelName(labelName, position);
	}

	/**
	 * Get the drop down field value by its label name
	 */
	@Given("Get the dropdown field value by its label name with position '$labelName','$position'")
	public void getDropdownFieldByLabelNameBasedOnPosition(String labelName, String position) {
		quizAdmin.getDropdownFieldByLabelName(labelName, position);
	}

	/**
	 * Get the field value by its label name based on article
	 */
	@Given("Get the field value by its label name based on article '$articleName','$labelName'")
	public void getValueByLabelNameArticleBased(String articleName, String labelName) {
		quizAdmin.searchArticle(articleName);
		quizAdmin.getFieldByLabelNameArticleBased(articleName, labelName);
	}

	/**
	 * Retrieve the description,notice,tokens,condition info from the article
	 */
	@Given("Retrieve the description,notice,tokens,condition info from the article '$position'")
	public void articleInfoBasedOnPosition(String position) {
		quizAdmin.articleInfo(position);
	}

	/**
	 * Unpublish the article
	 */
	@Given("Unpublish the article '$articleName'")
	public void unpublishArticle(String articleName) {
		quizAdmin.searchArticle(articleName);
		quizAdmin.unpublishArticle();
	}

	/**
	 * Publish the article
	 */
	@Given("Publish the article '$articleName'")
	public void publishArticle(String articleName) {
		quizAdmin.searchArticle(articleName);
		quizAdmin.publishArticle();
	}
}
