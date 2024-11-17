package com.pch.automation.steps.fp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.MyAccount;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.pages.fp.VideoPage;
import com.pch.automation.steps.NavigationSteps;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class VideoSteps extends ScenarioSteps {

	private static final long serialVersionUID = 1L;
	HomePage homepage;
	VideoPage videoPage;
	MyAccount accountPage;

	NavigationSteps navSteps;

	@Step
	public boolean verifyPageTitle() {
		if (getDriver().getTitle().trim().equalsIgnoreCase(videoPage.getVideoTitle())) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyVideolandingPage() {
		return videoPage.verifyVideoLandingPage();
	}

	@Step
	public void playVideo() {
		videoPage.playVideo();
	}

	@Step
	public boolean verifyTokensClaimed() {
		return videoPage.verifyTokensClaimedButton();
	}

	@Step
	public String getClaimedTokenAmount() {
		return videoPage.getClaimedTokenAmount();
	}

	@Step
	public LinkedHashMap<String, String> getVideoLogDetails() throws IOException, SQLException {
		return DatabaseHelper.getInstance().getVideoLogDetails(RegistrationPage.regGenerator.getEmail());
	}

	@Step
	public String getMainCategoryType() {
		return videoPage.getCategoryType();
	}

	@Step
	public String getVideoTitle() {
		return videoPage.getVideoTitle();
	}

	@Step
	public void verifyNextVideoPresence() {
		videoPage.verifyNextVideo();
	}

	@Step
	public void clickNextVideo(String category) {
		videoPage.clickNextArrow(category);
	}

	@Step
	public boolean verifyTokensClaimedAmountDisplayed() {
		return videoPage.verifyTokensClaimedAmount();
	}

	@Step
	public void playUnwatchedVideo() {
		int count = videoPage.totalVideosCount();
		for (int i = 0; i < count; i++) {
			videoPage.clickNextVideo();
			if (verifyTokensClaimedAmountDisplayed()) {
				break;
			}
		}
	}

	@Step
	public void verifyPlayCircle() {
		videoPage.verifyPlayCircle();
	}

	@Step
	public boolean verifyTextCompleteRegEarnTokens() {
		return videoPage.verifyDefaultTokenIconMsg(
				AppConfigLoader.getInstance().msgPropertyFileReader("videoPlayerCompleteRegUnRecMsg"));
	}

	@Step
	public boolean verifyVideoSection() {
		return videoPage.verifyFaVideosection();
	}

	@Step
	public boolean verifyBackToHomeLink() {
		return videoPage.verifyBackToHomeLink();
	}

	@Step
	public boolean verifyCategoryPlaylist() {
		return videoPage.verifyCategoryPlaylist();
	}

	@Step
	public int getPlaylistVideoCount(String category) {
		return videoPage.getPlaylistVideoCount(category);
	}

	@Step
	public boolean verifyVideoPlaylist(String category) {
		return videoPage.verifyVideoPlaylist(category);
	}

	@Step
	public LinkedList<String> getCategoryPlaylist() {
		return videoPage.getCategoryPlaylist();
	}

	@Step
	public boolean verifyVideoPlayerUnrecUser() {
		return videoPage.verifyNextVideoUnrecUser();
	}
}