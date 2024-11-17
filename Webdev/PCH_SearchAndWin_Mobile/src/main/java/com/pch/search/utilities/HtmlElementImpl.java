package com.pch.search.utilities;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HtmlElementImpl implements HtmlElement {
	private WebElement element=null;
	private WebDriver driver = null;
	private int timeout=30;

	public HtmlElementImpl(WebElement element,BrowserDriver driver){
		this(element,driver.getwrappedDriver());
	}

	private HtmlElementImpl(WebElement element,WebDriver driver){
		this.element=element;
		this.driver=driver;
	}


	public void click() {
		if(Environment.getParam("logLevel").toUpperCase().equals("DEBUG")){
			CustomLogger.log("clicking on: "+element.toString());
		}
		waitForVisible();
		try{
			element.click();
		}catch(WebDriverException e){
			if(Environment.getParam("logLevel").toUpperCase().equals("DEBUG")){
				CustomLogger.log("Element was not clickable");
				CustomLogger.log("Tring again to click on Element: "+element);
			}

			moveToElementAndClick();	
		}

	}

	public void submit() {
		element.submit();

	}

	public void sendKeys(CharSequence... keysToSend) {
		if(Environment.getParam("logLevel").toUpperCase().equals("DEBUG")){
			CustomLogger.log("sending keys in: "+element);
		}
		waitForVisible();
		try{
			element.sendKeys(keysToSend);
		}catch(InvalidElementStateException iese){
			Common.sleepFor(2000);
			element.sendKeys(keysToSend);
		}

	}

	public void clear() {
		waitForVisible();
		CustomLogger.log("Clearing the text");
		try{
			element.clear();
		}catch(InvalidElementStateException iese){
			Common.sleepFor(2000);
			element.clear();
		}

	}

	public String getTagName() {
		return element.getTagName();
	}

	public String getAttribute(String name) {
		return element.getAttribute(name);
	}

	public boolean isSelected() {
		return element.isSelected();
	}

	public boolean isEnabled() {
		return element.isEnabled();
	}

	public String getText() {
		return element.getText();
	}

	public List<HtmlElement> findElements(final By by) {
		//List<HtmlElement> elements = new ArrayList<HtmlElement>();
		/*for(WebElement e: element.findElements(by)){
			elements.add(new HtmlElementImpl(e,driver));
		}*/

		ExpectedCondition<List<HtmlElement>> customCondition = new ExpectedCondition<List<HtmlElement>>() {
			List<HtmlElement> htmlElements = new ArrayList<HtmlElement>();
			public List<HtmlElement> apply(WebDriver driver) {
				List<WebElement> elements = element.findElements(by);
				if(elements.size()>0){
					for(WebElement we:elements){
						htmlElements.add(new HtmlElementImpl(we, driver));
					}
					return htmlElements;
				}else{
					return null;
				}
			}
		};

		try{
			return new WebDriverWait(driver, 10).until(customCondition);
		}catch(TimeoutException toe){
			return new ArrayList<HtmlElement>();
		}


	}

	public HtmlElement findElement(By by) {			
		return findElement(by,20);
	}
	
	public HtmlElement findElement(final By by, int waitingTimeInSeconds){
		ExpectedCondition<HtmlElement> customCondition = new ExpectedCondition<HtmlElement>() {

			public HtmlElement apply(WebDriver driver) {
				return new HtmlElementImpl(element.findElement(by),driver);
			}
		};

		return new WebDriverWait(driver, waitingTimeInSeconds).until(customCondition);
	}

	public boolean isDisplayed() {
		return element.isDisplayed();
	}

	public Point getLocation() {
		return element.getLocation();
	}

	public Dimension getSize() {
		return element.getSize();
	}

	public String getCssValue(String propertyName) {
		return element.getCssValue(propertyName);
	}

	public void scrollDownAndClick() {		
		while(true){
			try{
				element.click();
				return;
			}catch(WebDriverException wde){
				String exceptionMesssage = wde.getMessage();
				if(exceptionMesssage.contains("Element is not clickable at point")||
					exceptionMesssage.contains("element not visible")){
					((JavascriptExecutor)driver).executeScript("window.scrollBy(0,50)", new Object[0]);
					//driver.executeScript("window.scrollBy(0,50)", new Object[0]);
				}else{
					CustomLogger.logException(wde);
					wde.printStackTrace();
					return;
				}
			}
		}
	}

	public void scrollUpAndClick(){
		while(true){
			try{
				element.click();
				return;
			}catch(WebDriverException wde){
				if(wde.getMessage().contains("Element is not clickable at point")){
					((JavascriptExecutor)driver).executeScript("window.scrollBy(0,-50)", new Object[0]);
					//driver.executeScript("window.scrollBy(0,-50)", new Object[0]);
				}else{
					CustomLogger.logException(wde);
					wde.printStackTrace();
					return;
				}
			}
		}
	}

	public void scrollRightAndClick(){
		while(true){
			try{
				element.click();
				return;
			}catch(WebDriverException wde){
				if(wde.getMessage().contains("Element is not clickable at point")){
					((JavascriptExecutor)driver).executeScript("window.scrollBy(50,0)", new Object[0]);
					//driver.executeScript("window.scrollBy(50,0)", new Object[0]);
				}else{
					CustomLogger.logException(wde);
					wde.printStackTrace();
					return;
				}
			}
		}
	}

	public void scrollLeftAndClick() {		
		while(true){
			try{
				element.click();
				return;
			}catch(WebDriverException wde){
				if(wde.getMessage().contains("Element is not clickable at point")){
					((JavascriptExecutor)driver).executeScript("window.scrollBy(-50,0)", new Object[0]);
					//driver.executeScript("window.scrollBy(-50,0)", new Object[0]);
				}else{
					CustomLogger.logException(wde);
					wde.printStackTrace();
					return;
				}
			}
		}
	}
	
	public void scrollDown(int px) {		
		try{			
			//scroll down
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollTop = arguments[1];",element, px);
			Thread.sleep(5000);				
		}catch(Exception e){				
			CustomLogger.logException(e);
			e.printStackTrace();			
		}
	}

	
	public void moveToElementAndClick() {		
		try{
			((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);				
		}catch(WebDriverException wde){				
			CustomLogger.logException(wde);
			wde.printStackTrace();
			return;
		}
	}

	public void rightClick() {	
		new Actions(driver).contextClick(this.getWrappedElement());
	}

	public void doubleClick() {
		new Actions(driver).doubleClick(this.getWrappedElement());
	}

	/*public BrowserDriver getDriver() {
		return driver;
	}*/

	public WebElement getWrappedElement() {
		return element;
	}

	public SelectList findSelectList(By by) {
		SelectList s=new SelectList(new Select(this.findElement(by).getWrappedElement()));
		return s;
	}

	public void waitForVisible() {

		new WebDriverWait(driver,timeout).until(ExpectedConditions.visibilityOf(element));

		/*ExpectedCondition<Boolean> customCondition = new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {
				return element.isDisplayed();
			}
		};

		return new WebDriverWait(driver, 10).until(customCondition);*/

	}

	public void waitTillNotVisible() {
		new WebDriverWait(driver,20).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
	}
	
	public void waitTillNotVisible(int seconds) {
		new WebDriverWait(driver,seconds).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
	}
	

	public void waitTillNotPresent() {
		new WebDriverWait(driver, 20).until(ExpectedConditions.stalenessOf(element));		
	}
	
	public void waitTillNotPresent(int seconds) {
		new WebDriverWait(driver, seconds).until(ExpectedConditions.stalenessOf(element));		
	}

	public int getCountOfElementsWithXPath(String xpath) {
		return this.getWrappedElement().findElements(By.xpath(xpath)).size();
	}

	public String getTextForHiddenElement() {		
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		return (String) jse.executeScript("return arguments[0].innerHTML", element);
	}
	
	public void removeFromDOM() {
		((JavascriptExecutor)driver).executeScript("arguments[0].parentNode.removeChild(arguments[0])",this.getWrappedElement());		
	}

	public TextBoxElement findTextBox(By by) {
		TextBoxElement tbe = new TextBoxElement(this.findElement(by));
		return tbe;
	}

	public CheckBoxElement findCheckBox(By by) {
		CheckBoxElement cbe = new CheckBoxElement(this.findElement(by));
		return cbe;
	}
	
	public int getCountOfElementsWithCSSSelcector(String cssSelector) {
		return this.getWrappedElement().findElements(By.cssSelector(cssSelector)).size();
	}
	
	public void forceClick(){
		if(!Environment.getBrowserType().equals("ie")){
			click();
		}else{
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("var evt = document.createEvent(\"MouseEvents\");evt.initMouseEvent('click', true, true,arguments[0].ownerDocument.defaultView, 1, 0, 0, 0, 0, false,false, false, false, 1, null);arguments[0].dispatchEvent(evt)",this.getWrappedElement());
		}
	}

	  public void mouseOver(){
	    	Actions builder = new Actions(driver);
	    	builder.moveToElement(element).perform();	    	
	    }

	  public void selectDropdownElementUsingVisibleText(String textToBeSelect){
		  new Select(element).selectByVisibleText(textToBeSelect);
	  }
	  
	  /*This method is to select multiple segments for a user
	   * @params: List of segments we want to select
	  */
	  public void selectMultipleSegmnetsUsingVisibleText(List<String> textToBeSelect){
		  
		  for(int i = 0; i<textToBeSelect.size(); i++){
			  new Select(element).selectByVisibleText(textToBeSelect.get(i));
			  Common.sleepFor(1000);
		  }      
	  }
	  
	public List<HtmlElement> getOptions(){
		 /* List<HtmlElement> listItems=new ArrayList<>();		 
		  listItems.addAll((Collection<? extends HtmlElement>) new Select(element).getOptions());
		  return listItems;*/
		return null;
		  
	  }	
	  
	  public String getFirstSelectedOption(){		  		 
		  String selectedSegment= new Select(element).getFirstSelectedOption().getText();
		   return selectedSegment;
		  
	  }
}
