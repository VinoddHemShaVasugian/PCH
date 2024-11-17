package com.pageobjects;

import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.util.BaseClass;

public class CentralServices_Page extends BaseClass {

	private static CentralServices_Page cs_instance = new CentralServices_Page();

	private CentralServices_Page() {
	}

	/**
	 * Returns a Singleton instance
	 * 
	 * @return cs_instance
	 */
	public static CentralServices_Page getInstance() {
		return cs_instance;
	}

	Random randomGenerator = new Random();
	private static final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private final By miniregistermember_option = By.xpath("//*[@label='registration']/option[2]");
	private final By email = By.cssSelector("#req_Email");
	private final By confirm_email = By.cssSelector("#req_ConfirmEmail");
	private final By password = By.cssSelector("#req_Password");
	private final By confirm_password = By.cssSelector("#req_ConfirmPassword");
	private final By testapi_button = By.cssSelector("#btnSubmitRequest");
	private final By reg_confirm_msg = By.cssSelector("textarea#status-message");
	private final By requestID = By.id("req_ExternalId");
	private final By requestEmail = By.id("req_ExternalEmail");
	private final By pch_email = By.id("req_PchEmail");
	private final By servicesList = By.id("lstServices");
	private final By registermember_option = By.xpath("//*[@label='registration']/option[3]");
	private final By set_segment_email_field = By.id("txtSetByEmailAndNameEmail");
	private final By set_segment_dropdown_field = By.id("lbxSetByEmailAndNameActiveSegments");
	private final By assign_segment_button = By.id("btnSetByEmailAndNameSegmentsMembership");
	private final By title = By.id("req_Title");
	private final By firstName = By.id("req_FirstName");
	private final By lastName = By.id("req_LastName");
	private final By screenName = By.id("req_ScreenName");

	/**
	 * To create a User with Password
	 * 
	 * @return user with password's email ID
	 */
	public String[] createGoldUser() {
		String user_details[] = new String[2];
		String user_email = "PCH" + randomString(5, 6) + Date() + "@pchmail.com";
		link(registermember_option, "click", 15);
		textbox(title, "enter", xmlReader(ENVIRONMENT, "Title"), 10);
		textbox(firstName, "enter", "QaPCH" + randomString(6, 7), 10);
		textbox(lastName, "enter", randomString(6, 7), 10);
		textbox(screenName, "enter", "QaPCH" + randomString(6, 7), 10);
		textbox(email, "enter", user_email, 10);
		textbox(confirm_email, "enter", user_email, 10);
		textbox(password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 10);
		textbox(confirm_password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 10);
		js_click(testapi_button, 30);
		waitForElementUntilTextPresent(reg_confirm_msg, "OnlineAccountToken", 30);
		log.info("Newly Registered Gold user Email is :: " + user_email);
		log.info("Newly Registered Gold reg user Email URL is :: "
				+ (xmlReader(ENVIRONMENT, "BaseURL") + "/?email=" + user_email + "&e=" + getGMTfromRespose()));
		user_details[0] = user_email;
		user_details[1] = xmlReader(ENVIRONMENT, "BaseURL") + "?email=" + user_email + "&e=" + getGMTfromRespose();
		return user_details;

	}

	/**
	 * To create a Silver user
	 * 
	 * @return silver user logged in url
	 */
	public String[] createSilverUser() {
		String user_details[] = new String[2];
		String user_email = "PCH" + randomString(5, 6) + Date() + "@pchmail.com";
		selectByVisibleText(servicesList, "registration/setmember", 10);
		textbox(email, "enter", user_email, 15);
		textbox(confirm_email, "enter", user_email, 15);
		js_click(testapi_button, 30);
		waitForElementUntilTextPresent(reg_confirm_msg, "OnlineAccountToken", 30);

		log.info("Newly Registered Silver user Email is :: " + user_email);
		log.info("Newly Registered Silver reg user Email URL is :: "
				+ (xmlReader(ENVIRONMENT, "BaseURL") + "/?email=" + user_email + "&e=" + getGMTfromRespose()));
		user_details[0] = user_email;
		user_details[1] = xmlReader(ENVIRONMENT, "BaseURL") + "/?e=" + getGMTfromRespose() + "&email=" + user_email;
		return user_details;
	}

	/**
	 * @return Mini Reg User URL
	 */
	// To create a Mini reg user
	public String[] createMiniReguser() {
		String user_details[] = new String[2];
		String user_email = "PCH" + randomString(5, 6) + Date() + "@pchmail.com";
		button(miniregistermember_option, 15);
		textbox(email, "enter", user_email, 15);
		textbox(confirm_email, "enter", user_email, 15);
		textbox(password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 15);
		textbox(confirm_password, "enter", xmlReader(ENVIRONMENT, "ValidPassword"), 15);
		js_click(testapi_button, 30);
		verify_reg_success_msg();
		log.info("Newly Registered Mini reg user Email is :: " + user_email);
		log.info("Newly Registered Mini reg user Email URL is :: " + (xmlReader(ENVIRONMENT, "BaseURL")
				+ "/?src1=swemail&src2=13S2505&mailid=pch13S2505&e=" + getGMTfromRespose() + "&email=" + user_email));
		user_details[0] = user_email;
		user_details[1] = xmlReader(ENVIRONMENT, "BaseURL") + "/?e=" + getGMTfromRespose() + "&email=" + user_email;
		return user_details;
	}

	/**
	 * @return Social User URL
	 */
	// To create a Social User from CS and method will return social user url
	public String[] createSocialUser() {
		String user_details[] = new String[2];
		int ReID = randomGenerator.nextInt(1000000);
		String RID = Integer.toString(ReID);
		String user_email = "PCH" + randomString(5, 6) + Date() + "@pchmail.com";

		selectByVisibleText(servicesList, "externalconnect/facebookwithemail", 10);
		textbox(requestID, "enter", RID, 15);
		textbox(requestEmail, "enter", user_email, 15);
		textbox(pch_email, "enter", user_email, 15);
		js_click(testapi_button, 30);
		verify_reg_success_msg();
		log.info("Newly Registered Social reg user Email is :: " + user_email);
		log.info("Newly Registered Social reg user Email URL is :: " + (xmlReader(ENVIRONMENT, "BaseURL")
				+ "/?src1=swemail&src2=13S2505&mailid=pch13S2505&e=" + getGMTfromRespose() + "&email=" + user_email));
		user_details[0] = user_email;
		user_details[1] = xmlReader(ENVIRONMENT, "BaseURL") + "/?src1=swemail&src2=13S2505&mailid=pch13S2505&e="
				+ getGMTfromRespose() + "&email=" + user_email;
		return user_details;
	}

	/**
	 * @return GMT Response
	 */
	// To get the GMT value from response
	public String getGMTfromRespose() {
		String Response;
		int Pos;
		Response = getText(reg_confirm_msg, 5);
		// System.out.println(Response);
		Pos = Response.indexOf("GlobalMemberToken");
		try {
			Response = Response.substring(Pos + 21, Pos + 57);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Response = Response.substring(Pos + 21, Pos + 57);
		}
		return Response;
	}

	/**
	 * To verify the success message after registration
	 */
	public boolean verify_reg_success_msg() {
		waitForElementUntilTextPresent(reg_confirm_msg, "success", 10);
		boolean status = getText(reg_confirm_msg, 5).contains("HttpStatusDesc:" + " " + "success");
		return status;
	}

	/**
	 * Navigate to CS Segmentation page
	 * 
	 * @throws Exception
	 */
	public void navigate_segmentation_page() throws Exception {
		invokeBrowser(xmlReader(ENVIRONMENT, "SegmentationURL"));
	}

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
