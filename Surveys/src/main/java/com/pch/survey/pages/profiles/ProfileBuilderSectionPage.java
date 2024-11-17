package com.pch.survey.pages.profiles;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pch.survey.pages.PageObject;

public class ProfileBuilderSectionPage extends PageObject {

	
	private static int questionsAnswered = 0;
	private static int questionsAnsweredRequirement=0;
	
	public ProfileBuilderSectionPage(WebDriver driver) {
		super(driver);
	}

	
	public ProfileBuilderSectionPage() {
		super();
	}
	
	
	public void selectAndAnswerQuestion(String question, String answer) {
		waitUntilThePageLoads();

		WebElement ele = driver.findElement(By.xpath("//*[contains(text(), 'What best describes your residence?')]"));
		scrollIntoView(ele);
		WebElement parent = ele.findElement(By.xpath("./.."));
		parent.findElement(By.className("profile-button--edit")).click();
		scrollIntoView(driver.findElement(By.cssSelector(".survey__profile-builder__update__menu__caret")));
		driver.findElement(By.cssSelector(".survey__profile-builder__update__menu__caret")).click();

		List<WebElement> lineItems = driver
				.findElements(By.cssSelector(".survey__profile-builder__update__menu__list__item"));
		for (WebElement li : lineItems) {
			if (li.getText().equals(answer)) {
				scrollIntoView(li);
				li.click();
				break;
			}
		}

	}
 
	
	public void answerAllProfileBuilderQuestions(int count) {
		questionsAnsweredRequirement = count;
		 answerAllProfileBuilderQuestions();
		 questionsAnsweredRequirement=0;
	}
	public void answerAllProfileBuilderQuestions() {
	
		questionsAnswered = 0;
if(questionsAnswered > 44)
		System.out.println();
		WebElement ele = driver.findElement(By.cssSelector(".category__question"));
		scrollIntoView(ele);
		ele.findElement(By.tagName("button")).click();
 		
		// what type of question set list or checkbox

		 
	 	while(true) {
   	 		System.out.println("question counter " + questionsAnswered);
	 		if ((questionsAnsweredRequirement > 0 && questionsAnswered >= questionsAnsweredRequirement) )
	 		   break; 
	 		
	 		waitUntilThePageLoads();
			waitSeconds(2);
	 		boolean questionFound = false;
			// dropdown selects
 	 		List<WebElement> ul =  getElementList(By.cssSelector(".survey__profile-builder__update__menu__list"));
			if (ul.size() > 0) {
				for (int y = 0; y < ul.size(); y++) {
					ul.get(y).click();
					List<WebElement> li = ul.get(y)
							.findElements(By.cssSelector(".survey__profile-builder__update__menu__list__item"));

					WebElement liEle = li.get(li.size() - 2);
					scrollIntoView(liEle);
					if(liEle.getText().trim().equals("")) {
						liEle = li.get(li.size() - 1);
					}
					liEle.click();
					waitSeconds(1);
				}
				questionFound =true;
				questionsAnswered = questionsAnswered+1;
				
				// if all questions are answered then save button removed 
		 		List<WebElement> saveBtn =  getElementList(By.cssSelector("#savetext"));
		 		if (saveBtn.size() > 0)
		 			saveBtn.get(0).click();
		 		else
		 			break;
		 		continue;
			}
			// checkboxes
 	 		List<WebElement> formHasCheckBoxes =  getElementList(By.cssSelector(".form-check"));
			if (formHasCheckBoxes.size() > 0) {
				List<WebElement> chkBoxs = driver.findElements(By.cssSelector(".checkmark"));
				chkBoxs.get(chkBoxs.size() - 1).click();
				questionFound =true;
				questionsAnswered = questionsAnswered+1;
	
				// if all questions are answered then save button removed 
		 		List<WebElement> saveBtn =  getElementList(By.cssSelector("#savetext"));
		 		if (saveBtn.size() > 0)
		 			saveBtn.get(0).click();
		 		else
		 			break;
		 		continue;
			}
			// sets of radio buttons
 	 		List<WebElement> radioButtonSets =  getElementList(By.cssSelector(".profile-question__grid-radio"));
			if (radioButtonSets.size() > 0) {
				for (int y = 0; y < radioButtonSets.size(); y++) {
					scrollIntoView(radioButtonSets.get(y));
					radioButtonSets.get(y).findElement(By.tagName("span")).click();
					waitSeconds(1);
				}
				questionFound =true;
				questionsAnswered = questionsAnswered+1;
		 		List<WebElement> saveBtn =  getElementList(By.cssSelector("#savetext"));
		 		if (saveBtn.size() > 0)
		 			saveBtn.get(0).click();
		 		else
		 			break;
		 		continue;
			}
			if(!questionFound || questionsAnswered >60) {
			   break;	
			}
		}
	}

	
	
	
 
    public int getNumberOfQuestionsAnswered() {
    	return questionsAnswered;
    	
    }
	 

	public void clickSaveButton() {
		driver.findElement(By.id("savetext")).click();
	}

	  public void clickExitButton() {
	    	scrollIntoView(driver.findElement(By.cssSelector("span[class='back pointer exit']"))).click();
	    }
	
	
	
}
