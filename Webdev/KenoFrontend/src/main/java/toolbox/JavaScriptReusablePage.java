package toolbox;

import net.thucydides.core.pages.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptReusablePage extends PageObject {

	public JavaScriptReusablePage(WebDriver driver) {
		super(driver);
	}
	
	public JavaScriptReusablePage() {}
	
	public static JavaScriptReusablePage getInstance() {
		return new JavaScriptReusablePage();
	}

	/**
	 * Java Script Reusable Action
	 */

	public boolean getReadOnlyStatus(String idProperty){
		return (Boolean) evaluateJavascript("return document.getElementById('"+idProperty+"').readOnly");
	}

	public boolean getDisabledStatus(String idProperty){
		return (Boolean) evaluateJavascript("return document.getElementById('"+idProperty+"').disabled");
	}

	public boolean getCheckedStatus(String idProperty){
		return (Boolean) evaluateJavascript("return document.getElementById('"+idProperty+"').checked");
	}

	public String getValue(String idProperty){
		return (String) evaluateJavascript("return document.getElementById('"+idProperty+"').value");
	}

	public String setValue(String idProperty,String value){
		return (String) evaluateJavascript("document.getElementById('"+idProperty+"').value='"+value+"'");
	}


	public void clickElement(String idProperty){
		evaluateJavascript("document.getElementById('"+idProperty+"').click()");
	}

	public void clickElement(WebElement element){
		((JavascriptExecutor) getDriver()).executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 100)",element);
	}
	
	public void clickElement(By locator) {
		evaluateJavascript("arguments[0].click();",getDriver().findElement(locator));
	}

	public void scrollTop(){
		evaluateJavascript("$(document).scrollTop(0)");
	}

	public void scrollToView(WebElement element){
		evaluateJavascript("arguments[0].scrollIntoView(true);",element);
		evaluateJavascript("arguments[0].click();",element);
	}

	public void removeReadonlyAttribute(WebElement element){
		evaluateJavascript("arguments[0].removeAttribute('readonly','readonly')",element);
	}

	public void removeAriaReadonlyAttribute(WebElement element){
		evaluateJavascript("arguments[0].setAttribute('aria-readonly','false')",element);
	}

	public void disableAlert(){
		//evaluateJavascript("window.onbeforeunload = function(){};");
		//evaluateJavascript("window.alert = function() {};");
	}
	
	public String getUsername() {
		return String.valueOf(((JavascriptExecutor) getDriver()).executeScript("return PCHUSER.firstName"));
	}
}
