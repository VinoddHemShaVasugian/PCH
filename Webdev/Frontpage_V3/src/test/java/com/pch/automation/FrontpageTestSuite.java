/*
 * @Author pvadivelu
 * PCH Search and Win and Front Page
 */
package com.pch.automation;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

/**
 * The Class SearchandWinTestSuite.
 */
public class FrontpageTestSuite extends SerenityStories {

	/**
	 * Instantiates a new searchand win test suite.
	 */
	public FrontpageTestSuite() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.serenitybdd.jbehave.SerenityStories#storyPaths()
	 */
	@Override
	public List<String> storyPaths() {
//		return Arrays.asList("stories/fp/articles/ArticlesPage.story", "stories/fp/contestentry/ContestEntry.story",
//				"stories/fp/lb/HighRisk.story", "stories/fp/lb/Optin.story", "stories/fp/lottery/Lottery.story",
//				"stories/fp/miscellaneous/Ads.story", "stories/fp/miscellaneous/Homepage.story",
//				"stories/fp/miscellaneous/SamsBanner.story", "stories/fp/miscellaneous/Scratchpath.story",
//				"stories/fp/miscellaneous/SubCategoryMenuValidation.story",
//				"stories/fp/miscellaneous/SweepStakes.story", "stories/fp/miscellaneous/Videos.story",
//				"stories/fp/nfsp/NFSPValidation.story", "stories/fp/serp/SERPValidation.story",
//				"stories/fp/serp/SuspiciousTerms.story", "stories/fp/tokens/FirstSearchTokens.story",
//				"stories/fp/tokens/SegidTokens.story", "stories/fp/tokens/TokenQueue.story",
//				"stories/fp/uninav/RewardsUpdate.story", "stories/fp/usertypes/MiniReg.story",
//				"stories/fp/usertypes/SilverReg.story", "stories/fp/usertypes/SocialReg.story",
//				"stories/fp/weather/Weather.story");
		return Arrays.asList("stories/test.story");
//		return Arrays.asList("stories/fp/articles/ArticlesPage.story", "stories/fp/usertypes/MiniReg.story",
//				"stories/fp/usertypes/SilverReg.story", "stories/fp/usertypes/SocialReg.story",
//				"stories/fp/weather/Weather.story", "stories/fp/contestentry/ContestEntry.story",
//				"stories/fp/nfsp/NFSPValidation.story");
//		return Arrays.asList("stories/fp/miscellaneous/Scratchpath.story");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jbehave.core.junit.JUnitStories#run()
	 */
	@Override
	public void run() {
		super.run();
	}
}