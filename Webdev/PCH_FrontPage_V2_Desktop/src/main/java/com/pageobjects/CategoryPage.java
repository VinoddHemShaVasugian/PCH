package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class CategoryPage extends BaseClass {

	private static final CategoryPage instance = new CategoryPage();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();

	private CategoryPage() {
	}

	public static CategoryPage getInstance() {
		return instance;
	}

	private final By gpt_ad_position = By
			.xpath("//div[@id='div-gpt-ad-bottom']/preceding-sibling::h3[@class='section-header']");
	private final By native_ad_position = By
			.xpath("//div[@id='div-native-ad-sponsored']/preceding-sibling::h3[@class='section-header']");
	private final By native_ad_under_top_stories = By.cssSelector("div#div-nativ-ad-home");

	/**
	 * Return the number of preceding siblings in the right rail of popular videos
	 * 
	 * @param popular_video_title
	 * @return
	 */
	public int find_popular_video_position(String popular_video_title) {
		By locator = By.xpath("//div[@id='right']//h3[text()='" + popular_video_title + "']/preceding-sibling::div");
		return get_webelements_list(locator).size();

	}

	/**
	 * Verify the display of Top Story Native ad
	 * 
	 * @return
	 */
	public boolean verify_top_story_native_ad() {
		return elementPresent(native_ad_under_top_stories);
	}

	/**
	 * Return the position of the Native Ad unit in the Sub Category page
	 * 
	 * @return
	 */
	public int get_position_of_native_ad_unit() {
		return get_webelements_list(native_ad_position).size();
	}

	/**
	 * Return the position of the GPT Ad unit in the Sub Category page
	 * 
	 * @return
	 */
	public int get_position_of_gpt_ad_unit() {
		return get_webelements_list(gpt_ad_position).size();
	}

	/**
	 * Select the Native Ad position value
	 * 
	 * @param position
	 */
	public void select_native_ad_position(int position) {
		selectByValue(admin_instance.get_dropdown_field_element_by_label("Tile Sponsored Ad Position"),
				String.valueOf(position), 15);
	}

	/**
	 * Select the GPT Ad position value
	 * 
	 * @param position
	 */
	public void select_gpt_ad_position(int position) {
		selectByValue(admin_instance.get_dropdown_field_element_by_label("Tile Ad Position"), String.valueOf(position),
				15);
	}
}
