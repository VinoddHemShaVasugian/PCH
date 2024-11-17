package com.pch.kenofrontend.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import toolbox.JavaScriptReusablePage;
import com.pch.kenofrontend.utilities.PropertiesReader;
import com.pch.kenofrontend.utilities.WebServiceClient;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class Leaderboard extends PageObject {

	private By leaderboard_table = new By.ByXPath(
			".//*[@id='leaderboard--tbody']/tr");
	private By leaderboardwinners = new By.ByCssSelector(".winner");
	private By myDailyTokenTotal = new By.ByCssSelector(".leaderboard--token-total");
	private By yesterdayview = new By.ByCssSelector(".leaderboard--yesterday-view");
	
	// Initialize the Pages
	JavaScriptReusablePage jsp;
	HomePage homePage;

	// Initialize Variables
	public static Hashtable<Integer, ArrayList<String>> mobileleaderboardrecordsapi = new Hashtable<Integer, ArrayList<String>>();
	public static Hashtable<Integer, ArrayList<String>> mobileleaderboardrecords = new Hashtable<Integer, ArrayList<String>>();

	public Leaderboard(WebDriver driver) {
		super(driver);
	}

	public void verifyUserDataInLeaderboard() {
		int index = 0;
		List<WebElementFacade> leaderboardtablerows = findAll(leaderboard_table);
		for (WebElement row : leaderboardtablerows) {
			index++;
			List<WebElement> rowElements = row.findElements(By.xpath(".//td"));
			ArrayList<String> rowData = new ArrayList<String>();
			for (WebElement column : rowElements) {
				if (column.getText().toString().length() > 2)
					rowData.add(column.getText().toString());
				if (column.getAttribute("innerHTML").contains("img")) {
					String gemClass = element(column)
							.findElement(By.tagName("img"))
							.getAttribute("class").toString();
					// System.out.println("Classname is: " + gemClass);
					System.out.println("And sub string of gem class is:"
							+ gemClass.substring(4));
					rowData.add(gemClass.substring(4));
				}
			}
			System.out.println("***Printing the whole record: "
					+ rowData.toString());
			if (rowData.toString().contains("KEEP PLAYING!")
					|| rowData.toString().contains("technical difficulties")) {
				System.out
						.println("***There is no record displayed on leaderboard***");
				break;
			} else {
				System.out.println("Record is added in list");
			}
			Collections.sort(rowData);
			System.out
					.println("***Record after sorting: " + rowData.toString());
			mobileleaderboardrecords.put(index, rowData);
		}
		System.out.println("Printing all records from leaderboard:"
				+ mobileleaderboardrecords);
	}

	public void fetchMobileLeaderboardAPIData() {
		JSONObject mobileleaderboarddata;
		String Url = "";
		String keyToRead = "dailyMobileData";
		try {
			Url = "https://keno."
					+ PropertiesReader.getInstance().getBaseConfig(
							"CurrentEnvironment") + ".pch.com/apis/leaderboard";
		} catch (Exception e) {
			e.printStackTrace();
		}
		mobileleaderboarddata = WebServiceClient
				.requestGet_IgnoringCertificateErrors(Url, keyToRead);
		JSONArray data = mobileleaderboarddata.getJSONArray(("data"));
		if (data.length() == 0)
			System.out
					.println("No record found for mobile leaderboard api data");
		else {
			System.out.println("Number of records found in leaderboard api are: "
					+ data.length());
			for (int i = 0; i < data.length(); i++) {
				ArrayList<String> mobileapidata = new ArrayList<String>();
				mobileapidata.add(data.getJSONObject(i).getString("name"));
				mobileapidata.add(Integer.toString((data.getJSONObject(i)
						.getInt("balance"))));
				mobileapidata.add(data.getJSONObject(i).getString("level"));
				System.out
						.println("***Mobile Leaderboard API record before sorting: "
								+ mobileapidata.toString());
				Collections.sort(mobileapidata);
				System.out
						.println("***Mobile Leaderboard API record after sorting: "
								+ mobileapidata.toString());
				mobileleaderboardrecordsapi.put(i + 1, mobileapidata);
			}
			System.out.println("Printing all records from api:"
					+ mobileleaderboardrecordsapi);
		}
	}

	public void compareMobileLeaderboardData() {
		fetchMobileLeaderboardAPIData();
		System.out.println("Leaderboard API Record: "
				+ mobileleaderboardrecordsapi);
		System.out
				.println("Leaderboard UI Record: " + mobileleaderboardrecords);
		Assert.assertTrue(mobileleaderboardrecordsapi
				.equals(mobileleaderboardrecords));
	}

	public void verifyYesterdayWinnersInLeaderboard() {
		List<ArrayList<String>> yesterdaywinnersrecords = new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> yesterdaywinnersapi = fetchYesterdayWinnersInLeaderboard();
		List<WebElementFacade> winnertablerows = findAll(leaderboardwinners);
		String tempprize = "";
		if (winnertablerows.size() == 0) {
			System.out
					.println("***There is no record displayed on yesterday's leaderboard***");
		} else {
			for (WebElement row : winnertablerows) {
				ArrayList<String> rowData = new ArrayList<String>();
				rowData.add(row.findElement(By.className("name")).getText()
						.toString());
				rowData.add(row.findElement(By.className("state")).getText()
						.toString());
				tempprize = row.findElement(By.className("prize")).getText()
						.toString().replace("$", "");
				tempprize = tempprize.replace("!", "");
				rowData.add(tempprize);
				if (row.getAttribute("innerHTML").contains("img")) {
					String gemClass = element(row)
							.findElement(By.tagName("img"))
							.getAttribute("class").toString();
					if (gemClass.equals("winnerIcon")) {
						if (element(row).findElement(By.tagName("img"))
								.getAttribute("src").toString()
								.contains("SuQmCC")) {
							System.out.println("Winner's device type is Mobile:");
							rowData.add("mobile");
						} else {
							System.out.println("Winner's device type is desktop");
							rowData.add("desktop");
						}
					}
				}
				System.out.println("***Yesterday Winner's Leaderboard Record before sorting: "
						+ rowData.toString());
				Collections.sort(rowData);
				System.out.println("***Yesterday Winner's Leaderboard Record after sorting: "
						+ rowData.toString());
				yesterdaywinnersrecords.add(rowData);
			}
		}
		System.out
				.println("Printing all records from yesterday winner's leaderboard:"
						+ yesterdaywinnersrecords);

		Assert.assertTrue(yesterdaywinnersapi.equals(yesterdaywinnersrecords));
		if(element(yesterdayview).isDisplayed()){
			element(yesterdayview).click();
		}
	}

	public List<ArrayList<String>> fetchYesterdayWinnersInLeaderboard() {
		List<ArrayList<String>> yesterdaywinnersapi = new ArrayList<ArrayList<String>>();
		JSONArray yesterdaywinnersdata;
		String Url = "";
		String keyToRead = "data";
		try {
			Url = "https://keno."
					+ PropertiesReader.getInstance().getBaseConfig(
							"CurrentEnvironment")
					+ ".pch.com/apis/leaderboard/yesterday";
			homePage.winnersAppearingInLeaderboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
		yesterdaywinnersdata = WebServiceClient
				.requestGetArray_IgnoringCertificateErrors(Url, keyToRead);
		if (yesterdaywinnersdata.length() == 0)
			System.out
					.println("No record found for yesterday's winners api data");
		else {
			System.out.println("Number of records found in yesterday leasderboard api are: "
					+ yesterdaywinnersdata.length());
			for (int i = 0; i < yesterdaywinnersdata.length(); i++) {
				ArrayList<String> winnersapidata = new ArrayList<String>();
				winnersapidata.add(yesterdaywinnersdata.getJSONObject(i)
						.getString("device"));
				winnersapidata.add(yesterdaywinnersdata.getJSONObject(i)
						.getString("name"));
				winnersapidata.add(yesterdaywinnersdata.getJSONObject(i)
						.getString("state"));
				winnersapidata.add(yesterdaywinnersdata.getJSONObject(i)
						.getString("prize_amount"));
				// winnersapidata.add(yesterdaywinnersdata.getJSONObject(i).getString("level"));
				System.out
						.println("***Yesterday's winner API record before sorting: "
								+ winnersapidata.toString());
				Collections.sort(winnersapidata);
				System.out
						.println("***Yesterday's Winners API record after sorting: "
								+ winnersapidata.toString());
				yesterdaywinnersapi.add(winnersapidata);
			}
			System.out
					.println("Printing all records from yesterday's winners api:"
							+ yesterdaywinnersapi);
		}
		return yesterdaywinnersapi;
	}

	public void verifyMyDailyTokenTotal() {
		String fname = "", level = "", tokens = "";
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);",
				element(myDailyTokenTotal));
		fname = element(myDailyTokenTotal)
				.findElement(By.cssSelector(".first-name")).getText()
				.toString();
		System.out.println("First name is: " + fname);
		
		level = element(myDailyTokenTotal).findElement(By.tagName("img"))
				.getAttribute("class").toString().substring(4);
		System.out.println("Level is: " + level);
		tokens = element(myDailyTokenTotal)
				.findElement(By.cssSelector(".tokens")).getText().toString();
		System.out.println("Tokens are: " + tokens);
		Assert.assertTrue(fname!="" && level!="" && tokens!="");
	}
	
	public void verifyMyDailyTokenTotalUserWithoutPassword() {
		String tokenmessage="", createpasswordbutton="";
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		js.executeScript("arguments[0].scrollIntoView(true);",
				element(myDailyTokenTotal));
		tokenmessage=element(myDailyTokenTotal).findElement(By.cssSelector(".token-total")).getText().toString();
		createpasswordbutton=element(myDailyTokenTotal).findElement(By.cssSelector("#sso-create-password-lightbox")).getText().toString();
		System.out.println("Tokens are: "+tokenmessage);
		System.out.println("Button is: "+createpasswordbutton);
		Assert.assertTrue(tokenmessage.contains("GET 1,000 TOKENS") && createpasswordbutton.equalsIgnoreCase("Click Here Now"));
	}
}
