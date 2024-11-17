package com.pch.search.utilities;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;

public interface BrowserDriver extends SearchContext {
	/**
	 * Load a new web page in the current browser window. This is done using an HTTP GET operation,
	 * and the method will block until the load is complete. This will follow redirects issued either
	 * by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect
	 * "rest" for any duration of time, it is best to wait until this timeout is over, since should
	 * the underlying page change whilst your test is executing the results of future calls against
	 * this interface will be against the freshly loaded page. Synonym for
	 * {@link org.openqa.selenium.WebDriver.Navigation#to(String)}.
	 * 
	 * @param url The URL to load. It is best to use a fully qualified URL
	 */
	void get(String url);

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 * 
	 * @return The URL of the page currently loaded in the browser
	 */
	String getCurrentUrl();

	// General properties

	/**
	 * The title of the current page.
	 * 
	 * @return The title of the current page, with leading and trailing whitespace stripped, or null
	 *         if one is not already set
	 */
	String getTitle();


	// Misc

	/**
	 * Get the source of the last loaded page. If the page has been modified after loading (for
	 * example, by Javascript) there is no guarantee that the returned text is that of the modified
	 * page. Please consult the documentation of the particular driver being used to determine whether
	 * the returned text reflects the current state of the page or the text last sent by the web
	 * server. The page source returned is a representation of the underlying DOM: do not expect it to
	 * be formatted or escaped in the same way as the response sent from the web server. Think of it as
	 * an artist's impression.
	 * 
	 * @return The source of the current page
	 */
	String getPageSource();

	/**
	 * Close the current window, quitting the browser if it's the last window currently open.
	 */
	void close();

	/**
	 * Quits this driver, closing every associated window.
	 */
	void quit();

	/**
	 * Return a set of window handles which can be used to iterate over all open windows of this
	 * WebDriver instance by passing them to {@link #switchTo()}.{@link Options#window()}
	 * 
	 * @return A set of window handles which can be used to iterate over all open windows.
	 */
	Set<String> getWindowHandles();

	/**
	 * Return an opaque handle to this window that uniquely identifies it within this driver instance.
	 * This can be used to switch to this window at a later date
	 */
	String getWindowHandle();

	/**
	 * Send future commands to a different frame or window.
	 * 
	 * @return A TargetLocator which can be used to select a frame or window
	 * @see org.openqa.selenium.WebDriver.TargetLocator
	 */
	TargetLocator switchTo();

	/**
	 * An abstraction allowing the driver to access the browser's history and to navigate to a given
	 * URL.
	 * 
	 * @return A {@link org.openqa.selenium.WebDriver.Navigation} that allows the selection of what to
	 *         do next
	 */
	Navigation navigate();

	/**
	 * Gets the Option interface
	 * 
	 * @return An option interface
	 * @see org.openqa.selenium.WebDriver.Options
	 */
	Options manage();

	/**
	 * An interface for managing stuff you would do in a browser menu
	 */
	WebDriver getwrappedDriver();


	Object executeScript(String script, Object ...args);

	void switchtoFrame(String frameId);
	
	void switchTo_iframe(String xpath);
	
	/**
	 * Use this method when waiting for page load to complete.
	 * It will wait indefinitely till document.readyState is not COMPLETE
	 */

	void waitForBrowserToLoadCompletely();

	int getCountOfElementsWithXPath(String xpath);
	
	int getCountOfElementsWithCSSSelcector(String cssSelector);

	public void openNewTabAndSwitchToIt();

	public void switchToTab(int tabIndex);
	
	//To switch to new window
	public boolean switchToChildWindow(String mainWindow);

	HtmlElement waitForElementToBeClickable(By by, int timeUnit);

	HtmlElement waitForElementToBeVisible(By by, int timeUnit);
	
	void waitForElementToBeDisappear(By by, int timeUnit);
	
	boolean isAlertPresent(int timeUnit);
	
}
