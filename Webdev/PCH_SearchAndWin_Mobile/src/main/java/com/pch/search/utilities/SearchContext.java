package com.pch.search.utilities;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public interface SearchContext {
	/**
	   * Find all elements within the current page using the given mechanism.
	   * This method is affected by the 'implicit wait' times in force at the time of execution. When
	   * implicitly waiting, this method will return as soon as there are more than 0 items in the
	   * found collection, or will return an empty list if the timeout is reached.
	   * 
	   * @param by The locating mechanism to use
	   * @return A list of all {@link HtmlElement}s, or an empty list if nothing matches
	   * @see org.openqa.selenium.By
	   * @see org.openqa.selenium.WebDriver.Timeouts
	   */
	  List<HtmlElement> findElements(By by);


	  /**
	   * Find the first {@link HtmlElement} using the given method.
	   * This method is affected by the 'implicit wait' times in force at the time of execution.
	   * The findElement(..) invocation will return a matching row, or try again repeatedly until 
	   * the configured timeout is reached.
	   *
	   * findElement should not be used to look for non-present elements, use {@link #findElements(By)}
	   * and assert zero length response instead.
	   *
	   * @param by The locating mechanism
	   * @return The first matching element on the current page
	   * @throws NoSuchElementException If no matching elements are found
	   * @see org.openqa.selenium.By
	   * @see org.openqa.selenium.WebDriver.Timeouts
	   */
	  HtmlElement findElement(By by);
	  
	  /**
	   * Find a dropdown list.
	   */
	  SelectList findSelectList(By by);
	  
	  HtmlElement findElement(By by, int waitingTimeInSeconds);
	  
	  TextBoxElement findTextBox(By by);
	  CheckBoxElement findCheckBox(By by);

}
