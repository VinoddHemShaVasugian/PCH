package com.pch.sw.tests.nfsp;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.testng.annotations.Test;

import com.pch.search.mobile.pages.GuidedSearchPage;
import com.pch.search.pages.web.HomePage;
import com.pch.search.pages.web.RegistrationPage;
import com.pch.search.pages.web.SearchResultsPage;
import com.pch.search.pages.web.WebHeaderPage;
import com.pch.search.utilities.BaseTest;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.GroupNames;
import com.pch.search.utilities.User;

public class SearchNFSPTests extends BaseTest {
	HomePage homePage;
	WebHeaderPage header;
	GuidedSearchPage gsPage;
	SearchResultsPage serp;
	public RegistrationPage webRegistrationPage;
	private User randomUser_1;
	String searchKeyword = "shoes";

//	@Test(groups = { GroupNames.Mobile,
//			GroupNames.Regression }, testName = "GuidedSearch-NFSP_Mobile-TestID-30267", description = "verify default nfsp for guest and registered user")
	public void testDefaultMobileNFSP() {

		// validate NFSP for unregistered user
		homePage.load();
		header.search("shoes", false);
		serp.validateNFSP("source=def_m");
		serp.validateSegment("segment=pchmobile1.pchmobile1");

		// validate NFSP for registered user
		header.loginToSearch("kcheguri21@pchmail.com", "testing");
		header.search("shoes", false);
		serp.validateNFSP("source=def_m");
		serp.validateSegment("segment=pchmobile1.pchmobile1");
	}

	@Test(groups = { GroupNames.Desktop,
			GroupNames.Regression }, testName = "GuidedSearch-NFSP_Mobile-TestID-30267", description = "verify default nfsp for guest and registered user")
	public void testDefaultDesktopNFSP() {

		// validate NFSP for unregistered user
		homePage.load();
		header.search("shoes", false);
		serp.validateNFSP("source=");
		serp.validateSegment("segment=pchmeta4.pchmeta4");

		// validate NFSP for registered user
		loginToSearch(randomUser_1);
		header.search("shoes", false);
		serp.validateNFSP("source=");
		serp.validateSegment("segment=pchmeta4.pchmeta4");
	}

//	@Test(groups = { GroupNames.Tablet,
//			GroupNames.Regression }, testName = "GuidedSearch-NFSP_Mobile-TestID-30267", description = "verify default nfsp for guest and registered user")
	public void testDefaultTabletNFSP() {

		// validate NFSP for unregistered user
		homePage.load();
		header.search("shoes", false);
		serp.validateNFSP("source=tab_t");
		serp.validateSegment("segment=pchtablet1.pchtablet1");

		// validate NFSP for registered user
		header.loginToSearch("kcheguri21@pchmail.com", "testing");
		header.search("shoes", false);
		serp.validateNFSP("source=tab_t");
		serp.validateSegment("segment=pchtablet1.pchtablet1");
	}

	@Test(groups = { GroupNames.Desktop,
			GroupNames.Regression }, testName = "", description = "verify nfsp values for all desktop nfsp's")
	public void testDesktopNFSP() throws IOException {
		Properties pro = Common.getPropertiesFromFile("NfspDesktop.properties");
		for (String nfsp : pro.stringPropertyNames()) {
			// for (String nfsp : pro.stringPropertyNames()) {
			// Get NFSP & Segment from the properties file
			String segment = pro.getProperty(nfsp);
			CustomLogger.log("==============================================");
			CustomLogger.log("Validating " + nfsp + "=" + segment);
			CustomLogger.log("==============================================");

			// Load SERP with nfsp and search term in a browser
			homePage.loadSerpWithNfsp(searchKeyword, nfsp);
			// Validate nfsp and segment values from browser console log
			header.validateNFSP(nfsp);
			header.validateSegment(segment);

			// Clear browser cookies for validating the next segment
			header.clearBrowserCookies();
			CustomLogger.log("==============================================");
		}
	}

//	@Test(groups = { GroupNames.Mobile,
//			GroupNames.Regression }, testName = "", description = "verify nfsp values for all Mobile nfsp's")
	public void testMobileNFSP() {

		Properties pro = Common.getPropertiesFromFile("NfspMobile.properties");

		@SuppressWarnings("rawtypes")
		Enumeration em = pro.keys();
		while (em.hasMoreElements()) {
			// Get nfsp from the properties file
			String nfsp = (String) em.nextElement();
			CustomLogger.log("==============================================");
			CustomLogger.log("Validating " + nfsp + "=" + pro.get(nfsp));
			CustomLogger.log("==============================================");

			// Get Segment from the properties file
			String segment = (String) pro.get(nfsp);

			// Load SERP with nfsp and search term in a browser
			homePage.loadSerpWithNfsp(searchKeyword, nfsp);

			// Validate nfsp and segment values from browser console log
			header.validateNFSP(nfsp);
			header.validateSegment(segment);

			// Clear browser cookies for validating the next segment
			header.clearBrowserCookies();
			CustomLogger.log("==============================================");
		}

	}

//	@Test(groups = { GroupNames.Tablet,
//			GroupNames.Regression }, testName = " ", description = "verify nfsp values for all Tablet nfsp's")
	public void testTabletNFSP() {

		Properties pro = Common.getPropertiesFromFile("NfspTablet.properties");

		@SuppressWarnings("rawtypes")
		Enumeration em = pro.keys();
		while (em.hasMoreElements()) {
			// Get nfsp from the properties file
			String nfsp = (String) em.nextElement();
			CustomLogger.log("==============================================");
			CustomLogger.log("Validating " + nfsp + "=" + pro.get(nfsp));
			CustomLogger.log("==============================================");

			// Get Segment from the properties file
			String segment = (String) pro.get(nfsp);

			// Load SERP with nfsp and search term in a browser
			homePage.loadSerpWithNfsp(searchKeyword, nfsp);

			// Validate nfsp and segment values from browser console log
			header.validateNFSP(nfsp);
			header.validateSegment(segment);

			// Clear browser cookies for validating the next segment
			header.clearBrowserCookies();
			CustomLogger.log("==============================================");
		}
	}

}
