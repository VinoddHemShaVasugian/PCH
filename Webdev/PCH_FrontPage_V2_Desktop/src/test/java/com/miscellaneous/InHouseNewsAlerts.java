package com.miscellaneous;

import org.testng.annotations.Test;

import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HomePage;
import com.pageobjects.InHouseAlertPage;
import com.pageobjects.JoomlaConfigPage;
import com.util.BaseClass;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class InHouseNewsAlerts extends BaseClass {

	private final HomePage homepage_instance = HomePage.getInstance();
	private final InHouseAlertPage news_alert_instance = InHouseAlertPage.getInstance();
	private final AccountsSignInPage signin_instance = AccountsSignInPage.getInstance();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();

	private final String fp_news_alert_article_name = "NewsAlert";

	@testId(test_id = "33442")
	@testCaseName(test_case_name = "In-House News Alerts on Top Stories Section")
	@Test(priority = 1, description = "Verify the In House Alerts on Category page", groups = { "DESKTOP",
			"TABLET" }, testName = "33442:In-House News Alerts on Top Stories Section")
	public void verify_in_house_alerts() throws Exception {
		// Step 1
		test_step_details(1, "Navigate to Joomla Admin application");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(fp_news_alert_article_name);
		String alert_category = get_first_selected_option(
				admin_instance.get_dropdown_field_element_by_label("Category", "2"), "text", 15);
		int alert_category_by_value = Integer.parseInt(get_first_selected_option(
				admin_instance.get_dropdown_field_element_by_label("Category", "2"), "value", 15));
		int alert_position_on_page = Integer.parseInt(get_first_selected_option(
				admin_instance.get_dropdown_field_element_by_label("Alert Position"), "text", 15));
		String alert_redirect_link = getAttribute(admin_instance.get_text_field_element_by_label("Alert Link"), "value")
				.toLowerCase();
		admin_instance.publish_article();
		admin_instance.search_for_article(alert_category);
		admin_instance.select_option_dropdown_field_element_by_label("Top Stories Category", alert_category, 1);
		admin_instance.publish_article();
		invokeBrowser(xmlReader(ENVIRONMENT, "app_import_site_pages"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		step_validator(1, true);

		// Step 2
		test_step_details(2, "Navigate to frontpage.qa.pch.com and sign-in wiith valid credentials");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_SignIn();
		signin_instance.login(xmlReader(ENVIRONMENT, "ValidUserName1"), xmlReader(ENVIRONMENT, "ValidPassword"));
		assertTrue(homepage_instance.verify_Home());
		step_validator(2, true);

		// Step 3
		test_step_details(3, "Navigate to category page which has News Alert and verify it");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + alert_category.toLowerCase());
		homepage_instance.close_openx_banner();
		assertTrue(news_alert_instance.verify_news_alert_section(fp_news_alert_article_name));//debug
		assertEqualsInt(news_alert_instance.get_position_of_news_alert_on_category_page(fp_news_alert_article_name),
				alert_position_on_page);
		news_alert_instance.click_on_news_alert_section(fp_news_alert_article_name);
		assertIsStringContains(getCurrentUrl(), alert_redirect_link);
		step_validator(3, true);

		// Step 4
		test_step_details(4, "Navigate to News Alert article on Admin to change the position and category page");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(fp_news_alert_article_name);
		alert_position_on_page = alert_position_on_page <= 3 ? 5 : 1;
		news_alert_instance.select_news_alert_positon(alert_position_on_page);
		alert_category_by_value = alert_category_by_value % 2 == 0 ? alert_category_by_value % 2 + 1
				: alert_category_by_value % 2 + 1;
		news_alert_instance.select_news_alert_category_page(alert_category_by_value);
		alert_category = get_first_selected_option(admin_instance.get_dropdown_field_element_by_label("Category", "2"),
				"text", 15).toLowerCase();
		admin_instance.save_and_close_article();
		step_validator(4, true);

		// Step 5
		test_step_details(5, "Clear the Cache and verify the news alert changes");
		invokeBrowser(xmlReader(ENVIRONMENT, "app_clear_cache"));
		invokeBrowser(xmlReader(ENVIRONMENT, "app_update_cache_content"));
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL") + alert_category);
		assertEqualsInt(news_alert_instance.get_position_of_news_alert_on_category_page(fp_news_alert_article_name),
				alert_position_on_page);
		step_validator(5, true);
	}
}
