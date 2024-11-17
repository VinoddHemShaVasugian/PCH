package com.pageobjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.util.BaseClass;

public class ScratchPathPage extends BaseClass {

	private static final ScratchPathPage scratchpath_instance = new ScratchPathPage();

	private ScratchPathPage() {
	}

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final InterstitialPage interstitial_instance = InterstitialPage.getInstance();
	private final By play_now_button = By.cssSelector("span.line1");
	private final By continue_button = By.cssSelector("span.gos__but-text");
	private final By scratch_path_token_amount = By.cssSelector("div.gos__amount");
	private final By reveal_all = By.id("reveal-all");
	private final By official_rules_link = By.id("official-rules");
	private final By facts_link = By.id("facts");

	/**
	 * Returns a Singleton instance
	 * 
	 * @return ScratchPathPage
	 */
	public static ScratchPathPage getInstance() {
		return scratchpath_instance;
	}

	/**
	 * Click on Play Now button on ScratchPath games
	 * 
	 * @throws Exception
	 */
	public String play_scratchpath_game() throws Exception {
		switch_to_scratchpath_iframe();
		button(play_now_button, 60);
		switch_iframe_default_content();
		while (!elementVisibility(continue_button)) {
			switch_to_scratchpath_iframe();
			assertTrue(verify_official_rules_link());
			assertTrue(verify_facts_link());
			button(reveal_all, 60);
			switch_iframe_default_content();
		}
		String token_amount = get_scratch_path_token_amount();
		button(continue_button, 60);
		return token_amount;
	}

	/**
	 * Wait for the ScratchPath Ad's to display
	 * 
	 * @throws Exception
	 */
	public void wait_for_scratchpath_ads_to_dsiplay() throws Exception {
		try {
			if (getTitle().contains("Publishers Clearing House")) {
				interstitial_instance.wait_for_skip_btn();
				interstitial_instance.click_skip_btn();
			}
		} catch (Exception e) {
			log.error("Exception in Scratch path Ad wait: " + e.getLocalizedMessage());
		}
	}

	/**
	 * Return the earned ScratchPath token amount
	 * 
	 * @return
	 */
	public String get_scratch_path_token_amount() {
		return getText(scratch_path_token_amount, 60).split(" ")[0].replace(",", "");
	}

	/**
	 * Switch to the Scratch path IFrame
	 * 
	 * @throws Exception
	 */
	public void switch_to_scratchpath_iframe() throws Exception {
		switch_to_iframe("iframe", count_iframe_get_desired_iframe_count("iframe", "src", "pathgamesassets"));
	}

	/**
	 * Verify the presence of Official Rules link
	 * 
	 * @return
	 */
	public boolean verify_official_rules_link() {
		return elementVisibility(official_rules_link);
	}

	/**
	 * Verify the presence of Facts link
	 * 
	 * @return
	 */
	public boolean verify_facts_link() {
		return elementVisibility(facts_link);
	}
}
