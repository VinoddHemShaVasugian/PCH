package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class ArticlePage extends BaseClass {

	private static final ArticlePage article_instance = new ArticlePage();

	private ArticlePage() {
	}

	public static ArticlePage getInstance() {
		return article_instance;
	}

	private final By next_article = By.cssSelector("div#nextArticle>a");
	private final By article_desc = By.tagName("h4");
	private final By article_sub_category_type = By.cssSelector("span.breadcrumbs_wrapper__crumb:nth-of-type(2)");
	private final By article_main_category_type = By.cssSelector("span.breadcrumbs_wrapper__crumb:nth-of-type(1)");
	private final By article_gpt_ad = By.id("gpt-ad-bottom-native");
	private final By fp_logo=By.xpath("//*[@id='pch-uni-nav']//a[contains(text(),'PCHfrontpage')]");
	private final By unclaim_token_amount = By.cssSelector("span.buttons__token-amount");
	private final By green_unclaim_token = By.cssSelector("button.buttons.buttons_claim.buttons_green.unclaimed");
	private final By grey_claim_token = By.cssSelector("button.buttons.buttons_claim.buttons_grey.claimed");
	private final By error_page = By.xpath("//h1[contains(text(),'Error 404')]");
	private final By complete_reg_to_earn_tokens = By
			.xpath("//div[text()='Complete Registration now to earn tokens for enjoying articles!']");
	private final By login_to_earn_tokens = By
			.xpath("//div[text()='Log In or Register now to earn tokens for enjoying articles!']");

	/**
	 * 
	 * @return the presence of the Article GPT Ad
	 */
	public boolean verify_article_gpt_ad() {
		return elementVisibility(article_gpt_ad);
	}

	/**
	 * 
	 * @return The Article Sub Category type
	 */
	public String get_article_sub_category_type() {
		return getText(article_sub_category_type, 45).toLowerCase().trim();
	}

	/**
	 * 
	 * @return The Article Main Category type
	 */
	public String get_article_main_category_type() {
		return getText(article_main_category_type, 45).toLowerCase().trim();
	}

	/**
	 * 
	 * @return the presence of the Next Article link
	 */
	public boolean verify_next_article_link() {
		return elementVisibility(next_article);
	}

	/**
	 * Click on Next Article link
	 */
	public void click_next_article_link() {
		button(next_article, 5);
	}

	/**
	 * 
	 * @return The Article description.
	 */
	public String get_article_desc() {
		return getAttribute(article_desc, "innerHTML");
	}
	
	public boolean verify_get_article_desc(){
		return elementVisibility(article_desc);
		}
	
	public void verify_category_tile(){
		 elementVisibility(article_sub_category_type);
		 elementVisibility(article_main_category_type);
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
	 * Verify the text - Complete Registration now to earn tokens for enjoying
	 * articles!
	 * 
	 * @return
	 */
	public boolean verify_text_complete_reg_earn_tokens() {
		return elementVisibility(complete_reg_to_earn_tokens);
	}

	
	

}