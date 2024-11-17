package com.pch.kenofrontend;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

/**
 * @author Lovekesh S. July 18, 2017
 *
 */
public class KenoFrontend_LogIn extends SerenityStories {
	
	public KenoFrontend_LogIn() {
		
	}

	@Override
	public List<String> storyPaths() {
		return Arrays
				.asList(
						"stories/KenoFrontend-Automation/KenoHomePage/LogIn.story"
						);
	}

	@Override
	public void run() throws Throwable {
		super.run();
	}

}
