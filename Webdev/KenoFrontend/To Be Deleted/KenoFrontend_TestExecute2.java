package com.pch.kenofrontend;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

/**
 * @author pvadivelu Dec 16, 2016
 *
 */
public class KenoFrontend_TestExecute2 extends SerenityStories {
	
	public KenoFrontend_TestExecute2() {
		
	}

	@Override
	public List<String> storyPaths() {
		return Arrays
				.asList(
						"stories/KenoFrontend-Automation/KenoHomePage/KenoPlayCards_And_LiveDrawing.story"
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
