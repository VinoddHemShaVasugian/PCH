package com.pageobjects;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.util.BaseClass;

public class JoomlaConfigPage extends BaseClass {

	private static final JoomlaConfigPage joomla_instance = new JoomlaConfigPage();

	private JoomlaConfigPage() {
	}

	public enum menu {
		Articles, Content;
	}

	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final By username = By.name("username");
	private final By password = By.name("passwd");
	private final By login_btn = By.xpath("//button[@class='btn btn-primary btn-block btn-large']");
	private final By panel_title = By.cssSelector("h1.page-title");
	private final By content_menu_toggle = By.xpath("//a[contains(text(),'Content')]/span");
	private final By article_submenu_toggle = By.xpath("//a[contains(text(),'Articles')]");
	private final By search_textbox = By.id("filter_search");
	private final By article_list = By.cssSelector("td.has-context >div >a");
	private final By save_close_button = By.cssSelector("#toolbar-save>button");
	private final By save_button = By.cssSelector("#toolbar-apply>button");
	private final By close_button = By.cssSelector("#toolbar-cancel>button");
	private final By publish_state = By.id("art_state");
	private final By confirmationMessge = By.cssSelector("div.alert-message");
	private final By searchtools = By.xpath("//button[contains(text(),'Search Tools')]");
	private final By selectcategory = By.xpath("//a[span='- Select Category -']/span");
	private final By categorytextbox = By.xpath(".//*[@id='filter_category_id_chzn']//input[@type = 'text']");
	private final By categorylistoption = By.xpath(".//*[@id='filter_category_id_chzn']//ul[@class='chzn-results']/li");
	private final By desktop_context_key = By.id("pch_contest_keys_desktop_ck");

	/**
	 * Retrieve the Contest Key for Desktop
	 */
	public String get_desktop_contest_key() {
		return getAttribute(desktop_context_key, "value");
	}

	/**
	 * Set the Contest Key for Desktop
	 */
	public void enter_desktop_contest_key(String key) {
		textbox_by_js(desktop_context_key, "enter", key, 45);
	}

	/**
	 * To set the Submit action value for Search Article
	 */
	public void set_submit_action_value(String submit_action_value) {
		textbox_by_js(get_text_field_element_by_label("Value", "2"), "enter", submit_action_value);
	}

	/**
	 * To set the predictive Text value for Search Article
	 */
	public void set_predictive_text(String predictive_text_value) {
		textbox_by_js(get_text_field_element_by_label("Value", "1"), "enter", predictive_text_value);
	}

	/**
	 * Will UnPublish the article, click save and close and verify the
	 * confirmation
	 */
	public boolean unpublish_article() {
		select_visible_text_by_js(publish_state, "Unpublished", 10);
		js_click(save_close_button, 2);
		return getText(confirmationMessge, 10).equals("Successfully Saved.");
	}

	/**
	 * Will publish the article, click save and close and verify the
	 * confirmation
	 */
	public boolean publish_article() {
		select_visible_text_by_js(publish_state, "Published", 18);
		js_click(save_close_button, 2);
		return getText(confirmationMessge, 10).equals("Successfully Saved.");
	}

	/**
	 * Will Save and Close the article
	 */
	public void save_and_close_article() {
		js_click(save_close_button, 2);
		waitForElementUntilTextPresent(panel_title, menu.Articles.toString(), 10);
	}

	/**
	 * Will Save the article
	 */
	public void save_article() {
		js_click(save_button, 2);
	}

	/**
	 * Will Close the article
	 */
	public void close_article() {
		js_click(close_button, 2);
		waitForElementUntilTextPresent(panel_title, menu.Articles.toString(), 10);
	}

	/**
	 * Retrieve the article setting select fields by using its label name Take
	 * Variable arguments as parameter. If the article consist two different
	 * fields with same label name, then the based on the given second argument
	 * the WebElement will be returned.
	 * 
	 * For instance, want to retrieve the second field have to pass 2 as second
	 * argument
	 * 
	 * @param label_name
	 * @return
	 */
	public WebElement get_text_field_element_by_label(String... label_name) {
		By by_string = By.xpath("//label[text()='" + label_name[0] + "']/parent::div/following-sibling::div/input");
		List<WebElement> element_list = get_webelements_list(by_string);
		return label_name.length > 1 ? element_list.get(Integer.parseInt(label_name[1]) - 1) : element_list.get(0);
	}

	/**
	 * Retrieve the article setting select fields by using its label name Take
	 * Variable arguments as parameter. If the article consist two different
	 * fields with same label name, then the based on the given second argument
	 * the WebElement will be returned.
	 * 
	 * For instance, want to retrieve the second field have to pass 2 as second
	 * argument
	 * 
	 * @param label_name
	 * @return
	 */
	public WebElement get_textarea_field_element_by_label(String... label_name) {
		By by_string = By.xpath("//label[text()='" + label_name[0] + "']/parent::div/following-sibling::div/textarea");
		List<WebElement> element_list = get_webelements_list(by_string);
		return label_name.length > 1 ? element_list.get(Integer.parseInt(label_name[1]) - 1) : element_list.get(0);
	}

	/**
	 * Retrieve the article input fields by using its key name. Take variable
	 * arguments as parameter. If the article consist two different fields with
	 * same key name, then the based on the given second argument the WebElement
	 * will be returned.
	 * 
	 * For instance, want to retrieve the second field have to pass 2 as second
	 * argument
	 * 
	 * @param label_name
	 * @return
	 */
	public WebElement get_input_field_element_by_key_name(String... key_name) {
		By by_string = By.xpath(
				"//input[@value='" + key_name[0] + "']/parent::div/parent::div/following-sibling::div/div/input");
		List<WebElement> element_list = get_webelements_list(by_string);
		return key_name.length > 1 ? element_list.get(Integer.parseInt(key_name[1]) - 1) : element_list.get(0);
	}

	/**
	 * Retrieve the article setting select fields by using its label name Take
	 * Variable arguments as parameter. If the article consist two different
	 * fields with same label name, then the based on the given second argument
	 * the WebElement will be returned.
	 * 
	 * For instance, want to retrieve the second field have to pass 2 as second
	 * argument
	 * 
	 * @param label_name
	 * @return
	 */
	public WebElement get_dropdown_field_element_by_label(String... label_name) {
		By by_string = By.xpath("//label[text()='" + label_name[0] + "']/parent::div/following-sibling::div/select");
		List<WebElement> element_list = get_webelements_list(by_string);
		return label_name.length > 1 ? element_list.get(Integer.parseInt(label_name[1]) - 1) : element_list.get(0);
	}

	/**
	 * Click the respective given field by the given label name on Joomla page
	 * 
	 * @param label_name
	 */
	public void click_field_element_by_label(String... label_name) {
		By by_string = By.xpath("//label[text()='" + label_name[0] + "']/parent::div/following-sibling::div//input");
		List<WebElement> element_list = get_webelements_list(by_string);
		WebElement ele = label_name.length > 1 ? element_list.get(Integer.parseInt(label_name[1]) - 1)
				: element_list.get(0);
		js_click(ele);
	}

	/**
	 * Return the List of sub categories which defined in the Joomla admin on
	 * Category page. Parameter will define the Category name.
	 * 
	 * @param label_name
	 * @return
	 */
	public LinkedList<String> get_sub_category_list_from_category_page(String label_name) {
		By by_string = By.xpath("//label[text()='" + label_name + "']/parent::div/following-sibling::div/select");
		List<WebElement> element_list = get_webelements_list(by_string);
		LinkedList<String> sub_category_list = new LinkedList<String>();
		for (WebElement ele : element_list) {
			sub_category_list.add(get_first_selected_option(ele, "text", 30));
		}
		return sub_category_list;
	}

	/**
	 * Search and open an article
	 * 
	 * @param article_name
	 */
	public void search_for_article(String article_name) {
		textbox_by_js(search_textbox, "enter", article_name, 2);
		submit(search_textbox, 2);
		elementIsClickable(article_list, 2);
		List<WebElement> ele_list = get_webelements_list(article_list);
		for (WebElement ele : ele_list) {
			if (ele.getText().equalsIgnoreCase(article_name)) {
				js_click(ele);
				break;
			}
		}
		waitForElement(save_close_button, 2);
	}

	/**
	 * Navigate to Article page
	 * 
	 */
	public void goToArticlePage() {
		js_click(content_menu_toggle, 2);
		js_click(article_submenu_toggle, 2);
		waitForElementUntilTextPresent(panel_title, menu.Articles.toString(), 15);
	}

	/**
	 * Returns a Singleton instance
	 * 
	 * @return JoomlaConfigPage
	 */
	public static JoomlaConfigPage getInstance() {
		return joomla_instance;
	}

	/**
	 * Login to the Joomla admin
	 * 
	 * @param user_email
	 * @param user_password
	 */
	public void log_in(String user_email, String user_password) {
		if (elementVisibility(username, 3)) {
			textbox(username, "enter", user_email, 1);
			textbox(password, "enter", user_password, 1);
			js_click(login_btn, 1);
			waitForElementPresent(panel_title, 30);
		}
	}

	/**
	 * To create a new drop down section on last position by click on Add
	 * button. It's written in generic way to add any section depends on the
	 * given label name.
	 * 
	 * @author mperumal
	 * @param label_name
	 */
	public void add_new_drop_down_section_by_label(String label_name, String dropdown_text) {
		By locator = By.xpath("(//label[text()='" + label_name
				+ "'])[last()]//ancestor::div[starts-with(@id,'cck1r_forms')]/aside/div[2]/span");
		js_click(locator, 30);
		select_option_dropdown_field_element_by_label(label_name, dropdown_text);
	}

	/**
	 * To create a new text field section on last position by click on Add
	 * button. It's written in generic way to add any section depends on the
	 * given label name.
	 * 
	 * @author mperumal
	 * @param label_name
	 */
	public void add_new_text_field_section_by_label(String label_name, String text_value) {
		By locator = By.xpath("(//label[text()='" + label_name
				+ "'])[last()]//ancestor::div[starts-with(@id,'cck1r_forms')]/aside/div[2]/span");
		js_click(locator, 30);
		enter_text_field_element_by_label(label_name, text_value);
	}

	/**
	 * To create a new UniNav section on last position by click on Add button.
	 * Label name is a reference name to find which section the new fields has
	 * to be add in the Admin
	 * 
	 * @author mperumal
	 * @param label_name
	 */
	public void add_new_uninav_field_section_by_label(String label_name) {
		By locator = By.xpath("(//label[text()='" + label_name
				+ "'])[last()]//parent::div/following-sibling::div//div[contains(@class,'cck_form_group_x_last')]/aside/div[2]/span");
		js_click(locator, 30);
	}

	/**
	 * To delete the added UniNav section by click on Delete button. Label name
	 * is a reference name to find which section the new fields has to be delete
	 * from the Admin. On default, it will delete the last one
	 * 
	 * @author mperumal
	 * @param label_name
	 */
	public void delete_added_uninav_field_section_by_label(String label_name) {
		By locator = By.xpath("(//label[text()='" + label_name
				+ "'])[last()]//parent::div/following-sibling::div//div[contains(@class,'cck_form_group_x_last')]/aside/div[1]/span");
		js_click(locator, 30);
	}

	/**
	 * To add the PCH property on the UniNav section.
	 * 
	 * @param property_name
	 * @param title_name
	 * @param prop_url
	 * @param property_css_name
	 * @param property_logo_path
	 */
	public void add_pch_property_on_uninav(String property_name, String title_name, String prop_url,
			String property_css_name, String property_logo_path) {
		String section_title = "PCH Property";
		add_new_uninav_field_section_by_label(section_title);
		enter_uninav_text_field_element_by_label(section_title, "Property", property_name);
		enter_uninav_text_field_element_by_label(section_title, "Title", title_name);
		enter_uninav_text_field_element_by_label(section_title, "Url", prop_url);
		enter_uninav_text_field_element_by_label(section_title, "Css Class", property_css_name);
		enter_uninav_text_field_element_by_label(section_title, "Logo", property_logo_path);
	}

	/**
	 * To add the PCH Extra Link on the UniNav section.
	 * 
	 * @param device_type
	 * @param title_name
	 * @param extra_link_url
	 */
	public void add_pch_extra_link_on_uninav(String device_type, String title_name, String extra_link_url) {
		String section_title = "PCH Extra Link";
		add_new_uninav_field_section_by_label(section_title);
		select_uninav_setting_dropdown_field_by_label(section_title, "Device", device_type);
		enter_uninav_text_field_element_by_label(section_title, "Title", title_name);
		enter_uninav_text_field_element_by_label(section_title, "Url", extra_link_url);
	}

	/**
	 * To delete the newly added drop down section by click on Delete button. On
	 * default it will delete the last added section. It's written in generic
	 * way to delete any section depends on the given label name.
	 * 
	 * @author mperumal
	 * @param label_name
	 */
	public void delete_last_drop_down_section_by_label(String label_name) {
		By locator = By.xpath("(//label[text()='" + label_name
				+ "'])[last()]//ancestor::div[starts-with(@id,'cck1r_forms')]/aside/div[1]/span");
		js_click(locator, 30);
	}

	/**
	 * Enter the value to the respective field by the given label name. It takes
	 * 3 parameter. 1st one is label name which field to enter, 2nd value to
	 * enter and 3rd defines the position. 3rd is variable argument, if it
	 * doesn't given on default it will take last field of the given type on the
	 * page
	 * 
	 * @param label_name
	 * @param value
	 * @param position
	 * @author mperumal
	 */
	public void enter_text_field_element_by_label(String label_name, String value, int... position) {
		By locator = By.xpath("//label[text()='" + label_name + "']/parent::div/following-sibling::div/input");
		if (position.length > 0) {
			List<WebElement> element_list = get_webelements_list(locator);
			textbox_by_js(element_list.get(position[0] - 1), "enter", value);
		} else {
			locator = By
					.xpath("(//label[text()='" + label_name + "'])[last()]/parent::div/following-sibling::div/input");
			textbox_by_js(locator, "enter", value, 30);
		}
	}

	/**
	 * Enter the value to the respective field by the given label name from the
	 * given parent section name. It takes 4 parameter. 1st one is section name
	 * under which field will be choose, 2nd parameter is the label name to
	 * select the field, 3rd value text going to enter and 4th defines the
	 * position. 4th is variable argument, if it doesn't given on default it
	 * will take last field of the given type on the page
	 * 
	 * @param section_name
	 * @param label_name
	 * @param value
	 * @param position
	 * @author mperumal
	 */
	public void enter_uninav_text_field_element_by_label(String section_name, String label_name, String value,
			int... position) {
		By locator = By
				.xpath("(//label[text()='" + section_name + "']/parent::div/following-sibling::div//label[text()='"
						+ label_name + "'])[last()]/parent::div/following-sibling::div/input");
		if (position.length > 0) {
			List<WebElement> element_list = get_webelements_list(locator);
			textbox(element_list.get(position[0] - 1), "enter", value, 30);
		} else {
			locator = By
					.xpath("(//label[text()='" + section_name + "']/parent::div/following-sibling::div//label[text()='"
							+ label_name + "'])[last()]/parent::div/following-sibling::div/input");
			textbox(locator, "enter", value, 30);
		}
	}

	/**
	 * Enter the value to the respective field by the given key name. It takes 3
	 * parameter. 1st one is key name which field value has to enter, 2nd
	 * parameter is the value to enter and 3rd defines the position. 3rd is
	 * variable argument, if it doesn't given on default it will take first
	 * field of the given type on the page
	 * 
	 * @param key_name
	 * @param value
	 * @param position
	 * @author mperumal
	 */
	public void enter_input_field_element_by_key_name(String key_name, String value, int... position) {
		By locator = By
				.xpath("//input[@value='" + key_name + "']/parent::div/parent::div/following-sibling::div/div/input");
		if (position.length > 0) {
			List<WebElement> element_list = get_webelements_list(locator);
			textbox(element_list.get(position[0] - 1), "enter", value, 30);
		} else {
			locator = By.xpath(
					"(//input[@value='" + key_name + "'])/parent::div/parent::div/following-sibling::div/div/input");
			textbox(locator, "enter", value, 30);
		}
	}

	/**
	 * Enter the value to the respective field by the given key name. It takes 3
	 * parameter. 1st one is key name which field value has to enter, 2nd
	 * parameter is the value to enter and 3rd defines the position. 3rd is
	 * variable argument, if it doesn't given on default it will take first
	 * field of the given type on the page
	 * 
	 * @param key_name
	 * @param value
	 * @param position
	 * @author mperumal
	 */
	public void enter_input_field_element_by_key_name_via_js(String key_name, String value, int... position) {
		By locator = By
				.xpath("//input[@value='" + key_name + "']/parent::div/parent::div/following-sibling::div/div/input");
		if (position.length > 0) {
			List<WebElement> element_list = get_webelements_list(locator);
			textbox_by_js(element_list.get(position[0] - 1), "enter", value);
		} else {
			locator = By.xpath(
					"(//input[@value='" + key_name + "'])/parent::div/parent::div/following-sibling::div/div/input");
			textbox_by_js(locator, "enter", value, 30);
		}
	}

	/**
	 * Select the value to the respective field by the given label name. It
	 * takes 3 parameter. 1st one is label name which field to enter, 2nd value
	 * to enter and 3rd defines the position. 3rd is variable argument, if it
	 * doesn't given on default it will take last field of the given type on the
	 * page
	 * 
	 * @author mperumal
	 * @param label_name
	 * @return
	 */
	public void select_dropdown_field_element_by_label(String label_name, String value, int... position) {
		By locator = By.xpath("//label[text()='" + label_name + "']/parent::div/following-sibling::div/select");

		if (position.length > 0) {
			List<WebElement> element_list = get_webelements_list(locator);
			select_visible_text_by_js(element_list.get(position[0] - 1), value, 30);
		} else {
			locator = By
					.xpath("(//label[text()='" + label_name + "'])[last()]/parent::div/following-sibling::div/select");
			select_visible_text_by_js(locator, value, 30);
		}
	}

	/**
	 * Select the value of the respective field by the given label name from the
	 * given section name(. It takes 4 parameter. 1st parameter defines from
	 * which section field has to be choose, 2nd one is label name which field
	 * to choose, 3rd value to enter and 4th defines the position. 4th is
	 * variable argument, if it doesn't given on default it will take last field
	 * of the given type on the page
	 * 
	 * @author mperumal
	 * @param label_name
	 * @return
	 */
	public void select_uninav_setting_dropdown_field_by_label(String section_name, String label_name, String value,
			int... position) {
		By locator = By
				.xpath("//label[text()='" + section_name + "']/parent::div/following-sibling::div//label[text()='"
						+ label_name + "']/parent::div/following-sibling::div/select");
		if (position.length > 0) {
			List<WebElement> element_list = get_webelements_list(locator);
			select_visible_text_by_js(element_list.get(position[0] - 1), value, 30);
		} else {
			locator = By
					.xpath("(//label[text()='" + section_name + "']/parent::div/following-sibling::div//label[text()='"
							+ label_name + "'])[last()]/parent::div/following-sibling::div/select");
			select_visible_text_by_js(locator, value, 30);
		}
	}

	/**
	 * Select the value based on the options tag to the respective field by the
	 * given label name. It takes 3 parameter. 1st one is label name which field
	 * to enter, 2nd value to enter and 3rd defines the position. 3rd is
	 * variable argument, if it doesn't given on default it will take last field
	 * of the given type on the page
	 * 
	 * @author mperumal
	 * @param label_name
	 * @return
	 */
	public void select_option_dropdown_field_element_by_label(String label_name, String value, int... position) {
		By locator = By.xpath("//label[text()='" + label_name + "']/parent::div/following-sibling::div/select");

		if (position.length > 0) {
			List<WebElement> element_list = get_webelements_list(locator);
			selectByOption(element_list.get(position[0] - 1), value, 30);
		} else {
			locator = By
					.xpath("(//label[text()='" + label_name + "'])[last()]/parent::div/following-sibling::div/select");
			selectByOption(locator, value, 30);
		}
	}

	/**
	 * Retrieve the Tile Category element from the page and select the Item
	 * story type either Article or Video by the given parameter.
	 * 
	 * It takes 3 parameters. 3rd parameter is the variable argument. If it
	 * defines the position it will consider that Tile Category for
	 * modification. If it doesn't on default it takes last section. 1st
	 * parameter says which item number should take from the chose Tile Category
	 * section. It automatically choose the other story type. If its has type
	 * has Article it choose Video and Vice-versa.
	 * 
	 * It return boolean value. True for Video selection and False for Article
	 * selection
	 * 
	 * @author mperumal
	 */
	public boolean choose_tile_category_story_types(String item_no, String... tile_category_position) {
		String story_type;
		By locator;
		if (tile_category_position.length > 0) {
			locator = By.xpath("(//div[starts-with(@id,'cck1r_forms_tiles')])[" + tile_category_position
					+ "]//div[contains(@class,'cck_radio')][" + item_no + "]//input[@checked='checked']");
			story_type = getAttribute(locator, "value").equals("1") ? "2" : "1";
			locator = By.xpath("(//div[starts-with(@id,'cck1r_forms_tiles')])[" + tile_category_position
					+ "]//div[contains(@class,'cck_radio')][" + item_no + "]//input[" + story_type + "]");
			js_click(locator, 30);
		} else {
			locator = By
					.xpath("(//div[starts-with(@id,'cck1r_forms_tiles')])[last()]//div[contains(@class,'cck_radio')]["
							+ item_no + "]//input[@checked='checked']");
			story_type = getAttribute(locator, "value").equals("1") ? "2" : "1";
			locator = By
					.xpath("(//div[starts-with(@id,'cck1r_forms_tiles')])[last()]//div[contains(@class,'cck_radio')]["
							+ item_no + "]//input[" + story_type + "]");
			js_click(locator, 30);
		}
		return story_type.equals("2") ? true : false;
	}

	/**
	 * Retrieve the Tile Category element from the page and select the Item
	 * story type either Article or Video by the given parameter.
	 * 
	 * It takes 2 parameters. 1st parameter defines the OurPick type position on
	 * page to do modification. It automatically choose the other story type. If
	 * its has type has Article it choose Video and Vice-versa.
	 * 
	 * It return boolean value. True for Video selection and False for Article
	 * selection
	 * 
	 * @author mperumal
	 */
	public boolean choose_ourpick_category_story_types(int ourpick_category_position) {
		By locator = By.xpath("(//div[starts-with(@id,'cck1r_forms_our_picks')])[" + ourpick_category_position
				+ "]//input[@checked='checked']");
		String story_type = getAttribute(locator, "value").equals("1") ? "2" : "1";
		locator = By.xpath("(//div[starts-with(@id,'cck1r_forms_our_picks')])[" + ourpick_category_position
				+ "]//input[" + story_type + "]");
		js_click(locator, 30);
		return story_type.equals("2") ? true : false;
	}

	public void select_search_category(String category) throws Exception {
		WebElement e = js_click(searchtools, 30);
		moveToElement(e);
		mouseHoverJScript(js_click(selectcategory, 30));
		sleepFor(10);
		action_click();
		textbox(categorytextbox, "enter", category, 10);
		js_click(categorylistoption, 10);
	}

	public void search_articles(String text) {
		textbox(search_textbox, "enter", text, 2);
		submit(search_textbox, 2);
		elementIsClickable(article_list, 2);
	}

	/**
	 * Get GS Id from the Custom HTML.
	 * 
	 * @param position
	 * @return
	 */
	public String get_gs_id(String device) {
		String custom_html = get_textarea_field_element_by_label(device).getText();
		custom_html = custom_html.substring(custom_html.indexOf("gsid") + 5, custom_html.length()).trim()
				.replaceAll("'", "");
		custom_html = custom_html.substring(0, custom_html.indexOf("}")).trim();
		log.info("GS Id: " + custom_html);
		return custom_html;
	}

	/**
	 * Enter the given key and value. it clear the key & values from the config
	 * article in the admin
	 * 
	 * @param key_name
	 * @param value
	 * @author npoojari
	 */

	public void clear_input_field_element_by_key_name(String key_name) {
		By locator_key = By.xpath("//input[@value='" + key_name + "']");
		By locator_value = By
				.xpath("(//input[@value='" + key_name + "'])/parent::div/parent::div/following-sibling::div/div/input");
		textbox(locator_key, "clear", "", 30);
		textbox(locator_value, "clear", "", 30);
	}

	/**
	 * Enter the key and value from the config. it will add the given key and
	 * value to the config article in the admin
	 * 
	 * @param key_name
	 * @param value
	 * @author npoojari
	 */

	public void enter_key_value_elements_config(String Key_name, String value) {
		By locator_key = By.xpath("//*[contains(@id , 'config_key')  and (@value= '')]");
		By locator_value = By.xpath("//*[contains(@id , 'config_value')  and (@value= '')]");
		textbox(locator_key, "enter", Key_name, 30);
		textbox(locator_value, "enter", value, 30);
	}

	/**
	 * Retrieve the Check field based on label name. Multiple fields are present
	 * it will pick based on the given position argument. If Status argument is
	 * 1, it will check the field and vice-versa.
	 * 
	 * @param label_name
	 * @param status
	 * @param position
	 */
	public void check_field_based_on_label_name(String label_name, int status, int position) {
		By locator = By.xpath("//label[text()='" + label_name + "']/parent::div/following-sibling::div//input");
		List<WebElement> element_list = get_webelements_list(locator);
		WebElement ele = element_list.size() > 1 ? element_list.get(position - 1) : element_list.get(0);
		String field_status = ele.getAttribute("checked");
		System.out.println(field_status);
		if (status == 1) {
			if (field_status == null || !field_status.equalsIgnoreCase("true"))
				js_click(ele);
		} else if (status == 0) {
			if (field_status.equalsIgnoreCase("true"))
				js_click(ele);
		}
	}
}
