package com.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class ComicsPage extends BaseClass {

	private static final ComicsPage comic_instance = new ComicsPage();

	private ComicsPage() {
	}

	public static ComicsPage getInstance() {
		return comic_instance;
	}

	private final By initial_feature_comic = By.xpath("//div[@id='top-carousel']//div[@data-item-index='0']/div/img");
	private final By first_carousel_image = By.xpath("//div[@id='bottom-carousel']//div[@data-item-index='0']/div/img");
	private final By more_comics_header = By.xpath("//div[@class='row comics-section-header']");
	private final By carousel_container_comics = By
			.xpath("//div[@id='bottom-carousel']//div[contains(@class,'pch-carousel-content-item')]"); 
	
	private final By top_carousel_next_arrow = By.cssSelector("div#top-carousel a.pch-carousel-forward-link");
	private final By current_selected_comic = By
			.xpath("//div[@id='top-carousel']//div[contains(@class,'dt-fx0')]/div/img"); 
	private final By current_carosel_comics = By
			.xpath("//div[@id='bottom-carousel']//div[contains(@class,'d-fxx d-fx')]/div/img"); 
	/* Only 3 comics must be displayed at a given point fx01,fx02,fx03 */																								 
	private final By more_comics_next_arrow = By.cssSelector("svg#svg-carousel-nav-forward");
	private final By bottom_carousel_next_arrow = By.cssSelector("div#bottom-carousel a.pch-carousel-forward-link");

	/*----------------------------------------------------------------------------------------*/

	public String get_first_feature_comic_image() {
		return getAttribute(initial_feature_comic, "src");
	}

	public String get_left_most_carousel_image() {
		return getAttribute(first_carousel_image, "src");
	}

	/**
	 * Below the feature area, More comics section will be display with previous
	 * day comics
	 */
	public Boolean verify_more_comics_present() {

		return compareEqualStrings(getText(more_comics_header, 5), "MORE COMICS");

	}

	/**
	 * On hover, the left and right scroll should display.
	 */

	public boolean verify_top_carousel_next_arrow() {

		mouseHover(initial_feature_comic);
		return elementPresent(top_carousel_next_arrow); /* checking the arrow is present on hover */
														 
	}

	public void click_top_carousel_next_arrow() {
		mouseHover(initial_feature_comic);
		moveToElement(top_carousel_next_arrow);
		button(top_carousel_next_arrow,	20); /* clicking the arrow to change the image */
	}

	/**
	 * Rerurns back the current comic image being displayed in the main feature
	 * comic container
	 */
	public String get_carosel_image() {

		return getAttribute(current_selected_comic, "src");

	}

	public void click_more_comics_next_arrow() {
		button(more_comics_next_arrow, 20); /* Change carosel comics */
	}

	/* at a given point only 3 comics are displayed in the carosel */
	public int get_currenct_carosel_comics_size() {

		return get_webelements_list(current_carosel_comics).size();

	}

	/** Returns a list of comics currently displayed in carosel */
	public List<String> get_comics_displayed_in_carosel() {
		List<String> carosel_image_list = new ArrayList<String>();

		List<WebElement> current_comics_list = get_webelements_list(current_carosel_comics);

		for (WebElement elm : current_comics_list) {
			carosel_image_list.add(getAttribute(elm, "src"));
		}
		return carosel_image_list;
	}

	public boolean verify_bottom_carousel_next_arrow() {

		mouseHover(current_carosel_comics);
		return elementVisibility(bottom_carousel_next_arrow, 20);

	}

	/**
	 * On click on forward/backward arrows, the 3 comics of the current slide
	 * should get changed and the previous/next day comics should be display.
	 */
	public void click_bottom_carousel_next_arrow() {

		moveToElement(bottom_carousel_next_arrow);
		button(bottom_carousel_next_arrow, 20);

	}

	/**
	 * Number of content to be display on the More Comics section will be based
	 * on the admin field Previous Comics Count. If the count is 'n', then 'n+1'
	 * comic will be display on section
	 */
	public int total_comics_count() {

		return get_webelements_list(carousel_container_comics).size();

	}

}
