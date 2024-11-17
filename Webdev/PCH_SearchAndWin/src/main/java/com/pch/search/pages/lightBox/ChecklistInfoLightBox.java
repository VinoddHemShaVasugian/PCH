package com.pch.search.pages.lightBox;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;

import com.pch.search.utilities.BrowserDriver;
import com.pch.search.utilities.HtmlElement;

public class ChecklistInfoLightBox extends LightBox {
	
	public ChecklistInfoLightBox(BrowserDriver driver){
		this.driver=driver;
	}
	private String blockingOverLayXpath = "//div[contains(@class,'fancybox-overlay fancybox-overlay-fixed')]";

	
	private boolean isBlockingOverLayPresent(){
		return (driver.getCountOfElementsWithXPath(blockingOverLayXpath)!=0);
	}
	
	private HtmlElement overlayElement(){
		return driver.findElement(By.xpath(blockingOverLayXpath));
	}
	
	private HtmlElement closeButtonElement(){
		return locateLightBox().findElement(By.xpath("//a[@title='Close']"));
	}
	
		
	@Override
	protected HtmlElement locateLightBox() {
		return overlayElement().findElement(By.className("fancybox-skin"));
	}
	
	public List<String> getInfoAboutCheckList(){
		List<HtmlElement> elements=locateLightBox().findElements(By.xpath("//p"));
		List<String> info = new ArrayList<String>();
		for(HtmlElement element:elements){
			info.add(element.getAttribute("textContent"));
		}
		return info;
	}

	@Override
	public void dismissLightBox() {
		HtmlElement blkngOverLay = overlayElement();
		closeButtonElement().click();
		if(isBlockingOverLayPresent()){
			blkngOverLay.waitTillNotVisible();
		}
	}

}
