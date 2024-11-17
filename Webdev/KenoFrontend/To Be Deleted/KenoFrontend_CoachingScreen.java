package com.pch.kenofrontend;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

/**
 * @author Lovekesh S. July 18, 2017
 *
 */
public class KenoFrontend_CoachingScreen extends SerenityStories {
	
	public KenoFrontend_CoachingScreen() {
		
	}

	@Override
	public List<String> storyPaths() {
		return Arrays
				.asList(
						"stories/KenoFrontend-Automation/KenoHomePage/KenoCoaching_Screen.story"
						);
	}

	@Override
	public void run() throws Throwable {
		super.run();
	}

}
