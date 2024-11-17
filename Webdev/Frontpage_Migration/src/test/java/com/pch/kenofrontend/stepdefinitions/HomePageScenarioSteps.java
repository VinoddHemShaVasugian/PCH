package com.pch.kenofrontend.stepdefinitions;

import java.util.List;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.sikuli.script.FindFailed;

import com.pch.kenofrontend.steps.CommonSteps;
import com.pch.kenofrontend.steps.HomePageSteps;
import com.pch.kenofrontend.steps.RegistrationSteps;
import com.pch.kenofrontend.steps.SignInPageSteps;

import net.thucydides.core.annotations.Steps;

public class HomePageScenarioSteps {
	
	@Steps
	HomePageSteps homePageSteps;
	CommonSteps commonSteps;
	
	//Changes for Results Page story
	@Steps
	RegistrationSteps registrationSteps;
	
	@Steps
	SignInPageSteps signInPageSteps;
	
	@When("Select the numbers by Quick Pick")
	public void whenSelectTheNumbersByQuickPick() {
		homePageSteps.selectNumbersByQuickPick();
	}
	
	@When("Submit the selected numbers")
	public void whenSubmitTheSelectedNumbers() {
		homePageSteps.submitSelectedNumbers();
	}
	
	
	@Given("Clear the selected numbers")
	public void whenClearTheSelection() {
		homePageSteps.clearSelectedNumbers();
	}
	
	@Then("Verify the successful registration")
	public void whenVerifyTheSuccessfulRegistration()
	{
		homePageSteps.verifySuccessRegisration();
	}
	
	@Given("Navigate to Registration Page")
	public void givenNavigateToRegistrationPage() {
		homePageSteps.goto_RegistrationPage();
	}
	
	@When("Navigate to SignIn Page")
	public void givenNavigateToSignInPage() { //Vinoth - Changed for FP
		homePageSteps.goto_SignInPage();
	}
		
/*	@When("Navigate to SignIn Page")
	public void givenNavigateToSignInPage() {
		homePageSteps.goto_SignInPage();
	}*/
	
/*	@Then("Verify the successful login")
	@When("Verify the successful login")
	public void whenVerifyTheSuccessfulLogin()
	{
		homePageSteps.verifySuccessRegisration();
	}*/
	
	@Then("Verify the FP successful login")
	@When("Verify the FP successful login")//Vinoth - Changed for FP
	public void whenVerifyTheFPSuccessfulLogin()
	{
		homePageSteps.verifySuccessRegisration();
	}
		
	@When("play first game")
	public void whenPlayFirstGame()
	{
		homePageSteps.playFirstGame();
	}
	
	@When("play $cardCount cards")
	public void whenPlayCards(int cardCount) throws InterruptedException
	{
		homePageSteps.playAllCards(cardCount);
	}
	
	@When("play $cardCount cards without registartion")
	public void whenPlayCardsWithOutRegistration(int cardCount) throws InterruptedException
	{
		homePageSteps.playAllCards(cardCount,false);
	}
	
	@Then("verify the status of the $cards cards")
	public void thenVerifyTheStatusOfTheCards(int cards) throws InterruptedException
	{
		homePageSteps.verifyCardStatus(cards);
	}
	
	@Given("verify the drawing status")
	public void thenVerifyTheDrawingStatus(int cards) throws InterruptedException
	{
		homePageSteps.verifyliveDrawing(cards);
	}
	
	//Changes for Results page diwresultstokens.story 
		@Given ("User login successfully with valid credentials $credentials")
		public void successfulLogin(ExamplesTable credentials)
		{
		homePageSteps.goto_SignInPage();
		signInPageSteps.enterCredentials(credentials);
		homePageSteps.verifySuccessRegisration();
		}
	//Changes for Results page diwresultstokens.story 
		@When ("navigate to results page by clicking on Results link")
		public void navigatetoResultspage() throws InterruptedException{
		homePageSteps.goto_ResultsPage();
		
		}
	//Changes for Coaching Screen story
		@Then("verify user sees following message as Coaching screen1: \"Pick 10 Numbers or Choose Quick Pick!\"")
		public void verifyCoachingForNumberPick()
		{
			homePageSteps.verifycoaching_NumberPick();
		}
		
		
		@Then ("user picks required numbers on the Keno gamecard")
		public void selectNumbers()
		{
			homePageSteps.selectNumbersByQuickPick();
		}
		
		@When ("user picks required numbers")
		public void selectNumbersByQuickPick()
		{
			homePageSteps.selectNumbersByQuickPick();
		}
		
		@When ("clicks on Submit")
		public void submitSelectedNumbers(){
			homePageSteps.submitSelectedNumbers();
		}
				
		
		@Then ("user sees following message as Coaching screen2: \"Submit Your Numbers!\"")
		public void verifyCoachingForNumberSubmit()
		{
			homePageSteps.verifycoaching_NumberSubmit();
		}
		
		
		@Then ("user submits first keno card")
		public void firscardsubmission(){
			homePageSteps.submitSelectedNumbers();
		}
		
		
		@Then ("user progresses to view keno ad followed by Choices screen")
		public void SubmitFirstCardandLAndOnChoices() throws InterruptedException 
		{
			homePageSteps.LandOnGameChoicesafterSubmission();	
		}
		
		@Then ("user sees following message as Coaching screen3: \"Play Any Instant Win Game To Unlock Your Next Keno Card!\"")
		public void verifyCoachingForIWGame()
		{
			homePageSteps.verifycoaching_IWGame();
		}
		
		@Then("user with password selects a SFL choice")
		public void selectsChoicePlaysSFL() throws Exception
		{
			//commonSteps.playSFLAsUwoPwd();
			homePageSteps.choiceSelection("SFL");
		}
		
		@Then ("user completes registration after submitting keno card")
		public void PostGamePlayRegistration() throws InterruptedException 
		{
			homePageSteps.submitSelectedNumbers();
			registrationSteps.verifyUserInRegistrationPage();
			registrationSteps.submit_Registration();
		}
		
		@Then ("user plays next Keno card and choice screens")
		public void playRestOfTheScratchCards() throws Exception
		{
			homePageSteps.playScratchPathAlone();
			homePageSteps.verifyNoCoachingScreen();
		}
		
		@Then ("coaching screens do not show anymore on the rest of the keno path")
		public void playRestOfTheKenoCards() throws InterruptedException
		{
			homePageSteps.selectNumbersByQuickPick();
			homePageSteps.verifyNoCoachingScreen();
			homePageSteps.submitSelectedNumbers();
			homePageSteps.LandOnGameChoicesafterSubmission();
			homePageSteps.verifyNoCoachingScreen();
		}
		
	//Changes for UnRecognized User Sign In_Post Game Play story
	    @Then ("It should be navigate to the registration page")
		public void verifyRegPage(){
	    		registrationSteps.verifyUserInRegistrationPage();
		}
	    
	    @Then ("SignIn successfully with valid credential before the next set of keno game has started $credentials")
	    public void signinpostgameplay(ExamplesTable credentials){
	    	signInPageSteps.enterCredentials(credentials);
	    	homePageSteps.verifySuccessRegisration();
	    }
	    @When("Submit the selected numbers as $numberstoselect")
		public void whenSubmitTheSelectedNumbers(List<String> numberstoselect) throws InterruptedException {
	    	System.out.println("Selected numbers for card are: "+numberstoselect);
			homePageSteps.submitSelectedNumbers(numberstoselect);
		}
	 //Changes for Login story
	    @Then("Welcome <first name> <last name> is displayed on the profile bar on the site")
	    public void checkuserafterlogin(){
	    	System.out.println("Check if user name displayed after login along with Welcome");
	    	homePageSteps.checkUserProfile();
	    }
	//Changes for Post Game Play token earning story    
	    @Then("on successful registration, user should be rewarded 1000 tokens")
	    public void validatePostRegistrationTokens()
		{
	    	homePageSteps.validateearnedtokens();
		}
	    
		
		@When("User submits first keno card")
		public void firstCardSubmission()
		{
			homePageSteps.submitSelectedNumbers();
		}
		

		@Then("User submits 2nd keno card")
		public void userSubmitsSecondKenoCard()
		{
			homePageSteps.playSecondKenoCard();
		}

		
		@When ("user picks required numbers on the Keno gamecard")
		public void selectedNumbers()
		{
			homePageSteps.selectNumbersByQuickPick();
		}

		@Then ("user clicks on \"Sign Up Now!\" button")
		public void useClicksOnSignUpButton()
		{
		homePageSteps.clickOnOptinSignupButton();
		}
		
		
		@Then ("\"Thank You\" message should display")
		public void thankYouMessagedisplay()
		{
			homePageSteps.verifyOptinThankYouMessage();			
		}
			
		@Then("\"Email Sign Up Bonus Tokens!\" message shows as per configured tokens in token history page")
		public void verifyOptinTokens() throws InterruptedException

		{
		homePageSteps.verifyOptinTokens();
		}


		@When ("user clicks on tutorials button")
		public void openTutorials()
		{
			homePageSteps.openTutorials();
		}
	
		@When ("user verifies the open screen")
		public void verifyTutorialsOpen()
		{
			homePageSteps.verifyTutorialsOpen();
		}
		
		@Then ("on clicking next button, it should scroll to the last screen while verifying the screens within")
		public void clickTutorialsNext()
		{
			homePageSteps.clickTutorialsNext();
		}
		
		@Then ("on clicking previous button, it should scroll to the first screen while verifyig the screens within")
		public void clickTutorialsPrevious()
		{
			homePageSteps.clickTutorialsPrevious();
		}
		
		@Then ("user should be able to close the screens by clicking close button")
		public void clickTutorialsClose()
		{
			homePageSteps.clickTutorialsClose();
		}
		
		@When ("user clicks on 'Complete Registration' button at top")
		public void clickCompleteRegBtn()
		{
			homePageSteps.clickCompleteRegBtn();
		}
		
		@Then ("should be able to register successfully")
		public void verifySuccessfulRegistration()
		{
			homePageSteps.verifySuccessfulRegistration();
		}
		
		@When ("user navigates to Token History")
		public void navigateToTokenHistory()
		{
			homePageSteps.navigateToTokenHistory();
		}
		
		@When("notes the picked numbers")
	    public void notesThePickedNumbers()
	    {
	        homePageSteps.readPickedNumbers();
	    }
		
		@Then("numbers submitted are same on side rail")
		public void numbersSubmittedSameOnSideRail()
		{
			homePageSteps.numbersSubmittedSameOnSideRail();
		}

		@Then("user with password plays SFL game")
		public void useWithPasswordPlaysSFLGame() throws FindFailed, InterruptedException
			{
			homePageSteps.playPathGameAsUWPwd("SFL");
			}
		
		@When("User observes the leaderboard")
		public void observeLeaderboard() {
			homePageSteps.observeLeaderboard();
		}

		@Then("user should see the Mobile Tab appearing in Leaderboard")
		public void mobileTabAppearingInLeaderboard() throws Exception {
			homePageSteps.mobileTabAppearingInLeaderboard();
		}	
		
}
