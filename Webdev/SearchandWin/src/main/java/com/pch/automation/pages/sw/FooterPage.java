package com.pch.automation.pages.sw;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.PageObject;

/**
 * Page objects and methods for Footer
 *
 * @author vsankar
 */
public class FooterPage extends PageObject {

	/**
	 * Instantiates a Footer page.
	 *
	 * @param driver
	 */
	public FooterPage(WebDriver driver) {
		super(driver);
	}

	private final By bbbLogo = By.cssSelector("img[alt='Publishers Clearing House LLC BBB Business Review']");
	private final By trusteLogo = By.cssSelector("img[alt='TRUSTe']");
	private final By copyrightText = By.cssSelector("div.copyright__notice");
	private final By footerLinks = By.cssSelector("li.sandw-menu__item");

	/**
	 * Get the Copyright text
	 * 
	 * @return Copyright value
	 * @author vsankar
	 */
	public String getCopyrightText() {
		return element(copyrightText).getText().replace("\n", "").replace(" ", "");
	}

	/**
	 * Click on BBB logo
	 * 
	 * @author vsankar
	 */
	public void clickBBBLogo() {
		clickOn(element(bbbLogo));
	}

	/**
	 * Click on Truste logo
	 * 
	 * @author vsankar
	 */
	public void clickTrusteLogo() {
		clickOn(element(trusteLogo));
	}

	/**
	 * Get landing page URL's from the Footer menu
	 * 
	 * @return Footer links
	 * @author vsankar
	 */
	public LinkedList<String> getFooterMenuLandingUrls() {
		List<WebElement> elementList = getDriver().findElements(footerLinks);
		LinkedList<String> landingUrls = new LinkedList<String>();
		for (WebElement ele : elementList) {
			landingUrls.add(ele.getAttribute("href"));
		}
		return landingUrls;
	}
}