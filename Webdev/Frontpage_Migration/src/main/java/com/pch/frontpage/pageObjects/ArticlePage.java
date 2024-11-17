package com.pch.frontpage.pageObjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class ArticlePage extends BaseClass {

	private static final ArticlePage instance = new ArticlePage();
	private final HomePage homepage_instance = HomePage.getInstance();
	private final ArticlePage article_page = ArticlePage.getInstance();

	public static ArticlePage getInstance() {
		return instance;
	}

	private final By next_article = By.cssSelector("div#nextArticle > a");
	private final By unclaim_token_amount = By.cssSelector("span.buttons__token-amount");
	private final By green_unclaim_token = By.cssSelector("button.buttons.buttons_claim.buttons_green.unclaimed");
	private final By grey_claim_token = By.cssSelector("button.buttons.buttons_claim.buttons_grey.claimed");
	private final By error_page = By.xpath("//h1[contains(text(),'Error 404')]");
	private final By complete_reg_to_earn_tokens = By
			.xpath("//div[text()='Complete Registration now to earn tokens for enjoying articles!']");
	private final By login_to_earn_tokens = By
			.xpath("//div[text()='Log In or Register now to earn tokens for enjoying articles!']");
	private final By bottom_gpt_ad = By.cssSelector("div#gpt-ad-bottom-native");
	private final By teads_contianer = By.xpath("//div[@id='teadsAd']/div[contains(@id,'google_ads_iframe')]");
	private final By main_page_category = By.cssSelector("section.breadcrumbs_wrapper");
//	private final By sub_page_category_2 = By.cssSelector("span.breadcrumbs_wrapper__crumb:nth-of-type(2)"); //step 6
//	private final By sub_page_category_1 = By.cssSelector("span.breadcrumbs_wrapper__crumb:nth-of-type(1)"); //For STG
	private final By sub_page_category = By.cssSelector("section.breadcrumbs_wrapper");
	private final By article_title = By.cssSelector("article#article>h1");

	/**
	 * Return the Story title
	 * 
	 * @return
	 */
	public String get_article_title() {
		return getText(article_title, 30);
	}

	/**
	 * Return the Category type
	 * 
	 * @return
	 */
	public String get_main_category_type() {
		String type = getText(main_page_category, 30);
		System.out.println("get_main_category_type - type :" + type);
		return type.contains("/") ? type.split("/")[1].toLowerCase().trim() : type.toLowerCase().trim();
	}

	/**
	 * Return the both Category & Sub-Category type
	 * 
	 * @return
	 */
	public String get_sub_category_type() {
		String type = getText(sub_page_category, 30);
		return type.contains("/") ? type.split("/")[1].toLowerCase().trim() : type.toLowerCase().trim();
	}

	/**
	 * Verify the display of Teads ad
	 * 
	 * @return
	 */
	public boolean verify_teads_ad() {
		return elementPresent(teads_contianer);
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
		return elementPresent(next_article);
	}

	/**
	 * Click on Next Article link
	 * 
	 */
	public void click_next_article() {
		button(next_article, 20);
	}

	/**
	 * Click on Claim token button
	 * 
	 */
	public void click_claim_button() {
		button(green_unclaim_token, 20);
		waitForElementVisibility(grey_claim_token, 30);
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
	 * Verify the text - Complete Registration now to earn tokens for enjoying
	 * articles!
	 * 
	 * @return
	 */
	public boolean verify_text_complete_reg_earn_tokens() {
		return elementVisibility(complete_reg_to_earn_tokens);
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
	 * Cliam tokens and verify vip level up message
	 * @return true
	 * @throws Exception 
	 */
	public boolean validating_vip_level_up(String firstName) throws Exception {
		homepage_instance.click_first_article_link();
		homepage_instance.click_claim_button();
		assertTrue(homepage_instance.verify_claimed_button());
		try {
			String vip_c1_msg = "Hi, "+firstName+""+msg_property_file_reader("vip_c1_msg_header")+"\n"+msg_property_file_reader("vip_c1_msg_body");
			System.out.println("vip_c1_msg: "+vip_c1_msg);
			homepage_instance.clic_new_VIP_logo(5);
			String afterActivity= homepage_instance.get_vip_msg();
			assertNotEquals(afterActivity, vip_c1_msg);
		return true;
			}
			catch(Exception e) {
				return false;
			}
	}
}
