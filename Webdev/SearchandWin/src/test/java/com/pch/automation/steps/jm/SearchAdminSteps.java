package com.pch.automation.steps.jm;

import java.util.TreeMap;

import com.pch.automation.pages.jm.ArticlePage;
import com.pch.automation.pages.jm.LoginPage;
import com.pch.automation.utilities.AppConfigLoader;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class SearchAdminSteps extends ScenarioSteps {

	private static final long serialVersionUID = 1L;
	LoginPage loginPage;
	ArticlePage articlePage;
	private static TreeMap<String, String> articleDetails = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);

	@Step
	public void loginSearchAdmin() {
		loginPage.loginSearchAdmin();
	}

	@Step
	public void searchArticle(String articleName) {
		articlePage.searchForArticle(articleName);
	}

	@Step
	public TreeMap<String, String> articleInfo(String... position) {
		articleDetails.put("description", articlePage.getValueByLabelName("Description"));
		articleDetails.put("notice", articlePage.getValueByLabelName("Notice"));
		articleDetails.put("tokens", articlePage.getValueByLabelName("Tokens", position[0]));
		articleDetails.put("conditions", articlePage.getValueByLabelName("Conditions", position[0]));
		return articleDetails;
	}

	@Step
	public void modifyFieldByLabelName(String labelName, String value) {
		articlePage.setValueByLabelName(labelName, value);
	}

	@Step
	public void modifyFieldByKeyName(String labelName, String value) {
		articlePage.setValueByKeyName(labelName, value);
	}

	@Step
	public void getFieldByLabelName(String labelName) {
		articleDetails.put(labelName, articlePage.getValueByLabelName(labelName));
	}

	@Step
	public void getFieldByLabelNameWithPosition(String labelName, String position) {
		articleDetails.put(labelName + position, articlePage.getValueByLabelName(labelName, position));
	}

	@Step
	public void getFieldByLabelNameArticleBased(String... labelName) {
		String deviceType = AppConfigLoader.deviceType;
		if (deviceType.equalsIgnoreCase("Mobile")) {
			articleDetails.put(labelName[0] + labelName[1], articlePage.getValueByLabelName(labelName[1], "3"));
		} else if (deviceType.equalsIgnoreCase("Tablet")) {
			articleDetails.put(labelName[0] + labelName[1], articlePage.getValueByLabelName(labelName[1], "2"));
		} else {
			articleDetails.put(labelName[0] + labelName[1], articlePage.getValueByLabelName(labelName[1], "1"));
		}
		articlePage.closeArticle();
	}

	@Step
	public void getFieldByKeyName(String keyName) {
		articleDetails.put(keyName, articlePage.getValueByKeyName(keyName));
	}

	@Step
	public void getDropdownFieldByLabelName(String labelName, String position) {
		articleDetails.put(labelName, articlePage.getFirstSelectedValueOfDropdownByLabelName(labelName, position));
	}

	@Step
	public void getDropdownFieldByLabelName(String labelName) {
		articleDetails.put(labelName, articlePage.getFirstSelectedValueOfDropdownByLabelName(labelName));
	}

	/**
	 * Return the article information like description, notice, tokens and
	 * condition, etc..
	 * 
	 * @return
	 */
	public static TreeMap<String, String> getArticleDetails() {
		return articleDetails;
	}

	/**
	 * Storing nfsp details for execution
	 * 
	 */
	@Step
	public void storeNFSPDetails(String... segmentedNFSP) {
		for (int i = 0; i < segmentedNFSP.length; i++) {
			articleDetails.put("segmentednfsp" + i, segmentedNFSP[i]);
		}
	}

	@Step
	public void unpublishArticle() {
		articlePage.unPublishArticle();
	}

	@Step
	public void publishArticle() {
		articlePage.publishArticle();
	}
}
