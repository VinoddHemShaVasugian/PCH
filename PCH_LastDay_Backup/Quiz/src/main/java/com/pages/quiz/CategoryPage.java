package com.pages.quiz;

import org.openqa.selenium.By;

import net.serenitybdd.core.pages.PageObject;

public class CategoryPage extends PageObject {

		
	/**
	 * Verify the header of category pages
	 * 
	 * @return true
	 */
	public boolean verifyCategoryPage(String category) {
		waitForRenderedElementsToBePresent(By.xpath("//h1[text()='"+category+" Quizzes']"));
		return isElementVisible(By.xpath("//h1[text()='"+category+" Quizzes']"));
	}
}