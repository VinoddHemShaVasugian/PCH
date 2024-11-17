package com.pageobjects;

import com.util.BaseClass;

public class OamPage extends BaseClass{
	
	private static final OamPage oam_instance = new OamPage();
	private OamPage(){}
	public static OamPage getInstance(){
		return oam_instance;
	}
	
	private final ContestEntryPage contest_entry_page = ContestEntryPage.getInstance();
	
	public void search_legacy_contest_entry(String user_email){
		contest_entry_page.click_search_button();
		contest_entry_page.click_leagacy_contest_entry_link();
		contest_entry_page.enter_search_email(user_email);
		contest_entry_page.click_search_on_entry_page();
	}

	public void login_oam() throws Exception {
		String oam_url = xmlReader(ENVIRONMENT, "OAMTool");
		oam_url = oam_url.substring(xmlReader(ENVIRONMENT, "OAMTool").indexOf("/") + 2, oam_url.length()).trim();
		oam_url = ("http://" + xmlReader(ENVIRONMENT, "OAMTool_username") + ":"
				+ xmlReader(ENVIRONMENT, "OAMTool_password") + "@" + oam_url).trim();
		invokeBrowser(oam_url);
		invokeBrowser(xmlReader(ENVIRONMENT, "OAMTool"));
		assertTrue(contest_entry_page.verify_managing_accounts());
	}
	
}
