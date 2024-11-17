package com.pch.search.utilities;

import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;


public interface HtmlElement extends SearchContext{
	/**
	 * Click this element. If this causes a new page to load, this method will attempt to block until 
	 * the page has loaded. At this point, you should discard all references to this element and any 
	 * further operations performed on this element will throw a StaleElementReferenceException unless
	 * you know the element and the page will still be present. If click() causes a new page to be 
	 * loaded via an event or is done by sending a native event then the method will *not* wait for
	 * it to be loaded and the caller should verify that a new page has been loaded.
	 * <p>
	 * There are some preconditions for an element to be clicked.  The element must be visible and 
	 * it must have a height and width greater then 0.
	 *
	 * @throws StaleElementReferenceException If the element no longer exists as initially defined
	 */
	void click();

	/**
	 * If this current element is a form, or an element within a form, then this will be submitted to
	 * the remote server. If this causes the current page to change, then this method will block until
	 * the new page is loaded.
	 * 
	 * @throws NoSuchElementException If the given element is not within a form
	 */
	void submit();

	/**
	 * Use this method to simulate typing into an element, which may set its value.
	 */
	void sendKeys(CharSequence... keysToSend);

	/**
	 * If this element is a text entry element, this will clear the value. Has no effect on other
	 * elements. Text entry elements are INPUT and TEXTAREA elements.
	 *
	 * Note that the events fired by this event may not be as you'd expect.  In particular, we don't
	 * fire any keyboard or mouse events.  If you want to ensure keyboard events are fired, consider
	 * using something like {@link #sendKeys(CharSequence...)} with the backspace key.  To ensure
	 * you get a change event, consider following with a call to {@link #sendKeys(CharSequence...)}
	 * with the tab key.
	 */
	void clear();

	/**
	 * Get the tag name of this element. <b>Not</b> the value of the name attribute: will return
	 * <code>"input"</code> for the element <code>&lt;input name="foo" /&gt;</code>.
	 * 
	 * @return The tag name of this element.
	 */
	String getTagName();

	/**
	 * Get the value of a the given attribute of the element. Will return the current value, even if
	 * this has been modified after the page has been loaded. More exactly, this method will return
	 * the value of the given attribute, unless that attribute is not present, in which case the value
	 * of the property with the same name is returned (for example for the "value" property of a
	 * textarea element). If neither value is set, null is returned. The "style" attribute is
	 * converted as best can be to a text representation with a trailing semi-colon. The following are
	 * deemed to be "boolean" attributes, and will return either "true" or null:
	 * 
	 * async, autofocus, autoplay, checked, compact, complete, controls, declare, defaultchecked,
	 * defaultselected, defer, disabled, draggable, ended, formnovalidate, hidden, indeterminate,
	 * iscontenteditable, ismap, itemscope, loop, multiple, muted, nohref, noresize, noshade,
	 * novalidate, nowrap, open, paused, pubdate, readonly, required, reversed, scoped, seamless,
	 * seeking, selected, spellcheck, truespeed, willvalidate
	 * 
	 * Finally, the following commonly mis-capitalized attribute/property names are evaluated as
	 * expected:
	 * 
	 * <ul>
	 * <li>"class"
	 * <li>"readonly"
	 * </ul>
	 * 
	 * @param name The name of the attribute.
	 * @return The attribute/property's current value or null if the value is not set.
	 */
	String getAttribute(String name);

	/**
	 * Determine whether or not this element is selected or not. This operation only applies to input
	 * elements such as checkboxes, options in a select and radio buttons.
	 * 
	 * @return True if the element is currently selected or checked, false otherwise.
	 */
	boolean isSelected();

	/**
	 * Is the element currently enabled or not? This will generally return true for everything but
	 * disabled input elements.
	 * 
	 * @return True if the element is enabled, false otherwise.
	 */
	boolean isEnabled();

	/**
	 * Get the visible (i.e. not hidden by CSS) innerText of this element, including sub-elements,
	 * without any leading or trailing whitespace.
	 * 
	 * @return The innerText of this element.
	 */
	String getText();

	
	/**
	 * Is this element displayed or not? This method avoids the problem of having to parse an
	 * element's "style" attribute.
	 * 
	 * @return Whether or not the element is displayed
	 */
	boolean isDisplayed();

	/**
	 * Where on the page is the top left-hand corner of the rendered element?
	 * 
	 * @return A point, containing the location of the top left-hand corner of the element
	 */
	Point getLocation();

	/**
	 * What is the width and height of the rendered element?
	 * 
	 * @return The size of the element on the page.
	 */
	Dimension getSize();

	/**
	 * Get the value of a given CSS property.
	 * Color values should be returned as rgba strings, so,
	 * for example if the "background-color" property is set as "green" in the
	 * HTML source, the returned value will be "rgba(0, 255, 0, 1)".
	 *
	 * Note that shorthand CSS properties (e.g. background, font, border, border-top, margin,
	 * margin-top, padding, padding-top, list-style, outline, pause, cue) are not returned,
	 * in accordance with the
	 * <a href="http://www.w3.org/TR/DOM-Level-2-Style/css.html#CSS-CSSStyleDeclaration">DOM CSS2 specification</a>
	 * - you should directly access the longhand properties (e.g. background-color) to access the
	 * desired values.
	 * 
	 * @return The current, computed value of the property.
	 */
	String getCssValue(String propertyName);

	/**
	 * This will move the scroll bar UP and  click the element once click is not shadowed by other element
	 */
	void scrollUpAndClick();
	
	
	/**
	 * This will move the scroll bar down and click the element once click is not shadowed by other element
	 */
	void scrollDownAndClick();	

	/**
	 * This will move the scroll bar left and click the element once click is not shadowed by other element
	 */
	void scrollLeftAndClick();
	
	/**
	 * This will move the scroll bar right and click the element once click is not shadowed by other element
	 */
	
	void scrollRightAndClick();
	
	/**
	 * Move to the element and perform click
	 */
	void moveToElementAndClick();
	
	/**
	 * Right click the element.
	 */

	void rightClick();
	
	/**
	 * Double click the element.
	 */

	void doubleClick();



	/**
	 * @return the WebElement wrapped by this HtmlElement
	 */

	WebElement getWrappedElement();
	
	/**
	 * Method which waits for default element timeout period.
	 * 
	 * If the element is already visible, the method will return immediately.
	 * 
	 * @param element
	 * @return element
	 */
	public  void waitForVisible();
	
	/**
	 * Overloaded method which waits till default element timeout period.
	 * 
	 * If the element is already not visible, the method will return immediately.
	 * 
	 * @param element
	 * @return element
	 */
	public void waitTillNotVisible();
	
	/**
	 * Waits for the HtmlElement to be removed from DOM.
	 */
	public void waitTillNotPresent();
	
	public void waitTillNotPresent(int seconds);
	
	int getCountOfElementsWithXPath(String xpath);
	
	int getCountOfElementsWithCSSSelcector(String cssSelector);
	
	public String getTextForHiddenElement();
	
	public void waitTillNotVisible(int seconds);
	
	/**
	 * This method will remove the current element from DOM,
	 * useful while dismissing advertisements. 
	 */
	public void removeFromDOM();
	
	public void forceClick();
	
	void scrollDown(int px);
	
	/**
	 * To do a mouseover to the element
	 * */
	public void mouseOver();
	
	/**
	 * To select any element from drop down 
	 * Note:Tag name should be 'Select'
	 * */
	public void selectDropdownElementUsingVisibleText(String textToBeSelect);
	
	public void selectMultipleSegmnetsUsingVisibleText(List<String> textToBeSelect);
	
	/**
	 * This function is to get all the options from drop down list
	 * */
	public List<HtmlElement> getOptions();	
	
	/**
	 * This function is to get first selected option from drop down list
	 * */
	public String getFirstSelectedOption();
}
