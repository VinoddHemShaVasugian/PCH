package com.pch.automation.steps.fp;

import com.pch.automation.pages.fp.AdsPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class AdSteps extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	AdsPage adsPage;

	@Step
	public boolean verifyRightRailMultipleAd(String width1, String height1, String width2, String height2) {
		String size[] = adsPage.getSizeOfRightRailGptMultipleAd();
		if ((size[0].equals(width1) && size[1].equals(height1))
				|| (size[0].equals(width2) && size[1].equals(height2))) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyRightRailAd(String width, String height) {
		String size[] = adsPage.getSizeOfRightRailGptAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyInlineAd(String width, String height) {
		String size[] = adsPage.getSizeOfInlineGptAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyInlineTileAd(String width, String height) {
		String size[] = adsPage.getSizeOfInlineGptTileAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyTopStoriesAd(String width, String height) {
		String size[] = adsPage.getSizeOfTopStoriesNativeAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyTrendingNowAd(String width, String height) {
		String size[] = adsPage.getSizeOfTrendingNowNativeAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyOurpicksAd(String width, String height) {
		String size[] = adsPage.getSizeOfOurPickNativeAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifySponsoredAd(String width, String height) {
		String size[] = adsPage.getSizeOfSponsoredNativeAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyBottomAd(String width, String height) {
		String size[] = adsPage.getSizeOfBottomNativeAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public void clickNextArticleOnInterstitialAd() {
		adsPage.clickNextArticleOnInterstitialAd();
	}
}