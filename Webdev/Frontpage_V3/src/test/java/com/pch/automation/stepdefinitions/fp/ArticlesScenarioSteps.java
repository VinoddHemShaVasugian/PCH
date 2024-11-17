package com.pch.automation.stepdefinitions.fp;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.pch.automation.steps.ArticlesSteps;
import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.fp.SubCategorySteps;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Steps;

public class ArticlesScenarioSteps {
	@Steps
	ArticlesSteps articleSteps;
	@Steps
	HomepageSteps homeSteps;
	@Steps
	NavigationSteps navSteps;
	@Steps
	SubCategorySteps subCategorySteps;

	LinkedList<String> urlList;
	String storyId;
	String tokenAmount;
	String categoryType;
	LinkedHashMap<String, String> storyLogDetails;

	@When("Click on any article from Top stories")
	public void ArticlePageFunctionality() {
		articleSteps.navigateToArticlePage();
	}

	@Then("Verify the Story log details for featured article and claimed status '$claimedStatus'")
	public void verifyStoryLogFeaturedArticle(String claimedStatus) throws IOException, SQLException {
		storyId = articleSteps.getStoryId();
		categoryType = articleSteps.getMainCategoryType();
		if (claimedStatus.equals("1")) {
			tokenAmount = homeSteps.getUnclaimTokenValue();
			Assert.assertTrue("Token not claimed for the day", homeSteps.clickClaimButton());
		}
		storyLogDetails = articleSteps.getStoryLogDetails();
		Assert.assertEquals("Story Id is not matched in database", storyLogDetails.get("story_id"), storyId);
		if (claimedStatus.equals("1")) {
			Assert.assertEquals("Tokens claimed amount is not matched in database", storyLogDetails.get("tokens"),
					tokenAmount);
		}
		Assert.assertEquals("Tokens claimed status is not matched in database", storyLogDetails.get("claimed"),
				claimedStatus);
		Assert.assertEquals("Category type is not matched in database", storyLogDetails.get("category"), categoryType);
		if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
			Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "D");
		} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Tablet")) {
			Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "T");
		} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Mobile")) {
			Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "M");
		}
	}

	@Then("Verify the Story log details for category page articles")
	public void verifyStoryLogCateforyPageArticles() throws IOException, SQLException {
		LinkedList<String> catagoryURLs = homeSteps.getMainCatagoryMenuUrlList();
		for (String url : catagoryURLs) {
			if (!url.endsWith("more") && !url.endsWith("everydaylife") && !url.contains("video")) {
				navSteps.navigateToFPApplication(url);
				homeSteps.clickFirstArticleLink();
				if (storyId.equals(articleSteps.getStoryId())) {
					articleSteps.clickNextArticle();
					articleSteps.verifyNextArticlePresence();
				}
				storyId = articleSteps.getStoryId();
				tokenAmount = homeSteps.getUnclaimTokenValue();
				categoryType = articleSteps.getMainCategoryType();
				Assert.assertTrue("Token not claimed for the day", homeSteps.clickClaimButton());
				storyLogDetails = articleSteps.getStoryLogDetails();
				Assert.assertEquals("Story Id is not matched in database", storyLogDetails.get("story_id"), storyId);
				Assert.assertEquals("Tokens claimed amount is not matched in database", storyLogDetails.get("tokens"),
						tokenAmount);
				Assert.assertEquals("Tokens claimed status is not matched in database", storyLogDetails.get("claimed"),
						"1");
				Assert.assertEquals("Category type is not matched in database", storyLogDetails.get("category"),
						categoryType);
				if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
					Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "D");
				} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Tablet")) {
					Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "T");
				} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Mobile")) {
					Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "M");
				}
			}
			break;
		}
	}

	@Then("Verify the Story log details for sub category page articles")
	public void verifyStoryLogSubCategoryPageArticles() throws IOException, SQLException {
		LinkedList<String> subCatagoryURLs = homeSteps.getSubCatagoryMenuUrlList();
		for (String url : subCatagoryURLs) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				navSteps.navigateToFPApplication(url);
				subCategorySteps.clickFirstArticle();
				if (storyId.equals(articleSteps.getStoryId())) {
					articleSteps.clickNextArticle();
					articleSteps.verifyNextArticlePresence();
				}
				storyId = articleSteps.getStoryId();
				tokenAmount = homeSteps.getUnclaimTokenValue();
				categoryType = articleSteps.getMainCategoryType();
				Assert.assertTrue("Token not claimed for the day", homeSteps.clickClaimButton());
				storyLogDetails = articleSteps.getStoryLogDetails();
				Assert.assertEquals("Story Id is not matched in database", storyLogDetails.get("story_id"), storyId);
				Assert.assertEquals("Tokens claimed amount is not matched in database", storyLogDetails.get("tokens"),
						tokenAmount);
				Assert.assertEquals("Tokens claimed status is not matched in database", storyLogDetails.get("claimed"),
						"1");
				Assert.assertEquals("Category type is not matched in database", storyLogDetails.get("category"),
						categoryType);
				if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
					Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "D");
				} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Tablet")) {
					Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "T");
				} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Mobile")) {
					Assert.assertEquals("Device type is not matched in database", storyLogDetails.get("device"), "M");
				}
			}
			break;
		}
	}

	@Then("Verify complete registration message on article page")
	public void verifyPresenceOfCompleteRegButton() {
		assertTrue("Complete registration message is not present on article page",
				articleSteps.verifyTextCompleteRegEarnTokens());
	}
}