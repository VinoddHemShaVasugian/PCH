package com.pageobjects;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;
import com.util.DriverManager;

public class HomePage extends BaseClass {

	private static final HomePage instance = new HomePage();
	private static final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private final SERPage serp_instance = SERPage.getInstance();

	private HomePage() {
	}

	public static HomePage getInstance() {
		return instance;
	}

	public final String PROPERTIES[] = new String[] { "PCH.com", "PCHsearch & win", "PCHlotto", "PCHKeno", "PCHslots",
			"PCHblackjack" };
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final By fancy_welcome_msg = By.cssSelector("a.fancybox-item.fancybox-close");
	private final By my_account = By.linkText("My Account");
	private final By search_box = By.id("searchField0");
	private final By collapse_search_box = By.id("searchField2");
	private final By latest_activity_message = By.cssSelector("p.uninav__msg__desc");
	private final By latest_activity_amount = By.cssSelector("span.uninav__msg__tokens-text");
	private final By latest_entry_activity_message = By.cssSelector("div.uninav__msg__message-content");
	private final By Register = By.linkText("Register");
	private final By sign_in = By.linkText("Sign In");
	private final By complete_registration = By.xpath("//a[text()='Complete Registration']");
	private final By searchBtn = By.cssSelector("div.search__bar-wrapper__submit");
	private final By token_value = By.cssSelector("p.uninav__token-center-alltime__tokens-amount");
	private final By token_history_link = By.linkText("Token History");
	private final By our_pick_lottery_link = By.xpath("//div[contains(.,'Lottery')]/following-sibling::img");
	private final By our_pick_horoscope_link = By.xpath("//div[contains(.,'Horoscopes')]/following-sibling::img");
	private final By our_pick_weather_link = By.xpath("//div[contains(.,'Weather')]/parent::a");
	private final By openx_image_hide_button = By
			.xpath("//div[@class='openXImageHolder']/span[@class='plug-wrapper__hide']");
	private final By sams_image = By.xpath("//div[@class='center-block plug-wrapper']/img[contains(@src,'https://samsassets')]");
	private final By search_bar_above_openx_ad = By
			.xpath("//div[@class='openXImageHolder']/parent::div/preceding-sibling::div[@id='search']");
	private final By videos_menu = By.xpath("//div[@id='header']//a[contains(@href ,'video')]");
	private final By horoscope_menu = By.xpath("//*[@id='header']//a[text()='Horoscope ']");
	private final By lastactivity_expandbtn = By.cssSelector("span.action-button");
	private final By lastactivity_trnsName = By.cssSelector("p.source");
	private final By sub_catagory_menu_urls = By.xpath("//div[@id='header']//ul[starts-with(@class,'sub-menu')]//a");
	private final By sub_catagory_menu_page_name = By
			.xpath("//div[@id='header-menu']//ul[starts-with(@class,'sub-menu')]//a/span");
	private final By first_article_link_from_top_stories = By
			.xpath("//li[@class='top-stories__li']/a[contains(@href,'/article')][1]");
	private final By first_video_link_from_top_stories = By
			.xpath("//li[@class='top-stories__li']/a[contains(@href,'/video')][1]");
	private final By iwe_cash_winner_message = By.cssSelector("div.messageIWE>div:nth-of-type(1)");
	private final By startVideoText = By.id("video-player-title");
	private final By firstvideo_List = By
			.cssSelector("li.fp-video-item.fp-video-item--selected div.fp-video-item__title");
	private final By bottom_overlay_message = By.cssSelector("span.fp-video-token-bar__text");
	private final By default_token_icon = By.cssSelector("img.fp-video-token-button__coin");
	private final By token_claim_message = By
			.cssSelector("div.fp-video-token-bar__tokens.fp-video-token-button.fp-video-token-button--just-claimed");
	private final By token_already_claim_message = By.cssSelector(
			"div.fp-video-token-bar__tokens.fp-video-token-button.fp-video-token-button--just-claimed.fp-video-token-button--already-claimed");
	private final By Home = By.linkText("Home");
	private final By sign_out = By.linkText("Sign Out");
	private final By main_catagory_menu_urls = By
			.xpath("//div[@id='header-menu']//ul[starts-with(@class,'sub-menu')]/preceding-sibling::a");
	private final By first_ourpick_story_type = By
			.xpath("(//div[@class='col-md-3 col-xs-3 custom-col-spacing_spacer-first our-picks__col'])[1]//i");
	private final By top_stories_list = By.cssSelector("li.top-stories__li");
	private final By sticky_header_menu = By.cssSelector("div.search-sticky.animated.fadeInDown");
	private final By back_to_top = By.id("back-to-top");
	private final By hover_search_box = By.cssSelector("div.search-sticky.animated.fadeInDown input#searchField1");
	private final By hover_search_button = By
			.cssSelector("div.search-sticky.animated.fadeInDown div.search__bar-wrapper__submit");
	private final By daily_bonus_game_bar_check_list = By
			.cssSelector("li.progress-bar-step.progress-bar-step--checked");
	private final By daily_bonus_game_locked_icon = By.cssSelector("img.progress-bar-sticky__lock");
	private final By daily_bonus_game_play_icon = By.cssSelector("img.progress-bar-sticky__play-now");
	private final By daily_bonus_game_info_icon = By.cssSelector("i.progress-bar-sticky__info>img");
	private final By daily_bonus_game_info_window = By.cssSelector("div.progress-bar-sticky-menu.animated.fadeInUp");
	private final By daily_bonus_game_info_window_close = By.cssSelector("div.progress-bar-sticky-menu__close");
	private final By lottery_menu = By.xpath("//*[@id='header']//a[text()='Lottery ']");
	private final By news_menu = By.xpath("//*[@id='header']//a[text()='News ']");
	private final By health_sub_menu = By.xpath("//a[span='Health/Medical']");
	private final By listing_content = By.xpath("//a[@class='listing__content__title']");
	private final By weather_menu = By.xpath("//*[@id='header']//a//img[contains(@src,'weather')]");
	private final By header_Menu = By.xpath("//*[@id='header-menu']//a");
	private final By uni_nav_list = By.cssSelector("li.uninav__carousel__item>a");
	private final By uni_nav_extra_link_list = By.cssSelector("a.uninav__link-group__link");
	private final By uninav_next_arrow = By.cssSelector("span.uninav__carousel__nxt");
	private final By uninav_next_arrow_disabled_status = By
			.cssSelector("span.uninav__carousel__nxt.uninav__carousel__nxt--disabled");
	private final By uninav_previous_arrow = By.cssSelector("span.uninav__carousel__prv");
	private final By uninav_previous_arrow_disabled_status = By
			.cssSelector("span.uninav__carousel__prv.uninav__carousel__prv--disabled");
	private final By green_unclaim_token = By.cssSelector("button.buttons.buttons_claim.buttons_green.unclaimed");
	private final By grey_claim_token = By.cssSelector("button.buttons.buttons_claim.buttons_grey.claimed");
	private final By unclaim_token_amount = By.cssSelector("span.buttons__token-amount");
	private final By popular_searches_header_bar = By
			.cssSelector("div#header h3.section-header.popular-searches__header");
	private final By popular_searches_terms = By.cssSelector("body.desktop.landscape div.two-row-scroller ul li a");
	private final By sidebar_popular_searches_header_bar = By
			.cssSelector("div#search-aside h3.section-header.popular-searches__header");
	private final By sidebar_popular_searches_terms = By.cssSelector("div.two-row-scroller ul li a");
	private final By popular_search_iframe = By.cssSelector("iframe.guidedSearchFrame");
	private final By inline_gpt_ad = By.cssSelector("div#div-gpt-ad-bottom>div>iframe");
	private final By right_rail_gpt_ad_1 = By.cssSelector("div#div-gpt-ad-multiple> div>iframe");
	private final By right_rail_gpt_ad_2 = By.cssSelector("div#div-gpt-ad-box> div>iframe");
	private final By inline_gpt_tile_ad = By.cssSelector("div#div-gpt-ad-tile>div>iframe");
	private final By bottom_native_ad = By.cssSelector("div#gpt-ad-bottom-native");
	private final By bottom_native_ad_size = By.cssSelector("div#gpt-ad-bottom-native>div>iframe");
	private final By top_stories_native_ad = By.cssSelector("div#div-nativ-ad-home>div>iframe");
	private final By trending_now_native_ad = By.cssSelector("div#div-nativ-ad-trendingnow");
	private final By sponsored_native_ad = By.cssSelector("div#div-native-ad-sponsored>div>iframe");
	private final By sub_category_native_ad = By.cssSelector("div#div-nativ-ad-subcategory>div>iframe");
	private final By our_pick_native_ad = By.cssSelector("div#div-nativ-ad-ourpicks>div>iframe");
	private final By fpcontent_down_error_msg = By.cssSelector("p.txt1");
	private final By fpcontent_cache_content = By.cssSelector("body>pre.sf-dump");
	private final By openX_banner_above_popularSearch = By.xpath(
			"//div[@class='container popular-searches']/preceding-sibling::div[1]//span[@id='plug-wrapper__hide']");
	private final By popular_search_above_search_bar = By
			.xpath("//div[@class='container popular-searches']/following-sibling::div//span[@id='plug-wrapper__hide']");
	private final By old_vip_badge_logo = By.cssSelector("span.viplogo");
	private final By new_vip_badge_logo = By.cssSelector("span.vip-badge__name");
	private final By vip_badge = By.cssSelector("div.uninav__logoholder");
	private final By click_token = By
			.xpath("//p[@class='uninav__token-center-alltime__tokens-amount uninav__token-balance__amount']");
	private final By evergage_popup = By.cssSelector("div.evergage-tooltip>a>img.eg-image");
	private final By vip_msg = By.cssSelector("div.vip-message__content");
	private final By evergage_top = By.cssSelector("div.evergage-tooltip-bar");
	

	/**
	 * Click on Claim button
	 * 
	 * @throws Exception
	 */
	public boolean click_claim_button() throws Exception {
		button(green_unclaim_token, 5);
		sleepFor(1);
		lb_instance.close_level_up_lb();
		return elementNotPresent(green_unclaim_token);
	}
	
	/**
	 * Verify VIP message on home
	 * 
	 * @throws Exception
	 */
	public String get_vip_msg() {
		return getText(vip_msg, 45);
	}
	
	/**
	 * Close on Evergage popup
	 * 
	 * @throws Exception
	 */
	public void close_evergage_popup() {
//		if(verify_evergage_popup()){
//			button(evergage_popup, 10);
//		}
	}
	
	/**
	 * Verify evergage popup
	 * 
	 * @throws Exception
	 */
	public boolean verify_evergage_popup() {
		sleepFor(5);
		return elementPresent(evergage_popup);
	}

	/**
	 * 
	 * @return the FP Content Cache content
	 */
	public boolean verify_fpcontent_cache_content() {
		return elementVisibility(fpcontent_cache_content);
	}

	/**
	 * 
	 * @return the Error message when the FP Content API is misconfigured/wrong
	 */
	public String verify_fpcontent_error_msg() {
		return getText(fpcontent_down_error_msg, 45);
	}

	/**
	 * Verify the display of Our Pick Native Ad
	 * 
	 * @return
	 */
	public boolean verify_our_pick_native_ad() {
		return elementVisibility(our_pick_native_ad);
	}

	/**
	 * Return the size of Our Pick Native Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_our_pick_native_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(our_pick_native_ad, "width");
		size[1] = getAttribute(our_pick_native_ad, "height");
		return size;
	}

	/**
	 * Verify the display of Sub Category Native Ad
	 * 
	 * @return
	 */
	public boolean verify_sub_category_native_ad() {
		return elementVisibility(sub_category_native_ad);
	}

	/**
	 * Return the size of Sub Category Native Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_sub_category_native_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(sub_category_native_ad, "width");
		size[1] = getAttribute(sub_category_native_ad, "height");
		return size;
	}

	/**
	 * Verify the display of Sponsored Native Ad
	 * 
	 * @return
	 */
	public boolean verify_sponsored_native_ad() {
		return elementVisibility(sponsored_native_ad);
	}

	/**
	 * Return the size of Sponsored Native Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_sponsored_native_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(sponsored_native_ad, "width");
		size[1] = getAttribute(sponsored_native_ad, "height");
		return size;
	}

	/**
	 * Verify the display of Trending Now Native Ad
	 * 
	 * @return
	 */
	public boolean verify_trending_now_native_ad() {
		return elementVisibility(trending_now_native_ad);
	}

	/**
	 * Return the size of Trending Now Native Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_trending_now_native_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(trending_now_native_ad, "width");
		size[1] = getAttribute(trending_now_native_ad, "height");
		return size;
	}

	/**
	 * Verify the display of Top Story Native Ad
	 * 
	 * @return
	 */
	public boolean verify_top_stories_native_ad() {
		return elementVisibility(top_stories_native_ad);
	}

	/**
	 * Return the size of Top Story Native Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_top_stories_native_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(top_stories_native_ad, "width");
		size[1] = getAttribute(top_stories_native_ad, "height");
		return size;
	}

	/**
	 * Verify the display of Bottom Native Ad
	 * 
	 * @return
	 */
	public boolean verify_bottom_native_ad() {
		return elementVisibility(bottom_native_ad);
	}

	/**
	 * Return the size of Bottom Native Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_bottom_native_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(bottom_native_ad_size, "width");
		size[1] = getAttribute(bottom_native_ad_size, "height");
		return size;
	}

	/**
	 * Verify the display of Inline GPT Tile Ad
	 * 
	 * @return
	 */
	public boolean verify_inline_gpt_tile_ad() {
		return elementVisibility(inline_gpt_tile_ad);
	}

	/**
	 * Return the size of Inline GPT Tile Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_inline_gpt_tile_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(inline_gpt_tile_ad, "width");
		size[1] = getAttribute(inline_gpt_tile_ad, "height");
		return size;
	}

	/**
	 * Verify the display of Inline GPT Ad
	 * 
	 * @return
	 */
	public boolean verify_inline_gpt_ad() {
		return elementVisibility(inline_gpt_ad);
	}

	/**
	 * Return the size of Inline GPT Ad
	 * 
	 * @return
	 */
	public String[] get_size_of_inline_gpt_ad() {
		String size[] = new String[2];
		size[0] = getAttribute(inline_gpt_ad, "width");
		size[1] = getAttribute(inline_gpt_ad, "height");
		return size;
	}

	/**
	 * Verify the display of Right Rail GPT Ad 1
	 * 
	 * @return
	 */
	public boolean verify_right_rail_gpt_ad_1() {
		return elementVisibility(right_rail_gpt_ad_1);
	}

	/**
	 * Return the size of Right Rail GPT Ad 1
	 * 
	 * @return
	 */
	public String[] get_size_of_right_rail_gpt_ad_1() {
		String size[] = new String[2];
		size[0] = getAttribute(right_rail_gpt_ad_1, "width");
		size[1] = getAttribute(right_rail_gpt_ad_1, "height");
		return size;
	}

	/**
	 * Verify the display of Right Rail GPT Ad 2
	 * 
	 * @return
	 */
	public boolean verify_right_rail_gpt_ad_2() {
		sleepFor(2);
		return elementVisibility(right_rail_gpt_ad_2);
	}

	/**
	 * Return the size of Right Rail GPT Ad 2
	 * 
	 * @return
	 */
	public String[] get_size_of_right_rail_gpt_ad_2() {
		String size[] = new String[2];
		size[0] = getAttribute(right_rail_gpt_ad_2, "width");
		size[1] = getAttribute(right_rail_gpt_ad_2, "height");
		return size;
	}

	/**
	 * Get the Claim Token value
	 * 
	 * @return
	 */
	public int get_unclaim_token_value() {
		return Integer.parseInt(getText(unclaim_token_amount, 15));
	}

	/**
	 * Verify the UnClaimed Token button
	 * 
	 * @return
	 */
	public boolean verify_unclaimed_button() {
		return elementVisibility(green_unclaim_token);
	}

	/**
	 * Verify the Claimed Token button
	 * 
	 * @return
	 */
	public boolean verify_claimed_button() {
		return elementVisibility(grey_claim_token);
	}

	/**
	 * To click on Sign out link and sign out from the site
	 */
	public void click_SignOut() {
		button(sign_out, 10);
	}

	/**
	 * To click on My Account link
	 */
	public void click_MyAccount() {
		button(my_account, 10);
	}

	/**
	 * To click on My Account link
	 */
	public void click_tokens_earned() {
		button(click_token, 10);
	}

	/**
	 * To verify the presence of Sign in Link
	 */
	public boolean verify_sign_out() {
		waitForElement(sign_out, 15);
		return elementPresent(sign_out);
	}

	/**
	 * To verify the presence of Sign in Link
	 */
	public boolean verify_UnRecHome() {
		waitForElement(sign_in, 15);
		return elementPresent(sign_in);
	}

	public void click_horoscopemenu() {
		button(horoscope_menu, 10);
	}

	public void click_healthmenu() {
		mouseHover(news_menu);
		button(health_sub_menu, 10);
	}

	public String get_source_htm() {
		return get_page_source();
	}

	public LinkedList<String> get_content_list() {
		List<WebElement> ele_list = get_webelements_list(listing_content);
		LinkedList<String> content_texts = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			content_texts.add(ele.getText());
		}
		return content_texts;
	}

	/**
	 * Get the IWE cash winner message from the light box
	 * 
	 * @throws Exception
	 */
	public String get_iwe_cash_winner_msg() {
		return getText(iwe_cash_winner_message, 10);
	}

	/**
	 * Click the First Article link of the Top Stories section of the Home page
	 * 
	 */
	public void click_first_article_link() {
		button(get_webelements_list(first_article_link_from_top_stories).get(0), 20);
	}

	/**
	 * Click the First Video link of the Top Stories section of the Home page
	 * 
	 */
	public void click_first_video_link() {
		button(get_webelements_list(first_video_link_from_top_stories).get(0), 30);
	}

	/**
	 * To click on Home Menu
	 */
	public void click_Home() {
		button(Home, 10);
	}

	/**
	 * To verify the already token claimed message below the video player
	 */
	public boolean verify_token_already_claim_status(String belowmessage) {
		waitForElementToBePresent(token_already_claim_message, 250);
		return getText(bottom_overlay_message, 10).equalsIgnoreCase(belowmessage)
				&& elementPresent(token_already_claim_message);
	}

	/**
	 * To verify the token claimed message below the video player
	 */
	public boolean verify_token_claim_status(String belowmessage) {
		waitForElementToBePresent(token_claim_message, 90);
		return getText(bottom_overlay_message, 10).equalsIgnoreCase(belowmessage)
				&& elementPresent(token_claim_message);
	}

	/**
	 * To verify the default token icon & message of the video player
	 */
	public boolean verify_default_token_icon_msg(String belowmessage) {
		return getText(bottom_overlay_message, 10).equalsIgnoreCase(belowmessage) && elementPresent(default_token_icon);
	}

	/**
	 * To get the text of First Video on List
	 */
	public String getfirstvideo_List() {
		return getText(firstvideo_List, 10);
	}

	/**
	 * To get the start video text
	 */
	public String getStartVideoText() {
		return getText(startVideoText, 10);
	}

	/**
	 * To verify the presence of Start Video Text
	 */
	public boolean verify_startVideoText() {
		return elementPresent(startVideoText);
	}

	/**
	 * Return the Sub_catagory pages Url's. It includes the More menu also.
	 * 
	 * @return
	 */
	public LinkedList<String> get_sub_catagory_menu_url_list() {
		List<WebElement> ele_list = get_webelements_list(sub_catagory_menu_urls);
		LinkedList<String> sub_catagory_urls = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			sub_catagory_urls.add(ele.getAttribute("href"));
		}
		return sub_catagory_urls;
	}

	/**
	 * Return the Sub_catagory pages name in list. It includes the More menu also.
	 * 
	 * @return
	 */
	public LinkedList<String> get_sub_catagory_menu_list() {
		List<WebElement> ele_list = get_webelements_list(sub_catagory_menu_page_name);
		LinkedList<String> sub_catagory_manu_name_list = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			sub_catagory_manu_name_list.add(ele.getAttribute("innerHTML").toLowerCase());
		}
		return sub_catagory_manu_name_list;
	}

	/**
	 * To verify the presence of searchBox
	 */
	public boolean verify_searchBox() {
		return elementPresent(search_box);
	}

	/**
	 * To verify the presence of collapse searchBox
	 */
	public boolean verify_collapse_searchBox() {
		return elementPresent(collapse_search_box);
	}

	/**
	 * To verify the Transaction name in Last Activity section
	 */
	public boolean verify_LastTransactionInLastActiviy(String lasttransactiontext) {
		return getText(lastactivity_trnsName, 10).contains(lasttransactiontext);
	}

	/**
	 * To expand Last activity module
	 */
	public void click_lastactivity_expandclosebtn() {
		button(lastactivity_expandbtn, 5);
	}

	/**
	 * Close the Openx banner
	 */
	public void close_openx_banner() {
		click_evergage_top_banner();
		close_evergage_popup();
//		button(openx_image_hide_button, 25);
//		waitForElementVisibility(collapse_search_box, 30);
	}

	/**
	 * To click on videos menu
	 */
	public void click_vidoes_menu() {
		button(videos_menu, 30);
	}

	public void click_horoscope_menu() {
		button(horoscope_menu, 30);
	}

	public void click_lottery_menu() {
		button(lottery_menu, 30);
	}

	public void click_weather_menu() {
		button(weather_menu, 30);
	}

	/**
	 * To click on lottery menu
	 */
	public void click_lotterymenu() {
		button(lottery_menu, 20);
	}

	/**
	 * @param SearchString
	 */
	public void search(String searchvalue) {
		textbox(search_box, "enter", searchvalue, 5);
		button(searchBtn, 10);
	}

	/**
	 * @return Latest activity message
	 */
	public String get_latest_activity_message() {
		click_tokens_earned();
		return getAttribute(latest_activity_message, "innerHTML");
	}

	/**
	 * @return Latest activity Token amount
	 */
	public String get_latest_activity_token_amount() {
		click_tokens_earned();
		return getAttribute(latest_activity_amount, "innerHTML").replace(",", "").split(" ")[0];
	}

	/**
	 * @return Latest entry activity Message
	 */
	public String get_latest_entry_activity_message() {
		click_tokens_earned();
		return getAttribute(latest_entry_activity_message, "innerHTML").replace(",", "");
	}

	/**
	 * Search for the given term
	 * 
	 * @param search_term
	 */
	public void search_term(String search_term) {
		textbox(search_box, "enter", search_term, 2);
		submit(search_box, 2);
	}

	/**
	 * Search for the given term on collapse search box
	 * 
	 * @param search_term
	 */
	public void search_term_on_collapse(String search_term) {
//		textbox(collapse_search_box, "enter", search_term, 2);
//		button(searchBtn, 2);
		search_term(search_term);
	}

	/**
	 * To click on Sign In link on home page for unrecognized user.
	 */
	public void click_SignIn() {
		button(sign_in, 10);
	}

	/**
	 * To click on Register link on home page for unrecognized user.
	 */
	public void click_Register() {
		button(Register, 10);
	}

	/**
	 * Verify the Register link on home page for unrecognized user.
	 */
	public boolean verify_register() {
		return elementVisibility(Register);
	}

	/**
	 * Verify the Register link on home page for unrecognized user.
	 */
	public boolean verify_signin() {
		return elementVisibility(sign_in);
	}

	/**
	 * To verify the my account link and also can be used to successful login and
	 * registration
	 */
	public boolean verify_complete_registration() {
		return elementVisibility(complete_registration);
	}

	/**
	 * To verify the my account link and also can be used to successful login and
	 * registration
	 */
	public void click_complete_registration() {
		if (elementPresent(fancy_welcome_msg)) {
			button(fancy_welcome_msg, 1);
		}
		button(complete_registration, 1);
	}

	/**
	 * To verify the my account link and also can be used to successful login and
	 * registration
	 */
	public boolean verify_Home() {
		sleepFor(2);
		return elementPresent(my_account);
	}

	/**
	 * @return TokenValue
	 */
	public int get_Tokens() throws Exception {
		sleepFor(10);
		waitForElementPresent(token_value, 40);
		String Tokenvalue = getText(token_value, 5);
		if (Tokenvalue.contentEquals("Loading...")) {
			DriverManager.getDriver().navigate().refresh();
		}
		log.info("Token Value is: " + Tokenvalue);
		Tokenvalue = Tokenvalue.replace(",", "");
		int Tokens = Integer.parseInt(Tokenvalue);
		return Tokens;
	}

	/**
	 * To click on Token History link from Activity section
	 */
	public void click_token_history() {
		button(token_history_link, 5);
	}

	/**
	 * Click the Horoscope link from the Our Picks section
	 */
	public void click_horoscope_our_pick() {
		button(our_pick_horoscope_link, 5);
	}

	/**
	 * Click the Lottery link from the Our Picks section
	 */
	public void click_lottery_our_pick() {
		button(our_pick_lottery_link, 5);
	}

	/**
	 * Verify the Lottery link from the Our Picks section
	 */
	public boolean verify_lottery_our_pick() {
		return elementVisibility(our_pick_lottery_link);
	}

	/**
	 * Click the Weather link from the Our Picks section
	 */
	public void click_weather_our_pick() {
		button(our_pick_weather_link, 15);
	}

	/***
	 * Verify the display of OpenX banner
	 */
	public boolean verify_openx_banner() {
//		return elementPresent(openx_image_hide_button);
		return elementPresent(sams_image);
	}

	/***
	 * Verify the display of Search box above the OpenX banner
	 */
	public boolean verify_search_bar_above_openx_banner() {
		return elementPresent(search_bar_above_openx_ad);
	}

	/**
	 * Close IWE cash win light box
	 */
	public void close_iwe_cash_win_lightbox() {
		waitForElement(iwe_cash_winner_message);
		button(fancy_welcome_msg, 15);
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
			if (!url.contains("#")) {
				main_catagory_urls.add(url);
			}
		}
		return main_catagory_urls;
	}

	/**
	 * Return the Main category name. It includes the More menu also.
	 * 
	 * @return
	 */
	public LinkedList<String> get_main_catagory_menu_name() {
		List<WebElement> ele_list = get_webelements_list(main_catagory_menu_urls);
		LinkedList<String> main_catagory_urls = new LinkedList<String>();
		for (WebElement ele : ele_list) {
			String url = ele.getAttribute("href");
			if (!url.contains("#")) {
				main_catagory_urls.add(url.substring(url.lastIndexOf("/") + 1, url.length()).toLowerCase().trim());
			}
		}
		return main_catagory_urls;
	}

	/**
	 * Verify the visibility of the Play icon on the first OurPick tile to validate
	 * it as a Video story type
	 */
	public boolean verify_fist_ourpick_type_as_video() {
		return elementVisibility(first_ourpick_story_type);
	}

	/**
	 * Return the count of the Top Stories in the Category page
	 * 
	 * @return
	 */
	public int get_top_stories_count() {
		return get_webelements_list(top_stories_list).size();
	}

	/**
	 * Verify the Sticky header menu
	 * 
	 * @return
	 */
	public boolean verify_sticky_header_menu() {
		return elementVisibility(sticky_header_menu);
	}

	/**
	 * Verify the presence of Back to Top button
	 * 
	 * @return
	 */
	public boolean verify_back_to_top_button() {
		return elementVisibility(back_to_top);
	}

	/**
	 * To search the string from Hover header menu
	 * 
	 * @param SearchString
	 */
	public void hover_search(String searchvalue) {
		textbox(hover_search_box, "enter", searchvalue, 5);
		button(hover_search_button, 10);
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
	 * Get the Header Menu list names
	 * 
	 * @return
	 */
	public List<WebElement> getHeaderMenuList() {
		return get_webelements_list(header_Menu);
	}

	/**
	 * Verify the Uni Nav Next arrow disabled status
	 * 
	 * @return
	 */
	public boolean verify_uninav_next_arrow_disabled_status() {
		return elementVisibility(uninav_next_arrow_disabled_status);
	}

	/**
	 * Verify the Uni Nav Next arrow enabled status
	 * 
	 * @return
	 */
	public boolean verify_uninav_next_arrow_enabled_status() {
		return elementVisibility(uninav_next_arrow);
	}

	/**
	 * Verify the Uni Nav Previous arrow disabled status
	 * 
	 * @return
	 */
	public boolean verify_uninav_previous_arrow_disabled_status() {
		return elementVisibility(uninav_previous_arrow_disabled_status);
	}

	/**
	 * Verify the Uni Nav Previous arrow enabled status
	 * 
	 * @return
	 */
	public boolean verify_uninav_previous_arrow_enabled_status() {
		return elementVisibility(uninav_previous_arrow);
	}

	/**
	 * Returns the PCH Properties list names
	 */
	public LinkedList<String> get_pch_property_list_names() {
		LinkedList<String> prop_name = new LinkedList<String>();
		int count = 5;
		while (!verify_uninav_next_arrow_disabled_status()) {
			for (WebElement ele : get_webelements_list(uni_nav_list)) {
				System.out.println(ele.getText());
				if (!ele.getText().equals("") && !ele.getText().isEmpty() && !prop_name.contains(ele.getText())) {
					prop_name.add(ele.getText());
				}
			}
			button(uninav_next_arrow, 5);
			count = count - 1;
			if (count == 0) {
				break;
			}
		}
		for (WebElement ele : get_webelements_list(uni_nav_list)) {
			if (!ele.getText().equals("") && !ele.getText().isEmpty() && !prop_name.contains(ele.getText())) {
				prop_name.add(ele.getText());
			}
		}
		return prop_name;
	}

	/**
	 * Returns the PCH Extra link name list
	 */
	public LinkedList<String> get_pch_extra_link_list_names() {
		LinkedList<String> extra_link_name = new LinkedList<String>();
		for (WebElement ele : get_webelements_list(uni_nav_extra_link_list)) {
			if (!ele.getText().equals("") && !ele.getText().isEmpty() && !extra_link_name.contains(ele.getText())) {
				extra_link_name.add(ele.getText());
			}
		}
		return extra_link_name;
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
		scrollToTopOfPage();
		switch_to_iframe_by_webelement(popular_search_iframe, 0);
		button(get_webelements_list(popular_searches_terms).get(0), 60);
		switch_iframe_default_content();
	}

	/**
	 * Verify the display of SideBar Popular Search Header bar
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verify_sidebar_popular_searches_header_bar() throws Exception {
		return elementVisibility(sidebar_popular_searches_header_bar);
	}

	/**
	 * Returns the text of SideBar Popular Search Header bar
	 * 
	 * @return
	 * @throws Exception
	 */
	public String get_sidebar_popular_search_header_bar_text() throws Exception {
		return getText(sidebar_popular_searches_header_bar, 30);
	}

	/**
	 * Verify the display of SideBar Popular Search terms
	 * 
	 * @return
	 */
	public boolean verify_sidebar_popular_search_terms() throws Exception {
		boolean status = true;
		try {
			switch_to_iframe_by_webelement(popular_search_iframe, 0);
			status = elementVisibility(sidebar_popular_searches_terms);
			switch_iframe_default_content();
		} catch (Exception e) {
			log.error("Sidebar Popular search iframe is not found.");
			status = false;
		}
		return status;
	}

	/**
	 * Return the SideBar Popular Search terms count
	 * 
	 * @return
	 */
	public int get_sidebar_popular_search_terms_count() throws Exception {
		switch_to_iframe_by_webelement(popular_search_iframe, 0);
		int count = get_webelements_list(sidebar_popular_searches_terms).size();
		switch_iframe_default_content();
		return count;
	}

	/**
	 * Click the SideBar First Popular Search from the Home page
	 * 
	 * @throws Exception
	 * 
	 */
	public void click_sidebar_first_popular_search_word() throws Exception {
		scrollToTopOfPage();
		switch_to_iframe_by_webelement(popular_search_iframe, 0);
		button(get_webelements_list(sidebar_popular_searches_terms).get(0), 60);
		switch_iframe_default_content();

	}

	/**
	 * Return the Popular Search terms above openx
	 * 
	 * @return
	 */
	public boolean verify_openx_above_popoular_search() {
		return elementPresent(openX_banner_above_popularSearch);
	}

	/**
	 * Return the Popular Search terms above openx
	 * 
	 * @return
	 */
	public boolean verify_popular_search_below_search_bar() {
		return elementPresent(popular_search_above_search_bar);
	}

	/**
	 * Verify the Old VIP logo on UniNav
	 * 
	 * @return
	 */
	public boolean verify_old_vip_logo() {
		return elementVisibility(old_vip_badge_logo);
	}

	
	/**
	 * Verify the New VIP badge on UniNav
	 * 
	 * @return
	 */
	public boolean verify_new_vip_badge(int time_out) {
		return elementVisibility(new_vip_badge_logo, time_out);
	}

	/**
	 * Click old VIP log
	 * 
	 */
	public void clic_old_VIP_logo(int time_out) {
		button(old_vip_badge_logo, time_out);
	}
	
	/**
	 * close evergage banner, displayed on page header
	 */
	public void click_evergage_top_banner() {
		button(evergage_top, 2);
	}
	
	/**
	 * Click new VIP log
	 * 
	 * @return True
	 * @throws Exception 
	 */
	public void clic_new_VIP_logo(int time_out) throws Exception {
		click_evergage_top_banner();
		doRefresh();
		button(new_vip_badge_logo, time_out);
	}
	
	public boolean verify_VIP_msg_before_and_after_search() {
		try {
		clic_new_VIP_logo(5);
		String beforeSearch= get_vip_msg();
		System.out.println("VIP message before search: "+beforeSearch);
		search_term(generateRandomString(5));
		switchToNewTab();
		clic_new_VIP_logo(5);
		String afterSearch= get_vip_msg();
		System.out.println("VIP message after search: "+afterSearch);
		serp_instance.verify_SERP_Completely();
		assertNotEqualsIgnoreCase(beforeSearch, afterSearch);
		return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	public boolean verify_vip_message_for_new_users(String firstName) throws Exception {
		try {
		String vip_c1_msg = "Hi, "+firstName+""+msg_property_file_reader("vip_c1_msg_header")+"\n"+msg_property_file_reader("vip_c1_msg_body");
		System.out.println("vip_c1_msg: "+vip_c1_msg);
		clic_new_VIP_logo(5);
		sleepFor(2);
		String beforeActivity= get_vip_msg();
		assertEquals(beforeActivity, vip_c1_msg);
	return true;
		}
		catch(Exception e) {
			return false;
		}
	}
}
