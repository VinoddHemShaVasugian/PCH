package com.pch.kenofrontend;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

public class KenoFrontend_TestSuite extends SerenityStories {

	public KenoFrontend_TestSuite() {
		super();
	}

	@Override
	public List<String> storyPaths() {
		return Arrays.asList(
//		 		"stories/KenoFrontend-Automation/KenoHomePage/KenoRegistration.story",
//		 		"stories/KenoFrontend-Automation/KenoHomePage/KenoPlayCards_And_LiveDrawing.story"
//				"stories/KenoFrontend-Automation/KenoResultsPage/diwresultstokens.story"
//				"stories/KenoFrontend-Automation/KenoHomePage/KenoCoaching_Screen.story"
//				"stories/KenoFrontend-Automation/Admin/Preconditions.story"
//				"stories/KenoFrontend-Automation/KenoHomePage/NoPasswordUserKenoCard.story"
//				"stories/KenoFrontend-Automation/KenoHomePage/KenoOptin.story"
//				"stories/KenoFrontend-Automation/KenoHomePage/UniversalNavigationWithBasicFunctionality.story"
//				"stories/KenoFrontend-Automation/KenoHomePage/UnRecognizedUserSignIn_PostGamePlay.story"
//				"stories/KenoFrontend-Automation/KenoHomePage/Tutorials.story"
				"stories/KenoFrontend-Automation/KenoHomePage/EnableMobileTabOnLeaderboard.story"
				);
	}

	@Override
	public void run() throws Throwable {
		super.run();
	}

}
