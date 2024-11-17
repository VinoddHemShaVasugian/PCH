package com.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.testng.IResultMap;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.util.PriorityListener.testId;

import atu.alm.wrapper.exceptions.ALMServiceException;

import com.util.PriorityListener.testCaseName;

/**
 * 
 * Has methods to create TestWise HTML report
 * 
 * 
 * @author Maniganda Perumal
 *
 */

public class TestWiseHTMLReport {

	private FileWriter test_file_out;
	private PrintWriter test_print_out;
	private String testReportPath = "./CustomHTMLReport/";
	private String testScreenshotPath = "Screenshots";
	private final Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	// Exception Map Declaration
	HashMap<String, Integer> individualExceptionCount = new HashMap<String, Integer>();
	ArrayList<String> exceptionFilters;
	private final ConsolidatedHTMLReport consolidatedReport = new ConsolidatedHTMLReport();
	private final SeleniumALMIntegration alm_instance = SeleniumALMIntegration.getInstance();

	/***
	 * To create a HTML report file for the Test
	 * 
	 * @author Maniganda Perumal
	 * @param testName
	 * 
	 */
	protected void createTestReportHTML(String testName, boolean exceptionFlag) {

		try {
			test_file_out = new FileWriter(
					testReportPath + ConsolidatedHTMLReport.currentTime + "//" + testName + ".html");
		} catch (IOException e) {
			Reporter.log("Error in Test Report File Creation" + e);
			log.error(e.getMessage());
		}
		test_print_out = new PrintWriter(test_file_out);

		test_print_out.println(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\">");
		test_print_out.println("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />");
		test_print_out.println("<title>" + testName + "TestReport</title>");
		test_print_out.println(
				"<link href='http://fonts.googleapis.com/css?family=Roboto:400,300,500,700' rel='stylesheet' type='text/css'/>");
		test_print_out.println(
				"<link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:400,700,300' rel='stylesheet' type='text/css'/>");
		consolidatedReport.cssStyle(test_print_out);

		if (exceptionFlag) {
			addingExceptionPieChart(test_print_out);
		}

		test_print_out.println(
				"<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js\">");
		test_print_out.println("</script>");
		test_print_out.println("<script type=\"text/javascript\">");
		test_print_out.println("$(document).ready(function(){");
		test_print_out.println("$('.exception').hide();  ");
		test_print_out.println(" $(\"#report tr.exception\").prev().find('td:last div').click(function(e){");
		test_print_out.println("$tr = $(e.target).parents('tr')");
		test_print_out.println("$tr.next(\"tr.exception\").toggle();");
		test_print_out.println("$tr.find(\".show-exception\").toggleClass(\"up\");");
		test_print_out.println("});});");
		test_print_out.println("</script>");
		test_print_out.println("<script type=\"text/javascript\">");
		test_print_out.println("function ExceptionToggle(elm) {");
		test_print_out.println("var ischecked = elm.checked;");
		test_print_out.println("$target = $('table#report tbody tr#' + elm.id);");
		test_print_out.println(" $target.toggle(ischecked);}");
		test_print_out.println("</script>");
		/*
		 * To store the image (Pass, Fail, Skip and Screenshot) in the base64 format
		 */
		imageLoad();
		test_print_out.println("</head>");

		test_print_out.println(
				"<body><div id=\"shell\"><div id=\"header\"><div id=\"header-left\"><span class=\"module-report\">Test Wise Report</span><p>Test Name :  "
						+ testName + " TestReport" + "</p></div>");
		test_print_out.println("</div>");
		test_print_out.println("<div class=\"content clearfix\">");

		if (exceptionFlag) {
			addingExceptionPercentTable(test_print_out);
		}

		test_print_out.println(
				"<div id=\"table-datas\"><table id= \"report\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >");
		test_print_out.println("<tr><th width=\"15%\"><strong>" + "Test Name" + "</strong></td>");
		test_print_out.println("<th width=\"35%\"><strong>" + "Description" + "</strong></td>");
		test_print_out.println("<th width=\"10%\"><strong>" + "QC Test ID" + "</strong></td>");
		if (!exceptionFlag) {
			test_print_out.println("<th width=\"7%\"><strong>" + "Result" + "</strong></td>");
		}
		test_print_out.println("<th width=\"8%\"><strong>" + "Screenshot" + "</strong></td>");
		test_print_out.println("<th width=\"25%\"><strong>" + "Log" + "</strong></td>");
		test_print_out.println("</tr>");
	}

	/**
	 * To store the images(Pass, Fail, Skip and Screenshot) to the base64 code. So,
	 * then we can view in the Offline mode
	 */
	private void imageLoad() {

		test_print_out.println("<script type=\"text/javascript\">");
		test_print_out.println("function setImage(ele, image) {");
		test_print_out.println("element = $(ele);");
		test_print_out.println("if(element){");
		test_print_out.println("element.attr('src', image);");
		test_print_out.println("};}");
		test_print_out.println("</script>");

		test_print_out.println("<script type=\"text/javascript\">");
		test_print_out.println("$(document).ready(function(){");
		// Screenshot Image base64 code
		test_print_out.println("var screenshotImage = 'data:image/png;base64,"
				+ "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ"
				+ "bWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdp"
				+ "bj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6"
				+ "eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0"
				+ "NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJo"
				+ "dHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlw"
				+ "dGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAv"
				+ "IiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RS"
				+ "ZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpD"
				+ "cmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNl"
				+ "SUQ9InhtcC5paWQ6OEEzNTRERjM5M0JEMTFFMzkzOUVBNTQxREM2MjRGRjMiIHhtcE1NOkRvY3Vt"
				+ "ZW50SUQ9InhtcC5kaWQ6OEEzNTRERjQ5M0JEMTFFMzkzOUVBNTQxREM2MjRGRjMiPiA8eG1wTU06"
				+ "RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo4QTM1NERGMTkzQkQxMUUzOTM5"
				+ "RUE1NDFEQzYyNEZGMyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo4QTM1NERGMjkzQkQxMUUz"
				+ "OTM5RUE1NDFEQzYyNEZGMyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1w"
				+ "bWV0YT4gPD94cGFja2V0IGVuZD0iciI/PgtWeGoAAADqSURBVHjaYvz//z8DJYAFROyPZ/QCUi1Q"
				+ "PjEmMgLxHyCuYYEKlAPxeSCuI8GAJpA+mAFMQLzVceH/p8Q6HejqrUCqkAnK/wfE7CR6H6T+HxMD"
				+ "hYCqBvwlUe9fZANAocpHogEg9YywWABFXQMwZK3RFP0E4kdArADErGhyrkB8F2bAGyC2A+IENEUv"
				+ "gDgHiFtxuOIkzIB6IH4HxMJIkiBXfQdidSBeCsScUK/CwFsgnsiIKy8AvZMFpOKh8Q3yyjRgQluI"
				+ "NS/gABZAbIbEPwPEC4lKB0DbbYGUNhDfR8JmQHFDjExBaXYGCDAA5lE4o7fbs/IAAAAASUVORK5C" + "YII=';");
		// Pass Image base64 code
		test_print_out.println("var passImage ='data:image/png;base64,"
				+ "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ"
				+ "bWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdp"
				+ "bj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6"
				+ "eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0"
				+ "NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJo"
				+ "dHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlw"
				+ "dGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAv"
				+ "IiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RS"
				+ "ZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpD"
				+ "cmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNl"
				+ "SUQ9InhtcC5paWQ6NDVBQ0M1MDk5M0JDMTFFMzgxQ0FCMUQyRTRFM0IwNDMiIHhtcE1NOkRvY3Vt"
				+ "ZW50SUQ9InhtcC5kaWQ6NDVBQ0M1MEE5M0JDMTFFMzgxQ0FCMUQyRTRFM0IwNDMiPiA8eG1wTU06"
				+ "RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo0NUFDQzUwNzkzQkMxMUUzODFD"
				+ "QUIxRDJFNEUzQjA0MyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo0NUFDQzUwODkzQkMxMUUz"
				+ "ODFDQUIxRDJFNEUzQjA0MyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1w"
				+ "bWV0YT4gPD94cGFja2V0IGVuZD0iciI/PhT6+EIAAAEgSURBVHjaxNO/S4JBGMDxe3+AYlP+ACUh"
				+ "GqxdcGkRmiRIXSOXIGhsSYj+hYZAcFCw6Z1CcGqKhqJFCkc3da2EELfAOr8nRyjW2xsO3suH47j3"
				+ "nvd5nuM1pJRikWGKBYe9dhv4z/sxHCELHy5t+eW5hCQusKPXDtoE8HQ4gxIS6OAc15MSPGSwh4pO"
				+ "v4VjPH/3QH66BtjFFcK4xyF6M00kgM0cwgcGU3tp1PThO+yjP3eNciRP0MUNNiH0XEEUTRTQ13sz"
				+ "TDJYRwDbKCKGMrbQwQFeVKk/MYKOtUEmT7oMVd8rUhgijwe3Jln+nDEUUqwgjVXEYeIUdaF67MLk"
				+ "GtXTwEhdqeagOrX+leXLTiK94w0RPOIMg7++rhhL/xvHAgwAT63teIFlPMEAAAAASUVORK5CYII=';");
		// Fail Image base64 code
		test_print_out.println("var failImage = 'data:image/png;base64,"
				+ "iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJ"
				+ "bWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdp"
				+ "bj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6"
				+ "eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0"
				+ "NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJo"
				+ "dHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlw"
				+ "dGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAv"
				+ "IiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RS"
				+ "ZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpD"
				+ "cmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNl"
				+ "SUQ9InhtcC5paWQ6NDFEQ0U3M0U5M0JEMTFFM0E2MjVBRjkzNURBOUE1NTMiIHhtcE1NOkRvY3Vt"
				+ "ZW50SUQ9InhtcC5kaWQ6NDFEQ0U3M0Y5M0JEMTFFM0E2MjVBRjkzNURBOUE1NTMiPiA8eG1wTU06"
				+ "RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo0MURDRTczQzkzQkQxMUUzQTYy"
				+ "NUFGOTM1REE5QTU1MyIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo0MURDRTczRDkzQkQxMUUz"
				+ "QTYyNUFGOTM1REE5QTU1MyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1w"
				+ "bWV0YT4gPD94cGFja2V0IGVuZD0iciI/PvYjEN4AAAGQSURBVHjafNPLK0RRHAfwczlYoZTSjA2r"
				+ "CTULOxak8VYsbCxQ7kbKLJRHbJUNFpSyYxb8BxaEhVAWLBRKkZRiQbGZ+5i5vr87v5tzTzN+9ek8"
				+ "7nndc3/XcExzQgiRFLl4hBl4FYWjCYahC9JSZDImKnF+SOUTzBeY3AaT0A41kJXCdVu0QXO8C53s"
				+ "nftqYYknB3ELC1I4ziEfR40+uIZOiMAeVCvPZ2ENPOnZ9igqKejWFqGJF1ABBvfRiWj8UTBICtv+"
				+ "QNkDvbAOMWWRSi49OIExeFN3MexEItSGRVjWTuNCK1zptyqFZalt2qk+z+1L2OJFQhOkF15gg28/"
				+ "XzTDKkyHjmzFgxTw329Xm0RJVQYrSl8/HPwtEPPvrJE/W6ky8B4auJ7i26d45v50cAclKPe1yVkY"
				+ "UdqUreUwBHUwDtu5E0SjHSiPtaNvKv9HEFXwwAl1x9nqJ1JEG/jNaavHJwxyctEr0C9wXoRUPoMv"
				+ "ECwJP0pbdQk7XB+gkn6mF/6+lIk3cCr+jynevZgavwIMAHdffyFa7zUMAAAAAElFTkSuQmCC';");
		// Skip Image base64 code
		test_print_out.println("var skipImage = 'data:image/png;base64,iVBORw0KGgoAAAANSUh"
				+ "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';");

		test_print_out.println("var passSrc = $(\".pass\");");
		test_print_out.println("setImage(passSrc, passImage);");
		test_print_out.println("var failSrc = $(\".fail\");");
		test_print_out.println("setImage(failSrc, failImage);");
		test_print_out.println("var skipSrc = $(\".skip\");");
		test_print_out.println("setImage(skipSrc, skipImage);");
		test_print_out.println("var screenshotSrc = $(\".screenshot\");");
		test_print_out.println("setImage(screenshotSrc, screenshotImage);");
		test_print_out.println("});</script>");
	}

	/**
	 * 
	 * Create Pie chart for the test suite exception
	 * 
	 * @param pw
	 */
	protected void addingExceptionPieChart(PrintWriter pw) {

		int assertion = (individualExceptionCount.containsKey("AssertionError"))
				? individualExceptionCount.get("AssertionError")
				: 0;
		int timeout = (individualExceptionCount.containsKey("TimeoutException"))
				? individualExceptionCount.get("TimeoutException")
				: 0;
		int webdriver = (individualExceptionCount.containsKey("WebDriverException"))
				? individualExceptionCount.get("WebDriverException")
				: 0;
		int npe = (individualExceptionCount.containsKey("NullPointerException"))
				? individualExceptionCount.get("NullPointerException")
				: 0;
		int ser = (individualExceptionCount.containsKey("StaleElementReferenceException"))
				? individualExceptionCount.get("StaleElementReferenceException")
				: 0;
		int other = (individualExceptionCount.containsKey("Others")) ? individualExceptionCount.get("Others") : 0;
		int totalCount = assertion + timeout + webdriver + npe + ser + other;

		int assertionPercent = (assertion > 0) ? Math.round(assertion * 100 / totalCount) : 0;
		int timeoutPercent = (timeout > 0) ? Math.round(timeout * 100 / totalCount) : 0;
		int webdriverPercent = (webdriver > 0) ? Math.round(webdriver * 100 / totalCount) : 0;
		int npePercent = (npe > 0) ? Math.round(npe * 100 / totalCount) : 0;
		int serPercent = (ser > 0) ? Math.round(ser * 100 / totalCount) : 0;
		int otherPercent = (other > 0) ? Math.round(other * 100 / totalCount) : 0;

		// Javascript function to create pie-chart
		pw.println("<script>window.onload=function(){ assertion=" + assertionPercent + "; timeout=" + timeoutPercent
				+ "; webdriver=" + webdriverPercent + "; npe=" + npePercent + "; ser=" + serPercent + "; other="
				+ otherPercent + ";}</script>");
		pw.println("<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>");
		pw.println("<script type=\"text/javascript\">");
		pw.println("google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});");
		pw.println("google.setOnLoadCallback(drawChart);");
		pw.println("function drawChart(){");
		pw.println(
				"var data = google.visualization.arrayToDataTable([['Test_report', 'Percentage'],['AssertionError', assertion],['TimeoutException', timeout],['WebDriverException', webdriver],['NullPointerException', npe],['StaleElementReferenceException', ser],['Other', other]]);");
		pw.println("var options = {");
		pw.println("legend: { position : 'right', textStyle: {color: 'black', fontSize: 14}},");
		pw.println("chartArea:{left:0,top:0,width:\"100%\",height:\"100%\"},");
		pw.println("is3D: true,");
		pw.println("backgroundColor: 'transparent',");
		pw.println(
				"slices: {0: {color: '#CCAB7A'}, 1:{color: '#FFEB99'}, 2:{color: '#D9B2B2'}, 3: {color: '#6B6BB2'}, 4:{color: '#FFA8A8'}, 5:{color: '#EBEB99'}},");
		pw.println("pieSliceTextStyle: {color: 'black', fontName: 'Arial', fontSize: '16'}};");

		pw.println("var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));");
		pw.println("chart.draw(data, options);}");
		pw.println("</script>");
	}

	/**
	 * 
	 * Create left side percentage table and exception filters for test suite
	 * exception test report
	 * 
	 * @param pw
	 */
	protected void addingExceptionPercentTable(PrintWriter pw) {

		int assertion = (individualExceptionCount.containsKey("AssertionError"))
				? individualExceptionCount.get("AssertionError")
				: 0;
		int timeout = (individualExceptionCount.containsKey("TimeoutException"))
				? individualExceptionCount.get("TimeoutException")
				: 0;
		int webdriver = (individualExceptionCount.containsKey("WebDriverException"))
				? individualExceptionCount.get("WebDriverException")
				: 0;
		int npe = (individualExceptionCount.containsKey("NullPointerException"))
				? individualExceptionCount.get("NullPointerException")
				: 0;
		int ser = (individualExceptionCount.containsKey("StaleElementReferenceException"))
				? individualExceptionCount.get("StaleElementReferenceException")
				: 0;
		int other = (individualExceptionCount.containsKey("Others")) ? individualExceptionCount.get("Others") : 0;

		// Left Table-Executor Details
		pw.println(
				"<div id=\"exception-left-table\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
		pw.println("<tr><td class=\"left-active-color\">AssertionError</td><td class=\"right-active-color\">"
				+ assertion + "</td></tr>");

		pw.println("<tr><td class=\"left-active-color\">TimeoutException</td><td class=\"right-active-color\">"
				+ timeout + "</td></tr>");

		pw.println(
				"<tr><td class=\"left-active-color\">StaleElementReferenceException</td><td class=\"right-active-color\">"
						+ ser + "</td></tr>");

		pw.println("<tr><td class=\"left-active-color\">WebDriverException</td><td class=\"right-active-color\">"
				+ webdriver + "</td></tr>");

		pw.println("<tr><td class=\"left-active-color\">NullPointerException</td><td class=\"right-active-color\">"
				+ npe + "</td></tr>");

		pw.println("<tr><td class=\"left-active-color\">Other</td><td class=\"right-active-color\">" + other
				+ "</td></tr>");

		pw.println("<tr><td class=\"left-active-color\">Overall Failed Percentage</td><td class=\"right-active-color\">"
				+ 23 + "</td></tr>");

		pw.println("</table></div>");

		// PieChart Report
		pw.println("<div id=\"exception-right-piereport\">");
		pw.println("<div id=\"piechart_3d\" style=\"width: 800px; height: 250px;  position: relative;\"></div></div>");

		// Exception filters
		pw.println("<div id=\"exception-filters\"><div id = \"filters\">");
		pw.println(
				"<input type='checkbox' id='NullPointerException' class='NullPointerException' checked='true' onClick='ExceptionToggle(this)'/>");
		pw.println("<span> NullPointerException</span>");
		pw.println(
				"<input type='checkbox' id='TimeoutException' class='TimeoutException' checked='true' onClick='ExceptionToggle(this)'/>");
		pw.println("<span> TimeoutException</span>");
		pw.println(
				"<input type='checkbox' id='AssertionError' class='AssertionError' checked='true' onClick='ExceptionToggle(this)'/>");
		pw.println("<span> AssertionError</span>");
		pw.println(
				"<input type='checkbox' id='StaleElementReferenceException' class='StaleElementReferenceException' checked='true' onClick='ExceptionToggle(this)'/>");
		pw.println("<span> StaleElementReferenceException</span>");
		pw.println(
				"<input type='checkbox' id='WebDriverException' class='WebDriverException' checked='true' onClick='ExceptionToggle(this)'/>");
		pw.println("<span> WebDriverException</span>");
		pw.println("<input type='checkbox' id='Others' class='Others' onClick='ExceptionToggle(this)'/>");
		pw.println("<span> Others</span>");
		pw.println("</div></div>");

	}

	/**
	 * 
	 * To append the exception of each test method
	 *
	 */
	protected void appendingTestMethodsException(LinkedHashMap<IResultMap, ArrayList<ITestNGMethod>> failedMethodMap) {

		String testName;
		String desc;
		Throwable throwable;
		String testData;
		String exceptionBaseClass = null;
		IResultMap failedTests;
		ArrayList<ITestNGMethod> methodList;
		int rowCount = 2;
		for (Entry<IResultMap, ArrayList<ITestNGMethod>> map : failedMethodMap.entrySet()) {
			failedTests = map.getKey();
			methodList = map.getValue();
			for (ITestNGMethod individualMethodName : methodList) {
				Set<ITestResult> testResultSet = failedTests.getResults(individualMethodName);
				Iterator<ITestResult> it = testResultSet.iterator();
				while (it.hasNext()) {
					ITestResult testResult = it.next();
					testName = testResult.getName();
					desc = individualMethodName.getDescription();
					throwable = testResult.getThrowable();
					exceptionBaseClass = throwable.getClass().getSimpleName();
					exceptionBaseClass = (exceptionFilters.contains(exceptionBaseClass)) ? exceptionBaseClass
							: "Others";

					if (rowCount % 2 == 0) {
						test_print_out.println("<tr class=\"active-color\" id=\"" + exceptionBaseClass + "\">");
					} else {
						test_print_out.println("<tr class=\"inactive-color\" id=\"" + exceptionBaseClass + "\">");
					}

					test_print_out.println("<td>" + testName + "</td><td>" + desc + "</td>");
					try {
						testData = (String) testResult.getParameters()[1];
						test_print_out.println("<td class=\"results\"><a  href=\"./" + testScreenshotPath + "/"
								+ testName + "_" + testData + ".png\" target=\"_blank\"><img class=\"screenshot\" src="
								+ " style=\"cursor: pointer;\"/></a></td>");
					} catch (ArrayIndexOutOfBoundsException AIOB) {
						test_print_out.println("<td class=\"results\"><a  href=\"./" + testScreenshotPath + "/"
								+ testName + ".png\" target=\"_blank\"><img class=\"screenshot\" src="
								+ " style=\"cursor: pointer;\"/></a></td>");
					}
					test_print_out.println("<td><div class=\"show-exception\">" + truncateStackTrace(throwable, 1)[0]
							+ "</div></td></tr>");
					test_print_out.println("<tr class=\"exception\">");
					test_print_out.println("<td colspan=\"5\">");
					test_print_out.println(truncateStackTrace(throwable, 2)[1]);
					test_print_out.println("</td></tr>");
					rowCount += 1;
				}

			}
		}

	}

	/**
	 * 
	 * Exception to be shown in the report
	 * 
	 * 
	 */
	protected void exceptionFiltersInReport() {
		exceptionFilters = new ArrayList<String>();
		exceptionFilters.add("NullPointerException");
		exceptionFilters.add("TimeoutException");
		exceptionFilters.add("StaleElementReferenceException");
		exceptionFilters.add("AssertionError");
		exceptionFilters.add("WebDriverException");

	}

	/**
	 * 
	 * Get the count of individual exception
	 * 
	 * @param exceptionType
	 */
	protected void testExceptionCount(String exceptionType) {

		if (exceptionFilters.contains(exceptionType)) {
			if (!individualExceptionCount.containsKey(exceptionType)) {
				individualExceptionCount.put(exceptionType, 1);
			} else {
				individualExceptionCount.put(exceptionType, individualExceptionCount.get(exceptionType) + 1);
			}
		} else {
			if (!individualExceptionCount.containsKey("Others")) {
				individualExceptionCount.put("Others", 1);
			} else {
				individualExceptionCount.put("Others", individualExceptionCount.get("Others") + 1);
			}
		}
	}

	/**
	 * 
	 * Add a row to display the test data of the method
	 * 
	 * @param testName
	 * @param testData
	 * @param url
	 */
	protected void addingTestDataRow(String testName, String testData, String url) {
		test_print_out.println("<tr class=\"testdata\">");
		test_print_out.println("<td colspan=\"2\">" + testName + " for " + testData);
		test_print_out.println("<td colspan=\"2\">" + url);
		test_print_out.println("</td></tr>");
	}

	/**
	 * 
	 * To append the result of each method of the test
	 * 
	 * @param methodName
	 * @param testName
	 * @param desc
	 * @param result
	 * @param throwable
	 * @throws ALMServiceException
	 * 
	 */
	protected void appendingTestMethodsResult(int method_count_of_class, ITestNGMethod methodName,
			ITestResult testResult, String result) {

		if (method_count_of_class % 2 == 0) {
			test_print_out.println("<tr class=\"active-color\">");
		} else {
			test_print_out.println("<tr class=\"inactive-color\">");
		}
		String test_id = "";
		String test_case_name = "";
		try {
			test_id = methodName.getConstructorOrMethod().getMethod().getAnnotation(testId.class).test_id();
			test_case_name = methodName.getConstructorOrMethod().getMethod().getAnnotation(testCaseName.class)
					.test_case_name();
		} catch (NullPointerException npe) {
			log.error("Exception in the Test Id annotations for the test name: " + testResult.getName() + ": "
					+ npe.getLocalizedMessage());
		}

		test_print_out.println("<td>" + testResult.getName() + "</td><td>" + methodName.getDescription() + "</td><td>"
				+ test_id + ":" + test_case_name + "</td>");
		if (result.equalsIgnoreCase("PASS")) {
			test_print_out.println("<td class=\"results\"><img id=\"pass\" class=\"pass\" src="
					+ "></td><td class=\"results\"></td><td></td></tr>");
			alm_instance.updateResults("PASS", test_id, test_case_name);
		} else if (result.equalsIgnoreCase("FAIL")) {
			test_print_out.println("<td class=\"results\"><img id=\"fail\" class=\"fail\" src=" + "></td>");
			test_print_out.println("<td class=\"results\"><a href=\"./" + testScreenshotPath + "/"
					+ testResult.getName() + ".png\" target=\"_blank\"><img class=\"screenshot\" src="
					+ " style=\"cursor: pointer;\"/></a></td>");
			test_print_out.println("<td><div class=\"show-exception\">"
					+ truncateStackTrace(testResult.getThrowable(), 1)[0] + "</div></td></tr>");
			test_print_out.println("<tr class=\"exception\">");
			test_print_out.println("<td colspan=\"5\">");
			test_print_out.println(truncateStackTrace(testResult.getThrowable(), 2)[1]);
			test_print_out.println("</td></tr>");
			testExceptionCount(testResult.getThrowable().getClass().getSimpleName());
			alm_instance.updateResults("FAIL", test_id, test_case_name);
		} else {
			test_print_out.println("<td class=\"results\"><img id=\"skip\" class=\"skip\" src=" + "></td>");
			test_print_out.println("<td class=\"results\"><a href=\"./" + testScreenshotPath + "/"
					+ testResult.getName() + ".png\" target=\"_blank\"><img class=\"screenshot\" src="
					+ " style=\"cursor: pointer;\"/></a></td><td></td>");
			test_print_out.println("</tr>");
			alm_instance.updateResults("NO_RUN", test_id, test_case_name);
		}

	}

	/**
	 * To close HTML Test report
	 * 
	 */
	protected void endTestHTMLReport() {

		test_print_out.println("</table></div></div>");
		test_print_out.println("</body></html>");
		test_print_out.flush();
		test_print_out.close();
	}

	/**
	 * 
	 * To truncate the exception stack trace to display in the report
	 * 
	 * @param throwable
	 * @param maxLines
	 * @return StringWriter
	 * 
	 */
	protected String[] truncateStackTrace(Throwable throwable, int maxLines) {

		StringWriter writer = new StringWriter();
		if (throwable != null) {
			throwable.printStackTrace(new PrintWriter(writer));
			String[] lines = writer.toString().split("\n");
			StringBuilder causeMsg = new StringBuilder();
			StringBuilder fullStackTrace = new StringBuilder();
			for (int i = 0; i < maxLines; i++) {
				causeMsg.append(lines[i]).append("\n");
			}
			for (String s : lines) {
				fullStackTrace.append(s).append("\n");
			}
			return new String[] { causeMsg.toString().replace("<", " ").replace(">", " "),
					fullStackTrace.toString().replace("<", " ").replace(">", " ") };
		}
		return null;
	}

}
