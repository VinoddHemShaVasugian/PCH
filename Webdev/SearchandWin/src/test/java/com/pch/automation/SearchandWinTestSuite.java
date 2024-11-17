package com.pch.automation;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

/**
 * The Class SearchandWinTestSuite.
 */
public class SearchandWinTestSuite extends SerenityStories {

	/**
	 * Instantiates a new searchand win test suite.
	 */
	public SearchandWinTestSuite() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.serenitybdd.jbehave.SerenityStories#storyPaths()
	 */
	@Override
	public List<String> storyPaths() {
		 /*return Arrays.asList("stories/sw/cv/CV.story",
		 "stories/sw/contestentry/ContestEntry.story",
		 "stories/sw/lb/HighRisk.story", "stories/sw/lb/Optin.story",
		 "stories/sw/nfsp/NFSPValidation.story",
		 "stories/sw/scratchpath/Scratchpath.story",
		 "stories/sw/serp/SERPValidation.story",
		 "stories/sw/tokens/FirstSearchTokens.story",
		 "stories/sw/usertypes/MiniReg.story",
		 "stories/sw/usertypes/SocialReg.story",
		 "stories/sw/usertypes/SilverReg.story",
		 "stories/sw/tokens/SegidTokens.story", "stories/sw/serp/AdTileSearch.story",
		 "stories/sw/serp/ConsecutiveSearchCK.story",
		 "stories/sw/serp/InternalAdTileSearch.story",
		 "stories/sw/serp/SuspiciousTerms.story",
		 "stories/sw/tokens/ExtensionTokens.story",
		 "stories/sw/tokens/TokenQueue.story",
		 "stories/sw/miscellaneous/SamsBanner.story",
		 "stories/sw/uninav/uninav.story");*/
//		return Arrays.asList("stories/test.story");
		return Arrays.asList("stories/sw/uninav/uninav.story");
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