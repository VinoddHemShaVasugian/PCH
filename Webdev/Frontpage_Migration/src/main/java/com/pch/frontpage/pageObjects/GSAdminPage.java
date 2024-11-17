package com.pch.frontpage.pageObjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class GSAdminPage extends BaseClass {

	private static GSAdminPage instance = new GSAdminPage();

	private GSAdminPage() {
	}

	public static GSAdminPage getInstance() {
		return instance;
	}

	private final By email = By.name("email");
	private final By password = By.name("password");
	private final By submit = By.cssSelector("button.btn.btn-primary.btn-block.btn-flat");
	private final By search_box = By.name("q");
	private final By guided_search_menu = By.xpath("//span[text()='Guided Searches']");
	private final By management_sub_menu = By
			.xpath("//span[text()='Guided Searches']/parent::a/following-sibling::ul//span[text()='Management']");
	private final By gs_search_box = By.cssSelector("input[placeholder='Search Guided-Search']");
	private final By view_icon = By.cssSelector("i.glyphicon.glyphicon-eye-open");
	private final By gs_return_button = By.xpath("//a[text()='Return']");
	private final By no_of_elements = By.id("elements");

	/**
	 * Login to the GS Admin
	 * 
	 * @param user_email
	 * @param user_password
	 */
	public void log_in(String user_email, String user_password) {
		textbox(email, "enter", user_email, 1);
		textbox(password, "enter", user_password, 1);
		button(submit, 1);
		waitForElementPresent(search_box, 30);
	}

	/**
	 * Navigate to respective GS article
	 * 
	 * @param GS_Id
	 */
	public void navigate_gs_article(String GS_Id) {
		button(guided_search_menu, 45);
		button(management_sub_menu, 45);
		textbox(gs_search_box, "enter", GS_Id, 60);
		waitForElementVisibility(By.xpath("//table[@id='guide-search-crud']//tbody//td[2][text()='" + GS_Id + "']"),
				45);
	}

	/**
	 * Open View mode of the Article
	 */
	public void view_gs_article() {
		button(view_icon, 60);
		waitForElementVisibility(gs_return_button, 60);
	}

	/**
	 * Returns the GS elements count
	 * 
	 * @return
	 */
	public int get_no_of_gs_elements() {
		return Integer.parseInt(getAttribute(no_of_elements, "value"));
	}
}