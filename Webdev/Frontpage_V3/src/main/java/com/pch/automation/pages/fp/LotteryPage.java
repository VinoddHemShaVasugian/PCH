package com.pch.automation.pages.fp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.pages.PageObject;

public class LotteryPage extends PageObject {
	HomePage homePage = new HomePage();

	private final By lotterypageHeader = By.xpath("//*[@class = 'section-header section-header--large clearfix']");
	private final By changeLotteryLocationBtn = By.id("lotto-map__selector");
	private final By lotteryMap = By.id("lotto-map__map");
	private final By lotterymapMichigan = By.xpath("//*[@id='lotto-map__map']//a[text()='Michigan']");
	private final By lotteryStateTitle = By.xpath("//*[@class='lotto-map__state']");
	private final By lastDrawingDate = By
			.xpath("//*[text()='Last Drawing:']/following-sibling::span[@class='lotto-draw__date'][1]");
	private final By nextDrawingDate = By
			.xpath("//*[text()='Next Drawing:']/following-sibling::span[@class='lotto-draw__date'][1]");
	private final By pastresultsNavLink = By.xpath("//*[text()='Past Results']");
	private final By payoutsNavLink = By.xpath("//*[text()='Payout']");
	private final By pastResultsSide = By.xpath("//div[@class='lotto-past-results__table-side-caption']");
	private final By pastResultsColums = By.xpath("//th[@class='lotto-past-results__th']");
	private final By payoutColumns = By.xpath("//tr[@class='lotto-payout__tr']/th");
	private final By payoutPastresultsNavLink = By
			.xpath("//a[@class = 'lotto-past-results-nav__link' and contains(text(), 'Click for payouts')]");
	private final By pastResultsHeaderText = By.xpath("//div[@class='lotto-past-results-nav']");
	private final By payoutPayoutsNavLink = By
			.xpath("//a[@class = 'lotto-past-results-nav__link' and contains(text(), 'Click for past results')]");
	private final By claimTokensBtn = By.xpath("//button[contains(text(), 'Claim Tokens')]");
	private final By jackpot = By.xpath("//span[contains(text(),'Next Jackpot')]");
	private final By pastResult = By.xpath("//div/a[contains(.,'Past Results')]");
	private final By payout = By.xpath("//div/a[contains(.,'Payout')]");
	private final By payoutHeaderText = By.xpath("//div[@class='lotto-past-results-nav']");

	public String getLotteryPageHeaderText() {
		return element(lotterypageHeader).getText();
	}

	public boolean verifyNextJackpot() {
		return isElementVisible(jackpot);
	}

	public String changeLottryLocation() {
		homePage.jsClick(changeLotteryLocationBtn);
		waitForRenderedElementsToBePresent(lotteryMap);
		String selectedCity = element(lotterymapMichigan).getText();
		homePage.jsClick(lotterymapMichigan);
		return selectedCity;
	}

	public String getLotteryStateTitle() {
		return element(lotteryStateTitle).getText();
	}

	public String getLotteryLastDrawDate() {
		return findAll(lastDrawingDate).get(0).getText();
	}

	public String getLotteryNextDrawDate() {
		return findAll(nextDrawingDate).get(0).getText();
	}

	public void clickLotteryPastResultsLink() {
		clickOn(findAll(pastresultsNavLink).get(0));
	}

	public void clickLotteryPayoutsLink() {
		clickOn(findAll(payoutsNavLink).get(0));
	}

	public String getPastResultSide() {
		return element(pastResultsSide).getText();
	}

	public List<String> getLotteryPastResultsColums() {
		List<String> columns = new ArrayList<String>();
		for (WebElement e : findAll(pastResultsColums)) {
			columns.add(e.getText());
		}
		return columns;
	}

	public List<String> getLotteryPayoutsColums() {
		List<String> columns = new ArrayList<String>();
		for (WebElement e : findAll(payoutColumns)) {
			columns.add(e.getText());
		}
		return columns;
	}

	public void clickPayoutPastresultsNavLink() {
		clickOn(element(payoutPastresultsNavLink));
	}

	public void clickPayoutPayoutsNavLink() {
		clickOn(element(payoutPayoutsNavLink));
	}

	public String getPayoutHeaderText() {
		return element(payoutHeaderText).getText();
	}

	public void clickClaimTokens() {
		clickOn(element(claimTokensBtn));
	}

	public boolean verifyClaimTokensDisplayed() {
		return isElementVisible(claimTokensBtn);
	}

	public boolean verifyLastDrawCurrentDate() throws ParseException {
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
		df.setLenient(false);
		Date dt = df.parse(getLotteryLastDrawDate().toString() + " 23:59:59");
		if (dt.compareTo(date) < 0) {
			return true;
		} else if (dt.compareTo(date) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean verifyNextdrawCurrentDate() throws ParseException {
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
		df.setLenient(false);
		Date dt = df.parse(getLotteryNextDrawDate().toString() + " 23:59:59");
		if (dt.compareTo(date) > 0) {
			return true;
		} else if (dt.compareTo(date) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void clickPastResults() {
		clickOn(element(pastResult));
	}

	public void clickPayout() {
		clickOn(element(payout));
	}

	public boolean verifyPastResult() {
		return isElementVisible(pastResultsHeaderText) && isElementVisible(pastResultsColums)
				&& isElementVisible(pastResultsSide);
	}

	public boolean verifyPayout() {
		return isElementVisible(payoutColumns) && isElementVisible(payoutPastresultsNavLink);
	}
}