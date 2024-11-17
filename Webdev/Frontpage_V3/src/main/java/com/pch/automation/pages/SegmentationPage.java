package com.pch.automation.pages;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;

public class SegmentationPage extends PageObject {
	private final By setSegmentEmailFieldByCode = By.id("txtSetByEmailAndCodeEmail");
	private final By setSegmentDropdownFieldByCode = By.id("lbxSetByEmailAndCodeActiveSegments");
	private final By assignSegmentButtonByCode = By.id("btnSetByEmailAndCodeSegmentsMembership");
	private final By successMsgByCode = By.id("lblSetByEmailAndCodeSegmentsDesc");
	private final By setSegmentEmailFieldByName = By.id("txtSetByEmailAndNameEmail");
	private final By setSegmentDropdownFieldByName = By.id("lbxSetByEmailAndNameActiveSegments");
	private final By assignSegmentButtonByName = By.id("btnSetByEmailAndNameSegmentsMembership");
	private final By successMsgByName = By.id("lblSetByEmailAndNameSegmentsDesc");
	private final String successMessage = "Segments assignment successful";

	/**
	 * Assign the Segment to the given user by code
	 * 
	 * @param userEmail
	 * @param segmentCode
	 */
	public void setSegmentByCode(String userEmail, String segmentCode) {
		typeInto(element(setSegmentEmailFieldByCode), userEmail);
		selectFromDropdown(element(setSegmentDropdownFieldByCode), segmentCode);
		clickOn(element(assignSegmentButtonByCode));
		waitForTextToAppear(element(successMsgByCode), successMessage);
	}

	/**
	 * Assign the Segment to the given user by name
	 * 
	 * @param userEmail
	 * @param segmentName
	 */
	public void setSegmentByName(String userEmail, String segmentName) {
		typeInto(element(setSegmentEmailFieldByName), userEmail);
		selectFromDropdown(element(setSegmentDropdownFieldByName), segmentName);
		clickOn(element(assignSegmentButtonByName));
		waitForTextToAppear(element(successMsgByName), successMessage);
	}
}
