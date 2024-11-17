package com.awardtokens;

import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.CentralServices_Page;
import com.pageobjects.HamburgerMenuPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class SegIdTokens extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final CentralServices_Page cs_instnace = CentralServices_Page.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final MyAccount my_account_instance = MyAccount.getInstance();
	private final AccountsSignInPage account_signin_instance = AccountsSignInPage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final HamburgerMenuPage ham_menu_instance = HamburgerMenuPage.getInstance();

	String token_seg_id_article_name = "Tokens / Link Promotion / SegId 111814-021815";

	@testId(test_id = "33085")
	@testCaseName(test_case_name = "AwardTokensWhenFollowingLinkOrSegID_Desktop_Tab")
	@Test(priority = 1, description = "Verify the token scenario for Seg Id user", groups = { "DESKTOP", "TABLET",
			"SANITY" }, testName = "33085:AwardTokensWhenFollowingLinkOrSegID_Desktop_Tab")
	public void verify_tokens_seg_id() throws Exception {
		String seg_id_activity_msg;
		String seg_id_token_value;
		String seg_id_value;
		// Step 1
		test_step_details(1, "Navigate to Joomla Admin application to getSeg Id Token value");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(token_seg_id_article_name);
		seg_id_activity_msg = getAttribute(admin_instance.get_text_field_element_by_label("Description"), "value");
		seg_id_token_value = getAttribute(admin_instance.get_text_field_element_by_label("Tokens"), "value");
		seg_id_value = getAttribute(admin_instance.get_text_field_element_by_label("Conditions"), "value").split("=")[1]
				.trim();

		// Step 2
		test_step_details(2, "Create a Full Reg user from CS page");
		invokeBrowser(xmlReader(ENVIRONMENT, "CentralServicesPageURL"));
		String[] user_details = cs_instnace.createGoldUser();

		// Step 3
		test_step_details(3, "Login to the application with the Seg Id and do a first search");
		invokeBrowser(user_details[1] + "&segid=" + seg_id_value);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.search(randomString(5, 6));
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(
				my_account_instance.verify_token_transactions_details(seg_id_activity_msg, seg_id_token_value, 1), 1);

		// Step 4
		test_step_details(4, "Verify the Seg Id token value for the same user second time");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		invokeBrowser(user_details[1] + "&segid=" + seg_id_value);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.search(randomString(5, 6));
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(
				my_account_instance.verify_token_transactions_details(seg_id_activity_msg, seg_id_token_value, 1), 1);

		// Step 5
		test_step_details(5, "Update the Daily search count and verify the Seg Id tokens for the same user");
		db_instance.updateDailySearchCount(user_details[0], 0);
		db_instance.updateTokenAwardLinkPromo(user_details[0]);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		delete_session_cookies();
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		invokeBrowser(user_details[1] + "&segid=" + seg_id_value);
		lb_instance.close_welcome_optin_lb();
		homepage_instance.search(randomString(5, 6));
		lb_instance.close_level_up_lb();
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(
				my_account_instance.verify_token_transactions_details(seg_id_activity_msg, seg_id_token_value, 2), 2);
	}
}
