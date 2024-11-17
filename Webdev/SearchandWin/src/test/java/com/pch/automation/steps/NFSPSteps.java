package com.pch.automation.steps;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonProcessingException;

import com.pch.automation.database.DatabaseHelper;
import com.pch.automation.pages.LightboxPage;
import com.pch.automation.pages.RegistrationPage;
import com.pch.automation.pages.SegmentationPage;
import com.pch.automation.pages.SerpPage;
import com.pch.automation.pages.jm.ArticlePage;
import com.pch.automation.pages.sw.HomePage;
import com.pch.automation.steps.jm.SearchAdminSteps;
import com.pch.automation.utilities.AppConfigLoader;
import com.pch.automation.utilities.NFSPHelper;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * 
 * @author vsankar
 *
 */
public class NFSPSteps extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The home page. */
	HomePage homePage;
	/** The search results page. */
	SerpPage serpPage;
	/** The article page. */
	ArticlePage articlePage;

	LightboxPage lbPage;

	SegmentationPage segPage;

	NFSPHelper nfspHelper;

	@Steps
	NavigationSteps navigationSteps;

	@Steps
	SearchAdminSteps searchAdminSteps;

	AppConfigLoader configInstance = AppConfigLoader.getInstance();

	@Step
	public void consecutiveSearches() {
		serpPage.consecutiveSearches(
				Integer.parseInt(SearchAdminSteps.getArticleDetails().get("algo_only_searches_unrecognized")));
	}

	@Step
	public String getNfspSourceFromPageSource() {
		return nfspHelper.getNfspSourceFromPageSource(1);
	}

	@Step
	public String getNfspSegmentFromPageSource() {
		return nfspHelper.getNfspSegmentFromPageSource(1);
	}

	@Step
	public String getNfspSegmentFromJson(String accessIdJson, String nfsp) throws JsonProcessingException, IOException {
		return nfspHelper.getNfspSegmentFromJson(accessIdJson, nfsp);
	}

	@Step
	public void resetDailySearchCount(String searchCount) throws SQLException, IOException {
		DatabaseHelper.getInstance().updateDailySearchCount(RegistrationPage.regGenerator.getEmail(),
				searchCount);
	}

	@Step
	public void assignSegmentByAdminProperty(String keyName) {
		String[] segment = SearchAdminSteps.getArticleDetails().get(keyName).replace("[", "").replace("]", "")
				.replace("\"", "").split(",");
		navigationSteps.navigateToSegmentationPage();
		segPage.setSegmentByCode(RegistrationPage.regGenerator.getEmail(), segment[0]);
		navigationSteps.navigateToSWApplication(AppConfigLoader.getInstance().getEnvironmentProperty("AppUrl") + "?em="
				+ RegistrationPage.regGenerator.getEmail() + "&gmt=" + RegistrationPage.regGenerator.getGmt());
	}

	@Step
	public void retriveSegmentNFSP(String segment) throws JsonProcessingException, IOException {
		searchAdminSteps.storeNFSPDetails(nfspHelper
				.getNfspSegmentFromJson(SearchAdminSteps.getArticleDetails().get("segments"), "d", segment, "d")
				.split(","));
	}
}
