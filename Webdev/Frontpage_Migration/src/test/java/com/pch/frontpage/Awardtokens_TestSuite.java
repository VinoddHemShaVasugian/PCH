package com.pch.frontpage;

import java.util.Arrays;
import java.util.List;

import net.serenitybdd.jbehave.SerenityStories;

/**
 * @author vsankar
 *
 */
public class Awardtokens_TestSuite extends SerenityStories {
	
	public Awardtokens_TestSuite() {
		
	}

	@Override
	public List<String> storyPaths() {

		return Arrays
				.asList(
						"stories/Frontpage-Automation/awardtokens/SegIdTokens.story"
						);
	}

	@Override
	public void run() throws Throwable {
		super.run();
	}

}
