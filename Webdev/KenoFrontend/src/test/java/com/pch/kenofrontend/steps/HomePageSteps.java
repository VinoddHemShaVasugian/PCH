package com.pch.kenofrontend.steps;

import java.util.List;

import org.sikuli.script.FindFailed;

import com.pch.kenofrontend.pages.HomePage;
import com.pch.kenofrontend.pages.WaitPage;
import com.pch.kenofrontend.utilities.DateUtil;
import com.pch.kenofrontend.utilities.WaitHelper;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.steps.ScenarioSteps;


public class HomePageSteps extends ScenarioSteps{
	
	
	HomePage homePage;
	WaitPage waitPage;

		
	@Step
	public void selectNumbersByQuickPick()
	{
		homePage.clickOn_QuickPick_Btn();
	}

	@Step
	public void submitSelectedNumbers()
	{
		homePage.clickOn_Submit_Btn_AfterQuickPick();
	}

	@Step
	public void clearSelectedNumbers()
	{
		homePage.clickOn_Clear_Btn();
	}

	@Step
	public void verifySuccessRegisration()
	{
		homePage.signoutLinkPresent();
//		homePage.vipPresent();
	}

	@Step
	public void goto_RegistrationPage(){

		homePage.clickOn_Register_Btn();
	}

	@Step
	public void goto_SignInPage(){

		homePage.clickOn_SignIn_Btn();
	}

	@Step
	public void verifySuccessLogin()
	{
		homePage.signoutLinkPresent();
		homePage.vipPresent();
	}

	@Step
	public void playFirstGame()
	{
		homePage.PlayFirstGame();
	}

	@Step
	public void playAllCards(int cardCount) throws InterruptedException
	{
		waitForNewSlot();
		homePage.clickOn_Home();
		homePage.playAllCards(cardCount);
	}

	@Step
	public void playAllCards(int cardCount, boolean stat) throws InterruptedException
	{
		waitForNewSlot();
		homePage.clickOn_Home();
		homePage.playAllCards(cardCount,stat);
	}

	@Step
	public void verifyCardStatus(int cards) throws InterruptedException
	{
		if(!getDriver().getCurrentUrl().endsWith("live-drawing"))
			homePage.CompletedStatusVerification(cards);
		if(!getDriver().getCurrentUrl().endsWith("live-drawing"))
		{
			homePage.clickOn_Live_Drawing();
		}
		WaitHelper.waitForPresenceOfElement(getDriver(), homePage.Drawn_Clock, 1200);
		homePage.getDrawnNumbers();
		homePage.verifymatchingDeatils();

	}

	@Step
	public void verifyliveDrawing(int cards) throws InterruptedException
	{
		if(!getDriver().getCurrentUrl().endsWith("live-drawing"))
		{
			homePage.clickOn_Live_Drawing();
		}
		WaitHelper.waitForPresenceOfElement(getDriver(), homePage.Drawn_Clock, 1200);
		homePage.getDrawnNumbers();
		homePage.verifymatchingDeatils();
	}


	@Step
	public void postcompleteapis(){
		//getDriver().findElement(By.id(id))
		//homePage.postCompletApis();
	}

	@Step
	public void waitForNewSlot() throws InterruptedException
	{
//		TimeZone timezone = TimeZone.getTimeZone("America/New_York");
//		Calendar calendar = Calendar.getInstance(timezone);
//		int min =calendar.get(Calendar.MINUTE);
		int min = DateUtil.getCurrentMinutes();
		// If the User has less than 14 minutes to Play Slot cards then wait for next 20 minutes game set
		if((min % 20) > 6) {
//		if((min % 20) < 1) { //Manipulated for testing otherwise it waits for 15 min
			System.out.println("Execution will sleep for " + (20 - (min % 20))+ " mins to get fresh 20 minutes game set " );
//			TimeUnit.MINUTES.sleep(20 - (min % 20));
			waitPage.forcibleWait((20 - (min % 20))*60*1000);
		}
	}

	//Changes for Results page diwresultstokens.story 
	@Step
	public void goto_ResultsPage() throws InterruptedException{

		homePage.clickOn_Results_Btn();
	}
	//Changes for Coaching Screen story
	
	@Step 
	public void verifycoaching_NumberPick()
	{
		homePage.coachingQuickpick();
	}
	
	@Step 
	public void verifycoaching_NumberSubmit()
	{
		homePage.coachingSubmit();
	}
	
	@Step
	public void LandOnGameChoicesafterSubmission() throws InterruptedException
	{
		homePage.LandOnGameChoices();
	}
	
	@Step
	public void verifycoaching_IWGame()
	{
		homePage.coachingIWGame();
	}
	
	@Step
	public void playScratchPathAlone() throws Exception
	{
		homePage.scratchpathplay();
		
	}
	@Step 
	public void verifyNoCoachingScreen()
	{
		homePage.nocoaching();
	}
	
	//Changes for UnRecognized User Sign In_Post Game Play story
	@Step
	public void submitSelectedNumbers(List<String> numberstoselect) throws InterruptedException
	{
		waitForNewSlot(2); //User should be allowed to submit numbers if minutes left are >3
		homePage.clickOn_Home();
		homePage.select_numbersforKenoCard(numberstoselect);
		
	}
	
	@Step
	public void waitForNewSlot(int avoidtimeinminutes) throws InterruptedException
	{
//		TimeZone timezone = TimeZone.getTimeZone("America/New_York");
//		Calendar calendar = Calendar.getInstance(timezone);
//		int min =calendar.get(Calendar.MINUTE);
		int min = DateUtil.getCurrentMinutes();
		// If the User has less than avoidtimeinminutes minutes to Play Slot cards then wait for next 20 minutes game set
		if((min % 20) >= 20-avoidtimeinminutes) { 
			System.out.println("Execution will sleep for " + (20 - (min % 20)+1)+ " mins to get fresh 20 minutes game set " );
//			TimeUnit.MINUTES.sleep(20 - (min % 20)+1);
			waitPage.forcibleWait((20 - (min % 20)+1)*60*1000);
		}
	}
	//Changes for Login story
	
	@Step
	public void checkUserProfile()
	{
		homePage.userprofile();
	}
	
	//Changes for Post Game Play token earning story
	
	@Step
	public void validateearnedtokens()
	{
		homePage.clickTokenHistory();
		homePage.validateTokensForNewUser();
	}

	@Step
	public void playSecondKenoCard()
	{
		homePage.playSecondKenoCard();
	}
	
	//Added below method to support RS registration ~Oct 19,2018 Lovekesh S
	@Step
	public void registeruserWithPassword(){
		homePage.rsRegistration();
	}
	
	@Step
	public void optinLightboxDisplayed() 
	{
		homePage.verifyOptinLightboxPresent();
	}
	
	@Step
	public void closeTheOptinLightbox()
	{
		homePage.closeOptinLightBox();
	}
	
	@Step
	public void clickOnOptinSignupButton()
	{
		homePage.clickOnOptinSignupButton();
	}

	@Step
	public void verifyOptinThankYouMessage()
	{
		homePage.verifyOptinThankYouMessage();
	}

	@Step
	public void verifyOptinTokens() throws InterruptedException {
		homePage.verifyOptinTokens();
		
	}
	
	@Step
	public void openTutorials()
	{
		homePage.openTutorials();
	}
	
	@Step
	public void verifyTutorialsOpen()
	{
		homePage.verifyTutorialsOpen();
	}
	
	@Step
	public void clickTutorialsNext()
	{
		homePage.clickTutorialsNext();
	}
	
	@Step
	public void clickTutorialsPrevious()
	{
		homePage.clickTutorialsPrevious();
	}
	
	@Step
	public void clickTutorialsClose()
	{
		homePage.clickTutorialsClose();
	}
	
	@Step
	public void clickCompleteRegBtn()
	{
		homePage.clickCompleteRegBtn();
	}
	
	@Step
	public void verifySuccessfulRegistration()
	{
		homePage.verifySuccessfulRegistration();
	}
	
	@Step
	public void navigateToTokenHistory()
	{
		homePage.navigateToTokenHistory();
	}
	
	@Step
	public void readPickedNumbers()
    {
        homePage.readPickedNumbers();
    }
		
	@Step
	public void numbersSubmittedSameOnSideRail()
	{
		homePage.numbersSubmittedSameOnSideRail();		
	}

	@Step
	public void choiceSelection(String game) throws Exception
	{
		switch (game)
		{
			case "SFL":
				{
					homePage.choiceSelection("SFL");
				}
				break;
			case "Scratch Card":
				{
					homePage.choiceSelection("Scratch Card");
				}
				break;
			default:
				System.out.println("default slots game");
					
		}
	}

	@Step
	public void playPathGameAsUWPwd(String game) throws FindFailed, InterruptedException {
		{
			switch (game)
			{
				case "SFL":
					{
						homePage.playChoiceAsUserWPwd("SFL");
					}
					break;
				case "Scratch Card":
					{
						homePage.playChoiceAsUserWPwd("Scratch Card");
					}
					break;
				default:
					System.out.println("default slots game");
			}
		}
		
	}	
	@Step
	public void observeLeaderboard() {
		homePage.observeLeaderboard();
	}
	
	@Step
	public void mobileTabAppearingInLeaderboard() throws Exception {
		homePage.mobileTabAppearingInLeaderboard();
	}
	
	@Step
	public void desktopTabAppearingInLeaderboard() throws Exception {
		homePage.desktopTabAppearingInLeaderboard();
	}
}
