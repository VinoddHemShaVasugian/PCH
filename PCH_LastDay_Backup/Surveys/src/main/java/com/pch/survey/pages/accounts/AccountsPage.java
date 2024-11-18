package com.pch.survey.pages.accounts;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.pch.survey.centralservices.Registrations;
import com.pch.survey.pages.PageObject;

public class AccountsPage extends PageObject {

	private final By accountIcon = By.xpath("//a[text()='Account Icon']");
	private final By accIcon = By.cssSelector("a.uninav__component-link");
	private final By myAccount = By.xpath("//a[text()='MY ACCOUNT']");
	private final By password = By.cssSelector("#password_main");
	private final By signIn = By.cssSelector("div.signInBtn");
	private final By myAccountPage = By.xpath("//h1[text()='My PCH Account']");
	private final By desc = By.xpath("//div[@aria-label='item']//span[@class='reward-description']");
	private final By amt = By.xpath("//div[@aria-label='item']//span[@class='token-amount']");
	private final By tokenHistory = By.xpath("//div[@aria-label='item']/div[@class='row token-reward']");
	private final By completeRegLink = By.cssSelector(".uninav__button");
	private final By clickMyAccount = By.xpath("//a[@class='uninav__top-bar__link uninav__top-bar__link--my-account']");

	public AccountsPage(WebDriver driver) {
		super(driver);
	}

	public void navigateToMyAccountPage() {
		waitUntilElementIsClickable(driver.findElement(accountIcon));
		driver.findElement(accountIcon).click();
		driver.findElement(myAccount).click();
		if (driver.getTitle().contains("Login")) {
			driver.findElement(password).sendKeys(Registrations.getPassword());
			driver.findElement(signIn).click();
		}
	}

	public boolean verifyMyAccountPage() {
		try {
			return driver.findElement(myAccountPage).isDisplayed();
		} catch (StaleElementReferenceException stale) {
			return driver.findElement(myAccountPage).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickMyAccount() {
		waitSeconds(3);
		driver.findElement(clickMyAccount).click();
	}

	public void clickCompleteRegLink() {
		driver.findElement(completeRegLink).click();
	}

	public WebElement scrollGetDesc(String category) {
		scrollIntoView(driver.findElement(desc));
		return driver.findElement(desc);
	}

	/**
	 * To get the token transaction description, token amount and their no. of
	 * occurrences for the user
	 * 
	 * @param description
	 * @param tokenAmount
	 * @param numberOfOccurrences
	 * @author vsankar
	 * @return numberOfOccurrences
	 */
	public int verifyTokenTransactionsDetails(String description, String tokenAmount, int numberOfOccurrences) {
		waitUntilThePageLoads();
		tokenAmount = tokenAmount.replace(",", "").trim();
		int numberOfTimesTokenTransactionMsg = 0;
		waitSeconds(5);
		waitUntilElementIsVisible(30, tokenHistory);
		List<WebElement> tokenHistoryList = driver.findElements(tokenHistory);
		for (int count = 1; count <= tokenHistoryList.size(); count = count + 1) {
			scrollIntoView(tokenHistoryList.get(count - 1));
			waitSeconds(2);
			tokenHistoryList = driver.findElements(desc);
			if (tokenHistoryList.get(count - 1).getText().contains(description)) {
				numberOfTimesTokenTransactionMsg++;
				WebElement tokenElement = driver.findElements(amt).get(count - 1);
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

	public WebElement scrollGetAmt(String category) {
		scrollIntoView(driver.findElement(amt));
		return driver.findElement(amt);
	}

}
