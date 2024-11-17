package com.pageobjects;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;

import com.util.BaseClass;

public class LotteryPage extends BaseClass {

	private static final LotteryPage lottery_instance = new LotteryPage();

	private LotteryPage() {
	}

	public static LotteryPage getInstance() {
		return lottery_instance;
	}

	private final By lottery_page_header = By.cssSelector("div.section-header.section-header--large.clearfix");
	private final By last_drawing_date = By
			.xpath("//span[text()='Last Drawing:']/following-sibling::span[@class='lotto-draw__date'][1]");
	private final By next_drawing_date = By
			.xpath("//span[text()='Next Drawing:']/following-sibling::span[@class='lotto-draw__date'][1]");

	/**
	 * Get the Page header
	 * 
	 * @return
	 */
	public String get_lottery_page_header() {
		return getText(lottery_page_header, 30);
	}

	/**
	 * Get the fist lottery last drawing date
	 * 
	 * @return
	 */
	public String get_lottery_last_drawdate() {
		return get_webelements_list(last_drawing_date).get(0).getText();
	}

	/**
	 * Get the first lottery first drawing date
	 * 
	 * @return
	 */
	public String get_lottery_next_drawdate() {
		return get_webelements_list(next_drawing_date).get(0).getText();
	}

	/**
	 * Get and verify the Last drawing date is lesser than or equal to the
	 * current date
	 * 
	 * @return
	 * @throws ParseException
	 */
	public boolean verify_lastdraw_currentdate() throws ParseException {
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss");
		df.setLenient(false);
		Date dt = df.parse(get_lottery_last_drawdate().toString() + " 23:59:59");
		System.out.println(dt);

		System.out.println(date);

		if (dt.compareTo(date) < 0) {
			return true;
		} else if (dt.compareTo(date) == 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Get and verify the Next drawing date is greater than or equal to the
	 * current date
	 * 
	 * @return
	 * @throws ParseException
	 */
	public boolean verify_nextdraw_currentdate() throws ParseException {
		Date date = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("EEEE, MMM dd, yyyy");
		df.setLenient(false);
		Date dt = df.parse(get_lottery_next_drawdate().toString());
		System.out.println(dt);

		System.out.println(date);

		if (dt.compareTo(date) > 0 || dt.compareTo(date) == 0) {
			return true;
		} else {
			return false;
		}
	}
}
