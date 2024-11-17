package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class HamburgerMenuPage extends BaseClass {

	private static final HamburgerMenuPage hamburger_instance = new HamburgerMenuPage();

	private HamburgerMenuPage() {
	}

	public static HamburgerMenuPage getInstance() {
		return hamburger_instance;
	}

	private final By hamburger_menu_icon = By.cssSelector("div.uninav__burger");
	private final By user_name_on_menu = By.cssSelector("a.uninav-nav__user-name");
	private final By sign_in_out = By.cssSelector("a.uninav-nav__account-action");
	private final By collapse_my_account_menu = By.cssSelector("//a[text()='My Account']");
	private final By token_history = By.xpath("//a[text()='Token History']");
	private final By my_info = By.cssSelector("//a[text()='My Info']");
	private final By rewards = By.cssSelector("//a[text()='Rewards']");

	/**
	 * Open the HamBurger Menu
	 */
	public void open_hamburger_menu() {
		if (!verify_hamburger_menu()) {
			button(hamburger_menu_icon, 60);
		}
	}

	/**
	 * Close the HamBurger Menu
	 */
	public void close_hamburger_menu() {
		if (verify_hamburger_menu()) {
			button(hamburger_menu_icon, 60);
		}
	}

	/**
	 * Verify the Visibility of the Hamburger menu
	 * 
	 * @return
	 */
	public boolean verify_hamburger_menu() {
		return elementVisibility(user_name_on_menu);
	}

	/**
	 * Click on Sign Out link form Hamburger Menu
	 */
	public void click_sign_out() {
		open_hamburger_menu();
		button(sign_in_out, 60);
	}

	/**
	 * Click on Sign In link form Hamburger Menu
	 */
	public void click_sign_in() {
		open_hamburger_menu();
		button(sign_in_out, 60);
	}

	/**
	 * Click on the My Account main menu form Hamburger Menu
	 */
	public void click_my_account() {
		open_hamburger_menu();
		if (elementVisibility(token_history))
			button(collapse_my_account_menu, 60);
	}

	/**
	 * Click on Token History link form Hamburger Menu
	 */
	public void click_token_history() {
		click_my_account();
		button(token_history, 50);
	}

	/**
	 * Click on My Info link form Hamburger Menu
	 */
	public void click_my_info() {
		click_my_account();
		button(my_info, 50);
	}

	/**
	 * Click on Token History link form Hamburger Menu
	 */
	public void click_rewards() {
		click_my_account();
		button(rewards, 50);
	}

}