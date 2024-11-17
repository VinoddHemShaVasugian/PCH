package com.pch.automation.pages.jm;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * @author mperumal Contains page functions for the FP Joomla login page
 * 
 */
public class ArticlePage extends PageObject {

	private final By joomlaIcon = By.cssSelector("span.icon-joomla");
	private final By articlesLink = By.linkText("Articles");
	private final By articleSearchField = By.id("filter_search");
	private final By articleSearchIcon = By.cssSelector("button.btn.hasTooltip[type='submit']");
	private final By articleList = By.cssSelector("td.has-context >div >a");
	private final By saveCloseButton = By.cssSelector("#toolbar-save>button");
	private final By saveButton = By.cssSelector("#toolbar-apply>button");
	private final By closeButton = By.cssSelector("#toolbar-cancel>button");
	private final By publishState = By.id("art_state");
	private final By confirmationMessge = By.cssSelector("div.alert-message");

	/**
	 * Unpublish the article
	 * 
	 */
	public void unPublishArticle() {
		selectFromDropdown(element(publishState), "Unpublished");
		clickOn(element(saveCloseButton));
		waitForRenderedElementsToBePresent(confirmationMessge);
	}

	/**
	 * Publish the article
	 */
	public void publishArticle() {
		selectFromDropdown(element(publishState), "Published");
		clickOn(element(saveCloseButton));
		waitForRenderedElementsToBePresent(confirmationMessge);
	}

	/**
	 * Will Save and Close the article
	 */
	public void saveAndCloseArticle() {
		clickOn(element(saveCloseButton));
		waitForRenderedElementsToBePresent(articleSearchField);
	}

	/**
	 * Will Save the article
	 */
	public void saveArticle() {
		clickOn(element(saveButton));
		waitForRenderedElementsToBePresent(confirmationMessge);
	}

	/**
	 * Will Close the article
	 */
	public void closeArticle() {
		clickOn(element(closeButton));
		waitForRenderedElementsToBePresent(articleSearchField);
	}

	/**
	 * Navigate to the Article page
	 */
	public void goToArticlePage() {
		clickOn(element(joomlaIcon));
		clickOn(element(articlesLink));
		waitForRenderedElementsToBePresent(articleSearchField);
	}

	/**
	 * Search and open an article
	 * 
	 * @param articleName
	 */
	public void searchForArticle(String articleName) {
		this.goToArticlePage();
		typeInto(element(articleSearchField), articleName);
		clickOn(element(articleSearchIcon));
		waitForRenderedElementsToBePresent(articleList);
		List<WebElementFacade> ele_list = findAll(articleList);
		for (WebElement ele : ele_list) {
			if (ele.getText().equalsIgnoreCase(articleName)) {
				clickOn(ele);
				break;
			}
		}
		waitForRenderedElementsToBePresent(saveButton);
	}

	/**
	 * Retrieve the value based on the given label name from the article . Ex:
	 * Consider an article - Tokens / First Search
	 * 
	 * @param labelName - Value need to be retrieve. Ex: Description
	 * @return the value for the given label name of the article
	 * 
	 * @author vsankar
	 * 
	 * 
	 */
	public String getValueByLabelName(String... labelName) {
		By by = By.xpath("//label[text()='" + labelName[0] + "']/parent::div/following-sibling::div/textarea");
		if (!containsElements(by)) {
			by = By.xpath("//label[text()='" + labelName[0] + "']/parent::div/following-sibling::div/input");
		}
		List<WebElementFacade> elementList = findAll(by);
		return element(labelName.length > 1 ? elementList.get(Integer.parseInt(labelName[1]) - 1) : elementList.get(0))
				.getTextValue().trim();
	}

	/**
	 * Modify/Update the value based on the given label name of the article. Ex:
	 * Consider an article - Tokens / First Search
	 * 
	 * @param labelName - Field need to be modify/update. Ex: Description
	 * @param value     - Replacing text
	 * 
	 * @author mperumal
	 * 
	 * 
	 */
	public void setValueByLabelName(String labelName, String value) {
		By by = By.xpath("//label[text()='" + labelName + "']/parent::div/following-sibling::div/textarea");
		if (!containsElements(by)) {
			by = By.xpath("//label[text()='" + labelName + "']/parent::div/following-sibling::div/input");
		}
		typeInto(element(by), value);
		this.saveArticle();
	}

	/**
	 * Retrieve the value based on the given key name from the article . Ex:
	 * Consider an article - config-sitepagesearch6
	 * 
	 * @param keyName - Value need to be retrieve. Ex: samsBanner
	 * @return the value for the given key name of the article
	 * 
	 * @author mperumal
	 * 
	 * 
	 */
	public String getValueByKeyName(String keyName) {
		By by = By.xpath("//input[@value='" + keyName + "']/parent::div/parent::div/following-sibling::div/div/input");
		return element(by).getTextValue().trim();
	}

	/**
	 * Modify/Update the value based on the given key name of the article. Ex:
	 * Consider an article - Tconfig-sitepagesearch6
	 * 
	 * @param keyName - Field need to be modify/update. Ex: samsBanner
	 * @param value   - Replacing text
	 * 
	 * @author mperumal
	 * 
	 * 
	 */
	public void setValueByKeyName(String keyName, String value) {
		By by = By
				.xpath("//input[@value='" + keyName + "']/parent::div/parent::div/following-sibling::div/div/textarea");
		if (!containsElements(by)) {
			by = By.xpath("//input[@value='" + keyName + "']/parent::div/parent::div/following-sibling::div/div/input");
		}
		typeInto(element(by), value);
		this.saveArticle();
	}

	/**
	 * Retrieve the first selected value for the given drop down label name of the
	 * article. Ex: Consider an article - Tokens / First Search
	 * 
	 * @param labelName - Value need to be retrieve. Ex: Segments Included
	 * @return the first selected value for the given label name of the article
	 * 
	 * @author mperumal
	 * 
	 * 
	 */
	public String getFirstSelectedValueOfDropdownByLabelName(String labelName) {
		By by = By.xpath("//label[text()='" + labelName + "']/parent::div/following-sibling::div/select");
		return getSelectedLabelFrom(element(by));
	}

	/**
	 * Retrieve the first selected value for the given drop down label name of the
	 * article based on the given position of the page. Ex: Consider an article -
	 * Tokens / First Search
	 * 
	 * @param labelName - Value need to be retrieve. Ex: Segments Included
	 * @return the first selected value for the given label name of the article
	 * 
	 * @author mperumal
	 * 
	 * 
	 */
	public String getFirstSelectedValueOfDropdownByLabelName(String labelName, String position) {
		By by = By.xpath("//div[contains(@class,'cck_form cck_form_group_x ')][" + position + "]//label[text()='"
				+ labelName + "']/parent::div/following-sibling::div/select");
		return getSelectedLabelFrom(element(by));
	}

	/**
	 * Retrieve all the selected value for the given drop down label name of the
	 * article. Ex: Consider an article - Tokens / First Search
	 * 
	 * @param labelName - Value need to be retrieve. Ex: Segments Included
	 * @return all the selected value for the given label name of the article
	 * 
	 * @author mperumal
	 * 
	 * 
	 */
	public Set<String> getAllSelectedValueOfDropdownByLabelName(String labelName) {
		By by = By.xpath("//label[text()='" + labelName + "']/parent::div/following-sibling::div/select");
		return getSelectedOptionLabelsFrom(element(by));
	}

	/**
	 * Select the value for the given drop down based on the label name of the
	 * article. Ex: Consider an article - Tokens / First Search
	 * 
	 * @param labelName - Drop down need to be select. Ex: Segments Included
	 * @param value     - Value to be select
	 * @author mperumal
	 * 
	 * 
	 */
	public void selectOneValueOnDropdownByLabelName(String labelName, String value) {
		By by = By.xpath("//label[text()='" + labelName + "']/parent::div/following-sibling::div/select");
		selectFromDropdown(element(by), value);
		this.saveArticle();
	}

	/**
	 * Get GS Id from the Custom HTML for the given device
	 * 
	 * @param device
	 * @return
	 */
	public String getGsId(String device) {
		String custom_html = getValueByLabelName(device);
		custom_html = custom_html.substring(custom_html.indexOf("gsid") + 5, custom_html.length()).trim()
				.replaceAll("'", "");
		custom_html = custom_html.substring(0, custom_html.indexOf("}")).trim();
		return custom_html;
	}

	/**
	 * Retrieve the article input fields by using its key name. Take variable
	 * arguments as parameter. If the article consist two different fields with same
	 * key name, then the based on the given second argument the WebElement will be
	 * returned.
	 * 
	 * For instance, want to retrieve the second field have to pass 2 as second
	 * argument
	 * 
	 * @param label_name
	 * @author vsankar
	 * @return
	 */
	public WebElement getInputFieldElementByKeyName(String... keyName) {
		By byString = By
				.xpath("//input[@value='" + keyName[0] + "']/parent::div/parent::div/following-sibling::div/div/input");

		List<WebElementFacade> elementList = findAll(byString);
		return keyName.length > 1 ? elementList.get(Integer.parseInt(keyName[1]) - 1) : elementList.get(0);
	}

}
