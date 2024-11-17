package com.pch.frontpage.stepdefinitions;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;

import com.pch.frontpage.steps.HomePageSteps;
import com.pch.frontpage.steps.JoomlaConfigPageSteps;

import net.thucydides.core.annotations.Steps;

public class AwardTokensScenarioSteps {
	
	@Steps
	JoomlaConfigPageSteps joomlaConfigPageSteps;
	HomePageSteps homePageSteps;

	@Given("Navigate to Joomla Admin application to getSeg Id Token value $user_name $password")
	public void givenNavigateToJoomlaAdminApplicationToGetSegIdTokenValue(String user_name, String password) {
		joomlaConfigPageSteps.login(user_name, password);
		/*invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(token_seg_id_article_name);
		seg_id_activity_msg = getAttribute(admin_instance.get_text_field_element_by_label("Description"), "value");
		seg_id_token_value = getAttribute(admin_instance.get_text_field_element_by_label("Tokens"), "value");
		seg_id_value = getAttribute(admin_instance.get_text_field_element_by_label("Conditions"), "value").split("=")[1]
				.trim();*/
	}

	@When("Create a Full Reg user from CS page")
	public void whenCreateAFullRegUserFromCSPage() {
		System.out.println("When-Create a Full Reg user from CS page");
	}

	@When("Login to the application with the Seg Id and do a first search")
	public void whenLoginToTheApplicationWithTheSegIdAndDoAFirstSearch() {
		System.out.println("When-Login to the application with the Seg Id and do a first search");
	}

	@Then("Verify the Seg Id token value for the same user second time")
	public void thenVerifyTheSegIdTokenValueForTheSameUserSecondTime() {
		System.out.println("Then-Verify the Seg Id token value for the same user second time");
	}

	@Then("Update the Daily search count and verify the Seg Id tokens for the same user")
	public void thenUpdateTheDailySearchCountAndVerifyTheSegIdTokensForTheSameUser() {
		System.out.println("Then-Update the Daily search count and verify the Seg Id tokens for the same user");
	}

}
