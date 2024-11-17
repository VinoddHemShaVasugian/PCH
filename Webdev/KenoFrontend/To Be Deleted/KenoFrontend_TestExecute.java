package com.pch.kenofrontend;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class KenoFrontend_TestExecute extends SerenityStories {
	
	public KenoFrontend_TestExecute() {
		
	}

	@Override
	public List<String> storyPaths() {
		return Arrays
				.asList(
						"stories/KenoFrontend-Automation/KenoHomePage/KenoRegistration.story",
						"stories/KenoFrontend-Manual/KenoHomePage/KenoHeader.story"
						);
	}

	/* (non-Javadoc)
	 * @see org.jbehave.core.junit.JUnitStories#run()
	 */
	@Override
	public void run() throws Throwable {
		super.run();
	}

}
