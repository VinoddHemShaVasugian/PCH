package com.pch.kenofrontend;

import java.util.Arrays;
import java.util.List;
import net.serenitybdd.jbehave.SerenityStories;

public class KenoFrontend_ResultsPage extends SerenityStories {
	
	public KenoFrontend_ResultsPage() {
		
	}

	@Override
	public List<String> storyPaths() {
		return Arrays
				.asList(
						"stories/KenoFrontend-Automation/KenoResultsPage/diwresultstokens.story"
//						,"stories/KenoFrontend-Automation/KenoHomePage/KenoCoaching_Screen.story"
						);
	}

	@Override
	public void run() throws Throwable {
		super.run();
	}

}
