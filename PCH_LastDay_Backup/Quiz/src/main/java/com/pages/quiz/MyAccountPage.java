package com.pages.quiz;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

/**
 * Contains Accounts/Token history page functions.
 * 
 * @author vsankar
 */

public class MyAccountPage extends PageObject {

	private final By tokenHistoryDescription = By.xpath("//div[@class='th_desc']");
	private final By myInfoLink = By.xpath("//span[contains(.,'My Info')]");
	private final By dobMonth = By.name("MN");
	private final By dobDay = By.name("DY");
	private final By dobYear = By.name("YR");
	private final By myInfoUpdateBtn = By.id("sub-myaccount-btn");
	private final By myInfoUpdateSuccessfullyMsg = By.cssSelector("div.submit-container div#success_message");
	private final By tokenTransaction = By.cssSelector("div.th_desc");
	private final By tokenHistory = By.cssSelector("#tabtoken > span");
	private final By rewards = By.xpath("//div[@id='content']/div/ul/li[3]/a/span");
	private final By manageOrders = By.cssSelector("a.btn-payonline-trackorder > div");

	/**
	 * To verify the landing on My Account page
	 */
	public boolean verifyMyAccount() {
		return isElementVisible(myInfoUpdateBtn) && isElementVisible(tokenHistory) && isElementVisible(rewards)
				&& isElementVisible(manageOrders);
	}

	/**
	 * Verify the 1st token transaction in my account page and return the result
	 */
	public boolean verifyFirstTokenTransactionWithText(String Text) throws Exception {
		waitForRenderedElements(tokenTransaction);
		boolean status = element(tokenTransaction).getText().contains(Text);
		return status;
	}

	/**
	 * Update the profile info of a User
	 */
	public void clickMyInfoUpdate() {
		clickOn(element(myInfoUpdateBtn));
		waitForRenderedElements(myInfoUpdateSuccessfullyMsg);
	}

	/**
	 * To get the token transaction description, token amount and their no. of
	 * occurrences for the user
	 * 
	 * @param description
	 * @param token_amount
	 * @param no_of_occurance
	 * @return
	 */
	public int verifyTokenTransactionsDetails(String description, String tokenAmount, int numberOfOccurrences) {
		int numberOfTimesTokenTransactionMsg = 0;
		waitABit(5000);
		waitForRenderedElements(tokenHistoryDescription);
		List<WebElementFacade> descList = findAll(tokenHistoryDescription);
		for (int count = 1; count <= descList.size(); count = count + 1) {
			withAction().moveToElement((WebElement) descList.get(count - 1)).perform();
			waitABit(5);
			descList = findAll(tokenHistoryDescription);
			if (descList.get(count - 1).getText().contains(description)) {
				numberOfTimesTokenTransactionMsg++;
				WebElement tokenElement = element(
						By.xpath("//article[" + count + "]//div[@class='date_tokens']/span[2]"));
				String actualTokenAmount = tokenElement.getText().replace("+", "").replace(",", "");
				if (tokenAmount.equals(actualTokenAmount)) {
					if (numberOfOccurrences > numberOfTimesTokenTransactionMsg) {
						continue;
					} else {
						return numberOfTimesTokenTransactionMsg;
					}
				} else {
					return numberOfTimesTokenTransactionMsg;
				}
			}
		}
		return numberOfTimesTokenTransactionMsg;
	}

	/**
	 * To get the token transaction description and their no. of occurrences for the
	 * user
	 * 
	 * @param description
	 * @param token_amount
	 * @param no_of_occurance
	 * @return
	 */
	public boolean verifyTokenTransactionsDescription(String description, int no_of_occurrences) {
		List<WebElementFacade> desc_list = findAll(tokenHistoryDescription);
		for (int count = 1; count <= desc_list.size(); count = count + 1) {
			withAction().moveToElement((WebElement) desc_list.get(count - 1)).perform();
			waitFor(2);
			desc_list = findAll(tokenHistoryDescription);
			if (desc_list.get(count - 1).getText().contains(description)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * To get the token transaction details based on the given parameter
	 * 
	 * @param description
	 * @param token_amount
	 * @param no_of_occurance
	 * @return
	 */
	public String getTokenTransactionAmount(int recordRowNum) {
		By tokenTransactionAmount = By.xpath("//article[" + recordRowNum + "]//div[@class='date_tokens']/span[2]");
		return element(tokenTransactionAmount).getText().replace("+", "").replace(",", "");
	}

	/**
	 * Click My Info link
	 */
	public void clickMyInfoLink() {
		clickOn(element(myInfoLink));
	}

	/**
	 * To Modify DOB
	 */
	public void modifyDOB(String date, String month, String year) {
		selectFromDropdown(element(dobMonth), month);
		selectFromDropdown(element(dobDay), date);
		selectFromDropdown(element(dobYear), year);
	}
}
