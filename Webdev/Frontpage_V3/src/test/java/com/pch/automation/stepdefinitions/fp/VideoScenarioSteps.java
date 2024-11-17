package com.pch.automation.stepdefinitions.fp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.pch.automation.steps.HomepageSteps;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.steps.fp.SubCategorySteps;
import com.pch.automation.steps.fp.VideoSteps;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Steps;

public class VideoScenarioSteps {
	@Steps
	VideoSteps videoSteps;
	@Steps
	HomepageSteps homeSteps;
	@Steps
	NavigationSteps navSteps;
	@Steps
	SubCategorySteps subCategorySteps;

	LinkedList<String> urlList;
	String videoTitle;
	String tokenAmount;
	String categoryType;
	LinkedHashMap<String, String> videoLogDetails;

	@When("Verify the video landing page")
	public void verifyVideolandingPage() {
		homeSteps.clickFirstVideoLink();
		Assert.assertTrue("Video page is not displayed, when clicks video menu", videoSteps.verifyVideolandingPage());
		Assert.assertTrue("Video page title mismatched.", videoSteps.verifyPageTitle());
	}

	@When("Click video from Top stories")
	public void navigateToTopStoriesVideo() {
		homeSteps.clickFirstVideoLink();
	}

	@Then("Verify video player for guest user")
	public void videoPlayerGuestUser() {
		Assert.assertTrue("Login and Register links on displayed on video player for guest user.",
				videoSteps.verifyVideoPlayerUnrecUser());
	}

	@Then("Verify video landing page content")
	public void verifyContentVideopage() {
		Assert.assertTrue("Video section is not displayed.", videoSteps.verifyVideoSection());
		Assert.assertTrue("Back to home page link is not displayed", videoSteps.verifyBackToHomeLink());
//		Assert.assertTrue("Video category playlist is not displayed", videoSteps.verifyCategoryPlaylist()); //As per enhancements categories need to finalize and update in code
		LinkedList<String> playList = videoSteps.getCategoryPlaylist();
		for (String menuName : playList) {
			if (menuName != null && !menuName.isEmpty()) {
				Assert.assertEquals("Video count is mismatched for " + menuName + " category",
						videoSteps.getPlaylistVideoCount(menuName), 3);
				break;
			}
		}
	}

	@Then("Verify the video playlist and video on category pages")
	public void verifyVideoPlaylistCategoryPages() throws IOException, SQLException {
		LinkedList<String> catagoryURLs = homeSteps.getMainCatagoryMenuUrlList();
		for (String url : catagoryURLs) {
			if (!url.endsWith("more") && !url.endsWith("everydaylife")
					&& !url.equals("https://frontpage.qa.pch.com/")) {
				navSteps.navigateToFPApplication(url);
				subCategorySteps.clickFirstVideo();
				Assert.assertTrue("Video page is not displayed, when clicks video menu",
						videoSteps.verifyVideolandingPage());
				Assert.assertTrue("Video page title mismatched.", videoSteps.verifyPageTitle());
				Assert.assertTrue("Video section is not displayed.", videoSteps.verifyVideoSection());
				Assert.assertTrue("Back to home page link is not displayed", videoSteps.verifyBackToHomeLink());
				break;
			}
		}
	}

	@Then("Verify the video playlist and video on sub category pages")
	public void verifyVideoPlaylistSubCategoryPages() throws IOException, SQLException {
		LinkedList<String> subCategoryUrlList = homeSteps.getSubCatagoryMenuUrlList();
		LinkedList<String> subCategoryMenuList = homeSteps.getSubCatagoryMenuName();
		for (int count = 0; count < subCategoryUrlList.size(); count++) {
			if (!subCategoryUrlList.get(count).endsWith("business")
					&& !subCategoryUrlList.get(count).endsWith("sports")) {
				navSteps.navigateToFPApplication(subCategoryUrlList.get(count));
				subCategorySteps.clickFirstVideo();
				Assert.assertTrue("Video page is not displayed, when clicks video menu",
						videoSteps.verifyVideolandingPage());
				Assert.assertTrue("Video page title mismatched.", videoSteps.verifyPageTitle());
				Assert.assertTrue("Video section is not displayed.", videoSteps.verifyVideoSection());
				Assert.assertTrue("Back to home page link is not displayed", videoSteps.verifyBackToHomeLink());
				LinkedList<String> playList = videoSteps.getCategoryPlaylist();
				for (String category : playList) {
					if (category != null && !category.isEmpty()) {
						Assert.assertEquals("Video count is mismatched for " + category + " category",
								videoSteps.getPlaylistVideoCount(category), 3);
					}
				}
				for (int i = count + 1; i < subCategoryMenuList.size();) {
					Assert.assertEquals(
							"Video playlist is not displayed for " + subCategoryMenuList.get(i) + " sub category", 3,
							videoSteps.getPlaylistVideoCount(subCategoryMenuList.get(i)));
					break;
				}
				break;
			}
		}
	}

	@Then("Play video and verify claimed tokens")
	public void verifyTokensClaimed() {
		Assert.assertTrue("Tokens not claimed for playing video", videoSteps.verifyTokensClaimed());
	}

	@Then("Verify the Video log details for featured article and claimed status '$claimedStatus'")
	public void verifyStoryLogFeaturedArticle(String claimedStatus) throws IOException, SQLException {
		videoSteps.verifyPlayCircle();
		videoTitle = videoSteps.getVideoTitle();
		categoryType = videoSteps.getMainCategoryType();
		if (claimedStatus.equals("1")) {
			tokenAmount = videoSteps.getClaimedTokenAmount();
		}
		videoLogDetails = videoSteps.getVideoLogDetails();
		Assert.assertEquals("Video title is not matched in database", videoLogDetails.get("video_title"), videoTitle);
		if (claimedStatus.equals("1")) {
			Assert.assertEquals("Tokens claimed amount is not matched in database", videoLogDetails.get("tokens"),
					tokenAmount);
		}
		Assert.assertEquals("Tokens claimed status is not matched in database", videoLogDetails.get("claimed"),
				claimedStatus);
		Assert.assertEquals("Category type is not matched in database", videoLogDetails.get("category"), categoryType);
		if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
			Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "D");
		} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Tablet")) {
			Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "T");
		} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Mobile")) {
			Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "M");
		}
	}

	@Then("Verify the Video log details for category page video")
	public void verifyStoryLogCategoryPages() throws IOException, SQLException {
		LinkedList<String> catagoryURLs = homeSteps.getMainCatagoryMenuUrlList();
		for (String url : catagoryURLs) {
			if (!url.endsWith("more") && !url.endsWith("everydaylife")
					&& !url.equals("https://frontpage.qa.pch.com/")) {
				navSteps.navigateToFPApplication(url);
				subCategorySteps.clickFirstVideoOnPlayer();
				videoSteps.verifyPlayCircle();
				if (videoTitle.equals(videoSteps.getVideoTitle())) {
					videoSteps.playUnwatchedVideo();
					videoSteps.verifyNextVideoPresence();
				}
				videoTitle = videoSteps.getVideoTitle();
				tokenAmount = videoSteps.getClaimedTokenAmount();
				categoryType = videoSteps.getMainCategoryType();
				videoLogDetails = videoSteps.getVideoLogDetails();
				Assert.assertEquals("Video title is not matched in database", videoLogDetails.get("video_title"),
						videoTitle);
				Assert.assertEquals("Tokens claimed amount is not matched in database", videoLogDetails.get("tokens"),
						tokenAmount);
				Assert.assertEquals("Tokens claimed status is not matched in database", videoLogDetails.get("claimed"),
						"1");
				Assert.assertEquals("Category type is not matched in database", videoLogDetails.get("category"),
						categoryType);
				if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
					Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "D");
				} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Tablet")) {
					Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "T");
				} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Mobile")) {
					Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "M");
				}
				break;
			}
		}
	}

	@Then("Verify the Video log details for sub category page video")
	public void verifyStoryLogSubCategoryPages() throws IOException, SQLException {
		LinkedList<String> subCatagoryURLs = homeSteps.getSubCatagoryMenuUrlList();
		for (String url : subCatagoryURLs) {
			if (!url.endsWith("business") && !url.endsWith("sports")) {
				navSteps.navigateToFPApplication(url);
				subCategorySteps.clickFirstVideoLink();
				videoSteps.verifyPlayCircle();
				if (videoTitle.equals(videoSteps.getVideoTitle())) {
					videoSteps.playUnwatchedVideo();
					videoSteps.verifyNextVideoPresence();
				}
				videoTitle = videoSteps.getVideoTitle();
				tokenAmount = videoSteps.getClaimedTokenAmount();
				categoryType = videoSteps.getMainCategoryType();
				videoLogDetails = videoSteps.getVideoLogDetails();
				Assert.assertEquals("Video title is not matched in database", videoLogDetails.get("video_title"),
						videoTitle);
				Assert.assertEquals("Tokens claimed amount is not matched in database", videoLogDetails.get("tokens"),
						tokenAmount);
				Assert.assertEquals("Tokens claimed status is not matched in database", videoLogDetails.get("claimed"),
						"1");
				Assert.assertEquals("Category type is not matched in database", videoLogDetails.get("category"),
						categoryType);
				if (AppConfigLoader.deviceType.equalsIgnoreCase("Desktop")) {
					Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "D");
				} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Tablet")) {
					Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "T");
				} else if (AppConfigLoader.deviceType.equalsIgnoreCase("Mobile")) {
					Assert.assertEquals("Device type is not matched in database", videoLogDetails.get("device"), "M");
				}
				break;
			}
		}
	}

	@Then("Verify complete registration message on Video page")
	public void verifyPresenceOfCompleteRegButton() {
		Assert.assertTrue("Complete registration message is not present on video page",
				videoSteps.verifyTextCompleteRegEarnTokens());
	}
}