package com.pch.automation.steps;

import java.util.LinkedList;
import java.util.List;

import com.pch.automation.pages.HeaderAndUninavPage;
import com.pch.automation.pages.SerpPage;
import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.utilities.RandomGenerator;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class HomepageSteps extends ScenarioSteps {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The home page. */
	HomePage homePage;
	SerpPage serpPage;
	HeaderAndUninavPage header;

	@Step
	public void searchAndVerifySERPPage() {
		homePage.search(RandomGenerator.randomAlphabetic(5));
		serpPage.verifySERPCompletely();
	}

	@Step
	public boolean verifySamsBanner() {
		return homePage.verifySamsBanner();
	}

	@Step
	public boolean verifyDefaultBanner() {
		return homePage.verifyFallbackDefaultBanner();
	}

	@Step
	public void clickEntertainmentMenu() {
		homePage.clickEntertainmentMenu();
	}

	@Step
	public void clickWeatherMenu() {
		homePage.clickWeatherMenu();
	}

	@Step
	public void clickLotteryMenu() {
		homePage.clickLotteryMenu();
	}
	
	@Step
	public void clickNewsMenu() {
		homePage.clickNewsMenu();
	}

	@Step
	public boolean verifyTopStoriesSection() {
		return homePage.verifyTopStoriesSection();
	}

	@Step
	public boolean verifyCategoriessSection() {
		return homePage.verifyCategoriesSection();
	}

	@Step
	public boolean verifyOurpicksSection() {
		return homePage.verifyOurPicksSection();
	}

	@Step
	public boolean verifyTrendingNowSection() {
		return homePage.verifyTrendingNowSection();
	}

	@Step
	public boolean verifyTrendingVideosSection() {
		return homePage.verifyTrendingVideosSection();
	}

	@Step
	public String getLatestActivityMessage() {
		return header.getLatestEntryActivityMessage();
	}

	@Step
	public boolean clickClaimButton() {
		return homePage.clickClaimButton();
	}

	@Step
	public int getDailyBonusGameCheckCount() {
		return homePage.getDailyBonusGameCheckCount();
	}

	@Step
	public boolean verifyDailyBonusGameIconEnabled() {
		return homePage.verifyDailyBonusGameLockIcon();
	}

	@Step
	public boolean verifyCategoryOnOurpicks(String category) {
		boolean result = false;
		List<WebElementFacade> categories = homePage.getOurpicksCategories();
		for (WebElementFacade elm : categories) {
			if (elm.getText().equalsIgnoreCase(category)) {
				result = true;
			}
		}
		return result;
	}

	@Step
	public String getUnclaimTokenValue() {
		return homePage.getUnclaimTokenValue();
	}

	@Step
	public LinkedList<String> getMainCatagoryMenuName() {
		return homePage.getMainCatagoryMenuName();
	}

	@Step
	public LinkedList<String> getSubCatagoryMenuName() {
		return homePage.getSubCatagoryMenuList();
	}

	@Step
	public LinkedList<String> getMainCatagoryMenuUrlList() {
		return homePage.getMainCatagoryMenuUrlList();
	}

	@Step
	public LinkedList<String> getSubCatagoryMenuUrlList() {
		return homePage.getSubCatagoryMenuUrlList();
	}

	@Step
	public void clickFirstArticleLink() {
		homePage.clickFirstArticleLink();
	}

	@Step
	public boolean verifyInfoPages() {
		boolean bl = (header.verifyAboutPage() && header.verifyEarnTokensPage() && header.verifyWaysToWinPage()
				&& header.verifyHowToSearchPage() && header.verifyDosPage());
		homePage.closeCurrentlyFocusedTab();
		return bl;
	}

	@Step
	public boolean verifyTokensClaimed() {
		return homePage.verifyClaimedButton();
	}

	@Step
	public void clickFirstVideoLink() {
		homePage.clickFirstVideoLink();
	}
}