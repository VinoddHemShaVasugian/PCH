package com.pageobjects;

import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.util.BaseClass;

public class SegmentsMembership_page extends BaseClass {

	private static SegmentsMembership_page seg_instance = new SegmentsMembership_page();

	private SegmentsMembership_page() {
	}

	/**
	 * Returns a Singleton instance
	 * 
	 * @return cs_instance
	 */
	public static SegmentsMembership_page getInstance() {
		return seg_instance;
	}

	Random randomGenerator = new Random();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private final By registermember_option = By.xpath("//*[@label='registration']/option[3]");
	private final By set_segment_email_field = By.id("txtSetByEmailAndNameEmail");
	private final By set_segment_dropdown_field = By.id("lbxSetByEmailAndNameActiveSegments");
	private final By assign_segment_button = By.id("btnSetByEmailAndNameSegmentsMembership");
	private final By title = By.id("req_Title");
	private final By firstName = By.id("req_FirstName");
	private final By lastName = By.id("req_LastName");
	private final By screenName = By.id("req_ScreenName");

	/**
	 * Assign the Segment to the given user
	 * 
	 * @param user_email
	 * @param segment
	 */
	public void set_segment_to_user(String user_email, String segment) {
		textbox(set_segment_email_field, "enter", user_email, 15);
		selectByValue(set_segment_dropdown_field, segment, 15);
		button(assign_segment_button, 15);
	}
}
