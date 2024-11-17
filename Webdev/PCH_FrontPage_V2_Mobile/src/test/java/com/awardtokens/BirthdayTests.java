package com.awardtokens;

import org.testng.annotations.Test;

import com.pageobjects.AccountsRegisterPage;
import com.pageobjects.AccountsSignInPage;
import com.pageobjects.HamburgerMenuPage;
import com.pageobjects.HomePage;
import com.pageobjects.JoomlaConfigPage;
import com.pageobjects.LightBoxPage;
import com.pageobjects.MyAccount;
import com.util.BaseClass;
import com.util.DB_Connector;
import com.util.PriorityListener.testCaseName;
import com.util.PriorityListener.testId;

public class BirthdayTests extends BaseClass {

	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();
	private final AccountsRegisterPage account_register_isntance = AccountsRegisterPage.getInstance();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final DB_Connector db_instance = DB_Connector.getInstance();
	private final AccountsSignInPage account_signin_instance = AccountsSignInPage.getInstance();
	private final MyAccount my_account_instance = MyAccount.getInstance();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final HamburgerMenuPage ham_menu_instance = HamburgerMenuPage.getInstance();
	private final String birthday_article_name = "Tokens / Birthday";

	@testId(test_id = "")
	@testCaseName(test_case_name = "BirthdayTokens_Desktop")
	@Test(priority = 1, groups = { "MOBILE" }, description = "Verify the Birthday token scenarios", testName = "")
	public void birthday_tokens() throws Exception {
		final String search_term = "Shoes";
		// Step 1
		test_step_details(1, "Login to Joomla and verify");
		invokeBrowser(xmlReader(ENVIRONMENT, "JoomlaURL"));
		admin_instance.log_in(xmlReader(ENVIRONMENT, "ValidJoomlaUserName"),
				xmlReader(ENVIRONMENT, "ValidJoomlaPassword"));
		admin_instance.goToArticlePage();
		admin_instance.search_for_article(birthday_article_name);
		String expected_birthday_desc = getAttribute(admin_instance.get_text_field_element_by_label("Description"),
				"value");
		String expected_birthday_token_amount = getAttribute(
				admin_instance.get_text_field_element_by_label("Tokens", "2"), "value");
		assertFalse(expected_birthday_desc.isEmpty());

		// Step 2
		test_step_details(2, "Create a full reg user with current date as dob from Frontpage application");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		homepage_instance.click_sign_in();
		homepage_instance.click_register();
		String user_email = account_register_isntance.register_full_user_with_optin(getCurrentDate("dd"),
				getCurrentMonth("MMMM"), getYearWithOffset(-15, "YYYY"));
		lb_instance.close_welcome_optin_lb();
		homepage_instance.search(search_term);
		lb_instance.close_level_up_lb();
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(my_account_instance.verify_token_transactions_details(expected_birthday_desc,
				expected_birthday_token_amount, 1), 1);

		// Step 3
		test_step_details(3, "Change the DOB expire date less than 2 days from current date");
		long updated_date_in_long = modifyCurrentDateByOffset("day", -2);
		db_instance.updateDailySearchCount(user_email, 0);
		db_instance.updateBirthDateExpireValue(user_email, updated_date_in_long);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_level_up_lb();
		ham_menu_instance.click_sign_out();
		homepage_instance.click_sign_in();
		account_signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.search(search_term);
		lb_instance.close_level_up_lb();
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(my_account_instance.verify_token_transactions_details(expected_birthday_desc,
				expected_birthday_token_amount, 2), 2);

		// Step 4
		test_step_details(4, "Change the DOB expire date less than 8 days from current date");
		updated_date_in_long = modifyCurrentDateByOffset("day", -8);
		db_instance.updateDailySearchCount(user_email, 0);
		db_instance.updateBirthDateExpireValue(user_email, updated_date_in_long);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		ham_menu_instance.click_sign_out();
		homepage_instance.click_sign_in();
		account_signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.search(search_term);
		lb_instance.close_level_up_lb();
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(my_account_instance.verify_token_transactions_details(expected_birthday_desc,
				expected_birthday_token_amount, 3), 3);

		// Step 5
		test_step_details(5, "Change the DOB expire date less than 5 days from current date");
		updated_date_in_long = modifyCurrentDateByOffset("day", -5);
		db_instance.updateDailySearchCount(user_email, 0);
		db_instance.updateBirthDateExpireValue(user_email, updated_date_in_long);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		ham_menu_instance.click_sign_out();
		homepage_instance.click_sign_in();
		account_signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.search(search_term);
		lb_instance.close_level_up_lb();
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(my_account_instance.verify_token_transactions_details(expected_birthday_desc,
				expected_birthday_token_amount, 4), 4);

		// Step 6
		test_step_details(6, "Change the DOB expire date more than 9 days from current date");
		updated_date_in_long = modifyCurrentDateByOffset("day", 9);
		db_instance.updateDailySearchCount(user_email, 0);
		db_instance.updateBirthDateExpireValue(user_email, updated_date_in_long);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		ham_menu_instance.click_sign_out();
		homepage_instance.click_sign_in();
		account_signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.search(search_term);
		lb_instance.close_level_up_lb();
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(my_account_instance.verify_token_transactions_details(expected_birthday_desc,
				expected_birthday_token_amount, 4), 4);

		// Step 7
		test_step_details(7, "Change the DOB expire date to Tomorrow date from current date");
		updated_date_in_long = modifyCurrentDateByOffset("day", 1);
		db_instance.updateDailySearchCount(user_email, 0);
		db_instance.updateBirthDateExpireValue(user_email, updated_date_in_long);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		ham_menu_instance.click_sign_out();
		homepage_instance.click_sign_in();
		account_signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.search(search_term);
		lb_instance.close_level_up_lb();
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(my_account_instance.verify_token_transactions_details(expected_birthday_desc,
				expected_birthday_token_amount, 4), 4);

		// Step 8
		test_step_details(8, "Change the DOB by My Account Info page to current date");
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		ham_menu_instance.click_sign_out();
		homepage_instance.click_sign_in();
		homepage_instance.click_register();
		user_email = account_register_isntance.register_full_user_with_optin(getDateWithOffset(-1, "dd"),
				getCurrentMonth("MMMM"), getYearWithOffset(-15, "YYYY"));
		lb_instance.close_welcome_optin_lb();
		updated_date_in_long = modifyCurrentDateByOffset("day", -2);
		db_instance.updateDailySearchCount(user_email, 0);
		db_instance.updateBirthDateExpireValue(user_email, updated_date_in_long);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		ham_menu_instance.click_sign_out();
		homepage_instance.click_sign_in();
		account_signin_instance.login(user_email, xmlReader(ENVIRONMENT, "ValidPassword"));
		homepage_instance.search(search_term);
		lb_instance.close_level_up_lb();
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(my_account_instance.verify_token_transactions_details(expected_birthday_desc,
				expected_birthday_token_amount, 1), 1);
		my_account_instance.click_my_info_link();
		my_account_instance.modify_dob(getCurrentDate("dd"), getCurrentMonth("MMMM"), getYearWithOffset(-15, "YYYY"));
		my_account_instance.click_my_info_update();
		db_instance.updateDailySearchCount(user_email, 0);
		invokeBrowser(xmlReader(ENVIRONMENT, "BaseURL"));
		lb_instance.close_level_up_lb();
		homepage_instance.search(search_term);
		assertIsStringContainsIgnoreCase(getCurrentUrl(),
				xmlReader(ENVIRONMENT, "BaseURL") + "search?q=" + search_term);
		ham_menu_instance.click_token_history();
		account_signin_instance.login(xmlReader(ENVIRONMENT, "ValidPassword"));
		assertEqualsInt(my_account_instance.verify_token_transactions_details(expected_birthday_desc,
				expected_birthday_token_amount, 1), 1);
	}
}
