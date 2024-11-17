package com.pageobjects;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class HomePage extends BaseClass {

	private static final HomePage instance = new HomePage();

	private HomePage() {
	}

	public static HomePage getInstance() {
		return instance;
	}

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final By main_catagory_menu_urls = By.xpath("//div[@id='header-menu']//ul//a");
	private final By lottery_menu = By.xpath("//a[text()='Lottery ']");
	private final By home_menu = By.xpath("//a[text()='Home '] ");
	private final By video_menu = By.xpath("//a[contains(text(),'Video ')]");
	private final By bact_to_top = By.id("back-to-top");
	private final By complete_registration_link = By
			.cssSelector("div.uninav__multi-nav.uninav__small.uninav__multi-nav--two-line>a");
	private final By weather_menu = By.cssSelector("div#header-menu li>a>img");
	private final By signin = By.xpath("//div[@class='uninav__multi-nav uninav__small']/a[text()='Sign In']");
	private final By claim_token_amount = By.cssSelector("span.buttons__token-amount");
	private final By claim_token_button = By.cssSelector("button.buttons.buttons_claim.buttons_green.unclaimed");
	private final By claimed_token_button = By.cssSelector("button.buttons.buttons_claim.buttons_grey.claimed");
	private final By token_icon_uni_nav = By.cssSelector("span.uninav__token-icon");
	private final By token_amount_uni_nav = By.cssSelector("span.uninav__token-amount");
	private final By token_amount_overlay_uni_nav = By.cssSelector("div.uninav__display-token.shown");
	private final By status_icon_uni_nav = By.cssSelector("span.uninav__token-level-icon");
	private final By unis_message = By.cssSelector("span.pch-notify-module__message");
	private final By first_article_link_from_top_stories = By
			.xpath("//li[@class='top-stories__li']/a[contains(@href,'/article')][1]");
	private final By first_video_link_from_top_stories = By
			.xpath("//li[@class='top-stories__li']/a[contains(@href,'/video')][1]");
	private final By daily_bonus_game_bar_check_list = By
			.cssSelector("li.progress-bar-step.progress-bar-step--checked");
	private final By daily_bonus_game_locked_icon = By.cssSelector("img.progress-bar-sticky__lock");
	private final By daily_bonus_game_play_icon = By.cssSelector("img.progress-bar-sticky__play-now");
	private final By daily_bonus_game_info_icon = By.cssSelector("div.progress-bar-sticky__title");
	private final By daily_bonus_game_info_window = By.cssSelector("div.progress-bar-sticky-menu.animated.fadeInUp");
	private final By daily_bonus_game_info_window_close = By.cssSelector("div.progress-bar-sticky-menu__close");
	private final By top_stories_list = By.cssSelector("li.top-stories__li");
	private final By our_pick_count = By.cssSelector("div.our-pick>a");
	private final By our_pick_weather_tile = By.xpath("//div[@class='our-pick']/a/div[text()='Weather']");
	private final By our_pick_horoscope_tile = By.xpath("//div[@class='our-pick']/a/div[text()='Horoscopes']");
	private final By our_pick_lottery_tile = By.xpath("//div[@class='our-pick']/a/div[text()='Lottery']");
	private final By category_tile_headers = By.cssSelector("h3.section-header>a");
	private final By category_native_ad = By.cssSelector("div#div-gpt-ad-bottom");
	private final By category_native_ad_size = By.cssSelector("div#div-gpt-ad-bottom iframe");
	private final By trending_now_ad = By.id("gpt-ad-trending");
	private final By trending_now_native_ad_size = By.cssSelector("div#gpt-ad-trending iframe");
	private final By our_pick_sponsored_ad = By.cssSelector("div#div-nativ-320x180-ad-ourpicks");
	private final By our_pick_sponsored_ad_size = By.cssSelector("div#div-nativ-320x180-ad-ourpicks iframe");
	private final By trending_now_items = By
			.cssSelector("div[class='bottom-spacer trending-now']:nth-of-type(even)>div[class$='trending-now__item']");
	private final By popular_videos_items = By
			.cssSelector("div[class='bottom-spacer trending-now']:nth-of-type(odd)>div[class$='trending-now__item']");
	private final By popular_video_section_title = By
			.xpath("//div[@class='bottom-spacer trending-now'][2]/preceding-sibling::h3[1]");
	private final By search_field = By.id("searchField2");
	private final By search_submit_btn = By.cssSelector("div.search__bar-wrapper__submit");
	private final By popular_search_iframe = By.cssSelector("iframe.guidedSearchFrame");
	private final By popular_searches_terms = By.cssSelector("body div.two-row-scroller ul li a");
	private final By popular_searches_header_bar = By
			.cssSelector("div#header h3.section-header.popular-searches__header");
	private final By bottom_complete_reg_msg_non_fully_reg_users = By
			.cssSelector("div#claimBtnMsgOutsideUrecognized div");
	private final By category_page_gpt_ad = By.id("div-gpt-300x250-ad-box");
	private final By category_page_gpt_ad_size = By.cssSelector("div#div-gpt-300x250-ad-box iframe");
	private final By top_stories_section = By.id("top-stories");
	private final By header_part = By.id("header-main-top");
	private final By header_menu = By.id("header-menu");
	private final By footer_part = By.className("footer");
	private final By progress_bar = By.id("progress-bar-sticky");
	private final By error_message_fp_content_down = By.xpath("//*[@id='wrapper']/main//div[@id='left']");
	private final By register = By.xpath("//a[text()='Register']");
	private final By openx_image_hide_button = By.cssSelector("div.openXImageHolder>span#plug-wrapper__hide");

	/**
	 * 
	 * @return The complete registration message present in bottom of the page
	 *         for all Non Fully registered user
	 */
	public String get_bottom_complete_reg_msg_of_non_fully_reg_users() {
		return getText(bottom_complete_reg_msg_non_fully_reg_users, 30);
	}

	/**
	 * Verify the presence of the bottom complete registration message for all
	 * Non Fully registered user
	 * 
	 * @return
	 */
	public boolean verify_bottom_complete_reg_msg_of_non_fully_reg_users() {
		return elementPresent(bottom_complete_reg_msg_non_fully_reg_users);
	}

	/**
	 * Enter value to the text field
	 * 
	 * @param text_to_enter
	 */
	public void search(String text_to_enter) {
		textbox(search_field, "enter", text_to_enter, 15);
		button(search_submit_btn, 30);
	}

	/**
	 * Verify the presence of the text field
	 * 
	 * @param text_to_enter
	 */
	public boolean verify_search_box() {
		return elementPresent(search_field);
	}

	/**
	 * 
	 * @return The Popular video section title
	 */
	public String get_popular_videos_section_title() {
		return getText(popular_video_section_title, 30);
	}

	/**
	 * 
	 * @return The presence of the Trending Now Section Ad
	 */
	public boolean verify_trending_now_ad() {
		return elementVisibility(trending_now_ad);
	}

	/**
	 * 
	 * @return The count of the Trending Now Section
	 */
	public int get_trending_now_elements_count() {
		return get_webelements_list(trending_now_items).size();
	}

	/**
	 * 
	 * @return The count of the Popular Videos
	 */
	public int get_popular_videos_count() {
		return get_webelements_list(popular_videos_items).size();
	}

	/**
	 * Retrieve the count of the Category Tile sections
	 * 
	 * @return Category Tile counts
	 */
	public String get_category_tile_count() {
		return String.valueOf(get_webelements_list(category_tile_headers).size());
	}

	/**
	 * Retrieve the count of the Our Picks
	 * 
	 * @return Our Picks sizes
	 */
	public String get_our_pick_count() {
		return String.valueOf(get_webelements_list(our_pick_count).size());
	}

	/**
	 * 
	 * @return Element presence of Our Pick Weather Tile
	 */
	public boolean verify_our_pick_weather_tile() {
		return elementVisibility(our_pick_weather_tile);
	}

	/**
	 * Click on Weather Tile of Our Pick section
	 */
	public void click_our_pick_weather_tile() {
		button(our_pick_weather_tile, 30);
	}

	/**
	 * 
	 * @return Element presence of Our Pick Lottery Tile
	 */
	public boolean verify_our_pick_lottery_tile() {
		return elementVisibility(our_pick_lottery_tile);
	}

	/**
	 * Click on Lottery Tile of Our Pick section
	 */
	public void click_our_pick_lotery_tile() {
		button(our_pick_lottery_tile, 30);
	}

	/**
	 * 
	 * @return Element presence of Our Pick Horoscope Tile
	 */
	public boolean verify_our_pick_horoscope_tile() {
		return elementVisibility(our_pick_horoscope_tile);
	}

	/**
	 * Click on Horoscope Tile of Our Pick section
	 */
	public void click_our_pick_horoscope_tile() {
		button(our_pick_horoscope_tile, 30);
	}

	/**
	 * Retrieve the count of the Top Stories
	 * 
	 * @return Top stories sizes
	 */
	public String get_top_stories_count() {
		return String.valueOf(get_webelements_list(top_stories_list).size());
	}

	/**
	 * Click the First Article link of the Top Stories section of the Home page
	 * 
	 */
	public void click_first_article_link() {
		moveToElement(get_webelements_list(first_article_link_from_top_stories).get(0));
		button(get_webelements_list(first_article_link_from_top_stories).get(0), 60);
	}

	/**
	 * Click the First Video link of the Top Stories section of the Home page
	 * 
	 */
	public void click_first_video_link() {
		moveToElement(get_webelements_list(first_video_link_from_top_stories).get(0));
		button(get_webelements_list(first_video_link_from_top_stories).get(0), 60);
	}

	/**
	 * Retrieve the UNiS message
	 * 
	 * @return
	 */
	public String get_unis_message() {
		return getAttribute(unis_message, "innerHTML").replaceAll("<br>", " ").replaceAll(",", "");
	}

	/**
	 * Verify the visibility of the Token Icon on the UniNav
	 * 
	 * @return
	 */
	public boolean verify_token_icon_uni_nav() {
		return elementVisibility(token_icon_uni_nav);
	}

	/**
	 * Verify the visibility of the Status Icon on the UniNav
	 * 
	 * @return
	 */
	public boolean verify_status_icon_uni_nav() {
		return elementVisibility(status_icon_uni_nav);
	}

	/**
	 * Verify the visibility of the Token amount overlay on the UniNav
	 * 
	 * @return
	 */
	public boolean verify_token_overlay_icon_uni_nav() {
		return elementVisibility(token_amount_overlay_uni_nav);
	}

	/**
	 * Click on Token Icon on UniNav
	 */
	public void click_token_icon_uni_nav() {
		button(token_icon_uni_nav, 60);
	}

	/**
	 * Retrieve the token amount from the UniNav
	 * 
	 * @return
	 */
	public int get_token_amount_from_uni_nav() {
		if (!verify_token_overlay_icon_uni_nav())
			click_token_icon_uni_nav();
		String token_amount = getText(token_amount_uni_nav, 60).replaceAll(",", "");
		token_amount = token_amount.substring(0, token_amount.indexOf(" ")).trim();
		return Integer.parseInt(token_amount);
	}

	/**
	 * Verify the visibility of the Complete Registration link
	 * 
	 * @return
	 */
	public boolean verify_complete_registration_link_uni_nav() {
		return elementVisibility(complete_registration_link);
	}

	/**
	 * Click on the Complete Registration link
	 */
	public void click_complete_reg_link_uni_nav() {
		button(complete_registration_link, 45);
	}

	/**
	 * Click on the Back to Top link
	 */
	public void click_back_to_top() {
		button(bact_to_top, 60);
	}

	/**
	 * Click the Lottery Menu from the Header Menu
	 */
	public void click_lottery_menu() {
		button(lottery_menu, 45);
	}

	/**
	 * Click the Home Menu from the Header Menu
	 */
	public boolean verify_Home() {
		return elementPresent(token_icon_uni_nav);
	}

	public void click_register() {
		button(register, 20);
	}

	/**
	 * Return the Main Category pages Url's. It includes the More menu also.
	 * 
	 * @return
	 */
	public LinkedList<String> get_main_catagory_menu_url_list() {
		List<WebElement> ele_list = get_webelements_list(main_catagory_menu_urls);
		LinkedList<String> main_catagory_urls = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			String url = ele.getAttribute("href");
			if (!url.endsWith("#") && !url.equals("/")) {
				main_catagory_urls.add(url);
			}
		}
		return main_catagory_urls;
	}

	/**
	 * Return the Sub Category pages Url's.
	 * 
	 * @return
	 */
	public LinkedList<String> get_sub_catagory_menu_url_list() {
		List<WebElement> ele_list = get_webelements_list(category_tile_headers);
		LinkedList<String> sub_catagory_urls = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			sub_catagory_urls.add(ele.getAttribute("href"));
		}
		return sub_catagory_urls;
	}

	/**
	 * Return the Sub Category menu's.
	 * 
	 * @return
	 */
	public LinkedList<String> get_sub_catagory_menu_names() {
		List<WebElement> ele_list = get_webelements_list(category_tile_headers);
		LinkedList<String> sub_catagory_name = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			sub_catagory_name.add(ele.getText());
		}
		return sub_catagory_name;
	}

	/**
	 * Click the Home Menu from the Header Menu
	 */
	public void click_home_menu() {
		button(home_menu, 45);
	}

	/**
	 * Click the Video Menu from the Header Menu
	 */
	public void click_video_menu() {
		button(video_menu, 45);
	}

	/**
	 * Click the Weather Menu from the Header Menu
	 */
	public void click_weather_menu() {
		button(weather_menu, 30);
	}

	/**
	 * Click the Sign-In buton from the UniNav
	 */
	public void click_sign_in() {
		button(signin, 10);
	}

	/**
	 * Click on Claim button
	 */
	public void click_claim_button() {
		button(claim_token_button, 10);
	}

	/**
	 * 
	 * @return the token amount
	 */
	public String get_unclaimed_token_amount() {
		return getText(claim_token_amount, 60);
	}

	/**
	 * Verify the claim tokens button
	 */
	public boolean verify_claim_button() {
		return elementVisibility(claim_token_button);
	}

	/**
	 * Verify the claimed status button
	 */
	public boolean verify_claimed_button() {
		return elementVisibility(claimed_token_button);
	}

	/**
	 * Return the count of the Daily bonus game unlocked state
	 * 
	 * @return
	 */
	public int get_daily_bonus_game_check_count() {
		return get_webelements_list(daily_bonus_game_bar_check_list).size();
	}

	/**
	 * Verify the Daily bonus game progress bar lock icon
	 * 
	 * @return
	 */
	public boolean verify_daily_bonus_game_lock_icon() {
		return elementVisibility(daily_bonus_game_locked_icon);
	}

	/**
	 * Verify the Daily bonus game progress bar lock icon
	 * 
	 * @return
	 */
	public boolean verify_daily_bonus_game_play_icon() {
		return elementVisibility(daily_bonus_game_play_icon);
	}

	/**
	 * Click the daily bonus game info icon
	 */
	public void click_daily_bonus_game_info_icon() {
		button(daily_bonus_game_info_icon, 15);
	}

	/**
	 * Verify the Daily bonus game info icon
	 * 
	 * @return
	 */
	public boolean verify_daily_bonus_game_info_window() {
		return elementVisibility(daily_bonus_game_info_window);
	}

	/**
	 * Close the Daily bonus game info window
	 */
	public void close_daily_bonus_game_info_window() {
		button(daily_bonus_game_info_window_close, 20);
	}

	/**
	 * Click on Daily bonus game progress bar lock icon
	 * 
	 * @return
	 */
	public void click_daily_bonus_game_play_icon() {
		button(daily_bonus_game_play_icon, 30);
	}

	/**
	 * Verify the display of Our Pick Sponsored Ad
	 * 
	 * @return
	 */
	public boolean verify_our_pick_sponsored_ad() {
		return elementVisibility(our_pick_sponsored_ad);
	}

	/**
	 * Return the size of Our Pick Sponsored Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_our_pick_sponsored_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(our_pick_sponsored_ad_size, "width");
		size[1] = getAttribute(our_pick_sponsored_ad_size, "height");
		return size;
	}

	/**
	 * Verify the display of Category Native Ad
	 * 
	 * @return
	 */
	public boolean verify_category_native_ad() {
		return elementVisibility(category_native_ad);
	}

	/**
	 * Return the size of Category Native Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_category_native_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(category_native_ad_size, "width");
		size[1] = getAttribute(category_native_ad_size, "height");
		return size;
	}

	/**
	 * Return the size of Trending Now Native Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_trending_now_native_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(trending_now_native_ad_size, "width");
		size[1] = getAttribute(trending_now_native_ad_size, "height");
		return size;
	}

	/**
	 * Verify the display of Popular Search Header bar
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verify_popular_searches_header_bar() throws Exception {
		return elementVisibility(popular_searches_header_bar);
	}

	/**
	 * Returns the text of Popular Search Header bar
	 * 
	 * @return
	 * @throws Exception
	 */
	public String get_popular_search_header_bar_text() throws Exception {
		return getText(popular_searches_header_bar, 30);
	}

	/**
	 * Verify the display of Popular Search terms
	 * 
	 * @return
	 */
	public boolean verify_popular_search_terms() throws Exception {
		boolean status = true;
		try {
			switch_to_iframe_by_webelement(popular_search_iframe, 0);
			status = elementVisibility(popular_searches_terms);
			switch_iframe_default_content();
		} catch (Exception e) {
			log.error("Popular search iframe is not found.");
			status = false;
		}
		return status;
	}

	/**
	 * Return the Popular Search terms count
	 * 
	 * @return
	 */
	public int get_popular_search_terms_count() throws Exception {
		// switch_to_iframe("iframe",
		// count_iframe_get_desired_iframe_count("iframe", "class",
		// "guidedSearchFrame"));
		switch_to_iframe_by_webelement(popular_search_iframe, 0);
		int count = get_webelements_list(popular_searches_terms).size();
		switch_iframe_default_content();
		return count;
	}

	/**
	 * Click the First Popular Search from the Home page
	 * 
	 * @throws Exception
	 * 
	 */
	public void click_first_popular_search_word() throws Exception {
		// scrollToTopOfPage();
		switch_to_iframe_by_webelement(popular_search_iframe, 0);
		button(get_webelements_list(popular_searches_terms).get(0), 60);
		switch_iframe_default_content();
	}

	public void click_on_topstories() {
		button(first_article_link_from_top_stories, 2);
	}

	public boolean verify_top_stories_visibility() {
		return elementVisibility(top_stories_section);
	}

	public boolean verify_page_after_content_down() {
		if (elementVisibility(header_part) && elementVisibility(header_menu) && elementVisibility(footer_part)
				&& elementVisibility(progress_bar)) {
			return true;
		} else
			return false;
	}

	public String verify_fpcontent_error_msg() {
		return getText(error_message_fp_content_down, 45);
	}

	/**
	 * Close the Openx banner
	 */
	public void close_openx_banner() {
		button(openx_image_hide_button, 25);
	}

	/**
	 * Return the size of Category Page GPT Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_category_page_gpt_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(category_page_gpt_ad_size, "width");
		size[1] = getAttribute(category_page_gpt_ad_size, "height");
		return size;
	}

	/**
	 * Verify the display of Category Page GPT Ad 2
	 * 
	 * @return
	 */
	public boolean verify_category_page_gpt_ad() {
		sleepFor(2);
		return elementVisibility(category_page_gpt_ad);
	}

	/**
	 * Returns the Google unique id for the Category Page Ad
	 * 
	 * @return
	 */
	public String get_category_page_gpt_ad_google_query_id() {
		return getAttribute(category_page_gpt_ad, "data-google-query-id");
	}
}
