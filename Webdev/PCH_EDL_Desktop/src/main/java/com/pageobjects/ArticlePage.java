package com.pageobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class ArticlePage extends BaseClass  {
	
	private static final  ArticlePage article_instance = new ArticlePage();

	private ArticlePage() {
	}

	public static ArticlePage getInstance() {
		return article_instance;
	}
	
	private final EDLHomePage edl_instance = EDLHomePage.getInstance();
	
	private final By recipe_of_day=By.xpath("//div[contains(text(),'GET THE RECIPE')]");
	private final By bottom_gpt_ad=By.id("div-gpt-ad-bottom");
	private final By next_article_link=By.xpath("//*[@id='nextSection']/a");
	private final By openx_image=By.xpath("//*[@class='openXImageHolder']");
	private final By search_bar=By.id("searchField2");
	private final By teads_ad=By.xpath("//div[@id='teadsAd']/div[contains(@id,'google_ads_iframe')]");
	private final By unrec_Message=By.id("claimBtnMsgOutsideUrecognized");
	private final By gpt_ad_mutiple_sidebar=By.id("div-gpt-ad-multiple");
	private final By gpt_ad_box=By.id("div-gpt-ad-box");
	private final By dont_miss_text=By.xpath("//*[@id='right']/h3");
	private final By dont_miss_section=By.className("bottom-spacer");
	private final By breadcrumbs_category_title=By.xpath("//*[@id='top']//a[contains(@href,'recipes')]");
	private final By recipe_title=By.className("recipe-of-the-day-content-title");
	private final By footer_menu=By.className("footer");
	private final By header_menu=By.xpath("//ul[@class='menu menu--header']");
	private final By first_article_recipes=By.xpath("//*[@class='row recipes-by-course-row']//div[contains(@style,'/images')][1]");
	private final By unclaim_token_amount = By.cssSelector("span.buttons__token-amount");
	private final By green_unclaim_token = By.cssSelector("button.buttons.buttons_claim.buttons_green.unclaimed");
	private final By grey_claim_token = By.cssSelector("button.buttons.buttons_claim.buttons_grey.claimed");
	private final By error_page = By.xpath("//h1[contains(text(),'Error 404')]");
	private final By login_to_earn_tokens = By
			.xpath("//div[text()='Log In or Register now to earn tokens for enjoying articles!']");
	
	public String get_recipe_title() {
		return getText(recipe_title, 15);
	}
	
	public boolean verify_teads_ad() {
	return elementPresent(teads_ad);
	}
	
	public boolean verify_search_bar(){
	return elementPresent(search_bar);
	}
	
	public boolean verify_dont_miss_section(){
		 elementPresent(dont_miss_text);
		return elementPresent(dont_miss_section);
	}
	
	public boolean verify_footer_menu(){
		return elementPresent(footer_menu);
	}
	
	public boolean verify_header_menu(){
		return elementPresent(header_menu);
	}

	/**
	 * Verify the display of Bottom GPT ad
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean verify_bottom_gpt_ad() throws Exception {
		scrollToBottomOfPage();
		return elementPresent(bottom_gpt_ad);
	}

	/**
	 * Verify the 404 Error page
	 * 
	 * @return
	 */
	public boolean verify_error_page() {
		return elementVisibility(error_page);
	}

	/**
	 * Return True if the Next Article link present
	 * 
	 * @return
	 */
	public boolean verify_next_article_presence() {
    return elementPresent(next_article_link);
	}

	/**
	 * Click on Next Article link
	 * 
	 */
	public void click_next_article() {
		button(next_article_link, 20);
		button(first_article_recipes,20);
	}

	/**
	 * Click on Claim token button
	 * 
	 */
	public void click_claim_button() {
		button(green_unclaim_token, 20);
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
	 * Get the Claim Token button text
	 * 
	 * @return
	 */
	public String get_unclaim_button_text() {
		return getText(green_unclaim_token, 15);
	}

	/**
	 * Get the Claim Token value
	 * 
	 * @return
	 */
	public String get_unclaim_token_value() {
		return getText(unclaim_token_amount, 15);
	}

	/**
	 * Verify the Claimed Token button text
	 * 
	 * @return
	 */
	public String get_claimed_button_text() {
		return getText(grey_claim_token, 15);
	}

	

	/**
	 * Verify the text - Log In or Register now to earn tokens for enjoying
	 * articles!
	 * 
	 * @return
	 */
	public boolean verify_text_login_to_earn_tokens() {
		return elementVisibility(login_to_earn_tokens);
	}

	/**
	 * Retrieve the Story Id of the read article
	 * 
	 * @return
	 */
	public String get_story_id() {
		String page_url = getCurrentUrl();
		Pattern pattern = Pattern.compile("\\w+([0-9]+)\\w+([0-9]+)");
		Matcher matcher = pattern.matcher(page_url);
		matcher.find();
		return matcher.group();
	}
	/**
	 * click the recipe of the day carousel
	 */
	
	public void click_recipe_of_day(){
		button(recipe_of_day,10);
	}
	
	/**
	 * Verify the recipe title on article page
	 * 
	 * @return
	 */
	public boolean verify_recipe_category_title() {
		return elementPresent(breadcrumbs_category_title);
	}
	
	public boolean verify_openx(){
		return elementVisibility(openx_image);
	}
	
	public boolean verify_sidebar_ad(){
		return elementPresent(gpt_ad_mutiple_sidebar);
	}
 
	public boolean verify_side_second_ad(){
	return elementPresent(gpt_ad_box);
	}
	
	public void click_article_category() {
		button(first_article_recipes,20);
	}
	

}

	
	
	
	
	
	
	

