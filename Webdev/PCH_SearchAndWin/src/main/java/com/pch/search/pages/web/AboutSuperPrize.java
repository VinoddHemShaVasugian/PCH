package com.pch.search.pages.web;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.pch.search.utilities.Action;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class AboutSuperPrize extends Action {

	public HtmlElement giveAwayNumber() {
		return driver.findElement(By.xpath(".//*[@id='disclosureBox']//p/strong/u"));
	}

	public int giveAwayNumberCount() {
		return driver.getCountOfElementsWithXPath((".//*[@id='disclosureBox']//p"));
	}

	/*
	 * This include entire -about super prize- page..
	 */
	public int aboutSuperPrizeContentCount() {
		return driver.getCountOfElementsWithXPath(".//*[@id='abtSP']");
	}

	public HtmlElement aboutSuperPrizeContent() {
		return driver.findElement(By.id("abtSP"));
	}

	public By giveAwayNumberByContent() {
		return By.xpath(".//*[@id='disclosureBox']//p/strong/u");
	}

	public By aboutSuperPrizeContentByValue() {
		return By.id("abtSP");
	}

	/*
	 * This method return a list<String> - with contents of values and conflicts
	 * page
	 */
	public List<String> contentOfValuesAndFactsPage() {

		CustomLogger.log("Navigating to Value and conflicts page");

		String url = "http://rules.pch.com/viewrulesfacts?type=searchdisclosure&nocss=1";
		driver.get(url);

		List<String> expectedContent = new ArrayList<String>();

		if (driver.getCountOfElementsWithXPath(".//*[@id='disclosureBox4']/h4/strong") > 0) {

			String superPrizeEvent = driver.findElement(By.xpath(".//*[@id='disclosureBox4']/h4/strong")).getText();
			expectedContent.add(superPrizeEvent);
		}

		String giveAwayNum1 = driver.findElement(By.xpath(".//*[@id='disclosureBox']//p/strong/u")).getText();

		String awardGuarantee = driver.findElement(By.xpath(".//*[@id='disclosureBox']//p")).getText();

		// String giveAwayNum2 =
		// driver.findElement(By.xpath(".//*[@id='disclosureBox2']/p/span/strong/u")).getText();

		expectedContent.add(giveAwayNum1);
		// expectedContent.add(giveAwayNum2);
		expectedContent.add(awardGuarantee);

		CustomLogger.log("Gathered the content from the page");

		return expectedContent;
	}

	/*
	 * Here we are refreshing the page for i no.of times, to verify visibility
	 * of give away number in the about super prize page
	 * 
	 * @param : i-for how may times we need to refresh the page
	 */
	public void waitTillGravitypresent(int i) {
		try {
			for (int j = 0; j <= i; j++) {
				if (driver.getCountOfElementsWithXPath((".//*[@id='disclosureBox']//p")) > 0) {
					break;
				} else {
					driver.navigate().refresh();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
