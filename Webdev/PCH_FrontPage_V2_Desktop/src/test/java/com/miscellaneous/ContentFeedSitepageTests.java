package com.miscellaneous;

import org.testng.annotations.Test;

import com.pageobjects.HomePage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class ContentFeedSitepageTests extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();

	@testId(test_id = "32147")
	@testCaseName(test_case_name = "FPContentToMakeConfigurable--ContentFeedStories")
	@Test(priority = 1, description = "Verify the Content feeds", groups = { "DESKTOP", "TABLET" })
	public void content_feed_tests() throws Exception {
		String expectedcontent;
		invokeBrowser(xmlReader(ENVIRONMENT, "FP_content_stories") + "6");
		expectedcontent = homepage_instance.get_source_htm();
		boolean flag = false;
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_healthmenu();
		for (String s : homepage_instance.get_content_list()) {
			if (expectedcontent.contains(s)) {
				flag = true;
			}
		}
		assertTrue(flag);
	}
}
