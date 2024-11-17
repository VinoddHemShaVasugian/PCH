package com.pageobjects;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;
import com.util.DriverManager;

public class EDLHomePage extends BaseClass {

	private static final EDLHomePage edl_home_instance = new EDLHomePage();
	private final LightBoxPage lb_instance = LightBoxPage.getInstance();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private EDLHomePage() {
	}

	public static EDLHomePage getInstance() {
		return edl_home_instance;
	}

	private final By token_history_link = By.linkText("Token History");
	private final By token_value_on_uninav = By.cssSelector("span.uninav__reward-center__token-amount");
	private final By register = By.linkText("Register");
	private final By my_account = By.linkText("My Account");
	private final By sign_in = By.linkText("Sign In");
	private final By comics_header_menu = By.xpath("//div[@id='header']//a[text()='Comics ']");
	private final By lifehacks_header_menu = By.xpath("//div[@id='header']//a[text()='LifeHacks ']");
	private final By pets_header_menu = By.xpath("//div[@id='header']//a[text()='Pets ']");
	private final By trending_header_menu = By.xpath("//div[@id='header']//a[text()='Trending ']");
	private final By todays_pick_section = By.id("edl-home-carousel");
	private final By horoscope_menu = By.xpath("//div[@id='header']//a[text()='Horoscope ']"); 
	//in QA the locator should be - //div[@id='header']//a[text()='Horoscopes ']
	private final By health_menu = By.xpath("//div[@id='header']//a[text()='Health ']");
	private final By date_in_title = By.xpath("//div[contains(@class,'section-header-date')]");
	private final By header_menu_items = By.xpath("//ul[@class='menu menu--header']/li");
	private final By footer_menu_items = By.xpath("//ul[@class='menu menu--footer']/li");
	private final By edl_title = By
			.xpath("//div[@class='row section-header-row'][1]//a[@class='section-header__link']");
	private final By edl_home_second_title = By
			.xpath("//div[@class='row section-header-row'][2]//a[@class='section-header__link']");
	private final By sweep_stakes = By.cssSelector("div#right div.sweeps");
	private final By sweep_stakes_home = By.cssSelector("div.sweeps");
	private final By edl_search = By.xpath("//div[@id='right']//form[@name='searchForm']");
	private final By trending_now = By.xpath("//div[@id='right']/h3[1]");
	private final By trending_now_video_link = By
			.xpath("//div[@class='bottom-spacer']//div[@class='row listing'][1]/div[2]/a");
	private final By trending_now_videos = By.xpath("//div[@class='bottom-spacer']//div[@class='row listing']");
	private final By green_unclaim_token = By.cssSelector("button.buttons.buttons_claim.buttons_green.unclaimed");
	private final By grey_claim_token = By.cssSelector("button.buttons.buttons_claim.buttons_grey.claimed");
	private final By unclaim_token_amount = By.cssSelector("span.buttons__token-amount");
	private final By daily_bonus_game_bar_check_list = By
			.cssSelector("li.progress-bar-step.progress-bar-step--checked");
	private final By daily_bonus_game_locked_icon = By.cssSelector("img.progress-bar-sticky__lock");
	private final By daily_bonus_game_play_icon = By.cssSelector("img.progress-bar-sticky__play-now");
	private final By daily_bonus_game_info_icon = By.cssSelector("i.progress-bar-sticky__info>img");
	private final By daily_bonus_game_info_window = By.cssSelector("div.progress-bar-sticky-menu.animated.fadeInUp");
	private final By daily_bonus_game_info_window_close = By.cssSelector("div.progress-bar-sticky-menu__close");
	private final By inline_gpt_ad_size = By.cssSelector("div#div-gpt-ad-bottom>div>iframe");
	private final By inline_gpt_ad = By.cssSelector("div#div-gpt-ad-bottom");
	private final By right_rail_gpt_ad_1 = By.cssSelector("div#div-gpt-ad-multiple");
	private final By right_rail_gpt_ad_2 = By.cssSelector("div#div-gpt-ad-box");
	private final By right_rail_gpt_ad_1_size = By.cssSelector("div#div-gpt-ad-multiple> div>iframe");
	private final By right_rail_gpt_ad_2_size = By.cssSelector("div#div-gpt-ad-box> div>iframe");
	private final By inline_gpt_tile_ad = By.cssSelector("div#div-gpt-ad-tile>div>iframe");
	private final By bottom_native_ad = By.cssSelector("div#gpt-ad-bottom-native");
	private final By bottom_native_ad_size = By.cssSelector("div#gpt-ad-bottom-native>div>iframe");
	private final By trending_now_native_ad = By.cssSelector("div#div-nativ-ad-trendingnow");
	private final By sponsored_native_ad = By.cssSelector("div#div-gpt-ad-sponsored>div>iframe");
	private final By latest_and_greatest_last_title = By.xpath("(//div[@id='left']/div)[last()]/div[last()]/a[1]/div");
	private final By latest_and_greatest_last_content_type = By
			.xpath("(//div[@id='left']/div)[last()]/div[last()]/a[2]");
	private final By latest_and_greatest_wide_section = By.xpath("//div[@id='left']//div[contains(@class,'col-md-8')]");
	private final By latest_and_greatest_section_desc = By.xpath("//div[@class='latest-greatest-desc']");
	private final By category_page_sections = By.cssSelector("div.row.bottom-spacer.latest-greatest-row");
	private final By official_rules_link = By.linkText("Official Rules |");
	private final By sweepstakes_facts_link = By.linkText("Sweepstakes Facts |");
	private final By search_box = By.name("q");
	private final By searchBtn = By.cssSelector("div.search__bar-wrapper__submit");
	private final By first_video_link = By
			.xpath("(//div[contains(@class,'latest-greatest-container')]//a[contains(@href,'video')])[1]");
	private final By first_video_desc = By
			.xpath("(//div[contains(@class,'latest-greatest-container')]//a[contains(@href,'video')]/div[2])[1]");
	private final By latest_activity_amount = By.cssSelector("p.uninav__message-center__earnings");
	private final By token_value = By.cssSelector("span.uninav__reward-center__token-amount");
	private final By recipe_header_menu=By.xpath("//div[@id='header']//a[contains(text(),'Recipes')]");
	private final By collapse_search_box = By.id("searchField2");
	private final By latest_activity_msg = By.xpath("//div[@class='uninav__message-center__message-display']/p");
	private final By latest_video_activity_dsc = By.xpath("//div[@class='uninav__message-center__message-display']/p[3]");
	private final By pch_front_page_link = By.linkText("PCHfrontpage");

	/**
	 * Click on the Search button
	 * 
	 */
	public void click_search_button() {
		button(searchBtn, 10);
	}

	/**
	 * Click on the PCHfrontpage
	 * 
	 */
	public void click_pch_front_page_link(){
		button(pch_front_page_link,10);
	}
	
	/**
	 * Search the given string
	 * 
	 * @param SearchString
	 */
	public void search(String searchvalue) {
		textbox(search_box, "enter", searchvalue, 5);
		button(searchBtn, 10);
	}

	/**
	 * Verify the visibility of the Search Box
	 * 
	 * @return
	 */
	public boolean verify_search_box() {
		moveToElement(search_box);
		return elementVisibility(search_box, 30);
	}

	/**
	 * Return the count of the Page sections of the category page
	 * 
	 * @return
	 */
	public int get_count_of_category_page_sections() {
		return get_webelements_list(category_page_sections).size();
	}

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
	 * Verify the display of Bottom Native Ad
	 * 
	 * @return
	 */
	public boolean verify_bottom_native_ad() {
		moveToElement(bottom_native_ad);
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
		size[0] = getAttribute(inline_gpt_ad_size, "width");
		size[1] = getAttribute(inline_gpt_ad_size, "height");
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
		size[0] = getAttribute(right_rail_gpt_ad_1_size, "width");
		size[1] = getAttribute(right_rail_gpt_ad_1_size, "height");
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
		size[0] = getAttribute(right_rail_gpt_ad_2_size, "width");
		size[1] = getAttribute(right_rail_gpt_ad_2_size, "height");
		return size;
	}

	/**
	 * Returns the Google unique id for the Inline Ad
	 * 
	 * @return
	 */
	public String get_inline_gpt_ad_google_query_id() {
		return getAttribute(inline_gpt_ad, "data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Right Rail Ad One
	 * 
	 * @return
	 */
	public String get_right_rail_ad_1_google_query_id() {
		return getAttribute(right_rail_gpt_ad_1, "data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Right Rail Ad Two
	 * 
	 * @return
	 */
	public String get_right_rail_ad_2_google_query_id() {
		return getAttribute(right_rail_gpt_ad_2, "data-google-query-id");
	}

	/**
	 * Returns the Google unique id for the Bottom Native Ad
	 * 
	 * @return
	 */
	public String get_bottom_native_ad_google_query_id() {
		return getAttribute(bottom_native_ad, "data-google-query-id");
	}

	/**
	 * Return the total page Ad google id's
	 * 
	 * @return
	 */
	public String[] get_page_ad_google_query_id_with_bottom_ad() {
		String ad_google_id[] = new String[4];
		ad_google_id[0] = get_inline_gpt_ad_google_query_id();
		ad_google_id[1] = get_right_rail_ad_1_google_query_id();
		ad_google_id[2] = get_right_rail_ad_2_google_query_id();
		ad_google_id[3] = get_bottom_native_ad_google_query_id();
		return ad_google_id;
	}

	/**
	 * Return the total page Ad google id's
	 * 
	 * @return
	 */
	public String[] get_page_ad_google_query_id_without_bottom_ad() {
		String ad_google_id[] = new String[3];
		ad_google_id[0] = get_inline_gpt_ad_google_query_id();
		ad_google_id[1] = get_right_rail_ad_1_google_query_id();
		ad_google_id[2] = get_right_rail_ad_2_google_query_id();
		return ad_google_id;
	}

	/**
	 * Click on Comics Header Menu
	 */
	public void click_comics_header_menu() {
		button(comics_header_menu, 10);
	}
	
	/**
	 * Click on Life Hacks Header Menu
	 */
	public void click_lifehacks_header_menu() {
		button(lifehacks_header_menu, 10);
	}
	
	/**
	 * Click on Pets Header Menu
	 */
	public void click_pets_header_menu() {
		button(pets_header_menu, 10);
	}
	
	/**
	 * Click on Trending Header Menu
	 */
	public void click_trending_header_menu() {
		button(trending_header_menu, 10);
	}

	/**
	 * Verify the visibility of Today's Pick section
	 * 
	 * @return
	 */
	public boolean verify_todays_pick_section() {
		return elementVisibility(todays_pick_section, 60);
	}

	/**
	 * Click on Horoscope Header Menu
	 */
	public void click_horoscope_menu() {
		button(horoscope_menu, 10);
	}

	/**
	 * Click on Health Header Menu
	 */
	public void click_health_menu() {
		button(health_menu, 10);
	}

	/**
	 * Click on Header Menu based on the given parameter
	 */
	public void click_header_menu(String menu_name) {
		By locator_menu = By.xpath("//div[@id='header']//a[text()='" + menu_name + " ']");
		button(locator_menu, 10);
	}

	/**
	 * Gets the add locator as first param expected Width of add as second and
	 * expected Height of ad as 3rd param. Returns True if the expected and
	 * actual dimention are same else returns false.
	 * 
	 * @param actual
	 * @param expected
	 * @return
	 */
	public boolean check_ad_dimention(By adLocator, int expectedWidth, int expectedHeight) {
		Boolean flag = false;
		int actualHeight = Integer.parseInt(getAttribute(adLocator, "height"));
		int actualWidth = Integer.parseInt(getAttribute(adLocator, "width"));

		if (compareInts(actualHeight, expectedHeight) && compareInts(actualWidth, expectedWidth)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/** Return True if the date is displayed on the EDL page */
	public boolean verfiy_date_on_page() {
		return elementPresent(date_in_title);
	}

	/**
	 * Checks if the selected page is highlighted in the header menu Param
	 * selMenuName : The menu item name ex: comic, horoscope etc..
	 * 
	 * @return
	 */
	public boolean verify_item_highlighted_header_menu(String selMenuName) {
		List<WebElement> header_menu_list = get_webelements_list(header_menu_items);
		for (WebElement e : header_menu_list) {
			if (e.getAttribute("class").contains("menu__item--selected")) {
				if (e.getText().equalsIgnoreCase(selMenuName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the selected page is highlighted in the footer menu Param
	 * selMenuName : The menu item name ex: comic, horoscope etc..
	 * 
	 * @return
	 */
	public boolean verify_item_highlighted_footer_menu(String selMenuName) {
		List<WebElement> footer_menu_list = get_webelements_list(footer_menu_items);
		for (WebElement e : footer_menu_list) {
			if (e.getAttribute("class").contains("menu__item--selected")) {
				if (e.getText().equalsIgnoreCase(selMenuName)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check to see if the EDL page is displayed with the page title on top left
	 * Param : The expected title ex : comic , horoscope etc Return : true if
	 * the tile is displayed with the expected title name else false
	 */
	public boolean verify_edl_title(String title) {
		return compareEqualStringsWithIgnoreCase(getText(edl_title, 5), title);
	}

	/** Returns true if Sweep stakes is present on the right rail */
	public boolean verify_sweepstakes() {
		return elementPresent(sweep_stakes);
	}
	
	/** Returns true if Sweep stakes is present on the right rail */
	public boolean verify_home_sweepstakes() {
		return elementPresent(sweep_stakes_home);
	}

	/**
	 * Returns true if the search now search box is displayed on the right rail
	 * else returns false
	 */
	public boolean verify_searchBox() {
		return elementPresent(edl_search);
	}

	/**
	 * Return the Trending Now title
	 * 
	 * @param trending_now_title
	 * @return
	 */
	public String get_trending_now_title() {
		moveToElement(trending_now);
		return getText(trending_now, 20);
	}

	/** Clicks on the Trending now first video link */
	public void click_first_trending_now_category_video() {
		moveToElement(trending_now_video_link);
		button(trending_now_video_link, 20);
	}

	/** Gets the video title of the first video link */
	public String get_trending_now_first_video_title() {
		return getText(trending_now_video_link, 20);
	}

	/**
	 * Return the Count of Trending Now videos
	 * 
	 * @return
	 */
	public int get_trending_now_video_count() {
		return get_webelements_list(trending_now_videos).size();
	}

	/**
	 * To click on Sign In link on home page for unrecognized user.
	 */
	public void click_sign_in() {
		button(sign_in, 10);
	}

	/**
	 * To verify the my account link and also can be used to successful login
	 * and registration
	 */
	public boolean verify_home() {
		return elementPresent(my_account);
	}

	/**
	 * To click on Register link on home page for unrecognized user.
	 */
	public void click_register() {
		button(register, 10);
	}

	/**
	 * @return TokenValue
	 */
	public int get_token_amount_from_uninav() throws Exception {
		sleepFor(10);
		waitForElementPresent(token_value_on_uninav, 40);
		String token_value = getText(token_value_on_uninav, 5);
		if (token_value.contentEquals("Loading...")) {
			DriverManager.getDriver().navigate().refresh();
		}
		log.info("Token Value is: " + token_value);
		token_value = token_value.replace(",", "");
		return Integer.parseInt(token_value);
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
	 * To click on Token History link from Activity section
	 */
	public void click_token_history() {
		button(token_history_link, 5);
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
	 * Check the edl home page second section which basically has 'The Latest &
	 * Greatest' written Param : The expected title as configured in joomla
	 * Return : true if the title is displayed with the expected title name else
	 * false
	 */
	public Boolean verify_homepage_second_title(String theTitle) {
		return compareEqualStrings(getText(edl_home_second_title, 5), theTitle);
	}

	/**
	 * Returns the title of the last latest and greatest article/video section
	 */
	public String get_ltst_and_grtst_last_title() {
		return getText(latest_and_greatest_last_title, 20);
	}

	/**
	 * The latest and greatest section can either have video,recipes,article,ads
	 * or sweepstakes fetches the href attribute of last section to assert any
	 * of the listed categories
	 * 
	 * @return
	 */
	public String get_ltst_and_grtst_last_content_type() {
		return getAttribute(latest_and_greatest_last_content_type, "href");
	}

	/**
	 * Clicks on the last article/video section present below latest&greatest
	 */
	public void click_ltst_and_grtst_last_content_type() {
		button(latest_and_greatest_last_content_type, 20);
	}
	
	/**
	 * Returns the category type of last latest & Greatest section 
	 * 
	 * @return
	 */
	public String get_ltst_and_grtst_last_category_type() {
		
	return getAttribute(latest_and_greatest_last_content_type, "href").substring(getAttribute(latest_and_greatest_last_content_type, "href").lastIndexOf("/")+1).replace("-", " ");
		
	}

	/**
	 * The fourth article/video div is 8-cols wide. Returns true if it is
	 * displayed else returns false.
	 * 
	 * @return
	 */
	public boolean is_wide_section_displayed() {
		return elementPresent(latest_and_greatest_wide_section);
	}

	/**
	 * Returns the Count of the number of articles/video section present below
	 * latest * greatest section present on Edl Home page.
	 * 
	 * @return
	 */
	public int get_size_of_ltst_and_grtst_section() {
		return get_webelements_list(latest_and_greatest_section_desc).size() - 1;
	}

	/** Clicks on the official Rules link present on the top of the page */
	public void click_official_rules_link() {
		button(official_rules_link, 20);
	}

	/** Clicks on the sweepstacks facts link present on the top of the page */
	public void click_sweepstakes_facts_link() {
		button(sweepstakes_facts_link, 20);
	}

	/** Clicks on the first available video on the EDL Home Page */
	public void click_first_video() {
		button(first_video_link, 20);
	}

	/**
	 * Returns the first avilable video's description present on the home page
	 */
	public String get_first_video_desc() {
		return getText(first_video_desc, 20);
	}

	/**
	 * @return Latest activity Token amount
	 */
	public String get_latest_activity_token_amount() {
		sleepFor(5);
		return getAttribute(latest_activity_amount, "innerHTML").replace(",", "").split(" ")[0];
	}

	public void click_recipe_header_menu() {
		button(recipe_header_menu,20);
	}

	public String get_main_category_type() {
		return getCurrentUrl().substring(getCurrentUrl().lastIndexOf("/") + 1);
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
	 * Search for the given term on collapse search box
	 * 
	 * @param search_term
	 */
	public void search_term_on_right_rail(String search_term) {
		textbox(collapse_search_box, "enter", search_term, 2);
		button(searchBtn, 2);
	}	
	
	/**
	 * @return Latest entry activity Message
	 */
	public String get_latest_entry_activity_message() {
		sleepFor(5);
		return getAttribute(latest_activity_msg, "innerHTML").replace(",", "");
	}
	
	
	/**
	 * @return Returns the description for watching video under latest activity window
	 */
	public String get_latest_video_activity_description() {
		sleepFor(5);
		return getAttribute(latest_video_activity_dsc, "innerHTML");
	}
		
}
