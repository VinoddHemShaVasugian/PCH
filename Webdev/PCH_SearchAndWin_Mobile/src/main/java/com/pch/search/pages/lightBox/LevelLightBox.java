package com.pch.search.pages.lightBox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.Environment;
import com.pch.search.utilities.HtmlElement;

public class LevelLightBox extends LightBox {
	private String lastDisplayedUserLevel;

	public LevelLightBox(BrowserDriver driver) {
		this.driver = driver;
	}

	private HtmlElement preCelebrationLoader() {
		return driver.findElement(By.id("preCelebrationLoader"));
	}

	/*
	 * private HtmlElement levelUpCelebrationFrameHolder(){ return
	 * driver.findElement(By.id("levelUpCelebrationIframeHolder")); }
	 */

	@Override
	protected HtmlElement locateLightBox() {
		try {
			HtmlElement preCelebrationLoader = preCelebrationLoader();
			preCelebrationLoader.waitTillNotVisible();
			if (Environment.getEnvironmentToRun().equalsIgnoreCase("YES")) {
				String frame_src = driver.findElement(By.xpath("//iframe[@id='levelUpCelebrationIframe']"))
						.getAttribute("src");
				driver.get(frame_src);
			} else {
				driver.switchtoFrame("levelUpCelebrationIframe");
			}

			String levelImageXPath = "//div[@id='congratsBox']/descendant::div[@class='level']/img";
			String continueButtonXPath = "//div[@id='congratsBox']/descendant::span[text()='Continue']";
			driver.findElement(By.xpath(continueButtonXPath));
			CustomLogger.log("Level LightBox is present");
			if (driver.getCountOfElementsWithXPath(levelImageXPath) > 0) {
				HtmlElement levelImage = driver.findElement(By.xpath(levelImageXPath));
				levelImage.waitForVisible();
				String imgSrcAttribute = levelImage.getAttribute("src");

				// http://www.qa.pch.com/pch_media/images/levels/lightbox/congrats-level-bronze.png
				lastDisplayedUserLevel = imgSrcAttribute.substring(imgSrcAttribute.lastIndexOf("-") + 1,
						imgSrcAttribute.indexOf(".png"));
				CustomLogger.log("Level Ligthbox, displayed status was - " + lastDisplayedUserLevel);
			}
			return (driver.findElement(By.xpath("//div[@id='congratsBox']")));
		} catch (WebDriverException wde) {
			CustomLogger.log("Light box not found");
			if (!Environment.getEnvironmentToRun().equalsIgnoreCase("YES")) {
				driver.switchTo().defaultContent();
			}
			return null;
		}
	}

	/**
	 * Close the status light box.
	 * 
	 * @return The current level (Bronze, Silver, Gold etc) of user.
	 */
	// @Override
	public void dismissLightBox() {
		/*
		 * if(!isLightBoxPresent()){ return; }
		 */
		boolean isCelebrationFrameHolderPresent = true;
		do {
			HtmlElement lightBox = locateLightBox();
			if (lightBox != null) {
				lightBox.waitForVisible();
				HtmlElement dismissBtn = lightBox.findElement(By.xpath("//a[@class='dismiss_lb']/img"));
				CustomLogger.log("Dismissing level light box by clicking 'X' button.");
				dismissBtn.click();
				Common.sleepFor(3000);
				CustomLogger.log("Waiting for Light box to leave.");
				// dismissBtn.waitTillNotPresent();
				CustomLogger.log("Light box removed.");

				/*
				 * If Dismiss Button is not leaving, it means Special reward
				 * message is being displayed. So closing it first.
				 */
				try {
					String specialRewardElementXpath = "//div[@id='BAMContainer']/descendant::div[@class='level_msg']";
					if (driver.getCountOfElementsWithXPath(specialRewardElementXpath) > 0) {
						CustomLogger.log("Light box didn't leave, checking special reward message");
						HtmlElement specialRewardElement = driver.findElement(By.xpath(specialRewardElementXpath));
						specialRewardElement.click();
						CustomLogger.log("Closed special reward message");
					}
				} catch (Exception e) {
					CustomLogger.log(e.toString());

					// dismissBtn.waitTillNotPresent();
				}
			} else if (isTechnicalDifficultyPresent()) {
				CustomLogger.log("As technical difficulty is present, so waiting for 5 seconds");
				Common.sleepFor(5000);
			}
			if (!Environment.getEnvironmentToRun().equalsIgnoreCase("YES")) {
				driver.switchTo().defaultContent();
			}
			isCelebrationFrameHolderPresent = driver
					.getCountOfElementsWithXPath("//div[@id='levelUpCelebrationIframeHolder']") != 0;
		} while (isCelebrationFrameHolderPresent);
	}

	public void dismissLightBox(boolean closeSpecialRewards) {
		/*
		 * if(!isLightBoxPresent()){ return; }
		 */
		if (!closeSpecialRewards) {
			dismissLightBox();
			return;
		}
		boolean isCelebrationFrameHolderPresent = true;
		do {
			HtmlElement lightBox = locateLightBox();
			if (lightBox != null) {
				lightBox.waitForVisible();
				HtmlElement dismissBtn = lightBox.findElement(By.xpath("//a[@class='dismiss_lb']"));
				CustomLogger.log("Dismissing level light box by clicking 'X' button.");
				dismissBtn.click();
				// try{
				CustomLogger.log("Waiting for Light box to leave.");
				// dismissBtn.waitTillNotPresent();
				// CustomLogger.log("Light box removed.");
				// }catch(TimeoutException toe){
				/*
				 * If Dismiss Button is not leaving, it means Special reward
				 * message is being displayed. So closing it first.
				 */
				CustomLogger.log("Light box didn't leave, checking special reward message");
				HtmlElement specialRewardElement = driver
						.findElement(By.xpath("//div[@id='BAMContainer']/descendant::div[@class='level_msg']"));
				specialRewardElement.click();
				CustomLogger.log("Closed special reward message");
				dismissBtn.waitTillNotPresent();
				// }
			} else if (isTechnicalDifficultyPresent()) {
				CustomLogger.log("As technical difficulty is present, so waiting for 5 seconds");
				Common.sleepFor(5000);
			}
			if (!Environment.getEnvironmentToRun().equalsIgnoreCase("YES")) {
				driver.switchTo().defaultContent();
			}
			isCelebrationFrameHolderPresent = driver
					.getCountOfElementsWithXPath("//div[@id='levelUpCelebrationIframeHolder']") != 0;
		} while (isCelebrationFrameHolderPresent);
	}

	public boolean isLightBoxPresent() {
		HtmlElement lightBox = locateLightBox();
		boolean result = false;
		if (lightBox != null) {
			result = lightBox.isDisplayed();
		}
		if (!Environment.getEnvironmentToRun().equalsIgnoreCase("YES")) {
			driver.switchTo().defaultContent();
		}
		return result;
	}

	public boolean isTechnicalDifficultyPresent() {
		// Hardcoding as of now to false.
		return false;
		// CustomLogger.log("Tecnical Difficulty Present");
	}

	public String getLastDisplayedUserStatus() {
		return lastDisplayedUserLevel;
	}

}