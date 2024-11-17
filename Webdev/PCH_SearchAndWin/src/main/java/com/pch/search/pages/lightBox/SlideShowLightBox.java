package com.pch.search.pages.lightBox;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.Common;
import com.pch.search.utilities.CustomLogger;
import com.pch.search.utilities.HtmlElement;

public class SlideShowLightBox extends LightBox {

	private String blockingOverLayXpath ="//div[contains(@class,'fancybox-overlay fancybox-overlay-fixed')]";
	private String lightBoXPath = "//div[contains(@class,'fancybox-outer')]";
	private List<HtmlElement> viewedImageandAdElements = new ArrayList<HtmlElement>();

	private boolean isBlockingOverLayPresent(){
		return (driver.getCountOfElementsWithXPath(blockingOverLayXpath)!=0);
	}

	private HtmlElement blockingOverLay(){
		return driver.findElement(By.xpath(blockingOverLayXpath));
	}

	public SlideShowLightBox(BrowserDriver driver){
		this.driver=driver;
	}

	@Override
	protected HtmlElement locateLightBox() {
		return blockingOverLay().findElement(By.xpath(lightBoXPath));
	}

	@Override
	public void dismissLightBox() {
		/**
		 * Close Welcome to FrontPage message.		
		 */	

		if(isBlockingOverLayPresent()){
			HtmlElement blkngOverLay = blockingOverLay();
			HtmlElement lightBox = locateLightBox();		
			lightBox.findElement(By.xpath("//a[@title='Close']")).click();
			if(isBlockingOverLayPresent()){
				blkngOverLay.waitTillNotPresent();
			}
		}
	}

	public int getTotalSlideCount(){
		HtmlElement lightBox = locateLightBox();
		return Integer.parseInt(Common.subString(lightBox.findElement(By.id("ajaxGalleryCount")).getText().trim(),"\\d+$"));
	}

	public int getCurrentSlideNumber(){
		HtmlElement lightBox = locateLightBox();
		return Integer.parseInt(Common.subString(lightBox.findElement(By.id("ajaxGalleryCount")).getText().trim(),"\\d+(?=\\/)"));
	}

	public String getCurrentSlideType(){
		/*int indexOfLastViewed =1;
		if(viewedImageandAdElementsindex.size()!=0){
			indexOfLastViewed = viewedImageandAdElementsindex.get(viewedImageandAdElementsindex.size()-1);
		}
		HtmlElement lightBox = locateLightBox();
		HtmlElement currentlyViewedSlide = lightBox.findElement(By.xpath(
				String.format("//div[@class='rsSlide  slideViewed'][%d]",indexOfLastViewed)));*/
		
		HtmlElement currentlyViewedSlide = viewedImageandAdElements.get(viewedImageandAdElements.size()-1);
		
		if(currentlyViewedSlide.getCountOfElementsWithXPath("descendant::iframe[contains(@id,'google_ads')]")>0){
			CustomLogger.log("Current slide is Ad");
			return "AD";
		}
		CustomLogger.log("Current slide is Image.");
		return "IMAGE";
	}

	public void updateViewed(){
		HtmlElement lightBox = locateLightBox();
		HtmlElement adImageContainer = lightBox.findElement(By.className("rsContainer"));
		List<HtmlElement> viewedElementsOnPage = adImageContainer.findElements(By.xpath("//div[@class='rsSlide  slideViewed']"));
		if(viewedElementsOnPage.size()==1){
			viewedImageandAdElements.clear();
			viewedImageandAdElements.add(viewedElementsOnPage.get(0));
			return;
		}else{
			HtmlElement lastViewedElement = viewedImageandAdElements.get(viewedImageandAdElements.size()-1);
			int xOffsetOfLastViewed = Integer.parseInt(Common.subString(lastViewedElement.getCssValue("left"),"\\d+"));
			int previousMaxOffset = Integer.MAX_VALUE;
			HtmlElement currentlyViewedElement  = null;
			for(HtmlElement element:viewedElementsOnPage){
				int xOffsetElement = Integer.parseInt(Common.subString(element.getCssValue("left"),"\\d+"));
				if((xOffsetElement > xOffsetOfLastViewed) && (xOffsetElement < previousMaxOffset)){
					previousMaxOffset = xOffsetElement;
					currentlyViewedElement=element;
				}				
			}
			viewedImageandAdElements.add(currentlyViewedElement);
		}
	}

	public void gotoNextSlide(){
		HtmlElement lightBox = locateLightBox();
		/*HtmlElement adImageContainer = lightBox.findElement(By.className("rsContainer"));
		List<HtmlElement> elements = adImageContainer.findElements(By.xpath("//div[contains(@class,'rsSlide')]"));
		int i=0;
		for(HtmlElement imageORad:elements){
			i++;
			if(imageORad.getAttribute("class").contains("slideViewed") && !viewedImageandAdElementsindex.contains(i)){
				viewedImageandAdElementsindex.add(i);
			}
		}*/		

		CustomLogger.log("Navigating to next slide");
		lightBox.findElement(By.xpath("//div[@class='rsArrow rsArrowRight']")).click();
		Common.sleepFor(2000);

		updateViewed();
		/*CustomLogger.log("Updating viewed slide indexes");
		adImageContainer = lightBox.findElement(By.className("rsContainer"));
		elements = adImageContainer.findElements(By.xpath("//div[contains(@class,'rsSlide')]"));
		i=0;
		for(HtmlElement imageORad:elements){
			i++;
			if(imageORad.getAttribute("class").contains("slideViewed") && 
				!viewedImageandAdElementsindex.contains(i)){
				viewedImageandAdElementsindex.add(i);
			}
		}*/	
	}

}
