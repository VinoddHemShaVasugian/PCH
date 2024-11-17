package com.pch.automation.steps.fp;

import java.util.LinkedList;

import com.pch.automation.pages.fp.HomePage;
import com.pch.automation.pages.fp.SubCategoryPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class SubCategorySteps extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	HomePage homePage;
	SubCategoryPage subCategoryPage;

	@Step
	public LinkedList<String> getSubCategoryMenuURl() {
		return homePage.getSubCatagoryMenuUrlList();
	}

	@Step
	public boolean verifyPageTitle(String pageName) {
		if (getDriver().getTitle().trim().equalsIgnoreCase(pageName.trim())) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public boolean verifyCurrentUrl(String pageName) {
		if (getDriver().getCurrentUrl().equalsIgnoreCase(pageName.trim())) {
			return true;
		} else {
			return false;
		}
	}

	@Step
	public void clickFirstArticle() {
		subCategoryPage.clickFirstArticleLink();
	}

	@Step
	public void clickFirstVideo() {
		subCategoryPage.clickFirstVideo();
	}

	@Step
	public void clickFirstVideoOnPlayer() {
		subCategoryPage.clickFirstVideoOnPlayer();
	}

	@Step
	public void clickFirstVideoLink() {
		subCategoryPage.clickFirstVideoLink();
	}

	@Step
	public void navigateCategoryPageFromLabel(String category) {
		subCategoryPage.navigateCategoryPageFromLabel(category);
	}

	@Step
	public String getPopularVideoSectionTitleName() {
		return subCategoryPage.getPopularVideosSectionTitleName();
	}

	@Step
	public int getArticleCount() {
		int a = subCategoryPage.getArticleCount() - subCategoryPage.getCountOfVideosOnPage();
		return a;
	}

	@Step
	public int getTrendingElementsCount() {
		return subCategoryPage.getTrendingElementsCount();
	}

	@Step
	public int getFullStoryLinkCount() {
		return subCategoryPage.getFullStoryLinkCount();
	}

	@Step
	public int getCountOfVideosOnPage() {
		return subCategoryPage.getCountOfVideosOnPage();
	}

	@Step
	public int getPositionOfGPTAdUnit() {
		return subCategoryPage.getPositionOfGpAdUnit();
	}

	@Step
	public int getPositionOfNativeAd() {
		return subCategoryPage.getPositionOfNativeAdUnit();
	}

	@Step
	public boolean verifyArticleWithoutImagePresence() {
		return subCategoryPage.verifyArticleWithoutImagePresence();
	}

	@Step
	public void clickFullStoryLink(int i) {
		subCategoryPage.clickFullStoryLink();
	}

	@Step
	public void clickViewMore() {
		subCategoryPage.clickViewMore();
	}

	@Step
	public String getNextPageDetailsFromViewMore() {
		return subCategoryPage.getNextPageDetailsFromViewMore();
	}
}