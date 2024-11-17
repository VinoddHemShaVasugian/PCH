package com.pch.quiz;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

/**
 * The Class QuizTestSuite.
 */
public class QuizTestSuite extends SerenityStories {

	/**
	 * Instantiates a new Quiz test suite.
	 */
	public QuizTestSuite() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.serenitybdd.jbehave.SerenityStories#storyPaths()
	 */
	@Override
	public List<String> storyPaths() {
//		return Arrays.asList("stories/quiz/acquisitionpath/AcquisitionFullpath.story",
//				"stories/quiz/acquisitionpath/AcquisitionHalfwaymark.story",
//				"stories/quiz/acquisitionpath/AcquisitionPersistentbanner.story",
//				"stories/quiz/contestentry/ContestEntry.story", "stories/quiz/homepage/homepage.story",
//				"stories/quiz/miscellaneous/ads.story", "stories/quiz/miscellaneous/Apphealth.story",
//				"stories/quiz/miscellaneous/Gos.story", "stories/quiz/miscellaneous/Registration.story",
//				"stories/quiz/miscellaneous/Uninav.story", "stories/quiz/usertypes/SocialReg.story",
//				"stories/quiz/usertypes/MiniReg.story", "stories/quiz/usertypes/SilverReg.story");
//	}
		
		return Arrays.asList("stories/quiz/contestentry/ContestEntry.story");
//		return Arrays.asList("stories/quiz/test.story");
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