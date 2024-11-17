package com.pch.automation.utilities;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.pages.PageObject;

public class NFSPHelper extends PageObject {

	/**
	 * Instantiates a SerpPage page.
	 *
	 * @param driver
	 */
	public NFSPHelper(WebDriver driver) {
		super(driver);
	}

	/**
	 * Get NFSP Segment from the given JSON string.
	 * 
	 * @param json_string - JSON in string
	 * @param source_name - Name which access id/segment has to be retrieve
	 * @param key_name    - Some cases access id/segment has key name. If it has can
	 *                    be pass here. It's a variable argument.
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public String getNfspSegmentFromJson(String jsonString, String sourceName, String... keyName)
			throws JsonProcessingException, IOException {
		String segment = (keyName.length > 0
				? WebServiceClient.getInstance().jsonParser(jsonString, sourceName, keyName[0])
				: WebServiceClient.getInstance().jsonParser(jsonString, sourceName)).replace("[", "").replace("]", "")
						.replace("\"", "").trim();
		if (keyName.length > 0)
			return segment;
		else {
			return segment.contains(",")
					? AppConfigLoader.deviceType.equalsIgnoreCase("Desktop") ? segment.split(",")[0]
							: segment.split(",")[2]
					: segment;
		}
	}

	/**
	 * Get NFSP Segment from the Page Source. If the parameter position value is 2,
	 * it will return the second NFSP Segment value which is present in the view
	 * page source.
	 * 
	 * @param position
	 * @return
	 */
	public String getNfspSegmentFromPageSource(int position) {
		waitABit(3000);
		String pageSource = getDriver().getPageSource();
		for (int pos = 0; pos < position; pos++) {
			pageSource = pageSource.substring(pageSource.indexOf("\"segment\"") + 10, pageSource.length());
		}
		pageSource = pageSource.substring(1, pageSource.indexOf(",") - 1).replace("\"", "");
		return pageSource;
	}

	/**
	 * Get NFSP Source from the Page Source. If the parameter position value is 2,
	 * it will return the second NFSP Source value which is present in the view page
	 * source.
	 * 
	 * @param position
	 * @return
	 */
	public String getNfspSourceFromPageSource(int position) {
		waitABit(3000);
		String pageSource = getDriver().getPageSource();
		for (int pos = 0; pos < position; pos++) {
			pageSource = pageSource.substring(pageSource.indexOf("\"source\":") + 9, pageSource.length());
		}
		pageSource = pageSource.substring(1, pageSource.indexOf(",") - 1);
		return pageSource;
	}
}
