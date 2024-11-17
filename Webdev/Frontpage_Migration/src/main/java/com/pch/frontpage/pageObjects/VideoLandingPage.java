package com.pch.frontpage.pageObjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class VideoLandingPage extends BaseClass {

	private static final VideoLandingPage instance = new VideoLandingPage();
	private final HomePage homepage_instance = HomePage.getInstance();

	private VideoLandingPage() {
	}

	public static VideoLandingPage getInstance() {
		return instance;
	}

	private final By entertainment_videos = By.xpath("//a[text()=' Entertainment']");
	private final By news_videos = By.xpath("//a[text()=' News']");
	private final By lifestyle_videos = By.xpath("//a[text()=' Lifestyle']");;
	private final By sports_videos = By.xpath("//a[text()=' Sports']");
	private final By breadcrumbs = By.cssSelector("div.container.breadcrumbs > h2");
	private final By playlistTitle = By.cssSelector("div.fp-video-playlist__title");
	private final By videotitle_fa = By.id("video-player-title");
	private final By video_desc_fa = By.id("vid-player-description");
	private final By tokens_claimed_button = By
			.cssSelector("div.fp-video-token-bar__tokens.fp-video-token-button.fp-video-token-button--just-claimed");
	private final By claimed_token_amount = By.cssSelector("span.fp-video-token-button__token-amt");
	private final By video_player = By.id("jwPlayer");
	private final By login = By.xpath("//a[contains(text(),'Log In')]");
	private final By register = By.xpath("(//a[contains(text(),'Register')])[2]");
	private final By nextVideo = By.cssSelector("small.fp-video-overlay-next-video__small-text");
	private final By complete_registration = By
			.cssSelector("section.fp-video-overlay-next-video__unrecognized-message--complete-reg");
	private final By play_circle = By.cssSelector(".glyphicon.glyphicon-play-circle");
	private final By next_video_desc_play_circle = By
			.cssSelector("aside.fp-video-overlay.fp-video-overlay-next-video[style='display: block;']");
	private final By close_popup = By.xpath("//*[@class='close_lb']");
	private final By back_to_home_link = By.cssSelector("a.next-section__link");
	private final By video_bottom_gpt_ad = By.cssSelector("div#gpt-ad-bottom-native");
	private final By video_inline_ad = By.cssSelector("div#div-gpt-ad-tile");
	private final By video_right_rail_ad1 = By.cssSelector("div#div-gpt-ad-multiple");
	private final By video_right_rail_ad2 = By.cssSelector("div#div-gpt-ad-box");
	private final By selected_video_title_on_bottom_playlist = By
			.cssSelector("li.fp-video-item.fp-video-item--selected div.fp-video-item__title");
	private final By video_play_mode = By
			.cssSelector("aside.fp-video-overlay.fp-video-overlay-next-video[style='display: none;']");

	/**
	 * Verify the display of Bottom GPT ad
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verify_bottom_gpt_ad() throws Exception {
		scrollToBottomOfPage();
		return elementPresent(video_bottom_gpt_ad);
	}

	/**
	 * Return the display of Back to Home link
	 * 
	 * @return
	 */
	public boolean verify_back_to_home_link() {
		return elementPresent(back_to_home_link);
	}

	/**
	 * Navigate to the respective sub-category page by using the given param label
	 * name.
	 * 
	 * @param sub_category_name
	 */
	public void navigate_sub_category_page_from_label(String sub_category_name) {
		By locator = By.xpath("//a[contains(text(), '" + sub_category_name + "')]");
		button(locator, 30);
	}

	/**
	 * To verify the play circle
	 * 
	 */
	public boolean verify_play_circle() {
		// fast_forward_jw_video_player();
		waitForElementNotVisible(video_play_mode, 5000);
		waitForElementToBePresent(next_video_desc_play_circle, 5000);
		return elementPresent(next_video_desc_play_circle);
	}

	public void click_play_circle() {
		verify_play_circle();
		button(play_circle, 500);
	}

	/**
	 * To get the login urls
	 */
	public String get_login_url() {
		waitForElementToBePresent(register, 500);
		return getAttribute(login, "href");
	}

	/**
	 * To get the register urls
	 */
	public String get_register_url() {
		waitForElementToBePresent(register, 500);
		return getAttribute(register, "href");
	}

	/**
	 * To verify the video end screen for Mini reg, Social user and silver usr.
	 */
	public boolean verify_videoendscreen_completeReg() {
		waitForElementToBePresent(complete_registration, 900);
		return elementPresent(complete_registration);
	}

	/**
	 * To verify the video end screen for unrec user.
	 */
	public boolean verify_next_video_unrec_user() {
		waitForElementToBePresent(register, 500);
		return elementPresent(login) && elementPresent(register) && elementPresent(nextVideo);
	}

	/**
	 * Verify the video playlist section by the given name
	 */
	public boolean verify_video_playlist(String category_name) {
		return elementPresent(
				By.xpath("//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), ' "
						+ category_name + "')]"));
	}

	/**
	 * To click on news video menu from video landing page
	 */
	public void click_news_videos() {
		button(news_videos, 5);
	}

	/**
	 * To click on sports video menu from video landing page
	 */
	public void click_sports_videos() {
		button(sports_videos, 5);
	}

	/**
	 * To click on entertainment video menu from video landing page
	 */
	public void click_entertainment_videos() {
		button(entertainment_videos, 5);
	}

	/**
	 * To verify the video player information
	 */
	public boolean verify_video_player() {
		return elementPresent(video_player);
	}

	/**
	 * To verify the video landing page completely
	 */
	public boolean verify_video_landing_page() {
		return elementPresent(videotitle_fa) && elementPresent(breadcrumbs);
	}

	/**
	 * To verify the category links on video landing page.
	 */
	public boolean verify_categoryPlaylist() {
		return elementPresent(entertainment_videos) && elementPresent(news_videos) && elementPresent(lifestyle_videos)
				&& elementPresent(sports_videos);
	}

	/**
	 * To verify the FA video section
	 */
	public boolean verify_fa_videosection() {
		return elementPresent(videotitle_fa) && elementPresent(video_desc_fa) && elementPresent(playlistTitle);
	}

	/**
	 * Retrieve the Video title
	 * 
	 * @return
	 */
	public String get_video_title() {
		return getText(videotitle_fa, 30);
	}

	/**
	 * Retrieve the Video title from bottom play list
	 * 
	 * @return
	 */
	public String get_video_title_on_bottom_playlist() {
		return getText(selected_video_title_on_bottom_playlist, 30);
	}

	public void close_completeregpopup() {
		button(close_popup, 20);
	}

	/**
	 * Verify the Tokens Claimed button on Video player
	 * 
	 * @return
	 */
	public boolean verify_tokens_claimed_button() {
		waitForElementVisibility(tokens_claimed_button, 250);
		return elementVisibility(tokens_claimed_button);
	}

	/**
	 * Retrieve the Claimed token amount
	 * 
	 * @return
	 */
	public String get_claimed_token_amount() {
		waitForElementVisibility(claimed_token_amount, 250);
		return getText(claimed_token_amount, 30);
	}

	/**
	 * Return the both Category & Sub-Category type
	 * 
	 * @return
	 */
	public String get_category_type() {
		String type = getText(breadcrumbs, 30);
		return type.contains("/") ? type.split("/")[1].toLowerCase().trim() : type.toLowerCase().trim();
	}

	/**
	 * Return the Video play list count
	 * 
	 * @return
	 */
	public int get_playlist_video_count(String category) {
		By entertainment_video_playlist_count = By.xpath("//section[@data-category-link='"
				+ category.toLowerCase().trim() + "']//div[contains(@class,'slick-active')]");
		return get_webelements_list(entertainment_video_playlist_count).size();
	}

	public boolean verify_previous_arrow_disable_status(String category) {
		By entertainment_previous_arrow_disable_status = By.cssSelector(
				"section[data-category-link='" + category.toLowerCase().trim() + "'] > svg[aria-disabled='true']");
		return elementPresent(entertainment_previous_arrow_disable_status);
	}

	public boolean verify_previous_arrow_enable_status(String category) {
		By entertainment_previous_arrow_enable_status = By.cssSelector(
				"section[data-category-link='" + category.toLowerCase().trim() + "'] > svg[aria-disabled='true']");
		return elementPresent(entertainment_previous_arrow_enable_status);
	}

	public boolean verify_next_arrow_enable_status(String category) {
		By entertainment_next_arrow_enable_status = By.cssSelector("section[data-category-link='"
				+ category.toLowerCase().trim() + "'] > svg[aria-disabled='false']:nth-of-type(2)");
		return elementPresent(entertainment_next_arrow_enable_status);
	}

	public void click_previous_arrow(String category) {
		By entertainment_previous_arrow_enable_status = By.cssSelector(
				"section[data-category-link='" + category.toLowerCase().trim() + "'] > svg[aria-disabled='true']");
		button(entertainment_previous_arrow_enable_status, 30);
	}

	public void click_next_arrow(String category) {
		By entertainment_next_arrow_enable_status = By.cssSelector("section[data-category-link='"
				+ category.toLowerCase().trim() + "'] > svg[aria-disabled='false']:nth-of-type(2)");
		button(entertainment_next_arrow_enable_status, 30);
	}

	/**
	 * Returns the Google unique id for the Bottom GPT Ad
	 * 
	 * @return
	 */
	public String get_bottom_ad_google_query_id() {
		return getAttribute(video_bottom_gpt_ad, "data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Inline Ad
	 * 
	 * @return
	 */
	public String get_inline_ad_google_query_id() {
		return getAttribute(video_inline_ad, "data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Right Rail Ad One
	 * 
	 * @return
	 */
	public String get_right_rail_ad_1_google_query_id() {
		return getAttribute(video_right_rail_ad1, "data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Right Rail Ad Two
	 * 
	 * @return
	 */
	public String get_right_rail_ad_2_google_query_id() {
		return getAttribute(video_right_rail_ad2, "data-google-query-id");
	}

	/**
	 * Return the total page Ad google id's
	 * 
	 * @return
	 */
	public String[] get_page_ad_google_query_id() {
		String ad_google_id[] = new String[4];
		ad_google_id[0] = get_inline_ad_google_query_id();
		ad_google_id[1] = get_right_rail_ad_1_google_query_id();
		ad_google_id[2] = get_right_rail_ad_2_google_query_id();
		return ad_google_id;
	}
	/**
	 * Cliam tokens and verify vip level up message
	 * @return true
	 */
	public boolean validating_vip_level_up(String firstName) {
		homepage_instance.click_vidoes_menu();
		assertTrue(verify_video_player());
		click_entertainment_videos();
		assertTrue(homepage_instance.verify_token_claim_status(msg_property_file_reader("video_watched")));
		try {
			String vip_c1_msg = "Hi, "+firstName+""+msg_property_file_reader("vip_c1_msg_header")+"\n"+msg_property_file_reader("vip_c1_msg_body");
			System.out.println("vip_c1_msg: "+vip_c1_msg);
			homepage_instance.clic_new_VIP_logo(5);
			sleepFor(2);
			String afterActivity= homepage_instance.get_vip_msg();
			assertNotEquals(afterActivity, vip_c1_msg);
		return true;
			}
			catch(Exception e) {
				return false;
			}
	}
}
