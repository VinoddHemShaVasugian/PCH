package com.pch.frontpage.pageObjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class HealthiNationPage extends BaseClass {

	private static final HealthiNationPage healthination_instance = new HealthiNationPage();

	private HealthiNationPage() {
	}

	public static HealthiNationPage getInstance() {
		return healthination_instance;
	}

	private final By bottom_ad = By.cssSelector("div#div-gpt-ad-tile");
	private final By right_rail_ad1 = By.cssSelector("div#div-gpt-ad-multiple");
	private final By right_rail_ad2 = By.cssSelector("div#div-gpt-ad-box");
	private final By ad_video_player = By.cssSelector("div[aria-label='Video Player']");
	private final By token_award_message = By.cssSelector("aside.fp-rolling-overlay");
	private final By token_award_default_message = By.cssSelector("span.fp-video-token-bar__text");
	private final By play_button = By.cssSelector("div[aria-label='Play'][role='button']");
	private final By reset_button = By.cssSelector("div[aria-label='Rewind 10s'][role='button']");
	private final By tokens_claimed_button = By
			.cssSelector("div.fp-video-token-bar__tokens.fp-video-token-button.fp-video-token-button--just-claimed");
	private final By tokens_already_claimed_button = By.cssSelector(
			"div.fp-video-token-bar__tokens.fp-video-token-button.fp-video-token-button--just-claimed.fp-video-token-button--already-claimed");

	/**
	 * Retrieve the Token award message
	 * 
	 * @return
	 */
	public String get_token_award_message() {
		return getAttribute(token_award_message, "innerHTML");
	}

	/**
	 * Retrieve the Token award default message
	 * 
	 * @return
	 */
	public String get_token_award_default_message() {
		return getText(token_award_default_message, 45);
	}

	/**
	 * Verify the Token award message
	 * 
	 * @return
	 */
	public boolean verify_token_award_default_message() {
		return elementVisibility(token_award_default_message);
	}

	/**
	 * Wait up to the HealthiNation ad completes
	 */
	public void wait_for_ad_complete() {
		waitForElementVisibility(token_award_message, 90);
	}

	/**
	 * Wait up to the HealthiNation video completes
	 */
	public void wait_for_video_complete() {
		waitForElementNotVisible(ad_video_player, 500);
	}

	/**
	 * Verify the display of the Play button on the Video player
	 * 
	 * @return
	 */
	public boolean verify_play_button() {
		mouseHover(ad_video_player);
		return elementVisibility(play_button);
	}

	/**
	 * Verify the display of the Token Claim button on the Video player
	 * 
	 * @return
	 */
	public boolean verify_token_claim_button() {
		return elementVisibility(tokens_claimed_button);
	}

	/**
	 * Verify the display of the Token Already Claim button on the Video player
	 * 
	 * @return
	 */
	public boolean verify_token_already_claim_button() {
		return elementVisibility(tokens_already_claimed_button);
	}

	/**
	 * Verify the display of the Reset button on the Video player
	 * 
	 * @return
	 */
	public boolean verify_reset_button() {
		mouseHover(ad_video_player);
		return elementVisibility(reset_button);
	}

	/**
	 * Verify the presence of the Video player
	 * 
	 * @return
	 */
	public boolean verify_video_player() {
		return elementPresent(ad_video_player);
	}

	/**
	 * Verify the presence of the Bottom GPT ad on Video player
	 * 
	 * @return
	 */
	public boolean verify_bottom_ad() {
		return elementVisibility(bottom_ad);
	}

	/**
	 * Verify the presence of the First Right rail GPT ad on Video player
	 * 
	 * @return
	 */
	public boolean verify_right_rail_ad_one() {
		return elementVisibility(right_rail_ad1);
	}

	/**
	 * Verify the presence of the Second Right rail GPT ad on Video player
	 * 
	 * @return
	 */
	public boolean verify_right_rail_ad_two() {
		return elementVisibility(right_rail_ad2);
	}
}
