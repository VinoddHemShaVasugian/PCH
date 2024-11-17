package com.pageobjects;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class VideoPage extends BaseClass {

	private static final VideoPage video_instance = new VideoPage();

	private VideoPage() {
	}

	public static VideoPage getInstance() {
		return video_instance;
	}

	private final By video_category = By.cssSelector("div.container.breadcrumbs > h2 ");
	private final By video_player = By.id("jwPlayer");
	private final By video_playlist_title = By.cssSelector("div.fp-video-playlist__title");
	private final By selected_video_title_on_bottom_playlist = By
			.cssSelector("li.fp-video-item.fp-video-item--selected >div:nth-of-type(2)");
	private final By video_bar_text = By.cssSelector("span.fp-video-token-bar__text");
	private final By video_default_token_icon = By.cssSelector("img.fp-video-token-button__coin");
	private final By next_video_label_after_video_complete = By
			.cssSelector("small.fp-video-overlay-next-video__small-text");
	private final By play_circle_after_video_complete = By.cssSelector(".glyphicon.glyphicon-play-circle");
	private final By next_video_desc_play_circle = By
			.cssSelector("aside.fp-video-overlay.fp-video-overlay-next-video[style='display: block;']");
	private final By login = By.xpath("//a[contains(text(),'Log In')]");
	private final By register = By.xpath("//a[contains(text(),'Register')]");
	private final By video_play_mode = By
			.cssSelector("aside.fp-video-overlay.fp-video-overlay-next-video[style='display: none;']");
	private final By video_title = By.cssSelector("h1.fp-video-info__title");
	private final By video_description = By.id("vid-player-description");
	private final By back_to_page = By.cssSelector("a.next-section__link");
	private final By category_list = By.cssSelector("h3.section-header > a");
	private final By video_title_on_video_player = By
			.cssSelector("aside.fp-video-overlay.fp-video-overlay-video-title");
	private final By claimed_token_amount = By.cssSelector("span.fp-video-token-button__token-amt");
	private final By token_claimed_message = By
			.cssSelector("div.fp-video-token-bar__tokens.fp-video-token-button.fp-video-token-button--just-claimed");
	private final By token_already_claim_message = By.cssSelector(
			"div.fp-video-token-bar__tokens.fp-video-token-button.fp-video-token-button--just-claimed.fp-video-token-button--already-claimed");
	private final By complete_reg_message_on_video_player = By
			.cssSelector("section.fp-video-overlay-next-video__unrecognized-message--complete-reg");
	private final By play_video = By.cssSelector("div[aria-label='Start Playback']");
	private final By pause_video = By.cssSelector("div[aria-label='Pause']");
	private final By next_video = By.cssSelector("div[aria-label='Next']");
	private final By rewind_10s_video = By.cssSelector("div[aria-label='Rewind 10 Seconds']");

	/**
	 * Verify the presence of Complete Registration message on Video player
	 * 
	 * @return
	 */
	public boolean verify_complete_reg_on_video_player() {
		return elementVisibility(complete_reg_message_on_video_player);
	}

	/**
	 * Retrieve the Claimed token amount
	 * 
	 * @return
	 */
	public String get_claimed_token_amount() {
		return getText(claimed_token_amount, 60);
	}

	/**
	 * Verify the Token Already Claimed status of the Video
	 * 
	 * @return
	 */
	public boolean verify_video_already_claimed_status() {
		return elementVisibility(token_already_claim_message);
	}

	/**
	 * Verify the Token claimed status of the Video
	 * 
	 * @return
	 */
	public boolean verify_video_claimed_status() {
		return elementVisibility(token_claimed_message);
	}

	/**
	 * Retrieve the Video title from the video player
	 * 
	 * @return
	 */
	public String get_video_title_from_video_player() {
		return getAttribute(video_title_on_video_player, "innerHTML");
	}

	/**
	 * Click on the completely visible video from the Play list of the Category
	 * Menu
	 * 
	 * @param category_name
	 */
	public void click_visible_video_from_category_menu(String category_name) {
		By video_ele = By.cssSelector("section[data-category-name='" + category_name + "'] div[aria-hidden='false']>a");
		js_click(video_ele, 30);
	}

	/**
	 * Get the completely visible video title from the Play list of the Category
	 * Menu
	 * 
	 * @param category_name
	 */
	public String get_visible_video_title_from_category_menu(String category_name) {
		By video_ele = By.cssSelector(
				"section[data-category-name='" + category_name + "'] div[aria-hidden='false'] span:nth-of-type(2)");
		String title = getAttribute(video_ele, "innerHTML");
		return title = title.substring(0, title.indexOf(".") - 1);
	}

	/**
	 * Return the available Category Menu URL's on the Video page
	 * 
	 * @return
	 */
	public ArrayList<String> get_category_links() {
		ArrayList<String> menu_url = new ArrayList<String>();
		for (WebElement ele : get_webelements_list(category_list)) {
			menu_url.add(ele.getAttribute("href"));
		}
		return menu_url;
	}

	/**
	 * Return the available Category Menu Name on the Video page
	 * 
	 * @return
	 */
	public ArrayList<String> get_category_menu_name() {
		ArrayList<String> menu_name = new ArrayList<String>();
		for (WebElement ele : get_webelements_list(category_list)) {
			menu_name.add(ele.getText().toLowerCase());
		}
		return menu_name;
	}

	/**
	 * Return the presence of the Video Categories on the Video page
	 * 
	 * @return
	 */
	public boolean verify_category_list() {
		return get_webelements_list(category_list).size() > 0 ? true : false;
	}

	/**
	 * Retrieve the Video Title from the bottom of the video player section
	 * 
	 * @return
	 */
	public String get_video_title_from_bottom_of_video_player() {
		return getText(video_title, 60);
	}

	/**
	 * Retrieve the Video DescriptionF from the bottom of the video player
	 * section
	 * 
	 * @return
	 */
	public String get_video_desc() {
		return getText(video_description, 60);
	}

	/**
	 * Verify the Back to Home/Featured link
	 * 
	 * @return
	 */
	public boolean verify_back_to_home_featured() {
		return elementVisibility(back_to_page);
	}

	/**
	 * Verify the Token default icon
	 * 
	 * @return
	 */
	public boolean verify_default_token_icon() {
		return elementVisibility(video_default_token_icon);
	}

	/**
	 * Retrieve the Video bar text
	 * 
	 * @return
	 */
	public String get_video_bar_text() {
		return getText(video_bar_text, 60);
	}

	/**
	 * Retrieve the Video title from the bottom play list
	 * 
	 * @return
	 */
	public String get_selected_video_title_from_bottom_playlist() {
		String title = getAttribute(selected_video_title_on_bottom_playlist, "innerHTML");
		return title = title.substring(0, title.indexOf(".") - 1).trim();
	}

	/**
	 * Get the Video Category type
	 */
	public String get_video_category_name() {
		String cat_name = getText(video_category, 60).toLowerCase();
		return cat_name.substring(cat_name.indexOf("/") + 1, cat_name.length());
	}

	/**
	 * Verify the Video player
	 */
	public boolean verify_video_player() {
		return elementVisibility(video_player);
	}

	/**
	 * Retrieve the Video play list video title
	 * 
	 * @return
	 */
	public String get_video_playlist_title() {
		return getText(video_playlist_title, 60);
	}

	/**
	 * To verify the play circle
	 * 
	 */
	public boolean verify_play_circle() {
		// waitForElementNotVisible(video_play_mode, 5000);
		System.out.println(elementPresent(pause_video));
		waitForElementToBePresent(next_video_desc_play_circle, 500);
		return elementPresent(next_video_desc_play_circle);
	}

	/**
	 * Play the video.
	 */
	public void play_video() {
		if (elementPresent(play_video)) {
			button(video_player, 60);
			// button(play_video, 60);
			// button(play_video, 60);
			js_click(play_video, 60);
		}
	}

	/**
	 * Pause the video.
	 */
	public void pause_video() {
		if (elementVisibility(pause_video)) {
			button(pause_video, 60);
		}
	}

	/**
	 * Goto the Next video
	 */
	public void next_video() {
		if (elementVisibility(next_video)) {
			button(next_video, 60);
		}
	}

	/**
	 * Rewind the video for 10s.
	 */
	public void rewind_video() {
		if (elementVisibility(rewind_10s_video)) {
			button(rewind_10s_video, 60);
		}
	}

	/**
	 * Click on the Play circle icon
	 */
	public void click_play_circle() {
		verify_play_circle();
		button(play_circle_after_video_complete, 500);
	}

	/**
	 * To get the login URL
	 */
	public String get_login_url() {
		waitForElementToBePresent(register, 500);
		return getAttribute(login, "href");
	}

	/**
	 * To get the register URL
	 */
	public String get_register_url() {
		waitForElementToBePresent(register, 500);
		return getAttribute(register, "href");
	}

	/**
	 * To verify the video end screen
	 */
	public boolean verify_next_video() {
		return elementVisibility(next_video_label_after_video_complete);
	}

	public boolean verify_previous_arrow_disable_status(String category) {
		By previous_arrow_disable_status = By.xpath("//section[@data-category-name='" + category.toLowerCase().trim()
				+ "']/*[name()='svg'][@aria-disabled='true']");
		return elementVisibility(previous_arrow_disable_status);
	}

	public boolean verify_previous_arrow_enable_status(String category) {
		By previous_arrow_enable_status = By.xpath("//section[@data-category-name='" + category.toLowerCase().trim()
				+ "']/*[name()='svg'][@aria-disabled='false'][1]");
		return elementVisibility(previous_arrow_enable_status);
	}

	public boolean verify_next_arrow_enable_status(String category) {
		By next_arrow_enable_status = By.xpath("//section[@data-category-name='" + category.toLowerCase().trim()
				+ "']/*[name()='svg'][@aria-disabled='false']");
		return elementVisibility(next_arrow_enable_status);
	}

	public void click_previous_arrow(String category) {
		By previous_arrow = By
				.xpath("//section[@data-category-name='" + category.toLowerCase().trim() + "']/*[name()='svg'][1]");
		button(previous_arrow, 30);
	}

	public void click_next_arrow(String category) {
		By next_arrow = By.xpath("//section[@data-category-name='" + category.toLowerCase().trim()
				+ "']/*[local-name()='svg'][2]/*[name()='polyline']");
		button(next_arrow, 30);
	}

}
