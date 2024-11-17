package com.pch.kenofrontend.pages;

import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
//import java.util.TimeZone;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.kenofrontend.utilities.DateUtil;
import com.pch.kenofrontend.utilities.WaitHelper;

import net.serenitybdd.core.pages.PageObject;
public class ResultsPage extends PageObject {

	// Initialize the Pages
	WaitPage waitPage;
	HomePage homePage;
	
	// Initialize the Page Objects using By Class
	private By recent_DrawingTime = new By.ByXPath(
			".//*[@id='results__pjax']/section[1]/p[1]/strong[2]");
	private By paytablelink = new By.ByCssSelector(".results__paytable-link");
	private By pay_table = new By.ByCssSelector(".paytable__amounts__match");
	private By datedropdown = new By.ByClassName("form-label__display");
	private By timedropdown = new By.ByXPath(".//*[@class='results__opt']");
	private By calendar = new By.ByCssSelector(".pika-table");
	private By timedrp = new By.ByCssSelector(".label");
	private By drawing_go_button = new By.ByCssSelector(
			".form-submit.form-submit--go.btn.btn-default");
	private By DrawnNumbers = new By.ByXPath(
			".//*[contains(@class, 'select-nums__num--winning')]/span");
	private By prizelist = new By.ByXPath(
			".//*[contains(@class, 'results__picks__award results__picks__award--tokens')]");
	private By previousMonthArrow = new By.ByCssSelector(".pika-prev");


	// Initialize Variables
	List<String> numbersdrawn = new ArrayList<String>();
	List<WebElement> results_drawnNumbers = null;
	ArrayList<String> prizetable = new ArrayList<>();
	ArrayList<String> paytableprizes;
	Hashtable<Integer, ArrayList<String>> paytable = new Hashtable<Integer, ArrayList<String>>();
	Hashtable<Integer, ArrayList<String>> played_numbers_table = new Hashtable<Integer, ArrayList<String>>();
	List<WebElement> whole_prize_list;
	String str_drawnnumbers;
	int date, min;

	public ResultsPage(WebDriver driver) {
		super(driver);
		min = DateUtil.getCurrentMinutes();
		date = Integer.parseInt(DateUtil.getCurrentDate());
	}

	public void get_recentdrawtime() {
		int drawtime;
		drawtime = Integer.parseInt(element(recent_DrawingTime).getText()
				.substring(3, 5));
		System.out.println("Recent Draw was held at minutes: " + drawtime);
		Assert.assertTrue(
				"Results Page is not showing correct recent drawing time", min
						- drawtime < 20);

	}

	public void paytable() {
		homePage.waitForElementPresence(paytablelink);
		if (element(paytablelink).isDisplayed()) {
			element(paytablelink).click();
			Assert.assertTrue(
					"Pay Table is not displayed on clicking Paytable link",
					!(element(pay_table).isVisible()));

		} else
			System.out.println("Pay Table link is not displaying");
	}

	public void datedropdown() {
		homePage.waitForElementPresence(datedropdown);
		if (element(datedropdown).isEnabled()) {
			element(datedropdown).click();
		}
	}

	public void validateCalendar() { 
		int disableddate = 0;

		System.out.println("Today's date is: " + date);
		element(datedropdown).click();
		homePage.waitForElementPresence(calendar);
		if (!element(calendar).isVisible()) {
			System.out
					.println("Calendar is not appearing so clicking on date drop down again");
			element(datedropdown).click();
			homePage.waitForElementPresence(calendar);
		}
		if (element(calendar).isVisible()) {
			if (date < 15) { // This logic will check for previous month dates
				System.out.println("Month is: "
						+ element(datedropdown).getText());
				if (element(datedropdown).getText().equals("FEBRUARY, 2017")
						|| element(datedropdown).getText()
								.equals("APRIL, 2017")
						|| element(datedropdown).getText().equals("JUNE, 2017")
						|| element(datedropdown).getText().equals(
								"AUGUST, 2017")
						|| element(datedropdown).getText().equals(
								"SEPTEMBER, 2017")
						|| element(datedropdown).getText().equals(
								"JANUARY, 2017")) {
					disableddate = (31 - 14 + date); 
				}
				if (element(datedropdown).getText().equals("MAY, 2017")
						|| element(datedropdown).getText().equals("JULY, 2017")
						|| element(datedropdown).getText().equals(
								"OCTOBER, 2017")
						|| element(datedropdown).getText().equals(
								"DECEMBER, 2017")) {
					disableddate = (30 - 14 + date);
				} 
				if (element(datedropdown).getText().equals("MARCH, 2017")) {
					disableddate = (28 - 14 + date);
				} 
				element(previousMonthArrow).click();

			} else {
				disableddate = date - 14;
			}
			List<WebElement> tableRows = element(calendar).findElements(
					By.tagName("tr"));
			for (WebElement rows : tableRows) {
				List<WebElement> cells = rows.findElements(By.xpath("td"));
				for (WebElement cell : cells) {
					System.out.println("Text is: " + cell.getText());
					if (cell.getText().toString().length() >= 1) {
						if (Integer.parseInt(cell.getText()) <= disableddate) {
							// This logic is valid only if today's date is less than 14th
							System.out.println("14 days prior date was: "
									+ Integer.parseInt(cell.getText())
									+ " and is not clickable");
							Assert.assertTrue(
									"Days prior to 13 days are clicakble",
									(element(cell).getAttribute("class")
											.contains("disabled")));
						}
					}
				}
			}
		}
	}

	public void validatematches() {
		try {
			whole_prize_list = getDriver().findElements(prizelist);
		} catch (StaleElementReferenceException e) {
			System.out
					.println("It has thrown Stale Element Reference Exception so trying one more attempt after little wait");
			element(prizelist).waitUntilVisible();
			whole_prize_list = getDriver().findElements(prizelist);
		}
		prizetable.add("100"); // Hardcoded for now. But in future can retrieve
		prizetable.add("250"); // it from admin
		prizetable.add("500");
		prizetable.add("1000");
		prizetable.add("5000");
		prizetable.add("10000");
		prizetable.add("50000");
		prizetable.add("100000");
		prizetable.add("500000");
		prizetable.add("1000000");

		for (int cardnum = 1; cardnum <= 5; cardnum++) {
			List<String> grpData = new ArrayList<String>();
			if (HomePage.numbers_selected_forallcards.get(cardnum) != null)
				grpData = HomePage.numbers_selected_forallcards.get(cardnum);
			System.out.println("Played numbers for card # " + cardnum
					+ " are: " + grpData);
			grpData.retainAll(numbersdrawn);
			System.out.println("Matched numbers for card # " + cardnum + " : "
					+ grpData);
			System.out.println("Total count of matched numbers for card # "
					+ cardnum + " = " + grpData.size());
			if ((grpData.size() > 0) && (whole_prize_list.size() > 0)) {
				String wonprize = whole_prize_list.get(cardnum - 1).getText();
				System.out.println("And the prize won for this card is: "
						+ wonprize);
				wonprize.substring(7, wonprize.length() - 1)
						.replaceAll(",", "");
				int temporaryprize = grpData.size() - 1;
				if ((prizetable.get(temporaryprize).equals(wonprize.substring(
						7, wonprize.length() - 1).replaceAll(",", ""))))
					System.out.println("Won correct prize of: "
							+ wonprize.substring(7, wonprize.length() - 1)
									.replaceAll(",", ""));
			}
		}
	}

	public void pickpastdate() {
		int flag = 0, datetoselect;
		datetoselect = date;
		try {
			homePage.waitForElementPresence(datedropdown);
			element(datedropdown).click();
		} catch (StaleElementReferenceException e) {
			System.out
					.println("It has thrown Stale Element Reference Exception for date drop down. Hence trying one more attempt after little wait");
			element(datedropdown).waitUntilClickable();
			element(datedropdown).click();
		}
		homePage.waitForElementPresence(calendar);
		if (!element(calendar).isVisible()) {
			System.out
					.println("Calendar is not appearing so clicking on date drop down again");
			element(datedropdown).click();
			homePage.waitForElementPresence(calendar);
		}
		if (element(calendar).isVisible()) {
			List<WebElement> tableRows = element(calendar).findElements(
					By.tagName("tr"));
			for (WebElement rows : tableRows) {
				List<WebElement> cells = rows.findElements(By.xpath("td"));
				for (WebElement cell : cells) {
					if (cell.getText().equals(Integer.toString(datetoselect))) {
						System.out.println("Selected date is "
								+ element(cell).getText());
						element(cell).click();
						flag = 1;
						break;
					}
				}
				if (flag == 1)
					break;
			}
		}
	}

	public void picktime() {
		String time = element(recent_DrawingTime).getText().toString();
		element(timedrp).click();
		waitPage.waitForElementPresent(timedropdown);

		if (element(timedropdown).isEnabled()) {
			String timetoselect = time.substring(0, 8) + " ET";
			System.out.println("Time to find is: " + timetoselect);
			List<WebElement> options = getDriver().findElements(timedropdown);
			List<String> text = new ArrayList<>();
			for (WebElement eleOptions : options) {
				text.add(eleOptions.getText());
				// System.out.println("text is: "+ eleOptions.getText());
				if (eleOptions.getText().equalsIgnoreCase(timetoselect)) {
					eleOptions.click();
					break;
				}

			}
		}
	}

	public void draw_GoButton() {
		element(drawing_go_button).click();
		getDrawnNumbersonResultsPage();
	}

	public void waitforLiveDraw() throws InterruptedException {
		int timetowait = (20 - (min % 20));
		System.out.println("Execution will sleep for " + timetowait
				+ " mins so that Live Drawing could happen ");
		waitPage.forcibleWait(timetowait * 60 * 1000);
		getDriver().navigate().refresh(); // Sometimes screen hangs on the page
											// and click does not work. Will
											// remove this code if it is a
											// defect
		homePage.clickOn_Results_Btn();

	}

	public void getDrawnNumbersonResultsPage() {
		waitPage.waitForElementPresent(DrawnNumbers);
		waitPage.forcibleWait(30000); // This forced timer is implemented so
										// that it could reflect drawn numbers
										// on results page too. Otherwise it
										// throws stale exception
		results_drawnNumbers = null;
		try {
			results_drawnNumbers = getDriver().findElements(DrawnNumbers);
		} catch (StaleElementReferenceException e) {
			System.out
					.println("It has thrown Stale Element Reference Exception so trying one more attempt after little wait");
			element(DrawnNumbers).waitUntilVisible();
			results_drawnNumbers = getDriver().findElements(DrawnNumbers);
		}

		for (WebElement results_drawnnumber : results_drawnNumbers)
			numbersdrawn.add(results_drawnnumber.getText().trim());

		System.out.println("Drawn numbers displayed on Live Drawing page are: "
				+ numbersdrawn);
	}

}