package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class LightBoxPage extends BaseClass {

	private static final LightBoxPage lb_instance = new LightBoxPage();

	private LightBoxPage() {
	}

	public static LightBoxPage getInstance() {
		return lb_instance;
	}

	private final By optinbox_close = By.cssSelector("section.optin_lb.modal-dialog > button.close_lb");
	private final By lb_close_button = By.cssSelector("button.close_lb");
	private final By levelUp_Box = By.xpath("//div[@id='congratsBox']//span[contains(.,'Continue')]");
	private final By levelUp_Close = By.cssSelector("div#congratsBox a.dismiss_lb img");
	private final By lb_accept_button = By.cssSelector("div.fp_lb_button");
	private final By lb_sign_in_button = By.cssSelector("div.fp_lb_button_holder > a:nth-of-type(1)");
	private final By lb_register_button = By.cssSelector("div.fp_lb_button_holder > a:nth-of-type(2)");
	private final By cp_password = By.cssSelector("input[name=\"lbpassword required\"]");
	private final By cp_confirmpassword = By.cssSelector("input[name=\"lbconfirmPassword required\"]");
	private final By cp_submit = By.cssSelector("button.submit");
	private final By five_hundred_search_lb = By.cssSelector("img[alt='OK']");
	private final By welcome_lb_learn_more_link = By.cssSelector("div.welcome_learn_more>a");
	private final By complete_registration_button = By.cssSelector("div.fp_lb_button_holder > a");
	private final By evergage_popup = By.cssSelector("#evergage-tooltip-ambwmvuS >a>div>img");

	/**
	 * To create password for Silver user Added two parameters as we can use the
	 * same method for error verifications too.
	 */
	public void enter_create_password(String password, String confirmpassword) {
		textbox(cp_confirmpassword, "enter", password, 10);
		textbox(cp_password, "enter", confirmpassword, 10);
		button(cp_submit, 10);
	}

	/**
	 * To verify the create password LB
	 */
	public boolean verify_create_password_lb() {
		return elementPresent(cp_password) && elementPresent(cp_confirmpassword) && elementPresent(cp_submit);
	}
	
	/**
	 * Close on Evergage popup
	 * 
	 * @throws none
	 */
	public void close_evergage_popup() {
		if(verify_evergage_popup()){
			button(evergage_popup, 5);
		}
	}
	
	/**
	 * Verify evergage popup
	 * 
	 * @throws none
	 */
	public boolean verify_evergage_popup() {
		sleepFor(5);
		return elementPresent(evergage_popup);
	}

	/**
	 * to close Welcome light box
	 */
	public void close_welcome_light_box() {
		button(lb_close_button, 10);
	}

	/**
	 * to close Optin light box
	 */
	public void close_optin_light_box() {
		verify_optin_lb();
		button(optinbox_close, 10);
	}

	/**
	 * Close the Welcome and Optin light box
	 */
	public void close_welcome_optin_lb() {
		close_evergage_popup();
		close_welcome_light_box();
		close_optin_light_box();
	}

	/**
	 * Verify the presence of Optin lightbox
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verify_optin_lb() {
		sleepFor(5);
		return elementPresent(optinbox_close);
	}

	/**
	 * This method is explicitly to close Bronze level up. This will work only for
	 * Bronze Level.
	 */
	public void close_bronze_level_up_lb() throws Exception {
		try {
			waitForElement(levelUp_Box, 10);
			if (elementPresent(levelUp_Box)) {
				button(levelUp_Close, 15);
			}
		} catch (Exception ignore) {
		}
	}

	/**
	 * To close the level up celebration light box.
	 */
	public void close_level_up_lb() throws Exception {
		try {
			waitForElement(levelUp_Close, 10);
			if (elementPresent(levelUp_Close)) {
				button(levelUp_Close, 15);
			}
		} catch (Exception ignore) {
		}
	}

	/**
	 * Close the LB
	 */
	public void close_lb() {
		button(lb_close_button, 15);
	}

	/**
	 * Click Accept button on the LB
	 */
	public void accept_lb() {
		button(lb_accept_button, 15);
	}

	/**
	 * Verify the LB via the Accept button
	 */
	public boolean verify_lb_accept_button() {
		return elementVisibility(lb_accept_button);
	}

	/**
	 * Click the Sign In button from light box
	 */
	public void click_lb_sign_in() {
		button(lb_sign_in_button, 15);
	}

	/**
	 * Click the Register button from light box
	 */
	public void click_lb_register() {
		button(lb_register_button, 15);
	}

	/**
	 * Verify the Register light box
	 */
	public boolean verfiy_register_sign_in_lb() {
		return elementVisibility(lb_sign_in_button);
	}

	/**
	 * Verify the visibility of 500 search light box
	 * 
	 * @return
	 */
	public boolean verify_500_search_lb() {
		return elementVisibility(five_hundred_search_lb);
	}

	/***
	 * Click on the 500 search light box
	 */
	public void click_500_ok_button() {
		button(five_hundred_search_lb, 20);
	}

	/**
	 * Verify the visibility of Learn More link of Welcome light box
	 * 
	 * @return
	 */
	public boolean verify_welcome_lb_learn_more_link() {
		return elementVisibility(welcome_lb_learn_more_link);
	}

	/**
	 * Click the Learn More link from Welcome LB
	 * 
	 * @return
	 */
	public void click_welcome_lb_learn_more_link() {
		button(welcome_lb_learn_more_link, 30);
	}

	/**
	 * To verify the display of complete Registration LB is displayed
	 */
	public boolean verify_complete_registraion_lb() {
		return elementVisibility(complete_registration_button);
	}

	/**
	 * Click on complete Registration LB
	 */
	public void click_complete_registraion_lb() {
		button(complete_registration_button, 20);
	}
}
