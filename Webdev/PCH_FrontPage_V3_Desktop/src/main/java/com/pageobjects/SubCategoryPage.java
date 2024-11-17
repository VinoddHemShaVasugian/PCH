package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class SubCategoryPage extends BaseClass {

	private static final SubCategoryPage instance = new SubCategoryPage();
	private final JoomlaConfigPage admin_instance = JoomlaConfigPage.getInstance();

	private SubCategoryPage() {
	}

	public static SubCategoryPage getInstance() {
		return instance;
	}

	private final By article_list = By.cssSelector("div.stories > div.row.listing");
	private final By trending_elements_list = By
			.cssSelector("div.bottom-spacer > div.row.listing > div:nth-of-type(2)");
	private final By popular_videos_section_title_name = By
			.xpath("//div[@class='bottom-spacer']/following-sibling::h3");
	private final By gpt_ad_position = By
			.xpath("//div[@class='stories']/div[@id='div-gpt-ad-bottom']/preceding-sibling::*");
	private final By native_ad_position = By
			.xpath("//div[@class='stories']/div[@id='div-nativ-ad-subcategory']/preceding-sibling::*");
	private final By view_more_button = By.cssSelector("div.viewMorebtn > a");
	private final By video_play_icon = By.cssSelector("div.stories i");
	private final By article_without_image = By.cssSelector("div.col-xs-12.listing__content");
	private final By full_story_link = By.cssSelector("span.listing__content__a__full-story");
	private final By first_article_link = By
			.xpath("//div[@class='stories']/div[@class='row listing']/div[1]/a[contains(@href,'/article')][1]");
	private final By first_video_link = By
			.xpath("//div[@class='stories']/div[@class='row listing']/div[1]/a[contains(@href,'/video')][1]");

	/**
	 * Click the First Article link of the Sub Category page
	 */
	public void click_first_article_link() {
		moveToElement(get_webelements_list(first_article_link).get(0));
		button(get_webelements_list(first_article_link).get(0), 30);
	}

	/**
	 * Click the First Video link of the Sub Category page
	 */
	public void click_first_video_link() {
		moveToElement(get_webelements_list(first_video_link).get(0));
		button(get_webelements_list(first_video_link).get(0), 60);
	}

	/**
	 * Return the Full Story count from teh Sub Category page
	 * 
	 * @return
	 */
	public int get_full_story_link_count() {
		return get_webelements_list(full_story_link).size();
	}

	/**
	 * Click on Full Story link based on the given Article number
	 * 
	 * @param article_no
	 */
	public void click_full_story_link(int article_no) {
		button(full_story_link, 5);
	}

	/**
	 * Return True if the story without an image is present on Sub Category page
	 * 
	 * @return
	 */
	public boolean verify_article_without_image_presence() {
		return elementPresent(article_without_image);
	}

	/**
	 * Return the Count of the Video stories present on the page
	 * 
	 * @return
	 */
	public int get_count_of_videos_on_page() {
		return get_webelements_list(video_play_icon).size();
	}

	/**
	 * Click on View More button
	 */
	public void click_view_more() {
		button(view_more_button, 5);
	}

	/**
	 * Return the page number details which going to navigate when click on View
	 * More button
	 * 
	 * @return
	 */
	public String get_next_page_details_from_view_more() {
		return getAttribute(view_more_button, "href");
	}

	/**
	 * Return the position of the Native Ad unit in the Sub Category page
	 * 
	 * @return
	 */
	public int get_position_of_native_ad_unit() {
		return get_webelements_list(native_ad_position).size() + 1;
	}

	/**
	 * Return the position of the GPT Ad unit in the Sub Category page
	 * 
	 * @return
	 */
	public int get_position_of_gpt_ad_unit() {
		return get_webelements_list(gpt_ad_position).size() + 1;
	}

	/**
	 * Return the count of the Stories present in the Sub Category page
	 * 
	 * @return
	 */
	public int get_article_count() {
		return get_webelements_list(article_list).size();
	}

	/**
	 * Return the count of the Trending Elements in the Sub Category page
	 * 
	 * @return
	 */
	public int get_trending_elements_count() {
		return get_webelements_list(trending_elements_list).size();
	}

	/**
	 * Return the Popular Video section title name of the Sub Category page
	 * 
	 * @return
	 */
	public String get_popular_videos_section_title_name() {
		return getText(popular_videos_section_title_name, 5);
	}

	/**
	 * Select the Native Ad position value
	 * 
	 * @param position
	 */
	public void select_native_ad_position(int position) {
		selectByValue(admin_instance.get_dropdown_field_element_by_label("Native Ad Position"),
				String.valueOf(position), 15);
	}

	/**
	 * Enter the GPT Ad position value
	 * 
	 * @param position
	 */
	public void enter_gpt_ad_position(int position) {
		textbox(admin_instance.get_text_field_element_by_label("Ad Positions"), "enter", String.valueOf(position), 15);
	}
}
