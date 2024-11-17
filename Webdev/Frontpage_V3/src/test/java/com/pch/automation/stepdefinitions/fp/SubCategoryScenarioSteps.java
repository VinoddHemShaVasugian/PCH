package com.pch.automation.stepdefinitions.fp;

import java.util.LinkedList;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.pch.automation.steps.ArticlesSteps;
import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.fp.SubCategorySteps;
import com.pch.automation.steps.fp.VideoSteps;
import com.pch.automation.steps.jm.FpAdminSteps;

import net.thucydides.core.annotations.Steps;

public class SubCategoryScenarioSteps {
	@Steps
	SubCategorySteps subCategorySteps;
	@Steps
	NavigationSteps navigationSteps;
	@Steps
	FpAdminSteps adminSteps;
	@Steps
	VideoSteps videoSteps;
	@Steps
	ArticlesSteps articleSteps;
	@Steps
	HomepageSteps homeSteps;

	public LinkedList<String> urlList;
	public int defaultVideoCount = 2;

	@When("Get sub category menu url list")
	public void getSubCategoryMenuUrlList() {
		urlList = subCategorySteps.getSubCategoryMenuURl();
	}

	@Then("Verify submenu landing pages")
	public void verifySubMenuRedirection() {
		for (String url : urlList) {
			String articleName = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
			navigationSteps.navigateToFPApplication(url);
			Assert.assertTrue("Redirected to incorrect page for Submenu -->" + articleName + "",
					subCategorySteps.verifyPageTitle(articleName));
		}
	}

	@Then("Verify articles, videos and Trending now section on sub category pages")
	public void verifySubCategoryPageContent() {
		for (String url : urlList) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				String articleName = url.substring(url.lastIndexOf("/") + 1, url.length()).replace("-", " ").trim();
				System.out.println("Login to Joomla and navigate to article name: " + articleName);
				adminSteps.loginFrontpageAdmin();
				adminSteps.searchArticle(articleName);
				adminSteps.getDropdownFieldByLabelName("Elements");
				adminSteps.getDropdownFieldByLabelName("Native Ad Position");
				adminSteps.getFieldByLabelName("Ad Positions");
				adminSteps.getDropdownFieldByLabelName("Trending Elements");
				adminSteps.getFieldByLabelName("Videos Text");
				String videoText = FpAdminSteps.getArticleDetails().get("Videos Text").equals("") ? "POPULAR VIDEOS"
						: FpAdminSteps.getArticleDetails().get("Videos Text");
				navigationSteps.navigateToFPApplication(url);
				Assert.assertEquals("Popular video section title mismatched", videoText.toLowerCase(),
						subCategorySteps.getPopularVideoSectionTitleName().toLowerCase());
				Assert.assertEquals("Article count mismatched", subCategorySteps.getArticleCount(),
						Integer.parseInt(FpAdminSteps.getArticleDetails().get("Elements")));
				Assert.assertEquals("Article count mismatched", subCategorySteps.getTrendingElementsCount(),
						Integer.parseInt(FpAdminSteps.getArticleDetails().get("Trending Elements")));
				Assert.assertEquals("Native ad position mismatched", subCategorySteps.getPositionOfNativeAd(),
						Integer.parseInt(FpAdminSteps.getArticleDetails().get("Native Ad Position")));
				Assert.assertEquals("GPT ad position mismatched", subCategorySteps.getPositionOfGPTAdUnit(),
						Integer.parseInt(FpAdminSteps.getArticleDetails().get("Ad Positions")));
				Assert.assertEquals("Default video count mismatched", subCategorySteps.getCountOfVideosOnPage(),
						defaultVideoCount);
				Assert.assertEquals("Full story navigation link count mismatched",
						subCategorySteps.getFullStoryLinkCount(),
						Integer.parseInt(FpAdminSteps.getArticleDetails().get("Elements")) + defaultVideoCount);
				Assert.assertTrue("Article configured without images are not displayed",
						subCategorySteps.verifyArticleWithoutImagePresence());
				String nextPageDetails = subCategorySteps.getNextPageDetailsFromViewMore();
				subCategorySteps.clickViewMore();
				Assert.assertTrue(articleName + " page URl is mismatched.",
						subCategorySteps.verifyCurrentUrl(nextPageDetails));
				subCategorySteps
						.clickFullStoryLink(Integer.parseInt(FpAdminSteps.getArticleDetails().get("Elements")) - 1);
				Assert.assertTrue("Next article/Video is not displayed.",
						articleSteps.verifyNextArticlePresence() || videoSteps.verifyVideoSection());
				break;
			}
		}
	}
}