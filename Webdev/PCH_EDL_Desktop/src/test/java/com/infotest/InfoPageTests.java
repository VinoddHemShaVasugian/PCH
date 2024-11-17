package com.infotest;

import org.testng.annotations.Test;

import com.pageobjects.EDLHomePage;
import com.pageobjects.InfoPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class InfoPageTests extends BaseClass{
	
	private final InfoPage infopage_instance = InfoPage.getInstance();
	private final EDLHomePage edl_instance = EDLHomePage.getInstance();	
	
	@testId(test_id = "35198")
	@testCaseName(test_case_name = "B-60665 [D T] EDL Rules Facts and Disclosures")
	@Test(priority = 1, description = "Verify Rules and Facts for EDL ", groups = { "DESKTOP",
			"TABLET" }, testName = "B-60665 [D T] EDL Rules Facts and Disclosures")
	public void verify_rules_and_facts_pages() throws Exception {

		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		
		test_step_details(1, "Check Official Rules Page got redirected correctly");
		edl_instance.click_official_rules_link();
		switchToNewTab();
		assertEqualsIgnoreCase(infopage_instance.get_rules_page_title(), "Rules");
				
		test_step_details(2, "Verify that the link text and the heading are same");
		assertEquals(infopage_instance.get_official_rules_link_text(), infopage_instance.get_official_rules_title_text());
		assertEquals(infopage_instance.get_official_prizes_link_text(), infopage_instance.get_official_prizes_title_text());
		assertEquals(infopage_instance.get_edl_official_prizes_link_text(), infopage_instance.get_edl_official_prizes_title_text());
		
		test_step_details(3, "Verify that the rules link navigates to official rules section");
		infopage_instance.click_official_rules_link_text();
		assertIsStringContainsIgnoreCase(getCurrentUrl(), "official_rules");
		
		test_step_details(4, "Verify that the prizes link navigates to official prizes section");
		infopage_instance.click_official_prizes_link_text();
		assertIsStringContainsIgnoreCase(getCurrentUrl(), "official_prizes");
		
		test_step_details(5, "Verify that the edl prizes link navigates to edl prizes section");
		infopage_instance.click_edl_official_prizes_link_text();
		assertIsStringContainsIgnoreCase(getCurrentUrl(), "edl_official_prizes");
		
		test_step_details(6, "Check Facts Page got redirected correctly");
		switchToMainTab();
		edl_instance.click_sweepstakes_facts_link();
		switchToNewTab();
		assertEqualsIgnoreCase(getTitle(),"Facts");
		
		test_step_details(7, "Check Facts Page has 3 facts boxes when clicked from EDL page");
		assertEqualsInt(infopage_instance.get_facts_box_count(), 3);
				
		
	}
}
