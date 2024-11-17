package com.pch.automation.steps;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.fp.ArticlesPage;

import net.thucydides.core.annotations.Step;

public class ArticlesSteps {

	ArticlesPage articlePage;

	@Step
	public void navigateToArticlePage() {
		articlePage.clickAnyArticle();
	}

	@Step
	public String getStoryId() {
		return articlePage.getStoryId();
	}

	@Step
	public String getMainCategoryType() {
		return articlePage.getMainCategoryType();
	}

	@Step
	public String getSubCategoryType() {
		return articlePage.getSubCategoryType();
	}

	@Step
	public String getArticleTitle() {
		return articlePage.getArticleTitle();
	}

	@Step
	public String getStoryLogColumnValue(String columnName) throws IOException, SQLException {
		LinkedHashMap<String, String> logDetails = DatabaseHelper.getInstance()
				.getStoryLogDetails(RegistrationPage.regGenerator.getEmail());
		return logDetails.get(columnName);
	}

	@Step
	public LinkedHashMap<String, String> getStoryLogDetails() throws IOException, SQLException {
		return DatabaseHelper.getInstance().getStoryLogDetails(RegistrationPage.regGenerator.getEmail());
	}

	@Step
	public void clickNextArticle() {
		articlePage.clickNextArticle();
	}

	@Step
	public boolean verifyNextArticlePresence() {
		return articlePage.verifyNextArticlePresence();
	}

	@Step
	public boolean verifyTextCompleteRegEarnTokens() {
		return articlePage.verifyTextCompleteRegEarnTokens();
	}
}