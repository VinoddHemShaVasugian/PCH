package com.steps.quiz;

import com.pages.quiz.AdsPage;
import com.pages.quiz.QuizPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class AdSteps extends ScenarioSteps {

	private static final long serialVersionUID = 1L;

	AdsPage adsPage;
	QuizPage quizPage;

	@Step
	public boolean verifyRightRailMultipleAd(String width1, String height1, String width2, String height2,
			String width3, String height3) {
		String size[] = adsPage.getSizeOfRightRailGptMultipleAd();
		if ((size[0].equals(width1) && size[1].equals(height1)) || (size[0].equals(width2) && size[1].equals(height2))
				|| (size[0].equals(width3) && size[1].equals(height3))) {
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
	public boolean verifyInlineAdCategoryPage(String width, String height) {
		String size[] = adsPage.getSizeOfCategoryPageInlineGptAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyBottomAd(String width, String height) {
		String size[] = adsPage.getSizeOfBottomAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyLeftRailAd(String width, String height) {
		String size[] = adsPage.getSizeOfLeftRailGptAd();
		if (size[0].equals(width) && size[1].equals(height)) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyPlaybuzzStickyRightRailAd() {
		return adsPage.verifyPlaybuzzStickyRightRailAd();
	}

	@Step
	public boolean verifyPlaybuzzStickyBottomAd() {
		return adsPage.verifyPlaybuzzStickyBottomAd();
	}

	@Step
	public void closePlaybuzzStickyRightRailAd() {
		adsPage.closePlaybuzzStickyRightRailAd();
	}

	@Step
	public boolean verifyInlineAdBetweenQuestions(String width, String height) {
		return quizPage.playQuizScrollViewAndVerifyInlineAds();
	}

	@Step
	public boolean verifyPageTaggingStatus(int adFrame) {
		return adsPage.verifyPageTaggingStatus(adFrame);
	}
}