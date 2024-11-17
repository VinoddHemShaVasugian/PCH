package com.pageobjects;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class SubCategoryPage extends BaseClass {

	private static final SubCategoryPage sub_category_instance = new SubCategoryPage();

	private SubCategoryPage() {
	}

	public static SubCategoryPage getInstance() {
		return sub_category_instance;
	}

	private final By header_text = By.cssSelector("h2.sub-category__header");
	private final By video_content_on_page = By.cssSelector("article i");
	private final By article_content_on_page = By
			.xpath("//div[contains(@class,'listing-content')]/a[contains(@href,'article')][1]");
	private final By article_content_on_page_without_image = By
			.xpath("//div[@class='listing-content sub-category__item-spacer']/a[contains(@href,'article')][1]");
	private final By full_story_link_of_first_video = By
			.xpath("//a[@class='listing__content__a'][contains(@href,'video')]/span");
	private final By description_of_first_video = By
			.xpath("//a[@class='listing__content__a'][contains(@href,'video')]");
	private final By full_story_link_of_first_article = By
			.xpath("//a[@class='listing__content__a'][contains(@href,'article')]/span");
	private final By description_of_first_article = By
			.xpath("//a[@class='listing__content__a'][contains(@href,'article')]");
	private final By view_more_button = By.cssSelector("div.viewMorebtn>a");
	private final By trending_now_items = By
			.cssSelector("div.bottom-spacer.trending-now:nth-of-type(2)>div[class$='trending-now__item']");
	private final By trending_now_ad = By.cssSelector(
			"div.bottom-spacer.trending-now:nth-of-type(1)>div[class$='trending-now__item trending-now__ad']");
	private final By popular_videos_items = By
			.cssSelector("div.bottom-spacer.trending-now:nth-of-type(3)>div[class$='trending-now__item']");
	private final By popular_video_section_title = By
			.xpath("//div[@class='bottom-spacer trending-now'][2]/preceding-sibling::h3[1]");

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
		return elementPresent(trending_now_ad);
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
	 * 
	 * @return Presence of the View More button
	 */
	public boolean verify_view_more_button() {
		return elementPresent(view_more_button);
	}

	/**
	 * Click on View more button
	 */
	public void click_view_more_button() {
		button(view_more_button, 60);
	}

	/**
	 * 
	 * @return the sub category header name
	 */
	public String get_sub_category_header_text() {
		String category_name = getAttribute(header_text, "innerHTML").replace("<span>", " ").replace("</span>", " ")
				.replace("-", " ").toLowerCase().trim();
		category_name = category_name.contains("/")
				? category_name.substring(category_name.indexOf("/") + 1, category_name.length()).trim()
				: category_name;
		return category_name;
	}

	/**
	 * 
	 * @return the category header name
	 */
	public String get_main_category_header_text() {
		return getAttribute(header_text, "innerHTML").toLowerCase().split("/")[0].trim();
	}

	/**
	 * 
	 * @return the total no. of videos present on the page
	 */
	public int get_total_videos_on_page() {
		return get_webelements_list(video_content_on_page).size();
	}

	/**
	 * 
	 * @return the total no. of articles present on the page with/without images
	 */
	public int get_total_articles_on_page() {
		return get_webelements_list(article_content_on_page).size();
	}

	/**
	 * 
	 * @return the total no. of articles present on the page without images
	 */
	public int get_total_articles_on_page_without_image() {
		return get_webelements_list(article_content_on_page_without_image).size();
	}

	/**
	 * Click on the Full Story link of the First video of the page
	 */
	public void click_full_story_link_of_first_video() {
		button(full_story_link_of_first_video, 30);
	}

	/**
	 * Click on the Full Story link of the First article of the page
	 */
	public void click_full_story_link_of_first_article() {
		button(full_story_link_of_first_article, 30);
	}

	/**
	 * 
	 * @return the description of the first video of the page
	 */
	public String get_desc_of_first_video() {
		String desc = getText(description_of_first_video, 45);
		// Substring is to remove the last 3 .'s from desc.
		return desc.substring(0, desc.indexOf("Full Story") - 4);
	}

	/**
	 * 
	 * @return the description of the first article of the page
	 */
	public String get_desc_of_first_article() {
		String desc = getText(description_of_first_article, 45);
		// Substring is to remove the last 3 .'s from desc.
		return desc.substring(0, desc.indexOf("Full Story") - 4);
	}
}